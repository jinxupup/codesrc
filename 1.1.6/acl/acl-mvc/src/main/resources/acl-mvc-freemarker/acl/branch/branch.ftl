<#include "/layout.ftl"/>
<@body>

  <@panel head="机构管理">
    <@panelBody>
       <@form id="queryForm" >
            <@row class="a">
                <@field label="机构代码">
                    <@input name="query.branchCode" />
                </@field>
                <@field label="机构名称">
                    <@input name="query.branchName" />
                </@field>
            </@row>
       </@form>
            <@toolbar class="">
                <@button id="queryButton" fa="search">查询</@button>
                <@buttonAuth code="ACL.BRANCH_MANAGE_ADD">
                	<@href href="acl/branch/addpage" fa="plus">新增</@href>
                </@buttonAuth>
            </@toolbar>
        
        <@table id="table" url="acl/branch/list" form_id="queryForm" button_id="queryButton" condensed="true">
            <@th checkbox="true"></@th>
            <@th field="branchCode" title="机构代码"></@th>
            <@th field="branchName" title="机构名称"></@th>
            <@th field="parentCode" title="上级机构代码"></@th>
            <@th field="parentPath" title="上级机构路径"></@th>
            <@th field="branchLevel" title="层级"></@th>
            <@th field="branchType" title="类型"></@th>
            <#-- <@th field="status" title="状态"></@th> -->
            <@th field="remark" title="说明"></@th>
            <@th title="操作" width="80px" render="true">
            	<@buttonAuth code="ACL.BRANCH_MANAGE_EDIT">
	                <@href href="acl/branch/editpage?branchCode={{row.branchCode}}"  name="编辑" />
            	</@buttonAuth>
            	<@buttonAuth code="ACL.BRANCH_MANAGE_EDIT" reverse="true">
            		<@href href="acl/branch/editpage?branchCode={{row.branchCode}}&&noEdit=Y"  name="查看" />
            	</@buttonAuth>
            	
            	<@buttonAuth code="ACL.BRANCH_MANAGE_DELETE">
	                <@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="acl/branch/delete?branchCode={{row.branchCode}}" success_url="acl/branch/page" />
            	</@buttonAuth>
            </@th>
        </@table>
        
    </@panelBody>  
  </@panel>
</@body>