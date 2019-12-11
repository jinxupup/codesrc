<#include "/layout.ftl" />

<@body>
	<@panel head="申请件批量导入界面">
		<@panelBody>
			<@tab id="tab">
		    	<@tabNav>
		    		<@tabTitle pane_id="pane1" active="${isEdit?string('false','true')}" title="申请件导入新增"/>
		        	<@tabTitle pane_id="pane2" active="${isEdit?string('true','false')}" title="申请件导入信息列表"/>
		    	</@tabNav>
		    	<@tabContent> 
		        	<@tabPane id="pane1" active="${isEdit?string('false','true')}">
		     			<#include "addApplyInfoFile_V1.ftl"/>
				 	</@tabPane>
		        	<@tabPane id="pane2" active="${isEdit?string('true','false')}">
		            	<#include "applyInfoFileList_V1.ftl"/>
		        	</@tabPane>
		   		</@tabContent>
			</@tab>
		</@panelBody>
	</@panel>
</@body>
