<#include "/layout.ftl"/>
<@body>
	<@panel>
    	<@panelBody>
    		<@form id="telFrom" action="applyBasicCheck/saveTelcheckInfo?appNo=${(appNo)!}" after="after">
				<@field hidden="true">
					<@input id="num" value="${(applyTelInquiryRecordDtoList?size)!'1'}"/>
				</@field>
				<@pureTable id="telSurveyInfo">
					<thead>
						<tr>
							<th width="20%">致电类型</th>
							<th width="15%">致电号码</th>
							<th width="20%">致电日期</th>
							<th width="15%">致电结果</th>
							<th width="20%">备注</th>
							<th width="10%">操作</th>
					     </tr>
					</thead>
					<tbody id="tbodyId">
					    <#if applyTelInquiryRecordDtoList?? && (applyTelInquiryRecordDtoList?size>0)>
						    <#list applyTelInquiryRecordDtoList as applyTelInquiryRecordDto>
								<#assign num = applyTelInquiryRecordDto_index>
							    <tr id="tr${(num)!}">
					               <td><@dictSelect id="telType${(num)!}" dicttype="CallType" change="telTypeChange('${(num)!}',this)"
					               		name="applyTelInquiryRecordDto[${(num)!}].telType" value="${(applyTelInquiryRecordDto.telType)!}"/></td>
					               <td><@input id="phone${(num)!}" name="applyTelInquiryRecordDto[${(num)!}].phone" value="${(applyTelInquiryRecordDto.phone)!}" readonly="true"/></td>
					               <td><@date id="telDate${(num)!}" datetype="datetime" name="applyTelInquiryRecordDto[${(num)!}].telDate" value="${(applyTelInquiryRecordDto.telDate?datetime)!}" readonly="true"/></td>
					               <td><@dictSelect id="telResult${(num)!}" dicttype="CallResult" change="telResultChange(${(num)!},this)"
					               		name="applyTelInquiryRecordDto[${(num)!}].telResult" value="${(applyTelInquiryRecordDto.telResult)!}"/></td>
					               <td><@input name="applyTelInquiryRecordDto[${(num)!}].memo" value="${(applyTelInquiryRecordDto.memo)!}"/></td>
					               <td><#if !applyTelInquiryRecordDto_has_next><@button name="拨打电话" click="addTelephone()" fa="plus"/></#if></td>   
							    </tr>
						    </#list>
						<#else>
							<tr id="tr0">
				               <td><@dictSelect id="telType0" dicttype="CallType" change="telTypeChange('0',this)" name="applyTelInquiryRecordDto[0].telType"/></td>
				               <td><@input id="phone0" name="applyTelInquiryRecordDto[0].phone" readonly="true"/></td>
				               <td><@date id="telDate0" datetype="datetime" name="applyTelInquiryRecordDto[0].telDate" readonly="true"/></td>
				               <td><@dictSelect id="telResult0" dicttype="CallResult" change="telResultChange(0)" name="applyTelInquiryRecordDto[0].telResult" /></td>
				               <td><@input id="memo0" name="applyTelInquiryRecordDto[0].memo" /></td>
				               <td><@button name="拨打电话" click="addTelephone()" fa="plus"/></td>
						    </tr>
					    </#if>
					</tbody>
				</@pureTable>
				<@toolbar align="center">
					<@submitButton name="提交"  fa="send" id="submitBtn"/>
					<@button name="返回"  fa="send" click="back" />
				</@toolbar>
			</@form>
    	</@panelBody>
    </@panel>
    <script type="text/javascript">
		var telSize = ${(applyTelInquiryRecordDtoList?size)!'1'}-1;
		
		<#--电话调查添加按钮-->
	    var addTelephone = function(){
	   		var num = $('#num').val();
	   		var flag = validTel(telSize,num);//非空判断
	   		if(flag == false){
	   			return;
	   		}
	    	var trHtml = $("#tr0").html();
	    	$("#tbodyId").append('<tr id="tr'+num+'">'+ trHtml +'</tr>');
	    	$("#tr"+num).children("td:eq(0)").children("select").val('')
	    		.attr({"id":"telType"+num,"name":"applyTelInquiryRecordDto["+num+"].telType","onChange":"telTypeChange('"+num+"',this)"});
	    	$("#tr"+num).children("td:eq(1)").children("input")
	    		.attr({"id":"phone"+num,"name":"applyTelInquiryRecordDto["+num+"].phone","value":""});
	    	$("#tr"+num).children("td:eq(2)").children("input")
	    		.attr({"id":"telDate"+num,"name":"applyTelInquiryRecordDto["+num+"].telDate","value":""});
	    	$("#tr"+num).children("td:eq(3)").children("select").val('')
	    		.attr({"id":"telResult"+num,"name":"applyTelInquiryRecordDto["+num+"].telResult","onChange":"telResultChange('"+num+"')"});
	    	$("#tr"+num).children("td:eq(4)").children("input")
	    		.attr({"name":"applyTelInquiryRecordDto["+num+"].memo","value":""});
	    	$("#tr"+num).children("td:eq(5)")
	    		.html('<button type="button" id="" name="删除" value="" class="btn btn-sm btn-danger" style=""><i class="fa fa-close"></i>删除</button>')
	       		.children("button").attr("onclick","delectTelephone('tr"+num+"')");
	        num++;
	    	$('#num').val(num);
		}
		
		<#--电话调查问题项非空校验-->
		/**
		*@param sizeNum 表格的总行数
		*@param num 新总行数
		*/
		function validTel(telSize,num){
			for(var i=telSize;i<num;i++){
	   			var telType = $('#telType'+i).val();
	   			var telResult = $('#telResult'+i).val();
	   			if(telType != undefined && telResult != undefined){
	   				if(telType == ''){
	   					alert("[致电类型]不能为空!");
	   					return false;
	   				}
	   				if(telResult == ''){
	   					alert("[致电结果]不能为空!");
	   					return false;
	   				}
	   			}
	   		}
	   		return true;
		}

		<#--电话调查删除按钮-->
		var delectTelephone = function(trId){
			$('#'+trId).remove();
		}
		
		<#--电话类型改变-->
		var telTypeChange = function(num,that){
			var callType = $(that).val();
			var phone = '';
			if(callType == 'A'){
				phone = '${(telMap['A'])!}';
			}else if(callType == 'B'){
				phone = '${(telMap['B'])!}';
			}else if(callType == 'C'){
				phone = '${(telMap['C'])!}';
			}else if(callType == 'D'){
				phone = '${(telMap['D'])!}';
			}else if(callType == 'E'){
				phone = '${(telMap['E'])!}';
			}else if(callType == 'F'){
				phone = '${(telMap['F'])!}';
			}else if(callType == 'G'){
				phone = '${(telMap['G'])!}';
			}else if(callType == 'H'){
				phone = '${(telMap['H'])!}';
			}else if(callType == 'I'){
				phone = '${(telMap['I'])!}';
			}else if(callType == 'J'){
				phone = '${(telMap['J'])!}';
			}else{
				phone = '';
			}
			$('#phone'+num).val(phone);
		}
		
		<#--致电结果改变-->
		function telResultChange(num,that){
			if($(that).val()==''){
				$("#telDate"+num).val('');
			}else{
				var date =ar_.datetimeFormat(new Date());
		  		$("#telDate"+num).val(date);
			}
		}
		
		<#--提交操作之后-->
		var after = function(res){
			alert(res.msg);
			if(res.s){
				$('#submitBtn').unicornButtonDisable(true);
				var dd = ar_.getDialog(parent);
				dd.close(true).remove();<#-- 关闭（隐藏）并销毁对话框-->
				return false;
			}
		}
		<#--返回按钮-->
		var back = function(){
			var dd = ar_.getDialog(parent);
			dd.close(false).remove();<#-- 关闭（隐藏）并销毁对话框-->
		}
	</script>
</@body>