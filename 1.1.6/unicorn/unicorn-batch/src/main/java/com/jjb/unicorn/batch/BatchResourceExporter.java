package com.jjb.unicorn.batch;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.TaskRejectedException;

import com.jjb.unicorn.batch.partition.YakLocalPartitionResourceMerge;
import com.jjb.unicorn.facility.util.CommandExecutionUtil;


/**
 * <p>Description: 批量文件导出方案，Step之后调用，朝目标文件服务器传送文件，如果Step为分片Step则会触发文件合并</p>
 * @ClassName: BatchFileExporter
 * @author jjb
 * @date 2015年11月30日 下午3:03:37
 * @version 1.0
 */
public class BatchResourceExporter<H extends FileHeader, D> implements Tasklet, InitializingBean {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private final static String SPACE = " ";
	
	/**
	 * <p>执行线程池</p>
	 */
	private TaskExecutor taskExecutor = new SyncTaskExecutor();
	
	/**
	 * <p>需要发送文件集合</p>
	 */
	private List<YakResource> fileResource;
	
	private CommandExecutionUtil commandExecutionUtil;
	
	/**
	 * <p>需要执行命令</p>
	 */
	private String executeCommand;
	
	/**
	 * <p>是否为切片Step文件导出，默认为{@code false}</p>
	 */
	private boolean isPartition = false;
	
	/**
	 * <p>文件合并，用于分片Step后文件合并导出步骤，串行Step不用设置此bean</p>
	 */
	private YakLocalPartitionResourceMerge<H, D> mergeFile;

	/**
	 * <p>Title: execute</p> 
	 * <p>目的：和批量使用同一个线程池上传文件</p>
	 * <p>承诺：只要中途无异常抛出，均表示成功</p>
	 * @param contribution
	 * @param chunkContext
	 * @return
	 * @throws Exception 
	 * @see org.springframework.batch.core.step.tasklet.Tasklet#execute(org.springframework.batch.core.StepContribution, org.springframework.batch.core.scope.context.ChunkContext) 
	 */
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		
		if (StringUtils.isBlank(executeCommand)) {
			throw new RuntimeException("执行命令为null或为空");
		}
		
		Set<Future<Integer>> tasks = new HashSet<Future<Integer>>();
		for (YakResource resource : fileResource) {
			// TODO 文件合并在主线程做，想在下面线程池做，但是传入需要final类型，时间紧迫有时间在分析
			if (isPartition) {
				logger.info("主线程并发Step合并文件[{}]", resource.getFile().getAbsolutePath());
				mergeFile.mergeFile(resource);
			}
			final String command = new StringBuffer(executeCommand).append(SPACE).append(resource.getPath()).append(SPACE).append(resource.getResourceDate()).toString();
			logger.debug("即将执行命令：[{}]", command);
			final FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
				public Integer call() throws Exception {
					// 只要不抛出异常则根据响应值判断
					return commandExecutionUtil.runCommand(command);
				}
			});
			try {
				tasks.add(task);
				taskExecutor.execute(task);
			} catch (TaskRejectedException e) {
				throw new TaskRejectedException("线程池执行出错，命令:" + command);
			}
		}
		
		for (Future<Integer> task : tasks) {
			if (task.get() != 0) {
				logger.debug("并行步骤执行失败");
				throw new RuntimeException("并行文件上传失败");
			}
		}
		return RepeatStatus.FINISHED;
	}

	/**
	 * 线程池 {@link TaskExecutor} 用来执行文件上传，使用批量线程池
	 * @param taskExecutor a {@link TaskExecutor}
	 */
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setFileResource(List<YakResource> fileResource) {
		this.fileResource = fileResource;
	}

	public void setCommandExecutionUtil(CommandExecutionUtil commandExecutionUtil) {
		this.commandExecutionUtil = commandExecutionUtil;
	}

	public void setExecuteCommand(String executeCommand) {
		this.executeCommand = executeCommand;
	}

	public void setMergeFile(YakLocalPartitionResourceMerge<H, D> mergeFile) {
		this.mergeFile = mergeFile;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (null != mergeFile) {
			logger.info("当前导出脚本为切片Step文件导出");
			this.isPartition = true;
		} else {
			logger.info("当前导出脚本为串行Step文件导出");
		}
	}
	
	
}
