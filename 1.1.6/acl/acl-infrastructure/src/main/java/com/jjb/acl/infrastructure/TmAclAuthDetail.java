package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 授权审核明细信息表
 * @author jjb
 */
public class TmAclAuthDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private Integer logId;

    private Integer roleId;

    private String roleName;

    private Integer userId;

    private String userNo;

    private String userName;

    private String sysType;

    private String resourceCode;

    private String resourceName;

    private String type;

    private Integer jpaVersion;

    /**
     * <p>主键ID</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>主键ID</p>
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
     * <p>日志记录表id</p>
     */
    public Integer getLogId() {
        return logId;
    }

    /**
     * <p>日志记录表id</p>
     */
    public void setLogId(Integer logId) {
        this.logId = logId;
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
     * <p>角色名称</p>
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * <p>角色名称</p>
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * <p>用户ID</p>
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * <p>用户ID</p>
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * <p>登陆名</p>
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * <p>登陆名</p>
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * <p>姓名</p>
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <p>姓名</p>
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
        map.put("id", id);
        map.put("org", org);
        map.put("logId", logId);
        map.put("roleId", roleId);
        map.put("roleName", roleName);
        map.put("userId", userId);
        map.put("userNo", userNo);
        map.put("userName", userName);
        map.put("sysType", sysType);
        map.put("resourceCode", resourceCode);
        map.put("resourceName", resourceName);
        map.put("type", type);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("logId")) this.setLogId(DataTypeUtils.getIntegerValue(map.get("logId")));
        if (map.containsKey("roleId")) this.setRoleId(DataTypeUtils.getIntegerValue(map.get("roleId")));
        if (map.containsKey("roleName")) this.setRoleName(DataTypeUtils.getStringValue(map.get("roleName")));
        if (map.containsKey("userId")) this.setUserId(DataTypeUtils.getIntegerValue(map.get("userId")));
        if (map.containsKey("userNo")) this.setUserNo(DataTypeUtils.getStringValue(map.get("userNo")));
        if (map.containsKey("userName")) this.setUserName(DataTypeUtils.getStringValue(map.get("userName")));
        if (map.containsKey("sysType")) this.setSysType(DataTypeUtils.getStringValue(map.get("sysType")));
        if (map.containsKey("resourceCode")) this.setResourceCode(DataTypeUtils.getStringValue(map.get("resourceCode")));
        if (map.containsKey("resourceName")) this.setResourceName(DataTypeUtils.getStringValue(map.get("resourceName")));
        if (map.containsKey("type")) this.setType(DataTypeUtils.getStringValue(map.get("type")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", org="+org+
        "org="+org+
        ", logId="+logId+
        "logId="+logId+
        ", roleId="+roleId+
        "roleId="+roleId+
        ", roleName="+roleName+
        "roleName="+roleName+
        ", userId="+userId+
        "userId="+userId+
        ", userNo="+userNo+
        "userNo="+userNo+
        ", userName="+userName+
        "userName="+userName+
        ", sysType="+sysType+
        "sysType="+sysType+
        ", resourceCode="+resourceCode+
        "resourceCode="+resourceCode+
        ", resourceName="+resourceName+
        "resourceName="+resourceName+
        ", type="+type+
        "type="+type+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (logId == null) logId = 0;
        if (roleId == null) roleId = 0;
        if (roleName == null) roleName = "";
        if (userId == null) userId = 0;
        if (userNo == null) userNo = "";
        if (userName == null) userName = "";
        if (sysType == null) sysType = "";
        if (resourceCode == null) resourceCode = "";
        if (resourceName == null) resourceName = "";
        if (type == null) type = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}