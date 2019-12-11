<#assign fieldsetMap = {'B1':'个人证明信息','B2':'收入证明信息','B3':'个人养老保险证明信息','B4':'个人住房公积金证明信息','B5':'我行存款证明信息','B6':'我行理财证明信息',
	'B7':'我行贷款证明信息','B8':'个人房产证明信息','B9':'企业法人证明信息','B10':'个体工商户证明信息','B11':'个人车产证明信息','B12':'居住证明证明信息'}>
<#list fieldsetMap?keys as fieldsetRegion>
	<#include "componentFieldView.ftl"/>
</#list>
