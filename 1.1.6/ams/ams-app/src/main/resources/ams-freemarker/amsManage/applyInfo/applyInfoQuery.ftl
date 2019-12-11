<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panelBody style="width:99.3%;">
		<@form id="queryForm" cols="4">
				<@row>
					<@field label="申请件编号">
						<@input id="appNo" name="query.appNo"  value="${(query.appNo)!}" />
					</@field>
					<@field label="姓名">
						<@input id="name" name="query.name"  value="${(query.name)!}" />
					</@field>
					<@field label="证件类型">
	               	 	<@dictSelect dicttype="IdType" name="query.idType" value="${(query.idType)!}" />            
	          		</@field>
					<@field label="证件号码">
						<@input id="idNo" name="query.idNo" value="${(query.idNo)!}" />
					</@field>
				</@row>
				<@row>
					<#--<@field label="客户类型">
						<@dictSelect id="custType" dicttype="CustType" name="query.custType" value="${(query.custType)!}"/>
					</@field>-->
					<@field label="申请渠道">
						<@dictSelect id="appSource" dicttype="AppSource"showcode='true' name="query.appSource"value="${(query.appSource)!}" />
					</@field>
					<@field label="查询起始日期">
						<@date id="beginDate" name="query.beginDate" datetype="date" value="${(query.beginDate)!}" />
					</@field>
					<@field label="查询截止日期">
						<@date id="endDate" name="query.endDate" datetype="date" value="${(query.endDate)!}" />
					</@field>
					
				</@row>
				<@row>
					<@field label="审批状态">
						<@multipleSelect id="rtfState" name="rtfState" value="${(query.rtfState)!''}" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
							'{"type":"RtfState"}','code','codeName') showfilter="true" />
					</@field>
					<@field label="公司名称">
						<@input id="corpName" name="query.corpName" value="${(query.corpName)!}" />
					</@field>
					<@field label="公司电话">
						<@input id="empPhone" name="query.empPhone" value="${(query.empPhone)!}" />
					</@field>
					<@field label="推广人编号">
						<@input id="spreaderNo" name="query.spreaderNo" value="${(query.spreaderNo)!}" />
					</@field>
				</@row>
				<@row>
					<@field label="预审人工号">
						<@input id="preNo" name="query.preNo" value="${(query.preNo)!}" />
					</@field>
					<@field label="录入员">
						<@input id="inputNo" name="query.inputNo" value="${(query.inputNo)!}" />
					</@field>
					<@field label="复核员">
						<@input id="reviewNo" name="query.reviewNo" value="${(query.reviewNo)!}" />
					</@field>
					<#--<@field label="补件操作员">
						<@input id="patchBoltNo" name="query.patchBoltNo" value="${(query.patchBoltNo)!}" />
					</@field>-->
				</@row>
				<#--<@row>-->
					<#--<@field label="人工核查员">-->
						<#--<@input id="personCheckNo" name="query.personCheckNo" value="${(query.personCheckNo)!}" />-->
					<#--</@field>-->
					<#--<@field label="初审员">-->
						<#--<@input id="checkNo" name="query.checkNo" value="${(query.checkNo)!}" />-->
					<#--</@field>-->
					<#--<@field label="电话调查员">-->
						<#--<@input id="phoneNo" name="query.phoneNo" value="${(query.phoneNo)!}" />-->
					<#--</@field>-->
					<#--<@field label="终审员">-->
						<#--<@input id="finalNo" name="query.finalNo" value="${(query.finalNo)!}" />-->
					<#--</@field>-->
				<#--</@row>-->
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
					<@button id="exportQueryResult" name="导出数据" />
				</@toolbar>
		</@form>
		<@table id="dataTable" url="ams_applyInfoQuery/applyInfoList" form_id="queryForm" load_auto="false"
					button_id="queryButton">
				<@th checkbox="true"/>
				<@th title="申请件编号" field="appNo" sortable="true"/>				
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
			<@th title="卡号" field="cardNo" sortable="true"/>
			<@th title="影像" field="imageNum"/>
			<@th title="<div style='width:40px'>操作</div>" render="true" >
				<@buttonAuth code="AMS_APPLY_DETAIL"><@href name="详情" href="/ams_activiti/handleTask?appNo={{row.appNo}}&detailFlag=Y"/></@buttonAuth>
			</@th>
				<#--<@th title="公司名称" field="corpName" sortable="true"/>-->
				<#--<@th title="公司电话" field="empPhone" sortable="true" />-->
				<#--<@th title="推广人编号" field="spreaderNo" sortable="true"/>-->
				<#--<@th title="预审人工号" field="preNo" sortable="true" />-->
				<#--<@th title="录入员" field="inputNo" sortable="true" />-->
				<#--<@th title="复核员" field="reviewNo" sortable="true" />-->
				<#--<@th title="初审员" field="checkNo" sortable="true" />-->
				<#--<@th title="补件操作员" field="patchBoltNo" sortable="true" />-->
				<#--<@th title="人工核查员" field="personCheckNo" sortable="true" />-->
				<#--<@th title="电话调查员" field="phoneNo" sortable="true" />-->
				<#--<@th title="终审员" field="finalNo" sortable="true" />-->
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
		$("#exportQueryResult").on('click',function(){
			window.location.href="${base}/ams_applyInfoQuery/exportExcel?"+$("#queryForm").serialize();
		});
	</script>
</@body>

