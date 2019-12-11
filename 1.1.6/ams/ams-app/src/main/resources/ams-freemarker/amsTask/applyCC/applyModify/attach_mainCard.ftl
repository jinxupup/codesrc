
<@row>
	<@field label="申请类型">
		<@dictSelect dicttype="AppType"  nullable="false" name="tmAppMain.appType" value="${(tmAppMain.appType)!}" readonly="true" />
	</@field>
	<@field label="与主卡持卡人关系">
		<@dictSelect readonly="true" dicttype="ContactRelation"  name="tmAppAttachApplierInfo.relationshipToBsc" value="${(tmAppAttachApplierInfo.relationshipToBsc)!}"  />
	</@field>
	<@field label="主卡卡号">
		<@input readonly="true" name="tmAppAttachApplierInfo.primCardNo" value="${(tmAppAttachApplierInfo.primCardNo)!}"  />
	</@field>
</@row>
<@row>
	<@field label="主卡人姓名">
		<@input name="tmAppPrimApplicantInfo.name" value="${(tmAppPrimApplicantInfo.name)!}" readonly="true" />
	</@field>
	<@field label="主卡人证件类型">
		<@dictSelect dicttype="IdType" name="tmAppPrimApplicantInfo.idType"  nullable="false" value="${(tmAppPrimApplicantInfo.idType)!}" readonly="true" />
	</@field>
	<@field label="主卡人证件号码">
		<@input name="tmAppPrimApplicantInfo.idNo" value="${(tmAppPrimApplicantInfo.idNo)!}" readonly="true" />
	</@field>
</@row>
<@row>
	<@field label="主卡卡产品代码">
		<@select id="productCd" readonly="true" name="tmAppMain.productCd" value="${(tmAppMain.productCd)!}" options=ams_('tableMap','productForStatus','A') />
	</@field>
	<@field label="主卡卡面">
		<@selectLink options=ams_("tableMap","productCardFace",'${(tmAppMain.productCd)!}')
			link_id="productCd" url="ams_/getCardFaceByProductCd?productCd" keyfield="key" valuefield="value"
			showcode='true' nullable="false" readonly="true" name="tmAppCardfaceInfo.pyhCd" value="${(tmAppCardFaceInfo[0].pyhCd)!''}" />
	</@field>
	<@field label="主卡申请日期">
		<@date datetype="datetime" readonly="true" name="tmAppPrimApplicantInfo.createDate" value="${(tmAppPrimApplicantInfo.createDate?datetime)!}"/>
	</@field>
</@row>