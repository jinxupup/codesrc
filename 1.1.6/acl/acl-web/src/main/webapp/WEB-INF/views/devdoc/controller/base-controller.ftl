<#include "/layout.ftl"/>

<@body>

<@panel head="BaseController">
<@panelBody>
    
    		<div class="devArticleList" >
		    	<ul>
		    		<li> <a href="#BaseController">BaseController控制器基类</a> </li>
		    	</ul>
		    </div>
    
    <article id="BaseController">
    <@fieldset class="devdoc-fieldset">
    <@legend class="devdoc-legend">
        ${"BaseController"?html} BaseController控制器基类
    </@legend>
    <div >
        <p>
            <strong>说明：</strong><br/>
            ${"BaseController"?html}BaseController控制器基类，提供了一系列便捷获取请求信息的方法。自定义控制器，需要继承BaseController类。
            
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
package xxx ;
import BaseController;

@Controller
@RequestMapping("/xxx")
public class XxxController extends BaseController{
    
    @RequestMapping("/yyypage")
    public String yyypage(){
        <i>...</i>
        return "yyy/yyy.ftl";
    }
    
    @ResponseBody
    @RequestMapping("/zzz")
    public Json zzz(){
        Json j = Json.newSuccess();
        <i>...</i>
        return j;
    }    
}
</pre>
        </p>
        <p>
            <strong>属性：</strong>
            <@pureTable>
                <thead>
                <tr>
                    <th width="200px">#属性</th>
                    <th width="250px">#类型</th>
                    <th >#描述</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>request</td>
                    <td>HttpServletRequest</td>
                    <td>http 请求</td>
                </tr>
                <tr>
                    <td>response</td>
                    <td>model</td>
                    <td>http 响应</td>
                </tr>
                <tr>
                    <td>model</td>
                    <td>Model</td>
                    <td>响应数据模型</td>
                </tr>
                <tr>
                    <td>redirectAttributes</td>
                    <td>RedirectAttributes</td>
                    <td>
                                                重定向属性
                    </td>
                </tr>
                </tbody>
            </@pureTable>
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
                    <td>setAttr</td>
                    <td>BaseController <code>this</code></td>
                    <td>
                        <ul>
                            <li>setAttr(String name, Object value)
                            </li>
                        </ul>
                    </td>
                    <td>
                        设置属性到响应Model，使用此方法，不用手动声明与获取Model对象。返回自身(this),可链式调用。<br/>
                        <code>setAttr("userId","1234").setAttr("name","dai");</code>
                    </td>
                </tr>
                <tr>
                    <td>removeAttr</td>
                    <td>BaseController <code>this</code></td>
                    <td>
                        <ul>
                            <li>removeAttr(String name)
                            </li>
                        </ul>
                    </td>
                    <td>从响应Model中删除属性</td>
                </tr>
                <tr>
                    <td>getPara</td>
                    <td>String</td>
                    <td>
                        <ul>
                            <li>getPara(String name)
                            </li>
                            <li>getPara(String name, String defaultValue) <br/>
                                defaultValue 当获取的name的值为null或空字符串(trim()后为空)，返回defaultValue
                            </li>
                        </ul>
                    </td>
                    <td>根据name，获取字符串值</td>
                </tr>
                
                <tr>
                    <td>getParaToInt</td>
                    <td>Integer</td>
                    <td>
                        <ul>
                            <li>getParaToInt(String name)
                            </li>
                            <li>getParaToInt(String name, Integer defaultValue)<br/>defaultValue 当获取的name值为null或空字符串(trim()后为空)，返回defaultValue
                            </li>
                        </ul>
                    </td>
                    <td>将参数转为整数
                    </td>
                </tr>
                <tr>
                    <td>getParaToLong</td>
                    <td>Long</td>
                    <td>
                        <ul>
                            <li>getParaToLong(String name)
                            </li>
                            <li>getParaToLong(String name, Long defaultValue) 
                                <br/>defaultValue 当获取的name的值为null或空字符串(trim()后为空)，返回defaultValue
                            </li>
                        </ul>
                    </td>
                    <td>将参数转为Long</td>
                </tr>
                <tr>
                    <td>getParaToBoolean</td>
                    <td>Boolean</td>
                    <td>
                        <ul>
                            <li>getParaToBoolean(String name)
                            </li>
                            <li>getParaToBoolean(String name, boolean defaultValue) <br/>
                                defaultValue 当获取的name的值为null或空字符串(trim()后为空)，返回defaultValue
                            </li>
                         </ul>
                    </td>
                    <td>将参数转为Boolean值，<br/>"1"、"true"、"Y"、"y"得到true，<br/>"0"、"false"、"N"、"n"得到false，<br/>其他值将抛出运行时异常</td>
                </tr>
                
                <tr>
                    <td>getParaToDate</td>
                    <td>java.util.Date</td>
                    <td>
                        <ul>
                            <li>getParaToDate(String name)
                            </li>
                            <li>getParaToDate(String name, Date defaultValue) <br/>
                                defaultValue 当获取的name的值为null或空字符串(trim()后为空)，返回defaultValue
                            </li>
                         </ul>
                    </td>
                    <td>
                                                将参数转为Date值,支持格式:<br/>
                                                 日期格式 yyyy-MM-dd <br/>
                                                 时间日期格式 yyyy-MM-dd HH:mm:ss
                    </td>
                </tr>
                <tr>
                    <td>getFile</td>
                    <td>MultipartFile</td>
                    <td>
                        <ul>
                            <li>getFile(String fileName)</li>
                        </ul>
                    </td>
                        <td>获取单个上传的文件
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">使用样例：                
    MultipartFile file = getFile(<i>"文件名"</i>);
    File targetFile = new File("d:\\targetFile.jpg"); <i>//本地保存的文件</i>
    try {
        file.transferTo(targetFile); <i>//将MultipartFile转换为本地文件,</i>*常用方法*
    } catch (IllegalStateException e) {
        // TODO 异常处理
        e.printStackTrace();
    } catch (IOException e) {
        // TODO 异常处理
        e.printStackTrace();
    }
