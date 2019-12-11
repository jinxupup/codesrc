<@buttonAuth code="CAS_UPLOAD_IMAGE">
    <@button id="uploadImageBtn" name="影像上传" click="uploadImageBtns" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
</@buttonAuth>
<@buttonAuth code="CAS_IMG_VIEW">
	<@button  name="旧-影像调阅" click="viewImageBtn" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
	&nbsp;&nbsp;&nbsp;
	<@button  name="影像调阅" click="openCmpSysBtns"/>
</@buttonAuth>
<script>
	<#--影像调阅 -旧 -->
	var viewImageBtn = function(){
        window.open("${base}/cas_common/showImages?appNo=" + "${(appNo)!}",'pic','width=1500px,height=810px, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no');
    }
    	<#--影像调阅 - 新-->
    var openCmpSysBtns = function () {
        //先查看该申请件是否存在批次号,不存在则新获取一个批次号
        $.ajax({
            url: "${base}/cas_common/getCmpSysUrl",
            type: "post",
            dataType: "json",
            data: {"appNo": '${(appNo)!}', "sysId": "CAS"},
            async: false,
            success: function (res) {
            	if(res.s){
            		window.open(res.obj, 'pic', 'width=1500px,height=810px, toolbar=no, menubar=no, scrollbars=yes, resizable=no,location=no');
            	}else{
            		alert(res.msg);
            	}
            }
        });
    };


</script>