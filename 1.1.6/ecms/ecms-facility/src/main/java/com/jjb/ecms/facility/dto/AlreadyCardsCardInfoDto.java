package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * @description 已有卡弹框DTO
 * @author hn
 * @date 2016年9月19日09:54:47
 */
public class AlreadyCardsCardInfoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String name;//姓名
	
	private String idNo;//证件号码
	
	private String cardNo;//卡号
	
	private String productCd;//卡产品代码
	
	private String blockCode;//卡片状态
	
	private Date cardExpireDate;//有效期
	
	private BigDecimal creditLimit;//固定额度
	
	private String appNo;//申请件编号
	
	private String cardMailerInd;//卡片寄送地址

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getProductCd() {
		return productCd;
	}

	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}

	public String getBlockCode() {
		return blockCode;
	}

	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}

	public Date getCardExpireDate() {
		return cardExpireDate;
	}

	public void setCardExpireDate(Date cardExpireDate) {
		this.cardExpireDate = cardExpireDate;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	/**
	 * @return the cardMailerInd
	 */
	public String getCardMailerInd() {
		return cardMailerInd;
	}

	/**
	 * @param cardMailerInd the cardMailerInd to set
	 */
	public void setCardMailerInd(String cardMailerInd) {
		this.cardMailerInd = cardMailerInd;
	}
	
}
