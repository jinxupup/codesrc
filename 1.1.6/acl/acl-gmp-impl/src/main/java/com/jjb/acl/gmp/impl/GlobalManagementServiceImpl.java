package com.jjb.acl.gmp.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.biz.gmp.TmInstOrgDao;
import com.jjb.acl.biz.gmp.TmInstanceDao;
import com.jjb.acl.biz.gmp.TmOrgDao;
import com.jjb.acl.biz.gmp.TmSysStatusDao;
import com.jjb.acl.gmp.api.GlobalManagementService;
import com.jjb.acl.gmp.api.OrgInstanceInfo;
import com.jjb.acl.gmp.api.SystemStatus;
import com.jjb.acl.infrastructure.TmInstOrg;
import com.jjb.acl.infrastructure.TmInstance;
import com.jjb.acl.infrastructure.TmOrg;
import com.jjb.acl.infrastructure.TmSysStatus;
import com.jjb.acl.infrastructure.dto.TmOrgInstance;
import com.jjb.acl.infrastructure.dto.TmOrgInstanceTwo;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.mq.config.AmqpInvokerClientFactoryBean;

/**
 * 针对这个大杂烩接口的大杂烩实现。将来有一天如果实在太多了，再把它分拆。
 * @author LI.J
 *
 */
@Service
public class GlobalManagementServiceImpl implements GlobalManagementService
{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TmOrgDao tmOrgDao;
	@Autowired
	private TmInstanceDao tmInstanceDao;
	@Autowired
	private TmInstOrgDao tmInstOrgDao;
	@Autowired
	private TmSysStatusDao tmSysStatusDao;

	@Resource
	private AmqpTemplate amqpTemplate;

	@Override
	public Map<String, Map<String, String>> getInstanceRoute()
/*	{
		if (logger.isDebugEnabled())
			logger.debug("生成前缀与实例映射。");

		Map<String, Map<String, String>> result = new HashMap<String, Map<String,String>>();
		
		JPAQuery query = new JPAQuery(em);
		
		QTmInstOrg qInstOrg = QTmInstOrg.tmInstOrg;
		QTmInstance qInstance = QTmInstance.tmInstance;
		
		//联表查询后按机构、系统前缀、实例名存入两层map
		for (Tuple objs : query.from(qInstOrg, qInstance)
				.where(qInstOrg.instanceId.eq(qInstance.instanceId))
				.list(qInstOrg, qInstance))
		{
			TmInstOrg instOrg = objs.get(qInstOrg);
			TmInstance instance = objs.get(qInstance);

			if (!result.containsKey(instOrg.getOrg()))
				result.put(instOrg.getOrg(), new HashMap<String, String>());

			result.get(instOrg.getOrg()).put(StringUtils.lowerCase(instance.getSystemType().toString()), instance.getInstanceName());
		}
		
		return result;
	}*/
    {
        if (logger.isDebugEnabled())
            logger.debug("生成前缀与实例映射。");
        Map<String, Map<String, String>> result = new HashMap<String, Map<String,String>>();

   /** 查询TmInstOrg,TmInstance中instanceId相等的数据,将需要的三个值放在TmOrgInstance中
         (分别是 查询TmInstOrg中的org ,TmInstance中的SystemType和InstanceName)
    */
      List<TmOrgInstance>  tmOrgInstanceList =tmInstOrgDao.selectInstOrgEqInstance();
        //联表查询后按机构、系统前缀、实例名存入两层map
        for (TmOrgInstance  tmOrgInstance:tmOrgInstanceList ){
            if (!result.containsKey(tmOrgInstance.getTmInstOrgOrg()))
                result.put(tmOrgInstance.getTmInstOrgOrg(), new HashMap<String, String>());

            result.get(tmOrgInstance.getTmInstOrgOrg()).put(StringUtils.lowerCase(tmOrgInstance.getTmInstanceSystemType().toString()), tmOrgInstance.getTmInstanceInstanceName());
        }
        return  result;

    }

