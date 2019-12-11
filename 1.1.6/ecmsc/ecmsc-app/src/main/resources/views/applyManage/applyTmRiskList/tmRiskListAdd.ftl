<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panel head="${isEdit?string('编辑风险名单','添加风险名单')}">
		<@panelBody>
			<@form id="queryForm" action="${isEdit?string('tmRiskList/edit','tmRiskList/add')}" success_url="tmRiskList/tmRiskList">
     			<@field hidden="true">
	     			 <@input name="id" value="${(tmRiskList.id)!}"  valid={"notempty":"true"} />
	      		</@field>
     			<@row>
			          <@field label="风险名单来源">
			          		<@dictSelect name="risklistSrc" dicttype="RiskListSource" value="${(tmRiskList.risklistSrc)!}" valid={"notempty":"true"}/>
			          </@field>
			          <@field label="证件类型">
			                <@dictSelect id="idType" change="idTypeChange" name="idType" dicttype="IdType" value="${(tmRiskList.idType)!}" valid={"notempty":"true"}/>
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="证件号码">
			                <@input id="idNo" name="idNo" value="${(tmRiskList.idNo)!}" valid={"notempty":"true"}/>
			          </@field>
			          <@field label="姓名">
			                <@input name="name" value="${(tmRiskList.name)!}"
			                	valid={"stringlength":"true","stringlength-max":"10","stringlength-message":"请正确填写姓名"}/>
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="移动电话">
			                <@input id="cellPhone" name="cellPhone" value="${(tmRiskList.cellPhone)!}"
			                	valid={"regexp or pattern":"^1[3456789]\\d{9}$","regexp-message":"请输入有效的手机号","notempty":"true"} />
			          </@field>
			          <@field label="家庭电话">
			                <@input name="homePhone" value="${(tmRiskList.homePhone)!}"
			                	valid={"regexp or pattern":"^((0\\d{2,3}\\-\\d{6,8}(\\-\\d{1,6})?)|(1[3456789]\\d{9}))$","regexp-message":"请输入有效的电话号码"}/>
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="家庭地址">
			                <@input name="homeAdd" value="${(tmRiskList.homeAdd)!}"/>
			          </@field>
			          <@field label="公司名称">
			                <@input name="corpName" value="${(tmRiskList.corpName)!}"
			                	valid={"stringlength":"true","stringlength-max":"80","stringlength-message":"请正确填写公司名称"}/>
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="公司电话">
			                <@input name="empPhone" value="${(tmRiskList.empPhone)!}"
			                	valid={"regexp or pattern":"^((0\\d{2,3}\\-\\d{6,8}(\\-\\d{1,6})?)|(1[3456789]\\d{9}))$","regexp-message":"请输入有效的公司电话"}/>
			          </@field>
			          <@field label="公司地址">
			                <@input name="empAdd" value="${(tmRiskList.empAdd)!}"/>
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="记录有效期">
			                <@date name="validDate" value="${(tmRiskList.validDate?date)!'2099-01-01'}" />
			          </@field>
			          <@field label="风险名单类型">
			                <@dictSelect name="actType" dicttype="ActType" value="${(tmRiskList.actType)!}" valid={"notempty":"true"}/>
			          </@field>
			      </@row>
			      <@row>
			          <@field label="电子邮箱">
			                <@input name="obText1" value="${(tmRiskList.obText1)!}" 
			                	valid={"regexp or pattern":"^([a-zA-Z0-9_\\.]+@[a-z0-9]+\\.[a-z]+(\\.[a-z]+)?)$","regexp-message":"请输入有效的邮箱地址"}/>
			          </@field>
			      </@row>
			      <@row>
				      <@field label="上风险名单原因说明" label_ar="6" field_ar="24" input_ar="30">
			          	    <@textarea name="reason" value="${(tmRiskList.reason)!}" rows="2" cols="" />
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="备注" label_ar="6" field_ar="24" input_ar="30">
			                <@textarea name="memo" value="${(tmRiskList.memo)!}" rows="2" cols=""/>
			          </@field>
			      </@row>
			      <@row>
			      	  	<@field label="jpaVersion" hidden="true">
							<@input name="jpaVersion" value="${(tmRiskList.jpaVersion)!}"/>
						</@field>
			      </@row>
			      <@toolbar align="center">      	  
			      	  <@submitButton />
			      	  <@backButton />
			      </@toolbar>
			</@form>
		</@panelBody>
	</@panel>

<script type="text/javascript">
		$(function(){
			var idType = $('#idType').val();
			var idNo = $('#idNo').val();
			var cellPhone = $('#cellPhone').val();
			if (idType == '' && idNo == '') {
				$('#queryForm').data('bootstrapValidator').enableFieldValidators('idNo', false, 'notEmpty');
				$('#queryForm').data('bootstrapValidator').enableFieldValidators('idType', false, 'notEmpty');
				$('#queryForm').data('bootstrapValidator').enableFieldValidators('cellPhone', true, 'notEmpty');
			} else {
				if (cellPhone == '') {
					$('#queryForm').data('bootstrapValidator').enableFieldValidators('idNo', true, 'notEmpty');
					$('#queryForm').data('bootstrapValidator').enableFieldValidators('idType', true, 'notEmpty');
					$('#queryForm').data('bootstrapValidator').enableFieldValidators('cellPhone', false, 'notEmpty');
				}
			}
		});
		<#--证件类型的改变，清空证件号-->
 		var idTypeChange = function(){
 			var idType = $('#idType').val();
 			var idNo = $('#idNo').val('');
 			var cellPhone = $('#cellPhone').val('');
			if(idType != ''){
 				$('#queryForm').data('bootstrapValidator').enableFieldValidators('idType', true,'notEmpty');
 				$('#queryForm').data('bootstrapValidator').enableFieldValidators('idNo', true,'notEmpty');
				$('#queryForm').data('bootstrapValidator').enableFieldValidators('cellPhone', false,'notEmpty');
				$("#queryForm").unicornValidField('idNo');
        		$("#queryForm").unicornValidField('idType');
				$("#queryForm").unicornValidField('cellPhone');
			}else{
 				$('#queryForm').data('bootstrapValidator').enableFieldValidators('idType', false,'notEmpty');
 				$('#queryForm').data('bootstrapValidator').enableFieldValidators('idNo', false,'notEmpty');
                $('#queryForm').data('bootstrapValidator').enableFieldValidators('cellPhone', true,'notEmpty');
				$("#queryForm").unicornValidField('idNo');
				$("#queryForm").unicornValidField('idType');
                $("#queryForm").unicornValidField('cellPhone');
			}
 		}
 		<#--身份证校验-->
 		function idNoValid(idNo) {
             var flag = false;
	     	 $.ajax({
	     		type: 'POST',
	     		async:false,
				url: '${base}/cas_applyInput/idNoValid' ,
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
						$("#queryForm").unicornValidField('idNo');
        			}
        		}
        	}//顺序不能换，否则验证会出错
        	var idNo = $('#idNo').val();
        	if(idNo != ''){
        		$('#queryForm').data('bootstrapValidator').enableFieldValidators('idNo', true,'notEmpty');
        		$('#queryForm').data('bootstrapValidator').enableFieldValidators('idType', true,'notEmpty');
				$("#queryForm").unicornValidField('idNo');
        		$("#queryForm").unicornValidField('idType');
        	}
        });
</script>   
</@body>

