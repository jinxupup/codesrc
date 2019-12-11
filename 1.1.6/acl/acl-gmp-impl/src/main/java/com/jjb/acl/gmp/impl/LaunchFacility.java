package com.jjb.acl.gmp.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class LaunchFacility {

	private Job job;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JobRepository jobRepository;
	
    //根据job和step名拉起一个step
	public void launchStepByNames(Job job,String[] stepNames) throws Exception
	{
		Logger logger = LoggerFactory.getLogger(LaunchFacility.class);
	
		//job发射器，job存储器
		JobLauncherTestUtils jobLauncherTestUtils = new JobLauncherTestUtils();
		jobLauncherTestUtils.setJob(job);
		jobLauncherTestUtils.setJobLauncher(jobLauncher);
		jobLauncherTestUtils.setJobRepository(jobRepository);
		//拉起step
		for (String stepName : stepNames)
		{
			Map<String, JobParameter> jobParameters = new HashMap<String, JobParameter>(); 
			jobParameters.put("startTime", new JobParameter(new Date().getTime()));
			JobParameters param = new JobParameters(jobParameters);
			logger.info("-----------------start "+stepName+"-----------------");
			jobLauncherTestUtils.launchStep(stepName, param);
			logger.info("-----------------end "+stepName+"-----------------");
		}
	}
	
	/**
	 * 
	 * args[0] x-batch jar包
	 * args[1] job
	 *stepNames
	 */
	public static void main(String[] args)
	{
		Logger logger = LoggerFactory.getLogger(LaunchFacility.class);
		logger.info("-------------程序开始运行...--------------");
		//获取要执行的所有step
		String[] stepNames = getStepNames(args);
		//根据args[0]（要处理的batch所在的jar包）获取要加载的xml
		ConfigurableApplicationContext ac = new ClassPathXmlApplicationContext("jar:file:"+args[0]+"!/service-context.xml");
		LaunchFacility launchStep = new LaunchFacility();
		//将launchStep1注入spring，类似于@Compoment
		ac.registerShutdownHook();
	    ac.getAutowireCapableBeanFactory().autowireBean(launchStep);
	    //获取job
	    Job job = ac.getBean(args[1],Job.class);
		try {
			launchStep.launchStepByNames(job,stepNames);
		} catch (Exception e) {
			ac.close();
			logger.error("程序运行失败", e);
		}
		ac.close();
		System.exit(0);
	}
	
	private static String[] getStepNames(String[] args)
	{
		String[] stepNames = new String[args.length-2];
		for (int i=2;i<args.length;i++)
		{
			stepNames[i-2] = args[i];
		}
		return stepNames;
			
	}
	
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public JobLauncher getJobLauncher() {
		return jobLauncher;
	}

	public void setJobLauncher(JobLauncher jobLauncher) {
		this.jobLauncher = jobLauncher;
	}

	public JobRepository getJobRepository() {
		return jobRepository;
	}

	public void setJobRepository(JobRepository jobRepository) {
		this.jobRepository = jobRepository;
	}
}
