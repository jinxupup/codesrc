<#include "/layout.ftl"/>

<@body>
<@panel head="表格">
<@panelBody>

			<div class="devArticleList" >
		    	<ul>
		    		<li> <a href="#pureTable">pureTable 原生表格 </a> </li>
		    		<li> <a href="#table">table 数据表格 </a> </li>
		    		<li> <a href="#th">th 表格字段 </a> </li>
		    		<li> <a href="#thDate">thDate 内日期展示 </a> </li>
		    	</ul>
		    </div>

    <article id="pureTable">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@pureTable>'?html}原生表格
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@pureTable>'?html}是原生的表格。书写时，请注意内部的html结构
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@pureTable>'?html}
    ${'<tbody>'?html}
        ${'<tr>'?html}
            ${'<th>'?html} <i>标题</i> ${'</th>'?html}
        ${'</tr>'?html}
        ${'<tr>'?html}
            ${'<td>'?html} <i>单元</i> ${'</td>'?html}
        ${'</tr>'?html}
    ${'</tbody>'?html}
${r'</@pureTable>'?html}    
</pre>
        </p>
        <p>
            <strong>属性：</strong>
            <@pureTable bordered="false">
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
                    <td>HTML id</td>
                </tr>
                <tr>
                    <td>是否条纹状表格</td>
                    <td>striped</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>给 <code>${'<tbody>'?html}</code> 之内的每一行增加斑马条纹样式</td>
                </tr>
                <tr>
                    <td>带边框的表格</td>
                    <td>bordered</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，表格没有外边框，单元格之间没有分隔线</td>
                </tr>
                <tr>
                    <td>鼠标悬停</td>
                    <td>hover</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为true时，鼠标移动到行上有hove效果</td>
                </tr>
                <tr>
                    <td>紧凑表格</td>
                    <td>condensed</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为true时，使表格看起来紧凑。其他请参见class属性</td>
                </tr>
                <tr>
                    <td>自定义class</td>
                    <td>class</td>
                    <td>string</td>
                    <td></td>
                    <td>自定义class，以追加的方式输出。设置为 thin-body 可将表格展示为瘦表格。将表格设置为定高表格，可填写一下javascript代码
<pre class="ar-pre-code">
  $("#tableId").bootstrapTable({height:"300",classes:'thin-table'});
  $("#tableId").parent().unicornScrollUnique(); 
</pre>                    	
                    </td>
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

<article id="table">   
<@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@table>'?html}数据表格
    </@legend>
        <p>
            <strong>说明：</strong><br/>
            ${'<@table>'?html}是数据表格，是unicorn中主要使用的表格，数据由url返回的json数据载入。
                        内部只用定义${r'<@th>'?html}标签，就可完成表格功能。
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@table>'?html}
    ${'<@th>'?html} <i>ID</i>  ${'</@th>'?html}
    ${'<@th>'?html} <i>名称</i> ${'</@th>'?html}
    ${'<@th>'?html} <i>说明</i> ${'</@th>'?html}
${r'</@table>'?html}    
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
                    <td>HTML id</td>
                </tr>
                <tr>
                    <td>是否自动选中行</td>
                    <td>click_to_select</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>点击行，是否自动选中对应的checkbox</td>
                </tr>
                <tr>
                    <td>是否单选</td>
                    <td>single_select</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>为true，数据单选</td>
                </tr>
                <tr>
                    <td>查询表单id</td>
                    <td>form_id</td>
                    <td>string</td>
                    <td></td>
                    <td>为表格提供查询功能的表单的id</td>
                </tr>
                <tr>
                    <td>查询按钮id</td>
                    <td>button_id</td>
                    <td>string</td>
                    <td></td>
                    <td>点击id，触发表格的查询功能</td>
                </tr>
                <tr>
                    <td>是否分页</td>
                    <td>pagination</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>是否开启表格的分页功能</td>
                </tr>
                <tr>
                    <td>每页数量</td>
                    <td>page_size</td>
                    <td>int</td>
                    <td>10</td>
                    <td>分页，每页的数量</td>
                </tr>
                <tr>
                    <td>数据加载地址</td>
                    <td>url</td>
                    <td>string</td>
                    <td></td>
                    <td>${r'<@table>'?html} 使用ajax方式，从服务器端加载表格数据。 每次数据请求操作都会调用此url，要求返回的数据模型为Page</td>
                </tr>
                <tr>
                    <td>数据是否自动加载</td>
                    <td>load_auto</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>${r'<@table>'?html} 默认会自动从服务器端加载数据，设置load_auto为false，进入页面不会自动加载数据</td>
                </tr>
                <tr>
                    <td>高度设置</td>
                    <td>height</td>
                    <td>string</td>
                    <td></td>
                    <td>对${r'<@table>'?html}表格设置高度，控制样式 </td>
                </tr>
                <tr>
                    <td>行数据样式</td>
                    <td>row_style</td>
                    <td>function</td>
                    <td></td>
                    <td>为数据行设置样式，可根据行数据不同，返回不同的样式对象，展示样式。方法有两个参数,row:行数据，index:行号
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->                    
<pre class="ar-pre-code">
function rowStyle(row, index) {
  if(row.xx=xx){
    return {
        classes: 'active', //'active', 'success', 'info', 'warning', 'danger','primary'
        css: {"color": "blue", "font-size": "50px"}
    };
  }else if(xxx){
    return {
        classes: '',
        css: {"color": "blue", "font-size": "50px"}
    };
  }
}
</pre>
</td>
                </tr>
                <tr>
                    <td>是否条纹状表格</td>
                    <td>striped</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>给 <code>${'<tbody>'?html}</code> 之内的每一行增加斑马条纹样式</td>
                </tr>
                <tr>
                    <td>带边框的表格</td>
                    <td>bordered</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为false时，单元格之间没有分隔线</td>
                </tr>
                <tr>
                    <td>紧凑表格</td>
                    <td>condensed</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>为true时，使表格看起来紧凑</td>
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
        <p>
            <b>Page数据模型：</b><br/>
            rows是当前页数据，total是总记录数
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->            
<pre class="ar-pre-code">
{
    "rows" : [{
            "code" : "P",
            "codeName" : "目录",
            "type" : "RESOURCE_TYPE",
            "typeName" : "资源类型"
            },
            ....
            {
            "code" : "M",
            "codeName" : "菜单",
            "type" : "RESOURCE_TYPE",
            "typeName" : "资源类型"
            }
    ],
    "total" : 22
}
</pre>
        </p>
    </@fieldset>   
