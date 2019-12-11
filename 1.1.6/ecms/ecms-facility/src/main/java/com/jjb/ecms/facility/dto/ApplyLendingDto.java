package com.jjb.ecms.facility.dto;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 
 * @ClassName ApplyLendingDto
 * @Description 放款维护数据集合,在原有申请进度查询(ApplyProcessQueryDto)上更改
 * @author user
 * @Date 2017年11月30日 上午9:27:10
 * @version 1.0.0
 */
public class ApplyLendingDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	SELECT C.NAME,C.ID_TYPE,C.ID_NO,C.APP_TYPE,C.PRODUCT_CD,C.OWNING_BRANCH,
//	A.CARD_NO,A.APP_NO,A.CASH_AMT,A.LOAN_INIT_TERM,A.INSTALMENT_CREDIT_RPODUCT_NO,A.INSTALMENT_CREDIT_ACTIVITY_NO,A.LOAN_FEE_METHOD,A.LOAN_FEE_CALC_METHOD,
//	A.LOAN_REG_STATUS,A.LOAN_REG_ID,A.STATUS,A.MCC_NO,B.MER_NAME,B.MER_BANK_NAME,B.MER_BANK_BRANCH,B.MER_BANK_ACCT_NO,B.MER_BANK_ACCT_NAME 
//	FROM TM_APP_INSTALMENT_CREDIT A,TM_APP_INSTAL_MERCHANT B,TM_APP_MAIN C 
//	WHERE A.MCC_NO= B.MER_ID AND A.APP_NO=C.APP_NO AND A.STATUS='L05'
	private String name; //姓名
	private String idType;
	private String idNo; //身份证号
	private String appType; //申请类型
	private String productCd; //产品
	private String owningBranch; //所属网点
	private String cardNo;
	private String appNo; //申请编号
	private BigDecimal cashAmt; //申请金额
	private int loanInitTerm; //分期技术
	private String instalmentCreditRpoductNo; //分期贷款产品
	private String instalmentCreditActivityNo; //分期贷款活动号
	private String loanFeeMethod; //分期手续费收取方式
	/**
     * <p>大额分期手续费计算方式</p>
     * <p>R：附加金额按比例计算</p>
     * <p>A：附加金额为固定金额</p>
     */
	private String loanFeeCalcMethod; 
	private String mccNo; //商户号
	private String merName; //商户姓名
	private String merBankName; //放款银行名称
	private String merBankBranch; //放款开户行号
	private String merBankAcctNo; //放款账号
	private String merBankAcctName; //放款账户姓名
	private String loanRegStatus;//分期注册状态
	private String status;//放款标识,用于查询cps放款结果,0--成功,1--失败
	private String loanRegId;//分期申请顺序号,唯一标识,用来进行放款维护的key
	private String remark;
	
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
	
	public String getAppNo() {
		return appNo;
	}
	public String getOwningBranch() {
		return owningBranch;
	}
	public void setOwningBranch(String owningBranch) {
		this.owningBranch = owningBranch;
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
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public BigDecimal getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}
	public int getLoanInitTerm() {
		return loanInitTerm;
	}
	public void setLoanInitTerm(int loanInitTerm) {
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
	public String getLoanFeeMethod() {
		return loanFeeMethod;
	}
	public void setLoanFeeMethod(String loanFeeMethod) {
		this.loanFeeMethod = loanFeeMethod;
	}
	public String getLoanFeeCalcMethod() {
		return loanFeeCalcMethod;
	}
	public void setLoanFeeCalcMethod(String loanFeeCalcMethod) {
		this.loanFeeCalcMethod = loanFeeCalcMethod;
	}
	public String getMccNo() {
		return mccNo;
	}
	public void setMccNo(String mccNo) {
		this.mccNo = mccNo;
	}
	public String getMerName() {
		return merName;
	}
	public void setMerName(String merName) {
		this.merName = merName;
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
	public String getLoanRegStatus() {
		return loanRegStatus;
	}
	public void setLoanRegStatus(String loanRegStatus) {
		this.loanRegStatus = loanRegStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLoanRegId() {
		return loanRegId;
	}
	public void setLoanRegId(String loanRegId) {
		this.loanRegId = loanRegId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	


}