<@fieldset legend="核身信息">
	<@row>
		<@field label="身份核查结果:" field_ar="9" label_ar="18" input_ar="18">
			<@select options={"Y":"通过","N":"不通过"}  value="${(tmAppPrimCreditResult.nciicResult)!}" label_only="true"/>
		</@field>
		<@field label="证件照是否一致:" field_ar="9" label_ar="18" input_ar="18">
			<@dictSelect dicttype="Indicator" value="${(tmAppPrimCreditResult.isFitIdPhoto)!}" label_only="true"/>
		</@field>
	</@row>
</@fieldset>	
<#include "pbocReportView_V1.ftl"/>
		