<#assign fieldsetMap = {'A1':'申请信息','A2':'申请人基本信息','A5':'服务要求','A6':'银行专用栏'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "casComponentFieldView_V1.ftl"/>
</#list>
