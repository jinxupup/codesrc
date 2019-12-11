package com.jjb.cas.batch.s4050;


/**
 * 批量生成未终审的卸数文件
 * 运行时，解开下面注释代码
 */
public class LaunchTest {
	
	public static void main(String[] args) {
		/*ApplicationContext context = new ClassPathXmlApplicationContext("batch-context.xml");
		JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
		Job job = (Job) context.getBean("casJob");

		try {
			// JOB 实行
			@SuppressWarnings("deprecation")
			JobExecution result = launcher.run(job, new JobParametersBuilder().addString("test", "1")
					.addDate("batch.date", new Date(117,7,03)).toJobParameters());
//			JobExecution result = launcher.run(job, new JobParametersBuilder().addString("org", "000065705500").addLong("temp", System.currentTimeMillis(),null)
//					.addDate("batch.date", new Date(117,6,31)).toJobParameters());
//						
			
			// 运行结果输出
//			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}
