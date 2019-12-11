<#include "/layout.ftl"/>
<@body>
<#--身份核身弹出框-->
<@panel>
    <@panelBody>
	    <@form>
	    	<@fieldset id="identifyCheck">
				<div style="float:left;margin-left:15px;margin-top:10px">
					<img id="urlStr" src="${(urlStr)!}" width="100" height="120" />
				</div>
				<div style="float:left;margin-left:28px;margin-top:10px">
					<div>
						<label>核身结果：</label>
						<span id="rspMsg">${(retMsg)!''}</span>
					</div>
					<div style="margin-top:6px">
						<label>核身时间：</label>
						<span id="queryTime">${(queryTime)!''}</span>
					</div>
				</div>
	    	</@fieldset>
	    	<@toolbar align="right" style="margin-right:15px;">
			    <@button name="联机身份核身" click="identifyCheckBtn" style="margin-right:5px;"/>
				<@button name="核身记录查询" click="idCheckRecordBtn" />
			</@toolbar>
		 </@form>  	
		 
		<@fieldset id="idCheckRecord" legend="核身记录:" style="display:none">
			<@table id="idCheckRecordList" page_size="5" load_auto="false">
				<@th title="申请件编号" field="appNo" />
				<@th title="客户姓名" field="custName" />
				<@th title="证件类型" field="idType" />
				<@th title="证件号码" field="idNo" />
				<@th title="时间" field="queryTime" />
				<@th title="时间" render="true" >
					<@thDate value="{{row.queryTime}}" datetype="datetime"/>
				</@th>
				<@th title="操作员" field="opUserNo" />
				<@th title="结果" field="retMsg" />
			</@table>
		</@fieldset>
    </@panelBody>
</@panel>

<script>
	    <#--联机身份核身 -->
		var identifyCheckBtn = function(){
			$.ajax({
				url : "${base}/commonDialog/idCheckNciic",
				type : "post",
				dataType : "json",
				data : {"appNo":'${(appNo)!}',"isLocal":false},
				success : function(res) {
					if(res.s){
						var map = r.obj;
						$('#urlStr').attr('src',res.obj.urlStr);
						$('#queryTime').text(res.obj.queryTime);
						$('#rspMsg').text(res.obj.repMsg);
					}else{
						$('#rspMsg').css('color','red').text(res.msg);
					}
				}
			});
	    }
	    
	    <#--核身记录查询 -->
	    var idCheckRecordBtn = function(){
	    	$('#idCheckRecord').css("display","block");
	    	var id_params = {url:ar_.randomUrl("${base}/commonDialog/localIdentifyCheckList?appNo=${(appNo)!}")};
			$("#idCheckRecordList").bootstrapTable("refresh",id_params);
	    }
	</script>
    
</@body>