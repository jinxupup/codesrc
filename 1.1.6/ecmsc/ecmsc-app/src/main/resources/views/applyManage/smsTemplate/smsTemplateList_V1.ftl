<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panel head="短信模板管理">
		<@panelBody>
			<@form id="queryForm" cols="4">
			     <@row>
			      
			          <@field label="信息代码">
			               <@input name="query.teCode" value="${(query.teCode)!}" />
			          </@field>
			          <@field label="信息分类">
			                <@dictSelect dicttype="SortingInformation" name="query.msgcategory" value="${(query.msgcategory)!}" />
			          </@field>
			     
			          <@field label="">
			          		&nbsp;&nbsp;
				          <@button id="queryButton" name="查询" fa="search"/>
				          &nbsp;&nbsp;
				          <@href href="smsTemplate/smsTemplateAddPage" name="新增" />
			          </@field>
			      </@row>
			</@form>
			</br>  
			<@table id="dataTable" url="smsTemplate/smsTemplateList" form_id="queryForm" button_id="queryButton" page_size="25">
			   
				  <@th title="信息代码" field="teCode" />
				  <@th title="信息分类" field="msgCategory" />
				  <@th title="发送方法" field="sendingMethod" />
				  <@th title="信息描述" field="teDesc" />
			            
			      <@th title="操作" render="true" >
				  		<@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="smsTemplate/delete?id={{row.id}}" success_url="smsTemplate/smsTemplatePage" />
				  		&nbsp;&nbsp;
				  		<@href href="smsTemplate/editpage?id={{row.id}}" name="编辑" />
				  </@th>         
			</@table>
		</@panelBody>
	</@panel>
	
	<script>
	
	</script>
</@body>
