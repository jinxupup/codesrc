<#include "/layout.ftl">
<@body>
 
 		<@field hidden="true">
            <@input name="flag" value="${flag!}"/>
        </@field>
    <@row> 
    	<@field label="系统类型">
			<@dictSelect id="sysType" dicttype="SYS_TYPE" value="${sysType!}" style="margin-left:15px;" change="selectSystype" /> 
		</@field>
		<@field hidden="true">
            <@input id="roleId" name="roleId" value="${(tmAclRole.roleId)!}"/>		                   
        </@field>  	
	</@row>
	<@fieldset id="result">
	<#if flag?? && flag == "U">
		<#include "userToRoleResource.ftl" />
	<#else>
		<#include "roleResource.ftl" />
	</#if>
		
	</@fieldset>           

<script type="text/javascript">
	$(function(){
		$("body").css({"overflow-x":"hidden"});		
	});

	var selectSystype = function(){
		var sysType = $('#sysType').val();
		ar_.go('${base}/acl/role/roleResourcePage?roleId=${(tmAclRole.roleId)!}&sysType='+sysType);
	}
 </script>
 
 </@body>