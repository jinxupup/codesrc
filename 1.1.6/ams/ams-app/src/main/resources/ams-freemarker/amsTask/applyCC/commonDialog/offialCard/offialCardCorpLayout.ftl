<#include "/layout.ftl"/>
<@body>
    <@panel>
        <@panelBody>
            <@form>
                <@table id="dataTable" url="commonDialog/getOffialCardCorp" form_id="queryForm" button_id="queryButton">
                    <@th title="单位编号" field="corpNo" />
                    <@th title="单位名称" field="corpName" />
                    <@th title="行业性质" field="empStructure" />
                    <@th title="行业类别" field="empType" />
                    <@th title="公司所在省" field="empProvince" />
                    <@th title="公司所在市" field="empCity" />
                    <@th title="公司所在县/区" field="empZone" />
                    <@th title="操作" render="true" >
                        <@button name="选择" click="choiceRows('{{row.corpNo}}','{{row.corpName}}','{{row.empStructure}}','{{row.empType}}','{{row.corpAddress}}','{{row.corpPostcode}}'
                    ,'{{row.corpPhone}}','{{row.empProvince}}','{{row.empCity}}','{{row.empZone}}','{{row.empAddrCtryCd}}')"/>
                    </@th>
                </@table>
            </@form>
        </@panelBody>
    </@panel>
<script>
    function choiceRows(corpNo, corpName, empStructure, empType, corpAddress, corpPostcode, corpPhone, empProvince, empCity, empZone, empAddrCtryCd) {

        var parentNode = window.parent.document.getElementById("J_iframe").contentWindow;

        parentNode.$("#corpName").val(corpNo + " - " + corpName);
        parentNode.$("#empType").val(empType);
        parentNode.$("#empStructure").val(empStructure);
        parentNode.$("#empAdd").val(corpAddress);
        parentNode.$("#empPostcode").val(corpPostcode);
        parentNode.$("#empPhone").val(corpPhone);
        parentNode.$("#empProvince").val(empProvince);
        parentNode.$("#empCity").val(empCity);
        parentNode.$("#empZone").val(empZone);
        parentNode.$("#empAddrCtryCd").val(empAddrCtryCd);
        
        //选择后将页面disabled的栏位的隐藏域同时赋值
        parentNode.$("#empType_ar_hidden_").val(empType);
        parentNode.$("#empStructure_ar_hidden_").val(empStructure);
        parentNode.$("#empAddrCtryCd_ar_hidden_").val(empAddrCtryCd);

        // 将节点设置为只读
//      parentNode.attrNode();
		alert("已选择：单位编号-" + corpNo + " ， 单位名称-" + corpName);
    }
</script>
</@body>