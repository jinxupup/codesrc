package com.jjb.ecms.adapter.utils;


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.ecms.adapter.socket.ResponseType;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.ecms.service.dto.TCustInfo;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.ecms.service.dto.T1006.T1006Image;
import com.jjb.ecms.service.dto.T1006.T1006Req;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 解析XML报文，对象转换
 * @author JYData-R&D-Big H.N
 * @date 2015年12月8日 下午1:37:30
 * @version V1.0
 */
public class TransBeanConvert {
	private static Logger logger = LoggerFactory.getLogger(TransBeanConvert.class);

	public static T1005Req convertT1005Req(Element el, ResponseType res)
			throws ProcessException {
		T1005Req t1005Req = new T1005Req();
		try {
			if(el==null){
				return null;
			}
			t1005Req = getEntity(t1005Req, el);
		} catch (Exception e) {
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERRS003_CODE);
			res.setDESC(SysConstants.ERRS003_MES);
			logger.error("请求参数转换异常", e);
		}
		
		//客户信息
		List<TCustInfo> custs = new ArrayList<TCustInfo>();
		List<Element> custEls = el.elements("Custs");
		if(custEls!=null) {
			for (int i = 0; i < custEls.size(); i++) {
				TCustInfo c = new TCustInfo();
				try {
					if(custEls.get(i)==null 
							&& custEls.get(i).element("Cust")!=null){
						return null;
					}
					Element custEl = custEls.get(i).element("Cust");
					c = getEntity(c, custEl);
					custs.add(c);
				} catch (Exception e) {
					res.setSTATUS("F");
					res.setCODE(SysConstants.ERRS003_CODE);
					res.setDESC(SysConstants.ERRS003_MES);
					logger.error("请求参数转换异常", e);
				}
				t1005Req.setCusts(custs);
			}
		}
		
