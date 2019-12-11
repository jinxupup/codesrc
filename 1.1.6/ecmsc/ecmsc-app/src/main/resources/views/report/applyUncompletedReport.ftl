<#include "/layout.ftl">
	<@body>
		<@panel head="审批未完成报表">
			<@panelBody>
		 		<@form id="queryForm" style="padding-top:10px;">
				    <@row>
				    <@field label="机构号">
						<#-- <@multipleSelect nullable="true" id="branch" name="query.branch" options=ecms_('tableMap','branchMap','')  showfilter="true" single="true" /> -->
						<@select id="branch" name="query.branch"  options={}/>
					</@field>
					<@field label="起始日期">
						<@date id="applyUncompletedStartDate" value="${.now?date}" name="query.applyUncompletedStartDate"/>
					</@field>
					<@field label="截止日期">
						<@date id="applyUncompletedEndDate" value="${.now?date}" name="query.applyUncompletedEndDate"/>
					</@field>
					</@row>
					<@row>
					<@field label="卡产品">
						<@select id="productCd" change="productCdChange" name="query.productCd" value="" options=ecms_('tableMap','hasProductBranch','')/>
					</@field> 
					<@field label="申请类型">
						<@dictSelect id="appType" dicttype="AppType" showcode='true' name="query.appType" />
					</@field>
        	        <@field label="申请渠道">
						<@dictSelect id="appSource" dicttype="AppSource" showcode='true' name="query.appSource" />
					</@field>
				    </@row>
					<@toolbar align="right">
					<@button id="queryButton" name="查询" fa="search"/>
					<@button  name="导出" click="exportApplyUncompletedData"  fa="file-excel-o"/>
				    </@toolbar>
		 		</@form>
			 <@table id="dataTable" url="applyUncompletedReportController/list" form_id="queryForm" button_id="queryButton"  before="checkkk()" load_auto="false">
                <@th title="申请编号" field="appNo" sortable="true"/>
                <@th title="证件类型" field="idType" render="true" sortable="true">
                	 <@thDictName dicttype="IdType"  value="{{row.idType}}" showcode="true"/>
                </@th> 
                <@th title="证件号码" field="idNo" />
                <@th title="姓名" field="name" />
                <@th title="申请类型" field="appType" render="true" sortable="true">
                	 <@thDictName dicttype="AppType"  value="{{row.appType}}" showcode="true"/>
                </@th>
                <@th title="卡产品" field="productCd" render="true">
					<@thGetName options = ecms_('tableMap','hasProductBranch','' ) value = "row.productCd" showcode="true"/>
				</@th>
                <@th title="受理网点" field="owningBranch" render="true">
					<@thGetName options = ecms_('tableMap','branchMap','') value="row.owningBranch" showcode="true" />
				</@th> 
                <@th title="申请渠道" field="appSource" render="true" sortable="true">
                	<@thDictName dicttype="AppSource"  value="{{row.appSource}}" showcode="true"/>
                </@th>
                <@th title="上一任务人" field="lastOpUser" />
                <@th title="任务所属人" field="owner" />
                <@th title="任务分配人" field="assignee" />
                <@th title="任务节点" field="taskDefKey" />
                <@th title="任务名称" field="taskName" />       
            </@table>
			</@panelBody>
		</@panel>
<link href="${base}/assets/plugins/select2/css/select2.css" rel="stylesheet"/>
<script src="${base}/assets/plugins/select2/js/select2.js"></script>
		<script type="text/javascript">
			function exportApplyUncompletedData(){
				var startDate = $("#applyUncompletedStartDate").val();
				var endDate = $("#applyUncompletedEndDate").val();
				if(!startDate || !endDate){
					alert("请起止日期再导出！");
					return;
				}
				if(!checkkk()){
					return false;
				}
				var branch = $("#branch").val();
				var productCd = $("#productCd").val();
				var appType = $("#appType").val();
				var appSource = $("#appSource").val();
				window.location.href="${base}/applyUncompletedReportController/exportApplyUncompletedData?startDate="+startDate+"&endDate="+endDate
									+"&branch="+branch+"&productCd="+productCd+"&appType="+appType+"&appSource="+appSource;
			}
			function checkkk(){
				var startDate = $("#applyUncompletedStartDate").val().split("-");
				var startTime = new Date(startDate[0],startDate[1],startDate[2]);
				var _startTime = startTime.getTime();
			//	alert(_startTime);
				
				var endDate = $("#applyUncompletedEndDate").val().split("-");
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
			
	$("#branch").select2({
		 minimumInputLength: 2,
		 language: "zh-CN",
		 ajax: { 
	        url: "${base}/ecms_/getBranchesSelect",
	        dataType: 'json',
	        quietMillis: 250,
	        data: function (query, page) {
	            return {
	                "query": query.term,
	                "isAll": false
	            };
	        },
	        processResults: function (data) {
	            return { results: 
	            	$.map(data.obj, function (label,value) {
            			return {
                			id: value,
                			text: value + "-" + label
            			}
            		})
       			};
	        },
	        cache: true
	    }
	});
		</script>
	</@body>