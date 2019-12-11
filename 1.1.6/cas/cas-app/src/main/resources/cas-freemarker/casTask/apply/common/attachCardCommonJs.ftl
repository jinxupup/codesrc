<script type="text/javascript">
	<#--附卡身份证号格式校验及生日、性别自动填充-->
	function idNoBlur(event){
    	var num = event.data.num;
    	if(num == null || num == undefined){
    		num = '';
    	}
    	var idNo = $('#idNo'+num).val();
    	if(idNo != ''){
    		idNo = $.trim(idNo);
    		if(idNo.length==15 || idNo.length==18){
    			if(idNoValid(idNo)){
    				if(idNo.length == 15){
						if(idNo.substring(14,15)%2 == 1){//男
							$('#gender'+num).val("M");
						}else{
							$('#gender'+num).val("F");
						}
						var birthday = "19"+idNo.substring(6,8)+"-"+idNo.substring(8,10)+"-"+idNo.substring(10,12);
					}
    				if(idNo.length == 18){
						if(idNo.substring(14,17)%2 == 1){//男
							$('#gender'+num).val("M");
						}else{
							$('#gender'+num).val("F");
						}
						var birthday = idNo.substring(6,10) + "-" + idNo.substring(10,12) + "-" + idNo.substring(12,14);	
					}
					if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].gender') != null){
						$("#applyInputForm").data('bootstrapValidator').updateStatus('attachCust['+num+'].gender','NOT_VALIDATED');
					}
					$('#gender'+num).attr({"readonly":"readonly","disabled":"disabled"});
					$('#birthday'+num).val(birthday);
					if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].birthday') != null){
						$('#applyInputForm').data('bootstrapValidator').updateStatus('attachCust['+num+'].birthday','NOT_VALIDATED');
					}
    			}else{
    				$('#idNo'+num).val('');
    				if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].idNo') != null){
						$("#applyInputForm").data('bootstrapValidator').updateStatus('attachCust['+num+'].idNo','NOT_VALIDATED');
					}
    			}
    		}
    	}
    }
	
	<#--证件类型的改变 -->
    function idTypeChange(num){
    	$('#idNo'+num).val('');
    	if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].idNo') != null){
			$("#applyInputForm").data('bootstrapValidator').updateStatus('attachCust['+num+'].idNo','NOT_VALIDATED');
		}
    	$('#gender'+num).removeAttr("readonly disabled").val('');
    	$('#birthday'+num).val('');
    	var idType = $('#idType'+num).val();
        if(idType == 'I'){
        	$('#idNo'+num).bind('blur',{'num':num},idNoBlur);
        	if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].idNo','regexp') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].idNo',true,'regexp');
			}
        }else{
        	$('#idNo'+num).unbind('blur');
        	if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].idNo','regexp') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].idNo',false,'regexp');
			}
        }
    }

	<#--汉字转拼音-->
	function ChineseToPinYin(name){
		var embLogo = '';
	 	$.ajax({
	 		type: 'POST',
	 		async:false,
			url: '${base}/applyInput/getEmbLogo' ,
			data: {"name":name},
			dataType: 'json',
			success:function(res){
				embLogo = res.msg;
			}
		});
		
		return embLogo;
	}
	
	<#--姓名转拼音-->
	function nameBlur(event){
		var num = event.data.num;
		if(num == null || num == undefined){
			num = '';
		}
		var name = $('#name'+num).val();
		if(name != ''){
			$('#embLogo'+num).val(ChineseToPinYin(name));
			if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].embLogo') != null){
				$("#applyInputForm").data('bootstrapValidator').updateStatus('attachCust['+num+'].embLogo','NOT_VALIDATED');
			}
		}	
	}
	<#--添加附卡初始化执行函数-->
	function initAttachCardInfo(num){
		<#--根据"自选卡号标志位"初始化"自选卡号"-->
		var ifSelectedCard = $('#ifSelectedCard'+num).val();
		if(ifSelectedCard != '' && ifSelectedCard == 'Y'){
			var validBit = $('#validBit'+num).val();
			if(validBit == ''){
				$('#ifSelectedCard'+num).removeAttr("disabled readonly");
				$('#cardNo'+num).removeAttr("readonly").attr("maxlength","9")
					.bind('keyup',{'appType':'S','num':num},cardNoKeyup).bind('blur',{'appType':'S','num':num},cleanSelectCardNo);
				$('#deleteCardNoBtn'+num).attr("disabled","disabled"); //禁用解锁卡号按钮
			}
		}else{
			if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].cardNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].cardNo', false,'notEmpty');
			}
			$('#ifSelectedCard'+num).removeAttr("disabled readonly");
			$('#cardNo'+num).val('');
			$('#deleteCardNoBtn'+num).attr("disabled","disabled"); //禁用解锁卡号按钮
		}
		
		<#--根据"姓名"初始化"姓名拼音"-->
		$('#name'+num).bind('blur',{'num':num},nameBlur);
		
		<#--'姓名拼音'绑定大写转化事件-->
		$('#embLogo'+num).bind('input',{},embLogoKeyup);
					
		<#--根据"证件类型"初始化"性别、生日"-->
		var idType = $('#idType'+num).val();
		if(idType != 'I'){
			if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].idNo','regexp') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].idNo', false,'regexp');
			}
		}else{
			$('#idNo'+num).bind('blur',{'appType':'S','num':num},idNoBlur);
		}
		
		<#--根据"证件长期有效"初始化"证件到期日"-->
		var idLastAll = $('#idLastAll'+num).val();
		if(idLastAll != '' && idLastAll == 'Y'){
			if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].idLastDate','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].idLastDate', false,'notEmpty');
			}
			$('#idLastDate'+num).attr('disabled','disabled');
		}
		
		<#--根据"领卡方式"初始化"领卡地址"-->
		var cardFetchMethod = $('#cardFetchMethod'+num).val();
		if(cardFetchMethod != ''){
			if(cardFetchMethod == 'B'){
				if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].cardMailerInd','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].cardMailerInd', false,'notEmpty');
				}
				$('#cardMailerInd').val('');
			}
			if(cardFetchMethod == 'A' || cardFetchMethod == 'E'){
				if($('#applyInputForm').data('bootstrapValidator').getFieldElements('attachCust['+num+'].fetchBranch','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].fetchBranch', false,'notEmpty');
				}
			}
		}
	}
</script>