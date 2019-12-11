<#include "/layout.ftl">
<@body>

<#macro dataTypeName datatype="">
    <#if datatype?? && datatype!="">
        <#if datatype=="string">
                                字符
        <#elseif datatype=="decimal">
                                数字
        <#elseif datatype=="bool">
                                布尔
        <#elseif datatype=="date">
                                日期
        </#if>
    </#if>
</#macro>
	
<style>
	ul.runtimeInfo{
		padding-left:0px;		
	}
	.runtimeInfo li{
		list-style-type:none;
	}
	.trial_table{border:solid #ddd; border-width:1px;}
	.trial_table td,.trial_table th{border: solid #ddd; border-width:0px 1px 1px 0px; padding:2px 5px;min-width:80px;font-weight:100;}
</style>

<script id="outTpl" type="text/html">
	{{each infos as info index}}
		<div>
			<h6 > </h6>
			<ul class="runtimeInfo">
				<li>策略方案 ：{{info.stClass}} / {{info.rulesetName}}</span></li>
				<li>触发规则 ：{{info.name}}</li>
				<li>触发时间 ：{{ info.currentTimeMillis | datetimeFormat }} </li>
				<li><span style="float:left">输出变量 ：</span>
					<table class="trial_table">
						<thead>
							<tr>
								<th>变量代码</th>
								<th>旧值</th>
								<th>新值</th>
							</tr>
						</thead>
						<tbody>
							{{each info.outputs as outInfo outKey}}
								<tr>
									<td>{{outKey}}</td>
									<td>{{outInfo.old}}</td>
									<td>{{outInfo.new}}</td>
								</tr>
							{{/each}}
						</tbody>
					</table>
				</li>
			</ul>
			<hr />
		</div>
	{{/each}}
</script>
<script>
	function renderOutLog(ruleRuntimeInfos){
		template.helper("datetimeFormat", ar_.datetimeFormat);
		var html = template('outTpl',{infos:ruleRuntimeInfos});
		$("#outContentId").append(html);
	}
</script>	
	
<script>

	var loadIndex;
	
	$(function(){
		$(".selectCheck").on("click",function(){
			
			var checked = $(this).is(':checked');
		
			$select = $(this).parent().prevAll("select");
			$input = $(this).parent().prevAll("input");
			
			if(checked){
				$select.attr("disabled","disabled");
				$select.css("display","none");
				$input.attr("disabled",false);
				$input.css("display","block");
			}else{
				$select.attr("disabled",false);
				$select.css("display","block");
				$input.attr("disabled","disabled");
				$input.css("display","none");
			}
			
		});
		
		$("#inputRestBtn").on("click",function(){
			$("#outputRestBtn").trigger('click');
		});
		
		$("#outputRestBtn").on('click',function(){
			var $outContent = $("#outContentId").empty(); 
		});
		
	});
	
	function bfSubmit(){
		$("#outputRestBtn").trigger('click');
		loadIndex = layer.load(2, {shade: false});
	}
	
	function aftSubmit(res){
		
		var $outForm = $("#outForm");
		$(".selectNoSelected").remove();
		$outForm[0].reset();
		
		var $outContent = $("#outContentId");
		$outContent.empty(); 
		if(res.s==true){
			
			var trailVar = res.obj;
			var out = trailVar.out;
			for(var x in out){
				var value = out[x];
			
				var $name = $("[name="+x+"]",$outForm)
				$name.val(value);
			
				if($name.is("select")){
					var selectedIndex  = $name[0].selectedIndex;
					if(selectedIndex<=0){
						$name.parent().after($("<div>",{class:"selectNoSelected",text:value,style:"padding-left:62px;margin-top:-10px;"}));
					}
				};
			}
			
			var ruleRuntimeInfos = trailVar.fact._rules;
			
			console.log(trailVar);
			if(ruleRuntimeInfos!=undefined && ruleRuntimeInfos!=null && ruleRuntimeInfos.length > 0){
				renderOutLog(ruleRuntimeInfos);
			}else{
				$outContent.append("<h5>触发记录...</h5>无规则触发");
			}
		}else{
			$outContent.append("<h5>规则配置错误，错误信息</h5>"+res.msg);
			$outContent.append("<h5>drl信息</h5>"+res.obj.drl);
		}
		
		layer.close(loadIndex);
		$("#subBtn").unicornButtonDisable(false); 
	}
</script>	
	
	
	
<table class="table">
<tr>
<td style="width:50%" valign="top">
<@panel head="输入变量" class="" sense="info">
<@panelBody class="" style="padding: 5px 0px;">
    <@form id="inputForm" cols="2" action="dmp/strategyTrial/trial?stId=${tmDmpStrategy.stId}" multi_submit="true" before="bfSubmit" after="aftSubmit">
        <@row>
            <@toolbar style="margin-top:0px;padding-top:0px;" align="right" right_offset="2" >
                <@submitButton id="subBtn" name="试算"></@submitButton>
                <@resetButton id="inputRestBtn" name="清空" sense="default"></@resetButton>
            </@toolbar>
            <@hr style="margin-bottom:10px;margin-top: 0px;"/>
        </@row>
        <#list inputFieldVars as fieldVar>
            
            <#if fieldVar_index%2 = 0>
                <div class="row " >
            </#if>
            
            <@field label=fieldVar.varName >
                <div class="input-group col-ar-36">
                      
                      <span class="input-group-addon" style="padding:0px;width:32px;" title="${fieldVar.dataType}" ><@dataTypeName datatype=fieldVar.dataType /></span>
                      <#if fieldVar.optionType=="A" >
                        <input type="text" name="input.${fieldVar.varCd}" class="form-control" />
                      <#elseif fieldVar.optionType=="D">
                        <input type="text" name="input.${fieldVar.varCd}" disabled="disabled" class="form-control" style="display:none;" />
                        <select name="input.${fieldVar.varCd}" class="form-control"  >
                            <option value=""></option>
                            <#list fieldVar.options as opt >
                                <option value="${opt.cd}">${opt.cd}-${opt.name}</option>
                            </#list>
                        </select>
                        <span class="input-group-addon" style="padding:0px;width:20px;background-color:white;" title="手动输入"> 
                            <input class="selectCheck" type="checkbox" /> 
                        </span>
                      <#elseif fieldVar.optionType=="T">
                        <input type="text" name="input.${fieldVar.varCd}" disabled="disabled" class="form-control" style="display:none;" />
                        <select name="input.${fieldVar.varCd}" class="form-control"  >
                            <option value=""></option>
                            <#list fieldVar.options as opt >
                                <option value="${opt.cd}">${opt.cd}-${opt.name}</option>
                            </#list>
                        </select>
                        <span class="input-group-addon" style="padding:0px;width:20px;background-color:white;" title="手动输入"> 
                            <input class="selectCheck" type="checkbox" /> 
                        </span>
                      </#if>
                      
                </div>
            </@field>
                
            <#if fieldVar_index%2 = 1>
                </div>
            </#if>
        </#list>
    </@form>
</@panelBody>
</@panel>
</td>
<#-- 结果 -->
<td style="width:50%" valign="top">
    <@panel head="输出变量" class="" sense="warning">
    <@panelBody style="padding: 5px 0px;">
        <@form id="outForm" cols="2">
            <@row>
                <@toolbar style="margin-top:0px;padding-top:0px;" align="left" class="col-ar-offset-1">
                    <@resetButton id="outputRestBtn" name="清空" sense="default"></@resetButton>
                </@toolbar>
                <@hr style="margin-bottom:10px;margin-top: 0px;"/>
            </@row>
            
            <#list outputFieldVars as fieldVar>
                
                <#if fieldVar_index%2 = 0>
                    <div class="row " >
                </#if>
                
                <@field label=fieldVar.varName >
                    <div class="input-group col-ar-36">
                          
                          <span class="input-group-addon" style="padding:0px;width:32px;" title="${fieldVar.dataType}" ><@dataTypeName datatype=fieldVar.dataType /></span>
                          <#if fieldVar.optionType=="A" >
                            <input type="text" name="${fieldVar.varCd}" class="form-control" />
                          <#elseif fieldVar.optionType=="D">
                            <select name="${fieldVar.varCd}" class="form-control"  >
                                <option value=""></option>
                                <#list fieldVar.options as opt >
                                    <option value="${opt.cd}">${opt.cd}-${opt.name}</option>
                                </#list>
                            </select>
                          <#elseif fieldVar.optionType=="T">
                            <input type="text" name="input.${fieldVar.varCd}" disabled="disabled" class="form-control" style="display:none;" />
                            <select name="input.${fieldVar.varCd}" class="form-control"  >
                                <option value=""></option>
                                <#list fieldVar.options as opt >
                                    <option value="${opt.cd}">${opt.cd}-${opt.name}</option>
                                </#list>
                            </select>
                            <span class="input-group-addon" style="padding:0px;width:20px;background-color:white;" title="手动输入"> 
                                <input class="selectCheck" type="checkbox" /> 
                            </span>
                          </#if>
                    </div>
                </@field>
                
                <#if fieldVar_index%2 = 1>
                    </div>
                </#if>
            </#list>
        </@form>
        
        <div style="padding:10px;clear:both;">
        <hr/>
            <div id="outContentId">
            <h5>触发记录...</h5>
            </div>
        </div>
    </@panelBody>
    </@panel>   
</td>
</tr>
</table>

 </@body>   