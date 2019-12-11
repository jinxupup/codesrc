<#include "/layout.ftl"/>

<@body>
<@panel head="表单">
<@panelBody>


    <div class="devArticleList" >
    	<ul>
    		<li> <a href="#valid">valid 验证器</a>  </li>
    		<li> <a href="#form">form 表单</a>  </li>
    		<li> <a href="#file">field 字段 </a></li>
    		<li> <a href="#input">input 输入框</a></li>
    		<li> <a href="#hidden">hidden 隐藏域</a></li>
    		<li> <a href="#select">select 下拉选择框</a></li>
    		<li> <a href="#selectLink">selectLink 联动下拉选择框</a></li>
    		<li> <a href="#option">option select自定义选项</a></li>
    		<li> <a href="#multipleSelect">multipleSelect 多选下拉选择框</a></li>
    		<li> <a href="#checkbox">checkbox 多选</a></li>
    		<li> <a href="#date">date 日期时间</a></li>
    		<li> <a href="#file">file 文件上传</a></li>
    		<li> <a href="#textarea">textarea 多行文本输入</a></li>
    	</ul>
    </div>
    
    
    <article id="valid">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'valid'?html} 表单验证器
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@form>'?html}表单，对每个输入表单都提供了验证属性<code>valid={}</code>。 valid基于bootstrap validator插件，便捷的提供了验证功能。
            在unicorn中，验证器的属性与bootstrap validator插件的一致，简化开发，在书写时不需要data-bv-前缀。
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${'<@field>'?html}
    ${r'<@input valid={"notEmpty":"notEmpty"} />'?html}
${'</@field>'?html}    
</pre>
        </p>
        <p>
            <strong>验证器：</strong>
            <@pureTable>
                <thead>
                <tr>
                    <th width="200px">#名称</th>
                    <th width="150px">#开关</th>
                    <th >#属性 / 类型 / 描述</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>非空验证</td>
                    <td>notEmpy</td>
                    <td>
                        <ul>
                        <li><code>"notEmpy-message":"xxx不能为空"</code> <strong>/</strong>string <strong>/</strong>错误提示</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>数值范围</td>
                    <td>between</td>
                    <td>
                        <ul>
                        <li><code>"between-message":"范围不正确"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        <li><code>"between-min":"-10"</code> <strong>/</strong>float <strong>/</strong>最小值</li>
                        <li><code>"between-max":"10"</code> <strong>/</strong>float <strong>/</strong>最大值</li>
                        <li><code>"between-inclusive":"true"</code> <strong>/</strong>boolean <strong>/</strong>是否包含min与max数值，默认true</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>数值范围/大于等于</td>
                    <td>greaterthan</td>
                    <td>
                        <ul>
                        <li><code>"greaterthan-message":"请输入大于10的数值"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        <li><code>"greaterthan-value":"10"</code> <strong>/</strong>float <strong>/</strong>比较值</li>
                        <li><code>"greaterthan-inclusive":"true"</code> <strong>/</strong>boolean <strong>/</strong>是否包含value数值，默认true</li>
                        </ul>
                    </td>
                </tr>
               <tr>
                    <td>数值范围/小于等于</td>
                    <td>lessthan</td>
                    <td>
                        <ul>
                        <li><code>"lessthan-message":"请输入小于10的数值"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        <li><code>"lessthan-value":"10"</code> <strong>/</strong>float <strong>/</strong>比较值</li>
                        <li><code>"lessthan-inclusive":"true"</code> <strong>/</strong>boolean <strong>/</strong>是否包含value数值，默认true</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>小数验证</td>
                    <td>decimal</td>
                    <td>
                        <ul>
                        <li><code>"decimal-message":"请输入正确的小数"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        <li><code>"decimal-iextent":"4"</code> <strong>/</strong>int <strong>/</strong>整数位数</li>
                        <li><code>"decimal-dextent":"2"</code> <strong>/</strong>int <strong>/</strong>小数位数</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>自定义js方法验证</td>
                    <td>callback</td>
                    <td>
                        <ul>
                         <li><code>"callback-message":"js验证失败"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                         <li><code>callback(fieldValue,validator)</code>  <strong>/</strong>function <strong>/</strong>
