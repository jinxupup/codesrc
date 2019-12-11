<#include "/layout.ftl"/>

<@body>
<@panel head="布局标签">
<@panelBody>
    
    		<div class="devArticleList" >
		    	<ul>
		    		<li> <a href="#panel">panel 面板</a> </li>
		    		<li> <a href="#panelHead">panelHead 面板头</a>  </li>
		    		<li> <a href="#panelBody">panelBody 面板主体</a>  </li>
		    		<li> <a href="#container">container 容器</a>  </li>
		    		<li> <a href="#row">row 行</a>  </li>
		    		<li> <a href="#hr">hr / 水平线</a>  </li>
		    		<li> <a href="#toolbar">toolbar 工具条 </a> </li>
		    		<li> <a href="#fieldset">fieldset 字段组 </a> </li>
		    		<li> <a href="#legend">legend 字段组标题 </a> </li>
		    		<li> <a href="#tab">tab 标签 </a> </li>
		    		<li> <a href="#tabNav">tabNav 标签导航 </a> </li>
		    		<li> <a href="#tabTitle">tabTitle 标签标题 </a> </li>
		    		<li> <a href="#tabContent">tabContent 标签内容容器 </a> </li>
		    		<li> <a href="#tabPane">tabPane 标签内容窗体</a> </li>
		    	</ul>
		    </div>
		    
    <article id="panel">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@panel>'?html}面板
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@panel>'?html}提供面板功能
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@panel head="标题">'?html}
    ${'<@panelBody>'?html}
    <i>内容...</i>
    ${'</@panelBody>'?html}
${r'</@panel>'?html}    
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>标题</td>
                    <td>head</td>
                    <td>string</td>
                    <td></td>
                    <td>此为简单定义，标题只包含文本。若需要复杂的标题，可不设置此属性，并在内部使用<code>${'<@panelHead>'?html}</code>标签</td>
                </tr>
                <tr>
                    <td>情境效果</td>
                    <td>sense</td>
                    <td>string</td>
                    <td>primary</td>
                    <td>不同情境效果会有不同的展示样式。可选：primary,success,info,warning,danger
                        
                        <div class="container-fluid">
                            <@row>
                            <div class="col-ar-7">
                                <@panel head="primary" sense="primary">
                                    <@panelBody>
                                        primary
                                    </@panelBody>
                                </@panel>
                            </div>
                            <div class="col-ar-7">
                                <@panel head="success" sense="success">
                                    <@panelBody>
                                        success
                                    </@panelBody>
                                </@panel>
                            </div>
                            <div class="col-ar-7">
                                <@panel head="info" sense="info">
                                    <@panelBody>
                                        info
                                    </@panelBody>
                                </@panel>
                            </div>
                            <div class="col-ar-7">
                                <@panel head="warning" sense="warning">
                                    <@panelBody>
                                        warning
                                    </@panelBody>
                                </@panel>
                            </div>
                            <div class="col-ar-7">
                                <@panel head="danger" sense="danger">
                                    <@panelBody>
                                        danger
                                    </@panelBody>
                                </@panel>
                            </div>
                            </@row>
                        </div>
                        
                    
                    </td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>


<article id="panelHead">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@panelHead>'?html}面板头
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@panel>'?html}面板头部
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@panel>'?html}
    ${'<@panelHead>'?html}
    <i>头部内容...</i>
    ${'</@panelHead>'?html}
    ...    
${r'</@panel>'?html}    
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
<article id="panelBody">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@panelBody>'?html}面板主体
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@panel>'?html}面板主体
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@panel head="标题">'?html}
    ${'<@panelBody>'?html}
    <i>内容...</i>
    ${'</@panelBody>'?html}
${r'</@panel>'?html} 
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    
<article id="container">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@container>'?html}容器
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@container>'?html}布局时使用，直接子标签请使用<code>${'<@row>'?html}</code> 默认会占据父级元素100%的宽度
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@container>'?html}
    ${'<@row>'?html}
    <i>内容...</i>
    ${'<@row>'?html}
