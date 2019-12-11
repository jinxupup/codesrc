package com.jjb.unicorn.facility.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 集合判断
 * @author JYData-R&D-HN
 * @date 2016年9月1日 下午8:25:35
 * @version V1.0
 */
public class CollectionUtils implements Serializable{
	private static Logger logger = LoggerFactory.getLogger(CollectionUtils.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CollectionUtils() {
	}

	/**
	 * Judge collection's empty.
	 * 
	 * @param collection
	 * @return boolean
	 */
	public static boolean isEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * Judge collection's not empty.
	 * 
	 * @param collection
	 * @return boolean
	 */
	public static boolean isNotEmpty(Collection collection) {
		return !isEmpty(collection);
	}

	/**
	 * 集合大小是否大于零
	 * 
	 * @param collection
	 * @return boolean
	 */
	public static boolean sizeGtZero(Collection collection) {
		if (!isEmpty(collection) && collection.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据value找key
	 * @param map
	 * @param value
	 * @return
	 */
	public static String getMapKey(Map<Object, Object> map, String value) {
		if(map==null || map.isEmpty() || value==null || value.trim().equals("")){
			return value;
		}
		String key = value;
		for (Map.Entry<Object, Object> entry : map.entrySet()) {
			if (value.equals(entry.getValue())) {
				key = StringUtils.valueOf(entry.getKey());
			}
		}
		return key;
	}
	
	public static <T> boolean containsKey(Map custMap, String key) {
		if(custMap==null || custMap.isEmpty() || key==null || key.trim().equals("")){
			return false;
		}
		if(custMap.containsKey(key)){
			return true;
		}
		return false;
	}
	/**
	 * 根据方法名取到序列化实体对象的值
	 * @param ser
	 * @param method
	 * @return
	 */
	public static Object getValueForMethod(Serializable ser,String method,Object... args) {
		if (ser != null && method!=null && !method.equals("")) {
			Class<?> cls = ser.getClass();
			try {
				Method m = cls.getMethod(method, null);
				Object obj = m.invoke(ser, args);
				return obj;
			} catch (Exception e) {
				logger.error("序列化类["+ser.getClass()+"]转换成对象Map异常", e);
			}
		}
		return null;
	}
	
	/**
	 * 根据方法名取到序列化实体对象的值
	 * @param ser
	 * @param method
	 * @return
	 */
	public static Map<String, Serializable> getMapForMethod(Serializable ser,String method) {
		if (ser != null && method!=null && !method.equals("")) {
			Class<?> cls = ser.getClass();
			try {
				Method m = cls.getMethod(method, null);
				Object obj = m.invoke(ser, null);
				if(obj!=null){
					Map<String, Serializable> map = (Map<String, Serializable>) obj;
					return map;
				}
			} catch (Exception e) {
				logger.error("序列化类["+ser.getClass()+"]转换成对象Map异常", e);
			}
		}
		return null;
	}
}