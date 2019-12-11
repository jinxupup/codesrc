<#include "/layout.ftl">

<@body>    
    <@panel head="规则参数列表">
        <@panelBody>
            <@form id="queryForm" cols="4">
                <@row>
                <@field label="类型">
                    <@input name="query.type" />
                </@field>
                <@field label="类型名称">
                    <@input name="query.typeName" />
                </@field>
                <@field label="代码">
                    <@input name="query.code" />
                </@field>
                <@field label="代码名称">
                    <@input name="query.codeName" />
                </@field>
                </@row>
            </@form>
            <@toolbar>
                <@button id="queryButton" name="查询" />
                <@href href="/ruleParam/addpage" name="新增" />
                <#-- <@button click="deleteFc" name="删除" /> -->
            </@toolbar>
            
            <@table id="dataTable" url="ruleParam/list" form_id="queryForm" button_id="queryButton">
                <@th checkbox="true"/>
                <@th title="类型" field="type" width="200px" />
                <@th title="类型名称" field="typeName" />
                <@th title="代码" field="code" />
                <@th title="代码名称" field="codeName" />
                <@th title="值" field="value" />
                <@th title="值2" field="value2" />
                <@th title="值3" field="value3" />
                <@th title="值4" field="value4" />
                <@th title="说明" field="remark" />
                <@th title="是否启用" field="ifUsed" />
                <@th title="操作" render="true" >
                    <@href href="ruleParam/editpage?id={{row.id}}" name="编辑" />
                    <@href href="ruleParam/addpage?id={{row.id}}" name="复制" />
                    <@button   name="删除" click="deleteRow('{{row.id}}')" />

                </@th>
            </@table>
        </@panelBody>
        
    </@panel>
         <script type="text/javascript">
			    function deleteRow(id){
			    			 $.ajax({
							type: "POST", 
							dataType : "json",	
							data:{"id":id},				 
							url: "${base}/ruleParam/delete", 
							success: function(ref){		
							alert(ref.msg); 		
								var params = {url:"${base}/ruleParam/list"};
								$("#dataTable").bootstrapTable("refresh",params);
								}
						});
					}
    </script>
    
    
</@body>
