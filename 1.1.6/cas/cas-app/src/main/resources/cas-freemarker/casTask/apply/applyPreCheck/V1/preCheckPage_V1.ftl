<#include "/layout.ftl"/>
<@body>
<#--共用js-->
	<#include "../../common/pageCommonJs.ftl"/>
<#--领卡方式的改变js-->
	<#include "../../common/cardFetchJs.ftl"/>

<@form id="preCheckForm">
	<@panel head="预审调查[${(appNo)!}]${(oldAppNo?? && oldAppNo!='')?string('[重审件]','')}${(returnFlag?? && returnFlag!='')?string('[${(returnFlag)!}]','')}"
	class="col-ar-24" style="height:670px;overflow-y:scroll;width:70% ;">
<@panelBody>
				<@field hidden="true">
					<@input name="tmAppMain.appNo" value="${(appNo)!}"/>
					<@input name="query.appNo" value="${(appNo)!}"/>
					<@input name="query.taskId" value="${(taskId)!}"/>
					<@input id="formKey" value="${(formKey)!}"/>
				</@field>
				<#if pageFieldMap??>
					<@tab id="tabs" style="height:530px;overflow-y:scroll;">
						<@tabNav>
							<#if appType??>
								<#if appType == "A">
									<@tabTitle pane_id="pane1" active="true" title="主附同申申请信息"/>
									<@tabTitle pane_id="pane2" title="附件证明信息"/>
								<#elseif appType == "B">
									<@tabTitle pane_id="pane1" title="独立主卡申请信息"/>
									<@tabTitle pane_id="pane2" title="附件证明信息" />
								<#elseif appType == "S">
									<@tabTitle pane_id="pane1" title="独立附卡申请信息"/>
								</#if>
								<@tabTitle pane_id="pane3" title="其他信息" />

							</#if>
						</@tabNav>
						<@tabContent>
							<#if appType??>
								<#if appType == "A" || appType == "B">
									<@tabPane id="pane1" active="true">
										<#include "../../applyView/V1/mainCardInfoView_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane2">
										<#include "../../applyView/V1/annexEviInfoView_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane3">
										<#include "../../applyView/V1/mainCardOtherInfoView_V1.ftl"/>
									</@tabPane>
								<#elseif appType == "S">
									<@tabPane id="pane1" active="true">
										<#include "../../applyView/V1/attachCardInfoView_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane3">
										<#include "../../applyView/V1/attachCardOtherInfoView_V1.ftl"/>
									</@tabPane>
								</#if>
							</#if>
							<#if pageOnOffParamDto?? && pageOnOffParamDto.isUsedRisk?? && pageOnOffParamDto.isUsedRisk == 'Y'>
								<@tabPane id="pane6">
									<#include "../../applyView/V1/cheatInfoView_V1.ftl"/>
								</@tabPane>
							</#if>
						</@tabContent>
					</@tab>
				</#if>
				</@panelBody>
	</@panel>
		   	<@panel head="预审操作信息" style="height:670px;overflow-y:scroll;width:30%;">
	   			<@panelBody>
					<@row>
						<@field label="签名状况" field_ar="35" label_ar="10" input_ar="20">
							<@dictSelect id="${(fieldPageDto.id)!}" dicttype="AnnexPreCheckType"
							name="query.indSignFile" value="${(tmAppPrimAnnexEvi.indSignFile)!}" nullable="true" valid={"notempty":"true"}/>
						</@field>
						</@row>
					<@row>
						<@field label="身份证明文件状况" field_ar="35" label_ar="10" input_ar="20">
							<@dictSelect id="${(fieldPageDto.id)!}" dicttype="AnnexPreCheckType"
							name="query.indIdFile" value="${(tmAppPrimAnnexEvi.indIdFile)!}" nullable="true" valid={"notempty":"true"}/>
						</@field>
						</@row>
					<@row>
						<@field label="工作证明状况" field_ar="35" label_ar="10" input_ar="20">
							<@dictSelect id="${(fieldPageDto.id)!}" dicttype="AnnexPreCheckType"
							name="query.indJobFile" value="${(tmAppPrimAnnexEvi.indJobFile)!}" nullable="true" valid={"notempty":"true"}/>
						</@field>
					</@row>
					<@row>
						<@field  label="三亲核实"  field_ar="35" label_ar="10" input_ar="20">
							<@multipleSelect id="SpreaderMode" name="SpreaderMode" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
							'{"type":"SpreaderMode"}','code','codeName') showfilter="false" value="${(tmAppPrimCardInfo.spreaderMode)!}"  valid={"notempty":"true"}/>
						</@field>
					</@row>
					<@row>
						<@field label="推广人网点" field_ar="35" label_ar="10" input_ar="20" >
							<@multipleSelect id="branch" name="branch"  value="${(tmAppPrimCardInfo.spreaderBranchThree)!}" options=cas_('tableMap','branchMap','allBranch')  showfilter="true" single="true" valid={"notempty":"true"} />
						</@field>
					<#--					<@field  label="推广人网点"  field_ar="9" label_ar="12" input_ar="24">
                                        <@ajaxSelect id="${(fieldPageDto.id)!}" options=fieldPageDto.options click="${(fieldPageDto.textName)!}" change="${(fieldPageDto.change)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"  showcode="${(fieldPageDto.showCode)!}" nullable="${(fieldPageDto.nullable)!}"/>
                                        </@field>-->
						<#--<@field  label="三亲核实"  field_ar="9" label_ar="12" input_ar="24">
								<@ajaxSelect id="branch" name="branch" value="" options="" onclick="clickAjaxSelect('allBranch','true','C',this)"/>
						</@field>-->

					</@row>
                    <@row>
                        <@field  label="申请件标签" field_ar="35" label_ar="10" input_ar="25" >
                            <@multipleSelect id="FlagApp" name="flagApp" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
                            '{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}" showfilter="true"/>
                        </@field>
                    </@row>
					<@row>
						<@field label="预审备注" field_ar="35" label_ar="10" input_ar="25" >
							<@textarea id="remark" value="${(REMARKCAS_APPLY_PRE_CHECK.memoInfo)!}" name="query.remark" rows="3"/>
						</@field>
					</@row>
				<@toolbar align="center" style="padding-top: 0px;">
				<#--	<@ajaxButton id="saveBtn" name=" 删除 " style="margin-right: 15px;"
                        url="cas_applyPreCheck/delete"  success_url="cas_tasklist/page"/>
                 -->
					<@ajaxButton id = "subBtn" name="删除" url="cas_applyPreCheck/delete"
					form_id="preCheckForm"   before="confirmdelete" style="margin-right: 15px;" success_url="cas_tasklist/page"/>
					<@ajaxButton id = "subBtn" name=" 提交 " url="cas_applyPreCheck/preCheckSubmit"
					form_id="preCheckForm"  before="beforeSubmit" after="afterSubmit" style="margin-right: 15px;" success_url="cas_tasklist/page"/>
					<@backButton fa="undo" style="margin-left:5px;"/>
				</@toolbar>
		</@panelBody>
