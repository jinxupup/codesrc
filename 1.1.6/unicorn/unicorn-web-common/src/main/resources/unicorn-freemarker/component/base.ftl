<#-- mapToStr map转成 js的map字符串，没有左右的大括号，用于填充已存在的map字符串 -->
<#macro mapToStr map={} prefix=""><#list map?keys as key>${prefix}${key}:'${map[key]}'</#list></#macro>

<#-- mapToStr 将freemarker的map转成 js的map字符串， -->
<#macro mapToMapStr map={} >{<#list map?keys as key>'${key}':'${map[key]}'<#if key_has_next>,</#if></#list>}</#macro>

<#-- mapToAttrs -->
<#macro mapToAttrs map={} >
    <#list map?keys as key> 
       ${key}="${map[key]}" 
    </#list>
</#macro>

<#-- listToMap -->
<#macro listToMap list=[] keyfield="" valuefield="" >
    <#list list as obj> 
       <#list obj?keys as key> 
	       ${key}="${obj[key]}" 
	    </#list>
    </#list>
</#macro>

<#-- 获取名称，通过传入map数据，获取名称，map的key与value判断，map的value是显示的名称-->
<#macro getName options={} value="" showcode="false">
	<#if value=="">
		<#return />
	</#if>
	<#list options?keys as key>
		<#if value==key ><#local _ftl_getName_localname_ar=options[key] /></#if>
    </#list>
    
    <#if ! _ftl_getName_localname_ar?? >
    	<#local _ftl_getName_localname_ar=value />
    </#if>
    <#if showcode=="true">${value}-</#if>${_ftl_getName_localname_ar}
</#macro>

<#-- 根据map创建，获取value的方法
     map的key和value 的内部不准出现双引号 -->
<#macro js_mapGetValue function_name="" map={} showcode="false">
    <#if function_name!="">
    var ${function_name} = 
    </#if>
    function(val){
    	
    	<#-- 增加对null值的判断 2018-01-10解开这段注释并将val='-' 改成val=''-->
    	if(val == null) {
			val = '';
			return val;
		}
		
		
        <#if  value?? && value?string=="">
            return "";
        </#if>
        var dest = val;
        
        <#list map?keys as key> 
            <#if key_index==0>
                if("${key}"==val){
                    dest = "${map[key]}";
                }
            </#if>
            <#if map?size gt 1 && key_index gte 1 >
                else if("${key}"==val){
                    dest = "${map[key]}";
                }
            </#if>
        </#list>
        
        <#if showcode?? && showcode=="true">
            dest = val+"-"+dest;
        </#if>
        return dest;
    }
</#macro>

<#-- 根据list对象创建，获取value的方法,keyField判断的key字段，valueField判断的value字段
     list对象的key和value 的内部不准出现双引号 -->
<#macro js_listGetValue function_name="" list=[] keyField="" valueField="" showcode="false">
	<#local map=ar_("listToMap",list,keyField,valueField) />
	<@js_mapGetValue function_name=function_name map=map showcode=showcode />
</#macro>

<#-- 转换js方法名，没有括号，添加括号。此方法一般作用于html元素的onclick方法上
    包含左右括号，而且)、;、}不是最后一个字符,则添加括号
 -->
<#macro js_funcEvent func="" params="">
    <#if func?? && func!="">
        <#local funcStr=func?string?trim />
        <#local length=funcStr?length />
        <#local last=funcStr?substring(length-1,length) />
        <#if last==")" || last=="}" || last==";">${funcStr}<#else>${funcStr}(<#if params?? && params!="">${params}<#else>this</#if>)</#if></#if></#macro>

<#-- icon -->
<#macro icon fa="">
    <i class="fa fa-${fa}"></i>
</#macro>

<#-- 说明内部code如何使用 -->
<#macro c>
	<code>
		<#-- ${'<div class="btn" >div</div>'?html} 显示html标签 -->
		<#-- 内部有ftl的标签，可用${"<@panel></@panel>"}${"<#if></#if>"}的形式 -->
		<#-- ${r'${obj.name}'}可显示${}符号 -->
		<#nested/>
	</code>
</#macro>

<#-- 返回顶部按钮 -->
<#macro gotop>
<script type="text/javascript">
    $(function(){
        $("body").append('<a class="ar-gotop" href="javascript:scrollTo(0,0);" >返回顶部</a>');
    });
</script>
</#macro>
