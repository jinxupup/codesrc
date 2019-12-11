package com.jjb.unicorn.web.tags;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.facility.kits.StrKit;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

public abstract class UnicornFunctionRouterTag implements TemplateMethodModelEx {

	Logger logger = LoggerFactory.getLogger(UnicornFunctionRouterTag.class);

	@SuppressWarnings("rawtypes")
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		return callMethod(arguments);
	}
	
	/**
	 * arguments 的第一个值为字符串，是要调用的方法。
	 * @param arguments
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected final Object callMethod( List arguments){
		if (arguments.size() < 1) {
			logger.debug("AresTags 参数调错误");
			return null;
		}

		String method = (String) arguments.get(0).toString();
		
		try {
			//禁止调用自身,禁止调用的方法
			if("callMethod".equals(method)||"getString".equals(method)||"checkArgumentsLength".equals(method)){
				throw new NoSuchMethodException();
			}
			Method methodInvoke = getClass().getDeclaredMethod(method,List.class);
			return methodInvoke.invoke(this, arguments);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			logger.debug("AresTags 调用错误,无{}方法", method );
			e.printStackTrace();
		}
		
		return null;
	}

	protected String getString(Object obj){
		if(obj==null){
			return "";
		}
		
		if(obj instanceof SimpleScalar){
			return (String)obj.toString();
		}else if(obj instanceof TemplateBooleanModel){
			try {
				boolean value = ((TemplateBooleanModel) obj).getAsBoolean();
				if(value==true){
					return "true";
				}else{
					return "false";
				}
			} catch (TemplateModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(obj instanceof TemplateNumberModel){
			try {
				return ((TemplateNumberModel) obj).getAsNumber().toString();
			} catch (TemplateModelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			return obj.toString();
		}
		return null;
	}
	
	
	/**
	 * 检查参数输入个数
	 * @param arguments
	 * @param min
	 * @return
	 */
	@SuppressWarnings("rawtypes") 
	protected final boolean argsSizeLess(List arguments,int min){
		
		if(arguments.size()<min){
			logger.info("ar_({})调用，参数个数输入错误,包含方法名，最少需要参数{}个",arguments.get(0),min);
			return false;
		}
		
		return true;
	}
	
	/**
	 * 列表对象转为map
	 * @param list 数据
	 * @param keyField
	 * @param valueField
	 * @return
	 */
	@SuppressWarnings("rawtypes") 
	protected final LinkedHashMap<String, Object> listToKeyValueMap(List list,String keyField,String valueField){
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		
		try{ 
			for(int i=0;i<list.size();i++ ){
				Object obj = list.get(i);
				Method getKeyMethod = obj.getClass().getDeclaredMethod("get" + StrKit.firstCharToUpperCase(keyField));
				Method getValueMethod = obj.getClass().getDeclaredMethod("get" + StrKit.firstCharToUpperCase(valueField));
				String key = getKeyMethod.invoke(obj).toString();
				Object value = getValueMethod.invoke(obj);
				map.put(key, value);
			}
		}catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
}
