<#include "/layout.ftl">
	<@body>
		<@panel head="审批拒绝月度报表">
			<@panelBody>
				<@form id="queryForm" style="padding-top:10px;">
				    <@row>
				  		<@field label="卡产品代码">
							<@select id="productCd" name="query.productCd" options=ecms_('tableMap','productForStatus','A,B,C')/>
						</@field>
				    </@row>
				    <@row>
						<@field label="所属分支机构">
			                <@multipleSelect id="owningBranch" name="query.owningBranch" options=ecms_('tableMap','branchMap','') />
			            </@field>
						<@field label="月份">
							<@date id="updateDate" name="query.updateDate" datetype="datemonth" />
						</@field>
					</@row>
					<@toolbar>
						<@button id="queryButton" name="查询" fa="search"/>
						<@button  name="导出" click="exportFailureInstallmentData"  fa="file-excel-o"/>
				    </@toolbar>
		 		</@form>
			 <@table id="dataTable" url="applyRejectMonth/getList" form_id="queryForm" button_id="queryButton" load_auto="false">
      <#--       <@th title="机构号" field="org" sortable="true"/> --> 
                <@th title="姓名" field="name" />                
                <@th title="证件类型" field="idType" />                
                <@th title="证件号" field="idNo" />                
                <@th title="产品编号" field="productCd" />                
                <@th title="产品名" field="productDesc" />                
                <@th title="申请编号" field="appNO"/>
                <@th title="分支行号" field="owningBranch"/>
                <@th title="分支行号" field="owningBranch" render="true" >
					<@thGetName options=ecms_('tableMap','branchMap','row.owningBranch') value="{{row.owningBranch}}" showcode="true" />
				</@th>
                <@th title="审批状态" field="rtfState" />
                <@th title="审批状态描述" field="stateDesc" />
                <@th title="审批拒绝日期" field="failedTime" render="true" >
					<@thDate value="{{row.failedTime}}" datetype="date"/>
				</@th>                
            </@table>
			</@panelBody>
		</@panel>
		
		<script type="text/javascript">
			function exportFailureInstallmentData(){
				var productCd = $("#productCd").val();
				var owningBranch = $("#owningBranch").val();
				var updateDate = $("#updateDate").val();
				if(!updateDate){
					alert("请选择日期再导出！");
					return;
				}
				window.location.href="${base}/applyRejectMonth/export?productCd="+
										productCd+"&owningBranch="+owningBranch+"&updateDate="+updateDate;
			}
		</script>
	</@body>