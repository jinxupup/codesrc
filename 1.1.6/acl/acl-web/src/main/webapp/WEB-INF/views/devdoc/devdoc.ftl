<#include "/layout.ftl">

<!DOCTYPE html>
<html lang="zh-CN" id="top-html">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<#-- 标题 -->
<title>${ar_('appHolder','webapp.title')}</title>

<#include "/unicorn-assets.ftl"/>

</head>
<body id="top-body">
    <div id="wrapper">

		<#-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
		    <div class="navbar-header">
		    	<span><span class="navbar-brand"></span><span class="navbar-brand-title">Unicorn</span></span>
		    </div>
		    <#-- /.navbar-header -->
		
		    
		</nav>


<#-- sidebar start -->
<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav <#--navbar-collapse--> ">
        <ul class="nav" id="side-menu">
            <li>
                <a href="${base}/devdoc/common/welcome.ftl" class="J_menuItem"><i class="fa fa-dashboard "></i> <span>首页</span></a>
            </li>
            <li>
                <a href="#"><i class="fa fa-arrows "></i> <span>基础</span><span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li>
                        <a href="devdoc/common/bs-grid.ftl" class="J_menuItem"><span>Grid 栅格系统</span></a>
                    </li>
                    <li>
                        <a href="devdoc/common/ui-panel.ftl" class="J_menuItem"> <span>Panels 面板</span></a>
                    </li>
                    <li>
                        <a href="devdoc/common/ui-tab.ftl" class="J_menuItem"> <span>Tab 标签页</span></a>
                    </li>
                    <li>
                        <a href="devdoc/common/ui-icons.ftl" class="J_menuItem"> <span>Icons 图标</span></a>
                    </li>
                    <li>
                        <a href="devdoc/common/ui-dialog.ftl" class="J_menuItem"> <span>Dialog 弹窗</span></a>
                    </li>
                </ul>
                <#-- /.nav-second-level -->
            </li>
            <li>
                <a href="#"><i class="fa fa-sitemap "></i> <span>多级菜单</span><span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li>
                        <a href="javascrip:void(0);"> <span>二级菜单</span></a>
                    </li>
                    <li>
                        <a href="javascrip:void(0);"> <span>二级菜单</span></a>
                    </li>
                    <li>
                        <a href="#"> <span>二级菜单</span><span class="fa arrow"></span></a>
                        <ul class="nav nav-third-level">
                            <li>
                                <a href="javascrip:void(0);"> <span>三级菜单</span></a>
                            </li>
                            <li>
                                <a href="#"> <span>三级菜单</span><span class="fa arrow"></span></a>
                                <ul class="nav nav-fourth-level">
		                            <li>
		                                <a href="javascrip:void(0);"> <span>四级菜单</span></a>
		                            </li>
		                            <li>
		                                <a href="javascrip:void(0);"> <span>四级菜单</span></a>
		                            </li>
		                        </ul>
                            </li>
                        </ul>
                        <#-- /.nav-third-level -->
                    </li>
                </ul>
                <#-- /.nav-second-level -->
            </li>
            <li>
                <a href="#"><i class="fa fa-book"></i> 开发文档<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <ul class="nav nav-second-level">
	                    <li>
	                        <a href="#">web组件 <span class="fa arrow"></span></a>
	                        <ul class="nav nav-third-level">
	                        	<li>
	                                <a href="devdoc/webtag/common.ftl" class="J_menuItem">公共功能</a>
	                            </li>
	                            <li>
	                                <a href="devdoc/webtag/grid.ftl" class="J_menuItem">布局</a>
	                            </li>
	                            <li>
	                                <a href="devdoc/webtag/form.ftl" class="J_menuItem">表单</a>
	                            </li>
	                            <li>
                                    <a href="devdoc/webtag/button.ftl" class="J_menuItem">按钮</a>
                                </li>
	                            <li>
	                                <a href="devdoc/webtag/table.ftl" class="J_menuItem">表格</a>
	                            </li>
	                             <li>
                                    <a href="devdoc/webtag/dict.ftl" class="J_menuItem">业务字典</a>
                                </li>
	                        </ul>
	                        <#-- /.nav-third-level -->
	                    </li>
	                </ul>
	                <ul class="nav nav-second-level">
                        <li>
                            <a href="#">控制层 <span class="fa arrow"></span></a>
                            <ul class="nav nav-third-level">
                                <li>
                                    <a href="devdoc/controller/base-controller.ftl" class="J_menuItem">BaseController</a>
                                    <a href="devdoc/controller/ar_-controller.ftl" class="J_menuItem">Ar_Controller</a>
                                </li>
                            </ul>
                            <#-- /.nav-third-level -->
                        </li>
                    </ul>
                </ul>
                <#-- /.nav-second-level -->
            </li>
            <li>
                <a href="#"><i class="fa fa-cogs "></i> 开发常用<span class="fa arrow"></span></a>
                <ul class="nav nav-second-level">
                    <li>
                        <a href="devdoc/common/useful.ftl" class="J_menuItem">开发配置菜单</a>
                    </li>
                </ul>
                <#-- /.nav-second-level -->
            </li>
        </ul>
    </div>
    <#-- /.sidebar-collapse -->
</div>
<#-- /.navbar-static-side -->
<#-- sidebar end -->

        <div class="row" id="page-wrapper">
            <div class="page-header">
                <ol class="breadcrumb" id="page-header-title" style="">
                    <li>
                    	<i class="fa fa-dashboard fa-fw"></i> 首页
                    </li>
                </ol>
            </div>
            <div id="page-primary"> 
                <iframe class="J_iframe" id="J_iframe" name="iframe0" width="100%" height="100%" 
                	src="${base}/devdoc/common/welcome.ftl" 
                	frameborder="0" seamless=""></iframe>
            </div>
        </div>

    </div>
</body>
</html>


