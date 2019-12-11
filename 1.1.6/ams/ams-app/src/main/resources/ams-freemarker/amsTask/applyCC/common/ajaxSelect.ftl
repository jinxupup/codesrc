<#macro ajaxSelect id="" name="" options={}  value="" nullable="false" append="false" multiple="false" click=""
	value_split="," showfilter="true" single="true" line_options="false" option_width="80" select_all="false" 
	on_open="" on_close="" on_check_all="" on_uncheck_all="" on_focus="" on_blur="" on_optgroup_click="" on_option_click="" on_filter=""
	showcode="true" valid={}  readonly="" class="" style="" width="" change="" label_only="false">

	<#if ! id?? ||id=="" >
		<#local id=ar_('uuid') />
	</#if>
	<#if label_only=="true">
		<#list options?keys as key>
			<span class="label-only ${class}" style="${style};width:${width}">
				<#list value?split(value_split) as value>
					<#if value==key ><#if showcode=="true">${key}-</#if>${options[key]}<#if value_has_next>,</#if></#if>
		    	</#list>
	    	</span>
	    </#list>
	    <#nested />
		<#return />
	</#if>
	<input id="${id}_hidden_value" type="hidden" value="${value}" readonly="readonly">
	<#if readonly?string=="readonly" || readonly?string=="true" || (valid?size>=1) >
		<input id="${id}_ar_hidden_" name="${name}" value="${value}"  data-toggle="unicorn-select-bind-hidden" class="form-control" 
			<#if readonly?string=="readonly" || readonly?string=="true">
				data-bv-notEmpty="false" 
			</#if>
			<@validator valid=valid /> 
			style="position:fixed;right:-100px;bottom:-1000px;"
		 /> 
	</#if>
 	
	<select id="${id}" multiple="multiple"
		<#if readonly?string=="readonly" || readonly?string=="true" || (valid?size>=1) >
			name="${name}_ar_show_"
			data-toggle="unicorn-select-bind-hidden"
			<#if readonly?string=="readonly" || readonly?string=="true">
				readonly="readonly" disabled="disabled"
				data-bv-notEmpty="false"
			</#if>
		<#else>
			name="${name}"
		</#if>
		ajax="true"
		<@validator valid=valid />  class="form-control ${class}"  style="${style};width:${width}" 
		<#if change?? && change!="">
			ajaxchange="true"
		</#if>
	>
		<#if nullable=="true">
			<option value=""></option>
		</#if>
		<#list options?keys as key> 
			<option value="${key}" 
				<#list value?split(value_split) as value>
					<#if value==key >selected="selected"</#if> 
				</#list>
				
			> <#if showcode=="true">${key}-</#if>${options[key]}</option>
	    </#list>
	    <#nested />
	</select>
	
	<script type="text/javascript">
		$(function(){
			$("[id='${id}']").multipleSelect({delimiter:"${value_split}",filter:${showfilter},single:${single},multiple:${line_options},multipleWidth:${option_width},selectAll:${select_all} 
				<#if on_open?string!="">,onOpen:${on_open}</#if>
				<#if on_close?string!="">,onClose:${on_close}</#if>
				<#if on_check_all?string!="">,onCheckAll:${on_check_all}</#if>
				<#if on_uncheck_all?string!="">,onUncheckAll:${on_uncheck_all}</#if>
				<#if on_focus?string!="">,onFocus:${on_focus}</#if>
				<#if on_blur?string!="">,onBlur:${on_blur}</#if>
				<#if on_optgroup_click?string!="">,onOptgroupClick:${on_optgroup_click}</#if>
				<#if on_option_click?string!="">,onClick:${on_option_click}</#if>
				<#if on_filter?string!="">,onFilter:${on_filter}</#if>
			});
			<#if name?? && name!=""><#-- 将变动状态重置 -->
			(function _form_multipleSelect_${id}(){
				var $_form_multipleSelect_${id} =  $("[id='${id}']").closest("form");
				if( $_form_multipleSelect_${id}.length==0){
					return ;
				}else{
					if($_form_multipleSelect_${id}.data('bootstrapValidator').getOptions('${name}') != null){
						$_form_multipleSelect_${id}.data('bootstrapValidator').updateStatus("${name}", 'NOT_VALIDATED');
					}
				}
			})();
			</#if>
			<#if click?? && click!="">
				$("[id='${id}']").siblings('.ms-parent').children('.ms-choice').bind("click",function(){
					<@js_funcEvent func="${click}"/>;
				});
			</#if>
			<#if change?? && change!="">
				$("[id='${id}']").on("ajaxchange",function(){
					<@js_funcEvent func="${change}"/>;
				});
				$("[id='${id}']").siblings('.ms-parent').delegate('li','click',function(){
					var oldValue = $("#${id}_hidden_value").val();
					var newValue = $(this).find('input').attr("value");
					if(oldValue != newValue){
						$("#${id}_hidden_value").val(newValue);
						$("[id='${id}']").trigger('ajaxchange');
					}
				});
			</#if>
		});
	</script>
</#macro>