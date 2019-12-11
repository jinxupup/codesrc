<#include "/layout.ftl"/>
<@body>
	<@panelBody style="width:99.3%;">
		<@form id="AuditQueryForm" cols="4">
			<@row>
				<@field label="申请件编号" label_ar="8">
					<@input id="appNo" name="query.appNo" />
				</@field>
				<@field label="姓名" label_ar="8">
					<@input id="name" name="query.name" />
				</@field>
				<@field label="证件号码" label_ar="8">
					<@input id="idNo" name="query.idNo" />
				</@field>
				<@field label="操作员ID" label_ar="8">
					<@input id="operatorId" name="query.operatorId" />
				</@field>
			</@row>
			<@row>
				<@field label="开始时间" label_ar="8">
					<@date id="startDate" name="query.startDate" datetype="datetime"/>
				</@field>
				<@field label="结束时间" label_ar="8">
					<@date id="endDate" name="query.endDate" datetype="datetime"/>
				</@field>
				<@toolbar align="right" style="margin-right:120px">
					<@button id="AuditQueryButton" name="搜索" style="margin-right:5px;"/>
				</@toolbar>
			</@row>
		</@form>
		<@table  url="cas_bizAudit/AuditQueryForm" form_id="AuditQueryForm" button_id="AuditQueryButton"  page_size="50" >
			<@th title="申请编号" field="appNo" sortable="false" />
			<@th title="姓名" field="name" sortable="false"/>
			<@th title="证件号码" field="idNo" sortable="false"/>
			<@th title="操作" field="ordType" sortable="false"/>
			<@th title="审批状态" field="rtfState" sortable="false"/>
			<@th title="操作员ID" field="operatorId" sortable="false"/>
			<@th title="创建时间" field="createDate" render="true" sortable="true">
				<@thDate value="{{row.createDate}}" datetype="datetime"/>
			</@th>
			<#--<@th width="46%" title="详细信息" field="operatorDo" sortable="false"/>-->
		</@table>
	</@panelBody>
</@body>
