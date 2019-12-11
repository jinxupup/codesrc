<#include "/layout.ftl">

<@body>
	
    	  	<@form id="queryForm" cols="3">
    	  		<@row class="a" >
    		  		<@field label="授权审核类型">
                        <@dictSelect dicttype="AuditType" name="query.auditType"/>
                    </@field>
                    <@field label="审核状态">
                          <@dictSelect dicttype="AuthStatus" name="query.status"/>
                    </@field>
    		  		<@field label="角色名称">
    		  			<@input name="query.roleName"/>
    		  		</@field>
    		  		
    		  	</@row>
    		  	<@row>
    		  		<@field label="用户姓名">
                        <@input name="query.userName" />
                    </@field>
            		<@field label="审核起始日期">
						<@date id="beginDate" name="query.beginDate" datetype="date" />
					</@field>
					<@field label="审核截止日期">
						<@date id="endDate" name="query.endDate" datetype="date" />
					</@field>
					 					
    	  		</@row>
    	  		<@row>
    	  			<@field label="" label_ar="0">
               			<@toolbar>
				    			<@button id="queryButton" fa="search">查询</@button>&nbsp;&nbsp;&nbsp;
			            </@toolbar>
               		</@field> 
    	  		</@row>
    	  	</@form>
    	  <@table id="dataTable" url="acl/authAudit/myCheckedList" form_id="queryForm" button_id="queryButton" condensed="true">
    	  	<@th title="授权审核类型" field="auditType" render="true" sortable="true">
                <@thDictName dicttype="AuditType"  value="{{row.auditType}}" showcode="true" />
            </@th>
    	  	<@th title="审核状态" field="status" render="true" sortable="true">
                <@thDictName dicttype="AuthStatus"  value="{{row.status}}" showcode="false" />
            </@th>
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
            <@th title="审核时间" field="checkDate" render="true" sortable="true">
            	<@thDate value="{{row.checkDate}}" />
            </@th>
            <@th title="审核备注" field="checkRemark"></@th>
    	  </@table>
	
</@body>