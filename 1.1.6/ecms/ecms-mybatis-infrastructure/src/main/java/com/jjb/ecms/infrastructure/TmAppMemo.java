package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请件备注备忘
 * @author jjb
 */
public class TmAppMemo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String memoType;

    private String taskKey;

    private String rtfState;

    private Integer memoVersion;

    private String memoInfo;

    private String taskDesc;

    private Date createDate;

    private String createUser;

    private Integer jpaVersion;

    /**
     * <p>主键ID</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>主键ID</p>
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * <p>行机构</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>行机构</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

    /**
     * <p>申请件编号</p>
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * <p>申请件编号</p>
     */
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    /**
     * <p>备注类型</p>
     */
    public String getMemoType() {
        return memoType;
    }

    /**
     * <p>备注类型</p>
     */
    public void setMemoType(String memoType) {
        this.memoType = memoType;
    }

    /**
     * <p>任务名称</p>
     */
    public String getTaskKey() {
        return taskKey;
    }

    /**
     * <p>任务名称</p>
     */
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    /**
     * <p>进件状态</p>
     */
    public String getRtfState() {
        return rtfState;
    }

    /**
     * <p>进件状态</p>
     */
    public void setRtfState(String rtfState) {
        this.rtfState = rtfState;
    }

    /**
     * <p>备注版本</p>
     */
    public Integer getMemoVersion() {
        return memoVersion;
    }

    /**
     * <p>备注版本</p>
     */
    public void setMemoVersion(Integer memoVersion) {
        this.memoVersion = memoVersion;
    }

    /**
     * <p>备注信息</p>
     */
    public String getMemoInfo() {
        return memoInfo;
    }

    /**
     * <p>备注信息</p>
     */
    public void setMemoInfo(String memoInfo) {
        this.memoInfo = memoInfo;
    }

    /**
     * <p>任务描述</p>
     */
    public String getTaskDesc() {
        return taskDesc;
    }

    /**
     * <p>任务描述</p>
     */
    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
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
     * <p>创建人</p>
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>创建人</p>
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("memoType", memoType);
        map.put("taskKey", taskKey);
        map.put("rtfState", rtfState);
        map.put("memoVersion", memoVersion);
        map.put("memoInfo", memoInfo);
        map.put("taskDesc", taskDesc);
        map.put("createDate", createDate);
        map.put("createUser", createUser);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("memoType")) this.setMemoType(DataTypeUtils.getStringValue(map.get("memoType")));
        if (map.containsKey("taskKey")) this.setTaskKey(DataTypeUtils.getStringValue(map.get("taskKey")));
        if (map.containsKey("rtfState")) this.setRtfState(DataTypeUtils.getStringValue(map.get("rtfState")));
        if (map.containsKey("memoVersion")) this.setMemoVersion(DataTypeUtils.getIntegerValue(map.get("memoVersion")));
        if (map.containsKey("memoInfo")) this.setMemoInfo(DataTypeUtils.getStringValue(map.get("memoInfo")));
        if (map.containsKey("taskDesc")) this.setTaskDesc(DataTypeUtils.getStringValue(map.get("taskDesc")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
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
        ", appNo="+appNo+
        "appNo="+appNo+
        ", memoType="+memoType+
        "memoType="+memoType+
        ", taskKey="+taskKey+
        "taskKey="+taskKey+
        ", rtfState="+rtfState+
        "rtfState="+rtfState+
        ", memoVersion="+memoVersion+
        "memoVersion="+memoVersion+
        ", memoInfo="+memoInfo+
        "memoInfo="+memoInfo+
        ", taskDesc="+taskDesc+
        "taskDesc="+taskDesc+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (memoType == null) memoType = "";
        if (taskKey == null) taskKey = "";
        if (rtfState == null) rtfState = "";
        if (memoVersion == null) memoVersion = 0;
        if (memoInfo == null) memoInfo = "";
        if (taskDesc == null) taskDesc = "";
        if (createDate == null) createDate = new Date();
        if (createUser == null) createUser = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}