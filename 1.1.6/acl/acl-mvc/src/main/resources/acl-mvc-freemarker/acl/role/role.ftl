<#include "/layout.ftl">
<@body>
    
    <@panel head="角色管理">
        <@panelBody>
            <@form id="queryForm" cols="2">
                <@row>
                	<@field label="角色名称" label_ar="12" input_ar="12">
                   		 <@input name="query.roleName" />
               		</@field>
               		<@field label="" label_ar="0">
               			<@toolbar>
			                <@button id="queryButton" name="查询" fa="search"/>&nbsp;&nbsp;&nbsp;
			                <@buttonAuth code="ACL_ROLE_ADD">
                				<@href href="acl/role/addpage" name="新增" fa="plus"/>
               				</@buttonAuth>
			            </@toolbar>
               		</@field>
                </@row>
            </@form>
            <@table id="dataTable" url="acl/role/list" form_id="queryForm" button_id="queryButton" condensed="true">
                <@th checkbox="true"/>
                <@th title="角色Id" field="roleId" />
                <@th title="角色名称" field="roleName" />
                <@th title="创建时间" field="createDate" render="true">
                    <@thDate value="{{row.createDate}}" />
                </@th>
                <@th title="创建人" field="createBy" />
                <@th title="更改时间" render="true" >
                    <@thDate value="{{row.updateDate}}" />
                </@th>
                <@th title="更改人" field="updateBy" />
                <@th title="备注" field="remark" />    
                <@th title="操作" render="true" >
					<@href href="acl/role/editpage?roleId={{row.roleId}}"  name="编辑" />
					
					<#-- mantis:0017706,superuser不可删除-->
	                <#--<@ajaxButton url="acl/role/delete?roleId={{row.roleId}}" confirm="确定要删除该角色？" sense="danger" name="删除" success_url="acl/role/page" />-->
					{{if row.roleName != "root"}}
						<@buttonAuth code="ACL_ROLE_DELETE">
                        	<@ajaxButton url="acl/role/delete?roleId={{row.roleId}}" confirm="确定要删除该角色？" sense="danger" name="删除" success_url="acl/role/page" />
                    	</@buttonAuth>
                    {{/if}}
				</@th>         
            </@table>
        </@panelBody>       
    </@panel>  
</@body>
