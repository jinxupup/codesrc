package com.jjb.unicorn.socket.codec;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.mina.core.buffer.IoBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.codec.exception.CodecException;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * <p>Description: 定义编解码操作对应的是报文的哪一段</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: CodecScope
 * @author BIG.CPU
 * @author LI.H
 * @date 2015年12月9日 下午3:20:33
 * @version 1.1
 */
public enum CodecScope {
	
	/**
	 * 报文头
	 */
	HEAD() {
		
		private Logger logger = LoggerFactory.getLogger(getClass());
		
		@Override
		public void doEncoder(EncodeHandler encodeHandler, MessageDefinition msgDef, YakMessage yakMessage, IoBuffer buffer) throws CodecException, DecoderException {
			ByteBuffer temp = encodeHandler.encodeFieldByMap(yakMessage, msgDef.getHead(), yakMessage.getHeadAttributes());
			logger.debug("报文头内容[{}]", yakMessage.getHeadAttributes().toString());
			// 缓存过滤器链编码结果
			byte[] bufferTemp = new byte[buffer.limit()];
			buffer.get(bufferTemp);

			// 重写缓冲区，报文头放在缓冲区开头
			buffer.clear();
			buffer.put(temp);
			buffer.put(bufferTemp);
			buffer.flip();
		}
		
		@Override
		public void doDecoder(DecodeHandler decodeHandler, MessageDefinition msgDef, ByteBuffer buffer, YakMessage yakMessage) throws CodecException, CharacterCodingException, DecoderException {
			
			Map<Integer, String> fieldsMap = decodeHandler.decodeHeadField(yakMessage, buffer);
			
			//存在RejectHead且2域第一个bit为1，表示后面还有一个头文件，把头部13域14域解出
			if(!msgDef.getRejecthead().isEmpty() && decodeHandler.containsField(Hex.decodeHex(fieldsMap.get(2).toCharArray()), 1)) {
				decodeHandler.decodeRejectHeadField(yakMessage, buffer, fieldsMap);
				logger.debug("Reject扩展报文头内容[{}]", fieldsMap.toString());
				yakMessage.setExtHeadAttributes(fieldsMap);
				
				//解第二个头部域
				fieldsMap = decodeHandler.decodeHeadField(yakMessage, buffer);
			}
			logger.debug("报文头内容[{}]", fieldsMap.toString());
			yakMessage.setHeadAttributes(fieldsMap);
		}

	},
	
	/**
	 * MTI
	 */
	MTI() {
		
		private final Logger logger = LoggerFactory.getLogger(getClass());
		
		@Override
		public void doEncoder(EncodeHandler encodeHandler, MessageDefinition msgDef, YakMessage yakMessage, IoBuffer buffer) throws CodecException, DecoderException {
			String value = (String) yakMessage.getCustomAttributes().get("com.jjb.osp.tran.mti");
			logger.debug("MTI内容[{}]", value);
			byte[] values = msgDef.getMti().getType().encodeField(msgDef.getMti(), yakMessage, value, msgDef.getEncoding(), true, -2, encodeHandler);
			byte[] bufferTemp = new byte[buffer.limit()];
			buffer.get(bufferTemp);

			// 重写缓冲区，报文头放在缓冲区开头
			buffer.clear();
			buffer.put(values);
			buffer.put(bufferTemp);
			buffer.flip();
		}
		
		@Override
		public void doDecoder(DecodeHandler decodeHandler, MessageDefinition msgDef, ByteBuffer buffer, YakMessage yakMessage) throws CodecException, CharacterCodingException, DecoderException  {
			// -2只是为了一个变量传递进去，无任何实质意义
			logger.debug("解码报文第[MTI]域");
			String mtiValue = decodeHandler.buildField(yakMessage, buffer, msgDef.getMti(), -2);
			logger.debug("解码报文第[MTI]域:[{}]", mtiValue);
			yakMessage.getCustomAttributes().put("com.jjb.osp.tran.mti", mtiValue);
		}

	},
	
	/**
	 * 报文体
	 */
	BODY() {
		
		@Override
		public void doEncoder(EncodeHandler encodeHandler, MessageDefinition msgDef, YakMessage yakMessage, IoBuffer buffer) throws CodecException, DecoderException {
			ByteBuffer temp = encodeHandler.encode8583Body(yakMessage);
			// 指针移动到缓冲区末尾
			buffer.position(buffer.limit());
			// 填入解码结果
			buffer.put(temp);
			// 写入完成、翻回
			buffer.flip();
		}
		
		@Override
		public void doDecoder(DecodeHandler decodeHandler, MessageDefinition msgDef, ByteBuffer buffer, YakMessage yakMessage) throws CodecException, CharacterCodingException, DecoderException {
			decodeHandler.decode8583Body(yakMessage, buffer);
		}

	},
	
	/**
	 * 全部报文
	 */
	BOTH() {
		
		@Override
		public void doEncoder(EncodeHandler encodeHandler, MessageDefinition msgDef, YakMessage yakMessage, IoBuffer buffer) throws CodecException, DecoderException {
			//ByteBuffer temp = 
		}
		
		@Override
		public void doDecoder(DecodeHandler decodeHandler, MessageDefinition msgDef, ByteBuffer buffer, YakMessage yakMessage) throws CodecException, CharacterCodingException, DecoderException  {
			decodeHandler.decodeByBoth(yakMessage, buffer);
		}

	};
	
	
	/**
	 * <p>目的：根据YakMassage对不同位置进行编码</p>
	 * <p>承诺：存在复杂子域则对复杂子域进行编码</p>
	 * @param codecHandler 编码处理器
	 * @param msgDef 报文格式定义
	 * @param yakMessage 需要编码消息对象
	 * @param buffer 缓冲区
	 * @throws CodecException
	 * @throws DecoderException
	 */
	public abstract void doEncoder(EncodeHandler encodeHandler, MessageDefinition msgDef, YakMessage yakMessage, IoBuffer buffer) throws CodecException, DecoderException;
	
	/**
	 * <p>目的：对不同的枚举类型进行解码，放入对应YakMessage的Map中</p>
	 * <p>承诺：存在复杂子域则解码复杂子域</p>
	 * @param decodeHandler 解码处理器
	 * @param msgDef 报文格式定义
	 * @param buffer 缓冲区
	 * @param yakMessage 请求对象
	 * @throws CodecException
	 * @throws CharacterCodingException
	 * @throws DecoderException
	 */
	public abstract void doDecoder(DecodeHandler decodeHandler, MessageDefinition msgDef, ByteBuffer buffer, YakMessage yakMessage) throws CodecException, CharacterCodingException, DecoderException;
	
}
