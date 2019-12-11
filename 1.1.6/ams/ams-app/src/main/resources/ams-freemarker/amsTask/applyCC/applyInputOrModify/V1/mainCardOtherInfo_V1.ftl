<#assign fieldsetMap = {'C1':'申请人基本信息'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldSet.ftl"/>
</#list>

<script type="text/javascript">
	<#--联系人性别初始化-->
	$(function(){
		$('#contactGender1').removeAttr("disabled");
		$('#contactGender1').removeAttr("readonly");
	});

	<#--联系人身份证号格式校验及生日、性别自动填充-->
	function contactIdNoBlur(event){
		var num = event.data.num;
		if(num == null){
			alert("联系人身份证号验证发生异常!");
		}
		var contactIdNo = $('#contactIdNo'+num).val();
    	if(contactIdNo != ''){
    		contactIdNo = $.trim(contactIdNo);
    		if(contactIdNo.length==15 || contactIdNo.length==18){
    			if(idNoValid(contactIdNo)){
    				if(num == 1){
	    				if(contactIdNo.length == 15){
							if(contactIdNo.substring(14,15)%2 == 1){//男
								$('#contactGender'+num+'_ar_hidden_').val("M");
								$('#contactGender'+num).val("M");
							}else{
								$('#contactGender'+num+'_ar_hidden_').val("F");
								$('#contactGender'+num).val("F");
							}
							var contactBirthday = "19"+contactIdNo.substring(6,7)+"-"+contactIdNo.substring(8,9)+"-"+contactIdNo.substring(10,11);
						}
	    				if(contactIdNo.length == 18){
							if(contactIdNo.substring(14,17)%2 == 1){//男
								$('#contactGender'+num+'_ar_hidden_').val("M");
								$('#contactGender'+num).val("M");
							}else{
								$('#contactGender'+num+'_ar_hidden_').val("F");
								$('#contactGender'+num).val("F");
							}
							var contactBirthday = contactIdNo.substring(6,10) + "-" + contactIdNo.substring(10,12) + "-" + contactIdNo.substring(12,14);	
						}
						$('#contactGender'+num).attr({"readonly":"readonly","disabled":"disabled"});
						$('#contactBirthday'+num).val(contactBirthday);
    				}
    			}else{
    				$('#contactIdNo'+num).val('');
    			}
    		}
    	}
	}
	
	<#--联系人证件类型的改变-->
	function contactIdTypeChange(num){
		var contactIdType = $('#contactIdType'+num).val();
		$('#contactIdNo'+num).removeAttr("readonly").val('');
		$('#contactGender'+num).removeAttr("readonly disabled").val('');
		$('#contactBirthday'+num).val('');
		if(contactIdType == 'I'){
			$('#contactIdNo'+num).bind('keyup blur',{'num':num},contactIdNoBlur);		
		}else{
			$('#contactIdNo'+num).unbind('keyup blur');
		}
	}
</script>
       