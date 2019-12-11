package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;

public class UnLocakCardDto implements Serializable{

	private static final long serialVersionUID = 1L;

    private Long id;

    private String appNo;

    private String org;

    private String name;

    private String idType;

    private String idNo;

    private String cellphone;

    private String cardNo;

    private String lockReason;

    private String operId;

    private Date updateTime;

    private Integer jpaVersion;

    private String status;
    
    private String rtfState;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
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

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getJpaVersion() {
		return jpaVersion;
	}

	public void setJpaVersion(Integer jpaVersion) {
		this.jpaVersion = jpaVersion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRtfState() {
		return rtfState;
	}

	public void setRtfState(String rtfState) {
		this.rtfState = rtfState;
	}
    
    
}
