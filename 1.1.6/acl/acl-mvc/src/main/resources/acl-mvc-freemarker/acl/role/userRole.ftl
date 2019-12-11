 <#include "/layout.ftl">
	<@body>
        <@toolbar style="margin-top:0">
        	<@buttonAuth code="ACL_ROLE_USER_EDIT">
            	<@button id="onClick" >角色成员添加</@button>
            </@buttonAuth>
        </@toolbar>
         <@table id="userRoleTable" url="acl/userRole/list?roleId=${tmAclRole.roleId}" form_id="queryForm" button_id="queryButton" pagination="false" condensed="true">
			<@th checkbox="true"/>
			<@th field="userNo" title="用户登录名"></@th>
			<@th field="userName" title="姓名"></@th>
            <@th field="status" title="用户状态" render="true">
                <@thDictName dicttype="USER_STATUS" value="{{row.status}}" />
            </@th>
            <@th field="depapment" title="任职部门"></@th>
            <@th field="post" title="职务"></@th>
            <@th title="操作" render="true">
            	<@buttonAuth code="ACL_ROLE_USER_EDIT">
            		<@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="acl/userRole/delete?userId={{row.userId}}&&roleId=${tmAclRole.roleId}"  after="refUserRole" />
				</@buttonAuth>
			</@th>
		</@table>

<script type="text/javascript">
	var refUserRole = function(){
		//var query = $("#datatable").serializeObject();
		//$("#datatable").bootstrapTable("getOptions").customerQuery = query;
		var params = {url:"${base}/acl/userRole/list?roleId=${tmAclRole.roleId}"};
		$("#userRoleTable").bootstrapTable("refresh",params);
	}


	$('#onClick').on('click', function () {
		var d = dialog({
			title: '角色成员添加',
			url: '${base}/acl/userRole/userList?roleId=${tmAclRole.roleId}',
			onclose: function(){
				var params = {url:"${base}/acl/userRole/list?roleId=${tmAclRole.roleId}"};
				$("#userRoleTable").bootstrapTable("refresh",params);
				this.remove();
			}
		});
		d.height(450)
		d.width(750)
		d.showModal();
		
	});
	
 </script>
 
 </@body>