</pre>                           
                        </td>
                    </tr>
                    <tr>
                    <td>getFileRequest</td>
                    <td>MultipartHttpServletRequest</td>
                    <td>
                        <ul>
                            <li>getFileRequest();
                            </li>
                        </ul>
                    </td>
                    <td>将请求转换为MultipartHttpServletRequest，可以获取文件，此请求可对上传的文件做响应的操作。
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code">
<i>//获取单个文件，可见getFile(String name)方法</i>                      
MultipartFile file = getFileRequest().getFile("文件名");
<i>//获取所有上传文件的名字</i>
Iterator<String> fileNames = getFileRequest().getFileNames();
<i>//获取某一名字的所有上传文件</i>
List<MultipartFile> files = getFileRequest().getFiles("文件名");
<i>//获取所有上传文件，key为名字，value为文件</i>
Map<String,MultipartFile> fileMap = getFileRequest().getFileMap();
</pre>                        
                        
                    </td>
                </tr>
                
                <tr>
                    <td>getDirectBean</td>
                    <td><T> T</td>
                    
                    <td>getDirectBean(Class<T> clazz)</td>
                    <td>
                                            使用了ServletRequestDataBinder，自动创建T实例，并将请求参数绑定到该实例上。和controller方法中使用对象变量效果一样。
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code"><i>    //手动声明对象，自动创建与绑定参数</i>
    @RequestMapping("/yyypage")
    public String yyypage(){
        TmAclDict dict = getDirectBean(TmAclDict.class);
        System.out.println(dict.getType); <i>如果请求中有 名字为type的请求参数，此处有值</i>
        return "yyy/yyy.ftl";
    }
</pre>
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code"><i>    //方法声明对象，自动绑定参数</i> 
    @RequestMapping("/yyypage")
    public String yyypage(TmAclDict dict){
        System.out.println(dict.getType); <i>如果请求中有 名字为type的请求参数，此处有值</i>
        return "yyy/yyy.ftl";
    }
</pre>                      
                    </td>
                </tr>
                <tr>
                    <td>getQuery</td>
                    <td>Query</td>
                    
                    <td>
                        <ul>
                            <li>getQuery()
                            </li>
                        </ul>
                    </td>
                    <td>Query类继承HashMap<String, Object>
                        
                        请求参数名需要以 query. 开头，调用getQuery()可自动将请求参数绑入。
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code"><i>    //方法声明对象，自动绑定参数</i> 
    <i>ftl代码</i>
    ${r'<@input name="query.name" value="目录" />'?html}
    ${r'<@input name="query.type" value="类型" />'?html}
    
    <i>controller代码</i>
    Query query = getQuery() <i>//query对象值为　{"name":"目录","type":"类型"}</i>
