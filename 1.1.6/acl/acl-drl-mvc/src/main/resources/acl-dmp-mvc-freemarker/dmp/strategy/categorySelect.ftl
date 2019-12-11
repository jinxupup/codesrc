<#include "/layout.ftl">
<@body>
<@panel head="决策类别新增-leibia" >
<@panelBody>
	<div style="width:600px;">
		<@form id="form" action="dmp/strategy/addFormPage"  ajax="false" cols="1">
			<@row>
	    	<@field label="策略方案类别">
	            <@multipleSelect name="stClass" single="true" showfilter="true"
	            	options=dict_("tableMap","com.jjb.dmp.infrastructure.TmDmpStrategyCategory",{},"stClass","name")
	             	valid={"notempty":"true","notempty-message":"请选择类别"}
	              />
	        </@field>
	        </@row>                
	         <@row>
	        <@toolbar style="padding-top:10px;">
	            <@submitButton name="下一步"/> 
	            <@href href="dmp/strategyCategory/page" name="返回" />
	        </@toolbar>
	         </@row>   
	    </@form>
	</div>
        </@panelBody>
    </@panel>
 
 </@body>   