package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 卡产品与卡面
 * @author jjb
 */
public class TmProductCardFace implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productCd;

    private String cardFaceCd;

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
     * <p>卡面代码</p>
     */
    public String getCardFaceCd() {
        return cardFaceCd;
    }

    /**
     * <p>卡面代码</p>
     */
    public void setCardFaceCd(String cardFaceCd) {
        this.cardFaceCd = cardFaceCd;
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
        map.put("cardFaceCd", cardFaceCd);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("productCd")) this.setProductCd(DataTypeUtils.getStringValue(map.get("productCd")));
        if (map.containsKey("cardFaceCd")) this.setCardFaceCd(DataTypeUtils.getStringValue(map.get("cardFaceCd")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", productCd="+productCd+
        "productCd="+productCd+
        ", cardFaceCd="+cardFaceCd+
        "cardFaceCd="+cardFaceCd+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (productCd == null) productCd = "";
        if (cardFaceCd == null) cardFaceCd = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}