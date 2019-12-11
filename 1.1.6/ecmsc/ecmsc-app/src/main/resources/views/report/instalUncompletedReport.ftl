<#include "/layout.ftl">

	<@body>
		<@panel head="审批未完成汇总报表">
			<@panelBody>
				<@form id="queryForm" style="padding-top:10px;">
				<@row>
				    <@field label="网点">
						<@multipleSelect id="branch" name="query.branch" options=ecms_('tableMap','branchMap','allBranch')  showfilter="true" single="true" />
					</@field>
					<@field label="卡产品">
						<@select id="productCd" change="productCdChange" name="query.productCd" value="" options=ecms_('tableMap','hasProductBranch','')/>
					</@field> 
				</@row>
				<@row>
					<@field label="起始日期">
						<@date id="instalUncompletedStartDate" value="${.now?date}" name="query.instalUncompletedStartDate" />
					</@field>
					<@field label="截止日期">
						<@date id="instalUncompletedEndDate" value="${.now?date}" name="query.instalUncompletedEndDate"/>
					</@field>
				</@row>
					<@toolbar align="right">
					<@button id="queryButton" name="查询" fa="search"/>
					<@button  name="导出" click="exportInstalUncompletedData"  fa="file-excel-o"/>
				    </@toolbar>
		 		</@form>
			 <@table id="dataTable" url="instalUncompletedReportController/list" form_id="queryForm" button_id="queryButton" load_auto="false" condensed="true" before="checkkk()">
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
			function exportInstalUncompletedData(){
				var startDate = $("#instalUncompletedStartDate").val();
				var endDate = $("#instalUncompletedEndDate").val();
				if(!startDate || !endDate){
					alert("请起止日期再导出！");
					return;
				}
				if(!checkkk()){
					return false;
				}
				window.location.href="${base}/instalUncompletedReportController/exportInstalUncompletedData?startDate="+startDate+"&endDate="+endDate;
			}
			function checkkk(){
				var startDate = $("#instalUncompletedStartDate").val().split("-");
				var startTime = new Date(startDate[0],startDate[1],startDate[2]);
				var _startTime = startTime.getTime();
			//	alert(_startTime);
				
				var endDate = $("#instalUncompletedEndDate").val().split("-");
				var endTime = new Date(endDate[0],endDate[1],endDate[2]);
				var _endTime = endTime.getTime();
				
				var now = new Date();
				var mon = now.getMonth() + 1;
				var day = now.getDate();
				var deadLine = now.getFullYear()-1 + "-" + (mon<10?"0"+mon:mon) + "-" +(day<10?"0"+day:day);
			//	alert(deadLine);

				deadLine = deadLine.split("-");	
				var deadTime = new Date(deadLine[0],deadLine[1],deadLine[2]);
				var _deadTime = deadTime.getTime();
			//	alert(_deadTime);
				
				if(_deadTime<=_startTime && _deadTime<=_endTime){
					return true;
				}else{
					alert("查询/导出时间限制为一年之内,请重新选择");
					return false;
				}
			}
		</script>
	</@body>