		return t1005Req;
	}
	public static T1006Req convertT1006Req(Element el, ResponseType res)
			throws ProcessException {
		T1006Req t1006Req = new T1006Req();
		try {
			if(el==null){
				return null;
			}
			t1006Req = getEntity(t1006Req, el);
		} catch (Exception e) {
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERRS003_CODE);
			res.setDESC(SysConstants.ERRS003_MES);
			logger.error("请求参数转换异常", e);
		}
		
		//客户信息
		List<T1006Image> pics = new ArrayList<T1006Image>();
		Element picEl = el.element("Pics");
		List<Element>  picEls= picEl.elements();
		if(picEls!=null) {
			for (int i = 0; i < picEls.size(); i++) {
				T1006Image c = new T1006Image();
				try {
					if(picEls.get(i)==null 
							&& picEls.get(i).element("Pic")!=null){
						return null;
					}
					Element custEl = picEls.get(i);
					c = getEntity(c, custEl);
					pics.add(c);
				} catch (Exception e) {
					res.setSTATUS("F");
					res.setCODE(SysConstants.ERRS003_CODE);
					res.setDESC(SysConstants.ERRS003_MES);
					logger.error("请求参数转换异常", e);
				}
			}
			t1006Req.setPics(pics);
		}
		
		return t1006Req;
	}
	
	public static <T>T convertRequest(Element el,
			ResponseType res,T t) throws ProcessException {
		try {
			t = getEntity(t, el);

		} catch (Exception e) {
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERRS003_CODE);
			res.setDESC(SysConstants.ERRS003_MES);
			logger.error("请求参数转换异常", e);
		}
		return t;
	}
	

	private static <T> T getEntity(T t, Element el) {
		Field[] fields = t.getClass().getDeclaredFields();
		
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			String objName = fields[i].getName();
			if(StringUtils.isBlank(objName)){
				continue;
			}
//			DOMXMLReader xmlReader
			String elName = (new StringBuffer()).append(Character.toUpperCase(objName.charAt(0)))
					.append(objName.substring(1)).toString();

			//T1005Req中的外部风控决策信息部分特殊映射
			if (StringUtils.equals(elName,"ExtMultiLoan1M")){
				elName="ExtMultiLoan1m";
			}
			if (StringUtils.equals(elName,"ExtBillCnt6M")){
				elName="ExtBillCnt6m";
			}
			if (StringUtils.equals(elName,"ExtCarrierHitBl6M")){
				elName="ExtCarrierHitBl6m";
			}
			String objValue = DOMXMLReader.getElementValue(el,elName).trim();
			
			try {
				String fName = (new StringBuffer()).append(Character.toLowerCase(objName.charAt(0)))
						.append(objName.substring(1)).toString();
				if("serialVersionUID".equals(objName) || "servId".equals(objName) || "servIdSimple".equals(objName)){
					continue;
				}
				PropertyDescriptor propertyDescriptor = BeanUtilsBean.getInstance().getPropertyUtils().getPropertyDescriptor(t, fName);
				if (propertyDescriptor == null) {
					logger.debug("不存在的报文字段：{"+fName+"},value：{"+objValue+"}");
					continue;
					//throw new ProcessException(SysConstants.ERRS003_CODE, SysConstants.ERRS003_MES);
				}
				
				if(StringUtils.isNotBlank(objValue)){
					try {
						if(fields[i].getType().equals(Date.class)){
							objValue = objValue.trim().replace("/", "").replace("-", "");
							String dateFormat = DateUtils.DAY_YMD;//默认年月日8位
							if(objValue.length() == 6){
								dateFormat = DateUtils.DAY_YM;
							}else if (objValue.length() == 14) {
								dateFormat = DateUtils.FULL_SECOND_LINE_NO;
							}else if (objValue.length() == 4){
								dateFormat = DateUtils.DAY_YM;
							}
							Date value = DateUtils.stringToDate2(objValue, dateFormat);
							fields[i].set(t, value);
						}else if(fields[i].getType().equals(String.class)){
		                   fields[i].set(t, objValue);
		                }else if(fields[i].getType().equals(Integer.class)){
		                    fields[i].set(t, new Integer(objValue));
		                }else if(fields[i].getType().equals(Long.class)){
		                    fields[i].set(t, Long.valueOf(objValue));
		                }else if(fields[i].getType().equals(Short.class)){     
		                	fields[i].set(t, Short.valueOf(objValue));
		                }else if(fields[i].getType().equals(BigDecimal.class)) {
		            		fields[i].set(t, new BigDecimal(objValue));
		            	}else if(fields[i].getType().equals(Double.class)){
		                	fields[i].set(t, Double.valueOf(objValue));
		                }else if(fields[i].getType().equals(Boolean.class)){
		                	fields[i].set(t, Boolean.valueOf(objValue));
		                }else {
		                	Class type = propertyDescriptor.getPropertyType();
		                	Object newValue = null;
		                	if (type.isEnum()) {
		                		try {
		                			newValue = Enum.valueOf(type, objValue);
								} catch (Exception e) {
									logger.warn("请求报文枚举类型{}转换失败：{}", type, e.getMessage());
								}
		                		
		        				BeanUtils.setProperty(t, objName, newValue);
		        			}
						}
					} catch (Exception e) {
						logger.error("请求报文类型转换失败：{}", e);
					}
				}
			} catch (Exception e) {
				logger.error("请求报文类型转换异常：{}", e);
			}
		}
		
		return t;
	}
	
	/**
     * Document值解析header里面节点信息.
     * @param document Document
     * @param nodeName String
     * @return String
     */
    public static String getHeaderNodeValue(Document document, String nodeName) {
    	String result = null;
        try{
        	if(document.getRootElement()!=null
        			&& document.getRootElement().element("ServiceHeader")!=null
        			&& document.getRootElement().element("ServiceHeader").element("ServResponse")!=null
        			&& document.getRootElement().element("ServiceHeader").element("ServResponse").element(nodeName)!=null){
        		result = document.getRootElement().element("ServiceHeader").element("ServResponse").element(nodeName).getText();  
        		logger.info(nodeName + "节点值为:" + result);
        	}
        	
        }catch(Exception e){
        	logger.error("联机指纹验证--解析响应报文失败！", e);
        	throw new ProcessException("联机指纹验证--解析响应报文失败！"+e.getMessage());
        }
        return result;
    }
    /**
     * Document值解析body里面节点信息.
     * @param document Document
     * @param nodeName String
     * @return String
     */
    public static String getBodyNodeValue(Document document, String nodeName) {
    	String result = null;
        try{
        	if(document.getRootElement()!=null
        			&& document.getRootElement().element("ServiceBody")!=null
        			&& document.getRootElement().element("ServiceBody").element("Response")!=null
        			&& document.getRootElement().element("ServiceBody").element("Response").element(nodeName)!=null){
        		result = document.getRootElement().element("ServiceBody").element("Response").element(nodeName).getText();        	
        	}
        }catch(Exception e){
        	logger.error("联机指纹验证--解析响应报文失败！", e);
        	throw new ProcessException("联机指纹验证--解析响应报文失败！"+e.getMessage());
        }
        return result;
    }
    /**
	 * 根据节点名称以及节点内容，返回节点字符串
	 * @param nodeName 节点名称
	 * @param nodeValue 节点内容
	 * @return	构造出的节点字符串
	 */
    public static String getNode(String nodeName, String nodeValue){
		StringBuilder node = new StringBuilder();
		if(!StringUtils.isEmpty(nodeName)){
			node.append("<"+nodeName+">");
			if(!StringUtils.isEmpty(nodeValue)){
				node.append(nodeValue);
			}
			node.append("</"+nodeName+">");
		}
		return node.toString();
	}
}