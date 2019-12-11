<@buttonAuth code="AMS_SCORE_INFO">
	<@button  name="评分信息" click="scoreInfoBtn" style="margin-left:5px;margin-right:5px;"/>
	<script type="text/javascript">
		<#--评分信息 -->
		var scoreInfoBtn = function(){
			var url = '${base}/commonDialog/pointProcessLayout?appNo=${(appNo)!}';
	        dialogInfo('【${(appNo)!}】评分信息',1000,600,url);
	    }
	</script>
</@buttonAuth>