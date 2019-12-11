package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库审计表
 * @author jjb
 */
public class TmAuditLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date operationTime;

    private String operationType;

    private String tableName;

    private String changeContents;

    private Integer userId;

    private String org;

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
     * <p>操作时间</p>
     */
    public Date getOperationTime() {
        return operationTime;
    }

    /**
     * <p>操作时间</p>
     */
    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    /**
     * <p>操作类型</p>
     * <p>insert,update,delete</p>
     */
    public String getOperationType() {
        return operationType;
    }

    /**
     * <p>操作类型</p>
     * <p>insert,update,delete</p>
     */
    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    /**
     * <p>表名</p>
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * <p>表名</p>
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * <p>内容</p>
     */
    public String getChangeContents() {
        return changeContents;
    }

    /**
     * <p>内容</p>
     */
    public void setChangeContents(String changeContents) {
        this.changeContents = changeContents;
    }

    /**
     * <p>操作员ID</p>
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * <p>操作员ID</p>
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
        map.put("operationTime", operationTime);
        map.put("operationType", operationType);
        map.put("tableName", tableName);
        map.put("changeContents", changeContents);
        map.put("userId", userId);
        map.put("org", org);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("operationTime")) this.setOperationTime(DataTypeUtils.getDateValue(map.get("operationTime")));
        if (map.containsKey("operationType")) this.setOperationType(DataTypeUtils.getStringValue(map.get("operationType")));
        if (map.containsKey("tableName")) this.setTableName(DataTypeUtils.getStringValue(map.get("tableName")));
        if (map.containsKey("changeContents")) this.setChangeContents(DataTypeUtils.getStringValue(map.get("changeContents")));
        if (map.containsKey("userId")) this.setUserId(DataTypeUtils.getIntegerValue(map.get("userId")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", operationTime="+operationTime+
        "operationTime="+operationTime+
        ", operationType="+operationType+
        "operationType="+operationType+
        ", tableName="+tableName+
        "tableName="+tableName+
        ", changeContents="+changeContents+
        "changeContents="+changeContents+
        ", userId="+userId+
        "userId="+userId+
        ", org="+org+
        "org="+org+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (operationTime == null) operationTime = new Date();
        if (operationType == null) operationType = "";
        if (tableName == null) tableName = "";
        if (changeContents == null) changeContents = "";
        if (userId == null) userId = 0;
        if (org == null) org = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}