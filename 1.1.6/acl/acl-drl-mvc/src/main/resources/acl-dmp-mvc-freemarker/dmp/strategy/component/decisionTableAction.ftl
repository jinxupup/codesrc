<style>
.action-line select,.action-line input{
	height: 26px;
}
</style>

<script id="action-line" type="text/x-template">
	<tr class="action-line">
		<td>
			<input type="hidden" class="uuid" v-model="uuid"/>
			<input type="text" class="name" v-model="name" style="width:100%"/>
		</td>
		<td>
			<select class="property" style="width:100%" v-model="property">
			  <optgroup v-if="leftOptions.length>0" label="输出变量">
			    <option v-for="(item,index) in leftOptions " :value="item.varCd">{{item.varName}}</option>
			  </optgroup>
			
			  <optgroup v-if="customPropertyOptions.length>0" label="自定义变量">
			    <option v-for="(item,index) in customPropertyOptions " :value="item.varCd">{{item.varName}}</option>
			  </optgroup>
			  <#-- 
			  <optgroup v-if="customFunctionOptions.length>0" label="自定义函数">
			    <option v-for="(item,index) in customFunctionOptions " :value="item.varCd">{{item.varName}}</option>
			  </optgroup>
			  -->
			</select>
		</td>
		<td>
			<select class="actionType" v-model="actionType" style="width:100%">
				<option v-for="(item,index) in actionTypeOptions " :value="item.op">{{item.name}}</option>
			</select>
		</td>
		<td><input type="button" class="actionLineDeleteBtn btn btn-xs btn-default" value="-删除" @click="actionLineDeleteBtn"/></td>
	</tr>
</script>

<script>
	Vue.component("action-line",{
		template:'#action-line',
		props:['lineInfo'],
		data:function(){
			var action = {};
			if(this.lineInfo.action!=undefined){
				action = this.lineInfo.action;
			}
			return {
				uuid:this.lineInfo.uuid,
				name:this.lineInfo.name,
				property: action.property,/*左值*/
			    actionType: action.actionType,/*动作类型*/
			    value:'',/*值*/
			    valueClassName:'',
			    
			    leftOptions:outputVarList,
			    customPropertyOptions:[],/*自定义变量选项*/
			    customFunctionOptions:st.functionFieldVars,/*自定义方法选项*/
			    
				actionTypeOptions:[{op:'C',name:'常量分配'},{op:'P',name:'变量分配'}]
			};
		},
		methods:{
			actionLineDeleteBtn:function(event){
				$(event.target).parent().parent().remove();
			}
		}
	})
</script>

<script id="action-table" type="text/x-template">
	<div id="">
		<div style="padding:5px;">
			<input type="button" class="actionLineAddBtn btn btn-xs btn-warning" value="新增结果" @click="actionLineAddBtn"/>
		</div>
		<table class="table table-striped table-bordered table-hover table-condensed"  style="margin-bottom:5px;">
			<thead>
			<tr>
				<th style="width:28%">结果名称</th>
				<th style="width:28%">输出变量</th>
				<th style="width:28%">输出类型</th>
				<th style="width:16%">操作</th>
			</tr>
			</thead>
			<tbody>
				<template v-for="(lineInfo, index) in actions">
					<action-line :lineInfo="lineInfo" ></action-line>
				</template>
			</tbody>
		</table>
	</div>
</script>

<script>
	Vue.component('action-table',{
		template:'#action-table',
		props:['actionsdata'],
		data:function(){
			return {actions:this.actionsdata};
		},
		methods:{
			actionLineAddBtn:function(){
				this.actions.push({name:'',action:{property:'',actionType:'C'}});
			}
		}
	});
</script>

<div id="actionLines">
	<action-table :actionsdata="actionsdata"></action-table>
</div>

<script>
	//var actionsdata = [{name:'',action:{property:'',actionType:'P'}}];
	var actionsdata = ${actionsdata};
	var v = new Vue({
		el:'#actionLines',
		data:{actionsdata:actionsdata}
	})
</script>

<script>
	st.getActionLines = function(){
		var actions = [];
	
		var $actions = $("#actionLines tr.action-line");
		$actions.each(function(){
			var column = {}; 
			var $tr = $(this);
			
			column.uuid = $(".uuid",$tr).val();
			column.name = $(".name",$tr).val();
			
			column.action = {};
			column.action.property = $(".property",$tr).val();
			column.action.actionType = $(".actionType",$tr).val();
			
			var propertyText = $(".property",$tr).find("option:selected").text(); 
			var actionTypeText = $(".actionType",$tr).find("option:selected").text(); 
			column.title = "["+propertyText+"]["+actionTypeText+"]";
			
			actions.push(column);
		});
		
		return actions;
	}
</script>