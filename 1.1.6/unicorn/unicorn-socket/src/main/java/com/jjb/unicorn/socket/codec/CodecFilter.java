package com.jjb.unicorn.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * <p>Title: CodecFilter.java</p>
 * <p>Description: 编码解码过滤器(目前只有VISA使用，支持扩展子域用法，银联以后看情况迁移过来)</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @author LI.H
 * @date 2015年10月19日
 * @version 1.0
 */
public class CodecFilter implements YakDecodeFilter, YakEncodeFilter {

	private CodecScope codecScope;
	
	@Autowired
	private DecodeHandler decodeHandler;
	
	@Autowired
	private EncodeHandler encodeHandler;

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer, YakMessage yakMessage, MessageDefinition msgDef, ProtocolEncoderOutput out) throws Exception {
		codecScope.doEncoder(encodeHandler, msgDef, yakMessage, buffer);
		return true;
	}

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer, YakMessage yakMessage, MessageDefinition msgDef, ProtocolDecoderOutput out) throws Exception {
		codecScope.doDecoder(decodeHandler, msgDef, buffer.buf(), yakMessage);
		return true;
	}

	public CodecScope getCodecScope() {
		return codecScope;
	}

	public void setCodecScope(CodecScope codecScope) {
		this.codecScope = codecScope;
	}

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer,
			YakMessage message, MessageDefinition msgHeaddef,
			MessageDefinition msgdef, ProtocolEncoderOutput out, int txnMode)
			throws Exception {
		return true;
	}

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer,
			YakMessage yakMessage, MessageDefinition msgHeaddef,
			MessageDefinition msgdef, ProtocolDecoderOutput out, int txnMode)
			throws Exception {
		return true;
	}
}
