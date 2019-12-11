package com.jjb.ecms.service.constants;

/**
 * @Description: 定义常用的联机调用参数
 * @author JYData-R&D-Big Star
 * @date 2016年11月23日 上午10:05:45
 * @version V1.0
 */
public class SysConstants {

	public static final String ORG = "000064540000";
	public static final String OPT_ZERO = "0";
	public static final String OPT_ONE = "1";
	public static final String OPT_TWO = "2";

	public static final String CURR_CD_156 = "156";
	public static final String CURR_CD_840 = "840";
	public static final String DEFAULT_CURR_CD = "000";

	public static final String DEFAULT_STMT_DATE = "000000";
	
	public static final String SUCCESS_CODE = "SSSS";

	public static final String ERRS001_CODE = "S001";
	public static final String ERRS001_MES ="系统处理异常";

	public static final String ERRS002_CODE = "S002";
	public static final String ERRS002_MES ="未知的服务码";

	public static final String ERRS003_CODE = "S003";
	public static final String ERRS003_MES = "非法的请求字段";

	public static final String ERRS004_CODE = "S004";
	public static final String ERRS004_MES = "无效操作类型";

	public static final String ERRS005_CODE = "S005";
	public static final String ERRS005_MES = "无效业务流水号！";

	public static final String ERRS006_CODE = "S006";
	public static final String ERRS006_MES = "报文验证错误";
	
	public static final String ERRS007_CODE ="S007";
	public static final String ERRS007_MES ="无效的日期类型";
	
	public static final String ERRS008_CODE ="S008";
	public static final String ERRS008_MES ="无效的翻页数据";
	
	public static final String ERRS009_CODE ="S009";
	public static final String ERRS009_MES ="翻页的起始位置不能大于等于结束位置";
	
	public static final String ERRS010_CODE ="S010";
	public static final String ERRS010_MES ="翻页查询记录数超过设置的最大值";
	
	public static final String ERRS011_CODE ="S011";
	public static final String ERRS011_MES ="翻页的结束位置lastrow必须大于0";
	
	public static final String ERRS012_CODE ="S012";
	public static final String ERRS012_MES ="未收到响应报文";
	
	public static final String ERRS013_CODE ="S013";
	public static final String ERRS013_MES ="xml解析错误";
	
	public static final String ERRS014_CODE ="S014";
	public static final String ERRS014_MES ="没有匹配到对应机构的连接地址";
	
	public static final String ERRS015_CODE ="S015";
	public static final String ERRS015_MES ="连接地址配置格式不正确";

	public static final String ERRB202_CODE = "S202";
	public static final String ERRB202_MES = "征信审核系统主服务响应超时";
		
	// 查询类Q, 系统异常S

	// 查询类Q；客户类100，账户类200，卡片类300

	/**
	 * 非法的证件类型或证件号码
	 */
	public static final String ERRQ001_CODE = "Q001";
	public static final String ERRQ001_MES = "非法的证件类型或证件号码";

	/**
	 * 证件类型/号码、影像批次号、操作员id都为空
	 */
	public static final String ERRQ002_CODE = "Q002";
	public static final String ERRQ002_MES = "无效的查询条件";
	
	public static final String ERRQ003_CODE = "Q003";
	public static final String ERRQ003_MES = "非法的机构号";

	//process 的bean名称前缀
    public static final String BEAN_NAME_PREFIX ="process" ;
    
    //#######-----ESB响应码定义----####
	public static final String ESB_SUCCESS_CODE = "00000000000000";

	public static final String ERR_ESB_S001_CODE = "20063000000001";
	public static final String ERR_ESB_S001_MES ="系统处理异常";

	public static final String ERR_ESB_S002_CODE = "20063000000002";
	public static final String ERR_ESB_S002_MES ="未知的服务码";

	public static final String ERR_ESB_S003_CODE = "20063000000003";
	public static final String ERR_ESB_S003_MES = "非法的请求字段";

	public static final String ERR_ESB_S004_CODE = "20063000000004";
	public static final String ERR_ESB_S004_MES = "无效操作类型";

	public static final String ERR_ESB_S005_CODE = "20063000000005";
	public static final String ERR_ESB_S005_MES = "无效业务流水号！";

	public static final String ERR_ESB_S006_CODE = "20063000000006";
	public static final String ERR_ESB_S006_MES = "报文验证错误";
	
	public static final String ERR_ESB_S007_CODE ="20063000000007";
	public static final String ERR_ESB_S007_MES ="无效的日期类型";
	
	public static final String ERR_ESB_S008_CODE ="20063000000008";
	public static final String ERR_ESB_S008_MES ="无效的翻页数据";
	
	public static final String ERR_ESB_S009_CODE ="20063000000009";
	public static final String ERR_ESB_S009_MES ="翻页的起始位置不能大于等于结束位置";
	
	public static final String ERR_ESB_S010_CODE ="20063000000010";
	public static final String ERR_ESB_S010_MES ="翻页查询记录数超过设置的最大值";
	
	public static final String ERR_ESB_S011_CODE ="20063000000011";
	public static final String ERR_ESB_S011_MES ="翻页的结束位置lastrow必须大于0";
	
	public static final String ERR_ESB_S012_CODE ="20063000000012";
	public static final String ERR_ESB_S012_MES ="未收到响应报文";
	
	public static final String ERR_ESB_S013_CODE ="20063000000013";
	public static final String ERR_ESB_S013_MES ="xml解析错误";
	
	public static final String ERR_ESB_S014_CODE ="20063000000014";
	public static final String ERR_ESB_S014_MES ="没有匹配到对应机构的连接地址";
	
	public static final String ERR_ESB_S015_CODE ="20063000000015";
	public static final String ERR_ESB_S015_MES ="连接地址配置格式不正确";

	public static final String ERR_ESB_B202_CODE = "20063000000202";
	public static final String ERR_ESB_B202_MES = "征信审核系统主服务响应超时";
    //#######-----ESB响应码定义-----结束--####
	
	
}
