<#if addAttachFlag?? && addAttachFlag><#--附卡新增-->
	<#include "/layout.ftl"/>
	<@body>
		<#include "addAttachContent_V1.ftl">
	</@body>
<#else><#--附卡遍历-->
	<#list 0..(attachNum-1) as atId>
		<div id="card${(atId)!}">
			<#include "addAttachContent_V1.ftl">
		</div>
	</#list>
</#if>