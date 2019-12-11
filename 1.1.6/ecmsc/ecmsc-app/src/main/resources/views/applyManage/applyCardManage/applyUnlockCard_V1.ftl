<#include "/layout.ftl">
<@body>
	<@form id="queryForm">
	     <@row>
	          <@field label="姓名">
					<@input id="name" name="query.name"  value="${(query.name)!}"/>
			  </@field>
	          <@field label="证件类型">
	                <@dictSelect dicttype="IdType" name="query.idType" />            
	          </@field>
	          <@field label="证件号码">
	                <@input name="query.idNo" value="${(query.idNo)!}"/>
	          </@field>
	          <@field label="申请编号">
	          		<@input name="query.appNo" value="${(query.appNo)!}"/>
	          </@field> 
	          <@field label="移动电话">
	                <@input name="query.cellphone" value="${(query.cellphone)!}"/>
	          </@field>   
	          <@field label="卡号">
					<@input id="cardNo" name="query.cardNo"  value="${(query.cardNo)!}"/>
			  </@field>
	          <@button id="queryButton" name="查询" fa="search" />
	    </@row>
	</@form>
	</br>
	<@table id="dataTable" url="card/unlockCardList" load_auto="false" form_id="queryForm" button_id="queryButton">
		<@th title="申请件编号" field="appNo" sortable="true"/>
		<@th title="姓名" field="name" sortable="true"/>
		<@th title="证件类型" field="idType" render="true" sortable="true">
			<@thDictName dicttype="IdType" value="{{row.idType}}" />
		</@th>
		<@th title="证件号码" field="idNo" sortable="true"/>
		<@th title="移动电话" field="cellphone" sortable="true"/>
		<@th title="审批状态" field="rtfState" render="true" sortable="true">
		 	<@thDictName dicttype="RtfState" showcode="true"  value="{{row.rtfState}}" />
		</@th>
		<@th title="卡号" field="cardNo" sortable="true"/>
		<@th title="修改人" field="operId"sortable="true" />
		<@th title="修改日期" sortable="true" render="true">
	        <@thDate  value="{{row.updateTime}}" />
        </@th>
		<@th title="操作" render="true" >
			<@buttonAuth code="ECMS_UNLOCK_CARD">
				<@ajaxButton confirm="确定解锁此卡号？" url="card/unlock?cardNo={{row.cardNo}}&appNo={{row.appNo}}" name="解锁卡号" 
					success_url="/card/unlockCard" before="checkRtfState('{{row.rtfState}}')"/>
			</@buttonAuth>
		</@th>
	</@table>
	 <script>
	     function format(value,row){
			if(row.attachFlag=="false"){
				var name=row.name+'<font color="red">(主)</font>';
			}else  if(row.attachFlag=="true"){
				var name=row.name+'<font color="blue">(附)</font>';
			}else{
				var name=row.name;
			}
			return name;		
		}
		
		var reload = function(){
			var query = {};
				query = $("#queryForm").serializeObject();
				var params = {url:ar_.randomUrl("${base}/card/unlockCardList")};
				$("#dataTable").bootstrapTable("getOptions").customerQuery = query;
				$("#dataTable").bootstrapTable("refresh",params);
		}
		function checkRtfState(rtfState){
			var _rtfState = rtfState;
			if(_rtfState=="K15" || _rtfState=="M05" || _rtfState=="A20"){
				return true;
			}else{
				alert("只有K15(终审拒绝)/M05(失败申请)/A20(录入无效删除)状态的申请件才能在此页面解锁卡号");
				return false;
			}
		}
	 </script>
</@body>