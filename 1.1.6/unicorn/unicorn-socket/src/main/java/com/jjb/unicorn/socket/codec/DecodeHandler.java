package com.jjb.unicorn.socket.codec;

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.socket.codec.exception.CodecException;
import com.jjb.unicorn.socket.definition.ExtFieldDefinition;
import com.jjb.unicorn.socket.definition.ExtFieldMap;
import com.jjb.unicorn.socket.definition.FieldDefinition;
import com.jjb.unicorn.socket.definition.FieldType;
import com.jjb.unicorn.socket.definition.MessageDefinition;
import com.jjb.unicorn.socket.definition.MessageType;

/**
 * <p>Description: 解码处理器，此处理器带解码扩展子域功能</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: DecodeHandler
 * @author LI.H
 * @date 2015年12月9日 下午3:21:06
 * @version 1.0
 */
public class DecodeHandler {
	
	private static Logger logger = LoggerFactory.getLogger(DecodeHandler.class);
	
	private MessageDefinition messageDef;
	
	private ExtFieldMap extFieldMap;
	
	@Autowired private CodeMarkUtils codeMarkUtils;
	
	
	/**
	 * <p>目的：解码报文头各子域</p>
	 * <p>承诺：报文头中的域必须连续否则抛出CodecException</p>
	 * @param message
	 * @param fieldsDefMap
	 * @param buffer
	 * @return
	 * @throws CodecException
	 */
	public Map<Integer, String> decodeHeadField(YakMessage message, ByteBuffer buffer) throws CodecException {
		final Map<Integer, String> fieldsMap = new HashMap<Integer, String>();
		// 根据变量定义依次解析变量值
		for (int i = 1; i <= messageDef.getHead().size(); i++) {
			logger.debug("解码报文头第[{}]域", i);
			FieldDefinition definition = messageDef.getHead().get(i);
			if (definition == null) {
				throw new CodecException("index为[" + i + "]的变量定义为null," + MessageType.FIELD_ONLY + "模式的变量定义不能有不连续的index");
			}
			fieldsMap.put(i, buildField(message, buffer, definition, i));
		}
		return fieldsMap;
	}
	
	
	/**
	 * <p>根据指定的头部域解对应的值</p>
	 * <p>此方法只用于解VISA Reject Head 13域14域</p>
	 * @param message
	 * @param buffer
	 * @param fieldsMap
	 */
	public void decodeRejectHeadField(YakMessage message, ByteBuffer buffer, Map<Integer, String> fieldsMap) {
		fieldsMap.put(13, buildField(message, buffer, messageDef.getRejecthead().get(13), 13));
		fieldsMap.put(14, buildField(message, buffer, messageDef.getRejecthead().get(14), 14));
	}
	

	/**
	 * <p>目的：解码8583格式报文体</p>
	 * <p>承诺：只解码8583格式报文体</p>
	 * @param message
	 * @param buffer
	 * @throws CharacterCodingException
	 * @throws DecoderException
	 */
	public void decode8583Body(YakMessage message, final ByteBuffer buffer) throws CharacterCodingException, DecoderException {
		
		buildBitMap(message, buffer);
		byte[] bitmap = Hex.decodeHex(message.getBodyAttributes().get(0).toCharArray());

		// 解码主位图中存在的报文域
		for (int i = 2; i <= 64; i++) {
			if (containsField(bitmap, i)) {
				logger.debug("解码报文体第>>[{}]<<域", i);
				message.getBodyAttributes().put(i, buildField(message, buffer, messageDef.getBody().get(i), i));
			}
		}

		// 解码第二位图中存在的报文域
		if (message.getBodyAttributes().get(1) != null) {
			bitmap = Hex.decodeHex(message.getBodyAttributes().get(1).toCharArray());
			for (int i = 2; i <= 64; i++) {
				if (containsField(bitmap, i)) {
					int field = i + 64;
					logger.debug("解码报文体第>>[{}]<<域", field);
					message.getBodyAttributes().put(field, buildField(message, buffer, messageDef.getBody().get(field), field));
				}
			}
		}

		// 解码第三位图中存在的报文域
		if (message.getBodyAttributes().get(65) != null) {
			bitmap = Hex.decodeHex(message.getBodyAttributes().get(65).toCharArray());
			for (int i = 2; i <= 64; i++) {
				if (containsField(bitmap, i)) {
					int field = i + 128;
					logger.debug("解码报文体第>>[{}]<<域", field);
					message.getBodyAttributes().put(field, buildField(message, buffer, messageDef.getBody().get(field), field));
				}
			}
		}
		logger.debug("报文体内容[{}]", codeMarkUtils.makeMask(message.getBodyAttributes().toString()));
	}
	
