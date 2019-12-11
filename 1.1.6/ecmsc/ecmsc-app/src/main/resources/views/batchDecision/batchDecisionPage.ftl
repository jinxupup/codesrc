<#include "/layout.ftl" />
<@body>
	<@panel head="信息导入界面">
		<@panelBody>
			<@tab id="tab">
		    	<@tabNav>
		    		<@tabTitle pane_id="pane1"  title="决策跑批文件导入新增"/>
		        	<@tabTitle pane_id="pane2" title="决策跑批信息列表"/>
		    	</@tabNav>
		    	<@tabContent> 
		        	<@tabPane id="pane1" active="true">
		     			<#include "addUploadInfoFile.ftl"/>
				 	</@tabPane>
		        	<@tabPane id="pane2" >
		            	<#include "uploadInfoFileList.ftl"/>
		        	</@tabPane>
		   		</@tabContent>
			</@tab>
		</@panelBody>
	</@panel>
</@body>
