<@buttonAuth code="AMS_ID_CHECK">
 	<@button name="核身结果" click="idCheckBtn" style="margin-left:5px;margin-right:5px;"/>
	<script>
	    <#--身份核身 -->
		var idCheckBtn = function(){
			var url = '${base}/commonDialog/identifyCheckLayout?appNo=${(appNo)!}';
	        dialogInfo('【${(appNo)!}】核身结果信息',900,400,url);
	    }
	</script>
</@buttonAuth>	
