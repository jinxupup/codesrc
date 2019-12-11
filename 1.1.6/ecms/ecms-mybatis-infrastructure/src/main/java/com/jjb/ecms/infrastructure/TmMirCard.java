package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 已申请卡信息表
 * @author jjb
 */
public class TmMirCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String acctNo;

    private String cardNo;

    private String latestCardNo;

    private String custId;

    private String productCd;

    private String acctType;

    private String bscSuppInd;

    private String pyhCd;

    private String owningBranch;

    private String appNo;

    private String blockCode;

    private String appRejectReason;

    private Date setupDate;

    private Integer jpaVersion;

    private Date cardExpireDate;

    private String idNo;

    private String idType;

    private String name;

    private String activateInd;

    private Date activateDate;

    private String overdueInd;

    private String overdueNumber;

    private Date updateTime;

    private String ifSwiped;

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
     * <p>账户编号</p>
     * <p>账号</p>
     */
    public String getAcctNo() {
        return acctNo;
    }

    /**
     * <p>账户编号</p>
     * <p>账号</p>
     */
    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
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
     * <p>最新介质卡号</p>
     */
    public String getLatestCardNo() {
        return latestCardNo;
    }

    /**
     * <p>最新介质卡号</p>
     */
    public void setLatestCardNo(String latestCardNo) {
        this.latestCardNo = latestCardNo;
    }

    /**
     * <p>客户编号</p>
     */
    public String getCustId() {
        return custId;
    }

    /**
     * <p>客户编号</p>
     */
    public void setCustId(String custId) {
        this.custId = custId;
    }

    /**
     * <p>卡产品代码</p>
     */
    public String getProductCd() {
        return productCd;
    }

    /**
     * <p>卡产品代码</p>
     */
    public void setProductCd(String productCd) {
        this.productCd = productCd;
    }

    /**
     * <p>账户类型</p>
     */
    public String getAcctType() {
        return acctType;
    }

    /**
     * <p>账户类型</p>
     */
    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    /**
     * <p>主附卡指示</p>
     */
    public String getBscSuppInd() {
        return bscSuppInd;
    }

    /**
     * <p>主附卡指示</p>
     */
    public void setBscSuppInd(String bscSuppInd) {
        this.bscSuppInd = bscSuppInd;
    }

    /**
     * <p>卡面代码</p>
     */
    public String getPyhCd() {
        return pyhCd;
    }

    /**
     * <p>卡面代码</p>
     */
    public void setPyhCd(String pyhCd) {
        this.pyhCd = pyhCd;
    }

    /**
     * <p>受理网点</p>
     */
    public String getOwningBranch() {
        return owningBranch;
    }

    /**
     * <p>受理网点</p>
     */
    public void setOwningBranch(String owningBranch) {
        this.owningBranch = owningBranch;
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
     * <p>锁定码</p>
     */
    public String getBlockCode() {
        return blockCode;
    }

    /**
     * <p>锁定码</p>
     */
    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    /**
     * <p>拒绝原因</p>
     */
    public String getAppRejectReason() {
        return appRejectReason;
    }

    /**
     * <p>拒绝原因</p>
     */
    public void setAppRejectReason(String appRejectReason) {
        this.appRejectReason = appRejectReason;
    }

    /**
     * <p>处理日期</p>
     */
    public Date getSetupDate() {
        return setupDate;
    }

    /**
     * <p>处理日期</p>
     */
    public void setSetupDate(Date setupDate) {
        this.setupDate = setupDate;
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
     * <p>卡片有效日期</p>
     */
    public Date getCardExpireDate() {
        return cardExpireDate;
    }

    /**
     * <p>卡片有效日期</p>
     */
    public void setCardExpireDate(Date cardExpireDate) {
        this.cardExpireDate = cardExpireDate;
    }

    /**
     * <p>证件号码</p>
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * <p>证件号码</p>
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * <p>证件类型</p>
     */
    public String getIdType() {
        return idType;
    }

    /**
     * <p>证件类型</p>
     */
    public void setIdType(String idType) {
        this.idType = idType;
    }

    /**
     * <p>姓名</p>
     */
    public String getName() {
        return name;
    }

    /**
     * <p>姓名</p>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>是否已激活</p>
     */
    public String getActivateInd() {
        return activateInd;
    }

    /**
     * <p>是否已激活</p>
     */
    public void setActivateInd(String activateInd) {
        this.activateInd = activateInd;
    }

    /**
     * <p>激活日期</p>
     */
    public Date getActivateDate() {
        return activateDate;
    }

    /**
     * <p>激活日期</p>
     */
    public void setActivateDate(Date activateDate) {
        this.activateDate = activateDate;
    }

    /**
     * <p>是否逾期</p>
     */
    public String getOverdueInd() {
        return overdueInd;
    }

    /**
     * <p>是否逾期</p>
     */
    public void setOverdueInd(String overdueInd) {
        this.overdueInd = overdueInd;
    }

    /**
     * <p>当前逾期期数</p>
     */
    public String getOverdueNumber() {
        return overdueNumber;
    }

    /**
     * <p>当前逾期期数</p>
     */
    public void setOverdueNumber(String overdueNumber) {
        this.overdueNumber = overdueNumber;
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
     * <p>是否完成首刷</p>
     */
    public String getIfSwiped() {
        return ifSwiped;
    }

    /**
     * <p>是否完成首刷</p>
     */
    public void setIfSwiped(String ifSwiped) {
        this.ifSwiped = ifSwiped;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("org", org);
        map.put("acctNo", acctNo);
        map.put("cardNo", cardNo);
        map.put("latestCardNo", latestCardNo);
        map.put("custId", custId);
        map.put("productCd", productCd);
        map.put("acctType", acctType);
        map.put("bscSuppInd", bscSuppInd);
        map.put("pyhCd", pyhCd);
        map.put("owningBranch", owningBranch);
        map.put("appNo", appNo);
        map.put("blockCode", blockCode);
        map.put("appRejectReason", appRejectReason);
        map.put("setupDate", setupDate);
        map.put("jpaVersion", jpaVersion);
        map.put("cardExpireDate", cardExpireDate);
        map.put("idNo", idNo);
        map.put("idType", idType);
        map.put("name", name);
        map.put("activateInd", activateInd);
        map.put("activateDate", activateDate);
        map.put("overdueInd", overdueInd);
        map.put("overdueNumber", overdueNumber);
        map.put("updateTime", updateTime);
        map.put("ifSwiped", ifSwiped);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("acctNo")) this.setAcctNo(DataTypeUtils.getStringValue(map.get("acctNo")));
        if (map.containsKey("cardNo")) this.setCardNo(DataTypeUtils.getStringValue(map.get("cardNo")));
        if (map.containsKey("latestCardNo")) this.setLatestCardNo(DataTypeUtils.getStringValue(map.get("latestCardNo")));
        if (map.containsKey("custId")) this.setCustId(DataTypeUtils.getStringValue(map.get("custId")));
        if (map.containsKey("productCd")) this.setProductCd(DataTypeUtils.getStringValue(map.get("productCd")));
        if (map.containsKey("acctType")) this.setAcctType(DataTypeUtils.getStringValue(map.get("acctType")));
        if (map.containsKey("bscSuppInd")) this.setBscSuppInd(DataTypeUtils.getStringValue(map.get("bscSuppInd")));
        if (map.containsKey("pyhCd")) this.setPyhCd(DataTypeUtils.getStringValue(map.get("pyhCd")));
        if (map.containsKey("owningBranch")) this.setOwningBranch(DataTypeUtils.getStringValue(map.get("owningBranch")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("blockCode")) this.setBlockCode(DataTypeUtils.getStringValue(map.get("blockCode")));
        if (map.containsKey("appRejectReason")) this.setAppRejectReason(DataTypeUtils.getStringValue(map.get("appRejectReason")));
        if (map.containsKey("setupDate")) this.setSetupDate(DataTypeUtils.getDateValue(map.get("setupDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("cardExpireDate")) this.setCardExpireDate(DataTypeUtils.getDateValue(map.get("cardExpireDate")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("activateInd")) this.setActivateInd(DataTypeUtils.getStringValue(map.get("activateInd")));
        if (map.containsKey("activateDate")) this.setActivateDate(DataTypeUtils.getDateValue(map.get("activateDate")));
        if (map.containsKey("overdueInd")) this.setOverdueInd(DataTypeUtils.getStringValue(map.get("overdueInd")));
        if (map.containsKey("overdueNumber")) this.setOverdueNumber(DataTypeUtils.getStringValue(map.get("overdueNumber")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
        if (map.containsKey("ifSwiped")) this.setIfSwiped(DataTypeUtils.getStringValue(map.get("ifSwiped")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", org="+org+
        "org="+org+
        ", acctNo="+acctNo+
        "acctNo="+acctNo+
        ", cardNo="+cardNo+
        "cardNo="+cardNo+
        ", latestCardNo="+latestCardNo+
        "latestCardNo="+latestCardNo+
        ", custId="+custId+
        "custId="+custId+
        ", productCd="+productCd+
        "productCd="+productCd+
        ", acctType="+acctType+
        "acctType="+acctType+
        ", bscSuppInd="+bscSuppInd+
        "bscSuppInd="+bscSuppInd+
        ", pyhCd="+pyhCd+
        "pyhCd="+pyhCd+
        ", owningBranch="+owningBranch+
        "owningBranch="+owningBranch+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", blockCode="+blockCode+
        "blockCode="+blockCode+
        ", appRejectReason="+appRejectReason+
        "appRejectReason="+appRejectReason+
        ", setupDate="+setupDate+
        "setupDate="+setupDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", cardExpireDate="+cardExpireDate+
        "cardExpireDate="+cardExpireDate+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", idType="+idType+
        "idType="+idType+
        ", name="+name+
        "name="+name+
        ", activateInd="+activateInd+
        "activateInd="+activateInd+
        ", activateDate="+activateDate+
        "activateDate="+activateDate+
        ", overdueInd="+overdueInd+
        "overdueInd="+overdueInd+
        ", overdueNumber="+overdueNumber+
        "overdueNumber="+overdueNumber+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        ", ifSwiped="+ifSwiped+
        "ifSwiped="+ifSwiped+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (acctNo == null) acctNo = "";
        if (cardNo == null) cardNo = "";
        if (latestCardNo == null) latestCardNo = "";
        if (custId == null) custId = "";
        if (productCd == null) productCd = "";
        if (acctType == null) acctType = "";
        if (bscSuppInd == null) bscSuppInd = "";
        if (pyhCd == null) pyhCd = "";
        if (owningBranch == null) owningBranch = "";
        if (appNo == null) appNo = "";
        if (blockCode == null) blockCode = "";
        if (appRejectReason == null) appRejectReason = "";
        if (setupDate == null) setupDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
        if (cardExpireDate == null) cardExpireDate = new Date();
        if (idNo == null) idNo = "";
        if (idType == null) idType = "";
        if (name == null) name = "";
        if (activateInd == null) activateInd = "";
        if (activateDate == null) activateDate = new Date();
        if (overdueInd == null) overdueInd = "";
        if (overdueNumber == null) overdueNumber = "";
        if (updateTime == null) updateTime = new Date();
        if (ifSwiped == null) ifSwiped = "";
    }
}