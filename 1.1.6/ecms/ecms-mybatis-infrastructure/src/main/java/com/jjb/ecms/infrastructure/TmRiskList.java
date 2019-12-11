package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 风险名单信息表
 * @author jjb
 */
public class TmRiskList implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String risklistSrc;

    private String name;

    private String idType;

    private String idNo;

    private String cellPhone;

    private String homePhone;

    private String homeAdd;

    private String corpName;

    private String empPhone;

    private String empAdd;

    private String reason;

    private String memo;

    private Date validDate;

    private String actType;

    private Integer jpaVersion;

    private String obText1;

    private String obText2;

    private String obText3;

    private String obText4;

    private BigDecimal obDecimal1;

    private BigDecimal obDecimal2;

    private Date updateDate;

    private Date obDate2;

    private Date createDate;

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
     * <p>风险名单来源</p>
     */
    public String getRisklistSrc() {
        return risklistSrc;
    }

    /**
     * <p>风险名单来源</p>
     */
    public void setRisklistSrc(String risklistSrc) {
        this.risklistSrc = risklistSrc;
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
     * <p>家庭电话</p>
     * <p>家庭电话</p>
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * <p>家庭电话</p>
     * <p>家庭电话</p>
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * <p>家庭地址</p>
     * <p>家庭地址</p>
     */
    public String getHomeAdd() {
        return homeAdd;
    }

    /**
     * <p>家庭地址</p>
     * <p>家庭地址</p>
     */
    public void setHomeAdd(String homeAdd) {
        this.homeAdd = homeAdd;
    }

    /**
     * <p>公司名称</p>
     */
    public String getCorpName() {
        return corpName;
    }

    /**
     * <p>公司名称</p>
     */
    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    /**
     * <p>公司电话</p>
     * <p>公司电话</p>
     */
    public String getEmpPhone() {
        return empPhone;
    }

    /**
     * <p>公司电话</p>
     * <p>公司电话</p>
     */
    public void setEmpPhone(String empPhone) {
        this.empPhone = empPhone;
    }

    /**
     * <p>公司地址</p>
     * <p>公司地址</p>
     */
    public String getEmpAdd() {
        return empAdd;
    }

    /**
     * <p>公司地址</p>
     * <p>公司地址</p>
     */
    public void setEmpAdd(String empAdd) {
        this.empAdd = empAdd;
    }

    /**
     * <p>上风险名单原因</p>
     */
    public String getReason() {
        return reason;
    }

    /**
     * <p>上风险名单原因</p>
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * <p>上风险名单原因说明</p>
     */
    public String getMemo() {
        return memo;
    }

    /**
     * <p>上风险名单原因说明</p>
     */
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /**
     * <p>记录有效期</p>
     */
    public Date getValidDate() {
        return validDate;
    }

    /**
     * <p>记录有效期</p>
     */
    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    /**
     * <p>风险名单类型</p>
     */
    public String getActType() {
        return actType;
    }

    /**
     * <p>风险名单类型</p>
     */
    public void setActType(String actType) {
        this.actType = actType;
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
     * <p>预留字段1</p>
     */
    public String getObText1() {
        return obText1;
    }

    /**
     * <p>预留字段1</p>
     */
    public void setObText1(String obText1) {
        this.obText1 = obText1;
    }

    /**
     * <p>预留字段2</p>
     */
    public String getObText2() {
        return obText2;
    }

    /**
     * <p>预留字段2</p>
     */
    public void setObText2(String obText2) {
        this.obText2 = obText2;
    }

    /**
     * <p>预留字段3</p>
     */
    public String getObText3() {
        return obText3;
    }

    /**
     * <p>预留字段3</p>
     */
    public void setObText3(String obText3) {
        this.obText3 = obText3;
    }

    /**
     * <p>预留字段4</p>
     */
    public String getObText4() {
        return obText4;
    }

    /**
     * <p>预留字段4</p>
     */
    public void setObText4(String obText4) {
        this.obText4 = obText4;
    }

    /**
     * <p>预留数字1</p>
     */
    public BigDecimal getObDecimal1() {
        return obDecimal1;
    }

    /**
     * <p>预留数字1</p>
     */
    public void setObDecimal1(BigDecimal obDecimal1) {
        this.obDecimal1 = obDecimal1;
    }

    /**
     * <p>预留数字2</p>
     */
    public BigDecimal getObDecimal2() {
        return obDecimal2;
    }

    /**
     * <p>预留数字2</p>
     */
    public void setObDecimal2(BigDecimal obDecimal2) {
        this.obDecimal2 = obDecimal2;
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
     * <p>预留时间2</p>
     */
    public Date getObDate2() {
        return obDate2;
    }

    /**
     * <p>预留时间2</p>
     */
    public void setObDate2(Date obDate2) {
        this.obDate2 = obDate2;
    }

    /**
     * <p>上单日期</p>
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * <p>上单日期</p>
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("risklistSrc", risklistSrc);
        map.put("name", name);
        map.put("idType", idType);
        map.put("idNo", idNo);
        map.put("cellPhone", cellPhone);
        map.put("homePhone", homePhone);
        map.put("homeAdd", homeAdd);
        map.put("corpName", corpName);
        map.put("empPhone", empPhone);
        map.put("empAdd", empAdd);
        map.put("reason", reason);
        map.put("memo", memo);
        map.put("validDate", validDate);
        map.put("actType", actType);
        map.put("jpaVersion", jpaVersion);
        map.put("obText1", obText1);
        map.put("obText2", obText2);
        map.put("obText3", obText3);
        map.put("obText4", obText4);
        map.put("obDecimal1", obDecimal1);
        map.put("obDecimal2", obDecimal2);
        map.put("updateDate", updateDate);
        map.put("obDate2", obDate2);
        map.put("createDate", createDate);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("risklistSrc")) this.setRisklistSrc(DataTypeUtils.getStringValue(map.get("risklistSrc")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("cellPhone")) this.setCellPhone(DataTypeUtils.getStringValue(map.get("cellPhone")));
        if (map.containsKey("homePhone")) this.setHomePhone(DataTypeUtils.getStringValue(map.get("homePhone")));
        if (map.containsKey("homeAdd")) this.setHomeAdd(DataTypeUtils.getStringValue(map.get("homeAdd")));
        if (map.containsKey("corpName")) this.setCorpName(DataTypeUtils.getStringValue(map.get("corpName")));
        if (map.containsKey("empPhone")) this.setEmpPhone(DataTypeUtils.getStringValue(map.get("empPhone")));
        if (map.containsKey("empAdd")) this.setEmpAdd(DataTypeUtils.getStringValue(map.get("empAdd")));
        if (map.containsKey("reason")) this.setReason(DataTypeUtils.getStringValue(map.get("reason")));
        if (map.containsKey("memo")) this.setMemo(DataTypeUtils.getStringValue(map.get("memo")));
        if (map.containsKey("validDate")) this.setValidDate(DataTypeUtils.getDateValue(map.get("validDate")));
        if (map.containsKey("actType")) this.setActType(DataTypeUtils.getStringValue(map.get("actType")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("obText1")) this.setObText1(DataTypeUtils.getStringValue(map.get("obText1")));
        if (map.containsKey("obText2")) this.setObText2(DataTypeUtils.getStringValue(map.get("obText2")));
        if (map.containsKey("obText3")) this.setObText3(DataTypeUtils.getStringValue(map.get("obText3")));
        if (map.containsKey("obText4")) this.setObText4(DataTypeUtils.getStringValue(map.get("obText4")));
        if (map.containsKey("obDecimal1")) this.setObDecimal1(DataTypeUtils.getBigDecimalValue(map.get("obDecimal1")));
        if (map.containsKey("obDecimal2")) this.setObDecimal2(DataTypeUtils.getBigDecimalValue(map.get("obDecimal2")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("obDate2")) this.setObDate2(DataTypeUtils.getDateValue(map.get("obDate2")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", org="+org+
        "org="+org+
        ", risklistSrc="+risklistSrc+
        "risklistSrc="+risklistSrc+
        ", name="+name+
        "name="+name+
        ", idType="+idType+
        "idType="+idType+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", cellPhone="+cellPhone+
        "cellPhone="+cellPhone+
        ", homePhone="+homePhone+
        "homePhone="+homePhone+
        ", homeAdd="+homeAdd+
        "homeAdd="+homeAdd+
        ", corpName="+corpName+
        "corpName="+corpName+
        ", empPhone="+empPhone+
        "empPhone="+empPhone+
        ", empAdd="+empAdd+
        "empAdd="+empAdd+
        ", reason="+reason+
        "reason="+reason+
        ", memo="+memo+
        "memo="+memo+
        ", validDate="+validDate+
        "validDate="+validDate+
        ", actType="+actType+
        "actType="+actType+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", obText1="+obText1+
        "obText1="+obText1+
        ", obText2="+obText2+
        "obText2="+obText2+
        ", obText3="+obText3+
        "obText3="+obText3+
        ", obText4="+obText4+
        "obText4="+obText4+
        ", obDecimal1="+obDecimal1+
        "obDecimal1="+obDecimal1+
        ", obDecimal2="+obDecimal2+
        "obDecimal2="+obDecimal2+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", obDate2="+obDate2+
        "obDate2="+obDate2+
        ", createDate="+createDate+
        "createDate="+createDate+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (risklistSrc == null) risklistSrc = "";
        if (name == null) name = "";
        if (idType == null) idType = "";
        if (idNo == null) idNo = "";
        if (cellPhone == null) cellPhone = "";
        if (homePhone == null) homePhone = "";
        if (homeAdd == null) homeAdd = "";
        if (corpName == null) corpName = "";
        if (empPhone == null) empPhone = "";
        if (empAdd == null) empAdd = "";
        if (reason == null) reason = "";
        if (memo == null) memo = "";
        if (validDate == null) validDate = new Date();
        if (actType == null) actType = "";
        if (jpaVersion == null) jpaVersion = 0;
        if (obText1 == null) obText1 = "";
        if (obText2 == null) obText2 = "";
        if (obText3 == null) obText3 = "";
        if (obText4 == null) obText4 = "";
        if (obDecimal1 == null) obDecimal1 = BigDecimal.ZERO;
        if (obDecimal2 == null) obDecimal2 = BigDecimal.ZERO;
        if (updateDate == null) updateDate = new Date();
        if (obDate2 == null) obDate2 = new Date();
        if (createDate == null) createDate = new Date();
    }
}