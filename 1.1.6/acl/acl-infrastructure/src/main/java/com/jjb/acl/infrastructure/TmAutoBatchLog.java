package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动跑批日志表
 * @author jjb
 */
public class TmAutoBatchLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String action;

    private String actionDesc;

    private Date startTime;

    private Date endTime;

    private Date processDate;

    private String status;

    private String ip;

    private String exceptionMessage;

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
     * <p>动作</p>
     */
    public String getAction() {
        return action;
    }

    /**
     * <p>动作</p>
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * <p>动作描述</p>
     */
    public String getActionDesc() {
        return actionDesc;
    }

    /**
     * <p>动作描述</p>
     */
    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
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
     * <p>日切后的批量日期</p>
     */
    public Date getProcessDate() {
        return processDate;
    }

    /**
     * <p>日切后的批量日期</p>
     */
    public void setProcessDate(Date processDate) {
        this.processDate = processDate;
    }

    /**
     * <p>自动跑批状态</p>
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>自动跑批状态</p>
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * <p>IP</p>
     */
    public String getIp() {
        return ip;
    }

    /**
     * <p>IP</p>
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * <p>异常信息</p>
     */
    public String getExceptionMessage() {
        return exceptionMessage;
    }

    /**
     * <p>异常信息</p>
     */
    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
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
        map.put("action", action);
        map.put("actionDesc", actionDesc);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("processDate", processDate);
        map.put("status", status);
        map.put("ip", ip);
        map.put("exceptionMessage", exceptionMessage);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("action")) this.setAction(DataTypeUtils.getStringValue(map.get("action")));
        if (map.containsKey("actionDesc")) this.setActionDesc(DataTypeUtils.getStringValue(map.get("actionDesc")));
        if (map.containsKey("startTime")) this.setStartTime(DataTypeUtils.getDateValue(map.get("startTime")));
        if (map.containsKey("endTime")) this.setEndTime(DataTypeUtils.getDateValue(map.get("endTime")));
        if (map.containsKey("processDate")) this.setProcessDate(DataTypeUtils.getDateValue(map.get("processDate")));
        if (map.containsKey("status")) this.setStatus(DataTypeUtils.getStringValue(map.get("status")));
        if (map.containsKey("ip")) this.setIp(DataTypeUtils.getStringValue(map.get("ip")));
        if (map.containsKey("exceptionMessage")) this.setExceptionMessage(DataTypeUtils.getStringValue(map.get("exceptionMessage")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", action="+action+
        "action="+action+
        ", actionDesc="+actionDesc+
        "actionDesc="+actionDesc+
        ", startTime="+startTime+
        "startTime="+startTime+
        ", endTime="+endTime+
        "endTime="+endTime+
        ", processDate="+processDate+
        "processDate="+processDate+
        ", status="+status+
        "status="+status+
        ", ip="+ip+
        "ip="+ip+
        ", exceptionMessage="+exceptionMessage+
        "exceptionMessage="+exceptionMessage+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (action == null) action = "";
        if (actionDesc == null) actionDesc = "";
        if (startTime == null) startTime = new Date();
        if (endTime == null) endTime = new Date();
        if (processDate == null) processDate = new Date();
        if (status == null) status = "";
        if (ip == null) ip = "";
        if (exceptionMessage == null) exceptionMessage = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}