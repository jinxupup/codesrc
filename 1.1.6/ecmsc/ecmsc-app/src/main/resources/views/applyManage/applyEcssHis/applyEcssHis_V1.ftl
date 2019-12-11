<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panelBody>
		<@form id="queryForm" cols="4">
        	<@row >
        	    <@field label="申请编号：">
        	        <@input name="query.appNo" value="${(tmAppImageHistory.appNo)!}"/>
        	    </@field>
        	    <@field label="客户姓名：">
        	        <@input name="query.name" value="${(tmAppImageHistory.name)!}"/>
        	    </@field>
        	    <@field label="证件类型：">
        	       <@dictSelect dicttype="IdType" name="query.idType" value="${(tmAppImageHistory.idType)!}" />
        	    </@field>
        	    <@field label="证件号码：">
        	        <@input name="query.idNo" value="${(tmAppImageHistory.idNo)!}"/>
        	    </@field>
        	</@row>
        	<@row >
        	    <@field label="任务编号：">
        	        <@input name="query.taskNum" value="${(tmAppImageHistory.taskNum)!}"/>
        	    </@field>
        	    <@field label="调阅人：">
        	        <@input name="query.operatroId" value="${(tmAppImageHistory.operatroId)!}"/>
        	    </@field>       
        	        <@button id="queryButton" name="查询" fa="search"></@button>
        	</@row>           	
		</@form>
		</br>
		<@table url="ecssHis/list" form_id="queryForm" button_id="queryButton" page_size="10">
    	  	<@th title="申请编号" field="appNo"></@th>
    	  	<@th title="客户姓名" field="name"></@th>
    	  	<@th title="证件类型" field="idType"></@th>
    	  	<@th title="证件号码" field="appNo"></@th>
    	  	<@th title="任务编号" field="taskNum"></@th>
            <@th title="影像批次号" field="appNo"></@th>
            <@th title="调阅人" field="operatorId"></@th>
            <@th title="调阅时间" field="operateTime"render="true" >
					<@thDate value="{{row.operateTime}}" />      
            </@th>
    	  </@table>
	</@panelBody>
</@body>