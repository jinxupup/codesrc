<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav <#--navbar-collapse--> ">
        <ul class="nav" id="side-menu">
            <#--
            <li class="sidebar-search">
                <div class="input-group custom-search-form">
                    <input type="text" class="form-control" placeholder="Search...">
                    <span class="input-group-btn">
                    <button class="btn btn-default" type="button">
                        <i class="fa fa-search"></i>
                    </button>
                </span>
                </div>
            </li>
            -->
            <#if ar_('appHolder','webapp.welcome')?? && ar_('appHolder','webapp.welcome')!='' >
            <li>
                <a href="${base}/${ar_('appHolder','webapp.welcome')}" class="J_menuItem"><i class="fa fa-dashboard "></i> <span>首页</span></a>
            </li>
            </#if>
            
            <#macro userMenuTreeRender resourceChildren="" resourceLevel=1>
                <#list resourceChildren as tree>
                    <#local resourceLevel=resourceLevel+1>
                    <li>
                        <#if tree.t.type=='M'>
                            <a href="${(tree.t.href)!'none'}" class="J_menuItem"><i class="fa fa-${(tree.t.icon)!}"></i> <span>${tree.name}</span></a>
                        </#if>
                        <#if tree.t.type=='P'>
                            <a href="#"><i class="fa fa-${(tree.t.icon)!}"></i> <span>${tree.name}</span><span class="fa arrow"></span></a>
                            <ul class="nav nav-${resourceLevel}-level">
                            	<#if ! (tree.children)?? >
                            		</ul></li>
				            		<#return />
				            	</#if>
                                <@userMenuTreeRender resourceChildren=tree.children resourceLevel=resourceLevel />
                            </ul>
                            <#-- /.nav-second-level -->
                        </#if>
                    </li>
                    <#local resourceLevel=resourceLevel-1>
                </#list>
            </#macro>
            
            <#if (userMenuTree.children)??>
                <@userMenuTreeRender resourceChildren=userMenuTree.children />
            </#if>
            
            
        </ul>
    </div>
    <#-- /.sidebar-collapse -->
</div>
<#-- /.navbar-static-side -->