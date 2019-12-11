<#include "/layout.ftl"/>
<@body>
<@panel head="管理员操作界面">
	<@form id="form" action="/acl/session/control" method="post">
	<@toolbar>
		 <@submitButton name="清空会话缓存" />
	</@toolbar>
	</@form>
</@panel>
</@body>