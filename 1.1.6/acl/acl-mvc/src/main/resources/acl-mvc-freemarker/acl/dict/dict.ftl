<#include "/layout.ftl">

<@body>
<script src="${base}/assets/plugins/artTemplate/template-debug.js"></script>


    <script type="text/javascript">
    	
    
        /*
        var $table = null;
        //删除方法
        var deleteFc = function(){
            var selections =  $("#dataTable").bootstrapTable('getSelections');
            alert(selections.length);
            
            for(var i=0;i<selections.length;i++){
                var row = selections[i];
                alert(row.id);
            }
            
        }*/
        
        /**
       	 * 修改类型后，自动刷新
       	 */
        function afterChange(){
        	$("#queryButton").trigger("click");
        }
    </script>
    


    <@panel head="业务字典列表">
        <@panelBody>
            <@form id="queryForm" cols="5">
                <@row>
	                <@field label="类型" label_ar="6" field_ar="11" input_ar="30" >
	                	<@multipleSelect name="query.type" options=ar_('listToMap',types,"type","typeName") value="${(query.type)!}" single="true" nullable="true" showfilter="true" change="afterChange" />
	                	<#-- 
	                	<@select name="query.type" options=ar_('listToMap',types,"type","typeName") nullable="true" search="true" change="afterChange" />
	                    -->
	                </@field>
	                <@field label="类型名称" label_ar="8" field_ar="7" input_ar="27" >
	                    <@input name="query.typeName" value="${(query.typeName)!}" />
	                </@field>
	                <@field label="代码" label_ar="8" field_ar="7" input_ar="27" >
	                    <@input name="query.code" value="${(query.code)!}"/>
	                </@field>
	                <@field label="代码名称" label_ar="8" field_ar="7" input_ar="27" >
	                    <@input name="query.codeName" value="${(query.codeName)!}" />
	                </@field>
	                <@field label="" label_ar="2" field_ar="3" input_ar="33">
	                	<@button id="queryButton" name="查询" />&nbsp;&nbsp;&nbsp;&nbsp;
		                <@href href="acl/dict/addpage" name="新增" />
	                </@field>
                </@row>
                <#-- 
                <@row>
	                <@field label="不查询" field_ar="36" label_ar="3" input_ar="33">
						<@checkbox>
							<label><input name="notinType" type="checkbox" checked="checked" value="COUNTRY" /> 国家 </label>
							<label><input name="notinType" type="checkbox" checked="checked" value="STATE" /> 省 </label>
							<label><input name="notinType" type="checkbox" checked="checked" value="CITY"  /> 市 </label>
							<label><input name="notinType" type="checkbox" checked="checked" value="ZONE"  /> 区县 </label>
						</@checkbox>
					</@field>
                </@row>
                -->
            </@form>
            <@table id="dataTable" url="acl/dict/list" form_id="queryForm" button_id="queryButton" condensed="true">
                <@th title="类型" field="type"  sortable="true"/>
                <@th title="类型名称" field="typeName" sortable="true" />
                <@th title="代码" field="code"  sortable="true"/>
                <@th title="代码名称" field="codeName"  sortable="true"/>
                <@th title="值" field="value"  sortable="true"/>
                <@th title="值2" field="value2" />
                <@th title="值3" field="value3" />
                <@th title="值4" field="value4" />
                <@th title="说明" field="remark" />
                <@th title="是否启用" field="ifUsed" render="true" sortable="true">
                	<@thDictName dicttype="Indicator" value="{{row.ifUsed}}" showcode="true" />
                </@th>
                <@th title="操作" render="true" >
                    <@href href="acl/dict/editpage?id={{row.id}}" name="编辑" />
                    <@href href="acl/dict/addpage?id={{row.id}}" name="复制" />
                    {{if row.ifCanDel=="Y"}}
                        <@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="acl/dict/delete?id={{row.id}}" success_url="acl/dict/page" />
                    {{/if}}
                </@th>
            </@table>
        </@panelBody>
        
    </@panel>
    
</@body>
