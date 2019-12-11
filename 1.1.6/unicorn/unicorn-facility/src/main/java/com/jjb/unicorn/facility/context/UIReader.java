package com.jjb.unicorn.facility.context;


import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.jjb.unicorn.facility.util.PropertiesLoader;

/**
 * 读取指定 jar包中的资源文件
 * @author jjb
 *
 */
public class UIReader {
	
	private static UIReader uiHolder = null;
	
    private static Map<String, HashMap<String, String>> map = null;
    private static PropertiesLoader loader = null;
    private static PropertiesLoader Localloader = new PropertiesLoader("classpath:/conf/i18n/Constants.properties");
    
    private static String sysName = AppHolder.getConfig("infrastructure");

    // 单例模式，一次性读取properties文件
    public static UIReader getInstance() {
    	if (uiHolder == null) {
    		synchronized(UIReader.class) {
    			if (uiHolder == null) {
    				LoadProperties();
    				uiHolder = new UIReader();
    			}
    		}
    	}
        return uiHolder;
    }
    // 读取xxx-infrastructure.jar下的属性文件，供页面加载时使用
    public static Map<String, HashMap<String, String>> LoadProperties() {
    	
    	if(map==null) {
	    	map = new HashMap<String, HashMap<String,String>>();
	    	// 1、加载 xxx-infrastructure.jar中的属性文件
	    	Enumeration<URL> urls;
			try {
				urls = Thread.currentThread().getContextClassLoader().
						getResources("com/allinfinance/"+sysName+"/infrastructure/client/ui/i18n");
				while (urls.hasMoreElements()) {  
	                URL url = urls.nextElement();  
	                if (url != null) {  
	                	 JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();  
	                     JarFile jarFile = jarURLConnection.getJarFile();
	                     Enumeration<JarEntry> jarEntries = jarFile.entries();
	                     while (jarEntries.hasMoreElements()) {  
	                    	 JarEntry jarEntry = jarEntries.nextElement();  
	                         String jarEntryName = jarEntry.getName(); // 类似：sun/security/internal/interfaces/TlsMasterSecret.class  
	                         String clazzName = jarEntryName.replace("/", ".");  
	                         int endIndex = clazzName.lastIndexOf(".");  
	                         String prefix = null;  
	                         if (endIndex > 0) {  
	                             String prefix_name = clazzName.substring(0, endIndex);  
	                             endIndex = prefix_name.lastIndexOf(".");  
	                             if(endIndex > 0){  
	                                 prefix = prefix_name.substring(0, endIndex);  
	                             }  
	                         }  
	                         if (prefix != null && jarEntryName.endsWith("properties")) {  
	                        	 if(prefix.equals("com.jjb."+sysName+".infrastructure.client.ui.i18n")){
	                        		 int end = jarEntryName.lastIndexOf(".");
	                        		 int second = jarEntryName.substring(0, end).lastIndexOf("/");
	                        		 String name = jarEntryName.substring(second+1, end);
	                      
	                                loader =  new PropertiesLoader("classpath:/"+jarEntryName);
	             	 	    		HashMap<String, String> tmpmap = new HashMap<String, String>();
	             	 	    		Properties properties = loader.getProperties();
	             	 	    		for(Map.Entry<Object,Object> entry:properties.entrySet() ){
	             	 	    			Object entryKey = entry.getKey();
	             	 	    			Object entryValue = entry.getValue();
	             	 	    			if(entryKey==null){
	             	 	    				continue;
	             	 	    			}else{
	             	 	    				if(entryValue==null){
	             	 	    					entryValue = "";
	             	 	    				}
	             	 	    				tmpmap.put(entry.getKey().toString(),entry.getValue().toString());
	             	 	    			}
	             	 	    		}
	             	 	    		map.put(name, tmpmap);
	                             } 
	                         }
	                     }
	                }  
	            }  
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 2、加载本地属性文件
			Properties properties = Localloader.getProperties();
			HashMap<String, String> tmpmap = new HashMap<String, String>();
    		for(Map.Entry<Object,Object> entry:properties.entrySet() ){
    			Object entryKey = entry.getKey();
    			Object entryValue = entry.getValue();
    			if(entryKey==null){
    				continue;
    			}else{
    				if(entryValue==null){
    					entryValue = "";
    				}
    				tmpmap.put(entry.getKey().toString(),entry.getValue().toString());
    			}
    		}
	    	map.put("Constants", tmpmap);
    	}
    	return map;
    }
   
    // FTL页面调用的方法
    public String getConfig(String tablename,String key) {
    	//LoadProperties();   在这里执行不是很好，每次都会加载   	
    	String value = null;
    	if(map!=null){
    		HashMap<String, String> tmpmap = map.get(tablename);
    		value = tmpmap.get(key);
    	}
        return value==null?"ERROR":value;
    }
    
}
