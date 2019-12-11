            <@form id="form" action="/acl/role/edit" multi_submit="true"> 
                <@field hidden="true">
                    <@input name="roleId" value="${(tmAclRole.roleId)!}"/>
                </@field>
               	<@row>
                	<@field label="角色Id">
                       	<@input id="roleId" name="roleId" value="${(tmAclRole.roleId)!}" readonly="true" />
                   	</@field>
                </@row>
                <@row>
                    <@field label="角色名称">
                    	<#-- mantis:0017706,root用户名不可修改-->
                    	<#--<@input id="roleName" name="roleName" value="${(tmAclRole.roleName)!}" valid={"notempty":"true","notempy-message":"角色名不能为空","stringlength":"true","stringlength-max":"40"} />-->
						<#if (tmAclRole.roleName) != "root" >
							<@input id="roleName" name="roleName" value="${(tmAclRole.roleName)!}" valid={"notempty":"true","notempy-message":"角色名不能为空","stringlength":"true","stringlength-max":"40"} />
                    	<#else>
							<@input id="roleName" name="roleName" value="${(tmAclRole.roleName)!}" readonly="true"/>
                    	</#if>
                    </@field>
                    <@field label="备注">
                        <@input name="remark" value="${(tmAclRole.remark)!}" valid={"stringlength":"true","stringlength-max":"400"}/>
                    </@field>
                </@row>                
                <@row>
                    <@toolbar>
                        <@buttonAuth code="ACL_ROLE_EDIT">
                        	 <@submitButton /> 
		        		</@buttonAuth>
                        <@backButton />
                    </@toolbar>
                </@row>
            </@form> 
