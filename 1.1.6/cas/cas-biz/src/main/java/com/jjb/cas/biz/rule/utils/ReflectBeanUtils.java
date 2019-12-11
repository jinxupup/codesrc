package com.jjb.cas.biz.rule.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jjb.unicorn.facility.cstruct.CBinaryInt;
import com.jjb.unicorn.facility.cstruct.CChar;
import com.jjb.unicorn.facility.exception.CException;
import com.jjb.unicorn.facility.exception.ProcessException;

public class ReflectBeanUtils {
	public static Object mapToObject(Map<String, Serializable> map, Class<?> beanClass) throws Exception {    
        if (map == null || map.size()<=0)   
            return null;    
    
        Object obj = beanClass.newInstance();  
        //获取关联的所有类，本类以及所有父类
        boolean ret = true;
        Class oo = obj.getClass();
        List<Class> clazzs = new ArrayList<Class>();
        while(ret){
            clazzs.add(oo);
            oo = oo.getSuperclass();
            if(oo == null || oo == Object.class)break;
        }
         
       for(int i=0;i<clazzs.size();i++){
           Field[] fields = clazzs.get(i).getDeclaredFields();   
           for (Field field : fields) {    
//               int mod = field.getModifiers();    
//               if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
//                   continue;    
//               }    
//               //由字符串转换回对象对应的类型
//                if (field != null) {
//                    field.setAccessible(true);
//                    field.set(obj, map.get(field.getName()));
//                }
                

				if (field==null || (field.getModifiers() & Modifier.STATIC) > 0)
					continue;
				Class<?> type = field.getType();
				
				CChar annoChar = field.getAnnotation(CChar.class);
				CBinaryInt annoInt = field.getAnnotation(CBinaryInt.class);
				
				if (annoChar != null)
				{
//					int len = annoChar.value();
//					byte bytes[] = new byte[len];
//					buffer.get(bytes);
					
//					String value = map.get(field.getName());
					
					
//					由字符串转换回对象对应的类型
	                if (field != null) {
	                    field.setAccessible(true);
	                    try {
	                    	if (map.get(field.getName()) == null && !annoChar.required() && !type.equals(String.class))
	    					{
	    						//可以忽略的字段，填null
	    						field.set(obj, null);
	    					}else{
	    						
	    						field.set(obj, map.get(field.getName()));
	    					}
						} catch (Exception e) {
							throw new ProcessException(field.getAnnotation(CException.class).value());
						}
	                    
	                }
					
					

//					if (map.get(field.getName()) == null && !annoChar.required() && !type.equals(String.class))
//					{
//						//可以忽略的字段，填null
//						field.set(obj, null);
//					}
//					else if (type.equals(Date.class))
//					{
//						Date date = (Date)map.get(field.getName());
//						SimpleDateFormat sdf = new SimpleDateFormat(annoChar.datePattern());
//						String value = sdf.format(date);
//						sdf.setLenient(true);
//						String datePattern = annoChar.datePattern();
//						if (datePattern.length() < value.length())
//						{
//							if (annoChar.leftPadding())
//								value = StringUtils.right(value, datePattern.length());
//							else
//								value = StringUtils.left(value, datePattern.length());
//						}
//						//日期类型异常捕获
//						try {
//							//如果日期是的“201710-2”格式的，java日期处理方法会将10月减去2天，
//							//这种方式不符合业务要求的，所以使用正则表达式进行匹配，检查格式是否符合要求，如果不符合要求，则抛出日期格式不正确异常信息
//							String rex = "[12][0-9]{3}[0-1][0-9][0-3][0-9]";
//							if(value.matches(rex)){
//								field.set(obj, sdf.parse(value));
//							}else{
//								throw new ProcessException(field.getAnnotation(CException.class).value());
//							}
//						} catch (Exception e) {
//							throw new ProcessException(field.getAnnotation(CException.class).value());
//						}
//					}
//					else if (type.equals(String.class))
//					{
//						String value = (String)map.get(field.getName());
////						if(value == null)
////							value = "";
//						if (annoChar.autoTrim())
//							value = value.trim();
//						//字符串直接赋值
//						field.set(obj, value);
//					}
//					else if (type.equals(Integer.class) || type.equals(int.class))
//					{
//						Integer value = (Integer)map.get(field.getName());
//						//Integer数字类型异常捕获
//						try {
//							field.set(obj, value);
//						} catch (Exception e) {
//							throw new ProcessException(field.getAnnotation(CException.class).value());
//						}
//						
//					}
//					else if (type.equals(Long.class) || type.equals(long.class))
//					{
//						Long value = (Long)map.get(field.getName());
//						//Long数字类型异常捕获
//						try {
//							field.set(obj, value);
//						} catch (Exception e) {
//							throw new ProcessException(field.getAnnotation(CException.class).value());
//						}
//					}
//					else if (type.equals(BigDecimal.class))
//					{
//						Long value = (Long)map.get(field.getName());
//						//BigDecimal数字类型异常捕获
//						try {
//							field.set(obj, BigDecimal.valueOf(value, annoChar.precision()));
//						} catch (Exception e) {
//							throw new ProcessException(field.getAnnotation(CException.class).value());
//						}
//					}
//					else if (type.isEnum())
//					{
//						try{
////							if (StringUtils.isNotBlank(value)){
////								field.set(obj, Enum.valueOf((Class<? extends Enum>)type, value.trim()));
//								field.set(obj, (Class<? extends Enum>)map.get(field.getName()));
////							}
//						}catch(Exception e){
//							throw new ProcessException(field.getAnnotation(CException.class).value());
//						}
//					}
//					//布尔类型的检查和异常捕获
//					else if (type.equals(Boolean.class) || type.equals(boolean.class))
//					{
//						Boolean value = (Boolean)map.get(field.getName());
//						try {
//							field.set(obj, value);
//						} catch (Exception e) {
//							throw new ProcessException(field.getAnnotation(CException.class).value());
//						}
//					}
//					else
//					{
//						throw new IllegalArgumentException("不支持的字段类型:" + type);
//					}
				}
				else if (annoInt != null)
				{
					int value = (Integer) map.get(field.getName());
					try {
						field.set(obj, value);
					}catch (Exception e) {
						throw new ProcessException(field.getAnnotation(CException.class).value());
					}
					
				}
				else
					assert false : field.getName() + " 必须指定字段类型注释";
			
                
                
           }   
       }
        return obj;    
    }    
    
	
	
    public static Map<String, Serializable> objectToMap(Object obj) throws Exception {    
        if(obj == null){    
            return null;    
        }   
        //获取关联的所有类，本类以及所有父类
        boolean ret = true;
        Class oo = obj.getClass();
        List<Class> clazzs = new ArrayList<Class>();
        while(ret){
            clazzs.add(oo);
            oo = oo.getSuperclass();
            if(oo == null || oo == Object.class)break;
        }
         
        Map<String, Serializable> map = new HashMap<String, Serializable>();    
    
       for(int i=0;i<clazzs.size();i++){
           Field[] declaredFields = clazzs.get(i).getDeclaredFields();    
           for (Field field : declaredFields) {  
               int mod = field.getModifiers();  
               //过滤 static 和 final 类型
               if(Modifier.isStatic(mod) || Modifier.isFinal(mod)){    
                   continue;    
               }    
               field.setAccessible(true);  
               map.put(field.getName(), (Serializable)field.get(obj));  
           }
       }
    
        return map;  
    } 
    
}
