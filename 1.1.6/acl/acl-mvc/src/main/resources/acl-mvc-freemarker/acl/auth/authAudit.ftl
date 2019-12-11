<#include "/layout.ftl">

<@body>
	<@panel head="授权审核">
	   <@panelBody>
    	  	<@form id="queryForm" cols="3">
    	  		<@row class="a" >
    		  		<@field label="授权审核类型">
                        <@dictSelect dicttype="AuditType" name="query.auditType"/>
                    </@field>
    		  		<@field label="角色名称">
    		  			<@input name="query.roleName"/>
    		  		</@field>
    		  		<@field label="用户姓名">
                        <@input name="query.userName" />
                    </@field>
    		  	</@row>
    		  	<@row>
    		  		
            		<@field label="申请起始日期">
						<@date id="beginDate" name="query.beginDate" datetype="date" />
					</@field>
					<@field label="申请截止日期">
						<@date id="endDate" name="query.endDate" datetype="date" />
					</@field>
    	  		</@row>
    	  		<@row>
					<@field label="" label_ar="0">
               			<@toolbar>
				    			<@button id="queryButton" fa="search">查询</@button>&nbsp;&nbsp;&nbsp;
				    			<@button id="mySubmitButton" click="mySubmit()" fa="search">提交历史</@button>&nbsp;&nbsp;&nbsp;
				    			<@button id="myCheckButton" click="myChecked()" fa="search">审核历史</@button>&nbsp;&nbsp;&nbsp;
			            </@toolbar>
               		</@field>    	  		
    	  		</@row>
    	  	</@form>
    	  <@table id="dataTable" url="acl/authAudit/list" form_id="queryForm" button_id="queryButton" condensed="true">
    	  	<@th title="授权审核类型" field="auditType" render="true" sortable="true">
                <@thDictName dicttype="AuditType"  value="{{row.auditType}}" showcode="true" />
            </@th>
    	  	<@th title="审核状态" field="status" render="true" sortable="true">
                <@thDictName dicttype="AuthStatus"  value="{{row.status}}" showcode="false" />
            </@th>
    	  	<@th title="申请提交机构" field="createBranchNo" sortable="true"></@th>
    	  	<@th title="角色名称" field="roleName" ></@th>
    	  	<@th title="用户登录名" field="userNo" ></@th>
            <@th title="用户姓名" field="userName" ></@th>
            <@th title="用户类型" field="userType" render="true" sortable="true">
                <@thDictName dicttype="USER_TYPE"  value="{{row.userType}}" showcode="false" />
            </@th>
            <@th title="备注" field="createRemark" ></@th>
            <@th title="提交用户" field="createBy"></@th>
            <@th title="提交时间" field="createDate" render="true" sortable="true">
            	<@thDate value="{{row.createDate}}" />
            </@th>
            <@th title="审核用户" field="checkBy" sortable="true"></@th>
            <@th title="审核时间" field="checkDate" render="true" sortable="true">
            	<@thDate value="{{row.checkDate}}" />
            </@th>
            <@th title="审核备注" field="checkRemark"></@th>
            <@th title="操作" render="true" >
            	<@toolbar class="">
	          		 {{if row.status == "W"}}
	                	<@ajaxButton  confirm="确定审核通过该笔授权申请？" name="通过" url="acl/authAudit/agree?id={{row.id}}" success_url="acl/authAudit/page" />
	            		<@button name="拒绝" id="refuseBtn" click="refuse('{{row.id}}')" />
	             	{{/if}}
	            	{{if row.auditType != "C"}}
	            		<@button id="roleResource" name="角色资源" click="roleResource('{{row.roleId}}')"/>
	            		<@button id="roleUsers" name="角色成员" click="roleUsers('{{row.roleId}}')"/>
	            	{{/if}}
            	</@toolbar>
            	
            </@th>
    	  </@table>
    	  
	  </@panelBody>
	</@panel>
	
<script type="text/javascript">

	var mySubmit = function(){
		var d = dialog({
			title: '提交历史记录',
			url: '${base}/acl/authAudit/mySubmitPage',
			onclose: function(){
				this.remove();
			}
		});
		d.height(450)
		d.width(900)
		d.showModal();
	};
	
	var myChecked = function(){
		var d = dialog({
			title: '审核历史记录',
			url: '${base}/acl/authAudit/myCheckedPage',
			onclose: function(){
				this.remove();
			}
		});
		d.height(450)
		d.width(900)
		d.showModal();
	};	
		
	var roleResource = function(roleId){
		var d = dialog({
			title: '该角色资源',
			url: '${base}/acl/role/roleHasResourcePage?roleId='+roleId,
			onclose: function(){
				this.remove();
			}
		});
		d.height(450)
		d.width(900)
		d.showModal();
	}; 
	
	var roleUsers = function(roleId){
		var d = dialog({
			title: '该角色成员',
			url: '${base}/acl/userRole/roleHasUserlistPage?roleId='+roleId,
			onclose: function(){
				this.remove();
			}
		});
		d.height(450)
		d.width(900)
		d.showModal();
	};
	
	
	var refuse = function(id){
		var d = dialog({
			title: '审核备注',
			url: '${base}/acl/authAudit/checkRemarkPage?logId='+id,
			onclose: function(){
				var params = {url:"${base}/acl/authAudit/list"};
				$("#dataTable").bootstrapTable("refresh",params);
				this.remove();
			}
		});
		d.height(130)
		d.width(650)
		d.showModal();
	};
	
</script>
</@body>