需要写js方法，并绑定到验证表单上，callback是验证方法
<pre class="ar-pre-code">
$('#form').bootstrapValidator({
    fields: {
        fieldname-字段的name属性: {
            validators: {
                callback: {
                    message: 'js验证失败',
                    callback: function(value, validator) {
                        if(value==3){
                            return true;
                        }else{
                            return false;
                        }
                    }
                }
            }
        }
    }
});
</pre>                                
                         </li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>日期</td>
                    <td>date</td>
                    <td>
                       使用<code>${r'<@date>'?html}</code>控件会自动校验数据格式，不需要写表单验证。
                        <ul>
                        <li><code>"date-message":"请输入有效的日期"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        <li><code>"date-format":"yyyy/mm/dd"</code> <strong>/</strong>string <strong>/</strong>自定义日期格式，默认为"MM/DD/YYYY"</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>不同校验</td>
                    <td>different</td>
                    <td>
                        <ul>
                        <li><code>"different-message":"请输入不同的值"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        <li><code>"different-field":""</code> <strong>/</strong>string <strong>/</strong>将要使用的字段的名称与当前的字段进行比较</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>整数</td>
                    <td>integer</td>
                    <td>
                        <ul>
                        <li><code>"integer-message":"请输入有效的整数值"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>数字</td>
                    <td>digits</td>
                    <td>
                        <ul>
                        <li><code>"digits-message":"请输入有效的数字"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>数值</td>
                    <td>numeric</td>
                    <td>
                        <ul>
                        <li><code>"digits-message":"请输入有效的数值，允许小数"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>邮箱</td>
                    <td>emailAddress</td>
                    <td>
                        <ul>
                        <li><code>"emailaddress-message":"请输入有效的邮件地址"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>相同校验</td>
                    <td>identical</td>
                    <td>
                        <ul>
                        <li><code>"identical-message":"请输入相同的值"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        <li><code>"identical-field":""</code> <strong>/</strong>string <strong>/</strong>将要使用的字段的名称与当前的字段进行比较</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>字符串大小写</td>
                    <td>stringcase</td>
                    <td>
                        <ul>
                        <li><code>"stringcase-message":"请输入小写字符串"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        <li><code>"stringcase-case":"lower"</code> <strong>/</strong>String <strong>/</strong>lower为小写字母,upper为大写字母</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>字符串长度</td>
                    <td>stringlength</td>
                    <td>
                        <ul>
                        <li><code>"stringlength-message":"字符串长度不正确"</code>  <strong>/</strong>string <strong>/</strong>错误提示</li>
                        <li><code>"stringlength-min":"2"</code> <strong>/</strong>number <strong>/</strong>最小值</li>
                        <li><code>"stringlength-max":"5"</code> <strong>/</strong>number <strong>/</strong>最大值</li>
                        </ul>
                    </td>
                </tr>
                 <tr>
                    <td>是否只能输入中文（包括空格）</td>
                    <td>chinese</td>
                    <td>
                        <ul>
                        <li><code>"chinese":"true"</code>  <strong>/</strong>string <strong>/</strong>请输入中文</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>是否不能输入中文</td>
                    <td>nochinese</td>
                    <td>
                        <ul>
                        <li><code>"nochinese":"true"</code>  <strong>/</strong>string <strong>/</strong>不能输入中文</li>
                        </ul>
                    </td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
        
      </div>  
    </@fieldset>
    </article>
    
    
    
    <article id="form">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@form>'?html}表单
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@form>'?html}表单，<code>${'<@form>'?html}</code>会自动加载表单验证器，并默认转换为ajax表单，以json格式交互数据。
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@panel head="标题">'?html}
    ${'<@form>'?html}
        ${'<@row>'?html}
            ${'<@field>'?html}
                ${'<@input ... />'?html}
            ${'</@field>'?html}    
        ${'</@row>'?html}
    ${'</@form>'?html}
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
                    <td>表单id，若未设置，框架会生成一个随机id</td>
                </tr>
                <tr>
                    <td>列数</td>
                    <td>cols</td>
                    <td>int</td>
                    <td>3</td>
                    <td>默认3列展示，修改列数，会自动修改内部元素的布局效果。</td>
                </tr>
                <tr>
                    <td>表单提交action</td>
                    <td>action</td>
                    <td>string</td>
                    <td></td>
                    <td>表单提交action</td>
                </tr>
                <tr>
                    <td>表单method</td>
                    <td>method</td>
                    <td>string</td>
                    <td>post</td>
                    <td>默认post方式提交数据</td>
                </tr>
                <tr>
                    <td>表单数据编码方式</td>
                    <td>enctype</td>
                    <td>string</td>
                    <td>application/x-www-form-urlencoded</td>
                    <td>
                                            可选值：
                        <ul>
                        <li>application/x-www-form-urlencoded：在发送前编码所有字符（默认）</li>
                        <li>multipart/form-data：不对字符编码。<code>在使用包含文件上传控件的表单时，必须使用该值</code></li>
                        <li>text/plain：空格转换为 "+" 加号，但不对特殊字符编码</li>
                        </ul>
                    </td>
                </tr>
                <tr>
                    <td>是否ajax</td>
                    <td>ajax</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>默认ajax方式，若值为false，将不使用form插件对表单扩展</td>
                </tr>
                <tr>
                    <td>多次提交</td>
                    <td>multi_submit</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>默认在点击提交按钮，并且返回的json数据表示成功，提交按钮将置灰，设置multi_submit为true，可多次提交</td>
                </tr>
                <tr>
                    <td>before</td>
                    <td>before</td>
                    <td>function</td>
                    <td></td>
                    <td>表单提交前调用。返回false，将不提交表单。可用此属性添加前置动作。</td>
                </tr>
                <tr>
                    <td>after</td>
                    <td>after</td>
                    <td>function</td>
                    <td></td>
                    <td>js function，点击提交按钮后的调用方法。返回的json数据为方法参数。方法返回false，将不做后续操作（success_url后续不跳转）。
                                            可用此属性添加后置动作。</td>
                </tr>
                <tr>
                    <td>成功跳转url</td>
                    <td>success_url</td>
                    <td>string</td>
                    <td></td>
                    <td>url地址，表单提交成功后，会自动跳转</td>
                </tr>
                <tr>
                    <td>是否触发验证</td>
                    <td>auto_valid</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>点击提交按钮会自动触发输入项的验证。 为false时，需要手动验证。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
        <p>
         <b>Json数据模型：</b><br/> s:是否成功，msg:信息描述，code:错误码，obj:附加对象
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->            
<pre class="ar-pre-code">
{
    "s" : true
    "msg" : "操作成功",
    "code" : "",
    "obj" : null,
}
</pre>
</p>
      </div>  
    </@fieldset>
    </article>
    

