<@panelBody>
	<@form id="abcd" action="smsTemplate/groupSendMessage" success_url="smsTemplate/smsTemplateSendPage" cols="4">
		 <@row>
			 <@field label="短信类型">
				 <@dictSelect dicttype="SortingInformation" name="msgType" value="${(tmAppMsgSend.msgType)!}" valid={"notempty":"true"} />
			 </@field>
			 <@field label="">
				  <@submitButton name="批量发送" fa="send"/>
				  <#--<@backButton fa=""/>-->
			 </@field>
		  </@row>
	</@form>
</@panelBody>

<script>

</script>
