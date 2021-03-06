package com.jjb.unicorn.socket.codec;

import java.util.Iterator;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * 编码过滤器链
 * 
 * @author BIG.CPU
 * 
 */
public class YakUnifiedEncodeFilterChain extends YakEncodeFilterChain{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private int txnMode;

	private MessageDefinition msgHeadDef;

	public MessageDefinition getMsgHeadDef() {
		return msgHeadDef;
	}

	public void setMsgHeadDef(MessageDefinition msgHeadDef) {
		this.msgHeadDef = msgHeadDef;
	}

	public int getTxnMode() {
		return txnMode;
	}

	public void setTxnMode(int txnMode) {
		this.txnMode = txnMode;
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
		logger.info("当前行内统一前置的报文模式txnMode为：{}",txnMode);
		Iterator<YakEncodeFilter> iterator = chain.iterator();
		while (iterator.hasNext()) {
			if (!iterator.next().doEncode(session, buffer, message,
					msgHeadDef, messageDefinition, out,txnMode))
				// 如果返回false则中断过滤器链
				break;
		}
		return buffer;
	}

}
