<#-- panel -->
<#macro panel id="" head="" sense="primary" class="" style="" show="true">
    
    <#if show=="false">
        <#return />
    </#if>

    <div id="${id}" class="panel panel-${sense} ${class}" style="${style}">
        <#if head?? && head!="">
            <div class="panel-heading">
                ${head}
            </div>
        </#if>
        <#nested />
    </div>
</#macro>
<#-- panelHead -->
<#macro  panelHead id="" class="" style="" show="true">
    
    <#if show=="false">
        <#return />
    </#if>
    
    <div id="${id}" class="panel-heading  ${class}" style="${style}">
        <#nested />
    </div>
</#macro>

<#-- panelBody -->
<#macro panelBody id="" class="" style="" show="true">
    
    <#if show=="false">
        <#return />
    </#if>
    
    <div id="${id}" class="panel-body ${class}" style="${style}">
        <#nested />
    </div>
</#macro>

<#-- container -->
<#macro container id="" fluid="true" class="" style="" align="center" width="" show="true">
    <#if show=="false">
        <#return />
    </#if>
    <div id="${id}" class="<#if fluid="false">container <#else> container-fluid </#if> ${class}" 
        style="<#if align=="center">margin-right:auto;margin-left:auto;</#if><#if align=="left">margin-right:auto;margin-left:0;</#if><#if align=="right">margin-right:0;margin-left:auto;</#if>
        ${style}
        <#if width??&&width?string!="">;width:${width};</#if>">
        <#nested />
    </div>
</#macro>

<#-- row -->
<#macro row id="" class="" style="" show="true">
	<#if show=="false">
		<#return />
	</#if>
	
	<div id="${id}" class="row ${class}" style="${style}">
		<#nested />
	</div>
</#macro>
<#-- col -->
<#macro col id="" ar="12" class="" style="" show="true">
    <#if show=="false">
        <#return />
    </#if>
    <div id="${id}" class="col-ar-${ar} ${class}" style="${style}">
        <#nested />
    </div>
</#macro>

<#macro hr id="" class="" style="" show="true">
    <#if show=="false">
        <#return />
    </#if>
    
    <hr class="ar-hr ${class}" style="${style}" />
</#macro>

<#-- toolbar -->
<#macro toolbar id="" offset="" class="" style="" show="true" align="left" right_offset="0">
    
    <#if show=="false">
        <#return />
    </#if>
    
    <#if offset?? && offset!="" >
        <#local offset=offset />
    <#else>
        <#if _ftl_form_field_ar?? && _ftl_form_field_ar?string!=""  >
            <#local offset= ((_ftl_form_field_ar?number)/3)?int />
            <#if align="center"> 
                <#local offset= 0 />
            </#if>
        <#else>
            <#local offset=0 />
        </#if>
    </#if>
    
    <div id="${id}" class="toolbar col-ar-offset-${offset} ${class}" style="${style} ;text-align:${align};">
        <#nested />
        <#if align=="right" && right_offset?string!="0" >
            <span class="col-ar-${right_offset}" style="float:right"> </span>
        </#if>
    </div>
    
</#macro>

<#-- fieldset -->
<#macro fieldset id="" legend="" class="" style="" show="true">
	<#if show=="false">
		<#return />
	</#if>
	
	<fieldset id="${id}" class="ar-fieldset ${class}" style="${style}">
		<#if legend?? && legend!="">
			<legend class="text-muted">${legend}</legend>
		</#if>
		<#nested />
	</fieldset>
</#macro>
<#-- legend -->
<#macro legend id="" class="" style="" show="true">
    <#if show=="false">
        <#return />
    </#if>
    <legend id="${id}" class="text-muted ${class}" style="${style}"><#nested /></legend>
</#macro>

<#-- 标签页 -->
<#macro tab id="" class="" style="" show="true">
    <#if show=="false">
        <#return />
    </#if>
    <div id="${id}" class="${class}" style="${style}">
        <#nested />
    </div>
</#macro>

<#macro tabNav id="" class="" style="" >
    <ul id="${id}" class="nav nav-tabs ${class}" style="${style}">
        <#nested/>
    </ul>
</#macro>

<#macro tabTitle id="" active="false" pane_id="" class="" style="" title="" show="true" url="" url_click_fresh="false" iframe="false" iframe_height="">
	    <#if show=="false">
	        <#return />
	    </#if>
	    
	    <#if url?? && url!="" >
	        <#if ! id?? ||id="" >
	            <#local id=ar_('uuid') />   
	        </#if>
	        <script type="text/javascript">
	            <#if active="true" >
	            	<#if iframe=="true">
	            		$(function(){
	            			$('#${pane_id}').empty().append(
	            				$("<iframe>",{src:"${base}/${url}",width:"100%",<#if iframe_height??&&iframe_height?string!=""> height:"${iframe_height}",</#if> frameborder:0})
	            			);
		                });
	            	<#else>
		                $(function(){
		                    $.get("${base}/${url}", function (data) {
		                        $('#${pane_id}').append(data);
		                    });
		                });
	                </#if>
	            </#if>
	            
	            <#if url_click_fresh="true" >
	                $(function(){
	                    $("#${id}").on('click',function(){
	                    	<#if iframe=="true">
		            			$('#${pane_id}').empty().append(
		            				$("<iframe>",{src:"${base}/${url}",width:"100%",<#if iframe_height??&&iframe_height?string!=""> height:"${iframe_height}",</#if> frameborder:0})
		            			);
			            	<#else>
		                        $.get("${base}/${url}", function (data) {
		                            $('#${pane_id}').empty();
		                            $('#${pane_id}').append(data);
		                        });
	                        </#if>
	                    });
	                });
	            <#else>
	            	<#if active!="true" >
		                $(function(){
		                    $("#${id}").one('click',function(){
		                    	<#if iframe=="true">
			            			$('#${pane_id}').empty().append(
			            				$("<iframe>",{src:"${base}/${url}",width:"100%",<#if iframe_height??&&iframe_height?string!=""> height:"${iframe_height}",</#if> frameborder:0})
			            			);
				            	<#else>
			                        $.get("${base}/${url}", function (data) {
			                            $('#${pane_id}').empty();
			                            $('#${pane_id}').append(data);
			                        });
			                    </#if>
		                    });
		                });
	                </#if>
	            </#if>
	        </script>
	    </#if>
	    <li id="${id}" class="${class} <#if active=="true"> active</#if>" style="${style}"><a href="#${pane_id}" data-toggle="tab">
	    ${title}<#nested/></a>
	    </li>
	</#macro>

	<#macro tabContent id="" class="" style="" border="false" padding="5px" height="">
	    <div id="${id}" class="tab-content ${class}" style="padding:${padding};<#if height?? && height?string !="">height:${height};</#if><#if border??&&border="true">border-bottom: 1px solid #ddd;border-left: 1px solid #ddd;border-right: 1px solid #ddd;</#if>${style}" >
	        <#nested/>
	    </div>
	</#macro>

	<#macro tabPane id="" active="false" class="" style="" show="true" height="">
	    <#if show=="false">
	        <#return />
	    </#if>
	    <div id="${id}" class="tab-pane ${class} <#if active=="true"> active</#if>" style="<#if height??&&height?string!=""> height:${height};overflow:auto;</#if>${style}" >
	        <#nested/>
	    </div>
	</#macro>
	