package com.jjb.ecms.constant;

/**
 * @Description: TmDitDic 参数表常量类
 * @author JYData-R&D-HN
 * @date 2016年9月27日 下午7:27:12
 * @version V1.0
 */
public class AppDitDicConstant {
	public static final String applyOnlineOnOffParam = "applyOnlineOnOffParam";
	public static final String applyPatchBoltParam = "applyPatchBoltParam";
	public static final String pbWaitTime_key = "waitTime";
	public static final String pbWarnTime_key = "warnTime";
	public static final String applyPageConfig = "applyPageConfig";//申请页面配置

	public static final String onLinOff_ajax_ddCheck = "ajaxDdCheck"; // 跨行联机调用开关参数 -是否自动校验约定还款账号有效性
	public static final String onLinOff_ddCheck = "ddCheck"; // 是否自动校验约定还款账号有效性

	public static final String onLinOff_innerBankCustNo = "innerBankCustNo"; // 联机调用开关参数-是否自动获取行内统一客户号
	public static final String onLinOff_innerCreditInfo = "innerCreditInfo"; // 联机调用开关参数-是否获取行内授信信息
	public static final String onLinOff_repeatIdentityVerification = "repeatIdentityVerification"; // 联机调用开关参数-申请时根据证件重复校验
	//数据库查询select * from TM_DIT_DIC where ITEM_NAME='sendRefuseMessage';
	public static final String isAutoTransferOrNot = "isAutoTransferOrNot"; // 是否开启自动分配
	public static final String onLinOff_sendRefuseMessage = "sendRefuseMessage"; // 联机调用开关参数-是否发送短信
	public static final String onLinOff_updateByRtfAndTimerState = "updateByRtfAndTimerState"; // 是否更新多卡同申失败件
	public static final String onLinOff_isCallPic = "isCallPic"; // 联机调用开关参数 -是否开启调用影像系统
	public static final String onLinOff_maxLimit = "maxLimit"; // 额度控制参数 -可直接授信最大额度
	public static final String onLinOff_threadPoolSize = "threadPoolSize"; // 额度控制参数 -可直接授信最大额度
	public static final String onLinOff_isCallNciic = "isCallNciic";//联机调用开关参数-是否开启调用-身份核身
	public static final String onLinOff_isPushProgress = "isPushProgress";//是否推送审批结果
	public static final String onLinOff_isCallExtRisk = "isExtRisk";//是否开启外部风控决策系统
//	public static final String onLinOff_isCallCreditLifeHelper = "isCallCreditLifeHelper";//是否开启外部风控决策系统
//	public static final String onLinOff_taskListAutoRefresh = "taskListAutoRefresh"; // 是否开启任务列表自动刷新功能：Y-开启，30s自动刷新，N-关闭
	public static final String onLinOff_useUserInfo = "useUserInfo"; // 是否开启信息验证：A-使用用户信息,B-强制使用用户信息,C-使用推广人信息,D-强制使用推广人信息
	public static final String onLinOff_RANKING_DATE = "rankingDate"; //是否开启排行榜日期范围及日期范围的值
	public static final String onLinOff_busSuperCardList = "busSuperCardList"; //商超卡产品代码清单
	public static final String onLinOff_trans0139Test = "trans0139Test"; //Trans0140-资产信息接口是否开启测试模式
	public static final String onLinOff_trans0140Test = "trans0140Test"; //Trans0140-资产信息接口是否开启测试模式

	//交易代码
	public static final String DICT_TYPE_ExtCheckIdRs = "ExtCheckIdRs"; //商超卡产品代码清单

	public static final String ext_Risk_EnToCn_Param ="extRiskEnToCnParam";  //获取风控中英文描述参数
	public static final String onLinOff_on = "Y"; // 联机开关 枚举值 -开
	public static final String onLinOff_off = "N"; // 联机开关 枚举值 -关
	public static final String pageParameters = "pageParameters";// 复核项和必输项
	public static final String review = "review";// 复核项查询标识
	public static final String input = "input";// 必输项标识
	public static final String telCheckInfo = "telCheckInfo";//电话调查核身问题何必问问题标识
	public static final String hisModify = "hisModify";//历史修改信息对比项
	public static final String pic_aicPicShow = "aicPicShow";//系统影像
	public static final String ext_51cc_NetConf = "ext51ccNetConf";//外部系统-51信用卡网络配置
	public static final String ext_bjj_risk_conf = "extBjjRiskConf";//外部系统-行内风控决策系统
	public static final String ext_ccif_conf = "extCcifConf";//外部系统-综合前置网络配置
	public static final String ext_ccfrontend_conf = "extCcfrontendConf";//外部系统-信用卡前置网络配置
	public static final String ext_cmp_conf = "extCmpConf";//外部系统-内容平台
	public static final String ext_creditLife_conf = "extCreditLifeConf";//外部系统-信用生活审批助手
	public static final String acctNo_query_ajax = "acctNoQueryAjax";//跨行账户查询网络配置参数
	public static final String instal_program_param_query = "instalProgramParamQuery";
	public static final String instal_program_merchant_param_query = "instalProgramMerchantParamQuery";
	public static final String instal_program__terms_param_query = "instalProgramTermsParamQuery";
	public static final String lending_process = "lendingProcess";//放款
	public static final String onLinOff_isSuport_Standard_large_staging="isSuportStlst";//是否支持标准大额分期，同步分期参数
	public static final String pointSetItems = "pointSetItems";//评分项或附件额度集合参数
	public static final String  SHENPI_QUOTA = "SHENPI_QUOTA";
	public static final String THE_NUMBEROF_TASK="theNumberOfTask";//申请件定时每次处理（B10状态&&非02渠道）数量
	public static final String MAX_REFUSE_TIMES = "maxRefuseTimes";// 异常件（B10状态&&非02渠道）最大处理次数
	/**
	 * wxl
	 * 新增影像系统调用参数配置
	 */
	public static final String CMP_cmpSysUrl = "cmpSysUrl"; // 内容平台url地址
	public static final String CMP_getBatchNumPath = "getBatchNumPath"; //获取批次号地址
//	public static final String CMP_showContentPath = "showContentPath"; // 内容调阅地址
	public static final String CMP_showContentPathParm = "showContentPathParm"; //加密参数传输地址
	public static final String CMP_isUseCustInfo = "isUseCustInfo"; //是否使用客户层调阅
	public static final String CMP_cmpAesKey = "CmpAesKey"; //是否使用客户层调阅
	public static final String CMP_moveOldImgPath = "moveOldImgPath"; //(临时)迁移旧影像内容地址

