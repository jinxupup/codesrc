package com.jjb.unicorn.socket.codec;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.socket.codec.exception.CodecException;
import com.jjb.unicorn.socket.definition.FieldDefinition;
import com.jjb.unicorn.socket.definition.MessageType;

/**
 * 编解码工具类
 * 
 * @author BIG.CPU
 * 
 */
public class CodecUtil {

	/**
	 * 报文允许的最大字节长度
	 */
	public static int messageBufferSize = 2048;

	private static Logger logger = LoggerFactory.getLogger(CodecUtil.class);

	/**
	 * 将buffer中的内容根据8583协议解码
	 * 
	 * @param encoding
	 *            编码格式，如：GBK
	 * @param fieldsDefMap
	 *            变量定义map，key为从1开始的索引，value为{@link FieldDefinition}
	 * @param buffer
	 *            存放待解码内容的缓冲区
	 * 
	 * @return 返回存放解码结果的map，key为从1开始的索引，value为变量值
	 * 
	 */
	public static Map<Integer, String> decode8583(CodeMarkUtils codeMarkUtils, final String encoding, final Map<Integer, FieldDefinition> fieldsDefMap, final ByteBuffer buffer) throws CharacterCodingException, DecoderException {
		final Map<Integer, String> fieldsMap = new HashMap<Integer, String>();

		// 获取位图
		byte[] bitmap = new byte[8];
		buffer.get(bitmap);

		fieldsMap.put(0, new String(Hex.encodeHex(bitmap)));

		// 解码主位图中存在的报文域
		for (int i = 1; i <= 64; i++) {
			if (containsField(bitmap, i)) {
				logger.debug("解码报文第[" + i + "]域");
				FieldDefinition fieldDefinition = fieldsDefMap.get(i);
				int length = getDefLength(fieldDefinition, buffer);
				String value = decodeField(codeMarkUtils,fieldDefinition, length, buffer, encoding,i);
				fieldsMap.put(i, value);
			}
		}

		// 解码次位图中存在的报文域
		if (fieldsMap.get(1) != null) {
			bitmap = Hex.decodeHex(fieldsMap.get(1).toCharArray());
			for (int i = 1; i <= 64; i++) {
				if (containsField(bitmap, i)) {
					logger.debug("解码报文第[" + i + "]域");
					FieldDefinition fieldDefinition = fieldsDefMap.get(i + 64);
					int length = getDefLength(fieldDefinition, buffer);
					String value = decodeField(codeMarkUtils,fieldDefinition, length, buffer, encoding,i);
					fieldsMap.put(i + 64, value);
				}
			}
		}

		return fieldsMap;
	}

	/**
	 * 获得变量的长度
	 * 
	 * @param fieldDefinition
	 *            变量定义
	 * @param buffer
	 *            存放变量的缓冲区
	 * @return 变量的长度
	 */
	private static int getDefLength(FieldDefinition fieldDefinition, ByteBuffer buffer) {
		// 得到字段长度
		int length;
		// 根据定长和变长确定不同计算方式
		if (fieldDefinition.getLengthSize() > 0) {
			logger.debug("变长域，长度为[" + fieldDefinition.getLengthSize() + "]位");
			byte[] lengthBytes = new byte[fieldDefinition.getLengthSize()];
			buffer.get(lengthBytes);
			logger.debug("长度字节表示为[" + Hex.encodeHexString(lengthBytes) + "]");
			length = Integer.parseInt(new String(lengthBytes));
			logger.debug("长度为[" + length + "]");
		} else {
			length = fieldDefinition.getContentMaxSize();
			logger.debug("定长域，长度为[" + length + "]位");
		}
		return length;
	}

