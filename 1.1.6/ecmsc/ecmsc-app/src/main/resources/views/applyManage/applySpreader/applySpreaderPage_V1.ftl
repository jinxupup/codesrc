<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panel head="推广人信息维护">
		<@panelBody>
			<@form id="queryForm" cols="4">
				<@row>
					<@field label="推广人类型" >
						<@dictSelect id="sprUserType" dicttype="SprUserType" value="${(sprUserType)!}" name="query.sprUserType"/>
					</@field>
					<@field label="推广人编号" >
						<@input name="query.spreaderNum" value="${(spreaderNum)!}" />
					</@field>
					<@field label="推广人姓名" >
						<@input name="query.spreaderName" value="${(spreaderName)!}" />
					</@field>
					<@field label="手机号码" >
						<@input name="query.spreaderPhone" valid={"regexp or pattern":"${(appPhone)!}","regexp-message":"请输入有效的联系电话"} />
					</@field>
				</@row>
				<@row>
					<@field label="所属网点机构" >
						<@multipleSelect name="query.spreaderBankId" value="${(spreaderBankId)!}"
							options=ecms_('tableMap','branchMap','allBranch') nullable="true" showfilter="true" single="true"/>
					</@field>
					<@field label="推广人状态" >
						<@dictSelect id="spreaderStatus" dicttype="SpreaderStatus" value="${(spreaderStatus)!'1'}" name="query.spreaderStatus"/>
					</@field>
					
					<@field>
						<@button id="queryButton" name="查询" fa="search"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<@href href="spreaderInfo/spreaderEditPage" name="新增" fa="plus"/>
					</@field>
				</@row>
			</@form>
			</br>
			<@table id="dataTable" url="spreaderInfo/spreaderList" form_id="queryForm" button_id="queryButton" page_size="10" load_auto="false" condensed="false">
				<@th title="推广人类型" field="sprUserType" render="true">
					<@thDictName  dicttype="SprUserType" showcode="true" value="{{row.sprUserType}}" />
				</@th>
				<@th title="推广人编号" field="spreaderNum" />
				<@th title="推广人姓名" field="spreaderName" />
				<@th title="手机号码" field="spreaderPhone" />
				<#--<@th title="推广人绩效号" field="spreaderPerformance" />-->
				<@th title="网点机构" field="owningBranch"render="true" sortable="true">
					<@thGetName options=ecms_('tableMap','branchMap','allBranch')  value="{{row.spreaderBankId}}" showcode="true"/>
				</@th>
			   	<#--<@th title="网点机构" render="true">
                			{{row.spreaderBankId}}-{{row.spreaderBankName}}
				</@th>-->
			   	<@th title="上级网点机构" field="spreaderOrg" render="true" sortable="true">
			   		<@thGetName options=ecms_('tableMap','branchMap','allBranch')  value="{{row.spreaderOrg}}" showcode="true"/>
			   	</@th>
				<@th title="更新时间" render="true">
					<@thDate value="{{row.updateDate}}" datetype="datetime"/>
				</@th>
				<@th title="推广人状态" render="true">
					<@thDictName dicttype="SpreaderStatus" showcode="true" value="{{row.spreaderStatus}}" />
				</@th>
				<@th title="操作" render="true" >
					<@href href="spreaderInfo/spreaderEditPage?id={{row.id}}" name="修改" />
				   	<@ajaxButton  confirm="确定删除此记录？" sense="danger" name="删除" url="spreaderInfo/spreaderDelete?id={{row.id}}" success_url="spreaderInfo/spreaderPage" />
				</@th>
			</@table>
			<@field hidden="true">
				<@input name="query._SORT_NAME" value="updateDate" />
				<@input name="query._SORT_ORDER" value="asc"/>
			</@field>
		</@panelBody>
		
	</@panel>
 
</@body>

