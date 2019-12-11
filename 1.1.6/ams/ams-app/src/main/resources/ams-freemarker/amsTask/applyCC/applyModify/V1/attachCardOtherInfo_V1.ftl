
<@fieldset legend="申请信息:">    
	<@row>    
       	<@field label="是否集体件">
           	<@dictSelect dicttype="Indicator"  name="tmAppMain.isClt" value="${(tmAppMain.isClt)!'N'}"/>
      	</@field>
      	<@field label="国籍">
           	<@dictSelect dicttype="Nationality" name="tmAppAttachApplierInfo.nationality" value="${(tmAppAttachApplierInfo.nationality)!'156'}" />
        </@field>
   </@row> 
 	<@row> 	
      	<@field label="快速审批标志">
			<@dictSelect dicttype="Indicator"  name="tmAppMain.approveQuickFlag" value="${(tmAppMain.approveQuickFlag)!'N'}"/>
		</@field>
		<@field label="优先处理标志">
			<@dictSelect dicttype="Indicator"  name="tmAppMain.isPriority" value="${(tmAppMain.isPriority)!'N'}"/>
		</@field>
		<@field label="紧急制卡标志">
			<@dictSelect dicttype="Indicator"  name="tmAppMain.isUrgentCard" value="${(tmAppMain.isUrgentCard)!'N'}"/>
		</@field> 
	</@row>  
 	<@row>           	  
      	<@field label="发证机关所在地">
           	<@input name="tmAppAttachApplierInfo.idIssuerAddress" value="${(tmAppAttachApplierInfo.idIssuerAddress)!}"/>
      	</@field>                 
      	<@field label="是否永久居住">
           	<@dictSelect dicttype="Indicator"  name="tmAppAttachApplierInfo.prOfCountry" value="${(tmAppAttachApplierInfo.prOfCountry)!'Y'}"/>
      	</@field>            	
      	<@field label="永久居住地国家代码">
           	<@dictSelect dicttype="Nationality" name="tmAppAttachApplierInfo.residencyCountryCd" value="${(tmAppAttachApplierInfo.residencyCountryCd)!'156'}"/>
      	</@field>
	</@row>
  	<@row>
  		<@field label="学位">
           	<@dictSelect dicttype="Degree" name="tmAppAttachApplierInfo.degree" value="${(tmAppAttachApplierInfo.degree)!}"/>
      	</@field>
  		<@field label="家庭人均年收入">
      		<@input name="tmAppAttachApplierInfo.familyAvgeVenue" value="${(tmAppAttachApplierInfo.familyAvgeVenue?string('#.##'))!}"
      		valid={"regexp or pattern":"^((0|([1-9](\\d{1,14})?))(\\.\\d{1,2})?$)","regexp-message":"请正确填写家庭人均年收入"}/>
		</@field>
		<@field label="家庭国家代码">
			<@dictSelect dicttype="Nationality" name="tmAppAttachApplierInfo.homeAddrCtryCd" value="${(tmAppAttachApplierInfo.homeAddrCtryCd)!'156'}" />
		</@field>         
	</@row>
</@fieldset>
              