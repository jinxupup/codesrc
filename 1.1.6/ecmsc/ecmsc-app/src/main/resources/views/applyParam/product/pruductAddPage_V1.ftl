<#include "/layout.ftl"/>
<@body>
<@gotop/>

	<@panel head="新增产品配置">
		<@panelBody>
			<@form id="productForm" action="productParam/productAdd"  success_url="productParam/page">
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
