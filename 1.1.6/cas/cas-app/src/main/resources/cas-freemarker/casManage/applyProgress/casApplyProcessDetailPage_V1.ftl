<#include "/layout.ftl"/>
<@body>
	<@gotop/>
	<@field label="申请类型" hidden="true">
		<@input id="appType"  name="appType" value="${(appType)!}" readonly="true"/>
		<@input id="appNo"  name="appNo" value="${(appNo)!}" readonly="true"/>
	</@field>
	<@panel head="申请进度明细【${(appNo)!}】${(oldAppNo?? && oldAppNo!='')?string('【重审件】','')}${(returnFlag?? && returnFlag!='')?string('【${(returnFlag)!}】','')}">
		<@panelBody>
			<@tab  style="height:480px;overflow:auto;">
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
		        	<#--<#if !(applyOperateDto?? && applyOperateDto.isFreeTelCheck?? && applyOperateDto.isFreeTelCheck == "Y")>-->
		        		<@tabTitle pane_id="pane4" title="电话调查信息"/>
		    	</@tabNav>
		    	<@tabContent>
		    		<#if appType??>
		    	 		<#if appType == "A" || appType == "B">
		        			<@tabPane id="pane1" active="true">
								<#include "../../casTask/apply/applyView/V1/mainCardInfoView_V1.ftl"/>
							</@tabPane>
							<@tabPane id="pane2">
			     				<#include "../../casTask/apply/applyView/V1/annexEviInfoView_V1.ftl"/>
			        		</@tabPane>
			        		<@tabPane id="pane3">
			     				<#include "../../casTask/apply/applyView/V1/mainCardOtherInfoView_V1.ftl"/>
			        		</@tabPane>
						<#elseif appType == "S">
							<@tabPane id="pane1" active="true">
								<#include "../../casTask/apply/applyView/V1/attachCardInfoView_V1.ftl"/>
							</@tabPane>
							<@tabPane id="pane3">
			     				<#include "../../casTask/apply/applyView/V1/attachCardOtherInfoView_V1.ftl"/>
			        		</@tabPane>
		        		</#if>
			        		<@tabPane id="pane4">
					        	<#include "../../casTask/apply/applyView/V1/telSuveryInfoView_V1.ftl"/>
					        </@tabPane>
		        	</#if>
		   		</@tabContent>
	 		</@tab>
			<@fieldset>
				<#include "../../casTask/apply/common/applyCreditInfoButC_V1.ftl"/>
	 		</@fieldset>
	 		<@form id="checkRsForm" cols="4">
	 			<@fieldset legend="审批结果信息">
		        	<@row>
						<@field label="自动审批结果:" field_ar="9" label_ar="14" input_ar="20">
							<@input value="${(applyRiskInfoDto.content)!}" label_only="true"/>
						</@field>
						<@field label="评分值:"field_ar="9" label_ar="14" input_ar="20">
							<@input id="pointResult" value="${(applyOperateDto.pointResult)!}" label_only="true"/>
						</@field>
						<@field label="系统建议额度:"field_ar="9" label_ar="14" input_ar="20">
				     		<@input id="sugLmt" value="${(applyOperateDto.sugLmt?string('#.##'))!}" label_only="true"/>
						</@field>
						<#--<@field label="附件建议额度:">
				     		<@input value="${(applyOperateDto.annexEviLmt?string('#.##'))!}" label_only="true"/>
						</@field>-->
						<#--<@field label="快速审批标志:">
				           <@dictSelect dicttype="Indicator" value="${(applyOperateDto.approveQuickFlag)!'N'}" label_only="true"/>
				        </@field>-->
				        <#--<@field label="优先处理标志">
				           <@dictSelect dicttype="Indicator" value="${(applyOperateDto.isPriority)!'N'}" label_only="true"/>
				        </@field>-->
					
						<@field label="初审结果:"field_ar="9" label_ar="14" input_ar="20">
					        <@dictSelect dicttype="BasicCheckResult" value="${(applyNodeInquiryData.checkResult)!}" label_only="true"/>
					    </@field>
					    </@row>
					<@row>
					   	<#if applyOperateDto.isFreeTelCheck?? && applyOperateDto.isFreeTelCheck == "Y">
					    	<label class="control-label"><input type="checkbox" checked="checked" disabled/>是否免电话调查 </label>
					    <#else>
					    	<@field label="电调结果:"field_ar="9" label_ar="14" input_ar="20">
						        <@dictSelect dicttype="TelCheckResult" value="${(applyNodeTelCheckBisicData.result)!}" label_only="true"/>
						    </@field>
					    </#if>
						<#if appType?? && appType == "A">
							<@field label="是否拒绝附属卡:"field_ar="9" label_ar="14" input_ar="20">
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
							<@field label="是否发送短信:"field_ar="9" label_ar="14" input_ar="20">
								<@dictSelect dicttype="Indicator" value="${(applyOperateDto.isSendSmsRefused)!}" label_only="true"/>
							</@field>
						</#if>
						<@field label="终审结果:"field_ar="9" label_ar="14" input_ar="20">
							<@dictSelect id="finalResult" dicttype="FinalAuditResult" value="${(applyNodeFinalAuditData.finalResult)!}" label_only="true"/>
						</@field>
						<#-- <label class="control-label"><input type="checkbox" 
								value="Y" <#if applyOperateDto.isPrdChange?? && applyOperateDto.isPrdChange == "Y">checked="checked" </#if> disabled/>
							是否允许卡片降级 </label> -->
						<@field label="核准额度:"field_ar="9" label_ar="14" input_ar="20">
				     		<@input id="accLmt" value="${(applyOperateDto.accLmt)!}" label_only="true"/>
						</@field>
						<@field label="大写标准额度:"field_ar="9" label_ar="14" input_ar="20">
				     		<@input id="bigLimit" value="${(bigLimit)!}" label_only="true"/>
						</@field>
					</@row>
					<@buttonAuth code="CAS_APPLY_REMARK">
						<@row>
							<@field label="拒绝原因:" field_ar="9" label_ar="14" input_ar="20">
						    	<@input id="pointResult" value="${(applyOperateDto.refuseCode)!}" label_only="true"/>
							</@field>
						</@row>
					</@buttonAuth>
					<@row>
						<#if (REMARKAMS_APPLY_REVIEW.memoInfo)??>
						<@field label="复核备注:" field_ar="32" label_ar="4" input_ar="32">
							<@textarea value="${(REMARKAMS_APPLY_REVIEW.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
						</@field>
						</#if>
					</@row>
					<@row>
						<#if (REMARKCAS_APPLY_PRE_CHECK.memoInfo)??>
							<@field label="预审备注:" field_ar="36" label_ar="4" input_ar="32">
								<@textarea  value="${(REMARKCAS_APPLY_PRE_CHECK.memoInfo)!}"  rows="1" label_only="true" style="margin-bottom:5px"/>
							</@field>
						</#if>
					</@row>
					<@buttonAuth code="CAS_APPLY_REMARK">
						<@row>
							<#if (REMARKCAS_APPLY_BASIC_CHECK.memoInfo)??>
			   	 			<@field label="初审备注:" field_ar="32" label_ar="4" input_ar="32">
								<@textarea value="${(REMARKCAS_APPLY_BASIC_CHECK.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
							</@field>
							</#if>
			   	 		</@row>
			   	 		<@row>
							<#if (REMARKCAS_APPLY_TEL_SURVEY.memoInfo)??>
			   	 			<@field label="电调备注:" field_ar="32" label_ar="4" input_ar="32">
								<@textarea value="${(REMARKCAS_APPLY_TEL_SURVEY.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
							</@field>
							</#if>
						</@row>
			   	 		<@row>
							<#if (REMARKCAS_APPLY_FINALAUDIT.memoInfo)??>
			   	 			<@field label="终审备注:" field_ar="32" label_ar="4" input_ar="32">
								<@textarea value="${(REMARKCAS_APPLY_FINALAUDIT.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
							</@field>
							</#if>
			   	 		</@row>
		   	 		</@buttonAuth>
		   	 		<@row>
						<#if (REMARKSYSAUTO.memoInfo)??>
		   	 			<@field label="系统备注:" field_ar="32" label_ar="4" input_ar="32">
							<@textarea value="${(REMARKSYSAUTO.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
						</@field>
						</#if>
		   	 		</@row>
					<#if (REMARKCAS_APPLY_FILE_MANAGE.memoInfo)??>
						<@field label="归档备注:" field_ar="32" label_ar="4" input_ar="32">
							<@textarea value="${(REMARKCAS_APPLY_FILE_MANAGE.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
						</@field>
					</#if>
					<@row>
						<#if (REMARKCAS_APPLY_DELETE.memoInfo)??>
							<@field label="进件取消备注:" field_ar="32" label_ar="4" input_ar="32">
								<@textarea value="${(REMARKCAS_APPLY_DELETE.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
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

        //如果申请件信息存在 进入页面则直接弹出申请件信息的窗口
        window.onload = function () {
			<#if hisApplyInfoList?? && hisApplyInfoList?size gt 0>
			var url = '${base}/cas_commonDialog/applyHistoryLayout?appNo=${(appNo)!}';
			dialogInfo('[${(appNo)!}]历史申请信息',1000,500,url);
			<#else>
			ar_.buttonDisable('historyBtn',true);
			</#if>
			accLmtKeyUp();
        };
		<#--关闭按钮 -->
		var closeBtn = function(){
			window.close();
		};

		<#--数字转化金额大写-->
		function accLmtKeyUp(){
				var accLmt = '${(bigLimit)!}';
			if (accLmt == 0 || accLmt == '') {
				$('#bigLimit').html("零");
			} else  {
				var unit = "千百拾亿千百拾万千百拾元角分", str = "";
				accLmt += "00";
				var p = accLmt.indexOf('.');
				if (p >= 0) {
					accLmt = accLmt.substring(0, p) + accLmt.substr(p + 1, 2);
				}
				unit = unit.substr(unit.length - accLmt.length);
				for (var i = 0; i < accLmt.length; i++) {
					str += '零壹贰叁肆伍陆柒捌玖'.charAt(accLmt.charAt(i)) + unit.charAt(i);
				}
				str = str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
				$('#bigLimit').html(str);
			}
			accLmtNum--;
		}
	</script>

</@body>		