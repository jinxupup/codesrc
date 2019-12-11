<#include "/layout.ftl"/>
<@body>
	<@panel head="业务员信息">
		<@panelBody>

			<@form id="form" action="manageRelation/addUser" before="beforeSubmit" success_url="manageRelation/userManagePage">

				<@fieldset legend="状态信息">
					<@field hidden="true">
						<@input name="id" value="${(user.id)!}"/>
					</@field>
					<@row>
						<@field label="业务员">
							<@multipleSelect id="userNo" name="userNo" value="${(user.userNo)!}" valid={"notempty":"true"}
							options=ecms_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true" />
						</@field>
						<@field label="工作状态">
							<@dictSelect dicttype="CURRENT_STATUS" name="condition" value="A" />
						</@field>
					</@row>
					<@row>
						<@field label="业务人员类型">
							<@dictSelect dicttype="OPERATOR_TYPE" name="userType"  value="${(user.userType)!}"  valid={"notempty":"true"}/>
						</@field>
						<@field label="上级业务员">
							<@multipleSelect id="highterUserNo" name="highterUserNo" value="${(user.highterUserNo)!}"
							options=ecms_('tableMap','getAllUser','') nullable="true" showfilter="true" single="true"/>
						</@field>
					</@row>
				<#--<@input name="highterUserNo" id="highterUserNo"  value="${(user.highterUserNo)!}"  />-->

				<#--<@field label="下级业务员工号">
					<@input name="lowerUserNo" id="lowerUserNo"  value="${(user.lowerUserNo)!}"  />
				</@field>-->
				<#--<@field label="上级业务员">
					<@input name="highterUser"  value="${(user.highterUser)!}" />
				</@field>-->
				<#--<@field label="下级业务员">
					<@input name="lowerUser"  value="${(user.lowerUser)!}" />
				</@field>-->
					<@row>
						<@field label="审批级别">
							<@dictSelect id="rank" dicttype="TASK_RANK" showcode="false" name="rank" value="${(user.rank)!}" />
						</@field>
						<@field label="组别">
							<@dictSelect id="groups" dicttype="GROUPS" name="groups" value="${(user.groups)!}"/>
						</@field>
					</@row>
					<@row>
						<@field label="审批区域组">
							<@dictSelect id="checkGroups" dicttype="CHECKGROUPS" name="checkGroups" value="${(user.checkGroups)!}"/>
						</@field>
					</@row>
				</@fieldset>
				<@row>
					<@toolbar>
						<@submitButton />
						<@backButton/>
					</@toolbar>
				</@row>
			</@form>
		</@panelBody>
	</@panel>
	<script type="text/javascript">

		var beforeSubmit = function(){
			var highterUserNo=$("#highterUserNo").val();
			var userNo = $("#userNo").val();
			/*var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
    　　		if(reg.test(userNo)){
                alert("登陆名不能包含汉字！");
                return false;
            } */
			if(highterUserNo !=null){
				if(highterUserNo[0] == userNo[0]){
					alert("上级业务员工号不能是自己！");
					return false;
				}
			}
		}

	</script>
</@body>