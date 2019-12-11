<@form id="queryForm" >
    <@row>
        <@field field_ar="8" label="文件名称">
            <@input name="query.fileName"/>
        </@field>
        <@field field_ar="6" label="处理状态" >
            <@dictSelect dicttype="Status" showcode='true' name="query.status" />
        </@field>
        <@field field_ar="10" label="查询处理起始日期">
                <@date id="beginDate" name="query.beginDate" datetype="date" value="${(query.beginDate)!}" />
            </@field>
            <@field field_ar="10" label="查询处理截止日期">
                <@date id="endDate" name="query.endDate" datetype="date" value="${(query.endDate)!}" />
            </@field>
        <@button id="queryButton" name="搜索" fa="search"/>
    </@row>
</@form>
</br>
<@table id="dataTable" url="tmRiskList/tmRiskUploadList" form_id="queryForm" button_id="queryButton">
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
        <@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="tmRiskList/deleteTmRiskUpload?id={{row.id}}" success_url="tmRiskList/fileUpload?isEdit=Y"/>
        <@href href="tmRiskList/download?id={{row.id}}" name="下载"/>
    </@th>
</@table>