package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TM_ACL_ROL_EXT_RESOURCE角色扩展资源
 * @author jjb
 */
public class TmAclRolExtResource implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private Integer roleId;

    private String resourceCode;

    private String resourceGroupCode;

    private String sysType;

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
     * <p>角色ID</p>
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * <p>角色ID</p>
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * <p>扩展资源代码</p>
     */
    public String getResourceCode() {
        return resourceCode;
    }

    /**
     * <p>扩展资源代码</p>
     */
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    /**
     * <p>扩展资源组代码</p>
     */
    public String getResourceGroupCode() {
        return resourceGroupCode;
    }

    /**
     * <p>扩展资源组代码</p>
     */
    public void setResourceGroupCode(String resourceGroupCode) {
        this.resourceGroupCode = resourceGroupCode;
    }

    /**
     * <p>系统类型</p>
     */
    public String getSysType() {
        return sysType;
    }

    /**
     * <p>系统类型</p>
     */
    public void setSysType(String sysType) {
        this.sysType = sysType;
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
        map.put("roleId", roleId);
        map.put("resourceCode", resourceCode);
        map.put("resourceGroupCode", resourceGroupCode);
        map.put("sysType", sysType);
        map.put("createDate", createDate);
        map.put("createBy", createBy);
        map.put("updateDate", updateDate);
        map.put("updateBy", updateBy);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("roleId")) this.setRoleId(DataTypeUtils.getIntegerValue(map.get("roleId")));
        if (map.containsKey("resourceCode")) this.setResourceCode(DataTypeUtils.getStringValue(map.get("resourceCode")));
        if (map.containsKey("resourceGroupCode")) this.setResourceGroupCode(DataTypeUtils.getStringValue(map.get("resourceGroupCode")));
        if (map.containsKey("sysType")) this.setSysType(DataTypeUtils.getStringValue(map.get("sysType")));
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
        ", roleId="+roleId+
        "roleId="+roleId+
        ", resourceCode="+resourceCode+
        "resourceCode="+resourceCode+
        ", resourceGroupCode="+resourceGroupCode+
        "resourceGroupCode="+resourceGroupCode+
        ", sysType="+sysType+
        "sysType="+sysType+
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
        if (roleId == null) roleId = 0;
        if (resourceCode == null) resourceCode = "";
        if (resourceGroupCode == null) resourceGroupCode = "";
        if (sysType == null) sysType = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}