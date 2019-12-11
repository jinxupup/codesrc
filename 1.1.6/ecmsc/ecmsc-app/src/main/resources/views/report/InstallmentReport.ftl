<#include "/layout.ftl">
	<@body>
		<@panel head="标准大额分期放款状态报表">
			<@panelBody>
				<@form id="queryForm" style="padding-top:10px;" >
				    <@row>
				  		<@field label="商户号">
				  		 	<@select options=ecms_('tableMap','mccNo','') id="mccNo" name="query.mccNo" /> 
				  		</@field>
				  		<@field label="卡产品代码">
							<@select id="productCd" name="query.productCd" options=ecms_('tableMap','productForStatus','A,B,C')/>
						</@field>
						<@field label="分期注册状态">
						    <@dictSelect id="loanRegStatus" nullable="true" dicttype="ApsLoanRegStatus" showcode='true' name="query.loanRegStatus" />
						</@field>
				    </@row>
				    <@row>
						<@field label="所属分支机构">
			                <#-- <@multipleSelect nullable="true" id="owningBranch" name="query.owningBranch" options=ecms_('tableMap','branchMap','') /> -->
			                <@select id="owningBranch" name="query.owningBranch"  options={}/>
			            </@field>
						<@field label="起始日期">
							<@date id="beginDate" value="${.now?date}" name="query.beginDate"/>
						</@field>
						<@field label="截止日期">
							<@date id="endDate" value="${.now?date}" name="query.endDate"/>
						</@field>
					</@row>
					<@toolbar>
						<@button id="queryButton" name="查询" fa="search" />
						<@button  name="导出" click="exportFailureInstallmentData"  fa="file-excel-o"/>
				    </@toolbar>
		 		</@form>
			 <@table id="dataTable" url="installmentReportController/list" form_id="queryForm" button_id="queryButton"  before="checkkk" load_auto="false">
      <#--       <@th title="机构号" field="org" sortable="true"/> --> 
                <@th title="姓名" field="name" />                
                <@th title="证件类型" field="idType" />                
                <@th title="证件号" field="idNo" />                
				<@th title="卡产品" field="productCd" render="true">
					<@thGetName options = ecms_('tableMap','hasProductBranch','' ) value = "row.productCd" showcode="true"/>
				</@th>
                <@th title="申请编号" field="appNo" sortable="true"/>
                <@th title="分期商户编号" field="mccNo" render="true">
					<@thGetName options = ecms_('tableMap','mccNo','' ) value = "row.mccNo" showcode="true"/>
                </@th>
                <@th title="分期借款金额" field="cashAmt" />
                <@th title="分期总期数" field="loanInitTerm" />
        <#--    <@th title="分期产品编号" field="instalmentCreditRpoductNo" />--> 
                <@th title="分期活动编号" field="instalmentCreditActivityNo" render="true">
					<@thGetName options = ecms_('tableMap','instalmentCreditActivityNo','' ) value = "row.instalmentCreditActivityNo" showcode="true"/>
                </@th>
                <@th title="分期注册状态" field="loanRegStatus" render="true">
					<@thDictName dicttype="ApsLoanRegStatus" value = "row.loanRegStatus" showcode="true"/>
                </@th>
                <@th title="放款时间" field="lendingTime" render="true" >
					<@thDate value="{{row.lendingTime}}" datetype="datetime"/>
				</@th>
                <@th title="放款次数" field="lendingTimes" />
         <#--   <@th title="卡号" field="cardNo" />--> 
         <#--   <@th title="币种" field="currCd" />--> 
                <@th title="营销人员姓名" field="marketerName" />
                <@th title="营销人员编号" field="marketerId" />
                <@th title="营销人员所属分行号" field="marketerBranchId" render="true">
					<@thGetName options = ecms_('tableMap','branchMap','' ) value = "row.marketerBranchId" showcode="true"/>
                </@th>
                <@th title="审批日期" field="updateDate" render="true" >
					<@thDate value="{{row.updateDate}}" datetype="datetime"/>
				</@th>                
            </@table>
			</@panelBody>
		</@panel>
<link href="${base}/assets/plugins/select2/css/select2.css" rel="stylesheet"/>
<script src="${base}/assets/plugins/select2/js/select2.js"></script>
		<script type="text/javascript">
			function exportFailureInstallmentData(){
				var startDate = $("#beginDate").val();
				var endDate = $("#endDate").val();
				var loanRegStatus = $("#loanRegStatus").val();
				var mccNo = $("#mccNo").val();
				var productCd = $("#productCd").val();
				var owningBranch = $("#owningBranch").val();
				if(!startDate || !endDate){
					alert("请选择起止日期再导出！");
					return;
				}
				if(!checkkk()){
					return false;
				}
				window.location.href="${base}/installmentReportController/exportInstallmentData?loanRegStatus="+
										loanRegStatus+"&startDate="+startDate+"&endDate="+endDate+"&mccNo="+mccNo+"&productCd="+productCd+"&owningBranch="+owningBranch;
			}
			function checkkk(){
				var startDate = $("#beginDate").val().split("-");
				var startTime = new Date(startDate[0],startDate[1],startDate[2]);
				var _startTime = startTime.getTime();
			//	alert(_startTime);
				
				var endDate = $("#endDate").val().split("-");
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
			
	$("#owningBranch").select2({
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