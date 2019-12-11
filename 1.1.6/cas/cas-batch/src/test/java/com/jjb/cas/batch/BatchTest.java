package com.jjb.cas.batch;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-service-context.xml")
@DirtiesContext(classMode=ClassMode.AFTER_CLASS)
public class BatchTest {
	
	@Autowired
	private JobLauncherTestUtils jobLauncherTestUtils;//spring容器中只能定义一个job
	
	@Test
	public void jobTestWithLauncherTestUtils() {
	     
		try {
			SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd"); 
			Date date = formate.parse("20151101");
			JobParameters jobParameters = new JobParametersBuilder().addDate("batch.date", date).addString("temp", System.currentTimeMillis() + "").toJobParameters();
			jobLauncherTestUtils.launchJob(jobParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
