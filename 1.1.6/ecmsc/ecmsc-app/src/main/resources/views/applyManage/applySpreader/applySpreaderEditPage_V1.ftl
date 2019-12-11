<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panel head="维护推广人信息">
		<@panelBody>
			<@form id="spreaderForm" cols="3" action="spreaderInfo/spreaderEdit" success_url="spreaderInfo/spreaderPage">
				<@field hidden="true">
					<@input id="id" name="tmAppSprePerBank.id" value="${(tmAppSprePerBank.id)!}" />
				</@field>
				<@row>
					<@field label="推广人类型" >
						<@dictSelect name="tmAppSprePerBank.sprUserType" dicttype="SprUserType" value="${(tmAppSprePerBank.sprUserType)!}" />
					</@field>
					<@field label="推广人编号" point_flag="true" point="*">
							<@input name="tmAppSprePerBank.spreaderNum" value="${(tmAppSprePerBank.spreaderNum)!}"/>
					</@field>
					<@field label="推广人姓名">
						<@input name="tmAppSprePerBank.spreaderName" value="${(tmAppSprePerBank.spreaderName)!}" />
					</@field>
				</@row>
				<@row>
					<#--<@field label="推广人绩效号">
						<@input name="tmAppSprePerBank.spreaderPerformance" value="${(tmAppSprePerBank.spreaderPerformance)!}" />
					</@field>-->
					<@field label="推广人所属网点">
						<@multipleSelect name="tmAppSprePerBank.spreaderBankId" value="${(tmAppSprePerBank.spreaderBankId)!}"
								options=ecms_('tableMap','branchMap','allBranch') nullable="true" showfilter="true" single="true"/>
					</@field>
					<@field label="推广人手机号码">
						<@input name="tmAppSprePerBank.spreaderPhone" value="${(tmAppSprePerBank.spreaderPhone)!}" 
								valid={"regexp or pattern":"^((0\\d{2,3}\\-\\d{6,8}(\\-\\d{1,6})?)|(1[3456789]\\d{9}))$","regexp-message":"请输入有效的手机号码"} />
					</@field>
					<@field label="jpaVersion" hidden="true">
						<@input name="tmAppSprePerBank.jpaVersion" value="${(tmAppSprePerBank.jpaVersion)!}"/>
					</@field>
					<@field label="推广人状态" >
						<@dictSelect name="tmAppSprePerBank.spreaderStatus" valid={"notempty":"true"} nullable="false"
							dicttype="SpreaderStatus" value="${(tmAppSprePerBank.spreaderStatus)!'1'}" />
					</@field>
				</@row>
				<@toolbar style="text-align: left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		            <@submitButton />
		            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<@backButton />
				</@toolbar>
			</@form>
		</@panelBody>
	</@panel>
</@body>
