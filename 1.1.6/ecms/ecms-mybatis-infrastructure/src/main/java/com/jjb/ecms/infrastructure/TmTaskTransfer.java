package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 任务转移记录表
 * @author jjb
 */
public class TmTaskTransfer implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String transferType;

    private String taskKey;

    private String taskName;

    private String rtfState;

    private String owner;

    private String assigner;

    private Date claimTime;

    private Date endTime;

    private String status;

    private Integer jpaVersion;

    /**
     * <p>标识</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>标识</p>
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
     * <p>转移类型</p>
     */
    public String getTransferType() {
        return transferType;
    }

    /**
     * <p>转移类型</p>
     */
    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    /**
     * <p>任务键</p>
     */
    public String getTaskKey() {
        return taskKey;
    }

    /**
     * <p>任务键</p>
     */
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
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
     * <p>审批状态</p>
     */
    public String getRtfState() {
        return rtfState;
    }

    /**
     * <p>审批状态</p>
     */
    public void setRtfState(String rtfState) {
        this.rtfState = rtfState;
    }

    /**
     * <p>任务所属人</p>
     */
    public String getOwner() {
        return owner;
    }

    /**
     * <p>任务所属人</p>
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * <p>任务分配人</p>
     */
    public String getAssigner() {
        return assigner;
    }

    /**
     * <p>任务分配人</p>
     */
    public void setAssigner(String assigner) {
        this.assigner = assigner;
    }

    /**
     * <p>获取时间</p>
     */
    public Date getClaimTime() {
        return claimTime;
    }

    /**
     * <p>获取时间</p>
     */
    public void setClaimTime(Date claimTime) {
        this.claimTime = claimTime;
    }

    /**
     * <p>结束时间</p>
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * <p>结束时间</p>
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * <p>状态</p>
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>状态</p>
     */
    public void setStatus(String status) {
        this.status = status;
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
        map.put("transferType", transferType);
        map.put("taskKey", taskKey);
        map.put("taskName", taskName);
        map.put("rtfState", rtfState);
        map.put("owner", owner);
        map.put("assigner", assigner);
        map.put("claimTime", claimTime);
        map.put("endTime", endTime);
        map.put("status", status);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("transferType")) this.setTransferType(DataTypeUtils.getStringValue(map.get("transferType")));
        if (map.containsKey("taskKey")) this.setTaskKey(DataTypeUtils.getStringValue(map.get("taskKey")));
        if (map.containsKey("taskName")) this.setTaskName(DataTypeUtils.getStringValue(map.get("taskName")));
        if (map.containsKey("rtfState")) this.setRtfState(DataTypeUtils.getStringValue(map.get("rtfState")));
        if (map.containsKey("owner")) this.setOwner(DataTypeUtils.getStringValue(map.get("owner")));
        if (map.containsKey("assigner")) this.setAssigner(DataTypeUtils.getStringValue(map.get("assigner")));
        if (map.containsKey("claimTime")) this.setClaimTime(DataTypeUtils.getDateValue(map.get("claimTime")));
        if (map.containsKey("endTime")) this.setEndTime(DataTypeUtils.getDateValue(map.get("endTime")));
        if (map.containsKey("status")) this.setStatus(DataTypeUtils.getStringValue(map.get("status")));
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
        ", transferType="+transferType+
        "transferType="+transferType+
        ", taskKey="+taskKey+
        "taskKey="+taskKey+
        ", taskName="+taskName+
        "taskName="+taskName+
        ", rtfState="+rtfState+
        "rtfState="+rtfState+
        ", owner="+owner+
        "owner="+owner+
        ", assigner="+assigner+
        "assigner="+assigner+
        ", claimTime="+claimTime+
        "claimTime="+claimTime+
        ", endTime="+endTime+
        "endTime="+endTime+
        ", status="+status+
        "status="+status+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (transferType == null) transferType = "";
        if (taskKey == null) taskKey = "";
        if (taskName == null) taskName = "";
        if (rtfState == null) rtfState = "";
        if (owner == null) owner = "";
        if (assigner == null) assigner = "";
        if (claimTime == null) claimTime = new Date();
        if (endTime == null) endTime = new Date();
        if (status == null) status = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}