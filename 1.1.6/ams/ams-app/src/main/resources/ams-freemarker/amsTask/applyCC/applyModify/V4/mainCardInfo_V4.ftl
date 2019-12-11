
<@fieldset legend="申请信息:">
	<@row>
		<@field label="是否自选卡号">
			<@dictSelect id="ifSelectedCard" dicttype="Indicator" change="ifSelectedCardChange('A')" nullable="false" name="tmAppPrimApplicantInfo.ifSelectedCard" value="${(tmAppPrimApplicantInfo.ifSelectedCard)!'N'}" />
		</@field>
		<@field label="自选卡号">
			<div style="margin-right: -5px;margin-left: -5px;">
				<div class="col-ar-10" style="padding-right:0px;">
					<@input id="cardBin" readonly="true" name="cardBin" value="${(cardBin)!}"/>
				</div>
				<div class="col-ar-14" style="padding-right:0px;padding-left:0px;">
					<@input id="cardNo" name="tmAppPrimApplicantInfo.cardNo" value="${(tmAppPrimApplicantInfo.cardNo)!}" valid={"regexp or pattern":"${(selectCardNoRegexp)!}","regexp-message":"请输入正确的自选卡号"}/>
				</div>
				<div class="col-ar-4" style="padding-left:0px;">
					<@input id="validBit" name="validBit" value="${(validBit)!}" readonly="readonly"/>
				</div>
				<div class="col-ar-8" style="padding-left:0px;">
					<@button id="deleteCardNoBtn" name="解锁卡号" click="deleteSelectCardNo('A')"/>
				</div>
			</div>
		</@field>
	</@row>
	<@row>
		<@field label="是否实时发卡" point_flag="${(baseInfoMap['realtimeIssuingFlag'])!}" point="*">
			<@dictSelect dicttype="Indicator" name="tmAppMain.realtimeIssuingFlag" nullable="false" value="${(tmAppMain.realtimeIssuingFlag)!'N'}"/>
		</@field>	
		<@field label="申请额度">
     		<@input name="tmAppMain.appLmt" value="${(tmAppMain.appLmt)!}" valid={"between":"true","between-min":"0","between-max":"${(tmProduct.approvalMaximum)!}"}/>
		</@field>
	</@row>
	<@row>
		<@field label="申请类型">
			<@dictSelect id="appType" dicttype="AppType" name="tmAppMain.appType" nullable="false" value="${(tmAppMain.appType)!}" readonly="true" />
		</@field>
		<@field label="卡产品代码">
			<@select id="productCd" change="productCdChange" name="tmAppMain.productCd" nullable="false" value="${(tmAppMain.productCd)!}" options=ams_('tableMap','productForStatus','A') readonly="true"/>
		</@field>
		<@field label="同意产品降级">
			<@dictSelect dicttype="Indicator" nullable="false" name="tmAppPrimCardInfo.isPrdChange" value="${(tmAppPrimCardInfo.isPrdChange)!'Y'}"/>
		</@field>
	</@row>	
</@fieldset>
<@fieldset>
<@row>
<@field label="是否申请分期" hidden="${(ifNotShowInstal)!'true'}" id="isShow">
	<#if defaultInstalChoose?string ="Y">
		<@dictSelect dicttype="Indicator" change="isApplyInstalChange" id="isInstalment" nullable="false" name="tmAppMain.isInstalment" value="${(tmAppMain.isInstalment)!'Y'}"/>
	<#else>
		<@dictSelect dicttype="Indicator" change="isApplyInstalChange" id="isInstalment" nullable="false" name="tmAppMain.isInstalment" value="${(tmAppMain.isInstalment)!'N'}"/>
	</#if>
</@field>
</@row>
</@fieldset>

<#-- <input id="isInstal" type="hidden" name="tmAppMain.isInstalment" value="N"/>  -->
<#if ifNotShowInstal?string ="false">
	<div id="showInstalment" >
		<#include "InstalmentCreditInfo.ftl"/>
	</div>
<#else>
	<div id="showInstalment" style="display: none;">
		<#include "InstalmentCreditInfo.ftl"/>
	</div>
