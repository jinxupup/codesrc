package com.jjb.unicorn.facility.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class BeanUtil {

	public static <E> void merge (E dest ,E source) {
		try {
			doMerge(dest,source);
		} catch (Exception e) {
			;
		}
	}
	
	private static <E> void doMerge (E dest ,E source) throws Exception {
		BeanInfo beanInfo=Introspector.getBeanInfo(dest.getClass());
        PropertyDescriptor[] proDescrtptors=beanInfo.getPropertyDescriptors();
        if(proDescrtptors ==null || proDescrtptors.length==0){
            return;
        }
        
        String propName = null;
        for(PropertyDescriptor propDesc:proDescrtptors){
        	propName = propDesc.getName();
        	Method writeMethod = propDesc.getWriteMethod();
        	if (writeMethod == null) {
        		continue;
        	}
        	
        	Method readMethod = ReflectUtil.getMethod(source.getClass(),"get"+headCharUpper(propName),new Class[]{});
        	if (readMethod == null) {
        		continue;
        	}
        	
            Object value = readMethod.invoke(source, new Object []{});
            writeMethod.invoke(dest, new Object[] {value});
        }
	}

	public static String headCharUpper (String keyProp) {
		String header = keyProp.substring(0, 1);
		String tail = keyProp.substring(1);
		
		return header.toUpperCase()+tail;
	}
}
