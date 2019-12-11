<#include "/layout.ftl">
<@body>
    <@panel head="按钮">
        <@panelBody>
            
            <div class="devArticleList" >
		    	<ul>
		    		<li> <a href="#href">href 超链接按钮</a> </li>
		    		<li> <a href="#button">button 普通按钮</a>  </li>
		    		<li> <a href="#submitButton">submitButton 提交按钮</a>  </li>
		    		<li> <a href="#resetButton">resetButton 提交按钮</a>  </li>
		    		<li> <a href="#backButton">backButton 返回按钮</a>  </li>
		    		<li> <a href="#ajaxButton">ajaxButton ajax按钮</a>  </li>
		    	</ul>
		    </div>
            
            <article id="href">
                <@fieldset class="devdoc-fieldset">
                <@legend class="devdoc-legend">
                    ${'<@href>'?html}超链接按钮
                </@legend>
                <div >
                    <p>
                        <strong>说明：</strong><br/>
                        ${'<@href>'?html}超链接按钮，点击会跳转到href指定的页面
                        
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@toolbar>'?html}
    ${'<@href name="编辑" href="dict/editPage?id={{d.row.id}}" />'?html}
${r'</@toolbar>'?html}    

${r'<@toolbar>'?html}
    ${'<@href name="编辑" href="dict/editPage?id=xxx" />'?html}
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
                                <td>按钮id</td>
                            </tr>
                            <tr>
                                <td>name</td>
                                <td>name</td>
                                <td>string</td>
                                <td></td>
                                <td>按钮名称，设置此属性，可用单标签形式<code>${r'<@button name="保存"/>'?html}</code>，不用在标签体内设置按钮名称</td>
                            </tr>
                            <tr>
                                <td>跳转地址</td>
                                <td>href</td>
                                <td>string</td>
                                <td></td>
                                <td>点击后跳转的地址</td>
                            </tr>
                            <tr>
                                <td>值</td>
                                <td>value</td>
                                <td>string</td>
                                <td></td>
                                <td>一般用不到</td>
                            </tr>
                            <tr>
                                <td>情境效果</td>
                                <td>sense</td>
                                <td>string</td>
                                <td>primary</td>
                                <td>不同情境效果会有不同的展示样式。可选：primary,success,info,warning,danger .
                                    <@button name="primary" sense="primary"/>
                                    <@button name="success" sense="success"/>
                                    <@button name="info" sense="info"/>
                                    <@button name="warning" sense="warning"/>
                                    <@button name="danger" sense="danger"/>
                                </td>
                            </tr>
                            <tr>
                                <td>图标</td>
                                <td>fa</td>
                                <td>string</td>
                                <td>false</td>
                                <td>设置fa，会在name前展示图标</td>
                            </tr>
                            <tr>
                                <td>自定义class</td>
                                <td>class</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义class</td>
                            </tr>
                            <tr>
                                <td>自定义style</td>
                                <td>style</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义style</td>
                            </tr>
                            <tr>
                                <td>target</td>
                                <td>target</td>
                                <td>string</td>
                                <td></td>
                                <td>${'<a>'?html}的属性，定义在何处打开链接，默认本页。如果是下载链接，可设置 <code>target="_blank"</code>,在新开的页面下载。</td>
                            </tr>
                            </tbody>
                        </@pureTable>
                    </p>
                  <div>  
                </@fieldset>
                </article>
            
        
            <article id="button">
                <@fieldset class="devdoc-fieldset">
                <@legend class="devdoc-legend">
                    ${'<@button>'?html}普通按钮
                </@legend>
                <div >
                    <p>
                        <strong>说明：</strong><br/>
                        ${'<@button>'?html}普通按钮
                        
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@toolbar>'?html}
    ${'<@button name="保存" />'?html}
${r'</@toolbar>'?html}    

