<#include "/layout.ftl" />
<#include "/content/upload/contentUploadJs.ftl"/>
<@body>
    <@panel head="影像上传界面【${(tmCmpMain.batchNo)!}】">
        <@panelBody>
            <@tab id="tab">
                <@tabContent>
                    <div style="padding-bottom:0px;"></div>
                <#--判断是否是parm查询,否则返回路径不能一样-->
                    <#if (parm)??>
                        <@form id="uploadForm" enctype="multipart/form-data" action="/assets/cmp_/uploadImage?sgin=upload" success_url="/assets/cmp_/showContent?parm=${(parm)!}">
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
                                </thead><tbody id="choiceTable">
                            <tr id="tr0">
                                <td>
                                    <@dictSelect id="bigType0" dicttype="fileBigType" change="setSecond(this,0)" nullable="true"  name="query.supType0"/>
                                </td>
                                <td><@select id="smallType0" name="query.subType0" showcode="true" /></td>

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
                                        <@href name="返回查看" href="/assets/cmp_/showContent?parm=${(parm)!}"/>
                                        <button title="退出本页面" style="cursor: pointer;color: #fff; background-color: #ff0000;border-color: #454545;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 40px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                                onclick="closePage()">退出
                                        </button>
                                    </td>
                                </tr>
                            </table>
                        </@form>
                    <#else>
                        <@form id="uploadForm" enctype="multipart/form-data" action="/assets/cmp_/uploadImage?sgin=upload" success_url="/assets/cmp_/showContent?batchNo=${(tmCmpMain.batchNo)!}">
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
                                </thead><tbody id="choiceTable">
                            <tr id="tr0">
                                <td>
                                    <@dictSelect id="bigType0" dicttype="fileBigType" change="setSecond(this,0)" nullable="true"  name="query.supType0"/>
                                </td>
                                <td><@select id="smallType0" name="query.subType0" showcode="true" /></td>

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
                                        <@href name="返回查看" href="/assets/cmp_/showContent?batchNo=${(tmCmpMain.batchNo)!}"/>
                                        <button title="退出本页面" style="cursor: pointer;color: #fff; background-color: #ff0000;border-color: #454545;top-bar-button: 90px;vertical-align: -2px;
                         border-radius: 4px;padding: 1px;width: 40px;height: 21.5px;font-size: 12px;font-family: 黑体;text-align: center;border: none;line-height: 23px"
                                                onclick="closePage()">退出
                                        </button>
                                    </td>
                                </tr>
                            </table>
                        </@form>
                    </#if>

                </@tabContent>
            </@tab>
        </@panelBody>
    </@panel>
</@body>
<script>
    function closePage() {
        window.opener=null;
        window.open('', '_self');
        window.close();
    }
</script>