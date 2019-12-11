package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务审计历史信息
 * @author jjb
 */
public class TmBizAudit implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String name;

    private String idNo;

    private String ordType;

    private String rtfState;

    private String operatorId;

    private Date createDate;

    private String operatorDo;

    private Integer jpaVersion;

    /**
     * <p>标识</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>标识</p>
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
     * <p>申请件状态</p>
     */
    public String getOrdType() {
        return ordType;
    }

    /**
     * <p>申请件状态</p>
     */
    public void setOrdType(String ordType) {
        this.ordType = ordType;
    }

    /**
     * <p>审批状态</p>
     */
    public String getRtfState() {
        return rtfState;
    }

    /**
     * <p>审批状态</p>
     */
    public void setRtfState(String rtfState) {
        this.rtfState = rtfState;
    }

    /**
     * <p>操作员ID</p>
     */
    public String getOperatorId() {
        return operatorId;
    }

    /**
     * <p>操作员ID</p>
     */
    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    /**
     * <p>创建时间</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>创建时间</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * <p>详细信息</p>
     */
    public String getOperatorDo() {
        return operatorDo;
    }

    /**
     * <p>详细信息</p>
     */
    public void setOperatorDo(String operatorDo) {
        this.operatorDo = operatorDo;
    }

    /**
     * <p>乐观锁</p>
     */
    public Integer getJpaVersion() {
        return jpaVersion;
    }

    /**
     * <p>乐观锁</p>
     */
    public void setJpaVersion(Integer jpaVersion) {
        this.jpaVersion = jpaVersion;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("name", name);
        map.put("idNo", idNo);
        map.put("ordType", ordType);
        map.put("rtfState", rtfState);
        map.put("operatorId", operatorId);
        map.put("createDate", createDate);
        map.put("operatorDo", operatorDo);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("ordType")) this.setOrdType(DataTypeUtils.getStringValue(map.get("ordType")));
        if (map.containsKey("rtfState")) this.setRtfState(DataTypeUtils.getStringValue(map.get("rtfState")));
        if (map.containsKey("operatorId")) this.setOperatorId(DataTypeUtils.getStringValue(map.get("operatorId")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("operatorDo")) this.setOperatorDo(DataTypeUtils.getStringValue(map.get("operatorDo")));
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
        ", name="+name+
        "name="+name+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", ordType="+ordType+
        "ordType="+ordType+
        ", rtfState="+rtfState+
        "rtfState="+rtfState+
        ", operatorId="+operatorId+
        "operatorId="+operatorId+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", operatorDo="+operatorDo+
        "operatorDo="+operatorDo+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (name == null) name = "";
        if (idNo == null) idNo = "";
        if (ordType == null) ordType = "";
        if (rtfState == null) rtfState = "";
        if (operatorId == null) operatorId = "";
        if (createDate == null) createDate = new Date();
        if (operatorDo == null) operatorDo = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}