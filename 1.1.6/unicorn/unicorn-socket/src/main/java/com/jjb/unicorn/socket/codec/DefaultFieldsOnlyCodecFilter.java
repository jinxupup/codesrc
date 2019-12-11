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
import com.jjb.unicorn.socket.codec.exception.CodecException;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * 默认的纯变量报文编解码过滤器，当报文体或报文头的内容符合此格式约定时适用
 * 
 * @author BIG.CPU
 * 
 */
public class DefaultFieldsOnlyCodecFilter implements YakDecodeFilter, YakEncodeFilter {
	
	private CodecScope codecScope = CodecScope.BODY;

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
	public boolean doDecode(IoSession session, IoBuffer buffer, YakMessage yakMessage, MessageDefinition msgDef, ProtocolDecoderOutput out) throws Exception {
		// 得到缓冲区的解码结果
		Map<Integer, String> valueMap = null;
		switch (this.codecScope) {
		case BODY:
			valueMap = CodecUtil.decodeFieldOnly(codeMarkUtils,msgDef.getEncoding(), msgDef.getBody(), buffer.buf());
			logger.debug("报文体内容[" + codeMarkUtils.makeMask(valueMap.toString()) + "]");
			yakMessage.setBodyAttributes(valueMap);
			break;
		case HEAD:
			valueMap = CodecUtil.decodeFieldOnly(codeMarkUtils,msgDef.getEncoding(), msgDef.getHead(), buffer.buf());
			logger.debug("报文头内容[" + valueMap.toString() + "]");
			yakMessage.setHeadAttributes(valueMap);
			break;
		}
		return true;
	}

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer, YakMessage message, MessageDefinition msgdef, ProtocolEncoderOutput out) throws CodecException, Exception {
		// 得到对象的编码结果
		ByteBuffer temp = null;
		switch (this.codecScope) {
		case BODY:
			temp = CodecUtil.encodeFieldOnly(msgdef.getEncoding(), msgdef.getBody(), message.getBodyAttributes());
			logger.debug("报文体内容[" + codeMarkUtils.makeMask(message.getBodyAttributes().toString()) + "]");
			// 指针移动到缓冲区末尾
			buffer.position(buffer.limit());
			// 填入解码结果
			buffer.put(temp);
			// 写入完成、翻回
			buffer.flip();
			break;
		case HEAD:
			// 消息头
			temp = CodecUtil.encodeFieldOnly(msgdef.getEncoding(), msgdef.getHead(), message.getHeadAttributes());
			logger.debug("报文头内容[" + message.getHeadAttributes().toString() + "]");
			// 缓存过滤器链编码结果
			byte[] bufferTemp = new byte[buffer.limit()];
			buffer.get(bufferTemp);

			// 重写缓冲区，报文头放在缓冲区开头
			buffer.clear();
			buffer.put(temp);
			buffer.put(bufferTemp);
			buffer.flip();
			break;
		}
		return true;
	}

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer,
			YakMessage yakMessage,MessageDefinition msgHeaddef, MessageDefinition msgdef,
			ProtocolDecoderOutput out,int txnMode) throws Exception {
		String reportType = yakMessage.getUnifiedHeadAttributes().get(YakMsgConstants.REPORT_TYPE_INDEX);//报文类型
		if(txnMode == YakMsgConstants.TXN_MODE_8583 || 
				(txnMode == YakMsgConstants.TXN_MODE_UNIFIED && YakMsgConstants.REPORT_TYPE_8583.equals(reportType))){
			// 得到缓冲区的解码结果
			Map<Integer, String> valueMap = null;
			switch (this.codecScope) {
			case BODY:
				valueMap = CodecUtil.decodeFieldOnly(codeMarkUtils, msgdef.getEncoding(), msgdef.getBody(), buffer.buf());
				logger.debug("报文体内容[" + codeMarkUtils.makeMask(valueMap.toString()) + "]");
				yakMessage.setBodyAttributes(valueMap);
				break;
			case HEAD:
				valueMap = CodecUtil.decodeFieldOnly(codeMarkUtils, msgdef.getEncoding(), msgdef.getHead(), buffer.buf());
				logger.debug("报文头内容[" + valueMap.toString() + "]");
				yakMessage.setHeadAttributes(valueMap);
				break;
			}
		}
		return true;
	}

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer,
			YakMessage message,MessageDefinition msgHeaddef, MessageDefinition msgdef,
			ProtocolEncoderOutput out, int txnMode) throws Exception {
		String reportType = message.getUnifiedHeadAttributes().get(YakMsgConstants.REPORT_TYPE_INDEX);//报文类型
		if(txnMode == YakMsgConstants.TXN_MODE_8583 || 
				(txnMode == YakMsgConstants.TXN_MODE_UNIFIED && YakMsgConstants.REPORT_TYPE_8583.equals(reportType))){
			// 得到对象的编码结果
			ByteBuffer temp = null;
			switch (this.codecScope) {
			case BODY:
				temp = CodecUtil.encodeFieldOnly(msgdef.getEncoding(), msgdef.getBody(), message.getBodyAttributes());
				logger.debug("报文体内容[" + codeMarkUtils.makeMask(message.getBodyAttributes().toString()) + "]");
				// 指针移动到缓冲区末尾
				buffer.position(buffer.limit());
				// 填入解码结果
				buffer.put(temp);
				// 写入完成、翻回
				buffer.flip();
				break;
			case HEAD:
				// 消息头
				temp = CodecUtil.encodeFieldOnly(msgdef.getEncoding(), msgdef.getHead(), message.getHeadAttributes());
				logger.debug("报文头内容[" + message.getHeadAttributes().toString() + "]");
				// 缓存过滤器链编码结果
				byte[] bufferTemp = new byte[buffer.limit()];
				buffer.get(bufferTemp);

				// 重写缓冲区，报文头放在缓冲区开头
				buffer.clear();
				buffer.put(temp);
				buffer.put(bufferTemp);
				buffer.flip();
				break;
			}
		}
		return true;
	}

}
