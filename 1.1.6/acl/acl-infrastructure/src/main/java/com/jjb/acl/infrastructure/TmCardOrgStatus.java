package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 卡组织状态表，保存连接各卡组织网络状态
 * @author jjb
 */
public class TmCardOrgStatus implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String inputsource;

    private String status;

    private String lastResp;

    private Date requestTime;

    private Date responseTime;

    private Date lastLoginTime;

    private Date lastLogoutTime;

    private Date lastBeginTime;

    private Date lastEndTime;

    private Date lastTestTime;

    private Integer jpaVersion;

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
     * <p>渠道来源</p>
     */
    public String getInputsource() {
        return inputsource;
    }

    /**
     * <p>渠道来源</p>
     */
    public void setInputsource(String inputsource) {
        this.inputsource = inputsource;
    }

    /**
     * <p>当前网络状态</p>
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>当前网络状态</p>
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * <p>上一次卡组织响应码</p>
     */
    public String getLastResp() {
        return lastResp;
    }

    /**
     * <p>上一次卡组织响应码</p>
     */
    public void setLastResp(String lastResp) {
        this.lastResp = lastResp;
    }

    /**
     * <p>请求时间</p>
     */
    public Date getRequestTime() {
        return requestTime;
    }

    /**
     * <p>请求时间</p>
     */
    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    /**
     * <p>响应时间</p>
     */
    public Date getResponseTime() {
        return responseTime;
    }

    /**
     * <p>响应时间</p>
     */
    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    /**
     * <p>上一次签到时间</p>
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * <p>上一次签到时间</p>
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * <p>上一次签退时间</p>
     */
    public Date getLastLogoutTime() {
        return lastLogoutTime;
    }

    /**
     * <p>上一次签退时间</p>
     */
    public void setLastLogoutTime(Date lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    /**
     * <p>上一次接收通知时间</p>
     */
    public Date getLastBeginTime() {
        return lastBeginTime;
    }

    /**
     * <p>上一次接收通知时间</p>
     */
    public void setLastBeginTime(Date lastBeginTime) {
        this.lastBeginTime = lastBeginTime;
    }

    /**
     * <p>上一次停止接收通知时间</p>
     */
    public Date getLastEndTime() {
        return lastEndTime;
    }

    /**
     * <p>上一次停止接收通知时间</p>
     */
    public void setLastEndTime(Date lastEndTime) {
        this.lastEndTime = lastEndTime;
    }

    /**
     * <p>上一次线路测试时间</p>
     */
    public Date getLastTestTime() {
        return lastTestTime;
    }

    /**
     * <p>上一次线路测试时间</p>
     */
    public void setLastTestTime(Date lastTestTime) {
        this.lastTestTime = lastTestTime;
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
        map.put("org", org);
        map.put("inputsource", inputsource);
        map.put("status", status);
        map.put("lastResp", lastResp);
        map.put("requestTime", requestTime);
        map.put("responseTime", responseTime);
        map.put("lastLoginTime", lastLoginTime);
        map.put("lastLogoutTime", lastLogoutTime);
        map.put("lastBeginTime", lastBeginTime);
        map.put("lastEndTime", lastEndTime);
        map.put("lastTestTime", lastTestTime);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("inputsource")) this.setInputsource(DataTypeUtils.getStringValue(map.get("inputsource")));
        if (map.containsKey("status")) this.setStatus(DataTypeUtils.getStringValue(map.get("status")));
        if (map.containsKey("lastResp")) this.setLastResp(DataTypeUtils.getStringValue(map.get("lastResp")));
        if (map.containsKey("requestTime")) this.setRequestTime(DataTypeUtils.getDateValue(map.get("requestTime")));
        if (map.containsKey("responseTime")) this.setResponseTime(DataTypeUtils.getDateValue(map.get("responseTime")));
        if (map.containsKey("lastLoginTime")) this.setLastLoginTime(DataTypeUtils.getDateValue(map.get("lastLoginTime")));
        if (map.containsKey("lastLogoutTime")) this.setLastLogoutTime(DataTypeUtils.getDateValue(map.get("lastLogoutTime")));
        if (map.containsKey("lastBeginTime")) this.setLastBeginTime(DataTypeUtils.getDateValue(map.get("lastBeginTime")));
        if (map.containsKey("lastEndTime")) this.setLastEndTime(DataTypeUtils.getDateValue(map.get("lastEndTime")));
        if (map.containsKey("lastTestTime")) this.setLastTestTime(DataTypeUtils.getDateValue(map.get("lastTestTime")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", org="+org+
        "org="+org+
        ", inputsource="+inputsource+
        "inputsource="+inputsource+
        ", status="+status+
        "status="+status+
        ", lastResp="+lastResp+
        "lastResp="+lastResp+
        ", requestTime="+requestTime+
        "requestTime="+requestTime+
        ", responseTime="+responseTime+
        "responseTime="+responseTime+
        ", lastLoginTime="+lastLoginTime+
        "lastLoginTime="+lastLoginTime+
        ", lastLogoutTime="+lastLogoutTime+
        "lastLogoutTime="+lastLogoutTime+
        ", lastBeginTime="+lastBeginTime+
        "lastBeginTime="+lastBeginTime+
        ", lastEndTime="+lastEndTime+
        "lastEndTime="+lastEndTime+
        ", lastTestTime="+lastTestTime+
        "lastTestTime="+lastTestTime+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (inputsource == null) inputsource = "";
        if (status == null) status = "";
        if (lastResp == null) lastResp = "";
        if (requestTime == null) requestTime = new Date();
        if (responseTime == null) responseTime = new Date();
        if (lastLoginTime == null) lastLoginTime = new Date();
        if (lastLogoutTime == null) lastLogoutTime = new Date();
        if (lastBeginTime == null) lastBeginTime = new Date();
        if (lastEndTime == null) lastEndTime = new Date();
        if (lastTestTime == null) lastTestTime = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}