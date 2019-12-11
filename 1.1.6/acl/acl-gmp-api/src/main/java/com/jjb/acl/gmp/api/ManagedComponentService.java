package com.jjb.acl.gmp.api;

import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.meta.RPCAsync;
import com.jjb.unicorn.facility.meta.RPCQueueName;
import com.jjb.unicorn.facility.meta.RPCVersion;

/**
 * 托管进程及托管批量相关服务
 * @author LI.J
 *
 */
@RPCQueueName("global.gmp.rpc.managed-component")
@RPCVersion("1.0.0")
public interface ManagedComponentService {
	
	void startProcess(int processId) throws ProcessException;
	
	void stopProcess(int processId) throws ProcessException;
	
	/**
	 * 更新托管进程状态(心跳)
	 * @param processId
	 * @param status
	 */
	@RPCAsync
	void updateProcessStatus(int processId, ProcessStatus status);
	
	@RPCAsync
	void updateBatchJobStatus(int batchInstId, BatchJobStatus status);
	
	@RPCAsync
	void updateBatchStepStatus(int batchInstId, String stepName, BatchStepStatus status);
	
	/**
	 * <p>提供单独批量Job状态更新</p>
	 * @param batchInstId
	 * @param status
	 */
	@RPCAsync
	void updateIndieBatchJobStatus(int indieBatchInstId, BatchJobStatus status);
	
	/**
	 * <p><提供单独批量Step状态更新/p>
	 * @param indieBatchInstId
	 * @param stepName
	 * @param status
	 */
	@RPCAsync
	void updateIndieBatchStepStatus(int indieBatchInstId, String stepName, BatchStepStatus status);
	
}