	public static final String RTF_STATE = "RtfState"; //审批状态
	public static final String T1013CardBlockCode = "T1013CardBlockCode"; //T1013CardBlockCode
	public static final String CreditRiskShowParams="CreditRiskShowParams";  //决策风险展示项

	//####风险警告、系统警告、申请件标签####----end----//
	public static final String FLAG_TYEP_RISK="FlagRisk";  //风险标签 type
	public static final String FLAG_TYEP_SYS="FlagSys";  //系统标签 type
	public static final String FLAG_TYEP_APP="FlagApp";  //自定义标签 type
	public static final String FLAG_LEVEL_R="R";  //拒绝
	public static final String FLAG_LEVEL_W="W";  //拒绝

	public static final String FLAG_SYS_001="S01";  //凸印姓名不一致{源凸印姓名}
	public static final String FLAG_SYS_002="S02";  //决策失败
	public static final String FLAG_SYS_003="S03";  //审批助手失败
	public static final String FLAG_SYS_004="S04";  //风险名单库查询失败
	public static final String FLAG_SYS_005="S05";  //渠道核身异常


	public static final String FLAG_RISK_W201="W201";//当前固定额度:#DESC 
	public static final String FLAG_RISK_W202="W202";//城市等级:#DESC级 
	public static final String FLAG_RISK_W203="W203";//客户手机虚拟号段:#DESC 
	public static final String FLAG_RISK_W204="W204";//亲属联系人手机虚拟号段:#DESC 
	public static final String FLAG_RISK_W205="W205";//紧急联系人手机虚拟号段:#DESC 
	public static final String FLAG_RISK_W206="W206";//客户同证件历史申请数:#DESC 
	public static final String FLAG_RISK_W207="W207";//客户同证件同产品通过数:#DESC 
	public static final String FLAG_RISK_W208="W208";//客户同证件不同手机申请数:#DESC 
	public static final String FLAG_RISK_W209="W209";//客户同手机不同证件申请数:#DESC 
	public static final String FLAG_RISK_W210="W210";//同亲属手机不同客户证件数:#DESC 
	public static final String FLAG_RISK_W211="W211";//同亲属手机不同客户手机数:#DESC 
	public static final String FLAG_RISK_W212="W212";//90D同单位地址不同证件申请数:#DESC 
	public static final String FLAG_RISK_W213="W213";//90D同单位地址不同手机申请数:#DESC 
	public static final String FLAG_RISK_W214="W214";//90D同客户证件-单位地址最小相似度:#DESC% 
	public static final String FLAG_RISK_W215="W215";//90D同客户证件-单位名称最小相似度:#DESC% 
	public static final String FLAG_RISK_W216="W216";//90D同客户手机-单位地址最小相似度:#DESC% 
	public static final String FLAG_RISK_W217="W217";//90D同客户手机-单位名称最小相似度:#DESC% 
	public static final String FLAG_RISK_W218="W218";//客户手机与他件亲属手机相同-姓名最小相似度:#DESC%
	public static final String FLAG_RISK_W219="W219";//亲属手机与他件亲属手机相同-姓名最小相似度:#DESC%
	public static final String FLAG_RISK_W220="W220";//亲属手机与他件客户手机相同-姓名最小相似度:#DESC%
	public static final String FLAG_RISK_W221="W221";//客户已有卡账户逾期数:#DESC 
	public static final String FLAG_RISK_W222="W222";//客户手机与亲属手机相同-当前逾期数:#DESC 
	public static final String FLAG_RISK_W223="W223";//客户手机与亲属手机相同-历史最高逾期数:#DESC 
	public static final String FLAG_RISK_W224="W224";//亲属手机与他件客户手机相同-当前逾期数:#DESC 
	public static final String FLAG_RISK_W225="W225";//亲属手机与他件客户手机相同-历史最高逾期数:#DESC 
	public static final String FLAG_RISK_W226="W226";//亲属手机与他件亲属手机相同-当前逾期数:#DESC 
	public static final String FLAG_RISK_W227="W227";//亲属手机与他件亲属手机相同-历史最高逾期数:#DESC 
	public static final String FLAG_RISK_W228="W228";//紧急联系人手机击中行内黑名单库 
	public static final String FLAG_RISK_W229="W229";//亲属联系人号码击中行内黑名单库 
	public static final String FLAG_RISK_W230="W230";//同推广人推广次数:#DESC 
	public static final String FLAG_RISK_W231="W231";//同推广人相同客户标识数:#DESC 
	public static final String FLAG_RISK_W232="W232";//同供应商来源次数:#DESC 
	public static final String FLAG_RISK_W233="W233";//同推广人推荐数:#DESC 
	public static final String FLAG_RISK_W234="W234";//同推广主管代码不同客户数:#DESC 
	//####风险警告、系统警告、申请件标签##----end----##//	
}
