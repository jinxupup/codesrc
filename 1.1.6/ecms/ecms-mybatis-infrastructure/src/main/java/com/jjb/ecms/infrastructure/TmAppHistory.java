package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请审批操作历史表
 * @author jjb
 */
public class TmAppHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String name;

    private String idNo;

    private String proNum;

    private String proName;

    private String rtfState;

    private String refuseCode;

    private String remark;

    private String operatorId;

    private Date createDate;

    private Integer jpaVersion;

    private String idType;

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
     * <p>流程实例号</p>
     */
    public String getProNum() {
        return proNum;
    }

    /**
     * <p>流程实例号</p>
     */
    public void setProNum(String proNum) {
        this.proNum = proNum;
    }

    /**
     * <p>流程节点名称</p>
     */
    public String getProName() {
        return proName;
    }

    /**
     * <p>流程节点名称</p>
     */
    public void setProName(String proName) {
        this.proName = proName;
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
     * <p>拒绝原因码</p>
     */
    public String getRefuseCode() {
        return refuseCode;
    }

    /**
     * <p>拒绝原因码</p>
     */
    public void setRefuseCode(String refuseCode) {
        this.refuseCode = refuseCode;
    }

    /**
     * <p>备注</p>
     * <p>备注</p>
     */
    public String getRemark() {
        return remark;
    }

    /**
     * <p>备注</p>
     * <p>备注</p>
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
     * <p>创建日期</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>创建日期</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("name", name);
        map.put("idNo", idNo);
        map.put("proNum", proNum);
        map.put("proName", proName);
        map.put("rtfState", rtfState);
        map.put("refuseCode", refuseCode);
        map.put("remark", remark);
        map.put("operatorId", operatorId);
        map.put("createDate", createDate);
        map.put("jpaVersion", jpaVersion);
        map.put("idType", idType);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("proNum")) this.setProNum(DataTypeUtils.getStringValue(map.get("proNum")));
        if (map.containsKey("proName")) this.setProName(DataTypeUtils.getStringValue(map.get("proName")));
        if (map.containsKey("rtfState")) this.setRtfState(DataTypeUtils.getStringValue(map.get("rtfState")));
        if (map.containsKey("refuseCode")) this.setRefuseCode(DataTypeUtils.getStringValue(map.get("refuseCode")));
        if (map.containsKey("remark")) this.setRemark(DataTypeUtils.getStringValue(map.get("remark")));
        if (map.containsKey("operatorId")) this.setOperatorId(DataTypeUtils.getStringValue(map.get("operatorId")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
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
        ", proNum="+proNum+
        "proNum="+proNum+
        ", proName="+proName+
        "proName="+proName+
        ", rtfState="+rtfState+
        "rtfState="+rtfState+
        ", refuseCode="+refuseCode+
        "refuseCode="+refuseCode+
        ", remark="+remark+
        "remark="+remark+
        ", operatorId="+operatorId+
        "operatorId="+operatorId+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", idType="+idType+
        "idType="+idType+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (name == null) name = "";
        if (idNo == null) idNo = "";
        if (proNum == null) proNum = "";
        if (proName == null) proName = "";
        if (rtfState == null) rtfState = "";
        if (refuseCode == null) refuseCode = "";
        if (remark == null) remark = "";
        if (operatorId == null) operatorId = "";
        if (createDate == null) createDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
        if (idType == null) idType = "";
    }
}