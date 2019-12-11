<@fieldset legend="电调记录：">

    <@table id="TelDataTable" url="cas_common/getTelCheckRecordList?appNo=${(appNo)!}" 
    		form_id="telRsQueryForm" button_id="telRsQueryForm" pagination="false" condensed="true">
        <@th title="致电类型" field="telType" render="true" sortable="true">
            <@thDictName dicttype="CallType"  value="{{row.telType}}" showcode="true"/>
        </@th>
        <@th title="致电号码" field="phone" />
        <@th title="致电日期" field="telDate" render="true" sortable="true">
            <@thDate value="{{row.telDate}}" datetype="datetime"/>
        </@th>
        <@th title="致电结果" field="telResult" render="true" sortable="true">
            <@thDictName dicttype="CallResult"  value="{{row.telResult}}" showcode="true"/>
        </@th>
        <@th title="备注" field="memo"/>
    </@table>
</@fieldset>
<#--<@fieldset legend="核身问题：">
	<div>
		<@pureTable>
			<thead>
				<tr>
					<th width="25%">问题内容</th>
					<th width="25%">问题答案</th>
					<th width="10%">问题结果</th>
					<th width="40%">备注</th>
				</tr>
			</thead>
			<tbody>
				<#if idCheckList??>
					<#list idCheckList as idCheck>
		        		<tr>
			        	   <td><@input value="${(idCheck.askContent)!}" label_only="true"/></td>                                                          
			               <td><@input value="${(idCheck.answer)!}" label_only="true"/></td>
			               <td><@dictSelect dicttype="Indicator" value="${(idCheck.result)!}" label_only="true"/></td>
			               <td><@input value="${(idCheck.memo)!}" label_only="true"/></td>
					    </tr>
					</#list>
			    </#if>
			</tbody>
		</@pureTable>
	</div>
</@fieldset>
<@fieldset legend="必问问题：">
	<div>
		<@pureTable>
			<thead>
				<tr>
					<th width="25%">问题内容</th>
					<th width="25%">问题答案</th>
					<th width="10%">问题结果</th>
					<th width="40%">备注</th>
				</tr>
			</thead>
	        <tbody>
	        	<#if mustCheckList??>
					<#list mustCheckList as mustCheck>
		        		<tr>
			        	   <td><@input value="${(mustCheck.askContent)!}" label_only="true"/></td>                                                          
			               <td><@input value="${(mustCheck.answer)!}" label_only="true"/></td>
			               <td><@dictSelect dicttype="Indicator" value="${(mustCheck.result)!}" label_only="true"/></td>
			               <td><@input value="${(mustCheck.memo)!}" label_only="true"/></td>
					    </tr>
					</#list>
			    </#if>
			</tbody>
		</@pureTable>
	</div>
</@fieldset>
<@fieldset legend="选核问题：">
	<@pureTable id="choiceCheckInfo">
	<thead>
		<tr>
			<th width="20%">问题内容</th>
			<th width="40%">问题答案</th>
			<th width="20%">问题结果</th>
			<th width="20%">备注/备忘</th>
		</tr>
	</thead>
		<tbody id="choiceTable">
			<#if choiceCheckList?? && (choiceCheckList?size>0)>
				<#list choiceCheckList as choiceCheck>
					<tr>
					   <td><@dictSelect dicttype="TelCheckValue" value="${(choiceCheck.askContent)!}" label_only="true"/></td>
					   <td><@input value="${(choiceCheck.answer)!}" label_only="true"/></td>
					   <td><@dictSelect dicttype="Indicator" value="${(choiceCheck.result)!}" label_only="true"/></td>
					   <td><@input value="${(choiceCheck.memo)!}" label_only="true"/></td>
					</tr>
				</#list>
			<#else>
				<tr>
				   <td></td>
				   <td></td>
				   <td></td>
				   <td></td>
				</tr>
			</#if>
		</tbody>
	</@pureTable>
</@fieldset>
-->
