<#include "/layout.ftl">

	<@body>
		<@panel head="审批占比报表">
			<@panelBody>
		 		<@form id="queryForm" style="padding-top:10px;">
				    <@row>
					<@field label="起始日期">
						<@date id="applySuccessStartDate" value="" name="query.applySuccessStartDate"/>
					</@field>
					<@field label="截止日期">
						<@date id="applySuccessEndDate" value="" name="query.applySuccessEndDate"/>
					</@field>
					</@row>
					<@toolbar align="right">
					<@button id="queryButton" name="查询" fa="search"/>
					<@button name="导出" click="exportApplyProportionData"  fa="file-excel-o"/>
				    </@toolbar>
		 		</@form>
			 <@table id="dataTable" url="applyProportionReportController/list" form_id="queryForm" load_auto="false" 
			 button_id="queryButton">
			 	<@th title="日期" field="date" render="true" >
					<@thDate value="{{row.date}}" datetype="date"/>
				</@th>
                <@th title="建议通过次数" field="sugSuccessTime" />
                <@th title="实际通过次数" field="realSuccessTime" />
                <@th title="通过占比" field="successTimePercent" />
                <@th title="建议拒绝次数" field="sugRejectTime" />
                <@th title="实际拒绝次数" field="realRejectTime" />
                <@th title="拒绝占比" field="rejectTimePercent" />
            </@table>
			</@panelBody>
		</@panel>
		
		<script type="text/javascript">
			function exportApplyProportionData(){
				var startDate = $("#applySuccessStartDate").val();
				var endDate = $("#applySuccessEndDate").val();
				if(!startDate || !endDate){
					alert("请起止日期再导出！");
					return;
				}
				if(!checkkk()){
					return false;
				}
				window.location.href="${base}/applyProportionReportController/exportApplyProportionData?startDate="+startDate+"&endDate="+endDate;
			}
			
			function checkkk(){
				var startDate = $("#applySuccessStartDate").val().split("-");
				var startTime = new Date(startDate[0],startDate[1],startDate[2]);
				var _startTime = startTime.getTime();
				
				var endDate = $("#applySuccessEndDate").val().split("-");
				var endTime = new Date(endDate[0],endDate[1],endDate[2]);
				var _endTime = endTime.getTime();
				
				var now = new Date();
				var mon = now.getMonth() + 1;
				var day = now.getDate();
				var deadLine = now.getFullYear()-1 + "-" + (mon<10?"0"+mon:mon) + "-" +(day<10?"0"+day:day);

				deadLine = deadLine.split("-");	
				var deadTime = new Date(deadLine[0],deadLine[1],deadLine[2]);
				var _deadTime = deadTime.getTime();
				
				if(_deadTime<=_startTime && _deadTime<=_endTime){
					return true;
				}else{
					alert("查询/导出时间限制为一年之内,请重新选择");
					return false;
				}
			}
		</script>
	</@body>