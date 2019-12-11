<@form id="queryForm">
	<@row>
		<@field label="文件名称" label_ar="12">
     		<@input name="query.fileName"/>
		</@field>
<#--		<@field label="处理日期">
     		<@date name="query.batchDate" datetype="date"/>
		</@field>-->
		<@field label="处理起始日期" label_ar="12">
			<@date id="beginDate" name="query.beginDate" datetype="date" value="${(query.beginDate)!}"/>
		</@field>
		<@field label="处理截止日期" label_ar="12">
			<@date id="endDate" name="query.endDate" datetype="date" value="${(query.endDate)!}"/>
		</@field>
	</@row>
	<@toolbar align="right">
		<@button id="queryButton" name="搜索" fa="search" style="margin-right:50px;"/>
	</@toolbar>
</@form>
</br>
<@table id="dataTable" url="batchDecision/list" form_id="queryForm" button_id="queryButton">
    <@th checkbox="false"/>
    <@th title="上传文件名称" field="fileName"/>
    <@th title="文件行号" field="lineNo"/>
    <@th title="内容" field="content" />
    <@th title="状态" field="startBpmn"/>
    <@th title="失败原因描述" field="failReason" />
    <@th title="上传日期" field="batchDate" render="true">
    	<@thDate value="{{row.batchDate}}"/>
    </@th>
    <@th title="操作" render="true">	
    	<@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="batchDecision/delete?id={{row.id}}" success_url="batchDecision/fileUploadList"/>
<#--
    	<@href href="creditReport/download?id={{row.id}}" name="下载"/>
-->
    </@th>
</@table>