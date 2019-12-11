package com.jjb.ecms.constant;

/**
 * @Description: 征审用常量值
 * @author JYData-R&D-HN
 * @date 2016年9月1日 上午11:43:00
 * @version V1.0
 */
public class AppConstant {

	// 公共log信息
	public static final String ZERO = "0"; // 0：直接来自九江返回.客户是否被人工信审拒绝；默认：0-否
	public static final String ONE = "1"; // 1：(行内初审)审批结论推送 or 1:是  or 1:通过
	public static final String TWO = "2"; // 2：(行内)终审结论推送  or 2:拒绝
	public static final String THREE = "3"; // 3：复议转人工
	public static final String FIFTY = "50"; // 默认查询排行榜记录数
	public static final String BEGINING = "_begin..."; // 开始
	public static final String END = "_end..."; // 结束
	public static final String D_NATIONALITY = "156"; // 默认国籍=中国
	public static final String MTAB = "MTAB";// 主卡前缀
//	public static final String MAIN_EXTEND = "mainExtend";// 主卡信息扩展表
//	public static final String ATTACH_EXTEND = "attachExtend";// 附卡信息扩展表
//	public static final String ATTATCH_TABS = "attatchTabs"; // 附卡前缀
//	public static final String ATTATCH_S = "attach-s"; // 附卡前缀
	public static final String M_CON_ITEM_INFO_PREFIX = "mconItemInfo"; // 主卡联系人前缀
	public static final String SERIAL_VERSION_UID = "serialVersionUID"; // 对象序列化标示
	// 节点对像
	public static final String APPLY_INFO_DTO = "applyInfoDto"; // 申请件信息
	public static final String APPLY_NODE_COMMON_DATA = "applyNodeCommonData";// 公共信息
	public static final String APPLY_NODE_CHEAT_CHECK_DATA = "applyNodeCheatCheckData";// 反欺诈
	public static final String APPLY_NODE_INQUIRY_DATA = "applyNodeInquiryData";// 初审
	public static final String APPLY_NODE_FINAL_AUDIT_DATA = "applyNodeFinalAuditData";// 终审
	public static final String APPLY_NODE_INFO_VALIDITY_DATA = "applyNodeInfoValidityData";// 申请资料验证
	public static final String APPLY_NODE_PATCHBOLT_DATA = "applyNodePatchboltData";// 补件
	public static final String APPLY_NODE_REVIEW_DATA = "applyNodeReviewData";// 复核
	public static final String APPLY_NODE_RULE_POINT_DATA = "applyNodeRulePointData";// 评分
	public static final String APPLY_NODE_GRADE_PROCESS_DATA = "applyNodeGradeProcessData";// 评分过程记录
	public static final String APPLY_NODE_TEL_CHECK_BISIC_DATA = "applyNodeTelcheckbisicData";// 电话调查
	public static final String APPLY_NODE_PRE_CHECK_DATA = "applyNodePreCheckData";// 预审确认
	public static final String APPLY_NODE_EXT_RISK = "applyNodeExtRisk";// 外部决策
	public static final String APPLY_NODE_EXT_RISK_DATA = "applyNodeExtRiskData";//行内（外部第三方）风控系统过程
	// 电话调查核身问题、必问问题、选核问题
	public static final String TEL_CHECK_INFO = "telCheckInfo";
	public static final String ID_CHECK_ITEM = "idCheckItem";
	public static final String MUST_CHECK_ITEM = "mustCheckItem";
	
	public static final String APPLY_NODE_HIS_DATA = "applyNodeHisData"; // 历史信息
	// 网点信息
	public static final String cardCollectInd = "cardCollectInd"; // 领卡权限
	public static final String issueInd = "issueInd"; // 发卡权限
	public static final String independEntity = "independEntity"; // 独立发卡
	public static final String allBranch = "allBranch"; // 所有网点机构
	public static final String allBranchMap = "allBranchMap"; // 所有网点机构MAP

	public static final String SAVE = "save"; // 保存
	public static final String SUBMIT = "submit"; // 提交
	public static final String ROLL_BACK = "rollback"; // t退回
	public static final String OVER_TIME = "overtime"; // 超时


