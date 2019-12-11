
<#macro form id="" action="" 
	cols="3" method="post" enctype="application/x-www-form-urlencoded" ajax="true" multi_submit="false" after="" before="" success_url="" class="" style="" 
	auto_valid="true" valid_excluded=""
>
	<#if ! id?? ||id=="" >
		<#local id=ar_('uuid') />	
	</#if>

	<#-- cols默认3，36/3，默认每列12。_ftl_form_field会影像内部字段宽度的计算 -->
	<#assign  _ftl_form_field_ar=(36/(cols?number))?int />
	
	<form id=${id} action="${base}/${action}" method="${method}" enctype="${enctype}" class="container-fluid ${class}"  style="${style}" data-ar-auto_valid="${auto_valid}" 
	   <#if success_url?? && success_url!="">data-ar-success_url="${base}/${ar_('success_url_replace',success_url)}"</#if>
	>
		<#nested />
		<script type="text/javascript">
			$(function(){
			
				<#-- 表单$ -->	
				var $form=$('[id="${id}"]');
				
				<#-- 绑定验证 -->	
				<#if auto_valid=="true">
					$('[id="${id}"]').bootstrapValidator({<#if valid_excluded!="">excluded:"${valid_excluded}"</#if>});
				</#if>
				
				/*置灰按钮*/							
				var $submitButtons = $(":submit",$form);
				$submitButtons.each(function(i){
					$(this).removeAttr("disabled");
					$(this).blur();
				});	
							
								
				<#if ajax=="true">
					$form.ajaxForm({
						dataType:'json',
						/*beforeSubmit:function(){ 修改为beforeSerialize,防止提交前不操作数据*/
						beforeSerialize:function(){
							<#if before?? && before!="">
								var before_result = (<@js_funcEvent func="${before}"/>); 
								if(before_result==false){
									return false;
								}
							</#if>
						},
						success:function(res) {
						
					        <#if after?? && after!="">
								var after_res = (<@js_funcEvent func="${after}" params="res"/>);		
								if(after_res==false){
									return false;
								}
							<#else>
								alert(res.msg);
								if(res.s){
									<#if multi_submit=="true">
										var $submitButtons = $(":submit",$form);
										$submitButtons.each(function(i){
											$(this).removeAttr("disabled");
											$(this).blur();
										});
									</#if>
								}else{
								    /*操作失败，默认恢复提交按钮*/
									var $submitButtons = $(":submit",$form);
									$submitButtons.each(function(i){
										$(this).removeAttr("disabled");
										$(this).blur();
									});
									return false;
								}
							</#if>
							
							<#if success_url?? && success_url!="">
								var success_url = $form.attr("data-ar-success_url");
                                var eval_success_url = "success_url = '" + success_url;
                                eval(eval_success_url);
                    
                                window.location.href="";
                                window.location.href=success_url;
							</#if>
					    }
					});
				</#if>
			});
		</script>
	</form>
	
	<#assign _ftl_form_field_ar=""/> 
</#macro>


<#-- field -->
<#macro field id="" label="" field_ar="" label_ar="" input_ar="" point="" point_class="" point_style="" point_flag="false" show="true" hidden="false">
	
	<#if show=="false" >
		<#return />
	</#if>
	
	<#if field_ar?? && field_ar!="" >
		<#local field_ar=field_ar />
	<#else>
		<#if _ftl_form_field_ar?? && _ftl_form_field_ar?string!=""  >
			<#local field_ar= _ftl_form_field_ar />
		<#else>
			<#local field_ar= 12 />
		</#if>
	</#if>
	
	<#if label_ar?? && label_ar?string==""  >
		<#local label_ar= 14 />
	</#if>
	<#if input_ar?? && input_ar?string==""  >
		<#local input_ar= 22 />
	</#if>
    
	<div <#if id?? && id !="">id=${id}</#if> class="form-group col-ar-${field_ar}"  <#if hidden=="true"> style="display:none;"</#if>  >
		<label class="col-ar-${label_ar} control-label" >
			<i style="margin-right:1px;${(point_class=='')?string('color:red','')} ${point_style}" class="field-point fa ${point_class}" ><#if  point_flag !="" && point_flag != "false">${point}</#if></i>${label}</label>
		<div class="col-ar-${input_ar} ">
			<#nested />
		</div>
	</div>
</#macro>

<#-- 验证器 -->
<#macro validator valid={} >
    <#list valid?keys as key> 
       data-bv-${key}="${valid[key]}"
    </#list>
</#macro>

<#-- input -->
<#--
name 启用验证，必须有name属性。只有name属性的值，才会提交到后台
type 可用值 type/password
-->
<#macro input id="" name="" type="text" value="" readonly="false" valid={} class="" style="" width="" label_only="false">
	<#if label_only=="true">
		<span id="${id}" class="label-only ${class}" style="${style};width:${width}" >${value}</span>
		<#return />
	</#if>
	 <input id="${id}" name="${name}" value="${value}" type="${type}"
	 <#if readonly?string=="readonly"||readonly?string=="true"> readonly="readonly"</#if>
	 <@validator valid=valid />  class="form-control ${class}" style="${style};width:${width}" />
</#macro>

<#macro hidden id="" name="" value="">
     <input id="${id}" name="${name}" value="${value}" type="hidden" />
</#macro>

<#-- select -->
<#macro select  id="" name="" options={}  value="" nullable="true" showcode="true" valid={}  readonly="" class="" style="" width="" change="" label_only="false" append="false" 
	s2="false" search="false" multiple="false" value_split=",">
	
	<#if ! id?? ||id=="" >
		<#local id=ar_('uuid') />	
	</#if>
	
	<#if label_only=="true">
	    
	    <#if multiple=="true" || multiple=="multiple">
		    <#list options?keys as key> 
				<#list value?split(value_split) as value>
					<#if value==key ><span class="label-only ${class}" style="${style};width:${width}" ><#if showcode=="true">${key}-</#if>${options[key]} </span></#if>
				</#list>
		    </#list>
		 <#else>
		 	<#list options?keys as key> 
				<#if value==key ><span class="label-only ${class}" style="${style};width:${width}" ><#if showcode=="true">${key}-</#if>${options[key]} </span></#if>
		    </#list>
		 </#if>
	    
	    <#nested />
		<#return />
	</#if>
	
	<#if readonly?string=="readonly"||readonly?string=="true">
		<#local readonly_boolean=true />
		<input id="${id}_ar_hidden_" name="${name}" value="${value}"  data-toggle="unicorn-select-bind-hidden" type="hidden" />
	<#else>
		<#local readonly_boolean=false />
	</#if>
 
	<select id="${id}" 
		<#if readonly_boolean==true>
			name="${name}_ar_show_"
			readonly="readonly" disabled="disabled"
			data-bv-notEmpty="false"
			data-toggle="unicorn-select-bind-hidden"
		<#else>
			name="${name}"
		</#if>
		
		<@validator valid=valid />  class="form-control ${class}"  style="${style};width:${width}" 
		<#if change?? && change!="">
			onchange="<@js_funcEvent func="${change}"/>"
		</#if>
		
		<#if multiple=="true" || multiple=="multiple">
			multiple="multiple"
		</#if>
		
	>
	
		<#local isMatch=false /> <#--是否被匹配上-->
		
		<#if nullable=="true" && multiple=="false">
			<option value="">&nbsp;</option>
		</#if>
		
	    <#if multiple=="true" || multiple=="multiple">
		    <#list options?keys as key> 
				<option value="${key}" 
					<#list value?split(value_split) as value>
						<#if value==key >selected="selected"</#if> 
					</#list>
				> <#if showcode=="true">${key}-</#if>${options[key]}</option>
		    </#list>
		 <#else>
		 	<#list options?keys as key> 
				<option value="${key}" <#if value==key >selected="selected"<#local isMatch=true/></#if> > <#if showcode=="true">${key}-</#if>${options[key]}</option>
		    </#list>
		 </#if>
	    
	    <#if append=="true" >
	    	<#if isMatch==false && value?? && value?string!="">
	    		<option value="${value}" selected="selected"> <#if showcode=="true">${value}-</#if>${value}</option>
	    	</#if>
	    </#if>
	    
	    <#nested />
	</select>
	
	<#-- <#if s2=="true">
	<script>
		$("#${id}").select2({
			language: 'zh-CN',
			<#if readonly_boolean!=true>
				placeholder: "",
				allowClear: true,
			</#if>
			width: "100%"
			<#if search=="false">
				,minimumResultsForSearch: "Infinity"
			</#if>
			<#if multiple=="true"||multiple=="multiple">
				,closeOnSelect: false
			</#if>
		});
		/*$("form .select2-container").addClass("form-control");*/
		$("#${id}").next().addClass("form-control");
	</script>
	</#if>
	 -->
</#macro>

<#-- checkbox / radio -->
<#macro checkbox id="" name="" type="checkbox" options={}  value="" value_split="," nullable="true" showcode="true"  readonly="" class="" style="" >
	<input id="${id}_ar_hidden_" name="${name}" value="${value}"  data-ar-value_split="${value_split}" data-toggle="unicorn-checkbox-bind-hidden" type="hidden" />
	
	  <div class="checkbox">
		<#list options?keys as key> 
			<label>
		      <input type="${type}" value="${key}" data-ar-value_split="${value_split}" data-toggle="unicorn-checkbox-bind-hidden" name="${name}_ar_show_"  class="${class}" style="${style};"   
		      <#if readonly?string=="readonly"||readonly?string=="true"> readonly="readonly" disabled="disabled" data-bv-notEmpty="false" </#if>
		      	<#list value?split(value_split) as val>
				 <#if val==key >checked="checked"</#if> 
				</#list>
		      > <#if showcode=="true">${key}-</#if>${options[key]}
		    </label>
	    </#list>
	    <#nested />
	  </div>
</#macro>

<#-- date -->
<#macro date id="" name="" datetype="date" value=""  readonly=""  settings={}  valid={} width="" class="" style=""  label_only="false">
	
	<#if label_only=="true">
		<span class="label-only ${class}" style="${style};width:${width}" >${value}</span>
		<#return />
	</#if>
	<#local dateFmt="yyyy-MM-dd">
	<#if datetype=="date">
		<#local dateFmt="yyyy-MM-dd">
	<#elseif datetype=='time'>
		<#local dateFmt="HH:mm:ss">
	<#elseif datetype=="datetime">
		<#local dateFmt="yyyy-MM-dd HH:mm:ss" />
	<#elseif datetype=="datemonth">
		<#local dateFmt="yyyy-MM">
	</#if>
	
	<#if settings?? && settings.dateFmt ?? && settings.dateFmt!="">
		<#local dateFmt=settings.dateFmt />
	</#if>
	
    <input type="input" id="${id}" name="${name}" value="${value}" class="form-control unicorn-date ${class}" style="${style};width:${width}"  
    	<#if readonly?string=="readonly"||readonly?string=="true"> 
    		readonly="readonly" 
		 	data-hide-onclick="WdatePicker({skin:'twoer',dateFmt:'${dateFmt}' <@mapToStr map=settings prefix="," />})"
    	<#else>
    		onclick="WdatePicker({skin:'twoer',dateFmt:'${dateFmt}' <@mapToStr map=settings prefix="," />})"
    	</#if>
	 	<@validator valid=valid />
	 		
 		<#if name??&&name!="" > 
	 		onchange="ar_.date_change('${name}')"
    	</#if>
	 		
		 	<#-- bootstrapValidator的验证和my97的有冲突 
	    	data-bv-date="true" data-bv-date-format="${dateFmt}"
	    	-->
	 />
</#macro>

<#-- file -->
<#macro file id="" name="" value="" readonly="false" valid={} class="" style="" width="">
     <input id="${id}" name="${name}" value="${value}" type="file"
     <#if readonly?string=="readonly"||readonly?string=="true"> readonly="readonly"</#if>
     <@validator valid=valid />  class="form-control ${class}" style="${style};width:${width}" />
</#macro>

<#-- selectLink -->
<#-- link_id关联的id -->
<#macro selectLink id="" name="" value="" link_id="" options={} url="" url_data={} url_parent_key="" keyfield="code" valuefield="codeName" filter=[] append="false"
	nullable="true" showcode="true" valid={}  readonly="" class="" style="" width="" change="" label_only="false" s2="false" search="false" multiple="false" value_split=",">
	
	<#if ! id?? ||id=="" >
		<#local id=ar_('uuid') />	
	</#if>
	
	<#if label_only=="true">
	    <#if multiple=="true" || multiple=="multiple">
		    <#list options?keys as key> 
				<#list value?split(value_split) as value>
					<#if value==key ><span class="label-only ${class}" style="${style};width:${width}" ><#if showcode=="true">${key}-</#if>${options[key]} </span></#if>
				</#list>
		    </#list>
		 <#else>
		 	<#list options?keys as key> 
				<#if value==key ><span class="label-only ${class}" style="${style};width:${width}" ><#if showcode=="true">${key}-</#if>${options[key]} </span></#if>
		    </#list>
		 </#if>
	    <#nested />
		<#return />
	</#if>
	
	<#if readonly?string=="readonly"||readonly?string=="true">
		<#local readonly_boolean=true />
		<input id="${id}_ar_hidden_" name="${name}" value="${value}"  data-toggle="unicorn-select-bind-hidden" type="hidden" />
	<#else>
		<#local readonly_boolean=false />
	</#if>
	
	<select id="${id}"  <@validator valid=valid />  class="form-control ${class}"  style="${style};width:${width}"  
		
		<#if readonly_boolean==true>
			name="${name}_ar_show_"
			readonly="readonly" disabled="disabled"
			data-bv-notEmpty="false"
			data-toggle="unicorn-select-bind-hidden"
		<#else>
			name="${name}"
		</#if>
		
		<#if change?? && change!="">
			onchange="<@js_funcEvent func="${change}"/>"
		</#if>
		
		<#if multiple=="true" || multiple=="multiple">
			multiple="multiple"
		</#if>
	>
	
		<#local isMatch=false /> <#--是否被匹配上-->
		
		<#if nullable=="true" && multiple=="false">
			<option value=""></option>
		</#if>
		<#list options?keys as key>
			<#local inFilter=false />
			<#list filter as f>
				<#if f?string == key?string>
					<#local inFilter=true />
				</#if>
			</#list>
			<#if inFilter==false>
				<#if multiple=="true" || multiple=="multiple">
					<option value="${key}" 
						<#list value?split(value_split) as value>
							<#if value==key >selected="selected"</#if> 
						</#list>
					> <#if showcode=="true">${key}-</#if>${options[key]}</option>
				 <#else>
					<option value="${key}" <#if value==key >selected="selected"<#local isMatch=true/></#if> > <#if showcode=="true">${key}-</#if>${options[key]}</option>
				 </#if>
			</#if>
	    </#list>
	    
	    <#if append=="true" >
	    	<#if isMatch==false && value?? && value?string!="">
	    		<option value="${value}" selected="selected"> <#if showcode=="true">${value}-</#if>${value}</option>
	    	</#if>
	    </#if>
	    
	    <#nested />
	</select>
	
	<script type="text/javascript">
		$(function() {
			$("[id='${link_id}']").on('change',function(){
				var url_parent_value = $(this).val();
				if(url_parent_value==""){
					url_parent_value = "_ar_null_";
				}
				<#if url?? && url!="">
                var url = "${base}/${url}";
                var data = {_PARENT_KEY:"${url_parent_key}", _PARENT_VALUE:url_parent_value};
                	<#if url_data?? && url_data?size gt 0 >
	                    var url_data = "<@mapToMapStr map=url_data />";
	                    data = eval('(' + url_data + ')');
	                    data = $.extend({},data);
                	</#if>
                
                $("[id='${id}']").empty();
                $("[id='${id}']").val("");
                $("[name='${name}']").val("");
                
                <#if nullable=="true" && multiple=="false" >
					$("[id='${id}']").append('<option value="" >&nbsp;</option>');
				</#if>
						
                $.post(ar_.randomUrl(url), data,
                  function(res){
	                    if(res.s){
	                        var options = res.obj;
							if(options!=null){
								var filter = [<#list filter as f>'${f}'<#if f_has_next>,</#if></#list>];
								for(var i=0;i<options.length;i++){
									var item = options[i];
									var inFilter = false;
									for(var j=0;j<filter.length;j++){
										if(item['${keyfield}']==filter[j]){
											inFilter = true;
											break;
										}
									}
									
									if(inFilter==false){
										$("[id='${id}']").append("<option value='"+item['${keyfield}']+"'>"<#if showcode=='true'>+item['${keyfield}']+"-"</#if>+item['${valuefield}']+"</option>");
									}
								}
							}
	                    }
	                    $("[id='${id}']").trigger("change");
	                },'json');
	            </#if>
				
			});
		});
	</script>
	
	<#-- <#if s2=="true">
		<script>
			$("#${id}").select2({
				language: 'zh-CN',
				<#if readonly_boolean!=true>
					placeholder: "",
					allowClear: true,
				</#if>
				width: "100%"
				<#if search=="false">
					,minimumResultsForSearch: "Infinity"
				</#if>
				<#if multiple=="true"||multiple=="multiple">
					,closeOnSelect: false
				</#if>
			});
			$("#${id}").next().addClass("form-control");
		</script>
	</#if> -->
</#macro>

<#-- textarea -->
<#macro textarea id="" name="" value="" readonly="false" valid={} class="" style="" width="" cols="" rows="" label_only="false">
	<#if label_only=="true">
		<span class="label-only ${class}" style="${style};width:${width}"  >${value}</span>
		<#return />
	</#if>
	 <textarea id="${id}" name="${name}" cols="${cols}" rows="${rows}"
	 <#if readonly?string=="readonly"||readonly?string=="true"> readonly="readonly"</#if>
	 <@validator valid=valid />  class="form-control ${class}" style="${style};width:${width}">${value}<#nested/></textarea>
</#macro>


<#-- option select自定义选项-->
<#macro option key="" label="" select_value="" value_split="," is_split="true" showcode="true" disabled="">
	<#if is_split=="false">
		<option value="${key}" select_value="${select_value}" <#if select_value==key >selected="selected"</#if> <#if disabled=="disabled" >disabled="disabled"</#if> >
	<#else>
		<option value="${key}" <#if disabled=="disabled" >disabled="disabled"</#if>
			<#list select_value?split(value_split) as value>
				<#if value==key >selected="selected"</#if> 
			</#list>
		>
	</#if>
		<#if showcode=="true">${key}-</#if>${label}<#nested /></option>
</#macro>
	
<#-- multipleSelect 多选下拉框-->
<#macro multipleSelect id="" name="" options={}  value="" nullable="false"
	value_split="," showfilter="false" single="false" line_options="false" option_width="80" select_all="false" 
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
		
		<@validator valid=valid />  class="form-control ${class}"  style="${style};width:${width}" 
		<#if change?? && change!="">
			onchange="<@js_funcEvent func="${change}"/>"
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
			<#if name?? && name!="" && (valid?size>=1) ><#-- 将变动状态重置 -->
			(function _form_multipleSelect_${id}(){
				var $_form_multipleSelect_${id} =  $("[id='${id}']").closest("form");
				if( $_form_multipleSelect_${id}.length==0){
					return ;
				}else{
					var auto_valid = $_form_multipleSelect_${id}.attr("data-ar-auto_valid"); 
					if(auto_valid=="true" || auto_valid==true){
						$_form_multipleSelect_${id}.data('bootstrapValidator').updateStatus("${name}", 'NOT_VALIDATED');
					}
				}	
			})();
			</#if>
		});
	</script>
</#macro>