${r'<@toolbar>'?html}
    ${'<@button/>保存</@button>'?html}
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
                                <td>按钮id</td>
                            </tr>
                            <tr>
                                <td>name</td>
                                <td>name</td>
                                <td>string</td>
                                <td></td>
                                <td>按钮名称，设置此属性，可用单标签形式<code>${r'<@button name="保存"/>'?html}</code>，不用在标签体内设置按钮名称</td>
                            </tr>
                            <tr>
                                <td>值</td>
                                <td>value</td>
                                <td>string</td>
                                <td></td>
                                <td>一般用不到</td>
                            </tr>
                            <tr>
                                <td>扩展设置</td>
                                <td>click</td>
                                <td>function</td>
                                <td></td>
                                <td>js function，点击按钮触发的js方法</td>
                            </tr>
                            <tr>
                                <td>情境效果</td>
                                <td>sense</td>
                                <td>string</td>
                                <td>primary</td>
                                <td>不同情境效果会有不同的展示样式。可选：primary,success,info,warning,danger.
                                    <@button name="primary" sense="primary"/>
                                    <@button name="success" sense="success"/>
                                    <@button name="info" sense="info"/>
                                    <@button name="warning" sense="warning"/>
                                    <@button name="danger" sense="danger"/></td>
                            </tr>
                            <tr>
                                <td>图标</td>
                                <td>fa</td>
                                <td>string</td>
                                <td>false</td>
                                <td>设置fa，会在name前展示图标</td>
                            </tr>
                            <tr>
                                <td>自定义class</td>
                                <td>class</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义class</td>
                            </tr>
                            <tr>
                                <td>自定义style</td>
                                <td>style</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义style</td>
                            </tr>
                            </tbody>
                        </@pureTable>
                    </p>
                  <div>  
                </@fieldset>
                </article>
                
                
                <article id="submitButton">
                <@fieldset class="devdoc-fieldset">
                <@legend class="devdoc-legend">
                    ${'<@submitButton>'?html}提交按钮
                </@legend>
                <div >
                    <p>
                        <strong>说明：</strong><br/>
                        ${'<@submitButton>'?html}普通按钮
                        
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@toolbar>'?html}
    ${'<@submitButton/>'?html}
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
                                <td>按钮id</td>
                            </tr>
                            <tr>
                                <td>name</td>
                                <td>name</td>
                                <td>string</td>
                                <td>提交</td>
                                <td>默认提交，可不在标签体内设置</td>
                            </tr>
                            <tr>
                                <td>值</td>
                                <td>value</td>
                                <td>string</td>
                                <td></td>
                                <td>一般用不到</td>
                            </tr>
                            <tr>
                                <td>点击事件</td>
                                <td>click</td>
                                <td>function</td>
                                <td></td>
                                <td>js function，点击按钮触发的js方法，返回false将不提交表单</td>
                            </tr>
                            <tr>
                                <td>情境效果</td>
                                <td>sense</td>
                                <td>string</td>
                                <td>primary</td>
                                <td>不同情境效果会有不同的展示样式。可选：primary,success,info,warning,danger.
                                    <@button name="primary" sense="primary"/>
                                    <@button name="success" sense="success"/>
                                    <@button name="info" sense="info"/>
                                    <@button name="warning" sense="warning"/>
                                    <@button name="danger" sense="danger"/></td>
                            </tr>
                            <tr>
                                <td>图标</td>
                                <td>fa</td>
                                <td>string</td>
                                <td>false</td>
                                <td>设置fa，会在name前展示图标</td>
                            </tr>
                            <tr>
                                <td>自定义class</td>
                                <td>class</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义class</td>
                            </tr>
                            <tr>
                                <td>自定义style</td>
                                <td>style</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义style</td>
                            </tr>
                            </tbody>
                        </@pureTable>
                    </p>
                  <div>  
                </@fieldset>
                </article>
                
                <article id="resetButton">
                <@fieldset class="devdoc-fieldset">
                <@legend class="devdoc-legend">
                    ${'<@resetButton>'?html}提交按钮
                </@legend>
                <div >
                    <p>
                        <strong>说明：</strong><br/>
                        ${'<@resetButton>'?html}重置按钮，放在表单内，点击会将表单内的元素置为默认值。
                        
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@toolbar>'?html}
    ${'<@resetButton/>'?html}
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
                                <td>按钮id</td>
                            </tr>
                            <tr>
                                <td>name</td>
                                <td>name</td>
                                <td>string</td>
                                <td>提交</td>
                                <td>默认提交，可不在标签体内设置</td>
                            </tr>
                            <tr>
                                <td>值</td>
                                <td>value</td>
                                <td>string</td>
                                <td></td>
                                <td>一般用不到</td>
                            </tr>
                            <tr>
                                <td>点击事件</td>
                                <td>click</td>
                                <td>function</td>
                                <td></td>
                                <td>js function，点击按钮触发的js方法</td>
                            </tr>
                            <tr>
                                <td>情境效果</td>
                                <td>sense</td>
                                <td>string</td>
                                <td>primary</td>
                                <td>不同情境效果会有不同的展示样式。可选：primary,success,info,warning,danger.
                                    <@button name="primary" sense="primary"/>
                                    <@button name="success" sense="success"/>
                                    <@button name="info" sense="info"/>
                                    <@button name="warning" sense="warning"/>
                                    <@button name="danger" sense="danger"/></td>
                            </tr>
                            <tr>
                                <td>图标</td>
                                <td>fa</td>
                                <td>string</td>
                                <td>false</td>
                                <td>设置fa，会在name前展示图标</td>
                            </tr>
                            <tr>
                                <td>自定义class</td>
                                <td>class</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义class</td>
                            </tr>
                            <tr>
                                <td>自定义style</td>
                                <td>style</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义style</td>
                            </tr>
                            </tbody>
                        </@pureTable>
                    </p>
                  <div>  
                </@fieldset>
                </article>
                
                <article id="backButton">
                <@fieldset class="devdoc-fieldset">
                <@legend class="devdoc-legend">
                    ${'<@backButton>'?html}返回按钮
                </@legend>
                <div >
                    <p>
                        <strong>说明：</strong><br/>
                        ${'<@backButton>'?html}返回上一页
                        
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@toolbar>'?html}
    ${'<@backButton/>'?html}
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
                                <td>按钮id</td>
                            </tr>
                            <tr>
                                <td>name</td>
                                <td>name</td>
                                <td>string</td>
                                <td>返回</td>
                                <td>默认返回，可不在标签体内设置</td>
                            </tr>
                            <tr>
                                <td>值</td>
                                <td>value</td>
                                <td>string</td>
                                <td></td>
                                <td>一般用不到</td>
                            </tr>
                            <tr>
                                <td>情境效果</td>
                                <td>sense</td>
                                <td>string</td>
                                <td>primary</td>
                                <td>不同情境效果会有不同的展示样式。可选：primary,success,info,warning,danger.
                                    <@button name="primary" sense="primary"/>
                                    <@button name="success" sense="success"/>
                                    <@button name="info" sense="info"/>
                                    <@button name="warning" sense="warning"/>
                                    <@button name="danger" sense="danger"/></td>
                            </tr>
                            <tr>
                                <td>图标</td>
                                <td>fa</td>
                                <td>string</td>
                                <td>false</td>
                                <td>设置fa，会在name前展示图标</td>
                            </tr>
                            <tr>
                                <td>自定义class</td>
                                <td>class</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义class</td>
                            </tr>
                            <tr>
                                <td>自定义style</td>
                                <td>style</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义style</td>
                            </tr>
                            </tbody>
                        </@pureTable>
                    </p>
                  <div>  
                </@fieldset>
                </article>
                
                <article id="ajaxButton">
                <@fieldset class="devdoc-fieldset">
                <@legend class="devdoc-legend">
                    ${'<@ajaxButton>'?html}ajax按钮
                </@legend>
                <div >
                    <p>
                        <strong>说明：</strong><br/>
                        ${'<@ajaxButton>'?html}ajax按钮，自动进行ajax校验，返回信息提示及成功后跳转路径
                        
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代-->
  
