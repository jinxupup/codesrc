package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 合伙人信息
 * @author jjb
 */
public class TmAppPartner implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String idNo;

    private String cellPhone;

    private String exitCard;

    private String partnerType;

    private String corpName;

    private String empDepartment;

    private String decisionResult;

    private String refuseReason;

    private String decisionJson;

    private Date createDate;

    private Date updateDate;

    private Integer jpaVersion;

    /**
     * <p>标志</p>
     * <p>主键自增</p>
     */
    public Integer getId() {
        return id;
    }

    /**
     * <p>标志</p>
     * <p>主键自增</p>
     */
    public void setId(Integer id) {
        this.id = id;
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
     * <p>移动电话</p>
     */
    public String getCellPhone() {
        return cellPhone;
    }

    /**
     * <p>移动电话</p>
     */
    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    /**
     * <p>是否已有卡客户</p>
     * <p>是否已有卡客户</p>
     */
    public String getExitCard() {
        return exitCard;
    }

    /**
     * <p>是否已有卡客户</p>
     * <p>是否已有卡客户</p>
     */
    public void setExitCard(String exitCard) {
        this.exitCard = exitCard;
    }

    /**
     * <p>合伙人类型</p>
     */
    public String getPartnerType() {
        return partnerType;
    }

    /**
     * <p>合伙人类型</p>
     */
    public void setPartnerType(String partnerType) {
        this.partnerType = partnerType;
    }

    /**
     * <p>单位名称</p>
     */
    public String getCorpName() {
        return corpName;
    }

    /**
     * <p>单位名称</p>
     */
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    /**
     * <p>部门</p>
     */
    public String getEmpDepartment() {
        return empDepartment;
    }

    /**
     * <p>部门</p>
     */
    public void setEmpDepartment(String empDepartment) {
        this.empDepartment = empDepartment;
    }

    /**
     * <p>决策结果</p>
     */
    public String getDecisionResult() {
        return decisionResult;
    }

    /**
     * <p>决策结果</p>
     */
    public void setDecisionResult(String decisionResult) {
        this.decisionResult = decisionResult;
    }

    /**
     * <p>拒绝原因</p>
     */
    public String getRefuseReason() {
        return refuseReason;
    }

    /**
     * <p>拒绝原因</p>
     */
    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    /**
     * <p>决策返回报文</p>
     */
    public String getDecisionJson() {
        return decisionJson;
    }

    /**
     * <p>决策返回报文</p>
     */
    public void setDecisionJson(String decisionJson) {
        this.decisionJson = decisionJson;
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
     * <p>更新时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>更新时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
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

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("name", name);
        map.put("idNo", idNo);
        map.put("cellPhone", cellPhone);
        map.put("exitCard", exitCard);
        map.put("partnerType", partnerType);
        map.put("corpName", corpName);
        map.put("empDepartment", empDepartment);
        map.put("decisionResult", decisionResult);
        map.put("refuseReason", refuseReason);
        map.put("decisionJson", decisionJson);
        map.put("createDate", createDate);
        map.put("updateDate", updateDate);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("cellPhone")) this.setCellPhone(DataTypeUtils.getStringValue(map.get("cellPhone")));
        if (map.containsKey("exitCard")) this.setExitCard(DataTypeUtils.getStringValue(map.get("exitCard")));
        if (map.containsKey("partnerType")) this.setPartnerType(DataTypeUtils.getStringValue(map.get("partnerType")));
        if (map.containsKey("corpName")) this.setCorpName(DataTypeUtils.getStringValue(map.get("corpName")));
        if (map.containsKey("empDepartment")) this.setEmpDepartment(DataTypeUtils.getStringValue(map.get("empDepartment")));
        if (map.containsKey("decisionResult")) this.setDecisionResult(DataTypeUtils.getStringValue(map.get("decisionResult")));
        if (map.containsKey("refuseReason")) this.setRefuseReason(DataTypeUtils.getStringValue(map.get("refuseReason")));
        if (map.containsKey("decisionJson")) this.setDecisionJson(DataTypeUtils.getStringValue(map.get("decisionJson")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", name="+name+
        "name="+name+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", cellPhone="+cellPhone+
        "cellPhone="+cellPhone+
        ", exitCard="+exitCard+
        "exitCard="+exitCard+
        ", partnerType="+partnerType+
        "partnerType="+partnerType+
        ", corpName="+corpName+
        "corpName="+corpName+
        ", empDepartment="+empDepartment+
        "empDepartment="+empDepartment+
        ", decisionResult="+decisionResult+
        "decisionResult="+decisionResult+
        ", refuseReason="+refuseReason+
        "refuseReason="+refuseReason+
        ", decisionJson="+decisionJson+
        "decisionJson="+decisionJson+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (name == null) name = "";
        if (idNo == null) idNo = "";
        if (cellPhone == null) cellPhone = "";
        if (exitCard == null) exitCard = "";
        if (partnerType == null) partnerType = "";
        if (corpName == null) corpName = "";
        if (empDepartment == null) empDepartment = "";
        if (decisionResult == null) decisionResult = "";
        if (refuseReason == null) refuseReason = "";
        if (decisionJson == null) decisionJson = "";
        if (createDate == null) createDate = new Date();
        if (updateDate == null) updateDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
    }
}