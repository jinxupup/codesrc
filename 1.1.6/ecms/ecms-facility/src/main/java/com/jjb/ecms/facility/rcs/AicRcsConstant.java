package com.jjb.ecms.facility.rcs;

/**
 * 调用风控输入变量的名称常量定义
 * @Description: 
 * @author JYData-R&D-HN
 * @date 2018年1月26日 下午7:57:18
 * @version V1.0
 */
public class AicRcsConstant {

	/**
	 * 输入
	 */
	//******************输入变量**************start//
	public static String appId = "appId";// 申请件信息-申请编号
	public static String appSource = "appSource";// 申请件信息-申请渠道
	public static String org = "org";// 申请件信息-机构号
	public static String isVirtualCard = "isVirtualCard";// 申请件信息-是否虚拟卡
	public static String productCd = "productCd";// 申请件信息-产品编号
	public static String custName = "custName";// 申请人信息-客户姓名
	public static String age = "age";// 申请人信息-年龄
	public static String gender = "gender";// 申请人信息-性别
	public static String maritalStatus = "maritalStatus";// 申请人信息-婚姻状况
	public static String isBankMem = "isBankMem";// 申请人信息-是否行内员工
	public static String phoneNo = "phoneNo";// 申请人信息-手机号
	public static String idType = "idType";// 申请人信息-证件类型
	public static String idNo = "idNo";// 申请人信息-证件号码
	public static String idFirst2Digits = "idFirst2Digits";// 申请人信息-证件号前两位数字
	public static String empProvince = "empProvince";// 申请人信息-公司所在省
	public static String educationType = "educationType";// 申请人信息-教育状况
	public static String isAttachedCard = "isAttachedCard";// 申请人信息-是否附卡申请人
	public static String yearIncome = "yearIncome";// 申请人信息-年收入
	public static String ifCallPBOC = "ifCallPBOC";// GPS控制输入-是否调用人行征信
	public static String idValidatyDaysRemaining = "idValidatyDaysRemaining";// GPS准入输入-证件有效期剩余天数
	public static String ifIdTooManyApplications = "ifIdTooManyApplications";// GPS准入输入-证件号码申请次数过多
	public static String ifMobileTooManyApplications = "ifMobileTooManyApplications";// GPS准入输入-手机号码申请次数过多
	public static String ifIdTooManyAppDiffNames = "ifIdTooManyAppDiffNames";// GPS准入输入-同一证件号码多次不同姓名申请
	public static String ifIdTooManyFails = "ifIdTooManyFails";// GPS准入输入-同一证件号码失败次数过多
	public static String ifExistInAps = "ifExistInAps";// GPS准入输入-申请卡产品在信审系统中是否存在
	public static String ifExistInCps = "ifExistInCps";// GPS准入输入-卡产品在发卡系统中是否重复申请
	public static String ifApplyAttachOnly = "ifApplyAttachOnly";// GPS准入输入-是否申请独立附卡
	public static String ifAttachToPrimMcard = "ifAttachToPrimMcard";// GPS准入输入-附卡是否有对应主卡
	public static String attachToPrimMcardStatus = "attachToPrimMcardStatus";// GPS准入输入-附属卡对应主卡状态
	public static String acctBlockCode = "acctBlockCode";// GPS准入输入-已申请卡片账户锁定码
	public static String cardBlockCode = "cardBlockCode";// GPS准入输入-已申请卡片锁定码
	public static String ifOneCellPhoneToNapply = "ifOneCellPhoneToNapply";// GPS反欺诈输入-申请人手机号码是否对应多个申请
	public static String ifOneIdToNname = "ifOneIdToNname";// GPS反欺诈输入-申请人证件是否对应多个姓名
	public static String ifOneHomePhoneToNapply = "ifOneHomePhoneToNapply";// GPS反欺诈输入-申请人住宅电话是否对应多个申请人
	public static String applyNum180Day = "applyNum180Day";// GPS反欺诈输入-半年申请次数
	public static String applyFailNum180Day = "applyFailNum180Day";// GPS反欺诈输入-半年内失败申请次数
	public static String pBlackList = "pBlackList";// GPS反欺诈输入-怀疑个人黑名单
	public static String ifCellPhoneBlackList = "ifCellPhoneBlackList";// GPS反欺诈输入-怀疑个人移动电话黑名单
	public static String ifpHomePhoneBlackList = "ifpHomePhoneBlackList";// GPS反欺诈输入-怀疑个人家庭电话黑名单
	public static String ifpHomeAddrBlackList = "ifpHomeAddrBlackList";// GPS反欺诈输入-怀疑个人家庭地址黑名单
	public static String ifpEmpPhoneBlackList = "ifpEmpPhoneBlackList";// GPS反欺诈输入-怀疑个人所在公司电话黑名单
	public static String ifpEmpAddrBlackList = "ifpEmpAddrBlackList";// GPS反欺诈输入-怀疑个人所在公司名称黑名单
	public static String isCorpAddBl = "isCorpAddBl";// GPS反欺诈输入-怀疑个人所在公司地址黑名单
	public static String ifOneHomeAddToApp = "ifOneHomeAddToApp";// GPS反欺诈输入-申请人家庭地址多人匹配（申请人的家庭地址对应多个申请人的家庭地址）
	public static String ifOneCorpAddToApp = "ifOneCorpAddToApp";// GPS反欺诈输入-申请人单位地址多人匹配（申请人的单位地址对应多个申请人的单位地址）
	public static String ifOneContactsToApp = "ifOneContactsToApp";// GPS反欺诈输入-申请人联系人多人匹配（申请人的联系人（包括直系和其他联系人）对应多个申请人的联系人）（姓名和联系电话同时匹配）
	public static String ifOneCorpPhoneToCorpName = "ifOneCorpPhoneToCorpName";// GPS反欺诈输入-同一单位电话对应多个单位名称
	public static String ifSameEmpAddAndHomeAdd = "ifSameEmpAddAndHomeAdd";// GPS反欺诈输入-申请件单位地址填写与住宅地址一致
	public static String ifOneEmailToApp = "ifOneEmailToApp";// GPS反欺诈输入-申请人邮箱多人匹配
	public static String ifOneHomePhoneToNEmpPhone = "ifOneHomePhoneToNEmpPhone";// GPS反欺诈输入-申请人住宅电话对单位电话多人匹配
	public static String ifOneContMobileToNContName = "ifOneContMobileToNContName";// GPS反欺诈输入-申请人联系人姓名多人匹配
	public static String ifOneMailerIndToApp = "ifOneMailerIndToApp";// GPS反欺诈输入-申请人邮寄住宅地址（不为空）多人匹配
	public static String ifpEmailBlackList = "ifpEmailBlackList";// GPS反欺诈输入-怀疑电子邮箱黑名单
	public static String ifManualAntiFraud = "ifManualAntiFraud";// GPS人工检核-已进行人工反欺诈检核
	public static String manualAntiFraudCode = "manualAntiFraudCode";// GPS人工检核-人工反欺诈检验结果编码
	//******************输入变量***************end//
	
	
	/**
	 * 输出
	 */
	//******************输出变量**************start//
	public static String content  ="content ";//自动审批结果 -自动通过 DC:自动拒绝 MF:转人工欺诈调查 RF:转人工审核
	public static String unapprovedReasonCode  ="unapprovedReasonCode ";//未通过原因码 -
	public static String unapprovedReasonDesc  ="unapprovedReasonDesc ";//未通过原因描述 -
	public static String admissionFailReasonCode ="admissionFailReasonCode ";//准入失败原因码表 -
	public static String admissionFailReasonDesc ="admissionFailReasonDesc ";//准入失败原因描述表-
	public static String fraudReasonCode  ="fraudReasonCode ";//欺诈原因编码表 -
	public static String fraudReasonDesc  ="fraudReasonDesc ";//欺诈原因详情表 -
	public static String creditReasonCode  ="creditReasonCode ";//信用规则原因码表 -益博睿
	public static String creditReasonDesc  ="creditReasonDesc ";//信用规则原因描述表-益博睿
	public static String creditScore  ="creditScore ";//信用分 -
	public static String creditScoreLevel  ="creditScoreLevel ";//评分等级 -评分的细项
	public static String scoreElement  ="scoreElement ";//评分项 -细项的取值
	public static String scoreOutcomde  ="scoreOutcomde ";//评分项取值 -细项的得分
	public static String scoreValue  ="scoreValue ";//评分项得分 -
	public static String suggestAmt  ="suggestAmt ";//建议额度 -
	//******************输出变量**************end//
	
}
