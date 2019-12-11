
<@button name="风控重调" click="applyInfoRiskAgain" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
<script type="text/javascript">
    <#--初审,电调,终审风控重调 -->
    var applyInfoRiskAgain = function(){
        if(!confirm("确认重新调用风险决策系统？ 如果重调可能产生额外费用，请慎用!")){
            return false;
        }
        $.ajax({
            url:"${base}/cas_applyInfoRiskAgain/applyInfoRiskAgain",
            type:"post",
            dataType : "json",
            data:$("#applyInputForm").serialize(),
            success:function(ref){
                if(ref.s){<#--如果成功-->
                    alert(ref.msg);
                    location.reload(true);
                }else{
                    alert(ref.msg);<#--如果失败，则显示失败原因-->
                    location.reload(true)
                }
            }
        });
    }
</script>
