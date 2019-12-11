package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户表
 * @author jjb
 */
public class TmCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String org;

    private String acctNo;

    private String custId;

    private String idNo;

    private String idType;

    private String title;

    private String name;

    private String gender;

    private Date birthday;

    private String occupation;

    private String bankmemberNo;

    private String nationality;

    private String prOfCountry;

    private String residencyCountryCd;

    private String maritalStatus;

    private String qualification;

    private String socialStatus;

    private String homePhone;

    private String houseOwnership;

    private String houseType;

    private Date homeStandFrom;

    private String liquidAsset;

    private String mobileNo;

    private String email;

    private String empStatus;

    private Integer nbrOfDependents;

    private String languageInd;

    private Date setupDate;

    private BigDecimal socialInsAmt;

    private String driveLicenseId;

    private Date driveLicRegDate;

    private String obligateAnswer;

    private String obligateQuestion;

    private String empStability;

    private String corpName;

    private String bankCustomerId;

    private BigDecimal creditLimit;

    private String embName;

    private String idIssuerAddress;

    private Date batchDate;

    private Integer jpaVersion;

    private String ecifCustomerCode;

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
     * <p>账户编号</p>
     * <p>账号</p>
     */
    public String getAcctNo() {
        return acctNo;
    }

    /**
     * <p>账户编号</p>
     * <p>账号</p>
     */
    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    /**
     * <p>客户编号</p>
     */
    public String getCustId() {
        return custId;
    }

    /**
     * <p>客户编号</p>
     */
    public void setCustId(String custId) {
        this.custId = custId;
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
     * <p>称谓</p>
     */
    public String getTitle() {
        return title;
    }

    /**
     * <p>称谓</p>
     */
    public void setTitle(String title) {
        this.title = title;
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
     * <p>性别</p>
     */
    public String getGender() {
        return gender;
    }

    /**
     * <p>性别</p>
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * <p>生日</p>
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * <p>生日</p>
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * <p>职业</p>
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * <p>职业</p>
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * <p>本行员工号</p>
     */
    public String getBankmemberNo() {
        return bankmemberNo;
    }

    /**
     * <p>本行员工号</p>
     */
    public void setBankmemberNo(String bankmemberNo) {
        this.bankmemberNo = bankmemberNo;
    }

    /**
     * <p>国籍</p>
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * <p>国籍</p>
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * <p>是否永久居住</p>
     */
    public String getPrOfCountry() {
        return prOfCountry;
    }

    /**
     * <p>是否永久居住</p>
     */
    public void setPrOfCountry(String prOfCountry) {
        this.prOfCountry = prOfCountry;
    }

    /**
     * <p>永久居住地国家代码</p>
     */
    public String getResidencyCountryCd() {
        return residencyCountryCd;
    }

    /**
     * <p>永久居住地国家代码</p>
     */
    public void setResidencyCountryCd(String residencyCountryCd) {
        this.residencyCountryCd = residencyCountryCd;
    }

    /**
     * <p>婚姻状况</p>
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * <p>婚姻状况</p>
     */
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * <p>教育状况</p>
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * <p>教育状况</p>
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    /**
     * <p>风险情况</p>
     */
    public String getSocialStatus() {
        return socialStatus;
    }

    /**
     * <p>风险情况</p>
     */
    public void setSocialStatus(String socialStatus) {
        this.socialStatus = socialStatus;
    }

    /**
     * <p>家庭电话</p>
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * <p>家庭电话</p>
     */
    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    /**
     * <p>房屋持有类型</p>
     */
    public String getHouseOwnership() {
        return houseOwnership;
    }

    /**
     * <p>房屋持有类型</p>
     */
    public void setHouseOwnership(String houseOwnership) {
        this.houseOwnership = houseOwnership;
    }

    /**
     * <p>住宅类型</p>
     */
    public String getHouseType() {
        return houseType;
    }

    /**
     * <p>住宅类型</p>
     */
    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    /**
     * <p>现住址居住起始年月</p>
     */
    public Date getHomeStandFrom() {
        return homeStandFrom;
    }

    /**
     * <p>现住址居住起始年月</p>
     */
    public void setHomeStandFrom(Date homeStandFrom) {
        this.homeStandFrom = homeStandFrom;
    }

    /**
     * <p>个人资产类型</p>
     */
    public String getLiquidAsset() {
        return liquidAsset;
    }

    /**
     * <p>个人资产类型</p>
     */
    public void setLiquidAsset(String liquidAsset) {
        this.liquidAsset = liquidAsset;
    }

    /**
     * <p>移动电话</p>
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * <p>移动电话</p>
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * <p>电子邮箱</p>
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>电子邮箱</p>
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>就业状态</p>
     */
    public String getEmpStatus() {
        return empStatus;
    }

    /**
     * <p>就业状态</p>
     */
    public void setEmpStatus(String empStatus) {
        this.empStatus = empStatus;
    }

    /**
     * <p>抚养人数</p>
     */
    public Integer getNbrOfDependents() {
        return nbrOfDependents;
    }

    /**
     * <p>抚养人数</p>
     */
    public void setNbrOfDependents(Integer nbrOfDependents) {
        this.nbrOfDependents = nbrOfDependents;
    }

    /**
     * <p>语言代码</p>
     */
    public String getLanguageInd() {
        return languageInd;
    }

    /**
     * <p>语言代码</p>
     */
    public void setLanguageInd(String languageInd) {
        this.languageInd = languageInd;
    }

    /**
     * <p>创建日期</p>
     */
    public Date getSetupDate() {
        return setupDate;
    }

    /**
     * <p>创建日期</p>
     */
    public void setSetupDate(Date setupDate) {
        this.setupDate = setupDate;
    }

    /**
     * <p>社保缴存金额</p>
     */
    public BigDecimal getSocialInsAmt() {
        return socialInsAmt;
    }

    /**
     * <p>社保缴存金额</p>
     */
    public void setSocialInsAmt(BigDecimal socialInsAmt) {
        this.socialInsAmt = socialInsAmt;
    }

    /**
     * <p>驾驶证号码</p>
     */
    public String getDriveLicenseId() {
        return driveLicenseId;
    }

    /**
     * <p>驾驶证号码</p>
     */
    public void setDriveLicenseId(String driveLicenseId) {
        this.driveLicenseId = driveLicenseId;
    }

    /**
     * <p>驾驶证登记日期</p>
     */
    public Date getDriveLicRegDate() {
        return driveLicRegDate;
    }

    /**
     * <p>驾驶证登记日期</p>
     */
    public void setDriveLicRegDate(Date driveLicRegDate) {
        this.driveLicRegDate = driveLicRegDate;
    }

    /**
     * <p>预留答案</p>
     */
    public String getObligateAnswer() {
        return obligateAnswer;
    }

    /**
     * <p>预留答案</p>
     */
    public void setObligateAnswer(String obligateAnswer) {
        this.obligateAnswer = obligateAnswer;
    }

    /**
     * <p>预留问题</p>
     */
    public String getObligateQuestion() {
        return obligateQuestion;
    }

    /**
     * <p>预留问题</p>
     */
    public void setObligateQuestion(String obligateQuestion) {
        this.obligateQuestion = obligateQuestion;
    }

    /**
     * <p>工作稳定性</p>
     */
    public String getEmpStability() {
        return empStability;
    }

    /**
     * <p>工作稳定性</p>
     */
    public void setEmpStability(String empStability) {
        this.empStability = empStability;
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
     * <p>行内统一用户号</p>
     */
    public String getBankCustomerId() {
        return bankCustomerId;
    }

    /**
     * <p>行内统一用户号</p>
     */
    public void setBankCustomerId(String bankCustomerId) {
        this.bankCustomerId = bankCustomerId;
    }

    /**
     * <p>信用额度</p>
     */
    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    /**
     * <p>信用额度</p>
     */
    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    /**
     * <p>凸印姓名</p>
     */
    public String getEmbName() {
        return embName;
    }

    /**
     * <p>凸印姓名</p>
     */
    public void setEmbName(String embName) {
        this.embName = embName;
    }

    /**
     * <p>发证机关所在地址</p>
     */
    public String getIdIssuerAddress() {
        return idIssuerAddress;
    }

    /**
     * <p>发证机关所在地址</p>
     */
    public void setIdIssuerAddress(String idIssuerAddress) {
        this.idIssuerAddress = idIssuerAddress;
    }

    /**
     * <p>批量日期</p>
     */
    public Date getBatchDate() {
        return batchDate;
    }

    /**
     * <p>批量日期</p>
     */
    public void setBatchDate(Date batchDate) {
        this.batchDate = batchDate;
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
     * <p>ECIF客户编号</p>
     */
    public String getEcifCustomerCode() {
        return ecifCustomerCode;
    }

    /**
     * <p>ECIF客户编号</p>
     */
    public void setEcifCustomerCode(String ecifCustomerCode) {
        this.ecifCustomerCode = ecifCustomerCode;
    }

    public Map<String, Serializable> convertToMap() {
        HashMap<String, Serializable> map = new HashMap<String, Serializable>();
        map.put("org", org);
        map.put("acctNo", acctNo);
        map.put("custId", custId);
        map.put("idNo", idNo);
        map.put("idType", idType);
        map.put("title", title);
        map.put("name", name);
        map.put("gender", gender);
        map.put("birthday", birthday);
        map.put("occupation", occupation);
        map.put("bankmemberNo", bankmemberNo);
        map.put("nationality", nationality);
        map.put("prOfCountry", prOfCountry);
        map.put("residencyCountryCd", residencyCountryCd);
        map.put("maritalStatus", maritalStatus);
        map.put("qualification", qualification);
        map.put("socialStatus", socialStatus);
        map.put("homePhone", homePhone);
        map.put("houseOwnership", houseOwnership);
        map.put("houseType", houseType);
        map.put("homeStandFrom", homeStandFrom);
        map.put("liquidAsset", liquidAsset);
        map.put("mobileNo", mobileNo);
        map.put("email", email);
        map.put("empStatus", empStatus);
        map.put("nbrOfDependents", nbrOfDependents);
        map.put("languageInd", languageInd);
        map.put("setupDate", setupDate);
        map.put("socialInsAmt", socialInsAmt);
        map.put("driveLicenseId", driveLicenseId);
        map.put("driveLicRegDate", driveLicRegDate);
        map.put("obligateAnswer", obligateAnswer);
        map.put("obligateQuestion", obligateQuestion);
        map.put("empStability", empStability);
        map.put("corpName", corpName);
        map.put("bankCustomerId", bankCustomerId);
        map.put("creditLimit", creditLimit);
        map.put("embName", embName);
        map.put("idIssuerAddress", idIssuerAddress);
        map.put("batchDate", batchDate);
        map.put("jpaVersion", jpaVersion);
        map.put("ecifCustomerCode", ecifCustomerCode);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("acctNo")) this.setAcctNo(DataTypeUtils.getStringValue(map.get("acctNo")));
        if (map.containsKey("custId")) this.setCustId(DataTypeUtils.getStringValue(map.get("custId")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("title")) this.setTitle(DataTypeUtils.getStringValue(map.get("title")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("gender")) this.setGender(DataTypeUtils.getStringValue(map.get("gender")));
        if (map.containsKey("birthday")) this.setBirthday(DataTypeUtils.getDateValue(map.get("birthday")));
        if (map.containsKey("occupation")) this.setOccupation(DataTypeUtils.getStringValue(map.get("occupation")));
        if (map.containsKey("bankmemberNo")) this.setBankmemberNo(DataTypeUtils.getStringValue(map.get("bankmemberNo")));
        if (map.containsKey("nationality")) this.setNationality(DataTypeUtils.getStringValue(map.get("nationality")));
        if (map.containsKey("prOfCountry")) this.setPrOfCountry(DataTypeUtils.getStringValue(map.get("prOfCountry")));
        if (map.containsKey("residencyCountryCd")) this.setResidencyCountryCd(DataTypeUtils.getStringValue(map.get("residencyCountryCd")));
        if (map.containsKey("maritalStatus")) this.setMaritalStatus(DataTypeUtils.getStringValue(map.get("maritalStatus")));
        if (map.containsKey("qualification")) this.setQualification(DataTypeUtils.getStringValue(map.get("qualification")));
        if (map.containsKey("socialStatus")) this.setSocialStatus(DataTypeUtils.getStringValue(map.get("socialStatus")));
        if (map.containsKey("homePhone")) this.setHomePhone(DataTypeUtils.getStringValue(map.get("homePhone")));
        if (map.containsKey("houseOwnership")) this.setHouseOwnership(DataTypeUtils.getStringValue(map.get("houseOwnership")));
        if (map.containsKey("houseType")) this.setHouseType(DataTypeUtils.getStringValue(map.get("houseType")));
        if (map.containsKey("homeStandFrom")) this.setHomeStandFrom(DataTypeUtils.getDateValue(map.get("homeStandFrom")));
        if (map.containsKey("liquidAsset")) this.setLiquidAsset(DataTypeUtils.getStringValue(map.get("liquidAsset")));
        if (map.containsKey("mobileNo")) this.setMobileNo(DataTypeUtils.getStringValue(map.get("mobileNo")));
        if (map.containsKey("email")) this.setEmail(DataTypeUtils.getStringValue(map.get("email")));
        if (map.containsKey("empStatus")) this.setEmpStatus(DataTypeUtils.getStringValue(map.get("empStatus")));
        if (map.containsKey("nbrOfDependents")) this.setNbrOfDependents(DataTypeUtils.getIntegerValue(map.get("nbrOfDependents")));
        if (map.containsKey("languageInd")) this.setLanguageInd(DataTypeUtils.getStringValue(map.get("languageInd")));
        if (map.containsKey("setupDate")) this.setSetupDate(DataTypeUtils.getDateValue(map.get("setupDate")));
        if (map.containsKey("socialInsAmt")) this.setSocialInsAmt(DataTypeUtils.getBigDecimalValue(map.get("socialInsAmt")));
        if (map.containsKey("driveLicenseId")) this.setDriveLicenseId(DataTypeUtils.getStringValue(map.get("driveLicenseId")));
        if (map.containsKey("driveLicRegDate")) this.setDriveLicRegDate(DataTypeUtils.getDateValue(map.get("driveLicRegDate")));
        if (map.containsKey("obligateAnswer")) this.setObligateAnswer(DataTypeUtils.getStringValue(map.get("obligateAnswer")));
        if (map.containsKey("obligateQuestion")) this.setObligateQuestion(DataTypeUtils.getStringValue(map.get("obligateQuestion")));
        if (map.containsKey("empStability")) this.setEmpStability(DataTypeUtils.getStringValue(map.get("empStability")));
        if (map.containsKey("corpName")) this.setCorpName(DataTypeUtils.getStringValue(map.get("corpName")));
        if (map.containsKey("bankCustomerId")) this.setBankCustomerId(DataTypeUtils.getStringValue(map.get("bankCustomerId")));
        if (map.containsKey("creditLimit")) this.setCreditLimit(DataTypeUtils.getBigDecimalValue(map.get("creditLimit")));
        if (map.containsKey("embName")) this.setEmbName(DataTypeUtils.getStringValue(map.get("embName")));
        if (map.containsKey("idIssuerAddress")) this.setIdIssuerAddress(DataTypeUtils.getStringValue(map.get("idIssuerAddress")));
        if (map.containsKey("batchDate")) this.setBatchDate(DataTypeUtils.getDateValue(map.get("batchDate")));
        if (map.containsKey("jpaVersion")) this.setJpaVersion(DataTypeUtils.getIntegerValue(map.get("jpaVersion")));
        if (map.containsKey("ecifCustomerCode")) this.setEcifCustomerCode(DataTypeUtils.getStringValue(map.get("ecifCustomerCode")));
    }

    @Override
    public String toString() {
        return getClass().getName() + "@" + Integer.toHexString(hashCode())+"["+
        "serialVersionUID="+serialVersionUID+
        ", org="+org+
        "org="+org+
        ", acctNo="+acctNo+
        "acctNo="+acctNo+
        ", custId="+custId+
        "custId="+custId+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", idType="+idType+
        "idType="+idType+
        ", title="+title+
        "title="+title+
        ", name="+name+
        "name="+name+
        ", gender="+gender+
        "gender="+gender+
        ", birthday="+birthday+
        "birthday="+birthday+
        ", occupation="+occupation+
        "occupation="+occupation+
        ", bankmemberNo="+bankmemberNo+
        "bankmemberNo="+bankmemberNo+
        ", nationality="+nationality+
        "nationality="+nationality+
        ", prOfCountry="+prOfCountry+
        "prOfCountry="+prOfCountry+
        ", residencyCountryCd="+residencyCountryCd+
        "residencyCountryCd="+residencyCountryCd+
        ", maritalStatus="+maritalStatus+
        "maritalStatus="+maritalStatus+
        ", qualification="+qualification+
        "qualification="+qualification+
        ", socialStatus="+socialStatus+
        "socialStatus="+socialStatus+
        ", homePhone="+homePhone+
        "homePhone="+homePhone+
        ", houseOwnership="+houseOwnership+
        "houseOwnership="+houseOwnership+
        ", houseType="+houseType+
        "houseType="+houseType+
        ", homeStandFrom="+homeStandFrom+
        "homeStandFrom="+homeStandFrom+
        ", liquidAsset="+liquidAsset+
        "liquidAsset="+liquidAsset+
        ", mobileNo="+mobileNo+
        "mobileNo="+mobileNo+
        ", email="+email+
        "email="+email+
        ", empStatus="+empStatus+
        "empStatus="+empStatus+
        ", nbrOfDependents="+nbrOfDependents+
        "nbrOfDependents="+nbrOfDependents+
        ", languageInd="+languageInd+
        "languageInd="+languageInd+
        ", setupDate="+setupDate+
        "setupDate="+setupDate+
        ", socialInsAmt="+socialInsAmt+
        "socialInsAmt="+socialInsAmt+
        ", driveLicenseId="+driveLicenseId+
        "driveLicenseId="+driveLicenseId+
        ", driveLicRegDate="+driveLicRegDate+
        "driveLicRegDate="+driveLicRegDate+
        ", obligateAnswer="+obligateAnswer+
        "obligateAnswer="+obligateAnswer+
        ", obligateQuestion="+obligateQuestion+
        "obligateQuestion="+obligateQuestion+
        ", empStability="+empStability+
        "empStability="+empStability+
        ", corpName="+corpName+
        "corpName="+corpName+
        ", bankCustomerId="+bankCustomerId+
        "bankCustomerId="+bankCustomerId+
        ", creditLimit="+creditLimit+
        "creditLimit="+creditLimit+
        ", embName="+embName+
        "embName="+embName+
        ", idIssuerAddress="+idIssuerAddress+
        "idIssuerAddress="+idIssuerAddress+
        ", batchDate="+batchDate+
        "batchDate="+batchDate+
        ", jpaVersion="+jpaVersion+
        "jpaVersion="+jpaVersion+
        ", ecifCustomerCode="+ecifCustomerCode+
        "ecifCustomerCode="+ecifCustomerCode+
        "]";
    }

    public void fillDefaultValues() {
        if (org == null) org = "";
        if (acctNo == null) acctNo = "";
        if (custId == null) custId = "";
        if (idNo == null) idNo = "";
        if (idType == null) idType = "";
        if (title == null) title = "";
        if (name == null) name = "";
        if (gender == null) gender = "";
        if (birthday == null) birthday = new Date();
        if (occupation == null) occupation = "";
        if (bankmemberNo == null) bankmemberNo = "";
        if (nationality == null) nationality = "";
        if (prOfCountry == null) prOfCountry = "";
        if (residencyCountryCd == null) residencyCountryCd = "";
        if (maritalStatus == null) maritalStatus = "";
        if (qualification == null) qualification = "";
        if (socialStatus == null) socialStatus = "";
        if (homePhone == null) homePhone = "";
        if (houseOwnership == null) houseOwnership = "";
        if (houseType == null) houseType = "";
        if (homeStandFrom == null) homeStandFrom = new Date();
        if (liquidAsset == null) liquidAsset = "";
        if (mobileNo == null) mobileNo = "";
        if (email == null) email = "";
        if (empStatus == null) empStatus = "";
        if (nbrOfDependents == null) nbrOfDependents = 0;
        if (languageInd == null) languageInd = "";
        if (setupDate == null) setupDate = new Date();
        if (socialInsAmt == null) socialInsAmt = BigDecimal.ZERO;
        if (driveLicenseId == null) driveLicenseId = "";
        if (driveLicRegDate == null) driveLicRegDate = new Date();
        if (obligateAnswer == null) obligateAnswer = "";
        if (obligateQuestion == null) obligateQuestion = "";
        if (empStability == null) empStability = "";
        if (corpName == null) corpName = "";
        if (bankCustomerId == null) bankCustomerId = "";
        if (creditLimit == null) creditLimit = BigDecimal.ZERO;
        if (embName == null) embName = "";
        if (idIssuerAddress == null) idIssuerAddress = "";
        if (batchDate == null) batchDate = new Date();
        if (jpaVersion == null) jpaVersion = 0;
        if (ecifCustomerCode == null) ecifCustomerCode = "";
    }
}