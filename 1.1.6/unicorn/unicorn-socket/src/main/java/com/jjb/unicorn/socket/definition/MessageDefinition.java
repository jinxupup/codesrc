package com.jjb.unicorn.socket.definition;

import java.util.Map;

import com.jjb.unicorn.socket.codec.LengthType;

/**
 * <p>Description: 消息格式定义类，用来定义针对该接入渠道整体往来报文格式</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: MessageDefinition
 * @author BIG.CPU
 * @author LI.H
 * @date 2015年12月10日 下午4:41:19
 * @version 1.1
 */
public class MessageDefinition {

	private String encoding;
	private String separator;
	private LengthType lengthtype;
	
	/**
	 * <p>拒绝报文头，当卡组织我方报文出现（目前只有VISA使用）</p>
	 */
	private Map<Integer, FieldDefinition> rejecthead;
	
	private Map<Integer, FieldDefinition> head;
	
	/**
	 * <p>MTI域，银联的MIT写在head中，由于VISA有拒绝报文头，此域在VISA渠道出现</p>
	 */
	private FieldDefinition mti;
	
	private Map<Integer, FieldDefinition> body;

	/**
	 * 
	 * @return 消息所用的编码格式，如GBK
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * 
	 * @param encoding 消息所用的编码格式，如GBK
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public LengthType getLengthtype() {
		return lengthtype;
	}

	public void setLengthtype(LengthType lengthtype) {
		this.lengthtype = lengthtype;
	}

	public Map<Integer, FieldDefinition> getRejecthead() {
		return rejecthead;
	}

	public void setRejecthead(Map<Integer, FieldDefinition> rejecthead) {
		this.rejecthead = rejecthead;
	}

	public Map<Integer, FieldDefinition> getHead() {
		return head;
	}

	public void setHead(Map<Integer, FieldDefinition> head) {
		this.head = head;
	}

	public FieldDefinition getMti() {
		return mti;
	}

	public void setMti(FieldDefinition mti) {
		this.mti = mti;
	}

	public Map<Integer, FieldDefinition> getBody() {
		return body;
	}

	public void setBody(Map<Integer, FieldDefinition> body) {
		this.body = body;
	}

}
