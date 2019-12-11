<style>
	.ruleActionButtonLine{
		padding:6px;
	}
	
	.ruleActionButtonLine select{
		height:26px;
	}
	
	.ruleActionLine{
		margin-bottom:5px;
	}
	.ruleActionLine .value,.ruleActionLine .value{
		height: 26px;
		width:180px;
	}
</style>

<script id="rule-action-line" type="text/x-template">
	<div class="ruleActionLine">
		
		<input type="hidden" class="property" v-model="property" />
		<span style="display:inline-block;width:135px;text-align:right;">{{propertyName}} : </span>
		<input type="hidden" class="actionType" v-model="actionType" />
		
		<span class="rightValueContainer">
			<span v-if="rightType=='input'">
				<input class="value" type="text"  v-model="value" />
			</span>
			<span v-if="rightType=='fieldSelect'">
				<select v-if="fieldSelectOptions!=''" class="value" v-model="value">
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
				<select v-if="dictSelectOptions!=''" class="value" v-model="value">
					<option value=""></option>
					<option v-for="(item,index) in dictSelectOptions " :value="item.cd">{{item.name}}</option>
				</select>
			</span>
			<span v-if="rightType=='tableSelect'">
                <select v-if="tableSelectOptions!=''" class="value" v-model="value">
                    <option value=""></option>
                    <option v-for="(item,index) in tableSelectOptions " :value="item.cd">{{item.name}}</option>
                </select>
            </span>
			<span v-if="rightType=='enumSelect'">
				<select v-if="enumSelectOptions!=''" class="value" v-model="value">
					<option value=""></option>
					<option v-for="(item,index) in enumSelectOptions " :value="item.cd">{{item.name}}</option>
				</select>
			</span>
			<span v-if="rightType=='between'">
				<input class="value" type="text" />和<input class="value" type="text" />
			</span>
		</span>
		
		<input type="button" class="actionLineDeleteBtn btn btn-xs btn-default" value="-删除" @click="actionLineDeleteBtn"/>
		
		<span class="text-danger" v-bind:class="{ 'has-wrong-input': errInfoHas }">{{errInfo}}</span>
	</div>
</script>
<script>
    console.log(outputVarList);
	Vue.component('rule-action-line',{
		template:'#rule-action-line',
		data:function(){
			return {
			//	var ruleActions = [{'property':'txnAmt','actionType':'C',value:'3',valueClassName:''},{'property':'txnType','actionType':'P',value:'3',valueClassName:''}];
				
				property: this.lineInfo.property,/*左值*/
			    actionType: this.lineInfo.actionType,/*动作类型*/
			    value:this.lineInfo.value,/*值*/
			    valueClassName:'',
			    
			    leftOptions:inputVarList,/*左值选项*/
			    customPropertyOptions:[],/*自定义变量选项*/
			    customFunctionOptions:st.functionFieldVars,/*自定义方法选项*/
			    
			    rightType:'',/*右值控件类型*/
			    fieldSelectOptions:{},
			    dictSelectOptions:{},
			    tableSelectOptions:{},
			    enumSelectOptions:{},
			    
			    //错误信息
                errInfo:'',
                errInfoHas:false
			};
		},
		computed:{
			propertyVar:function(){
		  		/*输入类型对象*/
				return st.getVarField(this.property,outputVarList);
		  	},
		  	propertyName:function(){
		  		/*输入类型对象*/
				return this.propertyVar.varName;
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
		  				this.fieldSelectOptions = st.leftOptionList;
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
		methods:{
			actionLineDeleteBtn:function(event){
				$(event.target).parent().remove();
			}
		},
		watch:{
		    value:function(val,oldVal){
                var errObj = st.valueCheck(this.propertyVar,this.value,(this.actionType=="P"?"fieldSelect":""));
                this.errInfo = errObj.msg;
                this.errInfoHas = !errObj.s;
            }
		},
		props:['lineInfo']
	});
</script>

<script id="rule-action" type="text/x-template">
	<div class="ruleAction">
		<div class="ruleActionButtonLine">
			<select class="propertyName">
			  <option value=""></option>
			  <optgroup v-if="cmpOutputVarList.length>0" label="输出变量">
			    <option v-for="(item,index) in cmpOutputVarList " :value="item.varCd">{{item.varName}}</option>
			  </optgroup>
			</select>
			<button @click="assignConstAction" class="btn btn-xs btn-warning">新增常量</button>	
			<button @click="assignPropertyAction" class="btn btn-xs btn-warning">新增变量</button>
		</div>
		<hr style="margin:2px 0;" />
		<rule-action-line v-for="(lineInfo, index) in ruleActions" :lineInfo="lineInfo"></rule-action-line>
	</div>
</script>

<script>
	Vue.component('rule-action',{
		template:'#rule-action',
		data:function(){
			return {
			    cmpOutputVarList : outputVarList,
				ruleActions:this.ruleactionlist
			};
		},
		methods:{
			assignConstAction:function(event){
				if(this.ruleActions==undefined){
					this.ruleActions = [];
				}
				var propertyName = $(event.target).parent().children(".propertyName").val();
				if(propertyName==''){
					return ;
				}
				this.ruleActions.push({'property':propertyName,'actionType':'C',value:'',valueClassName:''});
			},
			assignPropertyAction:function(){
				if(this.ruleActions==undefined){
					this.ruleActions = [];
				}
				var propertyName = $(event.target).parent().children(".propertyName").val();
				if(propertyName==''){
					return ;
				}
				this.ruleActions.push({'property':propertyName,'actionType':'P',value:'',valueClassName:''});
			}
		},
		computed:{
		},
		props:['ruleactionlist']
	});
	
	
</script>

<#-- 组件方法 -->
<script>
	/*动作列表*/
	st.getRuleactionlist = function(){
		var ruleActions = [];
		
		$t = $("#ruleActionList");
		$ruleActionLines = $(".ruleActionLine",$t);
		
		for(var i=0;i<$ruleActionLines.length;i++){
			var action = {};
		
			var $line = $($ruleActionLines[i]);
			var property = $line.children(".property").val();
			var actionType = $line.children(".actionType").val();
			var value = $(".value",$line).val();
			
			action.property = property;
			action.actionType = actionType;
			action.value = value;
			
			ruleActions.push(action);
		}
		return ruleActions;
	}
</script>

