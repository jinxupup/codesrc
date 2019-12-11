<#if applyOperateDto?? && applyOperateDto.isOldCust?? && applyOperateDto.isOldCust=="Y">
	<@button name="老客户信息 " click="oldCustInfoBtn" fa="edit" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
	<script type="text/javascript">
		<#--老客户信息  -->
		var oldCustInfoBtn = function(){
			var url = '${base}/commonDialog/alreadyCardLayout?appNo=${(appNo)!}';
	        dialogInfo('[${(appNo)!}]老客户信息',1000,600,url);
	    }
	</script>
</#if>