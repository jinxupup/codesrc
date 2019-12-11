
<@fieldset legend="个人信息:">
	<@row>            	 
		<@field label="是否集体件">
			<@dictSelect id="isClt" dicttype="Indicator"  nullable="false" name="tmAppMain.isClt" value="${(tmAppMain.isClt)!'N'}"/>
		</@field>
		<@field label="国籍">
			<@dictSelect id="nationality" dicttype="Nationality" nullable="false" name="tmAppPrimApplicantInfo.nationality" value="${(tmAppPrimApplicantInfo.nationality)!'156'}" />
		</@field>
	</@row>
	<@row>
		<@field label="快速审批标志">
			<@dictSelect dicttype="Indicator"  name="tmAppMain.approveQuickFlag" value="${(tmAppMain.approveQuickFlag)!'N'}"/>
		</@field>
		<@field label="优先处理标志">
			<@dictSelect dicttype="Indicator"  name="tmAppMain.isPriority" value="${(tmAppMain.isPriority)!'N'}"/>
		</@field>
		<@field label="紧急制卡标志">
			<@dictSelect dicttype="Indicator"  name="tmAppMain.isUrgentCard" value="${(tmAppMain.isUrgentCard)!'N'}"/>
		</@field>
	</@row>
	<@row>
		<@field label="发证机关所在地">
			<@input name="tmAppPrimApplicantInfo.idIssuerAddress" value="${(tmAppPrimApplicantInfo.idIssuerAddress)!}"/>
		</@field>
		<@field label="是否永久居住">
			<@dictSelect dicttype="Indicator"  nullable="false" name="tmAppPrimApplicantInfo.prOfCountry" value="${(tmAppPrimApplicantInfo.prOfCountry)!'Y'}"/>
		</@field>
		<@field label="永久居住地国家代码">
			<@dictSelect dicttype="Nationality" nullable="false" name="tmAppPrimApplicantInfo.residencyCountryCd" value="${(tmAppPrimApplicantInfo.residencyCountryCd)!'156'}"/>
		</@field>
	</@row>
	<@row>
		<@field label="学位">
			<@dictSelect dicttype="Degree" name="tmAppPrimApplicantInfo.degree" value="${(tmAppPrimApplicantInfo.degree)!}"/>
		</@field> 
		<@field label="个人资产类型">
			<@dictSelect dicttype="LiquidAsset"  name="tmAppPrimApplicantInfo.liquidAsset" value="${(tmAppPrimApplicantInfo.liquidAsset)!}"/>
		</@field>
		<@field label="房屋持有类型">
			<@dictSelect dicttype="HouseType" name="tmAppPrimApplicantInfo.houseType" value="${(tmAppPrimApplicantInfo.houseType)!}"  />
		</@field>
	</@row>
	<@row>
		<@field label="家庭人口">
			<@input name="tmAppPrimApplicantInfo.familyMember" value="${(tmAppPrimApplicantInfo.familyMember)!}" 
				valid={"regexp or pattern":"^((0$)|([1-9](\\d)?$))","regexp-message":"请输入正确的家庭人口"} />
		</@field>
		<@field label="家庭人均年收入">
			<@input name="tmAppPrimApplicantInfo.familyAvgeVenue" value="${(tmAppPrimApplicantInfo.familyAvgeVenue?string('#.##'))!}" 
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写家庭人均年收入"}/>
		</@field>
		<@field label="家庭国家代码">
			<@dictSelect dicttype="Nationality" nullable="false" name="tmAppPrimApplicantInfo.homeAddrCtryCd" value="${(tmAppPrimApplicantInfo.homeAddrCtryCd)!'156'}"  />
		</@field> 
	</@row>