<article id="file">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@field>'?html}字段
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@field>'?html}字段
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@row>'?html}
    ${r'<@field>'?html}
        ${'<@input ... />'?html}
    ${r'</@field>'?html}    
    ...
${r'</@row>'?html}
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
                    <td>字段名</td>
                    <td>label</td>
                    <td>string</td>
                    <td></td>
                    <td>对字段的描述</td>
                </tr>
                <tr>
                    <td>field宽度</td>
                    <td>field_ar</td>
                    <td>int</td>
                    <td>12</td>
                    <td>字段占据的宽度，默认表格分三列，所以字段占据36/3的宽度。在表格cols属性变化时，field_ar会自动计算</td>
                </tr>
                <tr>
                    <td>label的宽度</td>
                    <td>label_ar</td>
                    <td>int</td>
                    <td>12</td>
                    <td>在field中，label显示的宽度。默认label_ar与input_ar以1:2的比例显示</td>
                </tr>
                <tr>
                    <td>输入表单的宽度</td>
                    <td>input_ar</td>
                    <td>int</td>
                    <td>24</td>
                    <td>在field中，输入表单显示的宽度。默认input_ar与input_ar以1:2的比例显示</td>
                </tr>
                <tr>
                    <td>label的前缀</td>
                    <td>point</td>
                    <td>string</td>
                    <td></td>
                    <td>point会在label前面显示，默认字体颜色为红色，添加<span style="color:red">*</span>可着重标记字段</td>
                </tr>
                <tr>
                    <td>label的前缀是否显示</td>
                    <td>point_flag</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>point_flag为true，以红色显示point中的字符</td>
                </tr>
                <tr>
                    <td>自定义point_class</td>
                    <td>point_class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，可通过point_class为label添加图标</td>
                </tr>
                <tr>
                    <td>自定以point_style</td>
                    <td>point_style</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，可通过point_style为label添加图标</td>
                </tr>
                <tr>
                    <td>是否显示</td>
                    <td>show</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，将不渲染此标签。不会在html中留下任何内容。</td>
                </tr>
                <tr>
                    <td>是否隐藏字段</td>
                    <td>hidden</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>默认false，当表单中存在隐藏域，可将<code>${r'<@hidden>'?html}</code>放在<code>${r'<@field hidden="true">'?html}</code>中，多个隐藏域可放在同一个<code>${r'<@field>'?html}</code>中</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="input">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@input>'?html}输入框
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@input>'?html}输入框
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@input ... />'?html}
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
                    <td>输入框的值</td>
                </tr>
                <tr>
                    <td>类型</td>
                    <td>type</td>
                    <td>string</td>
                    <td>input</td>
                    <td>默认输入框，其他可选项：password</td>
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
                    <td>验证器</td>
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
                    <td>是否设为label</td>
                    <td>label_only</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>将输入框设置为label样式，默认false。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="hidden">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@hidden>'?html}隐藏域
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@hidden>'?html}隐藏域
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@hidden name="..." value="..." />'?html}
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
                    <td>name提交数据，会使用name属性。</td>
                </tr>
                <tr>
                    <td>值</td>
                    <td>value</td>
                    <td>string</td>
                    <td></td>
                    <td>隐藏域的值</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="select">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@select>'?html}下拉选择框
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@select>'?html}下拉选择框
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@select ... />'?html}
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
                    <td>下拉框的值</td>
                </tr>
                <tr>
                    <td>选项</td>
                    <td>options</td>
                    <td>map</td>
                    <td></td>
                    <td>map数据，例：{"M":"菜单","B":"按钮","P":"目录"}。可为空，直接在<code>${'<@select>'?html}</code>中输入<code>option</code>