</@panel>
</@form>

<script type="text/javascript">
    var c=window.parent.goToLoading();
	<#--保存预审审调查数据返回历史页面-->
		<#--<#if pageOnOffParamDto?? && pageOnOffParamDto.isSaveSkip?? && pageOnOffParamDto.isSaveSkip == 'Y'>
        var afterSave= function(){
            alert('操作成功！');
            history.go(-1);
        }
		</#if>-->
<#--必填-->
/*
    $(function() {
        $('#preCheckForm').data('bootstrapValidator').enableFieldValidators('branch', true, 'notEmpty');
        $('#preCheckForm').data('bootstrapValidator').enableFieldValidators('query.indSignFile', true, 'notEmpty');
        $('#preCheckForm').data('bootstrapValidator').enableFieldValidators('query.indIdFile', true, 'notEmpty');
        $('#preCheckForm').data('bootstrapValidator').enableFieldValidators('query.indJobFile', true, 'notEmpty');
        $('#preCheckForm').data('bootstrapValidator').enableFieldValidators('SpreaderMode', true, 'notEmpty');

    });
*/

	<#--提交预审调查的必输字段的验证-->
    var beforeSubmit = function(){
            if(!$("#preCheckForm").unicornValidForm()){
                alert('请将必输项填写完整！');
                return false;
            }
        c.showModal();
        }

    var afterSubmit = function(){
        c.remove();
        alert('操作成功！');
        history.go(-1);
	}

    var confirmdelete = function(){
            if(!confirm("确认删除？")){
                return false;
            }
        }


</script>
</@body>