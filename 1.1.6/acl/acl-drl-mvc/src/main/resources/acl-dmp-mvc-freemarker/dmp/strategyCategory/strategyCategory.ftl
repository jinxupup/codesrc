<#include "/layout.ftl"/>
<@body>
  <@panel head="决策类别">
    <@panelBody>
       <@form id="queryForm" >
            <@row class="">
                <@field label="决策类别">
                    <@input name="query.stClass" />
                </@field>
                <@field label="名称">
                    <@input name="query.name" />
                </@field>
                <@field label="" label_ar="">
                	<@button id="queryButton" fa="search">查询</@button>
                	<@href href="dmp/strategyCategory/addpage" fa="plus">新增</@href>
                </@field>
            </@row>
       </@form>
        <@table id="table" url="dmp/strategyCategory/list" form_id="queryForm" button_id="queryButton" condensed="true">
            <@th checkbox="true"></@th>
            <@th field="stClass" title="决策类别"></@th>
             <@th field="name" title="名称"></@th>
            <@th field="remark" title="说明"></@th>
            <@th title="操作" render="true">
                <@href href="dmp/strategyCategory/editpage?stClass={{row.stClass}}"  name="编辑" />
                <@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="dmp/strategyCategory/delete?stClass={{row.stClass}}" success_url="dmp/strategyCategory/page" />
            </@th>
        </@table>
        
    </@panelBody>  
  </@panel>
</@body>