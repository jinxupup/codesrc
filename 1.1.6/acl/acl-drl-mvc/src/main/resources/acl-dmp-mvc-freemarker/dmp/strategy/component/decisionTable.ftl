<style>
.decision-table select,.decision-table input{
	height: 26px;
}
</style>

<script id="decision-table-conditon-field" type="text/x-template">
	<div class="decision-table-conditon-field">
		<input type="hidden" class="condition-uuid" v-model="uuid" />
		<span class="condition-rightValueContainer">
			<span v-if="rightType=='input'">
				<input class="rightValue" type="text" style="width:100%;min-width:100px;" v-model="rightValue"/>
			</span>
			<span v-if="rightType=='fieldSelect'">
				<select v-if="fieldSelectOptions!=''" style="width:100%;min-width:100px;" class="rightValue" v-model="rightValue">
					<option value=""></option>
					<#--<option v-for="(item,index) in fieldSelectOptions " :value="item.varCd">{{item.varName}}</option>-->
					<optgroup v-if="leftOptions.length>0" label="输入变量">
					    <option v-for="(item,index) in leftOptions " :value="item.varCd">{{item.varName}}</option>
					</optgroup>
				    <optgroup v-if="customFunctionOptions.length>0" label="自定义函数">
				        <option v-for="(item,index) in customFunctionOptions " :value="item.varCd">{{item.varName}}</option>
				    </optgroup>
				</select>
			</span>
			<span v-if="rightType=='dictSelect'">
				<select v-if="dictSelectOptions!=''" class="rightValue" style="width:100%;min-width:100px;" v-model="rightValue">
					<option value=""></option>
					<option v-for="(item,index) in dictSelectOptions " :value="item.cd">{{item.name}}</option>
				</select>
			</span>
			<span v-if="rightType=='tableSelect'">
                <select v-if="tableSelectOptions!=''" class="rightValue" style="width:100%;min-width:100px;" v-model="rightValue">
                    <option value=""></option>
                    <option v-for="(item,index) in tableSelectOptions " :value="item.cd">{{item.name}}</option>
                </select>
            </span>
			<span v-if="rightType=='enumSelect'">
				<select v-if="enumSelectOptions!=''" class="rightValue" v-model="rightValue">
					<option value=""></option>
					<option v-for="(item,index) in enumSelectOptions " :value="item.cd">{{item.name}}</option>
				</select>
			</span>
			<span v-if="rightType=='between'">
				<input class="rightValue" type="text" />和<input class="rightValue" type="text" />
			</span>
			<span v-if="rightType=='null'">
				<input class="rightValue" type="hidden" value=""/>
			</span>
			<span class="text-danger" v-bind:class="{ 'has-wrong-input': errInfoHas }">{{errInfo}}</span>
		</span>
	</div>	
</script>
<script>
	Vue.component("decision-table-conditon-field",{
		template:'#decision-table-conditon-field',
		data:function(){
			var conditionData = {};
			if(this.row!=undefined&&this.row.conditionData!=undefined){
				conditionData = this.row.conditionData;
			}
			return {
				uuid:this.condition.uuid,
				fieldName:this.condition.criteria.fieldName,
				operator:this.condition.criteria.operator,
				
				rightValue:conditionData[this.condition.uuid],
				
				leftOptions:inputVarList,/*左值选项*/
			    customPropertyOptions:[],/*自定义变量选项*/
			    customFunctionOptions:st.functionFieldVars,/*自定义方法选项*/
				
			    fieldSelectOptions:{},
			    dictSelectOptions:{},
			    tableSelectOptions:{},
			    enumSelectOptions:{},
			    
			    //错误信息
			    errInfo:"",
                errInfoHas:false
			};
		},
		props:['condition','row'],
    	  computed:{
    	  	inputVar:function(){
    	  		/*输入类型对象*/
    			return st.getVarField(this.fieldName,st.leftOptionList);
    	  	},
    	  	rightType:function(){
    	  		var r = st.rightInputType(this.fieldName,st.leftOptionList,this.operator);
    	  		
    	  		this.fieldSelectOptions={};
    		    this.dictSelectOptions={};
    		    this.tableSelectOptions={};
    		    this.enumSelectOptions={};
    	  		
    	  		var i = this.inputVar; 
    	  		if(r!='null'){
    	  			if(r=='fieldSelect'){
    	  				this.fieldSelectOptions = {};
    	  			}else if(r=='dictSelect'){
    	  				this.dictSelectOptions = i.options;
    	  			}else if(r=='tableSelect'){
                        this.tableSelectOptions = i.options;
                    }else if(r=='enumSelect'){
    	  				this.enumSelectOptions = i.options;
    	  			}else if(r=='date'){
    	  				
    	  			}else if(r=='between'){
    	  				
    	  			}else{
    	  				
    	  			}
    	  		}
    	  		return r;
    	  	}
    	  },
    	watch:{
            rightValue:function(val,oldVal){
                
                console.log(this.inputVar ,this.rightValue,this.rightType);
            
                var errObj = st.valueCheck(this.inputVar,this.rightValue,this.rightType);
                this.errInfo = errObj.msg;
                this.errInfoHas = !errObj.s;
            }
    	}
	});
