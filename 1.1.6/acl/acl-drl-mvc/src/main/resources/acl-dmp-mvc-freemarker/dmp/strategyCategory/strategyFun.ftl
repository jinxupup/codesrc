<#include "/layout.ftl">
<@body>
<@toolbar offset="0" style="margin-top: -2px">
	<@button name="新增" click="funFormOut"/>
</@toolbar>	

<@table id="funList" url="dmp/strategyCategory/funList?stClass=${stClass}" pagination="false">
	<@th field="funCd" title="函数代码" ></@th>
	<@th field="funName" title="名称"></@th>
	<@th field="funParam" title="函数参数"></@th>
	<@th field="dataType" title="返回数据类型"></@th>
	<#--
	<@th field="funContent" title="函数主体"></@th>
	-->
	<@th field="remark" title="说明"></@th>
	<@th field="ifUsed" title="是否启用" render="true">
		<@thGetName options={"Y":"是","N":"否"} value="{{row.ifUsed}}" />
	</@th>
	<@th title="<div style='width:90px;'>操作</div>" render="true">
		<@button name="编辑" click="editFunFormOut('{{row.funCd}}')" />
		<@ajaxButton id="funDeleteBtn" name="删除" sense="danger" url="dmp/strategyCategory/deleteFun?stClass=${stClass}&funCd={{row.funCd}}" confirm="确认删除该函数？" after="deleteFunAfter" />
	</@th>
</@table>
<script type="text/javascript">
	
	function funListRefresh(){
		var params = {url:"${base}/dmp/strategyCategory/funList?stClass=${stClass}"};
		$("#funList").bootstrapTable("refresh",params);
	}
	
	function deleteFunAfter(res){
		alert(res.msg);
		if(res.s){
			 funListRefresh();
		}
	}
	
	function funFormOut(){
		top.dialog({
		    url: '${base}/dmp/strategyCategory/addFunPage?stClass=${stClass}',
		    id: 'funFormPanelId',
		    title:'新增函数',
		    width:860,
        	height:500,
        	onclose: function(){
				funListRefresh();
				this.remove();
			}
		}).showModal();
	};
	
	function editFunFormOut(funCd){
		top.dialog({
		    url: '${base}/dmp/strategyCategory/editFunPage?stClass=${stClass}&funCd='+funCd,
		    id: 'editFunFormPanelId',
		    title:'编辑函数',
		    width:860,
        	height:500,
        	onclose: function(){
				funListRefresh();
				this.remove();
			}
		}).showModal();
	};
	
</script>
</@body>