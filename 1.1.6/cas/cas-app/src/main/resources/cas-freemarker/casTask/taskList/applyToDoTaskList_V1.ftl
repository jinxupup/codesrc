<#include "/layout.ftl">
<@body>
	<@panelBody>
		<@form id="toDoQueryForm" cols="4">
			<@row>
				<@field label="任务名" label_ar="12">
					<@dictSelect id="taskName" dicttype="CasTaskName" showcode="false" name="query.taskName" />
				</@field>
				<@field label="申请件编号" label_ar="8">
					<@input id="appNo" name="query.appNo" />
				</@field>
				<@field label="姓名" label_ar="8">
					<@input id="name" name="query.name" />
				</@field>
				<@field label="证件号码" label_ar="8">
					<@input id="idNo" name="query.idNo" />
				</@field>
			</@row>
			<#--<@field label="进件方式" label_ar="8">
					<@dictSelect id="incomeType" dicttype="IncomeType" name="query.incomeType" />
				</@field>
				<@field label="我所属组的" label_ar="8">
					<@select id="myRole" name="query.myRole" options=cas_('tableMap','getCurrentUserRoles','') nullable="true" showcode="false"/>
				</@field>
				
				<@field label="客户类型" label_ar="8">
					<@dictSelect id="custType" dicttype="CustType" name="query.custType" />
				</@field>-->
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
				<@field label="上一任务操作人" label_ar="12">
					<@input id="taskLastOpUser" name="query.taskLastOpUser" />
				</@field>
				<@field label="初审额度" label_ar="8">
					<@input id="chkLmt" name="query.chkLmt" />
				</@field>
				<@field label="开始时间" label_ar="8">
					<@date id="startDate" name="query.startDate" datetype="date"/>
				</@field>
				<@field label="结束时间" label_ar="8">
					<@date id="endDate" name="query.endDate" datetype="date"/>
				</@field>
			</@row>
			<@row>
                <@field label="受理网点" label_ar="12">
					<@multipleSelect id="owningBranch" name="query.owningBranch" options=cas_('tableMap','branchMap',' ')  nullable="true" showfilter="true" single="true"/>
				</@field>
				<@field label="申请件标签" label_ar="8">
					<@multipleSelect id="FlagApp" name="appFlag" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
					'{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}" showfilter="true"/>
				</@field>
				<@toolbar align="right" style="margin-right:80px">
					<@button id="todoQueryButton" name="搜索" style="margin-right:15px"/>
					<@href name="我的任务" href="cas_tasklist/myTaskListPage" style="margin-right:16px"/>
					<@buttonAuth code="CAS_TASK_ASSIGN">
						<@button id="assignTask" name="案件分配" style="margin-right:16px"/>
					</@buttonAuth>
				</@toolbar>
			</@row>
		</@form>
		<@table id="home" url="cas_tasklist/todoTaskList" form_id="toDoQueryForm" button_id="todoQueryButton" page_size="25" load_auto="true">
			<@th checkbox="true"/>
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
			<@th title="初审额度" field="chkLmt" sortable="true"/>
                <@th title="任务名" field="taskName" sortable="true"/>
            <@th title="进件日期" field="m.createDate" render="true" sortable="true">
                <@thDate value="{{row.createTime}}" datetype="datetime"/>
            </@th>
				<@th title="<div style='width:85px'>操作</div>" render="true" >
                <@buttonAuth code="CAS_TASK_CLAIM">
					<@button id="claim" name="获取" click="claim('{{row.appNo}}','{{row.chkLmt}}','{{row.taskName}}','{{row.taskId}}')"  />
                	&nbsp;&nbsp;
					<@button id="handle" name="执行" click="handle('{{row.appNo}}','{{row.chkLmt}}','{{row.taskId}}','{{row.taskName}}')"/>
                </@buttonAuth>
        </@th>
		</@table>
	</@panelBody>
<script type="text/javascript">
    <#--案件分配-->
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
            url:'cas_taskTransfer/userSelect?select='+selected+'&appNoTaskId='+appNoTaskId,
            oniframeload:function(){},
            onclose:function () {
            	window.location.href="${base}/cas_tasklist/toDoTaskListPage";
            },
        });
        d.showModal();

    });
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
            	window.location.href="${base}/cas_tasklist/toDoTaskListPage";
            },
        });
        d.showModal();
    });
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
                    url:"${base}/cas_activiti/claimTask",
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
            url:"${base}/cas_tasklist/judgeOperateAuth",
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
            window.location.href="${base}/cas_activiti/handleTask?taskId="+applyTaskId+"&appNo="+applyNo;<#--获取任务ID和申请件编号-->
        }
    }

</script>

<@buttonAuth code="CAS_TASK_CLAIM">
    <script type="text/javascript">
		<#--待办任务--双击执行任务-->
        $("#home").on('dbl-click-row.bs.table',function(row, $element, field){
            var applyNo = $element['appNo'];<#--申请件编号-->
            var applyTaskId = $element['taskId'];<#--任务id-->
            var applyTaskName = $element['taskName'];<#--任务名-->
            var applyChkLmt = $element['chkLmt'];<#--初审额度-->
            var hasAuth = judgeOperateAuth(applyTaskName,applyNo,applyTaskId,applyChkLmt);
            if(hasAuth){
                window.location.href="${base}/cas_activiti/handleTask?taskId="+applyTaskId+"&appNo="+applyNo;<#--获取任务ID和申请件编号-->
            }
        });
    </script>
</@buttonAuth>
<script type="text/javascript">
	<#-- 返回至未分配任务，修改上方路径 -->
    function changeTitle(){
        var title = "";
        var that = top.$("[href='cas_tasklist/toDoTaskListPage']");
        var lis = top.$("[href='cas_tasklist/toDoTaskListPage']", top.$('#side-menu')).parents("ul");
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