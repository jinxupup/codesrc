<#include "/layout.ftl"/>
<@body>
	<#--案件流转记录弹出框-->
	<@panel>
	    <@panelBody style="padding-left:10px;">
	        <@pureTable>
	 			<tr>
					<th>转移类型</th>
					<th>任务名</th>
					<th>审批状态</th>
					<th>任务所属人</th>
					<th>任务分配人</th>
					<th>获取时间</th>
					<th>结束时间</th>
					<th>状态</th>
	 			</tr>
	 			<#if tmTaskTransferList??>
		 			<#list tmTaskTransferList as tmTaskTransfer>
		 				<tr>
		 					<td width="100">${(tmTaskTransfer.transferType)!}</td>
		 					<td width="100">${(tmTaskTransfer.taskName)!}</td>
		 					<td width="50">${(tmTaskTransfer.rtfState)!}</td>
		 					<td width="100">${(tmTaskTransfer.owner)!}</td>
		 					<td width="100">${(tmTaskTransfer.assigner)!}</td>
		 					<td width="150">${(tmTaskTransfer.claimTime?datetime)!}</td>
		 					<td width="150">${(tmTaskTransfer.endTime?datetime)!}</td>
		 					<td width="100">${(tmTaskTransfer.status)!}</td>
		 				</tr>
		 			</#list>
		 		</#if>
		   	 </@pureTable>
	    </@panelBody>
	</@panel> 
</@body>