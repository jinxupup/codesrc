<script>		
	<#--查看欺诈申请件进度详情-->
	function applyDblclick(appNo){
		<#--判断是否有查看申请进度详情权限-->
    	$.ajax({
			type: "POST", 
			dataType : "json",
			data:"permission=false",					 
			url: "${base}/cas_applyProcessQuery/viewDetailPermissions", 
			success: function(ref){
				if(ref.s){
					window.open("${base}/cas_activiti/handleTask?appNo="+appNo+"&detailFlag=Y&detailBtnFlag=Y&isOperate=N");
				}else{
					alert(ref.msg); 
				}	
			},
		});
	}
	
	<#--查看黑名单详情-->
	function blackListDblclick(id){
		a = top.dialog({
            title: '黑名单详情',
            width:800,
            height:350,
            url:'personalBlacklist/viewDialog?id=' + id,
            oniframeload:function(){},
            button:[
		        {
		            value: '返回',
		            callback: function () {
		            	this.close();
		                return false;
		            },
		            autofocus: true
		        }
            ]
        });
        a.showModal();
	}
</script>