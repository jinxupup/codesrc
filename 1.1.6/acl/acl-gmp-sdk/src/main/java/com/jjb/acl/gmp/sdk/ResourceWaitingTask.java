package com.jjb.acl.gmp.sdk;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepSynchronizationManager;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

public class ResourceWaitingTask implements Tasklet {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private List<Resource> waitResources;
	
	@Value("#{env['batchFilePollInterval']?:5000}")
	private int pollInterval;
	
	@Autowired(required=false)	//为单元测试时使用
	private GMPBatchStatusUpdater updater;
	
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		logger.info("开始检查输入文件状态");

		Set<Resource> notReady = new HashSet<Resource>();
		String hintMessage = "等待文件：";
		for (Resource resource : waitResources)
		{
			if (!resource.exists())
			{
				if (logger.isDebugEnabled())
					logger.debug("文件[{}]还不存在。", resource.getURL());
				notReady.add(resource);
				hintMessage += resource.getFilename() + " ";
			}
		}

		if (notReady.isEmpty())
		{
			logger.debug("文件都已存在，开始运行批量步骤");
			if (updater != null)
				updater.updateStatus(StepSynchronizationManager.getContext().getStepExecution(), "");
			return RepeatStatus.FINISHED;
		}
		
		//组织提示消息
		
		if (updater != null)
			updater.updateStatus(StepSynchronizationManager.getContext().getStepExecution(), hintMessage);

		//暂停一会儿
		try
		{
			logger.info("轮询等待[{}]秒", pollInterval/1000);
			Thread.sleep(pollInterval);
		}
		catch (InterruptedException e)
		{
			//被中断了也无所谓
		}
		return RepeatStatus.CONTINUABLE;
	}

	public List<Resource> getWaitResources() {
		return waitResources;
	}

	@Required
	public void setWaitResources(List<Resource> waitResources) {
		this.waitResources = waitResources;
	}


}
