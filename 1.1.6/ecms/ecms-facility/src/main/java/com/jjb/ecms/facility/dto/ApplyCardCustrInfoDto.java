/**
 * 
 */
package com.jjb.ecms.facility.dto;

import java.io.Serializable;

/**
 * @Description: 客户和已发卡信息关联查询模型
 * @author JYData-R&D-Big T.T
 * @date 2016年9月1日 下午8:06:09
 * @version V1.0  
 */

public class ApplyCardCustrInfoDto implements Serializable {

	 private static final long serialVersionUID = 1L;
	 
	 private String appNo;
	 
	 private String org;
	 
	 private String custId;
	  
	 private String idType;
	 
	 private String idNo;
	 
	 private String cardNo;
	 
	 private String bscSuppInd;
	 
	 private String blockCode;
	 
	 private String productCd;
	 

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

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBscSuppInd() {
		return bscSuppInd;
	}

	public void setBscSuppInd(String bscSuppInd) {
		this.bscSuppInd = bscSuppInd;
	}

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	 	 
}
