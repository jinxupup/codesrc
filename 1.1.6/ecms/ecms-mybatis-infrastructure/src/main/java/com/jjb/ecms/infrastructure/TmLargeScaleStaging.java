package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 大额分期申请表
 * @author jjb
 */
public class TmLargeScaleStaging implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String idType;

    private String idNo;

    private String cellphone;

    private String maritalStatus;

    private String appProducts;

    private BigDecimal appAmount;

    private String companyName;

    private String firstContactName;

    private String firstContactPhone;

    private String imageNum;

    private String policyResult;

    private String ruleList;

    private String refuseCode;

    private String weCode;

    private String pptyProvince;

    private String pptyCity;

    private String pptyArea;

    private String pptyAreaCode;

    private String channelType;

    private Date createDate;

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
     * <p>客户姓名</p>
     */
    public String getName() {
        return name;
    }

    /**
     * <p>客户姓名</p>
     */
    public void setName(String name) {
        this.name = name;
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
     * <p>客户身份证号</p>
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * <p>客户身份证号</p>
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * <p>手机号码</p>
     */
    public String getCellphone() {
        return cellphone;
    }

    /**
     * <p>手机号码</p>
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * <p>婚姻状况</p>
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * <p>婚姻状况</p>
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * <p>申请产品</p>
     */
    public String getAppProducts() {
        return appProducts;
    }

    /**
     * <p>申请产品</p>
     */
    public void setAppProducts(String appProducts) {
        this.appProducts = appProducts;
    }

    /**
     * <p>贷款申请金额</p>
     */
    public BigDecimal getAppAmount() {
        return appAmount;
    }

    /**
     * <p>贷款申请金额</p>
     */
    public void setAppAmount(BigDecimal appAmount) {
        this.appAmount = appAmount;
    }

    /**
     * <p>单位名称</p>
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * <p>单位名称</p>
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * <p>第一联系人姓名</p>
     */
    public String getFirstContactName() {
        return firstContactName;
    }

    /**
     * <p>第一联系人姓名</p>
     */
    public void setFirstContactName(String firstContactName) {
        this.firstContactName = firstContactName;
    }

    /**
     * <p>第一联系人手机</p>
     */
    public String getFirstContactPhone() {
        return firstContactPhone;
    }

    /**
     * <p>第一联系人手机</p>
     */
    public void setFirstContactPhone(String firstContactPhone) {
        this.firstContactPhone = firstContactPhone;
    }

    /**
     * <p>影像批次号</p>
     */
    public String getImageNum() {
        return imageNum;
    }

    /**
     * <p>影像批次号</p>
     */
    public void setImageNum(String imageNum) {
        this.imageNum = imageNum;
    }

    /**
     * <p>决策结果</p>
     */
    public String getPolicyResult() {
        return policyResult;
    }

    /**
     * <p>决策结果</p>
     */
    public void setPolicyResult(String policyResult) {
        this.policyResult = policyResult;
    }

    /**
     * <p>人行规则命中结果清单</p>
     */
    public String getRuleList() {
        return ruleList;
    }

    /**
     * <p>人行规则命中结果清单</p>
     */
    public void setRuleList(String ruleList) {
        this.ruleList = ruleList;
    }

    /**
     * <p>拒绝原因</p>
     */
    public String getRefuseCode() {
        return refuseCode;
    }

    /**
     * <p>拒绝原因</p>
     */
    public void setRefuseCode(String refuseCode) {
        this.refuseCode = refuseCode;
    }

    /**
     * <p>微信个人识别码</p>
     */
    public String getWeCode() {
        return weCode;
    }

    /**
     * <p>微信个人识别码</p>
     */
    public void setWeCode(String weCode) {
        this.weCode = weCode;
    }

    /**
     * <p>所属省</p>
     */
    public String getPptyProvince() {
        return pptyProvince;
    }

    /**
     * <p>所属省</p>
     */
    public void setPptyProvince(String pptyProvince) {
        this.pptyProvince = pptyProvince;
    }

    /**
     * <p>所属市</p>
     */
    public String getPptyCity() {
        return pptyCity;
    }

    /**
     * <p>所属市</p>
     */
    public void setPptyCity(String pptyCity) {
        this.pptyCity = pptyCity;
    }

    /**
     * <p>车牌归属地</p>
     */
    public String getPptyArea() {
        return pptyArea;
    }

    /**
     * <p>车牌归属地</p>
     */
    public void setPptyArea(String pptyArea) {
        this.pptyArea = pptyArea;
    }

    /**
     * <p>车牌归属地字母代号</p>
     */
    public String getPptyAreaCode() {
        return pptyAreaCode;
    }

    /**
     * <p>车牌归属地字母代号</p>
     */
    public void setPptyAreaCode(String pptyAreaCode) {
        this.pptyAreaCode = pptyAreaCode;
    }

    /**
     * <p>渠道类型</p>
     */
    public String getChannelType() {
        return channelType;
    }

    /**
     * <p>渠道类型</p>
     */
    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    /**
     * <p>创建日期</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>创建日期</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>乐观所</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观所</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("name", name);
        map.put("idType", idType);
        map.put("idNo", idNo);
        map.put("cellphone", cellphone);
        map.put("maritalStatus", maritalStatus);
        map.put("appProducts", appProducts);
        map.put("appAmount", appAmount);
        map.put("companyName", companyName);
        map.put("firstContactName", firstContactName);
        map.put("firstContactPhone", firstContactPhone);
        map.put("imageNum", imageNum);
        map.put("policyResult", policyResult);
        map.put("ruleList", ruleList);
        map.put("refuseCode", refuseCode);
        map.put("weCode", weCode);
        map.put("pptyProvince", pptyProvince);
        map.put("pptyCity", pptyCity);
        map.put("pptyArea", pptyArea);
        map.put("pptyAreaCode", pptyAreaCode);
        map.put("channelType", channelType);
        map.put("createDate", createDate);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("cellphone")) this.setCellphone(DataTypeUtils.getStringValue(map.get("cellphone")));
        if (map.containsKey("maritalStatus")) this.setMaritalStatus(DataTypeUtils.getStringValue(map.get("maritalStatus")));
        if (map.containsKey("appProducts")) this.setAppProducts(DataTypeUtils.getStringValue(map.get("appProducts")));
        if (map.containsKey("appAmount")) this.setAppAmount(DataTypeUtils.getBigDecimalValue(map.get("appAmount")));
        if (map.containsKey("companyName")) this.setCompanyName(DataTypeUtils.getStringValue(map.get("companyName")));
        if (map.containsKey("firstContactName")) this.setFirstContactName(DataTypeUtils.getStringValue(map.get("firstContactName")));
        if (map.containsKey("firstContactPhone")) this.setFirstContactPhone(DataTypeUtils.getStringValue(map.get("firstContactPhone")));
        if (map.containsKey("imageNum")) this.setImageNum(DataTypeUtils.getStringValue(map.get("imageNum")));
        if (map.containsKey("policyResult")) this.setPolicyResult(DataTypeUtils.getStringValue(map.get("policyResult")));
        if (map.containsKey("ruleList")) this.setRuleList(DataTypeUtils.getStringValue(map.get("ruleList")));
        if (map.containsKey("refuseCode")) this.setRefuseCode(DataTypeUtils.getStringValue(map.get("refuseCode")));
        if (map.containsKey("weCode")) this.setWeCode(DataTypeUtils.getStringValue(map.get("weCode")));
        if (map.containsKey("pptyProvince")) this.setPptyProvince(DataTypeUtils.getStringValue(map.get("pptyProvince")));
        if (map.containsKey("pptyCity")) this.setPptyCity(DataTypeUtils.getStringValue(map.get("pptyCity")));
        if (map.containsKey("pptyArea")) this.setPptyArea(DataTypeUtils.getStringValue(map.get("pptyArea")));
        if (map.containsKey("pptyAreaCode")) this.setPptyAreaCode(DataTypeUtils.getStringValue(map.get("pptyAreaCode")));
        if (map.containsKey("channelType")) this.setChannelType(DataTypeUtils.getStringValue(map.get("channelType")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", name="+name+
        "name="+name+
        ", idType="+idType+
        "idType="+idType+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", cellphone="+cellphone+
        "cellphone="+cellphone+
        ", maritalStatus="+maritalStatus+
        "maritalStatus="+maritalStatus+
        ", appProducts="+appProducts+
        "appProducts="+appProducts+
        ", appAmount="+appAmount+
        "appAmount="+appAmount+
        ", companyName="+companyName+
        "companyName="+companyName+
        ", firstContactName="+firstContactName+
        "firstContactName="+firstContactName+
        ", firstContactPhone="+firstContactPhone+
        "firstContactPhone="+firstContactPhone+
        ", imageNum="+imageNum+
        "imageNum="+imageNum+
        ", policyResult="+policyResult+
        "policyResult="+policyResult+
        ", ruleList="+ruleList+
        "ruleList="+ruleList+
        ", refuseCode="+refuseCode+
        "refuseCode="+refuseCode+
        ", weCode="+weCode+
        "weCode="+weCode+
        ", pptyProvince="+pptyProvince+
        "pptyProvince="+pptyProvince+
        ", pptyCity="+pptyCity+
        "pptyCity="+pptyCity+
        ", pptyArea="+pptyArea+
        "pptyArea="+pptyArea+
        ", pptyAreaCode="+pptyAreaCode+
        "pptyAreaCode="+pptyAreaCode+
        ", channelType="+channelType+
        "channelType="+channelType+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (name == null) name = "";
        if (idType == null) idType = "";
        if (idNo == null) idNo = "";
        if (cellphone == null) cellphone = "";
        if (maritalStatus == null) maritalStatus = "";
        if (appProducts == null) appProducts = "";
        if (appAmount == null) appAmount = BigDecimal.ZERO;
        if (companyName == null) companyName = "";
        if (firstContactName == null) firstContactName = "";
        if (firstContactPhone == null) firstContactPhone = "";
        if (imageNum == null) imageNum = "";
        if (policyResult == null) policyResult = "";
        if (ruleList == null) ruleList = "";
        if (refuseCode == null) refuseCode = "";
        if (weCode == null) weCode = "";
        if (pptyProvince == null) pptyProvince = "";
        if (pptyCity == null) pptyCity = "";
        if (pptyArea == null) pptyArea = "";
        if (pptyAreaCode == null) pptyAreaCode = "";
        if (channelType == null) channelType = "";
        if (createDate == null) createDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}