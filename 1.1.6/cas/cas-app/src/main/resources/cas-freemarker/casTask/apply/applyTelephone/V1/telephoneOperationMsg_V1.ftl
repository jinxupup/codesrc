
<#include "../../common/applyCreditInfoBut_V1.ftl"/>
<@fieldset legend="历史审批信息">
	<#--<@row>-->
		<#--<@field label="评分值:" field_ar="9" label_ar="12" input_ar="24">-->
			<#--<@input id="pointResult" value="${(applyOperateDto.pointResult)!}" label_only="true"/>-->
		<#--</@field>-->
		<#--<@field label="系统建议额度:" field_ar="9" label_ar="14" input_ar="22">-->
			<#--<div class="col-ar-24" style="padding-right:0px;padding-left:0px;"">-->
     			<#--<@input id="sugLmt" value="${(applyOperateDto.sugLmt?string('#.##'))!}" label_only="true"/>-->
			<#--</div>-->
			<#--<div class="col-ar-12" style="padding-right:0px;">
				<@button name="建议额度" click="sugLmtBtn"/>
			</div>-->
		<#--</@field>-->
		<#--<@field label="快速审批标志" field_ar="9" label_ar="12" input_ar="24">
           <@dictSelect dicttype="Indicator" value="${(applyOperateDto.approveQuickFlag)!'N'}" label_only="true"/>
        </@field>
        <@field label="优先处理标志" field_ar="9" label_ar="12" input_ar="24">
           <@dictSelect dicttype="Indicator" value="${(applyOperateDto.isPriority)!'N'}" label_only="true"/>
        </@field>-->
	<#--</@row>-->
	<@row>
		<@field point_flag="true" point="初审结果:"  field_ar="18" label_ar="16" input_ar="18"
			point_style=";color:${(applyNodeInquiryData?? && applyNodeInquiryData.result?? && applyNodeInquiryData.result=='R')?string('#FF0000','#337ab7')!};">
     		<@dictSelect dicttype="TelCheckResult" value="${(applyNodeInquiryData.checkResult)!}" label_only="true"/>
		</@field>
		<@field label="初审额度:" field_ar="18" label_ar="18" input_ar="18">
     		<@input value="${(applyOperateDto.chkLmt)!}" label_only="true"/>
		</@field>
		<@field label="初审拒绝原因:" field_ar="36" label_ar="8" input_ar="28">
     		<@multipleSelect options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
				'{"type":"ApplyRejectReason"}','code','codeName') value="${(applyNodeInquiryData.refuseCode)!}" label_only="true"/>
		</@field>
		<@field label="补件:" field_ar="36" label_ar="8" input_ar="28">
	 		<@multipleSelect options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
				'{"type":"ApplyPatchBoltType"}','code','codeName') value="${(patchBoltString)!''}" label_only="true"/>
		</@field>
		<#--<@field label="特批" field_ar="9" label_ar="12" input_ar="24">
     		<@dictSelect dicttype="SpecialApprove" name="tmAppMain.specialApprove" value="${(applyOperateDto.specialApprove)!}"/>
		</@field>-->
	</@row>
	<@row>
	<#if (REMARKCAS_APPLY_PRE_CHECK.memoInfo)??>
		<@field label="预审备注:"  field_ar="36" label_ar="8" input_ar="28">
			<@textarea  value="${(REMARKCAS_APPLY_PRE_CHECK.memoInfo)!}"  rows="1" label_only="true"/>
		</@field>
	</#if>
	<#if (REMARKCAS_APPLY_BASIC_CHECK.memoInfo)??>
		<@field label="初审备注:" field_ar="36" label_ar="8" input_ar="28">
			<@textarea value="${(REMARKCAS_APPLY_BASIC_CHECK.memoInfo)!}" label_only="true"/>
		</@field>
	</#if>
		<#if applyNodeInquiryData?? && applyNodeInquiryData.result?? && applyNodeInquiryData.result=='O'>
			<@field id="patchBoltRemarkDiv" label="补件备注:"  field_ar="36" label_ar="8" input_ar="28">
				<@textarea value="${(PATCHBOLTAPPLYINFO_PATCHBOLT.memoInfo)!}" label_only="true"/>
			</@field>
		</#if>
	<#if (REMARKCAS_APPLY_FINALAUDIT.memoInfo)??>
		<@field label="终审退回备注:"  field_ar="36" label_ar="8" input_ar="28">
			<@textarea value="${(REMARKCAS_APPLY_FINALAUDIT.memoInfo)!}" label_only="true"/>
		</@field>
	</#if>
	</@row>
