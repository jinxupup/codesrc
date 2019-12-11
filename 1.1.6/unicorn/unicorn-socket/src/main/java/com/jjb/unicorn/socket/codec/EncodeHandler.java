package com.jjb.unicorn.socket.codec;

import java.nio.ByteBuffer;
import java.util.Arrays;
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


/**
 * <p>Description: 编码处理器，此处理器带编码扩展子域功能</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: EncodeHandler
 * @author LI.H
 * @date 2015年12月12日 上午11:14:55
 * @version 1.0
 */
public class EncodeHandler {
	
	private static Logger logger = LoggerFactory.getLogger(EncodeHandler.class);
	
	/**
	 * 报文允许的最大字节长度
	 */
	public final static int MESSAGE_BUFFER_SIZE = 2048;
	
	private MessageDefinition messageDef;
	
	private ExtFieldMap extFieldMap;
	
	@Autowired private CodeMarkUtils codeMarkUtils;
	
	/**
	 * <p>目的：将传入的变量内容编码为8583协议格式报文体</p>
	 * <p>承诺：</p>
	 * @param fieldsMap 存放变量内容的map，key为从1开始的索引，value为变量值
	 * @return 返回存放编码结果的缓冲区
	 * @throws CodecException 变量定义格式异常,如BCD的长度容量定义为奇数、INT型长度容量定义为不是1、2或4的值
	 * @throws DecoderException 字节类型的域，长度不是偶数时抛出此异常
	 */
	public ByteBuffer encode8583Body(final YakMessage message) throws CodecException, DecoderException {
		
		final ByteBuffer buff = ByteBuffer.allocate(MESSAGE_BUFFER_SIZE);
		Map<Integer, String> fieldsMap = message.getBodyAttributes();
		// body定义中的域大于140，说明为VISA渠道
		boolean hassecondaryBitmap = messageDef.getBody().size() > 140;
		// 构造第二位图
		final byte[] secondaryBitmap = new byte[8];
		if (hassecondaryBitmap) {
			if (encodeBitmap(128, secondaryBitmap, fieldsMap)) {
				// 将次位图编入报文对象
				fieldsMap.put(65, Hex.encodeHexString(secondaryBitmap));
			} else {
				// 移除第二位图
				fieldsMap.remove(65);
			}
		}
		
		// 构造第一位图
		final byte[] firstBitmap = new byte[8];
		if (encodeBitmap(64, firstBitmap, fieldsMap)) {
			// 将第一位图编入报文对象
			fieldsMap.put(1, Hex.encodeHexString(firstBitmap));
		} else {
			// 移除第一位图
			fieldsMap.remove(1);
		}
		// 构造主位图
		final byte[] primaryBitmap = new byte[8];
		encodeBitmap(0, primaryBitmap, fieldsMap);
		buff.put(primaryBitmap);
		
		// 判断第一位图是否存在
		final int fieldsCount;
		if ((primaryBitmap[0] & 0x80) == 0x80) {
			buff.put(firstBitmap);
			fieldsMap.remove(1);
			// 判断第二位图是否存在
			if (hassecondaryBitmap && (firstBitmap[0] & 0x80) == 0x80) {
				buff.put(secondaryBitmap);
				fieldsMap.remove(65);
				fieldsCount = 192;
			} else {
				fieldsCount = 128;
			}
		} else {
			fieldsCount = 64;
		}
		// 编码变量
		for (int index = 1; index <= fieldsCount; index++) {
			if (fieldsMap.containsKey(index)) {
				String value = fieldsMap.get(index);
				logger.debug("编码报文体[{}]，值为[{}]", index, value);
				FieldDefinition fieldDefinition = messageDef.getBody().get(index);
				// 根据变量类型将变量转为字节数组
				byte[] valueBytes = fieldDefinition.getType().encodeField(fieldDefinition, message, value, messageDef.getEncoding(), true, index, this);
				ByteBuffer lvBytes= null;
				if (60 != index && FieldType.fb == fieldDefinition.getType()) {
					// 为4 bit BCD添加变量长度标识
					lvBytes = lvBytes(valueBytes, messageDef.getLengthtype(), fieldDefinition.getLengthSize(), value.toCharArray().length);
				} else {
					// 根据lv格式添加变量长度标识
					lvBytes = lvBytes(valueBytes, messageDef.getLengthtype(), fieldDefinition.getLengthSize(), valueBytes.length);
				}
				buff.put(lvBytes);
			}
		}
		// 写buff完成，翻回，为读取做准备
		buff.flip();
		logger.debug("报文体内容[{}]", codeMarkUtils.makeMask(message.getBodyAttributes().toString()));
		return buff;
	}
	
