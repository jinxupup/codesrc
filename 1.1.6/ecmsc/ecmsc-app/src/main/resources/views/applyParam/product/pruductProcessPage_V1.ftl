<#include "/layout.ftl"/>
<@body>
<@panel head="产品进件流程配置" >
	<@panelBody>
	<#list appSourceMap?keys as key>
		<@form cols="8"  id="productDitProcForm${(key)!}" action="productParam/setProductProcessSubmimt" success_url="productParam/setPruductProcessPage?id=${(tmProduct.id)!}">
			<@row>
				<@field hidden="true">
					<@input name="productCd" value="${(tmProduct.productCd)!}" valid={"notempty":"true"} />
					<@input name="productDesc" value="${(tmProduct.productDesc)!}" valid={"notempty":"true"} />
				</@field>
				<@field id="appSource" label="申请渠道" field_ar="4" label_ar="12" input_ar="24">
					<@dictSelect dicttype="AppSource" showcode='true' name="appSource" 
						value="${(key)!}" valid={"notempty":"true"}/>
				</@field>
				<@field label="系统流程" field_ar="6" label_ar="8" input_ar="28">
					<@dictSelect dicttype="ProcdefKey" name="procdefKey" value="${(processMap[key].procdefKey)!}" valid={"notempty":"true"} />
				</@field>
				<@field id="isDefault" label="是否默认" field_ar="3" label_ar="18" input_ar="18">
					<@dictSelect dicttype="Indicator" showcode='false' name="isDefault" 
						value="${(processMap[key].isDefault)!'N'}" valid={"notempty":"true"}/>
				</@field>
				<@field id="riskproduct1" label="决策1" field_ar="6" label_ar="8" input_ar="28">
					<@dictSelect dicttype="RiskProductCode" showcode='true' name="riskproduct1" 
						value="${(processMap[key].riskproduct1)!}"/>
				</@field>
				<@field id="riskproduct2" label="决策2" field_ar="6" label_ar="8" input_ar="28">
					<@dictSelect dicttype="RiskProductCode" showcode='true' name="riskproduct2" 
						value="${(processMap[key].riskproduct2)!}"/>
				</@field>
				<@field id="riskproduct3" label="决策3" field_ar="6" label_ar="8" input_ar="28">
					<@dictSelect dicttype="RiskProductCode" showcode='true' name="riskproduct3" 
						value="${(processMap[key].riskproduct3)!}"/>
				</@field>
				<#--<@field id="riskproduct4" label="决策4" field_ar="4" label_ar="8" input_ar="28">
					<@dictSelect dicttype="RiskProductCode" showcode='true' name="riskproduct4" 
						value="${(processMap[key].riskproduct4)!}"/>
				</@field>
				<@field id="riskproduct5" label="决策5" field_ar="4" label_ar="8" input_ar="28">
					<@dictSelect dicttype="RiskProductCode" showcode='true' name="riskproduct5" 
						value="${(processMap[key].riskproduct5)!}"/>
				</@field>-->
				<@field field_ar="1" label_ar="0">
					<@toolbar style="text-align: left">
						<@submitButton fa="check-circle"/>
					</@toolbar>
				</@field>
			</@row>
		</@form>
		</hr>
	</#list>
	<@toolbar align="center">
	    <@backButton fa="reply"/>
	</@toolbar> 
	</@panelBody>
</@panel>
</@body>
<#--include "productProcdefKeyPage_V1.ftl"/>-->