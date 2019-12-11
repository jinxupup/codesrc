package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 页面字段管理
 * @author jjb
 */
public class TmFieldProduct implements Serializable {
    private static final long serialVersionUID = 1L;

    private String productCd;

    private String fieldEn;

    private String fieldRegion;

    private Integer fieldSort;

    private String ifRequiredItem;

    private String ifReviewItem;

    private Integer reviewSort;

    private String isInput;

    private String isReview;

    private String value1;

    private String value2;

    private String value3;

    private String value4;

    private String value5;

    private String obText1;

    private String obText2;

    private String obText3;

    private String obText4;

    private String remark;

    private String createUser;

    private Date createDate;

    private Integer jpaVersion;

    /**
     * <p>卡产品代码</p>
     * <p>卡产品代码</p>
     */
    public String getProductCd() {
        return productCd;
    }

    /**
     * <p>卡产品代码</p>
     * <p>卡产品代码</p>
     */
    public void setProductCd(String productCd) {
        this.productCd = productCd;
    }

    /**
     * <p>字段名</p>
     * <p>字段id</p>
     */
    public String getFieldEn() {
        return fieldEn;
    }

    /**
     * <p>字段名</p>
     * <p>字段id</p>
     */
    public void setFieldEn(String fieldEn) {
        this.fieldEn = fieldEn;
    }

    /**
     * <p>字段所在的位置</p>
     * <p>字段所在的位置</p>
     */
    public String getFieldRegion() {
        return fieldRegion;
    }

    /**
     * <p>字段所在的位置</p>
     * <p>字段所在的位置</p>
     */
    public void setFieldRegion(String fieldRegion) {
        this.fieldRegion = fieldRegion;
    }

    /**
     * <p>字段位置顺序</p>
     * <p>字段位置顺序</p>
     */
    public Integer getFieldSort() {
        return fieldSort;
    }

    /**
     * <p>字段位置顺序</p>
     * <p>字段位置顺序</p>
     */
    public void setFieldSort(Integer fieldSort) {
        this.fieldSort = fieldSort;
    }

    /**
     * <p>是否是必输项</p>
     */
    public String getIfRequiredItem() {
        return ifRequiredItem;
    }

    /**
     * <p>是否是必输项</p>
     */
    public void setIfRequiredItem(String ifRequiredItem) {
        this.ifRequiredItem = ifRequiredItem;
    }

    /**
     * <p>是否是复核项</p>
     */
    public String getIfReviewItem() {
        return ifReviewItem;
    }

    /**
     * <p>是否是复核项</p>
     */
    public void setIfReviewItem(String ifReviewItem) {
        this.ifReviewItem = ifReviewItem;
    }

    /**
     * <p>复核项的顺序</p>
     * <p>复核项的顺序</p>
     */
    public Integer getReviewSort() {
        return reviewSort;
    }

    /**
     * <p>复核项的顺序</p>
     * <p>复核项的顺序</p>
     */
    public void setReviewSort(Integer reviewSort) {
        this.reviewSort = reviewSort;
    }

    /**
     * <p>录入是否显示</p>
     */
    public String getIsInput() {
        return isInput;
    }

    /**
     * <p>录入是否显示</p>
     */
    public void setIsInput(String isInput) {
        this.isInput = isInput;
    }

    /**
     * <p>复核是否显示</p>
     */
    public String getIsReview() {
        return isReview;
    }

    /**
     * <p>复核是否显示</p>
     */
    public void setIsReview(String isReview) {
        this.isReview = isReview;
    }

    /**
     * <p>一级审批节点是否显示</p>
     */
    public String getValue1() {
        return value1;
    }

    /**
     * <p>一级审批节点是否显示</p>
     */
    public void setValue1(String value1) {
        this.value1 = value1;
    }

    /**
     * <p>二级审批节点是否显示</p>
     */
    public String getValue2() {
        return value2;
    }

    /**
     * <p>二级审批节点是否显示</p>
     */
    public void setValue2(String value2) {
        this.value2 = value2;
    }

    /**
     * <p>三级审批节点是否显示</p>
     */
    public String getValue3() {
        return value3;
    }

    /**
     * <p>三级审批节点是否显示</p>
     */
    public void setValue3(String value3) {
        this.value3 = value3;
    }

    /**
     * <p>四级审批节点是否显示</p>
     */
    public String getValue4() {
        return value4;
    }

    /**
     * <p>四级审批节点是否显示</p>
     */
    public void setValue4(String value4) {
        this.value4 = value4;
    }

    /**
     * <p>五级审批节点是否显示</p>
     */
    public String getValue5() {
        return value5;
    }

    /**
     * <p>五级审批节点是否显示</p>
     */
    public void setValue5(String value5) {
        this.value5 = value5;
    }

    /**
     * <p>备用字段1</p>
     */
    public String getObText1() {
        return obText1;
    }

    /**
     * <p>备用字段1</p>
     */
    public void setObText1(String obText1) {
        this.obText1 = obText1;
    }

    /**
     * <p>备用字段2</p>
     */
    public String getObText2() {
        return obText2;
    }

    /**
     * <p>备用字段2</p>
     */
    public void setObText2(String obText2) {
        this.obText2 = obText2;
    }

    /**
     * <p>备用字段3</p>
     */
    public String getObText3() {
        return obText3;
    }

    /**
     * <p>备用字段3</p>
     */
    public void setObText3(String obText3) {
        this.obText3 = obText3;
    }