	public static final String RETURN_MSG ="交易成功";

	public static final String BASE_INFO_TAB = "baseInfoTab"; // 基本信息TAB
	public static final String BASE_INFO_FORM = "baseInfoForm"; // 基本信息FORM
	public static final String BASE_INFO_TYPE = "baseInfoType"; // 基本信息TYPE

	public static final String MCARD_INFO_TAB = "mcardInfoTab"; // 主卡信息TAB
	public static final String MCARD_INFO_TYPE = "mcardInfoType"; // 主卡信息Type

	public static final String PRIM_ANNEX_EVI_TAB = "primAnnexEviTab"; // 附件信息TAB
	public static final String PRIM_ANNEX_EVI_TYPE = "primAnnexEviType"; // 附件信息Type

	public static final String OTHERINFO_TAB = "otherinfoTab"; // 其他信息TAB
	public static final String OTHER_INFO_FORM = "otherInfoForm"; // 基本信息FORM
	public static final String OTHER_INFO_TYPE = "otherInfoType"; // 基本信息Type

	public static final String ATTACHINFO_TAB = "attachinfoTab"; // 附卡信息TAB
	public static final String ATTACHINFO_TYPE = "attachinfoType"; // 附卡信息Type
	public static final String CONTACT_INFO = "contactInfo"; // 联系人信息


	public static final String name = "name";
	public static final String gender = "gender";
	public static final String cellPhone = "cellPhone";
	public static final String sms_type = "sms_type";
	public static final String app_no = "app_no";
	public static final String org = "org";
	public static final String productCd = "productCd";
	public static final String description = "description";
	public static final String msgContent="msgContent";
	// ***********************
	// 录入复核
	public static final String REVIEW_VALUES = "reviewValues"; // 复核信息

	// 系统自动处理
	public static final String SYS_AUTO = "sysauto"; // 自动处理

	// 补件操作
	public static final String APPLY_PB_TIMEWAIT = "pbtimeWait"; // 补件等待天数
	public static final String APPLY_PB_STTIME = "pbstartTime"; // 补件开始日期
	public static final String APPLY_PB_ENDTIME = "pbEndTime"; // 补件结束日期
	public static final String APPLY_PB_ADDDAYS = "pbAddDays"; // 增加补件等待天数

	// 系统参数
	public static final String INIT_PROCDEFKEY = "initProcdefKey"; // 默认流程
	public static final String TASK_QUEUE = "taskQueue";// 任务队列超时时间

