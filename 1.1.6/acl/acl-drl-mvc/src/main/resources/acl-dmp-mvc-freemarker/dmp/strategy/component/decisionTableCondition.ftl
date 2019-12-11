<style>
.condition-line select,.condition-line input{
	height: 26px;
}

</style>

<script id="condition-line" type="text/x-template">
	<tr class="condition-line">
		<td>
			<input type="hidden" class="uuid" v-model="uuid"/>
			<input type="text" class="name"  v-model="name" style="width:100%"/>
		</td>
		<td>
			<select class="fieldName" @change="leftChange" v-model="fieldName" style="width:100%">
			  <optgroup v-if="leftOptions.length>0" label="输入变量">
			    <option v-for="(item,index) in leftOptions " :value="item.varCd">{{item.varName}}</option>
			  </optgroup>
			
			  <optgroup v-if="customPropertyOptions.length>0" label="自定义变量">
			    <option v-for="(item,index) in customPropertyOptions " :value="item.varCd">{{item.varName}}</option>
			  </optgroup>
			  
			  <optgroup v-if="customFunctionOptions.length>0" label="自定义函数">
			    <option v-for="(item,index) in customFunctionOptions " :value="item.varCd">{{item.varName}}</option>
			  </optgroup>
			</select>
		</td>
		<td>
			<select class="operator" v-model="operator" style="width:100%">
				<option v-for="(item,index) in operatorOptions " :value="item.op">{{item.name}}</option>
			</select>
		</td>
		<td><input type="button" class="conditionLineDeleteBtn btn btn-xs btn-default" value="-删除" @click="conditionLineDeleteBtn"/></td>
	</tr>
</script>
<script>
	Vue.component('condition-line',{
		template:'#condition-line',
		data:function(){
			return {
					uuid:this.lineInfo.uuid,
					name:this.lineInfo.name,
					fieldName:this.lineInfo.criteria.fieldName,
					operator:this.lineInfo.criteria.operator,
				
					leftOptions:inputVarList,/*左值选项*/
				    customPropertyOptions:[],/*自定义变量选项*/
				    customFunctionOptions:st.functionFieldVars,/*自定义方法选项*/
				    operatorOptions:operator_all,/*操作符选项*/
				};
		},
		methods:{
			conditionLineDeleteBtn:function(event){
				$(event.target).parent().parent().remove();
			},
			leftChange:function(){
			}
		},
		props:['lineInfo']
	});
</script>

<script id="condition-table" type="text/x-template">
	<div>
		<div style="padding:5px;">
			<input type="button" class="conditionLineAddBtn btn btn-xs btn-info" value="新增条件" @click="conditionLineAddBtn"/>
		</div>
		<table class="table table-striped table-bordered table-hover table-condensed" style="margin-bottom:5px;">
			<thead>
			<tr>
				<th style="width:28%">条件名称</th>
				<th style="width:28%">属性</th>
				<th style="width:28%">操作符</th>
				<th style="width:16%">操作</th>
			</tr>
			</thead>
			<tbody>
				<template v-for="(lineInfo, index) in conditions">
					<condition-line :lineInfo="lineInfo" ></condition-line>
				</template>
			</tbody>
		</table>
	</div>
</script>
<script>
	Vue.component('condition-table',{
		template:'#condition-table',
		props:['conditionsdata'],
		data:function(){
			return {conditions:this.conditionsdata};
		},
		methods:{
			conditionLineAddBtn:function(){
				this.conditions.push({name:'',criteria:{fieldName:'',operator:'equals'}});
			}
		}
	});
</script>

<div id="tableConditions">
	<condition-table :conditionsdata="conditionsdata"></condition-table>
</div>

<script>
	//var conditionsdata = [{name:'112',criteria:{fieldName:'222',operator:'equal'}},{name:'112',criteria:{fieldName:'222',operator:'equal'}}];
	var conditionsdata = ${conditionsdata};
	var v = new Vue({
		el:'#tableConditions',
		data:{conditionsdata:conditionsdata}
	});
</script>

<script>
	st.getTableConditions = function(){
		var conditions = [];
	
		var $conditions = $("#tableConditions tr.condition-line");
		$conditions.each(function(){
			var column = {}; 
			var $tds = $(this).children();
			
			//var $tr = $(this);
			column.uuid = $(".uuid",$tds[0]).val();
			column.name = $(".name",$tds[0]).val();
			column.criteria = {};
			column.criteria.fieldName = $("select",$tds[1]).val();
			column.criteria.operator = $("select",$tds[2]).val();
			
			var fieldNameText = $("select",$tds[1]).find("option:selected").text(); 
			var operatorText = $("select",$tds[2]).find("option:selected").text(); 
			column.title = "["+fieldNameText+"]["+operatorText+"]";
			
			conditions.push(column);
		});
		
		return conditions;
	}
</script>