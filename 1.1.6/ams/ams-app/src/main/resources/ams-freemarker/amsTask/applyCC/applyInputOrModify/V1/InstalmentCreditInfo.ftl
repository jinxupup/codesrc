<#-- 分期信息页面 -->
<#if pageFieldMap['A9']??>
	<@fieldset legend="标准大额分期信息:">
		<#list pageFieldMap['A9']?keys as key>
			<#assign fieldPageDto = pageFieldMap['A9'][key]>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'Y'><div class="row"></#if>
			<@field label="${(fieldPageDto.fieldName)!}" point_flag="${(fieldPageDto.ifRequire)!}" point="*">
				<#if fieldPageDto.componentType??>
					<#if fieldPageDto.componentType == "input">
						<#if fieldPageDto.id == "appFeeRate">
							<div style="margin-right: 0px;margin-left: 0px;">
								<div class="col-ar-28" style="padding-right:0px;padding-left:0px;">
									<@input id="${(fieldPageDto.id)!}" readonly="${(fieldPageDto.ifReadonly)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" />
								</div>
								<div id="exp">
  									<@input class="col-ar-3" value="例:0.1234" label_only="true" style="color:#989898" />
  								</div>
							</div>
						<#else>
							<@input id="${(fieldPageDto.id)!}" readonly="${(fieldPageDto.ifReadonly)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" />
						</#if>
					<#elseif fieldPageDto.componentType == "dictSelect">
						<@dictSelect id="${(fieldPageDto.id)!}" dicttype="${(fieldPageDto.dictType)!}" change="${(fieldPageDto.change)!}" name="${(fieldPageDto.name)!}" nullable="${(fieldPageDto.nullable)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}" showcode="${(fieldPageDto.showCode)!}"/>
					<#elseif fieldPageDto.componentType == "select">
						<@select id="${(fieldPageDto.id)!}" options=fieldPageDto.options change="${(fieldPageDto.change)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"  showcode='${(fieldPageDto.showCode)!}' nullable="${(fieldPageDto.nullable)!}"/>
					<#elseif fieldPageDto.componentType == "multipleSelect"><#--没有改变事件-->
						<@multipleSelect id="${(fieldPageDto.id)!}" options=fieldPageDto.options name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" nullable="${(fieldPageDto.nullable)!}" showfilter="true" single="${(fieldPageDto.textName)!}"/>
					<#elseif fieldPageDto.componentType == "date">
						<@date id="${(fieldPageDto.id)!}" settings=fieldPageDto.options name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"/>
					<#elseif fieldPageDto.componentType == "textarea">
						<@textarea id="${(fieldPageDto.id)!}" name="${(fieldPageDto.name)!}" rows="${(fieldPageDto.dictType)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"/>
					<#elseif fieldPageDto.componentType == "ajaxSelect"><#--异步加载下拉框-->
						<@ajaxSelect id="${(fieldPageDto.id)!}" options=fieldPageDto.options click="${(fieldPageDto.textName)!}" change="${(fieldPageDto.change)!}" name="${(fieldPageDto.name)!}" value="${(fieldPageDto.value)!}" readonly="${(fieldPageDto.ifReadonly)!}"  showcode="${(fieldPageDto.showCode)!}" nullable="${(fieldPageDto.nullable)!}"/>
					</#if>
				</#if>
			</@field>
			<#if fieldPageDto.isRow?? && fieldPageDto.isRow == 'N'></div></#if>
		</#list>
		<script type="text/javascript">
			$(function(){
				//活动号添加搜索功能
				if($("#instalmentCreditActivityNo").length > 0){
					$("#instalmentCreditActivityNo").select2({
					language: 'zh-CN',width: "100%"});
					$("form .select2-container").addClass("form-control");
				}
				if($("#loanFeeCalcMethod").length > 0){
					var loanFeeCalcMethod = $("#loanFeeCalcMethod").val();
					if(loanFeeCalcMethod=="R"){
						$("#appFeeAmt").val("");
						$('#appFeeAmt').attr("readonly","readonly");
					}else if(loanFeeCalcMethod=="A"){
						$("#appFeeRate").val("");
						$('#appFeeRate').attr("readonly","readonly");
					}else{
						$('#appFeeRate').attr("readonly","readonly");
						$('#appFeeAmt').attr("readonly","readonly");
						$("#appFeeRate").val("");
						$("#appFeeAmt").val("");
					}
				}
			});	
			
			function instalParamChanges(type){
				var productCd = $("#productCd").val();
				var activityNo = $("#instalmentCreditActivityNo").val();
				$.ajax({
						type: 'POST',
			     		dataType: 'json',
			     		data: {'productCd':productCd,'activityNo':activityNo,'type':type},
						url: '${base}/ams_applyInput/getInstalParam',
						success:function(res){
							if(res.s){
								if(res.code != null && res.code == "0"){
									alert(res.msg);
									$("#instalmentCreditActivityNo").val('');
								}
								var obj = res.obj;
									
								if(obj['activityNoMap'] != null){
									var activityNoMap = obj['activityNoMap'];
									var selectA = $("#instalmentCreditActivityNo");
						    		selectA.empty();
									selectA.append("<option value = ''></option>");
									$.each(activityNoMap,function(key,values){ 
										selectA.append("<option value = '"+key+"'>"+key+"-"+values+"</option>");
									});
								}
								
								if(obj['mccNoMap'] != null){
									var mccNoMap = obj['mccNoMap'];
									var selectM = $("#mccNo");
						    		selectM.empty();
									selectM.append("<option value = ''></option>");
									$.each(mccNoMap,function(key,values){ 
										selectM.append("<option value = '"+key+"'>"+key+"-"+values+"</option>");
									});
								}
								
								if(obj['termsMap'] != null){
									var termsMap = obj['termsMap'];
									var selectT = $("#loanInitTerm");
						    		selectT.empty();
									selectT.append("<option value = ''></option>");
									$.each(termsMap,function(key,values){ 
										selectT.append("<option value = '"+key+"'>"+key+"</option>");
									});
								}
							}else{
								alert(res.msg);
							}
							
			    		}
					});
			
			}
		
			function changeMethod(){
				var loanFeeCalcMethod = $("#loanFeeCalcMethod").val();
				if(loanFeeCalcMethod=="R"){
					$("#appFeeRate").removeAttr("readonly","readonly");
					$("#appFeeAmt").val("");
					$('#appFeeAmt').attr("readonly","readonly");
				}else if(loanFeeCalcMethod=="A"){
					$("#appFeeRate").val("");
					$('#appFeeRate').attr("readonly","readonly");
					$("#appFeeAmt").removeAttr("readonly","readonly");
				}else{
					$('#appFeeRate').attr("readonly","readonly");
					$('#appFeeAmt').attr("readonly","readonly");
					$("#appFeeRate").val("");
					$("#appFeeAmt").val("");
				}
			};
			
			<#--点击异步加载下拉框
			/**点击异步加载下拉框的options
			*@param type 类型
			*@param isCode  是否显示code(默认显示)
			*@param parentId 联动父级id
			*/
			function clickInstalAjaxSelect(type,isCode,that,parentId){
				var that = '#'+that;
				var enableAjax = $(that).attr('ajax');
				if(enableAjax=='true'){
					if(type == '' || type == undefined){
						return ;
					}
					$(that).attr('ajax','false');
					if(isCode == '' || isCode == undefined){
						isCode = true;//默认显示code
					}
					
					var parentValue = null;
					if(parentId != null && parentId != '' && parentId != undefined){
						parentValue = $('#'+parentId).multipleSelect('getSelects');//获取父级组件的值
					}else{
						parentValue = '';//获取父级组件的值
					}
					parentValue = parentValue + '';//将数组转化为字符串
					var name = $(that).attr('name');
					var value = $(that).multipleSelect('getSelects');
					$.ajax({
						type: 'POST',
						url : '${base}/ams_applyInput/getInstalParam,
						data : {'type':type,'name':name,'parentValue':parentValue,'isCode':isCode},
						dataType : 'json',
						success : function(res){
							if(res.s){
								//单选可搜索下拉框搜索部分
								$(that).siblings('.ms-parent').children('.ms-drop').children('ul').html(res.code);
								$(that).html(res.msg).multipleSelect('refresh').multipleSelect('setSelects',value);//刷新设默认值
							}else{
								$(that).attr('ajax','true');
								alert("网络异常，请稍后再试");
							}
						}
					});
				}
			}-->
			
			<#--选项改变联动异步加载
			/**改变异步加载
			*@param type 需要联动改变字段的类型
			*@param isCode 是否显示code
			*@param childId 需要联动子级id
			*/
			function changeInstalAjaxSelect(type,isCode,childId,that){
				if(type == '' || type == undefined || childId == '' || childId == undefined){
					return ;
				}
				
				if(isCode == '' || isCode == undefined){
					isCode = true;//默认显示code
				}
				var value = $('#'+$(that).attr('id')+'_hidden_value').val();//获取当前组件的值
				value = value + '';//将数组转化为字符串
				var name = $('#'+childId).attr('name');
				$.ajax({
					type: 'POST',
					url : '${base}/ams_applyInput/getInstalParam',
					data : {'type':type,'name':name,'parentValue':value,'isCode':isCode},
					dataType : 'json',
					success : function(res){
						if(res.s){
							$('#'+childId+'_hidden_value').val(res.obj);
							var $childId = $('#'+childId);
							$childId.siblings('.ms-parent').children('.ms-drop').children('ul').html(res.code);
							$childId.html(res.msg).multipleSelect('refresh').multipleSelect('setSelects',res.obj);//刷新设默认值
							var ajaxchange = $childId.attr('ajaxchange');//change事件标志
							if(ajaxchange == 'true' || ajaxchange == 'Y'){
								var childValue = $childId.multipleSelect('getSelects')+'';
								if(childValue != ''){
									$childId.trigger("ajaxchange");
								}
							}
						}else{
							alert("网络异常，请稍后再试");
						}
					}
				});
			 }-->
		</script>
	</@fieldset>
</#if>

