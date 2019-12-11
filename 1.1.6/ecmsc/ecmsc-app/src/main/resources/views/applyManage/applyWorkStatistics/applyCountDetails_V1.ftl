<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panel>
		<@form id="queryData" >
			<@hidden name="query.proName" value="${(applyTaskCountDto.proName)!}" />
			
			<@table form_id="queryData" url="taskCount/details?query.startDate=${(applyTaskCountDto.startDate)!}&query.endDate=${(applyTaskCountDto.endDate)!}&query.operatorId=${(applyTaskCountDto.operatorId)!}">
				<@th title="申请件编号" field="appNo"></@th>
				<@th title="姓名" field="name"></@th>
				<@th title="证件号码" field="idNo"></@th>
				<#--<@th title="申请类型" field="appType" render="true" sortable="true">
					<@thDictName  dicttype="AppType" value="{{row.appType}}" showcode="true"/>
				</@th>-->
				<@th title="申请产品" field="productCd" render="true" >
					<@thGetName options=ecms_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="true" />
				</@th>
				<@th title="任务处理日期" field="taskProcDate"></@th>
				<@th title="操作类型" field="rtfState" render="true" >
					<@thDictName dicttype="RtfState" value="{{row.rtfState}}" showcode="true" />
				</@th>
				<@th title="最新审批状态" field="currRtfState" render="true" >
					<@thDictName dicttype="RtfState" value="{{row.currRtfState}}" showcode="true" />
				</@th>
				<@th title="申请额度" field="appLmt"></@th>
				<@th title="初审额度" field="basicLmt"></@th>
				<@th title="终审额度" field="finalLmt"></@th>
				<#--<@th title="录入备注" field="inputRemark"></@th>
				<@th title="复核备注" field="reviewRemark"></@th>
				<@th title="预审备注" field="preRemark"></@th>-->
				<@th title="初审备注" field="basicRemark"></@th>
				<#--<@th title="补件备注" field="patchRemark"></@th>
				<@th title="电调备注" field="telRemark"></@th>-->
				<@th title="终审备注" field="finalRemark"></@th>
				
			</@table>
		</@form>
	</@panel>
</@body>
