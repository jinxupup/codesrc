<#include "/layout.ftl" />
<#include "/content/upload/contentUploadJs.ftl"/>

<@body>
    <@panel head="影像上传界面【${(tmCmpMain.batchNo)!}】">
        <@panelBody>
            <@tab id="tab">
                <@tabContent>
                <div style="padding-bottom:0px;"></div>
                    <@form id="uploadForm" enctype="multipart/form-data" action="/cmp_/uploadImage?sgin=upload" success_url="cmp_/showContent?batchNo=${(tmCmpMain.batchNo)!}">
                        <@hidden name="query.batchNo" value="${(tmCmpMain.batchNo)!}" />
                        <@hidden name="query.userNo" value="${(userNo)!}"/>
                        <@hidden name="query.systemId" value="${(systemId)!}"/>
                        <@hidden name="query.branchCode" value="${(branchCode)!}"/>
                        <@pureTable id="telSurveyInfo">
                        <thead>
                        <tr>
                            <th width="30%">文件大类型</th>
                            <th width="30%">文件小类型</th>
                            <th width="30%">文件选择</th>
                            <th width="10%">操作</th>
                        </tr>
                        <tbody id="choiceTable">
                        <tr id="tr0">
                        	<td> 
                        		<#--<@select id="bigType0" name="query.supType0" change="setSecond(this)"  showcode="true"
                            	options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",{"type":"fileBigType"},"code","codeName") />
                            	-->
                            	<@dictSelect id="bigType0" dicttype="fileBigType" change="setSecond(this,0)" nullable="true"  name="query.supType0"/>
                            </td>
                            <td><@select id="smallType0" name="query.subType0" showcode="true" /></td>
                            <#--
	                        	<td>
	                            	<@dictSelect id="bigType0" dicttype="fileBigType"  nullable="false"  name="query.supType0"/>
	                            </td>
	                            <td>
	                            	<@selectLink id="smallType0"
			                            link_id="bigType0" keyfield="code" valuefield="codeName" append="true"
			                            options=dict_("tableMap","com.jjb.acl.infrastructure.TmAclDict",'{"type":"ApplyPatchBoltType","value":"picType"}',"code","codeName")
			                            url="ar_/table2?tableClass=com.jjb.acl.infrastructure.TmAclDict&json='type':'ApplyPatchBoltType'" url_parent_key="value"
			                            showcode='true' nullable="false" name="query.subType0" value="" />
		                        </td>
	                        -->
                            <td><@input type="file" id="file0" name="fileName0" /></td>
                            <td><@button name="新建" fa="plus" click="addUploadInfo()"/></td>
                        </tr>
                        </tbody>
                        </@pureTable>
                        <div style="padding-bottom:50px;"></div>
                        <table style="width: 100%; text-align: center">
                            <tr>
                                <td>
                                    <@submitButton name="上传提交"/>
                                    <@href name="返回查看" href="/cmp_/showContent?batchNo=${(tmCmpMain.batchNo)!}"/>
                                </td>
                            </tr>
                        </table>
                    </@form>
                </@tabContent>
            </@tab>
        </@panelBody>
    </@panel>
</@body>
