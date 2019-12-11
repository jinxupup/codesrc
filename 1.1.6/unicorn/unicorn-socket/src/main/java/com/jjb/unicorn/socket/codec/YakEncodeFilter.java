package com.jjb.unicorn.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * 编码过滤器接口，根据编码类型、编码格式、变量定义进行编码
 * 
 * @author BIG.CPU
 * 
 */
public interface YakEncodeFilter {

	/**
	 * 对消息对象中的内容进行编码，将消息编为符合格式定义的字节数据
	 * 
	 * @param session
	 *            用于连接的会话上下文
	 * @param buffer
	 *            从上层过滤器链传入，存储之前编码结果数据的字节缓冲区
	 * @param message
	 *            源消息对象
	 * @param msgdef TODO
	 * @param out TODO
	 * @return 编码完成的字节缓冲区
	 * 
	 * @throws Exception
	 *             格式定义或类型错误时会抛出异常
	 */
	public boolean doEncode(IoSession session, IoBuffer buffer,
                            YakMessage message, MessageDefinition msgdef, ProtocolEncoderOutput out) throws Exception;

	/**
	 * 对消息对象中的内容进行编码，将消息编为符合格式定义的字节数据
	 * 
	 * @param session
	 *            用于连接的会话上下文
	 * @param buffer
	 *            从上层过滤器链传入，存储之前编码结果数据的字节缓冲区
	 * @param message
	 *            源消息对象
	 * @param msgdef TODO
	 * @param out TODO
	 * @return 编码完成的字节缓冲区
	 * 
	 * @throws Exception
	 *             格式定义或类型错误时会抛出异常
	 */
	public boolean doEncode(IoSession session, IoBuffer buffer, YakMessage message, 
			MessageDefinition msgHeaddef, MessageDefinition msgdef, ProtocolEncoderOutput out, int txnMode) throws Exception;

}
