<@buttonAuth code="CAS_MODIFY_HISTORY">
	<@button name="修改历史信息" click="modifyHistoryBtn" fa="edit" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
	<script type="text/javascript">
		<#--修改历史信息 -->
		var modifyHistoryBtn = function(){
			var url = '${base}/cas_commonDialog/updateHistoryLayout?appNo=${(appNo)!}';
	        dialogInfo('[${(appNo)!}]修改历史信息',800,420,url);
	    }
	</script>
</@buttonAuth>