<#include "/layout.ftl"/>

<@body>
<@panel head="公共功能">
<@panelBody>
    		<div class="devArticleList" >
		    	<ul>
		    		<li> <a href="#unicornjs">unicorn通用js方法</a> </li>
		    		<li> <a href="#unicornPlugin">unicorn js插件</a> 
		    			<ul>
		    				<li><a href="#unicornButtonDisable">unicornButtonDisable btn插件</a> </li>
		    				<li><a href="#unicornTab">unicornTab tab插件</a> </li>
		    				<li><a href="#unicornValidField">unicornValidField 字段校验插件</a> </li>
		    				<li><a href="#unicornValidForm">unicornValidForm 表单校验插件</a> </li>
		    			</ul> 
		    		</li>
		    		<li> <a href="#ar_">freemarker功能扩展</a> </li>
		    		<li> <a href="#getName">getName 获取名称</a>  </li>
		    		<li> <a href="#icon">icon 图标</a>  </li>
		    	</ul>
		    </div>
		    
	<article id="unicornjs">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        unicorn通用js方法
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            unicorn.js是unicorn一些常用的js方法
        </p>
        <p>
            <strong>属性：</strong>
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
                    <td>ar_.uniqueId</td>
                    <td>String</td>
                    <td>0或1</td>
                    <td>
                    	<ul>
                            <li>prefix，id的前缀。默认值（id）
                            </li>
                        </ul>
                    </td>
                    <td>产生用于dom操作的唯一id，默认由id加随机数产品</td>
                </tr>
                <tr>
                    <td>ar_.getDialog</td>
                    <td>Dialog对象</td>
                    <td>0或1</td>
                    <td>window对象</td>
                    <td>当该对象为空时，默认打开顶层window</td>
                </tr>
                <tr>
                    <td>ar_.go</td>
                    <td>String</td>
                    <td>1</td>
                    <td>url</td>
                    <td>跳转到url</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <div id="unicornPlugin"></div>
    <@fieldset legend="unicorn js插件">
    
    <article id="unicornButtonDisable">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        $('#btnId').unicornButtonDisable() 按钮禁用插件
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            按钮操作插件，提供对按钮操作方法
            调用方式：
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
$("#btnId").unicornValidField(true||disabled); 
</pre>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="unicornTab">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        $('#tabId').unicornTab() tab插件
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            tab操作插件，提供tab页操作的相关方法
            调用方式：
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
//设置参数
var option = {
    id:"" , //指定tab页的id，当为空时，会自动生成
    title:"titleName", //新增标签页的标题，不能为空
    url:"/acl-web/acl/dict/page", //载入内容的url，url对应的ftl页面
    iframe:false, //是否以iframe形式载入页面,默认false
    close:true,  //是否可以关闭，默认true
    height:"200px" //高度，默认200px
};
var id = $('#tab').ar_tab().add(option); //调用新增方法，返回的id，是新增的tab标签的id。#tab是${r'<@tab id="tab" >'?html}的id属性。
$('#tab').ar_tab().close(id); //手动调用js方法关闭，以id做参数。
</pre>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    
    <article id="unicornValidField">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        $('#formId').unicornValidField() 字段手动校验插件
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            unicorn 手动校验字段插件
            调用方式：
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
$("#formId").unicornValidField("name属性"); //name属性值将要校验的输入域的name
</pre>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="unicornValidForm">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        $('#formId').unicornValidForm() 手动校验表单插件
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            手动校验表单插件，校验通过返回true，否则返回false
            调用方式：
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
$("#formId").unicornValidForm();
</pre>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    </@fieldset><#-- end unicornPlugin -->
    
    <article id="ar_">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${"ar_('xxx',prams)"?html} freemarker功能扩展
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${"ar_('xxx')"?html}功能扩展，增强了freemarker功能。调用方式：
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r"${ar_('方法名',参数...)}"}
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
                    <td>uuid</td>
                    <td>string</td>
                    <td>0</td>
                    <td></td>
                    <td>以uuid方式产生随机字符串，去除中间横线(-),并在最前面加了ar_。此字符串唯一，不会重复。</td>
                </tr>
                <tr>
                    <td>b</td>
                    <td>boolean</td>
                    <td>1个或2个</td>
                    <td>
                        <ul>
                            <li>1个参数，当被判断的对象为true、"true"、"Y"时，反回true，不区分大小写
                            </li>
                            <li>2个参数，判断这两个参数是否相等(转换为字符串调用equal方法)
                            </li>
                        </ul>
                    </td>
                    <td>简化判断，返回boolean值</td>
                </tr>
                <tr>
                    <td>blank</td>
                    <td>string</td>
                    <td>1个或2个</td>
                    <td>
                        <ul>
                            <li>1个参数，当被判断的对象为null或空字符串"",返回空字符串""
                            </li>
                            <li>2个参数，判断第1个参数是否为null或空字符串"",若是返回第2个参数传入的值
                            </li>
                        </ul>
                    </td>
                    <td>判断是否为null或空字符串，若是返回设定的默认值</td>
                </tr>
                <tr>
                    <td>random</td>
                    <td>int</td>
                    <td>0或1个</td>
                    <td>1个参数时，会限定此随机数的最大值</td>
                    <td>产生随机整数</td>
                </tr>
                <tr>
                    <td>listToMap</td>
                    <td>map</td>
                    <td>3</td>
                    <td>
                        <ol>
                            <li>list 对象列表</li>
                            <li>keyField 转换为key的字段</li>
                            <li>valueField 转换为value的字段</li>
                        </ol>
                    </td>
                    <td>将list对象转为Map&lt;Object, Object&gt;对象。
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">例子：
<i>有list数据:</i>
list = 
[
  {id:"1",code:"B",name:"按钮"},
  {id:"2",code:"P",name:"目录"},
  {id:"3",code:"M",name:"菜单"}
]
<i>调用 </i>${r"ar_(list,'code','name')"}<i>,
将得到map结果:</i>
{
    "B":"按钮",
    "P";"目录",
    "M":"菜单"
}
</pre>                    
                    </td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="getName">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@getName>'?html} 通过传入map数据，获取名称
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@getName>'?html} 通过传入map数据，获取名称，map的key与value判断，map的value是显示的名称
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
    ${"<@getName options={}"?html}  ${'value="{{row.code}}"'?html} ${'showcode="false" />'?html}
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

