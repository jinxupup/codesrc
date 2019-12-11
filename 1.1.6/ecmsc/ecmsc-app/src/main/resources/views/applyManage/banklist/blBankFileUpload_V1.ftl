<#include "/layout.ftl" />

<@body>
    <@panel head="申请件批量导入界面">
        <@panelBody>
            <@tab id="tab">
                <@tabNav>
                    <@tabTitle pane_id="pane1" active="${isEdit?string('false','true')}" title="网点机构批量导入新增666"/>
                    <@tabTitle pane_id="pane2" active="${isEdit?string('true','false')}" title="网点机构导入信息列表666"/>
                </@tabNav>
                <@tabContent>
                    <@tabPane id="pane1" active="${isEdit?string('false','true')}">
                        <#include "addblBankFile_V1.ftl"/>
                    </@tabPane>
                    <@tabPane id="pane2" active="${isEdit?string('true','false')}">
                        <#include "blBankFileList_V1.ftl"/>
                    </@tabPane>
                </@tabContent>
            </@tab>
        </@panelBody>
    </@panel>
</@body>
