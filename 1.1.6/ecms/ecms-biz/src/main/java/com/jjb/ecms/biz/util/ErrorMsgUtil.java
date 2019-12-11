package com.jjb.ecms.biz.util;

/**
 * @Description: 校验申请资料字段信息
 * @author JYData-R&D-L.L
 * @date 2016年9月13日 下午4:51:16
 * @version V1.0  
 */
public class ErrorMsgUtil {
	
	public static final String TYPEERROR = "类型错误"; // 类型错误
	
	public static final String LENGTHERROR = "长度错误"; // 长度错误
	
	public static final String SEVLETERROR = "服务器错误"; // 服务器问题
	
	public static final String NOCHARSETERROR = "编码格式错误"; // 没选择上传文件编码
	
	public static final String FILENAMEERROR = "文件名错误"; // 文件名格式错误

	public static final String[] EnCellName = new String[]{"appType","org","bscSuppInd","productCd","approveQuickFlag","appLmt","owningBranch",
		"appSource","applyFromType","custType","appPromotionCd","isSalesInd","spreaderType","spreaderName","spreaderNo","spreaderTelephone",
		"spreaderBranchThree","spreaderBranchTwo","remark","memo","ifSelectedCard","cardNo","idType","idNo","idLastDate","idIssuerAddress","gender",
		"birthday","name","lnameLogo","nameLogo","nationality","prOfCountry","residencyCountryCd","maritalStatus","qualification",
		"houseOwnership","houseType","liquidAsset","yearIncome","cellphone","email","familyMember","familyAvgeVenue","homeAddrCtryCd",
		"homeState","homeCity","homeZone","homeAdd","homePostcode","homePhone","occupation","empPost","titleOfTechnical","bankmemFlag",
		"bankmemberNo","corpName","empStructure","empType","empStandFrom","empDepapment","empAddrCtryCd","empProvince","empCity","empZone",
		"empAdd","empPostcode","empPhone","corpFax","contactName","contactRelation","contactIdType","contactIdNo","contactGender",
		"contactBirthday","contactMobile","contactEmpName","contactCorpPost","contactEmpPhone","contactCorpFax","contactOName","contactORelation",
		"contactOIdType","contactOIdNo","contactOMobile","contactOEmpName","contactOCorpPost","contactOCorpPhone","empStability","empStatus",
		"homeStandFrom","refName","refCardNo","posPinVerifyInd","photoUseFlag","otherAsk","otherAnswer","cardFetchMethod","cardMailerInd",
		"stmtMediaType","stmtMailAddrInd","billingCycle","creditTypeOther","ddInd","ddBankAcctName","ddBankName","ddBankBranch","ddBankAcctNo",
		"cardFace","incomeEvi","socialStatus","insureState","insureDate","insureMonthCount","socialInsAmt","insureBase","isLegalPerson","openTime",
		"registerFund","stockProp","estateType","estateValue","estateAge","loan","equityProp","carState","carBuyDate","carBrand","carValue",
		"driveLicenseId","driveLicRegDate","carLpn","currentDeposit","timeDeposit","fund","bond","personalFinancing","insure","ctcBank",
		"ctcCreditLimit","sendCardDate","collateralType","collateralName","collateralValues","pledgeType","pledgeName","pledgeValues",
		"bondsmanName","bondsmanProvince","bondsmanCity","bondsmanZone","bondsmanTel","bondsmanAddress","bondsmanPhnoe","omLimit",
		"bondsmanEnterprise","bondsmanEnterpriseNum","bondsmanEnterpriseAddr","bondsmanEnterpriseLinit","fetchBranch","age","degree",
		"empWorkyears","empPostName","motherLogo","idLastAll","jobGrade","oldCorpName","smAmtVerifyInd","contactTelephone","contactOTelephone",
		"loanType","loanAmount","isSecurity","companyType","post","payrollStatus","payrollAmount","fundRiskState","fundDepositeState",
		"fundDepositeDate","fundDepositeMAmt","fundDepositeMCount","fundDepositeBase","carInsureNo","carInsureComp","deposit3mConsumCount",
		"deposit6mMonthDropAmt","deposit6mDayAvgAmt","financeAmt","financeTime","financeBuyDate","ctcCardCount","personRtLoan","miniLoan",
		"socialIsCmpIn","registerType","registerNo","nameStatus","idStatus","jobStatus","estateStatus","estateLoanTime","estatePayAmt",
		"isIndBusiness","registerTime","registerEndTime","depositStatus","depositType","depositOpenTime","depositAvgAmt","financeStatus",
		"loanStatus","loanEndTime","loanPayAmt","liveAddrStatus","liveStatus","cardSendAddType","incomeType","isPrdChange","isAsistRec",
		"spreaderMode","spreaderCorpPreNo","acceptNo","acceptName","acceptTelephone","acceptMode","acceptType","reviewNo","reviewName",
		"reviewTelephone","preNo","preName","preTelephone","fcPrimCardNo","fcRelationshipToBsc","fcName","fcLnameLogo","fcNameLogo","fcGender",
		"fcAge","fcBirthday","fcNationality","fcIdType","fcIdNo","fcIdLastDate","fcEmail","fcCellphone","fcHomeState","fcHomeCity","fcHomeZone",
		"fcHomePostcode","fcHomeAdd","fcHomePhone","fcIdLastAll","realtimeIssuingFlag",
		"isInstalment","instalmentCreditActivityNo","mccNo","cashAmt","loanInitTerm","loanFeeMethod","loanFeeCalcMethod","appFeeAmt","appFeeRate"};
	public static final String[] EnCellFcName = new String[]{"appType","org","bscSuppInd","approveQuickFlag","owningBranch","appSource","applyFromType","custType",
		"appPromotionCd","isSalesInd","spreaderType","spreaderName","spreaderNo","spreaderTelephone","spreaderBranchThree","spreaderBranchTwo",
		"remark","memo","fcPrimCardNo","fcRelationshipToBsc","fcIfSelectedCard","fcCardNo","fcIdType","fcIdNo","fcIdLastDate","fcIdIssuerAddress","fcGender","fcBirthday",
		"fcName","fcLnameLogo","fcNameLogo","fcNationality","fcPrOfCountry","fcResidencyCountryCd","fcMaritalStatus","fcQualification","fcHouseOwnership",
		"fcHouseType","fcLiquidAsset","fcYearIncome","fcCellphone","fcEmail","fcFamilyMember","fcFamilyAvgeVenue","fcHomeAddrCtryCd","fcHomeState",
		"fcHomeCity","fcHomeZone","fcHomeAdd","fcHomePostcode","fcHomePhone","fcEmpStability","fcEmpStatus","fcHomeStandFrom","refName","refCardNo","fcPosPinVerifyInd","fcPhotoUseFlag",
		"fcOtherAsk","fcOtherAnswer","fcCardFetch","fcCardMailerInd","billingCycle","cardFace","fcFetchBranch","fcAge","fcDegree","fcMotherLogo","fcIdLastAll",
		"fcSmAmtVerifyInd","incomeType","isPrdChange","isAsistRec","spreaderMode","spreaderCorpPreNo","acceptNo","acceptName","acceptTelephone","acceptMode",
		"acceptType","reviewNo","reviewName","reviewTelephone","preNo","preName","preTelephone"};//独立附卡信息

	public static final String[] BlPersonal = new String[]{"risklistSrc","name","idType","idNo","cellPhone","homePhone","homeAdd",
			"corpName","empPhone","empAdd","reason","memo","validDate","actType"};//风险名单信息
	public static final String[] BankList = new String[]{"branchCode","branchName","empAdd","city","parentCode","branchIssueInd","cardCollectInd"};//网点机构信息

	public static void main(String[] args) {
 	}
}
