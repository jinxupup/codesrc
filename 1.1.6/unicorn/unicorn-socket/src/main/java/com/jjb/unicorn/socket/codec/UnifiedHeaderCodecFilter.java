package com.jjb.unicorn.socket.codec;

import java.nio.ByteBuffer;
import java.util.Map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * 统一报文报文头文编解码过滤器
 * 
 * @author W.G
 * 
 */
public class UnifiedHeaderCodecFilter implements YakDecodeFilter,YakEncodeFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private CodeMarkUtils codeMarkUtils;

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer, YakMessage yakMessage,
                            MessageDefinition msgHeadDef, MessageDefinition msgDef, ProtocolDecoderOutput out, int txnMode) throws Exception {
		if(txnMode == YakMsgConstants.TXN_MODE_UNIFIED){
			// 得到缓冲区的解码结果
			Map<Integer, String> valueMap = null;
			valueMap = CodecUtil.decodeFieldOnly(codeMarkUtils, msgHeadDef.getEncoding(), msgHeadDef.getHead(), buffer.buf());
			logger.debug("接收统一报文的报文头内容[" + valueMap.toString() + "]");
			yakMessage.setUnifiedHeadAttributes(valueMap);
		}
		return true;
	}

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer,
			YakMessage message, MessageDefinition msgHeaddef, MessageDefinition msgdef,
			ProtocolEncoderOutput out, int txnMode) throws Exception {
		if(txnMode == YakMsgConstants.TXN_MODE_UNIFIED){
			ByteBuffer temp = null;
			// 消息头
			temp = CodecUtil.encodeFieldOnly(msgdef.getEncoding(), msgHeaddef.getHead(), message.getUnifiedHeadAttributes());
			logger.debug("发送统一报文的报文头内容[" + message.getUnifiedHeadAttributes() + "]");
			// 缓存过滤器链编码结果
			byte[] bufferTemp = new byte[buffer.limit()];
			buffer.get(bufferTemp);

			// 重写缓冲区，报文头放在缓冲区开头
			buffer.clear();
			buffer.put(temp);
			buffer.put(bufferTemp);
			buffer.flip();
		}
		return true;
	}

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer,
			YakMessage message, MessageDefinition msgdef,
			ProtocolEncoderOutput out) throws Exception {
		return true;
	}

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer,
			YakMessage yakMessage, MessageDefinition msgDef,
			ProtocolDecoderOutput out) throws Exception {
		return true;
	}

}
