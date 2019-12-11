<#include "/layout.ftl"/>
<@body>
    <@panel head="参数列表">
        <@panelBody>
            <@form id="queryForm" cols="3">
                <@row style="padding-top:10px;">
	                <@field label="参数类型" field_ar="18" label_ar="9" input_ar="24">
	                    <@multipleSelect name="dicType" options=ar_('listToMap',ecms_('tableList','getDitDicGroupType',''),"dicType","typeName") 
	                    	single="true" nullable="true" showfilter="true" change="afterChange"/>
	                </@field>
	             </@row>
	             <@row>
	                <@field label="参数|typeName">
	                    <@input name="typeName" />
	                </@field>
	                <@field label="参数|itemName">
	                    <@input name="itemName" />
	                </@field>
	                <@field label="参数|tabName">
	                    <@input name="tabName" />
	                </@field>
	                <@field label="参数|formName">
	                    <@input name="formName" />
	                </@field>
	                <@field label="参数|remark">
	                    <@input name="remark" />
	                </@field>
					<@field>
						<@button id="queryButton" name="查询" style="margin-right: 3%;"/>
						<@href href="sysParam/addpage" name="新增" style="margin-right: 3%;"/>
					</@field>
                </@row>
            </@form>
            </br>            
            <@table id="dataTable" url="sysParam/list" form_id="queryForm" page_size="15" button_id="queryButton">
                <@th checkbox="true"/>
                <@th title="参数类型" field="dicType"/>
                <@th title="参数|tabName" field="tabName" />
                <@th title="参数|formName" field="formName" />
                <@th title="参数|itemName" field="itemName" />
                <@th title="参数|remark" field="remark" />
                <@th title="参数|typeName" field="typeName" />
                <@th title="参数|ifUsed" field="ifUsed" />
                <@th title="操作" render="true" >
                    <@href href="sysParam/editpage?id={{row.id}}" name="编辑" />
                    <@href href="sysParam/addpage?id={{row.id}}" name="复制" />
                    {{if row.ifCanDel=="Y"}}
                        <@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="sysParam/delete?id={{row.id}}" success_url="sysParam/page" />
                    {{/if}}
                    
                </@th>
            </@table>
        </@panelBody>
        
    </@panel>
    <script type="text/javascript">
    	/**
       	 * 修改类型后，自动刷新
       	 */
        function afterChange(){
        	$("#queryButton").trigger("click");
        }
    </script>
    
</@body>
