package com.jjb.acl.gmp.api;

import java.io.Serializable;
import java.util.Date;

public class BatchJobStatus implements Serializable{
	private static final long serialVersionUID = 1L;

	private String status;
	
	private Date startTime;
	
	private Date endTime;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	} 
}
