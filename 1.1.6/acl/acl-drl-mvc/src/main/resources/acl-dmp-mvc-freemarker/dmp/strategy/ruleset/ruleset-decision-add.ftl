<#include "/layout.ftl"/>
<@body>

<div id="varFormPanel">
	<@container style="padding-top:10px;">
	<@form id="form" action="" cols="2" >
		<@row>
		<@field label="规则集名称">
			<@hidden name="tmDmpRuleset.stId" value="${(tmDmpRuleset.stId)!}"  />
			<@hidden name="tmDmpRuleset.rsId" value="${(tmDmpRuleset.rsId)!}"  />
			<@input name="tmDmpRuleset.ruleSetName" value="${(tmDmpRuleset.ruleSetName)!}" valid={"notEmpty":"false","stringlength":"true","stringlength-max":"100"} ></@input>
			<@hidden name="tmDmpRuleset.ruleSetType" value="${(tmDmpRuleset.ruleSetType)!}"  />
		</@field>
		</@row>
		<@row>
		<@field label="说明">
			<@input  name="tmDmpRuleset.remark"  value="${(tmDmpRuleset.remark)!}" valid={"notEmpty":"false","stringlength":"true","stringlength-max":"100"} />
		</@field>
		</@row>
		<@row>
		<@field label="规则是否排他">
			<@select options={"Y":"是","N":"否"} name="tmDmpRuleset.isExclusive"  value="${(tmDmpRuleset.isExclusive)!}" nullable="false" valid={"notEmpty":"false"} />
		</@field>
		</@row>
		<@row>
		<@field label="是否启用">
			<@select options={"Y":"是","N":"否"} name="tmDmpRuleset.ruleSetEnabled"  value="${(tmDmpRuleset.ruleSetEnabled)!}" nullable="false" valid={"notEmpty":"false"} />
		</@field>
		</@row>
		<@row>
			<@toolbar align="center" style="margin-top:20px;">
				<@ajaxButton id="submitAddBtn" url="dmp/ruleset/add" form_id="form" name="提交" before="submitBefore" after="submitAfter"/>
				<@button name="取消" click="clickCancel" sense="default"/>
			</@toolbar>
		</@row>
	</@form>
	</@container>
</div>

<script>
	function submitBefore(){
		if($('#form').unicornValidForm()){
			return true;
		}
		return false;
	}
	
	function submitAfter(res){
		alert(res.msg);
		if(res.s){
			$('#submitAddBtn').unicornButtonDisable(true);
			
			ar_.getDialog().title("编辑规则表");
			ar_.go('${base}/dmp/ruleset/editpage?rsId='+res.obj.rsId);
					
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