</article>    


<article id="th">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@th>'?html}表格字段
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@th>'?html}是${'<@table>'?html}的内部元素，${'<@th>'?html}会解析${'<@table>'?html}url返回的Page中的rows数据，并以合适的方式呈现。
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@table>'?html}
    ${'<@th title="代码">'?html} ${'</@th>'?html}
    ${'<@th title="名称">'?html} ${'</@th>'?html}
    ${'<@th title="类型">'?html} ${'</@th>'?html}
${r'</@table>'?html}    
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
                    <td>选中</td>
                    <td>checkbox</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>为true时，可将此列标记为checkbox列。一个表格只能有一个checkbox列</td>
                </tr>
                <tr>
                    <td>标题</td>
                    <td>title</td>
                    <td>boolean</td>
                    <td>true</td>
                    <td>此列的标题</td>
                </tr>
                <tr>
                    <td>字段</td>
                    <td>field</td>
                    <td>string</td>
                    <td></td>
                    <td>列代表的字段，rows中对象的key，将对应的值展示在单元格中</td>
                </tr>
                <tr>
                    <td>宽度</td>
                    <td>width</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>列的宽度。如果没有设置，宽度会根据内容自动填充。默认px为单位，可以添加'%'，则按比例设置宽度</td>
                </tr>
                <tr>
                    <td>是否可排序</td>
                    <td>sortable</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>true可设置此列可用于排序</td>
                </tr>
                <tr>
                    <td>渲染方法</td>
                    <td>formatter</td>
                    <td>function</td>
                    <td></td>
                    <td>js function名字。用js方法格式化该列的数据，该方法有三个参数：value:字段值row:行数据index:行索引</td>
                </tr>
                <tr>
                    <td>内部渲染</td>
                    <td>render</td>
                    <td>boolean</td>
                    <td>false</td>
                    <td>为true时，会直接将<code>${'<@th>'?html}</code><i>...内容...</i><code>${'</@th>'?html}</code>中的内容渲染到页面上。
                    渲染使用 artTemplate JS模版引擎，内部判断语法可使用符合artTemplate模板引擎的语法。
                                                有三个模版变量{{row}}:行数据，{{index}}:行索引，{{value}}:字段值(设置了field属性可用)。render为true时，formatter将失效。
<br/>内部渲染例子：
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@th title="操作" render="true" >'?html}                                     
    ${r'<@href href="dict/editpage?id={{row.id}}" name="编辑" />'?html}
    ${r'<@href href="dict/addpage?id={{row.id}}" name="复制" />'?html}
    {{if row.ifCanDel=="Y"}}
        ${r'<@href href="dict/delete?id={{row.id}}" sense="danger" name="删除" />'?html}
     {{/if}}
${r'</@th>'?html}                                            
</pre>                                               
                     </td>
                </tr>
                <tr>
                    <td>单元格样式</td>
                        <td>cell_style</td>
                        <td>function</td>
                        <td></td>
                    <td>js function 为单元格设置样式，可根据数据不同，返回不同的样式对象，展示样式。方法有四个参数,value:字段值，row:行数据，index:行号，field:字段
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->                    
<pre class="ar-pre-code">
function cellStyle(value, row, index, field) {
  if(row.xx=xx){
    return {
        classes: 'active', //'text-nowrap','active', 'success', 'info', 'warning', 'danger','primary'
        css: {"color": "blue", "font-size": "50px"}
    };
  }else if(xxx){
    return {
        classes: '',
        css: {"color": "blue", "font-size": "50px"}
    };
  }
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
    
    <article id="thDate">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${'<@thDate>'?html} ${'<@th>'?html}内日期展示
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${'<@thDate>'?html} ${'<@th>'?html}行内日期时间显示，用于<code>${'<@th>'?html}</code>行内的date类型数据显示。
            使用此标签，需要在<code>${'<@th>'?html}</code>中设置<code>render="true"</code>
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
${r'<@th render="true">'?html}
    ${'<@thDate  value="{{row.xxx}}" />'?html}
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
                    <td>datetype</td>
                    <td>string</td>
                    <td></td>
                    <td>日期时间类型，可选值date(默认)、datetime、time</td>
                </tr>
                <tr>
                    <td>值</td>
                    <td>value</td>
                    <td>string</td>
                    <td></td>
                    <td>定义时间的取值来源，使用模版变量，{{row.xxx}}，在数据模型中此字段是java.utl.Date类型</code>。
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