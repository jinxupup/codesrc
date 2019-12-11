<@fieldset legend="拨打电话：">
	 <div style="width:1000px;">
		<@pureTable id="telSurveyInfo">
			<thead>
				<tr>
					<th width="20%">致电类型</th>
					<th width="20%">致电号码</th>
					<th width="20%">致电日期</th>
					<th width="15%">致电结果</th>
					<th width="25%">备注</th>
			     </tr>
			</thead>
			<tbody id="tbodyId">
			    <#if applyTelInquiryRecordDtoList?? && (applyTelInquiryRecordDtoList?size>0)>
				    <#list applyTelInquiryRecordDtoList as applyTelInquiryRecordDto>
					    <tr>
			               <td><@dictSelect dicttype="CallType" value="${(applyTelInquiryRecordDto.telType)!}" label_only="true"/></td>
			               <td><@input value="${(applyTelInquiryRecordDto.phone)!}" label_only="true"/></td>
			               <td><@date datetype="datetime" value="${(applyTelInquiryRecordDto.telDate?datetime)!}" label_only="true"/></td>
			               <td><@dictSelect dicttype="CallResult" value="${(applyTelInquiryRecordDto.telResult)!}" label_only="true"/></td>
			               <td><@input value="${(applyTelInquiryRecordDto.memo)!}" label_only="true"/></td>  
					    </tr>
				    </#list>
				<#else>
					<tr>
		               <td></td>
		               <td></td>
		               <td></td>
		               <td></td>
		               <td></td>
				    </tr>
			    </#if>
			</tbody>
		</@pureTable>
	</div>
</@fieldset>
<@fieldset legend="核身问题：">
	<div style="width:1000px;">
		<@pureTable>
			<thead>
				<tr>
					<th width="25%">问题内容</th>
					<th width="25%">问题答案</th>
					<th width="25%">问题结果</th>
					<th width="25%">备注</th>
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
	<div style="width:1000px;">
		<@pureTable>
			<thead>
				<tr>
					<th width="25%">问题内容</th>
					<th width="25%">问题答案</th>
					<th width="25%">问题结果</th>
					<th width="25%">备注</th>
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
	<div style="width:1200px;">
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
		 
	</div>
</@fieldset>