<article id="icon">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@icon>'?html}图标
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@icon>'?html}字体图标，使用font-awesome图标。
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
    ${'<@icon fa="search"/>'?html}
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
                    <td>图标类</td>
                    <td>fa</td>
                    <td>string</td>
                    <td></td>
                    <td>图标类，可查看Icons 图标</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
        
        <p><strong>常用图标速查表：</strong>
        <@panel>
            <@panelBody>
          <div class="row fontawesome-icon-list">
          
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="plus"><i class="fa fa-plus" ></i> plus</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="edit"><i class="fa fa-edit" ></i> edit</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="remove"><i class="fa fa-remove" ></i> remove</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="minus"><i class="fa fa-minus" ></i> minus</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="check"><i class="fa fa-check" ></i> check</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="check"><i class="fa fa-check-square" ></i> check-square</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="plus-square"><i class="fa fa-plus-square" ></i> plus-square</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="minus-square"><i class="fa fa-minus-square" ></i> minus-square</span></div>
          
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="upload"><i class="fa fa-upload" ></i> upload</span></div>
          
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="envelope"><i class="fa fa-envelope" ></i> envelope</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="tag"><i class="fa fa-tag" ></i> tag</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="trash-o"><i class="fa fa-trash" ></i> trash</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="reorder"><i class="fa fa-reorder" ></i> reorder</span></div>
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="plus-reply"><i class="fa fa-reply" ></i> reply</span></div>
          
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="bank"><i class="fa fa-bank" ></i> bank</span></div>
          
          <div class="fa-hover col-md-3 col-sm-4 col-lg-2"><span class="yen"><i class="fa fa-yen" ></i> yen</span></div>
          
        </div>
            
            
            </@panelBody>
        </@panel>
        </p>
        
      <div>  
    </@fieldset>
    </article>

</@panelBody>
</@panel>
</@body>