<pre class="ar-pre-code">
${r'<@select>'?html}
    ${'<option value="M">菜单</option>'?html}
    ${'<option value="B">按钮</option>'?html}
    ${'<option value="P">目录</option>'?html}
${r'</@select>'?html}
</pre>
                    </td>
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
                    <td>验证器</td>
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
                    <td>是否显示value</td>
                    <td>showcode</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>在option中将value显示在label前，以-分隔</td>
                </tr>
                <tr>
                    <td>change事件</td>
                    <td>change</td>
                    <td>function</td>
                    <td></td>
                    <td>js function为下拉框绑定change事件</td>
                </tr>
                <tr>
                    <td>是否设为label</td>
                    <td>label_only</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>将下拉框设置为label样式，默认false。</td>
                </tr>
                <tr>
                    <td>是否追加显示</td>
                    <td>append</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>当选项都不匹配时（无key-value的映射），是否将字段值直接显示。为true时显示，此为特殊情况</td>
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
    
    <article id="selectLink">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@selectLink>'?html}联动下拉选择框
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@selectLink>'?html}下拉选择框
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@selectLink ... />'?html}
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
                    <td>下拉框的值</td>
                </tr>
                <tr>
                    <td>选项</td>
                    <td>options</td>
                    <td>map</td>
                    <td></td>
                    <td>map数据，例：{"M":"菜单","B":"按钮","P":"目录"}。可为空，直接在<code>${'<@select>'?html}</code>中输入<code>option</code>
<pre class="ar-pre-code">
${r'<@select>'?html}
    ${'<option value="M">菜单</option>'?html}
    ${'<option value="B">按钮</option>'?html}
    ${'<option value="P">目录</option>'?html}
