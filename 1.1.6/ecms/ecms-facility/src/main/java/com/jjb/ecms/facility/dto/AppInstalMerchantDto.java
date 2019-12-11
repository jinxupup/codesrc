package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.math.BigDecimal;

	
public class AppInstalMerchantDto implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;

    private String org;

    private String merId;

    private String merName;

    private String merState;

    private String merCity;

    private String merAddr;

    private String merPstlCd;

    private String merPhone;

    private String merContactor;

    private String merBankName;

    private String merBankBranch;

    private String merBankAcctNo;

    private String merBankAcctName;

    private BigDecimal merLmt;

    private BigDecimal postingLmt;

    private BigDecimal finishAuditLmt;

    private BigDecimal inAuditLmt;

    private Integer jpaVersion;
    
    private BigDecimal availableCredit;

	public BigDecimal getAvailableCredit() {
		return availableCredit;
	}

	public void setAvailableCredit(BigDecimal availableCredit) {
		this.availableCredit = availableCredit;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getMerId() {
		return merId;
	}

	public void setMerId(String merId) {
		this.merId = merId;
	}

	public String getMerName() {
		return merName;
	}

	public void setMerName(String merName) {
		this.merName = merName;
	}

	public String getMerState() {
		return merState;
	}

	public void setMerState(String merState) {
		this.merState = merState;
	}

	public String getMerCity() {
		return merCity;
	}

	public void setMerCity(String merCity) {
		this.merCity = merCity;
	}

	public String getMerAddr() {
		return merAddr;
	}

	public void setMerAddr(String merAddr) {
		this.merAddr = merAddr;
	}

	public String getMerPstlCd() {
		return merPstlCd;
	}

	public void setMerPstlCd(String merPstlCd) {
		this.merPstlCd = merPstlCd;
	}

	public String getMerPhone() {
		return merPhone;
	}

	public void setMerPhone(String merPhone) {
		this.merPhone = merPhone;
	}

	public String getMerContactor() {
		return merContactor;
	}

	public void setMerContactor(String merContactor) {
		this.merContactor = merContactor;
	}

	public String getMerBankName() {
		return merBankName;
	}

	public void setMerBankName(String merBankName) {
		this.merBankName = merBankName;
	}

	public String getMerBankBranch() {
		return merBankBranch;
	}

	public void setMerBankBranch(String merBankBranch) {
		this.merBankBranch = merBankBranch;
	}

	public String getMerBankAcctNo() {
		return merBankAcctNo;
	}

	public void setMerBankAcctNo(String merBankAcctNo) {
		this.merBankAcctNo = merBankAcctNo;
	}

	public String getMerBankAcctName() {
		return merBankAcctName;
	}

	public void setMerBankAcctName(String merBankAcctName) {
		this.merBankAcctName = merBankAcctName;
	}

	public BigDecimal getMerLmt() {
		return merLmt;
	}

	public void setMerLmt(BigDecimal merLmt) {
		this.merLmt = merLmt;
	}

	public BigDecimal getPostingLmt() {
		return postingLmt;
	}

	public void setPostingLmt(BigDecimal postingLmt) {
		this.postingLmt = postingLmt;
	}

	public BigDecimal getFinishAuditLmt() {
		return finishAuditLmt;
	}

	public void setFinishAuditLmt(BigDecimal finishAuditLmt) {
		this.finishAuditLmt = finishAuditLmt;
	}

	public BigDecimal getInAuditLmt() {
		return inAuditLmt;
	}

	public void setInAuditLmt(BigDecimal inAuditLmt) {
		this.inAuditLmt = inAuditLmt;
	}

	public Integer getJpaVersion() {
		return jpaVersion;
	}

	public void setJpaVersion(Integer jpaVersion) {
		this.jpaVersion = jpaVersion;
	}
	
}
