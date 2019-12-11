<#include "/layout.ftl"/>
<@body>
<script type="text/javascript">
    //如果申请件信息存在 进入页面则直接弹出申请件信息的窗口
    window.onload=function(){
		<#if hisApplyInfoList?? && hisApplyInfoList?size gt 0>
            var url = '${base}/cas_commonDialog/applyHistoryLayout?appNo=${(appNo)!}';
            dialogInfo('[${(appNo)!}]历史申请信息',1000,500,url);
		<#else>
            ar_.buttonDisable('historyBtn',true);
		</#if>
    }
    //初始化
    $(function(){
	<#--必填控制-->
        // 修复bootstrap validator重复向服务端提交bug
        $('#applyInputForm').on('success.form.bv', function(e) {
            // Prevent form submission
            e.preventDefault();
        });
        $('#applyInputForm').bootstrapValidator({
            message: '验证失败',
            excluded: ':disabled',
            fields: {
                'query.finalResult': {
                    message: '验证失败',
                    validators: {
                        notEmpty: {
                            message: '审批结果不能为空'
                        }
                    }
                },
                'tmAppMain.accLmt': {
                    message: '验证失败',
                    validators: {
                        notEmpty: {
                            message: '核准额度不能为空'
                        }
                    }
                },
                'refuseCode': {
                    message: '验证失败',
                    validators: {
                        notEmpty: {
                            message: '拒绝原因不能为空'
                        }
                    }
                } ,
                'query.remark': {
                    message: '验证失败',
                    validators: {
                        notEmpty: {
                            message: '终审备注不能为空'
                        }
                    }
                }
            }
        });
        var loanFeeCalcMethod = $("#loanFeeCalcMethod").val();
        if(loanFeeCalcMethod=="R"){
            $("#appFeeRateA").show();
            $("#appFeeAmtA").hide();
        }else if(loanFeeCalcMethod=="A"){
            $("#appFeeRateA").hide();
            $("#appFeeAmtA").show();
        }else if(loanFeeCalcMethod==" "){
            $("#appFeeRateA").hide();
            $("#appFeeAmtA").hide();
        }
    });
</script>
	<@panel>
		<@panel head="终审页面[${(appNo)!}]${(oldAppNo?? && oldAppNo!='')?string('[重审件]','')}${(returnFlag?? && returnFlag!='')?string('[${(returnFlag)!}]','')}"
		class="col-ar-24" style="height:670px;overflow-y:scroll;width:70% ;">
			<@panelBody>
				<@tab id="roleTab">
					<@tabNav>
						<#if appType??>

							<#if appType == "A">
								<@tabTitle pane_id="pane1" title="主附同申申请信息"/>
								<@tabTitle pane_id="pane2" title="附件证明信息"/>
							<#elseif appType == "B">
								<@tabTitle pane_id="pane1" title="独立主卡申请信息"/>
								<@tabTitle pane_id="pane2" title="附件证明信息" />
							<#elseif appType == "S">
								<@tabTitle pane_id="pane1" active="true" title="独立附卡申请信息"/>
							</#if>
							<@tabTitle pane_id="pane3" title="其他信息" />
							<@tabTitle pane_id="pane4" title="电调信息"/>
						<#--<@tabTitle pane_id="pane5" title="征信信息"></@tabTitle>-->
							<@tabTitle pane_id="pane6" active="true" title="决策信息"></@tabTitle>
						</#if>
					</@tabNav>
					<@tabContent>
						<#if appType??>
							<#if appType == "A" || appType == "B">
								<@tabPane id="pane1">
									<#include "../../applyView/V1/mainCardInfoView_V1.ftl"/>
								</@tabPane>
								<@tabPane id="pane2">
									<#include "../../applyView/V1/annexEviInfoView_V1.ftl"/>
								</@tabPane>
								<@tabPane id="pane3">
									<#include "../../applyView/V1/mainCardOtherInfoView_V1.ftl"/>
								</@tabPane>
							<#elseif appType == "S">
								<@tabPane id="pane1">
									<#include "../../applyView/V1/attachCardInfoView_V1.ftl"/>
								</@tabPane>
								<@tabPane id="pane3">
									<#include "../../applyView/V1/attachCardOtherInfoView_V1.ftl"/>
								</@tabPane>
							</#if>
						</#if>
						<@tabPane id="pane4">
							<#include "../../applyView/V1/telSuveryInfoView_V1.ftl"/>
						</@tabPane>
					<#--<@tabPane id="pane5" active="true">
                        <#include "../../applyView/V1/creditInfoTabView_V1.ftl"/>
                    </@tabPane>-->
						<@tabPane id="pane6" active="true">
							<#include "../../applyView/V1/cheatInfoView_V1.ftl"/>
						</@tabPane>
					</@tabContent>
				</@tab>
			</@panelBody>
		</@panel>

		<@panel head="" class="col-ar-12" style="height:670px;width:30%;overflow-y:scroll;">
			<@panelBody>
				<@form id="applyInputForm">
					<@field hidden="true">
						<@input name="tmAppMain.appNo" value="${(appNo)!}"/>
						<@input name="query.taskId" value="${(taskId)!}"/>
						<@input id="formKey" value="${(formKey)!}"/>
					</@field>
					<#include "finalAuditOperateInfo_V1.ftl"/>
					<@toolbar align="center">
						<@button name="提交" id="submit" style="margin-right:5px;"/>
						<@backButton style="margin-left:5px;"/>
					</@toolbar>
				</@form>
			</@panelBody>
		</@panel>

	</@panel>

