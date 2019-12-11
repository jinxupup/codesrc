<#include "/layout.ftl">
<@body>
	<@panel head="机构信息">
    <@panelBody>
	    	<@form id="form" action="${isEdit?string('acl/branch/edit','acl/branch/add')}" success_url="acl/branch/page" before="beforeSumit">
	    		<@row>
	    			<@field label="机构代码">
	    				<@input name="branchCode" value="${(branch.branchCode)!}" id="branchCode" readonly="${isEdit?string}"  valid={"notempty":"true","stringlength":"true","stringlength-max":"40" ,"nochinese":"true"} />
	    			</@field>
	    			<@field label="机构名称">
	    				<@input id="branchName" name="branchName" value="${(branch.branchName)!}"   valid={"notempty":"true","stringlength":"true","stringlength-max":"40"}/>
	    			    
	    			</@field>
	    		</@row>
	    		<@row>
	    			<@field label="上级机构代码">	    			
	    				<#--<@input name="parentCode" value="${(branch.parentCode)!}" id="parentCode_id" valid={"stringlength":"true","stringlength-max":"40"}/>-->	    			                          
						 <div id="id1" class="input-group" style="position: relative;">
	    			    <input  name="parentCode" type="text" readonly  value="${(branch.parentCode)!}" data-bv-stringlength="true"
                         data-bv-stringlength-max="400" class="form-control " style=";width:"
						 id="parentCode" href="#" onClick="showTree()"  />				 
						 <span class="input-group-addon" id="emptyParentCodeBtn"><@icon fa="remove" /></span>
						</div>					 
	    			</@field>  			
	    			<div id="menuContent" class="menuContent" style="display:none;width:21%;position: fixed; z-index:999;background: #f0f6e4">		     
				         <ul id="treeDemo" class="ztree" style="margin-top:0;background: #f0f6e4;height:250px;  overflow-y:scroll;overflow-x:scroll">
				         </ul>
			        </div>
			        
	    			<@field label="上级机构路径">
	    				<@input name="parentPath"  value="${(branch.parentPath)!}"   id="parentPath" valid={"stringlength":"true","stringlength-max":"400"} readonly="true" />
	    			</@field>
	    		</@row>	    			    		
	    		<@row>	
	    			<@field label="层级">
	    				<@select name="branchLevel" value="${(branch.branchLevel)!}" options={"H":"高","M":"中","L":"低"} valid={"stringlength":"true","stringlength-max":"1"}/>
	    			</@field>
	    		
	    			<@field label="类型">
	    				<@select name="branchType" value="${(branch.branchType)!}" options={"Z":"总行","F":"分行"} valid={"stringlength":"true","stringlength-max":"1"}/>
	    			</@field>
	    		</@row>
	    		<#-- 
                <@row>	
	    			<@field label="状态">
	    				<@select name="status" value="${(branch.status)!}" options={"Y":"可用","N":"不可用"} valid={"stringlength":"true","stringlength-max":"1"}/>
	    			</@field>
	    			
	    			<@field label="排序">
	    				<@input name="sort" value="${(branch.sort)!}" valid={"digits":"true"}/>
	    			</@field>
	    		</@row>
	    		-->
	    		<@row>
	    			<@field label="国家代码">
	    				<@input name="ctryCd"  value="${(branch.ctryCd)!}" valid={"stringlength":"true","stringlength-max":"3"}/>
	    			</@field>
	    			
	    			<@field label="省">
	    				<@input name="province" valid={"stringlength":"true","stringlength-max":"40"}/>
	    			</@field>
	    		</@row>
	    		<@row>	
	    			<@field label="市">
	    				<@input name="city" valid={"stringlength":"true","stringlength-max":"40"}/>
	    			</@field>
	    		
	    			<@field label="区/县">
	    				<@input name="zone" valid={"stringlength":"true","stringlength-max":"40"}/>
	    			</@field>
	    		</@row>
                <@row>	
	    			<@field label="地址">
	    				<@input name="empAdd" value="${(branch.empAdd)!}" valid={"stringlength":"true","stringlength-max":"200"}/>
	    			</@field>
	    			
	    			<@field label="邮编">
	    				<@input name="zipCode" value="${(branch.zipCode)!}" valid={"stringlength":"true","stringlength-max":"10"}/>
	    			</@field>
	    		</@row>
	    		<@row>
	    			<@field label="联系人">
	    				<@input name="contactUser" value="${(branch.contactUser)!}" valid={"stringlength":"true","stringlength-max":"40"}/>
	    			</@field>
	    			
	    			<@field label="联系电话">
	    				<@input name="phone" value="${(branch.phone)!}" valid={"stringlength":"true","stringlength-max":"20"}/>
	    			</@field>
	    		</@row>
	    		<@row>	
	    			<@field label="说明">
	    				<@input name="remark" value="${(branch.remark)!}" valid={"stringlength":"true","stringlength-max":"200"}/>
	    			</@field>
	    		</@row>
	    		<@row>
                    <@toolbar>
                    	<#if ! noEdit?? || noEdit!="Y">
                        	<@submitButton id="submitBtnId" />
                        </#if>
                        <@backButton />
                    </@toolbar>
                </@row>
	    	</@form>
	    </@panelBody>
