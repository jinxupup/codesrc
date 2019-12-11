
<@form id="applyPreInputAddForm" action="ams_applyPreInput/applyPreInputAdd" before="validProductInfo" after="after">
     <@row style="display: none;">
           <@field hidden="true">
               <@input id="appNo" name="appNo" value="" />
           </@field>                  		             
     </@row> 
     <@row>
           <@field label="申请类型" point_flag="true" point="*">
                <@dictSelect id="appType" change="appTypeChange" dicttype="AppType" nullable="false" name="tmAppMain.appType" value="B" valid={"notempty":"true"} /> 
           </@field>
           <@field id="productCdLable" label="申请卡产品" point_flag="true" point="*">
				<@select id="productCd" change="productCdChange" name="tmAppMain.productCd" value="" options=ams_('tableMap','productForStatus','AC','C') valid={"notempty":"true"}/>
           </@field>
      </@row>
      <@row>      
            <@field label="姓名" point_flag="true" point="*">
                 <@input id="name" name="name" value="" valid={"notempty":"true"}/>
            </@field>
            <@field label="证件类型" point_flag="true" point="*">
                 <@dictSelect id="idType" dicttype="IdType" change="idTypeChange" nullable="false" name="idType" value="I" valid={"notempty":"true"} />   
            </@field>
      </@row>
      <@row>
      		<@field label="证件号码" point_flag="true" point="*" >
                  <@input id="idNo" name="idNo" value="" valid={"notempty":"true"} />
            </@field>
            <@field label="手机号码" point_flag="true" point="*">
                  <@input name="cellphone" value=""  valid={"notempty":"true","regexp or pattern":"^1[3456789]\\d{9}$","regexp-message":"请输入有效的手机号"}/>
            </@field>    
      </@row>
      <@row id="applyAttach">
			<@field label="与主卡持卡人关系" point_flag="true" point="*">
				<@dictSelect dicttype="ContactRelation" showcode='false' name="relationshipToBsc" value="" valid={"notempty":"true"} />
			</@field>    
			<@field label="主卡卡号" point_flag="true" point="*">
				<@input id="primCardNo" name="primCardNo" value="" valid={"notempty":"true","stringlength":"true","stringlength-max":"20","stringlength-message":"请填写正确的身份证号/主卡卡号"}/>
			</@field>                    
	  </@row>
	  <#if pageOnOffParamDto?? && pageOnOffParamDto.isUseInputManageUser?? && pageOnOffParamDto.isUseInputManageUser == 'Y'><#--进件管理员开关参数-->
		  <@row>
				<@field label="进件管理员" point_flag="false" point="*">
					<@input name="tmAppPrimCardInfo.inputManageUser" value="" valid={"notempty":"false","stringlength":"true","stringlength-max":"40","stringlength-message":"请填写正确的进件管理员编号或姓名"}/>
				</@field>    
		  </@row>
	  </#if>
      <@toolbar align="center" style="margin-right:25%;">
            <@submitButton name="确定" style="margin-right:10px"/>
		    <@button  name="清空" style="margin-left:10px" click="cleanFieldBtn"/>
	  </@toolbar>
</@form>
<@row id="historylist" style="display:none">
	<#include "applyHistoryList.ftl"/>
</@row>

