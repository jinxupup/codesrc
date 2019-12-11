package com.jjb.acl.gmp.impl;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jjb.acl.biz.gmp.TmSysStatusDao;
import com.jjb.acl.facility.enums.sys.AutoBatchAction;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmSysStatus;

@Service
public class AutoLaunchBatchService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("#{env['maxSleepTime']?:'60000'}")
	private long  maxSleepTime;//控制sleep间隔
	
	@Value("#{env['launchStartTime']?:'22:00:00'}")
	private String  startTime;//自动拉批开始时间
	
	@Value("#{env['launchEndTime']?:'06:00:00'}")
	private String  endTime;//自动拉批结束时间
	@Autowired
	private TmSysStatusDao tmSysStatusDao;
	
	public void start() {
		 if (!"Y".equals(System.getProperty("autoBatchInProcess"))) {
			   logger.info("不启动自动跑批:在进程中没有设置启动自动跑批的属性");
			   return;
		   }
		logger.info("自动启批量服务启动成功!!!");
		Thread autoBatchTask = new Thread(new AutoBatchTask(), "AutoBatchTask");
		autoBatchTask.setDaemon(true);
		autoBatchTask.start();
	}
	
	class AutoBatchTask implements Runnable {
		private boolean flags = true; 
		
		//private Map<Integer, String> batchParams;
		
		@Override
		public void run() {
	/*
			while(flags) {
				if(isLaunchTime(startTime, endTime)){
					if(isLaunchTogether()){
						try {
							batchTogetherProcess();
						} catch(Throwable e) {
							logger.error("批中和批后同时拉起处理异常，等待重新拉起...", e);
							saveTmAutoBatchLog(AutoBatchAction.MB, new Date(),null,Indicator.N,null);
						}
					} else {
						try {
							batchProcess(BatchOptStatusDef.F, BatchOptStatusDef.M);
						} catch(Throwable e) {
							logger.error("批中拉起处理异常，等待重新拉起...", e);
							saveTmAutoBatchLog(AutoBatchAction.M, new Date(),null,Indicator.N,null);
						}
						
						try {
							batchProcess(BatchOptStatusDef.M, BatchOptStatusDef.B);
						} catch(Throwable e) {
							logger.error("批后拉起处理异常，等待重新拉起...", e);
							saveTmAutoBatchLog(AutoBatchAction.B, new Date(),null,Indicator.N,null);
						}
					}
				}
				
				try {
					//休眠maxSleepTime时间
					Thread.sleep(maxSleepTime);
				} catch (InterruptedException e) {
					logger.error("自动启批线程等待异常...", e);
				}
			}*/
		}
		
		/**
		 * 批中、批后分别拉起操作
		 * @param currStatus
		 * @param nextStatus
		 */
/*
		private void batchProcess(BatchOptStatusDef currStatus, BatchOptStatusDef nextStatus) {
			 Date stime = new Date();
			 Date etime = null;
		     boolean hasException = false;
			//批前、批中是否自动拉起批量
			BooleanExpression booleanExpression = null;
			if(BatchOptStatusDef.F == currStatus) {
				booleanExpression = qTmBthJobStatus.batchMidInd.eq(Indicator.Y);
			} else if(BatchOptStatusDef.M == currStatus) {
				booleanExpression = qTmBthJobStatus.batchBackInd.eq(Indicator.Y);
			}
			List<TmBthJobStatus> bjsList = new JPAQuery(em).from(qTmBthJobStatus)
					.where(booleanExpression)
					.list(qTmBthJobStatus);
			
			//自动拉起批中、批前
			if(bjsList != null && bjsList.size() > 0) {
				TmBthJobStatus TmBthJobStatus = bjsList.get(0);
				
				Date processDate = globalManagementService.getSystemStatus().getProcessDate();//批量日期
				//批量操作状态为批前（批中）&& 批量状态为完成  && 批量时间为当前批量时间 && 启动批量标志位Y
				long completeBatchCount = new JPAQuery(em).from(qTmBthInst)
					.where(qTmBthInst.batchOptStatus.eq(currStatus)
						.and(qTmBthInst.batchStatus.in(BatchStatusDef.COMPLETED, BatchStatusDef.FINISHED))
						.and(qTmBthInst.batchCompletedDate.eq(processDate))
						.and(qTmBthInst.enableFlag.eq(Indicator.Y)))
					.count();
				long batchCount = new JPAQuery(em).from(qTmBthInst)
						.where(qTmBthInst.batchOptStatus.eq(currStatus)
							.and(qTmBthInst.enableFlag.eq(Indicator.Y)))
						.count();
				
				//完成的批量数 == 总批量数  && 批中(批后)日期不等于当前批量日期 
				Date batchDate=new Date();
				if(currStatus == BatchOptStatusDef.F){
					batchDate = TmBthJobStatus.getBatchMidDate();
				} else if(currStatus == BatchOptStatusDef.M){
					batchDate = TmBthJobStatus.getBatchBackDate();
				}
				if(completeBatchCount == batchCount
						&& !batchDate.equals(processDate)) {
					// 批中（批后）批量  && 还未启动  && 启动批量标志位Y
					List<TmBthInst> launchBatchList = new JPAQuery(em).from(qTmBthInst)
							.where(qTmBthInst.batchOptStatus.eq(nextStatus)
									.and(qTmBthInst.batchStatus.in(BatchStatusDef.COMPLETED, BatchStatusDef.FINISHED))
									.and(qTmBthInst.batchCompletedDate.ne(processDate))
									.and(qTmBthInst.enableFlag.eq(Indicator.Y)))
							.list(qTmBthInst);
					//未启动的批量拉起批量
					AutoBatchAction action = null;
					if (nextStatus == BatchOptStatusDef.M) {
					    action = AutoBatchAction.M;
					}
					else {
						action = AutoBatchAction.B;
					}
					for(TmBthInst batchInst:launchBatchList) {
						//启动批量
						Map<String, String> jobParams = new LinkedHashMap<String, String>();
						try {
							globalManagementService.startBatch(getJobName(batchInst.getBthInstId()), jobParams);
						}catch(Exception e){
							logger.error("出现错误", e);
							hasException = true;
							saveTmAutoBatchLog(action, stime,etime,Indicator.N,e);
						}
					}
					if (launchBatchList != null && launchBatchList.size() > 0 &&  !hasException) {
						etime = new Date();
						saveTmAutoBatchLog(action, stime,etime,Indicator.Y,null);
				   }
					
					//更新批中、批后日期
					if(BatchOptStatusDef.F == currStatus) {
						TmBthJobStatus.setBatchMidDate(processDate);
					} else if(BatchOptStatusDef.M == currStatus) {
						TmBthJobStatus.setBatchBackDate(processDate);
					}
					rTmBthJobStatus.save(TmBthJobStatus);
				}
			}
		}
*/

		/**
		 * 是否在自动拉起批量时间段内
		 * @param startTime
		 * @param endTime
		 * @return
		 */
		private boolean isLaunchTime(String startTime, String endTime){
			Calendar calendar = Calendar.getInstance();
			Date currentTime = calendar.getTime();
			SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm:ss");
			
			String curTimeStr = sdFormat.format(currentTime);
			if(startTime.compareTo(endTime) > 0 ){
				//跨零点,区间之外不启动
				if(curTimeStr.compareTo(startTime) < 0 && curTimeStr.compareTo(endTime) > 0){
					return false;
				}else{
					return true;
				}
			} else if(startTime.compareTo(endTime) < 0 ){
				//不跨零点,区间之内启动
				if(curTimeStr.compareTo(startTime) >= 0 && curTimeStr.compareTo(endTime) <= 0){
					return true;
				}else{
					return false;
				}
			}
			
			return false;
		}
		
		/**
		 * 是否同时拉起批中和批后
		 * @return
		 */
/*
		private boolean isLaunchTogether(){
			BooleanExpression booleanExpression = qTmBthJobStatus.batchTogetherInd.eq(Indicator.Y);
			long count = new JPAQuery(em).from(qTmBthJobStatus)
					.where(booleanExpression).count();
			if(count > 0){
				return true;
			}
			return false;
			
		}
*/

		/**
		 * 同时拉起批中和批后
		 */
/*
		private void batchTogetherProcess(){
			 boolean hasException = false;
			 Date stime = new Date();
			 Date etime = null;
			 List<TmBthJobStatus> bjsList = new JPAQuery(em).from(qTmBthJobStatus)
					.where(qTmBthJobStatus.batchTogetherInd.eq(Indicator.Y))
					.list(qTmBthJobStatus);
			
			//批中、批前全部自动拉起
			if(bjsList != null && bjsList.size() > 0) {
				TmBthJobStatus TmBthJobStatus = bjsList.get(0);
				
				Date processDate = globalManagementService.getSystemStatus().getProcessDate();//批量日期
				//批量操作状态为批前（批中）&& 批量状态为完成  && 批量时间为当前批量时间 && 启动批量标志位Y
				long completeBatchCount = new JPAQuery(em).from(qTmBthInst)
					.where(qTmBthInst.batchOptStatus.eq(BatchOptStatusDef.F)
						.and(qTmBthInst.batchStatus.in(BatchStatusDef.COMPLETED, BatchStatusDef.FINISHED))
						.and(qTmBthInst.batchCompletedDate.eq(processDate))
						.and(qTmBthInst.enableFlag.eq(Indicator.Y)))
					.count();
				long batchCount = new JPAQuery(em).from(qTmBthInst)
						.where(qTmBthInst.batchOptStatus.eq(BatchOptStatusDef.F)
							.and(qTmBthInst.enableFlag.eq(Indicator.Y)))
						.count();
				
				//完成的批量数 == 总批量数  && 批中(批后)日期不等于当前批量日期 
				Date batchTogetherDate = TmBthJobStatus.getBatchTogetherDate();
				if(completeBatchCount == batchCount
						&& !batchTogetherDate.equals(processDate)) {
					// 批中（批后）批量  && 还未启动  && 启动批量标志位Y
					List<TmBthInst> launchBatchList = new JPAQuery(em).from(qTmBthInst)
							.where(qTmBthInst.batchOptStatus.in(BatchOptStatusDef.M, BatchOptStatusDef.B)
									.and(qTmBthInst.batchStatus.in(BatchStatusDef.COMPLETED, BatchStatusDef.FINISHED))
									.and(qTmBthInst.batchCompletedDate.ne(processDate))
									.and(qTmBthInst.enableFlag.eq(Indicator.Y)))
							.list(qTmBthInst);
					//未启动的批量拉起批量
					for(TmBthInst batchInst:launchBatchList) {
						//启动批量
						Map<String, String> jobParams = new LinkedHashMap<String, String>();
						try {
							//防止完成的批量又被拉起
							globalManagementService.startBatch(getJobName(batchInst.getBthInstId()), jobParams);
						}catch(Exception e) {
							logger.error("出现错误", e);
							hasException = true;
							saveTmAutoBatchLog(AutoBatchAction.MB, stime,etime,Indicator.N,e);
						}
						
					}
					if (launchBatchList != null && launchBatchList.size() > 0 && !hasException ) {
						etime = new Date();
						saveTmAutoBatchLog(AutoBatchAction.MB, stime,etime,Indicator.Y,null);
				   }
					//更新批中、批后、同时拉批日期
					TmBthJobStatus.setBatchMidDate(processDate);
					TmBthJobStatus.setBatchBackDate(processDate);
					TmBthJobStatus.setBatchTogetherDate(processDate);
					
					rTmBthJobStatus.save(TmBthJobStatus);
				}
			}
		}
*/

		/*private void initParams(){
			List<TmBatch> tmBatchList = new JPAQuery(em).from(qTmBatch).list(qTmBatch);
			batchParams = new HashMap<Integer, String>();
			for(TmBatch tmBatch:tmBatchList){
				batchParams.put(tmBatch.getBatchId(), tmBatch.getJobName());
			}
		}*/
/*
		private String  getJobName(Integer batchId){
			
		   TmBatch tmBatch=new JPAQuery(em).from(qTmBatch).where(qTmBatch.id.eq(batchId)).list(qTmBatch).get(0);
			
		   return tmBatch.getJobName();
		}
*/

	}
	
	private void saveTmAutoBatchLog(AutoBatchAction action,Date startTime,Date endTime,Indicator indicator,Exception e)
    {
		/*if (!isSaveException()) {
			return ;
		}
    	TmAutoBatchLog tmAutoBatchLog = new TmAutoBatchLog();
    	tmAutoBatchLog.setAction(action);
    	tmAutoBatchLog.setActionDesc(action.getActionDesc());
    	tmAutoBatchLog.setStartTime(startTime);
    	TmSysStatus status = rSystemStatus.findOne(1);
    	if (null != endTime){
    		tmAutoBatchLog.setEndTime(endTime);
    	}
    	tmAutoBatchLog.setStatus(indicator);
    	tmAutoBatchLog.setProcessDate(status.getProcessDate());
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
			e1.printStackTrace();
		}
    	rTmAutoBatchLog.save(tmAutoBatchLog);*/
    }
	
	public boolean isSaveException() {
		TmSysStatus status = tmSysStatusDao.selectById(1);
		return "Y".equals(status.getIsAutoStartAllBatch());
	}
	
	private String getLocalIP() {
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
		logger.error("将工作统计数据导出到excel 失败", e);
		 }
		if (null != ip){
			sIP = ip.getHostAddress();
		}
		return sIP;
	} 
}
