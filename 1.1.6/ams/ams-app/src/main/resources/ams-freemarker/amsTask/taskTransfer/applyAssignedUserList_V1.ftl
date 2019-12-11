<#include "/layout.ftl"/>
<@body>
<@gotop/>

<@panel>
        <@panelBody style="width:98.5%;">
        	<@field label="已选择任务：" hidden="true">
            		<@input id="selectTask" name="selectTask" value="${(selectTask)!}" readonly="true" />
            		<@input id="appNoTaskId" name="appNoTaskId" value="${(appNoTaskId)!}" readonly="true" />  		
    		</@field>
            	<#--	<@input id="aa" name="aa" value="${(taskList)!}" readonly="true" /> -->
    		<@form id="queryForm" >
	            <@row>
	                <@field label="用户" field_ar="24">
	                    <@multipleSelect name="query.userNo" value="" 
							options=ams_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true"/>
	                </@field>
	                <@field label_ar="3" input_ar="25">
	                 	<@button id="queryButton" fa="search">查询</@button>
	                	<@button id="save" >保存</@button>
	                </@field>
	            </@row>
       		</@form>
           <@table id="userList" url="ams_taskTransfer/getUserList?selectTask=${(selectTask)!}" single_select="true" form_id="queryForm" button_id="queryButton" pagination="false">
                <@th checkbox="true"/>
                <@th title="用户ID" field="userNo" />
                <@th title="姓名" field="userName" />
                <@th title="分行" field="branchCode" />
            </@table>
          
        </@panelBody>
    </@panel>


<script type="text/javascript">

		$("#save").on("click",function(){
				var rows = $('#userList').bootstrapTable('getSelections');
            	var ids = [];
	            for(var i=0;i<rows.length;i++){
	                ids.push(rows[i]['userNo']);
	            }
	            if(ids.length==0){
	            	alert("请选择要分配的员工");
	            	return false;
	            }else if(ids.length!=1){
	            	alert("只能分配给一位员工");
	            	return false;
	            }
	            
  
	     		var taskList = document.getElementById("selectTask").value;
	     		var appNoTaskId= document.getElementById("appNoTaskId").value;
			$.ajax({
				url:"${base}/ams_taskTransfer/judgeTransferOperaterUser",
				type:"post",
				data:{userNo:ids.join(","),appNoTaskId:appNoTaskId},
				dataType:"json",
	 			success:function(ref){
	 				if(ref.s){
						 $.ajax({
			 				url:"${base}/ams_taskTransfer/transferTask?userNo="+ids.join(",") + "&selectTask=" + taskList,
			 				data:{appNoTaskId:appNoTaskId},
			 				dataType:"json",
			 				success:function(ref){
			 					alert(ref.msg);
			 					if(ref.s){
				 					var flag='true';
				 					var d = ar_.getDialog(parent);/* parent.dialog.get(window); */			
				            		d.close(flag); // 关闭（隐藏）对话框
									d.remove();				 // 主动销毁对话框
									return false;
								}		         
			 					}
			 				});
			 	}else{
		 				alert(ref.msg);<#--如果失败，则显示失败原因-->
		 				return false;
		 			}
	 			}		
			});					
	            
		});

		var flashTransfer = function(){
			window.location.href="taskTransfer/list";
		
		}

</script>

</@body>