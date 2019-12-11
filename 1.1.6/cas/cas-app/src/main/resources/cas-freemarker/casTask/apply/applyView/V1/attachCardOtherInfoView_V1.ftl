<#assign fieldsetMap = {'C1':'申请件其他信息'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "casComponentFieldView_V1.ftl"/>
</#list>