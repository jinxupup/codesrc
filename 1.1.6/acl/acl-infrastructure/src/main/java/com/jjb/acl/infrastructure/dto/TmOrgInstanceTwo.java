package com.jjb.acl.infrastructure.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.jjb.unicorn.facility.util.DataTypeUtils;

/**
 * Created by smh on 2018/11/20.
 * Discription:
 */
public class TmOrgInstanceTwo implements Serializable{
    private static final long serialVersionUID = 1L;
    private Integer tmInstanceInstanceId;
    private String tmInstanceSystemType;
    private String tmInstanceInstanceName;
    private String tmInstanceInstanceMemo;
    private String tmInstOrgAccessAddress;

    public Integer getTmInstanceInstanceId() {
        return tmInstanceInstanceId;
    }

    public void setTmInstanceInstanceId(Integer tmInstanceInstanceId) {
        this.tmInstanceInstanceId = tmInstanceInstanceId;
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

    public String getTmInstanceInstanceMemo() {
        return tmInstanceInstanceMemo;
    }

    public void setTmInstanceInstanceMemo(String tmInstanceInstanceMemo) {
        this.tmInstanceInstanceMemo = tmInstanceInstanceMemo;
    }

    public String getTmInstOrgAccessAddress() {
        return tmInstOrgAccessAddress;
    }

    public void setTmInstOrgAccessAddress(String tmInstOrgAccessAddress) {
        this.tmInstOrgAccessAddress = tmInstOrgAccessAddress;
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
                "serialVersionUID="+serialVersionUID+
                "tmInstanceInstanceId="+tmInstanceInstanceId+
                "tmInstanceSystemType="+tmInstanceSystemType+
                "tmInstanceInstanceName="+tmInstanceInstanceName+
                "tmInstanceInstanceMemo="+tmInstanceInstanceMemo+
                "tmInstOrgAccessAddress="+tmInstOrgAccessAddress+
                "]";
    }

    public void fillDefaultValues() {
        if (tmInstanceSystemType == null) tmInstanceSystemType = "";
        if (tmInstanceInstanceName == null) tmInstanceInstanceName = "";
        if (tmInstanceInstanceMemo == null) tmInstanceInstanceMemo = "";
        if (tmInstOrgAccessAddress == null) tmInstOrgAccessAddress = "";

    }
    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("tmInstanceInstanceId")) this.setTmInstanceInstanceId(DataTypeUtils.getIntegerValue(map.get("tmInstanceInstanceId")));
        if (map.containsKey("tmInstanceSystemType")) this.setTmInstanceSystemType(DataTypeUtils.getStringValue(map.get("tmInstanceSystemType")));
        if (map.containsKey("tmInstanceInstanceName")) this.setTmInstanceInstanceName(DataTypeUtils.getStringValue(map.get("tmInstanceInstanceName")));
        if (map.containsKey("tmInstanceInstanceMemo")) this.setTmInstanceInstanceMemo(DataTypeUtils.getStringValue(map.get("tmInstanceInstanceMemo")));
        if (map.containsKey("tmInstOrgAccessAddress")) this.setTmInstOrgAccessAddress(DataTypeUtils.getStringValue(map.get("tmInstOrgAccessAddress")));

    }
    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("tmInstanceInstanceId", tmInstanceInstanceId);
        map.put("tmInstanceSystemType", tmInstanceSystemType);
        map.put("tmInstanceInstanceName", tmInstanceInstanceName);
        map.put("tmInstanceInstanceMemo", tmInstanceInstanceMemo);
        map.put("tmInstOrgAccessAddress", tmInstOrgAccessAddress);

        return map;
    }





}
