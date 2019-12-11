<#-- 补件操作页面 -->
<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panel head="附件证明信息 [${(appNo)!}]${(oldAppNo?? && oldAppNo!='')?string('[重审件]','')}${(returnFlag?? && returnFlag!='')?string('[${(returnFlag)!}]','')}" 
		class="col-ar-24" style="height:670px;overflow-y:scroll;width:56% ;margin-bottom:5px;">
		<@panelBody>
			<@form>
				<@tab>
					<@tabNav>
			    	 	<#if appType??>
			    	 		<#if appType == "A">
			        			<@tabTitle pane_id="pane1" title="主附同申申请信息"/>
			        			<@tabTitle pane_id="pane2" active="true" title="附件证明信息"/>
			        		<#elseif appType == "B">
				        		<@tabTitle pane_id="pane1" title="独立主卡申请信息"/>
				        		<@tabTitle pane_id="pane2" active="true" title="附件证明信息" />
							<#elseif appType == "S">
								<@tabTitle pane_id="pane1" active="true" title="独立附卡申请信息"/>
			        		</#if>
				        	<@tabTitle pane_id="pane3"  title="其他信息" />
			        	</#if>
			    	</@tabNav>
			    	<@tabContent>
			    		<#if appType??>
			    	 		<#if appType == "A" || appType == "B">
			        			<@tabPane id="pane1">
									<#include "../../applyView/V1/mainCardInfoView_V1.ftl"/>
								</@tabPane>
								<@tabPane id="pane2" active="true">
				     				<#include "../../applyView/V1/annexEviInfoView_V1.ftl"/>
				        		</@tabPane>
				        		<@tabPane id="pane3" >
				     				<#include "../../applyView/V1/mainCardOtherInfoView_V1.ftl"/>
				        		</@tabPane>
							<#elseif appType == "S">					
								<@tabPane id="pane1" active="true">
									<#include "../../applyView/V1/attachCardInfoView_V1.ftl"/>
								</@tabPane>
								<@tabPane id="pane3" >
				     				<#include "../../applyView/V1/attachCardOtherInfoView_V1.ftl"/>
				        		</@tabPane>
			        		</#if>
			        	</#if>
			   		</@tabContent>
		 		</@tab>
	 		</@form>
		</@panelBody>
	</@panel>
	<@panel head="补件操作信息" class="col-ar-12" style="width:44%; height:670px; overflow-y:scroll; margin-bottom:5px;">
		<@panelBody>
			<#include "../patchOperateInfo.ftl"/>
		</@panelBody>
	</@panel>
</@body>