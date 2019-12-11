<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panel head="${isEdit?string('修改','新增')!}页面字段">
		<@panelBody>
			<@form id="fieldForm" action="fieldManage/editField" success_url="fieldManage/applyFieldPage" cols="2">
				<@field hidden="true">
		     		<@input name="tmField.fieldId" value="${(tmField.fieldId)!}"/>
				</@field>
				<@field label="表名描述" hidden="true">
		     		<@input id="tabDesc" name="tmField.tabDesc" value="${(tmField.tabDesc)!}" readonly="true"/>
				</@field>
				<@row>
					<@field label="字段表名">
						<@dictSelect dicttype="BusTabName" showcode="false" change="tabChange(this)" name="tmField.tabName" value="${(tmField.tabName)!''}"
							valid={"notempty":"true","notempty-message":"字段表名不能为空"} readonly="${isEdit?string('true','false')}" />
					</@field>
					<@field label="字段">
			     		<@input name="tmField.fieldEn" valid={"regexp or pattern":"^[a-zA-Z0-9]{0,30}$","regexp-message":"字段格式不正确",
			     			"notempty":"true","notempty-message":"字段不能为空"} value="${(tmField.fieldEn)!}" />
					</@field>
				</@row>
				<@row>
					<@field label="字段名称">
                   <#-- "regexp or pattern":"^[\\u4e00-\\u9fa5//]{1,80}$","regexp-message":"字段名格式不正确",   删除这个字段格式验证-->
			     		<@input name="tmField.fieldName" valid={"notempty":"true","notempty-message":"字段名称不能为空"} value="${(tmField.fieldName)!}"/>
					</@field>
					<@field label="默认值">
			     		<@input name="tmField.defValue" value="${(tmField.defValue)!}"/>
					</@field>
				</@row>
				<@row>
					<@field label="换行始末">
						<@select name="tmField.isRow" value="${(tmField.isRow)!''}" options={"Y":"开始","N":"结束"}/>
					</@field>
					<@field label="field-label-input标签占比" >
						<@input class="col-ar-11" name="tmField.fieldAr" valid={"regexp or pattern":"^(0|([1-9](\\d+)?))$","regexp-message":"请输入整数"} value="${(tmField.fieldAr)!}"/>
						<span class="col-ar-2" style="text-align:center;">-</span>
			     		<@input class="col-ar-11" name="tmField.labelAr" valid={"regexp or pattern":"^(0|([1-9](\\d+)?))$","regexp-message":"请输入整数"} value="${(tmField.labelAr)!}"/>
			     		<span class="col-ar-1">-</span>
			     		<@input class="col-ar-11" name="tmField.inputAr" valid={"regexp or pattern":"^(0|([1-9](\\d+)?))$","regexp-message":"请输入整数"} value="${(tmField.inputAr)!}"/>
					</@field>
				</@row>
				<@row>
					<@field label="是否启用">
						<@dictSelect dicttype="Indicator" name="tmField.ifUsed" nullable="false" value="${(tmField.ifUsed)!'Y'}"/>
					</@field>
					<@field label="是否删除">
						<@dictSelect dicttype="Indicator" name="tmField.ifCancel" value="${(tmField.ifCancel)!'N'}"/>
					</@field>
				</@row>
				<@row>
					<@field label="是否只读">
						<@select name="tmField.ifReadonly" value="${(tmField.ifReadonly)!}" options={"true":"是","false":"否"} showcode="false"/>
					</@field>
					<@field label="组件类型">
			     		<@select name="tmField.componentType" valid={"notempty":"true","notempty-message":"组件类型不能为空"} value="${(tmField.componentType)!}" change="componentTypeChange"
			     			options={"input":"输入框","dictSelect":"字典下拉框","select":"下拉框","date":"日期选择框","multipleSelect":"下拉多选框","textarea":"文本框","ajaxSelect":"异步加载下拉框"}/>
					</@field>
				</@row>
				<#--input组件属性配置-->
				<@row id="inputTypeA">
					<@field label="正则表达式" field_ar="24" label_ar="7" input_ar="29">
			     		<@input id="regexp" name="tmField.fieldRegexp" value="${(tmField.fieldRegexp)!}"/>
					</@field>
				</@row>
				<@row id="inputTypeB">
					<@field label="字符串最大长度">
			     		<@input id="maxLength" name="tmField.maxLength" valid={"regexp or pattern":"^(0|([1-9](\\d+)?))$","regexp-message":"请输入整数"} value="${(tmField.maxLength)!}"/>
					</@field>
					<@field label="区间最小值和最大值">
			     		<@input id="betweenMin" class="col-ar-17" name="tmField.betweenMin" valid={"regexp or pattern":"^(0|([1-9](\\d+)?))$","regexp-message":"请输入整数"} value="${(tmField.betweenMin)!}" />
			     		<span class="col-ar-2">-</span>
			     		<@input id="betweenMax" class="col-ar-17" name="tmField.betweenMax" valid={"regexp or pattern":"^(0|([1-9](\\d+)?))$","regexp-message":"请输入整数"} value="${(tmField.betweenMax)!}"/>
					</@field>
				</@row>
				<#--dictSelect、multipleSelect、select组件属性配置-->
				<@row id="selectTypeA">
					<@field id="dict" label="字典类型">
			     		<@multipleSelect id="dictType" name="dictType" value="${(dictType)!}"
			     			options=ar_('listToMap',types,"type","typeName") single="true" nullable="true" showfilter="true"/>
					</@field>
					<@field id="option" label="下拉选项options">
			     		<@dictSelect id="options" dicttype="Options" name="options" value="${(options)!}" showcode="true"/>
					</@field>
					<@field label="下拉框改变js方法">
			     		<@input id="change" name="change" value="${(change)!}"/>
					</@field>
				</@row>
				<@row id="selectTypeB">
					<@field id="clickDiv" label="点击事件js方法">
			     		<@input id="click" name="click" value="${(click)!}"/>
					</@field>
					<@field label="是否启用空选项">
			     		<@select id="nullable" name="tmField.fieldNullable" value="${(tmField.fieldNullable)!}" options={"true":"是","false":"否"} showcode="false"/>
					</@field>
					<@field id="showCodeDiv" label="是否显示code">
			     		<@select id="showCode" name="tmField.showCode" value="${(tmField.showCode)!}" options={"true":"是","false":"否"} showcode="false"/>
					</@field>
				</@row>
				<@row id="selectTypeC">
					<@field label="是否单选">
			     		<@select id="single" name="single" value="${(single)!}" options={"true":"是","false":"否"} showcode="false"/>
					</@field>
					<@field label="是否可搜索">
			     		<@select id="showfilter" name="showfilter" value="${(showfilter)!}" options={"true":"是","false":"否"} showcode="false"/>
					</@field>
				</@row>
				<#--date组件属性配置-->
				<@row id="dateType">
					<@field label="当前日期是否是最大或小值">
						<@select id="settings" name="settings" value="${(settings)!}" options={"maxDate:%y-%M-%d":"最大值","minDate:%y-%M-%d":"最小值"} showcode="false"/>
					</@field>
					<@field label="日期格式">
			     		<@select id="dateFomate" name="dateFomate" value="${(dateFomate)!}" options={"yyyy-MM-dd":"年-月-日","yyyy-MM-dd HH:mm:ss":"年-月-日 时:分:秒"} showcode="false"/>
					</@field>
				</@row>
				<#--textarea组件属性配置-->
				<@row id="textareaType">
					<@field label="备注框name">
			     		<@select id="textName" name="textName" value="${(textName)!}" options={"MEMOINPUT":"推广人注记","REMARKINPUT":"预审人注记"}/>
					</@field>
					<@field label="行数">
			     		<@input id="rows" name="rows" value="${(rows)!}" valid={"regexp or pattern":"^[1-9]$","regexp-message":"请输入整数"}/>
					</@field>
				</@row>
				<@row style="margin-bottom:4px;">
					<@field label="备注" field_ar="24" label_ar="7" input_ar="29">
						<@textarea id="remark" style="margin-left:-2px;" name="tmField.remark" cols="" rows="3" value="${(tmField.remark)!''}" />
					</@field>
				</@row>
				<@toolbar align="center" class="col-ar-28" style="margin-top:15px;">
					<@submitButton name="提交" fa=""/>
					<@backButton name="返回" fa=""/>
				</@toolbar>
			</@form>
			<div style="margin-top: 15px; border: solid 1px #c7c1c1; width:860px;padding:2px 5px;color:#0b9c0b">
				<h4 style="color:red;">友情提示：</h4>
				<p>1、是否启用空选项默认为是(组件默认)；是否显示code默认为是(组件默认)；是否单选默认为否(组件默认)；是否可搜索默认为否(组件默认)</p>
				<p>2、下拉选项options下拉框的值请在数据字典中配置Options</p>
				<p>3、省市区、国家代码、网点机构请配置为异步加载下拉框(ajaxSelect)，其中省市区、网点为联动</p>
				<p>4、异步加载下拉框点击事件格式：clickAjaxSelect(数据字典,是否显示code,字段类型,this,父级id)；其中'是否显示code'参数需要与字段的配置一致，</p>
				<p style="margin-left:15px;">'字段类型'为(A：省市区、B:国家代码、C:网点、D:按等级查推广人机构网点、E:按下级查推广人机构网点)中的一种；如果是联动，则'父级id'参数必</p>
				<p style="margin-left:15px;">填；'NUM'表示的是附卡的序号(0、1、2)，主卡就不需要；如附卡县/区的点击事件配置：clickAjaxSelect('ZONE','false','A',this,'empCityNUM')</p>
				<p>5、异步加载下拉框改变事件格式：changeAjaxSelect(子级数据字典,子级是否显示code,子级id,子级字段类型,this)；其中'子级是否显示code'参数需</p>
				<p style="margin-left:15px;">要与子级字段的配置一致，'字段类型'为(A：省市区、B:国家代码、C:网点、D:按等级查推广人机构网点、E:按下级查推广人机构网点)中的一种；如</p>
				<p style="margin-left:15px;">果是联动，则'子级id'参数必填；'NUM'表示的是附卡的序号(0、1、2)，主卡就不需要； 如附卡县/区的点击事件配置：changeAjaxSelect('ZONE','false','homeZoneNUM','A',this)</p>
				<p>6、异步加载下拉框备注里需要定义字段回显的格式（是否显示code|是否是aclDic|数据字典类型），如：国家代码的配置为Y|Y|Nationality，省</p>
				<p style="margin-left:15px;">为N|Y|STATE，第一网点为Y|N|firstBranch</p>
				<p>7、自选卡号正则表达式中长度用NUM表示，后台默认为9，如^\d{NUM}$；申请额度最大值配置为MAX,若卡产品最高审批额度为空，则后台默认为5000元</p>
			</div>
		</@panelBody>
	</@panel>
	<script type="text/javascript">
		<#--初始化-->
		$(function(){
			hideAndShow('${(tmField.componentType)!}');
		});
		
		<#--表名描述-->
		var tabChange = function(that){
			var tabName = $(that).val();
			if(tabName == 'tmAppMain'){
				$('#tabDesc').val('申请主表');
			}else if(tabName == 'primCust'){
				$('#tabDesc').val('主卡申请人信息表');
			}else if(tabName == 'attachCust'){
				$('#tabDesc').val('附卡申请人信息表');
			}else if(tabName == 'tmAppContact'){
				$('#tabDesc').val('联系人信息表');
			}else if(tabName == 'tmAppPrimAnnexEvi'){
				$('#tabDesc').val('附件证明信息表');
			}else if(tabName == 'tmAppPrimCardInfo'){
				$('#tabDesc').val('卡片信息表');
			}else if(tabName == 'tmEtcCar'){
				$('#tabDesc').val('ETC车辆信息表');
			}else if(tabName == 'tmAppcardfaceInfo'){
				$('#tabDesc').val('卡面信息表');
			}else if(tabName == 'tmAppcardfaceInfo'){
				$('#tabDesc').val('汽车分期信息表');
			}else{
				$('#tabDesc').val('');
			}
		}
		<#--组件类型联动-->
		var componentTypeChange = function(that){
			var selectValue = $(that).val();
			hideAndShow(selectValue);
			$('#remark').text('');
		}
		
		function hideAndShow(selectValue){
			if(selectValue == 'select' || selectValue == 'multipleSelect' || selectValue == 'dictSelect' || selectValue == 'ajaxSelect'){
				$('#inputTypeA,#inputTypeB,#dateType,#textareaType').hide();
				$('#regexp,#maxLength,#betweenMin,#betweenMax,#settings,#dateFomate,#textName,#rows').val('');
				if(selectValue == 'multipleSelect'){
					$('#dict').hide();
					$('#dictType').multipleSelect('uncheckAll');
					$('#clickDiv').hide();
					$('#click').val('');
					$('#showCodeDiv').show();
					$('#option').show();
					$('#selectTypeC').show();
				}else{
					$('#selectTypeC').hide();
					$('#single,#showfilter').val('');
					if(selectValue == 'select' || selectValue == 'ajaxSelect'){
						$('#dict').hide();
						$('#dictType').multipleSelect('uncheckAll');
						$('#option').show();
						$('#showCodeDiv').show();
						if(selectValue == 'ajaxSelect'){
							$('#clickDiv').show();
						}else{
							$('#clickDiv').hide();
							$('#click').val('');
							$('#showCodeDiv').show();
						}
					}else if(selectValue == 'dictSelect'){
						$('#option').hide();
						$('#options').val('');
						$('#dict').show();
						$('#clickDiv').hide();
						$('#click').val('');
						$('#showCodeDiv').show();
					}
				}
				$('#selectTypeA,#selectTypeB').show();
			}else if(selectValue == 'date'){
				$('#inputTypeA,#inputTypeB,#textareaType,#selectTypeA,#selectTypeB,#selectTypeC').hide();
				$('#regexp,#maxLength,#betweenMin,#betweenMax,#options,#change,#click,#nullable,#showCode,#single,#showfilter,#textName,#rows').val('');
				$('#dictType').multipleSelect('uncheckAll');
				$('#dateType').show();
			}else if(selectValue == 'input'){
				$('#textareaType,#selectTypeA,#selectTypeB,#selectTypeC,#dateType').hide();
				$('#options,#change,#click,#nullable,#showCode,#single,#showfilter,#settings,#dateFomate,#textName,#rows').val('');
				$('#dictType').multipleSelect('uncheckAll');
				$('#inputTypeA,#inputTypeB').show();
			}else if(selectValue == 'textarea'){
				$('#inputTypeA,#inputTypeB,#dateType,#selectTypeA,#selectTypeB,#selectTypeC').hide();
				$('#regexp,#maxLength,#betweenMin,#betweenMax,#options,#change,#click,#nullable,#showCode,#single,#showfilter,#settings,#dateFomate').val('');
				$('#dictType').multipleSelect('uncheckAll');
				$('#textareaType').show();
			}else {
				$('#inputTypeA,#inputTypeB,#textareaType,#selectTypeA,#selectTypeB,#selectTypeC,#dateType').hide();
				$('#regexp,#maxLength,#betweenMin,#betweenMax,#options,#change,#click,#nullable,#showCode,#single,#showfilter,#settings,#dateFomate,#textName,#rows').val('');
				$('#dictType').multipleSelect('uncheckAll');
			}
		}
	</script>
</@body>