</script>

<script id="decision-table-action-field" type="text/x-template">
	<div class="decision-table-action-field">
		<input type="hidden" class="action-uuid" v-model="uuid" />
		<span class="action-rightValueContainer">
			<span v-if="rightType=='input'">
				<input class="rightValue" type="text"  style="width:100%;min-width:100px;" v-model="rightValue"/>
			</span>
			<span v-if="rightType=='fieldSelect'">
				<select v-if="fieldSelectOptions!=''" class="rightValue" style="width:100%;min-width:100px;" v-model="rightValue">
					<option value=""></option>
					<#-- <option v-for="(item,index) in fieldSelectOptions " :value="item.varCd">{{item.varName}}</option> -->
					<optgroup v-if="leftOptions.length>0" label="输入变量">
					    <option v-for="(item,index) in leftOptions " :value="item.varCd">{{item.varName}}</option>
					</optgroup>
				    <optgroup v-if="customFunctionOptions.length>0" label="自定义函数">
				        <option v-for="(item,index) in customFunctionOptions " :value="item.varCd">{{item.varName}}</option>
				    </optgroup>
				</select>
			</span>
			<span v-if="rightType=='dictSelect'">
				<select v-if="dictSelectOptions!=''" class="rightValue" style="width:100%;min-width:100px;" v-model="rightValue">
					<option value=""></option>
					<option v-for="(item,index) in dictSelectOptions " :value="item.cd">{{item.name}}</option>
				</select>
			</span>
			<span v-if="rightType=='tableSelect'">
                <select v-if="tableSelectOptions!=''" class="rightValue" style="width:100%;min-width:100px;" v-model="rightValue">
                    <option value=""></option>
                    <option v-for="(item,index) in tableSelectOptions " :value="item.cd">{{item.name}}</option>
                </select>
            </span>
			<span v-if="rightType=='enumSelect'">
				<select v-if="enumSelectOptions!=''" class="rightValue" style="width:100%;min-width:100px;" v-model="rightValue">
					<option value=""></option>
					<option v-for="(item,index) in enumSelectOptions " :value="item.cd">{{item.name}}</option>
				</select>
			</span>
			<span v-if="rightType=='between'">
				<input class="rightValue" type="text" />和<input class="rightValue" type="text" />
			</span>
			<span class="text-danger" v-bind:class="{ 'has-wrong-input': errInfoHas }">{{errInfo}}</span>
		</span>
	</div>	
