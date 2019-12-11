<#include "/layout.ftl"/>
<@body>
	<@gotop/>
	<@panelBody>
		<@form id="queryForm" cols="4">
			<@row>
				<@field label="申请件编号">
					<@input id="appNo" name="query.appNo"  value="${(query.appNo)!}" />
				</@field>
				<@field label="姓名">
					<@input id="name" name="query.name"  value="${(query.name)!}"/>
				</@field>
				<@field label="证件号码">
					<@input id="idNo" name="query.idNo" value="${(query.idNo)!}"/>
				</@field>

                <@field label="移动电话">
                    <@input id="cellPhone" name="query.cellPhone" value="${(query.cellPhone)!}"/>
                </@field>
			</@row>
			<@row>
                <@field label="卡号">
                    <@input id="cardNo" name="query.cardNo" value="${(query.cardNo)!}"/>
                </@field>
                <@field label="卡产品代码">
                    <@select id="productCd" name="query.productCd" options=cas_('tableMap','productForStatus','A,B,C') value="${(query.productCd)!}"/>
                </@field>
				<@field label="公司名称">
					<@input id="corpName" name="query.corpName" value="${(query.corpName)!}"/>
				</@field>
				<@field label="公司电话">
					<@input id="empPhone" name="query.empPhone" value="${(query.empPhone)!}"/>
				</@field>
			</@row>
			<@row>
                <@field label="申请渠道">
                    <@dictSelect id="appSource" dicttype="AppSource"showcode='true' name="query.appSource"value="${(query.appSource)!}" />
                </@field>
                <@field label="受理网点">
                    <@multipleSelect id="owningBranch" name="query.owningBranch" value="${(query.owningBranch)!}"
                    options=cas_('tableMap','branchMap','issueInd')  nullable="true" showfilter="true" single="true"/>
                </@field>
				<@field label="审批状态">
					<@multipleSelect id="rtfState" name="rtfState" value="${(query.rtfState)!''}"
					options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict','{"type":"RtfState"}','code','codeName') showfilter="true" />
				</@field>
                <@field label="任务所属人">
                    <@multipleSelect id="owner" name="query.owner" value="${(tmAclUser.userName)!}"
                    options=cas_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true"/>
                </@field>
			</@row>
            <@row>
                <@field label="推广人编号">
                    <@input id="spreader" name="query.spreader" value="${(query.spreader)!}" />
                </@field>
                <@field label="推广主管代码">
                    <@input id="spreaderSupCode" name="query.spreaderSupCode" value="${(query.spreaderSupCode)!}" />
                </@field>
                <@field label="推广区域代码">
                    <@input id="spreaderAreaCode" name="query.spreaderAreaCode" value="${(query.spreaderAreaCode)!}" />
                </@field>
             </@row>
			<@row>
                <@field label="进件查询起始日期">
                    <@date id="beginDate" name="query.beginDate" datetype="date"value="${(query.beginDate)!}"/>
                </@field>
                <@field label="进件查询截止日期">
                    <@date id="endDate" name="query.endDate" datetype="date" value="${(query.endDate)!}"/>
                </@field>
                <#--<@field label="申请件标签">-->
                    <#--<@multipleSelect id="FlagApp" name="flagApp" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',-->
                    <#--'{"type":"FlagApp"}','code','codeName') value="${(appFlagList)!}" showfilter="true"/>-->
                <#--</@field>-->
			</@row>
			<@toolbar align="right">
				<@button id="queryButton" name="查询" fa="search" style="margin-right:15px;"/>
				<@buttonAuth code="CAS_APPLY_REAUDIT">
					<@button id="reCheckButton" name="重审" fa="reply" style="margin-right:15px;"/>
				</@buttonAuth>
				<@button id="exportQueryResult" name="导出数据"  style="margin-right:15px;"/>
				<@resetButton sense="warning" name="清空" id="resetBut" style="margin-right:15px"/>
			</@toolbar>
		</@form>
    </br>
		<@table id="dataTable" url="cas_applyQuery/applyProcessList" form_id="queryForm" page_size="25"  load_auto="false"
		button_id="queryButton">
			<@th checkbox="true"/>
			<@th title="属性" field="approveQuickFlag"/>
			<@th title="申请件编号" field="appNo" sortable="true"/>
			<@th title="姓名" field="name"  formatter="format" sortable="true"/>
			<@th title="证件类型" field="idType" render="true" sortable="true">
				<@thDictName dicttype="IdType" value="{{row.idType}}" />
			</@th>
			<@th title="证件号码" field="idNo" sortable="true"/>
			<@th title="卡号" field="cardNo" sortable="true"/>
			<@th title="申请类型" field="appType" render="true" sortable="true">
				<@thDictName dicttype="AppType" value="{{row.appType}}" />
			</@th>
			<@th title="申请卡产品" field="productCd" render="true" sortable="true">
				<@thGetName options=cas_('tableMap','productForStatus','A,B,C') value="{{row.productCd}}" showcode="false" />
			</@th>
			<@th title="受理网点" field="owningBranch"render="true" sortable="true">
				<@thGetName options=cas_('tableMap','branchMap','issueInd')  value="{{row.owningBranch}}" showcode="false"/>
			</@th>
			<@th title="移动电话" field="cellPhone" sortable="true"/>
			<@th title="审批状态" field="rtfState"render="true" sortable="true">
            {{if row.attachFlag=="true"}}
            {{if row.ifAttachRefuse=="N"}}
				<@input value="M05-失败申请" label_only="true"/>
            {{else}}
			<#-- 		<@input value="L05-成功申请" label_only="true"/> -->
				<@thDictName dicttype="RtfState" showcode="true"  value="{{row.rtfState}}" />
            {{/if}}
            {{else}}
				<@thDictName dicttype="RtfState" showcode="true"  value="{{row.rtfState}}" />
            {{/if}}
			</@th>
			<@th title="任务所属人" field="owner"sortable="true" />
        <#-- <@th title="任务分配人" field="assignee" />
				<@th title="修改人" field="updateUser"sortable="true" />
				<@th title="申请日期" field="createDate"render="true" sortable="true">
					<@thDate value="{{row.createDate}}" />
				</@th>
				<@th title="影像" field="imageNum" render="true"sortable="true">
					{{if row.imageNum!=null }}
						有
					{{/if}}
				</@th> -->
            <@th title="上一操作人" field="taskLastOpUser" sortable="true"/>
			<@th title="授信额度" field="accLmt" />
			<@buttonAuth code="CAS_APPLY_REMARK"><@th title="拒绝原因" field="refuseCode"sortable="true"/></@buttonAuth>
			<@th title="<div style='width:40px'>操作</div>" render="true" >
				<@buttonAuth code="CAS_APPLY_DETAIL"><@href name="详情" href="/cas_activiti/handleTask?appNo={{row.appNo}}&detailFlag=Y"/></@buttonAuth>
				<@buttonAuth code="CAS_APPLY_UPDATE"><@href name="修改" href="/cas_activiti/handleTask?appNo={{row.appNo}}&updateFlag=Y"/></@buttonAuth>
				<@buttonAuth code="CAS_APPLY_DELETE">
                {{if row.rtfState !='P05'}}
                {{if row.rtfState !='A20'}}
                {{if row.rtfState !='K10'}}
                {{if row.rtfState !='L05'}}
                {{if row.rtfState !='N05'}}
                {{if row.rtfState !='O05'}}
                {{if row.rtfState !='Q05'}}
                <#--<input style="color: #fff; background-color: #ff4d4f;border-color: #ff4d4f; " type="button" value="删除"-->
                       <#--onclick="applyQueryDelete('{{row.appNo}}')"/>-->
                    <#--<@ajaxButton confirm="确定删除？" sense="danger" name="删除"/>-->
                <button style="color: #fff; background-color: #D9534F;border-color: #D9534F;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 36px;height: 21.5px;font-size: 1px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                        onclick="applyQueryDelete('{{row.appNo}}')">删除</button>
                {{/if}}
                {{/if}}
                {{/if}}
                {{/if}}
                {{/if}}
                {{/if}}
                {{/if}}
                </@buttonAuth>
                <#--<@buttonAuth code="CAS_APPLY_DELETE">-->
                    <#--<@button sense="danger" id="deletee" name="删除"/>-->
            <#--</@buttonAuth>-->
            <#--<@buttonAuth code="CAS_APPLY_DELETE">-->
            <#--<input style="background-color: #ff4b3f" type="button" value="删除"-->
            <#--onclick="lala('{{row.appNo}}')"/>-->
            <#--</@buttonAuth>-->
            <#--<@toolbar align="center">-->
            <#--<@button name="删除删除删除" id="delect" style="margin-right:5px;"/>  				<@input value="M05-失败申请" label_only="true"/>
