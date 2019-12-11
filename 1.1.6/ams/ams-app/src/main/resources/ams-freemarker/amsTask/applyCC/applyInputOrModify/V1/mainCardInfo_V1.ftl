<#if pageFieldMap['A1']??>
	<@fieldset legend="申请信息:">
		<#list pageFieldMap['A1']?keys as key>
			<#assign fieldPageDto = pageFieldMap['A1'][key]>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'Y'><div class="row"></#if>
			<@field label="${(fieldPageDto.fieldName)!}" point_flag="${(fieldPageDto.ifRequire)!}" point="*">
				<#if fieldPageDto.componentType??>
					<#if fieldPageDto.componentType == "input">
						<#if fieldPageDto.id == "cardNo">
							<div style="margin-right: -5px;margin-left: -5px;">
								<div class="col-ar-10" style="padding-right:0px;">
									<@input id="cardBin" readonly="true" name="cardBin" value="${(cardBin)!}"/>
								</div>
								<div class="col-ar-14" style="padding-right:0px;padding-left:0px;">
									<@input id="${(fieldPageDto.id)!}" readonly="${(fieldPageDto.ifReadonly)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" />
								</div>
								<div class="col-ar-4" style="padding-left:0px;">
									<@input id="validBit" name="validBit" value="${(validBit)!}" readonly="readonly"/>
								</div>
								<div class="col-ar-8" style="padding-left:0px;">
									<@button id="deleteCardNoBtn" name="解锁卡号" click="deleteSelectCardNo('A')"/>
								</div>
							</div>
						<#else>
							<@input id="${(fieldPageDto.id)!}" readonly="${(fieldPageDto.ifReadonly)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" />
						</#if>
					<#elseif fieldPageDto.componentType == "dictSelect">
						<@dictSelect id="${(fieldPageDto.id)!}" dicttype="${(fieldPageDto.dictType)!}" change="${(fieldPageDto.change)!}" name="${(fieldPageDto.name)!}" nullable="${(fieldPageDto.nullable)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}" showcode="${(fieldPageDto.showCode)!}"/>
					<#elseif fieldPageDto.componentType == "select">
						<@select id="${(fieldPageDto.id)!}" options=fieldPageDto.options change="${(fieldPageDto.change)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"  showcode='${(fieldPageDto.showCode)!}' nullable="${(fieldPageDto.nullable)!}"/>
					<#elseif fieldPageDto.componentType == "multipleSelect"><#--没有改变事件-->
						<@multipleSelect id="${(fieldPageDto.id)!}" options=fieldPageDto.options name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" nullable="${(fieldPageDto.nullable)!}" showfilter="true" single="${(fieldPageDto.textName)!}"/>
					<#elseif fieldPageDto.componentType == "date">
						<@date id="${(fieldPageDto.id)!}" settings=fieldPageDto.options name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"/>
					<#elseif fieldPageDto.componentType == "textarea">
						<@textarea id="${(fieldPageDto.id)!}" name="${(fieldPageDto.name)!}" rows="${(fieldPageDto.dictType)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"/>
					<#elseif fieldPageDto.componentType == "ajaxSelect"><#--异步加载下拉框-->
						<@ajaxSelect id="${(fieldPageDto.id)!}" options=fieldPageDto.options click="${(fieldPageDto.textName)!}" change="${(fieldPageDto.change)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"  showcode="${(fieldPageDto.showCode)!}" nullable="${(fieldPageDto.nullable)!}"/>
					</#if>
				</#if>
			</@field>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'N'></div></#if>
		</#list>
	</@fieldset>
</#if>
<#include "InstalmentCreditInfo.ftl"/>
<#assign fieldsetMap = {'A2':'申请人基本信息','A3':'职业资料'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldSet.ftl"/>
</#list>
<#--联系人-->
<#include "applyContact.ftl">
<#--主附同申附卡部分-->
<#if appType?? && appType=='A'>
	<div id="attachCardInfo">
		<input id="idNum" type="hidden" value="${(attachNum)!'1'}"/><#--附卡的数量，从1开始-->
		<#include "applyAddAttachCard_V1.ftl">
	</div>
