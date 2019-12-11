<#include "/layout.ftl"/>
<@body>
    <@gotop/>
    <@panelBody>
        <@form id="queryForm" cols="4">
            <@row>
                <@field label="风险名单来源">
                    <@dictSelect dicttype="RiskListSource" name="query.risklistSrc" value="${(query.risklistSrc)!}"/>
                </@field>
                <@field label="姓名">
                    <@input name="query.name" value="${(query.name)!}" />
                </@field>
                <@field label="证件号码">
                    <@input name="query.idNo" value="${(query.idNo)!}" />
                </@field>
                <@field label="移动电话">
                    <@input name="query.cellPhone" value="${(query.cellPhone)!}" />
                </@field>
                <@field label="家庭电话">
                    <@input name="query.homePhone" value="${(query.homePhone)!}" />
                </@field>
                <@field label="公司电话">
                    <@input name="query.empPhone" value="${(query.empPhone)!}" />
                </@field>
                <@field label="风险名单类型">
                    <@dictSelect name="query.actType" dicttype="ActType" value="${(query.actType)!}" />
                </@field>
                <@field label="">
                &nbsp;&nbsp;
                    <@button id="queryButton" name="查询" fa="search"/>
                &nbsp;&nbsp;
                    <@href href="tmRiskList/addpage" name="新增" />
                &nbsp;&nbsp;
                    <@href href="tmRiskList/fileUpload" name="批量导入" />
                </@field>
            </@row>
        </@form>
    </br>
        <@table id="dataTable" url="tmRiskList/list" form_id="queryForm" button_id="queryButton" page_size="10">
            <@th title="风险名单来源" field="risklistSrc" render="true" >
                <@thDictName  dicttype="RiskListSource" showcode="true" value="{{row.risklistSrc}}" />
            </@th>
            <@th title="客户姓名" field="name" />
            <@th title="证件号码" field="idNo" />
            <@th title="移动电话" field="cellPhone" />
            <@th title="家庭电话" field="homePhone" />
            <@th title="家庭地址" field="homeAdd" />
            <@th title="公司名称" field="corpName" />
            <@th title="公司电话" field="empPhone" />
            <@th title="公司地址" field="empAdd" />
            <@th title="记录有效期" field="validDate" render="true" >
                <@thDate value="{{row.validDate}}" />
            </@th>
            <@th title="上风险名单原因说明" field="reason" />
            <@th title="风险名单类型" field="actType" render="true" >
                <@thDictName  dicttype="ActType" showcode="true" value="{{row.actType}}" />
            </@th>
            <@th title="操作" render="true" >
                <@ajaxButton  confirm="确定删除此记录？" sense="danger"  name="删除" url="tmRiskList/delete?id={{row.id}}" success_url="tmRiskList/tmRiskList" />
            &nbsp;&nbsp;
                <@href href="tmRiskList/editpage?id={{row.id}}" name="编辑" />
            </@th>
        </@table>
    </@panelBody>

<script>
    <#--可以双击执行-->
    $("#dataTable").on('dbl-click-row.bs.table', function (row, $element, field) {
        var id = $element['id'];
        window.location.href = "${base}/tmRiskList/editpage?id=" + id;
    });
</script>
</@body>
