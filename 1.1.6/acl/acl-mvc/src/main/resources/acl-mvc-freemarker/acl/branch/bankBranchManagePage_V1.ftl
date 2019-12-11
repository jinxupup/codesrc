<#include "/layout.ftl">
<#--网点参数管理页面-->
<@body>
    <@panel head="网点列表">
        <@panelBody>
            <@gotop/>
            <@form id="queryForm" cols="3" style="padding-top:10px;">
                <@row>
                <#--<@field label="分支行编号">-->
                <#--<@input name="branchId" />-->
                <#--</@field>-->

                    <#--<@field label="所属网点机构" >-->
                        <#--<@multipleSelect name="branchId" value="${(tmAppMain.owningBranch)!}"-->
                        <#--options=ecms_('tableMap','branchMap','allBranch') nullable="true" showfilter="true" single="true"/>-->
                    <#--</@field>-->

                    <@field label="分支行名称" label_ar="8">
                        <@multipleSelect id="owningBranch" name="branchId" options=ecms_('tableMap','branchMap','allBranch')  nullable="true" showfilter="true" single="true"/>
                    </@field>
                <#--<@field label="分支行名称">-->
                <#--<@input name="name" />-->
                <#--</@field>-->

                    <@field label="分支行等级">
                        <@dictSelect dicttype="branchLevel" name="branchLevel" showcode="false"/>
                    </@field>

                    <@field label="" label_ar="0">
                        <@toolbar align="right">
                            <@button id="queryButton" name="查询" style="margin-right: 3%;"/>
                            <@href href="acl/branch/addpage" name="新增" style="margin-right: 3%;"/>
                            <@href href="acl/branch/fileUpload" name="批量导入" />
                            <@buttonAuth code="SYNC_BMP_BRANCHES">
                                <@button name="同步核心机构网点" click="syncBmpBranches()"/>
                            </@buttonAuth>
                        </@toolbar>
                    </@field>

				</@row>
			</@form>


			<@table id="dataTable" url="acl/branch/list" form_id="queryForm" page_size="10" button_id="queryButton" condensed="true">
				<@th checkbox="true"/>
				<@th title="分行号" field="branchCode"sortable="true"/>
				<@th title="分行名称" field="branchName" />
				<@th title="地址" field="empAdd" />
				<#--<@th title="所在城市" field="city" />-->
				<@th title="上级管理分行" field="parentCode" />

			<#--<@th title="发卡权限标识" field="branchIssueInd" sortable="true"/>-->
				<@th title="发卡权限标识" field="branchIssueInd" render="true" sortable="true">
					<@thDictName dicttype="Indicator"  value="{{row.branchIssueInd}}" showcode="true" />
				</@th>

			<#--<@th title="领卡权限标识" field="cardCollectInd" sortable="true"/>-->
				<@th title="领卡权限标识" field="cardCollectInd" render="true" sortable="true">
					<@thDictName dicttype="Indicator"  value="{{row.cardCollectInd}}" showcode="true" />
				</@th>

				<@th title="操作" render="true">
					<@href href="acl/branch/editpage?branchCode={{row.branchCode}}" name="编辑" />
					<@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="acl/branch/delete?branchCode={{row.branchCode}}" success_url="acl/branch/page" />
				</@th>
			</@table>
		</@panelBody>

	</@panel>
<script type="text/javascript">
	<#--确认同步核心产品参数 -->
    function syncBmpBranches(){
        if(confirm("是否确认同步核心网点机构参数？如果同步则清空当前系统网点参数")){
            window.location.href="${base}/branchParam/syncBmpBranches";
        }
    }
</script>
</@body>
