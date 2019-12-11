<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panel head="短信发送">
		<@panelBody>
				<@tab id="tabs">
					<@tabNav>
						<@tabTitle pane_id="pane1" active="true" title="短信管理"/>
						<@tabTitle pane_id="pane2" title="批量发送"/>
					</@tabNav>
					<@tabContent>
						<@tabPane id="pane1" active="true">
							<#include "../smsTemplate/smsSendOne_V1.ftl"/>
						</@tabPane>
						<@tabPane id="pane2">
							<#include "../smsTemplate/smsSendGroup_V1.ftl"/>
						</@tabPane>
					</@tabContent>
				</@tab>
		</@panelBody>
	</@panel>
</@body>
