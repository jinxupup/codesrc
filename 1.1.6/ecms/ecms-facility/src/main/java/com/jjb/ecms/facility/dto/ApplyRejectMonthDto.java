package com.jjb.ecms.facility.dto;

import java.util.Date;

public class ApplyRejectMonthDto {

	private String appNO;
	private String name;
	private String idType;
	private String idNo;
	private String productCd;
	private String productDesc;
	private String rtfState;
	private String stateDesc;
	private Date failedTime;
	private String owningBranch;
	
	public String getOwningBranch() {
		return owningBranch;
	}
	public void setOwningBranch(String owningBranch) {
		this.owningBranch = owningBranch;
	}
	public String getAppNO() {
		return appNO;
	}
	public void setAppNO(String appNO) {
		this.appNO = appNO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getRtfState() {
		return rtfState;
	}
	public void setRtfState(String rtfState) {
		this.rtfState = rtfState;
	}
	public String getStateDesc() {
		return stateDesc;
	}
	public void setStateDesc(String stateDesc) {
		this.stateDesc = stateDesc;
	}
	public Date getFailedTime() {
		return failedTime;
	}
	public void setFailedTime(Date failedTime) {
		this.failedTime = failedTime;
	}
	
	
}
