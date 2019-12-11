<#include "/layout.ftl">
<@body>
	<@panelBody style="width:99.3%;">
		<@form id="myQueryForm" cols="4">
			<@row>
				<@field label="任务名" label_ar="12">
					<@dictSelect id="taskName" dicttype="AmsTaskName" showcode="false" name="query.taskName" />
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
				<@field label="录入人工号/姓名" label_ar="12">
	                <@input name="query.inputInfo" />
	            </@field>
				<@field label="开始时间" label_ar="8">
					<@date id="startDate" name="query.startDate" datetype="datetime"/>
				</@field>
				<@field label="结束时间" label_ar="8">
					<@date id="endDate" name="query.endDate" datetype="datetime"/>
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
					<@button id="myQueryButton" name="搜索" style="margin-right:5px;"/>
                    <@button id="exportMyTaskList" name="导出数据"  style="margin-right:15px;"/>
                    <@href name="待分配任务" href="ams_tasklist/toDoTaskListPage" style="margin-right:10px;margin-left:5px;"/>
                <span style="color:red" > 审批超时天数：${(overDays)}天 </span>
					<#--<#if autoRefresh>
                    	<label style="padding-left:5px;"><input type="checkbox" id="autoRefresh" name="autoRefresh" value="Y" checked/>自动刷新</label>
					</#if>-->
				</@toolbar>
			</@row>
		</@form>
		<@table id="myTask" url="ams_tasklist/myTaskList" form_id="myQueryForm" button_id="myQueryButton" row_style="urgentTask" single_select="true" page_size="25" >
			<#--<@th checkbox="true"/> -->
			<@th title="属性" field="approveQuickFlag" />
			<@th title="姓名" field="name" sortable="true"/>
			<@th title="申请编号" field="appNo" sortable="true" />
			<@th title="申请类型" field="appType" render="true" sortable="true">
				<@thDictName  dicttype="AppType" value="{{row.appType}}" />
			</@th>
			<@th title="申请卡产品" field="productCd" render="true" >
				<@thGetName options=ams_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="false"/>
			</@th>
			<@th title="证件号码" field="idNo" sortable="true"/>
			<@th title="单位名" field="corpName" sortable="true"/>
        	<@th title="申请额度" field="appLmt" />
			<@th title="受理网点" field="owningBranch"render="true" sortable="true">
				<@thGetName options=ams_('tableMap','branchMap','') value="{{row.owningBranch}}" showcode="true"/>
			</@th>
			<@th title="任务名" field="taskName" sortable="true"/>
			<@th title="获取时间" field="claimTime" render="true" >
				<@thDate value="{{row.claimTime}}" datetype="datetime"/>
			</@th>
			<@th title="操作" render="true" >
				<@buttonAuth code="AMS_APPLY_MY_TASK">
					<@href href="ams_activiti/handleTask?taskId={{row.taskId}}&appNo={{row.appNo}}" name="执行" />&nbsp;&nbsp;
				</@buttonAuth>
				<@buttonAuth code="AMS_APPLY_MY_TASK">
					<@button id="viewPic" name="查看流程" click="viewPic('{{row.taskId}}')"/>&nbsp;&nbsp;
				</@buttonAuth>
				<@buttonAuth code="AMS_APPLY_MY_TASK">
					<@ajaxButton  confirm="确定取消此任务？" name="取消任务"
					url="ams_tasklist/cancelMyTask?taskId={{row.taskId}}&&appNo={{row.appNo}}"
					success_url="ams_tasklist/myTaskListPage" />
				</@buttonAuth>
			</@th>
		</@table>
	</@panelBody>
<script type="text/javascript">


    /*导出数据_我的待办任务*/
    $("#exportMyTaskList").on("click",function () {
        window.location.href = "${base}/ams_tasklist/exportMytaskList?" + $("#myQueryForm").serialize();
    });

	<#-- 自动刷新
    $(function () {
        var timerId = 0;

        if($("#autoRefresh").is(':checked')){
            timerId = startAutoRefresh();
        }

        $("#autoRefresh").click(function(){
            if($("#autoRefresh").is(':checked')){
                timerId = startAutoRefresh();
            }else{
                stopAutoRefresh(timerId);
            }
        });
    });
    function startAutoRefresh(){
        var tid = setInterval(function(){
            $("#myQueryButton").click();
        },30000);
        return tid;
    }

    function stopAutoRefresh(tid){
        clearInterval(tid);
    } -->

	<#--查看流程图 -->
    function viewPic(taskid)
    {
        b = top.dialog({
            title: '查看流程',
            width:1440,
            height:650,
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
	<#--我的任务--双击执行任务-->
    $("#myTask").on('dbl-click-row.bs.table',function(row, $element, field){
        var applyNo = $element['appNo'];
        var applyTaskId = $element['taskId'];
        window.location.href="${base}/ams_activiti/handleTask?taskId="+applyTaskId+"&appNo="+applyNo;<#--任务ID-->
    });



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
</script>

	<@buttonAuth code="AMS_CLAIM_TASK">
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
        var that = top.$("[href='ams_tasklist/myTaskListPage']");
        var lis = top.$("[href='ams_tasklist/myTaskListPage']", top.$('#side-menu')).parents("ul");
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
