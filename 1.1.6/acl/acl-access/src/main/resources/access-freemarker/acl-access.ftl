<#-- 业务字典下拉框 -->
<#macro dictSelect dicttype="" id="" name="" value="" nullable="true" showcode="true" valid={}  readonly="" class="" style="" width="" change=""  label_only="false" s2="true" search="false" multiple="false" value_split=",">
	<#local options=ar_("listToMap",dict_("list",dicttype),"code","codeName") />
	<@select id=id name=name options=options  value=value nullable=nullable showcode=showcode valid=valid  readonly=readonly class=class style=style width=width change=change label_only=label_only s2=s2 search=search multiple=multiple value_split=value_split></@select>
</#macro>
<#-- 生成获取业务字典名称的js方法 -->
<#macro js_dictGetName function_name="" dicttype="" showcode="false">
    <#local options=ar_("listToMap",dict_("list",dicttype),"code","codeName") />
    <@js_mapGetValue function_name=function_name map=options showcode=showcode />
</#macro>

<#-- 在table中显示中文,放在 <@th>中使用 -->
<#-- table render="true" 调用此方法有效 -->
<#macro thDictName dicttype="''" value="''" showcode="false">
    <#local uuid_function=ar_('uuid') />
    
    <#-- artTemplate模版 -->
        {{ ${value?replace("{{","")?replace("}}","")} | ${uuid_function} }}
        
    <#-- js方法 -->
        <div id="${uuid_function}" data-ar-th-inner-function="true" data-ar-th-inner-function-name="${uuid_function}">
            <@js_dictGetName dicttype=dicttype showcode=showcode/>
        </div>
</#macro>

<#-- 在table中显示中文,放在 <@th>中使用 通过传入map数据，获取名称，map的key与value判断，map的value是显示的名称-->
<#-- table render="true" 调用此方法有效 -->
<#macro thGetName options={} value="''" showcode="false">
    <#local uuid_function=ar_('uuid') />
    
    <#-- artTemplate模版 -->
        {{ ${value?replace("{{","")?replace("}}","")} | ${uuid_function} }}
        
    <#-- js方法 -->
        <div id="${uuid_function}" data-ar-th-inner-function="true" data-ar-th-inner-function-name="${uuid_function}">
            <@js_mapGetValue map=options showcode=showcode />
        </div>
</#macro>

<#-- 按钮权限判断 reverse反转，当没有此权限才显示 -->
 <#macro buttonAuth code="" reverse="false">
	<#assign hasCode=dict_("buttonAuth",code) />
	<#if reverse=="true">
		<#if hasCode!=true>
			<#nested />
	    </#if>
	<#else>
		<#if hasCode==true>
			<#nested />
	    </#if>
	</#if>
</#macro>