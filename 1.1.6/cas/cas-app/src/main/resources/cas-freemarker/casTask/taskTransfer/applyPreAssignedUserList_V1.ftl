<#include "/layout.ftl"/>
<@body>
    <@panel>
        <@panelBody>
            <@field hidden="true">
                <@input id="appNos" name="appNos" value="${(appNos)!}"/>
            </@field>
            <@form id="queryForm" >
                <@row>
                    <@field label="推广人工号或姓名" field_ar="18">
                        <@input id="spreaderName" name="query.spreaderName"  value="${(query.spreaderName)!}" />
                    </@field>
                    <@field label_ar="0" field_ar="17">
                        <@button id="queryButton" fa="search">查询</@button>&nbsp;&nbsp;
                        <@button id="save" >保存</@button>
                    </@field>
                </@row>
            </@form>
            <@table id="dataTable"  url="cas_common/getSpreaderList" form_id="queryForm" button_id="queryButton" page_size="10" condensed="true">
                <@th checkbox="true"/>
                <@th title="推广人编号" field="spreaderNum" />
                <@th title="推广人姓名" field="spreaderName" />
                <@th title="推广人电话" field="spreaderPhone" />
                <@th title="推广人所属网点" field="spreaderBankId" />
            </@table>
        </@panelBody>
    </@panel>

<script type="text/javascript">
	$("#save").on("click",function(){
        var rows = $('#dataTable').bootstrapTable('getSelections');
        var spreaderNum;
        var spreaderName;
        var spreaderPhone;
        var spreaderBankId;
        if(rows.length>0){
	        spreaderNum = rows[0]['spreaderNum'];
	        spreaderName = rows[0]['spreaderName'];
	        spreaderPhone = rows[0]['spreaderPhone'];
	        spreaderBankId = rows[0]['spreaderBankId'];
        }
        if(rows.length==0){
            alert("请选择转分配的推广人");
            return false;
        }
        var appNos= document.getElementById("appNos").value;
        $.ajax({
            url:"${base}/cas_taskTransfer/spreaderSave",
            type:"post",
            data:{spreaderNum:spreaderNum,spreaderName:spreaderName,spreaderPhone:spreaderPhone,spreaderBankId:spreaderBankId,appNos:appNos},
            dataType:"json",
            success:function(ref){
                if(ref.s){
                    alert("保存成功")
                    var d = ar_.getDialog(parent);/* parent.dialog.get(window); */
                    d.close(true); // 关闭（隐藏）对话框
                    d.remove();// 主动销毁对话框
                    return false;
                }else{
                    alert(ref.msg);<#--如果失败，则显示失败原因-->
                    return false;
                }
            }
        });

    });

</script>


</@body>