	@Override
	public SystemStatus getSystemStatus()
	{
		if (logger.isDebugEnabled())
			logger.debug("取系统状态");

		TmSysStatus status = tmSysStatusDao.selectById(1);
		SystemStatus result = new SystemStatus();
		result.setBusinessDate(status.getBusinessDate());
		result.setProcessDate(status.getProcessDate());
		result.setLastProcessDate(status.getLastProcessDate());
		//FIXME 这个要写数据库里
		result.setLastBusinessDate(DateUtils.add(status.getBusinessDate(), Calendar.DATE, -1));
		//当前时间在grace time之前表时当前是grace time
		//这个值在日切时写入
		result.setGraceTime(status.getGraceTime().compareTo(new Date()) > 0);
		//设置当前日切时间和保上次日切时间
		result.setDateSwitchTime(status.getDateSwitchTime());
		result.setLastDateSwitchTime(status.getLastDateSwitchTime());
		return result;
	}

	@Override
	public Map<String, String> getOrgList() {
		Map<String, String> orgMap = new TreeMap<String, String>();
		List<TmOrg> orgList = tmOrgDao.selectAll();
		if(orgList != null && !orgList.isEmpty()){
			for(TmOrg org : orgList){
				//为了下拉框的显示，写成这样
				orgMap.put(org.getOrg(), org.getOrg()+" - "+org.getOrgName());
			}
		}
		return orgMap;
	}

	@Override
	public List<String> getServeOrg(String system, String instanceName)
	{
		TmInstance instance = tmInstanceDao.findBySystemTypeAndInstanceName(system, instanceName);
		if (instance == null)
			throw new IllegalArgumentException("无效的服务类型与实例名");
		List<String> result = new ArrayList<String>();
		Integer instanceId=instance.getInstanceId();
		for (TmInstOrg instOrg : tmInstOrgDao.findByEqInstanceId(instanceId))
		{
			result.add(instOrg.getOrg());
		}
		return result;
	}

	@Override
	public List<OrgInstanceInfo> getOrgIntanceInfo(String orgId) {
		List<OrgInstanceInfo> orgInsts = new ArrayList<OrgInstanceInfo>();
/*		QTmInstOrg qInstOrg = QTmInstOrg.tmInstOrg;
		QTmInstance qInst = QTmInstance.tmInstance;
		
		JPAQuery query = new JPAQuery(em).from(qInstOrg, qInst)
										 .where(qInstOrg.instanceId.eq(qInst.instanceId), qInstOrg.org.eq(orgId))
										 .orderBy(qInstOrg.instanceId.asc());
		
		for (Tuple objs : query.list(qInstOrg, qInst))
		{
			TmInstOrg instOrg = objs.get(qInstOrg);
			TmInstance instance = objs.get(qInst);
			OrgInstanceInfo instInfo = new OrgInstanceInfo(instance.getInstanceId(), instance.getInstanceName(), 
														instance.getSystemType(), instance.getInstanceMemo(), instOrg.getAccessAddress());
			orgInsts.add(instInfo);
		}
		return orgInsts;*/
        List<TmOrgInstanceTwo>  tmOrgInstanceTwoList =tmInstOrgDao.selectInstOrgEqInstanceTwo(orgId);
        for (TmOrgInstanceTwo tmOrgInstanceTwo:tmOrgInstanceTwoList) {
            OrgInstanceInfo instInfo = new OrgInstanceInfo(tmOrgInstanceTwo.getTmInstanceInstanceId(), tmOrgInstanceTwo.getTmInstanceInstanceName(),
                    tmOrgInstanceTwo.getTmInstanceSystemType(), tmOrgInstanceTwo.getTmInstanceInstanceMemo(), tmOrgInstanceTwo.getTmInstOrgAccessAddress());
            orgInsts.add(instInfo);
        }
		return orgInsts;
	}

	@Override
	public String getInstance(String system, String org)/* {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QTmInstOrg.tmInstOrg, QTmInstance.tmInstance).where(QTmInstOrg.tmInstOrg.instanceId.eq(QTmInstance.tmInstance.instanceId), QTmInstOrg.tmInstOrg.org.eq(org),
				QTmInstance.tmInstance.systemType.eq(system));
		return jpaQuery.uniqueResult(QTmInstance.tmInstance.instanceName);
	}*/

