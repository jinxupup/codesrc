<@form id="patchBoltForm" >
	<@row>
		<@field hidden="true">
			<@input id="appNo" name="query.appNo" value="${(appNo)!}"/>
			<@input name="query.taskId" value="${(taskId)!}"/>
			<@input id="formKey" value="${(formKey)!}"/>
		</@field>
	</@row>
<@fieldset>
	<@legend style="color:#FF0000">补件确认</@legend>
	<@row>
		<@checkbox>
			<#--遍历补件类型-->
			<#list applyPatchBoltDtos as applyPatchBoltDto> 
				<#if applyPatchBoltDto.ifPathBolt><#--已经勾选-->  <#--modify by liuxin --2017/10/30-->
					<label><input type="checkbox" data-ar-value_split="," name="checkBox" checked="true"/>${(applyPatchBoltDto.applyPatchBoltTitle)} </label>
					<#else>
					<label><input type="checkbox" data-ar-value_split="," name="checkBox" />${(applyPatchBoltDto.applyPatchBoltTitle)} </label>
				</#if>
			</#list>
		</@checkbox>
	</@row>
</@fieldset>
<@fieldset>
	<@legend style="color:#FF0000">补件时间信息</@legend>
	<@row>
		<@field label="补件开始日期:" field_ar="18" label_ar="18" input_ar="18">
			<@input name="startDate" value="${(pbstartTime)!}" label_only="true"/>
		</@field>
		<@field label="补件到期日期:" field_ar="18" label_ar="18" input_ar="18" >
			<@input name="endDate" value="${(pbendtime)!}" label_only="true"/>
		</@field>
	</@row>
</@fieldset>
<@fieldset>
<@legend style="color:#FF0000">备注/备忘</@legend>
	<@field label="备注:" field_ar="36" label_ar="6" input_ar="30">
		<@textarea name="query.remark" value="${(REMARKAPPLYINFO_PATCHBOLT.memoInfo)!}" rows="1" />
	</@field>
	<@field label="备忘:" field_ar="36" label_ar="6" input_ar="30">
		<@textarea name="query.memo" value="${(MEMOAPPLYINFO_PATCHBOLT.memoInfo)!}" rows="1" />
	</@field>
<#if (REMARKCAS_APPLY_BASIC_CHECK.memoInfo)??>
	<@field label="初审备注:" field_ar="36" label_ar="6" input_ar="30">
		<@textarea value="${(REMARKCAS_APPLY_BASIC_CHECK.memoInfo)!}" rows="1" readonly="true"/>
	</@field>
</#if>
    <@field id="patchBoltRemarkDiv" label="补件备注:"  field_ar="36" label_ar="6" input_ar="30" >
        <@textarea value="${(PATCHBOLTAPPLYINFO_PATCHBOLT.memoInfo)!}" rows="1"/>
    </@field>
	<@row>
		<@field  label="申请件标签"  field_ar="36" label_ar="6" input_ar="20">
			<@multipleSelect id="FlagApp" name="flagApp" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
			'{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}" showfilter="true"/>
		</@field>
	</@row>
</@fieldset>
<@toolbar align="center">
	<@button name="保存" fa="save" click="saveFuc"/>
	<@button id="subButton" name="提交" fa="send" click="submitFuc"/>
	<@button name="补件超时确认"  fa="frown-o" click="overtimeFuc"/>
	<#--影像调阅-->
	<#include "../common/showPicBtn.ftl"/>
	<#-- 审计历史 -->
	<#include "../common/casShowAuditHistory.ftl"/>
	<#--审批历史信息-->
	<#include "../common/approveHistoryBtn.ftl"/>
	<#--修改历史信息-->
	<#include "../common/modifyHistoryBtn.ftl"/>
	<@buttonAuth code="CAS_DELETE_TASK">
		<#-- 状态为K10 、 L05 、N05、Q05、P05、A20时不支持删除 -->
		<#if applyOperateDto.rtfState != "K10" && applyOperateDto.rtfState != "L05" && applyOperateDto.rtfState != "N05" && applyOperateDto.rtfState != "Q05" && applyOperateDto.rtfState != "P05" && applyOperateDto.rtfState != "A20">
			<@ajaxButton fa="remove" confirm="确定删除？" sense="danger" name="删除" url="cas_applyQuery/applyQueryDelete?appNo=${(appNo)!}" success_url="cas_activiti/handleTask?appNo=${(appNo)!}&detailFlag=Y" style="margin-left:5px;margin-right:5px;" />
		</#if>
	</@buttonAuth>
	<@href name="返回"  fa="undo" href="cas_tasklist/page" />
</@toolbar> 	
</@form>

<script type="text/javascript">
	var appNo = '${(appNo)!}';

	<#--保存数据 -->
  	var saveFuc = function(){
		$.ajax({
			url:"${base}/cas_applyPatchBolt/patchBoltSave",
			type:"POST",
			data:$('#patchBoltForm').serialize(),
			dataType:"json",
			success:function(ref){
				alert(ref.msg);
			}
		});
	}
	
	<#--提交 -->
  	function submitFuc(){
  		<#--modify by liuxin --2017/10/30-->
  		var checkedNum=0;<#--补件种类复选框被选中的个数-->
		var checkNum=0;<#--补件种类复选框总个数-->
		$('input[name="checkBox"]').each(function(index,element){
			checkNum++;
		});
		$('input[name="checkBox"]:checked').each(function(index,element){
			checkedNum++;
		});
		if(checkedNum!=checkNum){
			if(!confirm("您当前“补件确认”信息未全部勾选，是否继续提交？")){
				return false;
			}
		}
	  	$(this).unicornButtonDisable(true);
		$.ajax({
			url:"${base}/cas_applyPatchBolt/patchBoltSubmit",
			type:"POST",
			data:$('#patchBoltForm').serialize(),
			dataType:"json",
			success:function(ref){
				alert(ref.msg);
				window.location.href="${base}/cas_tasklist/page";
			}
		});
	} 
	
	<#--超时确认-->
  	var overtimeFuc = function(){
		$.ajax({
			url:"${base}/cas_applyPatchBolt/patchBoltOverTime",
			type:"POST",
			data:$('#patchBoltForm').serialize(),
			dataType:"json",
			success:function(ref){
				alert(ref.msg);
				window.location.href="${base}/cas_tasklist/page";
			}
		});
	}
</script>

<#--弹出框共用js-->
<#include "../common/dialogJs.ftl"/>