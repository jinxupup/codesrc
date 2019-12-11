package com.jjb.ecms.facility.risk;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 调用风控输入变量集
 * @Description: 
 * @author JYData-R&D-HN
 * @date 2018年1月26日 下午7:57:18
 * @version V1.0
 */
public class BjjRiskReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String appId;//申请人信息-申请编号 
	private String appSource;//申请人信息-申请渠道 
	private String org;//申请人信息-机构号 
	private String isVirtualCard;//申请人信息-是否虚拟卡 
	private String productCd;//申请人信息-产品编号 
	private String ifApplyAttachOnly;//申请人信息-是否申请独立附卡 
	private String custName;//申请人信息-客户姓名 
	private Integer age;//申请人信息-年龄 
	private String gender;//申请人信息-性别 
	private String maritalStatus;//申请人信息-婚姻状况 
	private String isBankMem;//申请人信息-是否行内员工 
	private String phoneNo;//申请人信息-手机号 
	private String idType;//申请人信息-证件类型 
	private String idNo;//GPS控制输入-证件号码 
	private String idFirst2Digits;//GPS准入输入-证件号前两位数字 
	private String empProvince;//GPS准入输入-公司所在省 
	private String educationType;//GPS准入输入-教育状况 
	private String isAttachedCard;//GPS准入输入-是否附卡申请人 
	private BigDecimal yearIncome;//GPS准入输入-年收入 
	private String ifCallPBOC;//GPS准入输入-是否调用人行征信 
	private Integer idValidatyDaysRemaining;//GPS准入输入-证件有效期剩余天数 
	private String ifIdTooManyApplications;//GPS准入输入-证件号码申请次数过多 
	private String ifMobileTooManyApplications;//GPS准入输入-手机号码申请次数过多 
	private String ifIdTooManyAppDiffNames;//GPS准入输入-同一证件号码多次不同姓名申请 
	private String ifIdTooManyFails;//GPS准入输入-同一证件号码失败次数过多 
	private String ifExistInAps;//GPS反欺诈输入- 申请卡产品在信审系统中是否存在 
	private String ifExistInCps;//GPS反欺诈输入- 卡产品在发卡系统中是否重复申请 
	private String ifAttachToPrimMcard;//GPS反欺诈输入- 附卡是否有对应主卡 
	private String attachToPrimMcardStatus;//GPS反欺诈输入- 附属卡对应主卡状态 
	private String acctBlockCode;//GPS反欺诈输入- 已申请卡片账户锁定码 
	private String cardBlockCode;//GPS反欺诈输入- 已申请卡片锁定码 
	private String ifOneCellPhoneToNapply;//GPS反欺诈输入- 申请人手机号码是否对应多个申请 
	private String ifOneIdToNname;//GPS反欺诈输入- 申请人证件是否对应多个姓名 
	private String ifOneHomePhoneToNapply;//GPS反欺诈输入- 申请人住宅电话是否对应多个申请人 
	private Integer applyNum180Day;//GPS反欺诈输入- 半年申请次数 
	private Integer applyFailNum180Day;//GPS反欺诈输入- 半年内失败申请次数 
	private String pBlackListActType;//GPS反欺诈输入- 怀疑个人黑名单 行动类型
	private String ifCellPhoneBlackListActType;//GPS反欺诈输入- 怀疑个人移动电话黑名单 行动类型
	private String ifpHomePhoneBlackListActType;//GPS反欺诈输入- 怀疑个人家庭电话黑名单 行动类型
	private String ifpHomeAddrBlackListActType;//GPS反欺诈输入- 怀疑个人家庭地址黑名单 行动类型
	private String ifpEmpPhoneBlackListActType;//GPS反欺诈输入- 怀疑个人所在公司电话黑名单 行动类型
	private String ifpEmpAddrBlackListActType;//GPS反欺诈输入- 怀疑个人所在公司名称黑名单 行动类型
	private String isCorpAddBlActType;//GPS反欺诈输入- 怀疑个人所在公司地址黑名单 行动类型
	private String ifOneHomeAddToApp;//GPS反欺诈输入- 申请人家庭地址多人匹配（申请人的家庭地址对应多个申请人的家庭地址） 
	private String ifOneCorpAddToApp;//GPS反欺诈输入- 申请人单位地址多人匹配（申请人的单位地址对应多个申请人的单位地址） 
	private String ifOneContactsToApp;//GPS反欺诈输入- 申请人联系人多人匹配（申请人的联系人（包括直系和其他联系人）对应多个申请人的联系人）（姓名和联系电话同时匹配）
	private String ifOneCorpPhoneToCorpName;//GPS反欺诈输入- 同一单位电话对应多个单位名称 
	private String ifSameEmpAddAndHomeAdd;//GPS反欺诈输入- 申请件单位地址填写与住宅地址一致 
	private String ifOneEmailToApp;//GPS人工检核-申请人邮箱多人匹配 
	private String ifOneHomePhoneToNEmpPhone;//GPS人工检核-申请人住宅电话对单位电话多人匹配 
	private String ifOneContMobileToNContName;//-申请人联系人姓名多人匹配 
	private String ifOneMailerIndToApp;//-申请人邮寄住宅地址（不为空）多人匹配 
	private String ifpEmailBlackListActType;//-怀疑电子邮箱黑名单 行动类型
	private String ifMobile7DigitDup;//-手机号连续7位重复重复 
	private String ifManualAntiFraud;//-已进行人工反欺诈检核 
	private String manualAntiFraudCode;//-人工反欺诈检验结果编码 
	private Integer idApplyNum30Days;//证件号码30天内申请次数
	private Integer mobileApplyNum30Days;//手机号码30天内申请次数
	private Integer applyAmt;//申请额度
	private String businessAddress;//单位地址城市编号
	private String currentAddress;//居住地址城市编号
	
	public Integer getIdApplyNum30Days() {
		return idApplyNum30Days;
	}
	public void setIdApplyNum30Days(Integer idApplyNum30Days) {
		this.idApplyNum30Days = idApplyNum30Days;
	}
	public Integer getMobileApplyNum30Days() {
		return mobileApplyNum30Days;
	}
	public void setMobileApplyNum30Days(Integer mobileApplyNum30Days) {
		this.mobileApplyNum30Days = mobileApplyNum30Days;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAppSource() {
		return appSource;
	}
	public void setAppSource(String appSource) {
		this.appSource = appSource;
	}
	public String getOrg() {
		return org;
	}
	public void setOrg(String org) {
		this.org = org;
	}
	public String getIsVirtualCard() {
		return isVirtualCard;
	}
	public void setIsVirtualCard(String isVirtualCard) {
		this.isVirtualCard = isVirtualCard;
	}
	public String getProductCd() {
		return productCd;
	}
	public void setProductCd(String productCd) {
		this.productCd = productCd;
	}
	public String getIfApplyAttachOnly() {
		return ifApplyAttachOnly;
	}
	public void setIfApplyAttachOnly(String ifApplyAttachOnly) {
		this.ifApplyAttachOnly = ifApplyAttachOnly;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMaritalStatus() {
		return maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	public String getIsBankMem() {
		return isBankMem;
	}
	public void setIsBankMem(String isBankMem) {
		this.isBankMem = isBankMem;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdFirst2Digits() {
		return idFirst2Digits;
	}
	public void setIdFirst2Digits(String idFirst2Digits) {
		this.idFirst2Digits = idFirst2Digits;
	}
	public String getEmpProvince() {
		return empProvince;
	}
	public void setEmpProvince(String empProvince) {
		this.empProvince = empProvince;
	}
	public String getEducationType() {
		return educationType;
	}
	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}
	public String getIsAttachedCard() {
		return isAttachedCard;
	}
	public void setIsAttachedCard(String isAttachedCard) {
		this.isAttachedCard = isAttachedCard;
	}
	public BigDecimal getYearIncome() {
		return yearIncome;
	}
	public void setYearIncome(BigDecimal yearIncome) {
		this.yearIncome = yearIncome;
	}
	public String getIfCallPBOC() {
		return ifCallPBOC;
	}
	public void setIfCallPBOC(String ifCallPBOC) {
		this.ifCallPBOC = ifCallPBOC;
	}
	public Integer getIdValidatyDaysRemaining() {
		return idValidatyDaysRemaining;
	}
	public void setIdValidatyDaysRemaining(Integer idValidatyDaysRemaining) {
		this.idValidatyDaysRemaining = idValidatyDaysRemaining;
	}
	public String getIfIdTooManyApplications() {
		return ifIdTooManyApplications;
	}
	public void setIfIdTooManyApplications(String ifIdTooManyApplications) {
		this.ifIdTooManyApplications = ifIdTooManyApplications;
	}
	public String getIfMobileTooManyApplications() {
		return ifMobileTooManyApplications;
	}
	public void setIfMobileTooManyApplications(String ifMobileTooManyApplications) {
		this.ifMobileTooManyApplications = ifMobileTooManyApplications;
	}
	public String getIfIdTooManyAppDiffNames() {
		return ifIdTooManyAppDiffNames;
	}
	public void setIfIdTooManyAppDiffNames(String ifIdTooManyAppDiffNames) {
		this.ifIdTooManyAppDiffNames = ifIdTooManyAppDiffNames;
	}
	public String getIfIdTooManyFails() {
		return ifIdTooManyFails;
	}
	public void setIfIdTooManyFails(String ifIdTooManyFails) {
		this.ifIdTooManyFails = ifIdTooManyFails;
	}
	public String getIfExistInAps() {
		return ifExistInAps;
	}
	public void setIfExistInAps(String ifExistInAps) {
		this.ifExistInAps = ifExistInAps;
	}
	public String getIfExistInCps() {
		return ifExistInCps;
	}
	public void setIfExistInCps(String ifExistInCps) {
		this.ifExistInCps = ifExistInCps;
	}
	public String getIfAttachToPrimMcard() {
		return ifAttachToPrimMcard;
	}
	public void setIfAttachToPrimMcard(String ifAttachToPrimMcard) {
		this.ifAttachToPrimMcard = ifAttachToPrimMcard;
	}
	public String getAttachToPrimMcardStatus() {
		return attachToPrimMcardStatus;
	}
	public void setAttachToPrimMcardStatus(String attachToPrimMcardStatus) {
		this.attachToPrimMcardStatus = attachToPrimMcardStatus;
	}
	public String getAcctBlockCode() {
		return acctBlockCode;
	}
	public void setAcctBlockCode(String acctBlockCode) {
		this.acctBlockCode = acctBlockCode;
	}
	public String getCardBlockCode() {
		return cardBlockCode;
	}
	public void setCardBlockCode(String cardBlockCode) {
		this.cardBlockCode = cardBlockCode;
	}
	public String getIfOneCellPhoneToNapply() {
		return ifOneCellPhoneToNapply;
	}
	public void setIfOneCellPhoneToNapply(String ifOneCellPhoneToNapply) {
		this.ifOneCellPhoneToNapply = ifOneCellPhoneToNapply;
	}
	public String getIfOneIdToNname() {
		return ifOneIdToNname;
	}
	public void setIfOneIdToNname(String ifOneIdToNname) {
		this.ifOneIdToNname = ifOneIdToNname;
	}
	public String getIfOneHomePhoneToNapply() {
		return ifOneHomePhoneToNapply;
	}
	public void setIfOneHomePhoneToNapply(String ifOneHomePhoneToNapply) {
		this.ifOneHomePhoneToNapply = ifOneHomePhoneToNapply;
	}
	public Integer getApplyNum180Day() {
		return applyNum180Day;
	}
	public void setApplyNum180Day(Integer applyNum180Day) {
		this.applyNum180Day = applyNum180Day;
	}
	public Integer getApplyFailNum180Day() {
		return applyFailNum180Day;
	}
	public void setApplyFailNum180Day(Integer applyFailNum180Day) {
		this.applyFailNum180Day = applyFailNum180Day;
	}
	public String getIfOneHomeAddToApp() {
		return ifOneHomeAddToApp;
	}
	public void setIfOneHomeAddToApp(String ifOneHomeAddToApp) {
		this.ifOneHomeAddToApp = ifOneHomeAddToApp;
	}
	public String getIfOneCorpAddToApp() {
		return ifOneCorpAddToApp;
	}
	public void setIfOneCorpAddToApp(String ifOneCorpAddToApp) {
		this.ifOneCorpAddToApp = ifOneCorpAddToApp;
	}
	public String getIfOneContactsToApp() {
		return ifOneContactsToApp;
	}
	public void setIfOneContactsToApp(String ifOneContactsToApp) {
		this.ifOneContactsToApp = ifOneContactsToApp;
	}
	public String getIfOneCorpPhoneToCorpName() {
		return ifOneCorpPhoneToCorpName;
	}
	public void setIfOneCorpPhoneToCorpName(String ifOneCorpPhoneToCorpName) {
		this.ifOneCorpPhoneToCorpName = ifOneCorpPhoneToCorpName;
	}
	public String getIfSameEmpAddAndHomeAdd() {
		return ifSameEmpAddAndHomeAdd;
	}
	public void setIfSameEmpAddAndHomeAdd(String ifSameEmpAddAndHomeAdd) {
		this.ifSameEmpAddAndHomeAdd = ifSameEmpAddAndHomeAdd;
	}
	public String getIfOneEmailToApp() {
		return ifOneEmailToApp;
	}
	public void setIfOneEmailToApp(String ifOneEmailToApp) {
		this.ifOneEmailToApp = ifOneEmailToApp;
	}
	public String getIfOneHomePhoneToNEmpPhone() {
		return ifOneHomePhoneToNEmpPhone;
	}
	public void setIfOneHomePhoneToNEmpPhone(String ifOneHomePhoneToNEmpPhone) {
		this.ifOneHomePhoneToNEmpPhone = ifOneHomePhoneToNEmpPhone;
	}
	public String getIfOneContMobileToNContName() {
		return ifOneContMobileToNContName;
	}
	public void setIfOneContMobileToNContName(String ifOneContMobileToNContName) {
		this.ifOneContMobileToNContName = ifOneContMobileToNContName;
	}
	public String getIfOneMailerIndToApp() {
		return ifOneMailerIndToApp;
	}
	public void setIfOneMailerIndToApp(String ifOneMailerIndToApp) {
		this.ifOneMailerIndToApp = ifOneMailerIndToApp;
	}
	public String getIfMobile7DigitDup() {
		return ifMobile7DigitDup;
	}
	public void setIfMobile7DigitDup(String ifMobile7DigitDup) {
		this.ifMobile7DigitDup = ifMobile7DigitDup;
	}
	public String getIfManualAntiFraud() {
		return ifManualAntiFraud;
	}
	public void setIfManualAntiFraud(String ifManualAntiFraud) {
		this.ifManualAntiFraud = ifManualAntiFraud;
	}
	public String getManualAntiFraudCode() {
		return manualAntiFraudCode;
	}
	public void setManualAntiFraudCode(String manualAntiFraudCode) {
		this.manualAntiFraudCode = manualAntiFraudCode;
	}
	public String getpBlackListActType() {
		return pBlackListActType;
	}
	public void setpBlackListActType(String pBlackListActType) {
		this.pBlackListActType = pBlackListActType;
	}
	public String getIfCellPhoneBlackListActType() {
		return ifCellPhoneBlackListActType;
	}
	public void setIfCellPhoneBlackListActType(String ifCellPhoneBlackListActType) {
		this.ifCellPhoneBlackListActType = ifCellPhoneBlackListActType;
	}
	public String getIfpHomePhoneBlackListActType() {
		return ifpHomePhoneBlackListActType;
	}
	public void setIfpHomePhoneBlackListActType(String ifpHomePhoneBlackListActType) {
		this.ifpHomePhoneBlackListActType = ifpHomePhoneBlackListActType;
	}
	public String getIfpHomeAddrBlackListActType() {
		return ifpHomeAddrBlackListActType;
	}
	public void setIfpHomeAddrBlackListActType(String ifpHomeAddrBlackListActType) {
		this.ifpHomeAddrBlackListActType = ifpHomeAddrBlackListActType;
	}
	public String getIfpEmpPhoneBlackListActType() {
		return ifpEmpPhoneBlackListActType;
	}
	public void setIfpEmpPhoneBlackListActType(String ifpEmpPhoneBlackListActType) {
		this.ifpEmpPhoneBlackListActType = ifpEmpPhoneBlackListActType;
	}
	public String getIfpEmpAddrBlackListActType() {
		return ifpEmpAddrBlackListActType;
	}
	public void setIfpEmpAddrBlackListActType(String ifpEmpAddrBlackListActType) {
		this.ifpEmpAddrBlackListActType = ifpEmpAddrBlackListActType;
	}
	public String getIsCorpAddBlActType() {
		return isCorpAddBlActType;
	}
	public void setIsCorpAddBlActType(String isCorpAddBlActType) {
		this.isCorpAddBlActType = isCorpAddBlActType;
	}
	public String getIfpEmailBlackListActType() {
		return ifpEmailBlackListActType;
	}
	public void setIfpEmailBlackListActType(String ifpEmailBlackListActType) {
		this.ifpEmailBlackListActType = ifpEmailBlackListActType;
	}
	public Integer getApplyAmt() {
		return applyAmt;
	}
	public void setApplyAmt(Integer applyAmt) {
		this.applyAmt = applyAmt;
	}
	public String getBusinessAddress() {
		return businessAddress;
	}
	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}
	public String getCurrentAddress() {
		return currentAddress;
	}
	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AicRcsReq [appId=");
		builder.append(appId);
		builder.append(", appSource=");
		builder.append(appSource);
		builder.append(", org=");
		builder.append(org);
		builder.append(", isVirtualCard=");
		builder.append(isVirtualCard);
		builder.append(", productCd=");
		builder.append(productCd);
		builder.append(", ifApplyAttachOnly=");
		builder.append(ifApplyAttachOnly);
		builder.append(", custName=");
		builder.append(custName);
		builder.append(", age=");
		builder.append(age);
		builder.append(", gender=");
		builder.append(gender);
		builder.append(", maritalStatus=");
		builder.append(maritalStatus);
		builder.append(", isBankMem=");
		builder.append(isBankMem);
		builder.append(", phoneNo=");
		builder.append(phoneNo);
		builder.append(", idType=");
		builder.append(idType);
		builder.append(", idNo=");
		builder.append(idNo);
		builder.append(", idFirst2Digits=");
		builder.append(idFirst2Digits);
		builder.append(", empProvince=");
		builder.append(empProvince);
		builder.append(", educationType=");
		builder.append(educationType);
		builder.append(", isAttachedCard=");
		builder.append(isAttachedCard);
		builder.append(", yearIncome=");
		builder.append(yearIncome);
		builder.append(", ifCallPBOC=");
		builder.append(ifCallPBOC);
		builder.append(", idValidatyDaysRemaining=");
		builder.append(idValidatyDaysRemaining);
		builder.append(", ifIdTooManyApplications=");
		builder.append(ifIdTooManyApplications);
		builder.append(", ifMobileTooManyApplications=");
		builder.append(ifMobileTooManyApplications);
		builder.append(", ifIdTooManyAppDiffNames=");
		builder.append(ifIdTooManyAppDiffNames);
		builder.append(", ifIdTooManyFails=");
		builder.append(ifIdTooManyFails);
		builder.append(", ifExistInAps=");
		builder.append(ifExistInAps);
		builder.append(", ifExistInCps=");
		builder.append(ifExistInCps);
		builder.append(", ifAttachToPrimMcard=");
		builder.append(ifAttachToPrimMcard);
		builder.append(", attachToPrimMcardStatus=");
		builder.append(attachToPrimMcardStatus);
		builder.append(", acctBlockCode=");
		builder.append(acctBlockCode);
		builder.append(", cardBlockCode=");
		builder.append(cardBlockCode);
		builder.append(", ifOneCellPhoneToNapply=");
		builder.append(ifOneCellPhoneToNapply);
		builder.append(", ifOneIdToNname=");
		builder.append(ifOneIdToNname);
		builder.append(", ifOneHomePhoneToNapply=");
		builder.append(ifOneHomePhoneToNapply);
		builder.append(", applyNum180Day=");
		builder.append(applyNum180Day);
		builder.append(", applyFailNum180Day=");
		builder.append(applyFailNum180Day);
		builder.append(", pBlackListActType=");
		builder.append(pBlackListActType);
		builder.append(", ifCellPhoneBlackListActType=");
		builder.append(ifCellPhoneBlackListActType);
		builder.append(", ifpHomePhoneBlackListActType=");
		builder.append(ifpHomePhoneBlackListActType);
		builder.append(", ifpHomeAddrBlackListActType=");
		builder.append(ifpHomeAddrBlackListActType);
		builder.append(", ifpEmpPhoneBlackListActType=");
		builder.append(ifpEmpPhoneBlackListActType);
		builder.append(", ifpEmpAddrBlackListActType=");
		builder.append(ifpEmpAddrBlackListActType);
		builder.append(", isCorpAddBlActType=");
		builder.append(isCorpAddBlActType);
		builder.append(", ifOneHomeAddToApp=");
		builder.append(ifOneHomeAddToApp);
		builder.append(", ifOneCorpAddToApp=");
		builder.append(ifOneCorpAddToApp);
		builder.append(", ifOneContactsToApp=");
		builder.append(ifOneContactsToApp);
		builder.append(", ifOneCorpPhoneToCorpName=");
		builder.append(ifOneCorpPhoneToCorpName);
		builder.append(", ifSameEmpAddAndHomeAdd=");
		builder.append(ifSameEmpAddAndHomeAdd);
		builder.append(", ifOneEmailToApp=");
		builder.append(ifOneEmailToApp);
		builder.append(", ifOneHomePhoneToNEmpPhone=");
		builder.append(ifOneHomePhoneToNEmpPhone);
		builder.append(", ifOneContMobileToNContName=");
		builder.append(ifOneContMobileToNContName);
		builder.append(", ifOneMailerIndToApp=");
		builder.append(ifOneMailerIndToApp);
		builder.append(", ifpEmailBlackListActType=");
		builder.append(ifpEmailBlackListActType);
		builder.append(", ifMobile7DigitDup=");
		builder.append(ifMobile7DigitDup);
		builder.append(", ifManualAntiFraud=");
		builder.append(ifManualAntiFraud);
		builder.append(", manualAntiFraudCode=");
		builder.append(manualAntiFraudCode);
		builder.append(", idApplyNum30Days=");
		builder.append(idApplyNum30Days);
		builder.append(", mobileApplyNum30Days=");
		builder.append(mobileApplyNum30Days);
		builder.append(", applyAmt=");
		builder.append(applyAmt);
		builder.append(", businessAddress=");
		builder.append(businessAddress);
		builder.append(", currentAddress=");
		builder.append(currentAddress);
		builder.append("]");
		return builder.toString();
	}
	
}
