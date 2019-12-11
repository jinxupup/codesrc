<#include "/layout.ftl"/>
<#include "../../common/ajaxSelect.ftl"/>
<@body>
	<script type="text/javascript">
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
			      }
			  });
		});
	</script>
	
	<#--共用js-->
	<#include "../../common/pageCommonJs.ftl"/>
	<#--领卡方式的改变js-->
	<#include "../../common/cardFetchJs.ftl"/>
	
	<@panel head="申请录入界面[${(appNo)!}]${(oldAppNo?? && oldAppNo!='')?string('[重审件]','')}${(returnFlag?? && returnFlag!='')?string('[${(returnFlag)!}]','')}">
       <@panelBody>
        	<@form id="applyInputForm" auto_valid="true">
        		<@field hidden="true">
					<@input name="tmAppMain.appNo" value="${(appNo)!}"/>
					<@input name="retrialFlag" value="${(retrialFlag)!}"/>
					<@input name="oldAppNo" value="${(oldAppNo)!}"/>
					<@input name="returnFlag" value="${(returnFlag)!}"/>
					<@input id="formKey" value="${(formKey)!}"/>
				</@field>
				<#if pageFieldMap??>
				  	<@tab id="tab" style="height:650px;overflow-y:scroll;">
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
				    	<@tabContent>
				    		<#if appType??>
				    	 		<#if appType == "A" || appType == "B">
				        			<@tabPane id="pane1" active="true">
										<#include "mainCardInfo_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane2" >
					     				<#include "annexEviInfo_V1.ftl"/>
					        		</@tabPane>
					        		<@tabPane id="pane3" >
					     				<#include "mainCardOtherInfo_V1.ftl"/>
					        		</@tabPane>
								<#elseif appType == "S">
									<@tabPane id="pane1" active="true">
										<#include "attachCardInfo_V1.ftl"/>
									</@tabPane>
									<@tabPane id="pane3" >
					     				<#include "attachCardOtherInfo_V1.ftl"/>
					        		</@tabPane>
				        		</#if>
				        	</#if>
				   		</@tabContent>			   		
					</@tab>
				</#if>
				<@toolbar align="center" style="padding-top: 0px;">
					<@ajaxButton id="saveBtn" name="保存" multi_submit="true" url="ams_applyInput/updateApplyInputInfo" form_id="applyInputForm" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<@ajaxButton id="subBtn" name="提交" url="ams_applyInput/updateAndNextnode" form_id="applyInputForm" before="beforeSubmit" 
						after="afterSubmit" success_url="${isEdit?string('ams_tasklist/page','ams_applyPreInput/ams_applyPreInputpage')}"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<#include "../../common/showPicBtn.ftl"/><#--影像调阅按钮-->
					<#include "../../common/amsShowAuditHistory.ftl"/><#--审计历史-->
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<@resetButton name="还原" click="cleanOwningBranch"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="ams_applyInput/applyInputDelete?appNo=${(appNo)!}" 
						success_url="ams_applyPreInput/ams_applyPreInputpage?id=0" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<@href href="ams_applyPreInput/ams_applyPreInputpage?id=0"  name="返回" />
				</@toolbar>
			</@form>
      </@panelBody>
   </@panel>
	<script type="text/javascript">
		var c=window.parent.goToLoading();
		<#--提交之前的必输字段的验证-->
		var beforeSubmit = function(){
			<#-- s=contactNotNull();
			if(s!=null){
				return s;
			}-->
		 	if(!$("#applyInputForm").unicornValidForm()){
		 		alert('请将必输项填写完整！');
		 		return false;
		 	}
		 	<#if appType != 'S'>
		 		if($('#ddInd').val()!= 'N'){
		 			if(!checkCard()){
						return false;
					}
		 		}
			</#if>
			c.showModal();
		}
		var afterSubmit=function(){
			c.remove();
			alert('操作成功！');
			history.go(-1);
		}
	</script>
</@body>