	/**
	 * <p>目的：对于Both格式进行解码，实质上并没有使用，在此兼容之前格式</p>
	 * <p>承诺：没有经过验证，不保证结果</p>
	 * @param message
	 * @param buffer
	 * @throws CodecException
	 */
	public void decodeByBoth(YakMessage message, final ByteBuffer buffer) throws CodecException {
		byte separator = messageDef.getSeparator().getBytes()[0];
		Map<Integer, String> fieldsMap = CodecUtil.decodeFieldAndSeparator(codeMarkUtils, messageDef.getEncoding(), messageDef.getHead(), buffer, separator);
		logger.debug("报文头内容[{}]", fieldsMap.toString());
		message.setHeadAttributes(fieldsMap);
		
		// 解码报文体
		fieldsMap = CodecUtil.decodeFieldAndSeparator(codeMarkUtils, messageDef.getEncoding(), messageDef.getBody(), buffer, separator);
		logger.debug("报文体内容[{}]", codeMarkUtils.makeMask(fieldsMap.toString()));
		message.setBodyAttributes(fieldsMap);
	}
	
	/**
	 * <p>目的：构建子域，此方法会自动计算长度</p>
	 * <p>承诺：针对不同的子域进行构建，同时赋值入YakMessage</p>
	 * @param buffer 缓冲区
	 */
	public String buildField(YakMessage message, final ByteBuffer buffer, final FieldDefinition fieldDefinition, final int index) {
		return decodeField(message, fieldDefinition, getDefLength(fieldDefinition, buffer), buffer, index);
	}
	
	/**
	 * <p>获取该报文所有BitMap（主BitMap，第一BitMap，第二BitMap）</p>
	 * @param codeMarkUtils
	 * @param encoding
	 * @param fieldsDefMap
	 * @param buffer
	 * @return
	 * @throws DecoderException
	 */
	private void buildBitMap(YakMessage message, final ByteBuffer buffer) throws DecoderException {
		
		// 默认先获取主位图，主位图一定存在
		byte[] bitmap = buildBitMapField(buffer, messageDef.getBody().get(0));
		message.getBodyAttributes().put(0, new String(Hex.encodeHex(bitmap)));
		
		// 第一位图
		if (containsField(bitmap, 1)) {
			logger.debug("解码报文第一位图[1]域");
			bitmap = buildBitMapField(buffer, messageDef.getBody().get(1));
			message.getBodyAttributes().put(1, new String(Hex.encodeHex(bitmap)));
		} else {
			bitmap = new byte[8];
		}
		
		// 第二位图
		if (containsField(bitmap, 1)) {
			logger.debug("解码报文第二位图[65]域");
			message.getBodyAttributes().put(65, new String(Hex.encodeHex(buildBitMapField(buffer, messageDef.getBody().get(1)))));
		}
	}
	
	/**
	 * <p>目的：根据FieldDefinition获取BitMap</p>
	 * <p>承诺：本方法不校验FieldDefinition是否是BitMap类型子域</p>
	 * @param buffer 缓冲区
	 * @param fieldDef 子域定义
	 */
	private byte[] buildBitMapField(final ByteBuffer buffer, FieldDefinition fieldDef) {
		byte[] bitmap = new byte[getDefLength(fieldDef, buffer)];
		buffer.get(bitmap);
		return bitmap;
	}
	