	{
		TmOrgInstance tmOrgInstance = tmInstOrgDao.selectInstanceName(system, org);

			return  tmOrgInstance.getTmInstanceInstanceName();
	}

    @Override
    public void startBatch(String jobName, Map<String, String> param) throws ProcessException {

    }

    @Override
    public Long startIndieBatchByOrg(String org, String jobName, LinkedHashMap<String, String> jobParam) throws ProcessException {
        return null;
    }



/*	@Transactional
	public void updateBatchJobStatus(TmBthInst inst){
		rBatchInst.save(inst);
	}*/
	
/*	@Transactional
	public void updateTmBatch(TmBthInst batchInst,BatchStatusDef batchStatus){
		if(batchStatus !=null) batchInst.setBatchStatus(batchStatus);
		rBatchInst.save(batchInst);
	}*/
/*	@Transactional
	private TmBthInst loadBatchInst(int batchInstId)
	{
		TmBthInst batchInst = rBatchInst.findOne(batchInstId);
		
		if (batchInst == null)
			throw new IllegalArgumentException("无效id:" + batchInstId);
		
		em.lock(batchInst, LockModeType.PESSIMISTIC_FORCE_INCREMENT); 

		return batchInst;
	}
	public TmBthInst initTmBatchStatus(TmBthInst batchInst){
		for(int i = 0; i < 3; i++) {
			try {
				updateTmBatch(batchInst,BatchStatusDef.STARTING);
				break;
			} catch(JpaOptimisticLockingFailureException e) {//乐观锁异常xxxException
				logger.info("乐观锁异常，重试", e);
				batchInst = loadBatchInst(batchInst.getBthInstId());
			}
		}
		return batchInst;
	}
	@Override
	public void startBatch(String jobName, Map<String, String> param)throws ProcessException {
		logger.debug("拉起批量执行开始，JOB名称：["+jobName+"]");
		//通过JOB名称查询批量信息
		JPAQuery query = new JPAQuery(em);
		TmBatch batch = query.from(QTmBatch.tmBatch).where(QTmBatch.tmBatch.jobName.eq(jobName)).singleResult(QTmBatch.tmBatch);
		if(batch == null){
			throw new ProcessException("G001", "批量不存在");
		}
		
		//通过batchid查询batchInst
		TmBthInst batchInst = rBatchInst.findOne(QTmBthInst.tmBthInst.bthInstId.eq(batch.getId()));
		if (batchInst == null){
			throw new ProcessException("G002","批量实例不存在");
		}
		if(batchInst.getBatchStatus().equals(BatchStatusDef.STARTING)
				||batchInst.getBatchStatus().equals(BatchStatusDef.STARTED)
				||batchInst.getBatchStatus().equals(BatchStatusDef.STOPPING)){
			throw new ProcessException("G003","批量正在运行");
		}
//		em.lock(batchInst, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
		
		Map<String, JobOperator> operatorCache = new HashMap<String, JobOperator>();
		TmInstance instance = rInstance.findOne(batchInst.getInstanceId());
		String queueName = instance.getInstanceName() + "." + instance.getSystemType().toString() + ".rpc.job-operator";
		queueName = StringUtils.lowerCase(queueName);
		//如果没有缓存就建一个
		if (!operatorCache.containsKey(queueName))
		{
			AmqpInvokerClientFactoryBean<JobOperator> fb = new AmqpInvokerClientFactoryBean<JobOperator>();
			fb.setAmqpTemplate(amqpTemplate);
			fb.setAsync(false);
			fb.setQueueName(queueName);
			fb.setServiceInterface(JobOperator.class);
			
			try
			{
				operatorCache.put(queueName, fb.getObject());
			}
			catch (Exception e)
			{
				//不可能出错
				logger.error("出现错误", e);
			}
		}
		
		Date processDate = getSystemStatus().getProcessDate();
		JobOperator operator = operatorCache.get(queueName);
		
		BatchStatusDef lastBatchStatus=batchInst.getBatchStatus();
		
		boolean errorFlag=false;
		BatchStatusDef bsd=null;
		try
		{
			StringBuffer sb = new StringBuffer();
			for(String key : param.keySet()){
				sb = sb.append(key).append("=").append(param.get(key)).append(",");
			}
			String batchParam = MessageFormat.format("batch.date(date)={0,date,yyyy/MM/dd},batch.inst.id(long)={1,number,0}",
					processDate, batchInst.getBthInstId());
			batchParam = sb.append(batchParam).toString();
			logger.debug("批量启动参数["+batchParam+"]");
			long executionId;
			
			switch (lastBatchStatus)
			{
			case UNKNOWN:		//第一次
			case COMPLETED:		//上一批量执行完成
				//rBatchInst.save(batchInst);
				initTmBatchStatus(batchInst);
				executionId = operator.start(batch.getJobName(), batchParam);
				break;
			case FAILED:		//断批
			case STOPPED:		//主动中断
				//rBatchInst.save(batchInst);
				initTmBatchStatus(batchInst);
				executionId = operator.restart(batchInst.getExecutionId());
				break;
			default:
				throw new ProcessException("批量状态不正确：" + batchInst.getBatchStatus());	
			}

			
			for(int i = 0; i < 3; i++) {
				try {
					batchInst = rBatchInst.findOne(batchInst.getBthInstId());
					batchInst.setExecutionId(executionId);
					updateBatchJobStatus(batchInst);
					break;
				} catch(JpaOptimisticLockingFailureException e) {//乐观锁异常xxxException
					logger.info("乐观锁异常，重试", e);
				}
			}
		}
		catch (NoSuchJobException e)
		{
			errorFlag=true;
			bsd=BatchStatusDef.FAILED;
			throw new ProcessException("Job不存在");
		}
		catch (JobInstanceAlreadyExistsException e)
		{
			errorFlag=true;
			bsd=lastBatchStatus;
			throw new ProcessException("Job实例已存在（批量已运行）");
		}
		catch (JobParametersInvalidException e) 
		{
			errorFlag=true;
			bsd=BatchStatusDef.FAILED;
			throw new ProcessException("无效批量参数");
		}
		catch (JobInstanceAlreadyCompleteException e)
		{
			errorFlag=true;
			throw new ProcessException("批量已成功完成");
		}
		catch (NoSuchJobExecutionException e)
		{
			errorFlag=true;
			bsd=BatchStatusDef.FAILED;
			throw new ProcessException("没有此执行id:" + batchInst.getExecutionId());
		}
		catch (JobRestartException e) 
		{
			errorFlag=true;
			bsd=BatchStatusDef.FAILED;
			throw new ProcessException("重启批量失败");
		}finally{
			if(errorFlag && bsd !=null){
				for(int i = 0; i < 3; i++) {
					try {
						batchInst = rBatchInst.findOne(batchInst.getBthInstId());
						batchInst.setBatchStatus(bsd);
						updateBatchJobStatus(batchInst);
						break;
					} catch(JpaOptimisticLockingFailureException e) {//乐观锁异常xxxException
						logger.info("乐观锁异常，重试", e);
					}
				}
			}
		}
		logger.debug("拉起批量执行启动结束");
	}*/

/*	@Transactional
	@Override
	public Long startIndieBatchByOrg(String org, String jobName, LinkedHashMap<String, String> jobParam) throws ProcessException {
		if (StringUtils.isBlank(org) || StringUtils.isBlank(jobName) || null == jobParam) {
			throw new ProcessException("方法参数有误，请检查传入参数");
		}
		
		if (!org.equals(jobParam.get("org"))) {
			throw new ProcessException("机构号与批量参数中机构号不一致");
		}
		
		logger.debug("机构[{}]，执行批量[{}]", org, jobName);
		
		//获取TmBatch同时根据BatchId和Org查询TmIndieBthInst
		TmBatch batch = new JPAQuery(em).from(QTmBatch.tmBatch).where(QTmBatch.tmBatch.jobName.eq(jobName)).singleResult(QTmBatch.tmBatch);
		if (null == batch) {
			throw new ProcessException("G001", "批量不存在");
		}
		
		String indieBatchInstId = jobParam.get(TmIndieBthInst.P_Id);
		String systemType = jobParam.get("systemType");
		jobParam.remove(TmIndieBthInst.P_Id);
		jobParam.remove("systemType");
		TmIndieBthInst indieBatchInst = null;
		if(StringUtils.isNotBlank(indieBatchInstId)){
			indieBatchInst = em.find(TmIndieBthInst.class, Integer.valueOf(indieBatchInstId));
		}else{
			
			if (StringUtils.isBlank(systemType)) {
				throw new ProcessException("方法参数有误，没有上送系统类型参数");
			}
			QTmInstOrg qInstOrg = QTmInstOrg.tmInstOrg;
			QTmInstance qInst = QTmInstance.tmInstance;
			int instanceId = new JPAQuery(em).from(qInstOrg, qInst)
											 .where(qInstOrg.instanceId.eq(qInst.instanceId).and(qInstOrg.org.eq(org))
													 .and(qInst.systemType.eq(systemType)))
													 .uniqueResult(qInst.instanceId);
											 
			indieBatchInst = new TmIndieBthInst();
			indieBatchInst.setBatchId(batch.getId());
			indieBatchInst.setBatchParam("");
			indieBatchInst.setBatchStatus(BatchStatusDef.UNKNOWN);
			indieBatchInst.setEnableFlag(Indicator.Y);
			indieBatchInst.setInstanceId(instanceId);
			indieBatchInst.setOrg(org);
			indieBatchInst.setIndieTime(System.currentTimeMillis());
			em.persist(indieBatchInst);
		}
		
		if (null == indieBatchInst){
			throw new ProcessException("G002","批量实例不存在");
		}
		
		String batchParam = genBatchParam(jobParam, getSystemStatus().getProcessDate(), indieBatchInst.getId());
		if(StringUtils.isNotBlank(indieBatchInstId)){
			verifyJobRrunning(indieBatchInst, batchParam);
		}
		
		JobOperator jobOperator = genJobOperatorByInstanceId(indieBatchInst.getInstanceId());
		return performJob(jobOperator, batch, indieBatchInst, batchParam);
	}*/
	
