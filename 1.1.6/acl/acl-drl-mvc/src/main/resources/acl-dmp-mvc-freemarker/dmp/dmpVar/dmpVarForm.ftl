<#include "/layout.ftl"/>
<@body>
<#-- 联系人表单 -->
<div id="varFormPanel">
	<@container style="padding-top:10px;">
	<@form id="varForm" action="${isEdit?string('dmp/dmpVar/editVar','dmp/dmpVar/addVar')}" cols="2" >
		<@row>
	        <@field label="变量类型" field_ar="36" label_ar="6" input_ar="30">
	            <@dictSelect dicttype="DMP_VAR_TYPE" name="tmDmpVar.varType" value="${(tmDmpVar.varType)!}" nullable="false" readonly="${isEdit?string}" />
	        </@field>
			<@field label="变量代码" field_ar="36" label_ar="6" input_ar="30">
				<@input name="tmDmpVar.varCd" value="${(tmDmpVar.varCd)!}" readonly="${isEdit?string}" 
					valid={"regexp":"true","regexp-regexp":"^[a-zA-Z0-9$_]+$","regexp-message":"请输入字母、数字","stringlength":"true","stringlength-max":"40","notEmpty":"true"} />
			</@field>
			<@field label="变量名称" field_ar="36" label_ar="6" input_ar="30">
				<@input name="tmDmpVar.varName" value="${(tmDmpVar.varName)!}" valid={"stringlength":"true","stringlength-max":"20","notEmpty":"true"} />
			</@field>
			<@field label="选项来源" label_ar="12" >
				<@select name="tmDmpVar.optionType" options={"A":"自定义输入","D":"业务字典","T":"数据表"} value="${(tmDmpVar.optionType)!}" nullable="false" />
			</@field>
			<@field label="数据类型" label_ar="12" >
	            <@select options={"string":"字符","decimal":"数字","bool":"布尔","date":"日期"} 
	                name="tmDmpVar.dataType" value="${(tmDmpVar.dataType)!}"
	                valid={"notEmpty":"true"} nullable="false"
	            />
	            <#--,"datetime":"日期时间","time":"时间"-->
	        </@field>
			<@field label="选项参数" field_ar="36" label_ar="6" input_ar="30">
			    <@textarea name="tmDmpVar.optionParam" valid={"stringlength":"true","stringlength-max":"1000"} rows="10" >${(tmDmpVar.optionParam)!}</@textarea>
			    <#--
				<@input name="tmDmpVar.optionParam" value="${((tmDmpVar.optionParam)!)?html}" valid={"stringlength":"true","stringlength-max":"400"} />
				-->
			</@field>
			 <@field label="变量说明" field_ar="36" label_ar="6" input_ar="30">
	            <@input  name="tmDmpVar.remark"  value="${(tmDmpVar.remark)!}" valid={"notEmpty":"false"} valid={"stringlength":"true","stringlength-max":"100"} />
	        </@field>
			<@field label="是否启用" label_ar="12" >
				<@select options={"Y":"是","N":"否"} name="tmDmpVar.ifUsed"  value="${(tmDmpVar.ifUsed)!}" nullable="false" valid={"notEmpty":"false"} />
			</@field>
			<@toolbar align="center" style="margin-top:0px;">
				<@ajaxButton id="submitAddBtn" url="${isEdit?string('dmp/dmpVar/editVar','dmp/dmpVar/addVar')}" form_id="varForm" name="提交" before="submitBefore" after="submitAfter"/>
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