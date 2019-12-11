package com.jjb.acl.gmp.sdk;

import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.batch.YakFileFacilityWriter;

/**
 * 作为 {@link YakFileFacilityWriter}的一个特例实现，固定取批量日期为批次号
 * 注意：由于在初始化时取批量日期，scope一定要定义为step，不然会报GMP的RPC超时，因为初始化时RPC机制还没有初始化完。
 * @author LI.J
 *
 * @param <T>
 */
public class BatchFileWriter<T> extends YakFileFacilityWriter<T> {
	
	@Autowired
	private BatchStatusFacility batchStatusFacility;
	
	private String format = "yyyyMMdd";
	
	@PostConstruct
	public void initBatchDate()
	{
		setBatchNumber(new SimpleDateFormat(format).format(batchStatusFacility.getBatchDate()));
	}
}