</pre>  
                    </td>
                </tr>
                <tr>
                    <td>getPage</td>
                    <td><T> Page<T></td>
                    <td>
                        <ul>
                            <li>getPage(Class<T> clazz)<br/>
                                clazz Page中rows数据的对象类型
                            </li>
                        </ul>
                    </td>
                    <td>
                    前台是<code>${r'<@table>'?html}</code>时，可便捷的使用此方法，创建Page对象。
                    getPage方法会并绑入查询参数Query，排序参数（sortName、sortOrder），分页参数（pageNumber、pageSize。
                    </td>
                </tr>
                
                <tr>
                    <td>getBean</td>
                    <td><T> T</td>
                    <td>
                        <ul>
                            <li>getBean(Class<T> beanClass)
                            </li>
                            <li>getBean(Class<T> beanClass, boolean skipConvertError)
                            </li>
                            <li>getBean(Class<T> beanClass,String beanName)
                            </li>
                            <li>getBean(Class<T> beanClass,String beanName, boolean skipConvertError)
                            </li>
                        </ul>
                         beanClass 转换对象的类，beanName 请求参数前缀名（为空时，默认为将第一个字母转为小写的类名），skipConvertError 字段转换失败是否跳过错误（不抛出异常）
                    </td>
                    <td>
                        当页面复杂，涉及很多模型操作，使用此方法，可将数据分组。
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code"><i>    //方法声明对象，自动绑定参数</i> 
    <i>ftl代码</i>
    ${r'<@input name="dict.code" value="B" />'?html}
    ${r'<@input name="dict.codeName" value="按钮" />'?html}
    ${r'<@input name="dict2.code" value="M" />'?html}
    ${r'<@input name="dict2.codeName" value="菜单" />'?html}
    
    <i>controller代码</i>
    TmAclDict b = getBean(TmAclDict.class,"dict"); <i>//绑定dict对象值  b.code 为 B</i>
    TmAclDict m = getBean(TmAclDict.class,"dict2");<i>//绑定dict2对象值  m.code 为 M</i>
</pre>                          
                        
                    </td>
                </tr>
                <tr>
                    <td>getMap</td>
                    <td>Map<String, Object></td>
                    <td>getMap(String mapName)
                        mapName 请求参数前缀名
                    </td>
                    <td>将请求转为map
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code"><i>    //方法声明对象，自动绑定参数</i> 
    <i>ftl代码</i>
    ${r'<@input name="map.code" value="B" />'?html}
    ${r'<@input name="map.codeName" value="按钮" />'?html}
    
    <i>controller代码</i>
    Map map = getMap("map"); //绑定map对象值为{"code":"B","codeName":"按钮"}
</pre>                      
                    </td>
                </tr>
               
                <tr>
                    <td>getList</td>
                    <td><T> List<T></td>
                    <td>
                        <ul>
                            <li>getList(Class<T> beanClass,String listName)</li>
                        </ul>
                    </td>
                    <td>获取对象列表
<#-- 中的内容请居于最左侧，其中的tab使用4个空格替代 -->
<pre class="ar-pre-code"><i>    //方法声明对象，自动绑定参数</i> 
    <i>ftl代码</i>
    ${r'<@input name="dicts[0].code" value="B" />'?html}
    ${r'<@input name="dicts[0].codeName" value="按钮" />'?html}
    ${r'<@input name="dicts[1].code" value="M" />'?html}
    ${r'<@input name="dicts[1].codeName" value="菜单" />'?html}
    
    <i>controller代码</i>
    List<TmAclDict> list = getList(TmAclDict.class, "dicts"); <br/>
    //绑定list对象值为[{"code":"B","codeName":"按钮"},{"code":"M","codeName":"菜单"}]
</pre>

                    </td>
                </tr>
                <tr>
                    <td>setEdit</td>
                    <td>void</td>
                    <td>
                        <ul>
                            <li>setEdit()</li>
                        </ul>
                    </td>
                    <td>将编辑状态值设置为true，并放入响应对象，供页面使用</td>
                </tr>
                <tr>
                    <td>redirect</td>
                    <td>String</td>
                    <td>redirect(String action)</td>
                    <td>重定向页面</td>
                </tr>
                <tr>
                    <td>forward</td>
                    <td>String</td>
                    <td>forward(String action)</td>
                    <td>前向跳转</td>
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