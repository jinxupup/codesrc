package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统状态表
 * @author jjb
 */
public class TmSysStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date businessDate;

    private Date processDate;

    private Date lastProcessDate;

    private Date graceTime;

    private Date dateSwitchTime;

    private Date lastDateSwitchTime;

    private Date mtnDate;

    private Integer jpaVersion;

    private String isAutoStartAllBatch;

    private String autoBatchTime;

    /**
     * <p>ID</p>
     * <p>固定为1</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>ID</p>
     * <p>固定为1</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>系统联机服务日期</p>
     * <p>全系统统一的联机日期</p>
     */
    public Date getBusinessDate() {
        return businessDate;
    }

    /**
     * <p>系统联机服务日期</p>
     * <p>全系统统一的联机日期</p>
     */
    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    /**
     * <p>批量处理日期</p>
     */
    public Date getProcessDate() {
        return processDate;
    }

    /**
     * <p>批量处理日期</p>
     */
    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    /**
     * <p>上一批量日期</p>
     */
    public Date getLastProcessDate() {
        return lastProcessDate;
    }

    /**
     * <p>上一批量日期</p>
     */
    public void setLastProcessDate(Date lastProcessDate) {
        this.lastProcessDate = lastProcessDate;
    }

    /**
     * <p>GRACE_TIME</p>
     * <p>下一个grace time，如果当前时间在grace time之前，则表时当前是grace time 状态</p>
     */
    public Date getGraceTime() {
        return graceTime;
    }

    /**
     * <p>GRACE_TIME</p>
     * <p>下一个grace time，如果当前时间在grace time之前，则表时当前是grace time 状态</p>
     */
    public void setGraceTime(Date graceTime) {
        this.graceTime = graceTime;
    }

    /**
     * <p>当前日切时间</p>
     */
    public Date getDateSwitchTime() {
        return dateSwitchTime;
    }

    /**
     * <p>当前日切时间</p>
     */
    public void setDateSwitchTime(Date dateSwitchTime) {
        this.dateSwitchTime = dateSwitchTime;
    }

    /**
     * <p>上一日切时间</p>
     */
    public Date getLastDateSwitchTime() {
        return lastDateSwitchTime;
    }

    /**
     * <p>上一日切时间</p>
     */
    public void setLastDateSwitchTime(Date lastDateSwitchTime) {
        this.lastDateSwitchTime = lastDateSwitchTime;
    }

    /**
     * <p>上次维护日期</p>
     */
    public Date getMtnDate() {
        return mtnDate;
    }

    /**
     * <p>上次维护日期</p>
     */
    public void setMtnDate(Date mtnDate) {
        this.mtnDate = mtnDate;
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
     * <p>是否自动跑批</p>
     */
    public String getIsAutoStartAllBatch() {
        return isAutoStartAllBatch;
    }

    /**
     * <p>是否自动跑批</p>
     */
    public void setIsAutoStartAllBatch(String isAutoStartAllBatch) {
        this.isAutoStartAllBatch = isAutoStartAllBatch;
    }

    /**
     * <p>自动跑批时间</p>
     */
    public String getAutoBatchTime() {
        return autoBatchTime;
    }

    /**
     * <p>自动跑批时间</p>
     */
    public void setAutoBatchTime(String autoBatchTime) {
        this.autoBatchTime = autoBatchTime;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("businessDate", businessDate);
        map.put("processDate", processDate);
        map.put("lastProcessDate", lastProcessDate);
        map.put("graceTime", graceTime);
        map.put("dateSwitchTime", dateSwitchTime);
        map.put("lastDateSwitchTime", lastDateSwitchTime);
        map.put("mtnDate", mtnDate);
        map.put("jpaVersion", jpaVersion);
        map.put("isAutoStartAllBatch", isAutoStartAllBatch);
        map.put("autoBatchTime", autoBatchTime);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("businessDate")) this.setBusinessDate(DataTypeUtils.getDateValue(map.get("businessDate")));
        if (map.containsKey("processDate")) this.setProcessDate(DataTypeUtils.getDateValue(map.get("processDate")));
        if (map.containsKey("lastProcessDate")) this.setLastProcessDate(DataTypeUtils.getDateValue(map.get("lastProcessDate")));
        if (map.containsKey("graceTime")) this.setGraceTime(DataTypeUtils.getDateValue(map.get("graceTime")));
        if (map.containsKey("dateSwitchTime")) this.setDateSwitchTime(DataTypeUtils.getDateValue(map.get("dateSwitchTime")));
        if (map.containsKey("lastDateSwitchTime")) this.setLastDateSwitchTime(DataTypeUtils.getDateValue(map.get("lastDateSwitchTime")));
        if (map.containsKey("mtnDate")) this.setMtnDate(DataTypeUtils.getDateValue(map.get("mtnDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("isAutoStartAllBatch")) this.setIsAutoStartAllBatch(DataTypeUtils.getStringValue(map.get("isAutoStartAllBatch")));
        if (map.containsKey("autoBatchTime")) this.setAutoBatchTime(DataTypeUtils.getStringValue(map.get("autoBatchTime")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", businessDate="+businessDate+
        "businessDate="+businessDate+
        ", processDate="+processDate+
        "processDate="+processDate+
        ", lastProcessDate="+lastProcessDate+
        "lastProcessDate="+lastProcessDate+
        ", graceTime="+graceTime+
        "graceTime="+graceTime+
        ", dateSwitchTime="+dateSwitchTime+
        "dateSwitchTime="+dateSwitchTime+
        ", lastDateSwitchTime="+lastDateSwitchTime+
        "lastDateSwitchTime="+lastDateSwitchTime+
        ", mtnDate="+mtnDate+
        "mtnDate="+mtnDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", isAutoStartAllBatch="+isAutoStartAllBatch+
        "isAutoStartAllBatch="+isAutoStartAllBatch+
        ", autoBatchTime="+autoBatchTime+
        "autoBatchTime="+autoBatchTime+
        "]";
    }

    public void fillDefaultValues() {
        if (id == null) id = 0;
        if (businessDate == null) businessDate = new Date();
        if (processDate == null) processDate = new Date();
        if (lastProcessDate == null) lastProcessDate = new Date();
        if (graceTime == null) graceTime = new Date();
        if (dateSwitchTime == null) dateSwitchTime = new Date();
        if (lastDateSwitchTime == null) lastDateSwitchTime = new Date();
        if (mtnDate == null) mtnDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
        if (isAutoStartAllBatch == null) isAutoStartAllBatch = "";
        if (autoBatchTime == null) autoBatchTime = "";
    }
}