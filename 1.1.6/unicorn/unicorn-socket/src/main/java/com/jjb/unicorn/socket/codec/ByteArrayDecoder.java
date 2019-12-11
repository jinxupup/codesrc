package com.jjb.unicorn.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.jjb.unicorn.facility.service.YakMessage;

/**
 * 字节数组透传报文解码器
 * 
 * @author BIG.CPU
 *
 */
public class ByteArrayDecoder extends ProtocolDecoderAdapter {

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
			throws Exception {
		byte[] msg = new byte[in.limit()];
		in.get(msg);
		YakMessage message = new YakMessage();
		message.setRawMessage(msg);
		out.write(message);
	}

}
