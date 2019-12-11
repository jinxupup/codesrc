package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 已申请卡数据批量处理表
 * @author jjb
 */
public class TmEcmsBatch implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String ifWrittenTn;

    private String ifProcessed;

    private Date createDate;

    private Date updateTime;

    private Date processTime;

    private Long jpaVersion;

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
     * <p>是否导入成功</p>
     */
    public String getIfWrittenTn() {
        return ifWrittenTn;
    }

    /**
     * <p>是否导入成功</p>
     */
    public void setIfWrittenTn(String ifWrittenTn) {
        this.ifWrittenTn = ifWrittenTn;
    }

    /**
     * <p>是否保存完成</p>
     */
    public String getIfProcessed() {
        return ifProcessed;
    }

    /**
     * <p>是否保存完成</p>
     */
    public void setIfProcessed(String ifProcessed) {
        this.ifProcessed = ifProcessed;
    }

    /**
     * <p>创建时间</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>创建时间</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>更新时间</p>
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * <p>更新时间</p>
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * <p>处理日期</p>
     */
    public Date getProcessTime() {
        return processTime;
    }

    /**
     * <p>处理日期</p>
     */
    public void setProcessTime(Date processTime) {
        this.processTime = processTime;
    }

    /**
     * <p>乐观所</p>
     */
    public Long getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观所</p>
     */
    public void setJpaVersion(Long jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("ifWrittenTn", ifWrittenTn);
        map.put("ifProcessed", ifProcessed);
        map.put("createDate", createDate);
        map.put("updateTime", updateTime);
        map.put("processTime", processTime);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("ifWrittenTn")) this.setIfWrittenTn(DataTypeUtils.getStringValue(map.get("ifWrittenTn")));
        if (map.containsKey("ifProcessed")) this.setIfProcessed(DataTypeUtils.getStringValue(map.get("ifProcessed")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
        if (map.containsKey("processTime")) this.setProcessTime(DataTypeUtils.getDateValue(map.get("processTime")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getLongValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", ifWrittenTn="+ifWrittenTn+
        "ifWrittenTn="+ifWrittenTn+
        ", ifProcessed="+ifProcessed+
        "ifProcessed="+ifProcessed+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        ", processTime="+processTime+
        "processTime="+processTime+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (id == null) id = 0;
        if (ifWrittenTn == null) ifWrittenTn = "";
        if (ifProcessed == null) ifProcessed = "";
        if (createDate == null) createDate = new Date();
        if (updateTime == null) updateTime = new Date();
        if (processTime == null) processTime = new Date();
        if (jpaVersion == null) jpaVersion = 0l;
    }
}