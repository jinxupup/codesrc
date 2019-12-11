<#include "/layout.ftl"/>
<@body>
<@gotop/>

    <@panel head="网点参数新增/编辑" >
        <@panelBody>
            <@form id="form" action="${isEdit?string('branchParam/edit','branchParam/add')}" success_url="branchParam/page" > 
                <@row>
                    <@field label="分行号">
                        <@input name="branchCode" value="${(tmAclBranch.branchCode)!}" readonly="${isEdit?string}"  />
                    </@field>
                    <@field label="分行名称">
                        <@input name="branchName" value="${(tmAclBranch.branchName)!}"  />
                    </@field>
                    <@field label="分行级别">
                        <@input name="branchLevel" value="${(tmAclBranch.branchLevel)!}"   />
                    </@field>
                    
                </@row>
                <@row> 
                    <@field label="分行所在城市">
                        <@input name="city" value="${(tmAclBranch.city)!}"  />
                    </@field>
                    <@field label="区">
                        <@input name="zone" value="${(tmAclBranch.zone)!}" />
                    </@field>
                    <@field label="地址">
                        <@input name="empAdd"  value="${(tmAclBranch.empAdd)!}" />
                    </@field>
                </@row>
                <@row> 
                    <@field label="邮编">
                        <@input name="zipCode" value="${(tmAclBranch.zipCode)!}" valid={"regexp or pattern":"^([1-9]\\d{5})$","regexp-message":"请输入正确的邮编"} />
                    </@field>
                    <@field label="分行所在国家代码">
                        <@input name="countryCd" value="${(tmAclBranch.countryCd)!}" />
                    </@field>
                    <@field label="分行联系电话1">
                        <@input name="phone"  value="${(tmAclBranch.phone)!}" valid={"regexp or pattern":"^((0\\d{2,3}\\-\\d{6,8}(\\-\\d{1,6})?)|(1[3456789]\\d{9}))$","regexp-message":"请输入有效的电话号码"} />
                    </@field>
                </@row>
                <@row> 
                    <@field label="分行联系电话2">
                        <@input name="phone2"  value="${(tmAclBranch.phone2)!}" valid={"regexp or pattern":"^((0\\d{2,3}\\-\\d{6,8}(\\-\\d{1,6})?)|(1[3456789]\\d{9}))$","regexp-message":"请输入有效的电话号码"} />
                    </@field>
                    <@field label="上级管理分行">
                        <@input name="parentCode" value="${(tmAclBranch.parentCode)!}"  />
                    </@field>
                    <@field label="发卡权限标识">
                        <@select name="branchIssueInd" options={"Y":"是","N":"否"} value="${(tmAclBranch.branchIssueInd)!}" />
                    </@field>
                </@row>
                <@row> 
                    <@field label="领卡分行标识">
                        <@select name="cardCollectInd" options={"Y":"是","N":"否"} value="${(tmAclBranch.cardCollectInd)!}"  />
                    </@field>
                    <@field label="jpaVersion" hidden="true">
						<@input name="jpaVersion" value="${(tmAclBranch.jpaVersion)!}"/>
					</@field>
                </@row>
                <@row>
                    <@toolbar>
                        <@submitButton /> 
                        <@backButton />
                    </@toolbar>
                </@row>
            </@form>
        </@panelBody>
    </@panel>
</@body>