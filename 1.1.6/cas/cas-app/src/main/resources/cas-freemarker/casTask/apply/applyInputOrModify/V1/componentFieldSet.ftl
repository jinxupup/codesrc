<#if pageFieldMap['${(fieldsetRegion)!}']??>
	<@fieldset legend="${(fieldsetMap[fieldsetRegion])!}:">
		<#if fieldsetRegion == 'A3'>
			<#-- 公务卡单位信息按钮 开始 -->
			<@row id="getOffialCardCorp">
				<@hidden id="subCardType" name="tmProduct.subCardType" value="${(tmProduct.subCardType)!''}" />
				<#include "../../common/offialCardCorpBtn.ftl"/>
			</@row>
			<#--公务卡单位信息按钮 结束 -->
		</#if>
		<#list pageFieldMap['${(fieldsetRegion)!}']?keys as key>
			<#assign fieldPageDto = pageFieldMap['${(fieldsetRegion)!}'][key]>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'Y'><div class="row"></#if>
			<@field label="${(fieldPageDto.fieldName)!}" point_flag="${(fieldPageDto.ifRequire)!}" point="*" field_ar="${(fieldPageDto.fieldAr)!}" label_ar="${(fieldPageDto.labelAr)!}" input_ar="${(fieldPageDto.inputAr)!}">
				<#if fieldPageDto.componentType??>
					<#if fieldPageDto.componentType == "input">
						<#if fieldPageDto.id == "idIssuerAddress">
							
								<@input id="${(fieldPageDto.id)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" />
								<script>
									$(function(){
										$("#${(fieldPageDto.id)!}").on("blur",function(){
											selectAreaCode()
										});
									});
								</script>
						<#else>
							<@input id="${(fieldPageDto.id)!}" readonly="${(fieldPageDto.ifReadonly)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}"/>
						</#if>
					<#elseif fieldPageDto.componentType == "dictSelect">
						<@dictSelect id="${(fieldPageDto.id)!}" dicttype="${(fieldPageDto.dictType)!}" change="${(fieldPageDto.change)!}" name="${(fieldPageDto.name)!}" nullable="${(fieldPageDto.nullable)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}" showcode="${(fieldPageDto.showCode)!}"/>
					<#elseif fieldPageDto.componentType == "select">
						<@select id="${(fieldPageDto.id)!}" options=fieldPageDto.options change="${(fieldPageDto.change)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"  showcode='${(fieldPageDto.showCode)!}' nullable="${(fieldPageDto.nullable)!}"/>
					<#elseif fieldPageDto.componentType == "multipleSelect"><#--没有改变事件-->
						<@multipleSelect id="${(fieldPageDto.id)!}" options=fieldPageDto.options name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" nullable="${(fieldPageDto.nullable)!}" showfilter="true"  single="${(fieldPageDto.textName)!}"/>
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