	public static final String BL_PERSON_ID = "blPersonId";// 个人黑名单
	public static final String BL_P_MOBILE = "blPMobile";// 个人移动电话黑名单
	public static final String BL_P_HOME_PHONE = "blPHomePhone";// 个人家庭电话黑名单
	public static final String BL_P_HOME_ADD = "blPHomeAdd";// 个人家庭地址黑名单
	public static final String BL_P_CORP_NAME = "blPCoprName";// 个人公司名称黑名单
	public static final String BL_P_CORP_PHONE = "blPCorpPhone";// 个人公司电话黑名单
	public static final String BL_P_CORP_ADD = "blPCorpAdd";// 个人公司地址黑名单
	public static final String BL_P_EMAIL = "blPEmail";// 个人公司地址黑名单
	// 人工节点
	public static final String input = "APPLY_INPUT"; // 录入
	public static final String input_modify = "APPLY_INPUT_MODIFY"; // 录入修改
	public static final String review = "REVIEW"; // 录入复核
	public static final String applyinfo_creditReport = "APPLYINFO_CREDITREPORT"; // 人工核查
	public static final String apply_check = "APPLY_CHECK"; // 初审
	public static final String applyinfo_patchbolt = "APPLYINFO_PATCHBOLT"; // 补件操作
	public static final String applyinfo_finalaudit = "APPLYINFO_FINALAUDIT"; // 终审
	// 保存修改历史信息时使用
	public static final String TM_APP_CUST_INFO = "TM_APP_CUST_INFO"; // 表名
	public static final String TM_APP_CUST_INFO_NAME ="申请人信息";
	public static final String TM_APP_MAIN = "TM_APP_MAIN"; // 表名
	public static final String TM_APP_MAIN_NAME = "主表信息"; 
	public static final String TM_APP_AUDIT = "TM_APP_AUDIT"; // 表名
	public static final String TM_APP_AUDIT_NAME = "审计记录"; 
	public static final String TM_APP_CONTACT = "TM_APP_CONTACT"; // 表名
	public static final String TM_APP_CONTACT_NAME = "联系人信息";
//	public static final String TM_APPCARDFACE_INFO = "TM_APPCARDFACE_INFO"; // 表名
//	public static final String TM_APPCARDFACE_INFO_NAME = "卡面信息";
	public static final String TM_APP_PRIM_CARD_INFO = "TM_APP_PRIM_CARD_INFO"; // 表名
	public static final String TM_APP_PRIM_CARD_INFO_NAME = "卡片信息";
//	public static final String TM_APP_ATTACH_APPLIER_INFO = "TM_APP_ATTACH_APPLIER_INFO"; // 表名
//	public static final String TM_APP_ATTACH_APPLIER_INFO_NAME = "附卡申请人信息";
	public static final String TM_APP_PRIM_ANNEX_EVI = "TM_APP_PRIM_ANNEX_EVI"; // 表名
	public static final String TM_APP_PRIM_ANNEX_EVI_NAME = "附件证明信息";
//	public static final String TM_ETC_CAR = "TM_ETC_CAR"; // 表名
//	public static final String TM_ETC_CAR_NAME = "ET车辆信息";
//	public static final String TM_APP_EXTEND = "TM_APP_EXTEND"; // 表名
	public static final String TM_APP_INSTALMENT_CREDIT = "TM_APP_INSTALMENT_CREDIT"; // 表名
	public static final String TM_APP_INSTALMENT_CREDIT_NAME ="分期信息";

	// 数据类型
	public static final String STRING = "String"; //
	public static final String DATE = "Date"; //
	public static final String TIMESTAMP = "TimeStamp"; //
	public static final String DECIMAL = "Decimal"; //

	//缓存用到
	public static final String REJECT_REASON = "ApplyRejectReason";//拒绝原因（数据字典的类型）
	public static final String REJECT_FLAG = "R";//拒绝原因在初审、电调、终审直接拒绝的标志
	public static final String REJECT_REASON_MAP = "rejectReasonMap";//原因码-中文名称
	public static final String REJECT_REASON_FLAG_MAP = "rejectReasonFlagMap";//原因码-拒绝标志
	public static final String BACK_MARK = "ReturnedType";//退回类型（数据字典的类型）
	public static final String FIELD_REGIN = "FieldRegion";//字段所在的位置
	public static final String FIELD_PRODUCT = "FieldProduct";//字段所在的位置
	public static final String ACL_DICT_PREFIX_= "AclDictPrefix_";//业务字典表缓存数据前缀
	public static final String ALL_USERS = "AllUsers";//所有系统用户
	public static final String ALL_ADDRESS = "AllAddress";//所有省市区业务字典数据
	public static final String ALL_PRODUCT = "AllProduct";//(卡)产品
	public static final String ALL_PRODUCT_MAP = "AllProductMap";//(卡)产品
	public static final String ALL_PRODUCT_CARDFACE = "AllProductCardFace";//产品与卡面关系 
	public static final String ALL_PRODUCT_BRANCH = "AllProductBranch";//产品与网点机构关系
	public static final String ALL_PRODUCT_PROCESS = "AllProductProcess";//产品与网点机构关系
	public static final String ALL_USER_RELATION = "AllUserRelation";//所有业务人关系表对象
	public static final String AUTHORITY_USERS = "AuthorityUsers";//所有拥有节点权限的人
	public static final String APP_SOURCE = "AppSource";//所有拥有节点权限的人
	// 保存复核项map和对象

//	public static final String MainCardInfo = "mainCardInfo";
//	public static final String AttachCardInfo = "attachCardInfo";
	public static final String TmAppCustInfo = "tmAppCustInfo";
//	public static final String TmAppPrimApplicantInfo = "tmAppPrimApplicantInfo";
//	public static final String TmAppAttachApplierInfo = "tmAppAttachApplierInfo";
	public static final String TmAppMain = "tmAppMain";
	public static final String TmAppAudit = "tmAppAudit";
	public static final String TmAppContact = "tmAppContact";
	public static final String TmAppPrimCardInfo = "tmAppPrimCardInfo";
	public static final String TmAppPrimAnnexEvi = "tmAppPrimAnnexEvi";
	public static final String TmExtRiskInput = "tmExtRiskInput";
//	public static final String TmAppCardfaceInfo = "tmAppCardfaceInfo";
	public static final String TmAppInstalLoan = "tmAppInstalLoan";
//	public static final String TmAppExtend = "tmAppExtend";
//	public static final String TmAppExtendMap = "tmAppExtendMap";
//	public static final String TmAppCardfaceInfoList = "tmAppCardfaceInfoList"; // 卡面map

	
	// 必输项标志
	public static final String INPUT = "input";
	
