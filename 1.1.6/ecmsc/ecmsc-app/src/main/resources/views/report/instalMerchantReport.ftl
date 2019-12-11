<#include "/layout.ftl">

	<@body>
		<@panel head="分期商户报表">
			<@panelBody>
				<@form id="queryForm" style="padding-top:10px;">
				<@row>
					<@field label="商户号">
				  		 <@select options=ecms_('tableMap','mccNo','') id="mccNo" name="query.mccNo" /> 
				  	</@field>
				  	<#-- 
					<@field label="起始日期">
						<@date id="instalMerchantStartDate" value="${.now?date}" name="query.instalMerchantStartDate"/>
					</@field>
					<@field label="截止日期">
						<@date id="instalMerchantEndDate" value="${.now?date}" name="query.instalMerchantEndDate"/>
					</@field>
					-->
				</@row>
					<@toolbar align="right">
					<@button id="queryButton" name="查询" fa="search"/>
					<@button  name="导出" click="exportInstalMerchantData"  fa="file-excel-o"/>
				    </@toolbar>
		 		</@form>
			 <@table id="dataTable" url="instalMerchantReportController/list" form_id="queryForm" button_id="queryButton" load_auto="false"  >
			    <@th title="商户号" field="merId" sortable="true"/>
			    <@th title="商户名称" field="merName"/>
			    <@th title="商户联系人" field="merContactor"/>
			    <@th title="商户电话" field="merPhone"/>
                <@th title="商户授信总额" field="merLmt"/>
                <@th title="已入账未出账单金额" field="postingLmt"/>
                <@th title="审批通过未入账累计金额" field="finishAuditLmt"/>
                <@th title="审批中累计金额" field="inAuditLmt"/>
                <@th title="商户可用额度" field="availableCredit"/>             
            </@table>
			</@panelBody>
		</@panel>
		
		<script type="text/javascript">
			function exportInstalMerchantData(){
			/*	var startDate = $("#instalMerchantStartDate").val();
				var endDate = $("#instalMerchantEndDate").val();
				if(!startDate || !endDate){
					alert("请起止日期再导出！");
					return;
				}
				if(!checkkk()){
					return false;
				} */
//				window.location.href="${base}/instalMerchantReportController/exportInstalMerchantData?startDate="+startDate+"&endDate="+endDate;
				var branch = $("#branch").val();
				window.location.href="${base}/instalMerchantReportController/exportInstalMerchantData?branch="+branch;
			}
			function checkkk(){
				var startDate = $("#supplementRemindStartDate").val().split("-");
				var startTime = new Date(startDate[0],startDate[1],startDate[2]);
				var _startTime = startTime.getTime();
			//	alert(_startTime);
				
				var endDate = $("#supplementRemindEndDate").val().split("-");
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