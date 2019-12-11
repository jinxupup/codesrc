package com.jjb.ecms.adapter.socket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.codec.YakDecodeFilter;
import com.jjb.unicorn.socket.codec.YakEncodeFilter;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * @Description:
 * @author JYData-R&D-Big H.N
 * @date 2015年12月8日 下午1:37:30
 * @version V1.0
 */
public class RePositionFilter implements YakDecodeFilter, YakEncodeFilter {

	// @Override
	// public boolean doDecode(IoSession session, IoBuffer buffer,
	// YakMessage yakMessage, MessageDefinition msgDef,
	// ProtocolDecoderOutput out) throws Exception {
	// buffer.position(buffer.limit());
	// return false;
	// }

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer,
			YakMessage message, MessageDefinition msgdef,
			ProtocolEncoderOutput out) throws Exception {
		return false;
	}

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer,
			YakMessage yakMessage, MessageDefinition msgDef,
			ProtocolDecoderOutput out) throws Exception {
		buffer.position(buffer.position() + yakMessage.getRawMessage().length);
		byte[] b = yakMessage.getRawMessage();
		String s = new String(b, "UTF-8");
		yakMessage.getCustomAttributes().put("MESSAGE_KEY", s);
		return false;
	}

	@Override
	public boolean doEncode(IoSession arg0, IoBuffer arg1, YakMessage arg2,
			MessageDefinition arg3, MessageDefinition arg4,
			ProtocolEncoderOutput arg5, int arg6) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer,
			YakMessage yakMessage, MessageDefinition msgHeaddef,
			MessageDefinition msgdef, ProtocolDecoderOutput out, int txnMode)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
