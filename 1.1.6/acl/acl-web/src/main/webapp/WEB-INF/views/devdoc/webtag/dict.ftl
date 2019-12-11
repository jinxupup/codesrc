<#include "/layout.ftl"/>

<@body>
<@panel head="业务字典">
<@panelBody>
    
    		<div class="devArticleList" >
		    	<ul>
		    		<li> <a href="#dict_">dict_('xxx',prams) freemarker功能扩展 </a> </li>
		    		<li> <a href="#dictSelect">dictSelect 业务字典下拉框 </a> </li>
		    		<li> <a href="#thDictName">thDictName 行内业务字典名称 </a> </li>
		    		<li> <a href="#thGetName">thGetName 行内业务字典名称 </a> </li>
		    		<li> <a href="#buttonAuth">buttonAuth code="xxx" 按钮权限控制标签 </a> </li>
		    	</ul>
		    </div>
    
    <article id="dict_">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${"dict_('xxx',prams)"?html} freemarker功能扩展
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${"dict_('xxx',prams)"?html} freemarker功能扩展，对业务字典的操作做了封装。调用方式：
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r"${dict_('方法名',参数...)} />"}
</pre>
        </p>
        <p>
            <strong>方法：</strong>
            <@pureTable>
                <thead>
                <tr>
                    <th width="200px">#方法</th>
                    <th width="150px">#返回类型</th>
                    <th width="150px">#参数个数</th>
                    <th >#参数说明</th>
                    <th >#描述</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>list</td>
                    <td>list</td>
                    <td>1</td>
                    <td>字典类型</td>
                    <td>一般情况不用调用此方法，业务字典下拉框，直接使用<code>${r'<@dictSelect>'?html}</code>标签。返回业务字典的列表对象。返回的数据格式：
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
list = 
[
  {"code":"P","codeName":"目录","id":3,"org":"1","remark":"",<br/>   "type":"RESOURCE_TYPE","typeName":"资源类型","value":"",<br/>   "value2":"",<i>其他业务字典字段...</i> },
   ...
]
</pre>
                    </td>
                </tr>
                
                <tr>
                    <td>map</td>
                    <td>string</td>
                    <td>1</td>
                    <td>
                        <ol>
                            <li>字典类型</li>
                        </ol>
                    </td>
                    <td>将dict_('list',dicttype)方法返回的结果转为 map<String,String>形式，key为code，value为codeName</td>
                </tr>
                
                <tr>
                    <td>dict</td>
                    <td>TmAclDict</td>
                    <td>2</td>
                    <td>
                        <ol>
                            <li>字典类型</li>
                            <li>字典code</li>
                        </ol>
                    </td>
                    <td>返回单个业务字典</td>
                </tr>
                <tr>
                    <td>name</td>
                    <td>string</td>
                    <td>2</td>
                    <td>
                        <ol>
                            <li>字典类型</li>
                            <li>字典code</li>
                        </ol>
                    </td>
                    <td>获取单个业务字典的名称。例：<code>${r"${dict_('name','RESOURCE_TYPE','M'}"}</code>,会在页面显示：<code>菜单</code></td>
                </tr>
                
                <tr>
                    <td>tableList</td>
                    <td>string</td>
                    <td>2</td>
                    <td>
                        <ol>
                            <li>表对应的实体类（需包含包名）。 例子：com.jjb.acl.infrastructure.TmAclDict</li>
                            <li>查询筛选对象，json对象或json字符串 。为空需要传入  {}</li>
                        </ol>
                    </td>
                    <td>获取指定表的列表数据。用法dict_('tableList','xxx.xxx.TmXxxXX',{})</td>
                </tr>
                <tr>
                    <td>tableMap</td>
                    <td>string</td>
                    <td>2</td>
                    <td>
                        <ol>
                            <li>表对应的实体类（需包含包名）。 例子：com.jjb.acl.infrastructure.TmAclDict</li>
                            <li>查询筛选对象，json对象或json字符串 。为空需要传入  {}</li>
                            <li>keyField 将对象的哪个字段筛选为key</li>
                            <li>valueField 将对象的哪个字段筛选为value</li>
                        </ol>
                    </td>
                    <td>将dict_('tableMap','xxx.xxx.TmXxxXX',{},'code','codeName') 的数据转为 map<String,String>形式，key为keyField指定的字段，value为valueField指定的字段</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>


