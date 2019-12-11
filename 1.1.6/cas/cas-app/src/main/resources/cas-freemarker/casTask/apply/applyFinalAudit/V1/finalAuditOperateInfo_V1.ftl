
<#include "../../common/applyCreditInfoBut_V1.ftl"/>
<@fieldset legend="历史审批信息">
	<@row>
		<@field point_flag="true" point="初审结果:" field_ar="18" label_ar="16" input_ar="12"
			point_style=";color:${(applyNodeInquiryData?? && applyNodeInquiryData.checkResult?? && applyNodeInquiryData.checkResult=='R')?string('#FF0000','#337ab7')!};">
	        <@dictSelect dicttype="BasicCheckResult" value="${(applyNodeInquiryData.checkResult)!}" label_only="true"/>
	    </@field>
		<@field label="初审额度:" field_ar="18" label_ar="18" input_ar="18">
			<@input id="chkLmt" value="${(applyOperateDto.chkLmt)!}" label_only="true"/>
		</@field>
	    <@field label="初审拒绝原因:" field_ar="36" label_ar="8" input_ar="28">
     		<@multipleSelect options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
				'{"type":"ApplyRejectReason"}','code','codeName') value="${(checkRefuseCodes)!}" label_only="true"/>
		</@field>
		<#--<#if applyOperateDto.isFreeTelCheck?? && applyOperateDto.isFreeTelCheck == 'Y'>
			<@field label="" field_ar="36" label_ar="8" input_ar="28">
				<div>
					<label style="padding-left:5px;"><input type="checkbox" value="Y" checked disabled="true"/>免电调</label>
				</div>
			</@field>
		</#if>-->
	</@row>
	<#if !(applyOperateDto.isFreeTelCheck?? && applyOperateDto.isFreeTelCheck == 'Y')>
		<@row>
			<@field point_flag="true" point="电调结果:" field_ar="36" label_ar="8" input_ar="28"
				point_style=";color:${(applyNodeTelCheckBisicData?? && applyNodeTelCheckBisicData.result?? && applyNodeTelCheckBisicData.result=='R')?string('#FF0000','#337ab7')!};">
		        <@dictSelect dicttype="TelCheckResult" value="${(applyNodeTelCheckBisicData.result)!}" label_only="true"/>
		    </@field>
			<@field label="电调拒绝原因:" field_ar="36" label_ar="8" input_ar="28">
				<#if tmAppAudit.refuseCode?? && applyNodeTelCheckBisicData.result?? && applyNodeTelCheckBisicData.result=='R'>
					<@input id="chkLmt" value="${(tmAppAudit.telRefuseCode)!}" label_only="true"/>
				<#else>
					<#if applyNodeTelCheckBisicData.result?? && applyNodeTelCheckBisicData.result=='R'>
						<@input id="chkLmt" value="${(tmAppMain.refuseCode)!}" label_only="true"/>
					</#if>
				</#if>
			</@field>
		</@row>
	</#if>
	<@row>
		<#if (REMARKAMS_APPLY_REVIEW.memoInfo)??>
			<@row>
				<@field label="复核备注:" field_ar="36" label_ar="8" input_ar="28">
					<@textarea  value="${(REMARKAMS_APPLY_REVIEW.memoInfo)!}"  rows="1" label_only="true"/>
				</@field>
			</@row>
		</#if>
	<#if (REMARKCAS_APPLY_PRE_CHECK.memoInfo)??>
		<@field label="预审备注:"  field_ar="36" label_ar="8" input_ar="28">
			<@textarea  value="${(REMARKCAS_APPLY_PRE_CHECK.memoInfo)!}"  label_only="true"/>
		</@field>
	</#if>
	<#if (REMARKCAS_APPLY_BASIC_CHECK.memoInfo)??>
		<@field label="初审备注:"  field_ar="36" label_ar="8" input_ar="28">
			<@textarea value="${(REMARKCAS_APPLY_BASIC_CHECK.memoInfo)!}"label_only="true"/>
		</@field>
	</#if>
	</@row>
	<#if !(applyOperateDto.isFreeTelCheck?? && applyOperateDto.isFreeTelCheck == 'Y')&&(REMARKCAS_APPLY_TEL_SURVEY.memoInfo)??>
		<@row>
			<@field label="电调备注:"  field_ar="36" label_ar="8" input_ar="28">
				<@textarea value="${(REMARKCAS_APPLY_TEL_SURVEY.memoInfo)!}" label_only="true"/>
			</@field>
		</@row>
	</#if>
	<#if applyNodeInquiryData?? && applyNodeInquiryData.result?? && applyNodeInquiryData.result=='O'>
		<@field id="patchBoltRemarkDiv" label="补件备注:"  field_ar="36" label_ar="8" input_ar="28" >
			<@textarea value="${(PATCHBOLTAPPLYINFO_PATCHBOLT.memoInfo)!}" label_only="true"/>
		</@field>
	</#if>
