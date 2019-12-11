package com.jjb.acl.facility.enums.bus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import com.jjb.unicorn.facility.cstruct.CChar;
import com.jjb.unicorn.facility.exception.CException;
/**
 *@ClassName ApplyFileItem
 *@Description 成功申请送综合前置实时建账制item数据传输类
 *@Author lixing
 *Date 2018/10/24 9:34
 *Version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ApplyFileItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主附卡指示
	 */
	@CChar( value = 1, order = 200 )
	@CException( value = "R019")
	public String BscSuppInd;

	/**
	 * 产品代码
	 */
	@CChar( value = 6, order = 300 )
	@CException( value = "R271")
	public String ProductCd;

	/**
	 * 介质卡号
	 */
	@CChar( value = 19, order = 400 )
	@CException( value = "R272")
	public String CardNo;

	/**
	 * 对应主卡介质卡号
	 */
	@CChar( value = 19, order = 500 )
	@CException( value = "R273")
	public String PrimCardNo;

	/**
	 * 对应主卡逻辑卡号
	 */
	@CChar( value = 19, order = 600 )
	@CException( value = "R274")
	public String BscLogicalCardNo;

	/**
	 * 申请信息类型
	 */
	@CChar( value = 1, order = 700 )
	@CException( value = "R020")
	public String AppType;

	/**
	 * 申请件编号
	 */
	@CChar( value = 20, autoTrim = true, order = 800 )
	@CException( value = "R275")
	public String AppNo;

	/**
	 * 对应主卡申请记录编号
	 */
	@CChar( value = 20, autoTrim = true, order = 900 )
	@CException( value = "R276")
	public String PrimAppId;

	/**
	 * 消费凭密选择
	 */
	@CChar( value = 1, required = true, order = 1000 )
	@CException( value = "R021")
	public String PosPinVerifyInd;

	/**
	 * 彩照卡标志
	 */
	@CChar( value = 1, order = 1100 )
	@CException( value = "R022")
	public String PhotoUseFlag;

	/**
	 * 本行员工编号
	 */
	@CChar( value = 20, autoTrim = true, order = 1200 )
	@CException( value = "R277")
//	public String bankmemberNo;
	public String BankMemberNo;

	/**
	 * 姓名（中文）
	 */
	@CChar( value = 80, autoTrim = true, order = 1300 )
	@CException( value = "R278")
	public String Name;

	/**
	 * 性别
	 */
	@CChar( value = 1, order = 1400 )
	@CException( value = "R023")
	public String Gender;

	/**
	 * 出生日期
	 */
	//@CChar( value = 8, datePattern = "yyyyMMdd", order = 1500 )
	@CChar( value = 8, order = 1500 )
	@CException(value = "R087")
	public String Birthday;

	/**
	 * 婚姻状况
	 */
	@CChar( value = 1, order = 1600 )
	@CException( value = "R024")
	public String MaritalStatus;

	/**
	 * 国籍
	 */
	@CChar( value = 3, order = 1700 )
	@CException( value = "R279")
	public String Nationality;

	/**
	 * 家庭人口
	 */
	@CChar( value = 2, zeroPadding = true, order = 1800 )
	@CException(value = "R088")
	public Integer  NbrOfDependents;

	/**
	 * 证件类型
	 */
	@CChar( value = 1, order = 1900 )
	@CException( value = "R025")
	public String IdType;

	/**
	 * 证件号码
	 */
	@CChar( value = 30, autoTrim = true, order = 2000 )
	@CException( value = "R280")
	public String IdNo;

	/**
	 * 教育程度
	 */
	@CChar( value = 1, order = 2100 )
	@CException( value = "R026")
	public String Qualification;

	/**
	 * 现住址
	 */
	@CChar( value = 200, autoTrim = true, order = 2200 )
	@CException( value = "R281")
	public String HomeAddress;

	/**
	 * 现住址邮编
	 */
	@CChar( value = 10, autoTrim = true, order = 2300 )
	@CException( value = "R282")
	public String HomeZip;

	/**
	 * 现住址居住起始年月
	 */
