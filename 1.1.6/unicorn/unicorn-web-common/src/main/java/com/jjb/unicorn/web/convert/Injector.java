package com.jjb.unicorn.web.convert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.jjb.unicorn.facility.kits.StrKit;


/**
 * @description : 
 * @author : jjb
 * @version : 2016年7月19日
 */
public class Injector {
	
	public static final String SPLIT_CHAR = ".";
	
	private static <T> T createInstance(Class<T> objClass) {
		try {
			return objClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static <T> T injectBean(Class<T> beanClass, HttpServletRequest request, boolean skipConvertError) {
		String beanName = beanClass.getSimpleName();
		return (T)injectBean(beanClass, StrKit.firstCharToLowerCase(beanName), request, skipConvertError);
	}
	
	@SuppressWarnings("unchecked")
	public static final <T> T injectBean(Class<T> beanClass, String beanName, HttpServletRequest request, boolean skipConvertError) {
		Object bean = createInstance(beanClass);
		String modelNameAndDot = StrKit.notBlank(beanName) ? beanName + SPLIT_CHAR : null;
		
		Map<String, String[]> parasMap = request.getParameterMap();
		Method[] methods = beanClass.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith("set") == false || methodName.length() <= 3) {	// only setter method
				continue;
			}
			Class<?>[] types = method.getParameterTypes();
			if (types.length != 1) {						// only one parameter
				continue;
			}
			
			String attrName = StrKit.firstCharToLowerCase(methodName.substring(3));
			String paraName = modelNameAndDot != null ? modelNameAndDot + attrName : attrName;
			if (parasMap.containsKey(paraName)) {
				try {
					String paraValue = request.getParameter(paraName);
					Object value = paraValue != null ? TypeConverter.convert(types[0], paraValue) : null;
					method.invoke(bean, value);
				} catch (Exception e) {
					if (skipConvertError == false) {
						throw new RuntimeException(e);
					}
				}
			}
		}
		
		return (T)bean;
	}
	
	/**
	 * 将请求内的参数封装为Map
	 * @param beanName
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final Map<String, Object> injectMap(String beanName, HttpServletRequest request) {

		Map<String, Object> resParams = new HashMap<String, Object>();
		
		String modelNameAndDot = StrKit.notBlank(beanName) ? beanName + SPLIT_CHAR : null;
		Map<String, String[]> parasMap = request.getParameterMap();

		for (Entry<String, String[]> entry : parasMap.entrySet()) {
			String paraName = entry.getKey();
			String attrName;
			if (modelNameAndDot != null) {
				if (paraName.startsWith(modelNameAndDot)) {
					attrName = paraName.substring(modelNameAndDot.length());
				} else {
					continue ;
				}
			} else {
				attrName = paraName;
			}
			
			String[] paraValueArray = entry.getValue();
			String paraValue = (paraValueArray != null && paraValueArray.length > 0) ? paraValueArray[0] : null;
			resParams.put(attrName, paraValue);
			
		}
		
		return resParams;
	}
	
}

