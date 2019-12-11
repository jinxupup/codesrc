
<@row>
	<@field label="申请类型" point_flag="${(requiredMap['tmAppMain.appType'])!}" point="*">
		<@dictSelect dicttype="AppType"  nullable="false" name="tmAppMain.appType" value="${(tmAppMain.appType)!}" readonly="true" />
	</@field>
	<@field label="与主卡持卡人关系" point_flag="${(requiredMap['tmAppAttachApplierInfo.relationshipToBsc'])!}" point="*">
		<@dictSelect readonly="true" dicttype="ContactRelation"  name="tmAppAttachApplierInfo.relationshipToBsc" value="${(tmAppAttachApplierInfo.relationshipToBsc)!}"  />
	</@field>
	<@field label="主卡卡号" point_flag="${(requiredMap['tmAppAttachApplierInfo.primCardNo'])!}" point="*">
		<@input readonly="true" name="tmAppAttachApplierInfo.primCardNo" value="${(tmAppAttachApplierInfo.primCardNo)!}"  />
	</@field>
</@row>
<@row>
	<@field label="主卡人姓名" point_flag="${(requiredMap['tmAppPrimApplicantInfo.name'])!}" point="*">
		<@input value="${(tmAppPrimApplicantInfo.name)!}" readonly="true" />
	</@field>
	<@field label="主卡人证件类型" point_flag="${(requiredMap['tmAppPrimApplicantInfo.idType'])!}" point="*">
		<@dictSelect dicttype="IdType" value="${(tmAppPrimApplicantInfo.idType)!}" readonly="true" />
	</@field>
	<@field label="主卡人证件号码" point_flag="${(requiredMap['tmAppPrimApplicantInfo.idNo'])!}" point="*">
		<@input value="${(tmAppPrimApplicantInfo.idNo)!}" readonly="true" />
	</@field>
</@row>
<@row>
	<@field label="主卡卡产品代码" point_flag="${(requiredMap['tmAppMain.productCd'])!}" point="*">
		<@select id="productCd" readonly="true" name="tmAppMain.productCd" value="${(tmAppMain.productCd)!}" options=ams_('tableMap','productForStatus','A') />
	</@field>
	<@field label="主卡卡面" >
		<@selectLink options=ams_("tableMap","productCardFace",'${(tmAppMain.productCd)!}')
			link_id="productCd" url="ams_/getCardFaceByProductCd?productCd" keyfield="key" valuefield="value"
			showcode='true' nullable="false" readonly="true" name="tmAppCardfaceInfo.pyhCd" value="${(tmAppCardFaceInfo[0].pyhCd)!''}" />
	</@field>
	<@field label="主卡申请日期" point_flag="${(requiredMap['tmAppPrimApplicantInfo.createDate'])!}" point="*">
		<@date datetype="datetime" readonly="true" value="${(tmAppPrimApplicantInfo.createDate?datetime)!}"/>
	</@field>
</@row>