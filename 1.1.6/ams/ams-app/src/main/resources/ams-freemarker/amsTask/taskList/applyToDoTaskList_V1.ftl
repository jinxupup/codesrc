<#include "/layout.ftl">
<@body>
	<@panelBody style="width:99.3%;">
		<@form id="toDoQueryForm" cols="4">
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
            	<@field label="推广人工号/姓名" label_ar="12">
					<@input id="spreaderInfo" name="query.spreaderInfo" />
				</@field>
                <@field label="移动电话" label_ar="8">
					<@input id="cellPhone" name="query.cellPhone" />
				</@field>
				<@field label="单位名" label_ar="8">
                    <@input name="query.corpName" />
                </@field>
                <@field label="申请渠道" label_ar="8">
                    <@dictSelect id="applyFromType" dicttype="AppSource" name="query.appSource" />
                </@field>
            </@row>
            <@row>
            	<@field label="录入人工号/姓名" label_ar="12">
	                <@input name="query.inputInfo" />
	            </@field>
                <@field label="开始时间" label_ar="8">
                    <@date name="query.startDate" datetype="datetime"/>
                </@field>
                <@field label="结束时间" label_ar="8">
                    <@date name="query.endDate" datetype="datetime"/>
                </@field>
            </@row>
            <@row>
				<@field label="上一任务操作人" label_ar="12">
                    <@input name="query.taskLastOpUser" />
                </@field>
                <@field label="受理网点" label_ar="8">
                	<@multipleSelect id="owningBranch" name="query.owningBranch" options=ams_('tableMap','branchMap',' ')  nullable="true" showfilter="true" single="true"/>
                </@field>
                <@toolbar align="right" style="margin-right:80px">
                	<@button id="todoQueryButton" name="搜索" style="margin-right:15px"/>
	            	<@href name="我的任务" href="ams_tasklist/myTaskListPage" style="margin-right:16px"/>
	            	<@buttonAuth code="AMS_TASK_ASSIGN">
                		<@button id="assignTask" name="案件分配" />
                	</@buttonAuth>
                </@toolbar>
            </@row>
	</@form>
	<@table id="home" url="ams_tasklist/todoTaskList" form_id="toDoQueryForm" button_id="todoQueryButton" page_size="25" load_auto="true">
        <@th checkbox="true"/>
        <@th title="属性" field="approveQuickFlag" />
		<@th title="姓名" field="name" sortable="true"/>
		<@th title="申请编号" field="appNo" sortable="true"/>
		<@th title="申请类型" field="appType" render="true" sortable="true">
			<@thDictName  dicttype="AppType" value="{{row.appType}}" />
		</@th>
		<@th title="申请卡产品" field="productCd" render="true" >
			<@thGetName options=ams_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="false"/>
		</@th>
		<@th title="证件号码" field="idNo" sortable="true"/>
		<@th title="单位名" field="corpName" sortable="true"/>
		<@th title="任务名" field="taskName" sortable="true"/>
			<@th title="<div style='width:85px'>操作</div>" render="true" >
				<@buttonAuth code="AMS_APPLY_TO_DO_TASK">
					<@button id="claim" name="获取" click="claim('{{row.appNo}}','{{row.chkLmt}}','{{row.taskName}}','{{row.taskId}}')"  />
					&nbsp;&nbsp;
				</@buttonAuth>
				<@buttonAuth code="AMS_APPLY_MY_TASK">
					<@button id="handle" name="执行" click="handle('{{row.appNo}}','{{row.chkLmt}}','{{row.taskId}}','{{row.taskName}}')"/>
				</@buttonAuth>
			</@th>
    </@table>
