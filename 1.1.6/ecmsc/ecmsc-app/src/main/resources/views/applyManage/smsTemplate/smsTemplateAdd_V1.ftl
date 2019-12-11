<#include "/layout.ftl"/>
<@body>
<@gotop/>

	<@panel head="${isEdit?string('编辑短信模板','添加短信模板')}">
		<@panelBody>
			<@form id="queryForm" action="${isEdit?string('smsTemplate/edit','smsTemplate/add')}" success_url="smsTemplate/smsTemplatePage">
     			<@field hidden="true">
	     			 <@input name="id" value="${(tmMessageTemplate.id)!}"/>
	      		</@field>
     		   <@row>
			      	  <@field label="信息代码">
			                <@input name="teCode" value="${(tmMessageTemplate.teCode)!}"/>
			          </@field>
			          <@field label="信息描述">
			                <@input name="teDesc" value="${(tmMessageTemplate.teDesc)!}"/>
			          </@field>
			      </@row>
			       <@row>
			          <@field label="发送方法">
			                <@dictSelect dicttype="SendingMethod" name="sendingMethod" value="${(tmMessageTemplate.sendingMethod)!}"/>
			          </@field>
			          <@field label="信息分类">
			                <@dictSelect id="msgcategory" dicttype="SortingInformation" change="msgCategoryChange" name="msgcategory" value="${(tmMessageTemplate.msgcategory)!}"/>
			          </@field>
			      </@row>  
			      <@row>
			          <@field label="发送起始时间">
			                <@date name="startTime" value="${(startTime)!}"/>
			          </@field>
			          <@field label="发送结束时间">
			                <@date name="endTime" value="${(endTime)!}"/>
			          </@field>
			      </@row>
			      <@row style="margin-bottom:4px;">
			          <@field label="内容模板" field_ar="24" label_ar="6" input_ar="30">
			                <@textarea id="contentTemplate" class="col-ar-36" rows = "4" name="contentTemplate" value="${(tmMessageTemplate.contentTemplate)!}"/>
			          </@field>  
			      </@row>
			      <@row>
			      	<@field hidden="true">
			                <@input id="textValue" value=""/>
			          </@field>
						<div class="col-ar-4"></div>
						<div class="col-ar-20">
							<label class="col-ar-6" style="font-weight:100;">
								<input id="name" type="checkbox"  value="" onClick="clickCheck('${r'${name}'?html}',this)">
								<span>姓名</span>
							</label>
							<label class="col-ar-6" style="font-weight: 100;">
								<input id="cardNo" type="checkbox"  value="" onClick="clickCheck('${r'${card_no}'?html}',this)">
								<span>卡号</span>
							</label>         
							<label class="col-ar-6" style="font-weight: 100;">
								<input id="cardNoE4" type="checkbox"  value="" onClick="clickCheck('${r'${card_no_e4}'?html}',this)">
								<span>卡号（后四位）</span>
							</label>          
							<label class="col-ar-6" style="font-weight: 100;">
								<input id="txnDate" type="checkbox"  value="" onClick="clickCheck('${r'${txn_date}'?html}',this)">
								<span>交易日期</span>
							</label>          
							<label class="col-ar-6" style="font-weight: 100;">
								<input id="txnTime" type="checkbox"  value="" onClick="clickCheck('${r'${txn_time}'?html}',this)">
								<span>交易时间</span>
							</label>          
							<label class="col-ar-6" style="font-weight: 100;">
								<input id="gender" type="checkbox"  value="" onClick="clickCheck('${r'${gender}'?html}',this)">
								<span>性别</span>
							</label>          
							<label class="col-ar-6" style="font-weight: 100;">
								<input id="orgName" type="checkbox"  value="" onClick="clickCheck('${r'${org_name}'?html}',this)">
								<span>机构名称</span>
							</label>
							<label class="col-ar-6" style="font-weight: 100;">
								<input id="orgCssPhone" type="checkbox"  value="" onClick="clickCheck('${r'${org_css_phone}'?html}',this)">
								<span>客服电话</span>
							</label>          
							<label id="patchTypeLabel" class="col-ar-6" style="font-weight: 100;">
								<input id="patchType" type="checkbox"  value="" onClick="clickCheck('${r'${applyPatchBoltType}'?html}',this)">
								<span>补件类型</span>
							</label>         
							<label id="productLabel" class="col-ar-6" style="font-weight: 100;">
								<input id="product" type="checkbox"  value="" onClick="clickCheck('${r'${productCd}'?html}',this)">
								<span>卡产品代码</span>
							</label>          
							<label id="descriptionLabel" class="col-ar-6" style="font-weight: 100;">
								<input id="description" type="checkbox"  value="" onClick="clickCheck('${r'${description}'?html}',this)">
								<span>卡产品描述</span>
							</label>
						</div>
						<div class="col-ar-12"></div>
			      </@row>
			      <@toolbar align="center" style="margin-top:30px;margin-left:-400px;">     	  
			      	  <@submitButton />
			      	  <@backButton />
			      </@toolbar>
			</@form>
		</@panelBody>
	</@panel>

<script type="text/javascript">

	var msgCategoryChange = function(){
		$('#contentTemplate').text('');
		$('#name').removeAttr("checked");
		$('#cardNo').removeAttr("checked");
		$('#cardNoE4').removeAttr("checked");
		$('#txnDate').removeAttr("checked");
		$('#txnTime').removeAttr("checked");
		$('#gender').removeAttr("checked");
		$('#orgName').removeAttr("checked");
		$('#orgCssPhone').removeAttr("checked");
		$('#patchType').removeAttr("checked");
		$('#product').removeAttr("checked");
		$('#description').removeAttr("checked");
		var msgCategory = $('#msgCategory').val();
		if(msgCategory == 'ECMS01'){//补件
			$('#patchTypeLabel').css("display","block");
			$('#productLabel').css("display","none");
			$('#descriptionLabel').css("display","none");
		}else if(msgCategory == 'ECMS02'){//拒绝
			$('#patchTypeLabel').css("display","none");
			$('#productLabel').css("display","block");
			$('#descriptionLabel').css("display","block");
		}else{//为空
			$('#patchTypeLabel').css("display","block");
			$('#productLabel').css("display","block");
			$('#descriptionLabel').css("display","block");
		}
	}
	
	function clickCheck(str,that){


        if($(that).is(':checked')){
			var contentTemplate = $('#contentTemplate').text();
			$('#textValue').val(contentTemplate);
			$('#contentTemplate').text(contentTemplate+str);
		}else{
			var textValue = $('#textValue').val();
			$('#contentTemplate').text(textValue);
		}
	}
</script>   
</@body>