</@panel>
	
	<script type="text/javascript">
		var setting = {
			view: {
				dblClickExpand: false,
				showIcon:false
			},
			data: {
				keep: {
					parent: true
				},
				key: {
					name: "branchName"
				},
				simpleData: {
					enable: true,
					idKey: "branchCode",
					pIdKey: "parentCode"
				}
			},
			callback: {
				onClick: onNodeSelect
			}
		};
		
		var ztree = null;
        $(document).ready(function(){
			ztree = $.fn.zTree.init($("#treeDemo"), setting, ${branchTree});
			
			<#if isEdit?? && isEdit >
			//选中节点
			ztree.selectNode(ztree.getNodeByParam("branchCode","${(branch.parentCode)!}"));
			</#if>
		});
		
		function onNodeSelect(e, treeId, treeNode){
			$("#parentCode").val(treeNode.branchCode);
		}
		
		function genPath(){
			var branchCode = $("#branchCode").val();
			var parentCode = $("#parentCode").val();
			
			if(branchCode==parentCode){
				alert("上级机构代码不能和机构代码相同，请检查");
				return false;
			}
			
			var node = ztree.getNodeByParam("branchCode",parentCode);
			if(node!=null){
				var parentPath = node.parentPath + parentCode + "/";
			}else{
				var parentPath = "/";
			}
			
			console.log(parentPath);
			//不能修改节点的上级机构为它的下级机构
			if(parentPath.indexOf("/"+branchCode+"/")!=-1){
				alert("不能修改为它的下级机构");
				return false;
			}
			/*
			var re = new RegExp("(/"+branchCode+"/).*(/"+branchCode+"/)","g");
			var res =parentPath.match(re);
			if(res!=null){
				alert("不能修改为它的下级机构");
				return false;
			}*/
			
			$("#parentPath").val(parentPath);
			return true;
		}
	   
		function showTree() {  
			var objCode = $("#parentCode");
			var objOffset = $("#parentCode").offset();
			$("#menuContent").css({left:12 +"%", top:objOffset.top + objCode.outerHeight() + "px"}).slideDown("fast");
			$("body").bind("mousedown", onBodyDown);
		}
		function hideMenu() {
			$("#menuContent").fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
		function onBodyDown(event) {
			if (!(event.target.id == "parentCode" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
				hideMenu();
			}
		}

		 $("#emptyParentCodeBtn").click(function(){
            $("#parentCode").val("");
            $("#parentPath").val("");
         });
		
    	function beforeSumit(){
    		if(!genPath()){
    			$('#submitBtnId').unicornButtonDisable(false);
    			return false;
    		}
    		return true;
    	}
    
	</script>		
</@body>
