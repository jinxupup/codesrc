package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请进件审计记录
 * @author jjb
 */
public class TmAppAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String cfRiskClassic;

    private String isInstalment;

    private String isRealtimeIssuing;

    private String isOldCust;

    private String isFreeTelCheck;

    private String isSendSmsRefused;

    private String isSendSmsPatch;

    private String isReturned;

    private String isHaveRetrial;

    private String appNoHis;

    private String pcResult;

    private String basicResult;

    private String basicRefuseCode;

    private String telResult;

    private String telRefuseCode;

    private String patchResult;

    private String finalResult;

    private String finalRefuseCode;

    private Date updateDate;

    private String updateUser;

    private Integer jpaVersion;

    private String isRetrialApp;

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
     * <p>确认风险等级</p>
     */
    public String getCfRiskClassic() {
        return cfRiskClassic;
    }

    /**
     * <p>确认风险等级</p>
     */
    public void setCfRiskClassic(String cfRiskClassic) {
        this.cfRiskClassic = cfRiskClassic;
    }

    /**
     * <p>是否分期</p>
     */
    public String getIsInstalment() {
        return isInstalment;
    }

    /**
     * <p>是否分期</p>
     */
    public void setIsInstalment(String isInstalment) {
        this.isInstalment = isInstalment;
    }

    /**
     * <p>是否实时发卡</p>
     */
    public String getIsRealtimeIssuing() {
        return isRealtimeIssuing;
    }

    /**
     * <p>是否实时发卡</p>
     */
    public void setIsRealtimeIssuing(String isRealtimeIssuing) {
        this.isRealtimeIssuing = isRealtimeIssuing;
    }

    /**
     * <p>是否老客户</p>
     */
    public String getIsOldCust() {
        return isOldCust;
    }

    /**
     * <p>是否老客户</p>
     */
    public void setIsOldCust(String isOldCust) {
        this.isOldCust = isOldCust;
    }

    /**
     * <p>是否免电调</p>
     */
    public String getIsFreeTelCheck() {
        return isFreeTelCheck;
    }

    /**
     * <p>是否免电调</p>
     */
    public void setIsFreeTelCheck(String isFreeTelCheck) {
        this.isFreeTelCheck = isFreeTelCheck;
    }

    /**
     * <p>是否发送拒绝短信</p>
     */
    public String getIsSendSmsRefused() {
        return isSendSmsRefused;
    }

    /**
     * <p>是否发送拒绝短信</p>
     */
    public void setIsSendSmsRefused(String isSendSmsRefused) {
        this.isSendSmsRefused = isSendSmsRefused;
    }

    /**
     * <p>是否发送补件短信</p>
     */
    public String getIsSendSmsPatch() {
        return isSendSmsPatch;
    }

    /**
     * <p>是否发送补件短信</p>
     */
    public void setIsSendSmsPatch(String isSendSmsPatch) {
        this.isSendSmsPatch = isSendSmsPatch;
    }

    /**
     * <p>是否退回件</p>
     */
    public String getIsReturned() {
        return isReturned;
    }

    /**
     * <p>是否退回件</p>
     */
    public void setIsReturned(String isReturned) {
        this.isReturned = isReturned;
    }

    /**
     * <p>是否已重审</p>
     */
    public String getIsHaveRetrial() {
        return isHaveRetrial;
    }

    /**
     * <p>是否已重审</p>
     */
    public void setIsHaveRetrial(String isHaveRetrial) {
        this.isHaveRetrial = isHaveRetrial;
    }

    /**
     * <p>历史申请编号</p>
     */
    public String getAppNoHis() {
        return appNoHis;
    }

    /**
     * <p>历史申请编号</p>
     */
    public void setAppNoHis(String appNoHis) {
        this.appNoHis = appNoHis;
    }

    /**
     * <p>核查结果</p>
     */
    public String getPcResult() {
        return pcResult;
    }

    /**
     * <p>核查结果</p>
     */
    public void setPcResult(String pcResult) {
        this.pcResult = pcResult;
    }

    /**
     * <p>初审结果</p>
     */
    public String getBasicResult() {
        return basicResult;
    }

    /**
     * <p>初审结果</p>
     */
    public void setBasicResult(String basicResult) {
        this.basicResult = basicResult;
    }

    /**
     * <p>初审拒绝原因</p>
     */
    public String getBasicRefuseCode() {
        return basicRefuseCode;
    }

    /**
     * <p>初审拒绝原因</p>
     */
    public void setBasicRefuseCode(String basicRefuseCode) {
        this.basicRefuseCode = basicRefuseCode;
    }

    /**
     * <p>电调结果</p>
     */
    public String getTelResult() {
        return telResult;
    }

    /**
     * <p>电调结果</p>
     */
    public void setTelResult(String telResult) {
        this.telResult = telResult;
    }

    /**
     * <p>电调拒绝原因</p>
     */
    public String getTelRefuseCode() {
        return telRefuseCode;
    }

    /**
     * <p>电调拒绝原因</p>
     */
    public void setTelRefuseCode(String telRefuseCode) {
        this.telRefuseCode = telRefuseCode;
    }

    /**
     * <p>补件结果</p>
     */
    public String getPatchResult() {
        return patchResult;
    }

    /**
     * <p>补件结果</p>
     */
    public void setPatchResult(String patchResult) {
        this.patchResult = patchResult;
    }

    /**
     * <p>终审结果</p>
     */
    public String getFinalResult() {
        return finalResult;
    }

    /**
     * <p>终审结果</p>
     */
    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    /**
     * <p>终审拒绝原因</p>
     */
    public String getFinalRefuseCode() {
        return finalRefuseCode;
    }

    /**
     * <p>终审拒绝原因</p>
     */
    public void setFinalRefuseCode(String finalRefuseCode) {
        this.finalRefuseCode = finalRefuseCode;
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
     * <p>更新用户</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>更新用户</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
     * <p>是否是重审(新)件</p>
     */
    public String getIsRetrialApp() {
        return isRetrialApp;
    }

    /**
     * <p>是否是重审(新)件</p>
     */
    public void setIsRetrialApp(String isRetrialApp) {
        this.isRetrialApp = isRetrialApp;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("cfRiskClassic", cfRiskClassic);
        map.put("isInstalment", isInstalment);
        map.put("isRealtimeIssuing", isRealtimeIssuing);
        map.put("isOldCust", isOldCust);
        map.put("isFreeTelCheck", isFreeTelCheck);
        map.put("isSendSmsRefused", isSendSmsRefused);
        map.put("isSendSmsPatch", isSendSmsPatch);
        map.put("isReturned", isReturned);
        map.put("isHaveRetrial", isHaveRetrial);
        map.put("appNoHis", appNoHis);
        map.put("pcResult", pcResult);
        map.put("basicResult", basicResult);
        map.put("basicRefuseCode", basicRefuseCode);
        map.put("telResult", telResult);
        map.put("telRefuseCode", telRefuseCode);
        map.put("patchResult", patchResult);
        map.put("finalResult", finalResult);
        map.put("finalRefuseCode", finalRefuseCode);
        map.put("updateDate", updateDate);
        map.put("updateUser", updateUser);
        map.put("jpaVersion", jpaVersion);
        map.put("isRetrialApp", isRetrialApp);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("cfRiskClassic")) this.setCfRiskClassic(DataTypeUtils.getStringValue(map.get("cfRiskClassic")));
        if (map.containsKey("isInstalment")) this.setIsInstalment(DataTypeUtils.getStringValue(map.get("isInstalment")));
        if (map.containsKey("isRealtimeIssuing")) this.setIsRealtimeIssuing(DataTypeUtils.getStringValue(map.get("isRealtimeIssuing")));
        if (map.containsKey("isOldCust")) this.setIsOldCust(DataTypeUtils.getStringValue(map.get("isOldCust")));
        if (map.containsKey("isFreeTelCheck")) this.setIsFreeTelCheck(DataTypeUtils.getStringValue(map.get("isFreeTelCheck")));
        if (map.containsKey("isSendSmsRefused")) this.setIsSendSmsRefused(DataTypeUtils.getStringValue(map.get("isSendSmsRefused")));
        if (map.containsKey("isSendSmsPatch")) this.setIsSendSmsPatch(DataTypeUtils.getStringValue(map.get("isSendSmsPatch")));
        if (map.containsKey("isReturned")) this.setIsReturned(DataTypeUtils.getStringValue(map.get("isReturned")));
        if (map.containsKey("isHaveRetrial")) this.setIsHaveRetrial(DataTypeUtils.getStringValue(map.get("isHaveRetrial")));
        if (map.containsKey("appNoHis")) this.setAppNoHis(DataTypeUtils.getStringValue(map.get("appNoHis")));
        if (map.containsKey("pcResult")) this.setPcResult(DataTypeUtils.getStringValue(map.get("pcResult")));
        if (map.containsKey("basicResult")) this.setBasicResult(DataTypeUtils.getStringValue(map.get("basicResult")));
        if (map.containsKey("basicRefuseCode")) this.setBasicRefuseCode(DataTypeUtils.getStringValue(map.get("basicRefuseCode")));
        if (map.containsKey("telResult")) this.setTelResult(DataTypeUtils.getStringValue(map.get("telResult")));
        if (map.containsKey("telRefuseCode")) this.setTelRefuseCode(DataTypeUtils.getStringValue(map.get("telRefuseCode")));
        if (map.containsKey("patchResult")) this.setPatchResult(DataTypeUtils.getStringValue(map.get("patchResult")));
        if (map.containsKey("finalResult")) this.setFinalResult(DataTypeUtils.getStringValue(map.get("finalResult")));
        if (map.containsKey("finalRefuseCode")) this.setFinalRefuseCode(DataTypeUtils.getStringValue(map.get("finalRefuseCode")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("isRetrialApp")) this.setIsRetrialApp(DataTypeUtils.getStringValue(map.get("isRetrialApp")));
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
        ", cfRiskClassic="+cfRiskClassic+
        "cfRiskClassic="+cfRiskClassic+
        ", isInstalment="+isInstalment+
        "isInstalment="+isInstalment+
        ", isRealtimeIssuing="+isRealtimeIssuing+
        "isRealtimeIssuing="+isRealtimeIssuing+
        ", isOldCust="+isOldCust+
        "isOldCust="+isOldCust+
        ", isFreeTelCheck="+isFreeTelCheck+
        "isFreeTelCheck="+isFreeTelCheck+
        ", isSendSmsRefused="+isSendSmsRefused+
        "isSendSmsRefused="+isSendSmsRefused+
        ", isSendSmsPatch="+isSendSmsPatch+
        "isSendSmsPatch="+isSendSmsPatch+
        ", isReturned="+isReturned+
        "isReturned="+isReturned+
        ", isHaveRetrial="+isHaveRetrial+
        "isHaveRetrial="+isHaveRetrial+
        ", appNoHis="+appNoHis+
        "appNoHis="+appNoHis+
        ", pcResult="+pcResult+
        "pcResult="+pcResult+
        ", basicResult="+basicResult+
        "basicResult="+basicResult+
        ", basicRefuseCode="+basicRefuseCode+
        "basicRefuseCode="+basicRefuseCode+
        ", telResult="+telResult+
        "telResult="+telResult+
        ", telRefuseCode="+telRefuseCode+
        "telRefuseCode="+telRefuseCode+
        ", patchResult="+patchResult+
        "patchResult="+patchResult+
        ", finalResult="+finalResult+
        "finalResult="+finalResult+
        ", finalRefuseCode="+finalRefuseCode+
        "finalRefuseCode="+finalRefuseCode+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", isRetrialApp="+isRetrialApp+
        "isRetrialApp="+isRetrialApp+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (cfRiskClassic == null) cfRiskClassic = "";
        if (isInstalment == null) isInstalment = "";
        if (isRealtimeIssuing == null) isRealtimeIssuing = "";
        if (isOldCust == null) isOldCust = "";
        if (isFreeTelCheck == null) isFreeTelCheck = "";
        if (isSendSmsRefused == null) isSendSmsRefused = "";
        if (isSendSmsPatch == null) isSendSmsPatch = "";
        if (isReturned == null) isReturned = "";
        if (isHaveRetrial == null) isHaveRetrial = "";
        if (appNoHis == null) appNoHis = "";
        if (pcResult == null) pcResult = "";
        if (basicResult == null) basicResult = "";
        if (basicRefuseCode == null) basicRefuseCode = "";
        if (telResult == null) telResult = "";
        if (telRefuseCode == null) telRefuseCode = "";
        if (patchResult == null) patchResult = "";
        if (finalResult == null) finalResult = "";
        if (finalRefuseCode == null) finalRefuseCode = "";
        if (updateDate == null) updateDate = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
        if (isRetrialApp == null) isRetrialApp = "";
    }
}