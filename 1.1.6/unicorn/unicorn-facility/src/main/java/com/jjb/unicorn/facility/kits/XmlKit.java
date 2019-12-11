package com.jjb.unicorn.facility.kits;

import java.io.Serializable;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.mapper.MapperWrapper;

/**
 * @description : 
 * @author : jjb
 * 
 */

public class XmlKit {

	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T fromXml(String xml){
		XStream xstream = new XStream(new DomDriver()){
	        protected MapperWrapper wrapMapper(MapperWrapper next) {
	            return new MapperWrapper(next) {

	                public boolean shouldSerializeMember(Class definedIn, String fieldName) {
	                    return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
	                }
	                
	            };
	        }
	    };
	   return (T) xstream.fromXML(xml);
	}
	
	public static String toXml(Serializable obj){
		XStream xstream = new XStream(new DomDriver()){
	        protected MapperWrapper wrapMapper(MapperWrapper next) {
	            return new MapperWrapper(next) {
	                public boolean shouldSerializeMember(Class definedIn, String fieldName) {
	                    return definedIn != Object.class ? super.shouldSerializeMember(definedIn, fieldName) : false;
	                }
	            };
	        }
	    };
	   return xstream.toXML(obj);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T fromXmlToObject(String xml){
		XStream xstream = new XStream();
	   return (T) xstream.fromXML(xml);
	}
}
