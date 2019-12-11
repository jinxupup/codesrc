<#include "/layout.ftl">
<@body>
    <@panel head="决策类别新增" >
        <@panelBody>
            <@form id="form" action="dmp/strategyCategory/add"   success_url="dmp/strategyCategory/editpage?stClass={{res.obj.stClass}}" cols="2" >
                <@row>
                    <@field label="决策类别">
                        <@input id="stClass" name="stClass"  valid={"regexp":"true","regexp-regexp":"^[a-zA-Z0-9$_]+$","regexp-message":"请输入字母、数字","notempty":"true","notempy-message":"不能为空","stringlength":"true","stringlength-max":"40"} />
                    </@field>
                </@row>
                <@row>
	                <@field label="名称">
	                    <@input id="name" name="name"  valid={"notempty":"true","notempy-message":"不能为空","stringlength":"true","stringlength-max":"40"} />
	                </@field>
	            </@row>                
                <@row>
                    <@field label="说明">
                        <@textarea name="remark" rows="5" valid={"stringlength":"true","stringlength-max":"400"} />
                    </@field>
                </@row>                
                <@row>
                    <@toolbar>
                        <@submitButton id="addBtn"/> 
                        <@href href="dmp/strategyCategory/page" name="返回" />
                    </@toolbar>
                </@row>
            </@form> 
                       
        </@panelBody>
    </@panel>
 
 </@body>   