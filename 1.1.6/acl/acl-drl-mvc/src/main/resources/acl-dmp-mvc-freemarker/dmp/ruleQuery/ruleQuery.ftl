<#include "/layout.ftl"/>
<@body>
<@panel sense="primary" head="规则查询">
<@panelBody>
    <@form id="queryForm" >
        <@row class="">
           <@field label="方案ID">
               <@input name="query.stId" />
           </@field>
           <@field label="方案名称">
               <@input name="query.stName" />
           </@field>
           <@field label="方案类别">
               <@multipleSelect name="query.stClass" single="true" nullable="true" showfilter="true"
                    options=dict_("tableMap","com.jjb.dmp.infrastructure.TmDmpStrategyCategory",{},"stClass","name")
                  />
           </@field>
           
           <@field label="规则集名称">
                <@input name="query.ruleSetName" />
            </@field>
            <@field label="规则集是否启用">
                <@select options={"Y":"是","N":"否"} name="query.ruleSetEnabled" />
            </@field>
            <@field label="是否排他">
                <@select options={"Y":"是","N":"否"} name="query.isExclusive" />
            </@field>
           
            <@field label="规则名称">
                <@input name="query.ruleName" />
            </@field>
            <@field label="是否启用">
                <@select options={"Y":"是","N":"否"} name="query.ifUsed" />
            </@field>
            <#--
            <@field label="规则条件">
                <@input name="query.ruleObject" />
            </@field>
            -->
        </@row>
   </@form>
    <@toolbar class="">
        <@button id="queryButton" fa="search">查询</@button>
    </@toolbar>
            
    <@table url="dmp/ruleQuery/list" form_id="queryForm" button_id="queryButton" >
        <@th field="stId" title="方案ID-方案名称 (类别)" render="true">
            <span class="label label-info">{{row.stId}}</span>
            <span class="label label-default">{{row.stName}}</span>
            <span class="label label-warning">
                {{<@thGetName options=dict_("tableMap","com.jjb.dmp.infrastructure.TmDmpStrategyCategory",{},"stClass","name") showcode="true" value="{{row.stClass}}" />}}
            </span>
        </@th>
        <@th field="ruleSetName" title="规则集(是否启用)(是否排他)" render="true">
            <span class="label label-info">{{row.ruleSetName}}</span>
            <span class="label label-default">
                {{if row.ruleSetEnabled == 'Y'}} 启用  {{else}} 未启用  {{/if}}
            </span>
            
            <span class="label label-warning">
                {{if row.isExclusive == 'Y'}} 排他  {{else}} 不排他  {{/if}}
            </span>
        </@th>
        <@th field="ruleName" title="规则名称"></@th>
        <@th field="remark" title="规则说明"></@th>
        <@th field="priority" title="优先级">
        </@th>
        <@th field="ruleEnabled" title="是否启用" render="true">
            <@thGetName options={"Y":"是","N":"否"} value="{{row.ruleEnabled}}" />
        </@th>
    </@table>
</@panelBody>   
</@panel>
</@body>