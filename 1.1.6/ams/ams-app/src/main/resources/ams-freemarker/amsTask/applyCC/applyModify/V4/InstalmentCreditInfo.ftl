<#-- 分期信息页面 -->
<@fieldset legend="标准大额分期信息:" >
	<@row>
		<@field label="活动号" point_flag="false" point="*">
  			<@select nullable="true"  change="judgeTimeAndStatus(this)" options=ams_('tableMap','instalmentCreditActivityNo','${(tmAppMain.productCd)!}')  id="instalmentCreditActivityNo" value="${(tmAppInstalLoan.instalmentCreditActivityNo)!}" name="tmAppInstalLoan.instalmentCreditActivityNo" />
  		</@field>
  		<@field label="商户号" point_flag="false" point="*">
  		 	<@selectLink nullable="true"   options=ams_('tableMap','mccNoWithPra','${(tmAppInstalLoan.instalmentCreditActivityNo)!}') append="true" id="mccNo" link_id="instalmentCreditActivityNo" value="${(tmAppInstalLoan.mccNo)!}" url_parent_key="instalmentCreditActivityNo" url="applyInput/businessNo" keyfield="id" valuefield="show" name="tmAppInstalLoan.mccNo" />
  		</@field>
  		<@field label="分期借款金额" point_flag="false" point="*">
  			<@input id="cashAmt" name="tmAppInstalLoan.cashAmt"  value="${(tmAppInstalLoan.cashAmt)!}" />
  		</@field>
  		
  	</@row>
  	<@row>
  		<@field label="分期期数" point_flag="false" point="*">
  		 	<@selectLink nullable="true"  options=ams_('tableMap','terms','${(tmAppInstalLoan.instalmentCreditActivityNo)!}') append="true" id="loanInitTerm" link_id="instalmentCreditActivityNo" value="${(tmAppInstalLoan.loanInitTerm)!}" url_parent_key="instalmentCreditActivityNo" url="applyInput/getTerms" keyfield="show" valuefield="show" showcode="false" name="tmAppInstalLoan.loanInitTerm" />
  		</@field>
  		<@field label="客户级专项分期额度" point_flag="false" point="*" hidden="true">
  			<@input id="customerSpecialQuota" name="tmAppInstalLoan.customerSpecialQuota" value="${(tmAppInstalLoan.customerSpecialQuota)!}" />
  		</@field>
  		
  		<#if (tmAppInstalLoan.loanFeeMethod)??>
  			<@field label="分期手续费收取方式" hidden="false" >
  				<@dictSelect dicttype="LoanFeeTetchMethod" id="loanFeeMethod" name="tmAppInstalLoan.loanFeeMethod" nullable="true" value="${(tmAppInstalLoan.loanFeeMethod)!}" />
  			</@field>
  		<#else>
  			<@field label="分期手续费收取方式" hidden="${(ifNotUseCustomRate)!'true'}">
  				<@dictSelect dicttype="LoanFeeTetchMethod" id="loanFeeMethod"  name="tmAppInstalLoan.loanFeeMethod" nullable="true" value="${(tmAppInstalLoan.loanFeeMethod)!}" />
  			</@field>
  		</#if>
  		
  	</@row>
  	<@row>
  		<#if (tmAppInstalLoan.loanFeeCalcMethod)??>
  			<@field label="大额分期手续费计算方式" hidden="false">
  				<@dictSelect dicttype="LoanFeeCalcMethod" change="changeMethod"  id="loanFeeCalcMethod" name="tmAppInstalLoan.loanFeeCalcMethod" nullable="true" value="${(tmAppInstalLoan.loanFeeCalcMethod)!}" />
  			</@field>
  		<#else>
  			<@field label="大额分期手续费计算方式" hidden="${(ifNotUseCustomRate)!'true'}" >
  				<@dictSelect dicttype="LoanFeeCalcMethod" change="changeMethod"  id="loanFeeCalcMethod" name="tmAppInstalLoan.loanFeeCalcMethod" nullable="true" value="${(tmAppInstalLoan.loanFeeCalcMethod)!}" />
  			</@field>
  		</#if>
  		
  		<#if (tmAppInstalLoan.appFeeRate)?? || ((tmAppInstalLoan.loanFeeCalcMethod)?? && (tmAppInstalLoan.loanFeeCalcMethod) = "R")>
  			<@field label="手续费费率" id="appFeeRateA" hidden="false">
  				<@input id="appFeeRate" name="tmAppInstalLoan.appFeeRate"  value="${(tmAppInstalLoan.appFeeRate?c)!}" />
  			<#-- 	<@input id="exp" class="col-ar-3" value="例:0.1234" label_only="true" style="color:#989898" />-->
  			</@field>
  			<div id="exp">
  				<@input class="col-ar-3" value="例:0.1234" label_only="true" style="color:#989898" />
  			</div>
  		<#else>
  			<@field label="手续费费率" id="appFeeRateA" hidden="true">
  				<@input id="appFeeRate" name="tmAppInstalLoan.appFeeRate"  value="${(tmAppInstalLoan.appFeeRate?c)!}" />
  			<#--	<@input id="exp" class="col-ar-3" value="例:0.1234" label_only="true" style="color:#989898" />-->
  			</@field>
  			<div id="exp" style="display:none">
  				<@input class="col-ar-3" value="例:0.1234" label_only="true" style="color:#989898" />
  			</div>
  		</#if>
  		
  		<#if (tmAppInstalLoan.appFeeAmt)?? || ((tmAppInstalLoan.loanFeeCalcMethod)?? && (tmAppInstalLoan.loanFeeCalcMethod) = "A")>
  			<@field label="手续费固定金额" id="appFeeAmtA" hidden="false">
  				<@input id="appFeeAmt" name="tmAppInstalLoan.appFeeAmt" value="${(tmAppInstalLoan.appFeeAmt?c)!}" />
  			</@field>
  		<#else>
  			<@field label="手续费固定金额" id="appFeeAmtA" hidden="true">
  				<@input id="appFeeAmt" name="tmAppInstalLoan.appFeeAmt"  value="${(tmAppInstalLoan.appFeeAmt?c)!}" />
  			</@field>
  		</#if>
  		
  		
  		
  		<#-- 用途
  		<@field label="贷款" >
  			<@input name="tmAppInstalLoan.loanUse" value="${(tmAppInstalLoan.loanUse)!}"/>
  		</@field>
  		<@field label="营销人员姓名" >
  			<@input name="tmAppInstalLoan.marketerName" value="${(tmAppInstalLoan.marketerName)!}"/>
  		</@field>
  		<@field label="营销人员编号" >
  			<@input name="tmAppInstalLoan.marketerId" value="${(tmAppInstalLoan.marketerId)!}"/>
  		</@field>
  		<@field label="营销人员所属分行" >
  			<@input name="tmAppInstalLoan.marketerBranchId" value="${(tmAppInstalLoan.marketerBranchId)!}"/>
  		</@field>
  		-->
	</@row>
