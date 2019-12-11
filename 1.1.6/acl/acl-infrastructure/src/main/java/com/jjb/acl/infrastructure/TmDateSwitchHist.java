package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日切历史表
 * @author jjb
 */
public class TmDateSwitchHist implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Date switchTime;

    private String switchUser;

    private Date preSwitchDate;

    private Date postBusinessDate;

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
     * <p>日切时间戳</p>
     */
    public Date getSwitchTime() {
        return switchTime;
    }

    /**
     * <p>日切时间戳</p>
     */
    public void setSwitchTime(Date switchTime) {
        this.switchTime = switchTime;
    }

    /**
     * <p>日切操作员</p>
     */
    public String getSwitchUser() {
        return switchUser;
    }

    /**
     * <p>日切操作员</p>
     */
    public void setSwitchUser(String switchUser) {
        this.switchUser = switchUser;
    }

    /**
     * <p>日切前业务日期</p>
     */
    public Date getPreSwitchDate() {
        return preSwitchDate;
    }

    /**
     * <p>日切前业务日期</p>
     */
    public void setPreSwitchDate(Date preSwitchDate) {
        this.preSwitchDate = preSwitchDate;
    }

    /**
     * <p>日切后业务日期</p>
     */
    public Date getPostBusinessDate() {
        return postBusinessDate;
    }

    /**
     * <p>日切后业务日期</p>
     */
    public void setPostBusinessDate(Date postBusinessDate) {
        this.postBusinessDate = postBusinessDate;
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
        map.put("switchTime", switchTime);
        map.put("switchUser", switchUser);
        map.put("preSwitchDate", preSwitchDate);
        map.put("postBusinessDate", postBusinessDate);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("switchTime")) this.setSwitchTime(DataTypeUtils.getDateValue(map.get("switchTime")));
        if (map.containsKey("switchUser")) this.setSwitchUser(DataTypeUtils.getStringValue(map.get("switchUser")));
        if (map.containsKey("preSwitchDate")) this.setPreSwitchDate(DataTypeUtils.getDateValue(map.get("preSwitchDate")));
        if (map.containsKey("postBusinessDate")) this.setPostBusinessDate(DataTypeUtils.getDateValue(map.get("postBusinessDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", switchTime="+switchTime+
        "switchTime="+switchTime+
        ", switchUser="+switchUser+
        "switchUser="+switchUser+
        ", preSwitchDate="+preSwitchDate+
        "preSwitchDate="+preSwitchDate+
        ", postBusinessDate="+postBusinessDate+
        "postBusinessDate="+postBusinessDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (switchTime == null) switchTime = new Date();
        if (switchUser == null) switchUser = "";
        if (preSwitchDate == null) preSwitchDate = new Date();
        if (postBusinessDate == null) postBusinessDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}