	/**
	 * 将传入的变量内容编码为8583协议格式
	 * 
	 * @param encoding
	 *            编码格式，如：GBK
	 * @param fieldsDefMap
	 *            字段定义map，key为从1开始的索引，value为{@link FieldDefinition}
	 * @param fieldsMap
	 *            存放变量内容的map，key为从1开始的索引，value为变量值
	 * 
	 * @return 返回存放编码结果的缓冲区
	 * 
	 * @throws CodecException
	 *             变量定义格式异常,如BCD的长度容量定义为奇数、INT型长度容量定义为不是1、2或4的值
	 * @throws DecoderException
	 *             字节类型的域，长度不是偶数时抛出此异常
	 */
	public static ByteBuffer encode8583(final String encoding, final Map<Integer, FieldDefinition> fieldsDefMap, final Map<Integer, String> fieldsMap) throws CodecException, DecoderException {
		final ByteBuffer buff = ByteBuffer.allocate(messageBufferSize);
		// 构造次位图
		final byte[] secondaryBitmap = new byte[8];
		if (encodeBitmap(64, secondaryBitmap, fieldsMap)) {
			// 将次位图编入报文对象
			fieldsMap.put(1, Hex.encodeHexString(secondaryBitmap));
		} else {
			// 移除次位图
			fieldsMap.remove(1);
		}
		// 构造主位图
		final byte[] primaryBitmap = new byte[8];
		encodeBitmap(0, primaryBitmap, fieldsMap);
		// 主位图编入报文
		buff.put(primaryBitmap);
		// 判断次位图是否存在
		final int fieldsCount;
		if ((primaryBitmap[0] & 0x80) == 0x80) {
			fieldsCount = 128;
		} else {
			fieldsCount = 64;
		}
		// 编码变量
		for (int i = 1; i <= fieldsCount; i++) {
			if (fieldsMap.containsKey(i)) {
				String value = fieldsMap.get(i);
				FieldDefinition fieldDefinition = fieldsDefMap.get(i);
				// 根据变量类型将变量转为字节数组
				byte[] valueBytes = encodeField(fieldDefinition, value, encoding, true);
				// 根据lv格式添加变量长度标识
				ByteBuffer lvBytes = lvBytes(valueBytes, LengthType.STRING, fieldDefinition.getLengthSize());
				buff.put(lvBytes);
			}
		}
		// 写buff完成，翻回，为读取做准备
		buff.flip();
		return buff;
	}

	/**
	 * 将buffer中的内容根据固定变量数量格式解码
	 * 
	 * @param encoding
	 *            编码格式，如：GBK
	 * @param fieldsDefMap
	 *            变量定义map，key为从1开始的索引，value为{@link FieldDefinition}
	 * @param buffer
	 *            存放待解码内容的缓冲区
	 * 
	 * @return 返回存放解码结果的map，key为从1开始的索引，value为变量值
	 * 
	 * @throws CodecException
	 *             消息定义异常，当变量定义INDEX不连续时抛出此异常
	 * 
	 */
	public static Map<Integer, String> decodeFieldOnly(CodeMarkUtils codeMarkUtils,String encoding, Map<Integer, FieldDefinition> fieldsDefMap, ByteBuffer buffer) throws CodecException {
		final Map<Integer, String> fieldsMap = new HashMap<Integer, String>();
		// 根据变量定义依次解析变量值
		for (int i = 1; i <= fieldsDefMap.size(); i++) {
			logger.debug("解码报文第[" + i + "]域");
			FieldDefinition definition = fieldsDefMap.get(i);
			if (definition == null) {
				throw new CodecException("index为[" + i + "]的变量定义为null," + MessageType.FIELD_ONLY + "模式的变量定义不能有不连续的index");
			}
			int length = getDefLength(definition, buffer);
			String value = decodeField(codeMarkUtils,definition, length, buffer, encoding, i);
			fieldsMap.put(i, value);
		}
		return fieldsMap;
	}

