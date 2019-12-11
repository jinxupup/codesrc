package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 实例环境表
 * @author jjb
 */
public class TmInstEnv implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer instanceId;

    private String propKey;

    private String propValue;

    private String propMemo;

    private String maskValue;

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
     * <p>属性键值</p>
     */
    public String getPropKey() {
        return propKey;
    }

    /**
     * <p>属性键值</p>
     */
    public void setPropKey(String propKey) {
        this.propKey = propKey;
    }

    /**
     * <p>属性值</p>
     */
    public String getPropValue() {
        return propValue;
    }

    /**
     * <p>属性值</p>
     */
    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    /**
     * <p>备注</p>
     */
    public String getPropMemo() {
        return propMemo;
    }

    /**
     * <p>备注</p>
     */
    public void setPropMemo(String propMemo) {
        this.propMemo = propMemo;
    }

    /**
     * <p>加密取值</p>
     */
    public String getMaskValue() {
        return maskValue;
    }

    /**
     * <p>加密取值</p>
     */
    public void setMaskValue(String maskValue) {
        this.maskValue = maskValue;
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
        map.put("instanceId", instanceId);
        map.put("propKey", propKey);
        map.put("propValue", propValue);
        map.put("propMemo", propMemo);
        map.put("maskValue", maskValue);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("instanceId")) this.setInstanceId(DataTypeUtils.getIntegerValue(map.get("instanceId")));
        if (map.containsKey("propKey")) this.setPropKey(DataTypeUtils.getStringValue(map.get("propKey")));
        if (map.containsKey("propValue")) this.setPropValue(DataTypeUtils.getStringValue(map.get("propValue")));
        if (map.containsKey("propMemo")) this.setPropMemo(DataTypeUtils.getStringValue(map.get("propMemo")));
        if (map.containsKey("maskValue")) this.setMaskValue(DataTypeUtils.getStringValue(map.get("maskValue")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", instanceId="+instanceId+
        "instanceId="+instanceId+
        ", propKey="+propKey+
        "propKey="+propKey+
        ", propValue="+propValue+
        "propValue="+propValue+
        ", propMemo="+propMemo+
        "propMemo="+propMemo+
        ", maskValue="+maskValue+
        "maskValue="+maskValue+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (instanceId == null) instanceId = 0;
        if (propKey == null) propKey = "";
        if (propValue == null) propValue = "";
        if (propMemo == null) propMemo = "";
        if (maskValue == null) maskValue = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}