package com.jjb.dmp.infrastructure;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jjb.unicorn.facility.util.DataTypeUtils;

/**
 * TM_DMP_RULE规则表
 * @author jjb
 */
public class TmDmpRule implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String stId;

    private String rsId;

    private String ruleId;

    private String ruleName;

    private String ruleEnabled;

    private String ruleType;

    private Integer priority;

    private String remark;

    private String ruleObject;

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
     * <p>规则ID</p>
     */
    public String getRuleId() {
        return ruleId;
    }

    /**
     * <p>规则ID</p>
     */
    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    /**
     * <p>规则名称</p>
     */
    public String getRuleName() {
        return ruleName;
    }

    /**
     * <p>规则名称</p>
     */
    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    /**
     * <p>规则是否启用</p>
     */
    public String getRuleEnabled() {
        return ruleEnabled;
    }

    /**
     * <p>规则是否启用</p>
     */
    public void setRuleEnabled(String ruleEnabled) {
        this.ruleEnabled = ruleEnabled;
    }

    /**
     * <p>规则类型</p>
     */
    public String getRuleType() {
        return ruleType;
    }

    /**
     * <p>规则类型</p>
     */
    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * <p>优先级</p>
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * <p>优先级</p>
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
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
     * <p>规则对象</p>
     */
    public String getRuleObject() {
        return ruleObject;
    }

    /**
     * <p>规则对象</p>
     */
    public void setRuleObject(String ruleObject) {
        this.ruleObject = ruleObject;
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
        map.put("ruleId", ruleId);
        map.put("ruleName", ruleName);
        map.put("ruleEnabled", ruleEnabled);
        map.put("ruleType", ruleType);
        map.put("priority", priority);
        map.put("remark", remark);
        map.put("ruleObject", ruleObject);
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
        if (map.containsKey("ruleId")) this.setRuleId(DataTypeUtils.getStringValue(map.get("ruleId")));
        if (map.containsKey("ruleName")) this.setRuleName(DataTypeUtils.getStringValue(map.get("ruleName")));
        if (map.containsKey("ruleEnabled")) this.setRuleEnabled(DataTypeUtils.getStringValue(map.get("ruleEnabled")));
        if (map.containsKey("ruleType")) this.setRuleType(DataTypeUtils.getStringValue(map.get("ruleType")));
        if (map.containsKey("priority")) this.setPriority(DataTypeUtils.getIntegerValue(map.get("priority")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("ruleObject")) this.setRuleObject(DataTypeUtils.getStringValue(map.get("ruleObject")));
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
        "ruleId="+ruleId+
        "ruleName="+ruleName+
        "ruleEnabled="+ruleEnabled+
        "ruleType="+ruleType+
        "priority="+priority+
        "remark="+remark+
        "ruleObject="+ruleObject+
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
        if (ruleId == null) ruleId = "";
        if (ruleName == null) ruleName = "";
        if (ruleEnabled == null) ruleEnabled = "";
        if (ruleType == null) ruleType = "";
        if (priority == null) priority = 0;
        if (remark == null) remark = "";
        if (ruleObject == null) ruleObject = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}