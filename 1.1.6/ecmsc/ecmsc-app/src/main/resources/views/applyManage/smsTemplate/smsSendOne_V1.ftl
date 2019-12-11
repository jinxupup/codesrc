
	<@panelBody>
		<@form id="smsListFrom" cols="4">
			 <@row>
				 <#-- <@field label="信息代码">
					   <@input name="msgType" value="${(tmAppMsgSend.msgType)!}" />
				  </@field>
				  <@field label="信息分类">
						<@dictSelect dicttype="SortingInformation" name="query.msgcategory" value="${(query.msgcategory)!}" />
				  </@field>-->
				 <@field label="申请件编号">
					 <@input name="query.appNo" value="${(query.appNo)!}" />
				 </@field>
				 <@field label="姓名">
					 <@input name="query.name" value="${(query.name)!}" />
				 </@field>
				 <@field label="身份证号码">
					 <@input name="query.idNo" value="${(query.idNo)!}" />
				 </@field>
				 <@field label="手机号码">
					 <@input name="query.cellPhone" value="${(query.cellPhone)!}" />
				 </@field>
				 <@field label="短信状态">
					 <@dictSelect dicttype="MessageState" name="query.condition" value="${(query.condition)!}" />
				 </@field>
				  <@field label="">
					  <@button id="smsQurBnt" name="查询" fa="search" />
					  <#--<@backButton />-->
				  </@field>
			  </@row>
		</@form>
		</br>
		<@table url="smsTemplate/selectMessageOne" form_id="smsListFrom" load_auto="false" button_id="smsQurBnt" page_size="10">
			<@th title="申请件编号" field="appNo" />
			<@th title="姓名" field="name" />
			<@th title="身份证号码" field="idNo" />
			<@th title="手机号码" field="cellPhone" />
			<@th title="短信类型" field="msgType" render="true">
				<@thDictName dicttype="SortingInformation"  value="{{row.msgType}}" showcode="true"/>
			</@th>
			<@th title="短信状态" field="condition" render="true">
				<@thDictName dicttype="MessageState"  value="{{row.condition}}" showcode="true"/>
			</@th>
			<@th title="获取时间" field="createDate" render="true" >
				<@thDate value="{{row.createDate}}" datetype="datetime"/>
			</@th>
			<@th title="更新时间" field="updateTime" render="true" >
				<@thDate value="{{row.updateTime}}" datetype="datetime"/>
			</@th>
			<@th title="操作" render="true" >
            {{if row.condition !='S' }}
			{{if row.condition !='I'}}
            {{if row.condition !='N'}}
				<@ajaxButton  name="发送" url="smsTemplate/sendMessageOne?appNo={{row.appNo}}" success_url="smsTemplate/smsTemplateSendPage"  fa="send"/>
				<@ajaxButton confirm="确定标记此记录为无效？" sense="danger" name="无效" url="smsTemplate/settCondition?appNo={{row.appNo}}&condition=N" success_url="smsTemplate/smsTemplateSendPage"  />
            {{/if}}
			{{/if}}
            {{/if}}
            {{if row.condition =='I'}}
				<@ajaxButton  name="已发送" url="smsTemplate/settCondition?appNo={{row.appNo}}&condition=S" success_url="smsTemplate/smsTemplateSendPage"  />
				<@ajaxButton  name="待发送" url="smsTemplate/settCondition?appNo={{row.appNo}}&condition=W" success_url="smsTemplate/smsTemplateSendPage"  />
				<@ajaxButton  name="发送失败" url="smsTemplate/settCondition?appNo={{row.appNo}}&condition=F" success_url="smsTemplate/smsTemplateSendPage"  />
				<@ajaxButton confirm="确定标记此记录为无效？" sense="danger" name="无效" url="smsTemplate/settCondition?appNo={{row.appNo}}&condition=N" success_url="smsTemplate/smsTemplateSendPage"  />
            {{/if}}
			</@th>
		</@table>
	</@panelBody>