<pre class="ar-pre-code">
${r'<@toolbar>'?html}
    ${'<@ajaxButton/>'?html}
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
                                <td>按钮id</td>
                            </tr>
                            <tr>
                                <td>name</td>
                                <td>name</td>
                                <td>string</td>
                                <td>返回</td>
                                <td>默认返回，可不在标签体内设置</td>
                            </tr>
                            <tr>
                                <td>值</td>
                                <td>value</td>
                                <td>string</td>
                                <td></td>
                                <td>一般用不到</td>
                            </tr>
                            <tr>
                                <td>情境效果</td>
                                <td>sense</td>
                                <td>string</td>
                                <td>primary</td>
                                <td>不同情境效果会有不同的展示样式。可选：primary,success,info,warning,danger.
                                    <@button name="primary" sense="primary"/>
                                    <@button name="success" sense="success"/>
                                    <@button name="info" sense="info"/>
                                    <@button name="warning" sense="warning"/>
                                    <@button name="danger" sense="danger"/></td>
                            </tr>
                            <tr>
                                <td>图标</td>
                                <td>fa</td>
                                <td>string</td>
                                <td>false</td>
                                <td>设置fa，会在name前展示图标</td>
                            </tr>
                            <tr>
			                    <td>附加表单数据id</td>
			                    <td>form_id</td>
			                    <td>string</td>
			                    <td></td>
			                    <td>当form_id不为空，会在ajax时，附加表单中的数据</td>
			                </tr>
                            <tr>
                                <td>自定义class</td>
                                <td>class</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义class</td>
                            </tr>
                            <tr>
                                <td>自定义style</td>
                                <td>style</td>
                                <td>string</td>
                                <td></td>
                                <td>自定义style</td>
                            </tr>
                            <tr>
                                <td>url</td>
                                <td>url</td>
                                <td>string</td>
                                <td></td>
                                <td>每次数据请求操作都会调用此url</td>
                            </tr>
                            <tr>
                                <td>url_data</td>
                                <td>url_data</td>
                                <td>map</td>
                                <td></td>
                                <td>附加数据，以map的方式向后台传递数据；当有中文时请用此方式传递数据</td>
                            </tr>
                            <tr>
                                <td>multi_submit</td>
                                <td>multi_submit</td>
                                <td>boolean</td>
                                <td>false</td>
                                <td>默认在点击提交按钮，并且返回的json数据表示成功，提交按钮将置灰，设置multi_submit为true，可多次提交</td>
                            </tr>
                            <tr>
                                <td>after</td>
                                <td>after</td>
                                <td>function</td>
                                <td></td>
                                <td>js function，点击提交按钮后的调用方法。返回的json数据为方法参数。方法返回false，将不做后续操作（success_url后续不跳转）。 可用此属性添加后置动作。</td>
                            </tr>
                            <tr>
                                <td>before</td>
                                <td>before</td>
                                <td>function</td>
                                <td></td>
                                <td>表单提交前调用。返回false，将不提交表单。可用此属性添加前置动作。</td>
                            </tr>
                            <tr>
                                <td>自定义confirm</td>
                                <td>confirm</td>
                                <td>string</td>
                                <td></td>
                                <td>提示框弹出的提示信息</td>
                            </tr>
                            <tr>
                                <td>success_url</td>
                                <td>success_url</td>
                                <td>string</td>
                                <td></td>
                                <td>设置操作成功后跳转的页面路径</td>
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