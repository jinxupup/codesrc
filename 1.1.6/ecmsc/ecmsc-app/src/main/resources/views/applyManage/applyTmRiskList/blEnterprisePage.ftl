<#include "/layout.ftl"/>

<@body>
  	<@tab id="" >
    	<@tabNav> 
        	<@tabTitle pane_id="pane1" active="true" title="企业黑名单列表">
        	</@tabTitle>    
        	<@tabTitle pane_id="pane2"  title="企业黑名单新增">
        	</@tabTitle>   	
    	</@tabNav>
    	<@tabContent> 
        	<@tabPane id="pane1" active="true">
            	<#include "blEnterpriseList_V1.ftl"/>
        	</@tabPane>    
        	<@tabPane id="pane2" >
     			<#include "blEnterpriseAdd_V1.ftl"/>
        	</@tabPane>      	
   		</@tabContent> 
	</@tab> 
</@body>