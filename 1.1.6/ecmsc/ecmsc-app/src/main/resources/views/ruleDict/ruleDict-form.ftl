<#include "/layout.ftl">
<@body>
    <@panel head="业务字典新增" >
        <@panelBody>
            <@form id="form" action="${isEdit?string('ruleParam/edit','ruleParam/add')}" success_url="ruleParam/page" > 
                <@field hidden="true">
                    <@input name="id" value="${(dict.id)!}"/>
                </@field>
                <@row>
                    <@field label="类型">
                        <@input name="type" value="${(dict.type)!}" readonly="${isEdit?string}" valid={"notempty":"true","stringlength":"true","stringlength-max":"50"} />
                    </@field>
                    <@field label="类型名称">
                        <@input name="typeName" value="${(dict.typeName)!}" valid={"notempty":"true","stringlength":"true","stringlength-max":"50"}  />
                    </@field>
                </@row>
                <@row> 
                    <@field label="代码">
                        <@input name="code"  value="${(dict.code)!}" readonly="${isEdit?string}" valid={"notempty":"true","stringlength":"true","stringlength-max":"100"} />
                    </@field>
                    <@field label="代码名称">
                        <@input name="codeName" value="${(dict.codeName)!}" valid={"notempty":"true","stringlength":"true","stringlength-max":"100"} />
                    </@field>
                </@row>
                
                <@row> 
                    <@field label="值">
                        <@input name="value"  valid={"stringlength":"true","stringlength-max":"100"} />
                    </@field>
                    <@field label="值2">
                        <@input name="value2" valid={"stringlength":"true","stringlength-max":"100"}  />
                    </@field>
                </@row>
                <@row> 
                    <@field label="值3">
                        <@input name="value3"  valid={"stringlength":"true","stringlength-max":"100"} />
                    </@field>
                    <@field label="值4">
                        <@input name="value4" valid={"stringlength":"true","stringlength-max":"100"}  />
                    </@field>
                </@row>
                
                <@row> 
                    <@field label="排序">
                        <@input name="sort" valid={"integer":"true"}  />
                    </@field>
                    <@field label="说明">
                        <@input name="remark" valid={"stringlength":"true","stringlength-max":"100"} />
                    </@field>
                </@row>
                <@row> 
                    <@field label="能否删除">
                        <@select name="ifCanDel" options={"N":"否","Y":"是"}  nullable="false" />
                    </@field>
                    <@field label="是否启用">
                        <@select name="ifUsed" options={"Y":"是","N":"否"} nullable="false" />
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