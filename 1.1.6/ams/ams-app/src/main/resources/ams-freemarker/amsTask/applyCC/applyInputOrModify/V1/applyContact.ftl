<@fieldset legend="第一联系人信息:">
    <@field label="第一联系人中文姓名" point_flag="true" point="*">
        <@input name="tmAppContact[0].contactName" value="${(mconItemInfo1.contactName)!}"/>
    </@field>
    <@field label="第一联系人与申请人关系" point_flag="true" point="*">
        <@dictSelect dicttype="ContactRelation" name="tmAppContact[0].contactRelation" value="${(mconItemInfo1.contactRelation)!}" />
    </@field>
    <@field label="第一联系人移动电话" point_flag="true" point="*">
        <@input name="tmAppContact[0].contactMobile" value="${(mconItemInfo1.contactMobile)!}" />
    </@field>
    <@field label="第一联系人联系电话">
        <@input name="tmAppContact[0].contactTelephone" value="${(mconItemInfo1.contactTelephone)!}"/>
    </@field>
    <@field label="联系人类型" hidden="true">
        <#if (tmAppContact.contacType)??>
        <@input name="tmAppContact[0].contacType" value="${(tmAppContact.contacType)!}"/>
        <#else>
          <@input name="tmAppContact[0].contacType" value="${contacType!'1'}"/>
        </#if>
    </@field>
</@fieldset>
<@fieldset legend="其他联系人信息:">
    <@field label="其他联系人中文姓名" point_flag="true" point="*">
        <@input name="tmAppContact[1].contactName" value="${(mconItemInfo2.contactName)!}"
        valid={"stringlength":"true","stringlength-max":"10","stringlength-message":"请正确填写其他联系人姓名"}/>	</@field>
    <@field label="其他联系人与申请人关系" point_flag="true" point="*">
        <@dictSelect dicttype="ContactRelation" name="tmAppContact[1].contactRelation" value="${(mconItemInfo2.contactRelation)!}"/>
    </@field>
    <@field label="其他联系人移动电话" point_flag="true" point="*">
        <@input name="tmAppContact[1].contactMobile" value="${(mconItemInfo2.contactMobile)!}" valid={"regexp or pattern":"^1[3456789]\\d{9}$","regexp-message":"请输入有效的手机号"} />
    </@field>
    <@field label="其他联系人联系电话">
        <@input name="tmAppContact[1].contactTelephone" value="${(mconItemInfo2.contactTelephone)!}" valid={"regexp or pattern":"^((0\\d{2,3}-\\d{6,8}(-\\d{1,6})?)|(1[3456789]\\d{9}))$","regexp-message":"请输入有效的电话号码"}/>
    </@field>
    <@field label="联系人类型" hidden="true">
        <#if (tmAppContact.contacType)??>
            <@input name="tmAppContact[1].contacType" value="${(tmAppContact.contacType)!}"/>
        <#else>
        <@input name="tmAppContact[1].contacType" value="${contacType!'2'}"/>
        </#if>
    </@field>
</@fieldset>

