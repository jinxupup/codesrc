<div style="padding-bottom:50px;"></div>
<@form id="uploadForm" action="acl/branch/upload" success_url="acl/branch/fileUpload?isEdit=Y">
    <@row>
        <@field label="文件选择">
            <@input type="file" name="fileName"/>
        </@field>
        <@field label="文件编码">
            <@dictSelect dicttype="EncodeWay" name="encodeWay" showcode="false" value="GBK"/>
        </@field>
        <@submitButton name="上传提交" />
    </@row>
</@form>
<div style="padding-bottom:50px;"></div>