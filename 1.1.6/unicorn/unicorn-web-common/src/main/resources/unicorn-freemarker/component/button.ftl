<#-- href 超链接-->
<#macro href id="" name="" href="" value="" sense="primary" fa="" class="" style="" target="">
    <a href="${base}/${href}" id="${id}" name="${name}" value="${value}"
        class="btn btn-sm btn-${sense} ${class}" style="${style}" target="${target}"> 
        <#if fa??&&fa!=""><i class="fa fa-${fa}"></i> </#if><#nested/>${name}</a>
</#macro>
<#-- button 普通按钮-->
<#macro button id="" name="" value="" click="" sense="primary" fa="" class="" style="" >
    <button type="button" id="${id}" name="${name}" value="${value}" 
    <#if click??&&click!=""> onclick="<@js_funcEvent func="${click}"/>" </#if>
    class="btn btn-sm btn-${sense} ${class}" style="${style}" > 
    <#if fa??&&fa!=""><i class="fa fa-${fa}"></i> </#if><#nested/>${name}</button>
</#macro>
<#-- submitButton 提交按钮-->
<#macro submitButton id="" name="提交" value="" click="" sense="primary" fa="" class="" style="">
    <button type="submit" id="${id}" name="${name}" value="${value}" 
    <#if click??&&click!=""> onclick="<@js_funcEvent func="${click}"/>" </#if>
    class="btn btn-sm btn-${sense} ${class}" style="${style}" >
    <#if fa??&&fa!=""><i class="fa fa-${fa}"></i> </#if><#nested/>${name}</button>
</#macro>
<#-- resetButton 重置按钮-->
<#macro resetButton id="" name="重置" value="" click="" sense="primary" fa="" class="" style="">
    <button type="reset" id="${id}" name="${name}" value="${value}"
    <#if click??&&click!=""> onclick="<@js_funcEvent func="${click}"/>" </#if>
     class="btn btn-sm btn-${sense} ${class}" style="${style}" > 
     <#if fa??&&fa!=""><i class="fa fa-${fa}"></i> </#if><#nested/>${name}</button>
</#macro>
<#-- backButton 返回按钮-->
<#macro backButton id="" name="返回" value="" sense="primary" fa="" class="" style="">
    <button type="button" id="${id}" name="${name}" value="${value}" onclick="ar_.back()" class="btn btn-sm btn-${sense} ${class}" style="${style}"> 
    <#if fa??&&fa!=""><i class="fa fa-${fa}"></i> </#if><#nested/>${name}</button>
</#macro>
<#-- gobackButton 返回按钮
<#macro gobackButton id="" name="" value="" type="primary" class="" style="">
    <button type="button" id="${id}" name="${name}" value="${value}" onclick="ar_.goback()" class="btn btn-sm btn-${type} ${class}" style="${style}"> <#nested/></button>
</#macro>
-->

<#-- ajaxButton ajax按钮-->
<#macro ajaxButton id="" name="" value="" sense="primary" fa="" class="" style="" 
    url="" url_data={} form_id="" multi_submit="false" after="" before="" confirm="" success_url="" >
    
    <#local uuid_func=ar_('uuid') />
    
    <script type="text/javascript">
       function ${uuid_func}(that){
            var $this = $(that);
            
            <#if confirm?? && confirm!="">
                var confirmMsg = $this.attr("data-ar-confirm");
                if(!confirm(confirmMsg)){
                    return false;
                }
            </#if>
            
            <#if before?? && before!="">
                <#-- var before = $this.attr("data-ar-before"); -->
                var before_result = (<@js_funcEvent func="${before}"/>);
                if(before_result==false){
                    return false;
                }
            </#if>
            
            <#if url?? && url!="">
                var url = $this.attr("data-ar-url");
                var data = {};
                <#if url_data?? && url_data?size gt 0 >
                    var url_data = $this.attr("data-ar-url_data");
                    u_data = eval('(' + url_data + ')');
                    data = $.extend({},data,u_data);
                </#if>
                
                <#if form_id?? && form_id!="" >
                    var form_data = $("#${form_id}").serializeObject();
                    data = $.extend({},data,form_data);
                </#if>
                /*置灰当前按钮*/
                ar_.buttonDisable('${id}',true);
                
                $.post(ar_.randomUrl(url), data,
                  function(res){
                  
                    <#if after?? && after!="">
                        <#-- var after = $this.attr("data-ar-after"); -->
                        var after_res = (<@js_funcEvent func="${after}" params="res"/>);
                        if(after_res==false){
                            return false;
                        }
                    <#else>
                        alert(res.msg);
                        if(res.s){
                            <#if multi_submit=="true">
                                ar_.buttonDisable('${id}',false);
                            <#else>
                                ar_.buttonDisable('${id}',true);
                            </#if>
                        }else{
                            /*操作失败，默认恢复提交按钮*/
                            ar_.buttonDisable('${id}',false);
                            return false;
                        }
                    </#if>
                    
                    <#if success_url?? && success_url!="">
                        if(res.s){
                            var success_url = $this.attr("data-ar-success_url");
                            var eval_success_url = "success_url = '" + success_url;
                            eval(eval_success_url);                       
    
                            window.location.href="";
                            window.location.href=success_url;
                        }
                    </#if>
                    
                },'json');
            <#else>
                <#if success_url?? && success_url!="">
                    var success_url = $this.attr("data-ar-success_url");
                    var eval_success_url = "success_url = '" + success_url;
                    eval(eval_success_url);
                        
                    window.location.href="";
                    window.location.href=success_url;
                </#if>
            </#if>
        }
    </script>
    
    <button type="button" id="${id}" name="${name}" value="${value}" onclick="${uuid_func}(this)"
    data-ar-url="${base}/${url}" data-ar-url_data="<@mapToMapStr map=url_data />"
    data-ar-after="${after}" data-ar-before="${before}" data-ar-confirm="${confirm}"
    <#if success_url?? && success_url!="">data-ar-success_url="${base}/${ar_('success_url_replace',success_url)}"</#if>
    class="btn btn-sm btn-${sense} ${class}" style="${style}" > 
    <#if fa??&&fa!=""><i class="fa fa-${fa}"></i> </#if><#nested/>${name}</button>
</#macro>