	//复核对比项
	public static final String REVIEW = "review";
	public static final String MAIN_TAB = "mainTab";//主卡复核项
	public static final String COMMON_TAB = "commonTab";//主附卡都要复核项
	
	// 申请录入的常量
	public static final String gender_M = "M"; // 男
	public static final String gender_F = "F"; // 女
	public static final String cardFetch_B = "B"; // 领卡方式_领卡网点
	public static final String bscSuppInd_B = "B"; // 主附卡指示_(B是主卡，S是附卡)
	public static final String bscSuppInd_B_1 = "B1"; // 主附卡指示_(B是主卡，S是附卡)
	public static final String bscSuppInd_S = "S"; // 主附卡指示_(B是主卡，S是附卡)
	public static final String bscSuppInd_S_1 = "S1"; // 第一张附卡
	public static final String bscSuppInd_S_2 = "S2"; // 第二张附卡
	public static final String bscSuppInd_S_3 = "S3"; // 第三张附卡
	
//	public static final String SUCCESS="su002";//征信报告登记应到成功标志
	// 扩展字段
	public static final String APPLY_CUSTNAME = "applyCustName";
	public static final String APPLY_OWNINGBRANCH = "applyOwningBranch";
	public static final String APPLY_ORG = "applyOrg"; // 机构
	public static final String APPLY_APPNO = "applyAppNo"; // 申请编号
	public static final String APPLY_LMT = "appLmt"; // 申请额度
	public static final String APPLY_APPTYPE = "applyAppType"; // 申请类型
	public static final String APPLY_IDTYPE = "applyIdType"; // 证件类型
	public static final String APPLY_IDNAPPLY_PB_STTIMEO = "applyIdNo"; // 证件号码
	public static final String APPLY_PRODUCTCD = "applyProductCd"; // 卡产品代码
	public static final String APPLY_APPROVEQUICKFLAG = "applyApproveQuickFlag"; // 快速审批标志
	public static final String APPLYSTATUS = "applyStatus"; // 审批结果
	public static final String APPLY_RTFSTATETYPE = "applyRtfStateType"; // 审批状态
	public static final String APPLY_REJECTREASON = "applyRejectReason"; // 拒绝原因
	public static final String APPLY_OPERATORID = "applyOperatorId"; // 操作员id
	public static final String APPLY_DATE = "applyDate"; // 日期
	public static final String APPLY_CELLPHONE = "cellPhone"; // 移动电话
	public static final String APPLY_HOMEPHONE = "homePhone"; // 家庭电话
	public static final String APPLY_PRODUCT_TYPE = "applyProductType"; // 卡产品类型
	public static final String APPLY_IDNO = "applyIdNo";
	public static final String CREATE_USER = "creatUser";// 申请用户
	public static final String OPERATER_ID = "operaterId";
	public static final String CREATE_DATE = "creatDate";//
	public static final String NAME = "name";
	public static final String ID_NO = "idNo";//
	public static final String ID_TYPE = "idType";//
	public static final String APPNO = "appNo"; // 申请编号
	public static final String AccLmt = "accLmt";// 申请移除额度
	public static final String ORG = "org"; // 机构
	public static final String HomeState = "homeState";//
	public static final String HomeCity = "homeCity";
	public static final String HomeZone = "homeZone";
	public static final String IfSelectedCard = "ifSelectedCard";
	public static final String CardFetch = "cardFetch";
	public static final String EmbLogo = "embLogo";
	public static final String AttachNo = "attachNo";
	public static final String Cellphone = "cellphone";
	public static final String HomeAddrCtryCd = "homeAddrCtryCd";
	public static final String CorpName = "corpName";
	public static final String PosPinVerifyInd = "posPinVerifyInd";

