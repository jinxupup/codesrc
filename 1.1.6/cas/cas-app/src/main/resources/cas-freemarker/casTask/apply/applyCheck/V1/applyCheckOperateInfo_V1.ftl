<#include "../../common/applyCreditInfoBut_V1.ftl"/>
<@fieldset legend="历史审批信息">
	<@row>
		<@field label="电调审批意见:" field_ar="36" label_ar="8" input_ar="28">
			<@dictSelect dicttype="TelCheckResult" value="${(applyNodeTelCheckBisicData.result)!}" label_only="true"/>
		</@field>
	</@row>
	<@row>
		<@field label="电调拒绝原因:" field_ar="36" label_ar="8" input_ar="28">
			<@multipleSelect options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
			'{"type":"ApplyRejectReason"}','code','codeName') value="${(applyNodeTelCheckBisicData.refuseCode)!}" label_only="true"/>
		</@field>
	</@row>
    <#if (REMARKAMS_APPLY_REVIEW.memoInfo)??>
        <@row>
            <@field label="复核备注:" field_ar="36" label_ar="8" input_ar="28">
                <@textarea  value="${(REMARKAMS_APPLY_REVIEW.memoInfo)!}"  rows="1" label_only="true"/>
            </@field>
        </@row>
    </#if>
	<#if (REMARKCAS_APPLY_PRE_CHECK.memoInfo)??>
		<@row>
			<@field label="预审备注:" field_ar="36" label_ar="8" input_ar="28">
				<@textarea  value="${(REMARKCAS_APPLY_PRE_CHECK.memoInfo)!}"  rows="1" label_only="true"/>
			</@field>
		</@row>
	</#if>
	<#if (REMARKCAS_APPLY_TEL_SURVEY.memoInfo)??>
		<@row>
			<@field label="电调备注:" field_ar="36" label_ar="8" input_ar="28">
				<@textarea value="${(REMARKCAS_APPLY_TEL_SURVEY.memoInfo)!}" rows="1" label_only="true"/>
			</@field>
		</@row>
	</#if>
	<#if (REMARKCAS_APPLY_FINALAUDIT.memoInfo)??>
		<@row>
			<@field label="终审备注:" field_ar="36" label_ar="8" input_ar="28">
				<@textarea value="${(REMARKCAS_APPLY_FINALAUDIT.memoInfo)!}" rows="1" label_only="true"/>
			</@field>
		</@row>
	</#if>
</@fieldset>
<@fieldset legend="初审操作信息">
	<@row>
		<@field label="决策建议:" field_ar="18" label_ar="16" input_ar="20">
			<@input value="${(applyRiskInfoDto.content)!}" label_only="true"/>
		</@field>
		<@field label="建议额度:" field_ar="18" label_ar="16" input_ar="20">
			<@input value="${(applyRiskInfoDto.suggestAmt)!}" label_only="true"/>
		</@field>
	</@row>
	<@row>
		<@field point="审批意见" field_ar="36" label_ar="8" input_ar="28" 	point_flag="true">
        <div class="col-ar-20" style="padding-left:0px;">
			<@dictSelect id="basicCheckResult" dicttype="BasicCheckResult" change="basicCheckResultChange"
			name="query.basicCheckResult" value="${(applyNodeInquiryData.checkResult)!'P'}" nullable="false"/>
        </div>

