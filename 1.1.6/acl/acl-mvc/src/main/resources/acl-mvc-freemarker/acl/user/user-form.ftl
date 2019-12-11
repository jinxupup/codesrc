<#include "/layout.ftl"/>
<@body>
<@panel head="用户信息">
	<@panelBody>
		
	<@form id="form" action="${isEdit?string('acl/user/edit','acl/user/add')}" before="beforeSubmit" success_url="acl/user/page">
    	
    	<@fieldset legend="状态信息">
    	<@field hidden="true">
            <@input name="userId" value="${(user.userId)!}"/>
        </@field>
		<@row>
			<@field label="登陆名">
				<@input name="userNo" id="userNo"  value="${(user.userNo)!}" readonly="${isEdit?string}" valid={"notempty":"true","stringlength":"true","stringlength-max":"40"}/>
			</@field>
			<@field label="姓名">
				<@input name="userName"  value="${(user.userName)!}" valid={"notempty":"true","stringlength":"true","stringlength-max":"40"}/>
			</@field>
		</@row>
		<@row>
            <@field label="所属分支机构">
            	<#--<@input name="branchCode"  value="${(user.branchCode)!}" valid={"notempty":"true","stringlength":"true","stringlength-max":"40"}/>-->
                <@multipleSelect id="branchCode" name="branchCode" value="${(user.branchCode)!}" 
                	options=dict_('tableMap','branchMap','','') valid={"notempty":"true","stringlength":"true","stringlength-max":"40"} showfilter="true" single="true" />
            </@field>
            <@field label="用户状态">
                <@dictSelect dicttype="USER_STATUS" name="status"  value="${(user.status)!}" valid={"notempty":"true"} nullable="false"/>
            </@field>
        </@row>
        <@row>
            <@field label="工作状态">
                <@dictSelect dicttype="WORK_STATUS" name="workStatus" value="${(user.workStatus)!}"  valid={"stringlength":"true"}/>
            </@field>
            <@field label="用户类型">
                <@dictSelect dicttype="USER_TYPE" name="userType"  value="${(user.userType)!}" readonly="${isEdit?string}" valid={"notempty":"true"}/>
            </@field>
        </@row>
		</@fieldset> 
        <@fieldset legend="用户信息">
        <@row>
            <@field label="身份证号">
                <@input name="idNo"  value="${(user.idNo)!}" valid={"stringlength":"true","stringlength-max":"20"}/>
            </@field>
            <@field label="国家代码">
                <@input name="ctryCd"   value="${(user.ctryCd)!}" valid={"stringlength":"true","stringlength-max":"3"}/>
            </@field>
        </@row>
        <@row>
            <@field label="省">
                <@input name="province" value="${(user.province)!}" valid={"stringlength":"true","stringlength-max":"40"}/>
            </@field>
            <@field label="市">
                <@input name="city"  value="${(user.city)!}" valid={"stringlength":"true","stringlength-max":"40"}/>
            </@field>
        </@row>
        <@row>
            <@field label="区/县">
                <@input name="zone"  value="${(user.zone)!}" valid={"stringlength":"true","stringlength-max":"40"}/>
            </@field>
            <@field label="地址">
                <@input name="empAdd"   value="${(user.empAdd)!}" valid={"stringlength":"true","stringlength-max":"200"}/>
            </@field>
        </@row>
        <@row>
            <@field label="办公电话">
                <@input name="phone"  value="${(user.phone)!}" valid={"stringlength":"true","stringlength-max":"20"}/>
            </@field>
            <@field label="任职部门">
                <@input name="depapment"   value="${(user.depapment)!}"  valid={"stringlength":"true","stringlength-max":"80"}/>
            </@field>
        </@row>
        <@row>
            <@field label="职务">
                <@input name="post"  value="${(user.post)!}" valid={"stringlength":"true","stringlength-max":"1"}/>
            </@field>
            <@field label="EMAIL">
                <@input name="email"  value="${(user.email)!}"  valid={"stringlength":"true","stringlength-max":"100"}/>
            </@field>
        </@row><@row>
            <@field label="手机">
                <@input name="cellphome"  value="${(user.cellphome)!}" valid={"stringlength":"true","stringlength-max":"20"}/>
            </@field>
            <@field label="说明">
                <@input name="remark"  value="${(user.remark)!}" valid={"stringlength":"true","stringlength-max":"200"}/>
            </@field>
        </@row>
        </@fieldset>
        
		<@row>
			<@toolbar>
				<@submitButton />
				<@backButton/>
			</@toolbar>
		</@row>
	</@form>	
	<#if isEdit?string ="true" >
        <@fieldset legend="用户角色">
	        <@toolbar style="margin-top:0">
	        	<@buttonAuth code="ACL_USER_ROLE_ADD">
	        		<@button id="onClick" >用户角色添加</@button>
	        	</@buttonAuth>
	        </@toolbar>
	         <@table id="userRoleTable" url="acl/userRole/lists?userId=${user.userId}" form_id="queryForm" button_id="queryButton" pagination="false" condensed="true">
				<@th checkbox="true"/>
				<@th field="roleId" title="角色ID"></@th>
				<@th field="roleName" title="角色名称"></@th>
	            <@th title="操作" render="true">
	            	<@buttonAuth code="ACL_USER_ROLE_DELETE">
	            		<@ajaxButton id="ajaxButton" confirm="确定删除此记录？" sense="danger"  name="删除" url="acl/userRole/deleteRole?roleId={{row.roleId}}&&userId=${user.userId}"  after="refUserRole" />
	            	</@buttonAuth>
					<@button id="roleResource" name="角色资源" click="roleResource('{{row.roleId}}')"/>
				</@th>
			</@table>
        </@fieldset>
    </#if>	
	</@panelBody>
</@panel>
<script type="text/javascript">

	var beforeSubmit = function(){
		var userNo = $("#userNo").val();
		var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
　　		if(reg.test(userNo)){
			alert("登陆名不能包含汉字！");
			return false; 
		} 
	}
	
</script>
<#if isEdit?string ="true"  >
<script type="text/javascript">
	var refUserRole = function(){
		var params = {url:"${base}/acl/userRole/lists?userId=${user.userId}"};
		$("#userRoleTable").bootstrapTable("refresh",params);
	}
	
	$('#onClick').on('click', function () {
		var d = dialog({
			title: '角色成员添加',
			url: '${base}/acl/userRole/roleListPage?userId=${user.userId}',
			onclose: function(){
				var params = {url:"${base}/acl/userRole/lists?userId=${user.userId}"};
				$("#userRoleTable").bootstrapTable("refresh",params);
				this.remove();
			}
		});
		d.height(500)
		d.width(750)
		d.showModal();
		
	});
	
	
	var roleResource = function(roleId){
		var d = dialog({
			title: '该角色资源',
			url: '${base}/acl/role/roleHasResourcePage?roleId='+roleId,
			onclose: function(){
				this.remove();
			}
		});
		d.height(450)
		d.width(1220)
		d.showModal();
	};
	
 </script>
 </#if>
</@body>