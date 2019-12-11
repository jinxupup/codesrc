<#include "/layout.ftl">
	<@body>
		<@panel head="标准大额分期未放款明细报表">
			<@panelBody>
				<@form id="queryForm" style="padding-top:10px;">
				    <@row>
				   <@field label="活动号">
  			<@selectLink nullable="true" change="judgeTimeAndStatus(this)" append="true" id="instalmentCreditActivityNo" value="${(tmAppInstalLoan.instalmentCreditActivityNo)!}" link_id="productCd" url_parent_key="productCd" url="applyInput/getActicity" keyfield="id" valuefield="show" name="tmAppInstalLoan.instalmentCreditActivityNo" />
  		</@field>
  		<@field label="商户号">
  			<@selectLink nullable="true" append="true" id="mccNo" link_id="instalmentCreditActivityNo" value="${(tmAppInstalLoan.mccNo)!}" url_parent_key="instalmentCreditActivityNo" url="applyInput/businessNo" keyfield="id" valuefield="show" name="tmAppInstalLoan.mccNo" />
  		</@field>
  		<@field label="分期期数" >
  			<@selectLink nullable="true" append="true" id="loanInitTerm" link_id="instalmentCreditActivityNo" value="${(tmAppInstalLoan.loanInitTerm)!}" url_parent_key="instalmentCreditActivityNo" url="applyInput/getTerms" keyfield="show" valuefield="id" name="tmAppInstalLoan.loanInitTerm" />
  		</@field>
  		<@field label="分期手续费收取方式">
  			<@dictSelect dicttype="LoanFeeTetchMethod" id="loanFeeMethod" name="tmAppInstalLoan.loanFeeMethod" nullable="false" value="${(tmAppInstalLoan.loanFeeMethod)!}" />
  		</@field>
  		<@field label="大额分期手续费计算方式">
  			<@dictSelect dicttype="LoanFeeCalcMethod" change="changeMethod" id="loanFeeCalcMethod" name="tmAppInstalLoan.loanFeeCalcMethod" nullable="false" value="${(tmAppInstalLoan.loanFeeCalcMethod)!}" />
  		</@field>
  		<@field label="手续费费率" id="appFeeRateA">
  			<@input id="appFeeRate" name="tmAppInstalLoan.appFeeRate" value="${(tmAppInstalLoan.appFeeRate?c)!}" valid={"regexp or pattern":"^(1|1\\.[0]*|0?\\.(?!0+$)[\\d]+)$","regexp-message":"请输入0-1的数字"}/>
  		</@field>
  		<@field label="手续费固定金额" id="appFeeAmtA" hidden="true">
  			<@input id="appFeeAmt" name="tmAppInstalLoan.appFeeAmt" value="${(tmAppInstalLoan.appFeeAmt)!}" valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写金额"}/>
  		</@field>
  		<@field label="分期借款金额" >
  			<@input id="cashAmt" name="tmAppInstalLoan.cashAmt" value="${(tmAppInstalLoan.cashAmt)!}" valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写借款金额"}/>
  		</@field>
					<@field label="查询起始日期">
						<@date id="instalWithoutPaymentStartDate" value="${.now?date}" name="query.instalWithoutPaymentStartDate"/>
					</@field>
					<@field label="查询截止日期">
						<@date id="instalWithoutPaymentEndDate" value="${.now?date}" name="query.instalWithoutPaymentEndDate"/>
					</@field>
				    </@row>
					<@toolbar align="right">
					<@button id="queryButton" name="查询" fa="search"/>
					<@button  name="导出" click="exportInstalWithoutPaymentData"  fa="file-excel-o"/>
				    </@toolbar>
		 		</@form>
			 <@table id="dataTable" url="instalWithoutPaymentReportController/list" form_id="queryForm" button_id="queryButton" page_size="10" condensed="true">
                <@th title="机构号" field="org" sortable="true"/>
                <@th title="申请编号" field="appNo" sortable="true"/>
                <@th title="分期商户编号" field="mccNo" />
                <@th title="分期借款金额" field="cashAmt" />
                <@th title="分期总期数" field="loanInitTerm" />
                <@th title="分期产品编号" field="instalmentCreditRpoductNo" />
                <@th title="分期活动编号" field="instalmentCreditActivityNo" />
                <@th title="放款时间" field="lendingTime" />
                <@th title="放款次数" field="lendingTimes" />
                <@th title="卡号" field="cardNo" />
                <@th title="币种" field="currCd" />
                <@th title="营销人员姓名" field="marketerName" />
                <@th title="营销人员编号" field="marketerId" />
                <@th title="营销人员所属分行号" field="marketerBranchId" />                
            </@table>
			</@panelBody>
		</@panel>
		
		<script type="text/javascript">
			function exportInstalWithoutPaymentData(){
				var startDate = $("#instalWithoutPaymentStartDate").val();
				var endDate = $("#instalWithoutPaymentEndDate").val();
				if(!startDate || !endDate){
					alert("请起止日期再导出！");
					return;
				}
				window.location.href="${base}/instalWithoutPaymentReportController/exportInstalWithoutPaymentData?startDate="+startDate+"&endDate="+endDate;
			}
		</script>
	</@body>