${r'</@select>'?html}
</pre>
                    </td>
                </tr>
                <tr>
                    <td>关联上级Id</td>
                    <td>link_id</td>
                    <td>string</td>
                    <td></td>
                    <td>该id是与上级进行联动的id,当上级选择触发时,联动下拉框生效</td>
                </tr>
                <tr>
                    <td>联动的url路径</td>
                    <td>url</td>
                    <td>string</td>
                    <td></td>
                    <td>该url是指向进行联动的下级下拉框的取值路径</td>
                </tr>
                <tr>
                    <td>url_data</td>
                    <td>url_data</td>
                    <td>map</td>
                    <td></td>
                    <td>以map的方式向后台传递数据；当有中文时请用此方式传递数据</td>
                </tr>
                <tr>
                    <td>url_parent_key</td>
                    <td>url_parent_key</td>
                    <td>string</td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>显示对应属性的key值</td>
                    <td>keyfield</td>
                    <td>string</td>
                    <td>code</td>
                    <td>code表示在下拉框中显示的code属性的值,如果不写则找不到对应的属性值，显示为undefined</td>
                </tr>
                <tr>
                    <td>显示对应属性的value值</td>
                    <td>valuefield</td>
                    <td>string</td>
                    <td>codeName</td>
                    <td>codeName表示在下拉框中显示的name属性的值,如果不写则找不到对应的属性值，显示为undefined</td>
                </tr>
                <tr>
                    <td>过滤功能</td>
                    <td>filter</td>
                    <td>map</td>
                    <td></td>
                    <td>filter是过滤的功能，可以对一些keyfilter中的code值进行过滤，下拉框中就不显示改过滤的值</td>
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
                    <td>验证器</td>
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
                    <td>是否显示value</td>
                    <td>showcode</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>在option中将value显示在label前，以-分隔</td>
                </tr>
                <tr>
                    <td>change事件</td>
                    <td>change</td>
                    <td>function</td>
                    <td></td>
                    <td>js function为下拉框绑定change事件</td>
                </tr>
                <tr>
                    <td>是否设为label</td>
                    <td>label_only</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>将下拉框设置为label样式，默认false。</td>
                </tr>
                <tr>
                    <td>是否追加显示</td>
                    <td>append</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>当选项都不匹配时（无key-value的映射），是否将字段值直接显示。为true时显示，此为特殊情况</td>
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
    </@fieldset>
    </article>
    
    <article id="option">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@option>'?html}select自定义选项
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@option>'?html}select自定义选项
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@option key="APS" select_value="APS,CTS" showcode="false">APS</@option>'?html}
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
                    <td>key</td>
                    <td>key</td>
                    <td>string</td>
                    <td></td>
                    <td>选项的值，替换option标签的value属性</td>
                </tr>
                <tr>
                    <td>label</td>
                    <td>label</td>
                    <td>string</td>
                    <td></td>
                    <td>选项的label</td>
                </tr>
                <tr>
                    <td>select值</td>
                    <td>select_value</td>
                    <td>string</td>
                    <td></td>
                    <td>上级select的值</td>
                </tr>
                <tr>
                    <td>select值是否需要分隔</td>
                    <td>is_split</td>
                    <td>string</td>
                    <td>true</td>
                    <td>默认为分隔</td>
                </tr>
                <tr>
                    <td>值分隔符</td>
                    <td>value_split</td>
                    <td>string</td>
                    <td>,</td>
                    <td>当is_split值为true有效。select值的分隔字符串,默认以逗号(,)分隔</td>
                </tr>
                <tr>
                    <td>是否禁用选项</td>
                    <td>disabled</td>
                    <td>string</td>
                    <td></td>
                    <td>为disabled表示，该选项禁用</td>
                </tr>
                <tr>
                    <td>是否显示key</td>
                    <td>showcode</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>在option中将key显示在label前，以-分隔</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="multipleSelect">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@multipleSelect>'?html}多选下拉选择框
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@multipleSelect>'?html}多选下拉选择框
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@multipleSelect ... />'?html}
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
                    <td>控件的值，值是以value_split分隔的字符串</td>
                </tr>
                <tr>
                    <td>值分隔符</td>
                    <td>value_split</td>
                    <td>string</td>
                    <td>,</td>
                    <td>值的分隔字符串,默认以逗号(,)分隔</td>
                </tr>
                <tr>
                    <td>选项</td>
                    <td>options</td>
                    <td>map</td>
                    <td></td>
                    <td>map数据，例：{"M":"菜单","B":"按钮","P":"目录"}。可为空，直接在<code>${'<@multipleSelect>'?html}</code>中输入<code>option</code>和<code>optgroup</code>
<pre class="ar-pre-code">
${r'<@multipleSelect value="M,B,APS" line_options="true">'?html}
	${'<optgroup label="资源类型">'?html}
	    ${'<@option key="M" select_value="M,B,APS" showcode="false">菜单</@option>'?html}
	    ${'<@option key="B" select_value="M,B,APS" showcode="false">按钮</@option>'?html}
	    ${'<@option key="P" select_value="M,B,APS" showcode="false">目录</@option>'?html}
	${'</optgroup>'?html}
	${'<optgroup label="系统类型">'?html}
	    ${'<@option key="APS" select_value="M,B,APS" showcode="false">APS</@option>'?html}
    	${'<@option key="CTS" select_value="M,B,APS" showcode="false">CTS</@option>'?html}
	${'</optgroup>'?html}
