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
 * 分割符格式报文编解码过滤器
 * 
 * @author BIG.CPU
 * 
 */
public class DefaultFieldsAndSeparatorCodecFilter implements YakDecodeFilter,
		YakEncodeFilter {

	private CodecScope codecScope = CodecScope.BOTH;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CodeMarkUtils codeMarkUtils;
	
	public CodecScope getCodecScope() {
		return codecScope;
	}

	public void setCodecScope(CodecScope codecScope) {
		this.codecScope = codecScope;
	}

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer,
			YakMessage yakMessage, MessageDefinition msgDef,
			ProtocolDecoderOutput out) throws Exception {
		if (msgDef.getSeparator().length() > 1) {
			throw new IllegalArgumentException("只支持单字符的分隔符，当前分隔符为["
					+ msgDef.getSeparator() + "]");
		}
		byte separator = msgDef.getSeparator().getBytes()[0];
		Map<Integer, String> valueMap = null;
		switch (this.codecScope) {
		case BODY:
			// 解码报文体
			valueMap = CodecUtil.decodeFieldAndSeparator(codeMarkUtils,msgDef.getEncoding(),
					msgDef.getBody(), buffer.buf(), separator);
			logger.debug("报文体内容[" + codeMarkUtils.makeMask(valueMap.toString()) + "]");
			yakMessage.setBodyAttributes(valueMap);
			break;
		case HEAD:
			// 解码报文头
			valueMap = CodecUtil.decodeFieldAndSeparator(codeMarkUtils,msgDef.getEncoding(),
					msgDef.getHead(), buffer.buf(), separator);
			logger.debug("报文头内容[" + valueMap.toString() + "]");
			yakMessage.setHeadAttributes(valueMap);
			break;
		case BOTH:
			// 解码报文头
			valueMap = CodecUtil.decodeFieldAndSeparator(codeMarkUtils,msgDef.getEncoding(),
					msgDef.getHead(), buffer.buf(), separator);
			logger.debug("报文头内容[" + valueMap.toString() + "]");
			yakMessage.setHeadAttributes(valueMap);
			// 解码报文体
			valueMap = CodecUtil.decodeFieldAndSeparator(codeMarkUtils,msgDef.getEncoding(),
					msgDef.getBody(), buffer.buf(), separator);
			logger.debug("报文体内容[" + codeMarkUtils.makeMask(valueMap.toString()) + "]");
			yakMessage.setBodyAttributes(valueMap);
			break;
		}
		return true;
	}

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer,
			YakMessage message, MessageDefinition msgDef,
			ProtocolEncoderOutput out) throws Exception {
		if (msgDef.getSeparator().length() > 1) {
			throw new IllegalArgumentException("只支持单字符的分隔符，当前分隔符为["
					+ msgDef.getSeparator() + "]");
		}
		byte separator = msgDef.getSeparator().getBytes()[0];
		ByteBuffer temp = null;
		switch (this.codecScope) {
		case BODY:
			// 编码报文体
			temp = CodecUtil.encodeFieldAndSeparator(msgDef.getEncoding(),
					msgDef.getBody(), message.getBodyAttributes(), separator);
			logger.debug("报文体内容[" + codeMarkUtils.makeMask(message.getBodyAttributes().toString()) + "]");
			// 指针移动到缓冲区末尾
			buffer.position(buffer.limit());
			// 填入解码结果
			buffer.put(temp);
			// 写入完成、翻回
			buffer.flip();
			break;
		case HEAD:
			// 编码报文头
			temp = CodecUtil.encodeFieldAndSeparator(msgDef.getEncoding(),
					msgDef.getHead(), message.getHeadAttributes(), separator);
			logger.debug("报文头内容[" + message.getHeadAttributes().toString() + "]");
			// 缓存编码结果
			byte[] bufferTemp = new byte[buffer.limit()];
			buffer.get(bufferTemp);

			// 重写缓冲区，报文头放在缓冲区开头
			buffer.clear();
			buffer.put(temp);
			buffer.put(bufferTemp);
			buffer.flip();
			break;
		case BOTH:
			// 编码报文头
			temp = CodecUtil.encodeFieldAndSeparator(msgDef.getEncoding(),
					msgDef.getHead(), message.getHeadAttributes(), separator);
			logger.debug("报文头内容[" + message.getHeadAttributes().toString() + "]");
			buffer.put(temp);
			// 加入分隔符
			if (msgDef.getBody().size() > 0) {
				buffer.put(separator);
			}
			// 编码报文体
			temp = CodecUtil.encodeFieldAndSeparator(msgDef.getEncoding(),
					msgDef.getBody(), message.getBodyAttributes(), separator);
			logger.debug("报文体内容[" + codeMarkUtils.makeMask(message.getBodyAttributes().toString()) + "]");
			buffer.put(temp);
			// 写入完成、翻回
			buffer.flip();
		}
		return true;
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
