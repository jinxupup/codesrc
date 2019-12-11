package com.jjb.unicorn.facility.service;

import java.io.FileNotFoundException;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

/**
 * 基础的服务Daemon.
 * 可以带一个参数，指定在classpath中加载的spring配置文件名，程序会自动加上 "-context.xml"后缀
 * @author jjb
 *
 */
public class ServiceDaemon implements Daemon {
	
	private ConfigurableApplicationContext ctx;

	@Override
	public void init(DaemonContext context) throws DaemonInitException,	Exception
	{
		initLogging();
		
		ctx = new ClassPathXmlApplicationContext(getContextFilename(context.getArguments()));
		
		ctx.registerShutdownHook();
	}

	private static void initLogging() throws FileNotFoundException {
		//默认调用classpath下的log4j.properties，这样可以有效的避免因为xml优先的原因，被某些jar包里的log4j.xml给覆盖了。
		//相当于主动调整默认配置文件加载优先级
		String logLocation = System.getProperty("log.config");
		if (StringUtils.isNotBlank(logLocation))
			Log4jConfigurer.initLogging(logLocation, 1000 * 60);
		else
			Log4jConfigurer.initLogging("classpath:log4j.properties");
	}

	@Override
	public void start() throws Exception
	{
	}

	@Override
	public void stop() throws Exception
	{
	}

	@Override
	public void destroy()
	{
		ctx.close();
	}

	/**
	 * 这样可以直接跑，要关闭就杀掉进程
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		initLogging();
		@SuppressWarnings("resource")
		ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(getContextFilename(args));
		ctx.registerShutdownHook();
	}
	
	public static String getContextFilename(String args[])
	{
		String filename = "/service-context.xml";
		if (args.length >= 1 )
			filename = "/" + args[0] + "-context.xml";
		return filename;
	}
}
