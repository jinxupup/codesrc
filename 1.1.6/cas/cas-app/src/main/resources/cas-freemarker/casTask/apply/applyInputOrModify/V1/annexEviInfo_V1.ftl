<#assign fieldsetMap = {'B1':'附件核实情况','B2':'房产状况','B3':'他行卡状况','B4':'车产状况'}>
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