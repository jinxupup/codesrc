
<@row>
	<@field label="申请类型:" >
		<@dictSelect dicttype="AppType" value="${(tmAppMain.appType)!}" label_only="true"/>
	</@field>
	<@field label="与主卡持卡人关系:">
		<@dictSelect dicttype="ContactRelation" value="${(tmAppAttachApplierInfo.relationshipToBsc)!}" label_only="true"/>
	</@field>    
	<@field label="主卡卡号:">
		<@input value="${(tmAppAttachApplierInfo.primCardNo)!}" label_only="true"/>
	</@field>                    
	<@field label="主卡人姓名:">
		<@input value="${(tmAppPrimApplicantInfo.name)!}" label_only="true"/>
	</@field>
	<@field label="主卡人证件类型:">
		<@dictSelect dicttype="IdType" value="${(tmAppPrimApplicantInfo.idType)!}" label_only="true"/>
	</@field>
	<@field label="主卡人证件号码:">
		<@input value="${(tmAppPrimApplicantInfo.idNo)!}" label_only="true"/>
	</@field>
	<@field label="主卡卡产品代码:">
		<@select options=ams_('tableMap','productForStatus','A') value="${(tmAppMain.productCd)!}" label_only="true"/>
	</@field>
	<@field label="主卡卡面:">
		<@input value="${(tmAppCardFaceInfo[0].pyhCd)!''}" label_only="true"/>
	</@field>
	<@field label="主卡申请日期:">
		<@date value="${(tmAppPrimApplicantInfo.createDate?datetime)!}" label_only="true"/>
	</@field>
</@row>