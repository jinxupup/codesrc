package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 外部决策信息
 * @author jjb
 */
public class TmExtRiskInput implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String scene;

    private String extAppNo;

    private String extSugDecision;

    private String extEqBehAbn;

    private String extHighRiskBl;

    private String extContactAbn;

    private String extBl;

    private String extHighRisk;

    private String telOperatorsAbn;

    private String invalidApply;

    private String creditHighRisk;

    private String channel;

    private Integer extGrade;

    private String extRiskLevel;

    private BigDecimal extSugLmt;

    private Date createTime;

    private Date updateTime;

    private Integer inpretVal;

    private String inpretValRiskLvl;

    private BigDecimal priceAdvice;

    private String ruleRiskLvl;

    private String complexRiskLvl;

    private String custLvl;

    private String code;

    private String des;

    private String status;

    private Integer extMultiLoan1m;

    private Integer extBillCnt6m;

    private Integer extCarrierHitB16m;

    private String extAuditsRisk;

    private String extIflmk;

    private BigDecimal extLmkLmtTotal;

    private String extRefuseCode;

    private String extIsRefuse;

    private String extRiskType;

    private String buscore;

    private String finalTotScore;

    private String finalAuditresult;

    private String rejectReason;

    private String risklevel1;

    private String risklevel2;

    private String finalCreditlimt;

    private String finalOtherLimit;

    private String adjustindex;

    private String auditisriskJj;

    private String versionCode;

    private String brWorkplace;

    private String brEdulevel;

    private String brScore;

    private Integer jpaVersion;

    private String extCheckIdFlag;

    private String extCheckIdRs;

    private String deviceType;

    private String deviceGps;

    private String deviceGpsCn;

    private String weChatNickName;

    private String telOperatorsType;

    private String telBelong;

    /**
     * <p>ID</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>ID</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>机构号</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>机构号</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * <p>申请件编号</p>
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * <p>申请件编号</p>
     */
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    /**
     * <p>场景</p>
     */
    public String getScene() {
        return scene;
    }

    /**
     * <p>场景</p>
     */
    public void setScene(String scene) {
        this.scene = scene;
    }

    /**
     * <p>外部申请件编号</p>
     */
    public String getExtAppNo() {
        return extAppNo;
    }

    /**
     * <p>外部申请件编号</p>
     */
    public void setExtAppNo(String extAppNo) {
        this.extAppNo = extAppNo;
    }

    /**
     * <p>第三方建议决策</p>
     */
    public String getExtSugDecision() {
        return extSugDecision;
    }

    /**
     * <p>第三方建议决策</p>
     */
    public void setExtSugDecision(String extSugDecision) {
        this.extSugDecision = extSugDecision;
    }

    /**
     * <p>设备行为异常 </p>
     */
    public String getExtEqBehAbn() {
        return extEqBehAbn;
    }

    /**
     * <p>设备行为异常 </p>
     */
    public void setExtEqBehAbn(String extEqBehAbn) {
        this.extEqBehAbn = extEqBehAbn;
    }

    /**
     * <p>第三方内部高危黑名单</p>
     */
    public String getExtHighRiskBl() {
        return extHighRiskBl;
    }

    /**
     * <p>第三方内部高危黑名单</p>
     */
    public void setExtHighRiskBl(String extHighRiskBl) {
        this.extHighRiskBl = extHighRiskBl;
    }

    /**
     * <p>联系人异常</p>
     */
    public String getExtContactAbn() {
        return extContactAbn;
    }

    /**
     * <p>联系人异常</p>
     */
    public void setExtContactAbn(String extContactAbn) {
        this.extContactAbn = extContactAbn;
    }

    /**
     * <p>外部黑名单</p>
     */
    public String getExtBl() {
        return extBl;
    }

    /**
     * <p>外部黑名单</p>
     */
    public void setExtBl(String extBl) {
        this.extBl = extBl;
    }

    /**
     * <p>外部高风险</p>
     */
    public String getExtHighRisk() {
        return extHighRisk;
    }

    /**
     * <p>外部高风险</p>
     */
    public void setExtHighRisk(String extHighRisk) {
        this.extHighRisk = extHighRisk;
    }

    /**
     * <p>通讯录运营商异常</p>
     */
    public String getTelOperatorsAbn() {
        return telOperatorsAbn;
    }

    /**
     * <p>通讯录运营商异常</p>
     */
    public void setTelOperatorsAbn(String telOperatorsAbn) {
        this.telOperatorsAbn = telOperatorsAbn;
    }

    /**
     * <p>无效进件</p>
     */
    public String getInvalidApply() {
        return invalidApply;
    }

    /**
     * <p>无效进件</p>
     */
    public void setInvalidApply(String invalidApply) {
        this.invalidApply = invalidApply;
    }

    /**
     * <p>征信高风险</p>
     */
    public String getCreditHighRisk() {
        return creditHighRisk;
    }

    /**
     * <p>征信高风险</p>
     */
    public void setCreditHighRisk(String creditHighRisk) {
        this.creditHighRisk = creditHighRisk;
    }

    /**
     * <p>渠道标识</p>
     */
    public String getChannel() {
        return channel;
    }

    /**
     * <p>渠道标识</p>
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     * <p>第三方评分</p>
     */
    public Integer getExtGrade() {
        return extGrade;
    }

    /**
     * <p>第三方评分</p>
     */
    public void setExtGrade(Integer extGrade) {
        this.extGrade = extGrade;
    }

    /**
     * <p>第三方风险等级</p>
     */
    public String getExtRiskLevel() {
        return extRiskLevel;
    }

    /**
     * <p>第三方风险等级</p>
     */
    public void setExtRiskLevel(String extRiskLevel) {
        this.extRiskLevel = extRiskLevel;
    }

    /**
     * <p>第三方建议授信额度</p>
     */
    public BigDecimal getExtSugLmt() {
        return extSugLmt;
    }

    /**
     * <p>第三方建议授信额度</p>
     */
    public void setExtSugLmt(BigDecimal extSugLmt) {
        this.extSugLmt = extSugLmt;
    }

    /**
     * <p>创建时间</p>
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <p>创建时间</p>
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * <p>更新时间</p>
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * <p>更新时间</p>
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * <p>数字解读值</p>
     */
    public Integer getInpretVal() {
        return inpretVal;
    }

    /**
     * <p>数字解读值</p>
     */
    public void setInpretVal(Integer inpretVal) {
        this.inpretVal = inpretVal;
    }

    /**
     * <p>数字解读值风险等级</p>
     */
    public String getInpretValRiskLvl() {
        return inpretValRiskLvl;
    }

    /**
     * <p>数字解读值风险等级</p>
     */
    public void setInpretValRiskLvl(String inpretValRiskLvl) {
        this.inpretValRiskLvl = inpretValRiskLvl;
    }

    /**
     * <p>定价建议</p>
     */
    public BigDecimal getPriceAdvice() {
        return priceAdvice;
    }

    /**
     * <p>定价建议</p>
     */
    public void setPriceAdvice(BigDecimal priceAdvice) {
        this.priceAdvice = priceAdvice;
    }

    /**
     * <p>规则风险等级</p>
     */
    public String getRuleRiskLvl() {
        return ruleRiskLvl;
    }

    /**
     * <p>规则风险等级</p>
     */
    public void setRuleRiskLvl(String ruleRiskLvl) {
        this.ruleRiskLvl = ruleRiskLvl;
    }

    /**
     * <p>综合风险等级</p>
     */
    public String getComplexRiskLvl() {
        return complexRiskLvl;
    }

    /**
     * <p>综合风险等级</p>
     */
    public void setComplexRiskLvl(String complexRiskLvl) {
        this.complexRiskLvl = complexRiskLvl;
    }

    /**
     * <p>客户等级</p>
     */
    public String getCustLvl() {
        return custLvl;
    }

    /**
     * <p>客户等级</p>
     */
    public void setCustLvl(String custLvl) {
        this.custLvl = custLvl;
    }

    /**
     * <p>响应码</p>
     */
    public String getCode() {
        return code;
    }

    /**
     * <p>响应码</p>
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * <p>响应信息</p>
     */
    public String getDes() {
        return des;
    }

    /**
     * <p>响应信息</p>
     */
    public void setDes(String des) {
        this.des = des;
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
     * <p>1个月内多头借贷次数</p>
     */
    public Integer getExtMultiLoan1m() {
        return extMultiLoan1m;
    }

    /**
     * <p>1个月内多头借贷次数</p>
     */
    public void setExtMultiLoan1m(Integer extMultiLoan1m) {
        this.extMultiLoan1m = extMultiLoan1m;
    }

    /**
     * <p>6个月内信用卡账单月份数</p>
     */
    public Integer getExtBillCnt6m() {
        return extBillCnt6m;
    }

    /**
     * <p>6个月内信用卡账单月份数</p>
     */
    public void setExtBillCnt6m(Integer extBillCnt6m) {
        this.extBillCnt6m = extBillCnt6m;
    }

    /**
     * <p>6个月内电话催收库命中个数</p>
     */
    public Integer getExtCarrierHitB16m() {
        return extCarrierHitB16m;
    }

    /**
     * <p>6个月内电话催收库命中个数</p>
     */
    public void setExtCarrierHitB16m(Integer extCarrierHitB16m) {
        this.extCarrierHitB16m = extCarrierHitB16m;
    }

    /**
     * <p>是否触发电核规则</p>
     */
    public String getExtAuditsRisk() {
        return extAuditsRisk;
    }

    /**
     * <p>是否触发电核规则</p>
     */
    public void setExtAuditsRisk(String extAuditsRisk) {
        this.extAuditsRisk = extAuditsRisk;
    }

    /**
     * <p>是否存在联名卡</p>
     */
    public String getExtIflmk() {
        return extIflmk;
    }

    /**
     * <p>是否存在联名卡</p>
     */
    public void setExtIflmk(String extIflmk) {
        this.extIflmk = extIflmk;
    }

    /**
     * <p>已有联名卡总额度</p>
     */
    public BigDecimal getExtLmkLmtTotal() {
        return extLmkLmtTotal;
    }

    /**
     * <p>已有联名卡总额度</p>
     */
    public void setExtLmkLmtTotal(BigDecimal extLmkLmtTotal) {
        this.extLmkLmtTotal = extLmkLmtTotal;
    }

    /**
     * <p>拒绝原因</p>
     */
    public String getExtRefuseCode() {
        return extRefuseCode;
    }

    /**
     * <p>拒绝原因</p>
     */
    public void setExtRefuseCode(String extRefuseCode) {
        this.extRefuseCode = extRefuseCode;
    }

    /**
     * <p>是否拒绝</p>
     */
    public String getExtIsRefuse() {
        return extIsRefuse;
    }

    /**
     * <p>是否拒绝</p>
     */
    public void setExtIsRefuse(String extIsRefuse) {
        this.extIsRefuse = extIsRefuse;
    }

    /**
     * <p>风险策略类别</p>
     */
    public String getExtRiskType() {
        return extRiskType;
    }

    /**
     * <p>风险策略类别</p>
     */
    public void setExtRiskType(String extRiskType) {
        this.extRiskType = extRiskType;
    }

    /**
     * <p>百融征信评分</p>
     */
    public String getBuscore() {
        return buscore;
    }

    /**
     * <p>百融征信评分</p>
     */
    public void setBuscore(String buscore) {
        this.buscore = buscore;
    }

    /**
     * <p>百融最终评分</p>
     */
    public String getFinalTotScore() {
        return finalTotScore;
    }

    /**
     * <p>百融最终评分</p>
     */
    public void setFinalTotScore(String finalTotScore) {
        this.finalTotScore = finalTotScore;
    }

    /**
     * <p>百融决策结果</p>
     */
    public String getFinalAuditresult() {
        return finalAuditresult;
    }

    /**
     * <p>百融决策结果</p>
     */
    public void setFinalAuditresult(String finalAuditresult) {
        this.finalAuditresult = finalAuditresult;
    }

    /**
     * <p>百融拒绝原因</p>
     */
    public String getRejectReason() {
        return rejectReason;
    }

    /**
     * <p>百融拒绝原因</p>
     */
    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    /**
     * <p>百融风险等级1</p>
     */
    public String getRisklevel1() {
        return risklevel1;
    }

    /**
     * <p>百融风险等级1</p>
     */
    public void setRisklevel1(String risklevel1) {
        this.risklevel1 = risklevel1;
    }

    /**
     * <p>百融风险等级2</p>
     */
    public String getRisklevel2() {
        return risklevel2;
    }

    /**
     * <p>百融风险等级2</p>
     */
    public void setRisklevel2(String risklevel2) {
        this.risklevel2 = risklevel2;
    }

    /**
     * <p>百融申请人最终授信额度</p>
     */
    public String getFinalCreditlimt() {
        return finalCreditlimt;
    }

    /**
     * <p>百融申请人最终授信额度</p>
     */
    public void setFinalCreditlimt(String finalCreditlimt) {
        this.finalCreditlimt = finalCreditlimt;
    }

    /**
     * <p>百融申请人他行最高授信额度</p>
     */
    public String getFinalOtherLimit() {
        return finalOtherLimit;
    }

    /**
     * <p>百融申请人他行最高授信额度</p>
     */
    public void setFinalOtherLimit(String finalOtherLimit) {
        this.finalOtherLimit = finalOtherLimit;
    }

    /**
     * <p>百融申请人最终调整系数</p>
     */
    public String getAdjustindex() {
        return adjustindex;
    }

    /**
     * <p>百融申请人最终调整系数</p>
     */
    public void setAdjustindex(String adjustindex) {
        this.adjustindex = adjustindex;
    }

    /**
     * <p>百融是否触犯51电核规则</p>
     */
    public String getAuditisriskJj() {
        return auditisriskJj;
    }

    /**
     * <p>百融是否触犯51电核规则</p>
     */
    public void setAuditisriskJj(String auditisriskJj) {
        this.auditisriskJj = auditisriskJj;
    }

    /**
     * <p>百融输出结果版本号</p>
     */
    public String getVersionCode() {
        return versionCode;
    }

    /**
     * <p>百融输出结果版本号</p>
     */
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * <p>百融客户收入</p>
     */
    public String getBrWorkplace() {
        return brWorkplace;
    }

    /**
     * <p>百融客户收入</p>
     */
    public void setBrWorkplace(String brWorkplace) {
        this.brWorkplace = brWorkplace;
    }

    /**
     * <p>百融客户学历</p>
     */
    public String getBrEdulevel() {
        return brEdulevel;
    }

    /**
     * <p>百融客户学历</p>
     */
    public void setBrEdulevel(String brEdulevel) {
        this.brEdulevel = brEdulevel;
    }

    /**
     * <p>百融百融分</p>
     */
    public String getBrScore() {
        return brScore;
    }

    /**
     * <p>百融百融分</p>
     */
    public void setBrScore(String brScore) {
        this.brScore = brScore;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    /**
     * <p>渠道核身标志</p>
     */
    public String getExtCheckIdFlag() {
        return extCheckIdFlag;
    }

    /**
     * <p>渠道核身标志</p>
     */
    public void setExtCheckIdFlag(String extCheckIdFlag) {
        this.extCheckIdFlag = extCheckIdFlag;
    }

    /**
     * <p>渠道核身结果</p>
     */
    public String getExtCheckIdRs() {
        return extCheckIdRs;
    }

    /**
     * <p>渠道核身结果</p>
     */
    public void setExtCheckIdRs(String extCheckIdRs) {
        this.extCheckIdRs = extCheckIdRs;
    }

    /**
     * <p>终端设备类型</p>
     */
    public String getDeviceType() {
        return deviceType;
    }

    /**
     * <p>终端设备类型</p>
     */
    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    /**
     * <p>终端设备GPS</p>
     */
    public String getDeviceGps() {
        return deviceGps;
    }

    /**
     * <p>终端设备GPS</p>
     */
    public void setDeviceGps(String deviceGps) {
        this.deviceGps = deviceGps;
    }

    /**
     * <p>终端设备GPS地址中文</p>
     */
    public String getDeviceGpsCn() {
        return deviceGpsCn;
    }

    /**
     * <p>终端设备GPS地址中文</p>
     */
    public void setDeviceGpsCn(String deviceGpsCn) {
        this.deviceGpsCn = deviceGpsCn;
    }

    /**
     * <p>微信昵称</p>
     */
    public String getWeChatNickName() {
        return weChatNickName;
    }

    /**
     * <p>微信昵称</p>
     */
    public void setWeChatNickName(String weChatNickName) {
        this.weChatNickName = weChatNickName;
    }

    /**
     * <p>运行商类型</p>
     */
    public String getTelOperatorsType() {
        return telOperatorsType;
    }

    /**
     * <p>运行商类型</p>
     */
    public void setTelOperatorsType(String telOperatorsType) {
        this.telOperatorsType = telOperatorsType;
    }

    /**
     * <p>手机归属地</p>
     */
    public String getTelBelong() {
        return telBelong;
    }

    /**
     * <p>手机归属地</p>
     */
    public void setTelBelong(String telBelong) {
        this.telBelong = telBelong;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("scene", scene);
        map.put("extAppNo", extAppNo);
        map.put("extSugDecision", extSugDecision);
        map.put("extEqBehAbn", extEqBehAbn);
        map.put("extHighRiskBl", extHighRiskBl);
        map.put("extContactAbn", extContactAbn);
        map.put("extBl", extBl);
        map.put("extHighRisk", extHighRisk);
        map.put("telOperatorsAbn", telOperatorsAbn);
        map.put("invalidApply", invalidApply);
        map.put("creditHighRisk", creditHighRisk);
        map.put("channel", channel);
        map.put("extGrade", extGrade);
        map.put("extRiskLevel", extRiskLevel);
        map.put("extSugLmt", extSugLmt);
        map.put("createTime", createTime);
        map.put("updateTime", updateTime);
        map.put("inpretVal", inpretVal);
        map.put("inpretValRiskLvl", inpretValRiskLvl);
        map.put("priceAdvice", priceAdvice);
        map.put("ruleRiskLvl", ruleRiskLvl);
        map.put("complexRiskLvl", complexRiskLvl);
        map.put("custLvl", custLvl);
        map.put("code", code);
        map.put("des", des);
        map.put("status", status);
        map.put("extMultiLoan1m", extMultiLoan1m);
        map.put("extBillCnt6m", extBillCnt6m);
        map.put("extCarrierHitB16m", extCarrierHitB16m);
        map.put("extAuditsRisk", extAuditsRisk);
        map.put("extIflmk", extIflmk);
        map.put("extLmkLmtTotal", extLmkLmtTotal);
        map.put("extRefuseCode", extRefuseCode);
        map.put("extIsRefuse", extIsRefuse);
        map.put("extRiskType", extRiskType);
        map.put("buscore", buscore);
        map.put("finalTotScore", finalTotScore);
        map.put("finalAuditresult", finalAuditresult);
        map.put("rejectReason", rejectReason);
        map.put("risklevel1", risklevel1);
        map.put("risklevel2", risklevel2);
        map.put("finalCreditlimt", finalCreditlimt);
        map.put("finalOtherLimit", finalOtherLimit);
        map.put("adjustindex", adjustindex);
        map.put("auditisriskJj", auditisriskJj);
        map.put("versionCode", versionCode);
        map.put("brWorkplace", brWorkplace);
        map.put("brEdulevel", brEdulevel);
        map.put("brScore", brScore);
        map.put("jpaVersion", jpaVersion);
        map.put("extCheckIdFlag", extCheckIdFlag);
        map.put("extCheckIdRs", extCheckIdRs);
        map.put("deviceType", deviceType);
        map.put("deviceGps", deviceGps);
        map.put("deviceGpsCn", deviceGpsCn);
        map.put("weChatNickName", weChatNickName);
        map.put("telOperatorsType", telOperatorsType);
        map.put("telBelong", telBelong);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("scene")) this.setScene(DataTypeUtils.getStringValue(map.get("scene")));
        if (map.containsKey("extAppNo")) this.setExtAppNo(DataTypeUtils.getStringValue(map.get("extAppNo")));
        if (map.containsKey("extSugDecision")) this.setExtSugDecision(DataTypeUtils.getStringValue(map.get("extSugDecision")));
        if (map.containsKey("extEqBehAbn")) this.setExtEqBehAbn(DataTypeUtils.getStringValue(map.get("extEqBehAbn")));
        if (map.containsKey("extHighRiskBl")) this.setExtHighRiskBl(DataTypeUtils.getStringValue(map.get("extHighRiskBl")));
        if (map.containsKey("extContactAbn")) this.setExtContactAbn(DataTypeUtils.getStringValue(map.get("extContactAbn")));
        if (map.containsKey("extBl")) this.setExtBl(DataTypeUtils.getStringValue(map.get("extBl")));
        if (map.containsKey("extHighRisk")) this.setExtHighRisk(DataTypeUtils.getStringValue(map.get("extHighRisk")));
        if (map.containsKey("telOperatorsAbn")) this.setTelOperatorsAbn(DataTypeUtils.getStringValue(map.get("telOperatorsAbn")));
        if (map.containsKey("invalidApply")) this.setInvalidApply(DataTypeUtils.getStringValue(map.get("invalidApply")));
        if (map.containsKey("creditHighRisk")) this.setCreditHighRisk(DataTypeUtils.getStringValue(map.get("creditHighRisk")));
        if (map.containsKey("channel")) this.setChannel(DataTypeUtils.getStringValue(map.get("channel")));
        if (map.containsKey("extGrade")) this.setExtGrade(DataTypeUtils.getIntegerValue(map.get("extGrade")));
        if (map.containsKey("extRiskLevel")) this.setExtRiskLevel(DataTypeUtils.getStringValue(map.get("extRiskLevel")));
        if (map.containsKey("extSugLmt")) this.setExtSugLmt(DataTypeUtils.getBigDecimalValue(map.get("extSugLmt")));
        if (map.containsKey("createTime")) this.setCreateTime(DataTypeUtils.getDateValue(map.get("createTime")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
        if (map.containsKey("inpretVal")) this.setInpretVal(DataTypeUtils.getIntegerValue(map.get("inpretVal")));
        if (map.containsKey("inpretValRiskLvl")) this.setInpretValRiskLvl(DataTypeUtils.getStringValue(map.get("inpretValRiskLvl")));
        if (map.containsKey("priceAdvice")) this.setPriceAdvice(DataTypeUtils.getBigDecimalValue(map.get("priceAdvice")));
        if (map.containsKey("ruleRiskLvl")) this.setRuleRiskLvl(DataTypeUtils.getStringValue(map.get("ruleRiskLvl")));
        if (map.containsKey("complexRiskLvl")) this.setComplexRiskLvl(DataTypeUtils.getStringValue(map.get("complexRiskLvl")));
        if (map.containsKey("custLvl")) this.setCustLvl(DataTypeUtils.getStringValue(map.get("custLvl")));
        if (map.containsKey("code")) this.setCode(DataTypeUtils.getStringValue(map.get("code")));
        if (map.containsKey("des")) this.setDes(DataTypeUtils.getStringValue(map.get("des")));
        if (map.containsKey("status")) this.setStatus(DataTypeUtils.getStringValue(map.get("status")));
        if (map.containsKey("extMultiLoan1m")) this.setExtMultiLoan1m(DataTypeUtils.getIntegerValue(map.get("extMultiLoan1m")));
        if (map.containsKey("extBillCnt6m")) this.setExtBillCnt6m(DataTypeUtils.getIntegerValue(map.get("extBillCnt6m")));
        if (map.containsKey("extCarrierHitB16m")) this.setExtCarrierHitB16m(DataTypeUtils.getIntegerValue(map.get("extCarrierHitB16m")));
        if (map.containsKey("extAuditsRisk")) this.setExtAuditsRisk(DataTypeUtils.getStringValue(map.get("extAuditsRisk")));
        if (map.containsKey("extIflmk")) this.setExtIflmk(DataTypeUtils.getStringValue(map.get("extIflmk")));
        if (map.containsKey("extLmkLmtTotal")) this.setExtLmkLmtTotal(DataTypeUtils.getBigDecimalValue(map.get("extLmkLmtTotal")));
        if (map.containsKey("extRefuseCode")) this.setExtRefuseCode(DataTypeUtils.getStringValue(map.get("extRefuseCode")));
        if (map.containsKey("extIsRefuse")) this.setExtIsRefuse(DataTypeUtils.getStringValue(map.get("extIsRefuse")));
        if (map.containsKey("extRiskType")) this.setExtRiskType(DataTypeUtils.getStringValue(map.get("extRiskType")));
        if (map.containsKey("buscore")) this.setBuscore(DataTypeUtils.getStringValue(map.get("buscore")));
        if (map.containsKey("finalTotScore")) this.setFinalTotScore(DataTypeUtils.getStringValue(map.get("finalTotScore")));
        if (map.containsKey("finalAuditresult")) this.setFinalAuditresult(DataTypeUtils.getStringValue(map.get("finalAuditresult")));
        if (map.containsKey("rejectReason")) this.setRejectReason(DataTypeUtils.getStringValue(map.get("rejectReason")));
        if (map.containsKey("risklevel1")) this.setRisklevel1(DataTypeUtils.getStringValue(map.get("risklevel1")));
        if (map.containsKey("risklevel2")) this.setRisklevel2(DataTypeUtils.getStringValue(map.get("risklevel2")));
        if (map.containsKey("finalCreditlimt")) this.setFinalCreditlimt(DataTypeUtils.getStringValue(map.get("finalCreditlimt")));
        if (map.containsKey("finalOtherLimit")) this.setFinalOtherLimit(DataTypeUtils.getStringValue(map.get("finalOtherLimit")));
        if (map.containsKey("adjustindex")) this.setAdjustindex(DataTypeUtils.getStringValue(map.get("adjustindex")));
        if (map.containsKey("auditisriskJj")) this.setAuditisriskJj(DataTypeUtils.getStringValue(map.get("auditisriskJj")));
        if (map.containsKey("versionCode")) this.setVersionCode(DataTypeUtils.getStringValue(map.get("versionCode")));
        if (map.containsKey("brWorkplace")) this.setBrWorkplace(DataTypeUtils.getStringValue(map.get("brWorkplace")));
        if (map.containsKey("brEdulevel")) this.setBrEdulevel(DataTypeUtils.getStringValue(map.get("brEdulevel")));
        if (map.containsKey("brScore")) this.setBrScore(DataTypeUtils.getStringValue(map.get("brScore")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("extCheckIdFlag")) this.setExtCheckIdFlag(DataTypeUtils.getStringValue(map.get("extCheckIdFlag")));
        if (map.containsKey("extCheckIdRs")) this.setExtCheckIdRs(DataTypeUtils.getStringValue(map.get("extCheckIdRs")));
        if (map.containsKey("deviceType")) this.setDeviceType(DataTypeUtils.getStringValue(map.get("deviceType")));
        if (map.containsKey("deviceGps")) this.setDeviceGps(DataTypeUtils.getStringValue(map.get("deviceGps")));
        if (map.containsKey("deviceGpsCn")) this.setDeviceGpsCn(DataTypeUtils.getStringValue(map.get("deviceGpsCn")));
        if (map.containsKey("weChatNickName")) this.setWeChatNickName(DataTypeUtils.getStringValue(map.get("weChatNickName")));
        if (map.containsKey("telOperatorsType")) this.setTelOperatorsType(DataTypeUtils.getStringValue(map.get("telOperatorsType")));
        if (map.containsKey("telBelong")) this.setTelBelong(DataTypeUtils.getStringValue(map.get("telBelong")));
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
        ", scene="+scene+
        "scene="+scene+
        ", extAppNo="+extAppNo+
        "extAppNo="+extAppNo+
        ", extSugDecision="+extSugDecision+
        "extSugDecision="+extSugDecision+
        ", extEqBehAbn="+extEqBehAbn+
        "extEqBehAbn="+extEqBehAbn+
        ", extHighRiskBl="+extHighRiskBl+
        "extHighRiskBl="+extHighRiskBl+
        ", extContactAbn="+extContactAbn+
        "extContactAbn="+extContactAbn+
        ", extBl="+extBl+
        "extBl="+extBl+
        ", extHighRisk="+extHighRisk+
        "extHighRisk="+extHighRisk+
        ", telOperatorsAbn="+telOperatorsAbn+
        "telOperatorsAbn="+telOperatorsAbn+
        ", invalidApply="+invalidApply+
        "invalidApply="+invalidApply+
        ", creditHighRisk="+creditHighRisk+
        "creditHighRisk="+creditHighRisk+
        ", channel="+channel+
        "channel="+channel+
        ", extGrade="+extGrade+
        "extGrade="+extGrade+
        ", extRiskLevel="+extRiskLevel+
        "extRiskLevel="+extRiskLevel+
        ", extSugLmt="+extSugLmt+
        "extSugLmt="+extSugLmt+
        ", createTime="+createTime+
        "createTime="+createTime+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        ", inpretVal="+inpretVal+
        "inpretVal="+inpretVal+
        ", inpretValRiskLvl="+inpretValRiskLvl+
        "inpretValRiskLvl="+inpretValRiskLvl+
        ", priceAdvice="+priceAdvice+
        "priceAdvice="+priceAdvice+
        ", ruleRiskLvl="+ruleRiskLvl+
        "ruleRiskLvl="+ruleRiskLvl+
        ", complexRiskLvl="+complexRiskLvl+
        "complexRiskLvl="+complexRiskLvl+
        ", custLvl="+custLvl+
        "custLvl="+custLvl+
        ", code="+code+
        "code="+code+
        ", des="+des+
        "des="+des+
        ", status="+status+
        "status="+status+
        ", extMultiLoan1m="+extMultiLoan1m+
        "extMultiLoan1m="+extMultiLoan1m+
        ", extBillCnt6m="+extBillCnt6m+
        "extBillCnt6m="+extBillCnt6m+
        ", extCarrierHitB16m="+extCarrierHitB16m+
        "extCarrierHitB16m="+extCarrierHitB16m+
        ", extAuditsRisk="+extAuditsRisk+
        "extAuditsRisk="+extAuditsRisk+
        ", extIflmk="+extIflmk+
        "extIflmk="+extIflmk+
        ", extLmkLmtTotal="+extLmkLmtTotal+
        "extLmkLmtTotal="+extLmkLmtTotal+
        ", extRefuseCode="+extRefuseCode+
        "extRefuseCode="+extRefuseCode+
        ", extIsRefuse="+extIsRefuse+
        "extIsRefuse="+extIsRefuse+
        ", extRiskType="+extRiskType+
        "extRiskType="+extRiskType+
        ", buscore="+buscore+
        "buscore="+buscore+
        ", finalTotScore="+finalTotScore+
        "finalTotScore="+finalTotScore+
        ", finalAuditresult="+finalAuditresult+
        "finalAuditresult="+finalAuditresult+
        ", rejectReason="+rejectReason+
        "rejectReason="+rejectReason+
        ", risklevel1="+risklevel1+
        "risklevel1="+risklevel1+
        ", risklevel2="+risklevel2+
        "risklevel2="+risklevel2+
        ", finalCreditlimt="+finalCreditlimt+
        "finalCreditlimt="+finalCreditlimt+
        ", finalOtherLimit="+finalOtherLimit+
        "finalOtherLimit="+finalOtherLimit+
        ", adjustindex="+adjustindex+
        "adjustindex="+adjustindex+
        ", auditisriskJj="+auditisriskJj+
        "auditisriskJj="+auditisriskJj+
        ", versionCode="+versionCode+
        "versionCode="+versionCode+
        ", brWorkplace="+brWorkplace+
        "brWorkplace="+brWorkplace+
        ", brEdulevel="+brEdulevel+
        "brEdulevel="+brEdulevel+
        ", brScore="+brScore+
        "brScore="+brScore+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", extCheckIdFlag="+extCheckIdFlag+
        "extCheckIdFlag="+extCheckIdFlag+
        ", extCheckIdRs="+extCheckIdRs+
        "extCheckIdRs="+extCheckIdRs+
        ", deviceType="+deviceType+
        "deviceType="+deviceType+
        ", deviceGps="+deviceGps+
        "deviceGps="+deviceGps+
        ", deviceGpsCn="+deviceGpsCn+
        "deviceGpsCn="+deviceGpsCn+
        ", weChatNickName="+weChatNickName+
        "weChatNickName="+weChatNickName+
        ", telOperatorsType="+telOperatorsType+
        "telOperatorsType="+telOperatorsType+
        ", telBelong="+telBelong+
        "telBelong="+telBelong+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (scene == null) scene = "";
        if (extAppNo == null) extAppNo = "";
        if (extSugDecision == null) extSugDecision = "";
        if (extEqBehAbn == null) extEqBehAbn = "";
        if (extHighRiskBl == null) extHighRiskBl = "";
        if (extContactAbn == null) extContactAbn = "";
        if (extBl == null) extBl = "";
        if (extHighRisk == null) extHighRisk = "";
        if (telOperatorsAbn == null) telOperatorsAbn = "";
        if (invalidApply == null) invalidApply = "";
        if (creditHighRisk == null) creditHighRisk = "";
        if (channel == null) channel = "";
        if (extGrade == null) extGrade = 0;
        if (extRiskLevel == null) extRiskLevel = "";
        if (extSugLmt == null) extSugLmt = BigDecimal.ZERO;
        if (createTime == null) createTime = new Date();
        if (updateTime == null) updateTime = new Date();
        if (inpretVal == null) inpretVal = 0;
        if (inpretValRiskLvl == null) inpretValRiskLvl = "";
        if (priceAdvice == null) priceAdvice = BigDecimal.ZERO;
        if (ruleRiskLvl == null) ruleRiskLvl = "";
        if (complexRiskLvl == null) complexRiskLvl = "";
        if (custLvl == null) custLvl = "";
        if (code == null) code = "";
        if (des == null) des = "";
        if (status == null) status = "";
        if (extMultiLoan1m == null) extMultiLoan1m = 0;
        if (extBillCnt6m == null) extBillCnt6m = 0;
        if (extCarrierHitB16m == null) extCarrierHitB16m = 0;
        if (extAuditsRisk == null) extAuditsRisk = "";
        if (extIflmk == null) extIflmk = "";
        if (extLmkLmtTotal == null) extLmkLmtTotal = BigDecimal.ZERO;
        if (extRefuseCode == null) extRefuseCode = "";
        if (extIsRefuse == null) extIsRefuse = "";
        if (extRiskType == null) extRiskType = "";
        if (buscore == null) buscore = "";
        if (finalTotScore == null) finalTotScore = "";
        if (finalAuditresult == null) finalAuditresult = "";
        if (rejectReason == null) rejectReason = "";
        if (risklevel1 == null) risklevel1 = "";
        if (risklevel2 == null) risklevel2 = "";
        if (finalCreditlimt == null) finalCreditlimt = "";
        if (finalOtherLimit == null) finalOtherLimit = "";
        if (adjustindex == null) adjustindex = "";
        if (auditisriskJj == null) auditisriskJj = "";
        if (versionCode == null) versionCode = "";
        if (brWorkplace == null) brWorkplace = "";
        if (brEdulevel == null) brEdulevel = "";
        if (brScore == null) brScore = "";
        if (jpaVersion == null) jpaVersion = 0;
        if (extCheckIdFlag == null) extCheckIdFlag = "";
        if (extCheckIdRs == null) extCheckIdRs = "";
        if (deviceType == null) deviceType = "";
        if (deviceGps == null) deviceGps = "";
        if (deviceGpsCn == null) deviceGpsCn = "";
        if (weChatNickName == null) weChatNickName = "";
        if (telOperatorsType == null) telOperatorsType = "";
        if (telBelong == null) telBelong = "";
    }
}