${r'</@container>'?html} 
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>流式布局</td>
                    <td>fluid</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为true时，默认宽度100%。为false</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>宽度</td>
                    <td>width</td>
                    <td>string</td>
                    <td></td>
                    <td>当fluid为false时，请定义宽度。以px为单位，或添加'%'，则按比例设置宽度。</td>
                </tr>
                <tr>
                    <td>对齐方式</td>
                    <td>align</td>
                    <td>string</td>
                    <td></td>
                    <td>当fluid为false时有效。默认居中对齐，可选值：left左对齐、center居中对齐、right右对齐</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>    
    
<article id="row">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@row>'?html}行
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@panel>'?html}grid中的行，请不要单独使用，需要放在容器标签中，如 <code>${'<@form>'?html}</code>、<code>${'<@container>'?html}</code>
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@form>'?html}
    ${'<@row>'?html}
    <i>内容...</i>
    ${'<@row>'?html}
${r'</@form>'?html} 
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    
<article id="hr">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@hr />'?html}水平线
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@hr />'?html}水平线提供界面元素分组公共。比如多个<code>${'<@fieldSet>'?html}</code>一个<code>${'<@toolbar>'?html}</code>的情况，
            可以用<code>${'<@hr />'?html}</code>区分。<code>${'<@hr />'?html}</code>标签，默认上外边距margin-top为0，下外边距margin-bottom:15px;
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@form>'?html}
    ${r'<@fieldSet>'?html}
        <i>...</i>
    ${r'</@fieldSet>'?html}
    ${r'<@fieldSet>'?html}
        <i>...</i>
    ${r'</@fieldSet>'?html}
        <i>...</i>
    ${r'<@row>'?html}
        
        ${'<@hr />'?html}
    
        ${r'<@toolbar>'?html}
            <i>...</i>        
        ${r'</@toolbar>'?html} 
    ${r'<@row>'?html}
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>    
    
<article id="toolbar">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@toolbar>'?html}工具条
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@toolbar>'?html}用于放置按钮等元素，默认会占据全部宽度。在<code>${'<@form>'?html}</code>中可自动为按钮提供位置偏移。
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@toolbar>'?html}
    <i>button等内容...</i>
${r'</@toolbar>'?html} 
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>偏移量</td>
                    <td>offset</td>
                    <td>int</td>
                    <td>0</td>
                    <td>以36格栅格系统为基础,默认不偏移。在<code>${'<@form>'?html}</code>中会自动计算，使按钮与表单输入元素对齐</td>
                </tr>
                <tr>
                    <td>对齐方式</td>
                    <td>align</td>
                    <td>string</td>
                    <td>left</td>
                    <td>对齐方式，可选值：left左对齐、center居中、right右对齐</td>
                </tr>
                <tr>
                    <td>右偏移量</td>
                    <td>right_offset</td>
                    <td>int</td>
                    <td>0</td>
                    <td>当align为right时，有效。以36格栅格系统为基础。</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>    
    
    
    <article id="fieldset">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@fieldset>'?html}字段组
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@fieldset>'?html}将页面内容分组
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@fieldset legend="xxx">'?html}
    <i>内容xxx...</i>
${r'</@fieldset>'?html} 
${r'<@fieldset legend="yyy">'?html}
    <i>内容yyy...</i>
${r'</@fieldset>'?html}
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>fieldset标题</td>
                    <td>legend</td>
                    <td>String</td>
                    <td></td>
                    <td>此为简单定义，标题只包含文本。若需要复杂的标题，可不设置此属性，并在内部使用<code>${'<@legend>'?html}</code>标签</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    
    <article id="legend">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@legend>'?html}字段组标题
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@legend>'?html}字段组标题
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@fieldset >'?html}
    ${'<@legend>'?html} <i>标题...</i> ${'</@legend>'?html}
