<script type="text/javascript">
    var num = 1;
    <#--上传新建按钮-->
    var addUploadInfo = function () {
        <#-- var flag = validChoice(0, num);// 非空判断
        if (flag == false) {
            return;
        } -->
        var trHtml = $("#tr0").html();
        $("#choiceTable").append('<tr id="tr' + num + '">' + trHtml + '</tr>');
        $("#tr" + num).children("td:eq(0)").children("select").val('').attr({
            "id": "bigType" + num,
            "name": "query.supType" + num,
            "onchange":"setSecond(this,"+num+")"
        });
        $("#tr" + num).children("td:eq(1)").children("select").val('').attr({
        	"id": "smallType" + num, 
        	"name": "query.subType" + num,
        	"option":""
        });
        $("#tr" + num).children("td:eq(2)").children("input").val('')
            .attr({"id": "file" + num, "name": "fileName" + num});
        $("#tr" + num).children("td:eq(3)")
            .html('<button type="button" id="" name="删除" value="" class="btn btn-sm btn-danger" style=""><i class="fa fa-close"></i>删除</button>')
            .children("button").attr("onclick", "delectChoiceInfo('tr" + num + "')");
        num++;
    }
    <#--上传新建非空校验-->

    function validChoice(Size, num) {
        for (var i = Size; i < num; i++) {
            var bigType = $('#bigType' + i).val();
            var smallType = $('#smallType' + i).val();
            var file = $('#file' + i);
            if (bigType == '' || bigType == null) {
                alert("[文件大类型]不能为空!");
                return false;
            }
            if (smallType == '' || smallType ==null ) {
                alert("[文件小类型]不能为空!");
                return false;
            }
            if (file == undefined) {
                alert("上传的文件不能为空!")
                return false;
            }
        }
        return true;
    }

    <#--删除按钮-->
    var delectChoiceInfo = function (trId) {
        $('#' + trId).remove();
    }
    <#-- 内容大类联动内容小类 -->
    function setSecond(obj,curNum) {
        var val = obj.value;
        var url = "${base}/assets/cmp_/getTableByBigType?type=ApplyPatchBoltType";
        var data = {type: val, _PARENT_KEY: "value3", _PARENT_VALUE: val};
        $('#smallType' + curNum).empty();
        $('#smallType' + curNum).val("");
        $.post(ar_.randomUrl(url), data,
            function (res) {
                if (res.s) {
                    var options = res.obj;
                    if (options != null) {
                        var filter = [];
                        for (var i = 0; i < options.length; i++) {
                            var item = options[i];
                            var inFilter = false;
                            for (var j = 0; j < filter.length; j++) {
                                if (item['codeName'] == filter[j]) {
                                    inFilter = true;
                                    break;
                                }
                            }
                            if (inFilter == false) {
                                $('#smallType' + curNum).append("<option value='" + item['code'] + "'>" +item['code'] + "-"+ item['codeName'] + "</option>");
                            }
                        }
                    }
                }
                $('#smallType' + curNum).trigger("change");
            }, 'json');
    }

</script>
