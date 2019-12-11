<#include "/layout.ftl"/>
<@body>
	<@field label="申请件编号：" hidden="true">
		<@input id="appNo" name="appNo" value="${(appNo)!}" readonly="true" />
	</@field>
	<@fieldset legend="决策小结">
		<@row>
			<@field label="自动审批结果:" field_ar="7" label_ar="16" input_ar="20">
				<@input value="${(applyRiskInfoDto.content)!}" label_only="true"/>
			</@field>

			<@field label="最终评分:" field_ar="7" label_ar="16" input_ar="20">
				<@input value="${(applyRiskInfoDto.finalScore)!}" label_only="true"/>
			</@field>
			<@field label="建议额度:" field_ar="7" label_ar="16" input_ar="20">
				<@input value="${(applyRiskInfoDto.suggestAmt)!}" label_only="true"/>
			</@field>
			<@buttonAuth code="CAS_BANK_REPORT">
				<#if applyRiskInfoDto.lookPboc?? >
					<@button name="查看人行报告" id="lookPboc" click="show('${(applyRiskInfoDto.lookPboc)!}')"/>
				</#if>
			</@buttonAuth>
		</@row>
	</@fieldset>
	<#if tmAppFlagList??>
		<table style=" width: 100%">
			<tr style="vertical-align: top;">
				<td width="33%">
					<@pureTable striped="true">
						<tr><th colspan="2" style="text-align:center">风险警告</th></tr>
						<#list tmAppFlagList as tmAppFlag>
							<#if (tmAppFlag.flagType=="FlagRisk") >
								<tr>
									<td>${(tmAppFlag.flagCode)!}-${(tmAppFlag.flagDesc)!}</td>
									<td></td>
								</tr>
							</#if>
						</#list>
					</@pureTable>
				</td>
				<td width="33%">
					<@pureTable>
						<tr>
							<th style="text-align:center">系统警告</th>
						</tr>
						<#list tmAppFlagList as tmAppFlag>
							<#if tmAppFlag.flagType=="FlagSys" >
								<tr>
									<td>${(tmAppFlag.flagCode)!}-${(tmAppFlag.flagDesc)!}</td>
								</tr>
							</#if>
						</#list>
					</@pureTable>
				</td>
				<td width="33%">
					<@pureTable>
						<tr><th colspan="2" style="text-align:center">自定义标签</th></tr>
						<#list tmAppFlagList as tmAppFlag>
							<#if tmAppFlag.flagType=="FlagApp" >
								<tr>
									<td>${(tmAppFlag.flagCode)!}-${(tmAppFlag.flagDesc)!}</td>
									<td>${(tmAppFlag.createUser)!}</td>
								</tr>
							</#if>
						</#list>
					</@pureTable>
				</td>
			</tr>
		</table>
	</#if>
<#--<#if tmAppFlagList??>
    <@fieldset legend="申请件标签">
        <@row>
            <div class="col-ar-36">
                <#list tmAppFlagList as tmAppFlag>
                    <#if (tmAppFlag.flagType=="riskFlag") >
                        <@field label="风险标签:"  field_ar="8" label_ar="16" input_ar="20">
                            <@input value="${tmAppFlag.flagCode}" label_only="true"/>
                        </@field>
                    </#if>
                </#list>
            </div>
        </@row>
        <br>
        <@row>
            <div class="col-ar-36">
                <#list tmAppFlagList as tmAppFlag>
                    <#if (tmAppFlag.flagType=="systemFlag") >
                        <@field label="系统标签:"  field_ar="8" label_ar="16" input_ar="20">
                            <@input value="${tmAppFlag.flagCode}" label_only="true"/>
                        </@field>
                    </#if>
                </#list>
            </div>
        </@row>
        <br>
        <@row>
            <div class="col-ar-36">
                <#list tmAppFlagList as tmAppFlag>
                    <#if (tmAppFlag.flagType=="customFlag") >
                        <@field label="自定义标签:"  field_ar="8" label_ar="16" input_ar="20">
                            <@input value="${tmAppFlag.flagCode}" label_only="true"/>
                        </@field>
                    </#if>
                </#list>
            </div>
        </@row>
    </@fieldset>
</#if>-->

<#--
	<#if tmAppFlagList??>
		<@fieldset legend="申请件标签">
			<@row>
				<div class="col-ar-36">
					<#list tmAppFlagList as tmAppFlag>
						<#if (tmAppFlag.flagType=="riskFlag") >
							<@field label="风险标签:"  field_ar="8" label_ar="16" input_ar="20">
								<@input value="${tmAppFlag.flagCode}" label_only="true"/>
							</@field>
						</#if>
					</#list>
				</div>
			</@row>
			<br>
			<@row>
				<div class="col-ar-36">
					<#list tmAppFlagList as tmAppFlag>
						<#if (tmAppFlag.flagType=="systemFlag") >
							<@field label="系统标签:"  field_ar="8" label_ar="16" input_ar="20">
								<@input value="${tmAppFlag.flagCode}" label_only="true"/>
							</@field>
						</#if>
					</#list>
				</div>
			</@row>
			<br>
			<@row>
				<div class="col-ar-36">
					<#list tmAppFlagList as tmAppFlag>
						<#if (tmAppFlag.flagType=="customFlag") >
							<@field label="自定义标签:"  field_ar="8" label_ar="16" input_ar="20">
								<@input value="${tmAppFlag.flagCode}" label_only="true"/>
							</@field>
						</#if>
					</#list>
				</div>
			</@row>
		</@fieldset>
	</#if>
