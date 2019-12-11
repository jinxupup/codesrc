
<@toolbar offset="0" style="margin-top: -10px">
	<@button id="addRuleBtn" name="新建规则" click="addRuleFormOut('S')"></@button>
</@toolbar>

<@table id="ruleList" url="dmp/strategySimpleRuleController/list?rsId=${tmDmpRuleset.rsId}" pagination="false">
	<@th field="ruleName" title="规则名称" ></@th>
	<@th field="priority" title="优先级" ></@th>
	<@th field="remark" title="说明" ></@th>
	<@th field="ruleEnabled" title="是否启用" render="true">
		<@thGetName options={"Y":"是","N":"否"} value="{{row.ruleEnabled}}" />
	</@th>
	<@th title="<div style='width:90px;'>操作</div>" render="true">
		<@button name="编辑" click="editRuleFormOut('{{row.ruleId}}')" />
		<@ajaxButton id="deleteRuleBtn" name="删除" multi_submit="true" sense="danger" url="dmp/strategySimpleRuleController/delete?ruleId={{row.ruleId}}" confirm="确认删除该规则？" after="deleteRuleAfter" />
	</@th>
</@table>

<script type="text/javascript">
	
	/*新增pane的id*/
	var simpleRuleformTabPanelId = ""; 
	
	function ruleListRefresh(){
		var params = {url:"${base}/dmp/strategySimpleRuleController/list?rsId=${(tmDmpRuleset.rsId)!}"};
		$("#ruleList").bootstrapTable("refresh",params);
	}
	
	function deleteRuleAfter(res){
		alert(res.msg);
		if(res.s){
			 ruleListRefresh();
			 closeTabPanel();
		}
	}
	
	/*关闭form tab页*/
	function closeTabPanel(){
		if(simpleRuleformTabPanelId!=''){
			$('#tab').ar_tab().close(simpleRuleformTabPanelId);
			simpleRuleformTabPanelId = '';
		}
	}
	
	function addRuleFormOut(ruleSetType){
		closeTabPanel();
	    //设置参数
	    var option = {
		              title:"新建规则", //新增标签页的标题，不能为空
		              url:"${base}/dmp/strategySimpleRuleController/addpage?rsId=${tmDmpRuleset.rsId}", //载入内容的url，url对应的ftl页面
		              iframe:true, //是否以iframe形式载入页面,默认false
		              close:true,  //是否可以关闭，默认true
		              height:"530px" //高度，默认200px
	              };
	    simpleRuleformTabPanelId = $('#simpleRuleSetTab').ar_tab().add(option); 
	  //  $('#tab').ar_tab().close(id); //手动调用js方法关闭，以id做参数。
	
	};
	
	function editRuleFormOut(ruleId){
		
		closeTabPanel();
	    //设置参数
	    var option = {
		              title:"编辑规则", //新增标签页的标题，不能为空
		              url:"${base}/dmp/strategySimpleRuleController/editpage?ruleId="+ruleId, //载入内容的url，url对应的ftl页面
		              iframe:true, //是否以iframe形式载入页面,默认false
		              close:true,  //是否可以关闭，默认true
		              height:"530px" //高度，默认200px
	              };
	    simpleRuleformTabPanelId = $('#simpleRuleSetTab').ar_tab().add(option); 
	
	};
	
</script>