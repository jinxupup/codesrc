package com.jjb.dmp.infrastructure;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jjb.unicorn.facility.util.DataTypeUtils;

/**
 * TM_DMP_STRATEGY策略方案
 * @author jjb
 */
public class TmDmpStrategy implements Serializable {
    private static final long serialVersionUID = 1L;

    private String stId;

    private String org;

    private String stName;

    private String remark;

    private String stClass;

    private String stKey;

    private String stObject;

    private String ifUsed;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private Integer jpaVersion;

    /**
     * <p>策略方案ID</p>
     */
    public String getStId() {
        return stId;
    }

    /**
     * <p>策略方案ID</p>
     */
    public void setStId(String stId) {
        this.stId = stId;
    }

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
     * <p>策略方案名称</p>
     */
    public String getStName() {
        return stName;
    }

    /**
     * <p>策略方案名称</p>
     */
    public void setStName(String stName) {
        this.stName = stName;
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
     * <p>策略方案JAVA类</p>
     */
    public String getStClass() {
        return stClass;
    }

    /**
     * <p>策略方案JAVA类</p>
     */
    public void setStClass(String stClass) {
        this.stClass = stClass;
    }

    /**
     * <p>关键值</p>
     */
    public String getStKey() {
        return stKey;
    }

    /**
     * <p>关键值</p>
     */
    public void setStKey(String stKey) {
        this.stKey = stKey;
    }

    /**
     * <p>对象</p>
     */
    public String getStObject() {
        return stObject;
    }

    /**
     * <p>对象</p>
     */
    public void setStObject(String stObject) {
        this.stObject = stObject;
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
     * <p>修改时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>修改时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * <p>修改用户</p>
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * <p>修改用户</p>
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
        map.put("stId", stId);
        map.put("org", org);
        map.put("stName", stName);
        map.put("remark", remark);
        map.put("stClass", stClass);
        map.put("stKey", stKey);
        map.put("stObject", stObject);
        map.put("ifUsed", ifUsed);
        map.put("createDate", createDate);
        map.put("createBy", createBy);
        map.put("updateDate", updateDate);
        map.put("updateBy", updateBy);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("stId")) this.setStId(DataTypeUtils.getStringValue(map.get("stId")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("stName")) this.setStName(DataTypeUtils.getStringValue(map.get("stName")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("stClass")) this.setStClass(DataTypeUtils.getStringValue(map.get("stClass")));
        if (map.containsKey("stKey")) this.setStKey(DataTypeUtils.getStringValue(map.get("stKey")));
        if (map.containsKey("stObject")) this.setStObject(DataTypeUtils.getStringValue(map.get("stObject")));
        if (map.containsKey("ifUsed")) this.setIfUsed(DataTypeUtils.getStringValue(map.get("ifUsed")));
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
        "stId="+stId+
        "org="+org+
        "stName="+stName+
        "remark="+remark+
        "stClass="+stClass+
        "stKey="+stKey+
        "stObject="+stObject+
        "ifUsed="+ifUsed+
        "createDate="+createDate+
        "createBy="+createBy+
        "updateDate="+updateDate+
        "updateBy="+updateBy+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (stId == null) stId = "";
        if (org == null) org = "";
        if (stName == null) stName = "";
        if (remark == null) remark = "";
        if (stClass == null) stClass = "";
        if (stKey == null) stKey = "";
        if (stObject == null) stObject = "";
        if (ifUsed == null) ifUsed = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}