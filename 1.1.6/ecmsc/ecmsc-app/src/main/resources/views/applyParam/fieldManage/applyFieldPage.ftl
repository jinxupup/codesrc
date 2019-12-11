<#include "/layout.ftl">
<@body>
<@gotop/>
<@panelBody>
	<@form id="myQueryForm" cols="5">
        <@row>
            <@field label="字段表名">
            	<@dictSelect dicttype="BusTabName" showcode="false" name="query.tabName" value="${(query.tabName)!''}"/>
            </@field>
            <@field label="字段">
                <@input name="query.fieldEn" />
            </@field>
            <@field label="字段名称">
                <@input name="query.fieldName" />
            </@field>
            <@field label="是否启用">
				<@dictSelect dicttype="Indicator" name="query.ifUsed" value="${(query.ifUsed)!'Y'}"/>
			</@field>
			<@field label="" label_ar="0">
				<@button id="myQueryButton" name="查询" style="margin-right:5px;"/>
            	<@href name="新增" href="fieldManage/editFieldPage" style="margin-right:10px;margin-left:5px;"/>
			</@field>
        </@row>
	 </@form>
	 <@table id="myTask" url="fieldManage/fieldList" form_id="myQueryForm" button_id="myQueryButton" row_style="urgentTask" single_select="true" page_size="20">
        <@th checkbox="true"/>
        <@th title="表名描述" field="tabDesc"/>
        <@th title="字段" field="fieldEn"/>
        <@th title="默认值" field="defValue"/>
        <@th title="组件类型" field="componentType"/>
        <#--<@th title="下拉框改变事件" field="change"/>-->
        <@th title="是否可为空" field="nullable"/>
        <@th title="是否只读" field="ifReadonly"/>
        <#-- <@th title="正则表达式" field="regexp"/>-->
        <@th title="字符串最大长度" field="maxLength"/>
        <#-- <@th title="区间最小值" field="betweenMin"/>
        <@th title="区间最大值" field="betweenMax"/>
        <@th title="备注" field="remark" />-->
        <@th title="操作" render="true" >
        	<@href href="fieldManage/editFieldPage?fieldId={{row.fieldId}}" name="编辑" />
        	{{if row.ifUsed == 'Y'}}
				<button id="ifUsedBtn{{row.fieldId}}" class="btn btn-sm btn-danger" name="不启用" onclick="changeIfUsed('{{row.fieldId}}','N','不启用')">不启用</button>
			{{else}}
				<button id="ifUsedBtn{{row.fieldId}}" class="btn btn-sm btn-primary" name="启用" onclick="changeIfUsed('{{row.fieldId}}','Y','启用')">启用</button>
			{{/if}}
			{{if row.ifCancel=='Y'}}
				<@ajaxButton confirm="确定删除此字段？" url="fieldManage/deleteField?fieldId={{row.fieldId}}" name="删除" sense="danger"
					success_url="fieldManage/applyFieldPage"/>
			{{/if}}
        </@th>
    </@table>
</@panelBody>
	<script type="text/javascript">
		<#--启用/禁用字段-->
		var changeIfUsed = function(fieldId,ifUsed,oprType){
			if(confirm("是否要"+oprType+"该字段？")){
				$.ajax({
	 				url:"${base}/fieldManage/updateIfUsed",
	 				type:"post",
	 				data:{'fieldId':fieldId,'ifUsed':ifUsed},
	 				dataType:"json",
	 				success:function(res){
	 					alert(res.msg);
	 					if(res.s){
	 						if(ifUsed == 'Y'){
		 						$('#ifUsedBtn'+fieldId).removeClass('btn-primary').addClass('btn-danger').attr({'name':'不启用','onclick':'changeIfUsed("'+fieldId+'","N","不启用")'}).text('不启用');
		 					}else if(ifUsed == 'N'){
		 						$('#ifUsedBtn'+fieldId).removeClass('btn-danger').addClass('btn-primary').attr({'name':'启用','onclick':'changeIfUsed("'+fieldId+'","Y","启用")'}).text('启用');
		 					}
	 					}
	 				}
	 			});
			}
		} 
	</script>
</@body>
