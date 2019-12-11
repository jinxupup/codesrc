<#include "/layout.ftl"/>
<@body>
    <@gotop/>

    <@panelBody>
        <@form id="queryForm" cols="4">
            <@field label="当前操作员" hidden="true">
                <@input id="operator" name="operator" value="${(operator)!}" />
            </@field>
            <@row>
                <@field label="任务名" label_ar="12">
                    <@dictSelect id="taskName" dicttype="CasTaskName" showcode="false" name="query.taskName" />
                </@field>
                <@field label="申请件编号" label_ar="8">
                    <@input name="query.appNo" />
                </@field>
                
                <@field label="姓名" label_ar="8">
                    <@input name="query.name" />
                </@field>
                <@field label="证件号码" label_ar="8">
                    <@input name="query.idNo" />
                </@field>
            </@row>
            
            <@row>
            	<@field label="推广人工号/姓名" label_ar="12">
					<@input id="spreaderInfo" name="query.spreaderInfo" />
				</@field>
				<@field label="移动电话" label_ar="8">
					<@input id="cellPhone" name="query.cellPhone" />
				</@field>
				<@field label="单位名" label_ar="8">
					<@input id="corpName" name="query.corpName" />
				</@field>
				<@field label="申请渠道" label_ar="8">
					<@dictSelect id="applyFromType" dicttype="AppSource" name="query.appSource" />
				</@field>
			</@row>
            <@row>
            	<@field label="上一操作人" label_ar="12">
                    <@input name="query.taskLastOpUser" />
                </@field>
                <@field label="初审额度" label_ar="8">
                    <@input name="query.chkLmt" />
                </@field>
                <@field label="开始时间" label_ar="8">
                	<@date name="query.startDate"  datetype="date"/>
            	</@field>
            	<@field label="结束时间" label_ar="8">
                    <@date name="query.endDate" datetype="date" />
                </@field>
            </@row>
            <@row>
            	<@field label="任务所属人" label_ar="12">
                    <@multipleSelect name="query.owner" value="${(tmAclUser.userName)!}"
                    options=cas_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true"/>
                </@field>
            	<@field label="受理网点" label_ar="8">
                    <@multipleSelect id="owningBranch" name="query.owningBranch" options=cas_('tableMap','branchMap',' ')  nullable="true" showfilter="true" single="true"/>
                </@field>
                <@field label="申请件标签" label_ar="8">
					<@multipleSelect id="FlagApp" name="appFlag" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
					'{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}" showfilter="true"/>
				</@field>
            <#--
           <@field label="进件方式" label_ar="8">
                <@dictSelect id="incomeType" dicttype="IncomeType" name="query.incomeType" />
            </@field>
            
                <@field label="推广人工号/姓名" label_ar="12">
                    <@input name="query.spreaderInfo" />
                </@field>-->
            <#--
            <@field label="受理人姓名" label_ar="8">
                <@input name="query.acceptName" />
            </@field>
            <@field label="客户类型" label_ar="8">
                    <@dictSelect id="custType" dicttype="CustType" name="query.custType" />
                </@field>
           
               <@field label="受理人工号" label_ar="8">
                <@input name="query.acceptNum" />
            </@field>
            -->
                <@toolbar align="right" style="margin-right:90px">
                    <@button id="queryButton" name="搜索" style="margin-right:15px"/>
                    <@buttonAuth code="CAS_TASK_TRANSFER">
	                    <@ajaxButton id="transBtn" name="案件转分配" style="margin-right:15px"/>
                    </@buttonAuth>
                    <span style="color:red"> 当前超时天数设置：${(overDays)}天 </span>
                </@toolbar>
            </@row>
        </@form>
        <@table id="transferList" url="cas_taskTransfer/list" form_id="queryForm" button_id="queryButton" page_size="10" row_style="urgentTask">
            <@th checkbox="true" />
            <@th title="属性" field="approveQuickFlag" />
            <@th title="姓名" field="name" sortable="true"/>
            <@th title="申请编号" field="appNo" sortable="true"/>
            <@th title="申请类型" field="appType" render="true" sortable="true">
                <@thDictName  dicttype="AppType" value="{{row.appType}}" />
            </@th>
            <@th title="申请卡产品" field="productCd" render="true" sortable="true">
                <@thGetName options=cas_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="false"/>
            </@th>
            <@th title="上一任务操作人" field="taskLastOpUser" sortable="true"/>
            <@th title="证件号码" field="idNo" sortable="true"/>
            <@th title="推广人" render="true" sortable="true">
            {{row.spreaderNo}}-{{row.spreaderName}}
            </@th>
            <@th title="单位名" field="corpName" sortable="true"/>
        <#-- <@th title="申请额度" field="appLmt" />-->
            <@th title="初审额度" field="chkLmt" sortable="true"/>
        <#-- <@th title="快速审批标志" field="approveQuickFlag" render="true" >
                 	<@thDictName  dicttype="Indicator" value="{{row.approveQuickFlag}}" />
                 </@th>
                <@th title="优先处理标志" field="isPriority" render="true" >
                 	<@thDictName  dicttype="Indicator" value="{{row.isPriority}}" />
                 </@th>-->
        <#--      <@th title="受理网点" field="owningBranch"render="true" sortable="true">
                  <@thGetName options=cas_('tableMap','branchMap','issueInd')  value="{{row.owningBranch}}" showcode="true"/>
              </@th> -->
        <#--   <@th title="流程实例ID" field="proInstId"/> -->
        <#--   <@th title="任务定义KEY" field="taskDefKey"/> -->
        <#--   <@th title="流程名" field="proName" />-->
        <#--      <@th title="任务ID" field="taskId" sortable="true"/> -->
            <@th title="任务名" field="taskName" sortable="true"/>
            <@th title="获取时间" field="m.updateDate" render="true" sortable="true">
                <@thDate value="{{row.claimTime}}" datetype="datetime"/>
            </@th>
            <@th title="任务所属人" field="owner_" render="true" sortable="true">
                <@thGetName options=cas_('tableMap','getAllUser','') value="{{row.owner}}" showcode="false" />
            </@th>
            <@th title="任务分配人" field="assignee_" render="true" sortable="true">
                <@thGetName options=cas_('tableMap','getAllUser','') value="{{row.assignee}}" showcode="false" />
            </@th>
        </@table>

    </@panelBody>


