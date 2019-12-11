<#assign fieldsetMap = {'C1':'申请信息','C2':'申请人基本信息','C4':'直系亲属联系人信息','C5':'其他联系人信息'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldView.ftl"/>
</#list>