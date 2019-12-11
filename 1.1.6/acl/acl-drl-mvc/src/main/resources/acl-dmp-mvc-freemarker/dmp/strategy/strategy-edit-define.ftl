<#include "/layout.ftl" />
<@body>
	
<@tab id="" >
	<@tabNav> 
	    <@tabTitle pane_id="pane1" active="true" title="规则集列表">
	    </@tabTitle> 
	    <#--   
	    <@tabTitle pane_id="pane2"  title="自定义变量">
	    </@tabTitle>
	    <@tabTitle pane_id="pane3"  title="自定义函数">
	    </@tabTitle>    
	    <@tabTitle pane_id="pane4"  title="排除条件">
	    </@tabTitle>
	    <@tabTitle pane_id="pane5"  title="输入变量">
	    </@tabTitle>    
	    -->
	</@tabNav>
	<@tabContent> 
	    <@tabPane id="pane1" active="true">
	        <#include "./ruleset/rulesetList.ftl" />
	    </@tabPane>    
	    <#-- 
	    <@tabPane id="pane2" >
	        ...
	    </@tabPane>
	    <@tabPane id="pane3" >
	        ...
	    </@tabPane>
	    <@tabPane id="pane4" >
	        ...
	    </@tabPane>
	    <@tabPane id="pane5" >
	        
	    </@tabPane>
	    -->
	</@tabContent>
</@tab> 

</@body>