${r'</@fieldset>'?html} 
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    
    <article id="tab">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@tab>'?html}标签
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@tab>'?html}标签
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@tab id="" >'?html}
    ${'<@tabNav>'?html} 
        ${'<@tabTitle pane_id="pane1" active="true" title="tab1">'?html}
        ${'</@tabTitle>'?html}    
        ${'<@tabTitle pane_id="pane2"  title="tab2">'?html}
        ${'</@tabTitle>'?html}
    ${'</@tabNav>'?html}
    ${'<@tabContent>'?html} 
        ${'<@tabPane id="pane1" active="true">'?html}
            <i>...</i>
        ${'</@tabPane>'?html}    
        ${'<@tabPane id="pane2" >'?html}
            <i>...</i>
        ${'</@tabPane>'?html}
    ${'</@tabContent>'?html}
${r'</@tab>'?html} 
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id，如果是可增加标签页的tab，id必须设置。
                    可增加标签页的tab，调用ar_tab插件即可。
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->                    
<pre class="ar-pre-code">
${'<script type="text/javascript">'?html}
    //设置参数
    var option = {
	              title:"titleName", //新增标签页的标题，不能为空
	              url:"${base}/acl/dict/page", //载入内容的url，url对应的ftl页面
	              iframe:false, //是否以iframe形式载入页面,默认false
	              close:true,  //是否可以关闭，默认true
	              height:"200px" //高度，默认200px
              };
    var id = $('#tab').ar_tab().add(option); //调用新增方法，返回的id，是新增的tab标签的id。#tab是${r'<@tab id="tab" >'?html}的id属性。
    $('#tab').ar_tab().close(id); //手动调用js方法关闭，以id做参数。
${'</script>'?html}
</pre>                    
                    </td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="tabNav">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@tabNav>'?html}标签导航
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@tabNav>'?html}标签导航，标签导航是标签标题的容器
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@tab id="" >'?html}
    ${'<@tabNav>'?html} 
        ${'<@tabTitle pane_id="pane1" active="true" title="tab1">'?html}
        ${'</@tabTitle>'?html}    
        ${'<@tabTitle pane_id="pane2"  title="tab2">'?html}
        ${'</@tabTitle>'?html}
    ${'</@tabNav>'?html}
    ${'<@tabContent>'?html} 
        ${'<@tabPane id="pane1" active="true">'?html}
            <i>...</i>
        ${'</@tabPane>'?html}    
        ${'<@tabPane id="pane2" >'?html}
            <i>...</i>
        ${'</@tabPane>'?html}
    ${'</@tabContent>'?html}
${r'</@tab>'?html} 
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="tabTitle">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@tabTitle>'?html}标签标题
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@tabTitle>'?html}标签标题，标签的头部信息
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@tab id="" >'?html}
    ${'<@tabNav>'?html} 
        ${'<@tabTitle pane_id="pane1" active="true" title="tab1">'?html}
        ${'</@tabTitle>'?html}    
        ${'<@tabTitle pane_id="pane2"  title="tab2">'?html}
        ${'</@tabTitle>'?html}
    ${'</@tabNav>'?html}
    ${'<@tabContent>'?html} 
        ${'<@tabPane id="pane1" active="true">'?html}
            <i>...</i>
        ${'</@tabPane>'?html}    
        ${'<@tabPane id="pane2" >'?html}
            <i>...</i>
        ${'</@tabPane>'?html}
    ${'</@tabContent>'?html}
