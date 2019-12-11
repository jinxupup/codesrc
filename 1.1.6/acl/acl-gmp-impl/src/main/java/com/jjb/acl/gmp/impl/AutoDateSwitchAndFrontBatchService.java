package com.jjb.acl.gmp.impl;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.gmp.TmInstOrgDao;
import com.jjb.acl.biz.gmp.TmInstanceDao;
import com.jjb.acl.biz.gmp.TmSysStatusDao;
import com.jjb.acl.biz.gmp.TmSysStatusLogDao;
import com.jjb.acl.gmp.api.ParameterRefreshRequest;
import com.jjb.acl.infrastructure.TmInstOrg;
import com.jjb.acl.infrastructure.TmInstance;
import com.jjb.acl.infrastructure.TmSysStatus;
import com.jjb.acl.infrastructure.TmSysStatusLog;
import com.jjb.unicorn.facility.exception.ProcessException;

public class AutoDateSwitchAndFrontBatchService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private RabbitTemplate parameterRefreshExchange;

	@Autowired
	private RabbitTemplate branchRefreshAllExchange;

	@Resource
	private AmqpTemplate instanceRouteRefreshTemplate;

	@Autowired
	private TmSysStatusDao tmSysStatusDao;
	@Autowired
	private TmSysStatusLogDao tmSysStatusLogDao;
	@Autowired
	private TmInstanceDao tmInstanceDao;

/*	@Autowired
	private RTmSysStatusLog rSystemStatusLog;

	@Autowired
	private RTmBthInst rTmBthInst;

	@Autowired
	private RTmBthInst rBatchInst;
	
	@Autowired
	private RTmBatch rBatch;

	@Autowired
	private RTmInstance rInstance;

	@Autowired
	private RTmSysStatus rSystemStatus;

	@Autowired
	private RTmAutoBatchLog rTmAutoBatchLog;*/

	@Resource
	private AmqpTemplate amqpTemplate;

