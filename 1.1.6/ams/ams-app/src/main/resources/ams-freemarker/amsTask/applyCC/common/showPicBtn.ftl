<#--<@buttonAuth code="AMS_UPLOAD_IMAGE">
 	<@button id="uploadImageBtn" name="影像上传" click="uploadImageBtns" style="margin-left:5px;margin-right:5px;"/>	
</@buttonAuth>-->
<@buttonAuth code="AMS_IMG_VIEW">
 	<@button  name="旧-影像调阅" click="viewImageBtn" style="margin-left:5px;margin-right:5px;margin-bottom:2px;"/>
	&nbsp;&nbsp;&nbsp;
	<@button  name="影像调阅" click="openCmpSysBtns"/>
</@buttonAuth>
<script>
	<#--影像调阅 -旧 -->
	var viewImageBtn = function(){
        window.open("${base}/ams_commonDialog/showImages?appNo=" + "${(appNo)!}",'pic','width=1500px,height=810px, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no');
    }
    
	<#--影像调阅 - 新-->
    var openCmpSysBtns = function () {
        //先查看该申请件是否存在批次号,不存在则新获取一个批次号
        $.ajax({
            url: "${base}/ams_commonDialog/getCmpSysUrl",
            type: "post",
            dataType: "json",
            data: {"appNo": '${(appNo)!}', "sysId": "AMS"},
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
	
	// base64加密
	function encode64(input) {
	    var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv" + "wxyz0123456789+/" + "=";
	    var output = "";
	    var chr1, chr2, chr3 = "";
	    var enc1, enc2, enc3, enc4 = "";
	    var i = 0;
	    do {
            chr1 = input.charCodeAt(i++);
            chr2 = input.charCodeAt(i++);
            chr3 = input.charCodeAt(i++);
            enc1 = chr1 >> 2;
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
            enc4 = chr3 & 63;
            if (isNaN(chr2)) {
               enc3 = enc4 = 64;
            } else if (isNaN(chr3)) {
               enc4 = 64;
            }
            output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2) + keyStr.charAt(enc3) + keyStr.charAt(enc4);
            chr1 = chr2 = chr3 = "";
            enc1 = enc2 = enc3 = enc4 = "";
    	} while (i < input.length);
   
    	return output;
	}
	
</script>