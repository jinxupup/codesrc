<#macro table id="" click_to_select="true" single_select="false" form_id="" button_id="" 
    pagination="true" page_num="1" page_size="10" url="" load_auto="true" height="" row_style="" condensed="true" bordered="true" striped="true" class="" style="" before="">
	
	<#if ! id?? ||id="" >
		<#local id=ar_('uuid') />	
	</#if>
	
	<table 
		 id="${id}"
		
		 data-toggle="table" 
		 data-query-params-type="unicorn"
		 data-side-pagination="server"
		 data-undefined-text=""
		 
		 data-click-to-select="${click_to_select}"
		 data-single-select="${single_select}"
		 data-pagination="${pagination}" 
		 data-page-size="${page_size}"
         data-striped="${striped}"
         data-page-number="${page_num}"
		 
		 class="<#if condensed=="true">table-condensed </#if> <#if bordered=="false">table-no-bordered </#if> ${class}"
		 style="${style}"
		 
		 <#if height?? && height?string!="">
	       data-height="${height}"
	     </#if>
	     <#if row_style?? && row_style?string!="">
           data-row-style="${row_style}"
         </#if>
	     
		 > 
		  <thead>
		    <tr>
		      <#nested />
		    </tr>
		  </thead>
		  <tbody>
		    
		  </tbody>
	</table>
	
	  <script type="text/javascript">
	  		$('#${id}').bootstrapTable({
				url:ar_.randomUrl("${base}/${url}")
				<#if form_id??&&form_id!="">
			    ,customerQuery:$("#${form_id}").serializeObject()
			    </#if>
			    <#if load_auto??&&load_auto=="false">
			    ,loadAuto:false
			    </#if>
			});
			
			$(function(){
				<#if button_id??&&button_id!="">
				$("#${button_id}").on('click',function(){
					<#if before?? && before!="">
						var before_result = (<@js_funcEvent func="${before}"/>); 
						if(before_result==false){
							$("#${id}").bootstrapTable("removeAll");
							return false;
						}
					</#if>
					var query = {};
					<#if form_id??&&form_id!="">
					query = $("#${form_id}").serializeObject();
					</#if>
					var params = {url:ar_.randomUrl("${base}/${url}")};
					$("#${id}").bootstrapTable("getOptions").customerQuery = query;
					$("#${id}").bootstrapTable("refresh",params);
				});
				</#if>
			});
	  </script>
</#macro>

<#macro th checkbox="false" field="" title="" width="" sortable="false"  formatter=""  cell_style="" render="false" >
    <th 
        <#if checkbox?? && checkbox?string!="">
            data-checkbox="${checkbox}" 
        </#if>
        <#if field?? && field?string!="">
            data-field="${field}" 
        </#if>
        <#if title?? && title?string!="">
            data-title="<div <#if width?? && width?string!="">style='width:${width}' </#if> >${title}</div>"
        </#if>
        <#if width?? && width?string!="">
            data-width="${width}" 
        </#if>
        <#if sortable?? && sortable?string!="">
            data-sortable="${sortable}" 
        </#if>
        <#if formatter?? && formatter?string!="">
            data-formatter="${formatter}" 
        </#if>
        
        <#if cell_style?? && cell_style?string!="">
            data-cell-style="${cell_style}"
        </#if>
    
        <#if render="true">
            <#local uuid=ar_('uuid') />
            <#local uuid_formatter=uuid+"_formatter" />
            <#local uuid_tpl=uuid+"_tpl" />
            <#local uuid_tpl_engine=uuid+"_tpl_engine" />
            <#local uuid_tpl_render=uuid+"_tpl_render" />
            data-formatter="${uuid_formatter}"
        </#if>
    >
        
        <#if render="true">
            <#-- 内部渲染方法，变量 -->
                <div id="${uuid}" style="display:none;">
                    <#nested />
                </div>
                <script type="text/javascript">
                    var ${uuid_tpl_engine} = template;
                    $("[data-ar-th-inner-function='true']",$("#${uuid}")).each(function(){
                        var functionName = $(this).attr('data-ar-th-inner-function-name');
                        var func = $(this).html();
                        ${uuid_tpl_engine}.helper(functionName, eval("(false||"+func+")") );
                        $(this).remove();
                    });
                    
                    var ${uuid_tpl} = $("#${uuid}").html();
                    var ${uuid_tpl_render} = ${uuid_tpl_engine}.compile(${uuid_tpl});
                    
                   var ${uuid_formatter} = function(value,row,index){
                        var data = {value:value,row:row,index:index};
                        var render = ${uuid_tpl_render}(data);
                        return render;
                    }
                </script>
        </#if> <#-- render="true" end-->
        
        <#-- render="false" 可自行编写内部数据-->
        <#if render!="true">
            <#nested />
        </#if>
    </th>
</#macro>

<#-- 在table中显示时间日期,放在 <@th>中使用 -->
<#-- thDate render="true" 调用此方法有效 -->
<#macro thDate datetype="date" value="''" >
    <#local uuid_function=ar_('uuid') />
    
    <#-- artTemplate模版 -->
        {{ ${value?replace("{{","")?replace("}}","")} | ${uuid_function} }}
        
    <#-- js方法 -->
        <div id="${uuid_function}" data-ar-th-inner-function="true" data-ar-th-inner-function-name="${uuid_function}">
            function(val){
            
            	<#-- 增加对null值的判断 
            	if(val == null) {
					val = '-';
					return val;
				}
				-->
				
                <#if  value?? && value?string=="">
                    return "";
                </#if>
                var dest = val;
                
                <#if datetype=='datetime'>
                dest = ar_.datetimeFormat(dest);
                <#elseif datetype=='time'>
                dest = ar_.timeFormat(dest);
                <#else>
                dest = ar_.dateFormat(dest);
                </#if>
                return dest;
            }
        </div>
</#macro>

<#-- 原生表格 -->
<#macro pureTable id="" striped="true" bordered="true" hover="true" condensed="true" class="" style="">
	<div class="table-responsive">
	  <table id="${id}"
	  	class="table 
		  		<#if striped="true"> table-striped</#if> <#if bordered="true"> table-bordered</#if>
		  		<#if hover="true"> table-hover</#if> <#if condensed=="true"> table-condensed</#if>
	  		     ${class}
	  		  "
	    style="${style}"
	  >
			<#nested/>
	  </table>
	</div>
</#macro>
