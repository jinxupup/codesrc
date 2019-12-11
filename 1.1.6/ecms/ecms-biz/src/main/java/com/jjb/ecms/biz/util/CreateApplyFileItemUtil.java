package com.jjb.ecms.biz.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.AddressType;
import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.ApplyFileItem;
import com.jjb.acl.facility.enums.bus.BscSuppIndicator;
import com.jjb.acl.facility.enums.bus.CardFetchMethod;
import com.jjb.acl.facility.enums.bus.DdIndicator;
import com.jjb.acl.facility.enums.bus.SocialStatus;
import com.jjb.acl.facility.enums.bus.StmtMediaType;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppPrimCardInfoDao;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 创建申请成功文件数据
 * @author H.N
 *
 */
@Component
public class CreateApplyFileItemUtil {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CacheContext cacheContext;
//	@Autowired
//	private AccessDictService accessDictService;
	@Autowired
	private TmAppPrimCardInfoDao tmAppPrimCardInfoDao;
	
	/**
	 * 创建申请待制卡数据
	 * @param appMain
	 * @param bscSuppIndicator
//	 * @param appPrimApplicantInfo
//	 * @param appPrimContactInfoMap
//	 * @param appAttachApplierInfo
//	 * @param cardfaceInfo
	 * @param appPrimCardInfo
	 * @param appPrimAnnexEvi
	 * @return
	 */
	public ApplyFileItem createApplyFileItem(TmAppMain appMain,
			TmAppAudit appAudit, BscSuppIndicator bscSuppIndicator,
			Map<String, TmAppCustInfo> custMap,
			Map<String, TmAppContact> appPrimContactInfoMap,
			TmAppPrimCardInfo appPrimCardInfo,
			TmAppPrimAnnexEvi appPrimAnnexEvi, TmProduct product,
			Integer attachNo) {
		ApplyFileItem item = new ApplyFileItem();
		if(appMain==null){
			return null;
		}
		TmAppCustInfo primCust = custMap.get(AppConstant.bscSuppInd_B_1);
		TmAppCustInfo attachCust = custMap.get(AppConstant.bscSuppInd_S+attachNo);
		//申请主信息
		item.AppType = AppCommonUtil.checkType( appMain.getAppType(),null); //申请类型
		item.AppNo = appMain.getAppNo(); // 申请编号
//		item.org = appMain.getOrg(); // 机构号
		item.BscSuppInd = bscSuppIndicator.name(); // 主附卡指示
		item.ProductCd = appMain.getProductCd(); // 卡产品
		item.CreditLimit = appMain.getAccLmt()==null ? BigDecimal.ZERO : appMain.getAccLmt(); // 信用额度
		item.CustRateDiscount = BigDecimal.ZERO; // 客户级利率
		//获得录入员编号
		item.CreateUser = appPrimCardInfo.getInputNo();
		//银行信息
		item.OwningBranch = appMain.getOwningBranch(); // 发卡网点
		item.AppPromotionCd = null; // 促销码
		item.AppSource = appMain.getAppSource(); // 申请来源

//		item.salesInd = AppCommonUtil.stringToEnum(Indicator.class,appPrimCardInfo.getIsSalesInd(),Indicator.Y); // 是否接受推广邮件
		item.SalesInd = Indicator.Y.state; // 是否接受推广邮件
//		item.isPrdChange=AppCommonUtil.stringToEnum(Indicator.class,appPrimCardInfo.getIsPrdChange());//是否同意卡片自动降级
		item.RepresentName = StringUtils.setValue(appPrimCardInfo.getSpreaderNo(),"")
				+"-"+StringUtils.setValue(appPrimCardInfo.getSpreaderName(),""); // 客户经理
//		item.isUrgentCard = AppCommonUtil.stringToEnum(Indicator.class,appMain.getIsUrgentCard(),Indicator.N); // 是否紧急制卡
		item.IsUrgentCard = Indicator.N.state; // 是否紧急制卡
		//卡面信息
		item.DesignCd = appPrimCardInfo.getCardfaceCd(); // 版面代码
		//若主卡
		if(bscSuppIndicator == BscSuppIndicator.B) {
			//申请主卡人信息
			item.CardNo = primCust.getCardNo();
			if(StringUtils.isEmpty(primCust.getBankCustomerId())) {
				throw new ProcessException("["+primCust.getName()+"]客户号不能为空!");
			}
			item.BankCustomerId = primCust.getBankCustomerId(); // 行内客户号
			item.PosPinVerifyInd = AppCommonUtil.checkType( primCust.getPosPinVerifyInd(),Indicator.Y.state);// 消费凭密选择
			item.PhotoUseFlag =  AppCommonUtil.checkType(primCust.getPhotoUseFlag(),Indicator.N.state);// 彩照卡标志(现不用)
		//	item.bankmemberNo = primCust.getBankmemberNo(); // 本行员工编号

			item.BankMemberNo = primCust.getBankmemberNo(); // 本行员工编号

		/*	item.bankmemFlag =appPrimApplicantInfo.getBankmemFlag();//是否行内员工
			item.motherLogo=appPrimApplicantInfo.getMotherLogo();//母亲姓氏
			item.raiseNum=appPrimApplicantInfo.getRaiseNum();//抚养人数
			item.jobGrade=appPrimApplicantInfo.getJobGrade();//岗位级别
			item.oldCorpName=appPrimApplicantInfo.getOldCorpName();//前一单位名称
			item.oldCorpProveName=appPrimApplicantInfo.getOldCorpProveName();//前一单位证明人
			item.oldCorpProveCnt=appPrimApplicantInfo.getOldCorpProveCnt();//前一单位证明人联系方式
*/			item.Name = primCust.getName(); // 姓名（中文）
			item.Gender = AppCommonUtil.checkType(primCust.getGender(),null); // 性别


			item.Birthday = DateUtils.dateToString(primCust.getBirthday() ,DateUtils.DAY_YMD); // 出生日期
			item.MaritalStatus = AppCommonUtil.checkType(primCust.getMaritalStatus(),null); // 婚姻状况
			item.Nationality = primCust.getNationality(); // 国籍
			item.NbrOfDependents = primCust.getFamilyMember()==null ? null : Integer.parseInt(primCust.getFamilyMember()); // 家庭人口
			item.IdType = AppCommonUtil.checkType(primCust.getIdType(),null); // 证件类型
			item.IdNo = primCust.getIdNo(); // 证件号码
			item.Qualification = AppCommonUtil.checkType(primCust.getQualification(),null); //教育情况
			item.Degree = AppCommonUtil.checkType(primCust.getDegree(),null);//学位
			item.QUicsTxnInd = AppCommonUtil.checkType(primCust.getSmAmtVerifyInd(),Indicator.N.state);//是否支持小额免密免签
			item.HomeAddress = primCust.getHomeAdd(); // 现住址
			item.HomeZip = primCust.getHomePostcode(); // 现住址邮编
			// 现住址居住起始年月
			item.HomeStandFrom = null;
//			try {
////				item.homeStandFrom = DateUtils.stringToDate(primCust.getHomeStandFrom(), DateUtils.DAY_YMD);
//			} catch (ParseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			item.HomePhone = primCust.getHomePhone(); // 现住址电话号码
			item.MobileNo = primCust.getCellphone(); // 移动电话号码
			item.Email = primCust.getEmail(); // 电子信箱
			if(StringUtils.equals(primCust.getHouseOwnership(), "F")) {
				primCust.setHouseOwnership("E");//如果是F-单位宿舍，就自动转换成E-单位分配
			}
			item.HouseOwnership = AppCommonUtil.checkType(primCust.getHouseOwnership(),null); // 住宅状况
			if (primCust.getHouseOwnership()!=null && primCust.getHouseOwnership().equals("B")) {//B:按揭住宅
				item.HouseLoanFlag = Indicator.Y.state; // 是否有按揭贷款(现不用)
			} else {
				item.HouseLoanFlag = Indicator.N.state; // 是否有按揭贷款(现不用)
			}
			item.CompanyName = primCust.getCorpName(); // 单位全称
			item.CompanyAddress = primCust.getEmpAdd(); // 单位地址
			item.CompanyZip = primCust.getEmpPostcode(); // 单位邮编
			item.CompanyPhone = primCust.getEmpPhone(); // 单位电话号码
			item.CompanyCategory = AppCommonUtil.checkType(primCust.getEmpStructure(),null); // 单位性质
			String industry = "";
			if(StringUtils.isNotEmpty(primCust.getEmpType()) && primCust.getEmpType().length()>0) {
				industry = primCust.getEmpType().charAt(0)+"";
			}
			item.IndustryCategory = industry; // 行业类别
			item.Occupation =  AppCommonUtil.checkType(primCust.getOccupation(),"H");//职业
//			item.Occupation = null;
			item.TitleOfTechnical = AppCommonUtil.checkType(primCust.getTitleOfTechnical(),null); // 职称
			String title = "";
			if(StringUtils.isNotEmpty(primCust.getEmpPost()) && primCust.getEmpPost().length()>0) {
				title = primCust.getEmpPost().charAt(0)+"";
			}
			item.Title = title; // 职务
			BigDecimal yearIncome = primCust.getYearIncome();
			//如果年收入填写的小于1000，则乘以10000（因为当前系统以万元为单位的）
			if(yearIncome!=null && yearIncome.compareTo(new BigDecimal(1000))<0) {
				if(yearIncome.compareTo(new BigDecimal(1000))<0) {
					yearIncome  = yearIncome.multiply(new BigDecimal(10000));
				}
				yearIncome = yearIncome.setScale(2,BigDecimal.ROUND_HALF_UP);//四舍五入
			}
			item.RevenuePerYear = yearIncome; // 个人年收入
			item.FamilyAverageRevenue = null; // 家庭人均年收入
			item.ObligateQuestion = primCust.getOtherAsk(); // 预留问题
			item.ObligateAnswer = primCust.getOtherAnswer(); // 预留答案
			if(primCust.getEmbLogo()!=null){
				item.EmbName = primCust.getEmbLogo().toUpperCase(); // 凸印名-姓名拼音
			}
			item.HomeCountryCd = StringUtils.setValue(primCust.getHomeAddrCtryCd(),"156"); // 家庭国家代码
			item.HomeState = primCust.getHomeState(); // 家庭省份
			item.HomeCity = primCust.getHomeCity(); // 家庭城市
			item.HomeDistrict = primCust.getHomeZone(); // 家庭行政区
			item.CompanyCountryCd = StringUtils.setValue(primCust.getEmpAddrCtryCd(),"156"); // 公司国家代码
			item.CompanyState = primCust.getEmpProvince(); // 公司省份
			item.CompanyCity = primCust.getEmpCity(); // 公司城市
			item.CompanyDistrict = primCust.getEmpZone(); // 公司行政区
//			item.recomCardNo = appPrimCardInfo.getRefCardNo();//推荐人卡号
//			item.recomName = appPrimCardInfo.getRefName();//推荐人姓名
//			item.RecomCardNo = appPrimCardInfo.getSpreaderNo();//推荐人卡号
			item.RecomCardNo = appPrimCardInfo.getSpreaderCardNo(); //推荐人卡号
			item.RecomName = StringUtils.setValue(appPrimCardInfo.getSpreaderNo(),"")
					+"-"+StringUtils.setValue(appPrimCardInfo.getSpreaderName(),"");//推荐人姓名
//			item.empStability = AppCommonUtil.stringToEnum(EmpStability.class ,primCust.getEmpStability(),EmpStability.B); // 工作稳定性
//			item.empStatus = AppCommonUtil.stringToEnum(Indicator.class ,primCust.getEmpStatus(),Indicator.Y); // 就业状态
//			item.houseType = AppCommonUtil.stringToEnum(HouseType.class ,primCust.getHouseType(),null); // 住宅类型
			item.EmpStability = "B"; // 工作稳定性,B:基本稳定
			item.EmpStatus = Indicator.Y.state; // 就业状态
			item.HouseType = null; // 住宅类型
			item.IdIssuerAddress = primCust.getIdIssuerAddress(); // 发证机关所在地址
			item.IdLastDate =DateUtils.dateToString(primCust.getIdLastDate(),DateUtils.DAY_YMD); // 证件到期日
//			item.idLastaLL = appPrimApplicantInfo.getIdLastAll();//证件是否长期有效
			item.LiquidAsset = AppCommonUtil.checkType(primCust.getLiquidAsset(),null); // 个人资产类型
			item.PrOfCountry = null; // 是否永久居住
			item.ResidencyCountryCd = StringUtils.setValue(primCust.getResidencyCountryCd(), "156"); // 永久居住地国家代码
			item.CompanyFax  = null; // 公司传真
			item.EmpDepartment = primCust.getEmpDepartment(); // 任职部门
			//现单位工作起始年月
			item.CorpBegDate = null;
//			//现单位工作起始年
//			try {
//				item.corpBegDate = DateUtils.stringToDate(primCust.getEmpStandFrom(), DateUtils.DAY_YMD);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			item.EcifNo = primCust.getEcifCustomerCode();//ecif客户编号
			//申请主卡-第一联系人
			if(appPrimContactInfoMap!=null && appPrimContactInfoMap.containsKey("mconItemInfo1")){
				TmAppContact contactInfo = appPrimContactInfoMap.get("mconItemInfo1");
				item.ContactName = contactInfo.getContactName(); // 联系人姓名（中文）
				item.ContactRelationship = AppCommonUtil.checkType(contactInfo.getContactRelation(),null); // 联系人与申请人关系
				item.ContactIdType = AppCommonUtil.checkType(contactInfo.getContactIdType(),null); // 联系人身份证件类型
				item.ContactIdNo = contactInfo.getContactIdNo(); // 联系人证件号码
				item.ContactCorpPhone = contactInfo.getContactEmpPhone(); // 联系人公司电话
				item.ContactBirthday =DateUtils.dateToString(contactInfo.getContactBirthday(),DateUtils.DAY_YMD); // 联系人生日
				 
				item.ContactCorpPost = AppCommonUtil.checkType(contactInfo.getContactCorpPost(),null); // 联系人公司职务
				item.ContactCorpFax = contactInfo.getContactCorpFax(); // 联系人公司传真
				item.ContactCorpName = contactInfo.getContactEmpName(); // 联系人公司名称
				item.ContactGender = AppCommonUtil.checkType(contactInfo.getContactGender(),null); // 联系人性别
				item.ContactMobileNo = contactInfo.getContactMobile(); // 联系人手机
			}
			//申请主卡-第二联系人
			if(appPrimContactInfoMap!=null && appPrimContactInfoMap.containsKey("mconItemInfo2")){
				TmAppContact contactOInfo = appPrimContactInfoMap.get("mconItemInfo2");
				item.ContactOName = contactOInfo.getContactName(); // 其他联系人姓名（中文）
				item.ContactORelationship = AppCommonUtil.checkType(contactOInfo.getContactRelation(),null); // 其他联系人与申请人关系
				item.ContactOIdType = AppCommonUtil.checkType(contactOInfo.getContactIdType(),null); // 其他联系人身份证件名称
				item.ContactOIdNo = contactOInfo.getContactIdNo(); // 其他联系人证件号码
				item.ContactOCorpPhone = contactOInfo.getContactEmpPhone(); // 其他联系人公司电话
				item.ContactOCorpPost = AppCommonUtil.checkType(contactOInfo.getContactCorpPost(),null); // 其他联系人公司职务
				item.ContactOCorpName = contactOInfo.getContactEmpName(); // 其他联系人公司名称
				item.ContactOMobileNo = contactOInfo.getContactMobile(); // 其他联系人手机
			}
			//申请卡片信息
			item.CardFetchMethod = AppCommonUtil.checkType(appPrimCardInfo.getCardFetchMethod(),CardFetchMethod.A.name()); // 卡片寄送方式
			item.FetchBranch = appPrimCardInfo.getFetchBranch();//领卡网点
			item.StmtMediaType = AppCommonUtil.checkType(appPrimCardInfo.getStmtMediaType(),StmtMediaType.B.name()); // 账单介质类型标志
			item.StmtMailAddrInd = AppCommonUtil.checkType(appPrimCardInfo.getStmtMailAddrInd(),AddressType.H.name()); // 账单寄送标志
			item.DdOtherBankInd =AppCommonUtil.checkType(appPrimCardInfo.getDdBankAcctNoType(),Indicator.N.name()); // 约定还款账户类型,他行、本行
			item.DdInd = AppCommonUtil.checkType(appPrimCardInfo.getDdInd(),DdIndicator.N.name());// 约定还款类型
//			item.ddBankBranch = appPrimCardInfo.getDdBankBranch(); // 开户行所在地,开户行编号
			if(appPrimCardInfo.getDdBankBranch()!=null && appPrimCardInfo.getDdBankBranch().length()<=9){
				item.DdBankBranch = appPrimCardInfo.getDdBankBranch(); // 开户行所在地,开户行编号
			}
//			item.ddBankBranch2 = appPrimCardInfo.getDdBankBranch(); // 开户行编号
			item.DdBankAcctNo = appPrimCardInfo.getDdBankAcctNo(); // 约定还款扣款账号
			item.DdBankAcctName = appPrimCardInfo.getDdBankAcctName(); // 约定还款扣款账户姓名
			item.DdBankName = appPrimCardInfo.getDdBankName(); // 约定还款银行名称
			item.CardMailerInd  = AppCommonUtil.checkType(appPrimCardInfo.getCardMailerInd(),AddressType.H.name()); // 卡片寄送地址标志

			// 账单周期，不足两位补零
			String billingCycle = appPrimCardInfo.getBillingCycle();
			item.BillingCycle   = (billingCycle != null && billingCycle.trim().length() == 1) ?  "0"+billingCycle.trim() : billingCycle;

			//申请附件信息
			if (appPrimAnnexEvi != null) {
//				item.driveLicenseId = appPrimAnnexEvi.getDriveLicenseId(); // 驾驶证号码
//				item.driveLicRegDate = appPrimAnnexEvi.getDriveLicRegDate(); // 驾驶证登记日期
//				item.socialInsAmt = appPrimAnnexEvi.getSocialInsAmt(); // 社保缴存金额
//				item.socialStatus = AppCommonUtil.stringToEnum(SocialStatus.class ,appPrimAnnexEvi.getSocialStatus(),SocialStatus.N); // 风险情况

				item.DriveLicenseId = null; // 驾驶证号码
				item.DriveLicRegDate = null; // 驾驶证登记日期
				item.SocialInsAmt = null; // 社保缴存金额
				item.SocialStatus = SocialStatus.N.name(); // 风险情况
			}

			item.SpecLoanInd = Indicator.N.state;
			item.Guaranty = Indicator.N.state;
//			if(StringUtils.equals(appAudit.getIsInstalment(), "Y")){
//				item.SpecLoanInd = Indicator.Y.state;//大额分期申请
//				item.SpecLoanType = LoanType.K.name();//大额分期申请类型 目前为K,标准大额分期
//				item.LoanMerchantId = appInstalCredit.getMccNo();//商户号
//				item.LoanProgramId = appInstalCredit.getInstalActivityNo();//活动号
//				item.MarketerName = appInstalCredit.getMarketerName();//营销人员姓名
//				item.MarketerId = appInstalCredit.getMarketerId();//id
//				item.MarketerBranchId = appInstalCredit.getMarketerBranch();//分支行好
////				item.loanPrinApp = appInstalLoan.getLoanUse();//贷款用途 先不填
////				if(Indicator.Y.name().equals(appInstalLoan.getGuaranty())){//是否抵押 先不填
////					item.guaranty = Indicator.Y;
////				}else if(Indicator.N.name().equals(appInstalLoan.getGuaranty())){
////					item.guaranty = Indicator.N;
////				}
//				item.LoanInitTerm = appInstalCredit.getLoanInitTerm();//大额分期期数
//				item.LoanInitPrin = appInstalCredit.getCashAmt();//额度外分期金额
//				if(LoanFeeMethod.F.name().equals(appInstalCredit.getLoanFeeMethod())){//分期手续分收取方式
//					item.LoanFeeMethod = LoanFeeMethod.F.name();
//				}else if(LoanFeeMethod.E.name().equals(appInstalCredit.getLoanFeeMethod())){
//					item.LoanFeeMethod = LoanFeeMethod.E.name();
//				}
////				item.distributeMethod = appInstalLoan.getDistributeMethod();//分期本金分配方式 此项为非必输项，申请分期时，如果此项未输入，取分期产品参数中对应期数的[本金分配方式]
//				item.LoanFeeCalcMethod = appInstalCredit.getLoanFeeCalcMethod();//大额分期分期手续费计算方式
//				item.FeeRate = appInstalCredit.getAppFeeRate();//大额分期分期手续费金额
//				item.FeeAmt = appInstalCredit.getAppFeeAmt();//大额分期分期手续费金额
////				item.rlBankName = appInstalLoan.getDdBankName();//放款银行名称 ps:标准大额分期不启用
////				item.rlBankBranch = appInstalLoan.getDdBankBranch();//放款银行名称 ps:标准大额分期不启用
////				item.rlBankAcctNo = appInstalLoan.getDdBankAcctNo();//放款账号 ps:标准大额分期不启用
////				item.rlBankAcctName = appInstalLoan.getDdBankAcctName();//放款账户姓名 ps:标准大额分期不启用
//				item.CustLargeSpecLimit = appInstalCredit.getCustSpecialQuota(); //客户级专项额度
//				if(item.CustLargeSpecLimit == null)
//					item.CustLargeSpecLimit = appInstalCredit.getCashAmt();
//				if(LendingType.B.name().equals(product.getLendingMethod())){
//					logger.info("申请件[{}]申请分期，放款方式上送B-批量放款，所选产品：{}，产品配置放款方式：{}",appMain.getAppNo(),product.getProductCd(),product.getLendingMethod());
//					item.LargeLoanSendMode = LendingType.B.state;
//				}else {
//					logger.info("申请件[{}]申请分期，放款方式上送O-实时放款，所选产品：{}，产品配置放款方式：{}",appMain.getAppNo(),product.getProductCd(),product.getLendingMethod());
//					item.LargeLoanSendMode = LendingType.O.state;
//				}
//				logger.info("申请件[{}]分期申请上送参数放款方式：{}",appMain.getAppNo(),item.LargeLoanSendMode);
////				item.largeLoanSendMode = appInstalLoan.getSemdMode();//放款方式
//			}
		}

		//若附卡
		if (bscSuppIndicator == BscSuppIndicator.S)
		{
			//申请附卡人信息
			item.CardNo = attachCust.getCardNo();
			if(StringUtils.isEmpty(attachCust.getBankCustomerId())) {
				throw new ProcessException("["+attachCust.getName()+"]客户号不能为空!");
			}
			item.BankCustomerId = attachCust.getBankCustomerId(); // 行内客户号
			item.RelationshipToBsc = AppCommonUtil.checkType(attachCust.getRelationshipToBsc(),null); // 与主卡持卡人关系
			item.PosPinVerifyInd = AppCommonUtil.checkType(attachCust.getPosPinVerifyInd(),Indicator.Y.state); // 消费凭密选择
			item.PhotoUseFlag = AppCommonUtil.checkType(attachCust.getPhotoUseFlag(),Indicator.Y.state); // 彩照卡标志(现不用)
			item.BankMemberNo = attachCust.getBankmemberNo(); // 本行员工编号
			/*item.bankmemFlag =appAttachApplierInfo.getBankmemFlag();//是否行内员工
			item.motherLogo=appAttachApplierInfo.getMotherLogo();//母亲姓氏
			item.raiseNum=appAttachApplierInfo.getRaiseNum();//抚养人数
			item.jobGrade=appAttachApplierInfo.getJobGrade();//职位等级
			item.oldCorpName=appAttachApplierInfo.getOldCorpName();//前一单位名称
			item.oldCorpProveName=appAttachApplierInfo.getOldCorpProveName();//前一单位证明人
			item.oldCorpProveCnt=appAttachApplierInfo.getOldCorpProveCnt();//前一单位证明人联系方式
*/			item.Name = attachCust.getName(); // 姓名（中文）
			item.Gender = AppCommonUtil.checkType(attachCust.getGender(),null); // 性别
			item.Birthday = DateUtils.dateToString(attachCust.getBirthday() ,DateUtils.DAY_YMD); // 出生日期
			item.MaritalStatus = AppCommonUtil.checkType(attachCust.getMaritalStatus(),null); // 婚姻状况
			item.Nationality = attachCust.getNationality(); // 国籍
			item.IdType = AppCommonUtil.checkType(attachCust.getIdType(),null); // 证件类型
			item.IdNo = attachCust.getIdNo(); // 证件号码
			item.Qualification = AppCommonUtil.checkType(attachCust.getQualification(),null); // 教育情况
			item.Degree = AppCommonUtil.checkType(attachCust.getDegree(),null); //学位
			item.QUicsTxnInd = AppCommonUtil.checkType(attachCust.getSmAmtVerifyInd(),Indicator.N.state);//是否支持小额免密免签
			// 现住址居住起始年月
			item.HomeStandFrom = null;
//			try {
//				item.homeStandFrom = DateUtils.stringToDate(attachCust.getHomeStandFrom(), DateUtils.DAY_YMD);
//			} catch (ParseException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
			item.MobileNo = attachCust.getCellphone(); // 移动电话号码
			item.Email = attachCust.getEmail(); // 电子信箱
			item.HomeCountryCd = StringUtils.setValue(attachCust.getHomeAddrCtryCd(),"156"); // 家庭国家代码
			item.HomeState = attachCust.getHomeState(); // 家庭省份
			item.HomeCity = attachCust.getHomeCity(); // 家庭城市
			item.HomeDistrict = attachCust.getHomeZone(); // 家庭行政区
			item.HomeAddress = attachCust.getHomeAdd(); // 现住址
			item.HomeZip = attachCust.getHomePostcode(); // 现住址邮编
			item.HomePhone = attachCust.getHomePhone(); // 现住址电话号码

			item.CompanyName = StringUtils.setValue(attachCust.getCorpName(),primCust.getCorpName()); // 单位全称
			item.CompanyAddress = StringUtils.setValue(attachCust.getEmpAdd(),primCust.getEmpAdd()); // 单位地址
			item.CompanyZip = StringUtils.setValue(attachCust.getEmpPostcode(),primCust.getEmpPostcode()); // 单位邮编
			item.CompanyPhone = StringUtils.setValue(attachCust.getEmpPhone(),primCust.getEmpPhone()); // 单位电话号码
			item.CompanyCategory = AppCommonUtil.checkType(attachCust.getEmpStructure(),null); // 单位性质
			item.IndustryCategory = AppCommonUtil.checkType(attachCust.getEmpType(),null); // 行业类别
			item.Occupation = AppCommonUtil.checkType(attachCust.getOccupation(),null); // 职业
			item.TitleOfTechnical = AppCommonUtil.checkType(attachCust.getTitleOfTechnical(),null); // 职称
			item.Title = AppCommonUtil.checkType(attachCust.getEmpPost(),null); // 职务
			BigDecimal yearIncome = attachCust.getYearIncome();
			//如果年收入填写的小于1000，则乘以10000（因为当前系统以万元为单位的）
			if(yearIncome!=null && yearIncome.compareTo(new BigDecimal(1000))<0) {
				if(yearIncome.compareTo(new BigDecimal(1000))<0) {
					yearIncome  = yearIncome.multiply(new BigDecimal(10000));
				}
				yearIncome = yearIncome.setScale(2,BigDecimal.ROUND_HALF_UP);//四舍五入
			}
			item.RevenuePerYear = yearIncome; // 个人年收入
			item.FamilyAverageRevenue = null; // 家庭人均年收入
			item.ObligateQuestion = attachCust.getOtherAsk(); // 预留问题
			item.ObligateAnswer = attachCust.getOtherAnswer(); // 预留答案
			item.EmbName = attachCust.getEmbLogo().toUpperCase(); // 凸印名-姓名拼音
			item.CompanyCountryCd = StringUtils.setValue(attachCust.getEmpAddrCtryCd(), "156"); // 公司国家代码
			item.CompanyState = StringUtils.setValue(attachCust.getEmpProvince(),primCust.getEmpProvince()); // 公司省份
			item.CompanyCity = StringUtils.setValue(attachCust.getEmpCity(),primCust.getEmpCity()); // 公司城市
			item.CompanyDistrict = StringUtils.setValue(attachCust.getEmpZone(),primCust.getEmpZone()); // 公司行政区
//			item.empStability = AppCommonUtil.stringToEnum(EmpStability.class ,attachCust.getEmpStability(),EmpStability.B); // 工作稳定性
//			item.empStatus = AppCommonUtil.stringToEnum(Indicator.class ,attachCust.getEmpStatus(),Indicator.Y); // 就业状态
			item.EmpStability = "B"; // 工作稳定性
			item.EmpStatus = Indicator.Y.state; // 就业状态
			item.IdIssuerAddress = attachCust.getIdIssuerAddress(); // 发证机关所在地址
		
//			item.idLastaLL = appPrimApplicantInfo.getIdLastAll();//证件是否长期有效
			item.PrOfCountry = Indicator.Y.state; // 是否永久居住
			item.ResidencyCountryCd = StringUtils.setValue(attachCust.getResidencyCountryCd(), "156"); // 永久居住地国家代码
			item.CompanyFax  = null; // 公司传真
			item.CardFetchMethod = AppCommonUtil.checkType(appPrimCardInfo.getCardFetchMethod(),CardFetchMethod.A.name()); // 卡片寄送方式
			item.FetchBranch = appPrimCardInfo.getFetchBranch();//领卡网点
			item.CardMailerInd  = AppCommonUtil.checkType(appPrimCardInfo.getCardMailerInd(),AddressType.H.name()); // 卡片寄送地址标志
			item.EmpDepartment = attachCust.getEmpDepartment(); // 任职部门
			//现工作起始年月
			item.CorpBegDate = null;
//			//现单位工作起始年
//			try {
//				item.corpBegDate = DateUtils.stringToDate(attachCust.getEmpStandFrom(), DateUtils.DAY_YMD);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			//独立附卡
			if(appMain.getAppType()!=null && appMain.getAppType().equals(AppType.S.name())){
				item.PrimCardNo = attachCust.getPrimCardNo(); //对应主卡介质卡号
			}
			
			item.EcifNo = attachCust.getEcifCustomerCode();//ecif客户编号
//			String attachNo = Integer.toString(appAttachApplierInfo.getAttachNo());
//			if(tmAppExtend2s!=null&&tmAppExtend2s.containsKey(attachNo)&&tmAppExtend2s.get(attachNo)!=null){
//				item.ecifNo = tmAppExtend2s.get(attachNo).getEcifCustomerCode();
//			}
			
			//主附同申，附卡寄送地址，如未填写，上送主卡申请人公司或家庭地址
			if(primCust != null){
				if(StringUtils.isBlank(item.HomeAddress)){
					item.HomeState = primCust.getHomeState(); // 家庭省份
					item.HomeCity = primCust.getHomeCity(); // 家庭城市
					item.HomeDistrict = primCust.getHomeZone(); // 家庭行政区
					item.HomeAddress = primCust.getHomeAdd(); // 现住址
					item.HomeZip = primCust.getHomePostcode(); // 现住址邮编
				}
				if(StringUtils.isBlank(item.CompanyAddress)){
					item.CompanyName = primCust.getCorpName(); // 单位全称
					item.CompanyAddress = primCust.getEmpAdd(); // 单位地址
					item.CompanyZip = primCust.getEmpPostcode(); // 单位邮编
					item.CompanyState = primCust.getEmpProvince(); // 公司省份
					item.CompanyCity = primCust.getEmpCity(); // 公司城市
					item.CompanyDistrict = primCust.getEmpZone(); // 公司行政区
				}
			}
		}
		
		return item;
	}
	
