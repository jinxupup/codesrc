<#include "/layout.ftl"/>
<@body>

<#-- 联系人表单 -->
<div id="funFormPanel">
	<@container style="padding-top:10px;">
	<@form id="funForm" action="${isEdit?string('dmp/strategyCategory/editFun','dmp/strategyCategory/addFun')}" cols="2" >
		<@row>
		<@field hidden="true">
			<@input name="tmDmpStrategyFunction.stClass" value="${tmDmpStrategyFunction.stClass}" />
		</@field>
		<@field label="函数代码">
			<#if isEdit?? && isEdit>
				<@input name="tmDmpStrategyFunction.funCd" value="${(tmDmpStrategyFunction.funCd)!}"  readonly="${isEdit?string}" 
				  valid={"regexp":"true","regexp-regexp":"^[a-zA-Z0-9$_]+$","regexp-message":"请输入字母、数字","stringlength":"true","stringlength-max":"40","notEmpty":"true"} />
			<#else>
				<div class="input-group">
				  <span class="input-group-addon">ST_FUNCTION_</span>
				  <@input name="tmDmpStrategyFunction.funCd" value="${(tmDmpStrategyFunction.funCd)!}" 
				  valid={"regexp":"true","regexp-regexp":"^[a-zA-Z0-9$_]+$","regexp-message":"请输入字母、数字","stringlength":"true","stringlength-max":"25","notEmpty":"true"} />
				</div>
			</#if>
		</@field>
		<@field label="函数名称">
			<@input name="tmDmpStrategyFunction.funName" value="${(tmDmpStrategyFunction.funName)!}" valid={"stringlength":"true","stringlength-max":"20","notEmpty":"true"} />
		</@field>
		</@row>
		<@row>
		<@field label="函数参数" field_ar="36" label_ar="6" input_ar="30">
			<#-- 
			<@input name="tmDmpStrategyFunction.funParam" value="${(tmDmpStrategyFunction.funParam)!}" valid={"stringlength":"true","stringlength-max":"400"} />
			-->
			<@multipleSelect options=inputVars  name="tmDmpStrategyFunction.funParam" value="${(tmDmpStrategyFunction.funParam)!}"  valid={"stringlength":"true","stringlength-max":"400"} >
			</@multipleSelect>
			
		</@field>
		</@row>
		<@row>
		<@field label="返回数据类型">
			<@select options={"string":"字符","decimal":"数字","bool":"布尔","date":"日期"}
				name="tmDmpStrategyFunction.dataType" value="${(tmDmpStrategyFunction.dataType)!}"
				valid={"notEmpty":"true"} nullable="false"
			/>
			<#--,"datetime":"日期时间","time":"时间"-->
		</@field>
		</@row>
		<@row>
		<@field label="函数主体" field_ar="36" label_ar="6" input_ar="30">
			<@textarea name="tmDmpStrategyFunction.funContent" rows="12">${(tmDmpStrategyFunction.funContent)!}</@textarea>
		</@field>
		<@field label="函数说明">
			<@input  name="tmDmpStrategyFunction.remark"  value="${(tmDmpStrategyFunction.remark)!}" valid={"notEmpty":"false"} valid={"stringlength":"true","stringlength-max":"100"} />
		</@field>
		<@field label="是否启用">
			<@select options={"Y":"是","N":"否"} name="tmDmpStrategyFunction.ifUsed"  value="${(tmDmpStrategyFunction.ifUsed)!}" nullable="false" valid={"notEmpty":"false"} />
		</@field>
		</@row>
		<@row>
			<@toolbar align="center" style="margin-top:20px;">
				<@ajaxButton id="submitAddBtn" url="${isEdit?string('dmp/strategyCategory/editFun','dmp/strategyCategory/addFun')}" form_id="funForm" name="提交" before="submitBefore" after="submitAfter"/>
				<@button name="取消" click="clickCancel" sense="default"/>
			</@toolbar>
		</@row>
	</@form>
	</@container>
</div>

<script>
	function submitBefore(){
		if($('#funForm').unicornValidForm()){
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