//	@CChar( value = 8, datePattern = "yyyyMMdd", order = 2400 )
	@CChar( value = 8, order = 2400 )
	@CException(value = "R089")
	public String HomeStandFrom;

	/**
	 * 现住址电话号码
	 */
	@CChar( value = 15, autoTrim = true, order = 2500 )
	@CException( value = "R283")
	public String HomePhone;

	/**
	 * 移动电话号码
	 */
	@CChar( value = 20, autoTrim = true, order = 2600 )
	@CException( value = "R284")
	public String MobileNo;

	/**
	 * 电子信箱
	 */
	@CChar( value = 80, autoTrim = true, order = 2700 )
	@CException( value = "R285")
	public String Email;

	/**
	 * 住宅状况
	 */
	@CChar( value = 1, order = 2800 )
	@CException( value = "R027")
	public String HouseOwnership;

	/**
	 * 是否有按揭贷款
	 */
	@CChar( value = 1, order = 2900 )
	@CException( value = "R028")
	public String HouseLoanFlag;

	/**
	 * 单位全称
	 */
	@CChar( value = 80, autoTrim = true, order = 3000 )
	@CException( value = "R286")
	public String CompanyName;

	/**
	 * 单位地址
	 */
	@CChar( value = 200, autoTrim = true, order = 3100 )
	@CException( value = "R287")
	public String CompanyAddress;

	/**
	 * 单位邮编
	 */
	@CChar( value = 10, autoTrim = true, order = 3200 )
	@CException( value = "R288")
	public String CompanyZip;

	/**
	 * 单位电话号码
	 */
	@CChar( value = 20, autoTrim = true, order = 3300 )
	@CException( value = "R289")
	public String CompanyPhone;

	/**
	 * 单位性质
	 */
	@CChar( value = 1, order = 3400 )
	@CException( value = "R029")
	public String CompanyCategory;

	/**
	 * 行业类别
	 */
	@CChar( value = 2, order = 3500 )
	@CException( value = "R030")
	public String IndustryCategory;

	/**
	 * 职业
	 */
	@CChar( value = 1, order = 3600 )
	@CException( value = "R031")
	public String Occupation;

	/**
	 * 职称
	 */
	@CChar( value = 1, order = 3700 )
	@CException( value = "R032")
	public String TitleOfTechnical;

	/**
	 * 职务
	 */
	@CChar( value = 1, order = 3800 )
	@CException( value = "R033")
	public String Title;

	/**
	 * 个人年收入
	 */
	@CChar( value = 12, precision = 2, zeroPadding = true, order = 3900 )
	@CException(value = "R090")
	public BigDecimal RevenuePerYear;

	/**
	 * 家庭人均年收入
	 */
	@CChar( value = 12, precision = 2, zeroPadding = true, order = 4000 )
	@CException(value = "R091")
	public BigDecimal  FamilyAverageRevenue;

	/**
	 * 自动还款方式
	 */
	@CChar( value = 1, order = 4100 )
	@CException( value = "R034")
	public String DdInd;

	/**
	 * 约定还款开户行号
	 */
	@CChar( value = 9, autoTrim = true, order = 4200 )
	@CException( value = "R290")
	public String DdBankBranch;

	/**
	 * 约定还款扣款账号
	 */
	@CChar( value = 40, autoTrim = true, order = 4300 )
	@CException( value = "R291")
	public String DdBankAcctNo;

	/**
	 * 卡片寄送方式
	 */
	@CChar( value = 1, order = 4400 )
	@CException( value = "R035")
	public String CardFetchMethod;

	/**
	 * 预留问题
	 */
	@CChar( value = 40, autoTrim = true, order = 4500 )
	@CException( value = "R292")
	public String ObligateQuestion;

	/**
	 * 预留答案
	 */
	@CChar( value = 40, autoTrim = true, order = 4600 )
	@CException( value = "R293")
	public String ObligateAnswer;

	/**
	 * 联系人姓名（中文）
	 */
	@CChar( value = 80, autoTrim = true, order = 4700 )
	@CException( value = "R294")
	public String ContactName;

	/**
	 * 联系人与申请人关系
	 */
	@CChar( value = 1, order = 4800 )
	@CException( value = "R036")
	public String ContactRelationship;

	/**
	 * 联系人身份证件名称
	 */
	@CChar( value = 1, order = 4900 )
	@CException( value = "R037")
	public String ContactIdType;

	/**
	 * 联系人证件号码
	 */
	@CChar( value = 30, autoTrim = true, order = 5000 )
	@CException( value = "R295")
	public String ContactIdNo;

	/**
	 * 联系人公司电话号码
	 */
	@CChar( value = 15, autoTrim = true, order = 5100 )
	@CException( value = "R296")
	public String ContactCorpPhone;

	/**
	 * 信用额度
	 */
	@CChar( value = 13, zeroPadding = true, order = 5200 )
	@CException(value = "R092")
	public BigDecimal CreditLimit;

	/**
	 * 公务卡额度
	 */
	@CChar( value = 13, zeroPadding = true, order = 5210 )
	@CException(value = "R093")
	public BigDecimal CivilServiceLimit;

	/**
	 * 小额贷款额度
	 */
	@CChar( value = 13, zeroPadding = true, order = 5220 )
	@CException(value = "R094")
	public BigDecimal MicroCreditLimit;

	/**
	 * 商务卡额度
	 */
	@CChar( value = 13, zeroPadding = true, order = 5225 )
	@CException(value = "R095")
	public BigDecimal BusinessLimit;

	/**
	 * 专项额度车贷
	 */
	@CChar( value = 13, zeroPadding = true, order = 5230 )
	@CException(value = "R096")
	public BigDecimal CarLoan;

	/**
	 * 专项额度房贷
	 */
	@CChar( value = 13, zeroPadding = true, order = 5240 )
	@CException(value = "R097")
	public BigDecimal HouseLoan;

	/**
	 * 专项额度装修贷
	 */
	@CChar( value = 13, zeroPadding = true, order = 5250 )
	@CException(value = "R098")
	public BigDecimal RenovationLoan;

	/**
	 * 专项额度旅游贷
	 */
	@CChar( value = 13, zeroPadding = true, order = 5260 )
	@CException(value = "R099")
	public BigDecimal TravelLoan;

	/**
	 * 专项额度婚庆贷
	 */
	@CChar( value = 13, zeroPadding = true, order = 5270 )
	@CException(value = "R100")
	public BigDecimal WeddingLoan;

	/**
	 * 专项额度耐消贷
	 */
	@CChar( value = 13, zeroPadding = true, order = 5280 )
	@CException(value = "R101")
	public BigDecimal DurableLoan;

	/**
	 * 周期限额
	 */
	@CChar( value = 13, zeroPadding = true,  order = 5300 )
	@CException(value="R102")
	public BigDecimal CardCycleLimit;

	/**
	 * 周期取现限额
	 */
	@CChar( value = 13, zeroPadding = true,  order = 5400 )
	@CException(value = "R103")
	public BigDecimal CardCycleCashLimit;

	/**
	 * 网银周期交易限额
	 */
	@CChar( value = 13, zeroPadding = true, order = 5500 )
	@CException(value = "R104")
	public BigDecimal CardCycleNetLimit;

	/**
	 * 单笔交易限额
	 */
	@CChar( value = 13, zeroPadding = true,  order = 5600 )
	@CException(value = "R105")
	public BigDecimal CardTxnLimit;

	/**
	 * 单笔取现交易限额
	 */
	@CChar( value = 13, zeroPadding = true, order = 5700 )
	@CException(value = "R106")
	public BigDecimal CardTxnCashLimit;

	/**
	 * 网银单笔交易限额
	 */
	@CChar( value = 13, zeroPadding = true,  order = 5800 )
	@CException(value = "R107")
	public BigDecimal CardTxnNetLimit;

	/**
	 * 账单介质类型标志
	 */
	@CChar( value = 1, order = 5900 )
	@CException( value = "R038")
	public String StmtMediaType;

	/**
	 * 账单寄送标志
	 */
	@CChar( value = 1, order = 6000 )
	@CException( value = "R039")
	public String StmtMailAddrInd;

	/**
	 * 版面代码
	 */
	@CChar( value = 10, autoTrim = true, order = 6100 )
	@CException( value = "R297")
	public String DesignCd;
	/**
	 * 联名卡会员编号
	 */
	@CChar( value = 80, autoTrim = true, order = 6200 )
	@CException( value = "R298")
	public String CobrandNo;
	/**
	 * 凸印名-姓名拼音
	 */
	@CChar( value = 26, autoTrim = true, order = 6300 )
	@CException( value = "R299")
	public String EmbName;

	/**
	 * 家庭国家代码
	 */
	@CChar( value = 3, order = 6400 )
	@CException( value = "R300")
	public String HomeCountryCd;

	/**
	 * 家庭省份
	 */
	@CChar( value = 40, autoTrim = true, order = 6500 )
	@CException( value = "R301")
	public String HomeState;

	/**
	 * 家庭城市
	 */
	@CChar( value = 40, autoTrim = true, order = 6600)
	@CException( value = "R302")
	public String HomeCity;

	/**
	 * 家庭行政区
	 */
	@CChar( value = 40, autoTrim = true, order = 6700 )
	@CException( value = "R303")
	public String HomeDistrict;

	/**
	 * 公司国家代码
	 */
	@CChar( value = 3, order = 6800 )
	@CException( value = "R304")
	public String CompanyCountryCd;

	/**
	 * 公司省份
	 */
	@CChar( value = 40, autoTrim = true, order = 6900 )
	@CException( value = "R305")
	public String CompanyState;

	/**
	 * 公司城市
	 */
	@CChar( value = 40, autoTrim = true, order = 7000 )
	@CException( value = "R306")
	public String CompanyCity;

	/**
	 * 公司行政区
	 */
	@CChar( value = 40, autoTrim = true, order = 7100 )
	@CException( value = "R307")
	public String CompanyDistrict;

	/**
	 * 联系人生日
	 */
