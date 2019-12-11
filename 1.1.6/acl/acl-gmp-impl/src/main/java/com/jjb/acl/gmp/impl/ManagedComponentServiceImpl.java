package com.jjb.acl.gmp.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.biz.gmp.TmNodeDao;
import com.jjb.acl.biz.gmp.TmProcessDao;
import com.jjb.acl.facility.enums.sys.ProcessRunningStatus;
import com.jjb.acl.gmp.api.BatchJobStatus;
import com.jjb.acl.gmp.api.BatchStepStatus;
import com.jjb.acl.gmp.api.ManagedComponentService;
import com.jjb.acl.gmp.api.ProcessStatus;
import com.jjb.acl.infrastructure.TmBthInst;
import com.jjb.acl.infrastructure.TmBthStepExec;
import com.jjb.acl.infrastructure.TmNode;
import com.jjb.acl.infrastructure.TmProcess;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.service.ServiceDaemon;

@Service
@Transactional
public class ManagedComponentServiceImpl implements ManagedComponentService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final String DEFAULT_JAVA_HOME = "/usr/java/default";

	private static final String DEFAULT_JSVC = "/usr/jsvc";

	@Autowired
	private SSHFacility sshFacility;

	@Autowired
	private InstanceFacility instanceFacility;
	@Autowired
	private TmProcessDao tmProcessDao;
	@Autowired
	private TmNodeDao tmNodeDao;

	@Transactional
	private TmProcess updateTmProcess(int processId, ProcessRunningStatus status) {
		TmProcess process = tmProcessDao.findOne(processId);
		process.setProcessStatus(status.name());
		process.setLastStartup(new Date());
		process.setHstHeapFree("100.00%");
		tmProcessDao.update(process);
		return process;
	}

	private void doJsvcCommand(int processId, String commandArgument, ProcessRunningStatus status)
			throws ProcessException {
		// 在关闭时，由于这个过程非常快，极有可能被进程关闭消息同步更新状态造成乐观锁问题，所以要悲观锁定。
		TmProcess process = tmProcessDao.findOne(processId);

		if (logger.isDebugEnabled())
			logger.debug("启动进程:{}", processId);
		process.setProcessStatus(String.valueOf(status));
		process.setLastStartup(new Date());
		process.setHstHeapFree("100.00%");
		tmProcessDao.update(process);
		TmNode node = tmNodeDao.findOne(process.getNodeCode());

		String command;

		for (int i = 0; i < 3; i++) {
			try {
				process = updateTmProcess(processId, status);
				break;
			} catch (Exception e) {// 乐观锁异常xxxException
				logger.info("乐观锁异常，重试", e);
			}
		}

		switch (process.getProcessType()) {
		case "DAEMON":
			command = "{0} " + commandArgument +
				"-home {1} " +
				"-cp {2}/lib/{3}-{5}/{4}-{5}.jar " +
				"-outfile {2}/logs/{4}-{5}-{6}.out " +
				"-errfile {2}/logs/{4}-{5}-{6}.err " +
				"-pidfile {2}/pids/{4}-{6}.pid " +
				"-Dapp.home=${2} " +
				"-Dprocess.id={6} " +
				"-Dartifact.id={4} " +
				"-Dversion={5} " +
				"{7} " +
				"{8} ";
			break;
		case "CATALINA":
			command = "{0} " + commandArgument +
					"-home {1} " +
					"-cp {9}/bin/bootstrap.jar:{9}/bin/commons-daemon.jar:{9}/bin/tomcat-juli.jar " +
					"-outfile {2}/logs/{4}-{5}-{6}.out " +
					"-errfile {2}/logs/{4}-{5}-{6}.err " +
					"-pidfile {2}/pids/{4}-{6}.pid " +
					"-Dprocess.id={6} " +
					"-Dapp.home=${2} " +
					"-Duser.timezone=GMT+08 " +          //设置时区，解决夏令时问题
					"-Dcatalina.home={9} " +
					"-Dcatalina.base={2}/catalina " +			//这个排在参数前面，可以用JVM_ARGS覆盖
					"-Dcontext.path=/{4} " +
					"-Dartifact.id={4} " +
					"-Dversion={5} " +
					"-Dwar.file={2}/lib/{3}-{5}/{4}-{5}.war " +
					"{7} " +
					"org.apache.catalina.startup.Bootstrap ";
			break;
		default:
			throw new IllegalArgumentException();
		}

		command = MessageFormat.format(
				command,
				StringUtils.defaultIfEmpty(node.getJsvc(), DEFAULT_JSVC),				//0
				StringUtils.defaultIfEmpty(node.getJavaHome(), DEFAULT_JAVA_HOME),		//1
				node.getAppHome(),														//2
				getSystemNameFromTmProcess(process),						            //3
				process.getArtifactId(),												//4
				process.getArtifactVersion(),											//5
				process.getProcessId().toString(),										//6
				buildJVMArgs(process),													//7
				ServiceDaemon.class.getCanonicalName(),									//8
				node.getCatalinaHome()													//9
				);

		int exitCode = sshFacility.issueCommand(process.getNodeCode(), command,
				instanceFacility.loadInstanceEnvironment(process.getInstanceId()));
		if (exitCode != 0)
			throw new ProcessException("服务器响应码为[" + exitCode + "], 请参阅服务器日志");
	}

	@Override
	public void startProcess(int processId) throws ProcessException {
		doJsvcCommand(processId, "", ProcessRunningStatus.STARTING); // 默认是启动
	}

	@Override
	public void stopProcess(int processId) throws ProcessException {
		doJsvcCommand(processId, " -stop ", ProcessRunningStatus.STOPPING);
	}

	@Transactional
	public void updateTmProcessStatus(TmProcess process) {
		tmProcessDao.update(process);
	}

	@Override
	public void updateProcessStatus(int processId, ProcessStatus status) {

		TmProcess process = loadProcess(processId);

		ProcessRunningStatus oldStatus = ProcessRunningStatus.valueOf(process.getProcessStatus());

		Date oldTime = process.getLastHeartbeat();

		Date oldDate = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

		try {
			oldDate = oldTime != null ? sdf.parse(sdf.format(oldTime)) : null;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			logger.info("时间解析异常", e);
		}

		Date now = new Date();

		Date date1 = new Date(now.getTime() - 2000);

		if (oldTime != null && oldStatus == ProcessRunningStatus.STOPPED
				&& status.getRunningStatus() == ProcessRunningStatus.RUNNING && date1.before(oldDate)) {
			return;
		}

		process.setLastHeartbeat(now);
		process.setProcessStatus(status.getRunningStatus().name());
		process.setJmxHeapFree(status.getHeapFree());

		// 如果当前堆空闲百分比小于历史最小堆空闲百分比，则更新历史最小堆空闲百分比为当前堆空闲百分比
		updateProcessHstHeapFree(process);

		if (status.getRunningStatus() == ProcessRunningStatus.STOPPED) {
			// 清理状态
			process.setJmxHeapFree(null);
			process.setLastHeartbeat(null);
		}
		TmProcess targetProcess = new TmProcess();
		BeanUtils.copyProperties(process, targetProcess);
		for (int i = 0; i < 3; i++) {// 乐观锁异常 终态 重试，保证成功
			try {
				updateTmProcessStatus(targetProcess);
				break;
			} catch (Exception e) {// 乐观锁异常 终态 重试
				if (status.getRunningStatus() == ProcessRunningStatus.STOPPED) {
					targetProcess = loadProcess(processId);
					targetProcess.setLastHeartbeat(now);
					targetProcess.setProcessStatus(status.getRunningStatus().name());
					targetProcess.setJmxHeapFree(status.getHeapFree());

					// 如果当前堆空闲百分比小于历史最小堆空闲百分比，则更新历史最小堆空闲百分比为当前堆空闲百分比
					updateProcessHstHeapFree(targetProcess);

					// 清理状态
					targetProcess.setJmxHeapFree(null);
					targetProcess.setLastHeartbeat(null);

					logger.info("乐观锁异常 ，终态" + status.getRunningStatus() + "，重试!"+ e.getMessage());
				} else {
					logger.info("乐观锁异常，非终态直接返回!"+ e.getMessage());
					return;
				}
			}
		}
		logger.info("收到进程编号为[{}]的心跳报文", processId);
	}

	private void updateProcessHstHeapFree(TmProcess process) {

		String heapFree = process.getJmxHeapFree();
		if (!StringUtils.isEmpty(heapFree)) {

			// 取得百分比,将百分比的数值转成BigDecimal进行比较大小
			String[] heapFreeArr = heapFree.split("\\(");
			String currentHeap = heapFreeArr[0].replace("%", "").trim();
			if (!StringUtils.isEmpty(process.getHstHeapFree())) {
				String[] hstHeapFreeArr = process.getHstHeapFree().split("\\(");
				String hstHeap = hstHeapFreeArr[0].replace("%", "").trim();

				BigDecimal bCurrentHeap = new BigDecimal(currentHeap);
				BigDecimal bHstHeap = new BigDecimal(hstHeap);
				if (bCurrentHeap.compareTo(bHstHeap) < 0) {
					process.setHstHeapFree(heapFree);
					process.setHstHeapFreeTime(process.getLastHeartbeat());
				}
			} else {
				process.setHstHeapFree(heapFree);
				process.setHstHeapFreeTime(process.getLastHeartbeat());
			}

		}
	}

	/**
	 * 小工具函数，去掉悲观锁定
	 * 
	 * @param processId
	 * @return
	 */
	private TmProcess loadProcess(int processId) {
//		TmProcess process = em.find(TmProcess.class, processId);
		TmProcess process = tmProcessDao.findOne(processId);
		if (process == null)
			throw new IllegalArgumentException("无效进程编号：" + processId);
		return process;
	}

	@Transactional
	public void updateTmBthStepExec(TmBthStepExec TmBthStepExec) {
		/*
		 * rBatchStepExectuion.save(TmBthStepExec);
		 */
	}

	@Override
	public void updateBatchStepStatus(int batchInstId, String stepName,
			BatchStepStatus status) {/*
										 * 
										 * Date processDate = managementService.getSystemStatus().getProcessDate();
										 * 
										 * TmBthStepExec step =
										 * rBatchStepExectuion.findByBthInstIdAndProcessDateAndStepName(batchInstId,
										 * processDate, stepName);
										 * 
										 * SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); if(step
										 * != null && step.getStartTime() !=null && status.getStartTime()!=null &&
										 * sdf.format(step.getStartTime()).equals(sdf.format(status.getStartTime())) &&
										 * !StringUtils.isBlank(status.getExitCode()) &&
										 * !endStepStatus(StepStatusDef.valueOf(status.getExitCode().trim())) &&
										 * step.getStepStatus() !=null && endStepStatus(step.getStepStatus())){
										 * 
										 * logger.info("该step已经是终态1，直接返回"); return; }
										 * 
										 * if (step == null) { step = new TmBthStepExec();
										 * step.setBthInstId(batchInstId); step.setStepName(stepName);
										 * step.setProcessDate(processDate); }
										 * 
										 * step.setStartTime(status.getStartTime());
										 * step.setEndTime(status.getEndTime()); //
										 * step.setCommitCount(status.getCommitCount());
										 * step.setHintMessage(status.getHintMessage());
										 * step.setStepStatus(StringUtils.isBlank(status.getExitCode())?null:
										 * StepStatusDef.valueOf(status.getExitCode().trim()));
										 * step.setExitMessage(status.getExitDescription());
										 * 
										 * for(int i=0;i<10;i++){//乐观锁异常 终态 重试，保证成功 try { updateTmBthStepExec(step);
										 * break; }catch(DataIntegrityViolationException e){//803错误 重新find两次
										 * logger.info("803 find两次",e); step =
										 * rBatchStepExectuion.findByBthInstIdAndProcessDateAndStepName(batchInstId,
										 * processDate, stepName);
										 * 
										 * if(step != null && step.getStartTime() !=null && status.getStartTime()!=null
										 * && sdf.format(step.getStartTime()).equals(sdf.format(status.getStartTime()))
										 * && !StringUtils.isBlank(status.getExitCode()) &&
										 * !endStepStatus(StepStatusDef.valueOf(status.getExitCode().trim())) &&
										 * step.getStepStatus() !=null && endStepStatus(step.getStepStatus())){
										 * 
										 * logger.info("该step已经是终态2，直接返回",e); return; }
										 * 
										 * if(step == null){ try { Thread.sleep(500); } catch (InterruptedException e1)
										 * { e1.printStackTrace(); } step =
										 * rBatchStepExectuion.findByBthInstIdAndProcessDateAndStepName(batchInstId,
										 * processDate, stepName);
										 * 
										 * if(step != null && step.getStartTime() !=null && status.getStartTime()!=null
										 * && sdf.format(step.getStartTime()).equals(sdf.format(status.getStartTime()))
										 * && !StringUtils.isBlank(status.getExitCode()) &&
										 * !endStepStatus(StepStatusDef.valueOf(status.getExitCode().trim())) &&
										 * step.getStepStatus() !=null && endStepStatus(step.getStepStatus())){
										 * logger.info("该step已经是终态3，直接返回",e); return; }
										 * 
										 * if(step == null){ step = new TmBthStepExec(); step.setBthInstId(batchInstId);
										 * step.setStepName(stepName); step.setProcessDate(processDate); } }
										 * step.setStartTime(status.getStartTime());
										 * step.setEndTime(status.getEndTime());
										 * step.setHintMessage(status.getHintMessage());
										 * step.setStepStatus(StringUtils.isBlank(status.getExitCode())?null:
										 * StepStatusDef.valueOf(status.getExitCode().trim()));
										 * step.setExitMessage(status.getExitDescription());
										 * 
										 * try{ updateTmBthStepExec(step); return;
										 * }catch(JpaOptimisticLockingFailureException e1) {//乐观锁异常 终态 重试
										 * if(!StringUtils.isBlank(status.getExitCode()) &&
										 * endStepStatus(StepStatusDef.valueOf(status.getExitCode().trim()))){
										 * logger.info("乐观锁异常 ，终态"+step.getStepStatus()+"，重试", e); step =
										 * rBatchStepExectuion.findByBthInstIdAndProcessDateAndStepName(batchInstId,
										 * processDate, stepName); if(step != null && step.getStartTime() !=null &&
										 * status.getStartTime()!=null &&
										 * sdf.format(step.getStartTime()).equals(sdf.format(status.getStartTime())) &&
										 * !StringUtils.isBlank(status.getExitCode()) &&
										 * !endStepStatus(StepStatusDef.valueOf(status.getExitCode().trim())) &&
										 * step.getStepStatus() !=null && endStepStatus(step.getStepStatus())){
										 * 
										 * logger.info("该step已经是终态5，直接返回",e); return; }
										 * step.setStartTime(status.getStartTime());
										 * step.setEndTime(status.getEndTime());
										 * step.setHintMessage(status.getHintMessage());
										 * step.setStepStatus(StringUtils.isBlank(status.getExitCode())?null:
										 * StepStatusDef.valueOf(status.getExitCode().trim()));
										 * step.setExitMessage(status.getExitDescription()); }else{
										 * logger.info("乐观锁异常，非终态直接返回", e); return; } }
										 * }catch(JpaOptimisticLockingFailureException e) {//乐观锁异常 终态 重试，保证成功
										 * if(!StringUtils.isBlank(status.getExitCode()) &&
										 * endStepStatus(StepStatusDef.valueOf(status.getExitCode().trim()))){
										 * logger.info("乐观锁异常 ，终态"+step.getStepStatus()+"，重试", e); step =
										 * rBatchStepExectuion.findByBthInstIdAndProcessDateAndStepName(batchInstId,
										 * processDate, stepName); if(step != null && step.getStartTime() !=null &&
										 * status.getStartTime()!=null &&
										 * sdf.format(step.getStartTime()).equals(sdf.format(status.getStartTime())) &&
										 * !StringUtils.isBlank(status.getExitCode()) &&
										 * !endStepStatus(StepStatusDef.valueOf(status.getExitCode().trim())) &&
										 * step.getStepStatus() !=null && endStepStatus(step.getStepStatus())){
										 * 
										 * logger.info("该step已经是终态6，直接返回"); return; }
										 * step.setStartTime(status.getStartTime());
										 * step.setEndTime(status.getEndTime());
										 * step.setHintMessage(status.getHintMessage());
										 * step.setStepStatus(StringUtils.isBlank(status.getExitCode())?null:
										 * StepStatusDef.valueOf(status.getExitCode().trim()));
										 * step.setExitMessage(status.getExitDescription()); }else{
										 * logger.info("乐观锁异常，非终态直接返回", e); return; } } }
										 */
	}

	@Transactional
	public void updateBatchJobStatus(TmBthInst inst) {
		/*
		 * rBatchInst.save(inst);
		 */
	}

	@Override
	public void updateBatchJobStatus(int batchInstId, BatchJobStatus status) {
		/*
		 * logger.debug("调用方法:[updateBatchJobStatus],参数:[batchInstId:[{}],status:[{}]]",
		 * batchInstId, status.getStatus());
		 * 
		 * TmBthInst inst = rBatchInst.findOne(batchInstId);
		 * 
		 * BatchStatusDef dbStatus=inst.getBatchStatus();
		 * 
		 * SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		 * 
		 * if(inst.getStartTime() !=null && status.getStartTime()!=null &&
		 * sdf.format(inst.getStartTime()).equals(sdf.format(status.getStartTime())) &&
		 * !endBatchStatus(BatchStatusDef.valueOf(status.getStatus())) &&
		 * endBatchStatus(inst.getBatchStatus())){ logger.info("该job已是终态，直接返回"); return;
		 * } //em.lock(inst, LockModeType.PESSIMISTIC_FORCE_INCREMENT); BatchStatusDef
		 * batchStatus = BatchStatusDef.valueOf(status.getStatus());
		 * inst.setBatchStatus(batchStatus); inst.setStartTime(status.getStartTime());
		 * 
		 * if(batchStatus == BatchStatusDef.COMPLETED){ TmSysStatus systemStatus =
		 * rSystemStatus.findOne(1);
		 * inst.setBatchCompletedDate(systemStatus.getProcessDate());
		 * inst.setEndTime(status.getEndTime()); } for(int i = 0; i < 3; i++) { try {
		 * updateBatchJobStatus(inst); break; }
		 * catch(JpaOptimisticLockingFailureException e) {//乐观锁异常 终态 重试，保证成功
		 * if(endBatchStatus(batchStatus) || !endBatchStatus(dbStatus)){
		 * logger.info("乐观锁异常 ，心跳是终态或数据库值非终态"+inst.getBatchStatus()+"，重试", e); inst =
		 * rBatchInst.findOne(batchInstId); dbStatus=inst.getBatchStatus();
		 * if(inst.getStartTime() !=null && status.getStartTime()!=null &&
		 * sdf.format(inst.getStartTime()).equals(sdf.format(status.getStartTime())) &&
		 * !endBatchStatus(BatchStatusDef.valueOf(status.getStatus())) &&
		 * endBatchStatus(inst.getBatchStatus())){ logger.info("该job已是终态，直接返回",e);
		 * return; } batchStatus = BatchStatusDef.valueOf(status.getStatus());
		 * inst.setBatchStatus(batchStatus); inst.setStartTime(status.getStartTime());
		 * 
		 * if(batchStatus == BatchStatusDef.COMPLETED){ TmSysStatus systemStatus =
		 * rSystemStatus.findOne(1);
		 * inst.setBatchCompletedDate(systemStatus.getProcessDate());
		 * inst.setEndTime(status.getEndTime()); } }else{ logger.info("乐观锁异常，非终态直接返回",
		 * e); return; }
		 * 
		 * } }
		 */
	}

	/**
	 * 
	 * 判断批量状态是否终态
	 */
	/*
	 * public boolean endBatchStatus(BatchStatusDef status){ return status ==
	 * BatchStatusDef.COMPLETED || status == BatchStatusDef.STOPPED || status ==
	 * BatchStatusDef.FAILED || status == BatchStatusDef.ABANDONED || status ==
	 * BatchStatusDef.FINISHED ; }
	 */

	/**
	 * 
	 * 判断Step状态是否终态
	 */
	/*
	 * public boolean endStepStatus(StepStatusDef status){ return status ==
	 * StepStatusDef.COMPLETED || status == StepStatusDef.STOPPED || status ==
	 * StepStatusDef.FAILED || status == StepStatusDef.ABANDONED ; }
	 */

	private String buildJVMArgs(TmProcess process) {
		String jvmArgs = StringUtils.defaultIfEmpty(process.getJvmArgs(), "");
		if (process.getJvmHeapMax() != null)
			jvmArgs += " -Xmx" + process.getJvmHeapMax() + "m ";
		if (process.getJvmMaxPerm() != null)
			jvmArgs += " -XX:MaxPermSize=" + process.getJvmMaxPerm() + "m ";
		if (process.getProgramArgs() != null)
			jvmArgs += " " + process.getProgramArgs() + " ";
		return jvmArgs;
	}

	@Override
	@Transactional
	public void updateIndieBatchJobStatus(int indieBatchInstId, BatchJobStatus status) {

		/*
		 * TmIndieBthInst inst = rTmIndieBthInst.findOne(indieBatchInstId); if(inst !=
		 * null){ em.lock(inst, LockModeType.PESSIMISTIC_FORCE_INCREMENT);
		 * BatchStatusDef batchStatus = BatchStatusDef.valueOf(status.getStatus());
		 * inst.setBatchStatus(batchStatus); inst.setStartTime(status.getStartTime());
		 * 
		 * if(batchStatus == BatchStatusDef.COMPLETED){ TmSysStatus systemStatus =
		 * rSystemStatus.findOne(1);
		 * inst.setBatchCompletedDate(systemStatus.getBusinessDate());
		 * inst.setEndTime(status.getEndTime()); } }
		 */
	}

	@Override
	@Transactional
	public void updateIndieBatchStepStatus(int indieBatchInstId, String stepName, BatchStepStatus status) {

		/*
		 * Date processDate = managementService.getSystemStatus().getBusinessDate();
		 * TmIndieBthInst inst = rTmIndieBthInst.findOne(indieBatchInstId); if(inst !=
		 * null){ TmIndieBthStepExec stepExecution =
		 * rTmIndieBthStepExec.findByInstIdAndProcessDateAndStepNameAndIndieTime(
		 * indieBatchInstId, processDate, stepName, inst.getIndieTime()); if (null ==
		 * stepExecution) { stepExecution = new TmIndieBthStepExec();
		 * stepExecution.setId(indieBatchInstId); stepExecution.setStepName(stepName);
		 * stepExecution.setProcessDate(processDate);
		 * stepExecution.setIndieTime(inst.getIndieTime());
		 * stepExecution.setStartTime(status.getStartTime());
		 * stepExecution.setEndTime(status.getEndTime());
		 * stepExecution.setHintMessage(status.getHintMessage());
		 * stepExecution.setStepStatus(StepStatusDef.valueOf(status.getExitCode()));
		 * stepExecution.setExitMessage(status.getExitDescription());
		 * em.persist(stepExecution); } else {
		 * stepExecution.setStartTime(status.getStartTime());
		 * stepExecution.setEndTime(status.getEndTime());
		 * stepExecution.setHintMessage(status.getHintMessage());
		 * stepExecution.setStepStatus(StepStatusDef.valueOf(status.getExitCode()));
		 * stepExecution.setExitMessage(status.getExitDescription());
		 * em.merge(stepExecution); } }
		 */
	}

	/**
	 * <pre>
	 * jyd-4.2.5新增方法，用于模块拆分后，启动用应用时的jar包的路径拼接;
	 * 如果该字段为‘’或者null时用Artifact_Id字段“-”分割后第一个字符串拼接，有值时用System_Name拼接
	 * 
	 * <pre>
	 * 
	 * @param process
	 * @return
	 */
	public String getSystemNameFromTmProcess(TmProcess process) {
		String systemName = process.getSystemName();
		if (StringUtils.isBlank(systemName)) {
			systemName = StringUtils.split(process.getArtifactId(), "-")[0];
		}
		return systemName.toLowerCase();
	}

}
