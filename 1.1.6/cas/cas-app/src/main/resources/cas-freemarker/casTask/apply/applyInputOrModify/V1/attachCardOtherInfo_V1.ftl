<#assign fieldsetMap = {'C1':'申请人基本信息'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldSet.ftl"/>
</#list>
              