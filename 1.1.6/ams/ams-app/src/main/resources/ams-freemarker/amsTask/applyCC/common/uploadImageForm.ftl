<@buttonAuth code="AMS_UPLOAD_IMAGE">
	<@form id="imageForm" action="commonDialog/uploadImage" enctype="multipart/form-data">
		<@field hidden="true">
			<input id="imageFileName" type="file" name="imageFileName" onchange="imageChange()"/>
			<input id="appNo" name="appNo" value="${(appNo)!}"/>
			<@submitButton id="imageSubmit"/>
		</@field>
		<script>
		    <#--影像上传-->
			var uploadImage = function(){
				$('#imageFileName').click();
		    }
		    
		    function imageChange(){
		    	$('#imageSubmit').click();
		    }
		</script>
	</@form>
</@buttonAuth>