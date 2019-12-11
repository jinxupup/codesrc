<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panelBody>
		<@form id="queryForm" cols="4">
				<@row>
					<@field label="申请件编号">
						<@input id="appNo" name="query.appNo"  value="${(query.appNo)!}" />
					</@field>
					<@field label="姓名">
						<@input id="name" name="query.name"  value="${(query.name)!}" />
					</@field>
					<@field label="证件号码">
						<@input id="idNo" name="query.idNo" value="${(query.idNo)!}" />
					</@field>
					<@field label="移动电话">
						<@input id="cellPhone" name="query.cellPhone" value="${(query.cellPhone)!}"/>
					</@field>
				</@row>
				<@row>
					<@field label="公司电话">
						<@input id="empPhone" name="query.empPhone" value="${(query.empPhone)!}" />
					</@field>
					<@field label="申请渠道">
						<@dictSelect id="appSource" dicttype="AppSource"showcode='true' name="query.appSource"value="${(query.appSource)!}" />
					</@field>
					<@field label="客户类型">
						<@dictSelect id="custType" dicttype="CustType" name="query.custType" value="${(query.custType)!}"/>
					</@field>
					<@field label="审批状态">
						<@multipleSelect id="rtfState" name="rtfState" value="${(query.rtfState)!''}" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
						'{"type":"RtfState"}','code','codeName') showfilter="true" />
					</@field>
				</@row>
				<@row>
					<@field label="录入员">
						<@input id="inputNo" name="query.inputNo" value="${(query.inputNo)!}" />
					</@field>
					<@field label="预审员">
						<@input id="preNo" name="query.preNo" value="${(query.preNo)!}" />
					</@field>
					<@field label="复核员">
						<@input id="reviewNo" name="query.reviewNo" value="${(query.reviewNo)!}" />
					</@field>
					<@field label="补件操作员">
						<@input id="patchBoltNo" name="query.patchBoltNo" value="${(query.patchBoltNo)!}" />
					</@field>
				</@row>
				<@row>
					<@field label="初审员">
						<@input id="checkNo" name="query.checkNo" value="${(query.checkNo)!}" />
					</@field>
					<@field label="电话调查员">
						<@input id="phoneNo" name="query.phoneNo" value="${(query.phoneNo)!}" />
					</@field>
					<@field label="终审员">
						<@input id="finalNo" name="query.finalNo" value="${(query.finalNo)!}" />
					</@field>
					<@field label="任务所属人">
						<@multipleSelect id="taskOwner" name="query.taskOwner"
						options=ecms_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true"/>
					</@field>
				</@row>
			<@row>
				<@field label="推广人编号">
					<@input id="spreaderNo" name="query.spreaderNo" value="${(query.spreaderNo)!}" />
				</@field>
				<@field label="推广主管代码">
					<@input id="spreaderSupCode" name="query.spreaderSupCode" value="${(query.spreaderSupCode)!}" />
				</@field>
				<@field label="推广区域代码">
					<@input id="spreaderAreaCode" name="query.spreaderAreaCode" value="${(query.spreaderAreaCode)!}" />
				</@field>
				</@row>
			<@row>
				<@field label="进件起始日期">
					<@date id="beginDate" name="query.beginDate" datetype="date" value="${(query.beginDate)!}" />
				</@field>
				<@field label="进件截止日期">
					<@date id="endDate" name="query.endDate" datetype="date" value="${(query.endDate)!}" />
				</@field>
			</@row>
				<@row>
					<@field label="录入时间" hidden="true">
						<@input id="reviewNo" name="query.reviewNo" value="${(query.reviewNo)!}" />
					</@field>
					<@field label="复核时间" hidden="true">
						<@input id="checkNo" name="query.checkNo" value="${(query.checkNo)!}" />
					</@field>
					<@field label="补件时间" hidden="true">
						<@input id="phoneNo" name="query.phoneNo" value="${(query.phoneNo)!}" />
					</@field>
					<@field label="人工核查时间" hidden="true">
						<@input id="finalNo" name="query.finalNo" value="${(query.finalNo)!}" />
					</@field>
				</@row>
				<@row>
					<@field label="初审时间" hidden="true">
						<@input id="reviewNo" name="query.reviewNo" value="${(query.reviewNo)!}" />
					</@field>
					<@field label="电话调查时间" hidden="true">
						<@input id="checkNo" name="query.checkNo" value="${(query.checkNo)!}" />
					</@field>
					<@field label="终审时间" hidden="true">
						<@input id="phoneNo" name="query.phoneNo" value="${(query.phoneNo)!}" />
					</@field>
				</@row>
				<@toolbar align="right">
					<@button id="queryButton" name="查询" fa="search"/>
					<@button id="exportInfoResult" name="导出数据" />
				</@toolbar>
		</@form>
		<@table id="dataTable" url="cas_applyQuery/applyInfoList" form_id="queryForm" load_auto="false"
					button_id="queryButton">
<#--
				<@th checkbox="true"/>
