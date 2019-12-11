<#include "/layout.ftl"/>
<@body>
<@tab id="decisionTableTab" >
    <@tabNav> 
        <@tabTitle pane_id="decisionTableTab_pane1" title="规则集信息">
        </@tabTitle>    
        <@tabTitle id="decisionTableTab_title2" pane_id="decisionTableTab_pane2"  title="<i class='fa fa-th-list'></i> 决策表" active="true">
        </@tabTitle>
    </@tabNav>
    <@tabContent style="padding-left:0px;padding-right:0px;"> 
        <@tabPane id="decisionTableTab_pane1">
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
						<@ajaxButton id="submitAddBtn" url="dmp/ruleset/edit" form_id="form" name="提交" before="submitBefore" after="submitAfter"/>
						<@button name="取消" click="clickCancel" sense="default"/>
					</@toolbar>
				</@row>
			</@form>
        </@tabPane>    
        <@tabPane id="decisionTableTab_pane2" style="padding:5px;" active="true" >
            <#include "decision/decision-rule-list.ftl" />
        </@tabPane>
    </@tabContent>
</@tab>


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