</#if>
<#if pageFieldMap['A5']??>
	<@fieldset legend="服务要求:">
		<#list pageFieldMap['A5']?keys as key>
			<#assign fieldPageDto = pageFieldMap['A5'][key]>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'Y'><div class="row"></#if>
			<@field label="${(fieldPageDto.fieldName)!}" point_flag="${(fieldPageDto.ifRequire)!}" point="*">
				<#if fieldPageDto.componentType??>
					<#if fieldPageDto.componentType == "input">
						<#if fieldPageDto.id == "ddBankAcctNo">
							<div class="col-ar-28">
								<@input id="${(fieldPageDto.id)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}"/>
							</div>
							<div class="col-ar-8" style="padding-left:0px;">
								<@button id="checkCardBtn" name="验证账号" click="checkCard()"/>
							</div>
						<#else>
							<@input id="${(fieldPageDto.id)!}" readonly="${(fieldPageDto.ifReadonly)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" />
						</#if>
					<#elseif fieldPageDto.componentType == "dictSelect">
						<@dictSelect id="${(fieldPageDto.id)!}" dicttype="${(fieldPageDto.dictType)!}" name="${(fieldPageDto.name)!}" nullable="${(fieldPageDto.nullable)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}" change="${(fieldPageDto.change)!}"/>
					<#elseif fieldPageDto.componentType == "select" || fieldPageDto.componentType == "ajaxSelect">
						<@select id="${(fieldPageDto.id)!}" options=fieldPageDto.options showcode='${(fieldPageDto.showCode)!}' nullable="${(fieldPageDto.nullable)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}" change="${(fieldPageDto.change)!}"/>
					<#elseif fieldPageDto.componentType == "multipleSelect">
						<@multipleSelect id="${(fieldPageDto.id)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" options=fieldPageDto.options nullable="${(fieldPageDto.nullable)!}" showfilter="true" single="${(fieldPageDto.textName)!}" change="${(fieldPageDto.change)!}"/>
					<#elseif fieldPageDto.componentType == "date">
						<@date id="${(fieldPageDto.id)!}" settings=fieldPageDto.dictType name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value?date)!}" readonly="${(fieldPageDto.ifReadonly)!}"/>
					<#elseif fieldPageDto.componentType == "textarea">
						<@textarea id="${(fieldPageDto.id)!}" name="${(fieldPageDto.name)!}" rows="${(fieldPageDto.dictType)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"/>
					</#if>
				</#if>
			</@field>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'N'></div></#if>
		</#list>
	</@fieldset>
</#if>
<#assign fieldsetMap = {'A6':'银行专用栏'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldSet.ftl"/>
</#list>
<#--备注-->
<@row>
	<@field label="推广人注记" field_ar="36" label_ar="5" input_ar="31">
		<@textarea name="tmAppMemo.memoInfo" rows="1" value="${(MEMOINPUT.memoInfo)!}"/>
	</@field>
</@row>