</@fieldset>
<script>
	$(function(){
	<#-- 	var loanFeeCalcMethod = $("#loanFeeCalcMethod").val();
		if(loanFeeCalcMethod=="R"){
			$("#appFeeRateA").show();
			$("#appFeeAmtA").hide();
			$("#loanFeeCalcMethodA").show();
			$("#loanFeeMethodA").show();
		}else if(loanFeeCalcMethod=="A"){
			$("#appFeeRateA").hide();
			$("#appFeeAmtA").show();
			$("#loanFeeCalcMethodA").show();
			$("#loanFeeMethodA").show();
		}else if(loanFeeCalcMethod==" "){
			$("#appFeeRateA").hide();
			$("#appFeeAmtA").hide();
		}-->
<#-- 	var productCd = $("#productCd").val();
		var appNo = '${(tmAppMain.appNo)!}';
		var activityNo = '${(tmAppInstalLoan.instalmentCreditActivityNo)!}';
			$.ajax({
				type: 'POST',
	     		dataType: 'json',
	     		data: {'productCd':productCd},
				url: '${base}/applyInput/getAll',
				success:function(res){
					var object = res.obj;
					if(object.activityid!=null){
						$("#instalmentCreditActivityNo").append("<option value = '"+object.activityid+"'>"+object.activityid+"-"+object.activityname+"</option>");
					}
					if(object.businessid!=null){
						$("#mccNo").append("<option value = '"+object.businessid+"'>"+object.businessid+"-"+object.businessname+"</option>");
					}
					if(object.terms!=null){
						$("#loanInitTerm").append("<option value = '"+object.terms+"'>"+object.terms+"-"+object.terms+"</option>");
	    			}
	    			if(object!=null){
	    				//var obj = object.objList;
	    				for(var i=0;i<object.length;i++){
	    					var a = object[i];
	    					if(a.id!=activityNo){
	    					$("#instalmentCreditActivityNo").append("<option value = '"+a.id+"'>"+a.id+"-"+a.show+"</option>");
	    					}
	    				}
	    			}
	    		}
			});
			-->
	}
	)
