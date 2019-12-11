/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;


public class ApplyRejectReportQueryDto implements Serializable {
	
	private String appNo;
	private String idType;
	private String idNo;
	private String name;
	private String appType;
	private String productCd;
	private String owningBranch;
	private String appSource;
	private String refuseCode;
	private String refuseDesc;
	private String oper;
	private Date checkDate;
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAppType() {
		return appType;
	}
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public String getOwningBranch() {
		return owningBranch;
	}
	public void setOwningBranch(String owningBranch) {
		this.owningBranch = owningBranch;
	}
	public String getAppSource() {
		return appSource;
	}
	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}
	public String getRefuseCode() {
		return refuseCode;
	}
	public void setRefuseCode(String refuseCode) {
		this.refuseCode = refuseCode;
	}
	public String getRefuseDesc() {
		return refuseDesc;
	}
	public void setRefuseDesc(String refuseDesc) {
		this.refuseDesc = refuseDesc;
	}
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	

}
