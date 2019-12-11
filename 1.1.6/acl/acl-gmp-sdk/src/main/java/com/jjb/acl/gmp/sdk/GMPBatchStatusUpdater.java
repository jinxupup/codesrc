package com.jjb.acl.gmp.sdk;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.acl.gmp.api.BatchJobStatus;
import com.jjb.acl.gmp.api.BatchStepStatus;
import com.jjb.acl.gmp.api.ManagedComponentService;

public class GMPBatchStatusUpdater {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ManagedComponentService componentService;

	
	public void updateStatus(StepExecution stepExecution, String hintMessage) {
		int batchInstId = (int)stepExecution.getJobParameters().getLong("batch.inst.id", -1);
		if (batchInstId != -1)
		{
			if (logger.isDebugEnabled())
				logger.debug("发送更新批量状态信息：{}, {}", batchInstId, stepExecution.getStepName());

			BatchStepStatus status = new BatchStepStatus();
			status.setStartTime(stepExecution.getStartTime());
			status.setEndTime(stepExecution.getEndTime());
			status.setCommitCount(0);
			status.setHintMessage(hintMessage);
			status.setExitCode(stepExecution.getExitStatus().getExitCode());
			status.setExitDescription(stepExecution.getExitStatus().getExitDescription());
			componentService.updateBatchStepStatus(batchInstId, stepExecution.getStepName(), status);
		}
	}

	
	public void updateStatus(JobExecution jobExecution)
	{
		int batchInstId = (int)jobExecution.getJobInstance().getJobParameters().getLong("batch.inst.id", -1);
		if (batchInstId != -1)
		{
			if (logger.isDebugEnabled())
				logger.debug("发送更新批量状态信息：{}, {}", batchInstId, jobExecution.getJobInstance().getJobName());
			BatchJobStatus status = new BatchJobStatus();
			status.setStatus(jobExecution.getStatus().toString());
			status.setStartTime(jobExecution.getStartTime());
			status.setEndTime(jobExecution.getEndTime());

			componentService.updateBatchJobStatus(batchInstId, status);
		}
	}
	
	/**
	 * <p>独立批量更新状态</p>
	 * @param jobExecution Job执行器
	 */
	public void updateIndieStatus(JobExecution jobExecution, boolean isStart) {
		int indieBatchInstId = (int) jobExecution.getJobInstance().getJobParameters().getLong("indie.batch.inst.id", -1);
		if (-1 != indieBatchInstId) {
			if (logger.isDebugEnabled())
				logger.debug("IndieBatchInstId[{}], JobName[{}]，->{}<-，更新作业状态信息：, Status[{}]", indieBatchInstId, jobExecution.getJobInstance().getJobName(), isStart ? START: END, jobExecution.getStatus().toString());
			
			BatchJobStatus status = new BatchJobStatus();
			status.setStatus(jobExecution.getStatus().toString());
			status.setStartTime(jobExecution.getStartTime());
			status.setEndTime(jobExecution.getEndTime());

			componentService.updateIndieBatchJobStatus(indieBatchInstId, status);
		} else {
			if (logger.isDebugEnabled())
				logger.debug("未找到指定的indie.batch.inst.id");
		}
		
	}

	/**
	 * <p>独立批量更新状态</p>
	 * @param stepExecution step执行器
	 * @param hintMessage 附加描述信息
	 */
	public void updateIndieStatus(StepExecution stepExecution, String hintMessage, boolean isStart) {
		int indieBatchInstId = (int)stepExecution.getJobParameters().getLong("indie.batch.inst.id", -1);
		if (-1 != indieBatchInstId) {
			if (logger.isDebugEnabled())
				logger.debug("IndieBatchInstId[{}], StepName[{}]，->{}<-，更新作业步状态信息： Status[{}]", indieBatchInstId, stepExecution.getStepName(), isStart ? START: END, hintMessage);

			BatchStepStatus status = new BatchStepStatus();
			status.setStartTime(stepExecution.getStartTime());
			status.setEndTime(stepExecution.getEndTime());
			status.setCommitCount(0);
			status.setHintMessage(hintMessage);
			status.setExitCode(stepExecution.getExitStatus().getExitCode());
			status.setExitDescription(stepExecution.getExitStatus().getExitDescription());
			componentService.updateIndieBatchStepStatus(indieBatchInstId, stepExecution.getStepName(), status);
		} else {
			if (logger.isDebugEnabled())
				logger.debug("未找到指定的indie.batch.inst.id");
		}
	}
	
	private final static String START = "开始";
	private final static String END = "结束";
}
