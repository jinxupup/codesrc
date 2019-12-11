package com.jjb.unicorn.facility.util;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用于数据model的类型转换
 * @author jjb
 *
 */
public abstract class DataTypeUtils {

	public static Integer getIntegerValue(Object value)
	{
		if (value == null)
			return null;
		if (value instanceof Integer)
			return (Integer)value;
		if (value instanceof Long)
			return ((Long)value).intValue();
		if (value instanceof BigDecimal)
			return ((BigDecimal)value).intValue();
		return Integer.valueOf(value.toString());
	}
	
	public static Long getLongValue(Object value)
	{
		if (value == null)
			return null;
		if (value instanceof Integer)
			return ((Integer)value).longValue();
		if (value instanceof Long)
			return (Long)value;
		if (value instanceof BigDecimal)
			return ((BigDecimal)value).longValue();
		return Long.valueOf(value.toString());
	}

	public static BigDecimal getBigDecimalValue(Object value)
	{
		if (value == null)
			return null;
		if (value instanceof Integer)
			return BigDecimal.valueOf((Integer)value);
		if (value instanceof Long)
			return BigDecimal.valueOf((Long)value);
		if (value instanceof BigDecimal)
			return (BigDecimal)value;
		return new BigDecimal(value.toString());
	}
	
	public static String getStringValue(Object value)
	{
		if (value == null)
			return null;
		return value.toString(); 
	}
	public static Date getDateValue(Object value)
	{
		if (value == null)
			return null;
		return (Date)value; 
	}
	public static Boolean getBooleanValue(Object value)
	{
		return (Boolean)value;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T getEnumValue(Object value, Class<T> type)
	{
		if (value == null)
			return null;
		if (value instanceof Enum<?>)
			return (T)value;		//这里出于效率问题，不判具体的枚举类型了
		return Enum.valueOf(type, value.toString());
			
	}
}
