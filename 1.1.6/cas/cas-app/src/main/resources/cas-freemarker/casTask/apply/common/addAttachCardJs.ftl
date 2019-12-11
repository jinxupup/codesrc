<script type="text/javascript">
	<#--添加主附同申的附卡部分 -->
	var addAttachCard = function(){
		var idNum = $('#idNum').val();//附卡数量，从1开始（取值1,2,3）
		if(idNum == null || idNum == '' || idNum == undefined){
			$('#idNum').val(1);
			return;
		}
		if(idNum >= 3){
			alert('最多只能添加三张附卡');
			return false;
		}
		var div = document.createElement("div");
		var attachCardInfo = document.getElementById("attachCardInfo");
		var flag = false;
		if($('#card1').length == 0){
			div.id = 'card1';
			if($('#card2').length == 0){
				attachCardInfo.appendChild(div);
			}else{//先删除附卡1再添加附卡1
				attachCardInfo.insertBefore(div, document.getElementById("card2"));
				flag = true;
				idNum = 1;
			}
		}else if($('#card2').length == 0){
			div.id = 'card2'
			attachCardInfo.appendChild(div);
		}else{
			return false;
		}
		$('#card'+idNum).load("${base}/applyInput/addAttachCard #attach"+idNum,{'num':idNum,'productCd':$('#productCd').val()},function(){
			addValidFields(idNum);
			$('#ifSelectedCard'+idNum).removeAttr("readonly disabled");
			<#--根据"自选卡号标志位"初始化"自选卡号"-->
			if($('#applyInputForm').data('bootstrapValidator').getOptions('attachCust['+idNum+'].cardNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('attachCust['+idNum+'].cardNo', false,'notEmpty');
			}
			$('#ifSelectedCard'+idNum).removeAttr("disabled readonly");
			$('#deleteCardNoBtn'+idNum).attr("disabled","disabled"); //禁用解锁卡号按钮
			<#--根据"姓名"初始化"姓名拼音"-->
			$('#name'+idNum).bind('blur',{'num':idNum},nameBlur);
			<#--姓名拼音绑定大写转化事件-->
			$('#embLogo'+idNum).bind('input',{},embLogoKeyup);
			<#--根据"证件类型"初始化"性别、生日"-->
			$('#idNo'+idNum).bind('blur',{'appType':'S','num':idNum},idNoBlur);
			if(flag){
	        	idNum = idNum+2;
	        }else{
	        	idNum++;
	        }
			$('#idNum').val(idNum);
		});	
	}
	<#--动态增加附卡的必填项 -->
	function addValidFields(num){
		<#if validFieldMap?? && validFieldMap['attachTab']??>
			<#list validFieldMap['attachTab'] as validFieldInfoDto>
				$('#applyInputForm').bootstrapValidator('addField','${validFieldInfoDto.field}',
					{
            			validators: {
				        	<#if validFieldInfoDto.betweenFlag?? && validFieldInfoDto.betweenFlag>
				        		between: {
			                        min: ${(validFieldInfoDto.betweenMin)!},
			                        max: ${(validFieldInfoDto.betweenMax)!},
			                        message: '请输入${(validFieldInfoDto.betweenMin)!'0'}到${(validFieldInfoDto.betweenMax)!}之间的数值'
			                    },
				        	</#if>
				        	<#if validFieldInfoDto.lengthFlag?? && validFieldInfoDto.lengthFlag>
				        		stringLength: {
			                        max: ${(validFieldInfoDto.lengthMax)!},
			                        message: '最大长度为${(validFieldInfoDto.lengthMax)!}'
			                    },
				        	</#if>
				        	<#if validFieldInfoDto.regexpFlag?? && validFieldInfoDto.regexpFlag>
				        		regexp: {
			                        regexp: /${(validFieldInfoDto.regexp)!}/i,
			                        message: '格式不正确'
			                    },
				        	</#if>
				        	<#if validFieldInfoDto.notEmptyFlag?? && validFieldInfoDto.notEmptyFlag>
					         	notEmpty: {
					            	message: '不能为空'
					          	}
				          	</#if>
				        }
	              });
        	</#list>
		</#if>
	}
	<#--删除主附同申的附卡 -->
	function removeCard(num){
		deleteValidFields(num);
		var card = document.getElementById('card'+num);
		card.parentNode.removeChild(card);
		var idNum = $('#idNum').val();
		$('#idNum').val(--idNum);
	 }
	 
	<#--动态删除附卡的必填项 -->
	function deleteValidFields(num){
		<#if validFieldMap?? && validFieldMap['attachTab']??>
			<#list validFieldMap['attachTab'] as validFieldInfoDto>
				$('#applyInputForm').bootstrapValidator('removeField','${validFieldInfoDto.field}');
        	</#list>
		</#if>
	}
</script>