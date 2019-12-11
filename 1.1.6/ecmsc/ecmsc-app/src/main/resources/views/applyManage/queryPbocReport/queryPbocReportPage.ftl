<#include "/layout.ftl"/>
<@body>
	<@panelBody>
		<@form id="queryForm" cols="4">
			<@row>
				<@field label="证件类型">
					<@dictSelect id="idType" dicttype="IdType" change="idTypeChange" nullable="false" name="idType" value="I" valid={"notempty":"true","notEmpy-message":"不能为空"}/>
				</@field>
				<@field label="证件号码">
					<@input id="idNo" name="idNo" value="" valid={"notempty":"true","notEmpy-message":"不能为空"}/>
				</@field>
				<@field label="姓名">
					<@input id="name" name="name"  value="" valid={"notempty":"true","notEmpy-message":"不能为空"}/>
				</@field>
				<@button name="查询" fa="search" click="queryPbocReport"/>
			</@row>
		</@form>
		<div id="pbocReport" >
			<div id="msgTip" style="color:red;font-size:1.3em;padding-top:70px;padding-left:500px;"></div>
		</div>
	</@panelBody>
	
	<script type="text/javascript">
		var queryPbocReport = function(){
			if($("#queryForm").unicornValidForm()){
		 		$.ajax({
	     			type: 'POST',
		     		async: false,
					url: '${base}/pbocReportQuery/getPbocReport',
					data: {'name':$('#name').val(),'idType':$('#idType').val(),'idNo':$('#idNo').val()},
					dataType: 'json',
					success:function(res){
						if(res.s){
							$('#pbocReport').load("${base}/pbocReportQuery/sendPbocReport #pbocBody",{'appNo':res.msg});
						}else{
							$('#msgTip').text(res.msg);
						}
		    		}
				});
		 	}
		}
	</script>
</@body>