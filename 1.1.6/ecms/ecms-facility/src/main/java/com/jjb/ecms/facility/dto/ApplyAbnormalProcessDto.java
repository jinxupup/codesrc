package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;

/**
  * @Description: 异常流程申请件管理模型
  * @author JYData-R&D-L.L
  * @date 2016年9月22日 上午11:07:47
  * @version V1.0
 */
public class ApplyAbnormalProcessDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String org;
	private String appNo;
	private String appType;
	private String rtfState;
	private Date updateDate;
	private String taskId;
	private String procInstId;
	private String taskName;
	private String taskDefKey;
	private String owner;
	private String assignee;
	private String actId;
	private String excMsg;
	private String cardNo;
	private String idNo;
	private String idType;
	public String getExcMsg() {
		return excMsg;
	}
	public void setExcMsg(String excMsg) {
		this.excMsg = excMsg;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getRtfState() {
		return rtfState;
	}
	public void setRtfState(String rtfState) {
		this.rtfState = rtfState;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getProcInstId() {
		return procInstId;
	}
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getTaskDefKey() {
		return taskDefKey;
	}
	public void setTaskDefKey(String taskDefKey) {
		this.taskDefKey = taskDefKey;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getActId() {
		return actId;
	}
	public void setActId(String actId) {
		this.actId = actId;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	
}
