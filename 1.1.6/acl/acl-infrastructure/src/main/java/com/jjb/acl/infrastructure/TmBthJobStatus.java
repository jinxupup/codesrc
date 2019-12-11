package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 批量JOB状态
 * @author jjb
 */
public class TmBthJobStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date batchMidDate;

    private String batchMidInd;

    private Date batchBackDate;

    private String batchBackInd;

    private Date createTime;

    private Date batchTogetherDate;

    private String batchTogetherInd;

    private Date lastModifiedTime;

    private Integer jpaVersion;

    /**
     * <p>主键</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>主键</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>批中步骤批量处理时间</p>
     */
    public Date getBatchMidDate() {
        return batchMidDate;
    }

    /**
     * <p>批中步骤批量处理时间</p>
     */
    public void setBatchMidDate(Date batchMidDate) {
        this.batchMidDate = batchMidDate;
    }

    /**
     * <p>批中操作是否自动拉起</p>
     */
    public String getBatchMidInd() {
        return batchMidInd;
    }

    /**
     * <p>批中操作是否自动拉起</p>
     */
    public void setBatchMidInd(String batchMidInd) {
        this.batchMidInd = batchMidInd;
    }

    /**
     * <p>批后步骤批量处理时间</p>
     */
    public Date getBatchBackDate() {
        return batchBackDate;
    }

    /**
     * <p>批后步骤批量处理时间</p>
     */
    public void setBatchBackDate(Date batchBackDate) {
        this.batchBackDate = batchBackDate;
    }

    /**
     * <p>批后操作是否自动拉起</p>
     */
    public String getBatchBackInd() {
        return batchBackInd;
    }

    /**
     * <p>批后操作是否自动拉起</p>
     */
    public void setBatchBackInd(String batchBackInd) {
        this.batchBackInd = batchBackInd;
    }

    /**
     * <p>创建时间</p>
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <p>创建时间</p>
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * <p>批中和批后步骤批量处理时间</p>
     */
    public Date getBatchTogetherDate() {
        return batchTogetherDate;
    }

    /**
     * <p>批中和批后步骤批量处理时间</p>
     */
    public void setBatchTogetherDate(Date batchTogetherDate) {
        this.batchTogetherDate = batchTogetherDate;
    }

    /**
     * <p>批中和批后操作是否同时自动拉起</p>
     */
    public String getBatchTogetherInd() {
        return batchTogetherInd;
    }

    /**
     * <p>批中和批后操作是否同时自动拉起</p>
     */
    public void setBatchTogetherInd(String batchTogetherInd) {
        this.batchTogetherInd = batchTogetherInd;
    }

    /**
     * <p>最近一次维护操作时间</p>
     */
    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    /**
     * <p>最近一次维护操作时间</p>
     */
    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    /**
     * <p>版本号</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>版本号</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("batchMidDate", batchMidDate);
        map.put("batchMidInd", batchMidInd);
        map.put("batchBackDate", batchBackDate);
        map.put("batchBackInd", batchBackInd);
        map.put("createTime", createTime);
        map.put("batchTogetherDate", batchTogetherDate);
        map.put("batchTogetherInd", batchTogetherInd);
        map.put("lastModifiedTime", lastModifiedTime);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("batchMidDate")) this.setBatchMidDate(DataTypeUtils.getDateValue(map.get("batchMidDate")));
        if (map.containsKey("batchMidInd")) this.setBatchMidInd(DataTypeUtils.getStringValue(map.get("batchMidInd")));
        if (map.containsKey("batchBackDate")) this.setBatchBackDate(DataTypeUtils.getDateValue(map.get("batchBackDate")));
        if (map.containsKey("batchBackInd")) this.setBatchBackInd(DataTypeUtils.getStringValue(map.get("batchBackInd")));
        if (map.containsKey("createTime")) this.setCreateTime(DataTypeUtils.getDateValue(map.get("createTime")));
        if (map.containsKey("batchTogetherDate")) this.setBatchTogetherDate(DataTypeUtils.getDateValue(map.get("batchTogetherDate")));
        if (map.containsKey("batchTogetherInd")) this.setBatchTogetherInd(DataTypeUtils.getStringValue(map.get("batchTogetherInd")));
        if (map.containsKey("lastModifiedTime")) this.setLastModifiedTime(DataTypeUtils.getDateValue(map.get("lastModifiedTime")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", batchMidDate="+batchMidDate+
        "batchMidDate="+batchMidDate+
        ", batchMidInd="+batchMidInd+
        "batchMidInd="+batchMidInd+
        ", batchBackDate="+batchBackDate+
        "batchBackDate="+batchBackDate+
        ", batchBackInd="+batchBackInd+
        "batchBackInd="+batchBackInd+
        ", createTime="+createTime+
        "createTime="+createTime+
        ", batchTogetherDate="+batchTogetherDate+
        "batchTogetherDate="+batchTogetherDate+
        ", batchTogetherInd="+batchTogetherInd+
        "batchTogetherInd="+batchTogetherInd+
        ", lastModifiedTime="+lastModifiedTime+
        "lastModifiedTime="+lastModifiedTime+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (batchMidDate == null) batchMidDate = new Date();
        if (batchMidInd == null) batchMidInd = "";
        if (batchBackDate == null) batchBackDate = new Date();
        if (batchBackInd == null) batchBackInd = "";
        if (createTime == null) createTime = new Date();
        if (batchTogetherDate == null) batchTogetherDate = new Date();
        if (batchTogetherInd == null) batchTogetherInd = "";
        if (lastModifiedTime == null) lastModifiedTime = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}