	/**
	 * <p>判断作业是否能够执行</p>
	 * @return
	 */
/*
	private void verifyJobRrunning(TmIndieBthInst indieBatchInst, String batchParam) {
		if (BatchStatusDef.STARTING.equals(indieBatchInst.getBatchStatus()) ||
			BatchStatusDef.STARTED.equals(indieBatchInst.getBatchStatus()) ||
			BatchStatusDef.STOPPING.equals(indieBatchInst.getBatchStatus())) {
			throw new ProcessException("G003","批量正在运行");
		} else if(Indicator.Y.equals(indieBatchInst.getEnableFlag())) {
			//支持断点续批，且状态为FAILED和STOPPED且参数状态和失败批量不一致
			String batchOldParam = indieBatchInst.getBatchParam();
			String regex = "batch\\.date\\(date\\)=\\d{4}/\\d{2}/\\d{2}";
			if(batchParam.startsWith("batch.date(date)")){
				regex = regex +",?";
			}else{
				regex = ",?" + regex;
			}
			batchParam = batchParam.replaceAll(regex, "");
			batchOldParam = batchOldParam.replaceAll(regex, "");
			if (0 != batchParam.compareTo(batchOldParam) &&
				(BatchStatusDef.FAILED.equals(indieBatchInst.getBatchStatus()) || BatchStatusDef.STOPPED.equals(indieBatchInst.getBatchStatus()))) {
				throw new ProcessException("G003","存在未完成的批量，无法重新开始新批量");
			}
		}
	}
*/

