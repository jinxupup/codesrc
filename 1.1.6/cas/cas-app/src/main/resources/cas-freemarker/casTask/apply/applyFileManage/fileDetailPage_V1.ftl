<#include "/layout.ftl"/>
<@body>
<#--共用js-->
	<#include "../common/pageCommonJs.ftl"/>
<#--领卡方式的改变js-->
	<#include "../common/cardFetchJs.ftl"/>
<#-- 弹框共用js -->
	<#include "../common/dialogJs.ftl"/>
	<@form id="fileManageForm">
		<@panel head="进件归档【${(appNo)!}】${(oldAppNo?? && oldAppNo!='')?string('【重审件】','')}${(returnFlag?? && returnFlag!='')?string('【${(returnFlag)!}】','')}"
		class="col-ar-24" style="height:670px;overflow-y:scroll;width:70% ;">
			<@panelBody>
				<@field hidden="true">
					<@input name="tmAppMain.appNo" value="${(appNo)!}"/>
					<@input name="query.appNo" value="${(appNo)!}"/>
					<@input name="query.taskId" value="${(taskId)!}"/>
					<@input id="formKey" value="${(formKey)!}"/>
				</@field>
				<#if pageFieldMap??>
					<@tab id="tabs" >
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
										<#include "../applyView/V1/mainCardInfoView_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane2">
										<#include "../applyView/V1/annexEviInfoView_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane3">
										<#include "../applyView/V1/mainCardOtherInfoView_V1.ftl"/>
									</@tabPane>
								<#elseif appType == "S">
									<@tabPane id="pane1" active="true">
										<#include "../applyView/V1/attachCardInfoView_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane3">
										<#include "../applyView/V1/attachCardOtherInfoView_V1.ftl"/>
									</@tabPane>
								</#if>
							</#if>
							<#if pageOnOffParamDto?? && pageOnOffParamDto.isUsedRisk?? && pageOnOffParamDto.isUsedRisk == 'Y'>
								<@tabPane id="pane6">
									<#include "../applyView/V1/cheatInfoView_V1.ftl"/>
								</@tabPane>
							</#if>
						</@tabContent>
					</@tab>
				<#--<@form id="checkRsForm" cols="4">-->
					<@fieldset legend="审批结果信息">
						<@row>
							<#if (REMARKCAS_APPLY_PRE_CHECK.memoInfo)??>
								<@field label="预审备注:" field_ar="36" label_ar="4" input_ar="32">
									<@textarea  value="${(REMARKCAS_APPLY_PRE_CHECK.memoInfo)!}"  rows="1" label_only="true" style="margin-bottom:5px"/>
								</@field>
							</#if>
						</@row>
						<@row>
							<#if (REMARKSYSAUTO.memoInfo)??>
								<@field label="系统备注:" field_ar="32" label_ar="4" input_ar="32">
									<@textarea value="${(REMARKSYSAUTO.memoInfo)!}" rows="1" label_only="true" style="margin-bottom:5px"/>
								</@field>
							</#if>
						</@row>
					</@fieldset>
				<#--</@form>-->
				</#if>
			</@panelBody>
		</@panel>
		<@panelBody>
			<@fieldset legend=" ">
				<#include "../common/showPicBtn.ftl"/>
			</@fieldset>
			<@fieldset legend="归档操作信息">
				<@row>
					<@field point="归档结果" field_ar="36" label_ar="8" input_ar="28"    point_flag="true">
						<div class="col-ar-20" style="padding-left:0px;">
							<@dictSelect id="fileManageResult" dicttype="FileManageResult" change="basicCheckResultChange"
							name="query.fileManageResult" value="${(applyNodeInquiryData.checkResult)!'P'}" nullable="false"/>
						</div>
					</@field>
				</@row>
				<@row>
					<@field label="归档备注" field_ar="35" label_ar="10" input_ar="25" >
						<@textarea id="remark" value="${(REMARKCAS_APPLY_FILE_MANAGE.memoInfo)!}" name="query.remark" rows="3"/>
					</@field>
				</@row>
				<@row>
					<@field  label="申请件标签" field_ar="35" label_ar="10" input_ar="25">
						<@multipleSelect id="FlagApp" name="flagApp" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
						'{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}" showfilter="true" />
					</@field>
				</@row>
			</@fieldset>
			<@toolbar align="center" style="padding-top: 0px;">
			<#--<@ajaxButton id = "subBtn" name="取消" url="cas_applyPreCheck/delete"
            form_id="preCheckForm"   before="confirmdelete" style="margin-right: 15px;" success_url="cas_tasklist/page"/>-->
				<#--<@ajaxButton id = "subBtn" name=" 提交 " url="cas_applyFileManage/fileManageSubmit"
				form_id="fileManageForm"    style="margin-right: 15px;" success_url="fileManage/fileManagePage"/>-->
				<@button id = "subButton"  click="subBtn()"  >提交</@button>
				<@backButton fa="undo" style="margin-left:5px;"/>
			</@toolbar>
		</@panelBody>
	</@form>

	<script type="text/javascript">
		//如果申请件信息存在 进入页面则直接弹出申请件信息的窗口
		window.onload=function(){
			<#if hisApplyInfoList?? && hisApplyInfoList?size gt 0>
			var url = '${base}/cas_commonDialog/applyHistoryLayout?appNo=${(appNo)!}';
			dialogInfo('[${(appNo)!}]历史申请信息',1000,500,url);
			<#else>
			ar_.buttonDisable('historyBtn',true);
			</#if>
		}

        var subBtn = function(){
            $.ajax({
                type: "POST",
                dataType : "json",
                data:$('#fileManageForm').serialize(),
                url: "${base}/cas_applyFileManage/beforeFileManageSubmit",
                success: function(res){
                    if(res.s){
						submit();
                    }else {
                        var show = confirm("多卡同申主件尚未处理完成，是否强制提交?");
                        if (show) {
                            submit();
                        }else {
                            return false;
                        }
                    }
                },
            });
        };

        var submit = function(){
            $.ajax({
                type: "POST",
                dataType : "json",
                data:$('#fileManageForm').serialize(),
                url: "${base}/cas_applyFileManage/fileManageSubmit",
                success: function(res){
                    alert(res.msg);
                    if(res.s){
                        window.open("${base}/fileManage/fileManagePage");
                    }
                },
            });
        };
	</script>

</@body>