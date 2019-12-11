package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请件标签表
 * @author jjb
 */
public class TmAppFlag implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String appNo;

    private String flagType;

    private String flagCode;

    private String flagDesc;

    private String flagStatus;

    private String flagLevel;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

    private Integer jpaVersion;

    /**
     * <p>ID</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>ID</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>申请件编号</p>
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * <p>申请件编号</p>
     */
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    /**
     * <p>标签类型</p>
     */
    public String getFlagType() {
        return flagType;
    }

    /**
     * <p>标签类型</p>
     */
    public void setFlagType(String flagType) {
        this.flagType = flagType;
    }

    /**
     * <p>标签代码</p>
     */
    public String getFlagCode() {
        return flagCode;
    }

    /**
     * <p>标签代码</p>
     */
    public void setFlagCode(String flagCode) {
        this.flagCode = flagCode;
    }

    /**
     * <p>标签描述</p>
     */
    public String getFlagDesc() {
        return flagDesc;
    }

    /**
     * <p>标签描述</p>
     */
    public void setFlagDesc(String flagDesc) {
        this.flagDesc = flagDesc;
    }

    /**
     * <p>标签状态</p>
     */
    public String getFlagStatus() {
        return flagStatus;
    }

    /**
     * <p>标签状态</p>
     */
    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
    }

    /**
     * <p>标签等级</p>
     */
    public String getFlagLevel() {
        return flagLevel;
    }

    /**
     * <p>标签等级</p>
     */
    public void setFlagLevel(String flagLevel) {
        this.flagLevel = flagLevel;
    }

    /**
     * <p>创建日期</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>创建日期</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>创建人</p>
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>创建人</p>
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
     * <p>修改人</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>修改人</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("appNo", appNo);
        map.put("flagType", flagType);
        map.put("flagCode", flagCode);
        map.put("flagDesc", flagDesc);
        map.put("flagStatus", flagStatus);
        map.put("flagLevel", flagLevel);
        map.put("createDate", createDate);
        map.put("createUser", createUser);
        map.put("updateDate", updateDate);
        map.put("updateUser", updateUser);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("flagType")) this.setFlagType(DataTypeUtils.getStringValue(map.get("flagType")));
        if (map.containsKey("flagCode")) this.setFlagCode(DataTypeUtils.getStringValue(map.get("flagCode")));
        if (map.containsKey("flagDesc")) this.setFlagDesc(DataTypeUtils.getStringValue(map.get("flagDesc")));
        if (map.containsKey("flagStatus")) this.setFlagStatus(DataTypeUtils.getStringValue(map.get("flagStatus")));
        if (map.containsKey("flagLevel")) this.setFlagLevel(DataTypeUtils.getStringValue(map.get("flagLevel")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", flagType="+flagType+
        "flagType="+flagType+
        ", flagCode="+flagCode+
        "flagCode="+flagCode+
        ", flagDesc="+flagDesc+
        "flagDesc="+flagDesc+
        ", flagStatus="+flagStatus+
        "flagStatus="+flagStatus+
        ", flagLevel="+flagLevel+
        "flagLevel="+flagLevel+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (appNo == null) appNo = "";
        if (flagType == null) flagType = "";
        if (flagCode == null) flagCode = "";
        if (flagDesc == null) flagDesc = "";
        if (flagStatus == null) flagStatus = "";
        if (flagLevel == null) flagLevel = "";
        if (createDate == null) createDate = new Date();
        if (createUser == null) createUser = "";
        if (updateDate == null) updateDate = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}