	/**
	 * <p>根据InstanceId获取对应批量实例JobOperator</p>
	 * @param instanceId
	 * @return
	 */
	private JobOperator genJobOperatorByInstanceId(Integer instanceId) {
		
		Map<String, JobOperator> operatorCache = new HashMap<String, JobOperator>();
		TmInstance instance = tmInstanceDao.selectByInstanceId(instanceId);
		String queueName = instance.getInstanceName() + "." + instance.getSystemType().toString() + ".rpc.job-operator";
		queueName = StringUtils.lowerCase(queueName);
		//如果没有缓存就建一个
		if (!operatorCache.containsKey(queueName)) {
			AmqpInvokerClientFactoryBean<JobOperator> fb = new AmqpInvokerClientFactoryBean<JobOperator>();
			fb.setAmqpTemplate(amqpTemplate);
			fb.setAsync(false);
			fb.setQueueName(queueName);
			fb.setServiceInterface(JobOperator.class);
			
			try {
				operatorCache.put(queueName, fb.getObject());
			} catch (Exception e) {
				//不可能出错
				logger.error("出现错误", e);
			}
		}
		return operatorCache.get(queueName);
	}
	
	/**
	 * <p>根据批量参数，业务日期，实例号生成批量执行参数</p>
	 * @param param
	 * @param processDate
	 * @param batchInstId
	 * @return
	 */
	private String genBatchParam(Map<String, String> param, Date processDate, Integer batchInstId) {
		StringBuffer sb = new StringBuffer();
		for(String key : param.keySet()){
			sb = sb.append(key).append("=").append(param.get(key)).append(",");
		}
		String batchParam = MessageFormat.format("batch.date(date)={0,date,yyyy/MM/dd},indie.batch.inst.id(long)={1,number,0}",
				processDate, batchInstId);
		batchParam = sb.append(batchParam).toString();
		logger.debug("批量启动参数["+batchParam+"]");
		return batchParam;
	}

