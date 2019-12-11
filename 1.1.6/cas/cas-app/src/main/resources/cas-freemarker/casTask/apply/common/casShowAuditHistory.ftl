<@buttonAuth code="CAS_AUDIT_VIEW">
 	<@button  name="审计历史" click="openAudit" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
</@buttonAuth>
<script type="text/javascript">
    var openAudit = function(){
        var url = "${base}/cas_common/openAudit?appNo=" + "${(appNo)!}";
        dialogInfo('【${(appNo)!}】历史申请信息',1300,700,url);
    }
</script>