<#--        <div id="isFreeTelCheckDiv" class="col-ar-16">
<#--        <div id="isFreeTelCheckDiv" class="col-ar-16">
            <label style="padding-left:5px;font-family:'宋体';"><input id="isFreeTelCheck" type="checkbox" name="query.isFreeTelCheck" value="Y"/>免电调</label>
        </div>-->

		<#--<div id="isFreeTelCheckDiv" class="col-ar-16"}">
                        <label style="padding-left:5px;font-family:'宋体';"><input id="isFreeTelCheck" type="checkbox" name="tmAppAudit.isFreeTelCheck" value="Y"
                         ${(tmAppAudit.isFreeTelCheck?? && tmAppAudit.isFreeTelCheck == 'Y')?string('checked','')}  &lt;#&ndash;onclick="FTConclick"&ndash;&gt;/>免电调</label>
             </div>-->
		</@field>
	</@row>
	<@row>
		<#if appType?? && appType != "S">
			<@field label="初审额度" field_ar="36" label_ar="8" input_ar="28">
				<@input id="chkLmt" name="query.chkLmt" value="${(applyOperateDto.chkLmt)!}" valid={"regexp or pattern":"^((([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入正确的初审额度"}/>
			</@field>
		</#if>
	</@row>
	<@row>
		<#if appType?? && appType == "A">
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
	</@row>
	<@row>
		<@field id="refuseCodeDiv" label="拒绝原因"  field_ar="36" label_ar="8" input_ar="28"
		hidden="${(applyNodeInquiryData?? && applyNodeInquiryData.checkResult?? && applyNodeInquiryData.checkResult=='R')?string('false','true')}">
			<@multipleSelect id="refuseCode" name="refuseCode" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
			'{"type":"ApplyRejectReason"}','code','codeName') value="${(checkRefuseCodes)!}" showfilter="true"/>
		</@field>
	</@row>
	<@row>
		<@field id="supplement" label="补件类型"  field_ar="36" label_ar="8" input_ar="28"
		hidden="${(applyNodeInquiryData?? && applyNodeInquiryData.checkResult?? && applyNodeInquiryData.checkResult=='O')?string('false','true')}">
			<@multipleSelect id="applyPatchBoltType" name="applyPatchBoltType" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
			'{"type":"ApplyPatchBoltType"}','code','codeName') value="${(patchBoltString)!''}" showfilter="true" on_option_click="supplementChange"/>
		</@field>
	</@row>
	<@row>
		<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
			<@field id="sms" label="是否发送短信" field_ar="36" label_ar="8" input_ar="28"
			hidden="${(applyNodeInquiryData?? && applyNodeInquiryData.checkResult??	&& (applyNodeInquiryData.checkResult=='O'||applyNodeInquiryData.checkResult=='R'))?string('false','true')}">
				<#if applyNodeInquiryData?? && applyNodeInquiryData.checkResult??>
					<#if applyNodeInquiryData.checkResult=='R'><#--拒绝-->
						<@dictSelect id="isSendSms" dicttype="Indicator" name="query.isSendSms"	value="${(applyOperateDto.isSendSmsRefused)!}"/>
					<#elseif applyNodeInquiryData.checkResult=='O'><#--补件-->
						<@dictSelect id="isSendSms" dicttype="Indicator" name="query.isSendSms" value="${(applyOperateDto.isSendSmsPatch)!}"/>
					<#else><#--退回-->
						<@dictSelect id="isSendSms" dicttype="Indicator" name="query.isSendSms" value=""/>
					</#if>
				<#else><#--通过-->
					<@dictSelect id="isSendSms" dicttype="Indicator" name="query.isSendSms" value=""/>
				</#if>
			</@field>
		</#if>
	</@row>
	<@row>
		<@field label="初审备注" field_ar="36" label_ar="8" input_ar="28">
			<@textarea name="query.basicRemark" value="${(REMARKCAS_APPLY_BASIC_CHECK.memoInfo)!}" rows="3"/>
		</@field>
	</@row>
	<@row>
		<#if applyNodeInquiryData?? && applyNodeInquiryData.result?? && applyNodeInquiryData.result=='O'>
			<@field id="patchBoltRemarkDiv" label="补件备注:"  field_ar="36" label_ar="8" input_ar="28" >
				<@textarea value="${(PATCHBOLTAPPLYINFO_PATCHBOLT.memoInfo)!}" label_only="true"/>
			</@field>
		</#if>
	</@row>
    <@row>
        <@field  label="申请件标签"  field_ar="36" label_ar="8" input_ar="28">
            <@multipleSelect id="FlagApp" name="flagApp" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
            '{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}" showfilter="true"/>
        </@field>
    </@row>


