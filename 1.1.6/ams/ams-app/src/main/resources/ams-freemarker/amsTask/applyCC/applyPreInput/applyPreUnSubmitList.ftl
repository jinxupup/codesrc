
<@form id="queryForm" cols="4">
	<@row>
		<@field label="客户姓名">
			<@input name="query.name" />
		</@field>
		<@field label="证件类型">
			<@dictSelect dicttype="IdType" name="query.idType" />
		</@field>
		<@field label="证件号码">
			<@input name="query.idNo" />
		</@field>
		<@field label="" label_ar="0">
			<@button id="queryButton" name="查询" fa="search"/>
		</@field>
	</@row>
</@form>
<@table id="dataTable" url="ams_applyPreInput/applyPreInputAddlist" form_id="queryForm" button_id="queryButton">
	<@th checkbox="true"/>
	<@th title="申请件编号" field="appNo" />
	<@th title="申请类型" field="appType" render="true" >
		<@thDictName dicttype="AppType" value="{{row.appType}}" />
	</@th>
	<@th title="申请卡产品" field="productCd" render="true" >
		<@thGetName options=ams_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="true" />
	</@th>
	<@th title="姓名" field="name" />
	<@th title="证件类型" field="idType" render="true" >
		<@thDictName dicttype="IdType" value="{{row.idType}}" />
	</@th>
	<@th title="证件号码" field="idNo" />
	<@th title="移动电话" field="cellphone" />
	<@th title="申请日期" sortable="true" render="true">
		<@thDate  value="{{row.createDate}}" />
	</@th>
	<@th title="操作" render="true" >
		<@href href="ams_activiti/handleTask?appNo={{row.appNo}}"  name="继续录入" />
		<@ajaxButton confirm="确定删除此记录？" url="ams_applyPreInput/delete?appNo={{row.appNo}}&appType={{row.appType}}&name={{row.name}}&idType={{row.idType}}&idNo={{row.idNo}}"
		success_url="ams_applyPreInput/ams_applyPreInputpage?id=0" sense="danger" name="删除" />
	</@th>
</@table>