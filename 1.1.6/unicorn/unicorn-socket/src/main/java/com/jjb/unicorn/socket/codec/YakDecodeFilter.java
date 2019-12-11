package com.jjb.unicorn.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * 解码过滤器接口，根据编码类型、编码格式、变量定义进行解码
 * 
 * @author BIG.CPU
 * 
 */
public interface YakDecodeFilter {

	/**
	 * 对字节缓冲区中的内容进行解码
	 * 
	 * @param session
	 *            用于连接的会话上下文
	 * @param buffer
	 *            存储源数据的字节缓冲区
	 * @param yakMessage
	 *            上层过滤器链传入，存储之前解码结果的消息对象
	 * @param msgDef TODO
	 * @param out TODO
	 * @return 解码完成的消息对象
	 * 
	 * @throws Exception
	 *             格式定义或类型错误时会抛出异常
	 */
	public boolean doDecode(IoSession session, IoBuffer buffer,
                            YakMessage yakMessage, MessageDefinition msgDef, ProtocolDecoderOutput out) throws Exception;

	/**
	 * 对字节缓冲区中的内容进行解码
	 * @param session 用于连接的会话上下文
	 * @param buffer 存储源数据的字节缓冲区
	 * @param yakMessage 上层过滤器链传入，存储之前解码结果的消息对象
	 * @param msgDef
	 * @param out
	 * @param txnMode 报文模式
	 * @return 解码完成的消息对象
	 * @throws Exception 格式定义或类型错误时会抛出异常
	 */
	public boolean doDecode(IoSession session, IoBuffer buffer, YakMessage yakMessage, 
			MessageDefinition msgHeaddef, MessageDefinition msgdef, ProtocolDecoderOutput out,int txnMode) throws Exception;

}
