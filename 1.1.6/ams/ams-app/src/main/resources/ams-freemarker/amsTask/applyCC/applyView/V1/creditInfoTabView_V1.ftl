<@panel>
	<@panelBody>
		<@tab>
			<@tabNav>
				<@buttonAuth code="AMS_PBOC_TAB">
					<@tabTitle pane_id="pane11" active="true" title="人行征信"/>
				</@buttonAuth>
				<@buttonAuth code="AMS_CIS5">
					<@tabTitle pane_id="pane12" title="第三方征信"/>     
				</@buttonAuth>
			</@tabNav>
			<@tabContent style="width:98.5%">
				<@buttonAuth code="AMS_PBOC_TAB">
					<@tabPane id="pane11" active="true" >
	     				<#include "creditTabView_V1.ftl"/>
	        		</@tabPane>
        		</@buttonAuth>
				<@buttonAuth code="AMS_CIS5">
	    			<@tabPane id="pane12">
	    				<#include "../../common/cis5OrgProductBut.ftl"/>
	    			</@tabPane>
	    		</@buttonAuth>
		   	</@tabContent>
		</@tab>
	</@panelBody>
</@panel>