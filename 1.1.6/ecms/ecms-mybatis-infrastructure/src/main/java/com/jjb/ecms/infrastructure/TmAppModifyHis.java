package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请信息修改历史
 * @author jjb
 */
public class TmAppModifyHis implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String tableName;

    private String columnName;

    private String oldValue;

    private String newValue;

    private Date updateTime;

    private String updateUser;

    private String taskName;

    private String reservedField1;

    private String reservedField2;

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
     * <p>申请编号</p>
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * <p>申请编号</p>
     */
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    /**
     * <p>变更表名</p>
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * <p>变更表名</p>
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * <p>变更列名</p>
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * <p>变更列名</p>
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * <p>变更前的值</p>
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * <p>变更前的值</p>
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * <p>变更后的值</p>
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * <p>变更后的值</p>
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    /**
     * <p>变更时间</p>
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * <p>变更时间</p>
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * <p>变更人</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>变更人</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * <p>任务名</p>
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * <p>任务名</p>
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * <p>预留字段1</p>
     */
    public String getReservedField1() {
        return reservedField1;
    }

    /**
     * <p>预留字段1</p>
     */
    public void setReservedField1(String reservedField1) {
        this.reservedField1 = reservedField1;
    }

    /**
     * <p>预留字段2</p>
     */
    public String getReservedField2() {
        return reservedField2;
    }

    /**
     * <p>预留字段2</p>
     */
    public void setReservedField2(String reservedField2) {
        this.reservedField2 = reservedField2;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("tableName", tableName);
        map.put("columnName", columnName);
        map.put("oldValue", oldValue);
        map.put("newValue", newValue);
        map.put("updateTime", updateTime);
        map.put("updateUser", updateUser);
        map.put("taskName", taskName);
        map.put("reservedField1", reservedField1);
        map.put("reservedField2", reservedField2);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("tableName")) this.setTableName(DataTypeUtils.getStringValue(map.get("tableName")));
        if (map.containsKey("columnName")) this.setColumnName(DataTypeUtils.getStringValue(map.get("columnName")));
        if (map.containsKey("oldValue")) this.setOldValue(DataTypeUtils.getStringValue(map.get("oldValue")));
        if (map.containsKey("newValue")) this.setNewValue(DataTypeUtils.getStringValue(map.get("newValue")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("taskName")) this.setTaskName(DataTypeUtils.getStringValue(map.get("taskName")));
        if (map.containsKey("reservedField1")) this.setReservedField1(DataTypeUtils.getStringValue(map.get("reservedField1")));
        if (map.containsKey("reservedField2")) this.setReservedField2(DataTypeUtils.getStringValue(map.get("reservedField2")));
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
        ", appNo="+appNo+
        "appNo="+appNo+
        ", tableName="+tableName+
        "tableName="+tableName+
        ", columnName="+columnName+
        "columnName="+columnName+
        ", oldValue="+oldValue+
        "oldValue="+oldValue+
        ", newValue="+newValue+
        "newValue="+newValue+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", taskName="+taskName+
        "taskName="+taskName+
        ", reservedField1="+reservedField1+
        "reservedField1="+reservedField1+
        ", reservedField2="+reservedField2+
        "reservedField2="+reservedField2+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (tableName == null) tableName = "";
        if (columnName == null) columnName = "";
        if (oldValue == null) oldValue = "";
        if (newValue == null) newValue = "";
        if (updateTime == null) updateTime = new Date();
        if (updateUser == null) updateUser = "";
        if (taskName == null) taskName = "";
        if (reservedField1 == null) reservedField1 = "";
        if (reservedField2 == null) reservedField2 = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}