</@fieldset>
<@fieldset legend="公司信息:">
	<@row>
		<@field label="职业">
			<@dictSelect dicttype="Occupation"  name="tmAppPrimApplicantInfo.occupation" value="${(tmAppPrimApplicantInfo.occupation)!}"/>
		</@field>
		<@field label="公司国家代码">
			<@dictSelect id="empAddrCtryCd" readonly="${(ifCorpReadonly)!'false'}" dicttype="Nationality" nullable="false" name="tmAppPrimApplicantInfo.empAddrCtryCd" value="${(tmAppPrimApplicantInfo.empAddrCtryCd)!'156'}" />
		</@field>
		<@field label="公司传真">
			<@input name="tmAppPrimApplicantInfo.corpFax" value="${(tmAppPrimApplicantInfo.corpFax)!}" valid={"regexp or pattern":"^(0\\d{2,3}\\d{7,9}(\\d{3,5})?$)|(1[3456789]\\d{9}$)","regexp-message":"请输入正确的公司传真"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="第一联系人信息:"> 	                  
	<@row>
		<@field label="联系人证件类型">
			<@dictSelect id="contactIdType1" dicttype="IdType" change="contactIdTypeChange(1)" name="tmAppContact[0].contactIdType" value="${(tmAppContact1.contactIdType)!}"/>
		</@field>
		<@field label="联系人证件号">
			<@input id="contactIdNo1" name="tmAppContact[0].contactIdNo" value="${(tmAppContact1.contactIdNo)!}"/>
		</@field>
		<@field label="联系人性别">
			<@dictSelect id="contactGender1" dicttype="Gender" readonly="true" name="tmAppContact[0].contactGender" value="${(tmAppContact1.contactGender)!}"/>
		</@field>
	</@row>
	<@row>
		<@field label="联系人生日">
			<@date id="contactBirthday1" settings={"maxDate":"%y-%M-%d"} name="tmAppContact[0].contactBirthday" value="${(tmAppContact1.contactBirthday?date)!}"/>
		</@field>
		<@field label="联系人公司名称">
			<@input name="tmAppContact[0].contactEmpName" value="${(tmAppContact1.contactEmpName)!}"/>
		</@field>
		<@field label="联系人公司职务">
			<@dictSelect dicttype="EmpPost"  name="tmAppContact[0].contactCorpPost" value="${(tmAppContact1.contactCorpPost)!}"/>
		</@field>
	</@row>
	<@row>
		<@field label="联系人公司电话">
			<@input name="tmAppContact[0].contactEmpPhone" value="${(tmAppContact1.contactEmpPhone)!}" valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的电话号码"}/>
		</@field>
		<@field label="联系人公司传真">
			<@input name="tmAppContact[0].contactCorpFax" value="${(tmAppContact1.contactCorpFax)!}" valid={"regexp or pattern":"^(0\\d{10,17}$)","regexp-message":"请输入正确的公司传真"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="其他联系人信息:">   	  
	<@row>
		<@field label="联系人证件类型">
			<@dictSelect id="contactIdType2" dicttype="IdType" change="contactIdTypeChange(2)" name="tmAppContact[1].contactIdType" value="${(tmAppContact2.contactIdType)!}"/>
		</@field>
		<@field label="联系人证件号">
			<@input id="contactIdNo2" name="tmAppContact[1].contactIdNo" value="${(tmAppContact2.contactIdNo)!}"/>
		</@field>
		<@field label="联系人公司名称">
			<@input name="tmAppContact[1].contactEmpName" value="${(tmAppContact2.contactEmpName)!}"/>
		</@field>
	</@row>
	<@row>
		<@field label="联系人公司职务">
			<@dictSelect dicttype="EmpPost"  name="tmAppContact[1].contactCorpPost" value="${(tmAppContact2.contactCorpPost)!}"/>
		</@field>
		<@field label="联系人公司电话">
			<@input name="tmAppContact[1].contactEmpPhone" value="${(tmAppContact2.contactEmpPhone)!}" valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的电话号码"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="自动还款授权及消费方式:">
	<@row>
		<@field label="约定还款银行名称">
			<@input name="tmAppPrimCardInfo.ddBankName" value="${(tmAppPrimCardInfo.ddBankName)!''}"/>
		</@field>
		<@field label="约定还款开户行号">
			<@input id="ddBankBranch" name="tmAppPrimCardInfo.ddBankBranch" value="${(tmAppPrimCardInfo.ddBankBranch)!''}"/>
		</@field>
	</@row>
</@fieldset>
