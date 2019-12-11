<#include "/layout.ftl"/>
<#--工作流部署页面-->
<@body>
<@gotop/>
  	<@panel head="流程图部署">
  		<@panelBody>
	   		<@form id="selectForm" action="activiti/submitDiagrams" enctype="multipart/form-data" success_url="activiti/activitiDeployePage">
	   			<@row>
	   				<@field label="流程选择">
                 		<@file name="file"/>
            		</@field>
            		<@field label="流程名称">
                 		<@input name="proName"/>
            		</@field>
            		<@submitButton name="上传提交"/>
	   			</@row>
	   		</@form>
	   		<@fieldset legend="流程定义列表:">
	       	 	<@table id="processTable" url="activiti/processDefList" form_id="selectForm" button_id="submitBtn">
	       	 		<@th title="ID" field="id" />
	       	 		<@th title="名称" field="proName" />
	       	 		<@th title="流程定义的KEY" field="proKey" />
	       	 		<@th title="流程定义的版本" field="proVersion" />
	       	 		<@th title="文件名称" field="resourceName" />
	       	 		<@th title="图片名称" field="diagramResourceName" />
	       	 		<@th title="部署ID" field="deploymentId" />
	       	 		<@th title="操作" render="true">
	       	 			<@button id="operate" name="查看流程图" click="viewPic('{{row.diagramResourceName}}', '{{row.deploymentId}}')"/>
	       	 			{{if row.deploymentId==${(defDeploymentId)!}}}
 							<button id="use" type="button" class="btn btn-sm btn-info" name="设为默认" disabled=disabled>设为默认</button>
 						{{else}}
 							<button id="use" type="button" class="btn btn-sm btn-info" name="设为默认" onclick="use('{{row.proKey}}', '{{row.deploymentId}}')">设为默认</button>
 						{{/if}}
 					    <@button id="delete" name="删除" sense="danger" click="del('{{row.deploymentId}}')"/>
	       	 		</@th>
		   	 	</@table>
       	 	</@fieldset>
	   	</@panelBody>
	</@panel>  
	
<script type="text/javascript">
    
    <#--查看流程图 -->
    function viewPic(diagramResourceName, deploymentId)
	{
		b = top.dialog({
            title: '查看流程',
            width:1000,
            height:600,
            url:'activiti/getProDefImg?diagramResourceName=' + diagramResourceName + '&deploymentId=' + deploymentId, 
            oniframeload:function(){},
            button:[
            {	
                value: '确定',
                callback: function () {
               		this.close();
	                return false;
                },
                autofocus: true
            },
            ]
        });
        b.showModal(); 
	}
	
	<#--删除流程定义 -->
    function del(deploymentId)
	{
		if(confirm("是否确认删除？")){
		$.ajax({
				url:"${base}/activiti/deleteDeployment?deploymentId="+deploymentId,	
				type:"post",
				data:deploymentId,
				dataType:"json",
				success:function(ref){
					alert(ref.msg);
					window.location.href="${base}/activiti/activitiDeployePage";
				}
			});
		}
		
	} 
	<#--设置默认流程 -->
    function use(procdefKey, deploymentId)
	{
		if(confirm("是否确认设置该流程为默认流程？")){
		$.ajax({
	 				url:"${base}/activiti/initDeployment?procdefKey="+procdefKey+"&deploymentId="+deploymentId,	
	 				type:"post",
	 				data:procdefKey,
	 				dataType:"json",
	 				success:function(ref){
	 					alert(ref.msg);
	 					window.location.href="${base}/activiti/activitiDeployePage";
	 				}
	 			});
		}
		
	} 
</script>
</@body>