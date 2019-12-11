package com.jjb.dmp.engine.kit;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.jjb.unicorn.facility.kits.StrKit;

public class FunctionKit {
	
	//日期转换
	public static Date toDate(Object date) {
		Date ret = null;
		if(date!=null && StrKit.notBlank(date.toString())){
			if(date instanceof Date){
				ret = (Date) date;
			}else if(date instanceof String){
				try {
					SimpleDateFormat format = null;
					String dateStr = (String)date;
					if(dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2}")){
						format = new SimpleDateFormat("yyyy-MM-dd");
					}else if(dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}")){ //
						format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					}else if(dateStr.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")){
						format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					}else{
						format = new SimpleDateFormat("yyyy-MM-dd");
					}
					ret = format.parse(dateStr);
				} catch (ParseException e) {
					throw new IllegalArgumentException(e);
				}
			}
		}
		return ret;
	}
	
	//数字转换
	public static BigDecimal toDecimal(Object value){
		BigDecimal ret = null;  
        if( value != null && StrKit.notBlank(value.toString())) {  
            if( value instanceof BigDecimal ) {  
                ret = (BigDecimal) value;  
            } else if( value instanceof String ) {  
                ret = new BigDecimal( (String) value );  
            } else if( value instanceof BigInteger ) {  
                ret = new BigDecimal( (BigInteger) value );  
            } else if( value instanceof Number ) {  
                ret = new BigDecimal( ((Number)value).doubleValue() );  
            } else {  
                throw new ClassCastException("Not possible to coerce ["+value+"] from class "+value.getClass()+" into a BigDecimal.");  
            }  
        }
        return ret;  
	}
	
	//布尔转换
	public static Boolean toBoolean(Object value){
		Boolean ret = null;
		if( value != null && StrKit.notBlank(value.toString())) {  
			if(value instanceof Boolean){
				ret = (Boolean) value;
			}else if(value instanceof String){
				String boolstr = (String)value;
				if("true".equalsIgnoreCase(boolstr)||"Y".equalsIgnoreCase(boolstr)||"YES".equalsIgnoreCase(boolstr)){
					ret = true;
				}else if("false".equalsIgnoreCase(boolstr)||"N".equalsIgnoreCase(boolstr)||"NO".equalsIgnoreCase(boolstr)){
					ret = false;
				}
			}
		}
		return ret;
	}

	public static Date rebuidDate(String arg) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		if (arg == null)
			return null;
		if (arg.startsWith("$")) {
			if ("$today".equalsIgnoreCase(arg)) {
				return calendar.getTime();
			} else if ("$yesterday".equalsIgnoreCase(arg)) {
				calendar.add(Calendar.DATE, -1);
				return calendar.getTime();
			} else if ("$tomorrow".equalsIgnoreCase(arg)) {
				calendar.add(Calendar.DATE, 1);
				return calendar.getTime();
			} else if ("$weekAgo".equalsIgnoreCase(arg)) {
				calendar.add(Calendar.DATE, -7);
				return calendar.getTime();
			} else if ("$weekFromNow".equalsIgnoreCase(arg)) {
				calendar.add(Calendar.DATE, 7);
				return calendar.getTime();
			} else if ("$monthAgo".equalsIgnoreCase(arg)) {
				calendar.add(Calendar.MONTH, -1);
				return calendar.getTime();
			} else if ("$monthFromNow".equalsIgnoreCase(arg)) {
				calendar.add(Calendar.MONTH, 1);
				return calendar.getTime();
			}
		} else if (arg.endsWith("[-0D]")) {
			if (arg.indexOf("w") > 0) {
				calendar.add(Calendar.DATE, 7 * (Integer.parseInt(arg.substring(0, arg.indexOf("w")).replace("+", ""))));
				return calendar.getTime();
			} else if (arg.indexOf("m") > 0) {
				calendar.add(Calendar.MONTH, Integer.parseInt(arg.substring(0, arg.indexOf("m")).replace("+", "")));
				return calendar.getTime();
			} else if (arg.indexOf("d") > 0) {
				calendar.add(Calendar.DATE, Integer.parseInt(arg.substring(0, arg.indexOf("d")).replace("+", "")));
				return calendar.getTime();
			}
		} else {
			return null;
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		Integer a = 3;
		boolean q = (a instanceof Number);
		
		String value = "2013-14-35 12:00:00";
		   Calendar calendar=Calendar.getInstance();   
		   calendar.setTime(new Date());
		   calendar.set(Calendar.DAY_OF_MONTH,calendar.get(Calendar.DAY_OF_MONTH)+2);//让日期加1
//		   return calendar.getTime();
	}
}
