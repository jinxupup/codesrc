package com.jjb.acl.gmp.sdk;


import java.util.Date;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

public class GMPBatchStatusListener implements StepExecutionListener, JobExecutionListener{
	
	@Autowired
	private GMPBatchStatusUpdater updater;
	
	@Autowired
	private BatchStatusFacility batchStatusFacility;
	
	@Override
	public void beforeStep(StepExecution stepExecution) {
		updater.updateStatus(stepExecution, "准备开始");
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		stepExecution.setEndTime(new Date(System.currentTimeMillis()));
		updater.updateStatus(stepExecution, "步骤结束，状态:" + stepExecution.getStatus());		
		return stepExecution.getExitStatus();
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
		batchStatusFacility.refreshSystemStatus();
		updater.updateStatus(jobExecution);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		batchStatusFacility.refreshSystemStatus();
		updater.updateStatus(jobExecution);
	}
}
