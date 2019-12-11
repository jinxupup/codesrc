<#include "/layout.ftl"/>
<@body>
	<@panel head="申请信息【${(appNo)!}】${(oldAppNo?? && oldAppNo!='')?string('【重审件】','')}${(returnFlag?? && returnFlag!='')?string('【${(returnFlag)!}】','')}"
		class="col-ar-24" style="height:600px;overflow-y:scroll;width:68%;margin-bottom:5px;">
		<@panelBody>
			<@form>
				<@tab>
					<@tabNav>
			    	 	<#if appType??>
			    	 		<#if appType == "A">
			        			<@tabTitle pane_id="pane1" active="true" title="主附同申申请信息"/>
			        			<@tabTitle pane_id="pane2"  title="附件证明信息"/>
			        		<#elseif appType == "B">
				        		<@tabTitle pane_id="pane1" active="true"  title="独立主卡申请信息"/>
				        		<@tabTitle pane_id="pane2"  title="附件证明信息" />
							<#elseif appType == "S">
								<@tabTitle pane_id="pane1" active="true" title="独立附卡申请信息"/>
			        		</#if>
				        	<@tabTitle pane_id="pane3"  title="其他信息" />
			        	</#if>
			    	</@tabNav>
			    	<@tabContent style="width:98.5%">
			    		<#if appType??>
			    	 		<#if appType == "A" || appType == "B">
			        			<@tabPane id="pane1" active="true">
									<#include "../../applyView/V1/mainCardInfoView_V1.ftl"/>
								</@tabPane>
								<@tabPane id="pane2" >
				     				<#include "../../applyView/V1/annexEviInfoView_V1.ftl"/>
				        		</@tabPane>
				        		<@tabPane id="pane3" >
				     				<#include "../../applyView/V1/mainCardOtherInfoView_V1.ftl"/>
				        		</@tabPane>
							<#elseif appType == "S">					
								<@tabPane id="pane1" active="true">
									<#include "../../applyView/V1/attachCardInfoView_V1.ftl"/>
								</@tabPane>
								<@tabPane id="pane3" >
				     				<#include "../../applyView/V1/attachCardOtherInfoView_V1.ftl"/>
				        		</@tabPane>
			        		</#if>
			        	</#if>
			   		</@tabContent>
		 		</@tab>
	 		</@form>
		</@panelBody>
	</@panel>
 	<@panel head="复核操作" class="col-ar-12" style="height:600px;overflow-y:scroll;width:32%;margin-bottom:5px;">
		<@panelBody >
			<@form id="reviewForm" cols="1">
		    	<#include "../reviewOptions.ftl"/>
		    </@form>
		 </@panelBody>
	</@panel>
	<@toolbar align="center" style="margin:0px; padding:0px; height:35px;">
		<@button click="saveBtn" name="保存" fa="save"/>
		<@button click="submitBtn" name="提交" fa="send"/>
		<#include "../../common/showPicBtn.ftl"/><#--影像调阅按钮-->
		<@button click="replyBtn" name="退回" fa="reply"/>
		<#-- 审批历史信息 -->
		<#include "../../common/approveHistoryBtn.ftl"/>
		<#-- 修改历史信息-->
		<#include "../../common/modifyHistoryBtn.ftl"/>
		<#include "../../common/amsShowAuditHistory.ftl"/><#--审计历史-->
		<@button click="back" name="返回" fa="undo"/>
	</@toolbar>
	
	<script type="text/javascript">
		$(function(){
			<#if reviewFieldMap??>
				<#list reviewFieldMap?keys as tabType>
					<#list reviewFieldMap[tabType]?keys as key>
					<#assign fieldPageDto = reviewFieldMap[tabType][key]>
						$('#${(fieldPageDto.id)!}').text('');
					</#list>
				</#list>
			</#if>
		});

		<#--保存操作-->
		var saveBtn = function(){
			$.ajax({
				type: "POST",
				dataType : "json",
				data:$('#reviewForm').serialize(),
				url: "${base}/ams_review/saveApplyReviewInfo",
				success: function(ref){
					alert(ref.msg);
				},
			});
		}
	
		<#--提交操作-->
		var submitBtn = function(that){
			<#--复核项完整性验证
			if(!$("#reviewForm").unicornValidForm()){
			}
			-->
			$.ajax({
				type: "POST", 
				dataType : "json",			
				data: $('#reviewForm').serialize(),
				url: "${base}/ams_review/saveApplyReviewInfo",
				success: function(ref){
					if(ref.s){
						d = top.dialog({
							width:500,
							title: '复核与录入信息对比列表',
							height:400,
							url:"${base}/ams_review/compareInfoPage?appNo=${(appNo)!}&appType=${(appType)!}&taskId=${(taskId)!}",
							oniframeload:function(){},
							button:
							[
								{
							        value: '返回任务列表',
							        callback: function (){
							        	this.close();
							        	window.location.href="${base}/ams_tasklist/myTaskListPage";
							            return false;
							        },
							        autofocus: true
							    },
							     {
							        value: '返回',
							        callback: function () {
							        	this.close();
							            return false;
							        },
							        autofocus: true
								}
							],
							onclose: function(){
								if(this.returnValue == 'true'){//不继续复核新件就跳转到待办任务列表
									window.location.href="${base}/ams_tasklist/myTaskListPage";
								}else if(this.returnValue == 'false'){//防止主动关闭弹框触发
									//获取下一个申请件编号
									$.ajax({
										type: "POST",
										dataType : "json",		
										data: {'appNo':'${(appNo)!}'},
										url: "${base}/ams_review/getNextAppNo",
										success: function(refs){
											if(refs.s){
												if(refs.code == '' || refs.code == null){
													alert(refs.msg);
													window.location.href="${base}/ams_tasklist/myTaskListPage";
												}else{
													window.location.href="${base}/ams_activiti/handleTask?appNo="+refs.code;
												}
											}else{
												alert(refs.msg);
												window.location.href="${base}/ams_tasklist/myTaskListPage";
											}
										}
									});
								}
							}
			            });
			            d.showModal();
					}
				}
			});
		}
	
		<#--退回-->
		var replyBtn = function(){
			if(!confirm("确认退回？")){
				$('#submit').unicornButtonDisable(false);
				return false;
			}
			$.ajax({
				url:"${base}/ams_review/returnBackApplyReviewInfo",	
				type:"post",
				dataType : "json",
				data:$('#reviewForm').serialize(),
				success:function(ref){
					alert(ref.msg);
					window.location.href="${base}/ams_tasklist/myTaskListPage";
				}
			});
	 	}
	 	
	 	var back = function(){
	 		window.location.href="${base}/ams_tasklist/myTaskListPage";
	 	}
	 </script>
	<#--弹出框共用js-->
	<#include "../../common/dialogJs.ftl"/>
</@body>
