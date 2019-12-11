<#include "/layout.ftl">

<@body>
    	  	
           <@form id="remark_form" action="acl/authAudit/refuse" after="after">
           		<@row>
           			<@field hidden="true">
           		 		<@input id="logId" name="logId" value="${logId}"/>
        			</@field>
           		</@row>
           		<@row>
	    	  		<@field label="请填写备注:">
	           		 	<@input id="check_remark" name="check_remark" width="400px" valid={"notempty":"true","stringlength":"true","stringlength-max":"200"}/>
	        		</@field>
	        	</@row>
        		<@row>
					<@toolbar>
						<@submitButton />
					</@toolbar>
				</@row>
    	  	</@form>    	  
<script type="text/javascript">
 		var after = function(res){
 			alert(res.msg);	
 			
 			if(res.s){
	 			var d = ar_.getDialog(parent);
	            	d.close().remove();	
 			}
 			
 		};
 		
</script>	
</@body>