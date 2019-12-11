<#include "/layout.ftl">
<@body>
<@panel head="决策方案编辑" >
<@panelBody>
            
    <@tab id="tab" >
	    <@tabNav> 
	        <@tabTitle pane_id="pane1" active="true" title="决策方案">
	        </@tabTitle>    
	        <@tabTitle pane_id="pane2"  title="方案配置" url="dmp/strategy/strategyDefinePage?stId=${(tmDmpStrategy.stId)!}" iframe="true" iframe_height="100%">
	        </@tabTitle>
	        <@tabTitle pane_id="pane6"  title="试算" url="dmp/strategyTrial/page?stId=${(tmDmpStrategy.stId)!}" iframe="true" iframe_height="100%">
	    	</@tabTitle>
	    </@tabNav>
	    <@tabContent> 
	        <@tabPane id="pane1" active="true">
	        	<@form id="form" action="dmp/strategy/edit"  multi_submit="true" cols="2">
		            <@row>
		                <@field label="决策类别">
		                	<@hidden id="stId" name="stId" value="${tmDmpStrategy.stId}" />
		                    <@input id="stClass" name="stClass" value="${tmDmpStrategy.stClass}" label_only="true" />
		                </@field>
		            </@row>  
		            <@row>
		                <@field label="类名称">
		                	<@select options=dict_("tableMap","com.jjb.dmp.infrastructure.TmDmpStrategyCategory",{},"stClass","name")
		                	showcode="false" value="${(tmDmpStrategy.stClass)!}"  label_only="true" />
		                </@field>
		            </@row>
		            <@row>
		                <@field label="决策方案代码">
		                    <@input id="stId" name="stId" value="${(tmDmpStrategy.stId)!}" label_only="true" />
		                </@field>
		            </@row>
		            <@row>
		                <@field label="决策方案名称">
		                    <@input id="name" name="stName" value="${(tmDmpStrategy.stName)!}" valid={"notempty":"true","notempy-message":"不能为空","stringlength":"true","stringlength-max":"40","regexp":"true","regexp-regexp":"^[0-9a-zA-Z\\u4E00-\\u9FA5\\uF900-\\uFA2D_$]+$","regexp-message":"不能输入空格与特殊字符"} />
		                </@field>
		            </@row>              
		            <@row>
		                <@field label="说明">
		                    <@textarea name="remark" rows="5" value="${(tmDmpStrategy.remark)!}" valid={"stringlength":"true","stringlength-max":"400"} />
		                </@field>
		            </@row>   
		            <@row>
		                <@field label="是否启用">
		                    <@select options={"Y":"是","N":"否"} name="ifUsed"  value="${(tmDmpStrategy.ifUsed)!}" nullable="false" />
		                </@field>
		            </@row>             
		            <@row>
		                <@toolbar>
		                    <@submitButton/> 
		                    <@href href="dmp/strategy/page" name="返回" />
		                </@toolbar>
		            </@row>
		        </@form>
	        </@tabPane>    
	        <@tabPane id="pane2" >
	        </@tabPane>
	        <@tabPane id="pane6">
	    	</@tabPane> 
	    </@tabContent>
	</@tab>
	
	<script type="text/javascript">
		$(function(){
			var autoHeight = function(){
				ar_.autoHeight({id:"pane2",minus:150});
				ar_.autoHeight({id:"pane6",minus:150});
				setTimeout(autoHeight,200);
			}
			autoHeight();
		});
	</script>
            
                       
</@panelBody>
</@panel>
 </@body>   