<script type="text/javascript">
    $("#transBtn").on('click',function(){
    <#--判断是否已经选择了案件-->
        var row = $('#transferList').bootstrapTable('getSelections');
        var ids = [];
        var selected = "";
        var appNoTaskId="";<#--将appNo和taskId传到后台-->
        for(var i=0;i<row.length;i++){
            ids.push(row[i]['taskId']);
            selected += row[i]['taskId'] + ':' + row[i]['taskDefKey'] +',';  <#--52617:applyinfo-creditReport,29608:input-modify, 以这种形式传到后台-->
            appNoTaskId +=row[i]['taskId'] + ':' + row[i]['appNo'] +',';
        }
        if(ids.length == 0){
            alert("请选择需要分配的案件！");
            return false;
        }

        d = top.dialog({
            width:600,
            title: '分配员工',
            height:600,
            url:'cas_taskTransfer/userSelect?select='+selected+'&appNoTaskId='+appNoTaskId,
            oniframeload:function(){},
            onclose:function () {
            	window.location.href="${base}/cas_taskTransfer/page";
            },
        });
        d.showModal();

    });

    <#--已分配任务--双击执行任务（判断任务所属人是不是本人）-->
    $("#transferList").on('dbl-click-row.bs.table',function(row, $element, field){
        var applyNo = $element['appNo'];<#--申请件编号-->
        var applyTaskId = $element['taskId'];<#--任务id-->
        var owner = $element['owner'];<#--任务所属人-->
        var operator = '${(operator)!}';
        if(owner != operator){
            alert("不能执行其他人的任务，请重新选择！");
        }else{
            window.location.href="${base}/cas_activiti/handleTask?taskId="+applyTaskId+"&appNo="+applyNo;<#--获取任务ID和申请件编号-->
        }
    });

    <#--设置超时任务件-->
    function urgentTask(row, index){
        var classes = ['active', 'success', 'info', 'warning', 'danger'];  <#--定义行样式-->
        var currentDate = new Date();<#--获取当前时间-->
        <#--计算出相差的毫秒-->
        var times = currentDate - row.createTime;
        <#--计算出相差天数-->
        var days=Math.floor(times/(24*3600*1000));
        var overDays = ${(overDays)}+5;
        var applyTmOut=0;
        if(row.createTime != null && days > overDays){
        	applyTmOut=1;
        }
		<#--计算出相差的毫秒-->
        var claimTimes = currentDate - row.claimTime;
        <#--计算出相差天数-->
        var days2=Math.floor(claimTimes/(24*3600*1000));
        var overDays = ${(overDays)};
        var spTmOut=0;
        if(row.claimTime != null && days2 > overDays){
        	spTmOut=1;
        }
        if(applyTmOut==1 && spTmOut==1){
        	return {
        		classes: classes[3],
				css: {"color": "red"}
	        };
        }else if(applyTmOut==0 && spTmOut==1){
        	return {
				css: {"color": "red"}
	        };
        }else if(applyTmOut==1 && spTmOut==0){
        	return {
				classes: classes[3]
	        };
        }
        return {};
    }
    
    <#--预审转分配-->
	$("#preassignTask").on('click',function(){
		<#--判断是否已经选择了案件-->
        var row = $('#home').bootstrapTable('getSelections');
        var ids = [];
        var selected = "";
        var appNos="";<#--将appNo和taskId传到后台-->
        for(var i=0;i<row.length;i++){
            ids.push(row[i]['taskId']);
            //selected += row[i]['taskId'] + ':' + row[i]['taskDefKey'] +',';  <#--52617:applyinfo-creditReport,29608:input-modify, 以这种形式传到后台-->
            appNos +=row[i]['appNo'] +',';
        }
        if(ids.length == 0){
            alert("请选择需要分配的案件！");
            return false;
        }

        d = top.dialog({
            width:600,
            title: '重新分配案件推广人',
            height:600,
            url:'cas_taskTransfer/spreSelect?appNos='+appNos,
            oniframeload:function(){},
            onclose:function () {
            	window.location.href="${base}/cas_taskTransfer/page";
            },
        });
        d.showModal();
    });
</script>



</@body>