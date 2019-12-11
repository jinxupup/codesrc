<#include "/layout.ftl">

	<@body>
		<@panel>
			<@panelBody style="padding:15px">
				<div class="div1" style="width: auto;height: 600px;position: relative;">
   					<div class="div2" style="position: absolute;left: 50%;top: 50%;margin-left: -600px;margin-top: -200px;width: 1200px;height: 400px;line-height: 60px;text-align: center;">
   						<font size="5" color="red">抱歉，您当前访问的页面存在问题。。。</font><br/>
   						<font size="4" color="red">${(imageErrMessage)!}</font>
   					</div>
				</div>
			</@panelBody>
		</@panel>
		
	</@body>