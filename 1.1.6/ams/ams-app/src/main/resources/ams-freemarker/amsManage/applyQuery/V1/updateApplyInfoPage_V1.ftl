<#include "/layout.ftl"/>
<#include "../../../apply/common/ajaxSelect.ftl"/>
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
	<#include "../../../apply/common/pageCommonJs.ftl"/>
	<#--领卡方式的改变js-->
	<#include "../../../apply/common/cardFetchJs.ftl"/>
	<@panel head="申请件信息修改【${(appNo)!}】${(oldAppNo?? && oldAppNo!='')?string('【重审件】','')}${(returnFlag?? && returnFlag!='')?string('【${(returnFlag)!}】','')}">
		<@panelBody>
			<@form id="applyInputForm">
				<@field hidden="true">
  					<@input name="tmAppMain.appNo" value="${(appNo)!}"/>
  				</@field>
				<@tab style="height:380px;overflow-y:scroll;">
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
			    	<@tabContent style="width:98.5%;">
			    		<#if appType??>
			    	 		<#if appType == "A" || appType == "B" >
			        			<@tabPane id="pane1" active="true">
									<#include "../../../apply/applyInputOrModify/V1/mainCardInfo_V1.ftl"/>
								</@tabPane>
								<@tabPane id="pane2" >
				     				<#include "../../../apply/applyInputOrModify/V1/annexEviInfo_V1.ftl"/>
				        		</@tabPane>
				        		<@tabPane id="pane3" >
				     				<#include "../../../apply/applyInputOrModify/V1/mainCardOtherInfo_V1.ftl"/>
				        		</@tabPane>
							<#elseif appType == "S">
								<@tabPane id="pane1" active="true">
									<#include "../../../apply/applyInputOrModify/V1/attachCardInfo_V1.ftl"/>
								</@tabPane>
								<@tabPane id="pane3" >
				     				<#include "../../../apply/applyInputOrModify/V1/attachCardOtherInfo_V1.ftl"/>
				        		</@tabPane>
			        		</#if>
			        	</#if>
			   		</@tabContent>
		 		</@tab>
				<#include "../../../apply/common/applyCreditInfoBut_V1.ftl"/>
		 		<@fieldset legend="审批结果信息">
		        	<@row>
						<@field label="评分值:" field_ar="6" label_ar="14" input_ar="22">
		             		<@input value="${(tmAppMain.pointResult)!}" label_only="true"/>
		        		</@field>
		   	 			<@field label="系统建议额度:" field_ar="7" label_ar="14" input_ar="22">
		             		<@input value="${(tmAppMain.sugLmt)!}" label_only="true"/>
		        		</@field>
		        		<@field label="附件建议额度:" field_ar="7" label_ar="14" input_ar="22">
				     		<@input value="${(applyOperateDto.annexEviLmt?string('#.##'))!}" label_only="true"/>
						</@field>
						<@field label="快速审批标志" field_ar="8" label_ar="14" input_ar="22">
				           <@dictSelect dicttype="Indicator" value="${(applyOperateDto.approveQuickFlag)!'N'}" readonly="true"/>
				        </@field>
				        <@field label="优先处理标志" field_ar="8" label_ar="14" input_ar="22">
				           <@dictSelect dicttype="Indicator" value="${(applyOperateDto.isPriority)!'N'}" readonly="true"/>
				        </@field>
					</@row>
		   	 		<@row>
		   	 			<@field label="初审结果:" field_ar="9" label_ar="12" input_ar="24">
		   	 				<@dictSelect dicttype="BasicCheckResult" value="${(applyNodeInquiryData.checkResult)!}" label_only="true"/>
		        		</@field>
		        		<#if applyNodeInquiryData?? && applyNodeInquiryData.checkResult?? && applyNodeInquiryData.checkResult=='R'>
			        		<@field id="refuseCodeDiv" label="初审拒绝原因"  field_ar="9" label_ar="12" input_ar="24">
					     		<@multipleSelect options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
									'{"type":"ApplyRejectReason"}','code','codeName') value="${(checkRefuseCodes)!}" showfilter="true" label_only="true"/>
							</@field>
						</#if>
						<#if appType?? && appType == "A">
							<@field label="是否拒绝附属卡" field_ar="9" label_ar="12" input_ar="24">
					     		<@multipleSelect name="ifAttachRefuse" value="${(ifAttachRefuse)!''}" showfilter="true">
								    <#if attachNoMap??>
								    	<#list attachNoMap?keys as key>
								    		<@option key="${(key)}" select_value="${(ifAttachRefuse)!''}" showcode="false">是否拒绝<#if attachNum?? && attachNum != 0>第${(key)}张</#if>附卡-${attachNoMap[key]!}</@option>
								    	</#list>
								    </#if>
								</@multipleSelect>
							</@field>
						</#if>
		   	 		</@row>
		   	 		<@row>
		   	 			<@field label="终审结果:" field_ar="9" label_ar="12" input_ar="24">
							<@dictSelect dicttype="FinalAuditResult" value="${(applyNodeFinalAuditData.finalResult)!}" label_only="true"/>
		             	</@field>
		             	<#if applyNodeFinalAuditData?? && applyNodeFinalAuditData.finalResult?? && applyNodeFinalAuditData.finalResult == "R">
		             		<@field label="终审拒绝原因" field_ar="9" label_ar="12" input_ar="24">
		             			<@multipleSelect options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict',
									'{"type":"ApplyRejectReason"}','code','codeName') value="${(finalRefuseCodes)!}" showfilter="true" label_only="true"/>
	        				</@field>
	        			<#else>
	        				<@field label="核准额度" field_ar="9" label_ar="12" input_ar="24">
			             		<@input id="accLmt" name="tmAppMain.accLmt" value="${(tmAppMain.accLmt)!}"/>
			        		</@field>
			        		<@field label="大写标准额度" field_ar="9" label_ar="12" input_ar="24">
			             		<@input id="bigLimit" value="${(bigLimit)!}" readonly="true"/>
			        		</@field>
	        			</#if>
		   	 		</@row>
		   	 		<@row>
		   	 			<@field label="复核人备注:" field_ar="36" label_ar="3" input_ar="33">
							<@textarea value="${(REMARKAMS_APPLY_REVIEW.memoInfo)!}" rows="1" readonly="true" style="margin-bottom:5px"/>
						</@field>
					</@row>
		   	 		<@row>	
		   	 			<@field label="初审备注:" field_ar="36" label_ar="3" input_ar="33">
							<@textarea value="${(REMARKCAS_APPLY_BASIC_CHECK.memoInfo)!}" rows="1" readonly="true" style="margin-bottom:5px"/>
						</@field>
					</@row>
		   	 		<@row>	
		   	 			<@field label="电话调查备注:" field_ar="36" label_ar="3" input_ar="33">
							<@textarea value="${(REMARKCAS_APPLY_TEL_SURVEY.memoInfo)!}" rows="1" readonly="true" style="margin-bottom:5px"/>
						</@field>
					</@row>
		   	 		<@row>	
		   	 			<@field label="终审备注:" field_ar="36" label_ar="3" input_ar="33">
							<@textarea value="${(REMARKCAS_APPLY_FINALAUDIT.memoInfo)!}" rows="1" readonly="true" style="margin-bottom:5px"/>
						</@field>
		   	 		</@row>
 				</@fieldset>
	 		</@form>
		</@panelBody>
	</@panel>
	<@toolbar align="center" style="margin:0px; padding:0px; height:35px;">
		<@button click="saveBtn" name="保存***" fa="save"/>
		<@backButton name="返回" fa="undo"/>
		<@buttonAuth code="AMS_APPLY_INNER_BANKCUSTNO">
			<@button click="recallInnerBankCustNo" name="重调行内客户号"/>
		</@buttonAuth>
	</@toolbar>
	<script type="text/javascript">
		//初始化
		$(function(){
			$('#accLmt').bind("keyup",accLmtKeyUp);
			
		});
	
		<#--保存操作-->
		var saveBtn = function(){
			$.ajax({
				type: "POST",
				dataType : "json",			 
				data:$('#applyInputForm').serialize(),
				url: "${base}/ams_applyProcess/applyModifyInfoSave",
				success: function(ref){
					alert(ref.msg); 
				}
			});
		}
		<#--重新调用获取客户号-->
		var recallInnerBankCustNo = function(){
			$.ajax({
				type: "POST",
				dataType : "json",
				data: {'appNo':'${(appNo)!}'},
				url: "${base}/ecif/getInnerBankCustomerNo",
				success: function(ref){
					alert(ref.msg); 
				}
			});
		}
		<#--数字转化金额大写-->
		function accLmtKeyUp(){
			var accLmt = $(this).val();
			if(accLmt != ''){
		        var unit = "千百拾亿千百拾万千百拾元角分", str = "";
		        accLmt += "00";
		        var p = accLmt.indexOf('.');
		        if (p >= 0){
		        	accLmt = accLmt.substring(0, p) + accLmt.substr(p+1, 2);
		        }     
		        unit = unit.substr(unit.length - accLmt.length);
		        for (var i=0; i < accLmt.length; i++){
		            str += '零壹贰叁肆伍陆柒捌玖'.charAt(accLmt.charAt(i)) + unit.charAt(i);
		        }
		        str = str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
				$('#bigLimit').val(str);
			}else{
				$('#bigLimit').val('');
			}
		}	
	 </script>
</@body>