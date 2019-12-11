package com.jjb.unicorn.facility.util;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;


/**
 * Author: Yang xp
 * Date: 2016/12/7
 * Time: 9:29
 * Desc：xml
 */

public class XStreamUtil {

	
    private XStreamUtil(){
    }

    /**
     * 将对象转换为xml
     */
    public static String toXml(Object object){
        XStream xStream = new XStream(new StaxDriver());
        xStream.processAnnotations(object.getClass());
        xStream.aliasSystemAttribute(null,"class");
        xStream.autodetectAnnotations(true);
        String xml = xStream.toXML(object);
        return formatXml(xml);
    }

    /**
     * 格式化xml xstream生成的xml没有头信息
     */
    private static String formatXml(String xml){
    	
        try{
            return xml.replace("<?xml version=\"1.0\" ?>","<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        }catch(Exception e){
         //   log.info("格式化xml异常:",e);
            return xml;
        }
    }

    /**
     * xml转对象
     */
    public static <T> T toBean(String xml,Class<T> c){
        XStream xstream=new XStream(new DomDriver());
        xstream.processAnnotations(c);
        xstream.autodetectAnnotations(true);
        T obj=(T)xstream.fromXML(xml);
        return obj;
    }

    /**
     * xml转对象
     * 子类继承时需要指定类
     */
    public static <T> T toBean(String xml,Class<T> c,Class sonClass,Class parentClass){
        XStream xstream=new XStream(new DomDriver());
        xstream.processAnnotations(c);
        xstream.addDefaultImplementation(sonClass,parentClass);
        xstream.autodetectAnnotations(true);
        T obj=(T)xstream.fromXML(xml);
        return obj;
    }


}
