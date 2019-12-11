<#include "/layout.ftl">

	<@body>
		<@panel head="审批日汇总报表">
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
					<@button name="导出" click="exportApplyDailyStatisticData"  fa="file-excel-o"/>
				    </@toolbar>
		 		</@form>
			 <@table id="dataTable" url="applyDailyStatisticReportController/list" form_id="queryForm" load_auto="false" 
			 button_id="queryButton">
                <@th title="日期" field="date" render="true" >
					<@thDate value="{{row.date}}" datetype="date"/>
				</@th>
                <@th title="自动审批次数" field="applyTime" sortable="true" />
                <@th title="建议通过次数" field="sugSuccessTime" />
                <@th title="建议通过占比" field="sugSuccessTimePercent" />
                <@th title="建议拒绝次数" field="sugRejectTime" />
                <@th title="建议拒绝占比" field="sugRejectTimePercent" />
                <@th title="建议人工处理次数" field="sugPersonTime" />
                <@th title="建议人工处理占比" field="sugPersonTimePercent" />
                <#--<@th title="通过次数" field="successTime" />
                <@th title="通过占比" field="successTimePercent" />
                <@th title="拒绝次数" field="rejectTime" />
                <@th title="拒绝占比" field="rejectTimePercent" />
                <@th title="人工处理次数" field="personTime" />
                <@th title="人工处理占比" field="personTimePercent" />-->
            </@table>
			</@panelBody>
		</@panel>
		
		<script type="text/javascript">
			function exportApplyDailyStatisticData(){
				var startDate = $("#applySuccessStartDate").val();
				var endDate = $("#applySuccessEndDate").val();
				if(!startDate || !endDate){
					alert("请起止日期再导出！");
					return;
				}
				if(!checkkk()){
					return false;
				}
				
				window.location.href="${base}/applyDailyStatisticReportController/exportApplyDailyStatisticData?startDate="+startDate+"&endDate="+endDate;
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