
<@form id="queryForm" >
	<@row>
		<@field label="文件名称">
     		<@input name="query.fileName"/>
		</@field>
		<@field label="处理日期">
     		<@date name="query.batchDate"/>
		</@field>
		<@button id="queryButton" name="搜索" fa="search"/>
	</@row>
</@form>
</br>
<@table id="dataTable" url="ams_applyFileUpload/list" form_id="queryForm" button_id="queryButton">
    <@th checkbox="true"/>
    <@th title="上传文件名称" field="fileName"/>
    <@th title="文件行号" field="lineNo"/>
    <@th title="内容" field="content" />
    <@th title="状态" field="startBpmn"/>
    <@th title="失败原因描述" field="failReason" />
    <@th title="上传日期" field="batchDate" render="true">
    	<@thDate value="{{row.batchDate}}"/>
    </@th>
    <@th title="操作" render="true">	
    	<@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="ams_applyFileUpload/delete?id={{row.id}}" success_url="ams_applyFileUpload/fileUpload?isEdit=Y"/>
    	<@href href="ams_applyFileUpload/download?id={{row.id}}" name="下载"/>
    </@th>
</@table>