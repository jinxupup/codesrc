<@fieldset legend="个人证明信息:">
	<@row>
		<@field label="主卡签名">
			<@dictSelect dicttype="AnnexApprovalType" name="tmAppPrimAnnexEvi.nameStatus" value="${(tmAppPrimAnnexEvi.nameStatus)!}"/>
		</@field>
		<@field label="身份证明文件">
			<@dictSelect dicttype="AnnexApprovalType" name="tmAppPrimAnnexEvi.idStatus" value="${(tmAppPrimAnnexEvi.idStatus)!}"/>
		</@field>
		<@field label="工作证明文件">
			<@dictSelect dicttype="AnnexApprovalType" name="tmAppPrimAnnexEvi.jobStatus" value="${(tmAppPrimAnnexEvi.jobStatus)!}"/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="收入证明信息:">
	<@row>
		<@field label="代发状况">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.payrollStatus" value="${(tmAppPrimAnnexEvi.payrollStatus)!}"/>
		</@field>
		<@field label="月代发金额">
			<@input name="tmAppPrimAnnexEvi.payrollAmount" value="${(tmAppPrimAnnexEvi.payrollAmount)!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入正确的月代发金额"}/>
		</@field>
		<@field label="个人年收入">
			<@input name="tmAppPrimAnnexEvi.incomeEvi" value="${(tmAppPrimAnnexEvi.incomeEvi)!}" 
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入正确的个人年收入"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="个人养老保险证明信息:">
	<@row>
		<@field label="养老保险">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.insureState" value="${(tmAppPrimAnnexEvi.insureState)!}"/>
		</@field>
		<@field label="最后缴交月份">
			<@date name="tmAppPrimAnnexEvi.insureDate" value="${(tmAppPrimAnnexEvi.insureDate?date)!}" 
				valid={"regexp or pattern":"^((0$)|([1-9](\\d{1,2})?$))","regexp-message":"请输入正确的最后缴交月份"}/>
		</@field>
		<@field label="缴交基数">
			<@input name="tmAppPrimAnnexEvi.insureBase" value="${(tmAppPrimAnnexEvi.insureBase?string('#.##'))!}" 
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入有效的缴交基数"}/>
		</@field>
	</@row>
	<@row>
		<@field label="缴交月数">
			<@input name="tmAppPrimAnnexEvi.insureMonthCount" value="${(tmAppPrimAnnexEvi.insureMonthCount)!}" 
				valid={"regexp or pattern":"^((0$)|([1-9](\\d{1,2})?$))","regexp-message":"请输入正确的缴交月数"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="个人住房公积金证明信息:">
	<@row>
		<@field label="公积金">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.fundDepositeState" value="${(tmAppPrimAnnexEvi.fundDepositeState)!}"/>
		</@field>
		<@field label="最后缴交月份">
			<@date name="tmAppPrimAnnexEvi.fundDepositeDate" value="${(tmAppPrimAnnexEvi.fundDepositeDate?date)!}" 
				valid={"regexp or pattern":"^((0$)|([1-9](\\d{1,2})?$))","regexp-message":"请输入正确的最后缴交月份"}/>
		</@field>
		<@field label="缴交基数">
			<@input name="tmAppPrimAnnexEvi.fundDepositeBase" value="${(tmAppPrimAnnexEvi.fundDepositeBase?string('#.##'))!}" 
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入有效的缴交基数"}/>
		</@field>
	</@row>
	<@row>
		<@field label="缴交月数">
			<@input name="tmAppPrimAnnexEvi.fundDepositeMCount" value="${(tmAppPrimAnnexEvi.fundDepositeMCount)!}" 
				valid={"regexp or pattern":"^((0$)|([1-9](\\d{1,2})?$))","regexp-message":"请输入正确的缴交月数"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="我行存款证明信息:">
	<@row>
		<@field label="我行存款">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.depositStatus" value="${(tmAppPrimAnnexEvi.depositStatus)!}"/>
		</@field>
		<@field label="存款类型">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.depositType" value="${(tmAppPrimAnnexEvi.depositType)!}"/>
		</@field>
		<@field label="开户日期">
			<@date settings={"maxDate":"%y-%M-%d"} name="tmAppPrimAnnexEvi.depositOpenTime" value="${(tmAppPrimAnnexEvi.depositOpenTime?date)!}"/>
		</@field>
	<@row>
	</@row>	
		<@field label="年日均存款">
			<@input name="tmAppPrimAnnexEvi.depositAvgAmt" value="${(tmAppPrimAnnexEvi.depositAvgAmt?string('#.##'))!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入正确的年日均存款"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="我行理财证明信息:">
	<@row>
		<@field label="我行理财">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.financeStatus" value="${(tmAppPrimAnnexEvi.financeStatus)!}"/>
		</@field>
		<@field label="产品期限">
			<@date settings={"minDate":"%y-%M-%d"} name="tmAppPrimAnnexEvi.financeTime" value="${(tmAppPrimAnnexEvi.financeTime?date)!}"/>
		</@field>
		<@field label="理财金额">
			<@input name="tmAppPrimAnnexEvi.financeAmt" value="${(tmAppPrimAnnexEvi.financeAmt?string('#.##'))!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入正确的理财金额"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="我行贷款证明信息:">
	<@row>
		<@field label="我行贷款">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.loanStatus" value="${(tmAppPrimAnnexEvi.loanStatus)!}"/>
		</@field>
		<@field label="贷款类型">
			<@dictSelect dicttype="LoanType"  name="tmAppPrimAnnexEvi.loanType" value="${(tmAppPrimAnnexEvi.loanType)!}"/>
		</@field>
		<@field label="贷款金额">
			<@input name="tmAppPrimAnnexEvi.loanAmount" value="${(tmAppPrimAnnexEvi.loanAmount?string('#.##'))!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写贷款金额"}/>
		</@field>
	<@row>
	</@row>	
		<@field label="贷款期限">
			<@date settings={"minDate":"%y-%M-%d"} name="tmAppPrimAnnexEvi.loanEndTime" value="${(tmAppPrimAnnexEvi.loanEndTime?date)!}"/>
		</@field>
		<@field label="月还款额">
			<@input name="tmAppPrimAnnexEvi.loanPayAmt" value="${(tmAppPrimAnnexEvi.loanPayAmt?string('#.##'))!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写月还款额"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="个人房产证明信息:">
	<@row>
		<@field label="房产信息">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.estateStatus" value="${(tmAppPrimAnnexEvi.estateStatus)!}"/>
		</@field>
		<@field label="房产类型">
			<@dictSelect dicttype="EstateType" name="tmAppPrimAnnexEvi.estateType" value="${(tmAppPrimAnnexEvi.estateType)!}"/>
		</@field>
		<@field label="购房价格">
			<@input name="tmAppPrimAnnexEvi.estateValue" value="${(tmAppPrimAnnexEvi.estateValue?string('#.##'))!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写购房价格"}/>
		</@field>
	<@row>
	</@row>
		<@field label="贷款金额">
			<@input name="tmAppPrimAnnexEvi.loan" value="${(tmAppPrimAnnexEvi.loan?string('#.##'))!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写贷款金额"}/>
		</@field>
		<@field label="贷款期限">
			<@input name="tmAppPrimAnnexEvi.estateLoanTime" value="${(tmAppPrimAnnexEvi.estateLoanTime)!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,2})?))$)","regexp-message":"请正确填写贷款期限"}/>
		</@field>
		<@field label="月还款额">
			<@input name="tmAppPrimAnnexEvi.estatePayAmt" value="${(tmAppPrimAnnexEvi.estatePayAmt?string('#.##'))!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写月还款额"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="企业法人证明信息:">
	<@row>
		<@field label="企业法人信息">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.isLegalPerson" value="${(tmAppPrimAnnexEvi.isLegalPerson)!}"/>
		</@field>
		<@field label="成立日期">
			<@date settings={"maxDate":"%y-%M-%d"} name="tmAppPrimAnnexEvi.openTime" value="${(tmAppPrimAnnexEvi.openTime?date)!}"/>
		</@field>
		<@field label="注册资本">
			<@input name="tmAppPrimAnnexEvi.registerFund" value="${(tmAppPrimAnnexEvi.registerFund?string('#.##'))!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入正确的注册资本"}/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="个体工商户证明信息:">
	<@row>
		<@field label="个体工商户">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.isIndBusiness" value="${(tmAppPrimAnnexEvi.isIndBusiness)!}"/>
		</@field>
		<@field label="发照日期">
			<@date settings={"maxDate":"%y-%M-%d"} name="tmAppPrimAnnexEvi.registerTime" value="${(tmAppPrimAnnexEvi.registerTime?date)!}"/>
		</@field>
		<@field label="执照有效期">
			<@date name="tmAppPrimAnnexEvi.registerEndTime" value="${(tmAppPrimAnnexEvi.registerEndTime?date)!}"/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="个人车产证明信息:">
	<@row>
		<@field label="车产信息">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.carState" value="${(tmAppPrimAnnexEvi.carState)!}"/>
		</@field>
		<@field label="车辆价值">
			<@input name="tmAppPrimAnnexEvi.carValue" value="${(tmAppPrimAnnexEvi.carValue?string('#.##'))!}"
				valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入正确的车辆价值"}/>
		</@field>
		<@field label="注册日期">
			<@date settings={"maxDate":"%y-%M-%d"} name="tmAppPrimAnnexEvi.carBuyDate" value="${(tmAppPrimAnnexEvi.carBuyDate?date)!}"/>
		</@field>
	</@row>
</@fieldset>
<@fieldset legend="居住证明证明信息:">
	<@row>
		<@field label="居住证明">
			<@dictSelect dicttype="Indicator" name="tmAppPrimAnnexEvi.liveStatus" value="${(tmAppPrimAnnexEvi.liveStatus)!}"/>
		</@field>
		<@field label="地址是否一致">
			<@dictSelect dicttype="Indicator"  name="tmAppPrimAnnexEvi.liveAddrStatus" value="${(tmAppPrimAnnexEvi.liveAddrStatus)!}"/>
		</@field>
	</@row>
</@fieldset>	
