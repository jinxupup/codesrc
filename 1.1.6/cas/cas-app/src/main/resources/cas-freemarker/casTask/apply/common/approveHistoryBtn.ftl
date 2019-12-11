<@buttonAuth code="CAS_APPROVE_HISTORY">
	<@button name="审批历史信息" click="approveHistoryBtn" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
	<script type="text/javascript">
		<#--审批历史信息 -->
		var approveHistoryBtn = function(){
			var url = '${base}/cas_commonDialog/approveHistoryLayout?appNo=${(appNo)!}';
	        dialogInfo('[${(appNo)!}]审批历史信息',840,400,url);
	    }
	</script>
</@buttonAuth>