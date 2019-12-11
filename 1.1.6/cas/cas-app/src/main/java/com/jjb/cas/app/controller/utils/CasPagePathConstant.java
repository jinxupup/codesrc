package com.jjb.cas.app.controller.utils;

import org.springframework.stereotype.Component;

/**
 * 征审系统页面路径配置常量
 * @Description: 
 * @author JYData-R&D-HN
 * @date 2017年3月29日 下午12:08:08
 * @version V1.0
 */
@Component
public class CasPagePathConstant {
	
	public final static String APPLICATION_INPUT_MODIFY="application-input-modify";//录入修改
	public final static String APPLICATION_REVIEW="application-review";//录入复核
	public final static String APPLICATION_PERSONCHECK="applicantion-personcheck";//人工核查
	public final static String APPLICATION_CHECK="application-check";//初审
	public final static String APPLICATION_PATCHBLOT="application-patchblot";//补件
	public final static String APPLICATION_FINALAUDIT="application-finalaudit";//终审
	public final static String APPLICATION_TELEPHONESURVEY="application-telephonesurvey";//电话调查
	public final static String APPLICATION_CREDIT_CHECK="application-credit-check";//征审调查
	public final static String APPLICATION_PRE_CHECK="application-pre-check";//预审
	public final static String APPLICATION_FILE_MANAGE="application-fileManage";//归档

	public final static String applyPreInputPage="applyPreInputPage";//预录入
	public final static String applyFileUpload="applyFileUpload";//申请件批量导入
	public final static String applyAddAttachCard="applyAddAttachCard";//新增附卡
	public final static String telphoneSurveyLayout="telphoneSurveyLayout";//电话调查记录1
	
//	public final static String applyTaskList="applyTaskList";//待办任务管理
	public final static String applyToDoTaskList="applyToDoTaskList";//待办任务管理
	public final static String applyMyTaskList="applyMyTaskList";//待办任务管理
	public final static String applyTaskTransferPage = "applyTaskTransferPage";//已分配任务列表
	public final static String applyAssignedUserList = "applyAssignedUserList";//可分配用户列表
	public final static String applyPreAssignedUserList = "applyPreAssignedUserList";//可分配推广人列表
	

	public final static String casApplyProcessDetailPage ="casApplyProcessDetailPage";//申请件进度查询详情页面
	public final static String casApplyUpdatePage = "casApplyUpdatePage";//申请件进度查询修改页面
	public final static String applyCountQuery ="applyCountQuery";//申请工作量查询页面
	public final static String applyCountDetails ="applyCountDetails";//申请工作量明细页面
	public final static String applyAbnProcessList ="applyAbnProcessList";//异常件列表管理
	public final static String applyEcssHis ="applyEcssHis";//影像调阅记录
	public final static String blPersonalList = "blPersonalList";//(个人)黑名单管理页面
	public final static String blPersonalAdd = "blPersonalAdd";//(个人)黑名单新增
	public final static String blPersonalFileUpload = "blPersonalFileUpload";//(批量)黑名单新增
	public final static String blPersonalDialog = "blPersonalDialog";//(个人)黑名单详情
	public final static String applySpreaderPage = "applySpreaderPage";//推广人管理页面
	public final static String applySpreaderEditPage = "applySpreaderEditPage";//推广人信息修改页面
	public final static String systemAuditInfo = "systemAuditInfo";//历史操作修改记录页面
	public final static String smsTemplateList = "smsTemplateList";//短信模版管理列表
	public final static String smsTemplateAdd = "smsTemplateAdd";//短信模版新增
	public final static String applyUnlockCard = "applyUnlockCard";//卡号解锁
	public final static String bankBranchManagePage = "bankBranchManagePage";//银行网点管理
	public final static String bankBranchInfo = "bankBranchInfo";//银行网点维护
	public final static String sysParam = "sysParam";//审批系统参数管理
	public final static String sysParamForm = "sysParamForm";//审批系统参数维护
	public final static String productParam = "productParam";//产品参数管理
	public final static String instalProgram = "instalProgram";//分期活动
	public final static String instalMerchant = "instalMerchant";//分期商户
	public final static String instalProgramMerchant = "instalProgramMerchant";//分期活动商户
	public final static String instalProgramTerms = "instalProgramTerms";//分期活动期数
	public final static String pruductAddPage = "pruductAddPage";//产品参数新增
	public final static String pruductUpdatePage = "pruductUpdatePage";//产品参数修改
	public final static String applyActivitiDeployPage = "applyActivitiDeployPage";//审批流程部署
	public final static String applyFieldPage = "applyFieldPage";// 字段管理页面
	public final static String fieldProductPage = "fieldProductPage";// 卡产品关联字段页面
	public final static String editFieldPage = "editFieldPage";// 字段新增/修改页面
	public final static String pbocReportQueryPage = "pbocReportQueryPage";//人行报告查询页面
	public final static String getPbocReport = "getPbocReport";//人行报告返回页面
			
	public final static String userManagePage = "userManagePage";//用户管理：add by H.N 20171109
	public final static String pointParam = "pointParam";//评分参数管理 add by JJ.G 20171201
}
