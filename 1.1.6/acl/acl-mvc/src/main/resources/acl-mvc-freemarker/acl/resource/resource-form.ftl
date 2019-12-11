<#include "/layout.ftl">
<@body>
<@panel head="资源新增/资源编辑" >
      <@panelBody>
    	  	<@form id="form" action="${isEdit?string('acl/resource/edit','acl/resource/add')}" success_url="acl/resource/page">
    	  		<@row>
                    <@field label="系统类型">
                          <@dictSelect name="sysType" dicttype="SYS_TYPE" value="${(resource.sysType)!}" readonly="${isEdit?string}" valid={"notempty":"true"} />
                    </@field>
                    <@field label="资源类型">
                          <@dictSelect name="type" dicttype="RESOURCE_TYPE" value="${(resource.type)!}" readonly="false" valid={"notempty":"true"} />
                    </@field>
                </@row>
                
    	  		<@row>
    		  		<@field label="资源代码">
    		  			<@input name="resourceCode" value="${(resource.resourceCode)!}" readonly="${isEdit?string}" valid={"notempty":"true","nochinese":"true","stringlength":"true","stringlength-max":"40"}/>
    		  		</@field>
    	  			<@field label="资源名称">
    		  			<@input name="resourceName" value="${(resource.resourceName)!}" valid={"notempty":"true","stringlength":"true","stringlength-max":"40"} />
    		  		</@field>
    		  	</@row>
    		  	
                <@row >
    		  		<@field label="上级资源代码">
    		  			<@input name="parentResourceCode" value="${(resource.parentResourceCode)!}" readonly="" valid={"stringlength":"true","stringlength-max":"40"} />
    		  		</@field>
    	  			<@field label="资源路径">
    		  			<@input name="parentPath" value="${(resource.parentPath)!}" readonly="" valid={"stringlength":"true","stringlength-max":"400"} />
    		  		</@field>
    		  	</@row>
    		  	
                <@row>
    		  		<@field label="链接">
    		  			<@input name="href" value="${(resource.href)!}" valid={"stringlength":"true","stringlength-max":"200"}/>
    		  		</@field>
    	  			<@field label="图标">
    		  			<@input name="icon" value="${(resource.icon)!}" valid={"stringlength":"true","stringlength-max":"40"} />
    		  		</@field>
    		  	</@row>
    		  	
                <@row>
    		  		<@field label="排序">
    		  			<@input name="sort" value="${(resource.sort)!}" valid={"digits":"true"} />
    		  		</@field>
    	  			<@field label="资源控制">
    	  				<@dictSelect dicttype="ResourceControlType" name="isUsed" value="${(resource.isUsed)!'Y'}" showcode="true"/>
    		  		</@field>
    		  	</@row>
                <@row>
    		  		<@field label="说明">
    		  			<@input name="remark" value="${(resource.remark)!}" valid={"stringlength":"true","stringlength-max":"200"}/>
    		  		</@field>
    		  		<@field label="资源授权类型">
                          <@dictSelect name="resourceAuthType" dicttype="ResourceAuthType" value="${(resource.resourceAuthType)!}" readonly="" valid={"notempty":"true"} />
                    </@field>
    	  		</@row>
    	  		<@row>
    	  			<@toolbar>
    	  				<@submitButton />
    	  				<@backButton />
    	  			</@toolbar>
    	  		</@row>
        </@form>
    
    </@panelBody>
</@panel>

</@body>