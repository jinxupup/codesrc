<#include "/layout.ftl"/>
<@body>
<@gotop/>

    <@panel head="网点参数新增/编辑" >
        <@panelBody>
            <@form id="form" action="${isEdit?string('acl/branch/edit','acl/branch/add')}" success_url="acl/branch/page" >
                <@row>
                    <@field label="网点机构号">
                        <@input name="branchCode" value="${(tmAclBranch.branchCode)!}" readonly="${isEdit?string}"  />
                    </@field>
                    <@field label="网点机构名称">
                        <@input name="branchName" value="${(tmAclBranch.branchName)!}"  />
                    </@field>
                    <@field label="网点机构级别">
                        <@dictSelect dicttype="branchLevel" name="branchLevel" value="${(tmAclBranch.branchLevel)!}" showcode="false"/>
                    </@field>
                </@row>
                <@row> 
                    <@field label="上级管理机构">
                        <@input name="parentCode" value="${(tmAclBranch.parentCode)!}"  />
                    </@field>
                    <@field label="上级机构路径">
                        <@input name="parentPath" value="${(tmAclBranch.parentPath)!}"  />
                    </@field>
                    <@field label="地址">
                        <@input name="empAdd"  value="${(tmAclBranch.empAdd)!}" />
                    </@field>
                </@row>
                <@row>
                    <@field label="发卡权限标识">
                        <@dictSelect dicttype="Indicator" name="branchIssueInd" value="${(tmAclBranch.branchIssueInd)!'N'}" />
                    </@field>
                    <@field label="领卡权限标识">
                        <@dictSelect dicttype="Indicator" name="cardCollectInd" value="${(tmAclBranch.cardCollectInd)!'N'}"  />
                    </@field>
                    <@field label="是否撤销">
                        <@dictSelect dicttype="Indicator" name="cancelInd" value="${(tmAclBranch.cancelInd)!'N'}" />
                    </@field>
                </@row>
                <@row>
                    <#--@field label="独立发卡标识">
                        <@dictSelect dicttype="Indicator" name="ifEnableHairpin" value="${(tmAclBranch.ifEnableHairpin)!'N'}"  />
                    </@field-->
	                <@field label="所属审批组">
	                    <@dictSelect dicttype="CHECKGROUPS" name="branchSequence" value="${(tmAclBranch.branchSequence)!}" showcode="false" />
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