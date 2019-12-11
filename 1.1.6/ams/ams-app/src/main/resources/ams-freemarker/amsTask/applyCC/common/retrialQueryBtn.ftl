<#if hasOldAppNo??>
	<@button name="重审件查询 " click="retrialQueryBtn('${(hasOldAppNo)!}')" style="margin-left:5px;margin-right:5px;"/>
</#if>
	<script type="text/javascript">
		var retrialQueryBtn = function(appNo){
	        window.open('${base}/ams_activiti/handleTask?appNo='+appNo+'&detailFlag=Y&detailBtnFlag=Y');
	    }
	</script>