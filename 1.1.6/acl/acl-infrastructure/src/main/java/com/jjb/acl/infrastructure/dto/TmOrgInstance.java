package com.jjb.acl.infrastructure.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.jjb.unicorn.facility.util.DataTypeUtils;

/**
 * Created by smh on 2018/11/20.
 */
public class TmOrgInstance implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tmInstOrgOrg;
    private String tmInstanceSystemType;
    private String tmInstanceInstanceName;

    public String getTmInstOrgOrg() {
        return tmInstOrgOrg;
    }

    public void setTmInstOrgOrg(String tmInstOrgOrg) {
        this.tmInstOrgOrg = tmInstOrgOrg;
    }

    public String getTmInstanceSystemType() {
        return tmInstanceSystemType;
    }

    public void setTmInstanceSystemType(String tmInstanceSystemType) {
        this.tmInstanceSystemType = tmInstanceSystemType;
    }

    public String getTmInstanceInstanceName() {
        return tmInstanceInstanceName;
    }

    public void setTmInstanceInstanceName(String tmInstanceInstanceName) {
        this.tmInstanceInstanceName = tmInstanceInstanceName;
    }
    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
                "serialVersionUID="+serialVersionUID+
                "tmInstOrgOrg="+tmInstOrgOrg+
                "tmInstanceSystemType="+tmInstanceSystemType+
                "tmInstanceInstanceName="+tmInstanceInstanceName+
                "]";
    }

    public void fillDefaultValues() {
        if (tmInstOrgOrg == null) tmInstOrgOrg = "";
        if (tmInstanceSystemType == null) tmInstanceSystemType = "";
        if (tmInstanceInstanceName == null) tmInstanceInstanceName = "";
    }


    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("tmInstOrgOrg")) this.setTmInstOrgOrg(DataTypeUtils.getStringValue(map.get("tmInstOrgOrg")));
        if (map.containsKey("tmInstanceSystemType")) this.setTmInstanceSystemType(DataTypeUtils.getStringValue(map.get("tmInstanceSystemType")));
        if (map.containsKey("tmInstanceInstanceName")) this.setTmInstanceInstanceName(DataTypeUtils.getStringValue(map.get("tmInstanceInstanceName")));
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("tmInstOrgOrg", tmInstOrgOrg);
        map.put("tmInstanceSystemType", tmInstanceSystemType);
        map.put("tmInstanceInstanceName", tmInstanceInstanceName);
        return map;
    }
}
