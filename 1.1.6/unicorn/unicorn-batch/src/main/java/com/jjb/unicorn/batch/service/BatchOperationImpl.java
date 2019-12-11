package com.jjb.unicorn.batch.service;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.unicorn.batch.api.BatchOperation;
import com.jjb.unicorn.batch.exception.BatchException;
import com.jjb.unicorn.facility.exception.ProcessException;

@Service("batchOperationImpl")
public class BatchOperationImpl implements BatchOperation {
	
	@Autowired
	private JobOperator jobOperator;

	
	@Override
	public long startBatch(String sysType,String jobName, String params) throws BatchException {
		try {
			return jobOperator.start(jobName, params).longValue();
		} catch (NoSuchJobException e)
		{
			throw new BatchException("BH001","Job不存在");
		}
		catch (JobInstanceAlreadyExistsException e)
		{
			throw new ProcessException("BH002","Job实例已存在（批量已运行）");
		}
		catch (JobParametersInvalidException e) 
		{
			throw new ProcessException("BH003","无效批量参数");
		}
	}

	
	@Override
	public long restartBatch(String sysType,long executionId) throws BatchException {
		try {
			return jobOperator.restart(executionId);
		} catch (NoSuchJobException e)
		{
			throw new BatchException("BH001","Job不存在");
		}
		catch (JobParametersInvalidException e) 
		{
			throw new ProcessException("BH003","无效批量参数");
		}
		catch (JobInstanceAlreadyCompleteException e)
		{
			throw new ProcessException("BH004","批量已成功完成");
		}
		catch (NoSuchJobExecutionException e)
		{
			throw new ProcessException("BH005","没有此执行id:" + executionId);
		}
		catch (JobRestartException e) 
		{
			throw new ProcessException("BH006","重启批量失败");
		}
	}

	
	@Override
	public boolean stopBatch(String sysType,long executionId) throws BatchException {
		try {
			return jobOperator.stop(executionId);
		} catch (NoSuchJobExecutionException e) {
			throw new ProcessException("BH005","没有此执行id:" + executionId);
		} catch (JobExecutionNotRunningException e) {
			throw new ProcessException("BH007","此执行id对应的job没有运行:"+executionId);
		}

	}

}
