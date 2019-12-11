
<#if tmAttachApplierInfoList??>
	<#list tmAttachApplierInfoList as tmAppAttachApplierInfo>
		<@fieldset legend="申请信息:">
			<@row>
				<@field label="是否自选卡号">
					<@dictSelect id="ifSelectedCard" dicttype="Indicator" change="ifSelectedCardChange('S')" readonly="true" nullable="false" name="tmAppAttachApplierInfo.ifSelectedCard" value="${(tmAppAttachApplierInfo.ifSelectedCard)!'N'}" />
				</@field>
				<@field label="自选卡号">
					<div style="margin-right: -5px;margin-left: -5px;">
						<div class="col-ar-8" style="padding-right:0px;">
							<#assign attachCardBin="cardBin${(tmAppAttachApplierInfo['attachNo'])!''}">
							<@input id="cardBin" readonly="true" value="${(attachCardNoMap[attachCardBin])!}"/>
						</div>
						<div class="col-ar-16" style="padding-right:0px;padding-left:0px;">
							<@input id="cardNo" readonly="true" name="tmAppAttachApplierInfo.cardNo" value="${(tmAppAttachApplierInfo.cardNo)!}" valid={"regexp or pattern":"^(\\d{9}$)","regexp-message":"请输入9位正确的自选卡号"}/>
						</div>
						<div class="col-ar-4" style="padding-left:0px;">
							<#assign attachValidBit="validBit${(tmAppAttachApplierInfo['attachNo'])!''}">
							<@input id="validBit" name="validBit" value="${(attachCardNoMap[attachValidBit])!}" readonly="readonly"/>
						</div>
						<div class="col-ar-8" style="padding-left:0px;">
							<@button id="deleteCardNoBtn" name="解锁卡号" click="deleteSelectCardNo('S')"/>
						</div>
					</div>
				</@field>
				<@field label="同意产品降级">
					<@dictSelect dicttype="Indicator" nullable="false" name="tmAppPrimCardInfo.isPrdChange" value="${(tmAppPrimCardInfo.isPrdChange)!'Y'}"/>
				</@field>
			</@row>
			<#include "../attach_mainCard.ftl"/>
		</@fieldset>
		<@fieldset legend="申请人基本信息:">
			<@row>
				<@field label="姓名">
					<@input id="name" name="tmAppAttachApplierInfo.name" value="${(tmAppAttachApplierInfo.name)!}" />
				</@field>
				<@field label="姓名拼音">
					<@input id="embLogo" name="tmAppAttachApplierInfo.embLogo" value="${(tmAppAttachApplierInfo.embLogo)!}" 
						valid={"stringlength":"true","stringlength-max":"30","stringlength-message":"请正确填写姓名拼音"}/>
				</@field>
				<@field label="移动电话">
					<@input name="tmAppAttachApplierInfo.cellphone" value="${(tmAppAttachApplierInfo.cellphone)!}" readonly="true" />
				</@field>
			</@row>
			<@row>        
				<@field label="证件类型">
					<@dictSelect dicttype="IdType" id="idType"  nullable="false" name="tmAppAttachApplierInfo.idType" value="${(tmAppAttachApplierInfo.idType)!}" readonly="true"  />
				</@field>
				<@field label="证件号码">
					<@input id="idNo" name="tmAppAttachApplierInfo.idNo" value="${(tmAppAttachApplierInfo.idNo)!}" readonly="true" />
				</@field>
				<@field label="证件长期有效">
					<@dictSelect id="idLastAll" dicttype="Indicator" change="idLastAllChange('S')" nullable="false" name="tmAppAttachApplierInfo.idLastAll" value="${(tmAppAttachApplierInfo.idLastAll)!'N'}" />
				</@field>
			</@row>
			<@row>
				<@field label="性别">
					<@dictSelect id="gender" dicttype="Gender" readonly="true" name="tmAppAttachApplierInfo.gender" value="${(tmAppAttachApplierInfo.gender)!}" />
				</@field>
				<@field label="生日">
					<@date id="birthday" readonly="true" settings={"maxDate":"%y-%M-%d"} name="tmAppAttachApplierInfo.birthday" value="${(tmAppAttachApplierInfo.birthday?date)!}" />
				</@field>
				<@field label="证件到期日">
					<@date id="idLastDate" name="tmAppAttachApplierInfo.idLastDate" value="${(tmAppAttachApplierInfo.idLastDate?date)!}" />
				</@field>
			</@row>
			<@row>
				<@field label="婚姻状况">
					<@dictSelect dicttype="MaritalStatus" name="tmAppAttachApplierInfo.maritalStatus" value="${(tmAppAttachApplierInfo.maritalStatus)!}" />
				</@field>
				<@field label="教育状况">
					<@dictSelect dicttype="EducationType" name="tmAppAttachApplierInfo.qualification" value="${(tmAppAttachApplierInfo.qualification)!}" />
				</@field>
				<@field label="住宅狀況">
					<@dictSelect dicttype="EstateType"  name="tmAppAttachApplierInfo.houseOwnership" value="${(tmAppAttachApplierInfo.houseOwnership)!}"/>
				</@field>
			</@row>
			<@row>
				<@field label="家庭所在省">
					<@select id="homeState" options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",{"type":"STATE"},"codeName","codeName") showcode='false' nullable="false" name="tmAppAttachApplierInfo.homeState"
					 	value="${(tmAppAttachApplierInfo.homeState)!'江西省'}" />
				</@field>
				<@field label="家庭所在市">
					<@selectLink id="homeCity" options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",'{"type":"CITY","value2":"'+(ar_("blank",(tmAppAttachApplierInfo[""].homeState)!,"江西省"))+'"}',"codeName","codeName")
						link_id="homeState" keyfield="codeName" valuefield="codeName" append="true"
						url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'CITY'}" url_parent_key="value2"
						showcode='false' nullable="false" name="tmAppAttachApplierInfo.homeCity" value="${(tmAppAttachApplierInfo.homeCity)!'九江市'}" />
				</@field>
				<@field label="家庭所在区/县">
					 <@selectLink id="homeZone" options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",'{"type":"ZONE","value2":"'+(ar_("blank",(tmAppAttachApplierInfo[""].homeCity)!,"九江市"))+'"}',"codeName","codeName")
						link_id="homeCity" keyfield="codeName" valuefield="codeName" append="true"
						url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'ZONE'}" url_parent_key="value2"
						showcode='false' name="tmAppAttachApplierInfo.homeZone" value="${(tmAppAttachApplierInfo.homeZone)!}"/>
				</@field>
			</@row>
			<@row>
				<@field label="家庭地址">
					<@input name="tmAppAttachApplierInfo.homeAdd" value="${(tmAppAttachApplierInfo.homeAdd)!}" />
				</@field>
				<@field label="家庭住宅邮编">
					<@input name="tmAppAttachApplierInfo.homePostcode" value="${(tmAppAttachApplierInfo.homePostcode)!}" valid={"regexp or pattern":"^(\\d{6}$)","regexp-message":"请输入有效的邮编"}/>
				</@field>
				<@field label="家庭电话">
					<@input name="tmAppAttachApplierInfo.homePhone" value="${(tmAppAttachApplierInfo.homePhone)!}" valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的电话号码"}/>
				</@field>
			</@row>
			<@row>
			    <@field label="电子邮箱">
					<@input name="tmAppAttachApplierInfo.email" value="${(tmAppAttachApplierInfo.email)!}" valid={"regexp or pattern":"${(appEmail)!}","regexp-message":"请输入有效的邮箱地址"} />
				</@field>
				<@field label="工作稳定性">
					<@dictSelect dicttype="EmpStability"  name="tmAppAttachApplierInfo.empStability" value="${(tmAppAttachApplierInfo.empStability)!'B'}"/>
				</@field>
				<@field label="年收入">
					<@input name="tmAppAttachApplierInfo.yearIncome" value="${(tmAppAttachApplierInfo.yearIncome?string('#.##'))!}" 
						valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写年收入"}/>
				</@field>
			</@row>
			<@row>		
				<@field label="是否消费凭密">
					<@dictSelect dicttype="Indicator" nullable="false"  name="tmAppAttachApplierInfo.posPinVerifyInd" value="${(tmAppAttachApplierInfo.posPinVerifyInd)!'Y'}" />
				</@field>
				<@field label="小额免密免签">
					<@dictSelect dicttype="Indicator" nullable="false"  name="tmAppAttachApplierInfo.smAmtVerifyInd" value="${(tmAppAttachApplierInfo.smAmtVerifyInd)!'Y'}" />
				</@field>
			</@row>
		</@fieldset>
		<@fieldset legend="卡片寄送方式:">
			<@row>
				<@field label="卡片领取方式">
					<@dictSelect id="cardFetchMethod" dicttype="CardFetchMethod" change="cfMethodChange('S')" nullable="false" nullable="false" name="tmAppAttachApplierInfo.cardFetch" value="${(tmAppAttachApplierInfo.cardFetch)!'R'}" />
				</@field>
				<@field label="卡片寄送地址">
					<@dictSelect id="cardMailerInd" dicttype="CardMailerInd" nullable="false" name="tmAppAttachApplierInfo.cardMailerInd" value="${(tmAppAttachApplierInfo.cardMailerInd)!'C'}" />
				</@field>
				<@field label="领卡网点">
					<@multipleSelect id="fetchBranch" name="tmAppAttachApplierInfo.fetchBranch" options=ams_('tableMap','branchMap','cardCollectInd') value="${(tmAppAttachApplierInfo.fetchBranch)!}" showfilter="true" single="true"/>
				</@field>
			</@row>
		</@fieldset>
		<#include "bankInfo_V1.ftl"/>
	</#list>
</#if>