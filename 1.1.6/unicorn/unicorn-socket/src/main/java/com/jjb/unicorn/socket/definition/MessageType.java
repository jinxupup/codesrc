package com.jjb.unicorn.socket.definition;

/**
 * 消息类型枚举
 * 
 * @author BIG.CPU
 * 
 */
public enum MessageType {
	/**
	 * 消息中只包含#{@link FieldDefinition}中定义的内容
	 */
	FIELD_ONLY,
	
	/**
	 * 消息中包含#{@link FieldDefinition}中定义的内容和分隔符
	 */
	FIELD_AND_SEPERATOR,
	
	/**
	 * 按ISO8583的格式组织#{@link FieldDefinition}中定义的内容
	 */
	ISO_8583
}
