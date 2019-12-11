package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 制卡结果
 * @author jjb
 */
public class TmMirMakeRes implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String productCd;

    private String appNo;

    private String idType;

    private String idNo;

    private String embName;

    private String bscSuppInd;

    private BigDecimal creditLimit;

    private String companyName;

    private String reasonCode;

    private String owningBranch;

    private Date createTime;

    private Date updateTime;

    private Integer jpaVersion;

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
     * <p>产品代码</p>
     */
    public String getProductCd() {
        return productCd;
    }

    /**
     * <p>产品代码</p>
     */
    public void setProductCd(String productCd) {
        this.productCd = productCd;
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
     * <p>凸印名-姓名拼音</p>
     */
    public String getEmbName() {
        return embName;
    }

    /**
     * <p>凸印名-姓名拼音</p>
     */
    public void setEmbName(String embName) {
        this.embName = embName;
    }

    /**
     * <p>主附卡标识</p>
     */
    public String getBscSuppInd() {
        return bscSuppInd;
    }

    /**
     * <p>主附卡标识</p>
     */
    public void setBscSuppInd(String bscSuppInd) {
        this.bscSuppInd = bscSuppInd;
    }

    /**
     * <p>信用额度</p>
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    /**
     * <p>信用额度</p>
     */
    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * <p>公司名称</p>
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * <p>公司名称</p>
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * <p>原因码</p>
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * <p>原因码</p>
     */
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    /**
     * <p>发卡网点</p>
     */
    public String getOwningBranch() {
        return owningBranch;
    }

    /**
     * <p>发卡网点</p>
     */
    public void setOwningBranch(String owningBranch) {
        this.owningBranch = owningBranch;
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

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("productCd", productCd);
        map.put("appNo", appNo);
        map.put("idType", idType);
        map.put("idNo", idNo);
        map.put("embName", embName);
        map.put("bscSuppInd", bscSuppInd);
        map.put("creditLimit", creditLimit);
        map.put("companyName", companyName);
        map.put("reasonCode", reasonCode);
        map.put("owningBranch", owningBranch);
        map.put("createTime", createTime);
        map.put("updateTime", updateTime);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("productCd")) this.setProductCd(DataTypeUtils.getStringValue(map.get("productCd")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("embName")) this.setEmbName(DataTypeUtils.getStringValue(map.get("embName")));
        if (map.containsKey("bscSuppInd")) this.setBscSuppInd(DataTypeUtils.getStringValue(map.get("bscSuppInd")));
        if (map.containsKey("creditLimit")) this.setCreditLimit(DataTypeUtils.getBigDecimalValue(map.get("creditLimit")));
        if (map.containsKey("companyName")) this.setCompanyName(DataTypeUtils.getStringValue(map.get("companyName")));
        if (map.containsKey("reasonCode")) this.setReasonCode(DataTypeUtils.getStringValue(map.get("reasonCode")));
        if (map.containsKey("owningBranch")) this.setOwningBranch(DataTypeUtils.getStringValue(map.get("owningBranch")));
        if (map.containsKey("createTime")) this.setCreateTime(DataTypeUtils.getDateValue(map.get("createTime")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
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
        ", productCd="+productCd+
        "productCd="+productCd+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", idType="+idType+
        "idType="+idType+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", embName="+embName+
        "embName="+embName+
        ", bscSuppInd="+bscSuppInd+
        "bscSuppInd="+bscSuppInd+
        ", creditLimit="+creditLimit+
        "creditLimit="+creditLimit+
        ", companyName="+companyName+
        "companyName="+companyName+
        ", reasonCode="+reasonCode+
        "reasonCode="+reasonCode+
        ", owningBranch="+owningBranch+
        "owningBranch="+owningBranch+
        ", createTime="+createTime+
        "createTime="+createTime+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (productCd == null) productCd = "";
        if (appNo == null) appNo = "";
        if (idType == null) idType = "";
        if (idNo == null) idNo = "";
        if (embName == null) embName = "";
        if (bscSuppInd == null) bscSuppInd = "";
        if (creditLimit == null) creditLimit = BigDecimal.ZERO;
        if (companyName == null) companyName = "";
        if (reasonCode == null) reasonCode = "";
        if (owningBranch == null) owningBranch = "";
        if (createTime == null) createTime = new Date();
        if (updateTime == null) updateTime = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}