</#if>
<@fieldset legend="申请人基本信息:">
	<@row>
		<@field label="姓名">
			<@input id="mainName" name="tmAppPrimApplicantInfo.name" value="${(tmAppPrimApplicantInfo.name)!}"/>
		</@field>
		<@field label="姓名拼音">
			<@input name="tmAppPrimApplicantInfo.embLogo" value="${(tmAppPrimApplicantInfo.embLogo)!}" 
				valid={"stringlength":"true","stringlength-max":"30","stringlength-message":"请正确填写姓名拼音"}/>
		</@field>
		<@field label="移动电话">
			<@input name="tmAppPrimApplicantInfo.cellphone" value="${(tmAppPrimApplicantInfo.cellphone)!}" readonly="true" />
		</@field>	
	</@row>
	<@row>
		<@field label="证件类型">
			<@dictSelect id="idType" dicttype="IdType" name="tmAppPrimApplicantInfo.idType"  nullable="false" value="${(tmAppPrimApplicantInfo.idType)!}" readonly="true" />
		</@field>
		<@field label="证件号码">
			<@input id="idNo" name="tmAppPrimApplicantInfo.idNo" value="${(tmAppPrimApplicantInfo.idNo)!}" readonly="true" />
		</@field>
		<@field label="证件长期有效">
			<@dictSelect id="idLastAll" dicttype="Indicator" change="idLastAllChange('A')" nullable="false" name="tmAppPrimApplicantInfo.idLastAll" value="${(tmAppPrimApplicantInfo.idLastAll)!'N'}" />
		</@field>	
	</@row>
	<@row>
		<@field label="性别">
			<@dictSelect id="gender" dicttype="Gender" readonly="true" name="tmAppPrimApplicantInfo.gender" value="${(tmAppPrimApplicantInfo.gender)!}" />
		</@field>
		<@field label="生日">
			<@date id="birthday" readonly="true" settings={"maxDate":"%y-%M-%d"} name="tmAppPrimApplicantInfo.birthday" value="${(tmAppPrimApplicantInfo.birthday?date)!}" />
		</@field>
		<@field label="证件到期日">
			<@date id="idLastDate" name="tmAppPrimApplicantInfo.idLastDate" value="${(tmAppPrimApplicantInfo.idLastDate?date)!}" />
		</@field>
	</@row>
	<@row>
		<@field label="年龄">
			<@input id="age" name="tmAppPrimApplicantInfo.age" value="${(tmAppPrimApplicantInfo.age)!}" readonly="true" valid={"regexp or pattern":"^(0|([1-9](\\d)?))$","regexp-message":"请填写正确的年龄"}/>
		</@field>
		<#--<@field label="母亲姓氏" point_flag="${(mcardMap['motherLogo'])!}" point="*">
			<@input name="tmAppPrimApplicantInfo.motherLogo" value="${(tmAppPrimApplicantInfo.motherLogo)!}"/>
		</@field>-->
	</@row>
	<@row>
		<@field label="婚姻状况">
			<@dictSelect dicttype="MaritalStatus" name="tmAppPrimApplicantInfo.maritalStatus" value="${(tmAppPrimApplicantInfo.maritalStatus)!}"  />
		</@field>	
		<@field label="教育状况">
			<@dictSelect dicttype="EducationType" name="tmAppPrimApplicantInfo.qualification" value="${(tmAppPrimApplicantInfo.qualification)!}" />
		</@field>
		<@field label="住宅狀況">
			<@dictSelect dicttype="HouseOwnership" name="tmAppPrimApplicantInfo.houseOwnership"  value="${(tmAppPrimApplicantInfo.houseOwnership)!}"/>
		</@field>
	</@row>
	<@row>
		<@field label="家庭所在省">
			<@select id="homeState" options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",{"type":"STATE"},"codeName","codeName") showcode='false' nullable="false" name="tmAppPrimApplicantInfo.homeState"
			 	value="${(tmAppPrimApplicantInfo.homeState)!'江西省'}" />
		</@field>
		<@field label="家庭所在市">
			<@selectLink id="homeCity" options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",'{"type":"CITY","value2":"'+(ar_("blank",(tmAppPrimApplicantInfo.homeState)!,"江西省"))+'"}',"codeName","codeName")
				link_id="homeState" keyfield="codeName" valuefield="codeName" append="true"
				url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'CITY'}" url_parent_key="value2"
				showcode='false' nullable="false" name="tmAppPrimApplicantInfo.homeCity" value="${(tmAppPrimApplicantInfo.homeCity)!'九江市'}" />
		</@field>
		<@field label="家庭所在区/县">
			 <@selectLink options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",'{"type":"ZONE","value2":"'+(ar_("blank",(tmAppPrimApplicantInfo.homeCity)!,"九江市"))+'"}',"codeName","codeName")
				link_id="homeCity" keyfield="codeName" valuefield="codeName" append="true"
				url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'ZONE'}" url_parent_key="value2"
				showcode='false' name="tmAppPrimApplicantInfo.homeZone" value="${(tmAppPrimApplicantInfo.homeZone)!}"/>
		</@field>
	</@row>
	<@row>
		<@field label="家庭地址">
			<@input name="tmAppPrimApplicantInfo.homeAdd" value="${(tmAppPrimApplicantInfo.homeAdd)!}" />
		</@field>
		<@field label="家庭住宅邮编">
			<@input name="tmAppPrimApplicantInfo.homePostcode" value="${(tmAppPrimApplicantInfo.homePostcode)!}" valid={"regexp or pattern":"^(\\d{6}$)","regexp-message":"请输入正确的邮编"}/>
		</@field>
		<@field label="家庭电话">
			<@input name="tmAppPrimApplicantInfo.homePhone" value="${(tmAppPrimApplicantInfo.homePhone)!}" valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的电话号码"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="职业资料:">
	<@row>
		<@field label="工作稳定性">
           <@dictSelect dicttype="EmpStability"  nullable="false" name="tmAppPrimApplicantInfo.empStability" value="${(tmAppPrimApplicantInfo.empStability)!'B'}"/>
        </@field>
        <@field label="个人年收入">
			<@input name="tmAppPrimApplicantInfo.yearIncome" value="${(tmAppPrimApplicantInfo.yearIncome?string('#.##'))!}" 
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写年收入"}/>
		</@field>
	</@row>
	<#-- 公务卡单位信息按钮 开始 -->
	<#if (tmProduct.subCardType)?? && (tmProduct.subCardType) =="O">
		<@row id="getOffialCardCorp">
			<@hidden id="subCardType" name="tmProduct.subCardType" value="${(tmProduct.subCardType)!''}" />
			<#include "../../common/offialCardCorpBtn.ftl"/>
		</@row>
	</#if>
	<#-- 公务卡单位信息按钮 结束 -->
	<@row>
		<@field label="公司名称" field_ar="24" label_ar="7" input_ar="29">
			<@input id="corpName" readonly="${(ifCorpReadonly)!'false'}" name="tmAppPrimApplicantInfo.corpName" value="${(tmAppPrimApplicantInfo.corpName)!}" 
				valid={"stringlength":"true","stringlength-max":"80","stringlength-message":"请正确填写公司名称"}/>
		</@field>
		<@field label="公司性质">
			<@dictSelect id="empStructure" readonly="${(ifCorpReadonly)!'false'}" dicttype="EmpStructure" name="tmAppPrimApplicantInfo.empStructure" value="${(tmAppPrimApplicantInfo.empStructure)!}"/>
		</@field>
	</@row>
	<@row>
	<#--	<@field label="现单位工作起始年月">
			<@date settings={"maxDate":"%y-%M-%d"} name="tmAppPrimApplicantInfo.empStandFrom" value="${(tmAppPrimApplicantInfo.empStandFrom?date)!}"/>
		</@field>	-->
		<@field label="公司行业类别">
          	<@dictSelect id="empType" readonly="${(ifCorpReadonly)!'false'}" dicttype="EmpType" name="tmAppPrimApplicantInfo.empType" value="${(tmAppPrimApplicantInfo.empType)!}" />
        </@field>	
		<@field label="现单位工作年限">
			<@input name="tmAppPrimApplicantInfo.empWorkyears" value="${(tmAppPrimApplicantInfo.empWorkyears)!}" 
				valid={"regexp or pattern":"^(0|([1-9](\\d)?))(\\.(\\d))?$","stringlength-message":"请正确填写现单位工作年限"}/>
		</@field>
		<@field label="任职部门">
			<@input name="tmAppPrimApplicantInfo.empDepapment" value="${(tmAppPrimApplicantInfo.empDepapment)!}"
				valid={"stringlength":"true","stringlength-max":"80","stringlength-message":"请正确填写任职部门"}/>
		</@field>
	</@row>
	<@row>	
		<@field label="职务">
			<@dictSelect dicttype="EmpPost" name="tmAppPrimApplicantInfo.empPost"  value="${(tmAppPrimApplicantInfo.empPost)!}"/>
		</@field>
		<@field label="职务名称">
			<@input name="tmAppPrimApplicantInfo.empPostName" value="${(tmAppPrimApplicantInfo.empPostName)!}"
				valid={"stringlength":"true","stringlength-max":"80","stringlength-message":"请正确填写职务名称"}/>
		</@field>
		<@field label="职称">
			<@dictSelect dicttype="TitleOfTechnical" name="tmAppPrimApplicantInfo.titleOfTechnical"  value="${(tmAppPrimApplicantInfo.titleOfTechnical)!}"/>
		</@field>
		<#--<@field label="岗位级别">
            <@dictSelect dicttype="JobGrade" name="tmAppPrimApplicantInfo.jobGrade" value="${(tmAppPrimApplicantInfo.jobGrade)!}"/>
        </@field>-->
	</@row>
	<@row>
		<#if (tmProduct.subCardType)?? && (tmProduct.subCardType) =="O">
			<@field label="公司所在省" point_flag="${(requiredMap['tmAppPrimApplicantInfo.empProvince'])!}" point="*">
				 <@input id="empProvince" readonly="true"  name="tmAppPrimApplicantInfo.empProvince" value="${(tmAppPrimApplicantInfo.empProvince)!}"/>
			</@field>
			<@field label="公司所在市" point_flag="${(requiredMap['tmAppPrimApplicantInfo.empCity'])!}" point="*">
				<@input id="empCity" readonly="true"  name="tmAppPrimApplicantInfo.empCity" value="${(tmAppPrimApplicantInfo.empCity)!}"/>
			</@field>
			<@field label="公司所在区/县" point_flag="${(requiredMap['tmAppPrimApplicantInfo.empZone'])!}" point="*">
				<@input id="empZone" readonly="true"  name="tmAppPrimApplicantInfo.empZone" value="${(tmAppPrimApplicantInfo.empZone)!}"/>
			</@field>
		<#else>
			<@field label="公司所在省" point_flag="${(requiredMap['tmAppPrimApplicantInfo.empProvince'])!}" point="*">
				<@select id="empProvince" options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",{"type":"STATE"},"codeName","codeName") showcode='false' nullable="false" name="tmAppPrimApplicantInfo.empProvince"
				 	value="${(tmAppPrimApplicantInfo.empProvince)!'江西省'}" />
			</@field>
			<@field label="公司所在市" point_flag="${(requiredMap['tmAppPrimApplicantInfo.empCity'])!}" point="*">
				<@selectLink id="empCity" options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",'{"type":"CITY","value2":"'+(ar_("blank",(tmAppPrimApplicantInfo.empProvince)!,"江西省"))+'"}',"codeName","codeName")
					link_id="empProvince" keyfield="codeName" valuefield="codeName" append="true"
					url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'CITY'}" url_parent_key="value2"
					showcode='false' nullable="false" name="tmAppPrimApplicantInfo.empCity" value="${(tmAppPrimApplicantInfo.empCity)!'九江市'}" />
			</@field>
			<@field label="公司所在区/县" point_flag="${(requiredMap['tmAppPrimApplicantInfo.empZone'])!}" point="*">
				<@selectLink id="empZone"  options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",'{"type":"ZONE","value2":"'+(ar_("blank",(tmAppPrimApplicantInfo.empCity)!,"九江市"))+'"}',"codeName","codeName")
					link_id="empCity" keyfield="codeName" valuefield="codeName" append="true"
					url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'ZONE'}" url_parent_key="value2"
					showcode='false' name="tmAppPrimApplicantInfo.empZone" value="${(tmAppPrimApplicantInfo.empZone)!}"/>
			</@field>
		</#if>
	</@row>
	<@row>
		<@field label="公司地址">
			<@input id="empAdd" name="empAdd"  name="tmAppPrimApplicantInfo.empAdd" value="${(tmAppPrimApplicantInfo.empAdd)!}" />
		</@field>
		<@field label="公司邮编">
			<@input id="empPostcode" name="tmAppPrimApplicantInfo.empPostcode" value="${(tmAppPrimApplicantInfo.empPostcode)!}" valid={"regexp or pattern":"^(\\d{6}$)","regexp-message":"请输入有效的邮编"}/>
		</@field>
		<@field label="公司电话">
			<@input id="empPhone" name="tmAppPrimApplicantInfo.empPhone" value="${(tmAppPrimApplicantInfo.empPhone)!}" valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的电话号码"}/>
		</@field>
	</@row>
	<@row>	             
		<@field label="是否行内员工">
	     	<@dictSelect id="bankmemFlag" dicttype="Indicator"  nullable="false" name="tmAppPrimApplicantInfo.bankmemFlag" value="${(tmAppPrimApplicantInfo.bankmemFlag)!'N'}"/>
	 	</@field>
		<@field label="本行员工号">
	     	<@input name="tmAppPrimApplicantInfo.bankmemberNo" value="${(tmAppPrimApplicantInfo.bankmemberNo)!}"
				valid={"stringlength":"true","stringlength-max":"20","stringlength-message":"请正确填写本行员工号"}/>
	 	</@field>
	</@row>
