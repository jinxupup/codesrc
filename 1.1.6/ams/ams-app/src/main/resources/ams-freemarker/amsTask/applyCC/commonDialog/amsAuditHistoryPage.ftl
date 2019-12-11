<#include "/layout.ftl"/>
<@body>
<#--修改历史信息弹出框-->
	<@panel>
		<@panelBody style="padding-left:10px;">
			<@pureTable>
				<tr style="width: 100%">
					<th>申请件编号</th>
					<th>客户姓名</th>
					<th>证件号码</th>
					<th>操作</th>
					<th>审批状态</th>
					<th>操作员ID</th>
					<th>创建日期</th>
					<#--<th style="width:20%">详细信息</th>-->
				</tr>
				<#list auditHistoryList as auditHistory>
					<tr>
						<td>${(auditHistory.appNo)!}</td>
						<td>${(auditHistory.name)!}</td>
						<td>${(auditHistory.idNo)!}</td>
						<td>${(auditHistory.ordType)!}</td>
						<td>${(auditHistory.rtfState)!}</td>
						<td>${(auditHistory.operatorId)!}</td>
						<td>${(auditHistory.createDate?datetime)!}</td>
						<#--<td>${(auditHistory.operatorDo)!}</td>-->
					</tr>
				</#list>
			</@pureTable>
		</@panelBody>
	</@panel>

</@body>