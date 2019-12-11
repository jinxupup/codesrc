<#include "/layout.ftl"/>
<@body>
<@panel sense="primary" head="决策方案管理">
<@panelBody>
	<@form id="queryForm" cols="4">
        <@row class="">
            <@field label="名称">
                <@input name="query.stName" />
            </@field>
            <@field label="类别">
                <@multipleSelect name="query.stClass" single="true" nullable="true" showfilter="true"
	            	options=dict_("tableMap","com.jjb.dmp.infrastructure.TmDmpStrategyCategory",{},"stClass","name")
	              />
            </@field>
            <@field label="是否启用">
            	<@select options={"Y":"是","N":"否"} name="query.ifUsed" />
            </@field>
            <@field label="" label_ar="0">
            	<@button id="queryButton" fa="search">查询</@button>
				<@href href="dmp/strategy/addpage" fa="plus">新增</@href>
            </@field>
        </@row>
   </@form>
	<@table url="dmp/strategy/list" form_id="queryForm" button_id="queryButton" >
		<@th field="stId" title="方案ID"></@th>
		<@th field="stName" title="方案名称"></@th>
		<@th title="类别" render="true"> 
			<#-- {{row.stClass}} -  -->
			<@thGetName options=dict_("tableMap","com.jjb.dmp.infrastructure.TmDmpStrategyCategory",{},"stClass","name")
				showcode="true" value="{{row.stClass}}" />
		</@th>
		<@th field="remark" title="方案描述"></@th>
		<@th field="ifUsed" title="是否启用" render="true">
			<@thGetName options={"Y":"是","N":"否"} value="{{row.ifUsed}}" />
		</@th>
		<@th title="操作" render="true">
			<@href href="dmp/strategy/editpage?stId={{row.stId}}"  name="编辑" />
            <@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="dmp/strategy/delete?stId={{row.stId}}" success_url="dmp/strategy/page" />
		</@th>
	</@table>
</@panelBody>	
</@panel>
</@body>