</@fieldset>
<@fieldset legend="第一联系人信息:">
	<@row>
		<@field label="联系人姓名">
			<@input name="tmAppContact[0].contactName" value="${(tmAppContact1.contactName)!}"
				valid={"stringlength":"true","stringlength-max":"10","stringlength-message":"请正确填写第一联系人姓名"}/>
		</@field>
		<@field label="联系人与申请人关系">
			<@dictSelect dicttype="ContactRelation" name="tmAppContact[0].contactRelation" value="${(tmAppContact1.contactRelation)!}" />
		</@field>             
	</@row>
	<@row>
		<@field label="联系人移动电话">
			<@input name="tmAppContact[0].contactMobile" value="${(tmAppContact1.contactMobile)!}" valid={"regexp or pattern":"^1[3456789]\\d{9}$","regexp-message":"请输入有效的手机号"}/>
		</@field>
		<@field label="联系人电话">
			<@input name="tmAppContact[0].contactTelephone" value="${(tmAppContact1.contactTelephone)!}" valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的电话号码"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="其他联系人信息:">
	<@row>
		<@field label="联系人姓名">
			<@input name="tmAppContact[1].contactName" value="${(tmAppContact2.contactName)!}"
				valid={"stringlength":"true","stringlength-max":"10","stringlength-message":"请正确填写其他联系人姓名"}/>
		</@field>
		<@field label="联系人与申请人关系">
			<@dictSelect dicttype="ContactRelation" name="tmAppContact[1].contactRelation" value="${(tmAppContact2.contactRelation)!}"/>
		</@field>
	</@row>
	<@row>
		<@field label="联系人移动电话">
			<@input name="tmAppContact[1].contactMobile" value="${(tmAppContact2.contactMobile)!}" valid={"regexp or pattern":"^1[3456789]\\d{9}$","regexp-message":"请输入有效的手机号"} />
		</@field>
		<@field label="联系人电话">
			<@input name="tmAppContact[1].contactTelephone" value="${(tmAppContact2.contactTelephone)!}" valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的电话号码"}/>
		</@field>
	</@row>
