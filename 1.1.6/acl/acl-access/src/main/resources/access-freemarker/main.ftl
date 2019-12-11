<!DOCTYPE html>
<html lang="zh-CN" id="top-html">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<#-- 标题 -->
<title>${ar_('appHolder','webapp.title')}</title>

<#include "/unicorn-assets.ftl"/>

<#if ar_('appHolder','webapp.appendHead')!="" >
	<#include ar_('appHolder','webapp.appendHead') />
</#if>
<script type="text/javascript">
	function goToLoading(){
		var b = dialog({
			width: 200,
			height: 100,
			content: '<div style="text-align:center;margin-top:35px;"><div>系统加足马力在运行....请耐心等待!<img src="${base}/assets/i/loading.gif"/></div></div>',
		});
		return b;
	}
		 function goToSetPassword() {
		 	location.href = "${base}/passwordpage";
		 }
		 function alertDialog(){
        	var d = dialog({
					title: '提示信息',
					content: '初次登录请先修改密码',
					okValue: 'OK',
					ok: function(){
						goToSetPassword();
					},
					cancel: false
			});
			d.height(80)
			d.width(300)
			d.showModal();
        }
        function alertWarn(){
        	alert("登录密码即将过期，请及时修改密码。");
        }
		function alertDueDialog(){
        	var d = dialog({
					title: '提示信息',
					content: '密码已经过期，请修改密码',
					okValue: 'OK',
					ok: function(){
						goToSetPassword();
					},
					cancel: false
			});
			d.height(80)
			d.width(300)
			d.showModal();
        }
</script>

<#--定制化的图标信息 -->
<style>
<#if dict_("logoImg")??>
	.navbar-brand{
		background: url("${base}/assets/${(dict_("logoImg").value)!}") no-repeat;
		width: 300px;
		margin-left:0px;
	}
</#if>
</style>


</head>
<#if (firstLogin)?? && (firstLogin)==true>
	<body id="top-body" onload="alertDialog();">
<#else>
	<#if (overduewarn)?? && (overduewarn)==true>
		<body id="top-body" onload="alertWarn();">
	<#else>
		<#if (overdue)?? && (overdue)==true>
			<body id="top-body" onload="alertDueDialog();">
		<#else>
			<body id="top-body">
		</#if>
	</#if>
</#if>	
    <div id="wrapper">
		<#-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
		    <div class="navbar-header">
		    	<span><span class="navbar-brand"></span>
		    	<span class="navbar-brand-title">
		    		<#if dict_("webappTitle")??>
		    			${(dict_("webappTitle").value)!}
		    		<#else>
			    		${ar_('appHolder','webapp.title')}
		    		</#if>
		    	</span></span>
		    </div>
		    <#-- /.navbar-header -->
		
		    <#include "/nav/navbar-top.ftl"/>
		    
		</nav>

		
		<#include "/nav/navbar-sidebar.ftl"/>

        <div class="row" id="page-wrapper">
            <div class="page-header">
                <ol class="breadcrumb" id="page-header-title" style="">
                    <li>
                    	<#if ar_('appHolder','webapp.welcome')?? && ar_('appHolder','webapp.welcome')!='' >
                    	<i class="fa fa-dashboard fa-fw"></i> 首页
                    	</#if>
                    </li>
                </ol>
            </div>
            <div id="page-primary"> 
      		
                <iframe class="J_iframe" id="J_iframe" name="iframe0" width="100%" height="100%" 
                <#if ar_('appHolder','webapp.welcome')?? && ar_('appHolder','webapp.welcome')!='' >
                	src="${base}/${ar_('appHolder','webapp.welcome')}" 
                </#if>
                frameborder="0" seamless=""></iframe>
               
            </div>
        </div>

    </div>
    
<#if ar_('appHolder','webapp.appendBody')!="" >
	<#include ar_('appHolder','webapp.appendBody') />
</#if>
</body>
</html>
