package com.jjb.acl.gmp.api;

import java.io.Serializable;
import java.util.Date;

public class BatchStepStatus implements Serializable {

	private static final long serialVersionUID = 2971338578478459054L;

	private Date startTime;
	
	private Date endTime;
	
	private int commitCount;
	
	private String hintMessage;
	
	private String status;
	
	private String exitCode;
	
	private String exitDescription;

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

	public int getCommitCount() {
		return commitCount;
	}

	public void setCommitCount(int commitCount) {
		this.commitCount = commitCount;
	}

	public String getHintMessage() {
		return hintMessage;
	}

	public void setHintMessage(String hintMessage) {
		this.hintMessage = hintMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

	public String getExitDescription() {
		return exitDescription;
	}

	public void setExitDescription(String exitDescription) {
		this.exitDescription = exitDescription;
	}
}
