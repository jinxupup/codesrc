package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 批量实例表
 * @author jjb
 */
public class TmBthInst implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer bthInstId;

    private Integer instanceId;

    private String batchStatus;

    private Long executionId;

    private Integer jpaVersion;

    private String enableFlag;

    private Date batchCompletedDate;

    private Date startTime;

    private Date endTime;

    private String batchOptStatus;

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
     * <p>BTH_INST_ID</p>
     */
    public Integer getBthInstId() {
        return bthInstId;
    }

    /**
     * <p>BTH_INST_ID</p>
     */
    public void setBthInstId(Integer bthInstId) {
        this.bthInstId = bthInstId;
    }

    /**
     * <p>实例号</p>
     */
    public Integer getInstanceId() {
        return instanceId;
    }

    /**
     * <p>实例号</p>
     */
    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    /**
     * <p>批量处理状态</p>
     * <p>这里与 org.springframework.batch.core.BatchStatus一致，不直接引用的原因是避免造成赖项问题以及需要生成DomainClient</p>
     */
    public String getBatchStatus() {
        return batchStatus;
    }

    /**
     * <p>批量处理状态</p>
     * <p>这里与 org.springframework.batch.core.BatchStatus一致，不直接引用的原因是避免造成赖项问题以及需要生成DomainClient</p>
     */
    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    /**
     * <p>执行ID</p>
     * <p>记录对应批量正在运行的id，以便restart</p>
     */
    public Long getExecutionId() {
        return executionId;
    }

    /**
     * <p>执行ID</p>
     * <p>记录对应批量正在运行的id，以便restart</p>
     */
    public void setExecutionId(Long executionId) {
        this.executionId = executionId;
    }

    /**
     * <p>乐观锁版本号</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观锁版本号</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    /**
     * <p>是否启用</p>
     */
    public String getEnableFlag() {
        return enableFlag;
    }

    /**
     * <p>是否启用</p>
     */
    public void setEnableFlag(String enableFlag) {
        this.enableFlag = enableFlag;
    }

    /**
     * <p>上次批量完成日期</p>
     */
    public Date getBatchCompletedDate() {
        return batchCompletedDate;
    }

    /**
     * <p>上次批量完成日期</p>
     */
    public void setBatchCompletedDate(Date batchCompletedDate) {
        this.batchCompletedDate = batchCompletedDate;
    }

    /**
     * <p>开始时间</p>
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * <p>开始时间</p>
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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
     * <p>批量操作状态</p>
     */
    public String getBatchOptStatus() {
        return batchOptStatus;
    }

    /**
     * <p>批量操作状态</p>
     */
    public void setBatchOptStatus(String batchOptStatus) {
        this.batchOptStatus = batchOptStatus;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("bthInstId", bthInstId);
        map.put("instanceId", instanceId);
        map.put("batchStatus", batchStatus);
        map.put("executionId", executionId);
        map.put("jpaVersion", jpaVersion);
        map.put("enableFlag", enableFlag);
        map.put("batchCompletedDate", batchCompletedDate);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("batchOptStatus", batchOptStatus);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("bthInstId")) this.setBthInstId(DataTypeUtils.getIntegerValue(map.get("bthInstId")));
        if (map.containsKey("instanceId")) this.setInstanceId(DataTypeUtils.getIntegerValue(map.get("instanceId")));
        if (map.containsKey("batchStatus")) this.setBatchStatus(DataTypeUtils.getStringValue(map.get("batchStatus")));
        if (map.containsKey("executionId")) this.setExecutionId(DataTypeUtils.getLongValue(map.get("executionId")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("enableFlag")) this.setEnableFlag(DataTypeUtils.getStringValue(map.get("enableFlag")));
        if (map.containsKey("batchCompletedDate")) this.setBatchCompletedDate(DataTypeUtils.getDateValue(map.get("batchCompletedDate")));
        if (map.containsKey("startTime")) this.setStartTime(DataTypeUtils.getDateValue(map.get("startTime")));
        if (map.containsKey("endTime")) this.setEndTime(DataTypeUtils.getDateValue(map.get("endTime")));
        if (map.containsKey("batchOptStatus")) this.setBatchOptStatus(DataTypeUtils.getStringValue(map.get("batchOptStatus")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", bthInstId="+bthInstId+
        "bthInstId="+bthInstId+
        ", instanceId="+instanceId+
        "instanceId="+instanceId+
        ", batchStatus="+batchStatus+
        "batchStatus="+batchStatus+
        ", executionId="+executionId+
        "executionId="+executionId+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", enableFlag="+enableFlag+
        "enableFlag="+enableFlag+
        ", batchCompletedDate="+batchCompletedDate+
        "batchCompletedDate="+batchCompletedDate+
        ", startTime="+startTime+
        "startTime="+startTime+
        ", endTime="+endTime+
        "endTime="+endTime+
        ", batchOptStatus="+batchOptStatus+
        "batchOptStatus="+batchOptStatus+
        "]";
    }

    public void fillDefaultValues() {
        if (bthInstId == null) bthInstId = 0;
        if (instanceId == null) instanceId = 0;
        if (batchStatus == null) batchStatus = "";
        if (executionId == null) executionId = 0l;
        if (jpaVersion == null) jpaVersion = 0;
        if (enableFlag == null) enableFlag = "";
        if (batchCompletedDate == null) batchCompletedDate = new Date();
        if (startTime == null) startTime = new Date();
        if (endTime == null) endTime = new Date();
        if (batchOptStatus == null) batchOptStatus = "";
    }
}