	/**
	 * 将传入的变量内容编码为固定变量数量格式
	 * 
	 * @param encoding
	 *            编码格式，如：GBK
	 * @param fieldsDefMap
	 *            字段定义map，key为从1开始的索引，value为{@link FieldDefinition}
	 * @param fieldsMap
	 *            存放变量内容的map，key为从1开始的索引，value为变量值
	 * 
	 * @return 返回存放编码结果的缓冲区
	 * 
	 * @throws CodecException
	 *             变量定义格式异常,如BCD的长度容量定义为奇数、INT型长度容量定义为不是1、2或4的值
	 * @throws DecoderException
	 *             字节类型的域，长度不是偶数时抛出此异常
	 */
	public static ByteBuffer encodeFieldOnly(String encoding, Map<Integer, FieldDefinition> fieldsDefMap, Map<Integer, String> fieldsMap) throws CodecException, DecoderException {
		final ByteBuffer buff = ByteBuffer.allocate(messageBufferSize);
		// 编码变量
		for (int i = 1; i <= fieldsDefMap.size(); i++) {
			String value = fieldsMap.get(i);
			FieldDefinition fieldDefinition = fieldsDefMap.get(i);
			// 根据变量类型将变量转为字节数组
			byte[] valueBytes = encodeField(fieldDefinition, value, encoding, true);
			// 根据lv格式添加变量长度标识
			ByteBuffer lvBytes = lvBytes(valueBytes, LengthType.STRING, fieldDefinition.getLengthSize());
			buff.put(lvBytes);
		}
		// 写buff完成，翻回，为读取做准备
		buff.flip();
		return buff;
	}

	/**
	 * 将buffer中的内容根据分隔符格式解码
	 * 
	 * @param encoding
	 *            编码格式，如：GBK
	 * @param fieldsDefMap
	 *            变量定义map，key为从1开始的索引，value为{@link FieldDefinition}
	 * @param buffer
	 *            存放待解码内容的缓冲区
	 * @param separator
	 *            分隔符
	 * 
	 * @return 返回存放解码结果的map，key为从1开始的索引，value为变量值
	 * 
	 * @throws CodecException
	 *             消息定义异常，当变量定义INDEX不连续时抛出此异常
	 * 
	 */
	public static Map<Integer, String> decodeFieldAndSeparator(CodeMarkUtils codeMarkUtils,String encoding, Map<Integer, FieldDefinition> fieldsDefMap, ByteBuffer buffer, byte separator) throws CodecException {
		final Map<Integer, String> fieldsMap = new HashMap<Integer, String>();
		// 根据变量定义依次解码
		for (int i = 1; i <= fieldsDefMap.size(); i++) {
			logger.debug("解码报文第[" + i + "]域");
			FieldDefinition definition = fieldsDefMap.get(i);
			if (definition == null) {
				throw new CodecException("index为[" + i + "]的变量定义为null," + MessageType.FIELD_AND_SEPERATOR + "模式的变量定义不能有不连续的index");

			}

			// 获得变量的长度
			int length = 0;
			buffer.mark();
			byte b = buffer.get();
			while (b != separator && buffer.hasRemaining()) {
				length++;
				b = buffer.get();
			}

			// 如果域非空
			if (length > 0) {
				// 重置缓冲区到域起点
				buffer.reset();

				// 得到变量值
				String value = decodeField(codeMarkUtils,definition, length, buffer, encoding, i);
				fieldsMap.put(i, value);

				// 除了最后一域，跳过分隔符
				if (i != fieldsDefMap.size()) {
					buffer.position(buffer.position() + 1);
				}
			}
		}
		return fieldsMap;
	}

