package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自选卡号表
 * @author jjb
 */
public class TmLuckyCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String appNo;

    private String org;

    private String name;

    private String idType;

    private String idNo;

    private String cellphone;

    private String cardNo;

    private String lockReason;

    private String operId;

    private Date updateTime;

    private Integer jpaVersion;

    private String status;

    /**
     * <p>ID</p>
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>ID</p>
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>申请编号</p>
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * <p>申请编号</p>
     */
    public void setAppNo(String appNo) {
        this.appNo = appNo;
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
     * <p>姓名</p>
     */
    public String getName() {
        return name;
    }

    /**
     * <p>姓名</p>
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>证件类型</p>
     */
    public String getIdType() {
        return idType;
    }

    /**
     * <p>证件类型</p>
     */
    public void setIdType(String idType) {
        this.idType = idType;
    }

    /**
     * <p>证件号码</p>
     */
    public String getIdNo() {
        return idNo;
    }

    /**
     * <p>证件号码</p>
     */
    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    /**
     * <p>移动电话</p>
     */
    public String getCellphone() {
        return cellphone;
    }

    /**
     * <p>移动电话</p>
     */
    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    /**
     * <p>卡号</p>
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * <p>卡号</p>
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * <p>锁定原因</p>
     */
    public String getLockReason() {
        return lockReason;
    }

    /**
     * <p>锁定原因</p>
     */
    public void setLockReason(String lockReason) {
        this.lockReason = lockReason;
    }

    /**
     * <p>操作员ID</p>
     */
    public String getOperId() {
        return operId;
    }

    /**
     * <p>操作员ID</p>
     */
    public void setOperId(String operId) {
        this.operId = operId;
    }

    /**
     * <p>更新时间</p>
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * <p>更新时间</p>
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>JPA_VERSION</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    /**
     * <p>解锁状态</p>
     */
    public String getStatus() {
        return status;
    }

    /**
     * <p>解锁状态</p>
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("appNo", appNo);
        map.put("org", org);
        map.put("name", name);
        map.put("idType", idType);
        map.put("idNo", idNo);
        map.put("cellphone", cellphone);
        map.put("cardNo", cardNo);
        map.put("lockReason", lockReason);
        map.put("operId", operId);
        map.put("updateTime", updateTime);
        map.put("jpaVersion", jpaVersion);
        map.put("status", status);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getLongValue(map.get("id")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("cellphone")) this.setCellphone(DataTypeUtils.getStringValue(map.get("cellphone")));
        if (map.containsKey("cardNo")) this.setCardNo(DataTypeUtils.getStringValue(map.get("cardNo")));
        if (map.containsKey("lockReason")) this.setLockReason(DataTypeUtils.getStringValue(map.get("lockReason")));
        if (map.containsKey("operId")) this.setOperId(DataTypeUtils.getStringValue(map.get("operId")));
        if (map.containsKey("updateTime")) this.setUpdateTime(DataTypeUtils.getDateValue(map.get("updateTime")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("status")) this.setStatus(DataTypeUtils.getStringValue(map.get("status")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", org="+org+
        "org="+org+
        ", name="+name+
        "name="+name+
        ", idType="+idType+
        "idType="+idType+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", cellphone="+cellphone+
        "cellphone="+cellphone+
        ", cardNo="+cardNo+
        "cardNo="+cardNo+
        ", lockReason="+lockReason+
        "lockReason="+lockReason+
        ", operId="+operId+
        "operId="+operId+
        ", updateTime="+updateTime+
        "updateTime="+updateTime+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", status="+status+
        "status="+status+
        "]";
    }

    public void fillDefaultValues() {
        if (appNo == null) appNo = "";
        if (org == null) org = "";
        if (name == null) name = "";
        if (idType == null) idType = "";
        if (idNo == null) idNo = "";
        if (cellphone == null) cellphone = "";
        if (cardNo == null) cardNo = "";
        if (lockReason == null) lockReason = "";
        if (operId == null) operId = "";
        if (updateTime == null) updateTime = new Date();
        if (jpaVersion == null) jpaVersion = 0;
        if (status == null) status = "";
    }
}