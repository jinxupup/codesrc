package com.jjb.unicorn.socket.codec;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * 编码过滤器链
 * 
 * @author BIG.CPU
 * 
 */
public class YakEncodeFilterChain {

	public LinkedList<YakEncodeFilter> chain = new LinkedList<YakEncodeFilter>();

	public MessageDefinition messageDefinition;

	public void setChain(List<YakEncodeFilter> chain) {
		this.chain = new LinkedList<YakEncodeFilter>(chain);
	}

	public void add(YakEncodeFilter codec) {
		chain.add(codec);
	}

	public MessageDefinition getMessageDefinition() {
		return messageDefinition;
	}

	public void setMessageDefinition(MessageDefinition messageDefinition) {
		this.messageDefinition = messageDefinition;
	}

	/**
	 * 按过滤器链中注册过滤器的顺序调用过滤器的编码方法
	 * 
	 * @param session
	 *            mina连接session
	 * @param buffer
	 *            消息字节缓冲区
	 * @param message
	 *            消息对象
	 * @param out
	 *            TODO
	 * @return 解码后待
	 * @throws Exception
	 */
	public IoBuffer doEncodeFilterChain(IoSession session, IoBuffer buffer,
			YakMessage message, ProtocolEncoderOutput out) throws Exception {
		Iterator<YakEncodeFilter> iterator = chain.iterator();
		while (iterator.hasNext()) {
			if (!iterator.next().doEncode(session, buffer, message,
					messageDefinition, out))
				// 如果返回false则中断过滤器链
				break;
		}
		return buffer;
	}

}
