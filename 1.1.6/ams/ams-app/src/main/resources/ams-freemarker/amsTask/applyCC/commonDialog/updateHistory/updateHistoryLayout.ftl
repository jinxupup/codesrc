<#include "/layout.ftl"/>
<@body>
<#--修改历史信息弹出框-->
<@panel>
    <@panelBody style="padding-left:10px;">
         <@pureTable>
 			<tr>
				<th>变更表名</th>
				<th>变更列名</th>
				<th>变更前的值</th>
				<th>变更后的值</th>
				<th>变更人</th>
				<th>变更时间</th>
 			</tr>
 			<#list tmAppModifyHisList as tmAppModifyHis>
 				<tr>
 					<td>${(tmAppModifyHis.tableName)!}</td>
 					<td>${(tmAppModifyHis.reservedField1)!}</td>
 					<td>${(tmAppModifyHis.oldValue)!}</td>
 					<td>${(tmAppModifyHis.newValue)!}</td>
 					<td>${(tmAppModifyHis.updateUser)!}</td>
 					<td>${(tmAppModifyHis.updateTime?datetime)!}</td>
 				</tr>
 			</#list>
	   	 </@pureTable>
    </@panelBody>
</@panel>
    
</@body>