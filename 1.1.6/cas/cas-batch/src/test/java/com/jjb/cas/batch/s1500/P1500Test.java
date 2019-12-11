package com.jjb.cas.batch.s1500;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 同步核心卡帐状态测试
 * 
 * @author adi
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/batch-context-test.xml")
public class P1500Test {/*

	*//**
	 * 单元测试小工具
	 *//*
	@Resource(name = "p0000Launcher")
	private JobLauncherTestUtils jobLauncherTestUtils;

	*//**
	 * 资源文件resS150001Response
	 *//*
	@Resource
	private YakFileResourceMock<FileHeader, SynchroCardAcctInfoItem> resS150001Response;

	*//**
	 * 准备数据
	 * 
	 * @throws Exception
	 *//*
	@Before
	public void setUp() throws Exception {
		// 准备数据
		int rows = 3;
		SynchroCardAcctInfoItem[] items = new SynchroCardAcctInfoItem[rows];

		for (int i = 0; i < rows; i++) {
			SynchroCardAcctInfoItem item = new SynchroCardAcctInfoItem();
			item.org = "123";
			item.appNo = "123";
			item.setupDate = randomDate("2102-01-01", "2102-12-31");
			item.productCd = "123";
			item.acctNo = 123;
			item.cardNo = "123";
			item.pyhCd = "123";
			item.cardBlockCode = "H";
			item.acctBlockCode = "D";
			
			items[i] = item;
		}

		// 创建文件
		resS150001Response.prepare(new FileHeader(), items);
	}

	@Test
	public void TestCase1() throws Exception {
		// 开始跑批

		JobExecution e = jobLauncherTestUtils.launchStep("s1501-synchroblockcode");

		// 检查结果
		Assert.assertEquals(ExitStatus.COMPLETED, e.getExitStatus());

		// 检查输出数据的值
		List<SynchroCardAcctInfoItem> applyFileItem = resS150001Response
				.parseDetails();

		// 当天记录是否为空
		Assert.assertNotNull(applyFileItem);
	}

	private static Date randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date start = format.parse(beginDate);// 构造开始日期
			Date end = format.parse(endDate);// 构造结束日期
			// getTime()表示返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数。
			if (start.getTime() >= end.getTime()) {
				return null;
			}
			long date = random(start.getTime(), end.getTime());

			return new Date(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static long random(long begin, long end) {
		long rtn = begin + (long) (Math.random() * (end - begin));
		// 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
		if (rtn == begin || rtn == end) {
			return random(begin, end);
		}
		return rtn;
	}

*/}