//	@Autowired
//	private GlobalManagementService managementService;

	private int dateSwitchTimeInterval;
	@Value("#{env['autoBatchTime']?:'01:00:00'}")
	private String autoBatchTime;

	@Value("#{env['delayTimeForFrontBatch']?:-1}")
	private int delayTimeForFrontBatch;

	@Value("#{env['dateSwitchGraceTime']?:300}")
	private int graceTime;

	private Date startTime;

	private Date endTime;

	private Date nextProcessDate;

	@Autowired
	private Properties env;
	@Autowired
	private TmInstOrgDao tmInstOrgDao;

	private Map<String, JobOperator> operatorCache = new HashMap<String, JobOperator>();

	private ScheduledExecutorService executor;

	/*
     * 先日切
     * 每日autoStartBatchTime时，执行任务doFrontBatch();
    */
	@Transactional()
	public void start() {
		//如果进程没有启动自动跑批，直接return
		if (!"Y".equals(System.getProperty("autoBatchInProcess"))) {
			logger.info("不启动自动跑批:在进程中没有设置启动自动跑批的属性");
			return;
		}
		setDateSwitchTimeInterval();
		TmSysStatus status = tmSysStatusDao.selectById(1);
		status.setAutoBatchTime(autoBatchTime);
		tmSysStatusDao.saveTmSysStatus(status);
		//间隔上次的时间
		long oneDay = 24 * 60 * 60 * 1000;
		//启动第一次任务的延迟时间
		long initDelay = getTimeMillis(autoBatchTime) - System.currentTimeMillis();
		initDelay = initDelay > 0 ? initDelay : oneDay + initDelay;
		executor = Executors.newScheduledThreadPool(1, new InnerThreadFactory());
		executor.scheduleAtFixedRate(
				new Runnable() {
					@Override
					public void run() {
						try {
							TmSysStatus status = tmSysStatusDao.selectById(1);
							if (!"Y".equals(status.getIsAutoStartAllBatch())) {
								logger.info("不启动自动跑批:没有用启动自动跑批的方式启动进程");
								return;
							}
							logger.info("auto Date-Switch And Launch Front Batch ThreadName:" + Thread.currentThread().getName());
							//日切
							try {
								doDateSwitch();
							} catch (Exception e) {
								//saveTmAutoBatchLog(AutoBatchAction.DS,Indicator.N,e);
								throw e;
							}
							//跑批前批量
							try {
								if (delayTimeForFrontBatch >= 0) {
									Thread.sleep(delayTimeForFrontBatch * 1000);
									//	doFrontBatch();
								}
							} catch (Exception e) {
								//saveTmAutoBatchLog(AutoBatchAction.F,Indicator.N,e);
								throw e;
							}

						} catch (Exception e) {
							logger.error("以下异常在自动跑批线程中没有catch,此异常会导致本次自动跑批结束，对下次自动跑批无影响", e);
						}
					}
				},
				initDelay,
				oneDay,
				TimeUnit.MILLISECONDS);
	}

	//日切逻辑
	@Transactional()
	public void doDateSwitch() throws ProcessException {
		//处理日切逻辑
		startTime = new Date();
		endTime = null;
		TmSysStatus status = tmSysStatusDao.selectById(1);
		nextProcessDate = status.getBusinessDate();
		//日切前判断所有的批量实例是否处理
		//	checkBatchInst(status.getProcessDate());
		long currentTime = new Date().getTime();
		long lastSwitchTime = status.getDateSwitchTime().getTime();
		int timeDifference = (int) ((currentTime - lastSwitchTime) / (60 * 60 * 1000));
		if (dateSwitchTimeInterval > 0 && timeDifference < dateSwitchTimeInterval) {
			throw new ProcessException("当前时间与上次日切时间的间隔不足" + dateSwitchTimeInterval + "小时");

		} else {
			nextProcessDate = DateUtils.truncate(nextProcessDate, Calendar.DATE);
			if (nextProcessDate.compareTo(status.getProcessDate()) <= 0) {
				throw new ProcessException("新处理日期不正确");
			}
			status.setLastProcessDate(status.getProcessDate());
			status.setProcessDate(nextProcessDate);
			status.setBusinessDate(DateUtils.addDays(nextProcessDate, 1));
			status.setGraceTime(DateUtils.addSeconds(new Date(), graceTime));    //日切的grace time以当前系统时间为准
			status.setLastDateSwitchTime(status.getDateSwitchTime()); //记录日切时间
			status.setDateSwitchTime(new Date());
			logger.info("进行系统日切，从[{}]切换到[{}]", status.getProcessDate(), status.getBusinessDate());
			//在日切时间戳日志表中新增一条日切时间戳的记录
			TmSysStatusLog statusLog = new TmSysStatusLog();
			statusLog.setBusinessDate(status.getBusinessDate());
			statusLog.setDateSwitchTime(status.getDateSwitchTime());
			statusLog.setGraceTime(status.getGraceTime());
			statusLog.setLastDateSwitchTime(status.getLastDateSwitchTime());
			statusLog.setLastProcessDate(status.getLastProcessDate());
			statusLog.setOperId("jydBatch");
			statusLog.setOperOrg(getOrg());
			statusLog.setProcessDate(status.getProcessDate());
			tmSysStatusLogDao.saveTmSysStatusLog(statusLog);
			refreshAllParameters();//刷新参数
			refreshInstanceRoute();//刷新路由
		}
		endTime = new Date();
		//saveTmAutoBatchLog(AutoBatchAction.DS, Indicator.Y,null);
	}

	//刷新参数
	public void refreshAllParameters() {
		logger.info("发送刷新所有参数消息");
		parameterRefreshExchange.convertAndSend(new ParameterRefreshRequest());    //空对象表明全局刷新
		branchRefreshAllExchange.convertAndSend(new ParameterRefreshRequest());
	}

	//刷新路由
	public void refreshInstanceRoute() {
		logger.info("刷新路由");
		instanceRouteRefreshTemplate.convertAndSend(new Object());    //不需要内容
	}

	//检查是否符合跑批条件
/*	    private void checkBatchInst(Date processDate) throws ProcessException {
			
			QTmBthInst q = QTmBthInst.tmBthInst;
			List<TmBthInst> list = (List<TmBthInst>) rTmBthInst.findAll(q.enableFlag.eq(Indicator.Y));
			if(list != null && list.size() > 0){
				for(TmBthInst batchInst : list){
					if(batchInst.getBatchCompletedDate().compareTo(processDate) != 0){
						throw new ProcessException("存在未处理的批量实例");
					}
						
				}
			}
		}*/

	//获取当前日期
	private long getTimeMillis(String time) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			DateFormat dayFormat = new SimpleDateFormat("yy-MM-dd");
			Date curDate = dateFormat.parse(dayFormat.format(new Date()) + " " + time);
			return curDate.getTime();
		} catch (ParseException e) {
			logger.error("获取当前日期 失败", e);
		}
		return 0;
	}

