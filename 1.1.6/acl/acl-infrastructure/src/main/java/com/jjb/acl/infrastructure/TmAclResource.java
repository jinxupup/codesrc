package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TM_ACL_RESOURCE资源
 * @author jjb
 */
public class TmAclResource implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String sysType;

    private String resourceCode;

    private String resourceName;

    private String type;

    private String parentResourceCode;

    private String parentPath;

    private String href;

    private String icon;

    private Integer sort;

    private String isUsed;

    private String remark;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private Integer jpaVersion;

    private String resourceAuthType;

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
     * <p>资源代码</p>
     */
    public String getResourceCode() {
        return resourceCode;
    }

    /**
     * <p>资源代码</p>
     */
    public void setResourceCode(String resourceCode) {
        this.resourceCode = resourceCode;
    }

    /**
     * <p>资源名称</p>
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * <p>资源名称</p>
     */
    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * <p>资源类型</p>
     */
    public String getType() {
        return type;
    }

    /**
     * <p>资源类型</p>
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <p>上级资源代码</p>
     */
    public String getParentResourceCode() {
        return parentResourceCode;
    }

    /**
     * <p>上级资源代码</p>
     */
    public void setParentResourceCode(String parentResourceCode) {
        this.parentResourceCode = parentResourceCode;
    }

    /**
     * <p>资源路径</p>
     */
    public String getParentPath() {
        return parentPath;
    }

    /**
     * <p>资源路径</p>
     */
    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    /**
     * <p>链接</p>
     */
    public String getHref() {
        return href;
    }

    /**
     * <p>链接</p>
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * <p>图标</p>
     */
    public String getIcon() {
        return icon;
    }

    /**
     * <p>图标</p>
     */
    public void setIcon(String icon) {
        this.icon = icon;
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
    public String getIsUsed() {
        return isUsed;
    }

    /**
     * <p>是否启用</p>
     */
    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
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

    /**
     * <p>资源授权类型</p>
     * <p>资源授权类型：ResourceAuthType</p>
     * <p>A-无需授权</p>
     * <p>B-上级授权</p>
     * <p>C-机构内授权</p>
     */
    public String getResourceAuthType() {
        return resourceAuthType;
    }

    /**
     * <p>资源授权类型</p>
     * <p>资源授权类型：ResourceAuthType</p>
     * <p>A-无需授权</p>
     * <p>B-上级授权</p>
     * <p>C-机构内授权</p>
     */
    public void setResourceAuthType(String resourceAuthType) {
        this.resourceAuthType = resourceAuthType;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("org", org);
        map.put("sysType", sysType);
        map.put("resourceCode", resourceCode);
        map.put("resourceName", resourceName);
        map.put("type", type);
        map.put("parentResourceCode", parentResourceCode);
        map.put("parentPath", parentPath);
        map.put("href", href);
        map.put("icon", icon);
        map.put("sort", sort);
        map.put("isUsed", isUsed);
        map.put("remark", remark);
        map.put("createDate", createDate);
        map.put("createBy", createBy);
        map.put("updateDate", updateDate);
        map.put("updateBy", updateBy);
        map.put("jpaVersion", jpaVersion);
        map.put("resourceAuthType", resourceAuthType);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("sysType")) this.setSysType(DataTypeUtils.getStringValue(map.get("sysType")));
        if (map.containsKey("resourceCode")) this.setResourceCode(DataTypeUtils.getStringValue(map.get("resourceCode")));
        if (map.containsKey("resourceName")) this.setResourceName(DataTypeUtils.getStringValue(map.get("resourceName")));
        if (map.containsKey("type")) this.setType(DataTypeUtils.getStringValue(map.get("type")));
        if (map.containsKey("parentResourceCode")) this.setParentResourceCode(DataTypeUtils.getStringValue(map.get("parentResourceCode")));
        if (map.containsKey("parentPath")) this.setParentPath(DataTypeUtils.getStringValue(map.get("parentPath")));
        if (map.containsKey("href")) this.setHref(DataTypeUtils.getStringValue(map.get("href")));
        if (map.containsKey("icon")) this.setIcon(DataTypeUtils.getStringValue(map.get("icon")));
        if (map.containsKey("sort")) this.setSort(DataTypeUtils.getIntegerValue(map.get("sort")));
        if (map.containsKey("isUsed")) this.setIsUsed(DataTypeUtils.getStringValue(map.get("isUsed")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createBy")) this.setCreateBy(DataTypeUtils.getStringValue(map.get("createBy")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateBy")) this.setUpdateBy(DataTypeUtils.getStringValue(map.get("updateBy")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("resourceAuthType")) this.setResourceAuthType(DataTypeUtils.getStringValue(map.get("resourceAuthType")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", org="+org+
        "org="+org+
        ", sysType="+sysType+
        "sysType="+sysType+
        ", resourceCode="+resourceCode+
        "resourceCode="+resourceCode+
        ", resourceName="+resourceName+
        "resourceName="+resourceName+
        ", type="+type+
        "type="+type+
        ", parentResourceCode="+parentResourceCode+
        "parentResourceCode="+parentResourceCode+
        ", parentPath="+parentPath+
        "parentPath="+parentPath+
        ", href="+href+
        "href="+href+
        ", icon="+icon+
        "icon="+icon+
        ", sort="+sort+
        "sort="+sort+
        ", isUsed="+isUsed+
        "isUsed="+isUsed+
        ", remark="+remark+
        "remark="+remark+
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
        ", resourceAuthType="+resourceAuthType+
        "resourceAuthType="+resourceAuthType+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (sysType == null) sysType = "";
        if (resourceCode == null) resourceCode = "";
        if (resourceName == null) resourceName = "";
        if (type == null) type = "";
        if (parentResourceCode == null) parentResourceCode = "";
        if (parentPath == null) parentPath = "";
        if (href == null) href = "";
        if (icon == null) icon = "";
        if (sort == null) sort = 0;
        if (isUsed == null) isUsed = "";
        if (remark == null) remark = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
        if (resourceAuthType == null) resourceAuthType = "";
    }
}