</@fieldset>
<script type="text/javascript">
	<#--初始化-->
    $(function(){
	<#--审批结果初始化-->
        var basicCheckResult = '${(applyNodeInquiryData.checkResult)!"P"}';
        if(basicCheckResult=='R'){
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('applyPatchBoltType',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.basicRemark',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.patchBoltRemark',false,'notEmpty');
            $('#patchBoltRemarkDiv').hide();
            $('#patchBoltRemark').text('');
        }else if(basicCheckResult=='O'){
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
            var applyPatchBoltType = '${(patchBoltString)!}';
            if(applyPatchBoltType.indexOf('P') > 0 || applyPatchBoltType == 'P'){
                $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.patchBoltRemark',true,'notEmpty').validateField('tmAppMain.accLmt');
            }else{
                $('#patchBoltRemarkDiv').hide();
                $('#patchBoltRemark').text('');
                $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.patchBoltRemark',false,'notEmpty');
            }
        }else if(basicCheckResult.indexOf("B") != -1){
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.basicRemark',true,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('applyPatchBoltType',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.patchBoltRemark',false,'notEmpty');
            $('#patchBoltRemarkDiv').hide();
            $('#patchBoltRemark').text('');
            $('#applyInputForm').data('bootstrapValidator').validateField('query.basicRemark');
        }else{//通过
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('applyPatchBoltType',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.basicRemark',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.patchBoltRemark',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.chkLmt',true,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').validateField('query.chkLmt');
            $('#patchBoltRemarkDiv').hide();
            $('#patchBoltRemark').text('');
        }
    });



	<#--审批结果改变-->
    var basicCheckResultChange = function(that){
        var basicCheckResult = $(that).val();
       // $('#isFreeTelCheck').prop('checked',false);//免电话调查
        if(basicCheckResult=='R'){
            $(that).parent().prev().children().css("color","color:#337ab7");
           // $('#isFreeTelCheckDiv').css("display","none");//免电话调查
            $('#refuseCode').multipleSelect("uncheckAll");
            $('#refuseCodeDiv').css("display","block");
		<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
            $('#isSendSms').val('');
            $('#sms').css("display","block");
		</#if>
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('applyPatchBoltType',false,'notEmpty');
            $('#supplement').css("display","none");
            $('#applyPatchBoltType').multipleSelect('setSelects',['']);
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',true,'notEmpty').validateField('refuseCode');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.basicRemark',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.chkLmt',false,'notEmpty');
            $('#riskClassicDiv').css("display","none");//风险等级
            $('#riskClassic').val('');
        }else if(basicCheckResult=='O'){
            $(that).parent().prev().children().css("color","red");
           // $('#isFreeTelCheckDiv').css("display","none");//免电话调查
            $('#refuseCodeDiv').css("display","none");
            $('#refuseCode').multipleSelect("uncheckAll")
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
            $('#applyPatchBoltType').multipleSelect('setSelects',['']);
            $('#supplement').css("display","block");
		<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
            $('#isSendSms').val('');
            $('#sms').css("display","block");
		</#if>
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('applyPatchBoltType',true,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').validateField('applyPatchBoltType');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.basicRemark',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.chkLmt',false,'notEmpty');
            $('#riskClassicDiv').css("display","none");//风险等级
            $('#riskClassic').val('');
        }else if(basicCheckResult.indexOf("B") != -1){//退回
            $(that).parent().prev().children().css("color","color:#337ab7");
           // $('#isFreeTelCheckDiv').css("display","none");//免电话调查
            $('#refuseCodeDiv').css("display","none");
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
            $('#refuseCode').multipleSelect("uncheckAll");
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('applyPatchBoltType',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.basicRemark',true,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').validateField('query.basicRemark');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.chkLmt',false,'notEmpty');
        <#if sendRefuseMessage?? && sendRefuseMessage=="Y">
            $('#sms').css("display","none");
            $('#isSendSms').val('');
		</#if>
            $('#supplement').css("display","none");
            $('#applyPatchBoltType').multipleSelect('setSelects',['']);
            $('#riskClassicDiv').css("display","none");//风险等级
            $('#riskClassic').val('');
        }else if (basicCheckResult =='M'){
            $('#riskClassic').css("display","block");//风险等级
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.chkLmt',false,'notEmpty');

        }else{
            $(that).parent().prev().children().css("color","color:#337ab7");
          //  $('#isFreeTelCheckDiv').css("display","block");//免电话调查
            $('#refuseCodeDiv').css("display","none");
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('refuseCode',false,'notEmpty');
            $('#refuseCode').multipleSelect("uncheckAll");
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('applyPatchBoltType',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.basicRemark',false,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.chkLmt',true,'notEmpty');
            $('#applyInputForm').data('bootstrapValidator').validateField('query.chkLmt');
            $('#supplement').css("display","none");
            $('#applyPatchBoltType').multipleSelect('setSelects',['']);
		<#if sendRefuseMessage?? && sendRefuseMessage=="Y">
            $('#sms').css("display","none");
            $('#isSendSms').val('');
		</#if>
            $('#riskClassicDiv').css("display","none");//风险等级
            $('#riskClassic').val('');
        }
    }


	<#--补件为其他，补件备注不能为空-->
    var supplementChange = function(){
        var applyPatchBoltType = $('#applyPatchBoltType').multipleSelect('getSelects');
        if(applyPatchBoltType.indexOf('P') > 0 || applyPatchBoltType == 'P'){
            $('#patchBoltRemark').text('')
            $('#patchBoltRemarkDiv').show();
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.patchBoltRemark',true,'notEmpty').validateField('query.patchBoltRemark');
        }else{
            $('#patchBoltRemarkDiv').hide();
            $('#patchBoltRemark').text('')
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.patchBoltRemark',false,'notEmpty');
        }
    }


    <#-- 免电调时,初审备注必填 -->

/*    function  FTConclick (onclick) {
        var isFreeTelCheck= '{(tmAppAudit.isFreeTelCheck)}';
        if (isFreeTelCheck=='Y'){
            $('#applyInputForm').data('bootstrapValidator').enableFieldValidators('query.basicRemark',true,'notEmpty');
        }
    };*/


</script>


