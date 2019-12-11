package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TM_ACL_PARAM参数
 * @author jjb
 */
public class TmAclParam implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private Integer id;

    private String type;

    private String typeName;

    private String code;

    private String codeName;

    private String value;

    private String value2;

    private String value3;

    private String value4;

    private String remark;

    private Integer sort;

    private String ifUsed;

    private String ifCanDel;

    private String catagory;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private Integer jpaVersion;

    /**
     * <p>ORG</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>ORG</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * <p>主键标识</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>主键标识</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>参数类型</p>
     */
    public String getType() {
        return type;
    }

    /**
     * <p>参数类型</p>
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <p>参数名称</p>
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * <p>参数名称</p>
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * <p>参数代码</p>
     */
    public String getCode() {
        return code;
    }

    /**
     * <p>参数代码</p>
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * <p>参数代码名称</p>
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * <p>参数代码名称</p>
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * <p>值</p>
     */
    public String getValue() {
        return value;
    }

    /**
     * <p>值</p>
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * <p>值2</p>
     */
    public String getValue2() {
        return value2;
    }

    /**
     * <p>值2</p>
     */
    public void setValue2(String value2) {
        this.value2 = value2;
    }

    /**
     * <p>值3</p>
     */
    public String getValue3() {
        return value3;
    }

    /**
     * <p>值3</p>
     */
    public void setValue3(String value3) {
        this.value3 = value3;
    }

    /**
     * <p>值4</p>
     */
    public String getValue4() {
        return value4;
    }

    /**
     * <p>值4</p>
     */
    public void setValue4(String value4) {
        this.value4 = value4;
    }

    /**
     * <p>说明</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>说明</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * <p>排序</p>
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * <p>排序</p>
     */
    public void setSort(Integer sort) {
        this.sort = sort;
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

    /**
     * <p>大类</p>
     */
    public String getCatagory() {
        return catagory;
    }

    /**
     * <p>大类</p>
     */
    public void setCatagory(String catagory) {
        this.catagory = catagory;
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
     * <p>创建用户</p>
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * <p>创建用户</p>
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * <p>维护时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>维护时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * <p>维护用户</p>
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * <p>维护用户</p>
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * <p>版本</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>版本</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("org", org);
        map.put("id", id);
        map.put("type", type);
        map.put("typeName", typeName);
        map.put("code", code);
        map.put("codeName", codeName);
        map.put("value", value);
        map.put("value2", value2);
        map.put("value3", value3);
        map.put("value4", value4);
        map.put("remark", remark);
        map.put("sort", sort);
        map.put("ifUsed", ifUsed);
        map.put("ifCanDel", ifCanDel);
        map.put("catagory", catagory);
        map.put("createDate", createDate);
        map.put("createBy", createBy);
        map.put("updateDate", updateDate);
        map.put("updateBy", updateBy);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("type")) this.setType(DataTypeUtils.getStringValue(map.get("type")));
        if (map.containsKey("typeName")) this.setTypeName(DataTypeUtils.getStringValue(map.get("typeName")));
        if (map.containsKey("code")) this.setCode(DataTypeUtils.getStringValue(map.get("code")));
        if (map.containsKey("codeName")) this.setCodeName(DataTypeUtils.getStringValue(map.get("codeName")));
        if (map.containsKey("value")) this.setValue(DataTypeUtils.getStringValue(map.get("value")));
        if (map.containsKey("value2")) this.setValue2(DataTypeUtils.getStringValue(map.get("value2")));
        if (map.containsKey("value3")) this.setValue3(DataTypeUtils.getStringValue(map.get("value3")));
        if (map.containsKey("value4")) this.setValue4(DataTypeUtils.getStringValue(map.get("value4")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("sort")) this.setSort(DataTypeUtils.getIntegerValue(map.get("sort")));
        if (map.containsKey("ifUsed")) this.setIfUsed(DataTypeUtils.getStringValue(map.get("ifUsed")));
        if (map.containsKey("ifCanDel")) this.setIfCanDel(DataTypeUtils.getStringValue(map.get("ifCanDel")));
        if (map.containsKey("catagory")) this.setCatagory(DataTypeUtils.getStringValue(map.get("catagory")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createBy")) this.setCreateBy(DataTypeUtils.getStringValue(map.get("createBy")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateBy")) this.setUpdateBy(DataTypeUtils.getStringValue(map.get("updateBy")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", org="+org+
        "org="+org+
        ", id="+id+
        "id="+id+
        ", type="+type+
        "type="+type+
        ", typeName="+typeName+
        "typeName="+typeName+
        ", code="+code+
        "code="+code+
        ", codeName="+codeName+
        "codeName="+codeName+
        ", value="+value+
        "value="+value+
        ", value2="+value2+
        "value2="+value2+
        ", value3="+value3+
        "value3="+value3+
        ", value4="+value4+
        "value4="+value4+
        ", remark="+remark+
        "remark="+remark+
        ", sort="+sort+
        "sort="+sort+
        ", ifUsed="+ifUsed+
        "ifUsed="+ifUsed+
        ", ifCanDel="+ifCanDel+
        "ifCanDel="+ifCanDel+
        ", catagory="+catagory+
        "catagory="+catagory+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createBy="+createBy+
        "createBy="+createBy+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateBy="+updateBy+
        "updateBy="+updateBy+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (type == null) type = "";
        if (typeName == null) typeName = "";
        if (code == null) code = "";
        if (codeName == null) codeName = "";
        if (value == null) value = "";
        if (value2 == null) value2 = "";
        if (value3 == null) value3 = "";
        if (value4 == null) value4 = "";
        if (remark == null) remark = "";
        if (sort == null) sort = 0;
        if (ifUsed == null) ifUsed = "";
        if (ifCanDel == null) ifCanDel = "";
        if (catagory == null) catagory = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}