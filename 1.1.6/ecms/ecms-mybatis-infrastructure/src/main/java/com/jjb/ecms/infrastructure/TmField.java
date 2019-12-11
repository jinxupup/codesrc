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
public class TmField implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer fieldId;

    private String org;

    private String fieldEn;

    private String fieldName;

    private String tabName;

    private String tabDesc;

    private String ifUsed;

    private String ifCancel;

    private String defValue;

    private String dictType;

    private String fieldChange;

    private String textName;

    private String fieldNullable;

    private String showCode;

    private String fieldRegexp;

    private String maxLength;

    private String betweenMin;

    private String betweenMax;

    private String fieldAr;

    private String labelAr;

    private String inputAr;

    private String componentType;

    private String ifReadonly;

    private String createUser;

    private Date createDate;

    private String updateUser;

    private Date updateDate;

    private Integer jpaVersion;

    private String remark;

    private String isRow;

    /**
     * <p>字段ID</p>
     */
    public Integer getFieldId() {
        return fieldId;
    }

    /**
     * <p>字段ID</p>
     */
    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * <p>组织机构号</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>组织机构号</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * <p>字段</p>
     */
    public String getFieldEn() {
        return fieldEn;
    }

    /**
     * <p>字段</p>
     */
    public void setFieldEn(String fieldEn) {
        this.fieldEn = fieldEn;
    }

    /**
     * <p>字段名</p>
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * <p>字段名</p>
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * <p>表</p>
     */
    public String getTabName() {
        return tabName;
    }

    /**
     * <p>表</p>
     */
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    /**
     * <p>表描述</p>
     */
    public String getTabDesc() {
        return tabDesc;
    }

    /**
     * <p>表描述</p>
     */
    public void setTabDesc(String tabDesc) {
        this.tabDesc = tabDesc;
    }

    /**
     * <p>是否启用</p>
     */
    public String getIfUsed() {
        return ifUsed;
    }

    /**
     * <p>是否启用</p>
     */
    public void setIfUsed(String ifUsed) {
        this.ifUsed = ifUsed;
    }

    /**
     * <p>是否取消</p>
     */
    public String getIfCancel() {
        return ifCancel;
    }

    /**
     * <p>是否取消</p>
     */
    public void setIfCancel(String ifCancel) {
        this.ifCancel = ifCancel;
    }

    /**
     * <p>字段默认值</p>
     * <p>字段默认值</p>
     */
    public String getDefValue() {
        return defValue;
    }

    /**
     * <p>字段默认值</p>
     * <p>字段默认值</p>
     */
    public void setDefValue(String defValue) {
        this.defValue = defValue;
    }

    /**
     * <p>字典类型</p>
     * <p>字典类型</p>
     */
    public String getDictType() {
        return dictType;
    }

    /**
     * <p>字典类型</p>
     * <p>字典类型</p>
     */
    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    /**
     * <p>改变事件</p>
     * <p>改变事件</p>
     */
    public String getFieldChange() {
        return fieldChange;
    }

    /**
     * <p>改变事件</p>
     * <p>改变事件</p>
     */
    public void setFieldChange(String fieldChange) {
        this.fieldChange = fieldChange;
    }

    /**
     * <p>文本域name</p>
     * <p>文本域name</p>
     */
    public String getTextName() {
        return textName;
    }

    /**
     * <p>文本域name</p>
     * <p>文本域name</p>
     */
    public void setTextName(String textName) {
        this.textName = textName;
    }

    /**
     * <p>是否开启空选项</p>
     * <p>是否开启空选项</p>
     */
    public String getFieldNullable() {
        return fieldNullable;
    }

    /**
     * <p>是否开启空选项</p>
     * <p>是否开启空选项</p>
     */
    public void setFieldNullable(String fieldNullable) {
        this.fieldNullable = fieldNullable;
    }

    /**
     * <p>是否显示code</p>
     * <p>是否显示code</p>
     */
    public String getShowCode() {
        return showCode;
    }

    /**
     * <p>是否显示code</p>
     * <p>是否显示code</p>
     */
    public void setShowCode(String showCode) {
        this.showCode = showCode;
    }

    /**
     * <p>正则表达式</p>
     * <p>正则表达式</p>
     */
    public String getFieldRegexp() {
        return fieldRegexp;
    }

    /**
     * <p>正则表达式</p>
     * <p>正则表达式</p>
     */
    public void setFieldRegexp(String fieldRegexp) {
        this.fieldRegexp = fieldRegexp;
    }

    /**
     * <p>字符串最大长度</p>
     * <p>字符串最大长度</p>
     */
    public String getMaxLength() {
        return maxLength;
    }

    /**
     * <p>字符串最大长度</p>
     * <p>字符串最大长度</p>
     */
    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * <p>区间最小值</p>
     * <p>区间最小值</p>
     */
    public String getBetweenMin() {
        return betweenMin;
    }

    /**
     * <p>区间最小值</p>
     * <p>区间最小值</p>
     */
    public void setBetweenMin(String betweenMin) {
        this.betweenMin = betweenMin;
    }

    /**
     * <p>区间最大值</p>
     * <p>区间最大值</p>
     */
    public String getBetweenMax() {
        return betweenMax;
    }

    /**
     * <p>区间最大值</p>
     * <p>区间最大值</p>
     */
    public void setBetweenMax(String betweenMax) {
        this.betweenMax = betweenMax;
    }

    /**
     * <p>字段所占的空间比率</p>
     * <p>字段所占的空间比率</p>
     */
    public String getFieldAr() {
        return fieldAr;
    }

    /**
     * <p>字段所占的空间比率</p>
     * <p>字段所占的空间比率</p>
     */
    public void setFieldAr(String fieldAr) {
        this.fieldAr = fieldAr;
    }

    /**
     * <p>label所占的空间比率</p>
     * <p>label所占的空间比率</p>
     */
    public String getLabelAr() {
        return labelAr;
    }

    /**
     * <p>label所占的空间比率</p>
     * <p>label所占的空间比率</p>
     */
    public void setLabelAr(String labelAr) {
        this.labelAr = labelAr;
    }

    /**
     * <p>组件所占的空间比率</p>
     * <p>组件所占的空间比率</p>
     */
    public String getInputAr() {
        return inputAr;
    }

    /**
     * <p>组件所占的空间比率</p>
     * <p>组件所占的空间比率</p>
     */
    public void setInputAr(String inputAr) {
        this.inputAr = inputAr;
    }

    /**
     * <p>组件类型</p>
     * <p>组件类型</p>
     */
    public String getComponentType() {
        return componentType;
    }

    /**
     * <p>组件类型</p>
     * <p>组件类型</p>
     */
    public void setComponentType(String componentType) {
        this.componentType = componentType;
    }

    /**
     * <p>字段是否只读</p>
     * <p>字段是否只读</p>
     */
    public String getIfReadonly() {
        return ifReadonly;
    }

    /**
     * <p>字段是否只读</p>
     * <p>字段是否只读</p>
     */
    public void setIfReadonly(String ifReadonly) {
        this.ifReadonly = ifReadonly;
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
     * <p>修改人</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>修改人</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
     * <p>备注</p>
     * <p>备注</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>备注</p>
     * <p>备注</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <p>是否换行</p>
     */
    public String getIsRow() {
        return isRow;
    }

    /**
     * <p>是否换行</p>
     */
    public void setIsRow(String isRow) {
        this.isRow = isRow;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("fieldId", fieldId);
        map.put("org", org);
        map.put("fieldEn", fieldEn);
        map.put("fieldName", fieldName);
        map.put("tabName", tabName);
        map.put("tabDesc", tabDesc);
        map.put("ifUsed", ifUsed);
        map.put("ifCancel", ifCancel);
        map.put("defValue", defValue);
        map.put("dictType", dictType);
        map.put("fieldChange", fieldChange);
        map.put("textName", textName);
        map.put("fieldNullable", fieldNullable);
        map.put("showCode", showCode);
        map.put("fieldRegexp", fieldRegexp);
        map.put("maxLength", maxLength);
        map.put("betweenMin", betweenMin);
        map.put("betweenMax", betweenMax);
        map.put("fieldAr", fieldAr);
        map.put("labelAr", labelAr);
        map.put("inputAr", inputAr);
        map.put("componentType", componentType);
        map.put("ifReadonly", ifReadonly);
        map.put("createUser", createUser);
        map.put("createDate", createDate);
        map.put("updateUser", updateUser);
        map.put("updateDate", updateDate);
        map.put("jpaVersion", jpaVersion);
        map.put("remark", remark);
        map.put("isRow", isRow);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("fieldId")) this.setFieldId(DataTypeUtils.getIntegerValue(map.get("fieldId")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("fieldEn")) this.setFieldEn(DataTypeUtils.getStringValue(map.get("fieldEn")));
        if (map.containsKey("fieldName")) this.setFieldName(DataTypeUtils.getStringValue(map.get("fieldName")));
        if (map.containsKey("tabName")) this.setTabName(DataTypeUtils.getStringValue(map.get("tabName")));
        if (map.containsKey("tabDesc")) this.setTabDesc(DataTypeUtils.getStringValue(map.get("tabDesc")));
        if (map.containsKey("ifUsed")) this.setIfUsed(DataTypeUtils.getStringValue(map.get("ifUsed")));
        if (map.containsKey("ifCancel")) this.setIfCancel(DataTypeUtils.getStringValue(map.get("ifCancel")));
        if (map.containsKey("defValue")) this.setDefValue(DataTypeUtils.getStringValue(map.get("defValue")));
        if (map.containsKey("dictType")) this.setDictType(DataTypeUtils.getStringValue(map.get("dictType")));
        if (map.containsKey("fieldChange")) this.setFieldChange(DataTypeUtils.getStringValue(map.get("fieldChange")));
        if (map.containsKey("textName")) this.setTextName(DataTypeUtils.getStringValue(map.get("textName")));
        if (map.containsKey("fieldNullable")) this.setFieldNullable(DataTypeUtils.getStringValue(map.get("fieldNullable")));
        if (map.containsKey("showCode")) this.setShowCode(DataTypeUtils.getStringValue(map.get("showCode")));
        if (map.containsKey("fieldRegexp")) this.setFieldRegexp(DataTypeUtils.getStringValue(map.get("fieldRegexp")));
        if (map.containsKey("maxLength")) this.setMaxLength(DataTypeUtils.getStringValue(map.get("maxLength")));
        if (map.containsKey("betweenMin")) this.setBetweenMin(DataTypeUtils.getStringValue(map.get("betweenMin")));
        if (map.containsKey("betweenMax")) this.setBetweenMax(DataTypeUtils.getStringValue(map.get("betweenMax")));
        if (map.containsKey("fieldAr")) this.setFieldAr(DataTypeUtils.getStringValue(map.get("fieldAr")));
        if (map.containsKey("labelAr")) this.setLabelAr(DataTypeUtils.getStringValue(map.get("labelAr")));
        if (map.containsKey("inputAr")) this.setInputAr(DataTypeUtils.getStringValue(map.get("inputAr")));
        if (map.containsKey("componentType")) this.setComponentType(DataTypeUtils.getStringValue(map.get("componentType")));
        if (map.containsKey("ifReadonly")) this.setIfReadonly(DataTypeUtils.getStringValue(map.get("ifReadonly")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("isRow")) this.setIsRow(DataTypeUtils.getStringValue(map.get("isRow")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", fieldId="+fieldId+
        "fieldId="+fieldId+
        ", org="+org+
        "org="+org+
        ", fieldEn="+fieldEn+
        "fieldEn="+fieldEn+
        ", fieldName="+fieldName+
        "fieldName="+fieldName+
        ", tabName="+tabName+
        "tabName="+tabName+
        ", tabDesc="+tabDesc+
        "tabDesc="+tabDesc+
        ", ifUsed="+ifUsed+
        "ifUsed="+ifUsed+
        ", ifCancel="+ifCancel+
        "ifCancel="+ifCancel+
        ", defValue="+defValue+
        "defValue="+defValue+
        ", dictType="+dictType+
        "dictType="+dictType+
        ", fieldChange="+fieldChange+
        "fieldChange="+fieldChange+
        ", textName="+textName+
        "textName="+textName+
        ", fieldNullable="+fieldNullable+
        "fieldNullable="+fieldNullable+
        ", showCode="+showCode+
        "showCode="+showCode+
        ", fieldRegexp="+fieldRegexp+
        "fieldRegexp="+fieldRegexp+
        ", maxLength="+maxLength+
        "maxLength="+maxLength+
        ", betweenMin="+betweenMin+
        "betweenMin="+betweenMin+
        ", betweenMax="+betweenMax+
        "betweenMax="+betweenMax+
        ", fieldAr="+fieldAr+
        "fieldAr="+fieldAr+
        ", labelAr="+labelAr+
        "labelAr="+labelAr+
        ", inputAr="+inputAr+
        "inputAr="+inputAr+
        ", componentType="+componentType+
        "componentType="+componentType+
        ", ifReadonly="+ifReadonly+
        "ifReadonly="+ifReadonly+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", remark="+remark+
        "remark="+remark+
        ", isRow="+isRow+
        "isRow="+isRow+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (fieldEn == null) fieldEn = "";
        if (fieldName == null) fieldName = "";
        if (tabName == null) tabName = "";
        if (tabDesc == null) tabDesc = "";
        if (ifUsed == null) ifUsed = "";
        if (ifCancel == null) ifCancel = "";
        if (defValue == null) defValue = "";
        if (dictType == null) dictType = "";
        if (fieldChange == null) fieldChange = "";
        if (textName == null) textName = "";
        if (fieldNullable == null) fieldNullable = "";
        if (showCode == null) showCode = "";
        if (fieldRegexp == null) fieldRegexp = "";
        if (maxLength == null) maxLength = "";
        if (betweenMin == null) betweenMin = "";
        if (betweenMax == null) betweenMax = "";
        if (fieldAr == null) fieldAr = "";
        if (labelAr == null) labelAr = "";
        if (inputAr == null) inputAr = "";
        if (componentType == null) componentType = "";
        if (ifReadonly == null) ifReadonly = "";
        if (createUser == null) createUser = "";
        if (createDate == null) createDate = new Date();
        if (updateUser == null) updateUser = "";
        if (updateDate == null) updateDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
        if (remark == null) remark = "";
        if (isRow == null) isRow = "";
    }
}