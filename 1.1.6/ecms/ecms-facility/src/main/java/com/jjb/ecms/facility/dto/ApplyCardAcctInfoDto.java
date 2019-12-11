/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @Description: 客户、账户和已发卡信息关联查询模型
 * @author JYData-R&D-Big T.T
 * @date 2016年9月1日 下午8:06:09
 * @version V1.0  
 */

public class ApplyCardAcctInfoDto implements Serializable {

	 private static final long serialVersionUID = 1L;
	 
	 private String custId;
	 
	 private String cardNo;
	 
	 private String org;
	 
	 private String acctNo;
	 
	 private String creditLimit;
	 
	 private String productCd;
	 
	 private String acctType;
	 
	 private String bscSuppInd;
	 
	 private String pyhCd;
	 
	 private String idType;
	 
	 private String idNo;
	 
	 private String appNo;
	 
	 private String blockCode;
	 
	 private String setupDate;
	 
	 private String cardExpireDate;
	 
	 

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getBscSuppInd() {
		return bscSuppInd;
	}

	public void setBscSuppInd(String bscSuppInd) {
		this.bscSuppInd = bscSuppInd;
	}

	public String getPyhCd() {
		return pyhCd;
	}

	public void setPyhCd(String pyhCd) {
		this.pyhCd = pyhCd;
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

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public String getSetupDate() {
		return setupDate;
	}

	public void setSetupDate(String setupDate) {
		this.setupDate = setupDate;
	}

	public String getCardExpireDate() {
		return cardExpireDate;
	}

	public void setCardExpireDate(String cardExpireDate) {
		this.cardExpireDate = cardExpireDate;
	}
	 
}
