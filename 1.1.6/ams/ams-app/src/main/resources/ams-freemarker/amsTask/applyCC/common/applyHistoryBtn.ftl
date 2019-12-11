<@buttonAuth code="AMS_APPLY_HISTORY">
	<@button name="历史申请信息" click="applyHistoryBtn" fa="history" style="margin-left:5px;margin-right:5px;"/>
	<script type="text/javascript">
		<#--历史申请信息 -->
		var applyHistoryBtn = function(){
			var url = '${base}/ams_commonDialog/applyHistoryLayout?appNo=${(appNo)!}';
	        dialogInfo('【${(appNo)!}】历史申请信息',1000,500,url);
	    }
	</script>
</@buttonAuth>