<#include "/layout.ftl"/>
<@body>
<#--历史申请信息弹出框-->
    <@panel>
    	<@panelBody  style="padding-left:2px;">
	        <#if buttonNotOpen??>
	            <#if tmAppFlagList??>
	                <table style=" width: 100%">
						<tr style="vertical-align: top;">
							<td width="33%">
								<@pureTable striped="true">
									<tr><th colspan="2" style="text-align:center">风险警告</th></tr>
									<#list tmAppFlagList as tmAppFlag>
										<#if (tmAppFlag.flagType=="FlagRisk") >
											<tr>
												<td>${(tmAppFlag.flagCode)!}-${(tmAppFlag.flagDesc)!}</td>
												<td></td>
											</tr>
										</#if>
									</#list>
								</@pureTable>
							</td>
							<td width="33%">
								<@pureTable>
									<tr>
										<th style="text-align:center">系统警告</th>
									</tr>
									<#list tmAppFlagList as tmAppFlag>
										<#if tmAppFlag.flagType=="FlagSys" >
											<tr>
												<td>${(tmAppFlag.flagCode)!}-${(tmAppFlag.flagDesc)!}</td>
											</tr>
										</#if>
									</#list>
								</@pureTable>
							</td>
							<td width="33%">
								<@pureTable>
									<tr><th colspan="2" style="text-align:center">自定义标签</th></tr>
									<#list tmAppFlagList as tmAppFlag>
										<#if tmAppFlag.flagType=="FlagApp" >
											<tr>
												<td>${(tmAppFlag.flagCode)!}-${(tmAppFlag.flagDesc)!}</td>
												<td>${(tmAppFlag.createUser)!}</td>
											</tr>
										</#if>
									</#list>
								</@pureTable>
							</td>
						</tr>
					</table>
	            </#if>
	            <#assign num=0>
	        </#if>
            <@pureTable>
                <tr>
                    <th width="15%">申请件编号</th>
                    <th width="15%">审批状态</th>
                    <th width="10%">申请类型</th>
                    <th width="10%">申请卡产品</th>
                    <th width="8%">操作员ID</th>
                    <th width="8%">任务所属人</th>
                    <th width="15%">申请日期</th>
                    <th width="10%">拒绝原因码</th>
                    <#--<th width="10%">备注</th>-->
                    <th width="7%">操作</th>
                </tr>
                <#if applyInfoPreDtoList??>
                    <#list applyInfoPreDtoList as applyInfoPreDto>
                        <tr>
                            <td>${(applyInfoPreDto.appNo)!}</td>
                            <td><@dictSelect dicttype="RtfState" value="${(applyInfoPreDto.rtfState)!}" label_only="true"/></td>
                            <td><@dictSelect dicttype="AppType" value="${(applyInfoPreDto.appType)!}" label_only="true"/></td>
                            <td><@select options=cas_('tableMap','productForStatus','')  value="${(applyInfoPreDto.productCd)!}" label_only="true"/></td>
                            <td>${(applyInfoPreDto.createUser)!}</td>
                            <td>${(applyInfoPreDto.taskOwner)!}</td>
                            <td>${(applyInfoPreDto.createDate?datetime)!}</td>
                            <td>${(applyInfoPreDto.refuseCode)!}</td>
                            <#--<td><@dictSelect dicttype="ApplyRejectReason" value="${(applyInfoPreDto.refuseCode)!}" label_only="true"/></td>-->
                            <#--<td>${(applyInfoPreDto.remark)!}</td>-->
                            <td><@button click="applyDetailBtn('${(applyInfoPreDto.appNo)!}')" name="查看详情"/></td>
                        </tr>
                    </#list>
                </#if>
            </@pureTable>
        </@panelBody>
    </@panel>

    <script type="text/javascript">
        <#--详情按钮-->
        var applyDetailBtn = function (appNo) {
            window.open('${base}/cas_activiti/handleTask?appNo=' + appNo + '&detailFlag=Y&detailBtnFlag=Y');
        }
    </script>
</@body>