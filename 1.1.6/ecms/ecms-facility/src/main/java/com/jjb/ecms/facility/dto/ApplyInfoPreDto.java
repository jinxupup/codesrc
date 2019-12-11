package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * 预申请录入信息模型
 * 
 * @author H.N
 */
public class ApplyInfoPreDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String appNo;

    private String idType;
    
    private String idNo;
    
    private String name;
    
    private String appType;
    
    private String productCd;
    
    private String cellphone;
    
    private String createUser;
    
    private Date createDate;
    
    private String rtfState;
    
    private String refuseCode;
    
    private String cardNo;
    
    private String creditLimit;
    
    private String blockCode;

	private String cardExpireDate;

    private String taskOwner;//任务所属人姓名


	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public String getCardExpireDate() {
		return cardExpireDate;
	}

	public void setCardExpireDate(String cardExpireDate) {
		this.cardExpireDate = cardExpireDate;
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

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRtfState() {
		return rtfState;
	}

	public void setRtfState(String rtfState) {
		this.rtfState = rtfState;
	}

	public String getRefuseCode() {
		return refuseCode;
	}

	public void setRefuseCode(String refuseCode) {
		this.refuseCode = refuseCode;
	}

}    