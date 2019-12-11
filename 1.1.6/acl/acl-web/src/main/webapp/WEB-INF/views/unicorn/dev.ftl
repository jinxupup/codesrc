<#include "/layout.ftl"> 
<@body>
<@panel>
	
	<#macro treeSelect >
		
	</#macro>
	<#macro treeView options={} value="" value_split=",">
		
	</#macro>
	
	
	<@treeSelect />
	<@treeView options={} value="" value_split="" />
	
	<@button name="tree" click="tClick" /> <@button name="destroy" click="destroyClick" />
	<script type="text/javascript">
		var tClick = function(){
			
		};		
		var destroyClick = function(){
			$.fn.zTree.destroy("#treeDemo");
		};
	</script>
	
	<@form>
		<@field>
			<@input name="qq" valid={"numeric":"true"} />
		</@field>
		<@submitButton name="提交"/>
	</@form>
	
		
	<@hr />
	
	<div style="height:400px;width:300px;overflow:auto;border:1px solid #337ab7;">
		<ul id="treeDemo" class="ztree" >
	</div>	
	<script type="text/javascript">
	   var zTreeObj;
	   // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
	   var setting = {};
	   // zTree 的数据属性，深入使用请参考 API 文档（zTreeNode 节点数据详解）
	   var zNodes = [
		   {name:"test1", open:true, children:[
		      {name:"test1_1"}, {name:"test1_2"}]},
		   {name:"test2", open:true, children:[
		      {name:"test2_1"}, {name:"test2_2"}]},
		      {name:"test1", open:true, children:[
		      {name:"test1_1"}, {name:"test1_2"}]},
		   {name:"test2", open:true, children:[
		      {name:"test2_1"}, {name:"test2_2"}]},
		      {name:"test1", open:true, children:[
		      {name:"test1_1"}, {name:"test1_2"}]},
		   {name:"test2", open:true, children:[
		      {name:"test2_1"}, {name:"test2_2"}]},
		      {name:"test1", open:true, children:[
		      {name:"test1_1"}, {name:"test1_2"}]},
		   {name:"test2", open:true, children:[
		      {name:"test2_1"}, {name:"test2_2"}]},
		      {name:"test1", open:true, children:[
		      {name:"test1_1"}, {name:"test1_2"}]},
		   {name:"test2", open:true, children:[
		      {name:"test2_1"}, {name:"test2_2"}]},
		      {name:"test1", open:true, children:[
		      {name:"test1_1"}, {name:"test1_2"}]},
		   {name:"test2", open:true, children:[
		      {name:"test2_1"}, {name:"test2_2"}]}
		   ];
	   $(document).ready(function(){
	      zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	   });	
	</script>
	
	
	

</@panel>
</@body>