-->
            <#--<@backButton style="margin-left:5px;"/>-->
            <#--</@toolbar>-->
            </@th>
        </@table>
    </@panelBody>

<script type="text/javascript">


    function applyQueryDelete(appNo) {
        var remark = null;
        remark = prompt("请填写删除备注: ");
        if (remark == null | remark == "") {
            alert("取消删除");
        } else {
            $.ajax({
                url: "${base}/cas_applyQuery/applyQueryDelete",
                type: "get",
                data: {"appNo": appNo, "remark": remark},
                dataType: "json",
                async: false,
                success: function (res) {
                    window.location.href = "${base}/cas_applyQuery/applyProcessQuery";

                }
            });
        }
    }


    $("#resetBut").on("click", function () {
        $('select').multipleSelect('refresh');
        $('select').multipleSelect('uncheckAll');
    });
<#--用于返回按钮初始化列表-->
    $(function(){
        var query = $("#queryForm").serializeObject();

        var isIn = false;
        for(var x in query){
            if(query[x] != ""){
                isIn = true;
            }
        }
        if(isIn==true){
            var params = {url:ar_.randomUrl("${base}/cas_applyQuery/applyProcessList")};
            $("#dataTable").bootstrapTable("getOptions").customerQuery = query;
            $("#dataTable").bootstrapTable("refresh",params);
        }
    });

	<#--导出数据按钮-->
       $("#exportQueryResult").on("click",function(){
           var  elements = new Array();
           var appNo=document.getElementById("appNo").value;
           var name=document.getElementById("name").value;
           var idNo=document.getElementById("idNo").value;
           var cardNo=document.getElementById("cardNo").value;
           var cellPhone=document.getElementById("cellPhone").value;
           var corpName=document.getElementById("corpName").value;
           var empPhone=document.getElementById("empPhone").value;
           var owner=document.getElementById("owner").value;
           var rtfState=document.getElementById("rtfState").value;
           var beginDate=document.getElementById("beginDate").value;
           var endDate=document.getElementById("endDate").value;
           var productCd=document.getElementById("productCd").value;
           var appSource=document.getElementById("appSource").value;
           var spreader=document.getElementById("spreader").value;
           var spreaderSupCode=document.getElementById("spreaderSupCode").value;
           var spreaderAreaCode=document.getElementById("spreaderAreaCode").value;
           var owningBranch=document.getElementById("owningBranch").value;
           if(  (appNo!=""&&appNo!=null)||
                (name!=""&&name!=null)||
                (idNo!=""&&idNo!=null)||
                (cardNo!=""&&cardNo!=null)||
                (cellPhone!=""&&cellPhone!=null)||
                (corpName!=""&&corpName!=null)||
                (empPhone!=""&&empPhone!=null)||
                (owner!=""&&owner!=null)||
                (rtfState!=""&&rtfState!=null)||
                (beginDate!=""&&beginDate!=null)||
                (endDate!=""&&endDate!=null)||
                (productCd!=""&&productCd!=null)||
                (appSource!=""&&appSource!=null)||
                (spreader!=""&&spreader!=null)||
                (spreaderSupCode!=""&&spreaderSupCode!=null)||
               (spreaderAreaCode!=""&&spreaderAreaCode!=null)||
                (owningBranch!=""&&owningBranch!=null)
           ){
               window.location.href="${base}/cas_applyQuery/exportExcelProcess?"+$("#queryForm").serialize();
           	}
           else{
               alert("导出数据时查询条件不能为空。");
               return false;
           }
       });
    <#--
    function startAutoRefresh(){
    	var tid = setInterval(function(){
			$("#queryButton").click();
		},30000);
		return tid;
    }-->
	<#--重审-->
    $("#reCheckButton").on('click',function(){
	<#--判断是否已经选择了案件-->
        var row = $('#dataTable').bootstrapTable('getSelections');
        var appNo="";
        if(row.length==0){
            alert("请选择需要重审的案件!");
            return false;
        }
        for(var i=0;i<row.length;i++){
            if(row[i]['rtfState'] != "M05"){
                alert("只有申请失败的案件才能重审!");
                return false;
            }
            if(row[i]['isHaveRetrial'] == "Y"){
                alert("每笔案件只能重审一次!");
                return false;
            }
            //TODO: 待处理-主附同申附卡失败的状态已经调整，需要重新调整逻辑
            if(row[i]['appType'] == "A" && row[i]['mainRefFlag'] == "false"){
                alert("主附同申主卡成功，附卡失败不可以重审!");
                return false;
            }
            appNo=row[i]['appNo'];
        }
        window.location.href="${base}/cas_activiti/handleTask?retrialFlag=Y&appNo="+appNo;
    });

	<#--后台传过来attachFlag为ture，就标记红色-->
    function format(value,row){
        if(row.attachFlag=="false"){
            var name=row.name+'<font color="red">(主)</font>';
        }else  if(row.attachFlag=="true"){
            var name=row.name+'<font color="blue">(附)</font>';
        }else{
            var name=row.name;
        }
        return name;
    }

	<#--如果是主附同申的附卡且状态为N，则设置审批状态-->
    function setRtfstate(value,row){
        if(row.appType=="A" && row.attachFlag=="true" && row.ifAttachRefuse=="N"){
            var rtfState="M05-失败申请";
        }else{
            var rtfState=row.rtfState;
        }
        return rtfState;
    }
	<@buttonAuth code="CAS_APPLY_DETAIL">
		<#----双击查看详情-->
	    $("#dataTable").on('dbl-click-row.bs.table',function(row, $element, field){
	        var appNo = $element['appNo'];
	        window.location.href="${base}/cas_activiti/handleTask?detailFlag=Y&appNo="+appNo;
	    });
	</@buttonAuth>
	<#--导出数据按钮-->
       $("#exportQueryResult").on("click",function(){
           var  elements = new Array();
           var appNo=document.getElementById("appNo").value;
           var name=document.getElementById("name").value;
           var idNo=document.getElementById("idNo").value;
           var cardNo=document.getElementById("cardNo").value;
           var cellPhone=document.getElementById("cellPhone").value;
           var corpName=document.getElementById("corpName").value;
           var empPhone=document.getElementById("empPhone").value;
           var owner=document.getElementById("owner").value;
           var rtfState=document.getElementById("rtfState").value;
           var beginDate=document.getElementById("beginDate").value;
           var endDate=document.getElementById("endDate").value;
           var productCd=document.getElementById("productCd").value;
           var appSource=document.getElementById("appSource").value;
           var incomeType=document.getElementById("incomeType").value;
           var custType=document.getElementById("custType").value;
           var owningBranch=document.getElementById("owningBranch").value;
           if(  (appNo!=""&&appNo!=null)||
                (name!=""&&name!=null)||
                (idNo!=""&&idNo!=null)||
                (cardNo!=""&&cardNo!=null)||
                (cellPhone!=""&&cellPhone!=null)||
                (corpName!=""&&corpName!=null)||
                (empPhone!=""&&empPhone!=null)||
                (owner!=""&&owner!=null)||
                (rtfState!=""&&rtfState!=null)||
                (beginDate!=""&&beginDate!=null)||
                (endDate!=""&&endDate!=null)||
                (productCd!=""&&productCd!=null)||
                (appSource!=""&&appSource!=null)||
                (incomeType!=""&&incomeType!=null)||
                (custType!=""&&custType!=null)||
                (owningBranch!=""&&owningBranch!=null)
	   ){
	   window.location.href="${base}/cas_applyQuery/exportExcelProcess?"+$("#queryForm").serialize();
            <#--//window.location.href="${base}/cas_applyQuery/exportApplyQueryExcel ? "+$("#queryForm").serialize();-->
           	}
           else{
               alert("导出数据时查询条件不能为空。");
               return false;
           }
       });
</script>

</@body>