<#if pageFieldMap['A1']??>
	<@fieldset legend="申请信息:">
		<#list pageFieldMap['A1']?keys as key>
			<#assign fieldPageDto = pageFieldMap['A1'][key]>
			<@field label="${(fieldPageDto.fieldName)!}" point_flag="${(fieldPageDto.ifRequire)!}" point="*">
				<#if fieldPageDto.componentType??>
					<#if fieldPageDto.componentType == "input">
						<#if fieldPageDto.id == "cardNo0">
							<div style="margin-right: -5px;margin-left: -5px;">
								<div class="col-ar-10" style="padding-right:0px;">
									<@input id="cardBin" name="cardBin" readonly="true" value="${(attachCardNoMap['cardBin5'])!}"/>
								</div>
								<div class="col-ar-14" style="padding-right:0px;padding-left:0px;">
									<@input id="cardNo" readonly="true" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}"/>
								</div>
								<div class="col-ar-4" style="padding-left:0px;">
									<@input id="validBit" name="validBit" value="${(attachCardNoMap['validBit5'])!}" readonly="readonly"/>
								</div>
								<div class="col-ar-8" style="padding-left:0px;">
									<@button id="deleteCardNoBtn" name="解锁卡号" click="deleteSelectCardNo('S')"/>
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
		</#list>
		<@row>
			<@field label="主卡人姓名">
				<@input value="${(applyOperateDto.name)!}" readonly="true" />
			</@field>
			<@field label="主卡人证件类型">
				<@dictSelect dicttype="IdType" value="${(applyOperateDto.idType)!}" readonly="true" />
			</@field>
			<@field label="主卡人证件号码">
				<@input value="${(applyOperateDto.idNo)!}" readonly="true" />
			</@field>
		</@row>
		<@row>
			<@field label="主卡卡产品代码">
				<@select id="productCd" readonly="true" name="tmAppMain.productCd" value="${(applyOperateDto.productCd)!}" options=ams_('tableMap','productForStatus','A') />
			</@field>
			<@field label="主卡卡面">
				<@select options=ams_("tableMap","productCardFace",'${(applyOperateDto.productCd)!}')
					showcode='true' nullable="false" readonly="true" name="tmAppCardfaceInfo.pyhCd" value="${(applyOperateDto.pyhCd)!''}" />
			</@field>
			<@field label="主卡申请日期">
				<@date datetype="datetime" readonly="true" value="${(applyOperateDto.createDate?datetime)!}"/>
			</@field>
		</@row>
	</@fieldset>
</#if>
	
<#assign fieldsetMap = {'A2':'申请人基本信息','A7':'卡片寄送方式','A8':'银行专用栏'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldSet.ftl"/>
</#list>
		  
<script type="text/javascript">
	<#--附卡初始化-->
	$(function(){
		<#--根据"自选卡号标志位"初始化"自选卡号"-->
		var ifSelectedCard = $('#ifSelectedCard').val();
		if(ifSelectedCard != '' && ifSelectedCard == 'Y'){
			var validBit = $('#validBit').val();
			if(validBit == ''){
				$('#ifSelectedCard').removeAttr("disabled readonly");
				$('#cardNo').removeAttr("readonly").attr("maxlength","${(editCardNoLen)!9}")
							.bind('keyup',{'appType':'S'},cardNoKeyup).bind('blur',{'appType':'S'},cleanSelectCardNo);
				$('#deleteCardNoBtn').attr("disabled","disabled"); //禁用解锁卡号按钮
			}
		}else{
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.cardNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.cardNo',false,'notEmpty');
			}
			$('#ifSelectedCard').removeAttr("disabled readonly");
			$('#cardNo').val('');
			$('#deleteCardNoBtn').attr("disabled","disabled"); //禁用解锁卡号按钮
		}
		
		<#--'姓名拼音'绑定大写转化事件-->
		$('#embLogo').bind('input',{},embLogoKeyup);
		
		<#--根据"证件类型"初始化"性别、生日"-->
		var idType = $('#idType').val();
		if(idType != 'I'){
			$('#gender').removeAttr("disabled readonly");
			$('#birthday').removeAttr("readonly data-hide-onclick").attr("onclick","WdatePicker({skin:'twoer',dateFmt:'yyyy-MM-dd'})");
		}
		
		<#--根据"证件长期有效"初始化"证件到期日"-->
		var idLastAll = $('#idLastAll').val();
		if(idLastAll != '' && idLastAll == 'Y'){
			if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.cardNo','notEmpty') != null){
				$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.cardNo',false,'notEmpty');
			}
			$('#idLastDate').attr('disabled','disabled');
		}
				
		<#--根据"领卡方式"初始化"领卡地址"-->
		var cardFetchMethod = $('#cardFetchMethod').val();
		if(cardFetchMethod != ''){
			if(cardFetchMethod == 'B'){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.cardMailerInd','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.cardMailerInd',false,'notEmpty');
				}
				$('#cardMailerInd').val('').attr('disabled','disabled');
			}
			if(cardFetchMethod == 'A' || cardFetchMethod == 'E'){
				if($('#applyInputForm').data('bootstrapValidator').getOptions('tmAppAttachApplierInfo.fetchBranch','notEmpty') != null){
					$('#applyInputForm').data('bootstrapValidator').enableFieldValidators('tmAppAttachApplierInfo.fetchBranch',false,'notEmpty');
				}
			}
		}
	});
</script>
