
<@row>
	<@field hidden="true">
		<@input name="taskId" value="${(taskId)!}" />
		<@input name="tmAppMain.appType" value="${(appType)!}"/>
		<@input name="tmAppMain.appNo" value="${(appNo)!}" />
		<@input name="tmAppMain.productCd" value="${(applyOperateDto.productCd)!}" />
		<@input name="attachNum" value="${(attachNum)!}" />
		<@input id="formKey" value="${(formKey)!}"/>
	</@field>
</@row>
<script type="text/javascript">
	<#--姓名拼音格式转换-->
	function embLogoKeyup(event){
		var embLogo = $(this).val();
		embLogo = embLogo.toUpperCase();
		$(this).val(embLogo);
	}
</script>
<#if reviewFieldMap??>
	<#list reviewFieldMap?keys as tabType>
		<#if tabType == "commonTab">
			<@fieldset legend="卡复核信息:">
				<#assign commonInfoMap = reviewFieldMap[tabType]>
				<#list commonInfoMap?keys as key>
					<#assign fieldPageDto = commonInfoMap[key]>
					<@field label="${(fieldPageDto.fieldName)!}" point_flag="true" point="*">
						<@input name="${(fieldPageDto.name)!}" value="${(commonTab[fieldPageDto.name])!}"/>
					</@field>
				</#list>
			</@fieldset>
		</#if>
		<#if tabType == "mainTab">
			<@fieldset legend="主卡信息:">
				<#assign mainInfoMap = reviewFieldMap[tabType]>
				<#list mainInfoMap?keys as key>
					<#assign fieldPageDto = mainInfoMap[key]>
					<@field label="${(fieldPageDto.fieldName)!}" point_flag="true" point="*">
						<#if fieldPageDto.name == 'tmAppPrimApplicantInfo.embLogo'>
							<@input id="main${(fieldPageDto.id)!}" name="${(fieldPageDto.name)!}" value="${(mainTab[fieldPageDto.name])!}"
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
							
							<script type="text/javascript">
								$(function(){
									$('#main${(fieldPageDto.id)!}').bind('keyup',{},embLogoKeyup);
								});	
							</script>
						<#elseif fieldPageDto.name == 'tmAppPrimApplicantInfo.cellphone' || key == 'tmAppContact[1].contactMobile'
							|| fieldPageDto.name == 'tmAppContact[2].contactMobile'>
							<@input name="${(fieldPageDto.name)!}" value="${(mainTab[fieldPageDto.name])!}" 
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
						<#elseif fieldPageDto.name == 'tmAppPrimApplicantInfo.homePhone' || key == 'tmAppPrimApplicantInfo.empPhone'>
							<@input name="${(fieldPageDto.name)!}" value="${(mainTab[fieldPageDto.name])!}" 
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
						<#else>
							<@input name="${(fieldPageDto.name)!}" value="${(mainTab[fieldPageDto.name])!}"/>
						</#if>
					</@field>
				</#list>
			</@fieldset>
		</#if>
		<#if tabType == "attachTab_0">
			<@fieldset legend="${(appType?? && appType=='S')?string('附卡信息:','附卡1信息:')}">
				<#assign attachMap_0 = reviewFieldMap[tabType]>
				<#list attachMap_0?keys as key>
					<#assign fieldPageDto = attachMap_0[key]>
					<@field label="${(fieldPageDto.fieldName)!}" point_flag="true" point="*">
						<#if fieldPageDto.name == 'tmAppAttachApplierInfo[0].embLogo'>
							<@input id="attach${(fieldPageDto.id)!}" name="${(fieldPageDto.name)!}" value="${(attachTab_0[fieldPageDto.name])!}"
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
							
							<script type="text/javascript">
								$(function(){
									$('#attach${(fieldPageDto.id)!}').bind('keyup',{},embLogoKeyup);
								});
							</script>
						<#elseif fieldPageDto.name == 'tmAppAttachApplierInfo[0].cellphone'>
							<@input name="${(fieldPageDto.name)!}" value="${(attachTab_0[fieldPageDto.name])!}" 
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
						<#elseif fieldPageDto.name == 'tmAppAttachApplierInfo[0].homePhone'>
							<@input name="${(fieldPageDto.name)!}" value="${(attachTab_0[fieldPageDto.name])!}" 
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
						<#else>
							<@input name="${(fieldPageDto.name)!}" value="${(attachTab_0[fieldPageDto.name])!}"/>
						</#if>
					</@field>
				</#list>
			</@fieldset>
		</#if>
		<#if tabType == "attachTab_1">
			<@fieldset legend="附卡2信息:">
				<#assign attachMap_1 = reviewFieldMap[tabType]>
				<#list attachMap_1?keys as key>
					<#assign fieldPageDto = attachMap_1[key]>
					<@field label="${(fieldPageDto.fieldName)!}" point_flag="true" point="*">
						<#if fieldPageDto.name == 'tmAppAttachApplierInfo[1].embLogo'>
							<@input id="attach${(fieldPageDto.id)!}" name="${(fieldPageDto.name)!}" value="${(attachTab_1[fieldPageDto.name])!}"
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
							
							<script type="text/javascript">
								$(function(){
									$('#attach${(fieldPageDto.id)!}').bind('keyup',{},embLogoKeyup);
								});
							</script>
						<#elseif fieldPageDto.name == 'tmAppAttachApplierInfo[1].cellphone'>
							<@input name="${(fieldPageDto.name)!}" value="${(attachTab_1[fieldPageDto.name])!}" 
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
						<#elseif fieldPageDto.name == 'tmAppAttachApplierInfo[1].homePhone'>
							<@input name="${(fieldPageDto.name)!}" value="${(attachTab_1[fieldPageDto.name])!}" 
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
						<#else>
							<@input name="${(fieldPageDto.name)!}" value="${(attachTab_1[fieldPageDto.name])!}"/>
						</#if>
					</@field>
				</#list>
			</@fieldset>
		</#if>
		<#if tabType == "attachTab_2">
			<@fieldset legend="附卡3信息:">
				<#assign attachMap_2 = reviewFieldMap[tabType]>
				<#list attachMap_2?keys as key>
					<#assign fieldPageDto = attachMap_2[key]>
					<@field label="${(fieldPageDto.fieldName)!}" point_flag="true" point="*">
						<#if fieldPageDto.name == 'tmAppAttachApplierInfo[2].embLogo'>
							<@input id="attach${(fieldPageDto.id)!}" name="${(fieldPageDto.name)!}" value="${(attachTab_2[fieldPageDto.name])!}"
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
							
							<script type="text/javascript">
								$(function(){
									$('#attach${(fieldPageDto.id)!}').bind('keyup',{},embLogoKeyup);
								});
							</script>
						<#elseif fieldPageDto.name == 'tmAppAttachApplierInfo[2].cellphone'>
							<@input name="${(fieldPageDto.name)!}" value="${(attachTab_2[fieldPageDto.name])!}"
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
						<#elseif fieldPageDto.name == 'tmAppAttachApplierInfo[2].homePhone'>
							<@input name="${(fieldPageDto.name)!}" value="${(attachTab_2[fieldPageDto.name])!}"
								valid={"regexp or pattern":"${(fieldPageDto.regexp)!}","regexp-message":"格式不正确"}/>
						<#else>
							<@input name="${(fieldPageDto.name)!}" value="${(attachTab_2[fieldPageDto.name])!}"/>
						</#if>
					</@field>
				</#list>
			</@fieldset>
		</#if>
	</#list>