	/**
	 * <p>目的：根据FieldDefinition中的长度获取该子域长度</p>
	 * <p>承诺：此处会捕获IllegalArgumentException和CodecException异常</p>
	 * @param fieldDefinition
	 * @param buffer
	 * @return
	 */
	private int getDefLength(FieldDefinition fieldDefinition, ByteBuffer buffer) {
		// 得到字段长度
		int length = 0;
		// 根据定长和变长确定不同计算方式
		if (fieldDefinition.getLengthSize() > 0) {
			logger.debug("变长域，长度的长度为[{}]位", fieldDefinition.getLengthSize());
			byte[] lengthBytes = new byte[fieldDefinition.getLengthSize()];
			buffer.get(lengthBytes);
			length = getLengthByMessageDef(lengthBytes);
			logger.debug("长度为[{}]位", length);
		} else {
			length = fieldDefinition.getContentMaxSize();
			logger.debug("定长域，长度为[{}]位", length);
		}
		
		return length;
	}
	
	/**
	 * <p>目的：进行解码</p>
	 * <p>承诺：如果长度为0则响应""</p>
	 * @param message 消息对象
	 * @param definition 域定义
	 * @param length 域长度
	 * @param buffer 缓冲区
	 * @param index 域索引
	 * @return
	 */
	private String decodeField(YakMessage message, FieldDefinition definition, int length, ByteBuffer buffer, int index) {
		// 获得字符集
		final Charset charset = Charset.forName(messageDef.getEncoding());
		String result;
		// 如果长度为0则说明变量没有值，返回null
		if (length == 0) {
			return "";
		}
		// 根据字段类型将报文解码为报文域
		result = definition.getType().decodeField(message, charset, buffer, length, index, this);
		if(index == 2){
			logger.debug("域内容为[{}]", codeMarkUtils.makeCardMask(result));
		}else {
			logger.debug("域内容为[{}]", codeMarkUtils.makeMask(result));
		}
		return result;
	}
	
	/**
	 * <p>目的：无状态解码复杂子域中的定长子域</p>
	 * <p>承诺：此方法不校验该子域是否为定长子域</p>
	 * @param message 请求消息对象
	 * @param buffer 子域字节流
	 * @param index 子域索引
	 * @return 此子域二进制表示
	 */
	public String decodeExtFieldByFixedLength(YakMessage message, final ByteBuffer buffer, final int index) {
		logger.debug("开始解码复杂定长子域 [{}]", index);
		if (!extFieldMap.containsKey(index)) {
			logger.warn("未定义的复杂子域");
			return "";
		}
		for (int i = 0; i < extFieldMap.getExtFieldSize(index); i++) {
			ExtFieldDefinition efd = extFieldMap.getExtFieldDefByArrayindex(index, i);
			if (buffer.hasRemaining()) {
				logger.debug("开始解码复杂[{}]子域中孩子域[{}]", index, efd.getSubindex());
				message.getExtBodyAttributes().get(index).put(efd.getSubindex(), buildField(message, buffer, efd.getFieldDef(), index));
			}
		}
		return Hex.encodeHexString(buffer.array());
	}
	
