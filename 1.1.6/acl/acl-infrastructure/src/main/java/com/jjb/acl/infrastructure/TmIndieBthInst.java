package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 单机构独立批量实例表
 * @author jjb
 */
public class TmIndieBthInst implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private Integer batchId;

    private Integer instanceId;

    private String batchStatus;

    private Long executionId;

    private String enableFlag;

    private Date batchCompletedDate;

    private String indieType;

    private Long indieTime;

    private String batchParam;

    private Date startTime;

    private Date endTime;

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
     * <p>BATCH_ID</p>
     */
    public Integer getBatchId() {
        return batchId;
    }

    /**
     * <p>BATCH_ID</p>
     */
    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
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
     * <p>是否启用断点续批</p>
     */
    public String getEnableFlag() {
        return enableFlag;
    }

    /**
     * <p>是否启用断点续批</p>
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
     * <p>独立类型（预留字段，暂不启用）</p>
     */
    public String getIndieType() {
        return indieType;
    }

    /**
     * <p>独立类型（预留字段，暂不启用）</p>
     */
    public void setIndieType(String indieType) {
        this.indieType = indieType;
    }

    /**
     * <p>独立批量启动毫秒数（用来区分独立批量）</p>
     */
    public Long getIndieTime() {
        return indieTime;
    }

    /**
     * <p>独立批量启动毫秒数（用来区分独立批量）</p>
     */
    public void setIndieTime(Long indieTime) {
        this.indieTime = indieTime;
    }

    /**
     * <p>当前执行批量参数</p>
     */
    public String getBatchParam() {
        return batchParam;
    }

    /**
     * <p>当前执行批量参数</p>
     */
    public void setBatchParam(String batchParam) {
        this.batchParam = batchParam;
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

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("batchId", batchId);
        map.put("instanceId", instanceId);
        map.put("batchStatus", batchStatus);
        map.put("executionId", executionId);
        map.put("enableFlag", enableFlag);
        map.put("batchCompletedDate", batchCompletedDate);
        map.put("indieType", indieType);
        map.put("indieTime", indieTime);
        map.put("batchParam", batchParam);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("batchId")) this.setBatchId(DataTypeUtils.getIntegerValue(map.get("batchId")));
        if (map.containsKey("instanceId")) this.setInstanceId(DataTypeUtils.getIntegerValue(map.get("instanceId")));
        if (map.containsKey("batchStatus")) this.setBatchStatus(DataTypeUtils.getStringValue(map.get("batchStatus")));
        if (map.containsKey("executionId")) this.setExecutionId(DataTypeUtils.getLongValue(map.get("executionId")));
        if (map.containsKey("enableFlag")) this.setEnableFlag(DataTypeUtils.getStringValue(map.get("enableFlag")));
        if (map.containsKey("batchCompletedDate")) this.setBatchCompletedDate(DataTypeUtils.getDateValue(map.get("batchCompletedDate")));
        if (map.containsKey("indieType")) this.setIndieType(DataTypeUtils.getStringValue(map.get("indieType")));
        if (map.containsKey("indieTime")) this.setIndieTime(DataTypeUtils.getLongValue(map.get("indieTime")));
        if (map.containsKey("batchParam")) this.setBatchParam(DataTypeUtils.getStringValue(map.get("batchParam")));
        if (map.containsKey("startTime")) this.setStartTime(DataTypeUtils.getDateValue(map.get("startTime")));
        if (map.containsKey("endTime")) this.setEndTime(DataTypeUtils.getDateValue(map.get("endTime")));
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
        ", batchId="+batchId+
        "batchId="+batchId+
        ", instanceId="+instanceId+
        "instanceId="+instanceId+
        ", batchStatus="+batchStatus+
        "batchStatus="+batchStatus+
        ", executionId="+executionId+
        "executionId="+executionId+
        ", enableFlag="+enableFlag+
        "enableFlag="+enableFlag+
        ", batchCompletedDate="+batchCompletedDate+
        "batchCompletedDate="+batchCompletedDate+
        ", indieType="+indieType+
        "indieType="+indieType+
        ", indieTime="+indieTime+
        "indieTime="+indieTime+
        ", batchParam="+batchParam+
        "batchParam="+batchParam+
        ", startTime="+startTime+
        "startTime="+startTime+
        ", endTime="+endTime+
        "endTime="+endTime+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (batchId == null) batchId = 0;
        if (instanceId == null) instanceId = 0;
        if (batchStatus == null) batchStatus = "";
        if (executionId == null) executionId = 0l;
        if (enableFlag == null) enableFlag = "";
        if (batchCompletedDate == null) batchCompletedDate = new Date();
        if (indieType == null) indieType = "";
        if (indieTime == null) indieTime = 0l;
        if (batchParam == null) batchParam = "";
        if (startTime == null) startTime = new Date();
        if (endTime == null) endTime = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}