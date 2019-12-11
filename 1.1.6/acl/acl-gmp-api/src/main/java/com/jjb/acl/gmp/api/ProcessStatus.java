package com.jjb.acl.gmp.api;

import java.io.Serializable;

import com.jjb.acl.facility.enums.sys.ProcessRunningStatus;

public class ProcessStatus implements Serializable {

	private static final long serialVersionUID = -8161436260519912837L;
	private ProcessRunningStatus runningStatus;
	
	private String heapFree;
	
	public ProcessRunningStatus getRunningStatus() {
		return runningStatus;
	}

	public void setRunningStatus(ProcessRunningStatus runningStatus) {
		this.runningStatus = runningStatus;
	}

	public String getHeapFree() {
		return heapFree;
	}

	public void setHeapFree(String heapFree) {
		this.heapFree = heapFree;
	}
}
