<#if appType?? && appType != "S">
	
	<@buttonAuth code="AMS_BANK_REPORT">
		<@button fa="search" id="queryCreditReport" name="客户人行报告" style="margin-left:5px;margin-right:5px;"/>
	</@buttonAuth>
	<@buttonAuth code="AMS_BANK_REPORT_NET">
		<@button fa="search" id="pbocNetworkReportBtn" name="实时人行报告" style="margin-left:5px;margin-right:5px;"/>
	</@buttonAuth>
	<script language="JavaScript">
		var isYkBank='${(isYkBank)!}';
		var formKey='${(formKey)!}';
		var pbocResult='${(tmAppPrimCreditResult.pbocResult)!}';
		if(isYkBank!=null && isYkBank=='Y'){
		
			//电调环节所有人行按钮均不可见
			if(formKey==null || formKey=='application-telephonesurvey' || formKey=='applyProcessQueryDetailPage'){
				$('#queryCreditReport').css('display','none');
				$('#pbocNetworkReportBtn').css('display','none');
			}else if(formKey==null || formKey=='application-finalaudit'){
				//终审环节实时查询人行按钮均不可见
				$('#pbocNetworkReportBtn').css('display','none');
			}
			if(pbocResult!=null && pbocResult!='su002' && formKey!=null && formKey=='application-check'){
				$('#pbocNetworkReportBtn').css('display','false');
			}else{
				$('#pbocNetworkReportBtn').css('display','none');
			}
		}
	</script>
</#if>
<#-- 客户征信报告div，默认隐藏 -->
<#include "../commonDialog/creditReport/pbocReport.ftl"/><#-- 客户征信报告div，默认隐藏 -->