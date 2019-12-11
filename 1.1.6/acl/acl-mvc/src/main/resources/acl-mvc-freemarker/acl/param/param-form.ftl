<#include "/layout.ftl">
<@body>
    <@panel head="参数新增" >
        <@panelBody>
            <@form id="form" action="${isEdit?string('acl/param/edit','acl/param/add')}" success_url="acl/param/page" > 
                <@field hidden="true">
                    <@input name="id" value="${(param.id)!}"/>
                </@field>
                <@row>
                    <@field label="参数类型">
                        <@input name="type" value="${(param.type)!}" readonly="${isEdit?string}" valid={"notempty":"true","stringlength":"true","stringlength-max":"100"} />
                    </@field>
                    <@field label="参数名称">
                        <@input name="typeName" value="${(param.typeName)!}" valid={"notempty":"true","stringlength":"true","stringlength-max":"100"}  />
                    </@field>
                </@row>
                <@row> 
                    <@field label="参数代码">
                        <@input name="code"  value="${(param.code)!}" readonly="${isEdit?string}" valid={"notempty":"true","stringlength":"true","stringlength-max":"200"} />
                    </@field>
                    <@field label="参数代码名称">
                        <@input name="codeName" value="${(param.codeName)!}" valid={"notempty":"true","stringlength":"true","stringlength-max":"200"} />
                    </@field>
                </@row>
                
                <@row> 
                    <@field label="值">
                        <@input name="value"  value="${(param.value)!}" valid={"stringlength":"true","stringlength-max":"200"} />
                    </@field>
                    <@field label="值2">
                        <@input name="value2" value="${(param.value2)!}" valid={"stringlength":"true","stringlength-max":"200"}  />
                    </@field>
                </@row>
                <@row> 
                    <@field label="值3">
                        <@input name="value3"  value="${(param.value3)!}" valid={"stringlength":"true","stringlength-max":"200"} />
                    </@field>
                    <@field label="值4">
                        <@input name="value4" value="${(param.value4)!}" valid={"stringlength":"true","stringlength-max":"200"}  />
                    </@field>
                </@row>
                
                <@row> 
                    <@field label="排序">
                        <@input name="sort" value="${(param.sort)!}" valid={"integer":"true"}  />
                    </@field>
                    <@field label="说明">
                        <@input name="remark" value="${(param.remark)!}" valid={"stringlength":"true","stringlength-max":"200"} />
                    </@field>
                </@row>
                <@row> 
                    <@field label="能否删除">
                        <@select name="ifCanDel" value="${(param.ifCanDel)!}" options={"N":"否","Y":"是"}  nullable="false" />
                    </@field>
                    <@field label="是否启用">
                        <@select name="ifUsed" value="${(param.ifUsed)!}" options={"Y":"是","N":"否"} nullable="false" />
                    </@field>
                </@row>
                <@row>
                    <@toolbar>
                        <@submitButton /> <@backButton />
                    </@toolbar>
                </@row>
            </@form>
        </@panelBody>
    </@panel>
</@body>