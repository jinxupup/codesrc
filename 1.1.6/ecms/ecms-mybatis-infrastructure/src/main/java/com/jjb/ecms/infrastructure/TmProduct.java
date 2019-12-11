package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 产品参数表
 * @author jjb
 */
public class TmProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String productCd;

    private String productDesc;

    private String productStatus;

    private String cardClass;

    private String bin;

    private String cardNoRangeCeil;

    private String cardNoRangeFlr;

    private String fabricationInd;

    private String isEtc;

    private String subCardType;

    private BigDecimal approvalMaximum;

    private String ifEnableAttachCard;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer jpaVersion;

    /**
     * <p>主键ID</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>主键ID</p>
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
     * <p>产品名称描述</p>
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * <p>产品名称描述</p>
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /**
     * <p>产品状态</p>
     */
    public String getProductStatus() {
        return productStatus;
    }

    /**
     * <p>产品状态</p>
     */
    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    /**
     * <p>卡等级</p>
     */
    public String getCardClass() {
        return cardClass;
    }

    /**
     * <p>卡等级</p>
     */
    public void setCardClass(String cardClass) {
        this.cardClass = cardClass;
    }

    /**
     * <p>卡BIN</p>
     */
    public String getBin() {
        return bin;
    }

    /**
     * <p>卡BIN</p>
     */
    public void setBin(String bin) {
        this.bin = bin;
    }

    /**
     * <p>卡号段上限</p>
     */
    public String getCardNoRangeCeil() {
        return cardNoRangeCeil;
    }

    /**
     * <p>卡号段上限</p>
     */
    public void setCardNoRangeCeil(String cardNoRangeCeil) {
        this.cardNoRangeCeil = cardNoRangeCeil;
    }

    /**
     * <p>卡号段下限</p>
     */
    public String getCardNoRangeFlr() {
        return cardNoRangeFlr;
    }

    /**
     * <p>卡号段下限</p>
     */
    public void setCardNoRangeFlr(String cardNoRangeFlr) {
        this.cardNoRangeFlr = cardNoRangeFlr;
    }

    /**
     * <p>是否生产制卡文件</p>
     */
    public String getFabricationInd() {
        return fabricationInd;
    }

    /**
     * <p>是否生产制卡文件</p>
     */
    public void setFabricationInd(String fabricationInd) {
        this.fabricationInd = fabricationInd;
    }

    /**
     * <p>是否ETC卡</p>
     */
    public String getIsEtc() {
        return isEtc;
    }

    /**
     * <p>是否ETC卡</p>
     */
    public void setIsEtc(String isEtc) {
        this.isEtc = isEtc;
    }

    /**
     * <p>卡类型子类型</p>
     */
    public String getSubCardType() {
        return subCardType;
    }

    /**
     * <p>卡类型子类型</p>
     */
    public void setSubCardType(String subCardType) {
        this.subCardType = subCardType;
    }

    /**
     * <p>最高审批额度</p>
     */
    public BigDecimal getApprovalMaximum() {
        return approvalMaximum;
    }

    /**
     * <p>最高审批额度</p>
     */
    public void setApprovalMaximum(BigDecimal approvalMaximum) {
        this.approvalMaximum = approvalMaximum;
    }

    /**
     * <p>是否支持附卡</p>
     */
    public String getIfEnableAttachCard() {
        return ifEnableAttachCard;
    }

    /**
     * <p>是否支持附卡</p>
     */
    public void setIfEnableAttachCard(String ifEnableAttachCard) {
        this.ifEnableAttachCard = ifEnableAttachCard;
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
        map.put("productDesc", productDesc);
        map.put("productStatus", productStatus);
        map.put("cardClass", cardClass);
        map.put("bin", bin);
        map.put("cardNoRangeCeil", cardNoRangeCeil);
        map.put("cardNoRangeFlr", cardNoRangeFlr);
        map.put("fabricationInd", fabricationInd);
        map.put("isEtc", isEtc);
        map.put("subCardType", subCardType);
        map.put("approvalMaximum", approvalMaximum);
        map.put("ifEnableAttachCard", ifEnableAttachCard);
        map.put("createTime", createTime);
        map.put("createUser", createUser);
        map.put("updateTime", updateTime);
        map.put("updateUser", updateUser);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("productCd")) this.setProductCd(DataTypeUtils.getStringValue(map.get("productCd")));
        if (map.containsKey("productDesc")) this.setProductDesc(DataTypeUtils.getStringValue(map.get("productDesc")));
        if (map.containsKey("productStatus")) this.setProductStatus(DataTypeUtils.getStringValue(map.get("productStatus")));
        if (map.containsKey("cardClass")) this.setCardClass(DataTypeUtils.getStringValue(map.get("cardClass")));
        if (map.containsKey("bin")) this.setBin(DataTypeUtils.getStringValue(map.get("bin")));
        if (map.containsKey("cardNoRangeCeil")) this.setCardNoRangeCeil(DataTypeUtils.getStringValue(map.get("cardNoRangeCeil")));
        if (map.containsKey("cardNoRangeFlr")) this.setCardNoRangeFlr(DataTypeUtils.getStringValue(map.get("cardNoRangeFlr")));
        if (map.containsKey("fabricationInd")) this.setFabricationInd(DataTypeUtils.getStringValue(map.get("fabricationInd")));
        if (map.containsKey("isEtc")) this.setIsEtc(DataTypeUtils.getStringValue(map.get("isEtc")));
        if (map.containsKey("subCardType")) this.setSubCardType(DataTypeUtils.getStringValue(map.get("subCardType")));
        if (map.containsKey("approvalMaximum")) this.setApprovalMaximum(DataTypeUtils.getBigDecimalValue(map.get("approvalMaximum")));
        if (map.containsKey("ifEnableAttachCard")) this.setIfEnableAttachCard(DataTypeUtils.getStringValue(map.get("ifEnableAttachCard")));
        if (map.containsKey("createTime")) this.setCreateTime(DataTypeUtils.getDateValue(map.get("createTime")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
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
        ", productCd="+productCd+
        "productCd="+productCd+
        ", productDesc="+productDesc+
        "productDesc="+productDesc+
        ", productStatus="+productStatus+
        "productStatus="+productStatus+
        ", cardClass="+cardClass+
        "cardClass="+cardClass+
        ", bin="+bin+
        "bin="+bin+
        ", cardNoRangeCeil="+cardNoRangeCeil+
        "cardNoRangeCeil="+cardNoRangeCeil+
        ", cardNoRangeFlr="+cardNoRangeFlr+
        "cardNoRangeFlr="+cardNoRangeFlr+
        ", fabricationInd="+fabricationInd+
        "fabricationInd="+fabricationInd+
        ", isEtc="+isEtc+
        "isEtc="+isEtc+
        ", subCardType="+subCardType+
        "subCardType="+subCardType+
        ", approvalMaximum="+approvalMaximum+
        "approvalMaximum="+approvalMaximum+
        ", ifEnableAttachCard="+ifEnableAttachCard+
        "ifEnableAttachCard="+ifEnableAttachCard+
        ", createTime="+createTime+
        "createTime="+createTime+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (productCd == null) productCd = "";
        if (productDesc == null) productDesc = "";
        if (productStatus == null) productStatus = "";
        if (cardClass == null) cardClass = "";
        if (bin == null) bin = "";
        if (cardNoRangeCeil == null) cardNoRangeCeil = "";
        if (cardNoRangeFlr == null) cardNoRangeFlr = "";
        if (fabricationInd == null) fabricationInd = "";
        if (isEtc == null) isEtc = "";
        if (subCardType == null) subCardType = "";
        if (approvalMaximum == null) approvalMaximum = BigDecimal.ZERO;
        if (ifEnableAttachCard == null) ifEnableAttachCard = "";
        if (createTime == null) createTime = new Date();
        if (createUser == null) createUser = "";
        if (updateTime == null) updateTime = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}