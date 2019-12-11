<#include "/layout.ftl"/>
<@body>
<@gotop/>
	<@panel head="关联字段页面">
		<@panelBody>
			<@form id="fieldProductForm" action="fieldManage/editFieldProduct" success_url="productParam/page">
				<@row>
					<@field label="卡产品">
						<@select name="productCd" value="${(productCd)!}" options=ecms_('tableMap','productForStatus','') readonly="true"/>
					</@field>
					<@field label="同步卡产品">
						<@select id="copyProductCd" name="copyProductCd" value="" options=ecms_('tableMap','copyFieldProductCd','${(productCd)!}')/>
					</@field>
					<@button id="copyFieldInfoBtn" name="同步字段配置" click="copyFieldInfo()"/>
					<@href href="fieldManage/fieldProductPage?productCd=${(productCd)!''}" name="表列模式"/>
				</@row>
				<@tab id="tab">
					<#if tmFieldProductMap??>
						<#assign sum = 0>
						<#assign keys=tmFieldProductMap?keys/>
						<@tabNav>
							<#list keys as key>
			        			<@tabTitle pane_id="pane${(key_index)!}" active="${(key_index == 0)?string('true','false')}" title="${(key)!}"/>
							</#list>
						</@tabNav>
						<@tabContent>
							<#list keys as key>
			        			<@tabPane id="pane${(key_index)!}" active="${(key_index == 0)?string('true','false')}">
					    			<@pureTable>
										<thead>
											<tr>
									            <th>字段</th>
									            <th>字段所在位置</th>
									            <th width="60px;">位置顺序</th>
									            <th width="60px;">附卡顺序</th>
									            <th>必输项</th>
									            <th>复核项</th>
									            <th width="60px;">复核顺序</th>
									            <th>申请录入显示</th>
									            <th>复核|人工核查|补件|终审显示</th>
									            <th>初审调查显示</th>
									            <th>电话调查显示</th>
									            <th>其他</th>
									        </tr>
										</thead>
										<tbody>
											<#list tmFieldProductMap[key] as tmField>
												<#assign num = tmField_index + sum>
												<#if tmField?? && tmField.tabName??>
													<#if tmField.tabName == "primCust">
														<#assign type = "B">
													<#elseif tmField.tabName == "attachCust">
														<#assign type = "S">
													<#else>
														<#assign type = "A">
													</#if>
													
													<tr>
											            <td><input type="hidden" name="tmFieldProduct[${(num)!}].fieldId" value="${(tmField.fieldId)!}"/>
											            	<input type="hidden" name="tmFieldProduct[${(num)!}].fieldEn" value="${(tmField.fieldEn)!}"/>
											            	<span>${(tmField.tabDesc)!}-</br><#if userNo?? && userNo=="IT">${(tmField.fieldEn)!}-</br></#if></span>${(tmField.fieldName)!}</td>
											            <td><@dictSelect dicttype="FieldRegion" name="tmFieldProduct[${(num)!}].fieldRegion" value="${(tmField.fieldRegion)!}"/></td>
											            <td><@input name="tmFieldProduct[${(num)!}].fieldSort" value="${(tmField.fieldSort)!}"/></td>
											            <td><@input name="tmFieldProduct[${(num)!}].obText1" value="${(tmField.obText1)!}"/></td>
											            <td><@dictSelect dicttype="FieldRequired" name="tmFieldProduct[${(num)!}].ifRequiredItem" nullable="false" value="${(tmField.ifRequiredItem)!'N'}"/></td>
											            <td><@dictSelect dicttype="FieldReview${(type)!}" name="tmFieldProduct[${(num)!}].ifReviewItem" value="${(tmField.ifReviewItem)!'N'}" nullable="false"/></td>
											            <td><@input name="tmFieldProduct[${(num)!}].reviewSort" value="${(tmField.reviewSort)!}"/></td>
											            <td><@dictSelect dicttype="FieldAppType" name="tmFieldProduct[${(num)!}].isInput" nullable="false" value="${(tmField.isInput)!'N'}"/></td>
											            <td><@dictSelect dicttype="FieldAppType" name="tmFieldProduct[${(num)!}].isReview" nullable="false" value="${(tmField.isReview)!'N'}"/></td>
											            <td><@dictSelect dicttype="FieldAppType" name="tmFieldProduct[${(num)!}].value1" nullable="false" value="${(tmField.value1)!'N'}" /></td>
											            <td><@dictSelect dicttype="FieldAppType" name="tmFieldProduct[${(num)!}].value2" nullable="false" value="${(tmField.value2)!'N'}"/></td>
											            <td><@dictSelect dicttype="FieldOther" name="tmFieldProduct[${(num)!}].remark" value="${(tmField.remark)!''}" /></td>
											        </tr>
												</#if>
											</#list>
											 <#assign sum = sum + tmFieldProductMap[key]?size>
									    </tbody>
									</@pureTable>
					    		</@tabPane>
							</#list>
						</@tabContent>
					</#if>
				</@tab>
				<@toolbar align="center" style="margin-top:15px;">
					<@submitButton name="提交" fa="send" style="margin-right:5px;"/>
					<@backButton name="返回" fa="undo" style="margin-left:5px;margin-right:10px;"/>
					<@button id="cleanBtn" name="清空" fa="trash-o" click="cleanFieldProduct()"/>
				</@toolbar>
			</@form>
			<div style="margin-top: 15px; border: solid 1px #c7c1c1; width:1000px;padding:2px 5px;color:#0b9c0b">
				<h4 style="color:red;">友情提示：</h4>
				<p>1、字段所在的位置:A、B、C表示页面的第一、二、三个Tab;数字表示在Tab页中的对应的fieldset顺序;如果后续有新增tab页，则用D、E、F...以此类推即可;</p>
				<p>2、位置顺序:指定该字段在页面对应的fieldset中的顺序，如果没配置，则随机显示;</p>
				<p>3、必输项:N表示不必输;A表示主附同申申请时必输;B:表示独立主卡申请时必输;S表示独立附卡申请时必输;其中选项中的"|"表示"或"的关系;</p>
				<p>4、复核项:A表示不管申请类型是什么都需要复核的项;B表示只是主卡需要复核的项;S表示只是附卡需要复核的项;N表示不需要复核;</p>
				<p>5、复核顺序:表示复核项在主卡或者附卡复核中所在的顺序，<span style="color:red;">注意：该顺序必须配置，否则会出现不可预期的结果;</span></p>
				<p>6、节点(录入、复核、人工核查、初审、电调、补件、终审)页面显示:A、B、S分别对应申请件的类型，N表示不显示，其中选项中的"|"表示"或"的关系;</p>
				<p>7、其他:用来配置额外信息，其中false表示该字段是联动必输项，在联机进件或批量导入时使用；1或2用来区分复核项是亲属联系人还是其他联系人，配置复核项联系人中用到;</p>
			</div>
		</@panelBody>
	</@panel>
	
	<script type="text/javascript">
		<#--卡产品字段配置同步-->
		function copyFieldInfo(){
			var productCd = '${(productCd)!}';
			var copyProductCd = $('#copyProductCd').val();
			if(copyProductCd == ''){
				alert("请选择需要同步的卡产品");
			}else{
				$('#copyFieldInfoBtn').attr('disabled','disabled');
				$.ajax({
		     		type: 'POST',
		     		async:true,
					url: '${base}/fieldManage/copyFieldProduct',
					data: {'productCd':productCd,'copyProductCd':copyProductCd},
					dataType: 'json',
					success:function(res){
						alert(res.msg);
						if(res.s){
							window.location.href='${base}/fieldManage/fieldProductSetPage?productCd='+productCd;
						}else{
							$('#copyFieldInfoBtn').removeAttr('disabled');
						}
		    		} 
				});
			}
		}
		
		<#--清空卡产品字段配置信息-->
		function cleanFieldProduct(){
			var productCd = '${(productCd)!}';
			if(!confirm("确认清空当前产品与字段关联关系？")){
    			return false;
			}
			$('#cleanBtn').attr('disabled','disabled');
			$.ajax({
	     		type: 'POST',
	     		async:true,
				url: '${base}/fieldManage/deleteFieldProduct',
				data: {'productCd':productCd},
				dataType: 'json',
				success:function(res){
					alert(res.msg);
					if(res.s){
						window.location.href='${base}/fieldManage/fieldProductSetPage?productCd='+productCd;
					}else{
						$('#cleanBtn').removeAttr('disabled');
					}
	    		}
			});
		}
	</script>
</@body>
