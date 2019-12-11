package com.jjb.unicorn.facility.util;

public class ClassLoaderUtil {

	public static  Class<?> loadClass (String fullClassName) {
		Class<?> clazz = null;
		
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			clazz = loader.loadClass(fullClassName);
		} catch (Exception e)  {
			;
		}
		
		if (clazz == null) {
			try {
				clazz = Class.forName(fullClassName);
			} catch (ClassNotFoundException ce) {
				;
			}
		}
		
		return clazz;
	}
}
