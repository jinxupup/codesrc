<#include "/layout.ftl"/>
<@body>
<#-- 引入组件 -->
<#include "/dmp/strategy/component/stBase.ftl"/>
<#include "/dmp/strategy/component/filterCriteria.ftl"/>
<#include "/dmp/strategy/component/ruleAction.ftl"/>

<@container>
	<div style="height:135px;overflow:auto;">
		<@form id="form" action="" cols="2" >
			<@row>
			<@field label="规则名称">
				<@hidden name="tmDmpRule.stId" value="${(tmDmpRule.stId)!}"  />
				<@hidden name="tmDmpRule.rsId" value="${(tmDmpRule.rsId)!}"  />
				<@hidden name="tmDmpRule.ruleId" value="${(tmDmpRule.ruleId)!}"  />
				<@input name="tmDmpRule.ruleName" value="${(tmDmpRule.ruleName)!}" valid={"notEmpty":"true","stringlength":"true","stringlength-max":"40"} ></@input>
			</@field>
			<@field label="说明">
				<@input  name="tmDmpRule.remark"  value="${(tmDmpRule.remark)!}" valid={"notEmpty":"false","stringlength":"true","stringlength-max":"200"} />
			</@field>
			</@row>
			<@row>
			<@field label="优先级">
				<@input  name="tmDmpRule.priority"  value="${(tmDmpRule.priority)!}" valid={"integer":"true","notEmpty":"false","stringlength":"true","stringlength-max":"10"} />
			</@field>
			<@field label="是否启用">
				<@select options={"Y":"是","N":"否"} name="tmDmpRule.ruleEnabled"  value="${(tmDmpRule.ruleEnabled)!}" nullable="false" valid={"notEmpty":"false"}></@select>
			</@field>
			</@row>
		</@form>
		
		<hr style="margin:2px 0px 2px;"/>
		<@toolbar offset="1" align="center" style="margin-top:-2px;">
			<@button id="ruleSubmitBtn" sense="danger"  click="ruleSubmit" name="保存"></@button>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<@button name="取消" click="clickCancel" sense="default"/>
		</@toolbar>
	</div>
	<div style="">
	<@panel head="条件" sense="info" style="margin-bottom:3px;min-height:200px;">
		<div id="ruleObject">
			<rule-tree :treedata="treedata"></rule-tree>
		</div>
		
		<script>
		
			<#if isEdit?? && isEdit==true>
				<#-- 编辑页面操作 -->
				var filterCriteria = ${(filterCriteria)!"{}"};
			<#else>
				var filterCriteria = {};
			</#if>
			
			//var criteriaData = {"criteria":[{"criteria":[{"criteria":[{"criteria":[],"fieldName":"txnAmt","operator":"equals"},{"criteria":[],"fieldName":"txnAmt","operator":"and"}],"fieldName":"a","operator":"and",value:{}}],"fieldName":"b","operator":"or"}],"fieldName":"a","operator":"and",value:{}};
			var v = new Vue({
				el:'#ruleObject',
				data:{treedata: filterCriteria}
			});
		</script>
		<script>
			/*获取条件对象*/
			function genFilterCriteria(){
				var $t = $("#ruleObject .ruleTree:first");
				var filterCriteria = {};
				st.parseTree($t,filterCriteria);
				return filterCriteria;
			}
		</script>
	</@panel>
			
	<@panel head="操作" sense="warning" style="margin-bottom:3px;min-height:170px;">
		<div id="ruleActionList">
			<rule-action :ruleactionlist="ruleactionlist"></rule-action>
		</div>
		
		<script>
			var actionList = ${(actionList)!"[]"}
			var ruleactionlistVue = new Vue({
				el:'#ruleActionList',
				data:{ruleactionlist:actionList}
			});
	
			function getRuleactionlist(){
				return st.getRuleactionlist();
			}
		</script>
	</@panel>
	</div>
</@container>
<script>
	function ruleSubmit(){
		if(!$('#form').unicornValidForm()){
			return false;
		}
		
		//判断输入是否错误
		if(st.hasWrongInput()){
		  return false;
		}
		
		//条件信息
		var filterCriteria = genFilterCriteria();
		//获取动作信息
		var actionList = getRuleactionlist();
		console.log(actionList);
		
		var data = $("#form").serializeObject();
        data.filterCriteria = JSON.stringify(filterCriteria);
        data.actionList = JSON.stringify(actionList);;
        
		$('#ruleSubmitBtn').unicornButtonDisable(true);
		
		var url = "${base}/${isEdit?string('dmp/strategySimpleRuleController/edit','dmp/strategySimpleRuleController/add')}";
		
		$.post(url,data,function(res){
			alert(res.msg);
			if(res.s){
				
				ruleListRefresh();
				
				<#if isEdit?? && isEdit==true>
					<#-- 编辑页面操作 -->
					ar_.go('${base}/dmp/strategySimpleRuleController/editpage?ruleId=${tmDmpRule.ruleId}');	
					$('#ruleSubmitBtn').unicornButtonDisable(false);
				<#else>
					<#-- 新增页面跳转 -->
					window.parent.$("#"+window.parent.simpleRuleformTabPanelId).children(":first").text("编辑规则");
					ar_.go('${base}/dmp/strategySimpleRuleController/editpage?ruleId='+res.obj.ruleId);	
				</#if>
			}
			
			$('#ruleSubmitBtn').unicornButtonDisable(false);
		},'json');
		
	}
	
	/*刷新列表*/
	function ruleListRefresh(){
		window.parent.ruleListRefresh();
	}
	
	/**/
	function submitBefore(){
		if($('#form').unicornValidForm()){
			return true;
		}
		return false;
	}
	/**/
	function submitAfter(res){
		alert(res.msg);
		if(res.s){
			window.parent.ruleListRefresh();
			window.parent.$("#"+window.parent.simpleRuleformTabPanelId).children(":first").text("编辑规则");
			ar_.go('${base}/dmp/strategySimpleRuleController/editpage?ruleId='+res.obj.ruleId);
		}else{
			$('#submitAddBtn').unicornButtonDisable(false);
		}
	}
	
	function clickCancel(){
		window.parent.$("#simpleRuleSetTab_title2").addClass("active");
		window.parent.$("#simpleRuleSetTab_pane2").addClass("active");
		console.log(window.parent.$("#simpleRuleSetTab_pane2"));
		window.parent.closeTabPanel();
	}
</script>

</@body>