<#-- function changes(){
		var productCd = $("#productCd").val();
		$.ajax({
				type: 'POST',
	     		dataType: 'json',
	     		data: {'productCd':productCd},
				url: '${base}/applyInput/getAll',
				success:function(res){
					var object = res.obj;
					if(object.activityid!=null){
						$("#instalmentCreditActivityNo").append("<option value = '"+object.activityid+"'>"+object.activityid+"-"+object.activityname+"</option>");
					}
					if(object.businessid!=null){
						$("#mccNo").append("<option value = '"+object.businessid+"'>"+object.businessid+"-"+object.businessname+"</option>");
					}
					if(object.terms!=null){
						$("#loanInitTerm").append("<option value = '"+object.terms+"'>"+object.terms+"-"+object.terms+"</option>");
	    			}
	    			if(object!=null){
	    				//var obj = object.objList;
	    				$("#instalmentCreditActivityNo").empty();
	    				$("#instalmentCreditActivityNo").append("<option ></option>"); 
	    				for(var i=0;i<object.length;i++){
	    					var a = object[i];
	    					$("#instalmentCreditActivityNo").append("<option value = '"+a.id+"'>"+a.id+"-"+a.show+"</option>");
	    				}
	    			}
	    		}
			});
	}-->

	function changeMethod(){
		var loanFeeCalcMethod = $("#loanFeeCalcMethod").val();
		if(loanFeeCalcMethod=="R"){
			$("#appFeeRateA").show();
			$("#appFeeAmt").val("");
			$("#appFeeAmtA").hide();
			$("#exp").css('display',''); 
		}else if(loanFeeCalcMethod=="A"){
			$("#appFeeRate").val("");
			$("#appFeeRateA").hide();
			$("#appFeeAmtA").show();
			$("#exp").css('display','none'); 
		}else{
			$("#appFeeRateA").hide();
			$("#appFeeAmtA").hide();
			$("#appFeeRate").val("");
			$("#appFeeAmt").val("");
			$("#exp").css('display','none'); 
		}
	}
	
	function judgeTimeAndStatus(obj){
		var instalmentCreditActivityNo = $("#instalmentCreditActivityNo").val();
		var productCd = $("#productCd").val();
		if($(obj).val()!=""){
			$.ajax({
				type: 'POST',
	     		dataType: 'json',
	     		data: {'ActivityNo':instalmentCreditActivityNo,'productCd':productCd},
				url: '${base}/applyInput/judgeTimeAndStatus',
				success:function(res){
					if(res.code=="0"){
						alert(res.msg)
						$(obj).val("");
					}
				}
			});
		}
	}
	<#-- 
	function judgeAmt(obj){
		var cashAmt = $("#cashAmt").val();
		var loanInitTerm = $("#instalmentCreditActivityNo").val();
		var productCd = $("#productCd").val();
		var instalmentCreditActivityNo = $("#instalmentCreditActivityNo").val();
		var loanFeeMethod = $("#loanFeeMethod").val();
		alert("xixi");
		$.ajax({
				type: 'POST',
	     		dataType: 'json',
	     		data: {'cashAmt':cashAmt,'loanInitTerm':loanInitTerm,'productCd':productCd,'activityNo':instalmentCreditActivityNo,'loanFeeMethod':loanFeeMethod},
				url: '${base}/applyInput/judgeAmt',
				success:function(res){
				if(res.code=='0'){
					alert(res.msg);
					$("obj").val("");
				}
	    		}
			});
	}-->
	
<#--	function instalmentCreditActivityNo(){
		$.ajax({
			type: 'POST',
     		dataType: 'json',
     		data: {'appNo':appNo,'productCd':productCd},
			url: '${base}/applyInput/getAllC',
			success:function(res){
				var object = res.obj;
				if(object.activityid!=null){
					//document.getElementById("instalmentCreditActivityNo").setAttribute("nullable","false");
					$("#instalmentCreditActivityNo").append("<option value = '"+object.activityid+"'>"+object.activityid+"-"+object.activityname+"</option>");
				}
				if(object.businessid!=null){
					$("#mccNo").append("<option value = '"+object.businessid+"'>"+object.businessid+"-"+object.businessname+"</option>");
				}
				if(object.terms!=null){
					$("#loanInitTerm").append("<option value = '"+object.terms+"'>"+object.terms+"-"+object.terms+"</option>");
    			}
    			if(object.list!=null){
    				var objList = object.list;
    				for(var i=0;i<objList.length;i++){
    					var a = objList[i];
    					$("#instalmentCreditActivityNo").append("<option value = '"+a.activityid+"'>"+a.activityid+"-"+a.activityname+"</option>");
    				}
    			}
    		}
		});
	
	}	
-->
</script>
