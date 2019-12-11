<#include "/layout.ftl">
<@body>
    <@panel head="参数刷新">
        <@panelBody>
            <@fieldset legend="缓存服务列表">
	            <@form id="refreshForm" action="parameterRefresh/refresh" cols="3">
	 				 <@toolbar>
		                <@submitButton  name="刷新" />
		            </@toolbar>
	            </@form>
            </@fieldset>
        </@panelBody>
    </@panel>
</@body>
