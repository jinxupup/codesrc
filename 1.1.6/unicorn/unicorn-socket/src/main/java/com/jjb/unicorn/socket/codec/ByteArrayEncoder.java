package com.jjb.unicorn.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.jjb.unicorn.facility.service.YakMessage;

/**
 * 字节数组透传报文编码器
 * 
 * @author BIG.CPU
 *
 */
public class ByteArrayEncoder extends ProtocolEncoderAdapter {

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		YakMessage msg = (YakMessage) message;
		IoBuffer buff = IoBuffer.allocate(msg.getRawMessage().length);
		buff.put(msg.getRawMessage());
		buff.flip();
		out.write(buff);
	}

}
