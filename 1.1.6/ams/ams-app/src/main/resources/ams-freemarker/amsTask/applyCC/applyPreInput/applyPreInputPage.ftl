<#include "/layout.ftl"/>

<@body>
	<@panel head="申请预录入界面">
        <@panelBody>
			  	<@tab id="tab">
			    	<@tabNav>
			    		<#if tmAppnoSeq?? && tmAppnoSeq.seq == 0> <#--0是判断是不是删除操作-->
				    		<@tabTitle pane_id="pane1" title="预录入信息新增" /> 
				        	<@tabTitle pane_id="pane2" active="true" title="申请信息列表" /> 
			        	<#else>
				        	<@tabTitle pane_id="pane1" active="true" title="预录入信息新增" /> 
				        	<@tabTitle pane_id="pane2" title="申请信息列表" /> 
			        	</#if> 		        	
			    	</@tabNav>
			    	<@tabContent>
			    	<#if tmAppnoSeq?? && tmAppnoSeq.seq == 0>    
			        	<@tabPane id="pane1"  >
			     			<#include "applyPreInputInfo.ftl"/>
					 	</@tabPane>	
			        	<@tabPane id="pane2" active="true" >
			            	<#include "applyPreUnSubmitList.ftl"/>
			        	</@tabPane>
			        <#else>
			        	<@tabPane id="pane1" active="true" >
			     			<#include "applyPreInputInfo.ftl"/>
					 	</@tabPane>	
			        	<@tabPane id="pane2" >
			            	<#include "applyPreUnSubmitList.ftl"/>
			        	</@tabPane>
			        </#if>	
			   		</@tabContent>	   		
				</@tab>			
        </@panelBody>
     </@panel>
</@body>