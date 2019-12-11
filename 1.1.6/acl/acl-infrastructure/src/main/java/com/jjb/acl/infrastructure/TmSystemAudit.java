package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统审计历史信息
 * @author jjb
 */
public class TmSystemAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String operatorId;

    private String paramKey;

    private String paramOperation;

    private String newObject;

    private String oldObject;

    private String updateUser;

    private Date updateDate;

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
     * <p>操作员ID</p>
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * <p>操作员ID</p>
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * <p>参数主键</p>
     */
    public String getParamKey() {
        return paramKey;
    }

    /**
     * <p>参数主键</p>
     */
    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    /**
     * <p>参数操作类型</p>
     */
    public String getParamOperation() {
        return paramOperation;
    }

    /**
     * <p>参数操作类型</p>
     */
    public void setParamOperation(String paramOperation) {
        this.paramOperation = paramOperation;
    }

    /**
     * <p>新值</p>
     */
    public String getNewObject() {
        return newObject;
    }

    /**
     * <p>新值</p>
     */
    public void setNewObject(String newObject) {
        this.newObject = newObject;
    }

    /**
     * <p>原始值</p>
     */
    public String getOldObject() {
        return oldObject;
    }

    /**
     * <p>原始值</p>
     */
    public void setOldObject(String oldObject) {
        this.oldObject = oldObject;
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
        map.put("operatorId", operatorId);
        map.put("paramKey", paramKey);
        map.put("paramOperation", paramOperation);
        map.put("newObject", newObject);
        map.put("oldObject", oldObject);
        map.put("updateUser", updateUser);
        map.put("updateDate", updateDate);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("operatorId")) this.setOperatorId(DataTypeUtils.getStringValue(map.get("operatorId")));
        if (map.containsKey("paramKey")) this.setParamKey(DataTypeUtils.getStringValue(map.get("paramKey")));
        if (map.containsKey("paramOperation")) this.setParamOperation(DataTypeUtils.getStringValue(map.get("paramOperation")));
        if (map.containsKey("newObject")) this.setNewObject(DataTypeUtils.getStringValue(map.get("newObject")));
        if (map.containsKey("oldObject")) this.setOldObject(DataTypeUtils.getStringValue(map.get("oldObject")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
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
        ", operatorId="+operatorId+
        "operatorId="+operatorId+
        ", paramKey="+paramKey+
        "paramKey="+paramKey+
        ", paramOperation="+paramOperation+
        "paramOperation="+paramOperation+
        ", newObject="+newObject+
        "newObject="+newObject+
        ", oldObject="+oldObject+
        "oldObject="+oldObject+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (operatorId == null) operatorId = "";
        if (paramKey == null) paramKey = "";
        if (paramOperation == null) paramOperation = "";
        if (newObject == null) newObject = "";
        if (oldObject == null) oldObject = "";
        if (updateUser == null) updateUser = "";
        if (updateDate == null) updateDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}