//	@CChar( value = 8, datePattern = "yyyyMMdd", order = 7200 )
	@CChar( value = 8, order = 7200 )
	@CException(value="R108")
	public String ContactBirthday;

	/**
	 * 联系人公司职务
	 */
	@CChar( value = 1, order = 7300 )
	@CException( value = "R040")
	public String ContactCorpPost;

	/**
	 * 联系人公司传真
	 */
	@CChar( value = 20, autoTrim = true, order = 7400 )
	@CException( value = "R308")
	public String ContactCorpFax;

	/**
	 * 联系人公司名称
	 */
	@CChar( value = 80, autoTrim = true, order = 7500 )
	@CException( value = "R309")
	public String ContactCorpName;

	/**
	 * 联系人性别
	 */
	@CChar( value = 1, order = 7600 )
	@CException( value = "R041")
	public String ContactGender;

	/**
	 * 联系人手机
	 */
	@CChar( value = 20, autoTrim = true, order = 7700 )
	@CException( value = "R310")
	public String ContactMobileNo;

	/**
	 * 约定还款扣款账户姓名
	 */
	@CChar( value = 80, autoTrim = true, order = 7800 )
	@CException( value = "R311")
	public String DdBankAcctName;

	/**
	 * 约定还款银行名称
	 */
	@CChar( value = 40, autoTrim = true, order = 7900 )
	@CException( value = "R312")
	public String DdBankName;

	/**
	 * 发卡网点
	 */
	@CChar( value = 9, order = 8000 )
	@CException( value = "R313")
	public String OwningBranch;

	/**
	 * 与主卡持卡人关系
	 */
	@CChar( value = 1, autoTrim = true, order = 8100 )
	@CException( value = "R042")
	public String RelationshipToBsc;

	/**
	 * 促销码
	 */
	@CChar( value = 15, autoTrim = true, order = 8200 )
	@CException( value = "R314")
	public String AppPromotionCd;

	/**
	 * 申请来源
	 */
	@CChar( value = 20, autoTrim = true, order = 8300 )
	@CException( value = "R315")
	public String AppSource;

	/**
	 * 申请书条码
	 */
	@CChar( value = 40, autoTrim = true, order = 8400 )
	@CException( value = "R316")
	//public String barcode;
	public String BarCode;


	/**
	 * 推荐人介质卡号
	 */
	@CChar( value = 19, autoTrim = true, order = 8500 )
	@CException( value = "R317")
	public String RecomCardNo;

	/**
	 * 推荐人姓名
	 */
	@CChar( value = 80, autoTrim = true, order = 8600 )
	@CException( value = "R318")
	public String RecomName;

	/**
	 * 客户经理
	 */
	@CChar( value = 80, autoTrim = true, order = 8700 )
	@CException( value = "R319")
	public String RepresentName;

	/**
	 * 是否接受推广邮件
	 */
	@CChar( value = 1, required = true, order = 8800 )
	@CException( value = "R043")
	public String SalesInd;

	/**
	 * 驾驶证号码
	 */
	@CChar( value = 20, autoTrim = true, order = 8900 )
	@CException( value = "R320")
	public String DriveLicenseId;

	/**
	 * 驾驶证登记日期
	 */
	@CChar( value = 8, datePattern = "yyyyMMdd", order = 9000 )
	@CException(value = "R109")
	public Date DriveLicRegDate;

	/**
	 * 工作稳定性
	 */
	@CChar( value = 1, order = 9100 )
	@CException( value = "R044")
	public String EmpStability;

	/**
	 * 就业状态
	 */
	@CChar( value = 1, order = 9200 )
	@CException( value = "R045")
	public String EmpStatus;

	/**
	 * 住宅类型
	 */
	@CChar( value = 1, order = 9300 )
	@CException( value = "R046")
	public String HouseType;

	/**
	 * 发证机关所在地址
	 */
	@CChar( value = 200, autoTrim = true, order = 9400 )
	@CException( value = "R321")
	public String IdIssuerAddress;

	/**
	 * 语言代码
	 */
	@CChar( value = 4, autoTrim = true, order = 9500 )
	@CException( value = "R322")
	public String LanguageInd;

	/**
	 * 个人资产类型
	 */
	@CChar( value = 1, order = 9600 )
	@CException( value = "R047")
	public String LiquidAsset;

	/**
	 * 是否永久居住
	 */
	@CChar( value = 1, order = 9700 )
	@CException( value = "R048")
	public String PrOfCountry;

	/**
	 * 永久居住地国家代码
	 */
	@CChar( value = 3, order = 9800 )
	@CException( value = "R323")
	public String ResidencyCountryCd;

	/**
	 * 社保缴存金额
	 */
	@CChar( value = 15, precision = 2, zeroPadding = true, order = 9900 )
	@CException(value = "R110")
	public BigDecimal SocialInsAmt;

	/**
	 * 风险情况
	 */
	@CChar( value = 1, order = 10000 )
	@CException( value = "R049")
	public String SocialStatus;

	/**
	 * 公司传真
	 */
	@CChar( value = 20, autoTrim = true, order = 10100 )
	@CException( value = "R324")
	public String CompanyFax;

	/**
	 * 卡片寄送地址标志
	 */
	@CChar( value = 1, order = 10200 )
	@CException( value = "R050")
	public String CardMailerInd;

	/**
	 * 账单周期
	 */
	@CChar( value = 2, order = 10300 )
	@CException( value = "R325")
	public String BillingCycle;

	/**
	 * 卡片有效期
	 */