${r'</@tab>'?html} 
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>对应标签窗体id</td>
                    <td>pane_id</td>
                    <td>string</td>
                    <td></td>
                    <td>${'<@tabTitle>'?html}与${'<@tabPane>'?html}通过<code>pane_id</code>实现对应关系，
                    在${'<@tabTitle>'?html}中必须指定此属性。如果默认是激活状态，在对应的${'<@tabPane>'?html}中也需要设定激活状态。</td>
                </tr>
                <tr>
                    <td>是否激活状态</td>
                    <td>active</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>是否激活状态</td>
                </tr>
                <tr>
                    <td>ajax内容地址</td>
                    <td>url</td>
                    <td>string</td>
                    <td></td>
                    <td>
                        以ajax形式加载内容到<code>${'<@tabPane>'?html}</code>窗体，url为内容链接地址。载入内容的url，url对应的ftl页面，请使用${'<@segment>'}作为根标签
                    </td>
                </tr>
                <tr>
                    <td>是否每次点击都加载</td>
                    <td>url_click_fresh</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>
        若设置此属性为true，且url属性不为空，每次点击tab标题，都会重新载入<code>${'<@tabPane>'?html}</code>窗体内容。
                    </td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                <tr>
                    <td></td>
                    <td>iframe</td>
                    <td>string</td>
                    <td></td>
                    <td>以iframe形式载入页面，默认false；当为true时，tabPane 不需要添加高度属性</td>
                </tr>
                <tr>
                    <td></td>
                    <td>iframe_height</td>
                    <td>integer</td>
                    <td></td>
                    <td>设置tab页面高度</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="tabContent">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@tabContent>'?html}标签内容容器
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@tabContent>'?html}标签内容容器
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@tab id="" >'?html}
    ${'<@tabNav>'?html} 
        ${'<@tabTitle pane_id="pane1" active="true" title="tab1">'?html}
        ${'</@tabTitle>'?html}    
        ${'<@tabTitle pane_id="pane2"  title="tab2">'?html}
        ${'</@tabTitle>'?html}
    ${'</@tabNav>'?html}
    ${'<@tabContent>'?html} 
        ${'<@tabPane id="pane1" active="true">'?html}
            <i>...</i>
        ${'</@tabPane>'?html}    
        ${'<@tabPane id="pane2" >'?html}
            <i>...</i>
        ${'</@tabPane>'?html}
    ${'</@tabContent>'?html}
${r'</@tab>'?html} 
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否设置边框</td>
                    <td>border</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>对tabContent页面设置边框属性，默认false，无边框。</td>
                </tr>
                <tr>
                    <td>容器高度</td>
                    <td>height</td>
                    <td>string</td>
                    <td></td>
                    <td>对tabContent容器设置高度。设置此属性后，对其中tabPane需设置高度100% ${'<@tabPane id="pane1" height="100%">'?html}
                    	自适应高度可写js代码
<pre class="ar-pre-code">
${'<script type="text/javascript">'?html}
    $(function(){
        var autoHeight = function(){
            ar_.autoHeight({id:"tabContentId",minus:175});
            ar_.autoHeight({id:"pane2",minus:175}); /*iframe高度属性需额外设置*/
            setTimeout(autoHeight,200);
        }
        autoHeight();
    });
${'</script>'?html}
</pre>   
                    </td>
                </tr>
                <tr>
                    <td>设置内边距</td>
                    <td>padding</td>
                    <td>integer</td>
                    <td>15px</td>
                    <td>对tabContent页面设置内边距，默认15px。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="tabPane">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@tabPane >'?html}标签内容窗体
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@tabPane >'?html}标签内容窗体
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@tab id="" >'?html}
    ${'<@tabNav>'?html} 
        ${'<@tabTitle pane_id="pane1" active="true" title="tab1">'?html}
        ${'</@tabTitle>'?html}    
        ${'<@tabTitle pane_id="pane2"  title="tab2">'?html}
        ${'</@tabTitle>'?html}
    ${'</@tabNav>'?html}
    ${'<@tabContent>'?html} 
        ${'<@tabPane id="pane1" active="true">'?html}
            <i>...</i>
        ${'</@tabPane>'?html}    
        ${'<@tabPane id="pane2" >'?html}
            <i>...</i>
        ${'</@tabPane>'?html}
    ${'</@tabContent>'?html}
${r'</@tab>'?html} 
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
                    <td>id</td>
                    <td>id</td>
                    <td>string</td>
                    <td></td>
                    <td>元素id</td>
                </tr>
                <tr>
                    <td>是否激活状态</td>
                    <td>active</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>是否激活状态，${'<@tabPane>'?html}与${'<@tabTitle>'?html}是有对应关系的(通过${'<@tabTitle>'?html}的<code>pane_id</code>)，
                    如果激活状态，相对应的这两个标签都应该是激活状态。</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>自定义style</td>
                    <td>style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义style，以追加的方式输出</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                <tr>
                    <td>设置高度</td>
                    <td>height</td>
                    <td>integer</td>
                    <td></td>
                    <td>对tabPane设置高度；当tabTitle为 iframe时，则不需要添加高度属性</td>
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