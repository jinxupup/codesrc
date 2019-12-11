<#if pageOnOffParamDto?? && pageOnOffParamDto.isUsedIdCheck?? && pageOnOffParamDto.isUsedIdCheck == 'Y'>
	<@fieldset id="identifyCheck" legend="身份核身:">
		<@row>
	    	<div style="float:left;margin-left:15px;margin-top:10px">
				<img id="urlStr" src="" width="160px" height="200px"/>
			</div>
			<div style="float:left;margin-left:28px;margin-top:10px">
				<div>
					<label>核身结果：</label>
					<span id="rspMsg"></span>
				</div>
				<div style="margin-top:6px">
			    	<label>核身时间：</label>
			    	<span id="queryTime"></span>
		    	</div>
			</div>
		</@row>
	</@fieldset>
	<@toolbar align="center">
		<@button name="联机身份核身" click="identifyCheckBtn"/>
	</@toolbar>
	<script type="text/javascript">
		var identifyCheckBtn = function(){
			idCheckNciic(false);
		}
		<#--身份核身（本地：true|联网：false）-->
		function idCheckNciic(isLocal){
			$.ajax({
				url : "${base}/ams_commonDialog/idCheckNciic",
				type : "post",
				dataType : "json",
				data : {"appNo":$('#appNo').val(),"isLocal":isLocal},
				success : function(res) {
					if(res.s){
						var map = res.obj;
						$('#urlStr').attr('src',res.obj.urlStr);
						$('#queryTime').text(res.obj.queryTime);
						$('#rspMsg').text(res.obj.repMsg);
					}else{
						$('#rspMsg').css('color','red').text(res.msg);
					}
				}
			});
		}
	</script>
</#if>
<@fieldset legend="历史申请信息:">
	<@table id="historyInfo" url="" load_auto="false">
		<@th checkbox="true"/>
		<@th title="申请件编号" field="appNo" />
		<@th title="审批状态" field="rtfState"render="true" >
			<@thDictName dicttype="RtfState" value="{{row.rtfState}}" />
		</@th>
        <@th title="申请类型" field="appType" render="true" >
			<@thDictName dicttype="AppType" value="{{row.appType}}" />
		</@th>
        <@th title="操作员ID" field="createUser" />
        <@th title="申请日期" sortable="true" render="true">
			<@thDate  value="{{row.createDate}}" />
		</@th>
        <@th title="拒绝原因码" field="refuseCode" />
        <@th title="卡号" field="cardNo" />
        <@th title="卡产品代码" field="productCd" render="true" >
			<@thGetName options=ams_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="false" />
		</@th>		              
        <@th title="信用额度" field="creditLimit" />
        <@th title="锁定码" field="blockCode" />
        <@th title="卡片有效日期" field="cardExpireDate" />  
        <@th title="备注" field="remark" />
        <@th title="操作" render="true">
           <@button name="导入历史信息" click="importHisBtn('{{row.appNo}}','{{row.appType}}')"/>
        </@th>
	</@table>
</@fieldset>
<@toolbar align="center">
	<@button name="继续录入" click="continueInput" />		               
</@toolbar>

<script type="text/javascript">
	var importHisBtn = function(historyAppNo,historyAppType){
		var appType = $('#appType').val();
		var appNo = $('#appNo').val();
		window.location.href='${base}/ams_applyInput/importHistoryInfo?historyAppNo='+historyAppNo+'&historyAppType='+historyAppType+'&appNo='+appNo+'&appType='+appType;
	}
</script>