package com.jjb.ecms.infrastructure;

import com.jjb.unicorn.facility.util.DataTypeUtils;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 申请人信息表
 * @author jjb
 */
public class TmAppCustInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String org;

    private String appNo;

    private String bscSuppInd;

    private Integer attachNo;

    private String ifSelectedCard;

    private String relationshipToBsc;

    private String primCardNo;

    private String cardNo;

    private String name;

    private String embLogo;

    private String gender;

    private String age;

    private String nationality;

    private String residencyCountryCd;

    private String idType;

    private String idNo;

    private String idLastAll;

    private Date idLastDate;

    private String idIssuerAddress;

    private Date birthday;

    private String liquidAsset;

    private BigDecimal yearIncome;

    private String qualification;

    private String degree;

    private String maritalStatus;

    private String homeAddrCtryCd;

    private String homeState;

    private String homeCity;

    private String homeZone;

    private String houseOwnership;

    private String homeAdd;

    private String homePostcode;

    private String homePhone;

    private String cellphone;

    private String email;

    private String familyMember;

    private String bankmemFlag;

    private String bankmemberNo;

    private String corpName;

    private String empAddrCtryCd;

    private String empProvince;

    private String empCity;

    private String empZone;

    private String empAdd;

    private String empPostcode;

    private String empPhone;

    private String empDepartment;

    private String empPost;

    private String empStructure;

    private String empType;

    private Integer empWorkyears;

    private String jobGrade;

    private String occupation;

    private String titleOfTechnical;

    private String empPostName;

    private String smAmtVerifyInd;

    private String posPinVerifyInd;

    private String photoUseFlag;

    private String groupNum;

    private String otherAsk;

    private String otherAnswer;

    private Integer raiseNum;

    private String statementType;

    private String statementAddress;

    private String appnoExternal;

    private String bankCustomerId;

    private String ecifCustomerCode;

    private String custType;

    private String custClass;

    private String recordStatus;

    private String beforeName;

    private String islocal;

    private Integer liveYears;

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
     * <p>主附卡指示器</p>
     */
    public String getBscSuppInd() {
        return bscSuppInd;
    }

    /**
     * <p>主附卡指示器</p>
     */
    public void setBscSuppInd(String bscSuppInd) {
        this.bscSuppInd = bscSuppInd;
    }

    /**
     * <p>附卡编号</p>
     */
    public Integer getAttachNo() {
        return attachNo;
    }

    /**
     * <p>附卡编号</p>
     */
    public void setAttachNo(Integer attachNo) {
        this.attachNo = attachNo;
    }

    /**
     * <p>是否自选卡号</p>
     */
    public String getIfSelectedCard() {
        return ifSelectedCard;
    }

    /**
     * <p>是否自选卡号</p>
     */
    public void setIfSelectedCard(String ifSelectedCard) {
        this.ifSelectedCard = ifSelectedCard;
    }

    /**
     * <p>主卡持卡人关系</p>
     */
    public String getRelationshipToBsc() {
        return relationshipToBsc;
    }

    /**
     * <p>主卡持卡人关系</p>
     */
    public void setRelationshipToBsc(String relationshipToBsc) {
        this.relationshipToBsc = relationshipToBsc;
    }

    /**
     * <p>主卡卡号</p>
     */
    public String getPrimCardNo() {
        return primCardNo;
    }

    /**
     * <p>主卡卡号</p>
     */
    public void setPrimCardNo(String primCardNo) {
        this.primCardNo = primCardNo;
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
     * <p>凸印名称</p>
     */
    public String getEmbLogo() {
        return embLogo;
    }

    /**
     * <p>凸印名称</p>
     */
    public void setEmbLogo(String embLogo) {
        this.embLogo = embLogo;
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
     * <p>年龄</p>
     * <p>年龄</p>
     */
    public String getAge() {
        return age;
    }

    /**
     * <p>年龄</p>
     * <p>年龄</p>
     */
    public void setAge(String age) {
        this.age = age;
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
     * <p>证件是否长期有效</p>
     */
    public String getIdLastAll() {
        return idLastAll;
    }

    /**
     * <p>证件是否长期有效</p>
     */
    public void setIdLastAll(String idLastAll) {
        this.idLastAll = idLastAll;
    }

    /**
     * <p>证件到期日</p>
     */
    public Date getIdLastDate() {
        return idLastDate;
    }

    /**
     * <p>证件到期日</p>
     */
    public void setIdLastDate(Date idLastDate) {
        this.idLastDate = idLastDate;
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
     * <p>个人年收入</p>
     */
    public BigDecimal getYearIncome() {
        return yearIncome;
    }

    /**
     * <p>个人年收入</p>
     */
    public void setYearIncome(BigDecimal yearIncome) {
        this.yearIncome = yearIncome;
    }

    /**
     * <p>教育程度</p>
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * <p>教育程度</p>
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    /**
     * <p>学位</p>
     */
    public String getDegree() {
        return degree;
    }

    /**
     * <p>学位</p>
     */
    public void setDegree(String degree) {
        this.degree = degree;
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
     * <p>家庭国家代码</p>
     */
    public String getHomeAddrCtryCd() {
        return homeAddrCtryCd;
    }

    /**
     * <p>家庭国家代码</p>
     */
    public void setHomeAddrCtryCd(String homeAddrCtryCd) {
        this.homeAddrCtryCd = homeAddrCtryCd;
    }

    /**
     * <p>家庭所在省</p>
     */
    public String getHomeState() {
        return homeState;
    }

    /**
     * <p>家庭所在省</p>
     */
    public void setHomeState(String homeState) {
        this.homeState = homeState;
    }

    /**
     * <p>家庭所在市</p>
     * <p>家庭所在市</p>
     */
    public String getHomeCity() {
        return homeCity;
    }

    /**
     * <p>家庭所在市</p>
     * <p>家庭所在市</p>
     */
    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    /**
     * <p>家庭所在区县</p>
     * <p>家庭所在区县</p>
     */
    public String getHomeZone() {
        return homeZone;
    }

    /**
     * <p>家庭所在区县</p>
     * <p>家庭所在区县</p>
     */
    public void setHomeZone(String homeZone) {
        this.homeZone = homeZone;
    }

    /**
     * <p>住宅状况</p>
     * <p>住宅状况</p>
     */
    public String getHouseOwnership() {
        return houseOwnership;
    }

    /**
     * <p>住宅状况</p>
     * <p>住宅状况</p>
     */
    public void setHouseOwnership(String houseOwnership) {
        this.houseOwnership = houseOwnership;
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
     * <p>家庭住宅邮编</p>
     * <p>家庭住宅邮编</p>
     */
    public String getHomePostcode() {
        return homePostcode;
    }

    /**
     * <p>家庭住宅邮编</p>
     * <p>家庭住宅邮编</p>
     */
    public void setHomePostcode(String homePostcode) {
        this.homePostcode = homePostcode;
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
     * <p>家庭人口</p>
     */
    public String getFamilyMember() {
        return familyMember;
    }

    /**
     * <p>家庭人口</p>
     */
    public void setFamilyMember(String familyMember) {
        this.familyMember = familyMember;
    }

    /**
     * <p>是否行内员工</p>
     */
    public String getBankmemFlag() {
        return bankmemFlag;
    }

    /**
     * <p>是否行内员工</p>
     */
    public void setBankmemFlag(String bankmemFlag) {
        this.bankmemFlag = bankmemFlag;
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
     * <p>公司国家代码</p>
     */
    public String getEmpAddrCtryCd() {
        return empAddrCtryCd;
    }

    /**
     * <p>公司国家代码</p>
     */
    public void setEmpAddrCtryCd(String empAddrCtryCd) {
        this.empAddrCtryCd = empAddrCtryCd;
    }

    /**
     * <p>公司所在省</p>
     */
    public String getEmpProvince() {
        return empProvince;
    }

    /**
     * <p>公司所在省</p>
     */
    public void setEmpProvince(String empProvince) {
        this.empProvince = empProvince;
    }

    /**
     * <p>公司所在市</p>
     */
    public String getEmpCity() {
        return empCity;
    }

    /**
     * <p>公司所在市</p>
     */
    public void setEmpCity(String empCity) {
        this.empCity = empCity;
    }

    /**
     * <p>公司所在区/县</p>
     */
    public String getEmpZone() {
        return empZone;
    }

    /**
     * <p>公司所在区/县</p>
     */
    public void setEmpZone(String empZone) {
        this.empZone = empZone;
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
     * <p>公司邮编</p>
     * <p>公司邮编</p>
     */
    public String getEmpPostcode() {
        return empPostcode;
    }

    /**
     * <p>公司邮编</p>
     * <p>公司邮编</p>
     */
    public void setEmpPostcode(String empPostcode) {
        this.empPostcode = empPostcode;
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
     * <p>任职部门</p>
     * <p>任职部门</p>
     */
    public String getEmpDepartment() {
        return empDepartment;
    }

    /**
     * <p>任职部门</p>
     * <p>任职部门</p>
     */
    public void setEmpDepartment(String empDepartment) {
        this.empDepartment = empDepartment;
    }

    /**
     * <p>职务</p>
     */
    public String getEmpPost() {
        return empPost;
    }

    /**
     * <p>职务</p>
     */
    public void setEmpPost(String empPost) {
        this.empPost = empPost;
    }

    /**
     * <p>公司性质</p>
     */
    public String getEmpStructure() {
        return empStructure;
    }

    /**
     * <p>公司性质</p>
     */
    public void setEmpStructure(String empStructure) {
        this.empStructure = empStructure;
    }

    /**
     * <p>公司行业类别</p>
     */
    public String getEmpType() {
        return empType;
    }

    /**
     * <p>公司行业类别</p>
     */
    public void setEmpType(String empType) {
        this.empType = empType;
    }

    /**
     * <p>本公司工作年限</p>
     * <p>本单位工作年限</p>
     */
    public Integer getEmpWorkyears() {
        return empWorkyears;
    }

    /**
     * <p>本公司工作年限</p>
     * <p>本单位工作年限</p>
     */
    public void setEmpWorkyears(Integer empWorkyears) {
        this.empWorkyears = empWorkyears;
    }

    /**
     * <p>岗位级别</p>
     */
    public String getJobGrade() {
        return jobGrade;
    }

    /**
     * <p>岗位级别</p>
     */
    public void setJobGrade(String jobGrade) {
        this.jobGrade = jobGrade;
    }

    /**
     * <p>职业类型</p>
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * <p>职业类型</p>
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * <p>职称</p>
     */
    public String getTitleOfTechnical() {
        return titleOfTechnical;
    }

    /**
     * <p>职称</p>
     */
    public void setTitleOfTechnical(String titleOfTechnical) {
        this.titleOfTechnical = titleOfTechnical;
    }

    /**
     * <p>职务名称</p>
     * <p>职务名称</p>
     */
    public String getEmpPostName() {
        return empPostName;
    }

    /**
     * <p>职务名称</p>
     * <p>职务名称</p>
     */
    public void setEmpPostName(String empPostName) {
        this.empPostName = empPostName;
    }

    /**
     * <p>小额免密</p>
     */
    public String getSmAmtVerifyInd() {
        return smAmtVerifyInd;
    }

    /**
     * <p>小额免密</p>
     */
    public void setSmAmtVerifyInd(String smAmtVerifyInd) {
        this.smAmtVerifyInd = smAmtVerifyInd;
    }

    /**
     * <p>是否消费凭密</p>
     */
    public String getPosPinVerifyInd() {
        return posPinVerifyInd;
    }

    /**
     * <p>是否消费凭密</p>
     */
    public void setPosPinVerifyInd(String posPinVerifyInd) {
        this.posPinVerifyInd = posPinVerifyInd;
    }

    /**
     * <p>是否彩照卡</p>
     */
    public String getPhotoUseFlag() {
        return photoUseFlag;
    }

    /**
     * <p>是否彩照卡</p>
     */
    public void setPhotoUseFlag(String photoUseFlag) {
        this.photoUseFlag = photoUseFlag;
    }

    /**
     * <p>申请团办编号</p>
     * <p>申请团办编号</p>
     */
    public String getGroupNum() {
        return groupNum;
    }

    /**
     * <p>申请团办编号</p>
     * <p>申请团办编号</p>
     */
    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum;
    }

    /**
     * <p>预留问题</p>
     */
    public String getOtherAsk() {
        return otherAsk;
    }

    /**
     * <p>预留问题</p>
     */
    public void setOtherAsk(String otherAsk) {
        this.otherAsk = otherAsk;
    }

    /**
     * <p>预留答案</p>
     * <p>预留答案</p>
     */
    public String getOtherAnswer() {
        return otherAnswer;
    }

    /**
     * <p>预留答案</p>
     * <p>预留答案</p>
     */
    public void setOtherAnswer(String otherAnswer) {
        this.otherAnswer = otherAnswer;
    }

    /**
     * <p>抚养人数</p>
     */
    public Integer getRaiseNum() {
        return raiseNum;
    }

    /**
     * <p>抚养人数</p>
     */
    public void setRaiseNum(Integer raiseNum) {
        this.raiseNum = raiseNum;
    }

    /**
     * <p>对账单类型</p>
     */
    public String getStatementType() {
        return statementType;
    }

    /**
     * <p>对账单类型</p>
     */
    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    /**
     * <p>对账单地址</p>
     */
    public String getStatementAddress() {
        return statementAddress;
    }

    /**
     * <p>对账单地址</p>
     */
    public void setStatementAddress(String statementAddress) {
        this.statementAddress = statementAddress;
    }

    /**
     * <p>申请编号_外部送入</p>
     */
    public String getAppnoExternal() {
        return appnoExternal;
    }

    /**
     * <p>申请编号_外部送入</p>
     */
    public void setAppnoExternal(String appnoExternal) {
        this.appnoExternal = appnoExternal;
    }

    /**
     * <p>行内客户号</p>
     */
    public String getBankCustomerId() {
        return bankCustomerId;
    }

    /**
     * <p>行内客户号</p>
     */
    public void setBankCustomerId(String bankCustomerId) {
        this.bankCustomerId = bankCustomerId;
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

    /**
     * <p>客户类型</p>
     */
    public String getCustType() {
        return custType;
    }

    /**
     * <p>客户类型</p>
     */
    public void setCustType(String custType) {
        this.custType = custType;
    }

    /**
     * <p>客户等级</p>
     */
    public String getCustClass() {
        return custClass;
    }

    /**
     * <p>客户等级</p>
     */
    public void setCustClass(String custClass) {
        this.custClass = custClass;
    }

    /**
     * <p>记录状态</p>
     */
    public String getRecordStatus() {
        return recordStatus;
    }

    /**
     * <p>记录状态</p>
     */
    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    /**
     * <p>曾用名</p>
     */
    public String getBeforeName() {
        return beforeName;
    }

    /**
     * <p>曾用名</p>
     */
    public void setBeforeName(String beforeName) {
        this.beforeName = beforeName;
    }

    /**
     * <p>是否本地户籍</p>
     */
    public String getIslocal() {
        return islocal;
    }

    /**
     * <p>是否本地户籍</p>
     */
    public void setIslocal(String islocal) {
        this.islocal = islocal;
    }

    /**
     * <p>本地居住年限（年）</p>
     */
    public Integer getLiveYears() {
        return liveYears;
    }

    /**
     * <p>本地居住年限（年）</p>
     */
    public void setLiveYears(Integer liveYears) {
        this.liveYears = liveYears;
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
     * <p>修改时间</p>
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * <p>修改时间</p>
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * <p>修改人</p>
     */
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * <p>修改人</p>
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
        map.put("org", org);
        map.put("appNo", appNo);
        map.put("bscSuppInd", bscSuppInd);
        map.put("attachNo", attachNo);
        map.put("ifSelectedCard", ifSelectedCard);
        map.put("relationshipToBsc", relationshipToBsc);
        map.put("primCardNo", primCardNo);
        map.put("cardNo", cardNo);
        map.put("name", name);
        map.put("embLogo", embLogo);
        map.put("gender", gender);
        map.put("age", age);
        map.put("nationality", nationality);
        map.put("residencyCountryCd", residencyCountryCd);
        map.put("idType", idType);
        map.put("idNo", idNo);
        map.put("idLastAll", idLastAll);
        map.put("idLastDate", idLastDate);
        map.put("idIssuerAddress", idIssuerAddress);
        map.put("birthday", birthday);
        map.put("liquidAsset", liquidAsset);
        map.put("yearIncome", yearIncome);
        map.put("qualification", qualification);
        map.put("degree", degree);
        map.put("maritalStatus", maritalStatus);
        map.put("homeAddrCtryCd", homeAddrCtryCd);
        map.put("homeState", homeState);
        map.put("homeCity", homeCity);
        map.put("homeZone", homeZone);
        map.put("houseOwnership", houseOwnership);
        map.put("homeAdd", homeAdd);
        map.put("homePostcode", homePostcode);
        map.put("homePhone", homePhone);
        map.put("cellphone", cellphone);
        map.put("email", email);
        map.put("familyMember", familyMember);
        map.put("bankmemFlag", bankmemFlag);
        map.put("bankmemberNo", bankmemberNo);
        map.put("corpName", corpName);
        map.put("empAddrCtryCd", empAddrCtryCd);
        map.put("empProvince", empProvince);
        map.put("empCity", empCity);
        map.put("empZone", empZone);
        map.put("empAdd", empAdd);
        map.put("empPostcode", empPostcode);
        map.put("empPhone", empPhone);
        map.put("empDepartment", empDepartment);
        map.put("empPost", empPost);
        map.put("empStructure", empStructure);
        map.put("empType", empType);
        map.put("empWorkyears", empWorkyears);
        map.put("jobGrade", jobGrade);
        map.put("occupation", occupation);
        map.put("titleOfTechnical", titleOfTechnical);
        map.put("empPostName", empPostName);
        map.put("smAmtVerifyInd", smAmtVerifyInd);
        map.put("posPinVerifyInd", posPinVerifyInd);
        map.put("photoUseFlag", photoUseFlag);
        map.put("groupNum", groupNum);
        map.put("otherAsk", otherAsk);
        map.put("otherAnswer", otherAnswer);
        map.put("raiseNum", raiseNum);
        map.put("statementType", statementType);
        map.put("statementAddress", statementAddress);
        map.put("appnoExternal", appnoExternal);
        map.put("bankCustomerId", bankCustomerId);
        map.put("ecifCustomerCode", ecifCustomerCode);
        map.put("custType", custType);
        map.put("custClass", custClass);
        map.put("recordStatus", recordStatus);
        map.put("beforeName", beforeName);
        map.put("islocal", islocal);
        map.put("liveYears", liveYears);
        map.put("createDate", createDate);
        map.put("createUser", createUser);
        map.put("updateDate", updateDate);
        map.put("updateUser", updateUser);
        map.put("jpaVersion", jpaVersion);
        return map;
    }

    public void updateFromMap(Map<String, Serializable> map) {
        if (map.containsKey("id")) this.setId(DataTypeUtils.getIntegerValue(map.get("id")));
        if (map.containsKey("org")) this.setOrg(DataTypeUtils.getStringValue(map.get("org")));
        if (map.containsKey("appNo")) this.setAppNo(DataTypeUtils.getStringValue(map.get("appNo")));
        if (map.containsKey("bscSuppInd")) this.setBscSuppInd(DataTypeUtils.getStringValue(map.get("bscSuppInd")));
        if (map.containsKey("attachNo")) this.setAttachNo(DataTypeUtils.getIntegerValue(map.get("attachNo")));
        if (map.containsKey("ifSelectedCard")) this.setIfSelectedCard(DataTypeUtils.getStringValue(map.get("ifSelectedCard")));
        if (map.containsKey("relationshipToBsc")) this.setRelationshipToBsc(DataTypeUtils.getStringValue(map.get("relationshipToBsc")));
        if (map.containsKey("primCardNo")) this.setPrimCardNo(DataTypeUtils.getStringValue(map.get("primCardNo")));
        if (map.containsKey("cardNo")) this.setCardNo(DataTypeUtils.getStringValue(map.get("cardNo")));
        if (map.containsKey("name")) this.setName(DataTypeUtils.getStringValue(map.get("name")));
        if (map.containsKey("embLogo")) this.setEmbLogo(DataTypeUtils.getStringValue(map.get("embLogo")));
        if (map.containsKey("gender")) this.setGender(DataTypeUtils.getStringValue(map.get("gender")));
        if (map.containsKey("age")) this.setAge(DataTypeUtils.getStringValue(map.get("age")));
        if (map.containsKey("nationality")) this.setNationality(DataTypeUtils.getStringValue(map.get("nationality")));
        if (map.containsKey("residencyCountryCd")) this.setResidencyCountryCd(DataTypeUtils.getStringValue(map.get("residencyCountryCd")));
        if (map.containsKey("idType")) this.setIdType(DataTypeUtils.getStringValue(map.get("idType")));
        if (map.containsKey("idNo")) this.setIdNo(DataTypeUtils.getStringValue(map.get("idNo")));
        if (map.containsKey("idLastAll")) this.setIdLastAll(DataTypeUtils.getStringValue(map.get("idLastAll")));
        if (map.containsKey("idLastDate")) this.setIdLastDate(DataTypeUtils.getDateValue(map.get("idLastDate")));
        if (map.containsKey("idIssuerAddress")) this.setIdIssuerAddress(DataTypeUtils.getStringValue(map.get("idIssuerAddress")));
        if (map.containsKey("birthday")) this.setBirthday(DataTypeUtils.getDateValue(map.get("birthday")));
        if (map.containsKey("liquidAsset")) this.setLiquidAsset(DataTypeUtils.getStringValue(map.get("liquidAsset")));
        if (map.containsKey("yearIncome")) this.setYearIncome(DataTypeUtils.getBigDecimalValue(map.get("yearIncome")));
        if (map.containsKey("qualification")) this.setQualification(DataTypeUtils.getStringValue(map.get("qualification")));
        if (map.containsKey("degree")) this.setDegree(DataTypeUtils.getStringValue(map.get("degree")));
        if (map.containsKey("maritalStatus")) this.setMaritalStatus(DataTypeUtils.getStringValue(map.get("maritalStatus")));
        if (map.containsKey("homeAddrCtryCd")) this.setHomeAddrCtryCd(DataTypeUtils.getStringValue(map.get("homeAddrCtryCd")));
        if (map.containsKey("homeState")) this.setHomeState(DataTypeUtils.getStringValue(map.get("homeState")));
        if (map.containsKey("homeCity")) this.setHomeCity(DataTypeUtils.getStringValue(map.get("homeCity")));
        if (map.containsKey("homeZone")) this.setHomeZone(DataTypeUtils.getStringValue(map.get("homeZone")));
        if (map.containsKey("houseOwnership")) this.setHouseOwnership(DataTypeUtils.getStringValue(map.get("houseOwnership")));
        if (map.containsKey("homeAdd")) this.setHomeAdd(DataTypeUtils.getStringValue(map.get("homeAdd")));
        if (map.containsKey("homePostcode")) this.setHomePostcode(DataTypeUtils.getStringValue(map.get("homePostcode")));
        if (map.containsKey("homePhone")) this.setHomePhone(DataTypeUtils.getStringValue(map.get("homePhone")));
        if (map.containsKey("cellphone")) this.setCellphone(DataTypeUtils.getStringValue(map.get("cellphone")));
        if (map.containsKey("email")) this.setEmail(DataTypeUtils.getStringValue(map.get("email")));
        if (map.containsKey("familyMember")) this.setFamilyMember(DataTypeUtils.getStringValue(map.get("familyMember")));
        if (map.containsKey("bankmemFlag")) this.setBankmemFlag(DataTypeUtils.getStringValue(map.get("bankmemFlag")));
        if (map.containsKey("bankmemberNo")) this.setBankmemberNo(DataTypeUtils.getStringValue(map.get("bankmemberNo")));
        if (map.containsKey("corpName")) this.setCorpName(DataTypeUtils.getStringValue(map.get("corpName")));
        if (map.containsKey("empAddrCtryCd")) this.setEmpAddrCtryCd(DataTypeUtils.getStringValue(map.get("empAddrCtryCd")));
        if (map.containsKey("empProvince")) this.setEmpProvince(DataTypeUtils.getStringValue(map.get("empProvince")));
        if (map.containsKey("empCity")) this.setEmpCity(DataTypeUtils.getStringValue(map.get("empCity")));
        if (map.containsKey("empZone")) this.setEmpZone(DataTypeUtils.getStringValue(map.get("empZone")));
        if (map.containsKey("empAdd")) this.setEmpAdd(DataTypeUtils.getStringValue(map.get("empAdd")));
        if (map.containsKey("empPostcode")) this.setEmpPostcode(DataTypeUtils.getStringValue(map.get("empPostcode")));
        if (map.containsKey("empPhone")) this.setEmpPhone(DataTypeUtils.getStringValue(map.get("empPhone")));
        if (map.containsKey("empDepartment")) this.setEmpDepartment(DataTypeUtils.getStringValue(map.get("empDepartment")));
        if (map.containsKey("empPost")) this.setEmpPost(DataTypeUtils.getStringValue(map.get("empPost")));
        if (map.containsKey("empStructure")) this.setEmpStructure(DataTypeUtils.getStringValue(map.get("empStructure")));
        if (map.containsKey("empType")) this.setEmpType(DataTypeUtils.getStringValue(map.get("empType")));
        if (map.containsKey("empWorkyears")) this.setEmpWorkyears(DataTypeUtils.getIntegerValue(map.get("empWorkyears")));
        if (map.containsKey("jobGrade")) this.setJobGrade(DataTypeUtils.getStringValue(map.get("jobGrade")));
        if (map.containsKey("occupation")) this.setOccupation(DataTypeUtils.getStringValue(map.get("occupation")));
        if (map.containsKey("titleOfTechnical")) this.setTitleOfTechnical(DataTypeUtils.getStringValue(map.get("titleOfTechnical")));
        if (map.containsKey("empPostName")) this.setEmpPostName(DataTypeUtils.getStringValue(map.get("empPostName")));
        if (map.containsKey("smAmtVerifyInd")) this.setSmAmtVerifyInd(DataTypeUtils.getStringValue(map.get("smAmtVerifyInd")));
        if (map.containsKey("posPinVerifyInd")) this.setPosPinVerifyInd(DataTypeUtils.getStringValue(map.get("posPinVerifyInd")));
        if (map.containsKey("photoUseFlag")) this.setPhotoUseFlag(DataTypeUtils.getStringValue(map.get("photoUseFlag")));
        if (map.containsKey("groupNum")) this.setGroupNum(DataTypeUtils.getStringValue(map.get("groupNum")));
        if (map.containsKey("otherAsk")) this.setOtherAsk(DataTypeUtils.getStringValue(map.get("otherAsk")));
        if (map.containsKey("otherAnswer")) this.setOtherAnswer(DataTypeUtils.getStringValue(map.get("otherAnswer")));
        if (map.containsKey("raiseNum")) this.setRaiseNum(DataTypeUtils.getIntegerValue(map.get("raiseNum")));
        if (map.containsKey("statementType")) this.setStatementType(DataTypeUtils.getStringValue(map.get("statementType")));
        if (map.containsKey("statementAddress")) this.setStatementAddress(DataTypeUtils.getStringValue(map.get("statementAddress")));
        if (map.containsKey("appnoExternal")) this.setAppnoExternal(DataTypeUtils.getStringValue(map.get("appnoExternal")));
        if (map.containsKey("bankCustomerId")) this.setBankCustomerId(DataTypeUtils.getStringValue(map.get("bankCustomerId")));
        if (map.containsKey("ecifCustomerCode")) this.setEcifCustomerCode(DataTypeUtils.getStringValue(map.get("ecifCustomerCode")));
        if (map.containsKey("custType")) this.setCustType(DataTypeUtils.getStringValue(map.get("custType")));
        if (map.containsKey("custClass")) this.setCustClass(DataTypeUtils.getStringValue(map.get("custClass")));
        if (map.containsKey("recordStatus")) this.setRecordStatus(DataTypeUtils.getStringValue(map.get("recordStatus")));
        if (map.containsKey("beforeName")) this.setBeforeName(DataTypeUtils.getStringValue(map.get("beforeName")));
        if (map.containsKey("islocal")) this.setIslocal(DataTypeUtils.getStringValue(map.get("islocal")));
        if (map.containsKey("liveYears")) this.setLiveYears(DataTypeUtils.getIntegerValue(map.get("liveYears")));
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
        ", org="+org+
        "org="+org+
        ", appNo="+appNo+
        "appNo="+appNo+
        ", bscSuppInd="+bscSuppInd+
        "bscSuppInd="+bscSuppInd+
        ", attachNo="+attachNo+
        "attachNo="+attachNo+
        ", ifSelectedCard="+ifSelectedCard+
        "ifSelectedCard="+ifSelectedCard+
        ", relationshipToBsc="+relationshipToBsc+
        "relationshipToBsc="+relationshipToBsc+
        ", primCardNo="+primCardNo+
        "primCardNo="+primCardNo+
        ", cardNo="+cardNo+
        "cardNo="+cardNo+
        ", name="+name+
        "name="+name+
        ", embLogo="+embLogo+
        "embLogo="+embLogo+
        ", gender="+gender+
        "gender="+gender+
        ", age="+age+
        "age="+age+
        ", nationality="+nationality+
        "nationality="+nationality+
        ", residencyCountryCd="+residencyCountryCd+
        "residencyCountryCd="+residencyCountryCd+
        ", idType="+idType+
        "idType="+idType+
        ", idNo="+idNo+
        "idNo="+idNo+
        ", idLastAll="+idLastAll+
        "idLastAll="+idLastAll+
        ", idLastDate="+idLastDate+
        "idLastDate="+idLastDate+
        ", idIssuerAddress="+idIssuerAddress+
        "idIssuerAddress="+idIssuerAddress+
        ", birthday="+birthday+
        "birthday="+birthday+
        ", liquidAsset="+liquidAsset+
        "liquidAsset="+liquidAsset+
        ", yearIncome="+yearIncome+
        "yearIncome="+yearIncome+
        ", qualification="+qualification+
        "qualification="+qualification+
        ", degree="+degree+
        "degree="+degree+
        ", maritalStatus="+maritalStatus+
        "maritalStatus="+maritalStatus+
        ", homeAddrCtryCd="+homeAddrCtryCd+
        "homeAddrCtryCd="+homeAddrCtryCd+
        ", homeState="+homeState+
        "homeState="+homeState+
        ", homeCity="+homeCity+
        "homeCity="+homeCity+
        ", homeZone="+homeZone+
        "homeZone="+homeZone+
        ", houseOwnership="+houseOwnership+
        "houseOwnership="+houseOwnership+
        ", homeAdd="+homeAdd+
        "homeAdd="+homeAdd+
        ", homePostcode="+homePostcode+
        "homePostcode="+homePostcode+
        ", homePhone="+homePhone+
        "homePhone="+homePhone+
        ", cellphone="+cellphone+
        "cellphone="+cellphone+
        ", email="+email+
        "email="+email+
        ", familyMember="+familyMember+
        "familyMember="+familyMember+
        ", bankmemFlag="+bankmemFlag+
        "bankmemFlag="+bankmemFlag+
        ", bankmemberNo="+bankmemberNo+
        "bankmemberNo="+bankmemberNo+
        ", corpName="+corpName+
        "corpName="+corpName+
        ", empAddrCtryCd="+empAddrCtryCd+
        "empAddrCtryCd="+empAddrCtryCd+
        ", empProvince="+empProvince+
        "empProvince="+empProvince+
        ", empCity="+empCity+
        "empCity="+empCity+
        ", empZone="+empZone+
        "empZone="+empZone+
        ", empAdd="+empAdd+
        "empAdd="+empAdd+
        ", empPostcode="+empPostcode+
        "empPostcode="+empPostcode+
        ", empPhone="+empPhone+
        "empPhone="+empPhone+
        ", empDepartment="+empDepartment+
        "empDepartment="+empDepartment+
        ", empPost="+empPost+
        "empPost="+empPost+
        ", empStructure="+empStructure+
        "empStructure="+empStructure+
        ", empType="+empType+
        "empType="+empType+
        ", empWorkyears="+empWorkyears+
        "empWorkyears="+empWorkyears+
        ", jobGrade="+jobGrade+
        "jobGrade="+jobGrade+
        ", occupation="+occupation+
        "occupation="+occupation+
        ", titleOfTechnical="+titleOfTechnical+
        "titleOfTechnical="+titleOfTechnical+
        ", empPostName="+empPostName+
        "empPostName="+empPostName+
        ", smAmtVerifyInd="+smAmtVerifyInd+
        "smAmtVerifyInd="+smAmtVerifyInd+
        ", posPinVerifyInd="+posPinVerifyInd+
        "posPinVerifyInd="+posPinVerifyInd+
        ", photoUseFlag="+photoUseFlag+
        "photoUseFlag="+photoUseFlag+
        ", groupNum="+groupNum+
        "groupNum="+groupNum+
        ", otherAsk="+otherAsk+
        "otherAsk="+otherAsk+
        ", otherAnswer="+otherAnswer+
        "otherAnswer="+otherAnswer+
        ", raiseNum="+raiseNum+
        "raiseNum="+raiseNum+
        ", statementType="+statementType+
        "statementType="+statementType+
        ", statementAddress="+statementAddress+
        "statementAddress="+statementAddress+
        ", appnoExternal="+appnoExternal+
        "appnoExternal="+appnoExternal+
        ", bankCustomerId="+bankCustomerId+
        "bankCustomerId="+bankCustomerId+
        ", ecifCustomerCode="+ecifCustomerCode+
        "ecifCustomerCode="+ecifCustomerCode+
        ", custType="+custType+
        "custType="+custType+
        ", custClass="+custClass+
        "custClass="+custClass+
        ", recordStatus="+recordStatus+
        "recordStatus="+recordStatus+
        ", beforeName="+beforeName+
        "beforeName="+beforeName+
        ", islocal="+islocal+
        "islocal="+islocal+
        ", liveYears="+liveYears+
        "liveYears="+liveYears+
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
        if (org == null) org = "";
        if (appNo == null) appNo = "";
        if (bscSuppInd == null) bscSuppInd = "";
        if (attachNo == null) attachNo = 0;
        if (ifSelectedCard == null) ifSelectedCard = "";
        if (relationshipToBsc == null) relationshipToBsc = "";
        if (primCardNo == null) primCardNo = "";
        if (cardNo == null) cardNo = "";
        if (name == null) name = "";
        if (embLogo == null) embLogo = "";
        if (gender == null) gender = "";
        if (age == null) age = "";
        if (nationality == null) nationality = "";
        if (residencyCountryCd == null) residencyCountryCd = "";
        if (idType == null) idType = "";
        if (idNo == null) idNo = "";
        if (idLastAll == null) idLastAll = "";
        if (idLastDate == null) idLastDate = new Date();
        if (idIssuerAddress == null) idIssuerAddress = "";
        if (birthday == null) birthday = new Date();
        if (liquidAsset == null) liquidAsset = "";
        if (yearIncome == null) yearIncome = BigDecimal.ZERO;
        if (qualification == null) qualification = "";
        if (degree == null) degree = "";
        if (maritalStatus == null) maritalStatus = "";
        if (homeAddrCtryCd == null) homeAddrCtryCd = "";
        if (homeState == null) homeState = "";
        if (homeCity == null) homeCity = "";
        if (homeZone == null) homeZone = "";
        if (houseOwnership == null) houseOwnership = "";
        if (homeAdd == null) homeAdd = "";
        if (homePostcode == null) homePostcode = "";
        if (homePhone == null) homePhone = "";
        if (cellphone == null) cellphone = "";
        if (email == null) email = "";
        if (familyMember == null) familyMember = "";
        if (bankmemFlag == null) bankmemFlag = "";
        if (bankmemberNo == null) bankmemberNo = "";
        if (corpName == null) corpName = "";
        if (empAddrCtryCd == null) empAddrCtryCd = "";
        if (empProvince == null) empProvince = "";
        if (empCity == null) empCity = "";
        if (empZone == null) empZone = "";
        if (empAdd == null) empAdd = "";
        if (empPostcode == null) empPostcode = "";
        if (empPhone == null) empPhone = "";
        if (empDepartment == null) empDepartment = "";
        if (empPost == null) empPost = "";
        if (empStructure == null) empStructure = "";
        if (empType == null) empType = "";
        if (empWorkyears == null) empWorkyears = 0;
        if (jobGrade == null) jobGrade = "";
        if (occupation == null) occupation = "";
        if (titleOfTechnical == null) titleOfTechnical = "";
        if (empPostName == null) empPostName = "";
        if (smAmtVerifyInd == null) smAmtVerifyInd = "";
        if (posPinVerifyInd == null) posPinVerifyInd = "";
        if (photoUseFlag == null) photoUseFlag = "";
        if (groupNum == null) groupNum = "";
        if (otherAsk == null) otherAsk = "";
        if (otherAnswer == null) otherAnswer = "";
        if (raiseNum == null) raiseNum = 0;
        if (statementType == null) statementType = "";
        if (statementAddress == null) statementAddress = "";
        if (appnoExternal == null) appnoExternal = "";
        if (bankCustomerId == null) bankCustomerId = "";
        if (ecifCustomerCode == null) ecifCustomerCode = "";
        if (custType == null) custType = "";
        if (custClass == null) custClass = "";
        if (recordStatus == null) recordStatus = "";
        if (beforeName == null) beforeName = "";
        if (islocal == null) islocal = "";
        if (liveYears == null) liveYears = 0;
        if (createDate == null) createDate = new Date();
        if (createUser == null) createUser = "";
        if (updateDate == null) updateDate = new Date();
        if (updateUser == null) updateUser = "";
        if (jpaVersion == null) jpaVersion = 0;
    }
}