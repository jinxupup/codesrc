package com.jjb.acl.gmp.sdk;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 该类用于校验批量有batch.date参数，并且该参数与当前批量时间相等
 * @author LI.J
 *
 */
public class BatchDateJobParametersValidator implements JobParametersValidator {

	@Autowired
	private BatchStatusFacility batchStatusFacility;
	
	@Override
	public void validate(JobParameters parameters) throws JobParametersInvalidException {
		batchStatusFacility.refreshSystemStatus();
		Date date = parameters.getDate("batch.date", null);
		if (date == null)
			throw new JobParametersInvalidException("没有指定日期型的batch.date参数");
		if (!DateUtils.isSameDay(date, batchStatusFacility.getBatchDate()))
			throw new JobParametersInvalidException(MessageFormat.format("批量日期错误：当前输入参数为{0,date,long}，而当前批量日期为{1,date,long}", date, batchStatusFacility.getBatchDate()));
	}

}
