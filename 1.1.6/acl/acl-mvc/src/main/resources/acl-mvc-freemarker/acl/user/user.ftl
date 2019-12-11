<#include "/layout.ftl"/>
<@body>
  <@panel head="用户管理">
	<@panelBody>
	   <@form id="queryForm" cols="3">
            <@row class="a">
                <@field label="登陆名">
                    <@input name="query.userNo" />
                </@field>
                <@field label="用户姓名">
                    <@input name="query.userName" />
                </@field>
                <@field label="" label_ar="0">
                <@toolbar class="">
	                <@button id="queryButton" fa="search">查询</@button>&nbsp;&nbsp;&nbsp;
	                <@href href="acl/user/addpage" fa="plus">新增</@href>&nbsp;&nbsp;&nbsp;
					
					<@buttonAuth code="SYNC_BMP_USERS">
						<@button name="同步核心用户信息" click="syncBmpUsers()"/>
					</@buttonAuth>
	            </@toolbar>
                </@field>
            </@row>
       </@form>
		<@table id="table" url="acl/user/list" form_id="queryForm" button_id="queryButton">
			<@th field="userNo" title="登录名" sortable="true"></@th>
			<@th field="userName" title="用户姓名" sortable="true"></@th>
			<@th field="branchCode" title="所属分支机构" sortable="true"></@th>
            <@th field="status" title="用户状态" render="true" sortable="true">
                <@thDictName dicttype="USER_STATUS" value="{{row.status}}" />
            </@th>
            <@th field="workStatus" title="工作状态" render="true">
            	<@thDictName dicttype="WORK_STATUS" value="{{row.workStatus}}" />
            </@th>
            <@th field="userType" title="用户类型" render="true" sortable="true">
            	<@thDictName dicttype="USER_TYPE" value="{{row.userType}}" />
            </@th>
			<@th title="操作" render="true">
			  <@toolbar class="">
				<@href href="acl/user/editpage?userId={{row.userId}}"  name="编辑" fa="edit" />
				<@ajaxButton confirm="确认重置密码？" url="acl/user/resetpassword?userNo={{row.userNo}}"  name="重置密码" fa="unlock" sense="danger" success_url="acl/user/page"/>
				{{if row.userNo != "IT"}}
					<#--放开删除按钮并增加权限 -->
					<@buttonAuth code="ACL_USER_DELETE">
	                	<@ajaxButton  confirm="确定删除此记录？" sense="danger" name="删除" url="acl/user/delete?userId={{row.userId}}" success_url="acl/user/page" />
	                </@buttonAuth>
				{{/if}}
				
              </@toolbar>
			</@th>
		</@table>
		
	</@panelBody>  
  </@panel>
    <script type="text/javascript">
		<#--确认同步核心产品参数 -->
		function syncBmpUsers(){
			if(confirm("是否确认[同步核心用户]？如果同步则需要关注如下：\r\n 1.新同步过来的用户是没有对应角色权限的，需要再次添加\r\n 2.(当前)审批系统已有用户的所属机构网点可能被更新，需要重点关注")){
				window.location.href="${base}/userParam/syncBmpUsers";
			}	
		}
	</script>
</@body>