</@fieldset>
<@fieldset legend="终审操作信息">
	<@row>
		<@field label="决策建议:" field_ar="18" label_ar="16" input_ar="20">
			<@input value="${(applyRiskInfoDto.content)!}" label_only="true"/>
		</@field>
		<@field label="建议额度:" field_ar="18" label_ar="16" input_ar="20">
			<@input value="${(applyRiskInfoDto.suggestAmt)!}" label_only="true"/>
		</@field>
	</@row>
	<@row>
		<@field label="审批结果" field_ar="36" label_ar="8" input_ar="28">
			<@dictSelect id="finalResult" dicttype="${(applyOperateDto.approveQuickFlag?? && applyOperateDto.approveQuickFlag == 'Y')?string('FinalAuditResult1','FinalAuditResult')}"
				change="finalResultChange(this)" name="query.finalResult" value="${(applyNodeFinalAuditData.finalResult)!'P'}"/><#--快速审批没有退回项-->
		</@field>
		<#if tmAppMain?? && tmAppMain.appType?? && tmAppMain.appType == "A">
			<@field label="是否拒绝附属卡" field_ar="36" label_ar="8" input_ar="28">
				<@multipleSelect name="ifAttachRefuse" value="${(ifAttachRefuse)!''}" showfilter="true">
				    <#if attachNoMap??>
				    	<#list attachNoMap?keys as key>
				    		<@option key="${(key)}" select_value="${(ifAttachRefuse)!''}" showcode="false">是否拒绝<#if attachNum?? && attachNum != 1>第${(key)}张</#if>附卡-${attachNoMap[key]!}</@option>
				    	</#list>
				    </#if>
				</@multipleSelect>
			</@field>
		</#if>
		<@field id="refuseCodeDiv" label="拒绝原因" field_ar="36" label_ar="8" input_ar="28"
			hidden="${(applyNodeFinalAuditData?? && applyNodeFinalAuditData.finalResult?? && applyNodeFinalAuditData.finalResult=='R')?string('false','true')}">
     		<@multipleSelect id="refuseCode" name="refuseCode" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
				'{"type":"ApplyRejectReason"}','code','codeName') value="${(finalRefuseCodes)!}" showfilter="true"/>
		</@field>
		<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
			<@field id="sms" label="是否发送短信" field_ar="36" label_ar="8" input_ar="28"
				hidden="${(applyNodeFinalAuditData?? && applyNodeFinalAuditData.finalResult?? && applyNodeFinalAuditData.finalResult=='R')?string('false','true')}">
	     		<@dictSelect id="isSendSms" dicttype="Indicator" name="tmAppAudit.isSendSmsRefused" value="${(applyOperateDto.isSendSmsRefused)!}"/>
			</@field>
		</#if>
	</@row>
	<@row>
		<@field label="核准额度" field_ar="36" label_ar="8" input_ar="28">
     		<@input id="accLmt" name="tmAppMain.accLmt"  valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入正确的核准额度"}
				readonly="${(applyNodeFinalAuditData?? && applyNodeFinalAuditData.finalResult?? && (applyNodeFinalAuditData.finalResult=='R'||applyNodeFinalAuditData.finalResult?index_of('B') != -1))?string('true','false')}"
			/>
		</@field>
		<@field label="大写标准额度" field_ar="36" label_ar="8" input_ar="28">
     		<@input id="bigLimit" value="${(bigLimit)!}" label_only="true"/>
		</@field>
	</@row>
	<@row>
		<@field label="卡产品代码" field_ar="36" label_ar="8" input_ar="28">
			<@select id="productCd" name="tmAppMain.productCd" value="${(applyOperateDto.productCd)!}" options=cas_('tableMap','productForStatus','AC','C')
			nullable="false" readonly="${(applyOperateDto.isPrdChange?? && applyOperateDto.isPrdChange == 'Y')?string('false','true')}"/>
		</@field>
	</@row>
	<@row>
		<label class="control-label" style="padding-left:100px;">
			<input type="checkbox" name="tmAppPrimCardInfo.isPrdChange"
				   value="Y" <#if applyOperateDto.isPrdChange?? && applyOperateDto.isPrdChange == "Y">checked="checked" </#if>
			onclick="isChecked(this)" /> 是否允许卡片降级
		</label>
	</@row>
	<@row>
		<@field label="终审备注:"  field_ar="36" label_ar="8" input_ar="28">
			<@textarea id="remark" value="${(REMARKCAS_APPLY_FINALAUDIT.memoInfo)!}" name="query.remark" rows="3"/>
		</@field>
	</@row>
	<@row>
		<@field  label="申请件标签"  field_ar="36" label_ar="8" input_ar="28">
			<@multipleSelect id="FlagApp" name="flagApp" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
			'{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}"showfilter="true"/>
		</@field>
	</@row>
