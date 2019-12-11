<#include "/layout.ftl"/>
<@body>
	<@panel head="已完成任务">
		<@panelBody>
			<@form id="queryForm" cols="4">
				<@row>
					<@field label="推广人工号/姓名" label_ar="12">
						<@input id="spreaderInfo" name="query.spreaderInfo" />
					</@field>
					<@field label="申请件编号" label_ar="8">
						<@input id="appNo" name="query.appNo" />
					</@field>
					<@field label="姓名" label_ar="8">
						<@input id="name" name="query.name" />
					</@field>
					<@field label="证件号码" label_ar="8">
						<@input id="idNo" name="query.idNo" />
					</@field>
				</@row>
				<@row>
					<@field label="上一任务操作人" label_ar="12">
						<@input id="taskLastOpUser" name="query.taskLastOpUser" />
					</@field>
					<@field label="移动电话" label_ar="8">
						<@input id="cellPhone" name="query.cellPhone" />
					</@field>
					<@field label="单位名" label_ar="8">
						<@input id="corpName" name="query.corpName" />
					</@field>
					<@field label="申请渠道" label_ar="8">
						<@dictSelect id="applyFromType" dicttype="AppSource" name="query.appSource" />
					</@field>
				</@row>
				<@row>
					<@buttonAuth code="CAS_APPLY_COMPLETE">
					<@field label="操作员ID" label_ar="12">
						<@multipleSelect id="operatorId" name="query.operatorId" value="${(tmAclUser.userName)!}"
						options=cas_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true"/>
					</@field>
					</@buttonAuth>
					<@field label="受理网点" label_ar="8">
						<@multipleSelect id="owningBranch" name="query.owningBranch" options=cas_('tableMap','branchMap',' ')  nullable="true" showfilter="true" single="true"/>
					</@field>
					<@field label="开始时间" label_ar="8">
						<@date id="startDate" name="query.startDate" datetype="date"/>
					</@field>
					<@field label="结束时间" label_ar="8">
						<@date id="endDate" name="query.endDate" datetype="date"/>
					</@field>
				</@row>
				<@row>
					<@field label="申请件标签" label_ar="12">
						<@multipleSelect id="FlagApp" name="appFlag" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
						'{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}" showfilter="true"/>
					</@field>
					<@toolbar align="right">
						<@button id="queryButton" name="查询" fa="search" style="margin-right:15px;"/>
						<@resetButton sense="warning" name="清空" id="resetBut" style="margin-right:80px"/>
					</@toolbar>
				</@row>
			</@form>
			<@table  url="cas_tasklist/completedTaskList" form_id="queryForm" button_id="queryButton" load_auto="false"  page_size="25">
				<@th title="属性" field="approveQuickFlag" />
				<@th title="姓名" field="name" sortable="true"/>
				<@th title="申请编号" field="appNo" sortable="true" />
				<@th title="申请类型" field="appType" render="true" sortable="true">
					<@thDictName  dicttype="AppType" value="{{row.appType}}" />
				</@th>
				<@th title="申请卡产品" field="productCd" render="true" sortable="true">
					<@thGetName options=cas_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="false"/>
				</@th>
				<@th title="上一任务操作人" field="taskLastOpUser" sortable="true"/>
				<@th title="证件号码" field="idNo" sortable="true"/>
				<@th title="移动电话" field="cellPhone" sortable="true"/>
				<@th title="推广人" render="true" sortable="true">
					{{row.spreaderNo}}-{{row.spreaderName}}
				</@th>
				<@th title="单位名" field="corpName" sortable="true"/>
				<@th title="任务名" field="proName" sortable="true"/>
				<@th title="操作" render="true" >
					<@buttonAuth code="CAS_APPLY_DETAIL"><@href name="详情" href="/cas_activiti/handleTask?appNo={{row.appNo}}&detailFlag=Y"/></@buttonAuth>
					<@buttonAuth code="CAS_APPLY_UPDATE"><@href name="修改" href="/cas_activiti/handleTask?appNo={{row.appNo}}&updateFlag=Y"/></@buttonAuth>
				</@th>
			</@table>
		</@panelBody>
	</@panel>
</@body>