-->
	<@row>
		<@fieldset legend="命中规则列表">
			<#if applyRiskInfoDto.hitTheRule?? >
			<#--命中规则列表-->
				<@pureTable>
					<tr>
						<th width="20%">命中项目</th>
						<th>命中规则</th>
						<th>命中结果</th>
					</tr>
					<#list applyRiskInfoDto.hitTheRule?keys as key>
						<#list applyRiskInfoDto.hitTheRule[key]?keys as k>
							<tr>
								<td>${key}</td>
								<td>${k}</td>
								<td>${applyRiskInfoDto.hitTheRule[key][k]}</td>
							</tr>
						</#list>
					</#list>
				</@pureTable>
			</#if>
		</@fieldset>

	<#--	<div class="col-ar-36">
            <#if applyRiskInfoDto.thirdDataProduct?? >
                <#list applyRiskInfoDto.thirdDataProduct?keys as key>
                    <#if (key=="学信网-学历查询") >
                        <@fieldset legend="${key}">
                            <#list applyRiskInfoDto.thirdDataProduct[key]?keys as k>
                                <@field label="${k}:" label_ar="26" input_ar="10">
                                    <@input value="${applyRiskInfoDto.thirdDataProduct[key][k]}" label_only="true"/>
                                </@field>
                            </#list>
                        </@fieldset>
                    </#if>
                </#list>
            </#if>
        </div>
        <div class="col-ar-36">
            <#if applyRiskInfoDto.thirdDataProduct?? >
                <#list applyRiskInfoDto.thirdDataProduct?keys as key>
                    <@fieldset legend="${key}">
                    <#list applyRiskInfoDto.thirdDataProduct[key]?keys as k>
                        <@field label="${k}:" label_ar="26" input_ar="10">
                            <@input value="${applyRiskInfoDto.thirdDataProduct[key][k]}" label_only="true"/>
                        </@field>
                    </#list>
                    </@fieldset>
                </#list>
            </#if>
        </div>-->
		<div class="col-ar-36">
			<#if applyRiskInfoDto.showMap?? >
				<#list applyRiskInfoDto.showMap?keys as key>
					<#if (key=="学信网-学历查询") >
						<@fieldset legend="${key}">
							<#list applyRiskInfoDto.showMap[key]?keys as k>
								<@field label="${k}:" field_ar="12" label_ar="18" input_ar="18">
									<@input value="${(applyRiskInfoDto.showMap[key][k])!}" label_only="true"/>
								</@field>
							</#list>
						</@fieldset>
					</#if>
				</#list>
			</#if>
		</div>
		<div class="col-ar-36">
			<#if applyRiskInfoDto.showMap?? >
				<#list applyRiskInfoDto.showMap?keys as key>
					<#if (key!="学信网-学历查询") >
						<@fieldset legend="${key}" >
							<#list applyRiskInfoDto.showMap[key]?keys as k>
								<#if k?? && ((k=="人行单位名称") || (k=="人行家庭住址") || (k=="人行报告学历"))>
									<@field label="${k}:" field_ar="36" label_ar="6"  >
										<@input value="${(applyRiskInfoDto.showMap[key][k])!}"   label_only="true"/>
									</@field>
								<#elseif k?? && ((k=="说明"))>
									<@field label="${k}:" field_ar="36" label_ar="6" input_ar="30" >
										<@input value="${(applyRiskInfoDto.showMap[key][k])!}" style="color:red" label_only="true"/>
									</@field>
								<#else>
									<@field label="${k}:" field_ar="12" label_ar="18" input_ar="18">
										<@input value="${(applyRiskInfoDto.showMap[key][k])!}" label_only="true"/>
									</@field>
								</#if>
							</#list>
						</@fieldset>
					</#if>
				</#list>
			</#if>
		</div>
	</@row>
	<script type="text/javascript">
		var appNo = '${(appNo)!}';
		function show(x){
			if(x!=null && x!=undefined && x!=""){
				$.ajax({
					type: "POST",
					dataType : "json",
					data:"appNo="+appNo,
					url: "${base}/cas_common/saveAuditHistory",
					success: function(ref){
						if(ref.s){
							window.open(x,'人行报告','top=5, left=200, toolbar=no,scrollbars=yes menubar=no,resizable=yes,location=no, status=no');
						}else{
							alert(ref.msg);
						}
					},
				});
			}
		};
	</script>
</@body>