</@fieldset>

<script type="text/javascript">

	$('#accLmt').change(function lala() {
		accLmtKeyUp();
	});


	$(function(){
		<#--审批结果初始化-->
		var finalResult = '${(applyNodeFinalAuditData.finalResult)!"P"}';
	    if(finalResult == "P"){<#--如果是通过-->
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppMain.accLmt',true,'notEmpty');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.remark',false,'notEmpty');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
    	}else if(finalResult == "R"){<#--如果是拒绝-->
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',true,'notEmpty');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppMain.accLmt',false,'notEmpty');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.remark',false,'notEmpty');
    	}else if(finalResult.indexOf("B") != -1){ <#--如果是退回-->
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.remark',true,'notEmpty');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppMain.accLmt',false,'notEmpty');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
    	}else{
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.remark',false,'notEmpty');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppMain.accLmt',false,'notEmpty');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
    	}
		<#--大写绑定初始化-->
		accLmtKeyUp();
	});

	<#--数字转化金额大写-->
	var accLmtNum = 1;
	function accLmtKeyUp(){
		if (accLmtNum > 0) {
			var accLmt = '${(bigLimit)!}';
		}else {
			var accLmt = $('#accLmt').val();
		}
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
	
	<#--是否允许卡片降级 联动-->
	function isChecked(that){
		var isChecked = $(that).is(':checked');
        if(isChecked){
			$('#productCd').removeAttr("disabled readonly ");
        }else{
         	$('#productCd').attr({"readonly":"readonly","disabled":"disabled"});
        }
    }
    
	<#--审批结果联动-->
   	var finalResultChange = function(that){
    	var finalResult = $(that).val();
	    if(finalResult == "P"){<#--如果是通过-->
	    	$('#accLmt').val('').removeAttr("disabled readonly ");
	    	$('#bigLimit').val('');
	    	<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
		    	$('#sms').css("display","none");
		    	$('#isSendSms').val('');
		    </#if>
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppMain.accLmt',true,'notEmpty').validateField('tmAppMain.accLmt');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.remark',false,'notEmpty');
    		$('#refuseCodeDiv').css("display","none");
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
            $('#refuseCode').multipleSelect("uncheckAll");
    	}else if(finalResult == "R"){<#--如果是拒绝-->
    		$('#refuseCodeDiv').css("display","block");
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',true,'notEmpty').validateField('refuseCode');
	    	$('#bigLimit').val('');
	    	<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
		    	$('#isSendSms').val('Y');
		    	$('#sms').css("display","block");
		    </#if>
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppMain.accLmt',false,'notEmpty');
    		$('#accLmt').val('').attr({"disabled":"disabled","label_only":"label_only"});
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.remark',false,'notEmpty');
    	}else if(finalResult.indexOf("B") != -1){ <#--如果是退回-->
	    	$('#bigLimit').val('');
	    	<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
		    	$('#sms').css("display","none");
		    	$('#isSendSms').val('');
		    </#if>
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.remark',true,'notEmpty').validateField('query.remark');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppMain.accLmt',false,'notEmpty');
    		$('#accLmt').val('').attr({"disabled":"disabled","label_only":"label_only"});
    		$('#refuseCodeDiv').css("display","none");
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
    		$('#refuseCode').multipleSelect("uncheckAll");
    	}else{
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.remark',false,'notEmpty');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppMain.accLmt',false,'notEmpty');
    		$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
    		$('#refuseCode').multipleSelect("uncheckAll");
	    	$('#accLmt').val('').attr({"disabled":"disabled","label_only":"label_only"});
	    	$('#bigLimit').val('');
	    	<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
		    	$('#isSendSms').val('');
				$('#sms').css("display","none");
			</#if>
    	}
    }
</script>