	/**
	 * 获取卡面数据
	 * @param appMain
//	 * @param faceMap
//	 * @param attachNo
//	 * @param bscSupInd
//	 * @param product
//	 * @return
	 */
	@Transactional
	public TmAppPrimCardInfo fillCardFace(TmAppMain appMain, TmAppPrimCardInfo cardInfo) {
		
		List<String> list = getCardFace(appMain.getProductCd());
		if(list == null || list.size() == 0){
			logger.error("系统未查询到申请件["+appMain.getAppNo()+"]设产品["+appMain.getProductCd()+"]的卡面信息");
			throw new ProcessException("系统未查询到产品["+appMain.getProductCd()+"]设置的卡面信息,请重新登录后并重试!");
		}
		if(StringUtils.isNotEmpty(cardInfo.getCardfaceCd()) 
				&& list.contains(cardInfo.getCardfaceCd())){
			return cardInfo; 
		}
		String cardFace = null;
		for (int i = 0; i < list.size(); i++) {
			String s = list.get(i);
			if(StringUtils.isNotEmpty(s)){
				cardFace = s;
			}
		}
		cardInfo.setCardfaceCd(cardFace);
		tmAppPrimCardInfoDao.update(cardInfo);
		
		return cardInfo;
	}
	
	/**
	 * 获取卡产品对应卡面信息
	 */
	private List<String> getCardFace(String productCd) {
		LinkedHashMap<Object, Object> cardFaceMap = cacheContext.getSimpleProductCardFaceLinkedMap(productCd);
		if(cardFaceMap==null || cardFaceMap.isEmpty()){
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (Object obj : cardFaceMap.keySet()) {
			list.add(StringUtils.valueOf(obj));
		}
		return list;
	}
}
