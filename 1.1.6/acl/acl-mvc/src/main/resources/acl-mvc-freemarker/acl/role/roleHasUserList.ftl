<#include "/layout.ftl"/>
<@body>
		<@form id="queryForm" >
	            <@row>
	                <@field label="登陆名">
	                    <@input name="query.userNo" />
	                </@field>
	                <@field label="用户姓名">
	                    <@input name="query.userName" />
	                </@field>
	                <@field label_ar="1" input_ar="30">
	                 	<@button id="queryButton" fa="search">查询</@button>
	                </@field>
	            </@row>
       	</@form>
		<@table id="table" url="acl/userRole/list?roleId=${roleId}" form_id="queryForm" button_id="queryButton" height="400" condensed="true">
			<@th checkbox="true"/>
			<@th field="userNo" title="用户登录名"></@th>
			<@th field="userName" title="姓名"></@th>
            <@th field="status" title="用户状态" render="true">
                <@thDictName dicttype="USER_STATUS" value="{{row.status}}" />
            </@th>
            <@th field="userType" title="用户类型" render="true" sortable="true">
            	<@thDictName dicttype="USER_TYPE" value="{{row.userType}}" />
            </@th>
            <@th field="depapment" title="任职部门"></@th>
            <@th field="post" title="职务"></@th>
		</@table>
		
</@body>