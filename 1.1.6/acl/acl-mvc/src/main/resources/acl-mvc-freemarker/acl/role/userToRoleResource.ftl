<#macro roleResourceTree children="" roleResourceList="" isRoot="false" sysType="">
	 <#if children?? && children?size gt 0>	 	
	 	<ul class="list-unstyled " >
	 		<#list children as child>
	 			<#if child.t.type?? && child.t.type=='P'>
	 				<li>
	 					<ul class="list-unstyled <#if isRoot="true"> col-lg-3 col-md-6"<#else> style="line-height:20px;"</#if> >
	 						<li><label class="checkbox" style="font-weight: 500;">
	 							<input id="${(child.id)!}" type="checkbox" name="roleResources" value="${(child.id)!}"
	 							 	<#if roleResourceList?? > 
									 	<#list roleResourceList as tm>
										  	<#if child.id?? && tm.resourceCode == child.id && sysType==tm.sysType > checked="checked" </#if>
									  	</#list>  
								 	</#if> />
	 							<span style="color:rgb(0, 168, 255)"><@icon fa=(child.t.icon)!/> </span> ${child.name}
	 							</label>
	 						</li>
	 						<#if (child.children)?? && child.children?size gt 0>
	 							<li style="padding-left:25px;"><@roleResourceTree children=child.children roleResourceList=tmAclRoleResourceList sysType=sysType /></li>
	 	    				</#if>
	 	    			</ul>	 					
	 				</li>		 						 				
	 	    	</#if>
	 			<#if child.t.type?? && child.t.type == 'M'>
	 				<li><label class="checkbox <#if isRoot="true"> col-lg-3 col-md-6"<#else> style="line-height:20px;"</#if>" style="font-weight: 500;">
	 						<input id="${(child.id)!}"  type="checkbox" name="roleResources" value="${(child.id)!}"
	 							<#if roleResourceList?? > 
									 <#list roleResourceList as tm>
										 <#if child.id?? && tm.resourceCode == child.id && sysType==tm.sysType> checked="checked" </#if>
									 </#list>  
								</#if> />
	 						<span style="color:rgb(0, 168, 255)"><@icon fa=(child.t.icon)! /> </span> ${child.name}
	 					</label>
	 				</li>
	 		  	 	<#if (child.children)?? && child.children?size gt 0>
	 		  	 		<li><ul class="list-inline" style="padding-left:25px;">
	 		  	 				<#list child.children as btn>	 		  	 			
	 		  	 			 		<li style="padding-right:25px; margin-top: -13px;">
	 		  	 			 			<label class="checkbox" style="font-weight: 500;">
	 		  	 			 				<input id="${btn.id}" type="checkbox" name="roleResources" value="${btn.id}" 
	 		  	 			 					<#if roleResourceList?? > 
										 			<#list roleResourceList as tm>
										 				<#if tm.resourceCode == btn.id && sysType==tm.sysType> checked="checked" </#if>
													</#list>  
												</#if> />
	 		  	 			 				<span style="color:rgb(0, 168, 255)"><@icon fa=(btn.t.icon)! /> </span> ${btn.name}
	 		  	 			 			</label>
	 		  	 			 		</li>
								</#list>
	 						</ul>
	 					</li>		
	 				</#if>
	 			</#if>
	 		</#list>	 		
	 	</ul>
	</#if>		 
</#macro>

<#if treeList?? && tmAclRoleResourceList??>
	<@hr style="margin:20px 0;"/>
	
    <#list treeList?keys as tree>
        <@fieldset legend=tree+" - "+dict_('name','SYS_TYPE',tree)>
            <@form id="${ar_('uuid')}" action="acl/role/editRoleResources"  multi_submit="true"> 
		        <@field hidden="true">
		            <@input name="roleId" value="${(tmAclRole.roleId)!}"/>
		            <@input name="sysType" value="${tree}"/>                    
		        </@field>
		        <@row style="padding-left:50px">                     
					<@roleResourceTree children=treeList[tree].children roleResourceList=tmAclRoleResourceList isRoot="true" sysType=tree/>
		        </@row>
		    </@form>	
		 </@fieldset>	          
    </#list>   
 </#if>  
