package com.jjb.unicorn.socket.codec;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * 解码过滤器链
 * 
 * @author BIG.CPU
 * 
 */
public class YakDecodeFilterChain {

	public LinkedList<YakDecodeFilter> chain = new LinkedList<YakDecodeFilter>();

	public MessageDefinition messageDefinition;

	public void setChain(List<YakDecodeFilter> chain) {
		this.chain = new LinkedList<YakDecodeFilter>(chain);
	}

	public void add(YakDecodeFilter codec) {
		chain.add(codec);
	}

	public MessageDefinition getMessageDefinition() {
		return messageDefinition;
	}

	public void setMessageDefinition(MessageDefinition messageDefinition) {
		this.messageDefinition = messageDefinition;
	}

	/**
	 * 按过滤器链中注册过滤器的顺序调用过滤器的解码方法
	 * 
	 * @param session
	 *            mina连接session
	 * @param buffer
	 *            消息字节缓冲区
	 * @param message
	 *            消息对象
	 * @param out
	 *            TODO
	 * @return 解码后的消息对象
	 * 
	 * @throws Exception
	 */
	public YakMessage doDecodeFilterChain(IoSession session, IoBuffer buffer,
			YakMessage message, ProtocolDecoderOutput out) throws Exception {
		Iterator<YakDecodeFilter> iterator = chain.iterator();
		while (iterator.hasNext()) {
			if (!iterator.next().doDecode(session, buffer, message, messageDefinition, out))
				// 如果返回false则中断过滤器链
				break;
		}
		return message;
	}

}
