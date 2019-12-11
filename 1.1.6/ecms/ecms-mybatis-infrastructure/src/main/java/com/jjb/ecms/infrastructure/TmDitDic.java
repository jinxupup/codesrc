package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 内部用参数表
 * @author jjb
 */
public class TmDitDic implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String dicType;

    private String tabName;

    private String formName;

    private String itemName;

    private String remark;

    private Integer jpaVersion;

    private String typeName;

    private String ifUsed;

    private String ifCanDel;

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
     * <p>参数类型</p>
     */
    public String getDicType() {
        return dicType;
    }

    /**
     * <p>参数类型</p>
     */
    public void setDicType(String dicType) {
        this.dicType = dicType;
    }

    /**
     * <p>TAB名</p>
     */
    public String getTabName() {
        return tabName;
    }

    /**
     * <p>TAB名</p>
     */
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    /**
     * <p>FORM名</p>
     */
    public String getFormName() {
        return formName;
    }

    /**
     * <p>FORM名</p>
     */
    public void setFormName(String formName) {
        this.formName = formName;
    }

    /**
     * <p>字段名称</p>
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * <p>字段名称</p>
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
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
     * <p>类型名称</p>
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * <p>类型名称</p>
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
     * <p>能否删除</p>
     */
    public String getIfCanDel() {
        return ifCanDel;
    }

    /**
     * <p>能否删除</p>
     */
    public void setIfCanDel(String ifCanDel) {
        this.ifCanDel = ifCanDel;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("dicType", dicType);
        map.put("tabName", tabName);
        map.put("formName", formName);
        map.put("itemName", itemName);
        map.put("remark", remark);
        map.put("jpaVersion", jpaVersion);
        map.put("typeName", typeName);
        map.put("ifUsed", ifUsed);
        map.put("ifCanDel", ifCanDel);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("dicType")) this.setDicType(DataTypeUtils.getStringValue(map.get("dicType")));
        if (map.containsKey("tabName")) this.setTabName(DataTypeUtils.getStringValue(map.get("tabName")));
        if (map.containsKey("formName")) this.setFormName(DataTypeUtils.getStringValue(map.get("formName")));
        if (map.containsKey("itemName")) this.setItemName(DataTypeUtils.getStringValue(map.get("itemName")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("typeName")) this.setTypeName(DataTypeUtils.getStringValue(map.get("typeName")));
        if (map.containsKey("ifUsed")) this.setIfUsed(DataTypeUtils.getStringValue(map.get("ifUsed")));
        if (map.containsKey("ifCanDel")) this.setIfCanDel(DataTypeUtils.getStringValue(map.get("ifCanDel")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", org="+org+
        "org="+org+
        ", dicType="+dicType+
        "dicType="+dicType+
        ", tabName="+tabName+
        "tabName="+tabName+
        ", formName="+formName+
        "formName="+formName+
        ", itemName="+itemName+
        "itemName="+itemName+
        ", remark="+remark+
        "remark="+remark+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", typeName="+typeName+
        "typeName="+typeName+
        ", ifUsed="+ifUsed+
        "ifUsed="+ifUsed+
        ", ifCanDel="+ifCanDel+
        "ifCanDel="+ifCanDel+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (dicType == null) dicType = "";
        if (tabName == null) tabName = "";
        if (formName == null) formName = "";
        if (itemName == null) itemName = "";
        if (remark == null) remark = "";
        if (jpaVersion == null) jpaVersion = 0;
        if (typeName == null) typeName = "";
        if (ifUsed == null) ifUsed = "";
        if (ifCanDel == null) ifCanDel = "";
    }
}