	public static final String PyhCd = "pyhCd";
	public static final String BscSuppInd = "bscSuppInd";
	public static final String PyhDescrip = "pyhDescrip";
	public static final String UpdateDate = "pdateDate";
	public static final String UpdateUser = "pdateUser";
	public static final String ContactName = "contactName";
	public static final String ContactRelation = "contactRelation";
	public static final String ContactMobile = "contactMobile";
	public static final String ContactTelephone = "contactTelephone";
	public static final String contactEmpPhone = "contactEmpPhone";
	public static final String ContactGender = "contactGender";

	public static final String FetchBranch = "fetchBranch";
	public static final String EmpDepartment = "empDepartment";
	public static final String SalesInd = "salesInd";
	public static final String ApplyFromType = "applyFromType";
	public static final String InputName = "inputName";
	public static final String InputNo = "inputNo";
	public static final String InputDate = "inputDate";
	public static final String spreaderBranchThree = "spreaderBranchThree";
	public static final String spreaderBranchTwo = "spreaderBranchTwo";
	
	public static final String APP_REMARK = "REMARK";// 备注
	public static final String APP_MEMO = "MEMO";// 备忘
	public static final String APP_REMARK_114 = "REMARK114";// 114电话备注
	public static final String APP_REMARK_PERSONCHECK = "PERSONCHECKREMARK";// 人工核查备注
	public static final String APP_REMARK_PATCHBOLT = "PATCHBOLT";//补件备注
	public static final String APP_REMARK_PRECHECK = "PRECHECK";//预审备注
	public static final String ApplyMainCardInfo = "主卡信息";
	public static final String ApplyAttachCardInfo = "附卡信息";
	public static final String ApplyContactInfo = "联系人信息";
	public static final String ApplyEviInfo = "附件信息";
	public static final String ApplyOtherInfo = "其他信息";

	// 任务变量
	public static final String Claim = "claim";// 获取任务
	public static final String Cancel = "cancel";// 取消任务
	public static final String Complete = "complete";// 完成任务
	
	
	public static final String APPLY_INPUT_MODIFY = "申请录入修改";
	public static final String APPLY_REVIEW = "录入复核";
	public static final String APPLY_CREDIT_REPORT = "人工核查";
	public static final String APPLY_BASIC_CHECK = "初审调查";
	public static final String APPLY_PATCHBOLT = "补件操作";
	public static final String APPLY_FINAL_AUDIT = "终审";
	public static final String APPLY_END = "流程结束";
	
	
	//规则class
	public static final String  RULE_REPEATCHECK="RULE_REPEATCHECK";//自动申请判定
	public static final String  RULE_CHEATCHECK="RULE_CHEATCHECK";
	public static final String  RULE_CREDITESTIMATE="RULE_CREDITESTIMATE";
	public static final String  RULE_AUTOCHECK="RULE_AUTOCHECK";
	
	public static final String  SYS_AUDIT_SAVE="SAVE";
	public static final String  SYS_AUDIT_UPDATE="UPDATE";
	public static final String  SYS_AUDIT_DEL="DELETE";

	//征信调用
	public static final String IDFY01="02";  //身份核查 

	public static final String SUCESS="交易成功";  //综合前置返回调用成功标识
	public static final String ORGANIZATION = "000064540000";//机构号名称
	public static final String RETURN_CODE = "00000000000000";//交易工程返回代码

}
