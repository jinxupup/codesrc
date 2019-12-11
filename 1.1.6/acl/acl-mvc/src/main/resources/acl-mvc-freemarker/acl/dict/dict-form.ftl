<#include "/layout.ftl">
<@body>
    <@panel head="业务字典新增" >
        <@panelBody>
            <@form id="form" action="${isEdit?string('acl/dict/edit','acl/dict/add')}" success_url="acl/dict/page" > 
                <@field hidden="true">
                    <@input name="id" value="${(dict.id)!}"/>
                </@field>
                <@row>
                    <@field label="字典类型">
                        <@input name="type" value="${(dict.type)!}" readonly="${isEdit?string}" valid={"notempty":"true","stringlength":"true","stringlength-max":"100"} />
                    </@field>
                    <@field label="类型名称">
                        <@input name="typeName" value="${(dict.typeName)!}" valid={"notempty":"true","stringlength":"true","stringlength-max":"100"}  />
                    </@field>
                </@row>
                <@row> 
                    <@field label="字典代码">
                        <@input name="code"  value="${(dict.code)!}" readonly="${isEdit?string}" valid={"notempty":"true","stringlength":"true","stringlength-max":"200"} />
                    </@field>
                    <@field label="代码名称">
                        <@input name="codeName" value="${(dict.codeName)!}" valid={"notempty":"true","stringlength":"true","stringlength-max":"200"} />
                    </@field>
                </@row>
                
                <@row> 
                    <@field label="值">
                        <@input name="value"  value="${(dict.value)!}" valid={"stringlength":"true","stringlength-max":"200"} />
                    </@field>
                    <@field label="值2">
                        <@input name="value2" value="${(dict.value2)!}" valid={"stringlength":"true","stringlength-max":"200"}  />
                    </@field>
                </@row>
                <@row> 
                    <@field label="值3">
                        <@input name="value3"  value="${(dict.value3)!}" valid={"stringlength":"true","stringlength-max":"200"} />
                    </@field>
                    <@field label="值4">
                        <@input name="value4" value="${(dict.value4)!}" valid={"stringlength":"true","stringlength-max":"200"}  />
                    </@field>
                </@row>
                
                <@row> 
                    <@field label="排序">
                        <@input name="sort" value="${(dict.sort)!}" valid={"integer":"true"}  />
                    </@field>
                    <@field label="说明">
                        <@input name="remark" value="${(dict.remark)!}" valid={"stringlength":"true","stringlength-max":"200"} />
                    </@field>
                </@row>
                <@row> 
                    <@field label="能否删除">
                        <@select name="ifCanDel" value="${(dict.ifCanDel)!}" options={"N":"否","Y":"是"}  nullable="false" />
                    </@field>
                    <@field label="是否启用">
                        <@select name="ifUsed" value="${(dict.ifUsed)!}" options={"Y":"是","N":"否"} nullable="false" />
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