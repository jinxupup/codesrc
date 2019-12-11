<#include "/layout.ftl"/>	
<@body>
<@panel>
	<@panelBody>
		<@form id="queryForms" >
			<@pureTable>
			    <tbody>
			        <tr>
			        	<th>申请渠道</th>
			        	<th>审批流程</th>
			        	<th>是否默认</th>
			        	<th>风控产品1</th>
			        	<th>风控产品2</th>
			        	<th>风控产品3</th>
			        	<#--<th>风控产品4</th>
			        	<th>风控产品5</th>-->
			        </tr>
			        <#if processList??>
			 			<#list processList as processDto>
				 			<tr>
								<td><@dictSelect dicttype="AppSource" value="${(processDto.appSource)!}" label_only="true"/></td>
								<td><@dictSelect dicttype="ProcdefKey" value="${(processDto.procdefKey)!}" label_only="true"/></td>
								<td><@dictSelect dicttype="Indicator" value="${(processDto.isDefault)!}" label_only="true"/></td>
								<td><@dictSelect dicttype="RiskProductCode" value="${(processDto.riskproduct1)!}" label_only="true"/></td>
								<td><@dictSelect dicttype="RiskProductCode" value="${(processDto.riskproduct2)!}" label_only="true"/></td>
								<td><@dictSelect dicttype="RiskProductCode" value="${(processDto.riskproduct3)!}" label_only="true"/></td>
								<#--<td><@dictSelect dicttype="RiskProductCode" value="${(processDto.riskproduct4)!}" label_only="true"/></td>
								<td><@dictSelect dicttype="RiskProductCode" value="${(processDto.riskproduct5)!}" label_only="true"/></td>-->
				 			</tr>
				 		</#list>
			 		</#if>
			    </tbody>
			</@pureTable> 
		</@form>
	</@panelBody>
</@panel>
</@body>