<article id="dictSelect">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@dictSelect>'?html}业务字典下拉框
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@dictSelect>'?html}业务字典下拉框，<code>${'<@dictSelect>'?html}</code>与普通表单元素使用方式一致
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@dictSelect dicttype="RESOURCE_TYPE" ... />'?html}
${r'</@field>'?html}    
</pre>
        </p>
        <p>
            <strong>属性：</strong>
            <@pureTable>
                <thead>
                <tr>
                    <th width="200px">#名称</th>
                    <th width="150px">#属性</th>
                    <th width="150px">#数据类型</th>
                    <th width="150px">#默认值</th>
                    <th >#描述</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>业务字典类型</td>
                    <td>dicttype</td>
                    <td>string</td>
                    <td></td>
                    <td>业务字典类型</td>
                </tr>
                <tr>
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>字段id</td>
                </tr>
                <tr>
                    <td>name</td>
                    <td>name</td>
                    <td>string</td>
                    <td></td>
                    <td>name提交数据，会使用name属性。只有设置此属性，验证表单才会起作用</td>
                </tr>
                <tr>
                    <td>值</td>
                    <td>value</td>
                    <td>string</td>
                    <td></td>
                    <td>值</td>
                </tr>
                <tr>
                    <td>是否只读字段</td>
                    <td>readonly</td>
                    <td>string</td>
                    <td>false</td>
                    <td>为true表示，只读字段</td>
                </tr>
                <tr>
                    <td>验证器</td>
                    <td>valid</td>
                    <td>map</td>
                    <td></td>
                    <td>验证器，TODO</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class</td>
                </tr>
                <tr>
                    <td>自定以style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style</td>
                </tr>
                <tr>
                    <td>宽度</td>
                    <td>width</td>
                    <td>string</td>
                    <td></td>
                    <td>输入表单宽度，以px为单位，或添加'%'，则按比例设置宽度。</td>
                </tr>
                <tr>
                    <td>是否添加空选项</td>
                    <td>nullable</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为true，会添加一行空的选项</td>
                </tr>
                <tr>
                    <td>是否显示字典code</td>
                    <td>showcode</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>在option中将code显示在label前，以横线-分隔</td>
                </tr>
                <tr>
                    <td>change事件</td>
                    <td>change</td>
                    <td>function</td>
                    <td></td>
                    <td>js function为下拉框绑定change事件</td>
                </tr>
                
                <tr>
                    <td>是否启用select2插件</td>
                    <td>s2</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>当值为false时，search、multiple 属性无效 </td>
                </tr>
                <tr>
                    <td>开启匹配</td>
                    <td>search</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>对select空间增加查询输入的匹配框</td>
                </tr>
                <tr>
                    <td>是否多选</td>
                    <td>multiple</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>select支持多值的选择</td>
                </tr>
                <tr>
                    <td>是否多选</td>
                    <td>value_split</td>
                    <td>string</td>
                    <td>,</td>
                    <td>multiple为true时有效，对value值分割匹配</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="thDictName">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@thDictName>'?html} ${'<@th>'?html}行内业务字典名称
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@thDictName>'?html} ${'<@th>'?html}行内业务字典名称，用于<code>${'<@th>'?html}</code>行内的业务字典名称取值。
            使用此标签，需要在<code>${'<@th>'?html}</code>中设置<code>render="true"</code>
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@th render="true">'?html}
    ${'<@thDictName dicttype="RESOURCE_TYPE"  value="{{row.xxx}}" showcode="false" />'?html}
${r'</@th>'?html}    
</pre>
        </p>
        <p>
            <strong>属性：</strong>
            <@pureTable>
                <thead>
                <tr>
                    <th width="200px">#名称</th>
                    <th width="150px">#属性</th>
                    <th width="150px">#数据类型</th>
                    <th width="150px">#默认值</th>
                    <th >#描述</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>业务字典类型</td>
                    <td>dicttype</td>
                    <td>string</td>
                    <td></td>
                    <td>业务字典类型</td>
                </tr>
                <tr>
                    <td>值</td>
                    <td>value</td>
                    <td>string</td>
                    <td></td>
                    <td>定义字典code的取值来源，使用模版变量，{{row.xxx}}。如果是固定的字符串，请放在单引号''中,<code>value="'值'"</code>。
                    </td>
                </tr>
                <tr>
                    <td>是否显示code</td>
                    <td>showcode</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>为true时，在名称前显示code，以横线-分隔</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    

    <article id="thGetName">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@thGetName>'?html} ${'<@th>'?html}行内map数据获取名称
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@thGetName>'?html} ${'<@th>'?html}行内map数据获取名称，用于<code>${'<@th>'?html}</code>行内的表中数据的名称取值。
            使用此标签，需要在<code>${'<@th>'?html}</code>中设置<code>render="true"</code> 通过传入map数据，获取名称，map的key与value判断，map的value是显示的名称
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@th render="true">'?html}
    ${"<@thGetName options=dict_('tableMap','TmAclDict',{},'code','codeName')"?html}  ${'value="{{row.code}}" />'?html}
${r'</@th>'?html}    
</pre>
        </p>
        <p>
            <strong>属性：</strong>
            <@pureTable>
                <thead>
                <tr>
                    <th width="200px">#名称</th>
                    <th width="150px">#属性</th>
                    <th width="150px">#数据类型</th>
                    <th width="150px">#默认值</th>
                    <th >#描述</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>map数据</td>
                    <td>options</td>
                    <td>string</td>
                    <td></td>
                    <td>map的key与value判断，map的value是显示的名称</td>
                </tr>
                <tr>
                    <td>值</td>
                    <td>value</td>
                    <td>string</td>
                    <td></td>
                    <td>与map中的key判断，使用模版变量，{{row.xxx}}。如果是固定的字符串，请放在单引号''中,<code>value="'值'"</code>。
                    </td>
                </tr>
                <tr>
                    <td>是否显示code</td>
                    <td>showcode</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>为true时，在名称前显示code，以横线-分隔</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
        
    
    <article id="buttonAuth">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@buttonAuth code="xxx">'?html}按钮权限控制标签
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@buttonAuth>'?html} ${'<@buttonAuth>'?html}按钮权限控制标签，会根据code值，判断是否有权限展示其内部的信息。
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
    ${'<@buttonAuth code="xxx"> <@button name="按钮" /></@buttonAuth>'?html}
</pre>
        </p>
        <p>
            <strong>属性：</strong>
            <@pureTable>
                <thead>
                <tr>
                    <th width="200px">#名称</th>
                    <th width="150px">#属性</th>
                    <th width="150px">#数据类型</th>
                    <th width="150px">#默认值</th>
                    <th >#描述</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>资源code</td>
                    <td>code</td>
                    <td>string</td>
                    <td></td>
                    <td>对应资源表的 resource_code 字段的值</td>
                </tr>
                <tr>
                    <td>反转</td>
                    <td>reverse</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>当reverse="true",没有 resource_code的权限时，显示</td>
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