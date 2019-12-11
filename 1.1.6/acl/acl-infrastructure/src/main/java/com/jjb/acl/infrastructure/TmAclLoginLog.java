package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TM_ACL_LOGIN_LOG登陆日志
 * @author jjb
 */
public class TmAclLoginLog implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private Integer id;

    private String userNo;

    private String type;

    private String result;

    private Integer retry;

    private Date longinTime;

    private String sessionId;

    private String remark;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String updateBy;

    private Integer jpaVersion;

    /**
     * <p>ORG</p>
     */
    public String getOrg() {
        return org;
    }

    /**
     * <p>ORG</p>
     */
    public void setOrg(String org) {
        this.org = org;
    }

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
     * <p>登陆用户</p>
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * <p>登陆用户</p>
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * <p>操作类型</p>
     */
    public String getType() {
        return type;
    }

    /**
     * <p>操作类型</p>
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <p>操作结果</p>
     */
    public String getResult() {
        return result;
    }

    /**
     * <p>操作结果</p>
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * <p>密码错误次数</p>
     */
    public Integer getRetry() {
        return retry;
    }

    /**
     * <p>密码错误次数</p>
     */
    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    /**
     * <p>操作时间</p>
     */
    public Date getLonginTime() {
        return longinTime;
    }

    /**
     * <p>操作时间</p>
     */
    public void setLonginTime(Date longinTime) {
        this.longinTime = longinTime;
    }

    /**
     * <p>SESSION_ID</p>
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * <p>SESSION_ID</p>
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * <p>说明</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>说明</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * <p>创建用户</p>
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * <p>创建用户</p>
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * <p>维护时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>维护时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * <p>维护用户</p>
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * <p>维护用户</p>
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * <p>版本</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>版本</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("org", org);
        map.put("id", id);
        map.put("userNo", userNo);
        map.put("type", type);
        map.put("result", result);
        map.put("retry", retry);
        map.put("longinTime", longinTime);
        map.put("sessionId", sessionId);
        map.put("remark", remark);
        map.put("createDate", createDate);
        map.put("createBy", createBy);
        map.put("updateDate", updateDate);
        map.put("updateBy", updateBy);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("userNo")) this.setUserNo(DataTypeUtils.getStringValue(map.get("userNo")));
        if (map.containsKey("type")) this.setType(DataTypeUtils.getStringValue(map.get("type")));
        if (map.containsKey("result")) this.setResult(DataTypeUtils.getStringValue(map.get("result")));
        if (map.containsKey("retry")) this.setRetry(DataTypeUtils.getIntegerValue(map.get("retry")));
        if (map.containsKey("longinTime")) this.setLonginTime(DataTypeUtils.getDateValue(map.get("longinTime")));
        if (map.containsKey("sessionId")) this.setSessionId(DataTypeUtils.getStringValue(map.get("sessionId")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createBy")) this.setCreateBy(DataTypeUtils.getStringValue(map.get("createBy")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateBy")) this.setUpdateBy(DataTypeUtils.getStringValue(map.get("updateBy")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", org="+org+
        "org="+org+
        ", id="+id+
        "id="+id+
        ", userNo="+userNo+
        "userNo="+userNo+
        ", type="+type+
        "type="+type+
        ", result="+result+
        "result="+result+
        ", retry="+retry+
        "retry="+retry+
        ", longinTime="+longinTime+
        "longinTime="+longinTime+
        ", sessionId="+sessionId+
        "sessionId="+sessionId+
        ", remark="+remark+
        "remark="+remark+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createBy="+createBy+
        "createBy="+createBy+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateBy="+updateBy+
        "updateBy="+updateBy+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (userNo == null) userNo = "";
        if (type == null) type = "";
        if (result == null) result = "";
        if (retry == null) retry = 0;
        if (longinTime == null) longinTime = new Date();
        if (sessionId == null) sessionId = "";
        if (remark == null) remark = "";
        if (createDate == null) createDate = new Date();
        if (createBy == null) createBy = "";
        if (updateDate == null) updateDate = new Date();
        if (updateBy == null) updateBy = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}