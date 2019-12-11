
<#-- 引入组件 -->
<#include "/dmp/strategy/component/stBase.ftl"/>


<div style="float:left;">
	<@button id="saveRuleBtn" name="保存规则表" sense="danger"  click="saveDecisionTable" style="margin-left:20px;margin-right:10px;"></@button>
</div>

<div style="float:right;">
	<@button id="editConditionFormOutBtn" name="编辑条件&结果" sense="info" click="editConditionFormOut('S')" style="margin-right:10px;"></@button>
</div>

<#-- 
<@toolbar offset="0" style="margin-top: -15px">
	<@button id="saveRuleBtn" name="保存规则表" click="saveDecisionTable" ></@button>
	<@button id="editConditionFormOutBtn" name="编辑条件&结果" click="editConditionFormOut('S')" style="float:right;margin-right:10px;"></@button>
	<@button id="addRuleBtn" name="新建行" click="addRuleFormOut('S')" style="float:right;margin-right:10px;"></@button>
</@toolbar>

<hr style="  margin-top: 10px;margin-bottom: 10px;"/>
-->
<#include "/dmp/strategy/component/decisionTable.ftl"/>

<script type="text/javascript">
	
	/*新增pane的id*/
	var conditionFormTabPanelId = ""; 
	
	function editConditionFormOut(){
		closeTabPanel();
	    //设置参数
	    var option = {
		              title:"编辑条件&结果", //新增标签页的标题，不能为空
		              url:"${base}/dmp/strategyDecisionTable/editConditionPage?rsId=${tmDmpRuleset.rsId}", //载入内容的url，url对应的ftl页面
		              iframe:true, //是否以iframe形式载入页面,默认false
		              close:true,  //是否可以关闭，默认true
		              height:"510px" //高度，默认200px
	              };
	    conditionFormTabPanelId = $('#decisionTableTab').ar_tab().add(option);
	};
	
	function saveDecisionTable(){
	
	    //判断输入是否错误
        if(st.hasWrongInput()){
          return false;
        }
	
		var rows = st.getDecisionTableRows();
		//判断名字不能重复
		if(rows.length>0){
            for(var i=0;i<rows.length-1;i++){
                for(var j=i+1;j<rows.length;j++){
                    if(rows[i]['desc']==rows[j]['desc']){
                        alert("规则名称不能相同，请检查");
                        return ;
                    }
                }
            }
		}
		
		var decisionTable = {};
		decisionTable.rows = rows;
		var data = {};
        data.rsId = "${tmDmpRuleset.rsId}";
        data.decisionTable = JSON.stringify(decisionTable);
        
		$('#saveRuleBtn').unicornButtonDisable(true);
		
		var url = "${base}/${'dmp/strategyDecisionTable/editRows'}";
		
		$.post(url,data,function(res){
			alert(res.msg);
			if(res.s){
				//todo 刷新 ruleListRefresh();
				
				window.ar_.go("${base}/dmp/ruleset/editpage?rsId=${tmDmpRuleset.rsId}");
				$('#saveRuleBtn').unicornButtonDisable(false);
			}
			
			$('#saveRuleBtn').unicornButtonDisable(false);
		},'json');
		
	};
	
	
	
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
		if(conditionFormTabPanelId!=''){
			$('#tab').ar_tab().close(conditionFormTabPanelId);
			conditionFormTabPanelId = '';
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