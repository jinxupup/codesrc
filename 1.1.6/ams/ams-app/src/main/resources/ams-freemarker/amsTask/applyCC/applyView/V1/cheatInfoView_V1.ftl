<@fieldset legend="风控小结">
	<@row>
		<@field label="自动审批结果:" field_ar="7" label_ar="16" input_ar="20">
			<@dictSelect dicttype="Content" value="${(applyRiskInfoDto.content)!}" label_only="true"/>
		</@field>
		<@field label="信用分:" field_ar="7" label_ar="16" input_ar="20">
			<@input value="${(applyRiskInfoDto.creditScore)!}" label_only="true"/>
		</@field>
		<@field label="信用等级:" field_ar="7" label_ar="16" input_ar="20">
			<@input value="${(applyRiskInfoDto.creditScoreLevel)!}" label_only="true"/>
		</@field>
		<@field label="未通过类型:" field_ar="8" label_ar="16" input_ar="20">
			<@input value="${(applyRiskInfoDto.unapprovedReasonCode)!}" label_only="true"/>
		</@field>
		<@field label="建议额度:" field_ar="7" label_ar="16" input_ar="20">
			<@input value="${(applyRiskInfoDto.suggestAmt)!}" label_only="true"/>
		</@field>
	</@row>
</@fieldset>

<@row>
	<div class="col-ar-18">
		<@fieldset legend="信用评分">
			<#include "../../commonDialog/creditPoint/creditPointLayout.ftl"/>
		</@fieldset>
	</div>
</@row>

<@row>
	<div class="col-ar-18">
		<@fieldset legend="准入规则">
			<#include "../../commonDialog/admittanceRule/admittanceRuleLayout.ftl"/>
		</@fieldset>
	</div>
	<div class="col-ar-18">
		<@fieldset legend="信用规则">
			<#include "../../commonDialog/creditRule/creditRuleLayout.ftl"/>
		</@fieldset>
	</div>
</@row>

