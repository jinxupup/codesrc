package com.jjb.ecms.adapter.utils;

import org.dom4j.Element;

import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 解析XML报文
 * @author JYData-R&D-Big H.N
 * @date 2015年12月8日 下午1:37:30
 * @version V1.0
 */
public class DOMXMLReader {

//	private Logger logger = LoggerFactory.getLogger(DOMXMLReader.class);
	

	/**
	 * 获取报文体中指定标签值
	 * 
	 * @param tagName
	 *            标签名
	 * @return
	 */
	public static String getElementValue(Element el,String tagName) {

		String value = "";
		if(el!=null && el.element(tagName)!=null)
			value = el.element(tagName).getStringValue();
//		if (node != null) {
////			value = node.getTextContent();
//			value = node.getNodeValue();
//		}

		return StringUtils.valueOf(value);
	}

//	public String getValueByTagName(String tagName) {
//
//		String value = "";
//		if(element!=null && element.getElementsByTagName(tagName)!=null && element.getElementsByTagName(tagName).getLength()>0){
//			Node node = element.getElementsByTagName(tagName).item(0);
//			if (node != null) {
////				value = node.getTextContent();
//				value = node.getNodeValue();
//			}
//		}
//		//如果 “str == null || str.length() == 0 || str.equals("null") ”
//		if(StringUtils.isEmpty(value)){
//			value = "";
//		}
//		return value;
//	}
	public static String getStringValue(org.dom4j.Element element2){
		String str = "";
		if(element2!=null){
			str = element2.getStringValue();
		}
		return str;
	}
}