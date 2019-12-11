<@buttonAuth code="AMS_BANK_INNER_CREDIT_INFO">
    <@button name="信贷信息" click="creditInformationBtn" style="margin-left:5px;margin-right:5px;"/>
<script>
    var creditInformationBtn = function () {
        var url = '${base}/commonDialog/creditInformation?appNo=${(appNo)!}';
        dialogInfo('【${(appNo)!}】个人信贷信息', 1000, 600, url);
    }
</script>
</@buttonAuth>
