package com.jjb.unicorn.web.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jjb.unicorn.facility.constant.Literal;
import com.jjb.unicorn.facility.context.AppHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.web.convert.Injector;
import com.jjb.unicorn.web.convert.UnicornDateEditor;

/**
 * 封装获取参数的方法
 * @author jjb
 *
 */
public class BaseController{
	
	Logger logger = LoggerFactory.getLogger(getClass());

	//页面编码格式
	public static final String CHAR_SET = "UTF-8";
	//请求参数名与属性的分隔符
	public static final String SPLIT_CHAR = Injector.SPLIT_CHAR;
	//默认查询字符串前缀
	public static final String QUERY_STR = "query";
	
	//是否是编辑状态
	public static final String isEdit = "isEdit";
	
	private static ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<HttpServletRequest>();
	protected static ThreadLocal<HttpServletResponse> currentResponse = new ThreadLocal<HttpServletResponse>();
	protected static ThreadLocal<Model> currentModel = new ThreadLocal<Model>();
	
	@ModelAttribute
	public void init(HttpServletRequest request,HttpServletResponse response,Model model){
		
		currentRequest.set(request);
		currentResponse.set(response);
		currentModel.set(model);
		currentModel.get().addAttribute(isEdit, false);
		
	}
	
	protected HttpServletRequest getRequest() {
		return currentRequest.get();
	}
	
	protected HttpServletResponse getResponse() {
		return currentResponse.get();
	}
	
	protected Model getModel() {
		return currentModel.get();
	}
	
	@InitBinder  
    public void initBinder(WebDataBinder binder) {  
        binder.registerCustomEditor(Date.class, new UnicornDateEditor());
    }
	
	/**
	 * 为view设置属性
	 * @param name
	 * @param value
	 * @return
	 */
	public BaseController setAttr(String name, Object value) {
		getModel().addAttribute(name, value);
		return this;
	}
	
	public BaseController removeAttr(String name) {
		getRequest().removeAttribute(name);
		return this;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 */
	private String toCN(String value){
		if(value==null){
			return value;
		}
		try {
			if(StrKit.notBlank(AppHolder.getURIEncoding())){
				return new String(value.getBytes(AppHolder.getURIEncoding()),CHAR_SET);
			}else{
				return new String(value.getBytes("ISO-8859-1"),CHAR_SET);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value;
	}
	/**
	 * 当http method是get时，使用此方法。解析乱码。
	 * @param name
	 * @return
	 */
	public String getParaCN(String name){
		if("GET".equalsIgnoreCase(getRequest().getMethod())){
			return toCN(getRequest().getParameter(name));
		}else{
			return getRequest().getParameter(name);
		}
	}
	
	
	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	public String getPara(String name) {
		return getRequest().getParameter(name);
	}
	
	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	public String[] getParas(String name) {
		return getRequest().getParameterValues(name);
	}
	
	/**
	 * 获取参数，设置默认值
	 * @param name
	 * @return
	 */
	public String getPara(String name, String defaultValue) {
		String result = getRequest().getParameter(name);
		return result != null && !"".equals(result) ? result : defaultValue;
	}
	
	
	private Integer toInt(String value, Integer defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			value = value.trim();
			if (value.startsWith("N") || value.startsWith("n"))
				return -Integer.parseInt(value.substring(1));
			return Integer.parseInt(value);
		}
		catch (Exception e) {
			throw new RuntimeException("不能将值： \"" + value + "\" 转换为int.");
		}
	}
	
	/**
	 * 获取参数转int
	 * @param name
	 * @return
	 */
	public Integer getParaToInt(String name) {
		return toInt(getRequest().getParameter(name), null);
	}
	
	/**
	 * 获取参数转int,设置默认值
	 * @param name
	 * @return
	 */
	public Integer getParaToInt(String name, Integer defaultValue) {
		return toInt(getRequest().getParameter(name), defaultValue);
	}
	
	private Long toLong(String value, Long defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			value = value.trim();
			if (value.startsWith("N") || value.startsWith("n"))
				return -Long.parseLong(value.substring(1));
			return Long.parseLong(value);
		}
		catch (Exception e) {
			throw new RuntimeException("不能将值： \"" + value + "\" 转换为long.");
		}
	}
	
	public Long getParaToLong(String name) {
		return toLong(getRequest().getParameter(name), null);
	}
	
	public Long getParaToLong(String name, Long defaultValue) {
		return toLong(getRequest().getParameter(name), defaultValue);
	}
	
	private Boolean toBoolean(String value, Boolean defaultValue) {
		if (value == null || "".equals(value.trim()))
			return defaultValue;
		value = value.trim().toLowerCase();
		if ("1".equals(value) || "true".equals(value)||"Y".equalsIgnoreCase(value))
			return Boolean.TRUE;
		else if ("0".equals(value) || "false".equals(value)||"N".equalsIgnoreCase(value))
			return Boolean.FALSE;
		throw new RuntimeException("不能将值： \"" + value + "\" 转换为boolean.");
	}
	
	public Boolean getParaToBoolean(String name) {
		return toBoolean(getRequest().getParameter(name), null);
	}
	
	public Boolean getParaToBoolean(String name, Boolean defaultValue) {
		return toBoolean(getRequest().getParameter(name), defaultValue);
	}
	
	
	private Date toDate(String value, Date defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			
			value = value.trim();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	        format.setLenient(true);
	        Date date = null;  
	        try {  
	            date = format.parse(value);  
	        } catch (ParseException e) {  
	            format = new SimpleDateFormat("yyyy-MM-dd");  
	            format.setLenient(true);
	            try {  
	                date = format.parse(value);  
	            } catch (ParseException e1) {  
	                e1.printStackTrace();  
	            }  
	        }
			
			return date;
		} catch (Exception e) {
			throw new RuntimeException("不能将值： \"" + value + "\" 转换为Date.");
		}
	}
	