	/**
	 * 将传入的变量内容编码为分隔符格式
	 * 
	 * @param encoding
	 *            编码格式，如：GBK
	 * @param fieldsDefMap
	 *            字段定义map，key为从1开始的索引，value为{@link FieldDefinition}
	 * @param fieldsMap
	 *            存放变量内容的map，key为从1开始的索引，value为变量值
	 * @param separator
	 *            分隔符
	 * 
	 * @return 返回存放编码结果的缓冲区
	 * 
	 * @throws CodecException
	 *             消息定义异常，当变量定义INDEX不连续时抛出此异常
	 * @throws DecoderException
	 *             字节类型的域，长度不是偶数时抛出此异常
	 */
	public static ByteBuffer encodeFieldAndSeparator(String encoding, Map<Integer, FieldDefinition> fieldsDefMap, Map<Integer, String> fieldsMap, byte separator) throws CodecException,
			DecoderException {
		final ByteBuffer buffer = ByteBuffer.allocate(messageBufferSize);
		boolean isFirst = true;
		// 根据变量定义依次编码
		for (int i = 1; i <= fieldsDefMap.size(); i++) {
			FieldDefinition fieldDefinition = fieldsDefMap.get(i);
			if (fieldDefinition == null) {
				throw new CodecException("index为[" + i + "]的变量定义为null," + MessageType.FIELD_AND_SEPERATOR + "模式的变量定义不能有不连续的index");
			}
			// 除了第一个域，其他域之前先增加分隔符
			if (isFirst) {
				isFirst = false;
			} else {
				buffer.put(separator);
			}
			// 根据域类型编码
			byte[] valueBytes = encodeField(fieldDefinition, fieldsMap.get(i), encoding, false);
			buffer.put(valueBytes);
		}
		// 写入完成、翻回
		buffer.flip();
		return buffer;
	}

	/**
	 * 根据变量定义解码变量
	 * 
	 * @param definition
	 *            变量定义
	 * @param length
	 *            变量长度
	 * @param buffer
	 *            存放变量的字节缓冲区
	 * @param encoding
	 *            字符编码格式，如GBK
	 * 
	 * @return 解码后的变量
	 * 
	 */
	public static String decodeField(CodeMarkUtils codeMarkUtils,FieldDefinition definition, int length, ByteBuffer buffer, String encoding, int index) {
		// 获得字符集
		final Charset charset = Charset.forName(encoding);
		String result;
		// 如果长度为0则说明变量没有值，返回null
		if (length == 0) {
			return "";
		}
		// 得到字段内容
		byte[] contentTemp = new byte[length];
		buffer.get(contentTemp);
		if(index == 2 || index == 35 || index == 36 || index == 45 || index == 61 || index == 102 || 
				index == 103 || index ==52 || index == 55 || index == 48 || index == 57){
			logger.debug("域内容字节表示为[" + codeMarkUtils.makeMask(Hex.encodeHexString(contentTemp)) + "]");
		}else{
			logger.debug("域内容字节表示为[" + Hex.encodeHexString(contentTemp) + "]");
		}
		
		// 根据字段类型将报文解码为报文域
		switch (definition.getType()) {
		case n:
		case s:
			result = charset.decode(ByteBuffer.wrap(contentTemp)).toString();
			if(index == 2){
				logger.debug("字符域，域内容为[" + codeMarkUtils.makeCardMask(result) + "]");
			}else if(index == 35 || index == 36 || index == 45 || index == 61 || index == 102 || 
					index == 103 || index == 57 || index == 55){
				logger.debug("字符域，域内容为[" + codeMarkUtils.makeMask(result) + "]");
			}else{
				logger.debug("字符域，域内容为[" + result + "]");
			}
			break;
		case cn:
		case b:
			result = new String(Hex.encodeHex(contentTemp));
			logger.debug("字节域");
			break;
		default:
			throw new IllegalArgumentException("不支持的字段编码类型");
		}

		return result;
	}

