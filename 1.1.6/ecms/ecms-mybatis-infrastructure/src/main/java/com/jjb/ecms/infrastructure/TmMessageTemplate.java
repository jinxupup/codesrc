package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信模板表
 * @author jjb
 */
public class TmMessageTemplate implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String teCode;

    private String teDesc;

    private String systemType;

    private String msgcategory;

    private String sendingMethod;

    private String enabled;

    private String contentTemplate;

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
     * <p>信息代码</p>
     */
    public String getTeCode() {
        return teCode;
    }

    /**
     * <p>信息代码</p>
     */
    public void setTeCode(String teCode) {
        this.teCode = teCode;
    }

    /**
     * <p>信息描述</p>
     */
    public String getTeDesc() {
        return teDesc;
    }

    /**
     * <p>信息描述</p>
     */
    public void setTeDesc(String teDesc) {
        this.teDesc = teDesc;
    }

    /**
     * <p>系统类型</p>
     */
    public String getSystemType() {
        return systemType;
    }

    /**
     * <p>系统类型</p>
     */
    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    /**
     * <p>信息分类</p>
     */
    public String getMsgcategory() {
        return msgcategory;
    }

    /**
     * <p>信息分类</p>
     */
    public void setMsgcategory(String msgcategory) {
        this.msgcategory = msgcategory;
    }

    /**
     * <p>发送方法</p>
     */
    public String getSendingMethod() {
        return sendingMethod;
    }

    /**
     * <p>发送方法</p>
     */
    public void setSendingMethod(String sendingMethod) {
        this.sendingMethod = sendingMethod;
    }

    /**
     * <p>是否启用</p>
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * <p>是否启用</p>
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    /**
     * <p>内容模板</p>
     */
    public String getContentTemplate() {
        return contentTemplate;
    }

    /**
     * <p>内容模板</p>
     */
    public void setContentTemplate(String contentTemplate) {
        this.contentTemplate = contentTemplate;
    }

    /**
     * <p>发送起始时间</p>
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * <p>发送起始时间</p>
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * <p>发送截止时间</p>
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * <p>发送截止时间</p>
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * <p>乐观锁</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观锁</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("teCode", teCode);
        map.put("teDesc", teDesc);
        map.put("systemType", systemType);
        map.put("msgcategory", msgcategory);
        map.put("sendingMethod", sendingMethod);
        map.put("enabled", enabled);
        map.put("contentTemplate", contentTemplate);
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("teCode")) this.setTeCode(DataTypeUtils.getStringValue(map.get("teCode")));
        if (map.containsKey("teDesc")) this.setTeDesc(DataTypeUtils.getStringValue(map.get("teDesc")));
        if (map.containsKey("systemType")) this.setSystemType(DataTypeUtils.getStringValue(map.get("systemType")));
        if (map.containsKey("msgcategory")) this.setMsgcategory(DataTypeUtils.getStringValue(map.get("msgcategory")));
        if (map.containsKey("sendingMethod")) this.setSendingMethod(DataTypeUtils.getStringValue(map.get("sendingMethod")));
        if (map.containsKey("enabled")) this.setEnabled(DataTypeUtils.getStringValue(map.get("enabled")));
        if (map.containsKey("contentTemplate")) this.setContentTemplate(DataTypeUtils.getStringValue(map.get("contentTemplate")));
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
        ", teCode="+teCode+
        "teCode="+teCode+
        ", teDesc="+teDesc+
        "teDesc="+teDesc+
        ", systemType="+systemType+
        "systemType="+systemType+
        ", msgcategory="+msgcategory+
        "msgcategory="+msgcategory+
        ", sendingMethod="+sendingMethod+
        "sendingMethod="+sendingMethod+
        ", enabled="+enabled+
        "enabled="+enabled+
        ", contentTemplate="+contentTemplate+
        "contentTemplate="+contentTemplate+
        ", startTime="+startTime+
        "startTime="+startTime+
        ", endTime="+endTime+
        "endTime="+endTime+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (teCode == null) teCode = "";
        if (teDesc == null) teDesc = "";
        if (systemType == null) systemType = "";
        if (msgcategory == null) msgcategory = "";
        if (sendingMethod == null) sendingMethod = "";
        if (enabled == null) enabled = "";
        if (contentTemplate == null) contentTemplate = "";
        if (startTime == null) startTime = new Date();
        if (endTime == null) endTime = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}