<#assign fieldsetMap = {'B1':'个人证明信息','B2':'收入证明信息','B3':'个人养老保险证明信息','B4':'个人住房公积金证明信息','B5':'我行存款证明信息','B6':'我行理财证明信息',
	'B7':'我行贷款证明信息','B8':'个人房产证明信息','B9':'企业法人证明信息','B10':'个体工商户证明信息','B11':'个人车产证明信息','B12':'居住证明证明信息'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldSet.ftl"/>
</#list>
<script type="text/javascript">
	$(function(){
		$('#financeTime').bind('focus',fillFinanceTime);
	});
	<#--计算理财产品期限-->
	function fillFinanceTime(){
		var financeBuyDate = $('#financeBuyDate').val();
		var obDate1 = $('#obDate1').val();//理财产品到期日期
		if(financeBuyDate != '' && obDate1 != ''){
			var oDate1,oDate2,iDays;
			financeBuyDate  =  financeBuyDate.split("-");
	        oDate1  =  new  Date(financeBuyDate[1] + '-' + financeBuyDate[2] + '-' + financeBuyDate[0]);//转换为MM-dd-YYYY格式 
	        obDate1  =  obDate1.split("-");
	        oDate2  =  new  Date(obDate1[1] + '-' + obDate1[2] + '-' + obDate1[0]);
	        iDays  =  parseInt(Math.abs(oDate1 - oDate2)/1000/60/60/24);//把相差的毫秒数转换为天数
	        $('#financeTime').val(iDays);
		}
		console.log(iDays);
	}
</script>