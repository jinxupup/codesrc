<@fieldset legend="产品基础参数">
	<@row>
		<@field id="productCd" label="产品代码">
			<@input name="productCd" value="${(tmProduct.productCd)!}" valid={"notempty":"true"} />
		</@field>
		<@field id="productDesc" label="产品名称描述">
			<@input name="productDesc" value="${(tmProduct.productDesc)!}" valid={"notempty":"true"} />
		</@field>
		<#--<@field id="productType" label="产品类别">
			<@dictSelect dicttype="ProductType" change="productTypeChange" nullable="false" name="productType" value="${(tmProduct.productType)!}" valid={"notempty":"true"}/>
		</@field>
		<@field id="brand" label="卡品牌">
			<@dictSelect dicttype="CardBrand" name="brand" value="${(tmProduct.brand)!}" />
		</@field>-->
		<@field id="cardClass" label="卡等级">
			<@dictSelect dicttype="CardClass" name="cardClass" value="${(tmProduct.cardClass)!}"/>
		</@field>
		<@field id="bin" label="卡BIN">
			<@input name="bin" value="${(tmProduct.bin)!}" />
		</@field>
		<#--<@field id="cardNoLen" label="卡号长度">
			<@input name="cardNoLen" value="${(tmProduct.cardNoLen)!}" />
		</@field>-->
		<@field id="cardNoRangeCeil" label="卡号段上限">
			<@input name="cardNoRangeCeil" value="${(tmProduct.cardNoRangeCeil)!}" />
		</@field>
		<@field id="cardNoRangeFlr" label="卡号段下限">
			<@input name="cardNoRangeFlr" value="${(tmProduct.cardNoRangeFlr)!}" />
		</@field>
		<@field id="fabricationInd" label="是否实体卡">
			<@dictSelect dicttype="Indicator" name="fabricationInd" value="${(tmProduct.fabricationInd)!}" />
		</@field>
		<@field id="subCardType" label="卡类型子类型">
			<@dictSelect dicttype="CardSubType" name="subCardType" value="${(tmProduct.subCardType)!}" />
		</@field>
		<@field id="ifEnableAttachCard" label="是否支持附卡">
			<@dictSelect dicttype="Indicator" name="ifEnableAttachCard" value="${(tmProduct.ifEnableAttachCard)!}" />
		</@field>
		<#--<@field id="isInstalment" label="是否支持分期">
			<@dictSelect dicttype="Indicator" name="isInstalment"   value="${(tmProduct.isInstalment)!'N'}" />
		</@field>-->
		<@field id="cardFace" label="卡面信息" field_ar="24" label_ar="7" input_ar="29">
			<@multipleSelect name="cardFace" value="${(cardFace)!''}" options=dict_('tableMap','com.jjb.acl.infrastructure.TmAclDict','{"type":"CardFace"}','code','codeName') showfilter="true"  valid={"notempty":"true"}/>
		</@field>
		<#--<@field id="subCreditType" label="信贷子类型">
			<@dictSelect dicttype="LoanUse" name="subCreditType" value="${(tmProduct.subCreditType)!''}" />
		</@field>-->
		<@field id="approvalMaximum" label="最高审批额度">
			<@input name="approvalMaximum" value="${(tmProduct.approvalMaximum)!}" valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请输入正确的审批额度"}/>
		</@field>
		<#--<@field id="supProductCd" label="同类型上级产品">
			<@select name="supProductCd" options=ecms_('tableMap','productForStatus','A,B,C') value="${(tmProduct.supProductCd)!}"/>
		</@field>-->
		<@field id="productStatus" label="产品状态">
			<@dictSelect dicttype="ProductStatus" name="productStatus" value="${(tmProduct.productStatus)!}" valid={"notempty":"true"} />
		</@field>
		<#--<@field id="ifEnableHairpin" label="独立法人">
			<@dictSelect dicttype="IfEnableHairpin" change="setOwningBranch" name="ifEnableHairpin" value="${(tmProduct.ifEnableHairpin)!}" valid={"notempty":"true"} />
		</@field>
		<@field label="网点机构" field_ar="24" label_ar="7" input_ar="29">
			<@multipleSelect class="ifDisabled" name="owningBranch" value="${(owningBranch)!''}" options=ecms_('tableMap','branchMap','independEntity')  showfilter="true" select_all="true" single="false" />
		</@field>-->
		
		<@field id="" label="jpaVersion" hidden="true">
			<@input name="jpaVersion" value="${(tmProduct.jpaVersion)!}"/>
		</@field>
	</@row>