${r'</@multipleSelect>'?html}
</pre>
                    </td>
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
                    <td>验证器</td>
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
                    <td>是否显示value</td>
                    <td>showcode</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>在option中将value显示在label前，以-分隔</td>
                </tr>
                <tr>
                    <td>change事件</td>
                    <td>change</td>
                    <td>function</td>
                    <td></td>
                    <td>js function为下拉框绑定change事件</td>
                </tr>
                <tr>
                    <td>是否设为label</td>
                    <td>label_only</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>将下拉框设置为label样式，默认false。</td>
                </tr>
                
                <tr>
                    <td>筛选框</td>
                    <td>showfilter</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>是否显示筛选框</td>
                </tr>
                <tr>
                    <td>单选</td>
                    <td>single</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>是否单选，单选使用radio替换checkbox</td>
                </tr>
                <tr>
                    <td>是否添加空选项</td>
                    <td>nullable</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>为true，会添加一行空的选项。在single时，才推荐使用此属性</td>
                </tr>
                <tr>
                    <td>是否单行多个选项</td>
                    <td>line_options</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>当值为true时，一行会有多个选项，每个选项宽度由option_width指定</td>
                </tr>
                <tr>
                    <td>每个选项宽度</td>
                    <td>option_width</td>
                    <td>int</td>
                    <td>80</td>
                    <td> 每个选项宽度，line_options为true，有效</td>
                </tr>
                <tr>
                    <td>全选按钮</td>
                    <td>select_all</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>是否出现全选按钮</td>
                </tr>
                
                <tr>
                    <td>下拉框出现时触发事件</td>
                    <td>on_open</td>
                    <td>function</td>
                    <td></td>
                    <td>下拉框出现时触发事件</td>
                </tr>
                <tr>
                    <td>下拉框关闭时触发事件</td>
                    <td>on_close</td>
                    <td>function</td>
                    <td></td>
                    <td>下拉框关闭时触发事件</td>
                </tr>
                <tr>
                    <td>全选事件</td>
                    <td>on_check_all</td>
                    <td>function</td>
                    <td></td>
                    <td>全选触发事件</td>
                </tr>
                <tr>
                    <td>取消全选事件</td>
                    <td>on_uncheck_all</td>
                    <td>function</td>
                    <td></td>
                    <td>取消全选触发事件</td>
                </tr>
                <tr>
                    <td>获得焦点触发事件</td>
                    <td>on_focus</td>
                    <td>function</td>
                    <td></td>
                    <td>获得焦点触发事件</td>
                </tr>
                <tr>
                    <td>失去焦点触发事件</td>
                    <td>on_blur</td>
                    <td>function</td>
                    <td></td>
                    <td>失去焦点触发事件</td>
                </tr>
                <tr>
                    <td>optgroup 点击时触发事件</td>
                    <td>on_optgroup_click</td>
                    <td>function</td>
                    <td></td>
                    <td>optgroup点击时触发事件</td>
                </tr>
                <tr>
                    <td>单个选项点击触发事件</td>
                    <td>on_option_click</td>
                    <td>function</td>
                    <td></td>
                    <td>单个选项点击触发事件</td>
                </tr>
                <tr>
                    <td>过滤时触发事件</td>
                    <td>on_filter</td>
                    <td>function</td>
                    <td></td>
                    <td>过滤时触发事件</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="checkbox">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@checkbox>'?html} checkbox / radio 多选/单选框
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@checkbox>'?html}多选/单选框
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@checkbox ... />'?html}
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
                    <td>name提交数据，会使用name属性。只有设置此属性，才能向后抬传递参数</td>
                </tr>
                <tr>
                    <td>类型</td>
                    <td>type</td>
                    <td>string</td>
                    <td>checkbox</td>
                    <td>默认checkbox多选框，当值为radio时单选框</td>
                </tr>
                <tr>
                    <td>值</td>
                    <td>value</td>
                    <td>string</td>
                    <td></td>
                    <td>控件的值，值是以value_split分隔的字符串</td>
                </tr>
                <tr>
                    <td>值分隔符</td>
                    <td>value_split</td>
                    <td>string</td>
                    <td>,</td>
                    <td>值的分隔字符串,默认以逗号(,)分隔</td>
                </tr>
                <tr>
                    <td>选项</td>
                    <td>options</td>
                    <td>map</td>
                    <td></td>
                    <td>map数据，例：{"M":"菜单","B":"按钮","P":"目录"}。可为空，直接在<code>${'<@checkbox>'?html}</code>中输入<code>option</code>
