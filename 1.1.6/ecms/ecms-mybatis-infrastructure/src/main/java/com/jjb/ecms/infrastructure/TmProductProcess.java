package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 产品渠道流程表
 * @author jjb
 */
public class TmProductProcess implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String productCd;

    private String productDesc;

    private String appSource;

    private String procdefKey;

    private String isDefault;

    private String riskproduct1;

    private String riskproduct2;

    private String riskproduct3;

    private String riskproduct4;

    private String riskproduct5;

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
     * <p>产品描述</p>
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * <p>产品描述</p>
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    /**
     * <p>申请渠道代码</p>
     */
    public String getAppSource() {
        return appSource;
    }

    /**
     * <p>申请渠道代码</p>
     */
    public void setAppSource(String appSource) {
        this.appSource = appSource;
    }

    /**
     * <p>流程选择</p>
     */
    public String getProcdefKey() {
        return procdefKey;
    }

    /**
     * <p>流程选择</p>
     */
    public void setProcdefKey(String procdefKey) {
        this.procdefKey = procdefKey;
    }

    /**
     * <p>是否默认</p>
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * <p>是否默认</p>
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * <p>风险产品1</p>
     */
    public String getRiskproduct1() {
        return riskproduct1;
    }

    /**
     * <p>风险产品1</p>
     */
    public void setRiskproduct1(String riskproduct1) {
        this.riskproduct1 = riskproduct1;
    }

    /**
     * <p>风险产品2</p>
     */
    public String getRiskproduct2() {
        return riskproduct2;
    }

    /**
     * <p>风险产品2</p>
     */
    public void setRiskproduct2(String riskproduct2) {
        this.riskproduct2 = riskproduct2;
    }

    /**
     * <p>风险产品3</p>
     */
    public String getRiskproduct3() {
        return riskproduct3;
    }

    /**
     * <p>风险产品3</p>
     */
    public void setRiskproduct3(String riskproduct3) {
        this.riskproduct3 = riskproduct3;
    }

    /**
     * <p>风险产品4</p>
     */
    public String getRiskproduct4() {
        return riskproduct4;
    }

    /**
     * <p>风险产品4</p>
     */
    public void setRiskproduct4(String riskproduct4) {
        this.riskproduct4 = riskproduct4;
    }

    /**
     * <p>风险产品5</p>
     */
    public String getRiskproduct5() {
        return riskproduct5;
    }

    /**
     * <p>风险产品5</p>
     */
    public void setRiskproduct5(String riskproduct5) {
        this.riskproduct5 = riskproduct5;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("productCd", productCd);
        map.put("productDesc", productDesc);
        map.put("appSource", appSource);
        map.put("procdefKey", procdefKey);
        map.put("isDefault", isDefault);
        map.put("riskproduct1", riskproduct1);
        map.put("riskproduct2", riskproduct2);
        map.put("riskproduct3", riskproduct3);
        map.put("riskproduct4", riskproduct4);
        map.put("riskproduct5", riskproduct5);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("productCd")) this.setProductCd(DataTypeUtils.getStringValue(map.get("productCd")));
        if (map.containsKey("productDesc")) this.setProductDesc(DataTypeUtils.getStringValue(map.get("productDesc")));
        if (map.containsKey("appSource")) this.setAppSource(DataTypeUtils.getStringValue(map.get("appSource")));
        if (map.containsKey("procdefKey")) this.setProcdefKey(DataTypeUtils.getStringValue(map.get("procdefKey")));
        if (map.containsKey("isDefault")) this.setIsDefault(DataTypeUtils.getStringValue(map.get("isDefault")));
        if (map.containsKey("riskproduct1")) this.setRiskproduct1(DataTypeUtils.getStringValue(map.get("riskproduct1")));
        if (map.containsKey("riskproduct2")) this.setRiskproduct2(DataTypeUtils.getStringValue(map.get("riskproduct2")));
        if (map.containsKey("riskproduct3")) this.setRiskproduct3(DataTypeUtils.getStringValue(map.get("riskproduct3")));
        if (map.containsKey("riskproduct4")) this.setRiskproduct4(DataTypeUtils.getStringValue(map.get("riskproduct4")));
        if (map.containsKey("riskproduct5")) this.setRiskproduct5(DataTypeUtils.getStringValue(map.get("riskproduct5")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", productCd="+productCd+
        "productCd="+productCd+
        ", productDesc="+productDesc+
        "productDesc="+productDesc+
        ", appSource="+appSource+
        "appSource="+appSource+
        ", procdefKey="+procdefKey+
        "procdefKey="+procdefKey+
        ", isDefault="+isDefault+
        "isDefault="+isDefault+
        ", riskproduct1="+riskproduct1+
        "riskproduct1="+riskproduct1+
        ", riskproduct2="+riskproduct2+
        "riskproduct2="+riskproduct2+
        ", riskproduct3="+riskproduct3+
        "riskproduct3="+riskproduct3+
        ", riskproduct4="+riskproduct4+
        "riskproduct4="+riskproduct4+
        ", riskproduct5="+riskproduct5+
        "riskproduct5="+riskproduct5+
        "]";
    }

    public void fillDefaultValues() {
        if (productCd == null) productCd = "";
        if (productDesc == null) productDesc = "";
        if (appSource == null) appSource = "";
        if (procdefKey == null) procdefKey = "";
        if (isDefault == null) isDefault = "";
        if (riskproduct1 == null) riskproduct1 = "";
        if (riskproduct2 == null) riskproduct2 = "";
        if (riskproduct3 == null) riskproduct3 = "";
        if (riskproduct4 == null) riskproduct4 = "";
        if (riskproduct5 == null) riskproduct5 = "";
    }
}