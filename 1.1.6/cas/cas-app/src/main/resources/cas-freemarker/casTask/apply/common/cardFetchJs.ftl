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
				if($('#applyInputForm').data('bootstrapValidator').getOptions('attachCust['+num+'].cardMailerInd','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].cardMailerInd',false,'notEmpty');
				}
				if($('#applyInputForm').data('bootstrapValidator').getOptions('attachCust['+num+'].fetchBranch','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].fetchBranch',true,'notEmpty').validateField('attachCust.fetchBranch');
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
					if($('#applyInputForm').data('bootstrapValidator').getOptions('attachCust.cardMailerInd','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust.cardMailerInd',false,'notEmpty');
					}
					if($('#applyInputForm').data('bootstrapValidator').getOptions('attachCust.fetchBranch','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust.fetchBranch',true,'notEmpty').validateField('attachCust.fetchBranch');
					}
				}
			}
		}else{
			$('#cardMailerInd' + num).removeAttr('disabled').val('C');
			$('#fetchBranch' + num).multipleSelect('setSelects', ['']);
			if(num != ''){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('attachCust['+num+'].fetchBranch','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].fetchBranch',false,'notEmpty');
				}
				if($('#applyInputForm').data('bootstrapValidator').getOptions('attachCust['+num+'].cardMailerInd','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+num+'].cardMailerInd',true,'notEmpty').validateField('attachCust.cardMailerInd');
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
					if($('#applyInputForm').data('bootstrapValidator').getOptions('attachCust.fetchBranch','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust.fetchBranch',false,'notEmpty');
					}
					if($('#applyInputForm').data('bootstrapValidator').getOptions('attachCust.cardMailerInd','notEmpty') != null){
						$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust.cardMailerInd',true,'notEmpty').validateField('attachCust.cardMailerInd');
					}
				}
			}
		}
	}
</script>