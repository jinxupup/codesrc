
<@field hidden="true">
	<@input id="attachNo${(atId)!}" name="tmAppAttachApplierInfo[${(atId)!}].attachNo" value="${(tmAppAttachApplierInfo.attachNo)!'5'}" />
</@field>
<@row>
	<@field label="是否自选卡号">
		<@dictSelect id="ifSelectedCard${(atId)!}" dicttype="Indicator" change="ifSelectedCardChange('S','${(atId)!}')" readonly="true" nullable="false" name="tmAppAttachApplierInfo[${(atId)!}].ifSelectedCard" value="${(tmAppAttachApplierInfo.ifSelectedCard)!'N'}" />
	</@field>
	<@field label="自选卡号">
		<div style="margin-right: -5px;margin-left: -5px;">
			<div class="col-ar-8" style="padding-right:0px;">
				<#assign attachCardBin="cardBin${(tmAppAttachApplierInfo.attachNo)!'5'}">
				<@input id="cardBin${(atId)!}" readonly="true" value="${(attachCardNoMap[attachCardBin])!}"/>
			</div>
			<div class="col-ar-16" style="padding-right:0px;padding-left:0px;">
				<@input id="cardNo${(atId)!}" readonly="true" name="tmAppAttachApplierInfo[${(atId)!}].cardNo" value="${(tmAppAttachApplierInfo.cardNo)!}" valid={"regexp or pattern":"^(\\d{9}$)","regexp-message":"请输入9位正确的自选卡号"}/>
			</div>
			<div class="col-ar-4" style="padding-left:0px;">
				<#assign attachValidBit="validBit${(tmAppAttachApplierInfo.attachNo)!'5'}">
				<@input id="validBit${(atId)!}" name="validBit${(atId)!}" value="${(attachCardNoMap[attachValidBit])!}" readonly="readonly"/>
			</div>
			<div class="col-ar-8" style="padding-left:0px;">
				<@button id="deleteCardNoBtn${(atId)!}" name="解锁卡号" click="deleteSelectCardNo('S','${(atId)!}')"/>
			</div>
		</div>
	</@field>
</@row>
<@row>
	<@field label="姓名">
		<@input id="name${(atId)!}" name="tmAppAttachApplierInfo[${(atId)!}].name" value="${(tmAppAttachApplierInfo.name)!}"
			valid={"stringlength":"true","stringlength-max":"10","stringlength-message":"请正确填写附卡姓名"}/>
	</@field>
	<@field label="姓名拼音">
		<@input id="embLogo${(atId)!}" name="tmAppAttachApplierInfo[${(atId)!}].embLogo" value="${(tmAppAttachApplierInfo.embLogo)!}"
			valid={"stringlength":"true","stringlength-max":"30","stringlength-message":"请正确填写附卡姓名拼音"}/>
	</@field>
	<@field label="与主卡持卡人关系">
		<@dictSelect dicttype="ContactRelation"  name="tmAppAttachApplierInfo[${(atId)!}].relationshipToBsc" value="${(tmAppAttachApplierInfo.relationshipToBsc)!}" />
	</@field>
</@row>
<@row>
	<@field label="证件类型">
		<@dictSelect id="idType${(atId)!}" dicttype="IdType" change="idTypeChange(${(atId)!})" nullable="false" name="tmAppAttachApplierInfo[${(atId)!}].idType" value="${(tmAppAttachApplierInfo.idType)!'I'}" />
	</@field>
	<@field label="证件号码">
		<@input id="idNo${(atId)!}" name="tmAppAttachApplierInfo[${(atId)!}].idNo" value="${(tmAppAttachApplierInfo.idNo)!}" />
	</@field>
	<@field label="证件长期有效">
		<@dictSelect id="idLastAll${(atId)!}" dicttype="Indicator" change="idLastAllChange('S','${(atId)!}')" nullable="false" name="tmAppAttachApplierInfo[${(atId)!}].idLastAll" value="${(tmAppAttachApplierInfo.idLastAll)!'N'}" />
	</@field>
</@row>
<@row>
	<@field label="性别">
		<@dictSelect id="gender${(atId)!}" dicttype="Gender" readonly="true" name="tmAppAttachApplierInfo[${(atId)!}].gender" value="${(tmAppAttachApplierInfo.gender)!}" />
	</@field>
	<@field label="生日">
		<@date id="birthday${(atId)!}" settings={"maxDate":"%y-%M-%d"} name="tmAppAttachApplierInfo[${(atId)!}].birthday" value="${(tmAppAttachApplierInfo.birthday?date)!}" />
	</@field>
	<@field label="证件到期日">
		<@date id="idLastDate${(atId)!}" name="tmAppAttachApplierInfo[${(atId)!}].idLastDate" value="${(tmAppAttachApplierInfo.idLastDate?date)!}" />
	</@field>
