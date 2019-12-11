package com.jjb.acl.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 机构信息
 * @author jjb
 */
public class TmOrg implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String orgName;

    private String orgDesc;

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
     * <p>机构名称</p>
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * <p>机构名称</p>
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * <p>描述</p>
     */
    public String getOrgDesc() {
        return orgDesc;
    }

    /**
     * <p>描述</p>
     */
    public void setOrgDesc(String orgDesc) {
        this.orgDesc = orgDesc;
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
        map.put("orgName", orgName);
        map.put("orgDesc", orgDesc);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("orgName")) this.setOrgName(DataTypeUtils.getStringValue(map.get("orgName")));
        if (map.containsKey("orgDesc")) this.setOrgDesc(DataTypeUtils.getStringValue(map.get("orgDesc")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", org="+org+
        "org="+org+
        ", orgName="+orgName+
        "orgName="+orgName+
        ", orgDesc="+orgDesc+
        "orgDesc="+orgDesc+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (orgName == null) orgName = "";
        if (orgDesc == null) orgDesc = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}