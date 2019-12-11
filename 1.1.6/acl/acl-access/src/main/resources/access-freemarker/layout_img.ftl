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
		
		
		<#-- title小图标 -->
		<link rel="shortcut icon" href="assets/i/favicon.ico">
		<script type="text/javascript" src="${base}/assets/js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="${base}/assets/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${base}/assets/js/jqueryui.js"></script>
		<script type="text/javascript" src="${base}/assets/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="${base}/assets/js/mousewheel.js"></script>
		<script type="text/javascript" src="${base}/assets/js/iviewer.js"></script>
		<script type="text/javascript" src="${base}/assets/js/jquery.Jcrop.min.js"></script>
		<script type="text/javascript" src="${base}/assets/js/screenage.js"></script>
		<script type="text/javascript" src="${base}/assets/js/ui.tooltip.js"></script>
		
		<link type="text/css" rel="stylesheet" href="${base}/assets/css/custom.css" />
		<link type="text/css" rel="stylesheet" href="${base}/assets/css/iviewer.css" />
		<link type="text/css" rel="stylesheet" href="${base}/assets/css/jcrop.css" />
		
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