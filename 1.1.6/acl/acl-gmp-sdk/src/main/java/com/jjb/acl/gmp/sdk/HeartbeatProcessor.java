package com.jjb.acl.gmp.sdk;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.MessageFormat;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.context.Phased;

import com.jjb.acl.facility.enums.sys.ProcessRunningStatus;
import com.jjb.acl.gmp.api.ManagedComponentService;
import com.jjb.acl.gmp.api.ProcessStatus;

/**
 * 处理心跳及登入登出消息，由task:scheduled-tasks标签驱动
 * @author LI.J
 *
 */
public class HeartbeatProcessor implements Phased, Lifecycle{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 标识当前是否是GMP托管的进程，如果不是，就不需要心跳等操作
	 */
	private boolean managedProcess; 
	
	private int processId;
	
	private Object lock=new Object();
	
	@Autowired
	private ManagedComponentService componentService;
	
	public void beatHeart()
	{
		synchronized (lock) {
			if (managedProcess)
			{
				logger.info("发送心跳报文。");
				componentService.updateProcessStatus(processId, createProcessStatus(ProcessRunningStatus.RUNNING));
			}
		}
	}
	
	@Override
	public int getPhase() {
		//在最后一个初始化，第一个关闭
		return Integer.MAX_VALUE;
	}

	private ProcessStatus createProcessStatus(ProcessRunningStatus runningStatus)
	{
		ProcessStatus status = new ProcessStatus();
		status.setRunningStatus(runningStatus);

		MemoryMXBean mmb = ManagementFactory.getMemoryMXBean();
		MemoryUsage usage = mmb.getHeapMemoryUsage();
		status.setHeapFree(MessageFormat.format("{0,number,0.00}% ({1}MB)", (1.0 - (double)usage.getUsed() / usage.getMax()) * 100, (usage.getMax() - usage.getUsed()) >> 20));
		return status;
	}

	@Override
	public void start() {
	}
	
	@PostConstruct
	public void init(){
		String str = System.getProperty("process.id");
		managedProcess = StringUtils.isNotEmpty(str);
		
		if (!managedProcess)
		{
			logger.info("非托管进程，不作登录/心跳/登出处理。");
		}
		else
		{
			processId = Integer.parseInt(str);
			synchronized (lock) {
				componentService.updateProcessStatus(processId, createProcessStatus(ProcessRunningStatus.RUNNING));
			}
		}
	}

	@Override
	public void stop() {
		synchronized (lock) {
			if (managedProcess)
			{
				managedProcess=false;
				logger.info("发送进程注销消息，进程号[{}]。", processId);
				componentService.updateProcessStatus(processId, createProcessStatus(ProcessRunningStatus.STOPPED));
			}
		}
	}

	@Override
	public boolean isRunning() {
		return true;
	}
}
