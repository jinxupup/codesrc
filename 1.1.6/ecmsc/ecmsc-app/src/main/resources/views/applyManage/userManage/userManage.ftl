<#include "/layout.ftl"/>
<@body>
    <@panel head="业务人员管理">
        <@panelBody>
            <@form id="queryForm" cols="3">
                <@row class="a">
                    <@field label="业务员工号">
                        <@multipleSelect id="userNo" name="query.userNo"
                        options=ecms_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true"/>
                    </@field>
                <#--<@field label="业务员姓名">
                    <@input name="query.name" />
                </@field>-->
                    <@field label="上级业务员工号">
                        <@multipleSelect id="highterUserNo" name="query.highterUserNo"
                        options=ecms_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true"/>
                    <#--<@input name="query.highterUserNo" />-->
                    </@field>
                <#-- <@field label="上级用业务员">
                     <@input name="query.highterUser" />
                 </@field>-->
                    <@field label="组别">
                        <@dictSelect id="groups" dicttype="GROUPS" name="query.groups"/>
                    </@field>
                    <@field label="审批区域组">
                        <@dictSelect id="checkGroups" dicttype="CHECKGROUPS" name="query.checkGroups"/>
                    </@field>
                    <@field label="" label_ar="0">
                        <@toolbar class="">
                            <@button id="queryButton" fa="search">查询</@button>&nbsp;&nbsp;&nbsp;
                            <@buttonAuth code="ECMS_USER_ADD">
                                <@href href="manageRelation/addPage" fa="plus">新增</@href>&nbsp;&nbsp;&nbsp;
                            </@buttonAuth>
                        </@toolbar>
                    </@field>
                </@row>
            </@form>
            <@table  url="manageRelation/userList" form_id="queryForm" button_id="queryButton" load_auto="true"  page_size="10">
                <@th title="业务员工号" field="userNo" />
                <@th title="业务员姓名" field="name" />
                <@th title="上级业务员工号" field="highterUserNo" />
                <@th title="上级用业务员姓名" field="highterUser" />
            <#--<@th title="下级业务员工号" field="lowerUserNo" />
            <@th title="下级用业务员" field="lowerUser" />-->
                <@th field="condition" title="工作状态" render="true">
                    <@thDictName dicttype="CURRENT_STATUS" value="{{row.condition}}" />
                </@th>
                <@th field="userType" title="业务员类型" render="true" >
                    <@thDictName dicttype="OPERATOR_TYPE" value="{{row.userType}}" />
                </@th>
                <@th title="组别" field="groups"  />
                <@th title="审批区域组" field="checkGroups"  render="true">
                    <@thDictName dicttype="CHECKGROUPS" value="{{row.checkGroups}}"/>
                </@th>
                <@th title="操作" render="true">
                    <@toolbar class="">
                    <#--{{if row.userNo != "IT"}}-->
                        <@href href="manageRelation/editPage?userNo={{row.userNo}}"  name="编辑" fa="edit" />
                    <#--放开删除按钮并增加权限 -->
                        <@buttonAuth code="ECMS_USER_RELATION_DELETE">
                            <@ajaxButton  confirm="确定删除此记录？" sense="danger" name="删除" url="manageRelation/deleteUser?userNo={{row.userNo}}" success_url="manageRelation/userManagePage" />
                        </@buttonAuth>
                    <#--{{/if}}-->
                        <@buttonAuth code="ECMS_USER_STATUS">
                            {{if row.condition != "A"}}
                            <@ajaxButton confirm="确定改变业务员状态？" sense="success" name="正常作业" url="manageRelation/changeStatus?userNo={{row.userNo}}" success_url="manageRelation/userManagePage" />
                            {{else}}
                            <@ajaxButton confirm="确定改变业务员状态？" sense="danger" name="暂停作业" url="manageRelation/changeStatus?userNo={{row.userNo}}" success_url="manageRelation/userManagePage" />
                            {{/if}}
                        </@buttonAuth>
                    </@toolbar>
                </@th>
            </@table>
        </@panelBody>
    </@panel>
</@body>