//	@CChar( value = 8, datePattern = "yyyyMMdd", order = 10400 )
	@CChar( value = 8, order = 10400 )
	@CException(value = "R111")
	public Date CardExpireDate;

	/**
	 * 行内客户号
	 */
	@CChar( value = 20, order = 10500 )
	@CException( value = "R326")
	public String BankCustomerId;

	/**
	 * 客户级利率
	 */
	@CChar( value = 5, precision = 2, zeroPadding = true, order = 10600 )
	@CException(value = "R112")
	public BigDecimal CustRateDiscount;

	/**
	 * 其他联系人姓名（中文）
	 */
	@CChar( value = 80, autoTrim = true, order = 10700 )
	@CException( value = "R327")
	public String ContactOName;

	/**
	 * 其他联系人与申请人关系
	 */
	@CChar( value = 1, order = 10800 )
	@CException( value = "R051")
	public String ContactORelationship;

	/**
	 * 其他联系人身份证件类型
	 */
	@CChar( value = 1, order = 10900 )
	@CException( value = "R052")
	public String ContactOIdType;

	/**
	 * 其他联系人证件号码
	 */
	@CChar( value = 30, autoTrim = true, order = 11000 )
	@CException( value = "R328")
	public String ContactOIdNo;

	/**
	 * 其他联系人手机
	 */
	@CChar( value = 20, autoTrim = true, order = 11100 )
	@CException( value = "R329")
	public String ContactOMobileNo;

	/**
	 * 其他联系人公司名称
	 */
	@CChar( value = 80, autoTrim = true, order = 11200 )
	@CException( value = "R330")
	public String ContactOCorpName;


	/**
	 * 其他联系人公司职务
	 */
	@CChar( value = 1, order = 11300 )
	@CException( value = "R053")
	public String ContactOCorpPost;

	/**
	 * 其他联系人公司电话号码
	 */
	@CChar( value = 15, autoTrim = true, order = 14000 )
	@CException( value = "R331")
	public String ContactOCorpPhone;

	/**
	 * 是否紧急制卡
	 */
	@CChar( value = 1, order = 14100 )
	@CException( value = "R054")
	public String IsUrgentCard;

	/**
	 * 任职部门
	 */
	@CChar( value = 80, order = 15000 )
	@CException( value = "R332")
	//public String empDepapment;
	public String EmpDepartment;

	/**
	 * 申请录入人员
	 */
	@CChar( value = 40, order = 16000 )
	@CException( value = "R333")
	public String CreateUser;


	/**
	 * 领卡网点
	 */
	@CChar( value = 9, order = 17000 )
	@CException( value = "R334")
	public String FetchBranch;

	/**
	 * 学位
	 */
	@CChar( value = 1, order = 18000 )
	@CException( value = "R055")
	public String Degree;

	/**
	 * 本单位起始年份
	 */