	/**
	 * 获取参数转日期
	 * @param name
	 * @return
	 */
	public Date getParaToDate(String name) {
		return toDate(getRequest().getParameter(name), null);
	}
	
	/**
	 * 获取参数转日期，设置默认值
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Date getParaToDate(String name, Date defaultValue) {
		return toDate(getRequest().getParameter(name), defaultValue);
	}
	
	/**
	 * 获取文件请求对象，做相关文件操作
	 * @return
	 */
	public MultipartHttpServletRequest getFileRequest(){
		return (MultipartHttpServletRequest) getRequest();
	}
	
	/**
	 * 获取单个文件
	 * @param fileName
	 * @return
	 */
	public MultipartFile getFile(String fileName){
		return getFileRequest().getFile(fileName);
	}
	
	/**
	 * 直接获取bean，和写在方法上的方式一样
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getDirectBean(Class<T> clazz){
		ServletRequestDataBinder binder = new ServletRequestDataBinder(BeanUtils.instantiateClass(clazz));
		binder.registerCustomEditor(Date.class, new UnicornDateEditor());
	 	binder.bind(getRequest());
		return (T) binder.getTarget();		
	}
	
	/**
	 * 获取查询条件
	 * @return
	 */
	public Query getQuery(){
		Query query = getQuery(QUERY_STR);
		//GET请求转码
		if("GET".equalsIgnoreCase(getRequest().getMethod()))
/*		{
			for(Map.Entry<String, Object> entry : query.entrySet()){
				String key = entry.getKey();
				String value = (String) entry.getValue();
				if(StrKit.notBlank(value)){
					query.put(key, toCN(value));
				}
			}
		}else*/
			{
			for(Map.Entry<String, Object> entry : query.entrySet()){
				String key = entry.getKey();
				String value = (String) entry.getValue();
				if(StrKit.notBlank(value)){
					query.put(key, value);
				}
			}
		}
		
		String sortName = getPara("sortName", "1");
		String sortOrder = getPara("sortOrder", "asc");
		if(!"1".equals(sortName)){
			query.put(Literal._SORT_NAME, sortName);
			query.put(Literal._SORT_ORDER, sortOrder);
	    }
				
		return query;
	}
	
	public <T> Page<T> getPage(Class<T> clazz){
		
		Query query = getQuery();
		
		Page<T> page = new Page<T>();
	    page.setQuery(query);
	    page.setPageNumber(getParaToInt("pageNumber",1));
	    page.setPageSize(getParaToInt("pageSize",10));
	    page.setSortName(getPara("sortName", "1"));
	    page.setSortOrder(getPara(" sortOrder", "asc"));
	    
		return page;
	}
	
	public Query getQuery(String name){
		Query parameter = new Query();
		Map<String, Object> map = Injector.injectMap(name,getRequest());
		parameter.putAll(map);
		return parameter;
	}
	
	/**
	 * 获取bean
	 * @param beanClass
	 * @return
	 */
	public <T> T getBean(Class<T> beanClass) {
		return (T)Injector.injectBean(beanClass, getRequest(), false);
	}
	
	public <T> T getBean(Class<T> beanClass, boolean skipConvertError) {
		return (T)Injector.injectBean(beanClass, getRequest(), skipConvertError);
	}
	
	/**
	 * 获取bean
	 * @param beanClass
	 * @param beanName
	 * @return
	 */
	public <T> T getBean(Class<T> beanClass, String beanName) {
		return (T)Injector.injectBean(beanClass, beanName, getRequest(), false);
	}
	
	public <T> T getBean(Class<T> beanClass, String beanName, boolean skipConvertError) {
		return (T)Injector.injectBean(beanClass, beanName, getRequest(), skipConvertError);
	}
	 
	/**
	 * 获取map
	 * @param mapName
	 * @return
	 */
	public Map<String, Object> getMap(String mapName){
		return Injector.injectMap(mapName,getRequest());
	}
	
	/**
	 * 获取列表
	 * @param beanClass 列表内元素的类型
	 * @param listName  列表名称
	 * @return
	 */
	public <T> List<T> getList(Class<T> beanClass,String listName){
		List<T> list = new ArrayList<T>();
		
		Map<String, String[]> parasMap = getRequest().getParameterMap();
		
		Map<Integer,T> tmpMap = new TreeMap<Integer, T>();
		
		//名称匹配表达式，例：queues[1]-name
		Pattern namePattern = Pattern.compile("("+listName+"\\[(\\d+)\\])\\"+SPLIT_CHAR+".+");
		
		for(Map.Entry<String, String[]> entry:parasMap.entrySet()){
			String name = entry.getKey();
			Matcher matcher = namePattern.matcher(name);
			if(matcher.matches()){
				String element = matcher.group(1);
				Integer index = Integer.parseInt(matcher.group(2));
				if(tmpMap.containsKey(index)){
					//若map包含相同index，跳过
					continue;
				}else{
					T t = getBean(beanClass,element);
					tmpMap.put(index, t);
				}
			}
		}
		
	    for(Map.Entry<Integer, T> entry:tmpMap.entrySet()){
		    list.add(entry.getValue());
	    }
		
	    return list;
	}
	
	/**
	 * 设置为编辑状态
	 */
	protected void setEdit(){
		setAttr(isEdit, true);
	}
	
	/**
	 * 重定向
	 * @return
	 */
	protected String redirect(String action){
		return "redirect:"+action;
	}
	
	/**
	 * 向前请求
	 * @return
	 */
	protected String forward(String action){
		return "forward:"+action;
	}
	
}
