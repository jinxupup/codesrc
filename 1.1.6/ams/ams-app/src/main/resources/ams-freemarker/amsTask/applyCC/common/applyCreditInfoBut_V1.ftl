<@fieldset>
	<@legend style="color:red; padding-top:5px;"/>
	<#-- 弹框共用js -->
	<#include "dialogJs.ftl"/>
	<@field field_ar="36" label_ar="2" input_ar="33">
		<#-- 欺诈检查结果  -->
		<#--<#include "cheatResultBtn.ftl"/>-->
		<#-- 老客户信息 -->
		<#--<#include "oldCustInfoBtn.ftl"/>-->
		<#-- 历史申请信息 -->
		<#include "applyHistoryBtn.ftl"/>
		<#-- 审批历史信息 -->
		<#--<#include "approveHistoryBtn.ftl"/>-->
		<#-- 修改历史信息-->
		<#include "modifyHistoryBtn.ftl"/>
		<#-- 老客户信息-->
		<#--<#include "retrialQueryBtn.ftl"/>-->
		<#-- 人行客户报告 -->
		<#--<#include "bankReportBtn.ftl"/>-->
		<#-- 评分信息 -->
		<#--<#include "scoreInfoBtn.ftl"/>-->
		<#-- 身份核身 -->
		<#--<#include "idCheckBtn.ftl"/>-->
		<#-- 影像调阅 -->
		<#include "showPicBtn.ftl"/>
		<#include "amsShowAuditHistory.ftl"/><#--审计历史-->
		<#--第三方机构征信查询-->
		<#--<#include "cis5systemBtn.ftl"/>-->
		<#--案件流转记录-->
		<#include "taskTransRecordBtn.ftl"/>
		<#-- 个人-信贷信息 -->
		<#--<#include "creditInformationBtn.ftl"/>-->
		<#-- 风控信息 -->
		<#--<#include "creditRiskInfoBtn.ftl"/>-->
		<#-- 风控信息 -->
<#--
		<#include "amsPushBtn.ftl"/>
-->
	</@field>
</@fieldset>
