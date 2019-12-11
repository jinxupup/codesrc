<#include "dialogJs.ftl"/>
<#--<@buttonAuth code="CIS5_OFFIAL_CARD_CORP">-->
    <@button name="单位信息" click="offialCardCorp" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
    <script>
        var offialCardCorp = function () {
            var url = '${base}/commonDialog/offialCardCorp?appNo=${(appNo)!}';
            dialogInfo('单位信息', 800, 420, url);
        }
    </script>
<#--</@buttonAuth>-->