<script>
	<#--提交数据-->
    $("#submit").on('click',function(){
        if(!$("#applyInputForm").unicornValidForm()){
            alert('请将必输项填写完整！');
            return false;
        }else{
            if('${(applyNodeInquiryData.checkResult)!}'=='R' && $('#finalResult').val()=='P'){
                alert('初审拒绝件不能通过！');
                return false;
            }
            $('#submit').unicornButtonDisable(true);
            var finalResult = $('#finalResult').val();
            if(finalResult == 'P') {
                if (${(applyOperateDto.chkLmt)!0} ==0) {
                    if(!confirm("初审额度为空或为零，请确认是否继续‘通过’？")){
                        $('#submit').unicornButtonDisable(false);
                        return false;
                    }
                }else {
                    if ($('#accLmt').val() >${(applyOperateDto.chkLmt)!0}) {
                        alert('终审额度不能大于初审额度！');
                        $('#submit').unicornButtonDisable(false);
                        return false;
                    }
                }
                var accLmt = $('#accLmt').val();
                var a = dialog({
                    title: '确认核准额度',
                    width: 450,
                    height: 200,
                    content: '<div style="text-align:center;margin-top:35px;"><div><label>请再次核准额度:</label><input id="reAccLmt" autofocus/></div>'
                    +'<div style="margin-left:130px;"><span id="tip" style="color:#a94442;font-size:0.75em;"></span></div></div>',
                    button:
                            [
                                {
                                    value: '确定',
                                    callback: function () {
                                        if(accLmt == $('#reAccLmt').val()){
                                            this.close(true).remove();//关闭并销毁
                                        }else{
                                            $('#tip').html('核准额度前后不一致,请重新填写');
                                            $('#reAccLmt').val('').focus(function(){
                                                $('#tip').html('');
                                            });
                                        }
                                        $('#submit').unicornButtonDisable(false);
                                        return false;
                                    },
                                    autofocus: true
                                },
                                {
                                    value: '返回',
                                    callback: function () {
                                        this.close(false).remove();
                                        $('#submit').unicornButtonDisable(false);
                                        return false;
                                    },
                                    autofocus: true
                                }
                            ]
                });
                a.addEventListener('close', function() {

                    if(this.returnValue){
                        ajaxSubmit();
                    }
                });
                a.showModal();

            }else{
                if(finalResult.indexOf("B") != -1){
                    if(!confirm("确认退回？")){
                        $('#submit').unicornButtonDisable(false);
                        return false;
                    }
                }
                ajaxSubmit();
            }
        }
    });

	<#--终审AJAX提交数据请求-->
    function ajaxSubmit(){
        var c=window.parent.goToLoading();
        $.ajax({
           url:"${base}/cas_finalAuditQuota/beforeFinalSubmit",
            type:"post",
            dataType : "json",
            data:$("#applyInputForm").serialize(),
            success: function(res){
                if(res.s){
                    finalSubmit();
                }else {
                    var show = confirm("多卡同申主件尚未处理完成，是否强制提交?");
                    if (show) {
                        finalSubmit();
                    }else {
                        return false;
                    }
                }
            }
        });
    }

    function finalSubmit(){
        var c=window.parent.goToLoading();
        $.ajax({
            url:"${base}/cas_finalAuditQuota/finalSubmit",
            type:"post",
            dataType : "json",
            data:$("#applyInputForm").serialize(),
            success:function(ref){
                alert(ref.msg);
                if(ref.s){
                    window.location.href="${base}/cas_tasklist/page";
                }else{
                    $('#submit').unicornButtonDisable(false);
                }
            },
            beforeSend: function(){
                c.showModal();
            },
            complete:function(){
                c.remove();
            }
        });
    }
</script>
</@body>

