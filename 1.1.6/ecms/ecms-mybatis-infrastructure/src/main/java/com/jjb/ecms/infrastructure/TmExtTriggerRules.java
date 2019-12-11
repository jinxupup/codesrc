package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 外部规则触发清单
 * @author jjb
 */
public class TmExtTriggerRules implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String scene;

    private String extAppNo;

    private String ruleType;

    private String tags;

    private Date createTime;

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
     * <p>场景</p>
     */
    public String getScene() {
        return scene;
    }

    /**
     * <p>场景</p>
     */
    public void setScene(String scene) {
        this.scene = scene;
    }

    /**
     * <p>外部申请件编号</p>
     */
    public String getExtAppNo() {
        return extAppNo;
    }

    /**
     * <p>外部申请件编号</p>
     */
    public void setExtAppNo(String extAppNo) {
        this.extAppNo = extAppNo;
    }

    /**
     * <p>规则类型</p>
     */
    public String getRuleType() {
        return ruleType;
    }

    /**
     * <p>规则类型</p>
     */
    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    /**
     * <p>标签数据</p>
     */
    public String getTags() {
        return tags;
    }

    /**
     * <p>标签数据</p>
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     * <p>创建时间</p>
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * <p>创建时间</p>
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * <p>乐观锁ID</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观锁ID</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("scene", scene);
        map.put("extAppNo", extAppNo);
        map.put("ruleType", ruleType);
        map.put("tags", tags);
        map.put("createTime", createTime);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("scene")) this.setScene(DataTypeUtils.getStringValue(map.get("scene")));
        if (map.containsKey("extAppNo")) this.setExtAppNo(DataTypeUtils.getStringValue(map.get("extAppNo")));
        if (map.containsKey("ruleType")) this.setRuleType(DataTypeUtils.getStringValue(map.get("ruleType")));
        if (map.containsKey("tags")) this.setTags(DataTypeUtils.getStringValue(map.get("tags")));
        if (map.containsKey("createTime")) this.setCreateTime(DataTypeUtils.getDateValue(map.get("createTime")));
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
        ", scene="+scene+
        "scene="+scene+
        ", extAppNo="+extAppNo+
        "extAppNo="+extAppNo+
        ", ruleType="+ruleType+
        "ruleType="+ruleType+
        ", tags="+tags+
        "tags="+tags+
        ", createTime="+createTime+
        "createTime="+createTime+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (scene == null) scene = "";
        if (extAppNo == null) extAppNo = "";
        if (ruleType == null) ruleType = "";
        if (tags == null) tags = "";
        if (createTime == null) createTime = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}