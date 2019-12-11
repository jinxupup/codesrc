package com.jjb.unicorn.facility.util;

/**
 * 字段值处理接口，比如需要对像字段输出值进行掩码操作等
 * @author H.N
 *
 */
public interface IFieldValueHandler {

	/**
	 * 根据字段名判断，从而处理对应value值
	 * @param fieldName
	 * @return
	 */
	public Object handler(String fieldName,Object value);
}
