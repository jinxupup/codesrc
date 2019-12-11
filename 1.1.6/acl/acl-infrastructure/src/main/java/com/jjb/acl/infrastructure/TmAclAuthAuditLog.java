package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 授权审核日志表
 * @author jjb
 */
public class TmAclAuthAuditLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private Integer roleId;

    private String roleName;

    private Integer userId;

    private String userNo;

    private String userName;

    private String userType;

    private String auditType;

    private String visibleAuditBranchNo;

    private String status;

    private String auditBranchNo;

    private Date createDate;

    private String createBy;

    private String createBranchNo;

    private String createRemark;

    private Date checkDate;

    private String checkBy;

    private String checkRemark;

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
     * <p>用户类型</p>
     */
    public String getUserType() {
        return userType;
    }

    /**
     * <p>用户类型</p>
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * <p>审核类型</p>
     * <p>授权审核类型：AuditType</p>
     * <p>A-角色成员添加</p>
     * <p>B-角色资源添加</p>
     * <p>C-用户重置密码</p>
     */
    public String getAuditType() {
        return auditType;
    }

    /**
     * <p>审核类型</p>
     * <p>授权审核类型：AuditType</p>
     * <p>A-角色成员添加</p>
     * <p>B-角色资源添加</p>
     * <p>C-用户重置密码</p>
     */
    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    /**
     * <p>审核可见机构</p>
     * <p>以/分隔</p>
     */
    public String getVisibleAuditBranchNo() {
        return visibleAuditBranchNo;
    }

    /**
     * <p>审核可见机构</p>
     * <p>以/分隔</p>
     */
    public void setVisibleAuditBranchNo(String visibleAuditBranchNo) {
        this.visibleAuditBranchNo = visibleAuditBranchNo;
    }

    /**
     * <p>状态</p>
     * <p>授权审核状态：AuthStatus</p>
     * <p>W-待审核</p>
     * <p>A-审核通过</p>
     * <p>R-审核拒绝</p>
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>状态</p>
     * <p>授权审核状态：AuthStatus</p>
     * <p>W-待审核</p>
     * <p>A-审核通过</p>
     * <p>R-审核拒绝</p>
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * <p>审核机构</p>
     */
    public String getAuditBranchNo() {
        return auditBranchNo;
    }

    /**
     * <p>审核机构</p>
     */
    public void setAuditBranchNo(String auditBranchNo) {
        this.auditBranchNo = auditBranchNo;
    }

    /**
     * <p>提交时间</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>提交时间</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>提交员</p>
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * <p>提交员</p>
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * <p>申请机构</p>
     */
    public String getCreateBranchNo() {
        return createBranchNo;
    }

    /**
     * <p>申请机构</p>
     */
    public void setCreateBranchNo(String createBranchNo) {
        this.createBranchNo = createBranchNo;
    }

    /**
     * <p>申请备注</p>
     */
    public String getCreateRemark() {
        return createRemark;
    }

    /**
     * <p>申请备注</p>
     */
    public void setCreateRemark(String createRemark) {
        this.createRemark = createRemark;
    }

    /**
     * <p>审核时间</p>
     */
    public Date getCheckDate() {
        return checkDate;
    }

    /**
     * <p>审核时间</p>
     */
    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * <p>审核员</p>
     */
    public String getCheckBy() {
        return checkBy;
    }

    /**
     * <p>审核员</p>
     */
    public void setCheckBy(String checkBy) {
        this.checkBy = checkBy;
    }

    /**
     * <p>审核备注</p>
     */
    public String getCheckRemark() {
        return checkRemark;
    }

    /**
     * <p>审核备注</p>
     */
    public void setCheckRemark(String checkRemark) {
        this.checkRemark = checkRemark;
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
        map.put("roleId", roleId);
        map.put("roleName", roleName);
        map.put("userId", userId);
        map.put("userNo", userNo);
        map.put("userName", userName);
        map.put("userType", userType);
        map.put("auditType", auditType);
        map.put("visibleAuditBranchNo", visibleAuditBranchNo);
        map.put("status", status);
        map.put("auditBranchNo", auditBranchNo);
        map.put("createDate", createDate);
        map.put("createBy", createBy);
        map.put("createBranchNo", createBranchNo);
        map.put("createRemark", createRemark);
        map.put("checkDate", checkDate);
        map.put("checkBy", checkBy);
        map.put("checkRemark", checkRemark);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("roleId")) this.setRoleId(DataTypeUtils.getIntegerValue(map.get("roleId")));
        if (map.containsKey("roleName")) this.setRoleName(DataTypeUtils.getStringValue(map.get("roleName")));
        if (map.containsKey("userId")) this.setUserId(DataTypeUtils.getIntegerValue(map.get("userId")));
        if (map.containsKey("userNo")) this.setUserNo(DataTypeUtils.getStringValue(map.get("userNo")));
        if (map.containsKey("userName")) this.setUserName(DataTypeUtils.getStringValue(map.get("userName")));
        if (map.containsKey("userType")) this.setUserType(DataTypeUtils.getStringValue(map.get("userType")));
        if (map.containsKey("auditType")) this.setAuditType(DataTypeUtils.getStringValue(map.get("auditType")));
        if (map.containsKey("visibleAuditBranchNo")) this.setVisibleAuditBranchNo(DataTypeUtils.getStringValue(map.get("visibleAuditBranchNo")));
        if (map.containsKey("status")) this.setStatus(DataTypeUtils.getStringValue(map.get("status")));
        if (map.containsKey("auditBranchNo")) this.setAuditBranchNo(DataTypeUtils.getStringValue(map.get("auditBranchNo")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createBy")) this.setCreateBy(DataTypeUtils.getStringValue(map.get("createBy")));
        if (map.containsKey("createBranchNo")) this.setCreateBranchNo(DataTypeUtils.getStringValue(map.get("createBranchNo")));
        if (map.containsKey("createRemark")) this.setCreateRemark(DataTypeUtils.getStringValue(map.get("createRemark")));
        if (map.containsKey("checkDate")) this.setCheckDate(DataTypeUtils.getDateValue(map.get("checkDate")));
        if (map.containsKey("checkBy")) this.setCheckBy(DataTypeUtils.getStringValue(map.get("checkBy")));
        if (map.containsKey("checkRemark")) this.setCheckRemark(DataTypeUtils.getStringValue(map.get("checkRemark")));
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
        ", userType="+userType+
        "userType="+userType+
        ", auditType="+auditType+
        "auditType="+auditType+
        ", visibleAuditBranchNo="+visibleAuditBranchNo+
        "visibleAuditBranchNo="+visibleAuditBranchNo+
        ", status="+status+
        "status="+status+
        ", auditBranchNo="+auditBranchNo+
        "auditBranchNo="+auditBranchNo+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createBy="+createBy+
        "createBy="+createBy+
        ", createBranchNo="+createBranchNo+
        "createBranchNo="+createBranchNo+
        ", createRemark="+createRemark+
        "createRemark="+createRemark+
        ", checkDate="+checkDate+
        "checkDate="+checkDate+
        ", checkBy="+checkBy+
        "checkBy="+checkBy+
        ", checkRemark="+checkRemark+
        "checkRemark="+checkRemark+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (roleId == null) roleId = 0;
        if (roleName == null) roleName = "";
        if (userId == null) userId = 0;
        if (userNo == null) userNo = "";
        if (userName == null) userName = "";
        if (userType == null) userType = "";
        if (auditType == null) auditType = "";
        if (visibleAuditBranchNo == null) visibleAuditBranchNo = "";
        if (status == null) status = "";
        if (auditBranchNo == null) auditBranchNo = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (createBranchNo == null) createBranchNo = "";
        if (createRemark == null) createRemark = "";
        if (checkDate == null) checkDate = new Date();
        if (checkBy == null) checkBy = "";
        if (checkRemark == null) checkRemark = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}