	/**
	 * <p>目的：将传入的变量内容编码为固定变量数量格式</p>
	 * <p>承诺：</p>
	 * @param message
	 * @param fieldsDefMap 字段定义map，key为从1开始的索引，value为{@link FieldDefinition}
	 * @param fieldsMap 存放变量内容的map，key为从1开始的索引，value为变量值
	 * @return
	 * @throws CodecException 变量定义格式异常,如BCD的长度容量定义为奇数、INT型长度容量定义为不是1、2或4的值
	 * @throws DecoderException 字节类型的域，长度不是偶数时抛出此异常
	 */
	public ByteBuffer encodeFieldByMap(YakMessage message, Map<Integer, FieldDefinition> fieldsDefMap, Map<Integer, String> fieldsMap) throws CodecException, DecoderException {
		final ByteBuffer buff = ByteBuffer.allocate(MESSAGE_BUFFER_SIZE);
		// 编码变量
		for (int index = 1; index <= fieldsDefMap.size(); index++) {
			String value = fieldsMap.get(index);
			logger.debug("编码固定格式Map，Index为[{}]，Value为[{}]", index, value);
			FieldDefinition fieldDefinition = fieldsDefMap.get(index);
			// 根据变量类型将变量转为字节数组
			byte[] valueBytes = fieldDefinition.getType().encodeField(fieldDefinition, message, value, messageDef.getEncoding(), true, index, this);
			// 根据lv格式添加变量长度标识
			ByteBuffer lvBytes = lvBytes(valueBytes, LengthType.STRING, fieldDefinition.getLengthSize());
			buff.put(lvBytes);
		}
		// 写buff完成，翻回，为读取做准备
		buff.flip();
		return buff;
	}
	
	/**
	 * <p>目的：无状态编码定长扩展子域，不验证子域是否为定长扩展子域</p>
	 * <p>承诺：此只编码子域中的value</p>
	 * @param message 响应消息对象
	 * @param index 子域索引
	 * @return 此子域编码后值
	 */
	public byte[] encodeExtFieldByFixedLength(YakMessage message, final int index) {
		logger.debug("开始编码复杂定长子域 [{}]", index);
		if (!extFieldMap.containsKey(index)) {
			logger.warn("未定义的复杂子域");
			return null;
		}
		int maxSubIndex = 0;
		int maxlength = 0;
		boolean isFindMaxSubIndex = false;
		// 找到定长最大的有值的孩子域，该该孩子域之前的所有孩子域如果无值需要使用默认字符填充
		for (int i = extFieldMap.getExtFieldSize(index) - 1; i >= 0; i--) {
			ExtFieldDefinition efd = extFieldMap.getExtFieldDefByArrayindex(index, i);
			if (!isFindMaxSubIndex && (null != message.getExtBodyAttributes().get(index).get(efd.getSubindex()))) {
				maxSubIndex = i;
				isFindMaxSubIndex = true;
			}
			if (isFindMaxSubIndex) {
				maxlength += efd.getFieldDef().getLengthSize() > 0 ? efd.getFieldDef().getLengthSize() : efd.getFieldDef().getContentMaxSize();
			}
		}
		
		ByteBuffer result = ByteBuffer.allocate(maxlength);
		for (int i = 0; i <= maxSubIndex; i++) {
			ExtFieldDefinition efd = extFieldMap.getExtFieldDefByArrayindex(index, i);
			byte[] valueBytes = efd.getFieldDef().getType().encodeField(efd.getFieldDef(), message, message.getExtBodyAttributes().get(index).get(efd.getSubindex()), messageDef.getEncoding(), true, index, this);
			result.put(valueBytes);
		}
		return result.array();
	}
	
	/**
	 * <p>目的：无状态编码BitMap扩展子域，不验证子域是否为BitMap扩展子域</p>
	 * <p>承诺：此只编码子域中的value</p>
	 * TODO 目前visa前置关于bitmap格式复杂域需要响应的均使用原值，所以这里直接使用原值，等时间舒缓再做处理
	 * @param message 响应消息对象
	 * @param index 子域索引
	 * @return 此子域编码后值
	 * @throws DecoderException 
	 */
	public byte[] encodeExtFieldByBitMap(YakMessage message, final int index) {
		logger.debug("开始编码复杂BitMap子域 [{}]", index);
		if (!extFieldMap.containsKey(index)) {
			logger.warn("未定义的复杂子域");
			return null;
		}
		byte[] values = null;
		try {
			values = Hex.decodeHex(message.getBody(index).toCharArray());
		} catch (DecoderException e) {
			logger.warn("解码复杂子域失败");
		}
		return values;
	}
	
	/**
	 * <p>目的：无状态编码TLV扩展子域，不验证子域是否为TLV扩展子域</p>
	 * <p>承诺：此只编码子域中的value</p>
	 * TODO 目前visa前置关于TLV格式复杂域需要响应的均使用原值，所以这里直接使用原值，等时间舒缓再做处理
	 * @param message 响应消息对象
	 * @param index 子域索引
	 * @return 此子域编码后值
	 * @throws DecoderException 
	 */
	public byte[] encodeExtFieldByTlv(YakMessage message, final int index) {
		logger.debug("开始编码复杂TLV子域 [{}]", index);
		if (!extFieldMap.containsKey(index)) {
			logger.warn("未定义的复杂子域");
			return null;
		}
		byte[] values = null;
		try {
			values = Hex.decodeHex(message.getBody(index).toCharArray());
		} catch (DecoderException e) {
			logger.warn("解码复杂子域失败");
		}
		return values;
	}
	
