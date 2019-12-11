<@fieldset legend="拨打电话：">
	<div>
		<#--<@field hidden="true">
			<@input id="num" value="${(applyTelInquiryRecordDtoList?size)!'1'}"/>
		</@field>-->
		<@pureTable id="telSurveyInfo">
			<thead>
			<tr>
				<th width="17%">致电类型</th>
				<th width="12%">致电号码</th>
				<th width="15%">致电日期</th>
				<th width="15%">致电结果</th>
				<th width="36%">备注</th>
				<th width="10%">操作</th>
			</tr>
			</thead>
			<tbody id="tbodyId">
			<#if applyTelInquiryMap??>
				<#list applyTelInquiryMap?keys as key>
					<@form cols="8" id="telInquiry${(key)!}" >
						<tr>
							<td><@dictSelect id="telType${(key)!}" dicttype="CallType"
								name="telType" value="${(applyTelInquiryMap[key].telType)!}" readonly="true"/></td>
							<td>
								<#if applyTelInquiryMap[key].phone??>
									<@input id="phone${(key)!}" name="phone" 
										value="${(applyTelInquiryMap[key].phone)!}" readonly="true"/>
								<#else>
									<@input id="phone${(key)!}" name="phone"
										value="${(applyTelInquiryMap[key].phone)!}" readonly="false"/>
								</#if>
							</td>
							<td><@date id="telDate${(key)!}" datetype="datetime" name="telDate"
									value="${(applyTelInquiryMap[key].telDate?datetime)!}" readonly="true"/></td>
							<td><@dictSelect id="telResult${(key)!}" dicttype="CallResult" change="telResultChange(${(key)!},this)" 
									name="telResult" value="${(applyTelInquiryMap[key].telResult)!}" /></td>
							<td><@input id="memo${(key)!}" name="memo" value="${(applyTelInquiryMap[key].memo)!}"/></td>
							<td><@button fa="check-circle" name="保存" click="telResultSubmit('${(key)!}')" />
							</td>
						</tr>
					</@form>
					</#list>
				</#if>
			</tbody>
		</@pureTable>

		<script type="text/javascript">
			
			<#--致电结果改变-->
			function telResultChange(num,that){
				if($(that).val()==''){
					$("#telDate"+num).val('');
				}else{
					var date =ar_.datetimeFormat(new Date());
					$("#telDate"+num).val(date);
				}
			}

			var telResultSubmit=function(nums){
				var flag = validTelPhoneRs(nums);//非空判断
				if(flag == false){
					return;
				}
				var telType = $("#telType"+nums).val();
				var phone = $("#phone"+nums).val();
				var telDate = $("#telDate"+nums).val();
				var telResult = $("#telResult"+nums).val();
				var memo = $("#memo"+nums).val();
				var appNo = $("#appNo").val();

				$.ajax({
					url:"${base}/cas_applyBasicCheck/basicCheckTelSave",
					type:"post",
					dataType : "json",
					data: {'telType':telType,'phone':phone,'telDate':telDate,'telResult':telResult,'memo':memo,'appNo':appNo},
					success:function(ref){
						if (ref.s){
							alert(ref.msg);
							var parms = {url:ar_.randomUrl("${base}/cas_common/getTelCheckRecordList?appNo="+appNo)};
							$("#TelDataTable").bootstrapTable("refresh",parms);
							$("#memo"+nums).val('');
							$("#telDate"+nums).val('');
							$("#telResult"+nums).val('');
						}
						else {
							alert("操作失败"+ref.msg);
						}
					}
				});
			}
			//验证电调结果是否都正常输入
			function validTelPhoneRs(nums){
				var telType = $("#telType"+nums).val();
				var phone = $("#phone"+nums).val();
				var telDate = $("#telDate"+nums).val();
				var telResult = $("#telResult"+nums).val();
				var memo = $("#memo"+nums).val();
				var appNo = $("#appNo").val();
				if(telType == ''){
   					alert("[致电类型]不能为空!");
   					
   					return false;
   				}
				if(phone == ''){
   					alert("[致电号码]不能为空!");
   					return false;
   				}
				if(telResult == ''){
   					alert("[致电结果]不能为空!");
   					return false;
   				}
   				if(telDate == ''){
   					alert("[致电日期]不能为空!");
   					return false;
   				}
		   		return true;
			}
		</script>
	</div>
</@fieldset>