</@fieldset>
<@fieldset legend="电调操作信息" >
	<#--<@row>-->
		<#--<@field label="决策建议:" field_ar="18" label_ar="16" input_ar="20">-->
			<#--<@input value="${(applyRiskInfoDto.content)!}" label_only="true"/>-->
		<#--</@field>-->
		<#--<@field label="建议额度:" field_ar="18" label_ar="16" input_ar="20">-->
			<#--<@input value="${(applyRiskInfoDto.suggestAmt)!}" label_only="true"/>-->
		<#--</@field>-->
	<#--</@row>-->
	<@row>
		<@field label="审批意见"  field_ar="36" label_ar="8" input_ar="28">
			<div class="col-ar-18" style="padding-left:0px;">
	     		<@dictSelect id="telCheckResult" dicttype="TelCheckResult" change="telCheckResultChange"
	     			name="query.telCheckResult" value="${(applyNodeTelCheckBisicData.result)!'P'}" nullable="false"/>
			</div>
			<div id="isFreeTelCheckDiv" class="col-ar-18" style="${((!applyNodeTelCheckBisicData.result??)||(applyNodeTelCheckBisicData.result=='P'))?string('','display:none')}">
				<label style="padding-left:5px;font-family:'宋体';"><input id="isFreeTelCheck" type="checkbox" name="tmAppMain.isFreeTelCheck" value="Y"
     				${(applyOperateDto.isFreeTelCheck?? && applyOperateDto.isFreeTelCheck == 'Y')?string('checked','')}/>是否已完成电调</label>
			</div>
		</@field>
	</@row>
	<@row>
		<@field id="refuseCodeDiv" label="拒绝原因"  field_ar="36" label_ar="8" input_ar="28"
			hidden="${(applyNodeTelCheckBisicData?? && applyNodeTelCheckBisicData.checkResult?? && applyNodeTelCheckBisicData.checkResult=='R')?string('false','true')}">
     		<@multipleSelect id="refuseCode" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
				'{"type":"ApplyRejectReason"}','code','codeName') name="refuseCode" value="${(applyNodeTelCheckBisicData.refuseCode)!}"/>
		</@field>
		<#if appType?? && appType == "A">
			<@field label="是否拒绝附属卡"  field_ar="36" label_ar="8" input_ar="28">
				<@multipleSelect name="ifAttachRefuse" value="${(ifAttachRefuse)!''}" showfilter="true">
				    <#if attachNoMap??>
				    	<#list attachNoMap?keys as key>
				    		<@option key="${(key)}" select_value="${(ifAttachRefuse)!''}" showcode="false">是否拒绝<#if attachNum?? && attachNum != 1>第${(key)}张</#if>附卡-${attachNoMap[key]!}</@option>
				    	</#list>
				    </#if>
				</@multipleSelect>
			</@field>
		</#if>
		<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
			<@field id="sms" label="是否发送短信"  field_ar="36" label_ar="8" input_ar="28"
				hidden="${(applyNodeTelCheckBisicData?? && applyNodeTelCheckBisicData.result??&&applyNodeTelCheckBisicData.result=='R')?string('false','true')}">
				<@dictSelect id="isSendSms" dicttype="Indicator" name="tmAppMain.isSendSmsRefused" value="${(applyOperateDto.isSendSmsRefused)!'Y'}" nullable="false"/>
			</@field>
		</#if>
	</@row>
	<@row>
		<@field label="电话调查备注" field_ar="36" label_ar="8" input_ar="28">
			<@textarea id="telRemark" name="query.telRemark" value="${(REMARKCAS_APPLY_TEL_SURVEY.memoInfo)!}" rows="3"/>
		</@field>
	</@row>
	<@row>
		<@field  label="申请件标签" field_ar="36" label_ar="8" input_ar="28">
			<@multipleSelect id="FlagApp" name="flagApp" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
			'{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}" showfilter="true"/>
		</@field>
	</@row>
</@fieldset>

<script type="text/javascript">
	$(function(){
		<#--补件备注显示初始化-->
		var applyPatchBoltType = '${(patchBoltString)!}';
		if(applyPatchBoltType.indexOf('P') > 0 || applyPatchBoltType == 'P'){
			$('#patchBoltRemarkDiv').show();
		}
		<#--电调审批结果初始化-->
		var telCheckResult = '${(applyNodeTelCheckBisicData.result)!"P"}';
		if(telCheckResult=='R'){
			$('#refuseCodeDiv').css("display","block");
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',true,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.telRemark',false,'notEmpty');
		}else if(telCheckResult.indexOf("B") != -1){
			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.telRemark',true,'notEmpty');
		}else{
			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.telRemark',false,'notEmpty');
		}
	});
	<#--电调审批结果的改变-->
	var telCheckResultChange = function(that){
		var telCheckResult = $(that).val();
		$('#isFreeTelCheck').prop('checked',false);
		if(telCheckResult.indexOf("B") != -1){
			$('#isFreeTelCheckDiv').css("display","none");//免电话调查
			<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
				$('#sms').css("display","none");
				$('#isSendSms').val('');
			</#if>
			$('#refuseCodeDiv').css("display","none");
			$('#refuseCode').multipleSelect("uncheckAll");
			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.telRemark',true,'notEmpty');
			$('#applyInputForm').data('bootstrapValidator').validateField('query.telRemark');
		}else if(telCheckResult == 'R'){
			$('#isFreeTelCheckDiv').css("display","none");//免电话调查
			<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
				$('#isSendSms').val('Y');
				$('#sms').css("display","block");
			</#if>
			$('#refuseCode').multipleSelect("uncheckAll");
			$('#refuseCodeDiv').css("display","block");
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',true,'notEmpty').validateField('refuseCode');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.telRemark',false,'notEmpty');
		}else{
			$('#isFreeTelCheckDiv').css("display","block");//免电话调查
			<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
				$('#sms').css("display","none");
				$('#isSendSms').val('');
			</#if>
			$('#refuseCodeDiv').css("display","none");
			$('#refuseCode').multipleSelect("uncheckAll");
			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.telRemark',false,'notEmpty');
		}
	}
	<#--建议额度按钮-->
	var sugLmtBtn = function(){
		$.ajax({
			url:"${base}/cas_applyBasicCheck/getSuggestLimit",
			type:"post",
			dataType : "json",
			data:$("#applyInputForm").serialize(),
			success:function(ref){
				if(ref.s){
					$("#sugLmt").val(ref.obj);
					$("#pointResult").val(ref.code);
				}else{
					alert(ref.msg);
				}
			}
		});
	}
</script>