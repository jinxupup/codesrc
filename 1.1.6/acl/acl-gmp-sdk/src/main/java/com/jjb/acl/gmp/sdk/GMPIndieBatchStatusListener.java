package com.jjb.acl.gmp.sdk;


import java.util.Date;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>Title: GMPIndieBatchStatusListener.java</p>
 * <p>Description: 为多机构独立随机批量提供监听</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @author LI.H
 * @date 2015年8月27日
 * @version 1.0
 */
public class GMPIndieBatchStatusListener implements StepExecutionListener, JobExecutionListener {

	@Autowired
	private GMPBatchStatusUpdater updater;
	
	@Autowired
	private BatchStatusFacility batchStatusFacility;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		updateJobStatus(jobExecution, true);
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		updateJobStatus(jobExecution, false);
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		updater.updateIndieStatus(stepExecution, "准备开始，状态：" + stepExecution.getStatus(), true);
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		stepExecution.setEndTime(new Date(System.currentTimeMillis()));
		updater.updateIndieStatus(stepExecution, "步骤结束，状态:" + stepExecution.getStatus(), false);		
		return stepExecution.getExitStatus();
	}

	/**
	 * <p>Job使用同一个更新方法进行状态维护</p>
	 * @param jobExecution
	 */
	private void updateJobStatus(JobExecution jobExecution, boolean isStart) {
		batchStatusFacility.refreshSystemStatus();
		updater.updateIndieStatus(jobExecution, isStart);
	}
}
