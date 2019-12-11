<#assign fieldsetMap = {'A1':'申请信息','A9':'标准大额分期信息','A2':'申请人基本信息','A3':'职业资料'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldView.ftl"/>
</#list>
<#--联系人-->
<#include "applyContact.ftl">
<#--主附同申附卡部分-->
<#if appType?? && appType=='A'>
	<#list 0..attachNum as atId>
		<#include "addAttachContentView_V1.ftl">
	</#list>
</#if>

<#assign fieldsetMap = {'A5':'服务要求'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldView.ftl"/>
</#list>

<#assign fieldsetMap = {'A6':'银行专用栏','A7':'卡片及账单寄送','A8':'银行专用栏'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldView.ftl"/>
</#list>
<@row>
	<@field label="推广人注记" field_ar="36" label_ar="4" input_ar="32">
		<@textarea rows="1" value="${(MEMOINPUT.memoInfo)!''}" label_only="true"/>
	</@field>
</@row>