</@panelBody>
<script type="text/javascript">

  $("#assignTask").on('click',function(){
    	<#--判断是否已经选择了案件-->
    	var row = $('#home').bootstrapTable('getSelections');
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
						window.location.href="${base}/ams_tasklist/page";
					}
                },
            });
            d.showModal(); 
        
    });
    <#--查看流程图 -->
    function viewPic(taskid)
	{
		b = top.dialog({
            title: '查看流程',
            width:1000,
            height:600,
            url:'ams_activiti/showProDefImg?taskId=' + taskid, 
            oniframeload:function(){},
            button:[
            {	
                value: '确定',
                callback: function () {
               		this.close();
	                return false;
                },
                autofocus: true
            },
            ]
        });
        b.showModal(); 
	}
	
	<#--获取任务-->
	function claim(appNo,chkLmt,taskName,taskId)
	{
		
		var applyChkLmt = chkLmt; <#--初审额度-->
		var applyTaskName = taskName;<#--任务名-->
		var  applyNo = appNo;<#--申请件编号-->
		var  applyTaskId = taskId;<#--任务id-->
		
		if(confirm("是否确认获取？")){
			var hasAuth = judgeOperateAuth(applyTaskName,applyNo,applyTaskId,applyChkLmt);
			if(hasAuth){
				$.ajax({
					url:"${base}/ams_activiti/claimTask",
					type:"post",
					data:{appNo:applyNo,taskId:applyTaskId},
					dataType:"json",
		 			success:function(ref){
	 					if(ref.s){<#--如果成功-->
	 						$('#home').bootstrapTable('refresh');
	 						$('#myTask').bootstrapTable('refresh');
	 					}else{
		 					alert(ref.msg);<#--如果失败，则显示失败原因-->
	 					}
		 			}		
				});	
			}
		}
	}
	
	<#--判断是否有权限 获取、执行：  1.录入及复核不为同一人2.终审审批额度判断-->
	function judgeOperateAuth(applyTaskName,applyNo,applyTaskId,applyChkLmt){<#--任务名/申请件编号/任务id/初审额度-->
		var flag;<#--判断有无操作权限-->
		$.ajax({
				url:"${base}/ams_tasklist/judgeOperateAuth",
				type:"post",
				async: false,   <#--此处需要去掉异步-->
				data:{appNo:applyNo,taskId:applyTaskId,taskName:applyTaskName,chkLmt:applyChkLmt},
				dataType:"json",
	 			success:function(ref){
 					if(ref.s){<#--如果成功-->
 						flag = ref.s;
 						return flag;
 					}else{
	 					alert(ref.msg);<#--如果失败，则显示失败原因-->
	 					flag = ref.s;
 						return flag;
 					}
	 			}		
			});	
		return flag;		
	}
	 
	<#--执行任务(按钮)-->
	function handle(appNo,chkLmt,taskId,taskName)
	{
		var applyChkLmt = chkLmt;
		var applyTaskId= taskId;
		var applyTaskName = taskName;
		var applyNo=appNo;
		var hasAuth = judgeOperateAuth(applyTaskName,applyNo,applyTaskId,applyChkLmt);
		if(hasAuth){
			window.location.href="${base}/ams_activiti/handleTask?taskId="+applyTaskId+"&appNo="+applyNo;<#--获取任务ID和申请件编号-->
		}
	} 
	
</script>

<@buttonAuth code="AMS_TASK_CLAIM">
	<script type="text/javascript">
		<#--待办任务--双击执行任务-->
		$("#home").on('dbl-click-row.bs.table',function(row, $element, field){
			var applyNo = $element['appNo'];<#--申请件编号-->
			var applyTaskId = $element['taskId'];<#--任务id-->
			var applyTaskName = $element['taskName'];<#--任务名-->
			var applyChkLmt = $element['chkLmt'];<#--初审额度-->
			var hasAuth = judgeOperateAuth(applyTaskName,applyNo,applyTaskId,applyChkLmt);
			if(hasAuth){
				window.location.href="${base}/ams_activiti/handleTask?taskId="+applyTaskId+"&appNo="+applyNo;<#--获取任务ID和申请件编号-->
			}
		});
	</script>
</@buttonAuth>

<script type="text/javascript">
	<#-- 返回至未分配任务，修改上方路径 -->
	function changeTitle(){
		var title = "";
		var that = top.$("[href='ams_tasklist/toDoTaskListPage']");
		var lis = top.$("[href='ams_tasklist/toDoTaskListPage']", top.$('#side-menu')).parents("ul");
		for (var i = lis.length - 1; i >= 0; i--) {
			var ul = lis[i];
			var a = top.$(ul).prev("a");
			if($.trim(a.html())!=""){
				title += "<li>" + $.trim(a.html()) + "</li>";
			}
		}
		title += "<li>" + $.trim(that.html()) + "</li>";
		title = title.replace(/icon-angle-left/g,"");
		top.$("#page-header-title").empty().append(title);
	}
	
	$(function(){
		changeTitle();
	});
</script>
</@body>