<#include "/layout.ftl">

	<@body>
		<@panel head="补件提醒报表">
			<@panelBody>
		 		<@form id="queryForm" style="padding-top:10px;">
				    <@row>
				    <@field label="机构网点">
						<#-- <@multipleSelect id="branch" name="query.branch" options=ecms_('tableMap','branchMap','') nullable='true'  single="true" /> -->
						<@select id="branch" name="query.branch"  options={}/>
					</@field>
					<#-- 
					<@field label="查询起始日期">
						<@date id="supplementRemindStartDate" value="${.now?date}" name="query.supplementRemindStartDate" />
					</@field>
					<@field label="查询截止日期">
						<@date id="supplementRemindEndDate" value="${.now?date}" name="query.supplementRemindEndDate"/>
					</@field>
					-->
				    </@row>
					<@toolbar align="right">
					<@button id="queryButton" name="查询" fa="search"/>
					<@button  name="导出" click="exportSupplementRemindData"  fa="file-excel-o"/>
				    </@toolbar>
		 		</@form>
		 		</br>
			 <@table id="dataTable" url="supplementRemindReportController/list" form_id="queryForm" button_id="queryButton" load_auto="false" >            
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
                <@th title="移动电话" field="cellPhone"/>
                <@th title="补件类型" field="pbType" render="true">
                	<@thDictName dicttype="ApplyPatchBoltType"  value="{{row.pbType}}" showcode="true"/>
                </@th>
                <@th title="推广人编号" field="spreaderNum" />
                <@th title="推广人姓名" field="spreaderName" />
                <@th title="推广人所属机构" field="spreaderBank" />
                <@th title="补件开始时间" field="pbStartDate" render="true">
                    <@thDate value="{{row.pbStartDate}}" datetype="date"/>
                </@th>
                <@th title="补件结束时间" field="pbTimeoutDate" render="true">
                    <@thDate value="{{row.pbTimeoutDate}}" datetype="date"/>
                </@th>                        
            </@table>
			</@panelBody>
		</@panel>
<link href="${base}/assets/plugins/select2/css/select2.css" rel="stylesheet"/>
<script src="${base}/assets/plugins/select2/js/select2.js"></script>
		<script type="text/javascript">
			function exportSupplementRemindData(){
			/*	var startDate = $("#supplementRemindStartDate").val();
				var endDate = $("#supplementRemindEndDate").val();
				if(!startDate || !endDate){
					alert("请起止日期再导出！");
					return;
				}
				if(!checkkk()){
					return false;
				} */
//				window.location.href="${base}/supplementRemindReportController/exportSupplementRemindData?startDate="+startDate+"&endDate="+endDate;
				var branch = $("#branch").val();
				window.location.href="${base}/supplementRemindReportController/exportSupplementRemindData?branch="+branch;
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