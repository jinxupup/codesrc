<#include "/layout.ftl"/>
<@body>
	<@panelBody style="width:99.3%;">
		<@form id="OrderMainQueryForm" cols="4">
			<@row>
				<@field label="申请件编号" label_ar="8">
					<@input id="appNo" name="query.appNo" />
				</@field>
				<@field label="证件号码" label_ar="8">
					<@input id="idNo" name="query.idNo" />
				</@field>
				<@field label="有效产品状态" label_ar="8">
					<@input id="validProductType" name="query.validProductType" />
				</@field>
			</@row>

			<@row>
				<@field label="定时器状态"  label_ar="8">
					<@dictSelect id="timerState" dicttype="TimerState"showcode='true' name="query.timerState"value="${(query.timerState)!}" />
				</@field>
				<@toolbar align="right" style="margin-right:120px">
					<@button id="AuditQueryButton" name="搜索" style="margin-right:5px;"/>
				</@toolbar>
			</@row>
		</@form>
		<@table  url="cas_orderMain/orderMainQueryForm" form_id="OrderMainQueryForm" button_id="AuditQueryButton"  page_size="10" load_auto="false">
			<@th width="8%" title="申请编号" field="appNo" sortable="true" />
			<@th width="7%" title="证件号码" field="idNo" sortable="true"/>
			<@th width="10%" title="所有的产品编号" field="allProductCds" sortable="false"/>
			<@th width="10%" title="有效的产品编号" field="validProductCds" sortable="false"/>
			<@th width="1%" title="有效产品状态" field="validProductType" sortable="false"/>
			<@th width="7%"  title="定时器状态" field="timerState" render="true" sortable="false">
				<@thDictName dicttype="TimerState" value="{{row.timerState}}"  showcode="true" />
			</@th>

			<@th width="13%" title="异常信息" field="exceptionMsg" sortable="false"/>
			<@th width="6%" title="创建时间" field="createDate" render="true" sortable="true">
				<@thDate value="{{row.createDate}}" datetype="datetime"/>
			</@th>
			<@th width="6%" title="最后修改时间" field="updateDate" render="true" sortable="true">
				<@thDate value="{{row.updateDate}}" datetype="datetime"/>
			</@th>
			<@th width="6%" title="<div style='width:40px'>操作</div>" render="true" >
				{{if row.timerState =='W'}}
					<@ajaxButton  confirm="是否修改定时器状态为 P-待处理 ?" name="转待处理"
					url="/cas_orderMain/updateOrderMainTerms?appNo={{row.appNo}}&timerState=P"
					success_url="/cas_orderMain/openOrderMainTerms" />
				{{/if}}
				{{if row.timerState =='P'}}
				<@ajaxButton  confirm="是否修改定时器状态为 W-待规则执行 ?" name="转待决策"
				url="/cas_orderMain/updateOrderMainTerms?appNo={{row.appNo}}&timerState=W"
				success_url="/cas_orderMain/openOrderMainTerms" />
				{{/if}}
			</@th>
		</@table>
	</@panelBody>
</@body>
