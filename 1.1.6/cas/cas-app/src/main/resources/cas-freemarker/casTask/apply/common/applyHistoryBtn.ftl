<@buttonAuth code="CAS_APPLY_HISTORY">
	<@button name="历史申请信息" click="applyHistoryBtn" fa="history" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
	<script type="text/javascript">
		<#--历史申请信息 -->
		var applyHistoryBtn = function(){
			var url = '${base}/cas_commonDialog/applyHistoryLayout?appNo=${(appNo)!}&button=button';
	        dialogInfo('[${(appNo)!}]历史申请信息',1000,500,url);
	    }
	</script>
</@buttonAuth>