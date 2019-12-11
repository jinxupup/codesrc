<#include "/layout.ftl">

	<@body>
		<@panel head="异常流程申请件管理">
			<@panelBody>
		 		<@form id="queryForm" style="padding-top:10px;">
				<@row>
					<@field label="申请编号" >
						 <@input id="appNo" name="query.appNo" />
					</@field>
					<@field label="审批状态">
						 <@dictSelect id="rtfState" dicttype="RtfState" name="query.rtfState" />
					</@field>
				</@row>
				<@row>
					<@field label="证件类型">
						<@dictSelect dicttype="IdType" id="idType" name="query.idType"/>
					</@field>
					<@field label="证件号">
						 <@input id="idNo" name="query.idNo" />
					</@field>
					<@toolbar align="left"> &nbsp;&nbsp;&nbsp;
						<@button id="queryButton" name="查询" fa="search"/>
				    </@toolbar>
				</@row>
		 		</@form>
			 <@table id="dataTable" url="abnormalProcess/abnormalProcessApplyQuery" form_id="queryForm" button_id="queryButton" >       
                <@th title="申请编号" field="appNo"/>
                <@th title="证件类型" field="idType" render="true">
                	 <@thDictName dicttype="IdType"  value="{{row.idType}}" showcode="true"/>
                </@th> 
                <@th title="证件号码" field="idNo" />
                <@th title="申请类型" field="appType" render="true">
                	 <@thDictName dicttype="AppType"  value="{{row.appType}}" showcode="true"/>
                </@th> 
                <@th title="审批状态" field="rtfState" render="true">
                	 <@thDictName dicttype="RtfState"  value="{{row.rtfState}}" showcode="true"/>
                </@th> 
                <#--<@th title="任务ID" field="taskId"/>
                <@th title="任务名" field="taskName"/>
                <@th title="任务节点名" field="actId"/>
                -->
                <@th title="异常原因" field="excMsg" />
                <@th title="修改时间" field="updateDate" render="true">
                	<@thDate value="{{row.updateDate}}" datetype="datetime"/>
                </@th>
                <@th title="操作" field="oper" render="true">
	                <@ajaxButton name="发起流程" url="abnormalProcess/execution?appNo={{row.appNo}}" success_url="abnormalProcess/abnormalProcessApply" />
                	<@href href="/cas_activiti/handleTask?appNo={{row.appNo}}&detailFlag=Y" name="详细" />
                </@th>
            </@table>
			</@panelBody>
		</@panel>
		
		<script type="text/javascript">
		</script>
	</@body>