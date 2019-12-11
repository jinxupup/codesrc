package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 信贷信息表
 * @author jjb
 */
public class TmAppCreditLoan implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String mainLoanid;

    private String productCd;

    private String businessMold;

    private String businessType;

    private String merNo;

    private String merName;

    private BigDecimal applyMoney;

    private BigDecimal accLmt;

    private String loanPercent;

    private Integer loanInitTerm;

    private String loanUse;

    private String guaranteeway;

    private BigDecimal feeRate;

    private BigDecimal feeAmt;

    private String distributeMethod;

    private BigDecimal loanFixedPmtPrin;

    private BigDecimal loanFirstTermPrin;

    private BigDecimal loanFinalTermPrin;

    private BigDecimal loanInitFee;

    private BigDecimal loanFixedFee;

    private BigDecimal loanFirstTermFee;

    private BigDecimal loanFinalTermFee;

    private String mlSendMode;

    private Date lendingTime;

    private Integer lendingTimes;

    private String mlBankName;

    private String mlBankBranch;

    private String mlBankAcctNo;

    private String mlBankAcctName;

    private String repaymentway;

    private String interval;

    private String reasonDesc;

    private String status;

    private String statusDesc;

    private Date createDate;

    private String updateUser;

    private String createUser;

    private Date updateDate;

    private Integer jpaVersion;

    private String url;

    private String loanType;

    private Integer term;

    private String rateType;

    private BigDecimal loanRate;

    /**
     * <p>标识</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>标识</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>ORG</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>ORG</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * <p>申请编号</p>
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * <p>申请编号</p>
     */
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    /**
     * <p>主贷款业务编号</p>
     */
    public String getMainLoanid() {
        return mainLoanid;
    }

    /**
     * <p>主贷款业务编号</p>
     */
    public void setMainLoanid(String mainLoanid) {
        this.mainLoanid = mainLoanid;
    }

    /**
     * <p>产品编号</p>
     */
    public String getProductCd() {
        return productCd;
    }

    /**
     * <p>产品编号</p>
     */
    public void setProductCd(String productCd) {
        this.productCd = productCd;
    }

    /**
     * <p>业务大类</p>
     */
    public String getBusinessMold() {
        return businessMold;
    }

    /**
     * <p>业务大类</p>
     */
    public void setBusinessMold(String businessMold) {
        this.businessMold = businessMold;
    }

    /**
     * <p>业务小类</p>
     */
    public String getBusinessType() {
        return businessType;
    }

    /**
     * <p>业务小类</p>
     */
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    /**
     * <p>商户编号</p>
     */
    public String getMerNo() {
        return merNo;
    }

    /**
     * <p>商户编号</p>
     */
    public void setMerNo(String merNo) {
        this.merNo = merNo;
    }

    /**
     * <p>商户名称</p>
     */
    public String getMerName() {
        return merName;
    }

    /**
     * <p>商户名称</p>
     */
    public void setMerName(String merName) {
        this.merName = merName;
    }

    /**
     * <p>贷款金额</p>
     */
    public BigDecimal getApplyMoney() {
        return applyMoney;
    }

    /**
     * <p>贷款金额</p>
     */
    public void setApplyMoney(BigDecimal applyMoney) {
        this.applyMoney = applyMoney;
    }

    /**
     * <p>核准额度</p>
     */
    public BigDecimal getAccLmt() {
        return accLmt;
    }

    /**
     * <p>核准额度</p>
     */
    public void setAccLmt(BigDecimal accLmt) {
        this.accLmt = accLmt;
    }

    /**
     * <p>贷款成数</p>
     */
    public String getLoanPercent() {
        return loanPercent;
    }

    /**
     * <p>贷款成数</p>
     */
    public void setLoanPercent(String loanPercent) {
        this.loanPercent = loanPercent;
    }

    /**
     * <p>总期数</p>
     */
    public Integer getLoanInitTerm() {
        return loanInitTerm;
    }

    /**
     * <p>总期数</p>
     */
    public void setLoanInitTerm(Integer loanInitTerm) {
        this.loanInitTerm = loanInitTerm;
    }

    /**
     * <p>贷款用途</p>
     */
    public String getLoanUse() {
        return loanUse;
    }

    /**
     * <p>贷款用途</p>
     */
    public void setLoanUse(String loanUse) {
        this.loanUse = loanUse;
    }

    /**
     * <p>担保方式</p>
     */
    public String getGuaranteeway() {
        return guaranteeway;
    }

    /**
     * <p>担保方式</p>
     */
    public void setGuaranteeway(String guaranteeway) {
        this.guaranteeway = guaranteeway;
    }

    /**
     * <p>分期手续费比例</p>
     */
    public BigDecimal getFeeRate() {
        return feeRate;
    }

    /**
     * <p>分期手续费比例</p>
     */
    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    /**
     * <p>分期手续费固定金额</p>
     */
    public BigDecimal getFeeAmt() {
        return feeAmt;
    }

    /**
     * <p>分期手续费固定金额</p>
     */
    public void setFeeAmt(BigDecimal feeAmt) {
        this.feeAmt = feeAmt;
    }

    /**
     * <p>分期本金分配方式</p>
     * <p>F：按月平分</p>
     * <p>L：全部本金放在末期</p>
     */
    public String getDistributeMethod() {
        return distributeMethod;
    }

    /**
     * <p>分期本金分配方式</p>
     * <p>F：按月平分</p>
     * <p>L：全部本金放在末期</p>
     */
    public void setDistributeMethod(String distributeMethod) {
        this.distributeMethod = distributeMethod;
    }

    /**
     * <p>分期每期应还本金</p>
     */
    public BigDecimal getLoanFixedPmtPrin() {
        return loanFixedPmtPrin;
    }

    /**
     * <p>分期每期应还本金</p>
     */
    public void setLoanFixedPmtPrin(BigDecimal loanFixedPmtPrin) {
        this.loanFixedPmtPrin = loanFixedPmtPrin;
    }

    /**
     * <p>分期首期应还本金</p>
     */
    public BigDecimal getLoanFirstTermPrin() {
        return loanFirstTermPrin;
    }

    /**
     * <p>分期首期应还本金</p>
     */
    public void setLoanFirstTermPrin(BigDecimal loanFirstTermPrin) {
        this.loanFirstTermPrin = loanFirstTermPrin;
    }

    /**
     * <p>分期末期应还本金</p>
     */
    public BigDecimal getLoanFinalTermPrin() {
        return loanFinalTermPrin;
    }

    /**
     * <p>分期末期应还本金</p>
     */
    public void setLoanFinalTermPrin(BigDecimal loanFinalTermPrin) {
        this.loanFinalTermPrin = loanFinalTermPrin;
    }

    /**
     * <p>分期总手续费</p>
     */
    public BigDecimal getLoanInitFee() {
        return loanInitFee;
    }

    /**
     * <p>分期总手续费</p>
     */
    public void setLoanInitFee(BigDecimal loanInitFee) {
        this.loanInitFee = loanInitFee;
    }

    /**
     * <p>分期每期手续费</p>
     */
    public BigDecimal getLoanFixedFee() {
        return loanFixedFee;
    }

    /**
     * <p>分期每期手续费</p>
     */
    public void setLoanFixedFee(BigDecimal loanFixedFee) {
        this.loanFixedFee = loanFixedFee;
    }

    /**
     * <p>分期首期手续费</p>
     */
    public BigDecimal getLoanFirstTermFee() {
        return loanFirstTermFee;
    }

    /**
     * <p>分期首期手续费</p>
     */
    public void setLoanFirstTermFee(BigDecimal loanFirstTermFee) {
        this.loanFirstTermFee = loanFirstTermFee;
    }

    /**
     * <p>分期末期手续费</p>
     */
    public BigDecimal getLoanFinalTermFee() {
        return loanFinalTermFee;
    }

    /**
     * <p>分期末期手续费</p>
     */
    public void setLoanFinalTermFee(BigDecimal loanFinalTermFee) {
        this.loanFinalTermFee = loanFinalTermFee;
    }

    /**
     * <p>放款方式</p>
     */
    public String getMlSendMode() {
        return mlSendMode;
    }

    /**
     * <p>放款方式</p>
     */
    public void setMlSendMode(String mlSendMode) {
        this.mlSendMode = mlSendMode;
    }

    /**
     * <p>放款时间</p>
     */
    public Date getLendingTime() {
        return lendingTime;
    }

    /**
     * <p>放款时间</p>
     */
    public void setLendingTime(Date lendingTime) {
        this.lendingTime = lendingTime;
    }

    /**
     * <p>放款次数</p>
     */
    public Integer getLendingTimes() {
        return lendingTimes;
    }

    /**
     * <p>放款次数</p>
     */
    public void setLendingTimes(Integer lendingTimes) {
        this.lendingTimes = lendingTimes;
    }

    /**
     * <p>放款银行名称</p>
     */
    public String getMlBankName() {
        return mlBankName;
    }

    /**
     * <p>放款银行名称</p>
     */
    public void setMlBankName(String mlBankName) {
        this.mlBankName = mlBankName;
    }

    /**
     * <p>放款开户行号</p>
     */
    public String getMlBankBranch() {
        return mlBankBranch;
    }

    /**
     * <p>放款开户行号</p>
     */
    public void setMlBankBranch(String mlBankBranch) {
        this.mlBankBranch = mlBankBranch;
    }

    /**
     * <p>放款账号</p>
     */
    public String getMlBankAcctNo() {
        return mlBankAcctNo;
    }

    /**
     * <p>放款账号</p>
     */
    public void setMlBankAcctNo(String mlBankAcctNo) {
        this.mlBankAcctNo = mlBankAcctNo;
    }

    /**
     * <p>放款账户姓名</p>
     */
    public String getMlBankAcctName() {
        return mlBankAcctName;
    }

    /**
     * <p>放款账户姓名</p>
     */
    public void setMlBankAcctName(String mlBankAcctName) {
        this.mlBankAcctName = mlBankAcctName;
    }

    /**
     * <p>还款方式</p>
     */
    public String getRepaymentway() {
        return repaymentway;
    }

    /**
     * <p>还款方式</p>
     */
    public void setRepaymentway(String repaymentway) {
        this.repaymentway = repaymentway;
    }

    /**
     * <p>还款间隔</p>
     * <p>按月，目前只支持按月还款</p>
     */
    public String getInterval() {
        return interval;
    }

    /**
     * <p>还款间隔</p>
     * <p>按月，目前只支持按月还款</p>
     */
    public void setInterval(String interval) {
        this.interval = interval;
    }

    /**
     * <p>失败原因</p>
     */
    public String getReasonDesc() {
        return reasonDesc;
    }

    /**
     * <p>失败原因</p>
     */
    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    /**
     * <p>状态</p>
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>状态</p>
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * <p>状态描述</p>
     */
    public String getStatusDesc() {
        return statusDesc;
    }

    /**
     * <p>状态描述</p>
     */
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    /**
     * <p>创建时间</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>创建时间</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>更新人</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>更新人</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * <p>创建人</p>
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>创建人</p>
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * <p>更新时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>更新时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * <p>乐观锁</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观锁</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    /**
     * <p>影像地址</p>
     */
    public String getUrl() {
        return url;
    }

    /**
     * <p>影像地址</p>
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <p>贷款类型</p>
     */
    public String getLoanType() {
        return loanType;
    }

    /**
     * <p>贷款类型</p>
     */
    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    /**
     * <p>贷款期限（月）</p>
     * <p>整数型到月份数，如12,18,24等</p>
     */
    public Integer getTerm() {
        return term;
    }

    /**
     * <p>贷款期限（月）</p>
     * <p>整数型到月份数，如12,18,24等</p>
     */
    public void setTerm(Integer term) {
        this.term = term;
    }

    /**
     * <p>利率模式</p>
     */
    public String getRateType() {
        return rateType;
    }

    /**
     * <p>利率模式</p>
     */
    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    /**
     * <p>年利率（%）/月费率</p>
     * <p>如8%只许填写数值8</p>
     */
    public BigDecimal getLoanRate() {
        return loanRate;
    }

    /**
     * <p>年利率（%）/月费率</p>
     * <p>如8%只许填写数值8</p>
     */
    public void setLoanRate(BigDecimal loanRate) {
        this.loanRate = loanRate;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("mainLoanid", mainLoanid);
        map.put("productCd", productCd);
        map.put("businessMold", businessMold);
        map.put("businessType", businessType);
        map.put("merNo", merNo);
        map.put("merName", merName);
        map.put("applyMoney", applyMoney);
        map.put("accLmt", accLmt);
        map.put("loanPercent", loanPercent);
        map.put("loanInitTerm", loanInitTerm);
        map.put("loanUse", loanUse);
        map.put("guaranteeway", guaranteeway);
        map.put("feeRate", feeRate);
        map.put("feeAmt", feeAmt);
        map.put("distributeMethod", distributeMethod);
        map.put("loanFixedPmtPrin", loanFixedPmtPrin);
        map.put("loanFirstTermPrin", loanFirstTermPrin);
        map.put("loanFinalTermPrin", loanFinalTermPrin);
        map.put("loanInitFee", loanInitFee);
        map.put("loanFixedFee", loanFixedFee);
        map.put("loanFirstTermFee", loanFirstTermFee);
        map.put("loanFinalTermFee", loanFinalTermFee);
        map.put("mlSendMode", mlSendMode);
        map.put("lendingTime", lendingTime);
        map.put("lendingTimes", lendingTimes);
        map.put("mlBankName", mlBankName);
        map.put("mlBankBranch", mlBankBranch);
        map.put("mlBankAcctNo", mlBankAcctNo);
        map.put("mlBankAcctName", mlBankAcctName);
        map.put("repaymentway", repaymentway);
        map.put("interval", interval);
        map.put("reasonDesc", reasonDesc);
        map.put("status", status);
        map.put("statusDesc", statusDesc);
        map.put("createDate", createDate);
        map.put("updateUser", updateUser);
        map.put("createUser", createUser);
        map.put("updateDate", updateDate);
        map.put("jpaVersion", jpaVersion);
        map.put("url", url);
        map.put("loanType", loanType);
        map.put("term", term);
        map.put("rateType", rateType);
        map.put("loanRate", loanRate);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("mainLoanid")) this.setMainLoanid(DataTypeUtils.getStringValue(map.get("mainLoanid")));
        if (map.containsKey("productCd")) this.setProductCd(DataTypeUtils.getStringValue(map.get("productCd")));
        if (map.containsKey("businessMold")) this.setBusinessMold(DataTypeUtils.getStringValue(map.get("businessMold")));
        if (map.containsKey("businessType")) this.setBusinessType(DataTypeUtils.getStringValue(map.get("businessType")));
        if (map.containsKey("merNo")) this.setMerNo(DataTypeUtils.getStringValue(map.get("merNo")));
        if (map.containsKey("merName")) this.setMerName(DataTypeUtils.getStringValue(map.get("merName")));
        if (map.containsKey("applyMoney")) this.setApplyMoney(DataTypeUtils.getBigDecimalValue(map.get("applyMoney")));
        if (map.containsKey("accLmt")) this.setAccLmt(DataTypeUtils.getBigDecimalValue(map.get("accLmt")));
        if (map.containsKey("loanPercent")) this.setLoanPercent(DataTypeUtils.getStringValue(map.get("loanPercent")));
        if (map.containsKey("loanInitTerm")) this.setLoanInitTerm(DataTypeUtils.getIntegerValue(map.get("loanInitTerm")));
        if (map.containsKey("loanUse")) this.setLoanUse(DataTypeUtils.getStringValue(map.get("loanUse")));
        if (map.containsKey("guaranteeway")) this.setGuaranteeway(DataTypeUtils.getStringValue(map.get("guaranteeway")));
        if (map.containsKey("feeRate")) this.setFeeRate(DataTypeUtils.getBigDecimalValue(map.get("feeRate")));
        if (map.containsKey("feeAmt")) this.setFeeAmt(DataTypeUtils.getBigDecimalValue(map.get("feeAmt")));
        if (map.containsKey("distributeMethod")) this.setDistributeMethod(DataTypeUtils.getStringValue(map.get("distributeMethod")));
        if (map.containsKey("loanFixedPmtPrin")) this.setLoanFixedPmtPrin(DataTypeUtils.getBigDecimalValue(map.get("loanFixedPmtPrin")));
        if (map.containsKey("loanFirstTermPrin")) this.setLoanFirstTermPrin(DataTypeUtils.getBigDecimalValue(map.get("loanFirstTermPrin")));
        if (map.containsKey("loanFinalTermPrin")) this.setLoanFinalTermPrin(DataTypeUtils.getBigDecimalValue(map.get("loanFinalTermPrin")));
        if (map.containsKey("loanInitFee")) this.setLoanInitFee(DataTypeUtils.getBigDecimalValue(map.get("loanInitFee")));
        if (map.containsKey("loanFixedFee")) this.setLoanFixedFee(DataTypeUtils.getBigDecimalValue(map.get("loanFixedFee")));
        if (map.containsKey("loanFirstTermFee")) this.setLoanFirstTermFee(DataTypeUtils.getBigDecimalValue(map.get("loanFirstTermFee")));
        if (map.containsKey("loanFinalTermFee")) this.setLoanFinalTermFee(DataTypeUtils.getBigDecimalValue(map.get("loanFinalTermFee")));
        if (map.containsKey("mlSendMode")) this.setMlSendMode(DataTypeUtils.getStringValue(map.get("mlSendMode")));
        if (map.containsKey("lendingTime")) this.setLendingTime(DataTypeUtils.getDateValue(map.get("lendingTime")));
        if (map.containsKey("lendingTimes")) this.setLendingTimes(DataTypeUtils.getIntegerValue(map.get("lendingTimes")));
        if (map.containsKey("mlBankName")) this.setMlBankName(DataTypeUtils.getStringValue(map.get("mlBankName")));
        if (map.containsKey("mlBankBranch")) this.setMlBankBranch(DataTypeUtils.getStringValue(map.get("mlBankBranch")));
        if (map.containsKey("mlBankAcctNo")) this.setMlBankAcctNo(DataTypeUtils.getStringValue(map.get("mlBankAcctNo")));
        if (map.containsKey("mlBankAcctName")) this.setMlBankAcctName(DataTypeUtils.getStringValue(map.get("mlBankAcctName")));
        if (map.containsKey("repaymentway")) this.setRepaymentway(DataTypeUtils.getStringValue(map.get("repaymentway")));
        if (map.containsKey("interval")) this.setInterval(DataTypeUtils.getStringValue(map.get("interval")));
        if (map.containsKey("reasonDesc")) this.setReasonDesc(DataTypeUtils.getStringValue(map.get("reasonDesc")));
        if (map.containsKey("status")) this.setStatus(DataTypeUtils.getStringValue(map.get("status")));
        if (map.containsKey("statusDesc")) this.setStatusDesc(DataTypeUtils.getStringValue(map.get("statusDesc")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("url")) this.setUrl(DataTypeUtils.getStringValue(map.get("url")));
        if (map.containsKey("loanType")) this.setLoanType(DataTypeUtils.getStringValue(map.get("loanType")));
        if (map.containsKey("term")) this.setTerm(DataTypeUtils.getIntegerValue(map.get("term")));
        if (map.containsKey("rateType")) this.setRateType(DataTypeUtils.getStringValue(map.get("rateType")));
        if (map.containsKey("loanRate")) this.setLoanRate(DataTypeUtils.getBigDecimalValue(map.get("loanRate")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", org="+org+
        "org="+org+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", mainLoanid="+mainLoanid+
        "mainLoanid="+mainLoanid+
        ", productCd="+productCd+
        "productCd="+productCd+
        ", businessMold="+businessMold+
        "businessMold="+businessMold+
        ", businessType="+businessType+
        "businessType="+businessType+
        ", merNo="+merNo+
        "merNo="+merNo+
        ", merName="+merName+
        "merName="+merName+
        ", applyMoney="+applyMoney+
        "applyMoney="+applyMoney+
        ", accLmt="+accLmt+
        "accLmt="+accLmt+
        ", loanPercent="+loanPercent+
        "loanPercent="+loanPercent+
        ", loanInitTerm="+loanInitTerm+
        "loanInitTerm="+loanInitTerm+
        ", loanUse="+loanUse+
        "loanUse="+loanUse+
        ", guaranteeway="+guaranteeway+
        "guaranteeway="+guaranteeway+
        ", feeRate="+feeRate+
        "feeRate="+feeRate+
        ", feeAmt="+feeAmt+
        "feeAmt="+feeAmt+
        ", distributeMethod="+distributeMethod+
        "distributeMethod="+distributeMethod+
        ", loanFixedPmtPrin="+loanFixedPmtPrin+
        "loanFixedPmtPrin="+loanFixedPmtPrin+
        ", loanFirstTermPrin="+loanFirstTermPrin+
        "loanFirstTermPrin="+loanFirstTermPrin+
        ", loanFinalTermPrin="+loanFinalTermPrin+
        "loanFinalTermPrin="+loanFinalTermPrin+
        ", loanInitFee="+loanInitFee+
        "loanInitFee="+loanInitFee+
        ", loanFixedFee="+loanFixedFee+
        "loanFixedFee="+loanFixedFee+
        ", loanFirstTermFee="+loanFirstTermFee+
        "loanFirstTermFee="+loanFirstTermFee+
        ", loanFinalTermFee="+loanFinalTermFee+
        "loanFinalTermFee="+loanFinalTermFee+
        ", mlSendMode="+mlSendMode+
        "mlSendMode="+mlSendMode+
        ", lendingTime="+lendingTime+
        "lendingTime="+lendingTime+
        ", lendingTimes="+lendingTimes+
        "lendingTimes="+lendingTimes+
        ", mlBankName="+mlBankName+
        "mlBankName="+mlBankName+
        ", mlBankBranch="+mlBankBranch+
        "mlBankBranch="+mlBankBranch+
        ", mlBankAcctNo="+mlBankAcctNo+
        "mlBankAcctNo="+mlBankAcctNo+
        ", mlBankAcctName="+mlBankAcctName+
        "mlBankAcctName="+mlBankAcctName+
        ", repaymentway="+repaymentway+
        "repaymentway="+repaymentway+
        ", interval="+interval+
        "interval="+interval+
        ", reasonDesc="+reasonDesc+
        "reasonDesc="+reasonDesc+
        ", status="+status+
        "status="+status+
        ", statusDesc="+statusDesc+
        "statusDesc="+statusDesc+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", url="+url+
        "url="+url+
        ", loanType="+loanType+
        "loanType="+loanType+
        ", term="+term+
        "term="+term+
        ", rateType="+rateType+
        "rateType="+rateType+
        ", loanRate="+loanRate+
        "loanRate="+loanRate+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (mainLoanid == null) mainLoanid = "";
        if (productCd == null) productCd = "";
        if (businessMold == null) businessMold = "";
        if (businessType == null) businessType = "";
        if (merNo == null) merNo = "";
        if (merName == null) merName = "";
        if (applyMoney == null) applyMoney = BigDecimal.ZERO;
        if (accLmt == null) accLmt = BigDecimal.ZERO;
        if (loanPercent == null) loanPercent = "";
        if (loanInitTerm == null) loanInitTerm = 0;
        if (loanUse == null) loanUse = "";
        if (guaranteeway == null) guaranteeway = "";
        if (feeRate == null) feeRate = BigDecimal.ZERO;
        if (feeAmt == null) feeAmt = BigDecimal.ZERO;
        if (distributeMethod == null) distributeMethod = "";
        if (loanFixedPmtPrin == null) loanFixedPmtPrin = BigDecimal.ZERO;
        if (loanFirstTermPrin == null) loanFirstTermPrin = BigDecimal.ZERO;
        if (loanFinalTermPrin == null) loanFinalTermPrin = BigDecimal.ZERO;
        if (loanInitFee == null) loanInitFee = BigDecimal.ZERO;
        if (loanFixedFee == null) loanFixedFee = BigDecimal.ZERO;
        if (loanFirstTermFee == null) loanFirstTermFee = BigDecimal.ZERO;
        if (loanFinalTermFee == null) loanFinalTermFee = BigDecimal.ZERO;
        if (mlSendMode == null) mlSendMode = "";
        if (lendingTime == null) lendingTime = new Date();
        if (lendingTimes == null) lendingTimes = 0;
        if (mlBankName == null) mlBankName = "";
        if (mlBankBranch == null) mlBankBranch = "";
        if (mlBankAcctNo == null) mlBankAcctNo = "";
        if (mlBankAcctName == null) mlBankAcctName = "";
        if (repaymentway == null) repaymentway = "";
        if (interval == null) interval = "";
        if (reasonDesc == null) reasonDesc = "";
        if (status == null) status = "";
        if (statusDesc == null) statusDesc = "";
        if (createDate == null) createDate = new Date();
        if (updateUser == null) updateUser = "";
        if (createUser == null) createUser = "";
        if (updateDate == null) updateDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
        if (url == null) url = "";
        if (loanType == null) loanType = "";
        if (term == null) term = 0;
        if (rateType == null) rateType = "";
        if (loanRate == null) loanRate = BigDecimal.ZERO;
    }
}