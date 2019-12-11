package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 实例机构映射
 * @author jjb
 */
public class TmInstOrg implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer instanceId;

    private String org;

    private Integer jpaVersion;

    private String accessAddress;

    /**
     * <p>实例号</p>
     */
    public Integer getInstanceId() {
        return instanceId;
    }

    /**
     * <p>实例号</p>
     */
    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

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
     * <p>访问地址</p>
     * <p>实例访问地址</p>
     */
    public String getAccessAddress() {
        return accessAddress;
    }

    /**
     * <p>访问地址</p>
     * <p>实例访问地址</p>
     */
    public void setAccessAddress(String accessAddress) {
        this.accessAddress = accessAddress;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("instanceId", instanceId);
        map.put("org", org);
        map.put("jpaVersion", jpaVersion);
        map.put("accessAddress", accessAddress);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("instanceId")) this.setInstanceId(DataTypeUtils.getIntegerValue(map.get("instanceId")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("accessAddress")) this.setAccessAddress(DataTypeUtils.getStringValue(map.get("accessAddress")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", instanceId="+instanceId+
        "instanceId="+instanceId+
        ", org="+org+
        "org="+org+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", accessAddress="+accessAddress+
        "accessAddress="+accessAddress+
        "]";
    }

    public void fillDefaultValues() {
        if (instanceId == null) instanceId = 0;
        if (org == null) org = "";
        if (jpaVersion == null) jpaVersion = 0;
        if (accessAddress == null) accessAddress = "";
    }
}