</@row>
<@row>
	<@field label="家庭所在省">
		<@select id="homeState${(atId)!}" options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",{"type":"STATE"},"codeName","codeName") showcode='false' nullable="false" name="tmAppAttachApplierInfo[${(atId)!}].homeState"
		 	value="${(tmAppAttachApplierInfo.homeState)!'江西省'}" />
	</@field>
	<@field label="家庭所在市">
		<@selectLink id="homeCity${(atId)!}" options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",'{"type":"CITY","value2":"'+(ar_("blank",(tmAppAttachApplierInfo["${(atId)!}"].homeState)!,"江西省"))+'"}',"codeName","codeName")
			link_id="homeState${(atId)!}" keyfield="codeName" valuefield="codeName" append="true"
			url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'CITY'}" url_parent_key="value2"
			showcode='false' nullable="false" name="tmAppAttachApplierInfo[${(atId)!}].homeCity" value="${(tmAppAttachApplierInfo.homeCity)!'九江市'}" />
	</@field>
	<@field label="家庭所在区/县">
		 <@selectLink id="homeZone${(atId)!}" options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",'{"type":"ZONE","value2":"'+(ar_("blank",(tmAppAttachApplierInfo["${(atId)!}"].homeCity)!,"九江市"))+'"}',"codeName","codeName")
			link_id="homeCity${(atId)!}" keyfield="codeName" valuefield="codeName" append="true"
			url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'ZONE'}" url_parent_key="value2"
			showcode='false' name="tmAppAttachApplierInfo[${(atId)!}].homeZone" value="${(tmAppAttachApplierInfo.homeZone)!}"/>
	</@field>
</@row>
<@row>
	<@field label="家庭地址">
		<@input name="tmAppAttachApplierInfo[${(atId)!}].homeAdd" value="${(tmAppAttachApplierInfo.homeAdd)!}" />
	</@field>
	<@field label="家庭住宅邮编">
		<@input name="tmAppAttachApplierInfo[${(atId)!}].homePostcode" value="${(tmAppAttachApplierInfo.homePostcode)!}" valid={"regexp or pattern":"^(\\d{6}$)","regexp-message":"请输入有效的邮编"}/>
	</@field>
	<@field label="家庭电话">
		<@input name="tmAppAttachApplierInfo[${(atId)!}].homePhone" value="${(tmAppAttachApplierInfo.homePhone)!}" valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的电话号码"}/>
	</@field>
</@row>
<@row>
	<@field label="国籍">
       	<@dictSelect dicttype="Nationality" name="tmAppAttachApplierInfo[${(atId)!}].nationality" value="${(tmAppAttachApplierInfo.nationality)!'156'}" />
    </@field>
	<@field label="移动电话">
		<@input name="tmAppAttachApplierInfo[${(atId)!}].cellphone" value="${(tmAppAttachApplierInfo.cellphone)!}" valid={"regexp or pattern":"^1[3456789]\\d{9}$","regexp-message":"请输入有效的手机号"} />
	</@field>
	<@field label="电子邮箱">
		<@input name="tmAppAttachApplierInfo[${(atId)!}].email" value="${(tmAppAttachApplierInfo.email)!}" valid={"regexp or pattern":"${(appEmail)!}","regexp-message":"请输入有效的邮箱地址"}/>
	</@field>
</@row>
<@row>
	<@field label="卡片领取方式" point_flag="${(otherInfoMap['cardFetchMethod'])!}" point="*">
		<@dictSelect id="cardFetchMethod${(atId)!}" dicttype="CardFetchMethod" change="cfMethodChange('A',${(atId)!})" nullable="false" name="tmAppAttachApplierInfo[${(atId)!}].cardFetch" value="${(tmAppAttachApplierInfo.cardFetch)!'A'}" />
	</@field>
	<@field label="卡片寄送地址" >
		<@dictSelect id="cardMailerInd${(atId)!}" dicttype="CardMailerInd" name="tmAppAttachApplierInfo[${(atId)!}].cardMailerInd" value="${(tmAppAttachApplierInfo.cardMailerInd)!'C'}" />
	</@field>
	<@field label="领卡网点" >
		<@multipleSelect id="fetchBranch${(atId)!}" name="tmAppAttachApplierInfo[${(atId)!}].fetchBranch" options=ams_('tableMap','branchMap','cardCollectInd') value="${(tmAppAttachApplierInfo.fetchBranch)!}" showfilter="true" single="true"/>
	</@field>
</@row>