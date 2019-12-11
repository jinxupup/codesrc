<style>
.ruleTree select,.ruleTree input{
	height: 26px;
}
.fa-minus-square:hover{
	cursor:pointer;
	background:gray;
}
</style>

<#--START rule-line -->
<script id="rule-line" type="text/x-template">
	<div class="ruleLine" style="white-space:nowrap;margin-bottom:6px;">
		<i class="fa fa-minus-square" title="删除" style="color:red;" @click="deleteRuleLine"></i>
		<select class="fieldName" @change="leftChange" v-model="fieldNameII">
		  <option value=""></option>
		  <optgroup v-if="leftOptions.length>0" label="输入变量">
		    <option v-for="(item,index) in leftOptions " :value="item.varCd">{{item.varName}}</option>
		  </optgroup>
		  
		   <optgroup v-if="customFunctionOptions.length>0" label="自定义函数">
		    <option v-for="(item,index) in customFunctionOptions " :value="item.varCd">{{item.varName}}</option>
		  </optgroup>
		
		  <optgroup v-if="customPropertyOptions.length>0" label="自定义变量">
		    <option v-for="(item,index) in customPropertyOptions " :value="item.varCd">{{item.varName}}</option>
		  </optgroup>
		</select>
		
		<select v-if="fieldNameII!=''" class="operator" v-model="operatorII">
			<option value=""></option>
			<option v-for="(item,index) in operatorOptions " :value="item.op">
				{{item.name}}
			</option>
		</select>
		
		<span class="rightValueContainer">
			<span v-if="rightType=='input'">
				<input class="rightValue" type="text"  v-model="rightValue" @blur="rightValueBlur"/>
			</span>
			<span v-if="rightType=='fieldSelect'">
				<select v-if="fieldSelectOptions!=''" class="rightValue" v-model="rightValue">
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
				<select v-if="dictSelectOptions!=''" class="rightValue" v-model="rightValue">
					<option value=""></option>
					<option v-for="(item,index) in dictSelectOptions " :value="item.cd">{{item.name}}</option>
				</select>
			</span>
			<span v-if="rightType=='tableSelect'">
                <select v-if="tableSelectOptions!=''" class="rightValue" v-model="rightValue">
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
		    <span class="text-danger" v-bind:class="{ 'has-wrong-input': errInfoHas }">{{errInfo}}</span>
		</span>
	</div>
</script>

