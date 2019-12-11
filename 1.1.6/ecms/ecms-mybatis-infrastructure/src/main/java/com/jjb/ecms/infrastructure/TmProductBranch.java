package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 卡产品与机构
 * @author jjb
 */
public class TmProductBranch implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productCd;

    private String branchCode;

    private Integer jpaVersion;

    /**
     * <p>卡产品编号</p>
     */
    public String getProductCd() {
        return productCd;
    }

    /**
     * <p>卡产品编号</p>
     */
    public void setProductCd(String productCd) {
        this.productCd = productCd;
    }

    /**
     * <p>分行号</p>
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * <p>分行号</p>
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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
        map.put("productCd", productCd);
        map.put("branchCode", branchCode);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("productCd")) this.setProductCd(DataTypeUtils.getStringValue(map.get("productCd")));
        if (map.containsKey("branchCode")) this.setBranchCode(DataTypeUtils.getStringValue(map.get("branchCode")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", productCd="+productCd+
        "productCd="+productCd+
        ", branchCode="+branchCode+
        "branchCode="+branchCode+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (productCd == null) productCd = "";
        if (branchCode == null) branchCode = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}