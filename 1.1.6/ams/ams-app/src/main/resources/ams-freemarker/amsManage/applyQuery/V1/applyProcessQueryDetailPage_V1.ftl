<#include "/layout.ftl"/>
<@body>
	<@gotop/>
	<@field label="申请类型" hidden="true">
		<@input id="appType"  name="appType" value="${(appType)!}" readonly="true"/>
		<@input id="appNo"  name="appNo" value="${(appNo)!}" readonly="true"/>
	</@field>
	<@panel head="申请进度明细【${(appNo)!}】${(oldAppNo?? && oldAppNo!='')?string('【重审件】','')}${(returnFlag?? && returnFlag!='')?string('【${(returnFlag)!}】','')}">
		<@panelBody style="width:99.5%;">
			<@tab  style="height:380px;overflow:auto;">
				<@tabNav>
		    	 	<#if appType??>
		    	 		<#if appType == "A">
		        			<@tabTitle pane_id="pane1" active="true" title="主附同申申请信息"/>
		        			<@tabTitle pane_id="pane2"  title="附件证明信息"/>
		        		<#elseif appType == "B">
			        		<@tabTitle pane_id="pane1" active="true"  title="独立主卡申请信息"/>
			        		<@tabTitle pane_id="pane2"  title="附件证明信息" />
						<#elseif appType == "S">
							<@tabTitle pane_id="pane1" active="true" title="独立附卡申请信息"/>
		        		</#if>
		        	</#if>	
		        	<@tabTitle pane_id="pane3" title="其他信息"/>
		        	<#--<#if !(applyOperateDto?? && applyOperateDto.isFreeTelCheck?? && applyOperateDto.isFreeTelCheck == "Y")>
		        		<@tabTitle pane_id="pane4" title="电话调查信息"/>
		        	</#if>
					<@tabTitle pane_id="pane5"  title="征信信息"/>-->
		    	</@tabNav>
		    	<@tabContent style="width:98.5%;">
		    		<#if appType??>
		    	 		<#if appType == "A" || appType == "B">
		        			<@tabPane id="pane1" active="true">
								<#include "../../../amsTask/applyCC/applyView/V1/mainCardInfoView_V1.ftl"/>
							</@tabPane>
							<@tabPane id="pane2">
			     				<#include "../../../amsTask/applyCC/applyView/V1/annexEviInfoView_V1.ftl"/>
			        		</@tabPane>
			        		<@tabPane id="pane3">
			     				<#include "../../../amsTask/applyCC/applyView/V1/mainCardOtherInfoView_V1.ftl"/>
			        		</@tabPane>
						<#elseif appType == "S">
							<@tabPane id="pane1" active="true">
								<#include "../../../amsTask/applyCC/applyView/V1/attachCardInfoView_V1.ftl"/>
							</@tabPane>
							<@tabPane id="pane3">
			     				<#include "../../../amsTask/applyCC/applyView/V1/attachCardOtherInfoView_V1.ftl"/>
			        		</@tabPane>
		        		</#if>
		        		<#if !(applyOperateDto?? && applyOperateDto.isFreeTelCheck?? && applyOperateDto.isFreeTelCheck == "Y")>
			        		<@tabPane id="pane4">
					        	<#include "../../../amsTask/applyCC/applyView/V1/telSuveryInfoView_V1.ftl"/>
					        </@tabPane>
			        	</#if>
		        		<@tabPane id="pane5">
	     					<#include "../../../amsTask/applyCC/applyView/V1/creditInfoTabView_V1.ftl"/>
		        		</@tabPane>
		        	</#if>
		   		</@tabContent>
	 		</@tab>
			<@fieldset>
				<#include "../../../amsTask/applyCC/common/applyCreditInfoBut_V1.ftl"/>
	 		</@fieldset>
	 		<@form id="checkRsForm" cols="4">
	 			<@fieldset legend="审批结果信息">
		        	<@row>
						<@field label="自动审批结果:" field_ar="7" label_ar="16" input_ar="20">
							<@dictSelect dicttype="Content" value="${(applyRiskInfoDto.content)!}" label_only="true"/>
						</@field>
					<#if (applyNodeInquiryData.checkResult)??>
						<@field label="初审结果:">
							<@dictSelect dicttype="BasicCheckResult" value="${(applyNodeInquiryData.checkResult)!}" label_only="true"/>
						</@field>
					</#if>
						<#if applyOperateDto.isFreeTelCheck?? && applyOperateDto.isFreeTelCheck == "Y">
                        <label class="control-label"><input type="checkbox" checked="checked" disabled/>是否免电话调查 </label>
						<#else>
						<#if (applyNodeTelCheckBisicData.result)??>
							<@field label="电调结果:">
								<@dictSelect dicttype="TelCheckResult" value="${(applyNodeTelCheckBisicData.result)!}" label_only="true"/>
							</@field>
						</#if>
						</#if>
					<#if (applyNodeFinalAuditData.finalResult)??>
						<@field label="终审结果:">
							<@dictSelect id="finalResult" dicttype="FinalAuditResult" value="${(applyNodeFinalAuditData.finalResult)!}" label_only="true"/>
						</@field>
					</#if>
						<#--<@field label="评分值:">
							<@input id="pointResult" value="${(applyOperateDto.pointResult)!}" label_only="true"/>
						</@field>
						<@field label="系统建议额度:">
				     		<@input id="sugLmt" value="${(applyOperateDto.sugLmt?string('#.##'))!}" label_only="true"/>
						</@field>
						<@field label="附件建议额度:">
				     		<@input value="${(applyOperateDto.annexEviLmt?string('#.##'))!}" label_only="true"/>
						</@field>
						<@field label="快速审批标志:">
				           <@dictSelect dicttype="Indicator" value="${(applyOperateDto.approveQuickFlag)!'N'}" label_only="true"/>
				        </@field>-->
				        <#--<@field label="优先处理标志">
				           <@dictSelect dicttype="Indicator" value="${(applyOperateDto.isPriority)!'N'}" label_only="true"/>
				        </@field>
					</@row>
					<@row>-->


						<#--<#if appType?? && appType == "A">
							<@field label="是否拒绝附属卡:">
								<@multipleSelect value="${(ifAttachRefuse)!''}" label_only="true">
								    <#if attachNoMap??>
								    	<#list attachNoMap?keys as key>
								    		<@option key="${(key)}" select_value="${(ifAttachRefuse)!''}" showcode="false">是否拒绝<#if attachNum?? && attachNum != 0>第${(key)}张</#if>附卡-${attachNoMap[key]!}</@option>
								    	</#list>
								    </#if>
								</@multipleSelect>
							</@field>
						</#if>
						<#if sendRefuseMessage?? && sendRefuseMessage=="Y" && applyNodeInquiryData?? && applyNodeInquiryData.result?? && applyNodeInquiryData.result=='R'>
							<@field label="是否发送短信:">
								<@dictSelect dicttype="Indicator" value="${(applyOperateDto.isSendSmsRefused)!}" label_only="true"/>
							</@field>
						</#if>-->

						<#-- <label class="control-label"><input type="checkbox" 
								value="Y" <#if applyOperateDto.isPrdChange?? && applyOperateDto.isPrdChange == "Y">checked="checked" </#if> disabled/>
							是否允许卡片降级 </label> -->

					</@row>
					<@row>
						<@field label="系统建议额度:">
							<@input id="sugLmt" value="${(applyOperateDto.sugLmt?string('#.##'))!}" label_only="true"/>
						</@field>
					<#if (applyOperateDto.annexEviLmt?string('#.##'))??>
						<@field label="附件建议额度:">
							<@input value="${(applyOperateDto.annexEviLmt?string('#.##'))!}" label_only="true"/>
						</@field>
					</#if>
					<#if (applyOperateDto.accLmt)??>
						<@field label="核准额度:">
							<@input id="accLmt" value="${(applyOperateDto.accLmt)!}" label_only="true"/>
						</@field>
					</#if>
					<#if (bigLimit)??>
						<@field label="大写标准额度:">
							<@input id="bigLimit" value="${(bigLimit)!}" label_only="true"/>
						</@field>
					</#if>
						<#--<@field label="拒绝原因:" field_ar="36" label_ar="4" input_ar="32">
					    	<@input id="pointResult" value="${(applyOperateDto.refuseCode)!}" label_only="true"/>
						</@field>-->
					</@row>
					<@row>
						<@field label="复核备注:" >
							<@textarea value="${(REMARKAMS_APPLY_REVIEW.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
						</@field>
					<#if (REMARKCAS_APPLY_BASIC_CHECK.memoInfo)??>
		   	 			<@field label="初审备注:" >
							<@textarea value="${(REMARKCAS_APPLY_BASIC_CHECK.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
						</@field>
					</#if>
					<#if (REMARKCAS_APPLY_TEL_SURVEY.memoInfo)??>
		   	 			<@field label="电调备注:" >
							<@textarea value="${(REMARKCAS_APPLY_TEL_SURVEY.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
						</@field>
					</#if>
					<#if (REMARKCAS_APPLY_FINALAUDIT.memoInfo)??>
		   	 			<@field label="终审备注:" >
							<@textarea value="${(REMARKCAS_APPLY_FINALAUDIT.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
						</@field>
					</#if>
		   	 		</@row>
				</@fieldset>
	 		</@form>		
		</@panelBody>
	</@panel>
	<@toolbar align="center">
		<#if detailBtnFlag?? && detailBtnFlag == "Y">
			<@button fa="close" click="closeBtn" name="关闭"/>
		<#else>
			<@backButton name="返回" fa="reply"/>
		</#if>
	</@toolbar>

	<script type="text/javascript">
		<#--关闭按钮 -->
		var closeBtn = function(){
			window.close();	
		}
	</script>
	
</@body>		