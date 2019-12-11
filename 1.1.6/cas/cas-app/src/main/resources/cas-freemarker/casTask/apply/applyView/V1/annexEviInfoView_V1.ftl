<#assign fieldsetMap = {'B1':'附件核实情况','B2':'房产状况','B3':'他行卡状况','B4':'车产状况'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "casComponentFieldView_V1.ftl"/>
</#list>
