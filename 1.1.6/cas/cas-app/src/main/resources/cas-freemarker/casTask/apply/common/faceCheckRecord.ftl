    <@button name="生物识别结果" click="faceCheckRecordBtn" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
    <script type="text/javascript">
        <#--审批历史信息 -->
        var faceCheckRecordBtn = function(){
            var url = '${base}/cas_commonDialog/faceCheckRecordLayout?appNo=${(appNo)!}';
            dialogInfo('[${(appNo)!}]生物识别结果',840,400,url);
        }
    </script>
