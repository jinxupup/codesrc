package com.jjb.unicorn.web.tags;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.facility.context.AppHolder;
import com.jjb.unicorn.facility.kits.StrKit;

import freemarker.ext.beans.BeanModel;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleScalar;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateModelException;

public class UnicornTag extends UnicornFunctionRouterTag {

	Logger logger = LoggerFactory.getLogger(UnicornTag.class);

	@SuppressWarnings("rawtypes")
	public String appHolder(List arguments) {
		
		if(!argsSizeLess(arguments,2)){
			return "";
		}
		
		String configKey = getString(arguments.get(1));
		
		return AppHolder.getConfig(configKey);
	}
	
	/**
	 * 产生唯一号码
	 * @param arguments
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String uuid(List arguments) {
		return "ar_"+UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 返回true或false，给前台页面判断用
	 * @param arguments
	 * arguments[0] 方法名 "b"
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean b(List arguments) {
		
		if(!argsSizeLess(arguments,2)){
			return false;
		}
		
		if (arguments.size() == 2) {
			//两个参数，判断是否是true或Y
			Object p1 = (Object) arguments.get(1);
			String p1value = getString(p1);
			if("true".equalsIgnoreCase(p1value)||"Y".equalsIgnoreCase(p1value)){
				return true;
			}
		}else if (arguments.size() == 3) {
			//三个参数，判断第二个和第三个是否相等
			Object p1 = (Object) arguments.get(1);
			Object p2 = (Object) arguments.get(2);
			String p1value = getString(p1);
			String p2value = getString(p2);
			
			if(p1value.equals(p2value)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断是否为null或空字符串，若是，返回""或指定的值。不为空返回传入的值
	 * @param arguments
	 * arguments[0] 方法名 "blank"
	 * arguments[1] 判断的值
	 * arguments[2] 为空时返回的值
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String blank(List arguments) {
		
		if(!argsSizeLess(arguments,2)){
			return "";
		}
		
		if (arguments.size() == 2) {
			//两个参数，判断是否是null或空字符串
			Object p1 = (Object) arguments.get(1);
			String p1value = getString(p1);
			if(StrKit.isBlank(p1value)){
				return "";
			}else{
				return p1value;
			}
		}else if (arguments.size() == 3) {
			//三个参数，判断第二个和第三个是否相等
			Object p1 = (Object) arguments.get(1);
			Object p2 = (Object) arguments.get(2);
			String p1value = getString(p1);
			String p2value = getString(p2);
			
			if(StrKit.isBlank(p1value)||p1value.contains("freemarker.core.DefaultToExpression$EmptyStringAndSequence@")){
				return p2value;
			}else{
				return p1value;
			}
		}
		return "";
	}
	
	/**
	 * 产生随机整数
	 * arguments[0] 方法名 "random"
	 * arguments[1] 整数，随机数范围
	 * @param arguments
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Integer random(List arguments) {

		Random random = new Random();
		if (arguments.size() >= 2) {
			SimpleNumber simpleNumber = (SimpleNumber) arguments.get(1);
			return random.nextInt(simpleNumber.getAsNumber().intValue());
		} else {
			return random.nextInt();
		}

	}
	
	/**
     * 根据分组，将字符串，替换为目标字符串
     * @param str 字符串
     * @param pattern 正则表达式，内部以m.find()去找分组
     * @param newChar 替换后的字符串
     * @param oldChars 被替换的字符串，多个以逗号(,)分隔，每个字符串需满足正则
     * @return
     */
    @SuppressWarnings("rawtypes")
	public String success_url_replace(List arguments){
    	
    	if(!argsSizeLess(arguments,2)){
			return null;
		}
    	
    	String str = getString(arguments.get(1)).trim();
    	String pattern = "\\{\\{(res\\.obj\\..+?)\\}\\}";
    	
    	String[] oldChars = new String[]{"\\{","\\}"};
    	
    	String dest = str;
    	Pattern p = Pattern.compile(pattern);
    	Matcher m = p.matcher(str);
    	while(m.find()){
			
    		String group = m.group();
    		String destStr = group;
    		for(String oldChar:oldChars){
    			destStr = destStr.replaceAll(oldChar, "");
    		}
    		destStr = "'+"+destStr+"+'";
    		dest = dest.replace(group, destStr);
		}
    	if(dest.endsWith("+'")){
    		dest = dest.substring(0, dest.length()-2);
    	}else{
    		dest += "'";
    	}
    	
    	return dest;
    }

	/**
	 * 列表对象转为map
	 * @param arguments
	 * arguments[0] 方法名 listToMap
	 * arguments[1] list 数据
	 * arguments[2] keyField
	 * arguments[3] valueField
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public LinkedHashMap<String, Object> listToMap(List arguments) {
		
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		if(!argsSizeLess(arguments,4)){
			return null;
		}
		
		SimpleSequence list = (SimpleSequence) arguments.get(1);
		SimpleScalar keyField = (SimpleScalar) arguments.get(2);
		SimpleScalar valueField = (SimpleScalar) arguments.get(3);

		try {
			for(int i=0;i<list.size();i++ ){
				BeanModel t = (BeanModel) list.get(i); //包装器对象
				Object obj = t.getWrappedObject();
				
				Method getKeyMethod = obj.getClass().getDeclaredMethod("get" + StrKit.firstCharToUpperCase(keyField.getAsString()));
				Method getValueMethod = obj.getClass().getDeclaredMethod("get" + StrKit.firstCharToUpperCase(valueField.getAsString()));
				String key = getKeyMethod.invoke(obj).toString();
				Object value = getValueMethod.invoke(obj);
				
				map.put(key, value);
			}
			
		} catch (IllegalArgumentException e) {
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
		} catch (TemplateModelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}
	
}