</script>
<script>
	Vue.component("decision-table-action-field",{
		template:'#decision-table-action-field',
		data:function(){
			var actionData = {};
			if(this.row!=undefined&&this.row.actionData!=undefined){
				actionData = this.row.actionData;
			}
			
			return {
				uuid:this.actionvar.uuid,
				property:this.actionvar.action.property,
				actionType:this.actionvar.action.actionType,
				
				rightValue:actionData[this.actionvar.uuid],
				
				leftOptions:inputVarList,/*左值选项*/
			    customPropertyOptions:[],/*自定义变量选项*/
			    customFunctionOptions:st.functionFieldVars,/*自定义方法选项*/
				
			    fieldSelectOptions:{},
			    dictSelectOptions:{},
			    tableSelectOptions:{},
			    enumSelectOptions:{},
			    
			    //错误信息
                errInfo:"",
                errInfoHas:false
			};
		},
		props:['actionvar','row'],
		  computed:{
			propertyVar:function(){
		  		/*输入类型对象*/
				return st.getVarField(this.property,outputVarList);
		  	},
	  		rightType:function(){
	  			
	  			var r = 'null';
	  			if("C"==this.actionType){
	  				r = st.rightInputType(this.property,outputVarList);
	  			}else if("P"==this.actionType){
	  				r = st.rightInputType(this.property,outputVarList,'equalsField');
	  			}
		  		
		  		this.fieldSelectOptions={};
			    this.dictSelectOptions={};
			    this.tableSelectOptions={};
			    this.enumSelectOptions={};
		  		
		  		var i = this.propertyVar;
		  		
		  		if(r!='null'){
		  			if(r=='input'){
		  				this.fieldSelectOptions = {};
		  			}else if(r=='fieldSelect'){
		  				this.fieldSelectOptions = inputVarList;
		  			}else if(r=='dictSelect'){
		  				this.dictSelectOptions = i.options;
		  			}else if(r=='tableSelect'){
                        this.tableSelectOptions = i.options;
                    }else if(r=='enumSelect'){
		  				this.enumSelectOptions = i.options;
		  			}else if(r=='date'){
		  				
		  			}else if(r=='between'){
		  				
		  			}else{
		  				
		  			}
		  		}
		  		return r;
		  	}
		},
		watch:{
		  rightValue:function(val,oldVal){
                console.log(this.propertyVar);
                console.log(this.value);
                console.log(this.rightValue);
                console.log(this.rightType);
                console.log((this.actionType=="P"?"fieldSelect":""));
            
                var errObj = st.valueCheck(this.propertyVar,this.rightValue,(this.actionType=="P"?"fieldSelect":""));
                this.errInfo = errObj.msg;
                this.errInfoHas = !errObj.s;
            }
		}
	});
</script>

<script id="decision-table" type="text/x-template">
	<div class="decision-table">
	<div style="height:40px;float:left;">
		<button class="conditionLineAddBtn btn btn-sm btn-primary" @click="addNewLineBtn">新建行</button>
	</div>
	<div style="height:468px;overflow:scroll;clear:both;">
	<table class="decision-table table table-striped table-bordered table-hover table-condensed">
		<thead>
			<tr >
				<th style="width:30px;font-weight:100;">操作</th>
				<th style="width:50px;font-weight:100;">优先级</th>
				<th style="width:100px;font-weight:100;">规则名称</th>
				<th style="width:40px;font-weight:100;">启用</th>
				
				<th v-for="(condition,index) in conditions" :title="condition.title" style="color: #31708f;background-color: #d9edf7;font-weight:100;"> 
					<span>{{condition.name}}</span>
				</th>
				
				<th v-for="(action,index) in actions"  :title="action.title" style="color: #8a6d3b;background-color: #fcf8e3;font-weight:100;">
					<span>{{action.name}}</span>
				</th>
			</tr>
		</thead>
		<tbody>
			<tr v-for="(row,rowIndex) in rows">
				<td><button type="button" class="rowLineDeleteBtn btn btn-xs btn-danger"  @click="rowLineDeleteBtn">删除</button></td>
				<td><input class="priority" type="text" style="width:100%;min-width:50px;" v-model="row.priority" /></td>
				<td><input class="desc" type="text" style="width:100%;min-width:100px;" v-model="row.desc"   /></td>
				<td><select class="enabled" style="width:100%;min-width:40px;"  v-model="row.enabled" ><option value="Y">是</option><option value="N">否</option></select>
				</td>
				
				<td v-for="(condition,index) in conditions">
					<decision-table-conditon-field :condition="condition" :row="row"></decision-table-conditon-field>
				</td>
				
				<td v-for="(action,index) in actions">
					<decision-table-action-field :actionvar="action" :row="row"></decision-table-action-field>
				</td>
			</tr>
		</tbody>
	</table>
	</div>
	</div>
