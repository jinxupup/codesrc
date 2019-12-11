<#if hasOldAppNo??>
	<@button name="重审件查询 " click="retrialQueryBtn('${(hasOldAppNo)!}')" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
</#if>
	<script type="text/javascript">
		var retrialQueryBtn = function(appNo){
	        window.open('${base}/cas_activiti/handleTask?appNo='+appNo+'&detailFlag=Y&detailBtnFlag=Y');
	    }
	</script>