<script type="text/javascript">
    var cleanFieldBtn = function () {
        window.location.href= '${base}/ams_applyPreInput/ams_applyPreInputpage';
    }
 		$(function(){
 			$('#applyAttach').css("display","none");
 		});
 		<#--证件类型的改变，清空证件号-->
 		var idTypeChange = function(){
 			$('#idNo').val('');
 			$("#applyPreInputAddForm").data('bootstrapValidator').updateStatus('idNo','NOT_VALIDATED');
 		}
 		<#--申请类型与卡产品的联动-->
 		var appTypeChange = function(){
 			var appType = $('#appType').val();
 			if(appType == 'S'){
 				$('#productCd').val('');
 				$("#applyPreInputAddForm").data('bootstrapValidator').updateStatus('tmAppMain.productCd','NOT_VALIDATED');
 				$('#productCdLable').css("display","none");
 				$('#applyAttach').css("display","block");
 			}
 			else{
 				$('#productCdLable').css("display","block");
 				$('#applyAttach').css("display","none");
 				
 				if(appType == 'A'){
					productCdChange();
	 			}
 			}
 		}
 		
 		<#--公务卡不能申请附卡-->
 		var productCdChange = function(){
 			var appType = $('#appType').val();
 			var productCd = $('#productCd').val();
 			if(appType == 'A' && productCd != ''){
				$.ajax({
		     		type: 'POST',
		     		async:false,
					url: '${base}/ams_applyInput/getTmProduct',
					data: {'productCd':productCd},
					dataType: 'json',
					success:function(res){
						if(res.obj.ifEnableAttachCard == 'N'){
							alert("该卡产品不能申请附卡！");
 							$('#productCd').val('');
 							$("#applyPreInputAddForm").data('bootstrapValidator').updateStatus('tmAppMain.productCd','NOT_VALIDATED');
						}
		    		} 
				});
			}
 		}
 		
 		/**
 		/*主卡重复申请校验
 		**/
 		var validProductInfo = function(){
 			var idNo = $('#idNo').val();
 			var productCd = $('#productCd').val();
 			var name = $('#name').val();
 			var idType= $('#idType').val();
 			if(idNo == null || idNo == '' || idNo == undefined){
 				alert("证件号码为空");
 				return false;
 			}
 			if(productCd == null || productCd == '' || productCd == undefined){
 				alert("申请卡产品为空");
 				return false;
 			}
			if(name == null || name== '' || name== undefined){
				alert("姓名不能为空");
				return false;
			}
			if(idType == null || idType== '' || idType== undefined){
				alert("证件类型不能为空");
				return false;
			}

 			var appType = $('#appType').val();
 			if(appType == 'A' || appType == 'B'){
 				$.ajax({
		     		type: 'POST',
		     		async:false,
					url: '${base}/ams_applyPreInput/reApplyOfProduct' ,
					data: {"idNo":idNo,"productCd":productCd,"idType":idType,"name":name},
					dataType: 'json',
					success:function(res){
						if(res.s){
							return true;
						}else{
							if(confirm(res.msg+"，是否继续录入？")){
								return true;
							}else{
								return false;
							}
						}
					}
				});
 			}
 		}
 						
 		var after = function(res){
 			alert(res.msg);
 			if(res.s){
 				var appNo = res.obj.appNo;
	 			var appType = $('#appType').val();
	 			var idType = $('#idType').val();
	 			var idNo = $('#idNo').val();
				$('#appNo').val(appNo);
				$('#historylist').css("display","block");
				var his_params = {url:ar_.randomUrl("${base}/ams_applyPreInput/historyApplyList?idType="+idType+"&idNo="+idNo+"&appNo="+appNo)};
				$("#historyInfo").bootstrapTable("refresh",his_params);
 				<#if pageOnOffParamDto?? && pageOnOffParamDto.isUsedIdCheck?? && pageOnOffParamDto.isUsedIdCheck == 'Y'>
 					<#--目前只有九江启用身份核身，其他行停用-->
 					idCheckNciic(true);
				</#if>
 			}
 		}
 		
 		<#--继续录入按钮-->
		var continueInput = function(){
 			var appNo = $('#appNo').val();
 			window.location.href="${base}/ams_activiti/handleTask?appNo="+ appNo;
 		}
 		
 		<#--身份证校验-->
 		function idNoValid(idNo) {
             var flag = false;
	     	 $.ajax({
	     		type: 'POST',
	     		async:false,
				url: '${base}/ams_applyInput/idNoValid' ,
				data: {"idNo":idNo},
				dataType: 'json',
				success:function(res){
					flag = res.s;
					if(flag == false){
						alert("身份证号码不正确！");
					}
				}
			});
			
			return flag;
        }
        
        <#--身份证格式验证-->
        $('#idNo').blur(function(){
        	var idType = $('#idType').val();
        	if(idType == 'I'){
        		var idNo = $('#idNo').val();       	
        		if(idNo != ''){
        			if(! idNoValid(idNo)){
        				$('#idNo').val('');
						$("#applyPreInputAddForm").data('bootstrapValidator').updateStatus('idNo','NOT_VALIDATED');
        			}
        		}
        	}
        });
         <#--主卡卡号验证-->
         $('#primCardNo').blur(function(){
         	var primCardNo = $('#primCardNo').val();
         	if(primCardNo != ''){
				$.ajax({
     			type: 'POST',
     			async:false,//同步验证
			    url: '${base}/ams_applyPreInput/primCardNoValide?primCardNo='+primCardNo ,
			    data: {},
			    dataType: 'json',
			    success:function(res){
				    if(res.msg != 'true'){//true表示卡号/身份证号正确
				    	if(res.msg == 'S'){
					     	alert("附卡不能申请独立附卡！");
					     	$('#primCardNo').val('');
					     	$("#applyPreInputAddForm").data('bootstrapValidator').updateStatus('primCardNo','NOT_VALIDATED');
					    }else if(res.msg == 'V'){
					    	alert("主卡卡号无效！");
					    	$('#primCardNo').val('');
					    	$("#applyPreInputAddForm").data('bootstrapValidator').updateStatus('primCardNo','NOT_VALIDATED');
					    }else if(res.msg == 'B'){
					    	alert("主卡卡产品无效！");
					    	$('#primCardNo').val('');
					    	$("#applyPreInputAddForm").data('bootstrapValidator').updateStatus('primCardNo','NOT_VALIDATED');
					    }else if(res.msg == 'O'){
					    	alert("公务卡不能申请独立附卡！");
					    	$('#primCardNo').val('');
					    	$("#applyPreInputAddForm").data('bootstrapValidator').updateStatus('primCardNo','NOT_VALIDATED');
					    }else if(res.msg == 'IdNo'){//如果填写的是主卡身份证号(默认是主卡卡号)，提交前更改name属性
				    		$('#primCardNo').attr('name','pIdNo');//pIdNo不要更改，提交到后台有用到
				    	}else{
					     	alert("未检索到有效的主卡持卡人信息,请核实主卡账户和卡片状态！");
					     	$('#primCardNo').val('');
					     	$("#applyPreInputAddForm").data('bootstrapValidator').updateStatus('primCardNo','NOT_VALIDATED');
					    }    
				    }  
    			 } 
			  });
			}
         });     	
</script>	