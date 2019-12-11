package com.jjb.cas.batch.s1000;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/batch-context-test.xml")
public class P1000Test {/*
	*//**
	 * 批量单元测试工具
	 *//*
	@Resource(name="p1000Launcher")
	private JobLauncherTestUtils jobLauncherTestUtils;

	*//**
	 * 文件资源
	 *//*
	@Resource
	private YakFileResourceMock<FileHeader, ApplyResponseItem> resS1000Application;

	*//**
	 * 准备数据
	 * @throws ParseException
	 *//*
	@Before
	public void setUp() throws Exception {
		
		//准备数据
		int rows = 3;
		ApplyResponseItem[] items = new ApplyResponseItem[rows];
		
		for (int i = 0; i < rows ; i ++){
			ApplyResponseItem item = new ApplyResponseItem();
			item.org = "123";
			item.appNo  = "123";
			item.setupDate  = randomDate("2102-01-01", "2102-12-31");
			item.productCd  = "123";
			item.custId  = 123;
			item.name  = "123";
			item.idType  = IdType.I;
			item.idNo  = "123";
			item.acctType  = AccountType.A;
			item.acctNo  = 123;
			item.cardNo = "123";
			item.owningBranch  = "123";
			item.mobileNo  = "123";
			item.appRejectReason  = AppRejectReason.R00;
			item.logicalCardNo  = "123";
			item.bscSuppInd  = BscSuppIndicator.S;
			item.blockCode  = "123";
			item.creditLimitCust  = new BigDecimal(123);
			item.creditLimitAcct  = new BigDecimal(123);
			item.pyhCd  = "123";
			items[i] = item;
		}
		//创建文件
		resS1000Application.prepare(new FileHeader(), items);
	}

	*//**
	 * 回还文件到表
	 *//*
	@Test
	public void testCase1() throws Exception {
		
		//开始跑批

		JobExecution e = jobLauncherTestUtils.launchJob();
		
		//检查结果
		Assert.assertEquals(ExitStatus.COMPLETED, e.getExitStatus());
		
		//检查输出数据的值
		//List<ApplyResponseItem> applyFileItem = resS000001Apply.parseDetails();
		
		// 当天记录是否为空
		//Assert.assertNotNull(applyFileItem);
		
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
