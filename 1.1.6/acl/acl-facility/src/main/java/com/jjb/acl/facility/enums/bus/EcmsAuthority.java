package com.jjb.acl.facility.enums.bus;

/**
 * @Description: Ecms所有权限枚举(统一大写)
 * @author JYData-BigZ.Y
 * @date 2016年9月27日17:49:48
 * @version V1.0
 */
public enum EcmsAuthority {

	INPUT("applyinfo-input","AMS"),

	AMS_APPLY_MODIFY("applyinfo-input-modify","AMS"),//申请录入修改？

	AMS_APPLY_REVIEW("applyinfo-review","AMS"),//复核？

	CAS_APPLY_PERSONCHECK("applyinfo-personcheck","CAS"),//人工核查

	CAS_APPLY_BASIC_CHECK("applyinfo-check","CAS"),//初审

	CAS_APPLY_PRE_CHECK("applyinfo-pre-check","CAS"),//预审

	CAS_APPLY_FILE_MANAGE("applyinfo-fileManage","CAS"),//新增归档节点

	CAS_APPLY_PATCHBOLT("applyinfo-patchbolt","CAS"),//补件

	CAS_APPLY_FINALAUDIT("applyinfo-finalaudit","CAS"),//终审

	APPLYINFO_51CC_RISK("applyinfo-51cc-risk","CAS"),//新增征审审批节点
	
	APPLYINFO_CREDIT_CHECK("applyinfo-credit-check","CAS"),//新增征审审批节点

	CAS_APPLY_TEL_SURVEY("applyinfo-telephone-survey","CAS"),//新增电话调查节点

	APPLY_PROCESS_QUERY_DETAIL("申请进度查询详情权限","ECMS"), // 申请进度查询详情权限
	
	APPLY_QUERY_UPDATE("apply-query-update","EMCS"),//申请进度查询修改

	APPLY_REAUDIT("重审","ECMS"), // 重审

	ECMS_TASK_COUNT_QUERY("申请工作统计","ECMS"), // 申请工作统计

	ECMS_APPLY_TASK_COUNT("工作统计查询权限管理","ECMS"), // 工作统计查询权限管理

	ECMS_ASSIGN_TASK("案件分配","ECMS"), // 案件分配

	ECMS_CASE_TRANSFER("案件转分配","ECMS"),// 案件转分配
	
	ECMS_CLAIM_TASK("案件获取","ECMS"),//案件获取
	
	ECMS_DIAGRAMS_VIEW("查看流程图","ECMS");//查看流程图

	public String state;
	public String lab;
	public String sysType;

	private EcmsAuthority(String lab,String sysType) {
		this.state = name();
		this.lab = lab;
		this.sysType=sysType;
	}
}
