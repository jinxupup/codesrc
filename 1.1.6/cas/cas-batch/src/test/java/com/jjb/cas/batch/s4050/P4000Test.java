package com.jjb.cas.batch.s4050;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 批量生成未终审的卸数文件
 * 运行时，解开下面注释代码
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/batch-context-test.xml")
public class P4000Test {
	
//
//	/**
//	 * 批量单元测试工具
//	 */
//	@Resource(name="p4000Launcher")
//	private JobLauncherTestUtils jobLauncherTestUtils;
//	
//	@Autowired
//	private GlobalManagementServiceMock managementMock;
//	
//	
//
//	/**
//	 * 文件资源resS000001Apply
//	 */
//	@Resource
//	private YakFileResourceMock<FileHeader, ApsReportDataFileItem> resS004000ReportData;
//	/**
//	 * 正确生成：独立主卡，独立附卡记录
//	 */
//	@Test
//	public void testCase1() throws Exception {
//		
//		//设置批量时间
//		Date today = new Date();
//		managementMock.setupBatchDate(today, DateUtils.addDays(today, -1));
//		
//		//开始跑批
////		SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd"); 
////		final Date date = formate.parse("20170803");
////		JobParameters jobParameters = new JobParametersBuilder().addDate("batch.date", date).addString("org", "000065705500").addLong("temp", System.currentTimeMillis(),null).toJobParameters();			
////		JobParameters jobParameters = new JobParametersBuilder().addDate("batch.date", managementMock.getSystemStatus().getProcessDate()).addString("org", "000065705500").addLong("temp", System.currentTimeMillis(),null).toJobParameters();
//		JobExecution e = jobLauncherTestUtils.launchStep("s4000-reportData");
//		
//		//检查结果
//		Assert.assertEquals(ExitStatus.COMPLETED, e.getExitStatus());
//	}
}