</@fieldset> 
<#--<@fieldset legend="征审参数">
	<@row>
		<@field id="isCellPboc" label="是否联网查询人行">
			<@dictSelect dicttype="Indicator" name="isCellPboc"  value="${(tmProduct.isCellPboc)!'N'}" valid={"notempty":"true"}/>
		</@field>
		<@field id="procdefKey" label="流程选择">
			<@dictSelect dicttype="ProcdefKey" name="procdefKey" value="${(tmProduct.procdefKey)!}" valid={"notempty":"true"} />
		</@field>
		<@field id="pointRule" label="对应评分表代号">
			<@dictSelect dicttype="PointRule" name="pointRule" value="${(tmProduct.pointRule)!}" valid={"notempty":"true"} />
			<@select name="pointRule" options=ecms_('tableMap','productForPointRule','') value="${(tmProduct.pointRule)!}" valid={"notempty":"false"}/>
		</@field>
		<@field id="ifRealtimeIssuing" label="是否实时建账">
			<@dictSelect dicttype="Indicator" name="ifRealtimeIssuing"   value="${(tmProduct.ifRealtimeIssuing)!'Y'}" valid={"notempty":"true"} />
		</@field>
		<@field id="riskProductCd" label="决策产品代码">
			<@dictSelect dicttype="RiskProductCode" name="riskProductCd"   value="${(tmProduct.riskProductCd)!'Y'}" valid={"notempty":"true"} />
		</@field>
		<@field id="lendingMethod" label="放款方式">
			<@dictSelect dicttype="LendingMethod" name="lendingMethod" nullable="true"  value="${(tmProduct.lendingMethod)!}" />
		</@field>
		<@field id="instalCreditFrozenDays" label="放款操作有效天数">
			<@input name="instalCreditFrozenDays"   value="${(tmProduct.instalCreditFrozenDays)!}" valid={"regexp or pattern":"^([1-9]\\d|\\d)$","regexp-message":"请输入0-99的整数"} />
		</@field>
		<@field id="ifUseCustomRate" label="是否使用定制化费率">
			<@dictSelect dicttype="Indicator" name="ifUseCustomRate" nullable="true"  value="${(tmProduct.ifUseCustomRate)!}" />
		</@field>
	</@row>
</@fieldset> -->
<script type="text/javascript">
	$(function(){
		var productType = '${(tmProduct.productType)!}';
		changeProductType(productType);
	});

   function setOwningBranch(that) {
        var enable = $(that).val();
        $.ajax({
            type: "POST",
            url: "ifEnableHairpinChange",
            data: {"enable": enable},
            success: function (data) {
                var jsonData = eval('(' + data + ')');
                $("#owningBranch").html("");
                for(var key in jsonData){
                	alert(key);
                	$("#owningBranch").append("<option value='" + key + "'>" +key + "-" + jsonData[key] + "</option>");              
				}
                $('#owningBranch').multipleSelect('refresh');
            }
        });
	}

	function changeLendingMethod(){
		var ifRealtimeIssuing = $("#ifRealtimeIssuing").val();
		var lendingMethod = $("#lendingMethod").val();
		if(lendingMethod=="O" && ifRealtimeIssuing=="N"){
			$("#lendingMethod").val("B");
			alert("非实施发卡方式只支持批量放款!");
		}	
	}
	
	function changeIfEnableHairpin(){
		var ifRealtimeIssuing = $("#ifRealtimeIssuing").val();
		var lendingMethod = $("lendingMethod").val();
		if(ifRealtimeIssuing=='N'){
			$("#lendingMethod").val("B");
		}
	}
	<#--产品类型改变-->
	var productTypeChange = function(that){
		var typeVal = $(that).val();
		changeProductType(typeVal);
	}
	function changeProductType(typeVal){
		if(typeVal!=null && typeVal=='F'){//如果是虚拟卡(消金类)
			$('#brand').css("display","none");
			$('#cardClass').css("display","none");
			$('#bin').css("display","none");
			$('#cardNoLen').css("display","none");
			$('#cardNoRangeCeil').css("display","none");
			$('#cardNoRangeFlr').css("display","none");
			$('#fabricationInd').css("display","none");
			$('#subCardType').css("display","none");
			$('#ifEnableAttachCard').css("display","none");
			$('#isInstalment').css("display","none");
			$('#cardFace').css("display","none");
			
			$('#subCreditType').css("display","true");
			
		}else if(typeVal!=null && typeVal=='C'){//如果是贷记卡
			$('#brand').css("display","true");
			$('#cardClass').css("display","true");
			$('#bin').css("display","true");
			$('#cardNoLen').css("display","true");
			$('#cardNoRangeCeil').css("display","true");
			$('#cardNoRangeFlr').css("display","true");
			$('#fabricationInd').css("display","true");
			$('#subCardType').css("display","true");
			$('#ifEnableAttachCard').css("display","true");
			$('#isInstalment').css("display","true");
			$('#cardFace').css("display","true");
			
			$('#subCreditType').css("display","none");
		}
	}
	
</script>