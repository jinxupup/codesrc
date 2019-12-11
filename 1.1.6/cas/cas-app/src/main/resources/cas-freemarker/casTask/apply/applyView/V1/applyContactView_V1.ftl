<@fieldset legend="第一联系人信息:">
    <@field label="第一联系人中文姓名">
        <@input name="tmAppContact[0].contactName" value="${(mconItemInfo1.contactName)!}" label_only="true"/>
    </@field>
    <@field label="第一联系人与申请人关系">
        <@dictSelect dicttype="ContactRelation" name="tmAppContact[0].contactRelation" value="${(mconItemInfo1.contactRelation)!}" label_only="true"/>
    </@field>
    <@field label="第一联系人移动电话">
        <@input name="tmAppContact[0].contactMobile" value="${(mconItemInfo1.contactMobile)!}" label_only="true"/>
    </@field>
    <@field label="第一联系人联系电话">
        <@input name="tmAppContact[0].contactTelephone" value="${(mconItemInfo1.contactTelephone)!}" label_only="true"/>
    </@field>
    <@field label="联系人类型" hidden="true">
        <@input name="tmAppContact[0].contacType" value="${(tmAppContact.contacType)!'1'}" label_only="true"/>
    </@field>
</@fieldset>
<@fieldset legend="其他联系人信息:">
    <@field label="其他联系人中文姓名">
        <@input name="tmAppContact[1].contactName" value="${(mconItemInfo2.contactName)!}" label_only="true"/>
    </@field>
    <@field label="其他联系人与申请人关系">
        <@dictSelect dicttype="ContactRelation" name="tmAppContact[1].contactRelation" value="${(mconItemInfo2.contactRelation)!}" label_only="true"/>
    </@field>
    <@field label="其他联系人移动电话">
        <@input name="tmAppContact[1].contactMobile" value="${(mconItemInfo2.contactMobile)!}" label_only="true"/>
    </@field>
    <@field label="其他联系人联系电话">
        <@input name="tmAppContact[1].contactTelephone" value="${(mconItemInfo2.contactTelephone)!}" label_only="true"/>
    </@field>
    <@field label="联系人类型" hidden="true">
        <@input name="tmAppContact[1].contacType" value="${(tmAppContact.contacType)!'2'}" label_only="true"/>
    </@field>
</@fieldset>