//	@CChar( value = 8, datePattern = "yyyyMMdd", order = 18500 )
	@CChar( value = 8, order = 18500 )
	@CException(value = "R113")
	public Date CorpBegDate;

	/**
	 * 统一客户编号
	 */
	@CChar( value = 20, order = 19000 )
	@CException( value = "R335")
	public String EcifNo;

	/**
	 * 小额免密免签约
	 */
	@CChar( value = 1, order = 19500 )
	@CException( value = "R056")
	public String QUicsTxnInd;

	/**
	 * 证件到期日
	 * yyyyMMdd
	 */
//	@CChar( value = 8, datePattern = "yyyyMMdd",  order = 20000 )
	@CChar( value = 8, order = 20000 )
	@CException( value = "R137")
	public String IdLastDate;
 

	/**
	 * 大额分期申请
	 */
	@CChar( value = 1, order = 27800 )
	@CException( value = "R130")
	public String SpecLoanInd;

	/**
	 * 大额分期期数
	 */
	@CChar( value = 3, zeroPadding = true, order = 27810 )
	@CException( value = "R131")
	public Integer LoanInitTerm;

	/**
	 * 额度外分期金额
	 */
	@CChar( value = 13, zeroPadding = true, order = 27820 )
	@CException( value = "R132")
	public BigDecimal LoanInitPrin;

	/**
	 * 大额分期手续费收取方式
	 */
	@CChar( value = 1 , order = 27830 )
	@CException( value = "R133")
	public String LoanFeeMethod;


	/**
	 * 大额分期本金分配方式
	 */
	@CChar( value = 1 , order = 27840 )
	@CException( value = "R336")
	public String DistributeMethod;

	/**
	 * 大额分期分期手续费计算方式
	 */
	@CChar( value = 1 , order = 27850 )
	@CException( value = "R337")
	public String LoanFeeCalcMethod;

	/**
	 * 大额分期分期手续费比例
	 */
	@CChar( value = 5, precision = 4, zeroPadding = true, order = 27860 )
	@CException( value = "R134")
	public BigDecimal FeeRate;

	/**
	 * 大额分期分期手续费金额
	 */
	@CChar( value = 5, precision = 2, zeroPadding = true, order = 27870 )
	@CException( value = "R135")
	//public BigDecimal feeAmount;
	public  BigDecimal  FeeAmt;

	/**
	 * 放款银行名称
	 */
	@CChar( value = 80, autoTrim = true, order = 27880 )
	@CException( value = "R338")
	public String RlBankName;

	/**
	 * 放款开户行号
	 */
	@CChar( value = 11, autoTrim = true, order = 27890 )
	@CException( value = "R339")
	public String RlBankBranch;

	/**
	 * 放款账号
	 */
	@CChar( value = 40, autoTrim = true, order = 27891 )
	@CException( value = "R340")
	public String RlBankAcctNo;

	/**
	 * 放款账户姓名
	 */
	@CChar( value = 80, autoTrim = true, order = 27892 )
	@CException( value = "R341")
	public String RlBankAcctName;


	/**
	 * 放款账户对公/对私标识
	 */
	@CChar( value = 2, autoTrim = true, order = 27905 )
	@CException( value = "R356")
	public String LoanTarget;


	/**
	 * 放款方式
	 */
	@CChar( value = 1 , order = 27894 )
	@CException( value = "R342")
	public String LargeLoanSendMode;


	/**
	 * 约定还款他行代扣标识
	 */
	@CChar( value = 1, order = 27895 )
	@CException( value = "R345")
	public String DdOtherBankInd;
	/**
	 * 客户级专项额度
	 */
	@CChar( value = 13, zeroPadding = true, order = 27893 )
	@CException( value = "R136")
	public BigDecimal CustLargeSpecLimit;

	//新增  新征审需求
	/**
	 * 大额分期申请类型
	 */
	@CChar( value = 1, order = 27896)
	@CException( value = "R347")
	public String SpecLoanType;

	/**
	 * 分期商户号
	 */
	@CChar( value = 20, order = 27897)
	@CException( value = "R348")
	public String LoanMerchantId;

	/**
	 * 分期活动号
	 */
	@CChar( value = 20, order = 27898)
	@CException( value = "R349")
	public String LoanProgramId;

	/**
	 * 营销人员姓名
	 */
	@CChar( value = 80, order = 27899)
	@CException( value = "R350")
	public String MarketerName;

	/**
	 * 营销人员编号
	 */
	@CChar( value = 20, order = 27900)
	@CException( value = "R351")
	public String MarketerId;

	/**
	 * 营销人员所属分行
	 */
	@CChar( value = 9, order = 27901)
	@CException( value = "R352")
	public String MarketerBranchId;

	/**
	 * 分期款项用途
	 */
	@CChar( value = 8, order = 27902)
	@CException( value = "R353")
	public String LoanPrinApp;

	/**
	 * 是否抵押
	 */
	@CChar( value = 1, order = 27903)
	@CException( value = "R354")
	public String Guaranty;

	/**
	 * 申请来源-交易报文头使用，非业务字段
	 */
	@CChar( value = 2, order = 27904)
	@CException( value = "R356")
	public String ChannelId;


}
