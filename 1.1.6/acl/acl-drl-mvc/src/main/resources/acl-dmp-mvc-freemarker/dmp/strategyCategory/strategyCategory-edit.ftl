<#include "/layout.ftl">
<@body>
<@panel head="决策类别编辑" >
<@panelBody>
            
    <@tab id="tab" >
	    <@tabNav> 
	        <@tabTitle pane_id="pane1" active="true" title="决策类别">
	        </@tabTitle>    
	        <@tabTitle pane_id="pane2"  title="决策变量" url="dmp/strategyCategory/varPage?stClass=${(tmDmpStrategyCategory.stClass)!}" iframe="true" iframe_height="100%">
	        </@tabTitle>
	        <@tabTitle pane_id="pane3"  title="自定义函数" url="dmp/strategyCategory/funPage?stClass=${(tmDmpStrategyCategory.stClass)!}" iframe="true" iframe_height="100%">
	        </@tabTitle>
	    </@tabNav>
	    <@tabContent> 
	        <@tabPane id="pane1" active="true">
	        	<#-- 编辑start -->
	        	<@form id="form" action="dmp/strategyCategory/edit"  multi_submit="true" cols="2">
	                <@row>
	                    <@field label="决策类别">
	                        <@input id="stClass" name="stClass"  value="${(tmDmpStrategyCategory.stClass)!}" readonly="true" valid={"regexp":"true","regexp-regexp":"^[a-zA-Z0-9$_]+$","regexp-message":"请输入字母、数字","notempty":"true","nochinese":"true","notempy-message":"不能为空","stringlength":"true","stringlength-max":"200"} />
	                    </@field>
	                </@row>
	                <@row>
	                    <@field label="名称">
	                        <@input id="name" name="name"  value="${(tmDmpStrategyCategory.name)!}" valid={"notempty":"true","notempy-message":"不能为空","stringlength":"true","stringlength-max":"40"} />
	                    </@field>
	                </@row>              
	                <@row>
	                    <@field label="说明">
	                        <@textarea name="remark" rows="5" value="${(tmDmpStrategyCategory.remark)!}" valid={"stringlength":"true","stringlength-max":"100"} />
	                    </@field>
	                </@row>                
	                <@row>
	                    <@toolbar>
	                        <@submitButton id="addBtn"/> 
	                        <@href href="dmp/strategyCategory/page" name="返回" />
	                    </@toolbar>
	                </@row>
	              <#-- 编辑end -->  
	            </@form> 
	        </@tabPane>    
	        <@tabPane id="pane2" >
	        </@tabPane>
	        <@tabPane id="pane3" >
	        </@tabPane>
	    </@tabContent>
	</@tab>
	
	<script type="text/javascript">
		$(function(){
			var autoHeight = function(){
				ar_.autoHeight({id:"pane2",minus:180});
				ar_.autoHeight({id:"pane3",minus:180});
				setTimeout(autoHeight,200);
			}
			autoHeight();
		});
	</script>
            
                       
</@panelBody>
</@panel>
 </@body>   