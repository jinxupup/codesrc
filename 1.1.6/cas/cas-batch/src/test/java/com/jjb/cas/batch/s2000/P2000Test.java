package com.jjb.cas.batch.s2000;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试P2001ApplySucceedReport 每日审批通过报表
 * 
 * @author H.N
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/batch-context-test.xml")
public class P2000Test {/*

	*//**
	 * 批量单元测试工具
	 *//*
	@Resource(name = "p0000Launcher")
	private JobLauncherTestUtils jobLauncherTestUtils;

	*//**
	 * 资源文件resS200001ApplySucceedReport
	 *//*
	@Resource
	private YakFileResourceMock<FileHeader, I2001ApplySucceedReport> resS200001ApplySucceedReport;

	@Autowired
	private GlobalManagementServiceMock managementMock;

	@Autowired
	private RTmAppMain rTmAppMain;

	@Autowired
	private RTmAppPrimApplicantInfo rTmAppPrimApplicantInfo;

	@Autowired
	private RTmAppAttachApplierInfo rTmAppAttachApplierInfo;

	*//**
	 * 准备数据
	 * 
	 * @throws Exception
	 *//*
	@Before
	public void setUp() throws Exception {

		// 设置批量时间
		Date today = new Date();
		managementMock.setupBatchDate(today, DateUtils.addDays(today, -1));

		for (int i = 0; i < 2; i++) {
			// 主卡
			if (i == 0) {
				TmAppMain tmAppMain = new TmAppMain();
				MakeData.setDefaultValue(tmAppMain);

				tmAppMain.setOrg("000000000123");
				tmAppMain.setAppNo("2012092900000000000" + i);
				tmAppMain.setRefuseCode(Indicator.Y.name());
				tmAppMain.setAppType(AppType.A);
				tmAppMain.setProductCd("2");
				tmAppMain.setAccLmt(new BigDecimal(5000.00));
				tmAppMain.setUpdateUser("aotu");
				tmAppMain.setUpdateDate(today);

				rTmAppMain.save(tmAppMain);

				TmAppPrimApplicantInfo tmAppPrimApplicantInfo = new TmAppPrimApplicantInfo();
				MakeData.setDefaultValue(tmAppPrimApplicantInfo);

				tmAppPrimApplicantInfo.setName("张三");
				tmAppPrimApplicantInfo.setIdType(IdType.I);
				tmAppPrimApplicantInfo.setIdNo("500368000000000000" + i);

				rTmAppPrimApplicantInfo.save(tmAppPrimApplicantInfo);

			}

			// 附卡
			if (i == 2) {
				TmAppMain tmAppMain = new TmAppMain();
				MakeData.setDefaultValue(tmAppMain);

				tmAppMain.setOrg("000000000123");
				tmAppMain.setAppNo("2012092900000000000" + i);
				tmAppMain.setRefuseCode(Indicator.Y.name());
				tmAppMain.setAppType(AppType.A);
				tmAppMain.setProductCd("2");
				tmAppMain.setAccLmt(new BigDecimal(6000.00));
				tmAppMain.setUpdateUser("aotu");
				tmAppMain.setUpdateDate(today);

				rTmAppMain.save(tmAppMain);

				TmAppAttachApplierInfo tmAppAttachApplierInfo = new TmAppAttachApplierInfo();
				MakeData.setDefaultValue(tmAppAttachApplierInfo);

				tmAppAttachApplierInfo.setName("李四");
				tmAppAttachApplierInfo.setIdType(IdType.I);
				tmAppAttachApplierInfo.setIdNo("500368000000000000" + i);

				rTmAppAttachApplierInfo.save(tmAppAttachApplierInfo);
			}
		}
	}

	@Test
	public void testCase1() throws Exception {
		// 开始跑批
		JobExecution e = jobLauncherTestUtils.launchStep("s2000-applysucceedreport");

		// 检查结果
		Assert.assertEquals(ExitStatus.COMPLETED, e.getExitStatus());

		// 检查输出数据的值
		List<I2001ApplySucceedReport> applySucceedReport = resS200001ApplySucceedReport.parseDetails();

		// 当天记录是否为空
		Assert.assertNotNull(applySucceedReport);
	}
*/}
