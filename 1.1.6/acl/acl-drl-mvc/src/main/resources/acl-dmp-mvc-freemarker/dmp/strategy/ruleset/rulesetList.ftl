
<@toolbar offset="0" style="margin-top: -10px">
<@button name="新建规则集" fa="street-view" click="addRulesetFormOut('S')"></@button>
<@button name="新建决策表" fa="th-list" click="addRulesetFormOut('D')"></@button>
</@toolbar>

<@table id="rulesetList" url="dmp/ruleset/list?stId=${tmDmpStrategy.stId}" pagination="false">
	<@th field="ruleSetName" title="规则集名称" ></@th>
	<@th field="ruleSetType" title="类型" render="true">
		{{if row.ruleSetType=='S'}}
			<@icon fa="street-view" />规则集
		{{/if}}
		{{if row.ruleSetType=='D'}}
			<@icon fa="th-list" />决策表
		{{/if}}
	</@th>
	<@th field="ruleSetEnabled" title="规则是否排他" render="true">
		<@thGetName options={"Y":"是","N":"否"} value="{{row.isExclusive}}" />
	</@th>
	<@th field="ruleSetEnabled" title="是否启用" render="true">
		<@thGetName options={"Y":"是","N":"否"} value="{{row.ruleSetEnabled}}" />
	</@th>
	<@th field="remark" title="说明" ></@th>
	<@th title="<div style='width:90px;'>操作</div>" render="true">
		<@button name="编辑" click="editRulesetFormOut('{{row.rsId}}')" />
		<@ajaxButton name="删除" id="deleteRuleset" sense="danger" url="dmp/ruleset/delete?rsId={{row.rsId}}" confirm="确认删除该规则集？" after="deleteRulesetAfter" />
	</@th>
</@table>

<script type="text/javascript">
	
	function rulesetListRefresh(){
		var params = {url:"${base}/dmp/ruleset/list?stId=${(tmDmpStrategy.stId)!}"};
		$("#rulesetList").bootstrapTable("refresh",params);
	}
	
	function deleteRulesetAfter(res){
		alert(res.msg);
		if(res.s){
			 rulesetListRefresh();
		}
	}
	
	function addRulesetFormOut(ruleSetType){
		
		var title = '';
		if(ruleSetType=='S'){
			title = '新建规则集';
		}else if(ruleSetType=='D'){
			title = '新建决策表';
		}
	
		top.dialog({
		    url: '${base}/dmp/ruleset/addpage?stId=${tmDmpStrategy.stId}&ruleSetType='+ruleSetType,
		    id: 'rulesetFormPanelId',
		    title:title,
		    width:1020,
        	height:580,
        	onclose: function(){
				rulesetListRefresh();
				this.remove();
			}
		}).showModal();
	};
	
	function editRulesetFormOut(rsId){
		var url = ar_.randomUrl('${base}/dmp/ruleset/editpage?rsId='+rsId);
		top.dialog({
		    url: url,
		    id: 'editRulesetFormPanelId',
		    title:'编辑规则',
		    width:1020,
        	height:580,
        	onclose: function(){
				rulesetListRefresh();
				this.remove();
			}
		}).showModal();
	};
	
</script>