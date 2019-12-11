<#include "/layout.ftl">
<@body>
 
 	<@row> 
		<@field hidden="true">
            <@input id="roleId" name="roleId" value="${roleId!}"/>		                   
        </@field> 	
    	<@field label="系统类型">
			<@dictSelect id="sysType" dicttype="SYS_TYPE" value="${sysType!}" style="margin-left:15px;" change="selectSystype" /> 
		</@field>
	</@row>	
	<@fieldset id="result">
	
		<#include "roleHasResource.ftl" />
		
	</@fieldset>           
<script type="text/javascript">
	$(function(){
		$("body").css({"overflow-x":"hidden"});		
	});

	var selectSystype = function(){
		var sysType = $('#sysType').val();
		ar_.go('${base}/acl/role/roleHasResourcePage?roleId=${roleId!}&sysType='+sysType);
	}
 </script> 
 </@body>