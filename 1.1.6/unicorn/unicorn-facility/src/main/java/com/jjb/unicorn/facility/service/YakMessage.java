package com.jjb.unicorn.facility.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Description: 子系统存储消息对象</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: YakMessage
 * @author BIG.CPU
 * @author LI.H
 * @date 2015年12月2日 下午6:49:44
 * @version 1.6
 */
public class YakMessage implements Serializable {
	
	private static final long serialVersionUID = -6398632443261301426L;

	/**
	 * 在customAttributes中保存MAK，密钥重置用
	 */
	public static final String MAC_KEY_NAME = "com.jjb.hsp.mac.key";
	
	/**
	 * 在customAttributes中保存关键域信息，验证响应消息位图，TPS规则引擎用
	 */
	public static final String ROUTE_KEY_NAME = "com.jjb.tps.route.key";

	/**
	 * 在customAttributes中保存响应消息位图，验证响应消息位图，TPS规则引擎用
	 */
	public static final String RSP_FIELDS_NAME = "com.jjb.tps.response.fields";

	private byte[] rawMessage;
	
	private Boolean isHeartBeat = false;
	
	private String srcChannelId;
	
	private Boolean isRequest = false;
	
	private Boolean isInComing = false;

	
	private Map<Integer, String> headAttributes = new HashMap<Integer, String>();
	
	/**
	 * 行内统一报文头，用于行内统一前置使用
	 */
	private Map<Integer, String> unifiedHeadAttributes = new HashMap<Integer, String>();

	/**
	 * <p>头扩展域，有附加报文头时使用，目前只有visa使用</p>
	 * 
	 */
	private Map<Integer, String> extHeadAttributes = new HashMap<Integer, String>();
	
	private Map<Integer, String> bodyAttributes = new HashMap<Integer, String>();
	
	/**
	 * <p>体扩展域含有子域的域，Key为域索引，value中的key，BitMap类型为索引，TLV格式为T</p>
	 * <p>一期只有外卡使用</p>
	 */
	private Map<Integer, Map<String, String>> extBodyAttributes = new HashMap<Integer, Map<String, String>>();

	
	private Map<String, Serializable> customAttributes = new HashMap<String, Serializable>();

	/**
	 * 
	 * @return 消息原文
	 */
	public byte[] getRawMessage() {
		return rawMessage;
	}

	/**
	 * 
	 * @param rawMessage
	 *            消息原文
	 */
	public void setRawMessage(byte[] rawMessage) {
		this.rawMessage = rawMessage;
	}

	/**
	 * 
	 * @return 是否心跳报文
	 */
	public Boolean getIsHeartBeat() {
		return isHeartBeat;
	}

	/**
	 * 
	 * @param isHeartBeat 是否心跳报文
	 */
	public void setIsHeartBeat(Boolean isHeartBeat) {
		this.isHeartBeat = isHeartBeat;
	}

	/**
	 * 
	 * @return 来源渠道号
	 */
	public String getSrcChannelId() {
		return srcChannelId;
	}

	/**
	 * 
	 * @param srcChannelId 来源渠道号
	 */
	public void setSrcChannelId(String srcChannelId) {
		this.srcChannelId = srcChannelId;
	}

	/**
	 * 
	 * @return 请求/响应标识
	 */
	public Boolean getIsRequest() {
		return isRequest;
	}

	/**
	 * 
	 * @param isRequest 请求/响应标识
	 */
	public void setIsRequest(Boolean isRequest) {
		this.isRequest = isRequest;
	}

	/**
	 * 
	 * @return 接入/发出标识
	 */
	public Boolean getIsInComing() {
		return isInComing;
	}

	/**
	 * 
	 * @param isInComing 接入/发出标识
	 */
	public void setIsInComing(Boolean isInComing) {
		this.isInComing = isInComing;
	}
	
	/**
	 * 
	 * @return 消息头map
	 */
	public Map<Integer, String> getHeadAttributes() {
		return headAttributes;
	}

	/**
	 * 
	 * @param headAttributes 消息头map
	 */
	public void setHeadAttributes(Map<Integer, String> headAttributes) {
		this.headAttributes = headAttributes;
	}

	/**
	 * 
	 * @return 消息内容map
	 */
	public Map<Integer, String> getBodyAttributes() {
		return bodyAttributes;
	}

	/**
	 * 
	 * @param bodyAttributes 消息内容map
	 */
	public void setBodyAttributes(Map<Integer, String> bodyAttributes) {
		this.bodyAttributes = bodyAttributes;
	}

	/**
	 * @return 自定义内容map
	 */
	public Map<String, Serializable> getCustomAttributes() {
		return customAttributes;
	}
	
	public Map<Integer, String> getUnifiedHeadAttributes() {
		return unifiedHeadAttributes;
	}

	public void setUnifiedHeadAttributes(Map<Integer, String> unifiedHeadAttributes) {
		this.unifiedHeadAttributes = unifiedHeadAttributes;
	}

	/**
	 * 
	 * @param customAttributes 自定义内容map
	 */
	public void setCustomAttributes(Map<String, Serializable> customAttributes) {
		this.customAttributes = customAttributes;
	}

	public String getBody(int field) {
		return  bodyAttributes.get(field);
	}

	public Map<Integer, String> getExtHeadAttributes() {
		return extHeadAttributes;
	}

	public void setExtHeadAttributes(Map<Integer, String> extHeadAttributes) {
		this.extHeadAttributes = extHeadAttributes;
	}

	public Map<Integer, Map<String, String>> getExtBodyAttributes() {
		return extBodyAttributes;
	}

	public void setExtBodyAttributes(Map<Integer, Map<String, String>> extBodyAttributes) {
		this.extBodyAttributes = extBodyAttributes;
	}
	
	/**
	 * <p>目的：从消息对象中获取子域中的信息</p>
	 * <p>承诺：如果不存在则响应""，目前只有外卡允许使用此方法</p>
	 * @param index 子域 如 62域
	 * @param subIndex 子域的子域 如62.2
	 * @return
	 */
	public String getExtBody(int index, String subIndex) {
		if (!extBodyAttributes.containsKey(index) || !extBodyAttributes.get(index).containsKey(subIndex)) {
			return "";
		}
		return extBodyAttributes.get(index).get(subIndex);
	}
}
