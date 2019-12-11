package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @ClassName InstallmentReportDto
 * @Description TODO 分期报表
 * @author H.N
 * @Date 2017年12月23日 下午5:43:50
 * @version 1.0.0
 */
public class InstallmentReportDto implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String appNo;

    private String mccNo;

    private BigDecimal cashAmt;

    private Integer loanInitTerm;

    private String instalmentCreditRpoductNo;

    private String instalmentCreditActivityNo;
    private Date lendingTime;
    private Integer lendingTimes;
    private String loanFeeMethod;
    private String marketerName;
    private String marketerId;
    private String marketerBranchId;

    private String idType;
    private String idNo;
    private String name;
    private String productCd;
    private Date updateDate;
    private String owningBranch;
    private String loanRegStatus;
    private String merName;
    private String productDesc;
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getMccNo() {
		return mccNo;
	}
	public void setMccNo(String mccNo) {
		this.mccNo = mccNo;
	}
	public BigDecimal getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}
	public Integer getLoanInitTerm() {
		return loanInitTerm;
	}
	public void setLoanInitTerm(Integer loanInitTerm) {
		this.loanInitTerm = loanInitTerm;
	}
	public String getInstalmentCreditRpoductNo() {
		return instalmentCreditRpoductNo;
	}
	public void setInstalmentCreditRpoductNo(String instalmentCreditRpoductNo) {
		this.instalmentCreditRpoductNo = instalmentCreditRpoductNo;
	}
	public String getInstalmentCreditActivityNo() {
		return instalmentCreditActivityNo;
	}
	public void setInstalmentCreditActivityNo(String instalmentCreditActivityNo) {
		this.instalmentCreditActivityNo = instalmentCreditActivityNo;
	}
	public Date getLendingTime() {
		return lendingTime;
	}
	public void setLendingTime(Date lendingTime) {
		this.lendingTime = lendingTime;
	}
	public Integer getLendingTimes() {
		return lendingTimes;
	}
	public void setLendingTimes(Integer lendingTimes) {
		this.lendingTimes = lendingTimes;
	}
	public String getLoanFeeMethod() {
		return loanFeeMethod;
	}
	public void setLoanFeeMethod(String loanFeeMethod) {
		this.loanFeeMethod = loanFeeMethod;
	}
	public String getMarketerName() {
		return marketerName;
	}
	public void setMarketerName(String marketerName) {
		this.marketerName = marketerName;
	}
	public String getMarketerId() {
		return marketerId;
	}
	public void setMarketerId(String marketerId) {
		this.marketerId = marketerId;
	}
	public String getMarketerBranchId() {
		return marketerBranchId;
	}
	public void setMarketerBranchId(String marketerBranchId) {
		this.marketerBranchId = marketerBranchId;
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
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getOwningBranch() {
		return owningBranch;
	}
	public void setOwningBranch(String owningBranch) {
		this.owningBranch = owningBranch;
	}
	public String getLoanRegStatus() {
		return loanRegStatus;
	}
	public void setLoanRegStatus(String loanRegStatus) {
		this.loanRegStatus = loanRegStatus;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

    

}
