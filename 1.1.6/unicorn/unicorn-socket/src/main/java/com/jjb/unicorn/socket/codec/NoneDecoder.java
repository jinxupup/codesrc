package com.jjb.unicorn.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单工客户端使用的解码器，若在此端口收到消息，则抛出异常
 * 
 * @author BIG.CPU
 *
 */
public class NoneDecoder extends ProtocolDecoderAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		logger.warn("单工client无法接收消息");
		if(in.hasRemaining()) {
			in.clear();
			in.flip();
		}
	}

}
