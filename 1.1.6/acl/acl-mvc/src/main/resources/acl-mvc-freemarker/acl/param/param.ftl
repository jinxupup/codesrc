<#include "/layout.ftl">

<@body>
    <@panel head="参数列表">
        <@panelBody>
            <@form id="queryForm" cols="3">
                <@row>
                <@field label="参数类型">
                    <@input name="query.type" />
                </@field>
                <@field label="参数名称">
                    <@input name="query.typeName" />
                </@field>
                </@row>
                <@row>
                <@field label="参数代码">
                    <@input name="query.code" />
                </@field>
                <@field label="参数代码名称">
                    <@input name="query.codeName" />
                </@field>
                <@field label="" label_ar="0">
           			 <@toolbar>
		                <@button id="queryButton" name="查询" />&nbsp;&nbsp;&nbsp;
		                <@href href="acl/param/addpage" name="新增" />
		            </@toolbar>
           		</@field>
                </@row>
            </@form>
           
            
            <@table id="dataTable" url="acl/param/list" form_id="queryForm" button_id="queryButton" condensed="true">
                <@th checkbox="true"/>
                <@th title="参数类型" field="type" sortable="true"/>
                <@th title="参数名称" field="typeName" sortable="true"/>
                <@th title="参数代码" field="code" sortable="true"/>
                <@th title="代码名称" field="codeName" sortable="true"/>
                <@th title="值" field="value" />
                <@th title="值2" field="value2" />
                <@th title="值3" field="value3" />
                <@th title="值4" field="value4" />
                <@th title="说明" field="remark" />
                <@th title="是否启用" field="ifUsed" render="true" sortable="true">
                	<@thDictName dicttype="Indicator"  value="{{row.ifUsed}}" showcode="true" />
            	</@th>
                <@th title="操作" render="true" >
                    <@href href="acl/param/editpage?id={{row.id}}" name="编辑" />
                    <@href href="acl/param/addpage?id={{row.id}}" name="复制" />
                    {{if row.ifCanDel=="Y"}}
                        <@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="acl/param/delete?id={{row.id}}" success_url="acl/param/page" />
                    {{/if}}
                    
                </@th>
            </@table>
        </@panelBody>
        
    </@panel>
    
</@body>