	/**
	 * <p>目的：根据字段Map内容编码位图</p>
	 * <p>承诺：</p>
	 * @param offset 字段索引偏移量
	 * @param bitmap 位图字节数组
	 * @param fieldMap 包含8583字段的Map，Index(1-192)作为key,字段值作为value
	 * @return 位图是否为空
	 */
	private boolean encodeBitmap(final int offset, byte[] bitmap, final Map<Integer, String> fieldMap) {
		if (offset % 8 != 0) {
			throw new IllegalArgumentException("offset必须是8的倍数");
		}
		// 初始化空位图标识
		boolean isEmpty = false;
		int tempIndex;
		// 组装位图
		// 按字节循环位图
		for (int i = 0; i < bitmap.length; i++) {
			// 按bit循环位图
			for (int j = 1; j <= 8; j++) {
				// 组装循环范围内的key
				tempIndex = i * 8 + j + offset;
				bitmap[i] <<= 1;
				if (fieldMap.containsKey(tempIndex)) {
					// 记录字段存在
					bitmap[i] |= 0x01;
					// 标识范围内有字段存在
					isEmpty = true;
				}
			}
		}
		return isEmpty;
	}
	
	/**
	 * 根据长度位数在字段内容前增加字段长度
	 * @param bytes 字段内容
	 * @param lengthType 长度类型，参见{@link LengthType}
	 * @param lengthSize 字段长度所占字节数，0为定长
	 * @return 在内容前编入了长度的ByteBuffer
	 * @throws CodecException 长度与类型不符时抛出此异常，如INT应为型长度只能是1,2,4，BCD型长度一定是偶数
	 */
	public ByteBuffer lvBytes(final byte[] bytes, final LengthType lengthType, final int lengthSize) throws CodecException {
		return lvBytes(bytes, lengthType, lengthSize, lengthSize);
	}
	
	/**
	 * 根据长度位数在字段内容前增加字段长度
	 * @param bytes 字段内容
	 * @param lengthType 长度类型，参见{@link LengthType}
	 * @param lengthSize 字段长度所占字节数，0为定长
	 * @param valueLength 字段长度(为了兼容4bitBCD无奈之举)
	 * @return 在内容前编入了长度的ByteBuffer
	 * @throws CodecException 长度与类型不符时抛出此异常，如INT应为型长度只能是1,2,4，BCD型长度一定是偶数
	 */
	public ByteBuffer lvBytes(final byte[] bytes, final LengthType lengthType, final int lengthSize, final int valueLength) throws CodecException {
		if (lengthSize > 0) {
			// 如果为可变长度则将长度根据配置添在内容前
			ByteBuffer buff = ByteBuffer.allocate(lengthSize + bytes.length);
			buff.put(lengthType.getBytesByLength(lengthSize, valueLength));
			buff.put(bytes);
			buff.flip();
			return buff;
		} else {
			// 定长则直接返回
			return ByteBuffer.wrap(bytes);
		}
	}
	
	/**
	 * <p>目的：在给定内容左边用给定填充字节根据指定长度填充字节数组</p>
	 * <p>承诺：当数组长度超过指定长度将抛出IllegalArgumentException异常</p>
	 * @param bytes 数组内容
	 * @param length 数组总长度
	 * @param paddingByte 填充字节
	 * @return 按给定参数填充后的字节数组
	 */
	public byte[] leftPad(byte[] bytes, int length, byte paddingByte) {
		if (length < bytes.length) {
			throw new IllegalArgumentException("length必须大于字节数组本身的长度");
		}
		byte[] result = new byte[length];
		// 获取填充字节
		byte[] paddingBytes = new byte[length - bytes.length];
		Arrays.fill(paddingBytes, paddingByte);
		// 组装填充
		System.arraycopy(paddingBytes, 0, result, 0, paddingBytes.length);
		// 组装内容
		System.arraycopy(bytes, 0, result, paddingBytes.length, bytes.length);
		return result;
	}
	
	/**
	 * 在给定内容右边用给定填充字节根据指定长度填充字节数组
	 * 
	 * @param bytes
	 *            数组内容
	 * @param length
	 *            数组总长度
	 * @param paddingByte
	 *            填充字节
	 * 
	 * @return 按给定参数填充后的字节数组
	 */
	public byte[] rightPad(final byte[] bytes, final int length, final byte paddingByte) {
		if (length < bytes.length) {
			throw new IllegalArgumentException("length必须大于字节数组本身的长度");
		}
		byte[] result = new byte[length];
		// 获取填充字节
		byte[] paddingBytes = new byte[length - bytes.length];
		Arrays.fill(paddingBytes, paddingByte);
		// 组装内容
		System.arraycopy(bytes, 0, result, 0, bytes.length);
		// 组装填充
		System.arraycopy(paddingBytes, 0, result, bytes.length, paddingBytes.length);
		return result;
	}
	
	public void setMessageDef(MessageDefinition messageDef) {
		this.messageDef = messageDef;
	}

	public void setExtFieldMap(ExtFieldMap extFieldMap) {
		this.extFieldMap = extFieldMap;
	}
	
}