    /**
     * <p>备用字段4</p>
     */
    public String getObText4() {
        return obText4;
    }

    /**
     * <p>备用字段4</p>
     */
    public void setObText4(String obText4) {
        this.obText4 = obText4;
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
        map.put("productCd", productCd);
        map.put("fieldEn", fieldEn);
        map.put("fieldRegion", fieldRegion);
        map.put("fieldSort", fieldSort);
        map.put("ifRequiredItem", ifRequiredItem);
        map.put("ifReviewItem", ifReviewItem);
        map.put("reviewSort", reviewSort);
        map.put("isInput", isInput);
        map.put("isReview", isReview);
        map.put("value1", value1);
        map.put("value2", value2);
        map.put("value3", value3);
        map.put("value4", value4);
        map.put("value5", value5);
        map.put("obText1", obText1);
        map.put("obText2", obText2);
        map.put("obText3", obText3);
        map.put("obText4", obText4);
        map.put("remark", remark);
        map.put("createUser", createUser);
        map.put("createDate", createDate);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("productCd")) this.setProductCd(DataTypeUtils.getStringValue(map.get("productCd")));
        if (map.containsKey("fieldEn")) this.setFieldEn(DataTypeUtils.getStringValue(map.get("fieldEn")));
        if (map.containsKey("fieldRegion")) this.setFieldRegion(DataTypeUtils.getStringValue(map.get("fieldRegion")));
        if (map.containsKey("fieldSort")) this.setFieldSort(DataTypeUtils.getIntegerValue(map.get("fieldSort")));
        if (map.containsKey("ifRequiredItem")) this.setIfRequiredItem(DataTypeUtils.getStringValue(map.get("ifRequiredItem")));
        if (map.containsKey("ifReviewItem")) this.setIfReviewItem(DataTypeUtils.getStringValue(map.get("ifReviewItem")));
        if (map.containsKey("reviewSort")) this.setReviewSort(DataTypeUtils.getIntegerValue(map.get("reviewSort")));
        if (map.containsKey("isInput")) this.setIsInput(DataTypeUtils.getStringValue(map.get("isInput")));
        if (map.containsKey("isReview")) this.setIsReview(DataTypeUtils.getStringValue(map.get("isReview")));
        if (map.containsKey("value1")) this.setValue1(DataTypeUtils.getStringValue(map.get("value1")));
        if (map.containsKey("value2")) this.setValue2(DataTypeUtils.getStringValue(map.get("value2")));
        if (map.containsKey("value3")) this.setValue3(DataTypeUtils.getStringValue(map.get("value3")));
        if (map.containsKey("value4")) this.setValue4(DataTypeUtils.getStringValue(map.get("value4")));
        if (map.containsKey("value5")) this.setValue5(DataTypeUtils.getStringValue(map.get("value5")));
        if (map.containsKey("obText1")) this.setObText1(DataTypeUtils.getStringValue(map.get("obText1")));
        if (map.containsKey("obText2")) this.setObText2(DataTypeUtils.getStringValue(map.get("obText2")));
        if (map.containsKey("obText3")) this.setObText3(DataTypeUtils.getStringValue(map.get("obText3")));
        if (map.containsKey("obText4")) this.setObText4(DataTypeUtils.getStringValue(map.get("obText4")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", productCd="+productCd+
        "productCd="+productCd+
        ", fieldEn="+fieldEn+
        "fieldEn="+fieldEn+
        ", fieldRegion="+fieldRegion+
        "fieldRegion="+fieldRegion+
        ", fieldSort="+fieldSort+
        "fieldSort="+fieldSort+
        ", ifRequiredItem="+ifRequiredItem+
        "ifRequiredItem="+ifRequiredItem+
        ", ifReviewItem="+ifReviewItem+
        "ifReviewItem="+ifReviewItem+
        ", reviewSort="+reviewSort+
        "reviewSort="+reviewSort+
        ", isInput="+isInput+
        "isInput="+isInput+
        ", isReview="+isReview+
        "isReview="+isReview+
        ", value1="+value1+
        "value1="+value1+
        ", value2="+value2+
        "value2="+value2+
        ", value3="+value3+
        "value3="+value3+
        ", value4="+value4+
        "value4="+value4+
        ", value5="+value5+
        "value5="+value5+
        ", obText1="+obText1+
        "obText1="+obText1+
        ", obText2="+obText2+
        "obText2="+obText2+
        ", obText3="+obText3+
        "obText3="+obText3+
        ", obText4="+obText4+
        "obText4="+obText4+
        ", remark="+remark+
        "remark="+remark+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (productCd == null) productCd = "";
        if (fieldEn == null) fieldEn = "";
        if (fieldRegion == null) fieldRegion = "";
        if (fieldSort == null) fieldSort = 0;
        if (ifRequiredItem == null) ifRequiredItem = "";
        if (ifReviewItem == null) ifReviewItem = "";
        if (reviewSort == null) reviewSort = 0;
        if (isInput == null) isInput = "";
        if (isReview == null) isReview = "";
        if (value1 == null) value1 = "";
        if (value2 == null) value2 = "";
        if (value3 == null) value3 = "";
        if (value4 == null) value4 = "";
        if (value5 == null) value5 = "";
        if (obText1 == null) obText1 = "";
        if (obText2 == null) obText2 = "";
        if (obText3 == null) obText3 = "";
        if (obText4 == null) obText4 = "";
        if (remark == null) remark = "";
        if (createUser == null) createUser = "";
        if (createDate == null) createDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}