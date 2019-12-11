<#-- 布局文件 -->
<#assign base=request.contextPath />

<#-- 页面 -->
<#macro body>
<#include "/acl-component.ftl"/>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>${ar_('appHolder','webapp.title')}</title>
		
		<#include "unicorn-assets.ftl"/>
		
		<#if ar_('appHolder','webapp.appendHead')!="" >
			<#include ar_('appHolder','webapp.appendHead') />
		</#if>
	</head>
	<body>
		<#nested />
		
		<#if ar_('appHolder','webapp.appendBody')!="" >
			<#include ar_('appHolder','webapp.appendBody') />
		</#if>
	</body>
</html>
</#macro>

<#-- 页面片段 -->
<#macro segment>
    <#nested />
</#macro>