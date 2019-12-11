package com.jjb.unicorn.batch.api;

import com.jjb.unicorn.batch.exception.BatchException;

/**
 * 
 * @ClassName BatchOperation
 * @Description 批量操作类
 * @author jjb
 * @Date 2016年9月26日 下午9:10:15
 * @version 1.0.0
 */
public interface BatchOperation {
	
	
	/**
	 * 启动批量
	 * @param jobName  作业名称
	 * @param params   参数
	 * @return
	 */
	public long startBatch (String sysType,String jobName,String params) throws BatchException;
	
	
	/**
	 * 重启批量
	 * @param executionId   执行ID
	 * @return
	 */
	public long restartBatch (String sysType,long executionId) throws BatchException;
	
	
	/**
	 * 停止批量
	 * @param executionId
	 */
	public boolean stopBatch (String sysType,long executionId) throws BatchException;
}
