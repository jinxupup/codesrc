<#include "/layout.ftl">

	<@body>
		<@panel head="征信查询">
			<@panelBody>
		 		<@form id="queryForm" style="padding-top:10px;">
				<@row>
					<@field label="姓名" point_flag="true" point="*">
						 <@input id="name" name="query.name"  valid={"notempty":"true"}/>
					</@field>
					<@field label="证件号码" point_flag="true" point="*">
						<@input id="idNo" name="query.idNo" valid={"notempty":"true"}/>
					</@field>
					<@toolbar align="right">
						<@button id="queryButton" name="查询" fa="search"/>
				    </@toolbar>
				</@row>
		 		</@form>
			</@panelBody>
		</@panel>
		
		<script type="text/javascript">
				$("#queryButton").on('click',function () {
					var  new_window = window.open();
					$.ajax({
						url:"${base}/_pbocQuery/pbocQuery",
						type:'POST',
						dataType:"json",
						data:$("#queryForm").serialize(),
						success:function(ref){
							if(ref.s){
								new_window.location=ref.msg;
							}else{
								alert(ref.msg);
								new_window.close();
							}
						}
					})
				});

				<#--身份证格式验证-->
				$('#idNo').blur(function(){
						var idNo = $('#idNo').val();
						if(idNo != ''){
							if(! idNoValid(idNo)){
								$('#idNo').val('');
								$("#queryForm").data('bootstrapValidator').updateStatus('idNo','NOT_VALIDATED');
							}
					}
				});


				<#--身份证校验-->
				function idNoValid(idNo) {
					var flag = false;
					$.ajax({
						type: 'POST',
						async:false,
						url: '${base}/ams_applyInput/idNoValid' ,
						data: {"idNo":idNo},
						dataType: 'json',
						success:function(res){
							flag = res.s;
							if(flag == false){
								alert("身份证号码不正确！");
							}
						}
					});

					return flag;
				}
		</script>
	</@body>