<#--js部分-->
<#include "../../common/emailTipJs.ftl"/>
<script type="text/javascript">
	<#--主卡初始化-->
	$(function(){
		<#--根据"自选卡号标志位"初始化"自选卡号"-->
		var ifSelectedCard = $('#ifSelectedCard').val();
		if(ifSelectedCard != '' && ifSelectedCard == 'Y'){
			var validBit = $('#validBit').val();
			if(validBit == ''){
				$('#ifSelectedCard').removeAttr("disabled readonly");
				$('#cardNo').removeAttr("readonly").attr("maxlength","${(editCardNoLen)!9}")
							.bind('keyup',{'appType':'A'},cardNoKeyup).bind('blur',{'appType':'A'},cleanSelectCardNo);
				$('#deleteCardNoBtn').attr("disabled","disabled"); //禁用解锁卡号按钮
			}
		}else{
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.cardNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimApplicantInfo.cardNo', false,'notEmpty');
			}
			$('#ifSelectedCard').removeAttr("disabled readonly");
			$('#cardNo').val('');
			$('#deleteCardNoBtn').attr("disabled","disabled"); //禁用解锁卡号按钮
		}
		
		<#--主卡姓名拼音绑定大写转化事件-->
		$('#embLogo').bind('input',{},embLogoKeyup);
		
		<#--公司电话、亲属联系人电话绑定效验事件-->
		<#--$('#empPhone').bind('blur',{'cellphone':'${(applyOperateDto.cellphone)!}','id':1},telBlur);-->
		$('#contactMobile2').bind('blur',{'cellphone':'${(applyOperateDto.cellphone)!}','id':1},telBlur);
		$('#contactMobile1').bind('blur',{'cellphone':'${(applyOperateDto.cellphone)!}','id':2},telBlur);
		
		<#--根据"证件类型"初始化"性别、生日"-->
		var idType = $('#idType').val();
		if(idType != 'I'){
			$('#gender').removeAttr("disabled readonly");
			$('#birthday').removeAttr("readonly data-hide-onclick").attr("onclick","WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd' })");
			$('#age').removeAttr("disabled readonly");
		}
		
		<#--根据"证件长期有效"初始化"证件到期日"-->
		var idLastAll = $('#idLastAll').val();
		if(idLastAll != '' && idLastAll == 'Y'){
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.idLastDate','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimApplicantInfo.idLastDate', false,'notEmpty');
			}
			$('#idLastDate').attr('disabled','disabled');
		}
		
		<#--根据"约定还款类型"初始化"约定还款人姓名"-->
		var ddInd = $('#ddInd').val();
		if(ddInd != ''){
			if(ddInd == 'N'){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.ddBankAcctName','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.ddBankAcctName', false,'notEmpty');
				}
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.ddBankAcctNo','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.ddBankAcctNo', false,'notEmpty');
				}
				$('#ddBankAcctName').val($('#name').val()); //自动填充还款人姓名
				$('#checkCardBtn').attr("disabled","disabled");	
			}
		}

		<#--根据"领卡方式"初始化"领卡地址"-->
		var cardFetchMethod = $('#cardFetchMethod').val();
		if(cardFetchMethod != ''){
			if(cardFetchMethod == 'B'){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.cardMailerInd','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.cardMailerInd', false,'notEmpty');
				}
				$('#cardMailerInd').val('');
			}
			if(cardFetchMethod == 'A' || cardFetchMethod == 'E'){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.fetchBranch', 'notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.fetchBranch', false,'notEmpty');
				}
			}
		}
		
		<#--根据’账单介质类型‘设置初始必输项-->
		var stmtMediaType = $('#stmtMediaType').val();
		if(stmtMediaType != ''){
			if(stmtMediaType == 'P'){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.email','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimApplicantInfo.email', false,'notEmpty');
				}
			}
			if(stmtMediaType == 'E'){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.stmtMailAddrInd','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.stmtMailAddrInd', false,'notEmpty');
				}
				$('#stmtMailAddrInd').attr('disabled','disabled');
			}
		}
		// 根据产品代码判断是否显示“获取公务卡公司信息”按钮
		setOffialCardCorp();
		
		<#--根据’其他联系人与申请人关系‘设置初始必输项-->
		var contactRelation2 = $('#contactRelation1').val();
		if(contactRelation2 == "" || contactRelation2 == null || contactRelation2 == "undefined"){
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppContact[1].contactName','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppContact[1].contactName', false,'notEmpty');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppContact[1].contactMobile','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppContact[1].contactMobile', false,'notEmpty');
			}
		}
		
		<#-- 根据是否申请分期，设置初始必输项等-->
		if($("#isInstalment").length > 0){
			var isInstal = $('#isInstalment').val();
			if(isInstal == 'Y'){
				
			}else{
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.instalmentCreditActivityNo','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.instalmentCreditActivityNo', false,'notEmpty');
				}
		 	 	if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.mccNo','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.mccNo', false,'notEmpty');
				}
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.loanInitTerm','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.loanInitTerm', false,'notEmpty');
				}
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.cashAmt','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.cashAmt', false,'notEmpty');
				}
	<#--			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.customerSpecialQuota', false,'notEmpty'); -->
				if($("#cashAmt").length > 0){
					$('#cashAmt').attr("readonly","readonly");
					$('#cashAmt').val('');
				}
	<#--			$('#customerSpecialQuota').attr("readonly","readonly"); -->
				if($("#appFeeAmt").length > 0){
					$('#appFeeAmt').attr("readonly","readonly");
					$('#appFeeAmt').val('');
				}
				if($("#appFeeRate").length > 0){
					$('#appFeeRate').attr("readonly","readonly");
					$('#appFeeRate').val('');
				}
				if($("#loanFeeCalcMethod").length > 0){
					$('#loanFeeCalcMethod').attr({"readonly":"readonly","disabled":"disabled"});
					$('#loanFeeCalcMethod').val('');
				}
				if($("#loanFeeMethod").length > 0){
					$('#loanFeeMethod').attr({"readonly":"readonly","disabled":"disabled"});
					$('#loanFeeMethod').val('');
				}
				if($("#loanInitTerm").length > 0){
					$('#loanInitTerm').attr({"readonly":"readonly","disabled":"disabled"});
					$('#loanInitTerm').val('');
				}
				
				if($("#mccNo").length > 0){
					$('#mccNo').attr({"readonly":"readonly","disabled":"disabled"});
					$('#mccNo').val('');
				}
				if($("#instalmentCreditActivityNo").length > 0){
					$('#instalmentCreditActivityNo').attr({"readonly":"readonly","disabled":"disabled"});
					$('#instalmentCreditActivityNo').val('');
				}
				
	<#--			$('#customerSpecialQuota').val(''); -->
			}
		}
		
		<#--推广人工号验证-->
		$('#spreaderNo').blur(function(){
			var spreaderNo = $(this).val().trim();
			if(spreaderNo == null || spreaderNo == ''){
				return false;
			}
			$.ajax({
	     		type: 'POST',
	     		async:false,
				url: '${base}/ams_applyInput/userIdValid' ,
				data: {"userId":spreaderNo},
				dataType: 'json',
				success:function(res){
					var obj = res.obj;
					if(obj == null){
						<#--if(res.code == 'B' || res.code == 'D'){//前台有必输项控制，再做强制验证意义不大 by H.N of 2018.07.23
							$('#spreaderNo').parent().parent().addClass("has-error");
						}-->
						// $('#spreaderNo').val('');
						alert(res.msg);
					}else{
						if(res.code == 'user'){
							$('#spreaderName').val(obj.userName);
							$('#spreaderTelephone').val(obj.cellphome);
							if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.spreaderName') != null){
								$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppPrimCardInfo.spreaderName','NOT_VALIDATED');
							}
							if(res.msg == ''){//判断是不是一级机构
								$('#spreaderBranchOne_hidden_value').val(obj.branchCode);
								clickAjaxSelect('2','true','D','spreaderBranchOne','',[obj.branchCode]);
							}else{
								$('#spreaderBranchOne_hidden_value').val(res.msg);
								clickAjaxSelect('2','true','D','spreaderBranchOne','',[res.msg]);
								$('#spreaderBranchTwo_hidden_value').val(obj.branchCode);
								clickAjaxSelect('3','true','D','spreaderBranchTwo','spreaderBranchOne',[obj.branchCode]);
							}
						}else if(res.code == 'spreader'){
							$('#spreaderName').val(obj.spreaderName);
							$('#spreaderTelephone').val(obj.spreaderPhone);
							if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.spreaderName') != null){
								$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppPrimCardInfo.spreaderName','NOT_VALIDATED');
							}
							/*if(res.msg == ''){//判断是不是一级机构
								$('#spreaderBranchOne_hidden_value').val(obj.spreaderBankId);
								clickAjaxSelect('2','true','D','spreaderBranchOne','',[obj.spreaderBankId]);
							}else{
								$('#spreaderBranchOne_hidden_value').val(res.msg);
								clickAjaxSelect('2','true','D','spreaderBranchOne','',[res.msg]);
								$('#spreaderBranchTwo_hidden_value').val(obj.spreaderBankId);
								clickAjaxSelect('3','true','D','spreaderBranchTwo','spreaderBranchOne',[obj.spreaderBankId]);
							}*/
                            $('#spreaderBranchThree_hidden_value').val(obj.spreaderBankId);
                            clickAjaxSelect('allBranch','true','C','spreaderBranchThree','spreaderBranchTwo',[obj.spreaderBankId]);
						}
					}
				}
			});
		});
		
		<#--预审人工号验证-->
		$('#preNo').blur(function(){
			var preNo = $(this).val().trim();
			if(preNo == null || preNo == ''){
				return false;
			}
			$.ajax({
	     		type: 'POST',
	     		async:false,
				url: '${base}/ams_applyInput/userIdValid' ,
				data: {"userId":preNo},
				dataType: 'json',
				success:function(res){
					var obj = res.obj;
					if(obj == null){
						$('#preNo').val('');
						alert("预审人【' + preNo + '】信息不存在");
					}else{
						if(res.code == 'user'){
							$('#preName').val(obj.userName);
							$('#preTelephone').val(obj.cellPhone);
						} else if(res.code == 'spreader'){
							$('#preName').val(obj.spreaderName);
							$('#preTelephone').val(obj.spreaderPhone);
						}
					}
				}
			});
		});
	});
	
	<#--卡产品代码的改变-->
	var productCdChange = function(that){
		var productCd = $(that).val();
		var appType = $('#appType').val();
		var cardBin = getOrValidCardNo(productCd);
		if(cardBin == ''){// 判断该卡产品是否允许附卡
			return false;
		}
		var ifSelectedCard = $('#ifSelectedCard').val();
		if(ifSelectedCard != '' && ifSelectedCard == 'Y'){
			var validBit = $('#validBit').val();
			if(validBit != ''){
				deleteSelectCardNo('A');//解锁卡号
			}else{
				$('#cardNo').val('');
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.cardNo') != null){
					$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppPrimApplicantInfo.cardNo', 'NOT_VALIDATED');
				}
			}
			$('#cardBin').val(cardBin);
		}
		// 根据产品代码判断是否显示“获取公务卡公司信息”按钮
		setOffialCardCorp();
		if(appType == 'A'){
			var idNum = $('#idNum').val();//idNum的值从0开始
			var attachNo = '';
			var validBit = '';
			var ifSelectedCard = '';
			for(var i = 0; i <= idNum; i++){
				attachNo = $('#attachNo'+i).val();
				if(attachNo != ''){
					ifSelectedCard = $('#ifSelectedCard'+i).val();
					if(ifSelectedCard != '' && ifSelectedCard == 'Y'){
						validBit = $('#validBit'+i).val();
						if(validBit != ''){
							deleteSelectCardNo('A',i);//解锁卡号
						}else{
							$('#cardBin'+i).val(cardBin);
							$('#cardNo'+i).val('');
							if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+i+'].cardNo') != null){
								$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppAttachApplierInfo['+i+'].cardNo', 'NOT_VALIDATED');
							}
						}
					}
				}
			}
		}
		//根据产品代码判断是否有分期，重新查询产品对应分期活动
		if($('#isInstalment').length > 0){
			var isInstalment = $('#isInstalment').val();
			if(isInstalment == "Y"){
				$.ajax({
		            url:"${base}/ams_applyInput/getInstalParam",
		            type:"post",
		            dataType:"json",
		            data:{"productCd":productCd,'type':'P'},
		            success:function(res){
		                	if(res.s){
								if(res.code != null && res.code == "0"){
									alert(res.msg);
									$("#instalmentCreditActivityNo").val('');
								}
								var obj = res.obj;
									
								if(obj['activityNoMap'] != null){
									var activityNoMap = obj['activityNoMap'];
									var selectA = $("#instalmentCreditActivityNo");
						    		selectA.empty();
									selectA.append("<option value = ''></option>");
									$.each(activityNoMap,function(key,values){ 
										selectA.append("<option value = '"+key+"'>"+key+"-"+values+"</option>");
									});
								}
								
								if(obj['mccNoMap'] != null){
									var mccNoMap = obj['mccNoMap'];
									var selectM = $("#mccNo");
						    		selectM.empty();
									selectM.append("<option value = ''></option>");
									$.each(mccNoMap,function(key,values){ 
										selectM.append("<option value = '"+key+"'>"+key+"-"+values+"</option>");
									});
								}
								
								if(obj['termsMap'] != null){
									var termsMap = obj['termsMap'];
									var selectT = $("#loanInitTerm");
						    		selectT.empty();
									selectT.append("<option value = ''></option>");
									$.each(termsMap,function(key,values){ 
										selectT.append("<option value = '"+key+"'>"+key+"</option>");
									});
								}
							}else{
								alert(res.msg);
							}
							
		            }
		        });
			}
		}
		
	}
	
	<#--约定还款开户行号的改变 -->
	function ddBankBranchChange(){
		var ddBankBranch = $('#ddBankBranch').val();
		if(ddBankBranch != ''){
			$.ajax({
				url : "${base}/ams_applyInput/getDdBankName",
				type : "post",
				dataType : "json",
				data : {"ddBankBranch":ddBankBranch},
				success : function(res) {
					$('#ddBankName').val(res.msg);
				}
			});
		}
	}
		
	<#--约定还款类型的改变 -->
	var ddIndTypeChange = function(that){
		if($(that).val() == 'N'){
			$('#ddBankAcctName').val('');
			$('#ddBankAcctNo').val('');
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.ddBankAcctName','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.ddBankAcctName', false,'notEmpty');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.ddBankAcctNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.ddBankAcctNo', false,'notEmpty');
			}
			$('#checkCardBtn').attr("disabled","disabled");
		}else{
			$('#ddBankAcctName').val($('#mainName').val()); //自动填充还款人姓名
			$('#ddBankAcctNo').val('');
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.ddBankAcctName','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.ddBankAcctName', true,'notEmpty').validateField('tmAppPrimCardInfo.ddBankAcctName');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.ddBankAcctNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.ddBankAcctNo', true,'notEmpty').validateField('tmAppPrimCardInfo.ddBankAcctNo');
			}
			$('#checkCardBtn').removeAttr("disabled");
		}
	}

	<#--账单介质类型的改变 -->
	var stmtMediaTypeChange = function(that){
		var stmtMediaType = $(that).val();
		if(stmtMediaType == 'P'){
			$('#stmtMailAddrInd').val('C');
			$('#stmtMailAddrInd').removeAttr('disabled');
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.email','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimApplicantInfo.email', false ,'notEmpty');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.stmtMailAddrInd','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.stmtMailAddrInd', true,'notEmpty').validateField('tmAppPrimCardInfo.stmtMailAddrInd');
			}
		}else if(stmtMediaType == 'E'){
			$('#stmtMailAddrInd').val('');
			$('#stmtMailAddrInd').attr('disabled','disabled');
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.stmtMailAddrInd','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.stmtMailAddrInd', false,'notEmpty');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.email','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimApplicantInfo.email', true ,'notEmpty').validateField('tmAppPrimApplicantInfo.email');
			}
		}else if(stmtMediaType == 'B'){
			$('#stmtMailAddrInd').val('C');
			$('#stmtMailAddrInd').removeAttr('disabled');
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.stmtMailAddrInd','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.stmtMailAddrInd', true,'notEmpty');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.email','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimApplicantInfo.email', true ,'notEmpty').validateField('tmAppPrimApplicantInfo.email');
			}
		}
	}
	
	<#--约定还款账户有效性验证-->
	function checkCard(){
		var flag = true;
		var ddBankAcctNo = $('#ddBankAcctNo').val();// 约定还款账户账号
		if(ddBankAcctNo != ''){
			var ddBankAcctName = $('#ddBankAcctName').val();// 约定还款账户姓名
			var ddBankAcctNoType = $('#ddBankAcctNoType').val();
			var branchNo = $('#ddBankBranch').val();// 开户行号
			$('#checkCardBtn').attr("disabled","disabled");
			$.ajax({
	     		type: 'POST',
	     		dataType: 'json',
	     		data: {'ddBankAcctName':ddBankAcctName, 'ddBankAcctNo':ddBankAcctNo, 'idType':'${(applyOperateDto.idType)!}', 'idNo':'${(applyOperateDto.idNo)!}', 'branchNo':branchNo, 'acctNoType':ddBankAcctNoType},
				url: '${base}/ams_applyInput/checkDdBankAcct',
				success:function(res){
					alert(res.msg);
					if(!res.s){
						flag = false;
						$('#checkCardBtn').removeAttr("disabled");
//						$('#ddBankAcctNo').val('');
						if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.ddBankAcctNo') != null){
							$("#applyInputForm").data('bootstrapValidator').revalidateField('tmAppPrimCardInfo.ddBankAcctNo');
						}
					}
	    		}
			});
		}else{
			alert("没有需要验证的约定还款账户");
		}		
		return flag;
	};
	
	<#--设置公务卡单位信息-->
	function setOffialCardCorp(){
		// 根据产品代码判断是否显示“获取公务卡公司信息”按钮
		if($("#getOffialCardCorp")!=undefined){
			$("#getOffialCardCorp").hide();
			if($("#subCardType")!=undefined){
				var subCardType = $("#subCardType").val();
				if (subCardType!=null && subCardType == 'O') {
					$("#getOffialCardCorp").show();
					$("#corpName").attr('readonly','true');
        			$("#empType").attr('readonly','true');
        			$("#empStructure").attr('readonly','true');
        			$("#empAddrCtryCd").attr('readonly','true');
				}else{
					$("#getOffialCardCorp").hide();
					$("#corpName").removeAttr("readonly");
					$("#empType").removeAttr("readonly");
					$("#empStructure").removeAttr("readonly");
        			$("#empAddrCtryCd").removeAttr("readonly");
				}
			}
		}
	}
	
	<#--其他联系人与申请人关系的改变 -->
	var contactRelation2Change = function(that){
		var contactRelation2 = $(that).val();
		if(contactRelation2 != "" && contactRelation2 != null){
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppContact[1].contactName','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppContact[1].contactName', true,'notEmpty').validateField('tmAppContact[1].contactName');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppContact[1].contactMobile','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppContact[1].contactMobile', true,'notEmpty').validateField('tmAppContact[1].contactMobile');
			}
		}else{
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppContact[1].contactName','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppContact[1].contactName', false,'notEmpty');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppContact[1].contactMobile','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppContact[1].contactMobile', false,'notEmpty');
			}
		}
		
	}
	
	<#--是否申请分期的改变 -->
	var isApplyInstalChange = function(that){
	
		var isInstal = $(that).val();
		if(isInstal == 'Y'){
	 		$('#cashAmt').removeAttr("disabled readonly"); 
<#--			$('#customerSpecialQuota').removeAttr("disabled readonly"); -->
			if($("#appFeeAmt").length > 0){
				$('#appFeeAmt').removeAttr("disabled readonly");
			}
			if($("#appFeeRate").length > 0){
				$('#appFeeRate').removeAttr("disabled readonly");
			}
			if($("#loanFeeCalcMethod").length > 0){
				$('#loanFeeCalcMethod').removeAttr("disabled readonly");
			}
			if($("#loanFeeMethod").length > 0){
				$('#loanFeeMethod').removeAttr("disabled readonly");
			}
			
			$('#loanInitTerm').removeAttr("disabled readonly");
			$('#mccNo').removeAttr("disabled readonly");
			$('#instalmentCreditActivityNo').removeAttr("disabled readonly");
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.instalmentCreditActivityNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.instalmentCreditActivityNo', true,'notEmpty').validateField('tmAppInstalLoan.instalmentCreditActivityNo');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.cashAmt','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.cashAmt', true,'notEmpty').validateField('tmAppInstalLoan.cashAmt');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.mccNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.mccNo', true,'notEmpty').validateField('tmAppInstalLoan.mccNo');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.loanInitTerm','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.loanInitTerm', true,'notEmpty').validateField('tmAppInstalLoan.loanInitTerm');
			}
			
