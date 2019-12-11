<#include "/layout.ftl"/>
<@body>
	<@panelBody style="width:99.3%;">
		<@form id="AuditQueryForm" cols="4">
			<@row>
				<@field label="操作对象" label_ar="8">
					<@input id="operatorId" name="query.operatorId" />
				</@field>
				<@field label="修改内容" label_ar="8">
					<@input id="paramKey" name="query.paramKey" />
				</@field>
				<@field label="操作类型" label_ar="8">
					<@input id="paramOperation" name="query.paramOperation" />
				</@field>
				<@field label="修改人" label_ar="8">
					<@input id="updateUser" name="query.updateUser" />
				</@field>
			</@row>
			<@row>
				<@field label="开始时间" label_ar="8">
					<@date id="beginDate" name="query.beginDate" datetype="datetime"/>
				</@field>
				<@field label="结束时间" label_ar="8">
					<@date id="endDate" name="query.endDate" datetype="datetime"/>
				</@field>
				<@toolbar align="right" style="margin-right:120px">
					<@button id="AuditQueryButton" name="搜索" style="margin-right:5px;"/>
				</@toolbar>
			</@row>
		</@form>

		<@table id="dataTable" url="tmSystemAudit/AllSystemAuditList" form_id="AuditQueryForm" button_id="AuditQueryButton"  page_size="20" >
			<@th title="操作对象" field="operatorId" sortable="true"/>
			<@th title="修改内容" field="paramKey" sortable="true"/>
			<@th title="操作类型" field="paramOperation" sortable="true"/>
			<@th title="修改前" render="true">
				{{if row.oldObject!=''}}
				<@button id="oldObjectButton" name="详细信息" click="insertOld(id={{row.id}})"/>
				{{/if}}
			</@th>
			<@th title="修改后" render="true">
				{{if row.newObject!=''}}
				<@button id="newObjectButton" name="详细信息" click="insertNew(id={{row.id}})"/>
				{{/if}}
			</@th>
			<@th title="修改人" field="updateUser" sortable="true"/>
			<@th title="修改日期" field="updateDate" render="true" width="160px" sortable="true">
				<@thDate value="{{row.updateDate}}" datetype="datetime" />
			</@th>
		</@table>

		<#--<@table  url="cas_bizAudit/AuditQueryForm" form_id="AuditQueryForm" button_id="AuditQueryButton"  page_size="50" >-->
			<#--<@th width="10%" title="申请编号" field="appNo" sortable="false" />-->
			<#--<@th width="4%" title="姓名" field="name" sortable="false"/>-->
			<#--<@th width="10%" title="证件号码" field="idNo" sortable="false"/>-->
			<#--<@th width="10%" title="操作" field="ordType" sortable="false"/>-->
			<#--<@th width="6%" title="审批状态" field="rtfState" sortable="false"/>-->
			<#--<@th width="5%" title="操作员ID" field="operatorId" sortable="false"/>-->
			<#--<@th width="9%" title="创建时间" field="createDate" render="true" sortable="true">-->
				<#--<@thDate value="{{row.createDate}}" datetype="datetime"/>-->
			<#--</@th>-->
			<#--<@th width="46%" title="详细信息" field="operatorDo" sortable="false"/>-->
		<#--</@table>-->


	</@panelBody>

	<script>
		function insertOld(id){
			b = top.dialog({
				title: '修改前数据',
				width:600,
				height:400,
				url:'tmSystemAudit/getPreRecord?id=' + id,
				oniframeload:function(){},
				button:[
					{
						value: '确定',
						callback: function () {
							this.close();
							return false;
						},
						autofocus: true
					},
				]
			});
			b.showModal();
		}

		function insertNew(id){
			b = top.dialog({
				title: '修改后数据',
				width:600,
				height:400,
				url:'tmSystemAudit/getAfterRecord?id=' + id,
				oniframeload:function(){},
				button:[
					{
						value: '确定',
						callback: function () {
							this.close();
							return false;
						},
						autofocus: true
					},
				]
			});
			b.showModal();
		}
	</script>
</@body>
