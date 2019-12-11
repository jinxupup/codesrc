/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ApplySuccessReportQueryDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appNo;
	private String idType;
	private String idNo;
	private String name;
	private String appType;
	private String productCd;
	private BigDecimal accLmt;
	private String owningBranch;
	private String appSource;
	private String k10;
	private String a10;
	private String b10;
	private String f30;
	private String f10;
	private String f20;
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
	public BigDecimal getAccLmt() {
		return accLmt;
	}
	public void setAccLmt(BigDecimal accLmt) {
		this.accLmt = accLmt;
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
	public String getK10() {
		return k10;
	}
	public void setK10(String k10) {
		this.k10 = k10;
	}
	public String getA10() {
		return a10;
	}
	public void setA10(String a10) {
		this.a10 = a10;
	}
	public String getB10() {
		return b10;
	}
	public void setB10(String b10) {
		this.b10 = b10;
	}
	public String getF30() {
		return f30;
	}
	public void setF30(String f30) {
		this.f30 = f30;
	}
	public String getF10() {
		return f10;
	}
	public void setF10(String f10) {
		this.f10 = f10;
	}
	public String getF20() {
		return f20;
	}
	public void setF20(String f20) {
		this.f20 = f20;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
}
