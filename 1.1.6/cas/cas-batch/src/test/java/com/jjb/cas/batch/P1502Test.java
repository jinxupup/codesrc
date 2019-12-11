package com.jjb.cas.batch;


/**
 * 测试1502返回分贷通的回盘文件
 * 
 * @author hn
 * 
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/batch-context.xml")
public class P1502Test {
//
//	/***
//	 * 批量单元测试工具
//	 */
//	@Resource(name = "p1502Launcher")
//	private JobLauncherTestUtils jobLauncherTestUtils;
//
//	/**
//	 * 资源文件resS200001ApplySucceedReport
//	 */
//	@Resource
//	private YakFileResourceMock<FileHeader, ExportItem> resS200001ApplySucceedReport;
//
//	@Autowired
//	private GlobalManagementServiceMock managementMock;
//
//	/**
//	 * 准备数据
//	 * 
//	 * @throws Exception
//	 */
//	@Before
//	public void setUp() throws Exception {
//
//		// 设置批量时间
//		Date today = new Date();
//		managementMock.setupBatchDate(today, DateUtils.setDay(today, 1));
//
//	}
//
//	@Test
//	public void testCase1() throws Exception {
//		// 开始跑批
//		JobExecution e = jobLauncherTestUtils.launchStep("s1502-cardBindResponse");
//
////		// 检查结果
////		Assert.assertEquals(ExitStatus.COMPLETED, e.getExitStatus());
////
////		// 检查输出数据的值
////		List<ExportItem> applySucceedReport = resS200001ApplySucceedReport.parseDetails();
////
////		// 当天记录是否为空
////		Assert.assertNotNull(applySucceedReport);
//	}
}
