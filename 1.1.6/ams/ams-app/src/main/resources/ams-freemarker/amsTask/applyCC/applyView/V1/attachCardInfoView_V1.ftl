<#assign fieldsetMap = {'A1':'申请信息','A2':'申请人基本信息','A7':'卡片寄送方式','A8':'银行专用栏'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldView.ftl"/>
</#list>
