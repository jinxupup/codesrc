
<#include "/layout.ftl"/>
<@body>
<div class="panel panel-primary">
  <div class="panel-heading">
    <h3 class="panel-title">开发配置菜单</h3>
  </div>
  <div class="panel-body">
    <ul>
    	<li>权限管理
    		<ul>
				<li><a href="${base}/acl/user/page">用户管理</a></li>
				<li><a href="${base}/acl/role/page">角色管理</a></li>
				<li><a href="${base}/acl/branch/page">分支机构管理</a></li>
    		</ul>
    	</li>
    	<li>系统管理
			<ul>
				<li><a href="${base}/acl/dict/page">业务字典</a></li>
				<li><a href="${base}/acl/param/page">参数管理</a></li>
				<li><a href="${base}/acl/resource/page">资源管理</a></li>
			</ul>
    	</li>
    </ul>
  </div>
</div>
</@body>