<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panel head="审批人员额度分配">
		<@panelBody>
			<@form id="auditQuotaForm" cols="3" action="auditQuota/auditQuotaEdit" success_url="auditQuota/auditQuotaPage">
				<@field hidden="true">
					<@input id="id" name="id" value="${(tmAppAuditQuota.id)!}" />
				</@field>
				<@row>
					<@field label="操作员ID">
						<#if tmAppAuditQuota?? && tmAppAuditQuota.id??>
							<@input name="operatorId" value="${(tmAppAuditQuota.operatorId)!}" readonly="true"/>
						<#else>
							<@multipleSelect name="operatorId" value="${(tmAclUser.userName)!}"
							options=ecms_('tableMap','getAllUser','') showfilter="true" showcode="true" single="true"/>
						</#if>
					</@field>
					<@field label="审批节点">
						<#if tmAppAuditQuota?? && tmAppAuditQuota.id??>
							<@dictSelect name="taskName" dicttype="TaskName2" showcode="false" value="${(tmAppAuditQuota.taskName)!}" readonly="true"/>
						<#else>
							<@dictSelect name="taskName" dicttype="TaskName2" showcode="false" />
						</#if>
            		</@field>
					<@field label="可见额度最低值">
						<@input name="visibleMinimum" value="${(tmAppAuditQuota.visibleMinimum)!}" />
					</@field>
				</@row>
				<@row>
					<@field label="可见额度最高值">
						<@input name="visibleMaximum" value="${(tmAppAuditQuota.visibleMaximum)!}" />
					</@field>
					<@field label="审批额度最高值">
						<@input name="approvalMaximum" value="${(tmAppAuditQuota.approvalMaximum)!}" />
					</@field>
					<@field label="jpaVersion" hidden="true">
						<@input name="jpaVersion" value="${(tmAppAuditQuota.jpaVersion)!}"/>
					</@field>
				</@row>
				<@toolbar style="text-align: left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		            <@submitButton />
		            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<@backButton />
				</@toolbar>
			</@form>
		</@panelBody>
	</@panel>
</@body>