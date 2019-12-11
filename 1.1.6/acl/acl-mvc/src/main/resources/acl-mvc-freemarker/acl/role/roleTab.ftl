<#include "/layout.ftl">
<@body>
	<@tab id="roleTab" >
	    <@tabNav> 
	        <@tabTitle pane_id="pane1" active="true" title="角色编辑">
	        </@tabTitle>    
	        <@tabTitle pane_id="pane2"  title="角色用户" url="acl/userRole/page?roleId=${(tmAclRole.roleId)!}" iframe="true" iframe_height="100%">
	        </@tabTitle>
	        <@tabTitle pane_id="pane3"  title="角色资源"  url="acl/role/roleResourcePage?roleId=${(tmAclRole.roleId)!}" iframe="true" iframe_height="100%">
	        </@tabTitle>
	    </@tabNav>
	    <@tabContent> 
	        <@tabPane id="pane1" active="true">
	        	<#include "roleEdit.ftl"/>
	        </@tabPane>    
	        <@tabPane id="pane2" >
	        </@tabPane>
	        <@tabPane id="pane3" >
	        </@tabPane>
	    </@tabContent>
	</@tab>
	
	<script type="text/javascript">
		$(function(){
			var autoHeight = function(){
				ar_.autoHeight({id:"pane2",minus:75});
				ar_.autoHeight({id:"pane3",minus:75});
				setTimeout(autoHeight,200);
			}
			autoHeight();
		});
	</script>
</@body>