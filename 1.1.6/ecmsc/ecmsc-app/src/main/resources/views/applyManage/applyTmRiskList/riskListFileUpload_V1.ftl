<#include "/layout.ftl" />

<@body>
    <@panel head="风险名单批量导入界面">
        <@panelBody>
            <@tab id="tab">
                <@tabNav>
                    <@tabTitle pane_id="pane1" active="${isEdit?string('false','true')}" title="风险名单批量导入新增"/>
                    <@tabTitle pane_id="pane2" active="${isEdit?string('true','false')}" title="风险名单导入信息列表"/>
                </@tabNav>
                <@tabContent>
                    <@tabPane id="pane1" active="${isEdit?string('false','true')}">
                        <#include "addriskListFile_V1.ftl"/>
                    </@tabPane>
                    <@tabPane id="pane2" active="${isEdit?string('true','false')}">
                        <#include "riskListFileList_V1.ftl"/>
                    </@tabPane>
                </@tabContent>
            </@tab>
        </@panelBody>
    </@panel>
</@body>
