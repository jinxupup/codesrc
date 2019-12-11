package com.jjb.unicorn.batch;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class DebugSupportListener extends StepExecutionListenerSupport
{
	@Override
	public void beforeStep(StepExecution stepExecution)
	{
		if (stepExecution.getStepName().equals(System.getProperty("debug.break.before")))
			throw new RuntimeException("调试断点，停在[" + stepExecution.getStepName() + "]之前");
	}
}
