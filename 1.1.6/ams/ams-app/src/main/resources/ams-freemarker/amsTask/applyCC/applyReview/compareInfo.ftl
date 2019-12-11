<#include "/layout.ftl"/>
<@body>
	<@pureTable>
	 	<tr>
			<th width="15%">信息域</th>
            <th width="21%">对比项</th>
            <th width="32%">录入值</th>
            <th width="32%">复核值</th>
		</tr>
		<#if compareFieldsMap??>
			<#list compareFieldsMap?keys as key>
				<#if key == 'commonTab'>
					<#list compareFieldsMap[key] as compareInfo>
						<tr>
					     	<td style="color:red;">
					     		<#if compareInfo_index == 0>
					     			卡复核信息
					     		</#if>
					     	</td>
					        <td >
					     		${(compareInfo.option)!}
					     	</td>
					     	<td >
					     		${(compareInfo.inputValue)!}
					     	</td>
					     	<td >
					     		${(compareInfo.reviewValue)!}
					     	</td>
				        </tr>
					</#list> 
				</#if>
				<#if key == 'mainTab'>
					<#list compareFieldsMap[key] as compareInfo>
						<tr>
					     	<td style="color:red;">
					     		<#if compareInfo_index == 0>
					     			主卡复核项
					     		</#if>
					     	</td>
					        <td >
					     		${(compareInfo.option)!}
					     	</td>
					     	<td >
					     		${(compareInfo.inputValue)!}
					     	</td>
					     	<td >
					     		${(compareInfo.reviewValue)!}
					     	</td>
				        </tr>
					</#list> 
				</#if>
				<#if key == 'attachTab_0'>
					<#list compareFieldsMap[key] as compareInfo>
						<tr>
					     	<td style="color:red;">
					     		<#if compareInfo_index == 0>
					     			${(appType?? && appType == 'S')?string('附卡复核项','附卡1复核项')}
					     		</#if>
					     	</td>
					        <td >
					     		${(compareInfo.option)!}
					     	</td>
					     	<td >
					     		${(compareInfo.inputValue)!}
					     	</td>
					     	<td >
					     		${(compareInfo.reviewValue)!}
					     	</td>
				        </tr>
					</#list> 
				</#if>
				<#if key == 'attachTab_1'>
					<#list compareFieldsMap[key] as compareInfo>
						<tr>
					     	<td style="color:red;">
					     		<#if compareInfo_index == 0>
					     			附卡2复核项
					     		</#if>
					     	</td>
					        <td >
					     		${(compareInfo.option)!}
					     	</td>
					     	<td >
					     		${(compareInfo.inputValue)!}
					     	</td>
					     	<td >
					     		${(compareInfo.reviewValue)!}
					     	</td>
				        </tr>
					</#list>
				</#if>
				<#if key == 'attachTab_2'>
					<#list compareFieldsMap[key] as compareInfo>
						<tr>
					     	<td style="color:red;">
					     		<#if compareInfo_index == 0>
					     			附卡3复核项
					     		</#if>
					     	</td>
					        <td >
					     		${(compareInfo.option)!}
					     	</td>
					     	<td >
					     		${(compareInfo.inputValue)!}
					     	</td>
					     	<td >
					     		${(compareInfo.reviewValue)!}
					     	</td>
				        </tr>
					</#list> 
				</#if>
			</#list>
		</#if>
	</@pureTable>
	<@form id="subForm"  >
		<@field hidden="true">
			<@input name="appNo" value="${(appNo)!}"/>
			<@input name="taskId" value="${(taskId)!}"/>
			<@input name="remark" value="${(remark)!}"/>
			<@input id="formKey" value="${(formKey)!}"/>
		</@field>  
		<@toolbar align="center">
			<@button name="提交"  fa="send" id="submitBtn" />		
		</@toolbar>
	</@form>
	
	<script type="text/javascript">
		<#--录入与复核项的值不一致的字段数目-->
		var diffNum=${(diffNum)!};
		<#--返回-->
		$("undo").on('click',function(){
			this.close();
		});	
		<#--提交复核数据-->
		  $("#submitBtn").on('click',function(){
		  	$('#submitBtn').attr("disabled","disabled");
		  	<#--校验是否有不一致的值-->
			  	if(	diffNum > 0){
					alert("录入值与复核值不一致,请检查！");
					$('#submitBtn').removeAttr("disabled");
					return;
				}
				var flag = 'false';//默认需要继续复核功能
				$.ajax({
					type: "POST",
					data:$('#subForm').serialize(),
					dataType: 'json',
					url: "${base}/ams_review/applyInputReviewSubmit",
					success: function(res){
						if(res.s){
							<#-- 是否需要继续复核开关参数，为Y时需要此功能 -->
							<#if pageOnOffParamDto?? && pageOnOffParamDto.isContinueReview?? && pageOnOffParamDto.isContinueReview == 'Y'>
								if(!confirm("操作成功,是否继续复核新件?")){
									flag = 'true';
								}
							<#else>
								flag = 'true';
							</#if>
								var dd = ar_.getDialog(parent);
								dd.close(flag).remove();<#-- 关闭（隐藏）并销毁对话框-->
								return false;
							
						}else{
							$('#submitBtn').removeAttr("disabled");
							alert(res.msg);
						}
					}
				});
		  	});
		
</script>
</@body>