<#include "/layout.ftl">
<@body>
<@panel head="决策方案新增" >
<@panelBody>
        <@form id="form" action="dmp/strategy/add"  multi_submit="false" cols="2" success_url="dmp/strategy/editpage?stId={{res.obj.stId}}">
            <@row>
                <@field label="决策类别">
                	<@hidden id="stClass" name="stClass" value="${stClass}" />
                    <@input id="stClass" name="stClass" value="${stClass}" label_only="true" />
                </@field>
            </@row>  
            <@row>
                <@field label="类名称">
                	<@select options=dict_("tableMap","com.jjb.dmp.infrastructure.TmDmpStrategyCategory",{},"stClass","name") showcode="false" value="${stClass}" label_only="true" />
                </@field>
            </@row>
            <@row>
                <@field label="决策方案代码">
                    <@input id="stId" name="stId"  valid={"notempty":"true","notempy-message":"不能为空","stringlength":"true","stringlength-max":"40","regexp":"true","regexp-regexp":"^[0-9a-zA-Z_]+$","regexp-message":"不能输入空格与特殊字符"} />
                </@field>
            </@row> 
            <@row>
                <@field label="决策方案名称">
                    <@input id="name" name="stName"  valid={"notempty":"true","notempy-message":"不能为空","stringlength":"true","stringlength-max":"40","regexp":"true","regexp-regexp":"^[0-9a-zA-Z\\u4E00-\\u9FA5\\uF900-\\uFA2D_$]+$","regexp-message":"不能输入空格与特殊字符"} />
                </@field>
            </@row>              
            <@row>
                <@field label="说明">
                    <@textarea name="remark" rows="5" valid={"stringlength":"true","stringlength-max":"400"} />
                </@field>
            </@row>   
            <@row>
                <@field label="是否启用">
                    <@select options={"Y":"是","N":"否"} name="ifUsed"  nullable="false" />
                </@field>
            </@row>             
            <@row>
                <@toolbar>
                    <@submitButton id="addBtn"/> 
                    <@href href="dmp/strategy/page" name="返回" />
                </@toolbar>
            </@row>
        </@form> 
    </@panelBody>
</@panel>
 
 </@body>   