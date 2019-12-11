package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统实例信息
 * @author jjb
 */
public class TmInstance implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer instanceId;

    private String systemType;

    private String instanceName;

    private String instanceMemo;

    private Integer jpaVersion;

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
     * <p>实例名</p>
     */
    public String getInstanceName() {
        return instanceName;
    }

    /**
     * <p>实例名</p>
     */
    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    /**
     * <p>实例信息备注</p>
     */
    public String getInstanceMemo() {
        return instanceMemo;
    }

    /**
     * <p>实例信息备注</p>
     */
    public void setInstanceMemo(String instanceMemo) {
        this.instanceMemo = instanceMemo;
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
        map.put("instanceId", instanceId);
        map.put("systemType", systemType);
        map.put("instanceName", instanceName);
        map.put("instanceMemo", instanceMemo);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("instanceId")) this.setInstanceId(DataTypeUtils.getIntegerValue(map.get("instanceId")));
        if (map.containsKey("systemType")) this.setSystemType(DataTypeUtils.getStringValue(map.get("systemType")));
        if (map.containsKey("instanceName")) this.setInstanceName(DataTypeUtils.getStringValue(map.get("instanceName")));
        if (map.containsKey("instanceMemo")) this.setInstanceMemo(DataTypeUtils.getStringValue(map.get("instanceMemo")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", instanceId="+instanceId+
        "instanceId="+instanceId+
        ", systemType="+systemType+
        "systemType="+systemType+
        ", instanceName="+instanceName+
        "instanceName="+instanceName+
        ", instanceMemo="+instanceMemo+
        "instanceMemo="+instanceMemo+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (systemType == null) systemType = "";
        if (instanceName == null) instanceName = "";
        if (instanceMemo == null) instanceMemo = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}