<script type="text/javascript">
	Vue.component("rule-line",{
	  template:"#rule-line",
	  props:['lineInfo'],
	  data: function(){
	  	return {
		    fieldNameII: this.lineInfo.fieldName,/*左值*/
		    operatorII: this.lineInfo.operator,/*操作符值*/
		    rightValue:this.lineInfo.value,
		    rightValueClass:'',
		    value:'',
		    
		    rightVal:'',/*右值*/
		    rightVal2:'',
		    
		    leftOptions:inputVarList,/*左值选项*/
		    customPropertyOptions:[],/*自定义变量选项*/
		    customFunctionOptions:st.functionFieldVars,/*自定义方法选项*/
		    operatorOptions:operator_all,/*操作符选项*/
		    
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
	  methods:{
	  	leftChange:function(){
	  		
	  	},
	  	deleteRuleLine:function(event){
	  		$(event.target).parent().remove();
	  	},
	  	rightValueBlur:function(event){
	  		/*
	  		var linedata = {fieldName:this.fieldNameII,operator:this.operatorII,rightValue:this.rightValue,rightValueClass:this.rightValueClass};
	  		this.$emit("emitUpValue",linedata);
	  		*/
	  	}
	  },
	  computed:{
	  	inputVar:function(){
	  		/*输入类型对象*/
			return st.getVarField(this.fieldNameII,st.leftOptionList);
	  	},
	  	rightType:function(){
	  		var r = st.rightInputType(this.fieldNameII,st.leftOptionList,this.operatorII);
	  		
	  		this.fieldSelectOptions={};
		    this.dictSelectOptions={};
		    this.tableSelectOptions={};
		    this.enumSelectOptions={};
	  		
	  		var i = this.inputVar;
	  		if(r!='null'){
	  			if(r=='fieldSelect'){
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
	  watch:{
	  	fieldNameII:function(val, oldVal){
	  		this.operatorOptions = operator_all;
	  		this.operatorII = '';
	  		this.value = '';
	  		this.rightValue = '';
	  		this.rightValueClass = '';
	  	},
	  	operatorII:function(val, oldVal){
	  		this.value = '';
	  		this.rightValue = '';
	  		this.rightValueClass = '';
	  	},
	  	rightValue:function(val,oldVal){
	  		var errObj = st.valueCheck(this.inputVar,this.rightValue,this.rightType);
            this.errInfo = errObj.msg;
            this.errInfoHas = !errObj.s;
	  	}
	  }
	});
</script>
<#--END rule-line -->

<#--START rule-tree -->
<script id="rule-tree" type="text/x-template">
	<div class="ruleTree">
		<div >
			<span class="fa fa-minus-square" title="删除" style="color:red;" @click="deleteRuleTree"></span>
			<select class="operator" v-model="operator">
				<option value="and">与</option>
				<option value="or">或</option>
				<option value="notAnd">与非</option>
				<option value="notOr">或非</option>
				<#--<option value="NOT">非</option>-->
			</select>
		</div>
		
		<div  style="border-left:1px solid rgb(50,135,185);margin:5px 5px;margin-left: 75px;margin-top: -27px;">
		<div style="border-top:1px solid rgb(50,135,185);width:10px;padding-top:10px;">
		</div>
		
		<div class="criteriaContainer" style="margin-left:5px;">
		<template v-for="(lineInfo, index) in criteria">
			<template v-if="lineInfo.operator=='and'||lineInfo.operator=='or'||lineInfo.operator=='notAnd'||lineInfo.operator=='notOr'||lineInfo.operator=='not'">
				<rule-tree  :treedata="lineInfo" ></rule-tree>
			</template>
			<template v-else>
				<rule-line :lineInfo="lineInfo" v-on:emitUpValue="emitUpValue" ></rule-line>
			</template>
		</template>
		
		<div class="buttonLine" style="white-space:nowrap;">
			<i class="fa fa-circle-thin" style=""></i>
			<input type="button" class="addButton btn btn-xs btn-default" value="添加" @click="addRuleLine" />
			<input type="button" class="appButton btn btn-xs btn-default" value="增补" @click="appendRuleTree" />
		</div>
		</div>
		<div style="border-top:1px solid rgb(50,135,185);width:10px;margin-top:10px;">
		</div>
		</div>	
	</div>
</script>
<script type="text/javascript">
	Vue.component("rule-tree",{
		template:"#rule-tree",
		data:function(){
			if(this.treedata.operator===undefined||this.treedata.operator==''){
				this.treedata.operator = 'and';
			}
			return {operator:this.treedata.operator,fieldName:this.treedata.fieldName,value:this.treedata.value,criteria:this.treedata.criteria};
		},
		watch:{
			linedata:function(){
			
			}
		},
		methods:{
			'addRuleLine':function(){
				if(this.criteria===undefined){
					this.criteria = [];
				}
				this.criteria.push({operator:'',fieldName:'',value:{}});
			},
			'appendRuleTree':function(){
				if(this.criteria===undefined){
					this.criteria = [];
				}
				this.criteria.push({operator:'and',fieldName:'',value:{},criteria:[]});
			},
		  	'deleteRuleTree':function(event){
		  		$(event.target).parent().parent().remove();
		  	},
		  	emitUpValue:function(linedata){
		  		/*触发父组件的方法*/
		  	}
		},
		props:['treedata']
	});
</script>
<#--END rule-tree 

<div id="ruleObject">
	<rule-tree :treedata="treedata"></rule-tree>
</div>

<script>
	var treedata = {"criteria":[{"criteria":[{"criteria":[{"criteria":[],"fieldName":"txnAmt","operator":"equals"},{"criteria":[],"fieldName":"txnAmt","operator":"and"}],"fieldName":"a","operator":"and",value:{}}],"fieldName":"b","operator":"or"}],"fieldName":"a","operator":"and",value:{}};
	treedata = {};
	var v = new Vue({
		el:'#ruleObject',
		data:{treedata: treedata}
	});
</script>
-->

<#-- 组件方法 -->
<script>
	/*获取criteria*/
	st.parseTree =  function($t,treeData){
		var operator = $t.children(":eq(0)").children(":eq(1)").val(); //操作符
		if(operator=="and" || operator=="or" || operator=="notAnd" || operator=="notOr"){
			
			treeData.operator = operator;
			treeData.fieldName = "";
			treeData.criteria=[];
		
			var $criteriaContainer = $(".criteriaContainer:first",$t);
			var $criteriaList = $criteriaContainer.children();
			
			if($criteriaList.length>0){
				for(var i=0;i<$criteriaList.length-1;i++){
					var criteriaLine = {};
					var l = $criteriaList[i];
					var $l = $(l);
					if($l.hasClass("ruleTree")){
						//递归调用
						st.parseTree($l,criteriaLine);
					}else if($l.hasClass("ruleLine")){
						
						var lfieldValue = $l.children(":eq(1)").val();
						
						var loperator = $l.children(":eq(2)").val();
						
						var lrightValue = $(".rightValue",$l).val();/* $l.children(":eq(3)").children(":first").children(":first").val();*/
						
						criteriaLine.fieldName = lfieldValue;
						criteriaLine.operator = loperator;
						criteriaLine.value = lrightValue
						
					}
					treeData.criteria.push(criteriaLine);
				}
			}
		}
	}
</script>