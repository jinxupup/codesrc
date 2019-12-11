package com.jjb.unicorn.facility.util;

import java.io.File;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.Assert;

/**
 * <p>Description: 通用命令执行器，可执行所有命令</p>
 * @ClassName: CommandExecutionUtil
 * @author jjb
 * @date 2015年11月27日 上午11:07:27
 * @version 1.0
 */
public class CommandExecutionUtil {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * <p>所有环境变量参数</p>
	 */
	private Properties props;
	
	/**
	 * <p>执行参数默认无</p>
	 */
	private String[] environmentParams = null;
	
	/**
	 * <p>脚本工作目录</p>
	 */
	private File workingDirectory;
	
	/**
	 * <p>线程执行器</p>
	 * <p>默认为SimpleAsyncTaskExecutor</p>
	 * TODO 由于不是线程池，如果某子系统需要执行太多命令，有可能出现调度问题。以后解决。
	 */
	private TaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
	
	/**
	 * <p>是否需要同步等待命令执行，当此属性为true时commandTimeout，checkInterval， interruptOnCancel生效</p>
	 * <p>默认不等待</p>
	 */
	private boolean commandWait = false;
	
	/**
	 * <p>最大等待时间（毫秒）</p>
	 */
	private long commandTimeout = 60000;
	
	/**
	 * <p>检查执行结果时间间隔（毫秒）</p>
	 * <p>默认十秒</p>
	 */
	private long checkInterval = 10000;
	
	/**
	 * <p>异常是否中断任务</p>
	 * <p>默认不中断</p>
	 */
	private boolean interruptOnCancel = false;
	
	/**
	 * <p>目的：简单命令执行器，不能执行带有管道等复杂命令</p>
	 * <p>承诺：当命令为空则不执行命令，本方法不校验命令合法性，不解析命令</p>
	 * @param command 执行命令需要指定为final
	 * @return 响应-1为非法的命令，执行成功和不等待执行线程响应0
	 */
	public Integer runCommand(final String command) {

		if (StringUtils.isBlank(command)) {
			logger.warn("存在一个为空的命令");
			return -1;
		}
		
		logger.info("在路径[{}]执行[{}]命令", null == workingDirectory ? "" : workingDirectory.getPath(), command);
		
		FutureTask<Integer> systemCommandTask = new FutureTask<Integer>(new Callable<Integer>() {

			public Integer call() throws Exception {
				Process process = Runtime.getRuntime().exec(command, environmentParams, workingDirectory);
				if (commandWait) {
					return process.waitFor();
				} else {
					return 0;
				}
				
			}

		});
		long startTime = System.currentTimeMillis();
		taskExecutor.execute(systemCommandTask);
		
		while (commandWait) {
			try {
				Thread.sleep(checkInterval);
			} catch (InterruptedException e) {
				throw new RuntimeException("线程等待异常，理论上不可能出现此异常。");
			}
			if (systemCommandTask.isDone()) {
				try {
					int exitCode = systemCommandTask.get();
					logger.info("command[{}], ExitCode:[{}]", command, exitCode);
					return exitCode;
				} catch (InterruptedException e) {
					throw new RuntimeException("线程中断异常，理论上不可能出现此异常。");
				} catch (ExecutionException e) {
					throw new RuntimeException("执行器执行异常，理论上不可能出现此异常。");
				}
			} else if (System.currentTimeMillis() - startTime > commandTimeout) {
				systemCommandTask.cancel(interruptOnCancel);
				throw new RuntimeException("在等待最长时间内命令没有完成，超时。");
			}
		}
		return 0;
	}
	
	/**
	 * <p>目的：设置命令工作目录</p>
	 * <p>承诺：设置该命令执行工作目录，可以为null</p>
	 * @param dir
	 */
	public void setWorkingDirectory(String dir) {
		if (dir == null) {
			this.workingDirectory = null;
			return;
		}
		this.workingDirectory = new File(dir);
		Assert.isTrue(workingDirectory.exists(), "工作目录必须存在");
		Assert.isTrue(workingDirectory.isDirectory(), "工作目录必须为一个目录");
	}
	
	/**
	 * <p>目的：此task executor可提供注入</p>
	 * <p>承诺：此task executor将用来执行指定命令，请尽量避免使用一个同步的task executor</p>
	 * @param taskExecutor
	 */
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	/**
	 * <p>目的：此commandWait提供注入</p>
	 * <p>承诺：用来判断是否同步等闲调用进程执行情况
	 * @param commandWait 如果为<code>true</code>则会同步等待命令进程，默认为<code>false</code>
	 */
	public void setCommandWait(boolean commandWait) {
		this.commandWait = commandWait;
	}

	/**
	 * <p>目的：传入所有环境变量</p>
	 * <p>承诺：再次设置所有需要环境变量配置的值，没有则使用默认值</p>
	 * @param props
	 */
	public void setProps(Properties properties) {
		this.props = properties;
		
		setWorkingDirectory(props.getProperty("commandWorkingDirectory"));
		
		if (props.containsKey("commandWait"))
			this.commandWait = Boolean.valueOf((String) props.get("commandWait"));
		
		if (props.containsKey("commandTimeout"))
			this.commandTimeout = Long.valueOf((String) props.get("commandTimeout"));
		
		if (props.containsKey("commandCheckInterval"))
			this.checkInterval = Long.valueOf((String) props.get("commandCheckInterval"));
		
		if (props.containsKey("commandInterruptOnCancel"))
			this.interruptOnCancel = Boolean.valueOf((String) props.get("commandInterruptOnCancel"));
	}

	public Properties getProps() {
		return props;
	}
}
