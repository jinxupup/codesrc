<#include "/layout.ftl"/>
<@body>

<@panelBody style="width:99.3%;">
<@form id="queryForm" cols="4">
			<@field label="当前操作员" hidden="true">
                <@input id="operator" name="operator" value="${(operator)!}" />
            </@field>
        <@row>
			<@field label="任务名" label_ar="12">
                <@dictSelect id="taskName" dicttype="AmsTaskName" showcode="false" name="query.taskName" />
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
        	<@field label="任务所属人" label_ar="12">
                <@multipleSelect name="query.owner" value="${(tmAclUser.userName)!}"
					options=ams_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true"/>
            </@field>
        	<@field label="移动电话" label_ar="8">
				<@input id="cellPhone" name="query.cellPhone" />
			</@field>
			<@field label="单位名" label_ar="8">
                <@input name="query.corpName" />
            </@field>
            <@field label="申请渠道" label_ar="8">
				<@dictSelect  dicttype="AppSource"showcode='true' name="query.appSource" />
            </@field>
		</@row>
        <@row>
        	<@field label="推广人工号/姓名" label_ar="12">
				<@input id="spreaderInfo" name="query.spreaderInfo" />
			</@field>
            <@field label="开始时间" label_ar="8">
                <@date name="query.startDate"  datetype="date"/>
            </@field>
            <@field label="结束时间" label_ar="8">
                <@date name="query.endDate" datetype="date" />
            </@field>
        </@row>
        <@row>
        	<@field label="录入人工号/姓名" label_ar="12">
                <@input name="query.inputInfo" />
            </@field>
            <@field label="上一操作人" label_ar="8">
                <@input name="query.taskLastOpUser" />
            </@field>
            <@field label="受理网点" label_ar="8">
                 <@multipleSelect id="owningBranch" name="query.owningBranch" options=ams_('tableMap','branchMap',' ')  nullable="true" showfilter="true" single="true"/>
            </@field>
            <@toolbar align="right" style="margin-right:90px">
            	<@button id="queryButton" name="搜索" style="margin-right:15px"/>
            	<@ajaxButton id="transBtn" name="案件转分配" style="margin-right:15px"/>
            	<span style="color:red"> 当前超时天数设置：${(overDays)}天 </span>
            </@toolbar>
        </@row>
  </@form>
   <@table id="transferList" url="ams_taskTransfer/list" form_id="queryForm" button_id="queryButton" page_size="10" row_style="urgentTask">
        <@th checkbox="true" />
        <@th title="属性" field="approveQuickFlag" />
        <@th title="姓名" field="name" sortable="true"/>
        <@th title="申请编号" field="appNo" sortable="true"/>
      	<@th title="申请类型" field="appType" render="true" sortable="true">
         	<@thDictName  dicttype="AppType" value="{{row.appType}}" />
        </@th>
        <@th title="申请卡产品" field="productCd" render="true" >
			<@thGetName options=ams_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="false"/>
		</@th>
        <@th title="证件类型" field="idType" render="true" sortable="true">
         	<@thDictName  dicttype="IdType" value="{{row.idType}}" />
        </@th>
        <@th title="证件号码" field="idNo" sortable="true"/>
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
			<@thGetName options=ams_('tableMap','branchMap','issueInd')  value="{{row.owningBranch}}" showcode="true"/>
		</@th> -->
     <#--   <@th title="流程实例ID" field="proInstId"/> -->
     <#--   <@th title="任务定义KEY" field="taskDefKey"/> -->
      <#--   <@th title="流程名" field="proName" />-->
  <#--      <@th title="任务ID" field="taskId" sortable="true"/> -->
        <@th title="任务名" field="taskName" sortable="true"/>
        <@th title="获取时间" field="claimTime" render="true" sortable="true">
			<@thDate value="{{row.claimTime}}" datetype="datetime"/>
		</@th>
        <@th title="任务所属人" field="owner" />
        <@th title="任务分配人" field="assignee"/>
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
                url:'ams_taskTransfer/userSelect?select='+selected+'&appNoTaskId='+appNoTaskId,
                oniframeload:function(){},
                onclose:function () {
					if (this.returnValue=='true') {
						window.location.href="${base}/ams_taskTransfer/page";
					}
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
			window.location.href="${base}/ams_activiti/handleTask?taskId="+applyTaskId+"&appNo="+applyNo;<#--获取任务ID和申请件编号-->
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
		var overDays = ${(overDays)};
		if(row.createTime != null && days > overDays){
    		return {
		        classes: classes[4], 
		        css: {"color": "red"} 
		    };
  		}
  		
	    return {};
  		
	} 
	
	
<#--页面加载完后执行-->
$(document).ready(function(){ 
	if("${(taskTransferAuth)!}" == "no"){
		$('#transBtn').attr("disabled",true);
	}
	
});
	
</script>



</@body>