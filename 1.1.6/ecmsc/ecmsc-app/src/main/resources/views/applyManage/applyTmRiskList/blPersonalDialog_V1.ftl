<#include "/layout.ftl"/>
<@body>
<#--个人黑名单（给欺诈双击使用）-->
		<@panelBody>
			<@form id="queryForm" cols="2">
     			<@field hidden="true">
	     			 <@input name="id" value="${(tmPersonalBlacklist.id)!}"/>
	      		</@field>
     			<@row>
			          <@field label="黑名单来源">
			          		<@dictSelect name="blacklistSrc" dicttype="PersonalBlacklistSource" value="${(tmPersonalBlacklist.blacklistSrc)!}" label_only="true"/>
			          </@field>
			          <@field label="证件类型">
			                <@dictSelect name="idType" dicttype="IdType" value="${(tmPersonalBlacklist.idType)!}" label_only="true"/>
			          </@field>        
			      </@row>
			      <@row>
			      	  <@field label="证件号码">
			                <@input name="idNo" value="${(tmPersonalBlacklist.idNo)!}" label_only="true"/>
			          </@field>
			          <@field label="姓名">
			                <@input name="name" value="${(tmPersonalBlacklist.name)!}" label_only="true"/>
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="移动电话">
			                <@input name="cellphone" value="${(tmPersonalBlacklist.cellphone)!}" label_only="true"/>
			          </@field>
			          <@field label="家庭电话">
			                <@input name="homePhone" value="${(tmPersonalBlacklist.homePhone)!}" label_only="true"/>
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="公司地址">
			                <@input name="empAdd" value="${(tmPersonalBlacklist.empAdd)!}" label_only="true"/>
			          </@field>
			          <@field label="公司名称">
			                <@input name="corpName" value="${(tmPersonalBlacklist.corpName)!}" label_only="true"/>
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="公司电话">
			                <@input name="empPhone" value="${(tmPersonalBlacklist.empPhone)!}" label_only="true"/>
			          </@field>
			          <@field label="家庭地址">
			                <@input name="homeAdd" value="${(tmPersonalBlacklist.homeAdd)!}" label_only="true"/>
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="记录有效期">
			                <@date name="validDate" value="${(tmPersonalBlacklist.validDate?date)!}" label_only="true"/>
			          </@field>
			          <@field label="行动类型">
			                <@dictSelect name="actType" dicttype="ActType" value="${(tmPersonalBlacklist.actType)!}" label_only="true"/>
			          </@field>
			      </@row>
			      <@row>
			      	  <@field label="风险级别">
			                <@dictSelect name="riskLevel" dicttype="RiskLevel" value="${(tmPersonalBlacklist.riskLevel)!}" label_only="true"/>
			          </@field>
			      </@row>
			      <@row style="margin-top:10px;">
				      <@field label="上黑名单原因说明" label_ar="6" field_ar="36" input_ar="18">
			          	    <@textarea name="reason" class="col-ar-36" value="${(tmPersonalBlacklist.reason)!}" label_only="true"/>
			          </@field>
			      </@row>
			      <@row style="margin-top:10px;">
			      	  <@field label="备注" label_ar="6" field_ar="36" input_ar="18">
			                <@textarea name="memo" value="${(tmPersonalBlacklist.memo)!}" label_only="true"/>
			          </@field>    
			      </@row>
			</@form>
		</@panelBody>
</@body>

