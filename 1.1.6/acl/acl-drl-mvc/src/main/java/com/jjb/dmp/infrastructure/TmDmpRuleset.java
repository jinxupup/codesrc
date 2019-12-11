package com.jjb.dmp.infrastructure;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jjb.unicorn.facility.util.DataTypeUtils;

/**
 * TM_DMP_RULE规则集表
 * @author jjb
 */
public class TmDmpRuleset implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String stId;

    private String rsId;

    private String ruleSetName;

    private String ruleSetType;

    private String ruleSetEnabled;

    private String isExclusive;

    private String ruleSetObject;

    private String remark;

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
     * <p>规则集ID</p>
     */
    public String getRsId() {
        return rsId;
    }

    /**
     * <p>规则集ID</p>
     */
    public void setRsId(String rsId) {
        this.rsId = rsId;
    }

    /**
     * <p>规则集名称</p>
     */
    public String getRuleSetName() {
        return ruleSetName;
    }

    /**
     * <p>规则集名称</p>
     */
    public void setRuleSetName(String ruleSetName) {
        this.ruleSetName = ruleSetName;
    }

    /**
     * <p>规则集类型</p>
     */
    public String getRuleSetType() {
        return ruleSetType;
    }

    /**
     * <p>规则集类型</p>
     */
    public void setRuleSetType(String ruleSetType) {
        this.ruleSetType = ruleSetType;
    }

    /**
     * <p>规则集是否启用</p>
     */
    public String getRuleSetEnabled() {
        return ruleSetEnabled;
    }

    /**
     * <p>规则集是否启用</p>
     */
    public void setRuleSetEnabled(String ruleSetEnabled) {
        this.ruleSetEnabled = ruleSetEnabled;
    }

    /**
     * <p>规则是否排他</p>
     */
    public String getIsExclusive() {
        return isExclusive;
    }

    /**
     * <p>规则是否排他</p>
     */
    public void setIsExclusive(String isExclusive) {
        this.isExclusive = isExclusive;
    }

    /**
     * <p>规则集对象</p>
     */
    public String getRuleSetObject() {
        return ruleSetObject;
    }

    /**
     * <p>规则集对象</p>
     */
    public void setRuleSetObject(String ruleSetObject) {
        this.ruleSetObject = ruleSetObject;
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
        map.put("org", org);
        map.put("stId", stId);
        map.put("rsId", rsId);
        map.put("ruleSetName", ruleSetName);
        map.put("ruleSetType", ruleSetType);
        map.put("ruleSetEnabled", ruleSetEnabled);
        map.put("isExclusive", isExclusive);
        map.put("ruleSetObject", ruleSetObject);
        map.put("remark", remark);
        map.put("createDate", createDate);
        map.put("createBy", createBy);
        map.put("updateDate", updateDate);
        map.put("updateBy", updateBy);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("stId")) this.setStId(DataTypeUtils.getStringValue(map.get("stId")));
        if (map.containsKey("rsId")) this.setRsId(DataTypeUtils.getStringValue(map.get("rsId")));
        if (map.containsKey("ruleSetName")) this.setRuleSetName(DataTypeUtils.getStringValue(map.get("ruleSetName")));
        if (map.containsKey("ruleSetType")) this.setRuleSetType(DataTypeUtils.getStringValue(map.get("ruleSetType")));
        if (map.containsKey("ruleSetEnabled")) this.setRuleSetEnabled(DataTypeUtils.getStringValue(map.get("ruleSetEnabled")));
        if (map.containsKey("isExclusive")) this.setIsExclusive(DataTypeUtils.getStringValue(map.get("isExclusive")));
        if (map.containsKey("ruleSetObject")) this.setRuleSetObject(DataTypeUtils.getStringValue(map.get("ruleSetObject")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
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
        "org="+org+
        "stId="+stId+
        "rsId="+rsId+
        "ruleSetName="+ruleSetName+
        "ruleSetType="+ruleSetType+
        "ruleSetEnabled="+ruleSetEnabled+
        "isExclusive="+isExclusive+
        "ruleSetObject="+ruleSetObject+
        "remark="+remark+
        "createDate="+createDate+
        "createBy="+createBy+
        "updateDate="+updateDate+
        "updateBy="+updateBy+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (stId == null) stId = "";
        if (rsId == null) rsId = "";
        if (ruleSetName == null) ruleSetName = "";
        if (ruleSetType == null) ruleSetType = "";
        if (ruleSetEnabled == null) ruleSetEnabled = "";
        if (isExclusive == null) isExclusive = "";
        if (ruleSetObject == null) ruleSetObject = "";
        if (remark == null) remark = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}