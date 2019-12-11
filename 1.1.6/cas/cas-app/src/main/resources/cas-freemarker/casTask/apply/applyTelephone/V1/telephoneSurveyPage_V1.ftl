<#include "/layout.ftl"/>
<#include "../../common/ajaxSelect.ftl"/>
<@body>
	<script type="text/javascript">
		//如果申请件信息存在 进入页面则直接弹出申请件信息的窗口
        window.onload=function(){
			<#if hisApplyInfoList?? && hisApplyInfoList?size gt 0>
                var url = '${base}/cas_commonDialog/applyHistoryLayout?appNo=${(appNo)!}';
                dialogInfo('[${(appNo)!}]历史申请信息',1000,500,url);
			<#else>
                ar_.buttonDisable("historyBtn",true);
			</#if>
        }
		<#--必填字段验证功能-->
		 $(function (){
	 		// 修复bootstrap validator重复向服务端提交bug
           $('#applyInputForm').on('success.form.bv', function(e) {
            	// Prevent form submission
            	e.preventDefault();
        	});  
		   $('#applyInputForm').bootstrapValidator({
		　　　　　　message: '验证失败',
			    　   excluded: ':disabled',
			    fields: {
					<#if validFieldMap?? && validFieldMap['mainTab']??>
						 <#list validFieldMap['mainTab'] as validFieldInfoDto>
						 		'${validFieldInfoDto.field}': {
							        validators: {
							        	<#if validFieldInfoDto.betweenFlag?? && validFieldInfoDto.betweenFlag>
							        		between: {
						                        min: ${(validFieldInfoDto.betweenMin)!},
						                        max: ${(validFieldInfoDto.betweenMax)!},
						                        message: '请输入${(validFieldInfoDto.betweenMin)!'0'}到${(validFieldInfoDto.betweenMax)!}之间的数值'
						                    },
							        	</#if>
							        	<#if validFieldInfoDto.lengthFlag?? && validFieldInfoDto.lengthFlag>
							        		stringLength: {
						                        max: ${(validFieldInfoDto.lengthMax)!},
						                        message: '最大长度为${(validFieldInfoDto.lengthMax)!}'
						                    },
							        	</#if>
							        	<#if validFieldInfoDto.regexpFlag?? && validFieldInfoDto.regexpFlag>
							        		regexp: {
						                        regexp: /${(validFieldInfoDto.regexp)!}/i,
						                        message: '格式不正确'
						                    },
							        	</#if>
							        	<#if validFieldInfoDto.notEmptyFlag?? && validFieldInfoDto.notEmptyFlag>
								         	notEmpty: {
								            	message: '不能为空'
								          	}
							          	</#if>
							        }
								},
						 </#list>
					</#if>
					'query.telCheckResult': {
						validators: {
		                    notEmpty: {
		                        message: '审批结果不能为空'
		                    }
		                }
		             },
					'refuseCode': {
						validators: {
		                    notEmpty: {
		                        message: '拒绝原因不能为空'
		                    }
		                }
		             },
		             'query.telRemark': {
						validators: {
		                    notEmpty: {
		                        message: '电调备注不能为空'
		                    }
		                }
		             }
			      }
			  });
		});
	</script>
	
	<#--共用js-->
	<#include "../../common/pageCommonJs.ftl"/>
	<#--领卡方式的改变js-->
	<#include "../../common/cardFetchJs.ftl"/>
		<@form id="applyInputForm" auto_valid="false">
			<@panel head="电话调查[${(appNo)!}]${(oldAppNo?? && oldAppNo!='')?string('[重审件]','')}${(returnFlag?? && returnFlag!='')?string('[${(returnFlag)!}]','')}"
			class="col-ar-24" style="height:670px;overflow-y:scroll;width:70%">
				<@panelBody>
					<@field hidden="true">
						<@input name="tmAppMain.appNo" value="${(appNo)!}"/>
						<@input name="query.taskId" value="${(taskId)!}"/>
						<@input id="formKey" value="${(formKey)!}"/>
					</@field>
					<@tab id="tabs">
						<@tabNav>
							<#if appType == "A">
								<@tabTitle pane_id="pane1" title="主附同申申请信息"/>
								<@tabTitle pane_id="pane2" title="附件证明信息"/>
							<#elseif appType == "B">
								<@tabTitle pane_id="pane1" title="独立主卡申请信息"/>
								<@tabTitle pane_id="pane2" title="附件证明信息" />
							<#elseif appType == "S">
								<@tabTitle pane_id="pane1" title="独立附卡申请信息"/>
							</#if>
							<@tabTitle pane_id="pane3" title="其他信息" />
							<@tabTitle pane_id="pane4" title="决策信息" />
							<@tabTitle pane_id="pane5" active="true" title="电调信息"></@tabTitle>
                        <#--<@tabTitle pane_id="pane6" title="征信信息"></@tabTitle>-->
						</@tabNav>
						<@tabContent>
							<#if appType??>
								<#if appType == "A" || appType == "B">
									<@tabPane id="pane1">
										<#include "../../applyInputOrModify/V1/mainCardInfo_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane2">
										<#include "../../applyInputOrModify/V1/annexEviInfo_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane3">
										<#include "../../applyInputOrModify/V1/mainCardOtherInfo_V1.ftl"/>
									</@tabPane>
								<#elseif appType == "S">
									<@tabPane id="pane1" active="true">
										<#include "../../applyInputOrModify/V1/attachCardInfo_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane3">
										<#include "../../applyInputOrModify/V1/attachCardOtherInfo_V1.ftl"/>
									</@tabPane>
								</#if>
							</#if>
							<@tabPane id="pane4">
								<#include "../../applyView/V1/cheatInfoView_V1.ftl">
							</@tabPane>
							<@tabPane id="pane5" active="true">
								<#include "telSuveryInfo_V1.ftl">
							</@tabPane>
                        <#--<@tabPane id="pane6">
	     			        <#include "../../applyView/V1/creditInfoTabView_V1.ftl"/>
	        	        </@tabPane>-->
						</@tabContent>
					</@tab>
				</@panelBody>
			</@panel>
			<@panel head="" class="col-ar-12" style="height:670px;width:30%;overflow-y:scroll;">
				<@panelBody>
					<#include "telephoneOperationMsg_V1.ftl"/>
					<@toolbar align="center" style="margin-top:0px;">
						<@button id="saveBtn" name="保存" fa="save" style="margin-right:5px;"/>
						<@button id="subBtn" name="提交" style="margin-left:5px;margin-right:5px;"/>
						<@backButton style="margin-left:5px;" name="返回" />
					</@toolbar>
				</@panelBody>
			</@panel>
		</@form>
	<script type="text/javascript">
		<#--提交电话调查数据-->
	    $("#subBtn").on('click',function(){
            var c=window.parent.goToLoading();
            s=contactNotNull();
            if(s!=null){
                return s;
            }
			var telCheckResult = $('#telCheckResult').val();
	 		if(telCheckResult.indexOf("B") != -1){
	 			if($('#applyInputForm').data('bootstrapValidator').validateField('query.telRemark').isValid()){
		 			if(!confirm("确认退回？")){
	        			return false;
	    			}
				}
			}else{
				if(!$("#applyInputForm").unicornValidForm()){
			 		alert('请将必输项填写完整！');
			 		return false;
			 	}
			 	var isFreeTelCheck = $('#isFreeTelCheck').is(':checked');//是否已完成电调\ 是否免电调 ；
			 	if(!isFreeTelCheck){
		 		if(!confirm("请确认是否已完成电调? ")){
					return false;
				}
		 	}
			}
	 		$('#subBtn').unicornButtonDisable(true);
	 		<#--AJAX请求-->
			$.ajax({
 				url:"${base}/cas_telephone/telCheckSubmit",
 				type:"post",
 				dataType : "json",
 				data:$("#applyInputForm").serialize(),
 				success:function(ref){
	 				if(ref.s){
	 					alert(ref.msg);
		 				window.location.href="${base}/cas_tasklist/page";
					}else{
						alert("操作失败："+ref.msg);
	 				}
 				},
            	beforeSend: function(){
                	c.showModal();
            	},
            	complete:function(){
               	 c.remove();
            	}
	 		 });
	    });
	 
	 	<#--保存电话调查节点信息-->
		  $("#saveBtn").on('click',function(){
			var isFreeTelCheck = $('#isFreeTelCheck').is(':checked');//是否已完成电调\ 是否免电调 ；
		  	if(!isFreeTelCheck){
		 		if(!confirm("请确认是否已完成电调? ")){
					return false;
				}
		 	}
			<#--AJAX请求-->
			$.ajax({
 				url:"${base}/cas_telephone/telCheckSave",
 				type:"post",
 				dataType : "json",
 				data:$("#applyInputForm").serialize(),
 				success:function(ref){
 					if (ref.s) {
 						alert(ref.msg);
					}else {
 						alert("操作失败："+ref.msg)
					}
					<#if pageOnOffParamDto?? && pageOnOffParamDto.isSaveSkip?? && pageOnOffParamDto.isSaveSkip == 'Y'>
						if(ref.s){
							<#--提交初审调查数据返回历史页面-->
							history.go(-1);
						}
					</#if>
 				}
 			});
 		});	
	 </script>
</@body>
