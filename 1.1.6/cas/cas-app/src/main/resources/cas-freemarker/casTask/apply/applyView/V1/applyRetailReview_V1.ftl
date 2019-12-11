<@fieldset legend="外部风控信息:">
	<@row>
		<@field label="数字解读值:">
			<@input id="inpretVal" name="S90112Resp.inpretVal" value="${(tmExtRiskInput.inpretVal)!}" readonly="true"/>
		</@field>
		<@field label="数字解读值风险等级:">
			<@input id="inpretValRiskLvl" name="S90112Resp.inpretValRiskLvl" value="${(tmExtRiskInput.inpretValRiskLvl)!}" readonly="true"/>
		</@field>
		<@field label="申请评分:">
			<@input id="applyScore" name="tmExtRiskInput.extGrade" value="${(tmExtRiskInput.extGrade)!}" readonly="true"/>
		</@field>
	</@row>
	<@row>
		<@field label="申请评分风险等级:">
			<@input id="applyScoreRiskLvl" name="tmExtRiskInput.extRiskLevel" value="${(tmExtRiskInput.extRiskLevel)!}" readonly="true"/>
		</@field>
		<@field label="规则风险等级:">
			<@input id="ruleRiskLvl" name="tmExtRiskInput.ruleRiskLvl" value="${(tmExtRiskInput.ruleRiskLvl)!}" readonly="true"/>
		</@field>
		<@field label="综合风险等级:">
			<@input id="complexRiskLvl" name="tmExtRiskInput.complexRiskLvl" value="${(tmExtRiskInput.complexRiskLvl)!}" readonly="true"/>
		</@field>
	</@row>
	<@row>
		<@field label="额度建议:">
			<@input id="lmtAdvice" name="tmExtRiskInput.extSugLmt" value="${(tmExtRiskInput.extSugLmt)!}" readonly="true"/>
		</@field>
		<@field label="客户等级:">
			<@input id="custLvl" name="tmExtRiskInput.custLvl" value="${(tmExtRiskInput.custLvl)!}" readonly="true"/>
		</@field>
	</@row>
	<@row>
		<@button name="查看触发规则" click="viewTriggerRulesBtn"/>
	</@row>
</@fieldset>
<script>

<#-- 查看触发规则按钮 -->
var viewTriggerRulesBtn = function(){
	$.ajax({
		url:"${base}/commonDialog/viewTriggerRules",
		type:"post",
		dataType : "json",
		data:{'appNo':'${(appNo)!}'},
		success:function(ref){
			if(ref.s){
				window.open(ref.obj);
			}else{
				alert(ref.msg);
			}
		}
	});
}
</script>