<pre class="ar-pre-code">
${r'<@checkbox name="resourceType" >'?html}
	${'<label><input type="checkbox" value="M" data-ar-value_split="," data-toggle="unicorn-checkbox-bind-hidden" name="resourceType_ar_show_"  /> 菜单 </label>'?html}
	${'<label><input type="checkbox" value="B" data-ar-value_split="," data-toggle="unicorn-checkbox-bind-hidden" name="resourceType_ar_show_" disabled="disabled" /> 按钮 </label>'?html}
	${'<label><input type="checkbox" value="P" data-ar-value_split="," data-toggle="unicorn-checkbox-bind-hidden" name="resourceType_ar_show_" /> 目录 </label>'?html}
${r'</@checkbox>'?html}
如果是自定义的checkbox，可在${'<@checkbox ... />'?html}中不写name属性，
input选项中不写 data-ar-value_split、
data-toggle="unicorn-checkbox-bind-hidden"属性，
input的name不需要_ar_show_后缀，像普通checkbox或readonly使用即可。
</pre>
                    </td>
                </tr>
                <tr>
                    <td>是否只读字段</td>
                    <td>readonly</td>
                    <td>string</td>
                    <td>false</td>
                    <td>为true表示，只读选项</td>
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
                    <td>是否显示选项代码</td>
                    <td>showcode</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>在option中将value显示在label前，以-分隔</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="date">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@date>'?html}日期时间
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@date>'?html}日期时间
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@date ... />'?html}
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
                    <td>时间日期类型</td>
                    <td>datetype</td>
                    <td>string</td>
                    <td>date</td>
                    <td>默认日期date类型。可选项：date,格式yyyyMMdd；time，格式HH:mm:ss，datetime，格式yyyyMMdd HH:mm:ss</td>
                </tr>
                <tr>
                    <td>扩展设置</td>
                    <td>settings</td>
                    <td>map</td>
                    <td></td>
                    <td>扩展设置，通过此属性，可定义任何格式。例：{"dateFmt":"yyyyMM"}</td>
                </tr>
                <tr>
                    <td>值</td>
                    <td>value</td>
                    <td>string</td>
                    <td></td>
                    <td>日期时间值</td>
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
                    <td>验证器，<code>${'<@date>'?html}</code>默认校验格式，一般情况不需要设置</td>
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
                    <td>是否设为label</td>
                    <td>label_only</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>将表单设置为label样式，默认false。</td>
                </tr>
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="file">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@file>'?html}文件上传
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@file>'?html}文件上传，文件上传需要表单的enctype属性等于multipart/form-data <code>${r'<@form enctype="multipart/form-data">'?html}</code>
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@file name="..." />'?html}
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
                    <td>输入框的值</td>
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
                    <td>验证器</td>
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
                </tbody>
            </@pureTable>
        </p>
      <div>  
    </@fieldset>
    </article>
    
    <article id="textarea">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@textarea>'?html}多行文本输入
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@textarea>'?html}多行文本输入
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@field>'?html}
    ${'<@textarea>XXX</@textarea >'?html}
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
                    <td>输入域的值，可为空，在${'<@textarea>'?html}和${'</@textarea >'?html}之间放置内容</td>
                </tr>
                <tr>
                    <td>列数</td>
                    <td>cols</td>
                    <td>string</td>
                    <td></td>
                    <td>输入域占据的列数，当放在${'<@field>'?html}标签中，需计算外部容器宽度(根据${'<@field>'?html}的filed_ar、label_ar、input_ar属性)</td>
                </tr>
                <tr>
                    <td>行数</td>
                    <td>rows</td>
                    <td>string</td>
                    <td></td>
                    <td>输入域占据的行数</td>
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
                    <td>验证器</td>
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
                    <td>是否设为label</td>
                    <td>label_only</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>将表单设置为label样式，默认false。</td>
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