/*	   //拉起批前的批量
	   public void doFrontBatch(){
		   boolean hasException = false;
		   startTime = new Date();
		   endTime = null;
		   List<Integer> tmBatchIdList = getFrontBatchInstList();
		   for(int i = 0;i<tmBatchIdList.size();i++){
			try{
				startBasicBatch(tmBatchIdList.get(i));
				}catch(Exception e){
					logger.error("出现错误", e);
					hasException = true;
					saveTmAutoBatchLog(AutoBatchAction.F, Indicator.N,e);
				}
			}
		   if (!hasException) {
				endTime = new Date();
				saveTmAutoBatchLog(AutoBatchAction.F, Indicator.Y,null);
		   }
		
	   }
	   
*//*	   //获取批前批量的实例id
	   public  List<Integer> getFrontBatchInstList(){
			List<Integer> tmBatchIdlist = new ArrayList<Integer>();
			QTmBthInst q = QTmBthInst.tmBthInst;
			JPAQuery jpaQuery = new JPAQuery(em);
			List<TmBthInst> TmBatchlist = jpaQuery.from(q).where(q.enableFlag.eq(Indicator.Y).and(q.batchOptStatus.eq(BatchOptStatusDef.F))).orderBy(q.bthInstId.asc()).list(q);
			for(TmBthInst tmBthInst : TmBatchlist ){
				tmBatchIdlist.add(tmBthInst.getBthInstId());
			}
			return tmBatchIdlist;
		}

		@Transactional
		private TmBthInst loadBatchInst(int batchInstId) {
			TmBthInst batchInst = rBatchInst.findOne(batchInstId);
			if (batchInst == null)
				throw new IllegalArgumentException("无效id:" + batchInstId);
			em.lock(batchInst, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
			return batchInst;
		}
		
		*//**/

	/**
	 * 通过消息队列向对应的batch进程发送启动batch的命令
	 *
	 * @param
	 *//**//*
		private JobOperator getCachedOperator(TmBthInst inst) {
			TmInstance instance = rInstance.findOne(inst.getInstanceId());
			String queueName = instance.getInstanceName() + "." + instance.getSystemType().toString() + ".rpc.job-operator";
			queueName = StringUtils.lowerCase(queueName);
			//如果没有缓存就建一个
			if (!operatorCache.containsKey(queueName)){
				AmqpInvokerClientFactoryBean<JobOperator> fb = new AmqpInvokerClientFactoryBean<JobOperator>();
				fb.setAmqpTemplate(amqpTemplate);
				fb.setAsync(false);
				fb.setQueueName(queueName);
				fb.setServiceInterface(JobOperator.class);
				try {
					operatorCache.put(queueName, fb.getObject());
				}
				catch (Exception e) {
					//不可能出错
					throw new ProcessException("出现错误",e);
				}
			}
			logger.info("使用{}访问批量服务", queueName);
			return operatorCache.get(queueName);
		}
		
		//启动一个job   
	    @Transactional 
		public void startBasicBatch(int batchInstId) throws ProcessException {
			TmBthInst batchInst = loadBatchInst(batchInstId);
			TmBatch batch = rBatch.findOne(batchInst.getBthInstId());
			Date processDate = managementService.getSystemStatus().getProcessDate();
			JobOperator operator = getCachedOperator(batchInst);
			try {
				String param = MessageFormat.format("batch.date(date)={0,date,yyyy/MM/dd},batch.inst.id(long)={1,number,0}", processDate, batchInst.getBthInstId());
				long executionId;
				
				switch (batchInst.getBatchStatus()) {
				case UNKNOWN:		//第一次
				case COMPLETED:		//上一批量执行完成
					rBatchInst.save(batchInst);
					executionId = operator.start(batch.getJobName(), param);
					break;
				case FAILED:		//断批
				case STOPPED:		//主动中断
					rBatchInst.save(batchInst);
					executionId = operator.restart(batchInst.getExecutionId());
					break;
				default:
					throw new ProcessException("批量状态不正确：" + batchInst.getBatchStatus());	
				}
				batchInst = rBatchInst.findOne(batchInstId);
				if(batchInst!=null){
					batchInst.setExecutionId(executionId);
					batchInst.setBatchStatus(BatchStatusDef.STARTING);
					logger.info("启动批量:batchInstId[{}]",batchInst.getBthInstId());
				}
			}
			catch (NoSuchJobException e){
				throw new ProcessException("Job不存在");
			}
			catch (JobInstanceAlreadyExistsException e){
				throw new ProcessException("Job实例已存在（批量已运行）");
			}
			catch (JobParametersInvalidException e) {
				throw new ProcessException("无效批量参数");
			}
			catch (JobInstanceAlreadyCompleteException e){
				throw new ProcessException("批量已成功完成");
			}
			catch (NoSuchJobExecutionException e){
				throw new ProcessException("没有此执行id:" + batchInst.getExecutionId());
			}
			catch (JobRestartException e) {
				throw new ProcessException("重启批量失败");
			}
		}*//*
	    
    private void saveTmAutoBatchLog(AutoBatchAction action,Indicator indicator,Exception e){
    	TmAutoBatchLog tmAutoBatchLog = new TmAutoBatchLog();
    	tmAutoBatchLog.setAction(String.valueOf(action));
    	tmAutoBatchLog.setActionDesc(action.getActionDesc());
    	tmAutoBatchLog.setStartTime(startTime);
    	if (null != endTime){
    		tmAutoBatchLog.setEndTime(endTime);
    	}
    	tmAutoBatchLog.setStatus(String.valueOf(indicator));
    	tmAutoBatchLog.setProcessDate(nextProcessDate);
    	if (null != e){
    		StringBuilder sb = new StringBuilder();
    		String message = "" ;
    		if (e.getMessage() != null) {
    			message = e.getMessage();
    		}
    		sb.append(e.getClass().getCanonicalName()+":"+message);
    		for (StackTraceElement stack :e.getStackTrace()){
				sb.append("\nat "+stack.getClassName()+"."+stack.getMethodName()+"("+stack.getFileName()+":"+stack.getLineNumber()+")");
			}
    		tmAutoBatchLog.setExceptionMessage(sb.toString());
    	}
    	try {
			tmAutoBatchLog.setIp(getLocalIP());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}
    	rTmAutoBatchLog.save(tmAutoBatchLog);
    }
    
    /*
     * 获取机构号
     * */
	public String getOrg() {
		String org = null;
/*    	QTmInstance qTmInstance = QTmInstance.tmInstance;
    	QTmInstOrg qTmInstOrg = QTmInstOrg.tmInstOrg;
    	//理论只有一条
    	List<Integer> instanceIds = new JPAQuery(em).from(qTmInstance).where(qTmInstance.instanceName.eq((String) env.get("instanceName"))).list(qTmInstance.instanceId);*/

		String envInstanceName = (String) env.get("instanceName");
		TmInstance tmInstance = new TmInstance();
		tmInstance.setInstanceName(envInstanceName);
		List<TmInstance> tmInstanceList = tmInstanceDao.selectTmInstance(tmInstance);
		Integer instanceId = tmInstanceList.get(0).getInstanceId();

   /* 	if (instanceIds != null) {
    		List<String> orgs = new JPAQuery(em).from(qTmInstOrg).where(qTmInstOrg.instanceId.eq(instanceIds.get(0))).list(qTmInstOrg.org);*/
		if (instanceId != null) {
			List<TmInstOrg> tmInstOrgList = tmInstOrgDao.findByEqInstanceId(instanceId);
			List<String> orgs = new ArrayList<>();
			for (TmInstOrg tmInstOrgs : tmInstOrgList) {
				org=tmInstOrgs.getOrg();
				orgs.add(org);
			}

			for (String var : orgs) {
				if (!"000000000000".equals(var)) {
					org = var;
					break;
				}
			}

		}


		return org;
	}

    
    //由于默认创建的线程不是Daemon线程，所以自己实现。参考默认的DefaultThreadFactory线程工厂
    private static class InnerThreadFactory implements ThreadFactory {
    	@Override
        public Thread newThread(Runnable paramRunnable){
          Thread localThread = new Thread(paramRunnable,"autoDateSwitchAndFrontBatch");
          localThread.setDaemon(true);
          if (localThread.getPriority() != 5) {
            localThread.setPriority(5);
          }
          return localThread;
        }
    }
    
	private  String getLocalIP() {
	String sIP = "";
	InetAddress ip = null;
	try {
			boolean bFindIP = false;
			Enumeration<NetworkInterface> netInterfaces = (Enumeration<NetworkInterface>) NetworkInterface.getNetworkInterfaces();
			while (netInterfaces.hasMoreElements()) {
				if (bFindIP) {
					break;
				}
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				// ----------特定情况，可以考虑用ni.getName判断
				// 遍历所有ip
				Enumeration<InetAddress> ips = ni.getInetAddresses();
				while (ips.hasMoreElements()) {
					ip = (InetAddress) ips.nextElement();
					if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() // 127.开头的都是lookback地址
							&& ip.getHostAddress().indexOf(":") == -1) {
						bFindIP = true;
						break;
					}
				}
			}
    	}catch (Exception e){
		logger.error("获取IP地址 失败", e);
		 }
		if (null != ip){
			sIP = ip.getHostAddress();
		}
		return sIP;
	} 
	
	private void setDateSwitchTimeInterval() {
		String timeInterval=env.getProperty("dateSwitchTimeInterval");
		if(StringUtils.isBlank(timeInterval)){
			dateSwitchTimeInterval=0;
		}
		else{
			try {
				 dateSwitchTimeInterval = Integer.parseInt(env.getProperty("dateSwitchTimeInterval"));
			} catch (NumberFormatException e) {
				logger.error("setDateSwitchTimeInterval 方法异常", e);
				System.exit(1);
			}
		}
	}
}


