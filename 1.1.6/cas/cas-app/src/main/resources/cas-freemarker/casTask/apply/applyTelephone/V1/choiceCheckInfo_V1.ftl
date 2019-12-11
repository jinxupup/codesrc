<@fieldset legend="选核问题：">
	<div>
		<@field hidden="true">
			<@input id="choiceNum" value="${(choiceCheckList?size)!'1'}"/> 
		</@field>
		<@pureTable id="choiceCheckInfo">
		<thead>
			<tr>
				<th width="15%">问题内容</th>
				<th width="52%">问题答案</th>
				<th width="8%">问题结果</th>
				<th width="20%">备注/备忘</th>
				<th width="5%">操作</th>
			</tr>
		</thead>
			<tbody id="choiceTable">
				<#if choiceCheckList?? && (choiceCheckList?size>0)>
				    <#list choiceCheckList as choiceCheck>
						<#assign num = choiceCheck_index>
					    <tr id="tr_Choice${(num)!}">                                                          
			               <td><@dictSelect id="askContent${(num)!}" dicttype="TelCheckValue" change="askContentChange('${(num)!}',this)"
								name="choiceCheckList[${(num)!}].askContent" value="${(choiceCheck.askContent)!}"/></td>
			               <td><@input id="answer${(num)!}" name="choiceCheckList[${(num)!}].answer" value="${(choiceCheck.answer)!}"/></td>
			               <td><@dictSelect id="result${(num)!}" dicttype="Indicator" name="choiceCheckList[${(num)!}].result" value="${(choiceCheck.result)!}"/></td>
			               <td><@input name="choiceCheckList[${(num)!}].memo" value="${(choiceCheck.memo)!}"/></td>
					   	   <td><#if !choiceCheck_has_next><@button name="新建" fa="plus" click="addChoiceInfo()"/></#if></td>
					    </tr>
				    </#list>
				<#else>
					<tr id="tr_Choice0">
		               <td><@dictSelect id="askContent0" dicttype="TelCheckValue" change="askContentChange('0',this)" name="choiceCheckList[${(num)!}].askContent"/></td>
		               <td><@input id="answer0" name="choiceCheckList[${(num)!}].answer"/></td>
		               <td><@dictSelect id="result0" dicttype="Indicator" name="choiceCheckList[${(num)!}].result"/></td>
		               <td><@input name="choiceCheckList[${(num)!}].memo"/></td>
				   	   <td><@button name="新建" fa="plus" click="addChoiceInfo()"/></td>
				    </tr>
			    </#if>
			</tbody>
		</@pureTable>
		 
		<script >
			var choiceSize = ${(choiceCheckList?size)!'1'}-1;
			
		    <#--选核问题新建按钮-->
		    var addChoiceInfo = function(){
		   		var num = $('#choiceNum').val();
		   		var flag = validChoice(choiceSize,num);//非空判断
		   		if(flag == false){
		   			return;
		   		}
		    	var trHtml = $("#tr_Choice0").html();
		    	$("#choiceTable").append('<tr id="tr_Choice'+num+'">'+ trHtml +'</tr>');
		    	$("#tr_Choice"+num).children("td:eq(0)").children("select").val('')
		    		.attr({"id":"askContent"+num,"name":"choiceCheckList["+num+"].askContent","onChange":"askContentChange('"+num+"',this)"});
		    	$("#tr_Choice"+num).children("td:eq(1)").children("input")
		    		.attr({"id":"answer"+num,"name":"choiceCheckList["+num+"].answer","value":""});
		    	$("#tr_Choice"+num).children("td:eq(2)").children("select").val('')
		    		.attr({"id":"result"+num,"name":"choiceCheckList["+num+"].result"});
		    	$("#tr_Choice"+num).children("td:eq(3)").children("input")
		    		.attr({"name":"choiceCheckList["+num+"].memo","value":""});
		    	$("#tr_Choice"+num).children("td:eq(4)")
		    		.html('<button type="button" id="" name="删除" value="" class="btn btn-sm btn-danger" style=""><i class="fa fa-close"></i>删除</button>')
		    		.children("button").attr("onclick","delectChoiceInfo('tr_Choice"+num+"')");
		    	num++;
		    	$('#choiceNum').val(num);
			}
			
			<#--选核问题删除按钮-->
			var delectChoiceInfo = function(trId){
				$('#'+trId).remove();
			}
			
			<#--选核问题问题内容改变-->
			var askContentChange = function(num,that){
				var askContent = $(that).val();
				var answer = '';
				if(askContent == 'A'){
		    		answer = '${(applyOperateDto.homeState)!}'+'${(applyOperateDto.homeCity)!}'
		    			+'${(applyOperateDto.homeZone)!}'+'${(applyOperateDto.homeAdd)!}';
				}else if(askContent == 'B'){
					answer = '联系姓名:'+'${(applyOperateDto.contactName)!}'+'，联系电话:'+'${(applyOperateDto.contactMobile)!}';
				}else if(askContent == 'C'){
					answer = '姓名:'+'${(applyOperateDto.name)!}'+'，证件号码:'+'${(applyOperateDto.idNo)!}'
						+'，联系电话:'+'${(applyOperateDto.cellphone)!}';			
				}else if(askContent == 'D'){
					var stmtMediaType = '${(applyOperateDto.stmtMediaType)!}';
					if(stmtMediaType == 'E'){
						answer = '电子账单发送到电子邮箱'+'${(applyOperateDto.email)!}';
					}else if(stmtMediaType == 'P'){
						answer = '纸质账单寄送到'+'${(applyOperateDto.stmtMailAddrInd)!}';
					}else if(stmtMediaType == 'B'){
						answer = '电子账单发送到电子邮箱'+'${(applyOperateDto.email)!}'+'，纸质账单寄送到'+'${(applyOperateDto.stmtMailAddrInd)!}';
					}
				}else if(askContent == 'E'){
					answer = '推广人姓名:'+'${(applyOperateDto.spreaderName)!}'+'，联系电话:'+'${(applyOperateDto.spreaderTelephone)!}';
				}
				$('#answer'+num).val(answer);
			}
		</script>
	</div>
</@fieldset>