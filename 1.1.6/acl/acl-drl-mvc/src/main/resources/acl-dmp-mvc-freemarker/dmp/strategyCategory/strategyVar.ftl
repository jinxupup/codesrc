<#include "/layout.ftl">
<@body>
<@toolbar offset="0" style="margin-top: -2px">
	<@button name="添加变量" click="varFormOut"/>
</@toolbar>	

<@table id="varList" url="dmp/strategyCategory/varList?stClass=${stClass}" pagination="false">
	<@th field="varType" title="变量类型" ></@th>
	<@th field="varCd" title="变量代码" ></@th>
	<@th field="varName" title="变量名称"></@th>
	<@th field="isInput" title="输入变量" render="true">
		<@thGetName options={"Y":"是","N":"否"} value="{{row.isInput}}" />
	</@th>
	<@th field="isOutput" title="输出变量" render="true">
		<@thGetName options={"Y":"是","N":"否"} value="{{row.isOutput}}" />
	</@th>
	<#--
	<@th field="ifUsed" title="是否启用" render="true">
		<@thGetName options={"Y":"是","N":"否"} value="{{row.ifUsed}}" />
	</@th>
	<@th field="dataType" title="数据类型"></@th>
	-->
	<@th title="<div style='width:90px;'>操作</div>" render="true">
		<@button name="编辑" click="editVarFormOut('{{row.varType}}','{{row.varCd}}')" />
		<@ajaxButton id="varDeleteBtn" name="删除" sense="danger" url="dmp/strategyCategory/deleteVar?stClass=${stClass}&varType={{row.varType}}&varCd={{row.varCd}}" confirm="确认删除该变量？" after="deleteVarAfter" />
	</@th>
</@table>
<script type="text/javascript">
	
	function varListRefresh(){
		var params = {url:"${base}/dmp/strategyCategory/varList?stClass=${stClass}"};
		$("#varList").bootstrapTable("refresh",params);
	}
	
	function deleteVarAfter(res){
		alert(res.msg);
		if(res.s){
			 varListRefresh();
		}
	}
	
	function varFormOut(){
		top.dialog({
		    url: '${base}/dmp/strategyCategory/addVarPage?stClass=${stClass}',
		    id: 'varFormPanelId',
		    title:'添加变量',
		    width:1400,
        	height:580,
        	onclose: function(){
				varListRefresh();
				this.remove();
			}
		}).showModal();
	};
	
	function editVarFormOut(varType,varCd){
		top.dialog({
		    url: '${base}/dmp/strategyCategory/editVarPage?stClass=${stClass}&varType='+varType+'&varCd='+varCd,
		    id: 'editVarFormPanelId',
		    title:'编辑变量',
		    width:600,
        	height:300,
        	onclose: function(){
				varListRefresh();
				this.remove();
			}
		}).showModal();
	};
	
</script>
</@body>