</script>
<script>
	Vue.component("decision-table",{
		template:'#decision-table',
		data:function(){
			var innerRows = [];
			if(this.decisiontablevar.rows!=undefined){
				innerRows = this.decisiontablevar.rows;
			} 
			
			return {
				conditions:this.decisiontablevar.conditions,
				actions:this.decisiontablevar.actions,
				rows:innerRows
			};
		},
		methods:{
			rowLineDeleteBtn:function(event){
				$(event.target).parent().parent().remove();
			},
			addNewLineBtn:function(event){
				this.rows.push({enabled:"Y"});
			}
		},
		props:['decisiontablevar']
	});
</script>
<div id="decition-table-rule">
	<decision-table :decisiontablevar="decisiontablevar"></decision-table>
</div>
<script>
	//var decisiontablevar = {"actions":[{"action":{"actionType":"C","property":"txnAmt"},"name":"交易金额","stringParam":false,"title":"[交易金额][常量分配]","uuid":"b7e1c9af1d46482b8e7e634438e7fde5"},{"action":{"actionType":"P","property":"MCC"},"name":"MCC","stringParam":false,"title":"[MCC][变量分配]","uuid":"c3e422513efc4bf9bd9297ee62fd5710"}],"conditions":[{"criteria":{"fieldName":"txnType","operator":"notEqualField"},"name":"交易类型","title":"[交易类型][不等于字段]","uuid":"c282a46d2d4142a6871f03a8ce280702"},{"criteria":{"fieldName":"MCC","operator":"equalsField"},"name":"MCC","title":"[MCC][等于字段]","uuid":"65975d37899e4f99b1a87c7a700b797c"}],"rows":[{"actionData":{"c3e422513efc4bf9bd9297ee62fd5710":"MCC","b7e1c9af1d46482b8e7e634438e7fde5":"1"},"conditionData":{"c282a46d2d4142a6871f03a8ce280702":"txnAmt","65975d37899e4f99b1a87c7a700b797c":"txnType"},"desc":"2","enabled":"Y","priority":1},{"actionData":{"c3e422513efc4bf9bd9297ee62fd5710":"","b7e1c9af1d46482b8e7e634438e7fde5":""},"conditionData":{"c282a46d2d4142a6871f03a8ce280702":"","65975d37899e4f99b1a87c7a700b797c":""},"desc":"5","enabled":"Y","priority":25}]};
	var decisiontablevar = $.parseJSON(${decisionTableVar});
	var vdt = new Vue({
		el:'#decition-table-rule',
		data:{
			decisiontablevar:decisiontablevar
		}
	});
</script>

<script>
	st.getDecisionTableRows = function(){
		var rows = [];
		var $trs = $("#decition-table-rule tr");
		for(var i=1;i<$trs.length;i++){
			var row = {}; 
			var conditionData = {};
			var actionData = {};
		
			var $tr = $trs[i];
			var $tds = $("td",$tr);
			
			var $td1 = $tds[1];
			var $td2 = $tds[2];
			var $td3 = $tds[3];
			row.priority = $("input",$td1).val();
			row.desc = $("input",$td2).val();
			row.enabled = $("input",$td3).val();
			
			for(var j=4;j<$tds.length;j++){
				var $td = $tds[j];
				var $div = $("div:first",$td);
				if($div.hasClass("decision-table-conditon-field")){
					var uuid = $(".condition-uuid",$div).val();
					var obj = $(".rightValue",$div).val();
					conditionData[uuid] = obj;
				}else if($div.hasClass("decision-table-action-field")){
					var uuid = $(".action-uuid",$div).val();
					var obj = $(".rightValue",$div).val();
					actionData[uuid] = obj;
				}
			}
			row.conditionData = conditionData;
			row.actionData = actionData;
			rows.push(row);
		}
		return rows;
	}
</script>

