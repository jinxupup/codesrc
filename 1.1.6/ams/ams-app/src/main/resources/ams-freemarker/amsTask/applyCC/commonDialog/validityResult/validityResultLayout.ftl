<#include "/layout.ftl"/>
<@body>
<@gotop/>
<#--审批资料检验结果信息弹出框-->
<@panel>
    <@panelBody>
        <@pureTable>
 			<tr>
				<th>主附卡</th>
				<th>问题原因</th>
 			</tr>
 			<#list applyInfoValidityResultDtos as applyInfoValidityResultDto>
 				<tr>
 					<td>${(applyInfoValidityResultDto.appType)!}</td>
 					<td>${(applyInfoValidityResultDto.memo)!}</td>
 				</tr>
 			</#list>
	   	 </@pureTable>
    </@panelBody>
</@panel>
    
</@body>