	/**
	 * <p>目的：无状态解码复杂子域中的BitMap类型子域</p>
	 * <p>承诺：此方法不校验该子域是否 为BitMap类型子域</p>
	 * @param message 请求消息对象
	 * @param buffer 缓冲区
	 * @param index 子域索引
	 * @return 此子域二进制表示
	 */
	public String decodeExtFieldByBitMap(YakMessage message, final ByteBuffer buffer, final int index) {
		logger.debug("开始解码复杂BitMap子域 [{}]", index);
		if (!extFieldMap.containsKey(index)) {
			logger.warn("未定义的复杂子域");
			return "";
		}
		ExtFieldDefinition bitMapField = extFieldMap.getSubFieldByKey(index, ExtFieldDefinition.ZERO);
		byte[] bitmap = buildBitMapField(buffer, bitMapField.getFieldDef());
		String bitMapString = new String(Hex.encodeHex(bitmap));
		message.getExtBodyAttributes().get(index).put(bitMapField.getSubindex(), bitMapString);
		logger.debug("复杂BitMap子域 [{}]中BitMap值为[{}]", index, bitMapString);
		
		// 排除bitmap域，顺序遍历
		for (int i = 1; i < extFieldMap.getExtFieldSize(index); i++) {
			ExtFieldDefinition efd = extFieldMap.getExtFieldDefByArrayindex(index, i);
			if (containsField(bitmap, Integer.valueOf(efd.getSubindex()))) {
				logger.debug("开始解码复杂[{}]子域中孩子域[{}]", index, efd.getSubindex());
				message.getExtBodyAttributes().get(index).put(efd.getSubindex(), buildField(message, buffer, efd.getFieldDef(), index));
			}
		}
		return Hex.encodeHexString(buffer.array());
	}
	
	/**
	 * <p>目的：无状态解码复杂子域中的TLV类型子域</p>
	 * <p>承诺：此方法不校验该子域是否 为TLV类型子域</p>
	 * @param message 请求消息对象
	 * @param buffer 缓冲区
	 * @param index 子域索引
	 * @return 此子域二进制表示
	 */
	public String decodeExtFieldByTLV(YakMessage message, final ByteBuffer buffer, final int index) {
		logger.debug("开始解码复杂TLV子域 [{}]", index);
		if (!extFieldMap.containsKey(index)) {
			logger.warn("未定义的复杂子域");
			return "";
		}
		ExtTlvField etf =  ExtTlvField.valueOf(extFieldMap.getInput() + index);
		while (buffer.hasRemaining()) {
			String datasetId = buildField(message, buffer, extFieldMap.getSubFieldByKey(index, ExtFieldDefinition.DATASET_ID).getFieldDef(), index);
			byte[] tlvs = getTlvValues(buffer, index);
			// 不存在的datasetid不构建，直接进行下一步
			if (etf.whetherDatasetId(datasetId)) {
				buildTlv(ByteBuffer.wrap(tlvs), index, message, etf);
			} else {
				logger.warn("出现未定义的datasetId：[{}]", datasetId);
			}
		}
		return Hex.encodeHexString(buffer.array());
	}
	
	/**
	 * <p>目的：对该dataset中的所有TLV进行解码</p>
	 * <p>承诺：对于value编码为4 bit BCD的长度需要乘以2再进行解码</p>
	 * @param tlvbuffer 
	 * @param index
	 * @param message
	 * @param etf
	 */
	private void buildTlv(ByteBuffer tlvbuffer, final int index, YakMessage message, ExtTlvField etf) {
		if (!tlvbuffer.hasRemaining()) {
			return;
		}
		//获取tag，强制在TLV格式数组中第三位，同时判断是否下一个byte是否依然属于此tag
		String tag = buildField(message, tlvbuffer, extFieldMap.getSubFieldByKey(index, ExtFieldDefinition.TAG).getFieldDef(), index);
		if (etf.nextBytePartTag(tag))
			tag = tag + buildField(message, tlvbuffer, extFieldMap.getSubFieldByKey(index, ExtFieldDefinition.TAG).getFieldDef(), index);
		
		// 获取length对应的数组
		byte[] lengthBytes = getTlvLength(index, tlvbuffer);
		int length = getLengthByMessageDef(lengthBytes);
		byte[] values = new byte[length];
		tlvbuffer.get(values);
		if (etf.whetherContainTag(tag)) {
			ExtFieldDefinition efd = extFieldMap.getSubFieldByUpperKey(index, tag);
			if (efd.getFieldDef().getType() == FieldType.fb) {
				if (0 == efd.getFieldDef().getLengthSize())
					length = efd.getFieldDef().getContentMaxSize();
				else
					length = length << 1;
			}
			String value = decodeField(message, efd.getFieldDef(), length, ByteBuffer.wrap(values), index);
			message.getExtBodyAttributes().get(index).put(efd.getSubindex(), value);
		} else {
			logger.warn("出现未定义的TAG：[{}]", tag);
		}
		buildTlv(tlvbuffer, index, message, etf);
	}
	
