package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 法人担保信息
 * @author jjb
 */
public class TmAppLegalperson implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String appNo;

    private String companyName;

    private String enterpriseNature;

    private String idType;

    private String idNumber;

    private String setupTime;

    private String represeNtative;

    private String represeNtativephone;

    private BigDecimal registeredCapital;

    private BigDecimal realizedCapital;

    private BigDecimal annualTurnover;

    private BigDecimal profit;

    private String operateAddress;

    private Date createDate;

    private String createUser;

    private Date updateDate;

    private String updateUser;

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
     * <p>公司名称</p>
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * <p>公司名称</p>
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * <p>企业性质</p>
     */
    public String getEnterpriseNature() {
        return enterpriseNature;
    }

    /**
     * <p>企业性质</p>
     */
    public void setEnterpriseNature(String enterpriseNature) {
        this.enterpriseNature = enterpriseNature;
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
    public String getIdNumber() {
        return idNumber;
    }

    /**
     * <p>证件号码</p>
     */
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * <p>成立时间</p>
     */
    public String getSetupTime() {
        return setupTime;
    }

    /**
     * <p>成立时间</p>
     */
    public void setSetupTime(String setupTime) {
        this.setupTime = setupTime;
    }

    /**
     * <p>法人代表</p>
     */
    public String getRepreseNtative() {
        return represeNtative;
    }

    /**
     * <p>法人代表</p>
     */
    public void setRepreseNtative(String represeNtative) {
        this.represeNtative = represeNtative;
    }

    /**
     * <p>联系电话</p>
     */
    public String getRepreseNtativephone() {
        return represeNtativephone;
    }

    /**
     * <p>联系电话</p>
     */
    public void setRepreseNtativephone(String represeNtativephone) {
        this.represeNtativephone = represeNtativephone;
    }

    /**
     * <p>注册资金(元)</p>
     */
    public BigDecimal getRegisteredCapital() {
        return registeredCapital;
    }

    /**
     * <p>注册资金(元)</p>
     */
    public void setRegisteredCapital(BigDecimal registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    /**
     * <p>实到资金(元)</p>
     */
    public BigDecimal getRealizedCapital() {
        return realizedCapital;
    }

    /**
     * <p>实到资金(元)</p>
     */
    public void setRealizedCapital(BigDecimal realizedCapital) {
        this.realizedCapital = realizedCapital;
    }

    /**
     * <p>年营业额(元)</p>
     */
    public BigDecimal getAnnualTurnover() {
        return annualTurnover;
    }

    /**
     * <p>年营业额(元)</p>
     */
    public void setAnnualTurnover(BigDecimal annualTurnover) {
        this.annualTurnover = annualTurnover;
    }

    /**
     * <p>年利润(元)</p>
     */
    public BigDecimal getProfit() {
        return profit;
    }

    /**
     * <p>年利润(元)</p>
     */
    public void setProfit(BigDecimal profit) {
        this.profit = profit;
    }

    /**
     * <p>经营地址</p>
     */
    public String getOperateAddress() {
        return operateAddress;
    }

    /**
     * <p>经营地址</p>
     */
    public void setOperateAddress(String operateAddress) {
        this.operateAddress = operateAddress;
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
     * <p>创建人</p>
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * <p>创建人</p>
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
     * <p>更新人</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>更新人</p>
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
        map.put("appNo", appNo);
        map.put("companyName", companyName);
        map.put("enterpriseNature", enterpriseNature);
        map.put("idType", idType);
        map.put("idNumber", idNumber);
        map.put("setupTime", setupTime);
        map.put("represeNtative", represeNtative);
        map.put("represeNtativephone", represeNtativephone);
        map.put("registeredCapital", registeredCapital);
        map.put("realizedCapital", realizedCapital);
        map.put("annualTurnover", annualTurnover);
        map.put("profit", profit);
        map.put("operateAddress", operateAddress);
        map.put("createDate", createDate);
        map.put("createUser", createUser);
        map.put("updateDate", updateDate);
        map.put("updateUser", updateUser);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("companyName")) this.setCompanyName(DataTypeUtils.getStringValue(map.get("companyName")));
        if (map.containsKey("enterpriseNature")) this.setEnterpriseNature(DataTypeUtils.getStringValue(map.get("enterpriseNature")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("idNumber")) this.setIdNumber(DataTypeUtils.getStringValue(map.get("idNumber")));
        if (map.containsKey("setupTime")) this.setSetupTime(DataTypeUtils.getStringValue(map.get("setupTime")));
        if (map.containsKey("represeNtative")) this.setRepreseNtative(DataTypeUtils.getStringValue(map.get("represeNtative")));
        if (map.containsKey("represeNtativephone")) this.setRepreseNtativephone(DataTypeUtils.getStringValue(map.get("represeNtativephone")));
        if (map.containsKey("registeredCapital")) this.setRegisteredCapital(DataTypeUtils.getBigDecimalValue(map.get("registeredCapital")));
        if (map.containsKey("realizedCapital")) this.setRealizedCapital(DataTypeUtils.getBigDecimalValue(map.get("realizedCapital")));
        if (map.containsKey("annualTurnover")) this.setAnnualTurnover(DataTypeUtils.getBigDecimalValue(map.get("annualTurnover")));
        if (map.containsKey("profit")) this.setProfit(DataTypeUtils.getBigDecimalValue(map.get("profit")));
        if (map.containsKey("operateAddress")) this.setOperateAddress(DataTypeUtils.getStringValue(map.get("operateAddress")));
        if (map.containsKey("createDate")) this.setCreateDate(DataTypeUtils.getDateValue(map.get("createDate")));
        if (map.containsKey("createUser")) this.setCreateUser(DataTypeUtils.getStringValue(map.get("createUser")));
        if (map.containsKey("updateDate")) this.setUpdateDate(DataTypeUtils.getDateValue(map.get("updateDate")));
        if (map.containsKey("updateUser")) this.setUpdateUser(DataTypeUtils.getStringValue(map.get("updateUser")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", id="+id+
        "id="+id+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", companyName="+companyName+
        "companyName="+companyName+
        ", enterpriseNature="+enterpriseNature+
        "enterpriseNature="+enterpriseNature+
        ", idType="+idType+
        "idType="+idType+
        ", idNumber="+idNumber+
        "idNumber="+idNumber+
        ", setupTime="+setupTime+
        "setupTime="+setupTime+
        ", represeNtative="+represeNtative+
        "represeNtative="+represeNtative+
        ", represeNtativephone="+represeNtativephone+
        "represeNtativephone="+represeNtativephone+
        ", registeredCapital="+registeredCapital+
        "registeredCapital="+registeredCapital+
        ", realizedCapital="+realizedCapital+
        "realizedCapital="+realizedCapital+
        ", annualTurnover="+annualTurnover+
        "annualTurnover="+annualTurnover+
        ", profit="+profit+
        "profit="+profit+
        ", operateAddress="+operateAddress+
        "operateAddress="+operateAddress+
        ", createDate="+createDate+
        "createDate="+createDate+
        ", createUser="+createUser+
        "createUser="+createUser+
        ", updateDate="+updateDate+
        "updateDate="+updateDate+
        ", updateUser="+updateUser+
        "updateUser="+updateUser+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        "]";
    }

    public void fillDefaultValues() {
        if (appNo == null) appNo = "";
        if (companyName == null) companyName = "";
        if (enterpriseNature == null) enterpriseNature = "";
        if (idType == null) idType = "";
        if (idNumber == null) idNumber = "";
        if (setupTime == null) setupTime = "";
        if (represeNtative == null) represeNtative = "";
        if (represeNtativephone == null) represeNtativephone = "";
        if (registeredCapital == null) registeredCapital = BigDecimal.ZERO;
        if (realizedCapital == null) realizedCapital = BigDecimal.ZERO;
        if (annualTurnover == null) annualTurnover = BigDecimal.ZERO;
        if (profit == null) profit = BigDecimal.ZERO;
        if (operateAddress == null) operateAddress = "";
        if (createDate == null) createDate = new Date();
        if (createUser == null) createUser = "";
        if (updateDate == null) updateDate = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}