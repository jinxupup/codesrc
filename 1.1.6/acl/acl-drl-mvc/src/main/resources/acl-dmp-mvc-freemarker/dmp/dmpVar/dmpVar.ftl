<#include "/layout.ftl">
<@body>
<@form id="queryForm" cols="4">
    <@row class="">
        <@field label="变量类型">
            <@dictSelect dicttype="DMP_VAR_TYPE" name="query.varType"/>
        </@field>
        <@field label="变量代码">
            <@input name="query.varCd" />
        </@field>
        <@field label="名称">
            <@input name="query.varName" />
        </@field>
        <@field label="" label_ar="2">
        	<@button id="queryButton" fa="search">查询</@button>
    		<@button name="新增" click="addVarFormOut" fa="plus"/>
        </@field>
    </@row>
</@form>

<@table id="varList" url="dmp/dmpVar/varList" form_id="queryForm" button_id="queryButton" condensed="true">
    <@th field="varType" title="变量类型" ></@th>
	<@th field="varCd" title="变量代码" ></@th>
	<@th field="varName" title="变量名称"></@th>
	
	<@th field="ifUsed" title="是否启用" render="true">
		<@thGetName options={"Y":"是","N":"否"} value="{{row.ifUsed}}" />
	</@th>
	<@th field="dataType" title="数据类型"></@th>
	<@th field="optionType" title="选项类型"></@th>
	<@th field="optionParam" title="选项参数" render="true">
		<@thGetName options={"A":"自定义输入","D":"业务字典","T":"数据表"} value="{{row.optionParam}}" />
	</@th>
	<@th field="remark" title="说明"></@th>
	<@th title="<div style='width:90px;'>操作</div>" render="true">
		<@button name="编辑" click="editVarFormOut('{{row.varType}}','{{row.varCd}}')" />
		<#-- 
		<@ajaxButton id="varDeleteBtn" name="删除" sense="danger" url="dmp/dmpVar/deleteVar?varType={{row.varType}}&varCd={{row.varCd}}" confirm="确认删除该变量？" after="deleteVarAfter" />
		-->
	</@th>
</@table>
<script type="text/javascript">
	
	function varListRefresh(){
		var params = {url:"${base}/dmp/dmpVar/varList"};
		$("#varList").bootstrapTable("refresh",params);
	}
	
	function deleteVarAfter(res){
		alert(res.msg);
		if(res.s){
			 varListRefresh();
		}
	}
	
	function addVarFormOut(){
		top.dialog({
		    url: '${base}/dmp/dmpVar/addVarPage',
		    id: 'varFormPanelId',
		    title:'新增变量',
		    width:860,
        	height:460,
        	onclose: function(){
				varListRefresh();
				this.remove();
			}
		}).showModal();
	};
	
	function editVarFormOut(varType,varCd){
		top.dialog({
		    url: '${base}/dmp/dmpVar/editVarPage?varType='+varType+'&varCd='+varCd,
		    id: 'editVarFormPanelId',
		    title:'编辑变量',
		    width:860,
        	height:460,
        	onclose: function(){
				varListRefresh();
				this.remove();
			}
		}).showModal();
	};
	
</script>
</@body>