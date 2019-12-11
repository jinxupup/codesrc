<#include "/layout.ftl"/>
<@body>
<@gotop/>
<#--怀疑黑名单详情-->
<@panel>
    <@panelBody  style="padding-left:10px;">
        <@fieldset legend="申请中的详细信息:">
	        <@pureTable>
   	 			<tr>
   	 				<th width="75px">姓名</th>
   	 				<th>申请编号</th>
					<th width="80px">证件类型</th>
					<th>证件号码</th>
					<th>申请人手机号</th>
					<th>家庭电话</th>
					<th>家庭地址</th>
					<th>公司名称</th>
					<th>公司电话</th>
					<th>公司地址</th>
   	 			</tr>
   	 			<#list applyCheatBlackDetailDtoCur as applyCheatDetailDto>
   	 				<tr ondblclick = "applyDblclick('${(applyCheatDetailDto.appNo)!}')">
   	 					<td>${(applyCheatDetailDto.name)!}</td>
   	 					<td>${(applyCheatDetailDto.appNo)!}</td>
   	 					<td>${(applyCheatDetailDto.idType)!}</td>
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
        <@fieldset legend="黑名单详细信息:">
             <@pureTable id="blackListTable">
   	 			<tr>
   	 			    <th>姓名</th>
   	 				<th>黑名单来源</th>
					<th>上黑名单原因说明</th>
					<th>证件类型</th>
					<th>证件号码</th>
					<th>申请人手机号</th>
					<th>家庭电话</th>
					<th>家庭地址</th>
					<th>公司名称</th>
					<th>公司电话</th>
					<th>公司地址</th>
   	 			</tr>
   	 			<#list applyCheatBlackDetailDtosOld as tmPersonalBlackList>
   	 				<tr ondblclick = "blackListDblclick('${(tmPersonalBlackList.id)!}')">
   	 					<td>${(tmPersonalBlackList.name)!}</td>
   	 					<td>${(tmPersonalBlackList.blacklistSrc)!}</td>
   	 					<td>${(tmPersonalBlackList.memo)!}</td>
   	 					<td>${(tmPersonalBlackList.idType)!}</td>
   	 					<td>${(tmPersonalBlackList.idNo)!}</td>
   	 					<td>${(tmPersonalBlackList.cellPhone)!}</td>
   	 					<td>${(tmPersonalBlackList.homePhone)!}</td>
   	 					<td>${(tmPersonalBlackList.homeAdd)!}</td>
   	 					<td>${(tmPersonalBlackList.corpName)!}</td>
   	 					<td>${(tmPersonalBlackList.empPhone)!}</td>
   	 					<td>${(tmPersonalBlackList.empAdd)!}</td>
   	 				</tr>
   	 			</#list>
	   	 	</@pureTable>
        </@fieldset>    
        </@panelBody>
    </@panel>
    <#--引入方法js-->
	<#include "cheatCheckBtnJs.ftl"/>
</@body>