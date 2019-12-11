<#if pageFieldMap['${(fieldsetRegion)!}']??>
	<@fieldset legend="${(fieldsetMap[fieldsetRegion])!}:">
		<#list pageFieldMap['${(fieldsetRegion)!}']?keys as key>
			<#assign fieldPageDto = pageFieldMap['${(fieldsetRegion)!}'][key]>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'Y'><div class="row"></#if>
			<@field label="${(fieldPageDto.fieldName)!}:" field_ar="${(fieldPageDto.fieldAr)!}" label_ar="${(fieldPageDto.labelAr)!}" input_ar="${(fieldPageDto.inputAr)!}">
				<#if fieldPageDto.componentType??>
					<#if fieldPageDto.componentType == "input">
						<@input id="${(fieldPageDto.id)!}" value="${(fieldPageDto.value)!}" label_only="true"/>
					<#elseif fieldPageDto.componentType == "dictSelect">
						<@dictSelect id="${(fieldPageDto.id)!}" dicttype="${(fieldPageDto.dictType)!}" value="${(fieldPageDto.value)!}" label_only="true"/>
					<#elseif fieldPageDto.componentType == "select" || fieldPageDto.componentType == "ajaxSelect">
						<@select id="${(fieldPageDto.id)!}" options=fieldPageDto.options value="${(fieldPageDto.value)!}" showcode='${(fieldPageDto.showCode)!}' label_only="true"/>
					<#elseif fieldPageDto.componentType == "multipleSelect"><#--没有改变事件-->
						<@multipleSelect id="${(fieldPageDto.id)!}" options=fieldPageDto.options value="${(fieldPageDto.value)!}" single="${(fieldPageDto.textName)!}" label_only="true"/>
					<#elseif fieldPageDto.componentType == "date">
						<@date id="${(fieldPageDto.id)!}" value="${(fieldPageDto.value)!}" label_only="true"/>
					<#elseif fieldPageDto.componentType == "textarea">
						<@textarea id="${(fieldPageDto.id)!}" rows="${(fieldPageDto.dictType)!}" value="${(fieldPageDto.value)!}" label_only="true"/>
					</#if>
				</#if>
			</@field>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'N'></div></#if>
		</#list>
	</@fieldset>
</#if>