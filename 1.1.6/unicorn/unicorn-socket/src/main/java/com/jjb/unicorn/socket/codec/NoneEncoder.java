package com.jjb.unicorn.socket.codec;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单工server端编码器，若在此端口发送消息，则抛出异常
 * 
 * @author BIG.CPU
 *
 */
public class NoneEncoder extends ProtocolEncoderAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		logger.warn("单工server无法发送消息");
	}

}
