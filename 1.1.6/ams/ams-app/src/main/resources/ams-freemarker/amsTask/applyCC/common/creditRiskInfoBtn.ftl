<@buttonAuth code="AMS_CREDIT_RISK_INFO">
	<@button name="风控信息" click="creditRiskInfoBtn" style="margin-left:5px;margin-right:5px;"/>
	<script type="text/javascript">
		<#--风控信息 -->
		var creditRiskInfoBtn = function(){
			var url = '${base}/commonDialog/creditRiskInfoLayout?appNo=${(appNo)!}';
		    dialogInfo('【${(appNo)!}】风控信息',1250,500,url);
		}
	</script>
</@buttonAuth>