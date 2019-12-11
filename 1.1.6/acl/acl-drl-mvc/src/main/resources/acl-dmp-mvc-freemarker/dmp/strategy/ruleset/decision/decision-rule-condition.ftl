<#include "/layout.ftl"/>
<@body>
<#-- 引入组件 -->
<#include "/dmp/strategy/component/stBase.ftl"/>

<@container>
	<@toolbar offset="1" style="margin-top:-10px;">
		<@button id="conditionSubmitBtn" click="conditionSubmit" sense="danger" name="保存"></@button>
		<@button name="取消" click="clickCancel" sense="default"/>
	</@toolbar>
	<hr style="margin:8px 0px;"/>
	<@panel head="条件" sense="info" style="margin-bottom:3px;min-height:230px;">
		
		<#include "/dmp/strategy/component/decisionTableCondition.ftl"/>
		
	</@panel>
		
	<@panel head="结果" sense="warning" style="margin-bottom:3px;min-height:220px;">
		<#include "/dmp/strategy/component/decisionTableAction.ftl" />
	</@panel>
</@container>

<script>
	function conditionSubmit(){
		/*if(!$('#form').unicornValidForm()){
			return false;
		}*/
		
		var conditions = getTableConditions();
		var actions = getActionLines();
		
		console.log(conditions);
		console.log(actions);
		
		var decisionTable = {};
		decisionTable.conditions=conditions;
		decisionTable.actions=actions;
		
		var data = {};
        data.rsId = "${tmDmpRuleset.rsId}";
        data.decisionTable = JSON.stringify(decisionTable);
        
		$('#conditionSubmitBtn').unicornButtonDisable(true);
		
		var url = "${base}/${'dmp/strategyDecisionTable/editCondition'}";
		
		$.post(url,data,function(res){
			alert(res.msg);
			if(res.s){
				//todo 刷新 ruleListRefresh();
				
				window.parent.ar_.go("${base}/dmp/ruleset/editpage?rsId=${tmDmpRuleset.rsId}");
				
				$('#conditionSubmitBtn').unicornButtonDisable(false);
			}
			
			$('#conditionSubmitBtn').unicornButtonDisable(false);
		},'json');
		
	}
	
	function getTableConditions(){
		return st.getTableConditions();	
	}
	
	function getActionLines(){
		return st.getActionLines();
	}
	
	function clickCancel(){
		window.parent.$("#decisionTableTab_title2").addClass("active");
		window.parent.$("#decisionTableTab_pane2").addClass("active");
		window.parent.closeTabPanel();
	}
</script>
</@body>