<#--			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.customerSpecialQuota', true,'notEmpty').validateField('tmAppInstalLoan.customerSpecialQuota'); -->
			
		}else {
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.instalmentCreditActivityNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.instalmentCreditActivityNo', false,'notEmpty');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.cashAmt','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.cashAmt', false,'notEmpty');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.mccNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.mccNo', false,'notEmpty');
			}
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppInstalLoan.loanInitTerm','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.loanInitTerm', false,'notEmpty');
			}
			
<#--			$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppInstalLoan.customerSpecialQuota', false,'notEmpty'); -->
			
			$('#cashAmt').attr("readonly","readonly");
<#--			$('#customerSpecialQuota').attr("readonly","readonly"); -->
			if($("#appFeeAmt").length > 0){
				$('#appFeeAmt').attr("readonly","readonly");
				$('#appFeeAmt').val('');
			}
			if($("#appFeeRate").length > 0){
				$('#appFeeRate').attr("readonly","readonly");
				$('#appFeeRate').val('');
			}
			if($("#loanFeeCalcMethod").length > 0){
				$('#loanFeeCalcMethod').attr({"readonly":"readonly","disabled":"disabled"});
				$('#loanFeeCalcMethod').val('');
			}
			if($("#loanFeeMethod").length > 0){
				$('#loanFeeMethod').attr({"readonly":"readonly","disabled":"disabled"});
				$('#loanFeeMethod').val('');
			}
			
			$('#loanInitTerm').attr({"readonly":"readonly","disabled":"disabled"});
			$('#mccNo').attr({"readonly":"readonly","disabled":"disabled"});
			$('#instalmentCreditActivityNo').attr({"readonly":"readonly","disabled":"disabled"});
			
			$('#cashAmt').val('');
<#--			$('#customerSpecialQuota').val(''); -->
			$('#loanInitTerm').val("");
			$('#mccNo').val("");
			$('#instalmentCreditActivityNo').val("");
		}
	};
</script>
