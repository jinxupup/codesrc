package com.jjb.ecms.facility.dto;

import java.io.Serializable;

public class AppInstalProgramMerchantDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String org;

	private String programId;
	private String merId;
	private String merName;
	private String merState;
	private String merCity;
	private String merAddr;
	private String merPstlCd;
	private String merPhone;
	private String merContactor;

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
	public String getProgramId() {
		return programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
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
	
	

}
