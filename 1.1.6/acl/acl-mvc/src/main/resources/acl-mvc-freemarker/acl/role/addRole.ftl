<#include "/layout.ftl">
<@body>
    <@panel head="角色新增" >
        <@panelBody>
            <@form id="form" action="acl/role/add"  after="after">  <#-- success_url="acl/role/editpage?roleId={{res.obj.roleId}}" -->
                <@field hidden="true">
                    <@input name="roleId" />
                </@field>
                <@row>
                    <@field label="角色名称">
                        <@input id="roleName" name="roleName"  valid={"notempty":"true","notempy-message":"角色名不能为空","stringlength":"true","stringlength-max":"40"} />
                    </@field>
                    <@field label="备注">
                        <@input name="remark" valid={"stringlength":"true","stringlength-max":"400"} />
                    </@field>
                </@row>                
                <@row>
                    <@toolbar>
                        <@submitButton id="addBtn"/> 
                        <@backButton />
                    </@toolbar>
                </@row>
            </@form> 
                       
        </@panelBody>
    </@panel>
 

 	<script type="text/javascript">
 		var after = function(res){
 			alert(res.msg);		
 			if(res.s){
	 			ar_.go('${base}/acl/role/editpage?roleId='+res.obj.roleId);
 			}else{
 				ar_.buttonDisable("addBtn",false);
 			}
 		}
 	</script>
 
 </@body>   