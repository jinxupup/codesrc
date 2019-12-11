<#include "/layout.ftl"/>
<@body>
<@gotop/>

    <@panel head="审批额度分配">
        <@panelBody>
            <@form id="queryForm" cols="4">
                <@row>
					<@field label="操作员ID">
						<@multipleSelect name="operatorId" value="${(tmAclUser.userName)!}"
							options=ecms_('tableMap','getAllUser','') nullable="true" showfilter="true" showcode="true" single="true"/>
					</@field>
            		<@field label="审批节点">
               		 	<@dictSelect name="taskName" dicttype="TaskName2" showcode="false" />
            		</@field>
					<@href href="auditQuota/auditQuotaEditPage" name="新增" />&nbsp;&nbsp;&nbsp;
                	<@button id="queryButton" name="查询" />&nbsp;&nbsp;&nbsp;
                	<#--<@button id="historyButton" name="历史修改信息" click="applyHistoryUpdateBtn" />-->
				</@row>
            </@form>
            </br>
            <@table id="dataTable" url="auditQuota/auditQuotaList" form_id="queryForm" button_id="queryButton" page_size="10" condensed="true">
                <@th title="操作员ID" field="operatorId" sortable="true"/>
                <@th title="审批节点" field="taskName" render="true" sortable="true">
         			<@thDictName  dicttype="TaskName2" value="{{row.taskName}}" />
        		</@th>
                <@th title="可见额度最低值" field="visibleMinimum" sortable="true"/>
                <@th title="可见额度最高值" field="visibleMaximum" sortable="true"/>
                <@th title="审批额度最高值" field="approvalMaximum" sortable="true"/>
                <@th title="修改人" field="updateUser" sortable="true"/>
                <@th title="修改日期" render="true" sortable="true">
                	 <@thDate value="{{row.updateDate}}"/>
                </@th>
                <@th title="操作" render="true" >
                	<@href href="auditQuota/auditQuotaEditPage?id={{row.id}}" name="修改" />&nbsp;&nbsp;&nbsp;
                   	<@ajaxButton  confirm="确定删除此记录？" sense="danger" name="删除" url="auditQuota/auditQuotaDelete?id={{row.id}}" success_url="auditQuota/auditQuotaPage" />
                </@th>
            </@table>
        </@panelBody>
    </@panel>
    <script>
		function dialogInfo(title,width,height,url){
			a = top.dialog({
				title: title,
				width: width,
				height: height,
				url: url,
				oniframeload:function(){},
				button:
				[
				    {
				        value: '确定',
				        callback: function () {
				       		this.close();
				            return false;
				        },
				        autofocus: true
				    },
				    {
				        value: '返回',
				        callback: function () {
				        	this.close();
				            return false;
				        },
				        autofocus: true
				    }
				]
			});
			a.showModal();
		}
    	<#--历史修改记录-->
		var applyHistoryUpdateBtn = function(){
			var url = 'tmSystemAudit/tmSystemAuditPage';
        	dialogInfo('历史修改记录',1000,500,url);
    	}
    </script>

</@body>
