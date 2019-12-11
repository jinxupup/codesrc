<#include "/layout.ftl"/>
<@body>
    <@panel head="进件归档管理">
        <@panelBody>
            <@form id="queryForm" cols="4">
                <@row>
                    <@field label="申请件编号">
                        <@input id="appNo" name="query.appNo"  value="${(query.appNo)!}" />
                    </@field>
                    <@field label="客户姓名">
                        <@input id="name" name="query.name"  value="${(query.name)!}"/>
                    </@field>
                    <@field label="证件号码">
                        <@input id="idNo" name="query.idNo" value="${(query.idNo)!}"/>
                    </@field>
                    <@field label="归档标志">
                        <@dictSelect id="fileFlag" dicttype="FileFlag"showcode='true' name="query.fileFlag"value="${(query.fileFlag)!}" />
                    </@field>
                </@row>
                <@row>
                    <@field label="申请渠道">
                        <@dictSelect id="appSource" dicttype="AppSource"showcode='true' name="query.appSource"value="${(query.appSource)!}" />
                    </@field>
                    <@field label="推广人工号/姓名" >
                        <@input id="spreader" name="query.spreader" />
                    </@field>
                    <@field label="查询起始日期">
                        <@date id="beginDate" name="query.beginDate" datetype="datetime"value="${(query.beginDate)!}"/>
                    </@field>
                    <@field label="查询截止日期">
                        <@date id="endDate" name="query.endDate" datetype="datetime" value="${(query.endDate)!}"/>
                    </@field>
                </@row>
                <@row>
                    <@field label="受理网点">
                        <@multipleSelect id="owningBranch" name="query.owningBranch" value="${(query.owningBranch)!}"
                        options=cas_('tableMap','branchMap','issueInd')  nullable="true" showfilter="true" single="true"/>
                    </@field>
                    <@field label="卡产品代码">
                        <@select id="productCd" name="query.productCd" options=cas_('tableMap','productForStatus','A,B,C') value="${(query.productCd)!}"/>
                    </@field>
                    <@toolbar align="center">
                        <@button id="queryButton" name="查询" fa="search" style="margin-right:15px;"/>
                        <@resetButton sense="warning" name="清空" id="resetBut" style="margin-right:15px"/>
                    </@toolbar>
                </@row>
            </@form>
            </br>
            <@table  url="fileManage/fileList" form_id="queryForm" button_id="queryButton" load_auto="false"  page_size="25">
                <@th title="申请编号" field="appNo" sortable="true" />
                <@th title="客户姓名" field="name" sortable="true"/>
                <@th title="申请类型" field="appType" render="true" sortable="true">
                    <@thDictName  dicttype="AppType" value="{{row.appType}}" />
                </@th>
                <@th title="申请卡产品代码" field="productCd" render="true" sortable="true">
                    <@thGetName options=cas_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="false"/>
                </@th>
                <@th title="证件号码" field="idNo" sortable="true"/>
                <@th title="受理网点" field="owningBranch" render="true" sortable="true">
                    <@thGetName options=cas_('tableMap','branchMap','issueInd') value="{{row.owningBranch}}" showcode="false"/>
                </@th>
                <@th title="申请时间" field="m.inputDate" render="true" sortable="true">
                    <@thDate value="{{row.inputDate}}" datetype="datetime"/>
                </@th>
                <@th title="更新时间" field="m.updateDate" render="true" sortable="true">
                    <@thDate value="{{row.updateDate}}" datetype="datetime"/>
                </@th>
                <@th title="归档标志" field="fileFlag" render="true" sortable="true" >
                <@thDictName dicttype="FileFlag" showcode="true"  value="{{row.fileFlag}}" />
                </@th>
                <@th title="任务所属人" field="owner"sortable="true" />
                <@th title="操作" render="true">
                    <@toolbar class="">
                        <@buttonAuth code="CAS_APPLY_FLMANAGE">
                        {{if row.fileFlag == 'N'}}
                        {{if row.owner == null || row.owner == ''}}
                        <@href name="归档" href="/cas_activiti/handleTask?appNo={{row.appNo}}"/>
                        {{/if}}
                        {{/if}}
                        </@buttonAuth>
                        <#--{{if row.fileFlag != 'N' || row.owner != null}}-->
                        <@buttonAuth code="CAS_APPLY_DETAIL"><@href name="详情" href="/cas_activiti/handleTask?appNo={{row.appNo}}&detailFlag=Y"/></@buttonAuth>
                    <#--{{/if}}-->
                    </@toolbar>
                </@th>
            </@table>
        </@panelBody>
    </@panel>
</@body>

