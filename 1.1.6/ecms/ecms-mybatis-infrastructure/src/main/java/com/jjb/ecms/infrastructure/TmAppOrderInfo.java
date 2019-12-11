package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 订单信息表
 * @author jjb
 */
public class TmAppOrderInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String licenseType;

    private String licenseperson;

    private String brandnAme;

    private String userDefined;

    private String guidePrice;

    private BigDecimal usedTime;

    private String vin;

    private String engineNo;

    private String mileage;

    private String firstDate;

    private String appraisal;

    private String obText3;

    private String obText4;

    private BigDecimal obDecimal1;

    private BigDecimal obDecimal2;

    private Date obDate1;

    private Date obDate2;

    private Integer jpaVersion;

    private String carseriesName;

    private String carmodelName;

    private String year;

    private String exownerName;

    private String exidType;

    private String exidNumber;

    private String exphoneNumber;

    private BigDecimal projectSum;

    private BigDecimal financingSum;

    private Integer leasingTerm;

    private BigDecimal rentSum;

    private BigDecimal monthPayment;

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
     * <p>贷款业务编号</p>
     */
    public String getAppNo() {
        return appNo;
    }

    /**
     * <p>贷款业务编号</p>
     */
    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    /**
     * <p>牌照类型</p>
     */
    public String getLicenseType() {
        return licenseType;
    }

    /**
     * <p>牌照类型</p>
     */
    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    /**
     * <p>上牌人</p>
     */
    public String getLicenseperson() {
        return licenseperson;
    }

    /**
     * <p>上牌人</p>
     */
    public void setLicenseperson(String licenseperson) {
        this.licenseperson = licenseperson;
    }

    /**
     * <p>品牌</p>
     */
    public String getBrandnAme() {
        return brandnAme;
    }

    /**
     * <p>品牌</p>
     */
    public void setBrandnAme(String brandnAme) {
        this.brandnAme = brandnAme;
    }

    /**
     * <p>自定义车型</p>
     */
    public String getUserDefined() {
        return userDefined;
    }

    /**
     * <p>自定义车型</p>
     */
    public void setUserDefined(String userDefined) {
        this.userDefined = userDefined;
    }

    /**
     * <p>车辆指导价</p>
     */
    public String getGuidePrice() {
        return guidePrice;
    }

    /**
     * <p>车辆指导价</p>
     */
    public void setGuidePrice(String guidePrice) {
        this.guidePrice = guidePrice;
    }

    /**
     * <p>车龄（年）</p>
     * <p>备注</p>
     */
    public BigDecimal getUsedTime() {
        return usedTime;
    }

    /**
     * <p>车龄（年）</p>
     * <p>备注</p>
     */
    public void setUsedTime(BigDecimal usedTime) {
        this.usedTime = usedTime;
    }

    /**
     * <p>车架号</p>
     */
    public String getVin() {
        return vin;
    }

    /**
     * <p>车架号</p>
     */
    public void setVin(String vin) {
        this.vin = vin;
    }

    /**
     * <p>发动机号</p>
     */
    public String getEngineNo() {
        return engineNo;
    }

    /**
     * <p>发动机号</p>
     */
    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }

    /**
     * <p>里程数</p>
     */
    public String getMileage() {
        return mileage;
    }

    /**
     * <p>里程数</p>
     */
    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    /**
     * <p>首次上牌时间</p>
     */
    public String getFirstDate() {
        return firstDate;
    }

    /**
     * <p>首次上牌时间</p>
     */
    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    /**
     * <p>评估价值</p>
     */
    public String getAppraisal() {
        return appraisal;
    }

    /**
     * <p>评估价值</p>
     */
    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal;
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
     * <p>预留时间1</p>
     */
    public Date getObDate1() {
        return obDate1;
    }

    /**
     * <p>预留时间1</p>
     */
    public void setObDate1(Date obDate1) {
        this.obDate1 = obDate1;
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
     * <p>车系</p>
     */
    public String getCarseriesName() {
        return carseriesName;
    }

    /**
     * <p>车系</p>
     */
    public void setCarseriesName(String carseriesName) {
        this.carseriesName = carseriesName;
    }

    /**
     * <p>车型</p>
     */
    public String getCarmodelName() {
        return carmodelName;
    }

    /**
     * <p>车型</p>
     */
    public void setCarmodelName(String carmodelName) {
        this.carmodelName = carmodelName;
    }

    /**
     * <p>年份</p>
     */
    public String getYear() {
        return year;
    }

    /**
     * <p>年份</p>
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * <p>原有车辆所有人姓名</p>
     */
    public String getExownerName() {
        return exownerName;
    }

    /**
     * <p>原有车辆所有人姓名</p>
     */
    public void setExownerName(String exownerName) {
        this.exownerName = exownerName;
    }

    /**
     * <p>原有车辆所有人证件类型</p>
     */
    public String getExidType() {
        return exidType;
    }

    /**
     * <p>原有车辆所有人证件类型</p>
     */
    public void setExidType(String exidType) {
        this.exidType = exidType;
    }

    /**
     * <p>原有车辆所有人证件号码</p>
     */
    public String getExidNumber() {
        return exidNumber;
    }

    /**
     * <p>原有车辆所有人证件号码</p>
     */
    public void setExidNumber(String exidNumber) {
        this.exidNumber = exidNumber;
    }

    /**
     * <p>原有车辆所有人手机号码</p>
     */
    public String getExphoneNumber() {
        return exphoneNumber;
    }

    /**
     * <p>原有车辆所有人手机号码</p>
     */
    public void setExphoneNumber(String exphoneNumber) {
        this.exphoneNumber = exphoneNumber;
    }

    /**
     * <p>项目总额（元）</p>
     */
    public BigDecimal getProjectSum() {
        return projectSum;
    }

    /**
     * <p>项目总额（元）</p>
     */
    public void setProjectSum(BigDecimal projectSum) {
        this.projectSum = projectSum;
    }

    /**
     * <p>融资金额（元）</p>
     */
    public BigDecimal getFinancingSum() {
        return financingSum;
    }

    /**
     * <p>融资金额（元）</p>
     */
    public void setFinancingSum(BigDecimal financingSum) {
        this.financingSum = financingSum;
    }

    /**
     * <p>租赁期限（月）</p>
     */
    public Integer getLeasingTerm() {
        return leasingTerm;
    }

    /**
     * <p>租赁期限（月）</p>
     */
    public void setLeasingTerm(Integer leasingTerm) {
        this.leasingTerm = leasingTerm;
    }

    /**
     * <p>租金总额（元）</p>
     */
    public BigDecimal getRentSum() {
        return rentSum;
    }

    /**
     * <p>租金总额（元）</p>
     */
    public void setRentSum(BigDecimal rentSum) {
        this.rentSum = rentSum;
    }

    /**
     * <p>每期租金（元）</p>
     */
    public BigDecimal getMonthPayment() {
        return monthPayment;
    }

    /**
     * <p>每期租金（元）</p>
     */
    public void setMonthPayment(BigDecimal monthPayment) {
        this.monthPayment = monthPayment;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("id", id);
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("licenseType", licenseType);
        map.put("licenseperson", licenseperson);
        map.put("brandnAme", brandnAme);
        map.put("userDefined", userDefined);
        map.put("guidePrice", guidePrice);
        map.put("usedTime", usedTime);
        map.put("vin", vin);
        map.put("engineNo", engineNo);
        map.put("mileage", mileage);
        map.put("firstDate", firstDate);
        map.put("appraisal", appraisal);
        map.put("obText3", obText3);
        map.put("obText4", obText4);
        map.put("obDecimal1", obDecimal1);
        map.put("obDecimal2", obDecimal2);
        map.put("obDate1", obDate1);
        map.put("obDate2", obDate2);
        map.put("jpaVersion", jpaVersion);
        map.put("carseriesName", carseriesName);
        map.put("carmodelName", carmodelName);
        map.put("year", year);
        map.put("exownerName", exownerName);
        map.put("exidType", exidType);
        map.put("exidNumber", exidNumber);
        map.put("exphoneNumber", exphoneNumber);
        map.put("projectSum", projectSum);
        map.put("financingSum", financingSum);
        map.put("leasingTerm", leasingTerm);
        map.put("rentSum", rentSum);
        map.put("monthPayment", monthPayment);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("licenseType")) this.setLicenseType(DataTypeUtils.getStringValue(map.get("licenseType")));
        if (map.containsKey("licenseperson")) this.setLicenseperson(DataTypeUtils.getStringValue(map.get("licenseperson")));
        if (map.containsKey("brandnAme")) this.setBrandnAme(DataTypeUtils.getStringValue(map.get("brandnAme")));
        if (map.containsKey("userDefined")) this.setUserDefined(DataTypeUtils.getStringValue(map.get("userDefined")));
        if (map.containsKey("guidePrice")) this.setGuidePrice(DataTypeUtils.getStringValue(map.get("guidePrice")));
        if (map.containsKey("usedTime")) this.setUsedTime(DataTypeUtils.getBigDecimalValue(map.get("usedTime")));
        if (map.containsKey("vin")) this.setVin(DataTypeUtils.getStringValue(map.get("vin")));
        if (map.containsKey("engineNo")) this.setEngineNo(DataTypeUtils.getStringValue(map.get("engineNo")));
        if (map.containsKey("mileage")) this.setMileage(DataTypeUtils.getStringValue(map.get("mileage")));
        if (map.containsKey("firstDate")) this.setFirstDate(DataTypeUtils.getStringValue(map.get("firstDate")));
        if (map.containsKey("appraisal")) this.setAppraisal(DataTypeUtils.getStringValue(map.get("appraisal")));
        if (map.containsKey("obText3")) this.setObText3(DataTypeUtils.getStringValue(map.get("obText3")));
        if (map.containsKey("obText4")) this.setObText4(DataTypeUtils.getStringValue(map.get("obText4")));
        if (map.containsKey("obDecimal1")) this.setObDecimal1(DataTypeUtils.getBigDecimalValue(map.get("obDecimal1")));
        if (map.containsKey("obDecimal2")) this.setObDecimal2(DataTypeUtils.getBigDecimalValue(map.get("obDecimal2")));
        if (map.containsKey("obDate1")) this.setObDate1(DataTypeUtils.getDateValue(map.get("obDate1")));
        if (map.containsKey("obDate2")) this.setObDate2(DataTypeUtils.getDateValue(map.get("obDate2")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("carseriesName")) this.setCarseriesName(DataTypeUtils.getStringValue(map.get("carseriesName")));
        if (map.containsKey("carmodelName")) this.setCarmodelName(DataTypeUtils.getStringValue(map.get("carmodelName")));
        if (map.containsKey("year")) this.setYear(DataTypeUtils.getStringValue(map.get("year")));
        if (map.containsKey("exownerName")) this.setExownerName(DataTypeUtils.getStringValue(map.get("exownerName")));
        if (map.containsKey("exidType")) this.setExidType(DataTypeUtils.getStringValue(map.get("exidType")));
        if (map.containsKey("exidNumber")) this.setExidNumber(DataTypeUtils.getStringValue(map.get("exidNumber")));
        if (map.containsKey("exphoneNumber")) this.setExphoneNumber(DataTypeUtils.getStringValue(map.get("exphoneNumber")));
        if (map.containsKey("projectSum")) this.setProjectSum(DataTypeUtils.getBigDecimalValue(map.get("projectSum")));
        if (map.containsKey("financingSum")) this.setFinancingSum(DataTypeUtils.getBigDecimalValue(map.get("financingSum")));
        if (map.containsKey("leasingTerm")) this.setLeasingTerm(DataTypeUtils.getIntegerValue(map.get("leasingTerm")));
        if (map.containsKey("rentSum")) this.setRentSum(DataTypeUtils.getBigDecimalValue(map.get("rentSum")));
        if (map.containsKey("monthPayment")) this.setMonthPayment(DataTypeUtils.getBigDecimalValue(map.get("monthPayment")));
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
        ", licenseType="+licenseType+
        "licenseType="+licenseType+
        ", licenseperson="+licenseperson+
        "licenseperson="+licenseperson+
        ", brandnAme="+brandnAme+
        "brandnAme="+brandnAme+
        ", userDefined="+userDefined+
        "userDefined="+userDefined+
        ", guidePrice="+guidePrice+
        "guidePrice="+guidePrice+
        ", usedTime="+usedTime+
        "usedTime="+usedTime+
        ", vin="+vin+
        "vin="+vin+
        ", engineNo="+engineNo+
        "engineNo="+engineNo+
        ", mileage="+mileage+
        "mileage="+mileage+
        ", firstDate="+firstDate+
        "firstDate="+firstDate+
        ", appraisal="+appraisal+
        "appraisal="+appraisal+
        ", obText3="+obText3+
        "obText3="+obText3+
        ", obText4="+obText4+
        "obText4="+obText4+
        ", obDecimal1="+obDecimal1+
        "obDecimal1="+obDecimal1+
        ", obDecimal2="+obDecimal2+
        "obDecimal2="+obDecimal2+
        ", obDate1="+obDate1+
        "obDate1="+obDate1+
        ", obDate2="+obDate2+
        "obDate2="+obDate2+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", carseriesName="+carseriesName+
        "carseriesName="+carseriesName+
        ", carmodelName="+carmodelName+
        "carmodelName="+carmodelName+
        ", year="+year+
        "year="+year+
        ", exownerName="+exownerName+
        "exownerName="+exownerName+
        ", exidType="+exidType+
        "exidType="+exidType+
        ", exidNumber="+exidNumber+
        "exidNumber="+exidNumber+
        ", exphoneNumber="+exphoneNumber+
        "exphoneNumber="+exphoneNumber+
        ", projectSum="+projectSum+
        "projectSum="+projectSum+
        ", financingSum="+financingSum+
        "financingSum="+financingSum+
        ", leasingTerm="+leasingTerm+
        "leasingTerm="+leasingTerm+
        ", rentSum="+rentSum+
        "rentSum="+rentSum+
        ", monthPayment="+monthPayment+
        "monthPayment="+monthPayment+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (licenseType == null) licenseType = "";
        if (licenseperson == null) licenseperson = "";
        if (brandnAme == null) brandnAme = "";
        if (userDefined == null) userDefined = "";
        if (guidePrice == null) guidePrice = "";
        if (usedTime == null) usedTime = BigDecimal.ZERO;
        if (vin == null) vin = "";
        if (engineNo == null) engineNo = "";
        if (mileage == null) mileage = "";
        if (firstDate == null) firstDate = "";
        if (appraisal == null) appraisal = "";
        if (obText3 == null) obText3 = "";
        if (obText4 == null) obText4 = "";
        if (obDecimal1 == null) obDecimal1 = BigDecimal.ZERO;
        if (obDecimal2 == null) obDecimal2 = BigDecimal.ZERO;
        if (obDate1 == null) obDate1 = new Date();
        if (obDate2 == null) obDate2 = new Date();
        if (jpaVersion == null) jpaVersion = 0;
        if (carseriesName == null) carseriesName = "";
        if (carmodelName == null) carmodelName = "";
        if (year == null) year = "";
        if (exownerName == null) exownerName = "";
        if (exidType == null) exidType = "";
        if (exidNumber == null) exidNumber = "";
        if (exphoneNumber == null) exphoneNumber = "";
        if (projectSum == null) projectSum = BigDecimal.ZERO;
        if (financingSum == null) financingSum = BigDecimal.ZERO;
        if (leasingTerm == null) leasingTerm = 0;
        if (rentSum == null) rentSum = BigDecimal.ZERO;
        if (monthPayment == null) monthPayment = BigDecimal.ZERO;
    }
}