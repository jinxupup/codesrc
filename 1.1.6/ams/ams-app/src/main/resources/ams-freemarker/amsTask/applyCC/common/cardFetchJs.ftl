<script type="text/javascript">
	<#--卡片领取方式的改变 -->
	function cardFetchMethodChange(appType, num){
		if(appType == null || appType == ''){
			alert("卡片领取方式选择操作异常!");
			return false;
		}
		if(num == null || num == undefined){
			num = '';
		}
		var cardFetchMethod = $('#cardFetchMethod' + num).val();
		if(cardFetchMethod == 'B'){
			$('#cardMailerInd' + num).val('').attr('disabled','disabled');
			$('#fetchBranch' + num).removeAttr('disabled readonly');
			var owningBranch = $('#owningBranch').val();
			$('#fetchBranch' + num).multipleSelect('setSelects', [owningBranch]);
			if(num != ''){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+num+'].cardMailerInd','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo['+num+'].cardMailerInd',false,'notEmpty');
				}
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+num+'].fetchBranch','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo['+num+'].fetchBranch',true,'notEmpty').validateField('tmAppAttachApplierInfo.fetchBranch');
				}
			}else{
				if(appType == 'A'){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.cardMailerInd','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.cardMailerInd',false,'notEmpty');
					}
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.fetchBranch','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.fetchBranch',true,'notEmpty').validateField('tmAppPrimCardInfo.fetchBranch');
					}
				}
				if(appType == 'S'){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.cardMailerInd','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.cardMailerInd',false,'notEmpty');
					}
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.fetchBranch','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.fetchBranch',true,'notEmpty').validateField('tmAppAttachApplierInfo.fetchBranch');
					}
				}
			}
		}else{
			$('#cardMailerInd' + num).removeAttr('disabled').val('C');
			$('#fetchBranch' + num).multipleSelect('setSelects', ['']);
			if(num != ''){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+num+'].fetchBranch','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo['+num+'].fetchBranch',false,'notEmpty');
				}
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo['+num+'].cardMailerInd','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo['+num+'].cardMailerInd',true,'notEmpty').validateField('tmAppAttachApplierInfo.cardMailerInd');
				}	
			}else{
				if(appType == 'A'){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.fetchBranch','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.fetchBranch',false,'notEmpty');
					}
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppPrimCardInfo.cardMailerInd','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppPrimCardInfo.cardMailerInd',true,'notEmpty').validateField('tmAppPrimCardInfo.cardMailerInd');
					}
				}
				if(appType == 'S'){
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.fetchBranch','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.fetchBranch',false,'notEmpty');
					}
					if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.cardMailerInd','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.cardMailerInd',true,'notEmpty').validateField('tmAppAttachApplierInfo.cardMailerInd');
					}
				}
			}
		}
	}
</script>