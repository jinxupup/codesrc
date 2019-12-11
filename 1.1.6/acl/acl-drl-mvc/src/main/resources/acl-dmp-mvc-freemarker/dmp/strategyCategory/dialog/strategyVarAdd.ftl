<#include "/layout.ftl"/>
<@body>
<style>
   .fixed-table-container thead th .th-inner, .fixed-table-container tbody td .th-inner{
        padding:1px;
   }
   .table-condensed>tbody>tr>td, .table-condensed>tbody>tr>th, .table-condensed>tfoot>tr>td, .table-condensed>tfoot>tr>th, .table-condensed>thead>tr>td, .table-condensed>thead>tr>th {
        padding: 3px;
    }
    
    #hasVars td{
        padding:1px;
        vertical-align:middle;
    }
</style>

<table class="table">
<tr>
<td style="width:50%" valign="top">
<@panel head="所有变量" class="" sense="info" style="margin-bottom:0px;">
<@panelBody class="" style="padding: 0px 0px;">
<@pureTable id="allVars" style="margin-bottom:0;" class="table-condensed">
<thead>
    <tr>
        <th> 类型 </th>
        <th> 代码 </th>
        <th> 名称 </th>
        <th> 数据类型 </th>        
        <th> 操作 </th>
    </tr>
</thead>
<tbody>
    <#list varList as varIn>
        <tr>
            <td> ${varIn.varType} </td>
            <td> ${varIn.varCd}  </td>
            <td> ${varIn.varName} </td>
            <td> ${varIn.dataType} </td>
            <td> <@button name="添加" click="addRight('${varIn.varType}','${varIn.varCd}','${varIn.varName}')" /> </td>
        </tr>
    </#list>
</tbody>
<script>

    var dbHasMap = {}; //已在数据库保存的变量
    var destMap = {};

    $(function(){
        $('#allVars').bootstrapTable({'height':'510','search':true});
    });
    function addRight(varType,varCd,varName){
        
        var varValue = destMap[varCd];
        if(varValue!=undefined||varValue!=null){
            var hasVarType = varValue['varType'];
            if(hasVarType==varType){
                alert("已添加该变量");
                return;
            }else{
                alert("已选择相同代码，不同类型的代码，请在已选变量中删除后添加");
                return;
            }
        }
        
        destMap[varCd] = {varCd:varCd,varType:varType,varName:varName};
        
    
        var $varType = $("<td>",{text:varType});
        var $varCd = $("<td>",{text:varCd+"-"+varName}).append($("<input>",{type:"hidden",class:"varCd",value:varCd}));
        
        var $isInput = '<label style="font-weight:500;margin-right:5px;"><input class="isInput" type="checkbox" value="Y"/> 输入 </label>';
        var $isOutput = '<label style="font-weight:500;"><input class="isOutput" type="checkbox" value="Y" style="margin-right:0;" /> 输出 </label>';
        var $direct = $("<td>",{}).append($isInput).append($isOutput);
        var $delete = $("<td>",{}).append('<button type="button" name="取消" value="" class="btnDelete btn btn-sm btn-default " style="">取消</button>');
        
        $tr = $("<tr>").append($varType).append($varCd).append($direct).append($delete);
        $("#hasVarsBody").append($tr);
    }
    
    //删除行
    $(function(){
        $("#hasVarsBody").delegate("button.btnDelete",'click',function(){
            $tr = $(this).parent().parent();
            
            var varCd = $(".varCd",$tr).val();
            delete destMap[varCd];
            $tr.remove();
        });
    });
    
    function saveBtn(){
        var $trs = $("#hasVarsBody").children();
        $trs.each(function(){
            $tr = $(this);
            var varCd = $(".varCd",$tr).val();
            var isInput = $(".isInput",$tr).is(':checked');
            
            var isOutput = $(".isOutput",$tr).is(':checked');
            
            destMap[varCd]['isInput'] = isInput?"Y":"N";
            destMap[varCd]['isOutput'] = isOutput?"Y":"N";
        })
        console.log(destMap);    
        
        var destMapStr = JSON.stringify(destMap); 
        var data = {};
        data.strategyVars = destMapStr;
        data.stClass = '${stClass}';
        $.post("${base}/dmp/strategyCategory/addVar",data,function(res){
            alert(res.msg);
            if(res.s){
            	var dialog = ar_.getDialog();
				dialog.close().remove;
            }
        },'json');
        
    }
    
</script>
</@pureTable>
</@panelBody>
</@panel>
</td>
<td style="width:50%" valign="top">
   
   <@hidden name="" value="${stClass}" />
    
<@panel head="已选变量" class="" sense="warning" style="margin-bottom:0px;">
<@panelBody class="" style="padding: 0px 0px;">

<@toolbar align="right" style="height:48px;" right_offset="3">
    <@button name="保存" style="margin-top:5px;" click="saveBtn" />
</@toolbar>


<div style=" height:415px;overflow:scroll;">
<@pureTable id="hasVars" style="margin-bottom:0px;" class="table-condensed">
<thead>
    <tr>
        <th style="width:70px;"> 类型 </th>
        <th style="width:215px;"> 代码  - 名称 </th>
        <th style="width:70px;"> 变量方向  </th>        
        <th style="width:40px;"> 操作 </th>
    </tr>
</thead>
<tbody id="hasVarsBody">

</tbody>
<script>
    $(function(){
       // $('#hasVars').bootstrapTable({'striped':true,'height':'480','search':true});
    });
</script>
</@pureTable>
</div>


</@panelBody>
</@panel>
</td>
</tr>
</table>




</@body>