<#include "/layout.ftl"/>

<@body>
<@gotop/>
		<@panelBody>
			<@form id="queryForm">
				<@row>
					<@field label="起始日期">
						<@date id="startDate" name="query.startDate" value="${(applyTaskCountDto.startDate)!}" />
					</@field>
					<@field label="截止日期">
						<@date id="endDate" name="query.endDate" value="${(applyTaskCountDto.endDate)!}" />
					</@field>
				</@row>
				<@row>
					<@field label="操作人员ID">
						<#if hasTaskAuth?? && hasTaskAuth=="true" >
							<@multipleSelect id="operatorId" name="query.operatorId" value="${(applyTaskCountDto.operatorId)!}"
								options=cas_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true"/>
						<#else>
							<@input name="query.operatorId" value="${(applyTaskCountDto.operatorId)!}" readonly="true"/>
						</#if>
							
					</@field>
					<@field label="任务名称">
                    	<!-- <@select id="proName" name="query.proName" options=ecms_('tableMap','taskNameForCount','') 
                    			nullable="true" showcode="false" value="${(applyTaskCountDto.proName)!}" /> -->
                    	<@dictSelect id="proName" dicttype="TaskNameForCount" name="query.proName" showcode="false" value="${(applyTaskCountDto.proName)!}"/>
                    </@field>
					&nbsp;&nbsp;
					<@button id="queryButton" name="查询"></@button>
					<#-- <a href="${base}/taskCount/exportExcel" name="导出工作统计数据" target="_blank" >导出工作统计数据</a> -->
					&nbsp;&nbsp;
					<@button id="resultButton" name="导出汇总" click="queryResult" ></@button>
				</@row>
			</@form>
			</br>
			<@table id="taskCount" url="taskCount/list" form_id="queryForm" button_id="queryButton" page_size="10">
			 	<@th title="起始日期" field="startDate" ></@th>
			  	<@th title="截止日期" field="endDate" ></@th>
			  	<@th title="操作人员ID" field="operatorId"></@th>
			  	<@th title="任务名称" field="proName"></@th>
			  	<@th title="数量" field="nums"></@th>
			  	<@th title="操作" render="true">
			  	<#--	<@href href="taskCount/taskDetails?applyTaskCountDto.startDate={{row.startDate}}&applyTaskCountDto.endDate={{row.endDate}}&applyTaskCountDto.operatorId={{row.operatorId}}&proName={{row.proName}}" name="查看明细" /> -->
			  		<@button id="detail" name="查看明细" click="detail('{{row.startDate}}','{{row.endDate}}','{{row.operatorId}}','{{row.proName}}')"/>
			  		<@button id="exportDetailBut" name="导出明细" click="exportDetail('{{row.startDate}}','{{row.endDate}}','{{row.operatorId}}','{{row.proName}}')" ></@button>
			  	</@th>
			</@table>
		</@panelBody>
</@body>
<script type="text/javascript">
	var queryResult = function(){
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		var operatorId = $('#operatorId').val();
		var proName = encodeURI(encodeURI($('#proName').val()));
		window.location.href="${base}/taskCount/exportExcel?startDate="+startDate+"&endDate="+endDate+"&operatorId="+operatorId+"&proName="+proName+"";
																
	};
	var exportDetail = function(startDate,endDate,operatorId,proName){
		proName = encodeURI(proName);
		window.location.href="${base}/taskCount/exportDetailExcel?query.startDate="+startDate
			+"&query.endDate="+endDate+"&query.operatorId="+operatorId+"&query.proName="+proName+"&pageSize=2000";
																
	};
	
	<#--点击查看详情-->
	var detail = function(startDate,endDate,operatorId,proName){
		proName = encodeURI(proName);
		a = dialog({
                title: '工作统计详情页面',
                width:1400,
                height:550,
                url:'${base}/taskCount/taskDetails?applyTaskCountDto.startDate='+startDate+'&applyTaskCountDto.endDate='+endDate+'&applyTaskCountDto.operatorId='+operatorId+'&proName='+proName,
                oniframeload:function(){},
                button:[
		        {
		            value: '返回',
		            callback: function () {
		            	this.close();
		                return false;
		            },
		            autofocus: true
		        }
                ]
            });
            a.showModal(); 
	};
</script>