	/**
	 * 根据变量定义编码变量
	 * 
	 * @param fieldDefinition
	 *            变量定义
	 * @param tempValue
	 *            变量值
	 * @param encoding
	 *            字符编码格式，如GBK
	 * @param needPadding
	 *            定长变量，长度不足时是否需要补足
	 * 
	 * @return 编码后的字节数组
	 * 
	 * @throws DecoderException
	 *             字节类型的域，长度不是偶数时抛出此异常
	 * 
	 */
	public static byte[] encodeField(FieldDefinition fieldDefinition, String tempValue, String encoding, boolean needPadding) throws DecoderException {
		// 根据字段类型将报文域转为字节数组
		byte[] tempBytes = null;
		switch (fieldDefinition.getType()) {
		// 数字类型
		case n:
			try {
				tempBytes = (tempValue == null ? new byte[0] : (tempValue).getBytes(encoding));
			} catch (UnsupportedEncodingException e) {
				// 忽略
			}
			// 定长数字类型左补零
			if (fieldDefinition.getLengthSize() == 0 && needPadding) {
				tempBytes = leftPad(tempBytes, fieldDefinition.getContentMaxSize(), (byte) '0');
			}
			break;
		case s:
			try {
				tempBytes = (tempValue == null ? new byte[0] : (tempValue).getBytes(encoding));
			} catch (UnsupportedEncodingException e) {
				// 忽略
			}
			// 定长字符类型右补空格
			if (fieldDefinition.getLengthSize() == 0 && needPadding) {
				tempBytes = rightPad(tempBytes, fieldDefinition.getContentMaxSize(), (byte) ' ');
			}
			break;
		case cn:
			if (tempValue != null) {
				// 如果长度是奇数，则左补0
				if ((tempValue.length() & 0x01) != 0) {
					tempValue = "0" + tempValue;
				}
				try {
					tempBytes = Hex.decodeHex(tempValue.toCharArray());
				} catch (DecoderException e) {
					// 不会出现，忽略
				}
			} else {
				tempBytes = new byte[0];
			}
			// 定长数字类型左补零
			if (fieldDefinition.getLengthSize() == 0 && needPadding) {
				tempBytes = leftPad(tempBytes, fieldDefinition.getContentMaxSize(), (byte) '0');
			}
			break;
		case b:
			tempBytes = (tempValue == null ? new byte[0] : Hex.decodeHex(tempValue.toCharArray()));
			// 定长字节类型右补空格
			if (fieldDefinition.getLengthSize() == 0 && needPadding) {
				tempBytes = rightPad(tempBytes, fieldDefinition.getContentMaxSize(), (byte) ' ');
			}
			break;
		default:
			throw new IllegalArgumentException("不支持的字段编码类型");
		}
		return tempBytes;
	}

