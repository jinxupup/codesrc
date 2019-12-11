<#include "/layout.ftl"/>
<@body>

<#--<@panel head="已处理工作量">
	<@panelBody>
		&lt;#&ndash; 定义要显示的列数 columnCount &ndash;&gt;
		<#assign columnCount = 3>
		&lt;#&ndash; 计算显示当前记录集需要的表格行数 rowCount &ndash;&gt;
		<#if indexWorkCountDtos?size % columnCount == 0>
		    <#assign rowCount = ( indexWorkCountDtos?size / columnCount) - 1 >
		<#else>
		    <#assign rowCount = ( indexWorkCountDtos?size / columnCount) >
		</#if>
				<#list 0..rowCount as row>
						<#list 0..columnCount - 1 as cell >
								<#if indexWorkCountDtos[row * columnCount + cell]?? >
									<#assign indexWorkCountDto = indexWorkCountDtos[row * columnCount + cell]>
									<div class="col-lg-3 col-md-3">
										<div class="thumbnail" style="min-height: 100px;background-color: snow">
											<div class="caption"align="center">
												<h2  ><font face="microsoft yahei" size="4" color="black">${(indexWorkCountDto.proName)!}</font></h2>
												<p style="color:blue;">已处理：${(indexWorkCountDto.nums)!}</p>
											</div>
										</div>
									</div>
								</#if>
						</#list>
				</#list>

	</@panelBody>
</@panel>	-->
</br>
	<@panel head="未处理工作量">
		<@panelBody>
		<#-- 定义要显示的列数 columnCount -->
			<#assign columnUntreatedCount = 3>
		<#-- 计算显示当前记录集需要的表格行数 rowCount -->
			<#if indexWorkUntreatedCountDtos?size % columnUntreatedCount == 0>
				<#assign rowUntreatedCount = ( indexWorkUntreatedCountDtos?size / columnUntreatedCount) - 1 >
			<#else>
				<#assign rowUntreatedCount = ( indexWorkUntreatedCountDtos?size / columnUntreatedCount) >
			</#if>
			<#list 0..rowUntreatedCount as row>
				<#list 0..columnUntreatedCount - 1 as cell >
					<#if indexWorkUntreatedCountDtos[row * columnUntreatedCount + cell]?? >
						<#assign indexWorkUntreatedCountDto = indexWorkUntreatedCountDtos[row * columnUntreatedCount + cell]>
                    <div class="col-lg-3 col-md-3">
                        <div class="thumbnail" style="min-height: 100px;background-color: snow">
                            <div class="caption"align="center">
                                <h2  ><font face="microsoft yahei" size="4" color="black">${(indexWorkUntreatedCountDto.proName)!}</font></h2>
                                <p style="color:blue;">未处理：${(indexWorkUntreatedCountDto.nums)!}</p>
                            </div>
                        </div>
                    </div>
					</#if>
				</#list>
			</#list>

		</@panelBody>
	</@panel>
</@body>