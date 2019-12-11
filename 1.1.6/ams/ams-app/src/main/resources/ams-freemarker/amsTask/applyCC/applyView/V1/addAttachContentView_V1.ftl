<#if pageFieldMap['attachTab_${(atId)!}']??>
	<@fieldset legend="附卡申请人${(atId)!}信息:">
		<#list pageFieldMap['attachTab_${(atId)!}']?keys as key>
			<#assign fieldPageDto = pageFieldMap['attachTab_${(atId)!}'][key]>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'Y'><div class="row"></#if>
			<@field label="${(fieldPageDto.fieldName)!}" field_ar="${(fieldPageDto.fieldAr)!}" label_ar="${(fieldPageDto.labelAr)!}" input_ar="${(fieldPageDto.inputAr)!}">
				<#if fieldPageDto.componentType??>
					<#if fieldPageDto.componentType == "input">
						<@input id="${(fieldPageDto.id)!}" value="${(fieldPageDto.value)!}" label_only="true"/>
					<#elseif fieldPageDto.componentType == "dictSelect">
						<@dictSelect id="${(fieldPageDto.id)!}" dicttype="${(fieldPageDto.dictType)!}" value="${(fieldPageDto.value)!}" label_only="true"/>
					<#elseif fieldPageDto.componentType == "select" || fieldPageDto.componentType == "ajaxSelect">
						<@select id="${(fieldPageDto.id)!}" options=fieldPageDto.options value="${(fieldPageDto.value)!}" showcode='${(fieldPageDto.showCode)!}' label_only="true"/>
					<#elseif fieldPageDto.componentType == "multipleSelect"><#--没有改变事件-->
						<@multipleSelect id="${(fieldPageDto.id)!}" options=fieldPageDto.options value="${(fieldPageDto.value)!}" label_only="true"/>
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