	/**
	 * 根据字段Map内容编码位图
	 * 
	 * @param offset
	 *            字段索引偏移量
	 * @param bitmap
	 *            位图字节数组
	 * @param fieldMap
	 *            包含8583字段的Map，Index(1-128)作为key,字段值作为value
	 * 
	 * @return 位图是否为空
	 */
	public static boolean encodeBitmap(final int offset, byte[] bitmap, final Map<Integer, String> fieldMap) {
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
	 * 判断给定索引在位图中是否存在
	 * 
	 * @param bitmap
	 *            位图
	 * @param index
	 *            索引
	 * 
	 * @return 索引存在则返回true，否则false
	 */
	public static boolean containsField(final byte[] bitmap, final int index) {
		// 得到index在位图中的第几字节
		final int mapIndex = (index - 1) / 8;
		// 判断index是否存在
		if (((bitmap[mapIndex] << (index - 1) % 8) & 0x80) == 0x80) {
			return true;
		} else {
			return false;
		}
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
	public static byte[] rightPad(final byte[] bytes, final int length, final byte paddingByte) {
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

	/**
	 * 在给定内容左边用给定填充字节根据指定长度填充字节数组
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
	public static byte[] leftPad(byte[] bytes, int length, byte paddingByte) {
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
	 * 根据长度位数在字段内容前增加字段长度
	 * 
	 * @param bytes
	 *            字段内容
	 * @param lengthType
	 *            长度类型，参见{@link LengthType}
	 * @param lengthSize
	 *            字段长度所占字节数，0为定长
	 * 
	 * @return 在内容前编入了长度的ByteBuffer
	 * 
	 * @throws CodecException
	 *             长度与类型不符时抛出此异常，如INT应为型长度只能是1,2,4，BCD型长度一定是偶数
	 */
	public static ByteBuffer lvBytes(final byte[] bytes, final LengthType lengthType, final int lengthSize) throws CodecException {
		if (lengthSize > 0) {
			// 如果为可变长度则将长度根据配置添在内容前
			ByteBuffer buff = ByteBuffer.allocate(lengthSize + bytes.length);
			byte[] length;
			switch (lengthType) {
			case STRING:
				length = String.format("%0" + lengthSize + "d", bytes.length).getBytes();
				break;
			case INT:
				if (lengthSize != 1 && lengthSize != 2 && lengthSize != 4) {
					throw new CodecException("INT类型的长度容量必须为1,2,4中的一个");
				}
				length = new byte[lengthSize];
				for (int i = 0; i < lengthSize; i++) {
					length[i] = (byte) (bytes.length >> ((lengthSize - i - 1) * 8));
				}
				break;
			case BCD:
				try {
					length = intToBCD(bytes.length, lengthSize);
				} catch (DecoderException e) {
					throw new CodecException("BCD类型的长度容量必须为偶数", e);
				}
				break;
			default:
				throw new IllegalArgumentException("不支持的长度类型[" + lengthType + "]");
			}
			buff.put(length);
			buff.put(bytes);
			buff.flip();
			return buff;
		} else {
			// 定长则直接返回
			return ByteBuffer.wrap(bytes);
		}
	}

	/**
	 * 将byte数组转换为int
	 * 
	 * @param bytes
	 *            待转换的字节数组
	 * 
	 * @return 转换后的int值
	 * 
	 * @throws CodecException
	 *             数组长度有误，必须为(1,2,4)
	 */
	public static int bytesToInt(final byte[] bytes) throws CodecException {
		if (bytes.length != 1 && bytes.length != 2 && bytes.length != 4) {
			throw new CodecException("INT类型的byte数组长度必须为1,2,4中的一个");
		}
		int n = 0;
		for (int i = 0; i < bytes.length; i++) {
			n <<= 8;
			n |= (bytes[i] & 0xff);
		}
		return n;
	}

	/**
	 * 根据银联规范格式化MAB
	 * <ul>
	 * <li>所有小写字母转换成大写字母</li>
	 * <li>除了字母、数字、空格、逗号、点号以外的字符都删去</li>
	 * <li>删去所有域的起始空格和结尾空格</li>
	 * <li>多余一个的连续空格，由一个空格代替</li>
	 * </ul>
	 * 
	 * @param data
	 *            带有参与MAC预算的域数据的缓冲区，每个参与运算的域之间添加一个字符空格
	 * 
	 * @return 格式化后的MAB
	 */
	public static ByteBuffer dataToMAB(ByteBuffer data) {
		ByteBuffer result = ByteBuffer.allocate(data.limit());
		char previousChar = 0x00;
		char c;
		while (data.hasRemaining()) {
			c = data.getChar();
			if (c == ' ' && previousChar == ' ') {
				// 删去所有域的起始空格和结尾空格，多余一个的连续空格，由一个空格代替
				continue;
			} else if (c >= 'a' && c <= 'z') {
				// 所有小写字母转换成大写字母
				c -= 0x20;
			} else if ((c < 'A' || c > 'Z') && (c < '0' || c > '9') && c != ' ' && c != ',' && c != '.') {
				// 除了字母、数字、空格、逗号、点号以外的字符都删去
				continue;
			}
			result.putChar(c);
			previousChar = c;
		}
		// 写入完毕，翻回
		result.flip();
		return result;
	}

	/**
	 * 将数字转换为BCD码
	 * 
	 * @param num
	 *            数字
	 * @param length
	 *            BCD码的长度
	 * 
	 * @return BCD码，不足长度的左补0
	 * 
	 * @throws DecoderException
	 *             长度为奇数时抛出此异常
	 */
	public static byte[] intToBCD(int num, int length) throws DecoderException {
		String strLen = String.format("%0" + length + "d", num);
		byte[] result = Hex.decodeHex(strLen.toCharArray());
		return result;
	}
	
}
