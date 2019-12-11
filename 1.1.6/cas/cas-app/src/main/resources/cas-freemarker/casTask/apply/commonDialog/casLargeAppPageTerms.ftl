<#include "/layout.ftl"/>
<@body>
	<@panelBody style="width:99.3%;">
		<@form id="AuditQueryForm" cols="4">
			<@row>
				<@field label="姓名" label_ar="8">
					<@input id="name" name="query.name" />
				</@field>
				<@field label="证件号码" label_ar="8">
					<@input id="idNo" name="query.idNo" />
				</@field>
				<@field label="渠道类型" label_ar="8">
					<@input id="channelType" name="query.channelType" />
				</@field>
			<@row>
			</@row>
				<@field label="开始时间" label_ar="8">
					<@date id="startDate" name="query.startDate" datetype="datetime"/>
				</@field>
				<@field label="结束时间" label_ar="8">
					<@date id="endDate" name="query.endDate" datetype="datetime"/>
				</@field>
				<@toolbar align="right" style="margin-right:120px">
					<@button id="queryButton" name="搜索" style="margin-right:5px;"/>
				</@toolbar>
			</@row>
		</@form>
		<@table  url="cas_LargeApp/largeQueryForm" form_id="AuditQueryForm" button_id="queryButton"  page_size="10" >
			<@th title="姓名" field="name" sortable="true"/>
			<@th title="证件号码" field="idNo" sortable="true"/>
			<@th title="本人手机" field="cellphone" sortable="true"/>
			<@th title="婚姻状况" field="maritalStatus" sortable="true"/>
			<@th title="申请产品" field="appProducts" sortable="true"/>
			<#--<@th title="申请卡产品代码" field="productCd" render="true" sortable="true">-->
				<#--<@thGetName options=cas_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="false"/>-->
			<#--</@th>-->
			<@th title="贷款申请金额" field="appAmount" sortable="true"/>
			<@th title="单位名称" field="companyName" sortable="true"/>
			<@th title="第一联系人姓名" field="firstContactName" sortable="true"/>
			<@th title="第一联系人手机" field="firstContactPhone" sortable="true"/>
			<@th title="影像批次号" field="imageNum" sortable="true"/>
			<@th title="决策结果" field="policyResult" sortable="true"/>
			<@th title="人行规则命中结果清单" field="ruleList" sortable="true"/>
			<@th title="拒绝原因" field="refuseCode" sortable="true"/>
			<@th title="微信个人识别码" field="weCode" sortable="true"/>
			<@th title="车牌所属省" field="pptyProvince" sortable="true"/>
			<@th title="车牌所属市" field="pptyCity" sortable="true"/>
			<@th title="车牌归属地" field="pptyArea" sortable="true"/>
			<@th title="车牌归属地字母代号" field="pptyAreaCode" sortable="true"/>
			<@th title="渠道类型" field="channelType" sortable="true"/>
			<@th title="创建时间" field="createDate" render="true" sortable="true">
				<@thDate value="{{row.createDate}}" datetype="datetime"/>
			</@th>
		</@table>
	</@panelBody>
</@body>











