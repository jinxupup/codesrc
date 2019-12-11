<#include "/layout.ftl"/>
<@body>
<#--生物识别历史弹出框-->
    <@panel>
        <@panelBody style="padding-left:10px;">

            <@pureTable>

                <th>申请编号</th>
                <th>创建时间</th>
                <th>更新时间</th>
                <th>最终验证结果</th>
                <tr>
                    <td width="100">${(fcrd.appNo)!}</td>
                    <td width="100">${(fcrd.createTime?datetime)!}</td>
                    <td width="100">${(fcrd.updateTime?datetime)!}</td>
                    <td width="100">${(fcrd.finalResult)!}</td>
                </tr>
            </@pureTable>

            <@pureTable>
                <tr>
                    <th>当前验证次数</th>
                    <th>验证结果</th>
                    <th>当前验证结果描述</th>
                </tr>
                <#if faceCheckRecordDtos??>
                    <#list faceCheckRecordDtos as faceCheckRecord>
                        <tr>
                            <td width="100">${(faceCheckRecord.currCnt)!}</td>
                            <td width="100">${(faceCheckRecord.checkRs)!}</td>
                            <td width="100">${(faceCheckRecord.checkRsDesc)!}</td>
                        </tr>
                    </#list>
                </#if>
            </@pureTable>
        </@panelBody>
    </@panel>
</@body>