<#include "/layout.ftl"/>
<@body>
<@gotop/>
<#--老客户信息弹出框-->
<@panel>
    <@panelBody  style="padding-left:10px;">
    	<@pureTable>
    		<caption>姓名:${(applyName)!} 证件号码:${(applyIdNo)!}</caption>
 			<tr>
				<th>卡号</th>
				<th>卡产品类型</th>
				<th>固定额度</th>
				<th>卡片状态</th>
				<th>有效期</th>
				<th>卡片寄送地址</th>
				<th>申请件编号</th>
				<th>操作</th>
 			</tr>
 			<#if alreadyCardsCardInfoDtos??>
	 			<#list alreadyCardsCardInfoDtos as alreadyCardsCardInfoDto>
	 				<tr>
	 					<td>${(alreadyCardsCardInfoDto.cardNo)!}</td>
	 					<#--<td><@dictSelect options=ams_('tableMap','productForLowerLevel','${(alreadyCardsCardInfoDto.productCd)!}',
	 					'${(tmAppMain.appType)!}') value="${(alreadyCardsCardInfoDto.productCd)!}" label_only="true"/></td>-->
	 					<td><@select value="${(alreadyCardsCardInfoDto.productCd)!}" options=ams_('tableMap','productForStatus','A,B,C') label_only="true"/></td>
	 					<td>${(alreadyCardsCardInfoDto.creditLimit)!}</td>
	 					<td>${(alreadyCardsCardInfoDto.blockCode)!}</td>
	 					<td>${(alreadyCardsCardInfoDto.cardExpireDate?date)!}</td>
	 					<td><@dictSelect dicttype="CardMailerInd" value="${(alreadyCardsCardInfoDto.cardMailerInd)!}" label_only="true"/></td>
	 					<td>${(alreadyCardsCardInfoDto.appNo)!}</td>
	 					<td>
	 						<@button id="applyDetail" name="申请详情" click="applyDetail('${(alreadyCardsCardInfoDto.appNo)!}')"/>
	 					</td>
	 				</tr>
	 			</#list>
	 		</#if>
	   	 </@pureTable>
    </@panelBody>
</@panel>
   
<script>
	<#--申请人详细信息-->
	function applyDetail(appNo){
		if(appNo == "" || appNo == null){
			alert("申请件编号为空，请确认后重试！");
		}else{
			<#--查找是否存在以前的件-->
			$.ajax({
				url:"${base}/finalAudit/hasApplyInfo",
				type:"post",
				dataType : "json",
				data:{'appNo':appNo},
				success:function(res){
					if(res.s){
						window.open("${base}/ams_activiti/handleTask?appNo="+res.msg+"&detailFlag=Y");<#--跳转到申请详情页面-->
					}else{
						alert(res.msg);
					}
				}
			});
		}
	}
</script>
</@body>