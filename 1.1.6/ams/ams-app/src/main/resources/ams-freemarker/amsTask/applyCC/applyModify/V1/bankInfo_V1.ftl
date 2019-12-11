<@fieldset legend="银行专用栏:">
	<@row>
		<@field label="推广方式" point_flag="${(otherInfoMap['spreaderMode'])!}" point="*">
			<@multipleSelect name="spreaderMode" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
				'{"type":"SpreaderMode"}','code','codeName') value="${(tmAppPrimCardInfo.spreaderMode)!''}" showfilter="true"/>
		</@field>
		<@field label="推广渠道">
			<@dictSelect dicttype="SpreaderType" nullable="false" name="tmAppPrimCardInfo.spreaderType" value="${(tmAppPrimCardInfo.spreaderType)!'A'}"/>
		</@field>
		<@field label="受理网点">
			<@multipleSelect id="owningBranch" name="tmAppMain.owningBranch" value="${(tmAppMain.owningBranch)!}"
				options=ams_('tableMap','branchMap','issueInd')  nullable="true" showfilter="true" single="true"/>
		</@field>
	</@row>
	<@row>
		<@field label="客户类型">
			<@dictSelect dicttype="CustType" nullable="false" name="tmAppMain.custType" value="${(tmAppMain.custType)!'A00'}"/>
		</@field>
		<@field label="账单日">
			<@input name="tmAppPrimCardInfo.billingCycle" value="${(tmAppPrimCardInfo.billingCycle)!}"
				valid={"regexp or pattern":"^(([1-9](\\s)?)|(1\\d)|(2[0-8]))$","regexp-message":"请输入正确的账单日"}/>
		</@field>		
		<@field label="申请渠道">
			<@dictSelect dicttype="AppSource" nullable="false" name="tmAppMain.appSource" value="${(tmAppMain.appSource)!'06'}"/>
		</@field>	
	</@row>
	<@row>
        <@field label="推广人工号"> 
            <@input id="spreaderNo" name="tmAppPrimCardInfo.spreaderNo" value="${(tmAppPrimCardInfo.spreaderNo)!}"
            	valid={"stringlength":"true","stringlength-max":"20","stringlength-message":"请正确填写推广人工号"}/>
        </@field>
        <@field label="推广人姓名">
            <@input id="spreaderName" name="tmAppPrimCardInfo.spreaderName" value="${(tmAppPrimCardInfo.spreaderName)!}"
            	valid={"stringlength":"true","stringlength-max":"20","stringlength-message":"请正确填写推广人姓名"}/>
        </@field>
        <@field label="推广人联系电话">
            <@input name="tmAppPrimCardInfo.spreaderTelephone" value="${(tmAppPrimCardInfo.spreaderTelephone)!}"
            	valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的联系电话"}/>
        </@field>
     </@row>
	 <@row>
        <@field label="推广人一级网点名称" point_flag="${(otherInfoMap['spreaderBranchOne'])!}" point="*">			
			<@select id="spreaderBranchOne" options=ams_('tableMap','branchMap','')
			nullable="false" name="tmAppPrimCardInfo.spreaderBranchOne" value="${(tmAppPrimCardInfo.spreaderBranchOne)!''}" />				
		</@field>
		<@field label="推广人二级网点名称" point_flag="${(otherInfoMap['spreaderBranchTwo'])!}" point="*">
			<@selectLink id="spreaderBranchTwo" options=ams_('tableMap','subBranchMap','${(tmAppPrimCardInfo.spreaderBranchOne)!}')
				link_id="spreaderBranchOne" keyfield="branchCode" valuefield="branchName" append="true"
				url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclBranch&json={}" url_parent_key="parentCode"
				nullable="true" name="tmAppPrimCardInfo.spreaderBranchTwo" value="${(tmAppPrimCardInfo.spreaderBranchTwo)!''}" />	
		</@field>
		<@field label="促销码" point_flag="${(otherInfoMap['appPromotionCd'])!}" point="*">
           <@input name="tmAppPrimCardInfo.appPromotionCd" value="${(tmAppPrimCardInfo.appPromotionCd)!}"
           		valid={"stringlength":"true","stringlength-max":"15","stringlength-message":"请正确填写的促销码"}/>
        </@field>
	</@row>
	<@row style="margin-bottom:4px;">
		<@field label="推广人注记" field_ar="36" label_ar="5" input_ar="31">
			<@textarea style="margin-left:-2px;" name="tmAppMemoSpr.memoInfo" cols="" rows="1" value="${(MEMOINPUT.memoInfo)!''}" readonly="true"/>
		</@field>
	</@row>
	<@row>
		<@field label="预审人工号" point_flag="${(otherInfoMap['preNo'])!}" point="*">
			<@input id="preNo" name="tmAppPrimCardInfo.preNo" value="${(tmAppPrimCardInfo.preNo)!}" 
				valid={"stringlength":"true","stringlength-max":"20","stringlength-message":"请正确填写预审人工号"}/>
		</@field>
		<@field label="预审人姓名" point_flag="${(otherInfoMap['preName'])!}" point="*">
			<@input id="preName" name="tmAppPrimCardInfo.preName" value="${(tmAppPrimCardInfo.preName)!}" 
				valid={"stringlength":"true","stringlength-max":"20","stringlength-message":"请正确填写预审人姓名"}/>
		</@field>
		<@field label="预审人联系电话" point_flag="${(otherInfoMap['preTelephone'])!}" point="*">
			<@input name="tmAppPrimCardInfo.preTelephone" value="${(tmAppPrimCardInfo.preTelephone)!}"
				valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的联系电话"}/>
		</@field>
	</@row>
	<@row style="margin-bottom:4px;">
		<@field label="预审人注记" field_ar="36" label_ar="5" input_ar="31">
			<@textarea style="margin-left:-2px;" name="tmAppMemoPre.memoInfo" cols="" rows="1" value="${(REMARKINPUT.memoInfo)!''}" readonly="true"/>
		</@field>
	</@row>
	<@row>
		<@field label="进件管理员工号" point_flag="${(otherInfoMap['inputManageUser'])!}" point="*">
            <@input name="tmAppPrimCardInfo.inputManageUser" value="${(tmAppPrimCardInfo.inputManageUser)!}" readonly="true"/>
		</@field>
		<@field label="录入员" point_flag="${(otherInfoMap['inputName'])!}" point="*">
			<@input name="tmAppPrimCardInfo.inputName" value="${(tmAppPrimCardInfo.inputName)!}" readonly="true"/>
		</@field>
		<@field label="录入日期" point_flag="${(otherInfoMap['inputDate'])!}" point="*">
			<@date datetype="datetime" readonly="true" name="tmAppPrimCardInfo.inputDate" value="${(tmAppPrimCardInfo.inputDate?datetime)!}"/>
		</@field>
	</@row>
</@fieldset>