</@fieldset>

<#if tmAppMain?? && tmAppMain.appType?? && tmAppMain.appType == 'A' && tmAttachApplierInfoList??>
	<input id="idNum" type="hidden" value="${(tmAppnoSeq.seq)!'5'}"/><#--值从5开始-->
	<#list tmAttachApplierInfoList as tmAppAttachApplierInfo>
		<#assign atId = tmAppAttachApplierInfo.attachNo-5>
		<#if tmAppAttachApplierInfo_index == 0>
			<@fieldset>
				<@legend>附卡申请人信息</@legend>
				<#include "attachContent_V4.ftl">
			</@fieldset>
		<#else>
			<@fieldset>
				<@legend>附卡${(atId)!}申请人信息</@legend>
				<#include "attachContent_V4.ftl">
			</@fieldset>
		</#if>
	</#list>
</#if>

<@fieldset legend="自动还款授权及消费方式:">
	<@row>
		<@field label="约定还款类型" point_flag="${(otherInfoMap['ddInd'])!}" point="*">
			<@dictSelect id="ddIndType" dicttype="DdInd" change="ddIndTypeChange" nullable="false"  name="tmAppPrimCardInfo.ddInd" value="${(tmAppPrimCardInfo.ddInd)!'N'}" />
		</@field>
		<@field label="约定还款扣款人姓名">
	        <@input id="ddBankAcctName" name="tmAppPrimCardInfo.ddBankAcctName" value="${(tmAppPrimCardInfo.ddBankAcctName)!}"
	        	valid={"stringlength":"true","stringlength-max":"10","stringlength-message":"请正确填写还款扣款人姓名"}/>
	    </@field>
	    <@field label="约定还款账户类型">
			<@dictSelect id="ddBankAcctNoType" dicttype="DdBankAcctNoType" readonly="true" name="tmAppPrimCardInfo.ddBankAcctNoType" value="${(tmAppPrimCardInfo.ddBankAcctNoType)!'N'}" nullable="false"/>
		</@field>
	</@row>
	<@row>
    	<@field label="约定还款开户行号" point_flag="${(otherInfoMap['ddBankBranch'])!}" point="*">
			<@dictSelect id="ddBankBranch" dicttype="DdBankBranch" change="ddBankBranchChange" readonly="true" name="tmAppPrimCardInfo.ddBankBranch" value="${(tmAppPrimCardInfo.ddBankBranch)!}" nullable="false"/>
		</@field>
		<@field label="约定还款银行名称" point_flag="${(otherInfoMap['ddBankName'])!}" point="*">
			<@input id="ddBankName"  name="tmAppPrimCardInfo.ddBankName" value="${(tmAppPrimCardInfo.ddBankName)!}" readonly="true"/>
		</@field>
		<@field label="约定还款扣款账号">
			<div class="col-ar-28">
				<@input id="ddBankAcctNo" name="tmAppPrimCardInfo.ddBankAcctNo" value="${(tmAppPrimCardInfo.ddBankAcctNo)!}" valid={"regexp or pattern":"^\\d{1,20}$","regexp-message":"请输入有效的还款账号"}/>
			</div>
			<div class="col-ar-8" style="padding-left:0px;">
				<@button id="checkCardBtn" name="验证账号" click="checkCard()"/>
			</div>
		</@field>
	</@row>
	<@row>
		<@field label="是否消费凭密">
			<@dictSelect dicttype="Indicator" nullable="false"  name="tmAppPrimApplicantInfo.posPinVerifyInd" value="${(tmAppPrimApplicantInfo.posPinVerifyInd)!'Y'}" />
		</@field>
		<@field label="小额免密免签">
			<@dictSelect dicttype="Indicator" nullable="false"  name="tmAppPrimApplicantInfo.smAmtVerifyInd" value="${(tmAppPrimApplicantInfo.smAmtVerifyInd)!'Y'}" />
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="卡片及账单寄送:">
	<@row>
		<@field label="卡片领取方式">
			<@dictSelect id="cardFetchMethod" dicttype="CardFetchMethod" change="cfMethodChange('A')" nullable="false"   name="tmAppPrimCardInfo.cardFetchMethod" value="${(tmAppPrimCardInfo.cardFetchMethod)!'R'}" />
		</@field>
		<@field label="卡片寄送地址">
			<@dictSelect id="cardMailerInd" dicttype="CardMailerInd" name="tmAppPrimCardInfo.cardMailerInd" value="${(tmAppPrimCardInfo.cardMailerInd)!'C'}" />
		</@field>
		<#--<@field label="领卡网点">
			<@multipleSelect id="fetchBranch" name="tmAppPrimCardInfo.fetchBranch" options=ams_('tableMap','branchMap','cardCollectInd') value="${(tmAppPrimCardInfo.fetchBranch)!}" showfilter="true" single="true"/>
		</@field>-->
	</@row>
	<@row>
		<@field label="账单介质类型">
			<@dictSelect id="stmtMediaType" dicttype="StmtMediaType" change="stmtMediaTypeChange" nullable="false"  name="tmAppPrimCardInfo.stmtMediaType" value="${(tmAppPrimCardInfo.stmtMediaType)!'E'}" />
		</@field>
		<@field label="电子邮箱">
			<@input id="email" name="tmAppPrimApplicantInfo.email" value="${(tmAppPrimApplicantInfo.email)!}" valid={"regexp or pattern":"${(appEmail)!}","regexp-message":"请输入有效的邮箱地址"} />
		</@field>
		<@field label="账单寄送地址">
			<@dictSelect id="stmtMailAddrInd" dicttype="CardMailerInd" name="tmAppPrimCardInfo.stmtMailAddrInd" value="${(tmAppPrimCardInfo.stmtMailAddrInd)!}" />
		</@field>
	</@row>
