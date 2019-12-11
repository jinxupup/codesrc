package com.jjb.acl.gmp.sdk;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.facility.util.CommandExecutionUtil;

/**
 * <p>Description: 命令执行监听器，可为需要执行脚本的Job和Step执行脚本，脚本在环境变量中key必须为jobName/stepName_before/after</p>
 * <p>此监听器只能提供日终批量使用，对于随机批量监听器请使用{@link IndieCommandExecutionListener}</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: CommandExecutionListener
 * @author LI.H
 * @date 2015年11月20日 下午2:03:46
 * @version 1.0
 */
public class CommandExecutionListener implements StepExecutionListener, JobExecutionListener {

	private final static Logger logger = LoggerFactory.getLogger(CommandExecutionListener.class);
	
	protected final static String BEFORE_SUFFIX = "_before";
	
	protected final static String AFTER_SUFFIX = "_after";
	
	protected final static String SPACE = " ";
	
	protected final static String DATE_FOMART = "yyyyMMdd";
	
	/**
	 * <p>批量作业前监听状态</p>
	 */
	protected final static String JOB_LISTENER_BEGIN_STATUS_KEY = "JOB_BEGIN_LISTENER_STATUS";
	
	/**
	 * <p>批量作业后监听状态</p>
	 */
	protected final static String JOB_LISTENER_END_STATUS_KEY = "JOB_END_LISTENER_STATUS";
	
	/**
	 * <p>批量作业步前监听状态</p>
	 */
	protected final static String STEP_LISTENER_BEGIN_STATUS_KEY = "STEP_BEGIN_LISTENER_STATUS";
	
	/**
	 * <p>批量作业步后监听状态</p>
	 */
	protected final static String STEP_LISTENER_END_STATUS_KEY = "STEP_END_LISTENER_STATUS";
	
	private CommandExecutionUtil commandExecutionUtil;
	
	@Autowired
	protected BatchStatusFacility batchStatusFacility;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		executionOrder(jobExecution.getExecutionContext(), JOB_LISTENER_BEGIN_STATUS_KEY, getCommandKey(jobExecution.getJobInstance().getJobName(), BEFORE_SUFFIX), jobExecution.getJobInstance().getJobParameters());
		
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		// 作业正确结束才允许执行
		if (BatchStatus.COMPLETED == jobExecution.getStatus()) {
			executionOrder(jobExecution.getExecutionContext(), JOB_LISTENER_END_STATUS_KEY, getCommandKey(jobExecution.getJobInstance().getJobName(), AFTER_SUFFIX), jobExecution.getJobInstance().getJobParameters());
		}
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		executionOrder(stepExecution.getExecutionContext(), STEP_LISTENER_BEGIN_STATUS_KEY, getCommandKey(stepExecution.getStepName(), BEFORE_SUFFIX), stepExecution.getJobParameters());
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// 作业步正确结束才允许执行
		if (BatchStatus.COMPLETED == stepExecution.getStatus()) {
			executionOrder(stepExecution.getExecutionContext(), STEP_LISTENER_END_STATUS_KEY, getCommandKey(stepExecution.getStepName(), AFTER_SUFFIX), stepExecution.getJobParameters());
		}
		return stepExecution.getExitStatus();
	}
	
	/**
	 * <p>目的：获取将要执行的命令的key值</p>
	 * <p>承诺：当name和suffix都不为null响应name+suffix.sh</p>
	 * @param name
	 * @param suffix
	 * @return
	 */
	protected String getCommandKey(String name, String suffix) {
		return new StringBuffer(name).append(suffix).toString();
	}
	
	/**
	 * <p>目的：执行决定，决定是否执行命令，同时为命令拼接参数</p>
	 * <p>承诺：当前执行上下文已经执行成功命令或当环境变量中不存在key值对应的value则不执行命令</p>
	 * @param key
	 */
	protected void executionOrder(ExecutionContext ec, String excutionKey, String commandKey, JobParameters jobParameters) {
		
		if (!ec.containsKey(excutionKey)) {
			ec.put(excutionKey, BatchStatus.UNKNOWN);
		}
		if (BatchStatus.COMPLETED != ec.get(excutionKey)) {
			try {
				if (commandExecutionUtil.getProps().containsKey(commandKey)) {
					String command = (String) commandExecutionUtil.getProps().get(commandKey);
					String batchDate = new SimpleDateFormat(DATE_FOMART).format(batchStatusFacility.getBatchDate());
					logger.info("从环境变量中获取初始执行命令[{}]，预计加入批量日期[{}]后执行", command, batchDate);
					commandExecutionUtil.runCommand(command + SPACE + batchDate);
				}
			} catch (Exception e) {
				throw new RuntimeException("批量监听器执行此命令出现异常，请人工干预检查命令执行结果，之后重启批量。");
			}
			ec.put(excutionKey, BatchStatus.COMPLETED);
		} else {
			logger.info("断点续批，监听器已经执行过环境变量[{}]命令，不再执行。", commandKey);
		}
		
	}

	public void setCommandExecutionUtil(CommandExecutionUtil commandExecutionUtil) {
		this.commandExecutionUtil = commandExecutionUtil;
	}
	
}
