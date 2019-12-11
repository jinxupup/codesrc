package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 批量定义表
 * @author jjb
 */
public class TmBatch implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String batchName;

    private String jobName;

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
     * <p>批量名</p>
     * <p>中文描述，要写明批量功能</p>
     */
    public String getBatchName() {
        return batchName;
    }

    /**
     * <p>批量名</p>
     * <p>中文描述，要写明批量功能</p>
     */
    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    /**
     * <p>批量Job名</p>
     * <p>在jobs.xml中定义的job结点名</p>
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * <p>批量Job名</p>
     * <p>在jobs.xml中定义的job结点名</p>
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
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
        map.put("batchName", batchName);
        map.put("jobName", jobName);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("batchName")) this.setBatchName(DataTypeUtils.getStringValue(map.get("batchName")));
        if (map.containsKey("jobName")) this.setJobName(DataTypeUtils.getStringValue(map.get("jobName")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", batchName="+batchName+
        "batchName="+batchName+
        ", jobName="+jobName+
        "jobName="+jobName+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (batchName == null) batchName = "";
        if (jobName == null) jobName = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}