</@fieldset>
<#include "bankInfo_V4.ftl"/>

<script type="text/javascript">
<#-- 	$(function(){
		 根据初始卡产品,获取是否分期
		var show = '${(ifShow)!}';
		if(show=="false"){
			$('#showInstalment').hide();
			$('#isInstal').val("N");
		}else{
			$('#showInstalment').show();
			$('#isInstal').val("Y");
		}
	});-->
	<#--卡产品代码的改变
	var productCdChange = function(that){
		var productCd = $(that).val();
		var appType = $('#appType').val();
		var cardBin = getOrValidCardNo(productCd);
		if(cardBin == ''){// 判断该卡产品是否允许附卡
			return false;
		}
		var ifSelectedCard = $('#ifSelectedCard').val();
		if(ifSelectedCard != '' && ifSelectedCard == 'Y'){
			var validBit = $('#validBit').val();
			if(validBit != ''){
				deleteSelectCardNo('A');//解锁卡号
			}else{
				$('#cardNo').val('');
				$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppPrimApplicantInfo.cardNo', 'NOT_VALIDATED');
			}
			$('#cardBin').val(cardBin);
		}
		if(appType == 'A'){
			var idNum = $('#idNum').val()-5;//原始idNum的值最小是5，减5是为了得到下标从0开始，刚好满足附卡下标从0开始的特征
			var attachNo = '';
			var validBit = '';
			var ifSelectedCard = '';
			for(var i = 0; i <= idNum; i++){
				attachNo = $('#attachNo'+i).val();
				if(attachNo != ''){
					ifSelectedCard = $('#ifSelectedCard'+i).val();
					if(ifSelectedCard != '' && ifSelectedCard == 'Y'){
						validBit = $('#validBit'+i).val();
						if(validBit != ''){
							deleteSelectCardNo('A',i);//解锁卡号
						}else{
							$('#cardBin'+i).val(cardBin);
							$('#cardNo'+i).val('');
							$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppAttachApplierInfo['+i+'].cardNo', 'NOT_VALIDATED');
						}
					}
				}
			}
		}
	}
	-->
	<#--约定还款开户行号的改变 -->
	function ddBankBranchChange(){
		var ddBankBranch = $('#ddBankBranch').val();
		if(ddBankBranch != ''){
			$.ajax({
				url : "${base}/ams_applyInput/getDdBankName",
				type : "post",
				dataType : "json",
				data : {"ddBankBranch":ddBankBranch},
				success : function(res) {
					$('#ddBankName').val(res.msg);
				}
			});
		}
	}
	
	<#--约定还款账户有效性验证-->
	function checkCard(){
		var flag = true;
		var ddBankAcctNo = $('#ddBankAcctNo').val();// 约定还款账户账号
		if(ddBankAcctNo != ''){
			var ddBankAcctName = $('#ddBankAcctName').val();// 约定还款账户姓名
			var ddBankAcctNoType = $('#ddBankAcctNoType').val();
			var branchNo = $('#ddBankBranch').val();// 开户行号
			$('#checkCardBtn').attr("disabled","disabled");
			$.ajax({
	     		type: 'POST',
	     		async:false,
	     		dataType: 'json',
	     		data: {'ddBankAcctName':ddBankAcctName, 'ddBankAcctNo':ddBankAcctNo, 'idType':'${(tmAppMain.idType)!}', 'idNo':'${(tmAppMain.idNo)!}', 'branchNo':branchNo, 'acctNoType':ddBankAcctNoType},
				url: '${base}/ams_applyInput/checkDdBankAcct',
				success:function(res){
					alert(res.msg);
					if(!res.s){
						flag = false;
						$('#checkCardBtn').removeAttr("disabled");
						$('#ddBankAcctNo').val('');
					}
	    		}
			});
		}else{
			alert("没有需要验证的约定还款账户");
		}		
		return flag;
	};
</script>