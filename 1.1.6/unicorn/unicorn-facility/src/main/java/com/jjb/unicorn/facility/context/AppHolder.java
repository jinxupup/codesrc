package com.jjb.unicorn.facility.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.util.PropertiesLoader;

/**
 * 当前应用上下文信息
 * @author jjb
 *
 */
public class AppHolder {

	public static final String PROPERTIES_FILE = "conf/app.properties";
	
	private static AppHolder appHolder = new AppHolder();
	
    private static Map<String, String> map = null;
    
    private static PropertiesLoader loader = new PropertiesLoader("classpath:/conf/app.properties");


    public static AppHolder getInstance() {
        return appHolder;
    }

    public static String getConfig(String key) {
    	
    	String value = "";
    	
    	if(map==null){
    		map = new HashMap<String, String>();
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
    				map.put(entry.getKey().toString(),entry.getValue().toString());
    			}
    		}
    	}
    	
    	value = map.get(key);
        return value==null?"":value;
    }

    /**
     * 系统类型
     * @return
     */
    public static String getSysType() {
        return getConfig("sysType");
    }

    /**
     * 是否开发模式
     * @return
     */
    public static Boolean isDevMode() {
        String devMode = getConfig("devMode");
        return "true".equals(devMode);
    }
	
    public static String getURIEncoding(){
    	return getConfig("URIEncoding");
    }
    
    /**
     * 是否引入acl-mvc包，默认true
     * @return
     */
    public static Boolean getDepAclMvc(){
    	String depAclMvc = getConfig("depAclMvc");
    	if(StrKit.isBlank(depAclMvc)||!"false".equalsIgnoreCase(depAclMvc)){
    		return true;
    	}
        return false;
    }
    
}
