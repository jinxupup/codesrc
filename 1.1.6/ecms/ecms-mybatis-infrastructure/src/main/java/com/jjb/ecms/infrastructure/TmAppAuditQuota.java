package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TM_APP_AUDIT_QUOTA
 * @author jjb
 */
public class TmAppAuditQuota implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String operatorId;

    private String taskName;

    private BigDecimal visibleMinimum;

    private BigDecimal visibleMaximum;

    private BigDecimal approvalMaximum;

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
     * <p>可见额度最低值</p>
     */
    public BigDecimal getVisibleMinimum() {
        return visibleMinimum;
    }

    /**
     * <p>可见额度最低值</p>
     */
    public void setVisibleMinimum(BigDecimal visibleMinimum) {
        this.visibleMinimum = visibleMinimum;
    }

    /**
     * <p>可见额度最高值</p>
     */
    public BigDecimal getVisibleMaximum() {
        return visibleMaximum;
    }

    /**
     * <p>可见额度最高值</p>
     */
    public void setVisibleMaximum(BigDecimal visibleMaximum) {
        this.visibleMaximum = visibleMaximum;
    }

    /**
     * <p>审批额度最高值</p>
     */
    public BigDecimal getApprovalMaximum() {
        return approvalMaximum;
    }

    /**
     * <p>审批额度最高值</p>
     */
    public void setApprovalMaximum(BigDecimal approvalMaximum) {
        this.approvalMaximum = approvalMaximum;
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
     * <p>修改日期</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>修改日期</p>
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
        map.put("taskName", taskName);
        map.put("visibleMinimum", visibleMinimum);
        map.put("visibleMaximum", visibleMaximum);
        map.put("approvalMaximum", approvalMaximum);
        map.put("updateUser", updateUser);
        map.put("updateDate", updateDate);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("operatorId")) this.setOperatorId(DataTypeUtils.getStringValue(map.get("operatorId")));
        if (map.containsKey("taskName")) this.setTaskName(DataTypeUtils.getStringValue(map.get("taskName")));
        if (map.containsKey("visibleMinimum")) this.setVisibleMinimum(DataTypeUtils.getBigDecimalValue(map.get("visibleMinimum")));
        if (map.containsKey("visibleMaximum")) this.setVisibleMaximum(DataTypeUtils.getBigDecimalValue(map.get("visibleMaximum")));
        if (map.containsKey("approvalMaximum")) this.setApprovalMaximum(DataTypeUtils.getBigDecimalValue(map.get("approvalMaximum")));
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
        ", taskName="+taskName+
        "taskName="+taskName+
        ", visibleMinimum="+visibleMinimum+
        "visibleMinimum="+visibleMinimum+
        ", visibleMaximum="+visibleMaximum+
        "visibleMaximum="+visibleMaximum+
        ", approvalMaximum="+approvalMaximum+
        "approvalMaximum="+approvalMaximum+
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
        if (taskName == null) taskName = "";
        if (visibleMinimum == null) visibleMinimum = BigDecimal.ZERO;
        if (visibleMaximum == null) visibleMaximum = BigDecimal.ZERO;
        if (approvalMaximum == null) approvalMaximum = BigDecimal.ZERO;
        if (updateUser == null) updateUser = "";
        if (updateDate == null) updateDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}