<script src="${base}/assets/plugins/vue/vue.js"></script>

<#-- 公共方法 -->
<script>
	var st = {};
	
	var rightInputTypeList = ['input','fieldSelect','dictSelect','enumSelect','date','between','null'];
	
	/**
	 * varCd 变量代码string格式
	 * varList 变量列表，inputVarList或outputVarList
	 */
	st.getVarField = function(varCd,varList){
		var iVar = {};
		$.each( varList, function(i, obj){
		  	if(obj.varCd == varCd){
		  		iVar = obj;
		  		return false;
		  	}
		});
		return iVar;
	};
	/**
	 * varCd 变量代码string格式
	 * varList 变量列表，inputVarList或outputVarList
	 * operator 操作符string格式
	 */
	st.rightInputType = function(varCd,varList,operator){
		
		var r = 'null';
		
		if(varCd===undefined||varCd==''){
			return r;
		}
		var varField = st.getVarField(varCd,varList);
		
  		/*操作符类型  字段比较*/
  		if(operator=='isNull'||operator=='notNull'){
  			r = 'null';
  			return r;
  		}else if(operator=='equalsField'||operator=='notEqualField'||operator=='greaterThanField'||operator=='greaterOrEqualField'||operator=='lessThanField'||operator=='lessOrEqualField'){
			r = 'fieldSelect';/*字段比较*/
		}else if(operator=='iregexp'||operator=='iNotRegexp'){
			r = 'input';
		}
		if(r != 'null'){ return r; }
		
		/*输入变量类型*/
		if(varField.optionType=='A'){
			r = 'input';
		}else if(varField.optionType=='D'){
			r = 'dictSelect';
		}else if(varField.optionType=='T'){
            r = 'tableSelect';
		}else if(varField.optionType=='E'){
			r = 'enumSelect';
		}else{
			
		}
		return r;
	};
	
	
</script>
<script type="text/javascript">
    st.valueCheckConst = function(varField,value){

        if(value==undefined||value==null||value==""){
            return {s:true,msg:""};
        }
        if(varField==undefined||varField==null){
            return {s:true,msg:""};
        }
        
        var dataType = varField.dataType;
        if(dataType==undefined||dataType==null){
            return {s:true,msg:""};
        }
        
        if(dataType=="string"){
            return {s:true,msg:""};
        }else if(dataType=="decimal"){
            var regexp = /^[0-9]+(\.?[0-9]+)?$/;
            var isNumber = regexp.test(value);
            if(!isNumber){
                return { s: false, msg: "请输入数字" };
            }
        }else if(dataType=="bool"){
            if(!(value=="true"||value=="false"||value==true||value==false)){
                return { s: false, msg: "请输入布尔值true/false" };
            }
        }else if(dataType=="date"){
            var regexp = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2})$/;     
            var isDate = regexp.test(value);     
            if(isDate){
                return {s:true,msg:""};
            }
            
            var regexpDateTime = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;     
            isDate = regexpDateTime.test(value);     
            if(isDate){
                return {s:true,msg:""};
            }
            
            var regexpDateTimeM = /^(\d{1,4})(-)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2})$/;     
            isDate = regexpDateTimeM.test(value);     
            if(isDate){
                return {s:true,msg:""};
            }
            return { s: false, msg: "请输入日期值" };
        }else{
            
        }
        return {s:true,msg:""};
    }
    
    st.valueCheckProperty = function(varField,value){
        if(value==undefined||value==null||value==""){
            return {s:true,msg:""};
        }
        if(varField==undefined||varField==null){
            return {s:true,msg:""};
        }
        
        var dataType = varField.dataType;
        if(dataType==undefined||dataType==null){
            return {s:true,msg:""};
        }
        
        var rightVarField = st.getVarField(value,st.leftOptionList);
        if(rightVarField==undefined||rightVarField==null||rightVarField.dataType==undefined||rightVarField==null||rightVarField==""){
            return {s:false,msg:"数据类型错误"};
        }
        
        if(dataType==rightVarField.dataType){
            return {s:true,msg:""};
        }else{
            if(dataType=="string"){
                return { s: false, msg: "请选择字符格式" };
            }else if(dataType=="decimal"){
                return { s: false, msg: "请选择数字格式" };
            }else if(dataType=="bool"){
                return { s: false, msg: "请选择布尔格式" };
            }else if(dataType=="date"){
                return { s: false, msg: "请选择日期格式" };
            }else{
                
            }
        }
        return {s:true,msg:""};
    }
    
    //校验右侧输入项
    st.valueCheck = function(varField,value,rightType){
        
        var checkResult = {s:true,msg:""};
    
        if(rightType=="fieldSelect"){
            checkResult = st.valueCheckProperty(varField,value);
        }else{
            checkResult = st.valueCheckConst(varField,value);
        }
        
        if(checkResult.s){
            if(st.errCount>=1){
                st.errCount -= 1;
            }
        }else{
            st.errCount += 1;
        }
                    
        return checkResult;    
    }
    
    //错误的输入框数
    st.hasWrongInput = function(){
        var $has = $(".has-wrong-input");
        console.log($has.length);
        if($has.length>0){
            alert("输入有错误，请检查");
            return true;
        }        
        return false;
    };
    