	/**
	 * <p>根据传入参数执行Job</p>
	 * @param operator Job执行器
	 * @param batch 批量表
	 * @param indieBatchInst 机构实例
	 * @param batchParam 参数
	 * @return
	 */
/*
	private Long performJob(JobOperator operator, TmBatch batch, TmIndieBthInst indieBatchInst, String batchParam) {
		long executionId;
		
		//如果实例不支持断点续批，则强行把FAILED和STOPPED更改为COMPLETED启动新批量
		if (Indicator.N.equals(indieBatchInst.getEnableFlag()) && (BatchStatusDef.FAILED.equals(indieBatchInst.getBatchStatus()) || BatchStatusDef.STOPPED.equals(indieBatchInst.getBatchStatus()))) {
			indieBatchInst.setBatchStatus(BatchStatusDef.COMPLETED);
		}
		try {
			switch (indieBatchInst.getBatchStatus()) {
				case UNKNOWN:	//第一次
				case COMPLETED:
					//启动批量则把唯一标识写入批量实例
					indieBatchInst.setIndieTime(new Date().getTime());
					rTmIndieBthInst.save(indieBatchInst);
					executionId = operator.start(batch.getJobName(), batchParam);
					break;
				case FAILED:	//断批
				case STOPPED:	//主动中断
					rTmIndieBthInst.save(indieBatchInst);
					executionId = operator.restart(indieBatchInst.getExecutionId());
					break;
				default:
					throw new ProcessException("批量状态不正确：" + indieBatchInst.getBatchStatus());	
			}
			indieBatchInst = rTmIndieBthInst.findOne(indieBatchInst.getId());
			if(indieBatchInst!=null){
				indieBatchInst.setExecutionId(executionId);
				indieBatchInst.setBatchStatus(BatchStatusDef.STARTING);
				indieBatchInst.setBatchParam(batchParam);
			}
		
		} catch (NoSuchJobException e) {
			throw new ProcessException("Job不存在");
		} catch (JobInstanceAlreadyExistsException e) {
			throw new ProcessException("Job实例已存在（批量已运行）");
		} catch (JobParametersInvalidException e) {
			throw new ProcessException("无效批量参数");
		} catch (JobInstanceAlreadyCompleteException e) {
			throw new ProcessException("批量已成功完成");
		} catch (NoSuchJobExecutionException e) {
			throw new ProcessException("没有此执行id:" + indieBatchInst.getExecutionId());
		} catch (JobRestartException e) {
			throw new ProcessException("重启批量失败");
		}
		return executionId;
	}
*/

}
