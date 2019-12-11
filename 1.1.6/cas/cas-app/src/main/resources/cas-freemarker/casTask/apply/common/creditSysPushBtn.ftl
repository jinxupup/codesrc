<@buttonAuth code="CAS_PUSH_PBOC">
	<@button name="推送人行报告" click="pushPbocBtn" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
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
	<@button name="推送审批结论" click="pushProgressBtn" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
	<script type="text/javascript">
		
		<#--推送审批结论 -->
		var pushProgressBtn = function(){
			if(confirm("是否确认推送审批结论？")){
				$.ajax({
					url : "${base}/cas_common/creditSysPushProgress",
					type : "post",
					dataType : "json",
					data : {"appNo":'${(appNo)!}',"pushType":'1'},
					success : function(res) {
						alert(res.msg);
					}
				});
	    	}
		}
		
	</script>
	<@button name="推送终审结论" click="pushFinalRsBtn" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
	<script type="text/javascript">
		<#--推送审批结论 -->
		var pushFinalRsBtn = function(){
			if(confirm("是否确认推送终审结论？")){
				$.ajax({
					url : "${base}/cas_common/creditSysPushProgress",
					type : "post",
					dataType : "json",
					data : {"appNo":'${(appNo)!}',"pushType":'2'},
					success : function(res) {
						alert(res.msg);
					}
				});
		    }
		}
	</script>
</@buttonAuth>