package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统状态日志
 * @author jjb
 */
public class TmSysStatusLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date businessDate;

    private Date processDate;

    private Date lastProcessDate;

    private Date graceTime;

    private Date dateSwitchTime;

    private Date lastDateSwitchTime;

    private String operId;

    private String operOrg;

    private Integer jpaVersion;

    /**
     * <p>日志ID</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>日志ID</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>业务日期</p>
     */
    public Date getBusinessDate() {
        return businessDate;
    }

    /**
     * <p>业务日期</p>
     */
    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    /**
     * <p>批量日期</p>
     */
    public Date getProcessDate() {
        return processDate;
    }

    /**
     * <p>批量日期</p>
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
     * <p>宽限时间</p>
     */
    public Date getGraceTime() {
        return graceTime;
    }

    /**
     * <p>宽限时间</p>
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
     * <p>操作员ID</p>
     */
    public String getOperId() {
        return operId;
    }

    /**
     * <p>操作员ID</p>
     */
    public void setOperId(String operId) {
        this.operId = operId;
    }

    /**
     * <p>操作员归属机构号</p>
     */
    public String getOperOrg() {
        return operOrg;
    }

    /**
     * <p>操作员归属机构号</p>
     */
    public void setOperOrg(String operOrg) {
        this.operOrg = operOrg;
    }

    /**
     * <p>乐观所</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观所</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
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
        map.put("operId", operId);
        map.put("operOrg", operOrg);
        map.put("jpaVersion", jpaVersion);
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
        if (map.containsKey("operId")) this.setOperId(DataTypeUtils.getStringValue(map.get("operId")));
        if (map.containsKey("operOrg")) this.setOperOrg(DataTypeUtils.getStringValue(map.get("operOrg")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
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
        ", operId="+operId+
        "operId="+operId+
        ", operOrg="+operOrg+
        "operOrg="+operOrg+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (businessDate == null) businessDate = new Date();
        if (processDate == null) processDate = new Date();
        if (lastProcessDate == null) lastProcessDate = new Date();
        if (graceTime == null) graceTime = new Date();
        if (dateSwitchTime == null) dateSwitchTime = new Date();
        if (lastDateSwitchTime == null) lastDateSwitchTime = new Date();
        if (operId == null) operId = "";
        if (operOrg == null) operOrg = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}