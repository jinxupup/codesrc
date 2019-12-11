<@buttonAuth code="CAS_PUSH_PBOC">
	<@button name="推送人行报告" click="pushPbocBtn" style="margin-left:5px;margin-right:5px;"/>
	<script type="text/javascript">
		<#--推送人行报告 -->
		var pushPbocBtn = function(){
			$.ajax({
				url : "${base}/commonDialog/creditSysPushPboc",
				type : "post",
				dataType : "json",
				data : {"appNo":'${(appNo)!}'},
				success : function(res) {
					alert(res.msg);
				}
			});
	    }
	    
	    
	</script>
</@buttonAuth>
<@buttonAuth code="CAS_PUSH_PROGRESS">
	<@button name="推送审批结论" click="pushProgressBtn" style="margin-left:5px;margin-right:5px;"/>
	<script type="text/javascript">
		<#--推送审批结论 -->
		var RtfStateProgressBtn = function(){
			$.ajax({
				url : "${base}/commonDialog/creditSysPushProgress",
				type : "post",
				dataType : "json",
				data : {"appNo":'${(appNo)!}'},
				success : function(res) {
					alert(res.msg);
				}
			});
	    }
	</script>
</@buttonAuth>