<@buttonAuth code="AMS_TASK_TRANSFER">
	<@button name="案件流转记录" click="taskTransRecordBtn" style="margin-left:5px;margin-right:5px;"/>
	<script type="text/javascript">
		<#--审批历史信息 -->
		var taskTransRecordBtn = function(){
			var url = '${base}/ams_commonDialog/taskTransRecordLayout?appNo=${(appNo)!}';
	        dialogInfo('【${(appNo)!}】案件流转记录',850,400,url);
	    }
	</script>
</@buttonAuth>