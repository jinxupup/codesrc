<#include "/layout.ftl"/>
<@body>
<#-- 联系人表单 -->
<div id="varFormPanel">
	<@container style="padding-top:10px;">
	<@form id="varForm" action="${isEdit?string('dmp/strategyCategory/editVar','dmp/strategyCategory/addVar')}" cols="1" >
		<@row>
	        <@field label="变量类型" field_ar="36" label_ar="6" input_ar="30">
	            <@dictSelect dicttype="DMP_VAR_TYPE" name="tmDmpStrategyVar.varType" value="${(tmDmpStrategyVar.varType)!}" nullable="true" readonly="${isEdit?string}" />
	        </@field>
			<@field hidden="true" field_ar="36" label_ar="6" input_ar="30">
				<@input name="tmDmpStrategyVar.stClass" value="${tmDmpStrategyVar.stClass}" />
			</@field>
			<@field label="变量代码" field_ar="36" label_ar="6" input_ar="30">
				<@input name="tmDmpStrategyVar.varCd" value="${(tmDmpStrategyVar.varCd)!}" readonly="${isEdit?string}" 
					valid={"regexp":"true","regexp-regexp":"^[a-zA-Z0-9$_]+$","regexp-message":"请输入字母、数字","stringlength":"true","stringlength-max":"40","notEmpty":"true"} />
			</@field>
			<@field label="变量名称" field_ar="36" label_ar="6" input_ar="30">
				<@input name="tmDmpStrategyVar.varName" value="${(tmDmpStrategyVar.varName)!}" valid={"stringlength":"true","stringlength-max":"20","notEmpty":"true"} />
			</@field>
			<@field label="变量方向" field_ar="36" label_ar="6" input_ar="30">
				<@checkbox>
					<label><input type="checkbox" value="Y"  name="tmDmpStrategyVar.isInput"   <#if (tmDmpStrategyVar.isInput)?? && tmDmpStrategyVar.isInput=="Y" > checked="checked" </#if> /> 输入变量 </label>
					<label><input type="checkbox" value="Y"  name="tmDmpStrategyVar.isOutput"  <#if (tmDmpStrategyVar.isOutput)?? && tmDmpStrategyVar.isOutput=="Y" > checked="checked" </#if> /> 输出变量 </label>
				</@checkbox>
			</@field>
			<#--
			<@field label="数据类型">
				<@select options={"string":"字符","decimal":"数字","bool":"布尔","date":"日期"} 
					name="tmDmpStrategyVar.dataType" value="${(tmDmpStrategyVar.dataType)!}"
					valid={"notEmpty":"true"} nullable="false"
				/>
			</@field>
			</@row>
			<@row>
			<@field label="选项来源">
				<@select name="tmDmpStrategyVar.optionType" options={"A":"自定义输入","D":"业务字典","T":"数据表"} value="${(tmDmpStrategyVar.optionType)!}" nullable="false" />
			</@field>
			</@row>
			<@row>
			<@field label="选项参数" field_ar="36" label_ar="6" input_ar="30">
			    <@textarea name="tmDmpStrategyVar.optionParam" valid={"stringlength":"true","stringlength-max":"400"} rows="10" >${(tmDmpStrategyVar.optionParam)!}</@textarea>
			</@field>
			</@row>
			<@row>
			<@field label="是否启用">
				<@select options={"Y":"是","N":"否"} name="tmDmpStrategyVar.ifUsed"  value="${(tmDmpStrategyVar.ifUsed)!}" nullable="false" valid={"notEmpty":"false"} />
			</@field>
			</@row>
			-->
			<@field label="变量说明" field_ar="36" label_ar="6" input_ar="30">
				<#-- 都启用 -->
				<@hidden name="tmDmpStrategyVar.ifUsed" value="Y" />
				
				<@input  name="tmDmpStrategyVar.remark"  value="${(tmDmpStrategyVar.remark)!}" valid={"notEmpty":"false"} valid={"stringlength":"true","stringlength-max":"100"} />
			</@field>
			<@toolbar align="center" style="margin-top:20px;">
				<@ajaxButton id="submitAddBtn" url="${isEdit?string('dmp/strategyCategory/editVar','dmp/strategyCategory/addVar')}" form_id="varForm" name="提交" before="submitBefore" after="submitAfter"/>
				<@button name="取消" click="clickCancel" sense="default"/>
			</@toolbar>
		</@row>
	</@form>
	</@container>
</div>

<script>
	function submitBefore(){
		if($('#varForm').unicornValidForm()){
			return true;
		}
		return false;
	}
	function submitAfter(res){
		alert(res.msg);
		if(res.s){
			$('#submitAddBtn').unicornButtonDisable(true);
			
			var dialog = ar_.getDialog();
			dialog.close().remove;
			
		}else{
			$('#submitAddBtn').unicornButtonDisable(false);
		}
	}
	
	function clickCancel(){
		var dialog = ar_.getDialog();
		dialog.close().remove;
	}
</script>

</@body>