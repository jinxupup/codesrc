<@buttonAuth code="CAS_CREDIT_RISK_INFO">
	<@button name="决策信息" click="creditRiskInfoBtn" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
	<script type="text/javascript">
		var appNo = '${(appNo)!}';
		<#--风控信息 -->
		var creditRiskInfoBtn = function(){
			var url = '${base}/cas_commonDialog/creditRiskInfoLayout?appNo=${(appNo)!}';
		    dialogInfo('[${(appNo)!}]决策信息',1250,500,url);
		}
	</script>
</@buttonAuth>