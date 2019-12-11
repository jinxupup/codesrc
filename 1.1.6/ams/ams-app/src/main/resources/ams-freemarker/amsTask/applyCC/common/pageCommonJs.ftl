<script type="text/javascript">
    function contactNotNull(){
        var a=document.getElementsByName("tmAppContact[1].contactName");
        var b=document.getElementsByName("tmAppContact[1].contactRelation");
        var c=document.getElementsByName("tmAppContact[1].contactMobile");
        if(a==null ||b==null || c==null){
            alert('请填好所有的联系人姓名,联系人关系和联系人移动电话');
            return false;
        }else if(a[0].value =='' || b[0].value =='' || c[0].value ==''){
            alert('请填好所有的联系人姓名,联系人关系和联系人移动电话');
            return false;
        }
    }
	<#--姓名拼音格式转换(大写)-->
	function embLogoKeyup(event){
		var embLogo = $(this).val();
		embLogo = embLogo.toUpperCase();
		$(this).val(embLogo);
	}
	
	<#--单位电话、联系人电话与申请人联系电话校验-->
	function telBlur(event){
		var cellphone = event.data.cellphone.trim();
		if(cellphone != '' || cellphone != null || cellphone != undefined){
			var tel = $(this).val();
			if(tel != ''){
				var id = event.data.id;
				if(cellphone == tel){
					if(id == 1){
						alert("其他联系人电话不能为申请人电话！");
					}else if(id == 2){
						alert("直属联系人电话不能为申请人电话！");
					}
					$(this).val('');
				}
			}
		}	
	}
	
	<#--主、附卡'是否自选卡号'的改变-->
	/**
	*@param	appType 卡类别(主卡 | 附卡)
	*@param	num 第几张卡
	*/
	function ifSelectedCardChange(appType,num){
		if(appType == null || appType == ''){
			alert("自选卡号操作异常!");
			return false;
		}
		if(num == null || num == undefined){
			num = '';
		}
		var ifSelectedCard = $('#ifSelectedCard'+num).val();
		$('#ifSelectedCard'+num+'_ar_hidden_').val(ifSelectedCard);
		if(ifSelectedCard == 'Y'){
			var productCd = $('#productCd').val();
			var cardBin = getOrValidCardNo(productCd);
			$('#cardBin'+num).val(cardBin);
			$('#cardNo'+num).removeAttr("readonly").attr("maxlength","${(editCardNoLen)!9}");
			if(num != ''){
				$('#cardNo'+num).bind('keyup',{'appType':appType,'num':num},cardNoKeyup).bind('blur',{'appType':appType,'num':num},cleanSelectCardNo);
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+num+'].cardNo','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo['+num+'].cardNo',true,'notEmpty');
				}
			}else{
				$('#cardNo').bind('keyup',{'appType':appType},cardNoKeyup).bind('blur',{'appType':appType},cleanSelectCardNo);
				if(appType == 'A'){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.cardNo','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimApplicantInfo.cardNo',true,'notEmpty');
					}
				}
				if(appType == 'S'){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.cardNo','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.cardNo',true,'notEmpty');
					}
				}	
			}
		}else{
			$('#cardBin'+num).val('');
			$('#cardNo'+num).unbind('blur keyup').val('');
			if(num != ''){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+num+'].cardNo','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo['+num+'].cardNo',false,'notEmpty');
				}						
			}else{
				if(appType == 'A'){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.cardNo','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimApplicantInfo.cardNo',false,'notEmpty');
					}
				}
				if(appType == 'S'){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.cardNo','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.cardNo',false,'notEmpty');
					}
				}
			}
			$('#cardNo'+num).removeAttr("maxlength").attr("readonly","readonly");
			$('#validBit'+num).val('');	
		}
	}
	
	<#--根据卡产品代码获取卡bin(即卡号的前N位数字),验证自选卡号是否在对应的卡产品卡段内,并取验证位-->
	/**
	*@param	productCd 卡产品代码
	*@param	cardNo N位自选卡号(功能的标志位  cardNo=='':取验证位,cardNo!='':验证自选卡号)
	*@param attachNo 附卡编号(主附同申时用以区分对应的附卡)
	*return false：卡号已使用或不在该卡产品范围内
	*		检验位：自选卡号校验位
	*		空 ：主附同申不支持附卡
	*/
	function getOrValidCardNo(productCd,cardNo,attachNo){
		var cardBin = '';
		if(productCd != ''){
			$.ajax({
	     		type: 'POST',
	     		async:false,
				url: '${base}/applyInput/getTmProduct',
				data: {'productCd':productCd,'cardNo':cardNo,'appNo':'${(appNo)!}','attachNo':attachNo},
				dataType: 'json',
				success:function(res){
					if(res.s){
						if(cardNo != null && cardNo != ''){
							cardBin = res.msg;//得到自选卡号校验位或false
						}else{//得到bin
							cardBin = res.obj.bin;
							if('${(appType)!}' == 'A' && res.obj.ifEnableAttachCard == 'N'){
								$('#productCd').val('');
								if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppMain.productCd') != null){
									$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppMain.productCd','NOT_VALIDATED');
								}
								alert("该卡产品不能申请附卡！");
								return '';
							}
							var subCardType = res.obj.subCardType;
							if($('#subCardType')!=undefined){
								$('#subCardType').val(subCardType);
							}
						}
					}else{
						cardBin = 'false';//验证错误
						alert(res.msg);
					}
	    		} 
			});
		}
		
		return cardBin;
	}
	
	<#--主附卡'自选卡号'范围验证及获得校验位-->
	function cardNoKeyup(event){
		var num = event.data.num;
		var appType = event.data.appType;
		var attachNo = '';
		if(appType == null){
			alert("自选卡号校验出现异常!");
			return false;
		}
		if(num == null || num == undefined){
			num = '';
		}else{
			attachNo = $('#attachNo'+num).val();
		}
		var cardNo = $('#cardNo'+num).val();
		if(cardNo != ''){
			cardNo = $.trim(cardNo);
			if(cardNo.length == ${(editCardNoLen)!9}){
				var productCd = $('#productCd').val();
				var validBit = getOrValidCardNo(productCd,cardNo,attachNo);//validBit='false'|校验位
				if(validBit != 'false'){
					$('#cardNo'+num).attr("readonly","readonly").unbind('blur keyup');
					$('#validBit'+num).val(validBit);
					$('#deleteCardNoBtn'+num).removeAttr("disabled");
					$('#ifSelectedCard'+num).attr({"disabled":"disabled","readonly":"readonly"});
					alert("卡号选择成功！");	
				}else{
					if(num != ''){
						if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+num+'].cardNo') != null){
							$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppAttachApplierInfo['+num+'].cardNo','NOT_VALIDATED');
						}
					}else{
						if(appType == 'A'){
							if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.cardNo') != null){
								$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppPrimApplicantInfo.cardNo','NOT_VALIDATED');
							}
						}
						if(appType == 'S'){
							if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.cardNo') != null){
								$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppAttachApplierInfo.cardNo','NOT_VALIDATED');
							}
						}
					}
				}
			}
		}
	}

	<#--自选卡号的清零处理-->
	function cleanSelectCardNo(event){
		var num = event.data.num;
		var appType = event.data.appType;
		if(appType == null || appType == ''){
			alert("自选卡号处理操作异常!");
			return false;
		}
		if(num == null || num == undefined){
			num = '';
		}
		var validBit = $('#validBit'+num).val();
		if(validBit == ''){
			$('#cardNo'+num).val('');
			if(num != ''){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+idNum+'].cardNo') != null){
					$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppAttachApplierInfo['+idNum+'].cardNo','NOT_VALIDATED');
				}
			}else{
				if(appType == 'S'){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.cardNo') != null){
						$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppAttachApplierInfo.cardNo','NOT_VALIDATED');
					}
				}else{
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.cardNo') != null){
						$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppPrimApplicantInfo.cardNo','NOT_VALIDATED');
					}
				}
			}	
		}
	}

	<#--自选卡号解锁功能-->
	function deleteSelectCardNo(appType,num){			
		if(appType == null || appType == ''){
			alert("自选卡号解锁操作异常!");
			return false;
		}
		var attachNo = '';
		if(num == null || num == undefined){
			num = '';
		}else{
			attachNo = $('#attachNo'+num).val();
		}
		var cardBin = $('#cardBin'+num).val();
		var cardNo = $('#cardNo'+num).val();
		var validBit = $('#validBit'+num).val();
		if(cardNo != ''){
			cardNo = cardBin + cardNo;
			$.ajax({
	     		type: 'POST',
				url: '${base}/applyInput/cardNoDelete' ,
				data: {'cardNo':cardNo,'appNo':'${(appNo)!}','attachNo':attachNo,'validBit':validBit},
				dataType: 'json',
				success:function(res){
					if(res.s){
					 	$('#cardNo'+num).removeAttr("readonly").val('')
					 		.bind('keyup',{'appType':appType,'num':num},cardNoKeyup).bind('blur',{'appType':appType,'num':num},cleanSelectCardNo);
					 	$('#validBit'+num).val('');
					 	$('#ifSelectedCard'+num).removeAttr("disabled readonly");
					 	if(num == ''){
					 		if(appType == 'A'){
					 			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.cardNo') != null){
									$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppPrimApplicantInfo.cardNo','NOT_VALIDATED');
								}
					 		}
					 		if(appType == 'S'){
					 			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.cardNo') != null){
									$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppAttachApplierInfo.cardNo','NOT_VALIDATED');
								}
					 		}
						}else{
							if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+num+'].cardNo') != null){
								$("#applyInputForm").data('bootstrapValidator').updateStatus('tmAppAttachApplierInfo['+num+'].cardNo','NOT_VALIDATED');
							}
						}
						alert("卡号"+cardNo+validBit+"解锁成功！");
					}else{
						alert("卡号"+cardNo+validBit+"解锁失败！错误信息-[ "+res.msg+" ]");
					}
	    		} 
			});
		}
	}
	
	<#--主、附卡身份证号格式校验-->
	function idNoValid(idNo) {
        var flag = false;
        if(idNo != null || idNo != ''){
        	$.ajax({
	     		type: 'POST',
	     		async:false,
				url: '${base}/applyInput/idNoValid' ,
				data: {"idNo":idNo},
				dataType: 'json',
				success:function(res){
					flag = res.s;
					if(flag == false){
						alert("身份证号码"+idNo+"不正确！");
					}
				}
			});
        }
		return flag;
    }
  
	<#--证件长期有效标志的改变-->
	function idLastAllChange(appType,num){
		if(appType == null){
    		alert("证件长期有效操作出现异常!");
    		return false;
    	}
    	if(num == null || num == undefined){
    		num = '';
    	}
		var idLastAll = $('#idLastAll'+num).val();
		if(idLastAll == 'Y'){
			if(appType == 'A'){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.idLastDate','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimApplicantInfo.idLastDate',false,'notEmpty');
				}
			}
			if(appType == 'S'){
				if(num != ''){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+num+'].idLastDate','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo['+num+'].idLastDate',false,'notEmpty');
					}
				}else{
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.idLastDate','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.idLastDate',false,'notEmpty');
					}
				}
			}
			$('#idLastDate'+num).val('').attr("disabled","disabled");
		}else{
			$('#idLastDate'+num).removeAttr("disabled").val('');
			if(appType == 'A'){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimApplicantInfo.idLastDate','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimApplicantInfo.idLastDate',true,'notEmpty').validateField('tmAppPrimApplicantInfo.idLastDate');
				}
			}
			if(appType == 'S'){
				if(num != ''){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+num+'].idLastDate','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo['+num+'].idLastDate',true,'notEmpty').validateField('tmAppAttachApplierInfo['+num+'].idLastDate');
					}
				}else{
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.idLastDate','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.idLastDate',true,'notEmpty').validateField('tmAppAttachApplierInfo.idLastDate');
					}
				}
			}
		}
	}
	
	<#--点击异步加载下拉框-->
	/**点击异步加载下拉框的options
	*@param type 字典类型
	*@param isCode  是否显示code(默认显示)
	*@param fieldType  字段类型(A：省市区、B:国家代码、C:网点)
	*@param parentId 联动父级id
	*@param defValue 当前组件默认值 ,如[01276]
	*/
	function clickAjaxSelect(dicType,isCode,fieldType,that,parentId,defValue){
		var that = '#'+that;
		var enableAjax = $(that).attr('ajax');
		if(enableAjax=='true'){
			if(dicType == '' || dicType == undefined){
				return ;
			}
			$(that).attr('ajax','false');
			if(isCode == '' || isCode == undefined){
				isCode = true;//默认显示code
			}
			if(fieldType == '' || fieldType == undefined){
				return ;
			}
			var parentValue = null;
			if(parentId != null && parentId != '' && parentId != undefined){
				parentValue = $('#'+parentId).multipleSelect('getSelects');//获取父级组件的值
			}else{
				parentValue = '';//获取父级组件的值
			}
			parentValue = parentValue + '';//将数组转化为字符串
			var name = $(that).attr('name');
			var value = $(that).multipleSelect('getSelects');
			if(defValue != null && defValue != '' && defValue != undefined){
				value = defValue;
			}
			$.ajax({
				type: 'POST',
				url : '${base}/ams_activiti/addrQuery',
				data : {'dicType':dicType,'name':name,'parentValue':parentValue,'isCode':isCode,'fieldType':fieldType},
				dataType : 'json',
				success : function(res){
					if(res.s){
						//单选可搜索下拉框搜索部分
						$(that).siblings('.ms-parent').children('.ms-drop').children('ul').html(res.code);
						$(that).html(res.msg).multipleSelect('refresh').multipleSelect('setSelects',value);//刷新设默认值
					}else{
						$(that).attr('ajax','true');
						alert("网络异常，请稍后再试");
					}
				}
			});
		}
	}
	
	<#--选项改变联动异步加载-->
	/**改变异步加载
	*@param dicType 需要联动改变字段的数据字典
	*@param isCode 是否显示code
	*@param childId 需要联动子级id
	*@param fieldType 字段类型(A：省市区、B:国家代码、C:网点)
	*/
	function changeAjaxSelect(dicType,isCode,childId,fieldType,that){
		if(dicType == '' || dicType == undefined || childId == '' || childId == undefined){
			return ;
		}
		if(fieldType == '' || fieldType == undefined){
			return ;
		}
		if(isCode == '' || isCode == undefined){
			isCode = true;//默认显示code
		}
		var value = $('#'+$(that).attr('id')+'_hidden_value').val();//获取当前组件的值
		value = value + '';//将数组转化为字符串
		var name = $('#'+childId).attr('name');
		$.ajax({
			type: 'POST',
			url : '${base}/ams_activiti/addrQuery',
			data : {'dicType':dicType,'name':name,'parentValue':value,'isCode':isCode,'fieldType':fieldType},
			dataType : 'json',
			success : function(res){
				if(res.s){
					$('#'+childId+'_hidden_value').val(res.obj);
					var $childId = $('#'+childId);
					$childId.siblings('.ms-parent').children('.ms-drop').children('ul').html(res.code);
					$childId.html(res.msg).multipleSelect('refresh').multipleSelect('setSelects',res.obj);//刷新设默认值
					var ajaxchange = $childId.attr('ajaxchange');//change事件标志
					if(ajaxchange == 'true' || ajaxchange == 'Y'){
						var childValue = $childId.multipleSelect('getSelects')+'';
						if(childValue != ''){
							$childId.trigger("ajaxchange");
						}
					}
				}else{
					alert("网络异常，请稍后再试");
				}
			}
		});
	 }
	 
	 <#-- 根据输入地区代码查询地区名称 -->
	 function selectAreaCode(){
		var idIssuerAddress = $('#idIssuerAddress').val();
		if(idIssuerAddress.length>=4){
			$.ajax({
	     		type: 'POST',
	     		dataType: 'json',
	     		data: {'dicType':'AreaCode', 'areaCode':idIssuerAddress},
				url: '${base}/commonDialog/selectAreaByCode',
				success:function(res){
					if(res!=null){
						var result = res.obj;
						$('#idIssuerAddress').val(result);
						if(res.s!=null && res.s==false && res.msg!=null && res.msg!=""){
							alert(res.msg);
						}
					}
	    		}
			});
		}
	}
</script>