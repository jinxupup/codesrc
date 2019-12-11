package com.jjb.acl.gmp.sdk;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.jjb.acl.gmp.api.GlobalManagementService;
import com.jjb.acl.gmp.api.SystemStatus;

/**
 * 对系统状态的封装，为批量提供一个带缓存的基础设施。如果不需要缓存，请直接注入 {@link GlobalManagementService}
 * @author LI.J
 *
 */
public class BatchStatusFacility
{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private GlobalManagementService globalManagementService;
	
	@Cacheable("systemStatusCache")
	public SystemStatus getSystemStatus()
	{
		return globalManagementService.getSystemStatus();
	}
	
	@CacheEvict("systemStatusCache")
	public void refreshSystemStatus()
	{
		logger.info("清空批量日期缓存");
	}
	
	/**
	 * 取批量日期.
	 * 简单封装，即 getSystemStatus().getProcessDate()
	 */
	public Date getBatchDate()
	{
		return getSystemStatus().getProcessDate();
	}
	
	/**
	 * 取上一批量日期.
	 * 简单封装，即 getSystemStatus().getLastProcessDate()
	 */
	public Date getLastBatchDate()
	{
		return getSystemStatus().getLastProcessDate();
	}
	
	/**
	 * 确定指定日期在上一处理日和当前处理日之间。不包括正好在上一处理日的日期。
	 * @return
	 */
	public boolean shouldProcess(Date date)
	{
		SystemStatus status = getSystemStatus();
		//这里要保证就算date里包含时间也要正确处理
		date = DateUtils.truncate(date, Calendar.DATE);
		return
			status.getLastProcessDate().compareTo(date) < 0 &&
			status.getProcessDate().compareTo(date) >= 0;
	}
}
