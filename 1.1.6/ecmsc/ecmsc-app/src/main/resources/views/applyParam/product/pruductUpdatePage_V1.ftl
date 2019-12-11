<#include "/layout.ftl"/>
<@body>
<@gotop/>

	<@panel head="更新产品配置">
		<@panelBody>
			<@form id="productForm" action="productParam/productUpdate"  success_url="productParam/page">
				<@field hidden="true">
					<@input id="id" name="id" value="${(tmProduct.id)!}" />
				</@field>
				<#include "productInfo_V1.ftl"/>
				<@toolbar style="text-align: left">
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		            <@submitButton />
		            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<@backButton />
				</@toolbar>
			</@form>
		</@panelBody>
	</@panel>
</@body>
