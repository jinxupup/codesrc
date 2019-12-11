package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 分期贷款表
 * @author jjb
 */
public class TmAppInstalLoan implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private BigDecimal custSpecialQuota;

    private String mccNo;

    private BigDecimal cashAmt;

    private Integer loanInitTerm;

    private String instalRpoductNo;

    private String loanFeeMethod;

    private String loanFeeCalcMethod;

    private BigDecimal appFeeRate;

    private BigDecimal appFeeAmt;

    private String loanUse;

    private String instalActivityNo;

    private Date lendingTime;

    private Integer lendingTimes;

    private String cardNo;

    private String currCd;

    private String status;

    private String statusDesc;

    private String remark;

    private String marketerName;

    private String marketerId;

    private String marketerBranch;

    private String loanPrinApp;

    private String guaranteeway;

    private Integer loanRegId;

    private String loanRegStatus;

    private String reasonDesc;

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

    private String mlBankName;

    private String mlBankBranch;

    private String mlBankAcctNo;

    private String mlBankAcctName;

    private String ifExpired;

    private String merName;

    private Date activeDate;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private Integer jpaVersion;

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
     * <p>客户级专项分期额度</p>
     */
    public BigDecimal getCustSpecialQuota() {
        return custSpecialQuota;
    }

    /**
     * <p>客户级专项分期额度</p>
     */
    public void setCustSpecialQuota(BigDecimal custSpecialQuota) {
        this.custSpecialQuota = custSpecialQuota;
    }

    /**
     * <p>分期商户编号</p>
     */
    public String getMccNo() {
        return mccNo;
    }

    /**
     * <p>分期商户编号</p>
     */
    public void setMccNo(String mccNo) {
        this.mccNo = mccNo;
    }

    /**
     * <p>分期借款金额</p>
     */
    public BigDecimal getCashAmt() {
        return cashAmt;
    }

    /**
     * <p>分期借款金额</p>
     */
    public void setCashAmt(BigDecimal cashAmt) {
        this.cashAmt = cashAmt;
    }

    /**
     * <p>分期总期数</p>
     */
    public Integer getLoanInitTerm() {
        return loanInitTerm;
    }

    /**
     * <p>分期总期数</p>
     */
    public void setLoanInitTerm(Integer loanInitTerm) {
        this.loanInitTerm = loanInitTerm;
    }

    /**
     * <p>分期产品编号</p>
     */
    public String getInstalRpoductNo() {
        return instalRpoductNo;
    }

    /**
     * <p>分期产品编号</p>
     */
    public void setInstalRpoductNo(String instalRpoductNo) {
        this.instalRpoductNo = instalRpoductNo;
    }

    /**
     * <p>分期手续费收取方式</p>
     */
    public String getLoanFeeMethod() {
        return loanFeeMethod;
    }

    /**
     * <p>分期手续费收取方式</p>
     */
    public void setLoanFeeMethod(String loanFeeMethod) {
        this.loanFeeMethod = loanFeeMethod;
    }

    /**
     * <p>大额分期手续费计算方式</p>
     * <p>R：附加金额按比例计算</p>
     * <p>A：附加金额为固定金额</p>
     */
    public String getLoanFeeCalcMethod() {
        return loanFeeCalcMethod;
    }

    /**
     * <p>大额分期手续费计算方式</p>
     * <p>R：附加金额按比例计算</p>
     * <p>A：附加金额为固定金额</p>
     */
    public void setLoanFeeCalcMethod(String loanFeeCalcMethod) {
        this.loanFeeCalcMethod = loanFeeCalcMethod;
    }

    /**
     * <p>申请分期手续费费率</p>
     */
    public BigDecimal getAppFeeRate() {
        return appFeeRate;
    }

    /**
     * <p>申请分期手续费费率</p>
     */
    public void setAppFeeRate(BigDecimal appFeeRate) {
        this.appFeeRate = appFeeRate;
    }

    /**
     * <p>申请分期手续费固定金额</p>
     */
    public BigDecimal getAppFeeAmt() {
        return appFeeAmt;
    }

    /**
     * <p>申请分期手续费固定金额</p>
     */
    public void setAppFeeAmt(BigDecimal appFeeAmt) {
        this.appFeeAmt = appFeeAmt;
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
     * <p>分期活动编号</p>
     */
    public String getInstalActivityNo() {
        return instalActivityNo;
    }

    /**
     * <p>分期活动编号</p>
     */
    public void setInstalActivityNo(String instalActivityNo) {
        this.instalActivityNo = instalActivityNo;
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
     * <p>卡号</p>
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * <p>卡号</p>
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * <p>币种</p>
     */
    public String getCurrCd() {
        return currCd;
    }

    /**
     * <p>币种</p>
     */
    public void setCurrCd(String currCd) {
        this.currCd = currCd;
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
     * <p>备注</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>备注</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <p>营销人员姓名</p>
     */
    public String getMarketerName() {
        return marketerName;
    }

    /**
     * <p>营销人员姓名</p>
     */
    public void setMarketerName(String marketerName) {
        this.marketerName = marketerName;
    }

    /**
     * <p>营销人员编号</p>
     */
    public String getMarketerId() {
        return marketerId;
    }

    /**
     * <p>营销人员编号</p>
     */
    public void setMarketerId(String marketerId) {
        this.marketerId = marketerId;
    }

    /**
     * <p>营销人员所属分行</p>
     */
    public String getMarketerBranch() {
        return marketerBranch;
    }

    /**
     * <p>营销人员所属分行</p>
     */
    public void setMarketerBranch(String marketerBranch) {
        this.marketerBranch = marketerBranch;
    }

    /**
     * <p>分期款项用途</p>
     * <p>款项用途的编号，最长8位</p>
     */
    public String getLoanPrinApp() {
        return loanPrinApp;
    }

    /**
     * <p>分期款项用途</p>
     * <p>款项用途的编号，最长8位</p>
     */
    public void setLoanPrinApp(String loanPrinApp) {
        this.loanPrinApp = loanPrinApp;
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
     * <p>分期申请顺序号</p>
     */
    public Integer getLoanRegId() {
        return loanRegId;
    }

    /**
     * <p>分期申请顺序号</p>
     */
    public void setLoanRegId(Integer loanRegId) {
        this.loanRegId = loanRegId;
    }

    /**
     * <p>贷款注册状态</p>
     */
    public String getLoanRegStatus() {
        return loanRegStatus;
    }

    /**
     * <p>贷款注册状态</p>
     */
    public void setLoanRegStatus(String loanRegStatus) {
        this.loanRegStatus = loanRegStatus;
    }

    /**
     * <p>分期失败原因</p>
     */
    public String getReasonDesc() {
        return reasonDesc;
    }

    /**
     * <p>分期失败原因</p>
     */
    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
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
     * <p>是否已过期</p>
     */
    public String getIfExpired() {
        return ifExpired;
    }

    /**
     * <p>是否已过期</p>
     */
    public void setIfExpired(String ifExpired) {
        this.ifExpired = ifExpired;
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
     * <p>分期激活日期</p>
     */
    public Date getActiveDate() {
        return activeDate;
    }

    /**
     * <p>分期激活日期</p>
     */
    public void setActiveDate(Date activeDate) {
        this.activeDate = activeDate;
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

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("custSpecialQuota", custSpecialQuota);
        map.put("mccNo", mccNo);
        map.put("cashAmt", cashAmt);
        map.put("loanInitTerm", loanInitTerm);
        map.put("instalRpoductNo", instalRpoductNo);
        map.put("loanFeeMethod", loanFeeMethod);
        map.put("loanFeeCalcMethod", loanFeeCalcMethod);
        map.put("appFeeRate", appFeeRate);
        map.put("appFeeAmt", appFeeAmt);
        map.put("loanUse", loanUse);
        map.put("instalActivityNo", instalActivityNo);
        map.put("lendingTime", lendingTime);
        map.put("lendingTimes", lendingTimes);
        map.put("cardNo", cardNo);
        map.put("currCd", currCd);
        map.put("status", status);
        map.put("statusDesc", statusDesc);
        map.put("remark", remark);
        map.put("marketerName", marketerName);
        map.put("marketerId", marketerId);
        map.put("marketerBranch", marketerBranch);
        map.put("loanPrinApp", loanPrinApp);
        map.put("guaranteeway", guaranteeway);
        map.put("loanRegId", loanRegId);
        map.put("loanRegStatus", loanRegStatus);
        map.put("reasonDesc", reasonDesc);
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
        map.put("mlBankName", mlBankName);
        map.put("mlBankBranch", mlBankBranch);
        map.put("mlBankAcctNo", mlBankAcctNo);
        map.put("mlBankAcctName", mlBankAcctName);
        map.put("ifExpired", ifExpired);
        map.put("merName", merName);
        map.put("activeDate", activeDate);
        map.put("createDate", createDate);
        map.put("createUser", createUser);
        map.put("updateDate", updateDate);
        map.put("updateUser", updateUser);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("custSpecialQuota")) this.setCustSpecialQuota(DataTypeUtils.getBigDecimalValue(map.get("custSpecialQuota")));
        if (map.containsKey("mccNo")) this.setMccNo(DataTypeUtils.getStringValue(map.get("mccNo")));
        if (map.containsKey("cashAmt")) this.setCashAmt(DataTypeUtils.getBigDecimalValue(map.get("cashAmt")));
        if (map.containsKey("loanInitTerm")) this.setLoanInitTerm(DataTypeUtils.getIntegerValue(map.get("loanInitTerm")));
        if (map.containsKey("instalRpoductNo")) this.setInstalRpoductNo(DataTypeUtils.getStringValue(map.get("instalRpoductNo")));
        if (map.containsKey("loanFeeMethod")) this.setLoanFeeMethod(DataTypeUtils.getStringValue(map.get("loanFeeMethod")));
        if (map.containsKey("loanFeeCalcMethod")) this.setLoanFeeCalcMethod(DataTypeUtils.getStringValue(map.get("loanFeeCalcMethod")));
        if (map.containsKey("appFeeRate")) this.setAppFeeRate(DataTypeUtils.getBigDecimalValue(map.get("appFeeRate")));
        if (map.containsKey("appFeeAmt")) this.setAppFeeAmt(DataTypeUtils.getBigDecimalValue(map.get("appFeeAmt")));
        if (map.containsKey("loanUse")) this.setLoanUse(DataTypeUtils.getStringValue(map.get("loanUse")));
        if (map.containsKey("instalActivityNo")) this.setInstalActivityNo(DataTypeUtils.getStringValue(map.get("instalActivityNo")));
        if (map.containsKey("lendingTime")) this.setLendingTime(DataTypeUtils.getDateValue(map.get("lendingTime")));
        if (map.containsKey("lendingTimes")) this.setLendingTimes(DataTypeUtils.getIntegerValue(map.get("lendingTimes")));
        if (map.containsKey("cardNo")) this.setCardNo(DataTypeUtils.getStringValue(map.get("cardNo")));
        if (map.containsKey("currCd")) this.setCurrCd(DataTypeUtils.getStringValue(map.get("currCd")));
        if (map.containsKey("status")) this.setStatus(DataTypeUtils.getStringValue(map.get("status")));
        if (map.containsKey("statusDesc")) this.setStatusDesc(DataTypeUtils.getStringValue(map.get("statusDesc")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("marketerName")) this.setMarketerName(DataTypeUtils.getStringValue(map.get("marketerName")));
        if (map.containsKey("marketerId")) this.setMarketerId(DataTypeUtils.getStringValue(map.get("marketerId")));
        if (map.containsKey("marketerBranch")) this.setMarketerBranch(DataTypeUtils.getStringValue(map.get("marketerBranch")));
        if (map.containsKey("loanPrinApp")) this.setLoanPrinApp(DataTypeUtils.getStringValue(map.get("loanPrinApp")));
        if (map.containsKey("guaranteeway")) this.setGuaranteeway(DataTypeUtils.getStringValue(map.get("guaranteeway")));
        if (map.containsKey("loanRegId")) this.setLoanRegId(DataTypeUtils.getIntegerValue(map.get("loanRegId")));
        if (map.containsKey("loanRegStatus")) this.setLoanRegStatus(DataTypeUtils.getStringValue(map.get("loanRegStatus")));
        if (map.containsKey("reasonDesc")) this.setReasonDesc(DataTypeUtils.getStringValue(map.get("reasonDesc")));
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
        if (map.containsKey("mlBankName")) this.setMlBankName(DataTypeUtils.getStringValue(map.get("mlBankName")));
        if (map.containsKey("mlBankBranch")) this.setMlBankBranch(DataTypeUtils.getStringValue(map.get("mlBankBranch")));
        if (map.containsKey("mlBankAcctNo")) this.setMlBankAcctNo(DataTypeUtils.getStringValue(map.get("mlBankAcctNo")));
        if (map.containsKey("mlBankAcctName")) this.setMlBankAcctName(DataTypeUtils.getStringValue(map.get("mlBankAcctName")));
        if (map.containsKey("ifExpired")) this.setIfExpired(DataTypeUtils.getStringValue(map.get("ifExpired")));
        if (map.containsKey("merName")) this.setMerName(DataTypeUtils.getStringValue(map.get("merName")));
        if (map.containsKey("activeDate")) this.setActiveDate(DataTypeUtils.getDateValue(map.get("activeDate")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
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
        ", custSpecialQuota="+custSpecialQuota+
        "custSpecialQuota="+custSpecialQuota+
        ", mccNo="+mccNo+
        "mccNo="+mccNo+
        ", cashAmt="+cashAmt+
        "cashAmt="+cashAmt+
        ", loanInitTerm="+loanInitTerm+
        "loanInitTerm="+loanInitTerm+
        ", instalRpoductNo="+instalRpoductNo+
        "instalRpoductNo="+instalRpoductNo+
        ", loanFeeMethod="+loanFeeMethod+
        "loanFeeMethod="+loanFeeMethod+
        ", loanFeeCalcMethod="+loanFeeCalcMethod+
        "loanFeeCalcMethod="+loanFeeCalcMethod+
        ", appFeeRate="+appFeeRate+
        "appFeeRate="+appFeeRate+
        ", appFeeAmt="+appFeeAmt+
        "appFeeAmt="+appFeeAmt+
        ", loanUse="+loanUse+
        "loanUse="+loanUse+
        ", instalActivityNo="+instalActivityNo+
        "instalActivityNo="+instalActivityNo+
        ", lendingTime="+lendingTime+
        "lendingTime="+lendingTime+
        ", lendingTimes="+lendingTimes+
        "lendingTimes="+lendingTimes+
        ", cardNo="+cardNo+
        "cardNo="+cardNo+
        ", currCd="+currCd+
        "currCd="+currCd+
        ", status="+status+
        "status="+status+
        ", statusDesc="+statusDesc+
        "statusDesc="+statusDesc+
        ", remark="+remark+
        "remark="+remark+
        ", marketerName="+marketerName+
        "marketerName="+marketerName+
        ", marketerId="+marketerId+
        "marketerId="+marketerId+
        ", marketerBranch="+marketerBranch+
        "marketerBranch="+marketerBranch+
        ", loanPrinApp="+loanPrinApp+
        "loanPrinApp="+loanPrinApp+
        ", guaranteeway="+guaranteeway+
        "guaranteeway="+guaranteeway+
        ", loanRegId="+loanRegId+
        "loanRegId="+loanRegId+
        ", loanRegStatus="+loanRegStatus+
        "loanRegStatus="+loanRegStatus+
        ", reasonDesc="+reasonDesc+
        "reasonDesc="+reasonDesc+
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
        ", mlBankName="+mlBankName+
        "mlBankName="+mlBankName+
        ", mlBankBranch="+mlBankBranch+
        "mlBankBranch="+mlBankBranch+
        ", mlBankAcctNo="+mlBankAcctNo+
        "mlBankAcctNo="+mlBankAcctNo+
        ", mlBankAcctName="+mlBankAcctName+
        "mlBankAcctName="+mlBankAcctName+
        ", ifExpired="+ifExpired+
        "ifExpired="+ifExpired+
        ", merName="+merName+
        "merName="+merName+
        ", activeDate="+activeDate+
        "activeDate="+activeDate+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (custSpecialQuota == null) custSpecialQuota = BigDecimal.ZERO;
        if (mccNo == null) mccNo = "";
        if (cashAmt == null) cashAmt = BigDecimal.ZERO;
        if (loanInitTerm == null) loanInitTerm = 0;
        if (instalRpoductNo == null) instalRpoductNo = "";
        if (loanFeeMethod == null) loanFeeMethod = "";
        if (loanFeeCalcMethod == null) loanFeeCalcMethod = "";
        if (appFeeRate == null) appFeeRate = BigDecimal.ZERO;
        if (appFeeAmt == null) appFeeAmt = BigDecimal.ZERO;
        if (loanUse == null) loanUse = "";
        if (instalActivityNo == null) instalActivityNo = "";
        if (lendingTime == null) lendingTime = new Date();
        if (lendingTimes == null) lendingTimes = 0;
        if (cardNo == null) cardNo = "";
        if (currCd == null) currCd = "";
        if (status == null) status = "";
        if (statusDesc == null) statusDesc = "";
        if (remark == null) remark = "";
        if (marketerName == null) marketerName = "";
        if (marketerId == null) marketerId = "";
        if (marketerBranch == null) marketerBranch = "";
        if (loanPrinApp == null) loanPrinApp = "";
        if (guaranteeway == null) guaranteeway = "";
        if (loanRegId == null) loanRegId = 0;
        if (loanRegStatus == null) loanRegStatus = "";
        if (reasonDesc == null) reasonDesc = "";
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
        if (mlBankName == null) mlBankName = "";
        if (mlBankBranch == null) mlBankBranch = "";
        if (mlBankAcctNo == null) mlBankAcctNo = "";
        if (mlBankAcctName == null) mlBankAcctName = "";
        if (ifExpired == null) ifExpired = "";
        if (merName == null) merName = "";
        if (activeDate == null) activeDate = new Date();
        if (createDate == null) createDate = new Date();
        if (createUser == null) createUser = "";
        if (updateDate == null) updateDate = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}