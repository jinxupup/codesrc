<#include "/layout.ftl"/>

<@body>

<@panel head="Ar_Controller">
<@panelBody>
    
    		<div class="devArticleList" >
		    	<ul>
		    		<li> <a href="#Ar_Controller">unicorn功能增强控制器类</a> </li>
		    	</ul>
		    </div>
    
    <article id="Ar_Controller">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${"Ar_Controller"?html} unicorn功能增强控制器类
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${"Ar_Controller"?html} unicorn功能增强控制器类，提供了简便的通过controller获取数据的方法。供页面ajax调用时使用。
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
<@xml_escape>

省市区级联

省 ${r'<@select id="homeState" options=dict_("tableMap","TmAclDict",{"type":"STATE"},"codeName","codeName") showcode="false" name="tmAppPrimApplicantInfo.homeState"
value="${(tmAppPrimApplicantInfo.homeState)!}" valid={"notempty":"true"} />'}

市${r'<@selectLink id="homeCity" options=dict_("tableMap","TmAclDict",'}<#-- 断点 -->"{'type':'CITY','value2':'"+(ar_('blank',(tmAppPrimApplicantInfo.homeState)!,"null"))+"'}","codeName","codeName")
link_id="homeState" url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'CITY'}" url_parent_key="value2" keyfield="codeName" valuefield="codeName"
showcode="false"  name="tmAppPrimApplicantInfo.homeCity" value="${(tmAppPrimApplicantInfo.homeCity)!}" valid={"notempty":"true"} />

 区/县${r'<@selectLink id="homeZone" options=dict_("tableMap","TmAclDict",'}<#-- 断点 -->"{'type':'ZONE','value2':'"+(ar_('blank',(tmAppPrimApplicantInfo.homeCity)!,"null"))+"'}","codeName","codeName")
	link_id="homeCity" url="ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'ZONE'}" url_parent_key="value2" keyfield="codeName" valuefield="codeName"
	showcode="false"  name="tmAppPrimApplicantInfo.homeZone"  valid={"notempty":"true"} />
</@xml_escape>		
</pre>
        </p>
        
        <p>
            <strong>方法：</strong>
            <@pureTable>
                <thead>
                <tr>
                    <th width="200px">#方法</th>
                    <th width="150px">#返回类型</th>
                    <th >#参数说明</th>
                    <th >#描述</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>table</td>
                    <td>Json</td>
                    <td>
                        <ul>
                            <li>tableClass 表对应的实体类（需包含包名）。 例子：com.jjb.acl.infrastructure.TmAclDict
                            </li>
                            <li>json 查询筛选对象，json字符串 。为空需要传入  {}
                            </li>
                            <li>_PARENT_KEY 当联动时，标记传入父级字段对应的字段
                            </li>
                            <li>_PARENT_VALUE 当联动时，传入父级字段的值
                            </li>
                        </ul>
                    </td>
                    <td>
                    	通过此方法可动态获取表中的数据,例：
                    	<code>ar_/table?tableClass=com.jjb.acl.infrastructure.TmAclDict&json={'type':'CITY'}</code>
                    	根据此链接，可获取城市信息，数据格式：json
                    </td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
</@panelBody>
</@panel>
</@body>