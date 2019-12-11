/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;

public class SupplementRemindReportQueryDto implements Serializable {

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
	private String owningBranch;
	private String appSource;
	private String cellPhone;
	private String pbType;
	private String spreaderBank;
	private String spreaderName;
	private String spreaderNum;
	private Date pbStartDate;
	private Date pbTimeoutDate;
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
	public String getCellPhone() {
		return cellPhone;
	}
	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}
	public String getPbType() {
		return pbType;
	}
	public void setPbType(String pbType) {
		this.pbType = pbType;
	}
	public String getSpreaderBank() {
		return spreaderBank;
	}
	public void setSpreaderBank(String spreaderBank) {
		this.spreaderBank = spreaderBank;
	}
	public String getSpreaderName() {
		return spreaderName;
	}
	public void setSpreaderName(String spreaderName) {
		this.spreaderName = spreaderName;
	}
	public String getSpreaderNum() {
		return spreaderNum;
	}
	public void setSpreaderNum(String spreaderNum) {
		this.spreaderNum = spreaderNum;
	}
	public Date getPbStartDate() {
		return pbStartDate;
	}
	public void setPbStartDate(Date pbStartDate) {
		this.pbStartDate = pbStartDate;
	}
	public Date getPbTimeoutDate() {
		return pbTimeoutDate;
	}
	public void setPbTimeoutDate(Date pbTimeoutDate) {
		this.pbTimeoutDate = pbTimeoutDate;
	}

}
