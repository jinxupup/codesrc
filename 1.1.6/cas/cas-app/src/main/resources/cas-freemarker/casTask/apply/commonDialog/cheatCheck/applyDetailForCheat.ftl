<#include "/layout.ftl"/>
<@body>
<@gotop/>
<@panel>
    <@panelBody  style="padding-left:10px;">
        <@fieldset legend="申请中的详细信息:">
	        <@pureTable>
   	 			<tr>
   	 				<th width="6%">姓名</th>
   	 				<th>申请编号</th>
					<th>证件号码</th>
					<th>申请人手机号</th>
					<th>家庭电话</th>
					<th>家庭地址</th>
					<th>公司名称</th>
					<th>公司电话</th>
					<th>公司地址</th>
   	 			</tr>
   	 			<#list applyCheatDetailDtoCur as applyCheatDetailDto>
   	 				<tr>
   	 					<td>${(applyCheatDetailDto.name)!}</td>
   	 					<td>${(applyCheatDetailDto.appNo)!}</td>
   	 					<td>${(applyCheatDetailDto.idNo)!}</td>
   	 					<td>${(applyCheatDetailDto.cellPhone)!}</td>
   	 					<td>${(applyCheatDetailDto.homePhone)!}</td>
   	 					<td>${(applyCheatDetailDto.homeAdd)!}</td>
   	 					<td>${(applyCheatDetailDto.corpName)!}</td>
   	 					<td>${(applyCheatDetailDto.empPhone)!}</td>
   	 					<td>${(applyCheatDetailDto.empAdd)!}</td>
   	 				</tr>
   	 			</#list>
	   	 	</@pureTable>
        </@fieldset>
        <@fieldset legend="已申请人详细信息:">
             <@pureTable id="table1">
   	 			<tr ondblclick = "applyDblclick('${(applyCheatDetailDto.appNo)!}')">
   	 			    <th>姓名</th>
   	 				<th>申请编号</th>
   	 			    <th>证件号码</th>
					<th>申请人手机号</th>
					<th>家庭电话</th>
					<th>家庭地址</th>
					<th>公司名称</th>
					<th>公司电话</th>
					<th>公司地址</th>
					<th>拒绝原因</th>
					<th>任务所属人</th>
					<th>审批状态</th>
   	 			</tr>
   	 			<#list applyCheatDetailDtosOld as applyCheatDetailDto>
   	 				<tr>
   	 					<td>${(applyCheatDetailDto.name)!}</td>
   	 					<td>${(applyCheatDetailDto.appNo)!}</td>
   	 					<td>${(applyCheatDetailDto.idNo)!}</td>
   	 					<td>${(applyCheatDetailDto.cellPhone)!}</td>
   	 					<td>${(applyCheatDetailDto.homePhone)!}</td>
   	 					<td>${(applyCheatDetailDto.homeAdd)!}</td>
   	 					<td>${(applyCheatDetailDto.corpName)!}</td>
   	 					<td>${(applyCheatDetailDto.empPhone)!}</td>
   	 					<td>${(applyCheatDetailDto.empAdd)!}</td>
   	 				    <td>${(applyCheatDetailDto.refuseCode)!}</td>
	 				    <td>${(applyCheatDetailDto.owner)!}</td>
	 				   <td><@getName options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',{"type":"RtfState"},'code','codeName')  value="${(applyCheatDetailDto.rtfState)!}" /></td>
   	 				</tr>
   	 			</#list>
	   	 	</@pureTable>
        </@fieldset>  
	</@panelBody>
</@panel>
<#--引入方法js-->
<#include "cheatCheckBtnJs.ftl"/>
</@body>