-->
			<@th title="<div style='width:40px'>操作</div>" render="true" >
				<@buttonAuth code="CAS_APPLY_DETAIL"><@href name="详情" href="/cas_activiti/handleTask?appNo={{row.appNo}}&detailFlag=Y"/></@buttonAuth>
			</@th>
				<@th title="申请件编号" field="appNo" sortable="true"/>
			    <@th title="卡号" field="cardNo" sortable="true"/>
			    <@th title="姓名" field="name" sortable="true"/>
				<@th title="证件类型" field="idType" render="true" sortable="true">
					<@thDictName dicttype="IdType" value="{{row.idType}}" showcode="true"/>
				</@th>
				<@th title="证件号码" field="idNo" sortable="true"/>
				<@th title="客户类型" field="custType" render="true" sortable="true">
					<@thDictName dicttype="CustType" value="{{row.custType}}" />
				</@th>
				<@th title="申请渠道" field="appSource" render="true" sortable="true">
					<@thDictName dicttype="AppSource" value="{{row.appSource}}" />
				</@th>
				<@th title="审批状态" field="rtfState" render="true" sortable="true">
				 	<@thDictName dicttype="RtfState" showcode="true"  value="{{row.rtfState}}" />
				</@th>
				<@th title="公司名称" field="corpName" sortable="true"/>
				<@th title="公司电话" field="empPhone" sortable="true" />
				<@th title="推广人编号" field="spreaderNo" sortable="true"/>
				<@th title="录入员" field="inputNo" sortable="true" />
				<@th title="预审员" field="preNo" sortable="true" />
				<@th title="复核员" field="reviewNo" sortable="true" />
				<@th title="初审员" field="checkNo" sortable="true" />
				<@th title="补件操作员" field="patchBoltNo" sortable="true" />
				<@th title="电话调查员" field="phoneNo" sortable="true" />
				<@th title="终审员" field="finalNo" sortable="true" />
				<@th title="推广主管代码" field="spreaderSupCode" sortable="true" />
				<@th title="推广团队代码" field="spreaderTeamCode" sortable="true" />
				<@th title="推广区域代码" field="spreaderAreaCode" sortable="true" />
				<@th title="授信额度" field="accLmt" />
				<@th title="任务所属人" field="taskOwner" />
				<#--<@th title="录入时间" field="inputDate" sortable="true" hidden="true"/>
				<@th title="复核时间" field="reviewDate" sortable="true" hidden="true"/>
				<@th title="初审时间" field="checkDate" sortable="true" hidden="true"/>
				<@th title="补件时间" field="patchBoltDate" sortable="true" hidden="true"/>
				<@th title="人工核查时间" field="personCheckDate" sortable="true" hidden="true"/>
				<@th title="电话调查时间" field="phoneDate" sortable="true" hidden="true"/>
				<@th title="终审时间" field="finalDate" sortable="true" hidden="true"/>-->
		</@table>
	</@panelBody>
	
	<script>
		<#--导出数据按钮-->
/*		$("#exportQueryResult").on('click',function(){
		});*/

		<#--导出数据按钮-->
        $("#exportInfoResult").on("click",function(){
            var  elements = new Array();
            var appNo=document.getElementById("appNo").value;
            var name=document.getElementById("name").value;
            var idNo=document.getElementById("idNo").value;
            var appSource=document.getElementById("appSource").value;
            var beginDate=document.getElementById("beginDate").value;
            var endDate=document.getElementById("endDate").value;
            var rtfState=document.getElementById("rtfState").value;
            var empPhone=document.getElementById("empPhone").value;
            var spreaderNo=document.getElementById("spreaderNo").value;
            var preNo=document.getElementById("preNo").value;
            var inputNo=document.getElementById("inputNo").value;
			var cellPhone=document.getElementById("cellPhone").value;
            var reviewNo=document.getElementById("reviewNo").value;
            var patchBoltNo=document.getElementById("patchBoltNo").value;
            var checkNo=document.getElementById("checkNo").value;
            var phoneNo=document.getElementById("phoneNo").value;
            var finalNo=document.getElementById("finalNo").value;
			var spreaderSupCode=document.getElementById("spreaderSupCode").value;
			var spreaderAreaCode=document.getElementById("spreaderAreaCode").value;
			if(		(appNo!=""&&appNo!=null)||
                    (name!=""&&name!=null)||
                    (idNo!=""&&idNo!=null)||
                    (appSource!=""&&appSource!=null)||
                    (beginDate!=""&&beginDate!=null)||
                    (endDate!=""&&endDate!=null)||
					(cellPhone!=""&&cellPhone!=null)||
					(rtfState!=""&&rtfState!=null)||
                    (empPhone!=""&&empPhone!=null)||
                    (spreaderNo!=""&&spreaderNo!=null)||
                    (preNo!=""&&preNo!=null)||
                    (inputNo!=""&&inputNo!=null)||
                    (reviewNo!=""&&reviewNo!=null)||
                    (patchBoltNo!=""&&patchBoltNo!=null)||
                    (checkNo!=""&&checkNo!=null)||
                    (phoneNo!=""&&phoneNo!=null)||
                    (finalNo!=""&&finalNo!=null)||
					(spreaderSupCode!=""&&spreaderSupCode!=null)||
					(spreaderAreaCode!=""&&spreaderAreaCode!=null)
			){
                window.location.href="${base}/cas_applyQuery/exportExcelInfo?"+$("#queryForm").serialize();
            }
            else{
                alert("导出数据时查询条件不能为空。");
                return false;
            }
        });
		<@buttonAuth code="CAS_APPLY_DETAIL">
		<#----双击查看详情-->
		$("#dataTable").on('dbl-click-row.bs.table',function(row, $element, field){
			var appNo = $element['appNo'];
			window.location.href="${base}/cas_activiti/handleTask?detailFlag=Y&appNo="+appNo;
		});
		</@buttonAuth>
	</script>
</@body>

