package com.jjb.unicorn.infrastructure;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jjb.unicorn.facility.util.DataTypeUtils;

/**
 * YAK_KEY_CONTEXT
 * @author jjb
 */
public class YakKeyContext implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer contextId;

    private byte[] keyList;

    private Date setupDate;

    /**
     * <p>CONTEXT_ID</p>
     */
    public Integer getContextId() {
        return contextId;
    }

    /**
     * <p>CONTEXT_ID</p>
     */
    public void setContextId(Integer contextId) {
        this.contextId = contextId;
    }

    /**
     * <p>KEY_LIST</p>
     */
    public byte[] getKeyList() {
        return keyList;
    }

    /**
     * <p>KEY_LIST</p>
     */
    public void setKeyList(byte[] keyList) {
        this.keyList = keyList;
    }

    /**
     * <p>SETUP_DATE</p>
     */
    public Date getSetupDate() {
        return setupDate;
    }

    /**
     * <p>SETUP_DATE</p>
     */
    public void setSetupDate(Date setupDate) {
        this.setupDate = setupDate;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("contextId", contextId);
        map.put("keyList", keyList);
        map.put("setupDate", setupDate);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("contextId")) this.setContextId(DataTypeUtils.getIntegerValue(map.get("contextId")));
        if (map.containsKey("setupDate")) this.setSetupDate(DataTypeUtils.getDateValue(map.get("setupDate")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        "contextId="+contextId+
        "keyList="+keyList+
        "setupDate="+setupDate+
        "]";
    }

    public void fillDefaultValues() {
        if (keyList == null) keyList = null;
        if (setupDate == null) setupDate = new Date();
    }
}