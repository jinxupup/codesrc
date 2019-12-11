<#include "/layout.ftl"/>	
<@body>
	<#--审批风险名单库信息弹出框-->
	<@panel>
    	<@panelBody  style="padding-left:10px;">
	        <@pureTable>
				<tr>
					<th width="10%">风险名单来源</th>
					<th width="10%">客户姓名</th>
					<th width="10%">手机号码</th>
					<th width="15%">证件号码</th>
					<th width="15%">上风险名单原因说明</th>
					<th width="8%">风险名单类型</th>
	 			</tr>
	 			<#if tmRiskLists??>
		 			<#list tmRiskLists as tmRiskList>
			 			<tr>
							<td><@dictSelect dicttype="RiskListSource" value="${(tmRiskList.risklistSrc)!}" label_only="true"/></td>
							<td>${(tmRiskList.name)!}</td>
							<td>${(tmRiskList.cellPhone)!}</td>
							<td>${(tmRiskList.idNo)!}</td>
							<td>${(tmRiskList.reason)!}</td>
							<td><@dictSelect dicttype="ActType"  value="${(tmRiskList.actType)!}" label_only="true"/></td>
						</tr>
			 		</#list>
		 		</#if>
			</@pureTable>
    	</@panelBody>
    </@panel>
</@body>