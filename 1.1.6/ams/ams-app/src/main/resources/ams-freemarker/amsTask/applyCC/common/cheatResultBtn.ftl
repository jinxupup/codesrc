<@buttonAuth code="AMS_CHEAT_RESULT">
	<@button name="欺诈检查结果" click="cheatResultBtn()" style="margin-left:5px;margin-right:5px;"/>
	<script type="text/javascript">
		<#--欺诈检查结果 -->
		var cheatResultBtn = function(){
			var url = '${base}/commonDialog/cheatCheckLayout?appNo=${(appNo)!}&isOperate=${(isOperate)!}';
	        dialogInfo('【${(appNo)!}】欺诈检查结果信息',800,420,url);
	    }
	</script>
</@buttonAuth>