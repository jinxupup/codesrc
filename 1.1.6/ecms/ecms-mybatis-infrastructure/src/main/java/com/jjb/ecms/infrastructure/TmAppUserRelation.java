package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务员信息表
 * @author jjb
 */
public class TmAppUserRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String org;

    private String userNo;

    private String name;

    private String condition;

    private String highterUserNo;

    private String highterUser;

    private String lowerUserNo;

    private String lowerUser;

    private String rank;

    private String userType;

    private String groups;

    private Long jpaVersion;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private String checkGroups;

    /**
     * <p>ID</p>
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>ID</p>
     */
    public void setId(Long id) {
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
     * <p>业务员编号</p>
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * <p>业务员编号</p>
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * <p>姓名</p>
     */
    public String getName() {
        return name;
    }

    /**
     * <p>姓名</p>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>状态</p>
     */
    public String getCondition() {
        return condition;
    }

    /**
     * <p>状态</p>
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * <p>上级业务员编号</p>
     */
    public String getHighterUserNo() {
        return highterUserNo;
    }

    /**
     * <p>上级业务员编号</p>
     */
    public void setHighterUserNo(String highterUserNo) {
        this.highterUserNo = highterUserNo;
    }

    /**
     * <p>上级业务员</p>
     */
    public String getHighterUser() {
        return highterUser;
    }

    /**
     * <p>上级业务员</p>
     */
    public void setHighterUser(String highterUser) {
        this.highterUser = highterUser;
    }

    /**
     * <p>下级业务员编号</p>
     */
    public String getLowerUserNo() {
        return lowerUserNo;
    }

    /**
     * <p>下级业务员编号</p>
     */
    public void setLowerUserNo(String lowerUserNo) {
        this.lowerUserNo = lowerUserNo;
    }

    /**
     * <p>下级业务员</p>
     */
    public String getLowerUser() {
        return lowerUser;
    }

    /**
     * <p>下级业务员</p>
     */
    public void setLowerUser(String lowerUser) {
        this.lowerUser = lowerUser;
    }

    /**
     * <p>审批级别</p>
     */
    public String getRank() {
        return rank;
    }

    /**
     * <p>审批级别</p>
     */
    public void setRank(String rank) {
        this.rank = rank;
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
     * <p>组别</p>
     */
    public String getGroups() {
        return groups;
    }

    /**
     * <p>组别</p>
     */
    public void setGroups(String groups) {
        this.groups = groups;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public Long getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public void setJpaVersion(Long jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    /**
     * <p>新增时间</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>新增时间</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>新增人</p>
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>新增人</p>
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
     * <p>更新人</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>更新人</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * <p>审批组</p>
     */
    public String getCheckGroups() {
        return checkGroups;
    }

    /**
     * <p>审批组</p>
     */
    public void setCheckGroups(String checkGroups) {
        this.checkGroups = checkGroups;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("userNo", userNo);
        map.put("name", name);
        map.put("condition", condition);
        map.put("highterUserNo", highterUserNo);
        map.put("highterUser", highterUser);
        map.put("lowerUserNo", lowerUserNo);
        map.put("lowerUser", lowerUser);
        map.put("rank", rank);
        map.put("userType", userType);
        map.put("groups", groups);
        map.put("jpaVersion", jpaVersion);
        map.put("createDate", createDate);
        map.put("createUser", createUser);
        map.put("updateDate", updateDate);
        map.put("updateUser", updateUser);
        map.put("checkGroups", checkGroups);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getLongValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("userNo")) this.setUserNo(DataTypeUtils.getStringValue(map.get("userNo")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("condition")) this.setCondition(DataTypeUtils.getStringValue(map.get("condition")));
        if (map.containsKey("highterUserNo")) this.setHighterUserNo(DataTypeUtils.getStringValue(map.get("highterUserNo")));
        if (map.containsKey("highterUser")) this.setHighterUser(DataTypeUtils.getStringValue(map.get("highterUser")));
        if (map.containsKey("lowerUserNo")) this.setLowerUserNo(DataTypeUtils.getStringValue(map.get("lowerUserNo")));
        if (map.containsKey("lowerUser")) this.setLowerUser(DataTypeUtils.getStringValue(map.get("lowerUser")));
        if (map.containsKey("rank")) this.setRank(DataTypeUtils.getStringValue(map.get("rank")));
        if (map.containsKey("userType")) this.setUserType(DataTypeUtils.getStringValue(map.get("userType")));
        if (map.containsKey("groups")) this.setGroups(DataTypeUtils.getStringValue(map.get("groups")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getLongValue(map.get("jpaVersion")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("checkGroups")) this.setCheckGroups(DataTypeUtils.getStringValue(map.get("checkGroups")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", org="+org+
        "org="+org+
        ", userNo="+userNo+
        "userNo="+userNo+
        ", name="+name+
        "name="+name+
        ", condition="+condition+
        "condition="+condition+
        ", highterUserNo="+highterUserNo+
        "highterUserNo="+highterUserNo+
        ", highterUser="+highterUser+
        "highterUser="+highterUser+
        ", lowerUserNo="+lowerUserNo+
        "lowerUserNo="+lowerUserNo+
        ", lowerUser="+lowerUser+
        "lowerUser="+lowerUser+
        ", rank="+rank+
        "rank="+rank+
        ", userType="+userType+
        "userType="+userType+
        ", groups="+groups+
        "groups="+groups+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", checkGroups="+checkGroups+
        "checkGroups="+checkGroups+
        "]";
    }

    public void fillDefaultValues() {
        if (id == null) id = 0l;
        if (org == null) org = "";
        if (userNo == null) userNo = "";
        if (name == null) name = "";
        if (condition == null) condition = "";
        if (highterUserNo == null) highterUserNo = "";
        if (highterUser == null) highterUser = "";
        if (lowerUserNo == null) lowerUserNo = "";
        if (lowerUser == null) lowerUser = "";
        if (rank == null) rank = "";
        if (userType == null) userType = "";
        if (groups == null) groups = "";
        if (jpaVersion == null) jpaVersion = 0l;
        if (createDate == null) createDate = new Date();
        if (createUser == null) createUser = "";
        if (updateDate == null) updateDate = new Date();
        if (updateUser == null) updateUser = "";
        if (checkGroups == null) checkGroups = "";
    }
}