</script>

<#-- 总体配置对象 -->
<script>
	/*输入变量列表
	var inputVarList = [
		{"varCd":"txnAmt","varName":"交易金额","dataType":"decimal","optionType":"A","optionParam":"",options:[]},
		{"varCd":"txnType","varName":"交易类型","dataType":"string","optionType":"D","optionParam":"",options:[{"cd":"RT","name":"消费"},{"cd":"CS","name":"取现"}]},
		{"varCd":"MCC","varName":"MCC","dataType":"string","optionType":"E","optionParam":"xxxx.enum",options:[{"cd":"RT1","name":"消费1"},{"cd":"CS1","name":"取现1"}]}
	];*/
	
	/*输出变量列表
	var outputVarList = [
		{"varCd":"txnAmt","varName":"交易金额","dataType":"decimal","optionType":"A","optionParam":"",options:[]},
		{"varCd":"txnType","varName":"交易类型","dataType":"string","optionType":"D","optionParam":"",options:[{"cd":"RT","name":"消费"},{"cd":"CS","name":"取现"}]},
		{"varCd":"MCC","varName":"MCC","dataType":"string","optionType":"E","optionParam":"xxxx.enum",options:[{"cd":"RT1","name":"消费1"},{"cd":"CS1","name":"取现1"}]}
	];*/
	
	var inputVarList = [];
	var outputVarList = [];
	st.functionFieldVars = [];//[{"varCd":"txnAmt11","varName":"交易金额111","dataType":"decimal","optionType":"A"}];
	
	<#if inputVarList?? >
		inputVarList = ${inputVarList};
	</#if>
	
	<#if outputVarList?? >
		outputVarList = ${outputVarList};
	</#if>
	
	<#if functionFieldVars?? >
		st.functionFieldVars = ${functionFieldVars};
	</#if>
	
	st.leftOptionList = inputVarList.concat(st.functionFieldVars);
	
	var operator_all = [
		{op:"equals",name:"等于"},
		{op:"notEqual",name:"不等于"},
		{op:"greaterThan",name:"大于"},
		{op:"greaterOrEqual",name:"大于等于"},
		{op:"lessThan",name:"小于"},
		{op:"lessOrEqual",name:"小于等于"},
		{op:"equalsField",name:"等于字段"},
		{op:"notEqualField",name:"不等于字段"},
		{op:"greaterThanField",name:"大于字段"},
		{op:"greaterOrEqualField",name:"大于等于字段"},
		{op:"lessThanField",name:"小于字段"},
		{op:"lessOrEqualField",name:"小于等于字段"},
		{op:"isNull",name:"为空"},
		{op:"notNull",name:"不为空"},
		{op:"iregexp",name:"匹配表达式"},
		{op:"iNotRegexp",name:"不匹配表达式"}
	];
	
	var operator_string = [
		{op:"equals",name:"等于"},
		{op:"notEqual",name:"不等于"},
		{op:"greaterThan",name:"大于"},
		{op:"greaterOrEqual",name:"大于等于"},
		{op:"lessThan",name:"小于"},
		{op:"lessOrEqual",name:"小于等于"},
		{op:"equalsField",name:"等于字段"},
		{op:"notEqualField",name:"不等于字段"},
		{op:"greaterThanField",name:"大于字段"},
		{op:"greaterOrEqualField",name:"大于等于字段"},
		{op:"lessThanField",name:"小于字段"},
		{op:"lessOrEqualField",name:"小于等于字段"},
		{op:"isNull",name:"为空"},
		{op:"notNull",name:"不为空"},
		{op:"iregexp",name:"匹配表达式"},
		{op:"iNotRegexp",name:"不匹配表达式"}
	];
	var operator_decimal = [
		{op:"equals",name:"等于"},
		{op:"notEqual",name:"不等于"},
		{op:"greaterThan",name:"大于"},
		{op:"greaterOrEqual",name:"大于等于"},
		{op:"lessThan",name:"小于"},
		{op:"lessOrEqual",name:"小于等于"},
		{op:"equalsField",name:"等于字段"},
		{op:"notEqualField",name:"不等于字段"},
		{op:"greaterThanField",name:"大于字段"},
		{op:"greaterOrEqualField",name:"大于等于字段"},
		{op:"lessThanField",name:"小于字段"},
		{op:"lessOrEqualField",name:"小于等于字段"},
		{op:"isNull",name:"为空"},
		{op:"notNull",name:"不为空"},
	];
	
	
	<#--/*所有操作符选项
	var operator_all = [
		{op:"equals",name:"等于"},
		{op:"notEqual",name:"不等于"},
		{op:"iEquals",name:"等于(忽略大小写)"},
		{op:"iNotEqual",name:"不等于(忽略大小写)"},
		{op:"greaterThan",name:"大于"},
		{op:"lessThan",name:"小于"},
		{op:"lessOrEqual",name:"小于等于"},
		{op:"greaterOrEqual",name:"大于等于"},
		{op:"between",name:"在...之间"},
		{op:"betweenInclusive",name:"在...之间(包括边界值)"},
		{op:"iBetween",name:"between"},
		{op:"iBetweenInclusive",name:"between (inclusive)"},
		{op:"iContains",name:"包含"},
		{op:"iStartsWith",name:"开始于"},
		{op:"iEndsWith",name:"结束于"},
		{op:"contains",name:"包含(匹配大小写)"},
		{op:"startsWith",name:"开始于(匹配大小写)"},
		{op:"endsWith",name:"结束于(匹配大小写)"},
		{op:"iNotContains",name:"不包含"},
		{op:"iNotStartsWith",name:"非开始于"},
		{op:"iNotEndsWith",name:"非结束于"},
		{op:"notContains",name:"不包含(匹配大小写)"},
		{op:"notStartsWith",name:"非开始于(匹配大小写)"},
		{op:"notEndsWith",name:"非结束于(匹配大小写)"},
		{op:"isNull",name:"为空"},
		{op:"notNull",name:"不为空"},
		{op:"regexp",name:"匹配表达式(精确匹配)"},
		{op:"iregexp",name:"匹配表达式"},
		{op:"iNotRegexp",name:"不匹配表达式"},
		{op:"inSet",name:"在...之中"},
		{op:"notInSet",name:"不在...之中"},
		{op:"equalsField",name:"等于字段"},
		{op:"notEqualField",name:"不等于字段"},
		{op:"greaterThanField",name:"大于字段"},
		{op:"greaterOrEqualField",name:"大于等于字段"},
		{op:"lessThanField",name:"小于字段"},
		{op:"lessOrEqualField",name:"小于等于字段"},
		{op:"containsField",name:"包含其他字段值(匹配大小写)"},
		{op:"startsWithField",name:"开始于其他字段值(匹配大小写)"},
		{op:"endsWithField",name:"结束于其他字段值(匹配大小写)"}
	];*/
	-->
	
	/*表单输入框选项*/
	var rightType = ['input','fieldSelect','dictSelect','enumSelect','date','between','null'];
</script>