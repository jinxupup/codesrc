<#include "/layout.ftl"/>	
<@body>
	<#--历史申请信息弹出框-->
	<@panel>
    	<@panelBody  style="padding-left:10px;">
	        <@pureTable>
				<tr>
					<th width="15%">申请件编号</th>
					<th width="15%">审批状态</th>
					<th width="10%">申请类型</th>
					<th width="10%">申请卡产品</th>
					<th width="8%">操作员ID</th>
					<th width="15%">申请日期</th>
					<th width="10%">拒绝原因码</th>
					<#--<th width="10%">备注</th>-->
					<th width="7%">操作</th>
	 			</tr>
	 			<#if applyInfoPreDtoList??>
		 			<#list applyInfoPreDtoList as applyInfoPreDto>
			 			<tr>
							<td>${(applyInfoPreDto.appNo)!}</td>
							<td><@dictSelect dicttype="RtfState" value="${(applyInfoPreDto.rtfState)!}" label_only="true"/></td>
							<td><@dictSelect dicttype="AppType" value="${(applyInfoPreDto.appType)!}" label_only="true"/></td>
							<td><@select options=ams_('tableMap','productForStatus','')  value="${(applyInfoPreDto.productCd)!}" label_only="true"/></td>
							<td>${(applyInfoPreDto.createUser)!}</td>
							<td>${(applyInfoPreDto.createDate?datetime)!}</td>
							<td><@dictSelect dicttype="ApplyRejectReason" value="${(applyInfoPreDto.refuseCode)!}" label_only="true"/></td>
							<#--<td>${(applyInfoPreDto.remark)!}</td>-->
							<td><@button click="applyDetailBtn('${(applyInfoPreDto.appNo)!}')" name="查看详情"/></td>
			 			</tr>
			 		</#list>
		 		</#if>
			</@pureTable>
    	</@panelBody>
    </@panel>
    
    <script type="text/javascript">
    	<#--详情按钮-->
    	var applyDetailBtn = function(appNo){
    		window.open('${base}/ams_activiti/handleTask?appNo='+appNo+'&detailFlag=Y&detailBtnFlag=Y');
    	}
    </script>
</@body>