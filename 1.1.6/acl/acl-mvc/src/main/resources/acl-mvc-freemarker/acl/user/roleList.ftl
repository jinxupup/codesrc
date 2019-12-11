<#include "/layout.ftl"/>
<@body>

			<@form id="queryForm" >
	            <@row>
	                <@field label="角色ID">
	                    <@input name="query.roleId" />
	                </@field>
	                <@field label="角色名称">
	                    <@input name="query.roleName" />
	                </@field>
	                <@field label_ar="1" input_ar="30">
	                 	<@button id="queryButton" fa="search">查询</@button>
	                	<@button id="save" >保存</@button>
	                </@field>
	            </@row>
       		</@form>
		
		<@table id="table" url="acl/role/list" form_id="queryForm" button_id="queryButton" height="400" condensed="true">
			<@th checkbox="true"/>
			<@th field="roleId" title="角色ID"></@th>
			<@th field="roleName" title="角色名称"></@th>
		</@table>
		<script>
			
			$("#save").on("click",function(){
				var rows = $('#table').bootstrapTable('getSelections');
            	var ids = [];
	            for(var i=0;i<rows.length;i++){
	                ids.push(rows[i]['roleId']);
	            }
	            if(ids.length==0){
	            	alert("请选择要添加的角色");
	            	return false;
	            }
	            
	            $.post("${base}/acl/userRole/addRole?userId=${userId1}",{"ids":ids.join(",")},function(res){
	            	alert(res.msg);
	            	if(res.s){
	            		var d = ar_.getDialog(parent);/* parent.dialog.get(window); */
	            		d.close().remove();
	            	}
	            },'json');
	            
			});
		
		</script>
		
</@body>