<#include "/layout.ftl"/>
<@body>
<@gotop/>

    <@panel head="参数新增/编辑" >
        <@panelBody>
            <@form id="form" action="${isEdit?string('sysParam/edit','sysParam/add')}" success_url="sysParam/page" > 
                <@field hidden="true">
                    <@input name="id" value="${(param.id)!}"/>
					<@input name="jpaVersion" value="${(param.jpaVersion)!}"/>
                    
                </@field>
                <@row>
                    <@field label="参数类型-dicType">
                        <@input name="dicType" value="${(param.dicType)!}" valid={"notempty":"true","stringlength":"true","stringlength-max":"50"} />
                    </@field>
                    <@field label="参数-typeName">
                        <@input name="typeName" value="${(param.typeName)!}" valid={"stringlength":"true","stringlength-max":"50"}  />
                    </@field>
                </@row>
                <@row> 
                    <@field label="参数-itemName">
                        <@input name="itemName"  value="${(param.itemName)!}" valid={"stringlength":"true","stringlength-max":"100"} />
                    </@field>
                    <@field label="参数-tabName">
                        <@input name="tabName" value="${(param.tabName)!}" valid={"stringlength":"true","stringlength-max":"100"} />
                    </@field>
                </@row>
                <@row> 
                    <@field label="参数-formName">
                        <@input name="formName"  value="${(param.formName)!}" valid={"stringlength":"true","stringlength-max":"100"} />
                    </@field>
                    <@field label="参数-remark">
                        <@input name="remark" value="${(param.remark)!}" valid={"stringlength":"true","stringlength-max":"100"} />
                    </@field>
				</@row>
                <@row> 
                    <@field label="参数-ifUsed">
                    	<@input name="ifUsed" value="${(param.ifUsed)!}" valid={"stringlength":"true","stringlength-max":"20"} />
                    </@field>
                    <@field label="能否删除">
                        <@select name="ifCanDel" options={"N":"否","Y":"是"}  nullable="false" />
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