</#if>

<@fieldset legend="备注/备忘:" style="margin-top:1px;">
	<@field label="复核人工号" field_ar="18" label_ar="15" input_ar="21">
		<@input id="reviewNo" name="tmAppPrimCardInfo.reviewNo" value="${(applyOperateDto.reviewNo)!}" 
			valid={"stringlength":"true","stringlength-max":"20","stringlength-message":"请正确填写复核人工号"}/>
	</@field>
	<@field label="复核人姓名" field_ar="18" label_ar="15" input_ar="21">
		<@input id="reviewName" name="tmAppPrimCardInfo.reviewName" value="${(applyOperateDto.reviewName)!}" 
			valid={"stringlength":"true","stringlength-max":"20","stringlength-message":"请正确填写复核人姓名"}/>
	</@field>
	<@field field_ar="36" label_ar="7" input_ar="29" label="复核人备注">
		<@textarea name="tmAppMemoReview.memoInfo" cols="" rows="3" value="${(REMARKAMS_APPLY_REVIEW.memoInfo)!''}" />
	</@field>
	<@row>
		<@field  label="申请件标签"  field_ar="36" label_ar="7" input_ar="29">
			<@multipleSelect id="FlagApp" name="flagApp" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
			'{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}" showfilter="true"/>
		</@field>
	</@row>
</@fieldset>
