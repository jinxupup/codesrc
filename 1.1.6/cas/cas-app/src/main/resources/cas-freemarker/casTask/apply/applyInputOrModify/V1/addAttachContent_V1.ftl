<#if pageFieldMap['attachTab_${(atId)!}']??>
	<@fieldset id="attach${(atId)!}" legend="附卡申请人${(atId==0)?string('','${(atId)!}')}信息:">
		<@row>
			<#if atId?? && atId == 0>
				<#if showBtnFlag?? && showBtnFlag>
					<@button name="新增附卡" click="addAttachCard"/>
					<#include "../../common/addAttachCardJs.ftl"/>
				</#if>
				<#include "../../common/attachCardCommonJs.ftl"/><#--附卡公共js方法-->
			<#else>
				<#if showBtnFlag?? && showBtnFlag>
					<@button name="删除附卡${(atId)!}" click="removeCard('${(atId)!}')"/>
				</#if>
			</#if>
		</@row>
		<@field hidden="true">
			<@input name="attachCust[${(atId)!}].attachNo" value="${(atId+5)!}" />
		</@field>
		<#list pageFieldMap['attachTab_${(atId)!}']?keys as key>
			<#assign fieldPageDto = pageFieldMap['attachTab_${(atId)!}'][key]>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'Y'><div class="row"></#if>
			<@field label="${(fieldPageDto.fieldName)!}" point_flag="${(fieldPageDto.ifRequire)!}" point="*">
				<#if fieldPageDto.componentType??>
					<#if fieldPageDto.componentType == "input">
						<#if fieldPageDto.id == "cardNo${(atId)!}">
							<div style="margin-right: -5px;margin-left: -5px;">
								<div class="col-ar-10" style="padding-right:0px;">
									<#assign attachCardBin="cardBin${(atId+5)!}">
									<@input id="cardBin${(atId)!}" name="cardBin${(atId)!}" readonly="true" value="${(attachCardNoMap[attachCardBin])!}"/>
								</div>
								<div class="col-ar-14" style="padding-right:0px;padding-left:0px;">
									<@input id="cardNo${(atId)!}" readonly="true" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}"/>
								</div>
								<div class="col-ar-4" style="padding-left:0px;">
									<#assign attachValidBit="validBit${(atId+5)!}">
									<@input id="validBit${(atId)!}" name="validBit${(atId)!}" value="${(attachCardNoMap[attachValidBit])!}" readonly="readonly"/>
								</div>
								<div class="col-ar-8" style="padding-left:0px;">
									<@button id="deleteCardNoBtn${(atId)!}" name="解锁卡号" click="deleteSelectCardNo('S','${(atId)!}')"/>
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
		<script type="text/javascript">
			<#--附卡初始化(执行函数在'common/attachCardCommonJs.ftl'文件里)-->
			$(function(){
				initAttachCardInfo('${(atId)!'0'}');
			});	
		</script>
	</@fieldset>
</#if>