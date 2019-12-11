	<div style="padding-bottom:50px;"></div>
	<@form id="uploadForm" action="batchDecision/uploadAndHandle" success_url="batchDecision/fileUploadList">
		<@row>
			<@field label="文件选择">
         		<@input type="file" name="fileName"/>
    		</@field>
			<@field label="业务条线" >
				<@dictSelect id="productCode" dicttype="RiskProductCode" name="query.productCode" />
			</@field>
<#--    		<@field label="文件编码">
         		<@dictSelect dicttype="EncodeWay" name="encodeWay" showcode="false" value="GBK"/>
    		</@field>-->
    		<@submitButton name="上传提交" id="submitBtn"/>
		</@row>
	</@form>
<#--    <@ajaxButton id="subBtn" name="上传提交" url="batchDecision/uploadAndHandle" form_id="uploadForm" before="beforeSubmit"
    after="afterSubmit" success_url="batchDecision/fileUploadList"/>-->
	<div style="padding-bottom:50px;"></div>
<#--
    <script type="text/javascript">
        $("#subBtn").on('click',function() {
            var c = window.parent.goToLoading();
            c.showModal();

        }
        var afterSubmit=function(){
            c.remove();
            alert('操作成功！');
            history.go(-1);
        }
        </script>-->