	/**
	 * <p>目的：获取TLV格式中的length</p>
	 * <p>承诺：由于不同的卡组织TLV格式中L的获取各不相同</p>
	 * @param index
	 * @return
	 */
	private byte[] getTlvLength(final int index, ByteBuffer tlvbuffer) {
		ExtFieldDefinition efd = extFieldMap.getSubFieldByKey(index, ExtFieldDefinition.LENGTH);
		// 非定长，证明是VISA，VISA格式中length很特殊
		if (efd.getFieldDef().getLengthSize() > 0) {
			byte[] lengthByte = new byte[efd.getFieldDef().getLengthSize()];
			tlvbuffer.get(lengthByte);
			// 第一位为1改为0
			if (containsField(lengthByte, 0)) {
				lengthByte[0] &= 0x7f;
				int lengthLength = getLengthByMessageDef(lengthByte);
				byte[] lengthValue = new byte[lengthLength];
				tlvbuffer.get(lengthValue);
				return lengthValue;
			} else {
				return lengthByte;
			}
		} else {
			byte[] lengthByte = new byte[efd.getFieldDef().getContentMaxSize()];
			tlvbuffer.get(lengthByte);
			return lengthByte;
			
		}
	}
	
	/**
	 * <p>目的：获取该dataset对应的所有TLV缓冲</p>
	 * <p>承诺：此处不判断缓冲是否到达低端</p>
	 * @param buffer 缓冲区
	 * @param index 子域索引
	 * @return
	 */
	private byte[] getTlvValues(final ByteBuffer buffer, final int index) {
		byte[] datasetlengthByte = new byte[getDefLength(extFieldMap.getSubFieldByKey(index, ExtFieldDefinition.DATASET_LENGTH).getFieldDef(), buffer)];
		buffer.get(datasetlengthByte);
		byte[] tlvs = new byte[getLengthByMessageDef(datasetlengthByte)];
		buffer.get(tlvs);
		return tlvs;
	}
	
	/**
	 * <p>目的：根据消息定义中的数字型获取字段对应长度</p>
	 * <p>承诺：由于枚举需要抛出异常，懒得一直处理声明式异常，此处会捕获IllegalArgumentException和CodecException异常</p>
	 * @return 出现异常响应0
	 */
	private int getLengthByMessageDef(byte[] bytes) {
		logger.debug("长度字节表示为[{}]",  Hex.encodeHexString(bytes));
		try {
			return messageDef.getLengthtype().getValueLengthByType(bytes);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (CodecException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * <p>目的：判断bitMap中是否有子域</p>
	 * <p>承诺：存在则响应true</p>
	 * @param bitmap
	 * @param index
	 * @return
	 */
	public boolean containsField(final byte[] bitmap, final int index) {
		// 得到index在位图中的第几字节
		final int mapIndex = (index - 1) / 8;
		// 判断index是否存在
		if (((bitmap[mapIndex] << (index - 1) % 8) & 0x80) == 0x80) {
			return true;
		} else {
			return false;
		}
	}

	public void setMessageDef(MessageDefinition messageDef) {
		this.messageDef = messageDef;
	}

	public void setExtFieldMap(ExtFieldMap extFieldMap) {
		this.extFieldMap = extFieldMap;
	}
	
}
