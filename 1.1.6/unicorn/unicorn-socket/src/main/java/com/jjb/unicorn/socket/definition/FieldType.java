package com.jjb.unicorn.socket.definition;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.socket.codec.AsciiEbcdicCodecUtil;
import com.jjb.unicorn.socket.codec.CodecUtil;
import com.jjb.unicorn.socket.codec.DecodeHandler;
import com.jjb.unicorn.socket.codec.EncodeHandler;
import com.jjb.unicorn.socket.codec.exception.CodecException;

/**
 * <p>Description: 金融交易报文域value类型(枚举策略)</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: FieldType
 * @author BIG.CPU 
 * @author LI.H
 * @date 2015年12月7日 下午7:46:24
 * @version 1.1
 */
public enum FieldType {
	
	/**
	 * 字符表示的数字 
	 */
	n {
		@Override
		public String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler) {
			return s.decodeField(message, charset, buffer, length, index, decodeHandler);
		}
		
		@Override
		public byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler) {
			byte[] tempBytes = null;
			try {
				tempBytes = (tempValue == null ? new byte[0] : (tempValue).getBytes(encoding));
			} catch (UnsupportedEncodingException e) {
				// 忽略
			}
			// 定长数字类型左补零
			if (fieldDefinition.getLengthSize() == 0 && needPadding) {
				tempBytes = encodeHandler.leftPad(tempBytes, fieldDefinition.getContentMaxSize(), (byte) '0');
			}
			return tempBytes;
		}
	},
	
	/**
	 * 字符串
	 */
	s {
		@Override
		public String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler) {
			return charset.decode(ByteBuffer.wrap(getValueByBuffer(buffer, length))).toString();
		}
		
		@Override
		public byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler) {
			byte[] tempBytes = null;
			try {
				tempBytes = (tempValue == null ? new byte[0] : (tempValue).getBytes(encoding));
			} catch (UnsupportedEncodingException e) {
				// 忽略
			}
			// 定长字符类型右补空格
			if (fieldDefinition.getLengthSize() == 0 && needPadding) {
				tempBytes = encodeHandler.rightPad(tempBytes, fieldDefinition.getContentMaxSize(), (byte) ' ');
			}
			return tempBytes;
		}
	},
	
	/**
	 * BCD压缩码
	 */
	cn {
		@Override
		public String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler) {
			return b.decodeField(message, charset, buffer, length, index, decodeHandler);
		}
		
		@Override
		public byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler) {
			byte[] tempBytes = null;
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
				tempBytes = encodeHandler.leftPad(tempBytes, fieldDefinition.getContentMaxSize(), (byte) '0');
			}
			return tempBytes;
		}
	},
	
	/**
	 * 二进制字符表示
	 */
	b {
		@Override
		public String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler) {
			return new String(Hex.encodeHex(getValueByBuffer(buffer, length)));
		}
		
		@Override
		public byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler) {
			byte[] tempBytes = null;
			try {
				tempBytes = (tempValue == null ? new byte[0] : Hex.decodeHex(tempValue.toCharArray()));
			} catch (DecoderException e) {
				
			}
			// 定长字节类型右补空格
			if (fieldDefinition.getLengthSize() == 0 && needPadding) {
				tempBytes = encodeHandler.rightPad(tempBytes, fieldDefinition.getContentMaxSize(), (byte) ' ');
			}
			return tempBytes;
		}
	},
	
	/**
	 * <p>二进制byte数组</p>
	 */
	bd {
	
		/**
		 * <p>需要把该byte数组中的二进制bit转换为对应十进制数字</p>
		 */
		@Override
		public String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler) {
			String result;
			try {
				result = Integer.toString(CodecUtil.bytesToInt(getValueByBuffer(buffer, length)));
			} catch (CodecException e) {
				result = "";
			}
			return result;
		}
		
		@Override
		public byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler) {
			// 此种类型编码默认取最大长度
			int value = Integer.valueOf(tempValue);
			byte[] result = new byte[4];  
			result[0] = (byte)((value >>> 24) & 0xff);
			result[1] = (byte)((value >>> 16)& 0xff );  
			result[2] = (byte)((value >>> 8) & 0xff );  
			result[3] = (byte)((value >>> 0) & 0xff );
			byte[] tempBytes = new byte[fieldDefinition.getContentMaxSize()];
			for (int i = tempBytes.length - 1; i >= 0; i--) {
				tempBytes[i] = result[4 - (tempBytes.length - i)];
			}
			return tempBytes;
		}
	},
	
	/**
	 * 4 bit BCD
	 */
	fb {
		/**
		 * <p>对于4 bit BCD两个数字占一个byte，所以对于奇数位要前补4 bit 0，对于VISA61域需要极其特殊的判断</p>
		 * <p>60域先全部转换为String由后置处理器处理</p>
		 */
		@Override
		public String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler) {
			if (60 == index) {
				return new String(Hex.encodeHex(getValueByBuffer(buffer, length)));
			} else {
				String result = new String(Hex.encodeHex(getValueByBuffer(buffer, length + 1 >> 1)));
				return length % 2 == 1 ? result.substring(1) : result;
			}
		}
		
		@Override
		public byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler) {
			byte[] tempBytes = null;
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
					tempBytes = encodeHandler.leftPad(tempBytes, (fieldDefinition.getContentMaxSize() + 1) >> 1, (byte) '0');
			}
			
			return tempBytes;
		}
		
	},
	
	/**
	 * EBCDIC编码
	 */
	e {
		@Override
		public String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler) {
			//把EBCDIC的码全部映射为ASCII再全部转换为String
			return charset.decode(ByteBuffer.wrap(AsciiEbcdicCodecUtil.EBCDICToASCII(getValueByBuffer(buffer, length)))).toString();
		}
		
		@Override
		public byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler) {
			byte[] tempBytes = null;
			try {
				tempBytes = (tempValue == null ? new byte[0] : (tempValue).getBytes(encoding));
			} catch (UnsupportedEncodingException e) {
				tempBytes = (tempValue == null ? new byte[0] : (tempValue).getBytes());
			}
			
			//由内部ASCII转换为EBCDIC
			tempBytes = AsciiEbcdicCodecUtil.ASCIIToEBCDIC(tempBytes);
			if (fieldDefinition.getLengthSize() == 0 && needPadding) {
				tempBytes = encodeHandler.rightPad(tempBytes, fieldDefinition.getContentMaxSize(), (byte) ' ');
			}
			return tempBytes;
		}
		
	},
	
	
	/**
	 * <p>BipMap格式子域</p>
	 */
	bm {
		@Override
		public String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler) {
			if (!message.getExtBodyAttributes().containsKey(index)) {
				message.getExtBodyAttributes().put(index, new HashMap<String, String>());
			}
			return decodeHandler.decodeExtFieldByBitMap(message, ByteBuffer.wrap(getValueByBuffer(buffer, length)), index);
		}

		@Override
		public byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler) {
			return encodeHandler.encodeExtFieldByBitMap(message, index);
		}
	},
	
	/**
	 * <p>定长格式子域</p>
	 */
	fl {
		@Override
		public String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler) {
			if (!message.getExtBodyAttributes().containsKey(index)) {
				message.getExtBodyAttributes().put(index, new HashMap<String, String>());
			}
			return decodeHandler.decodeExtFieldByFixedLength(message, ByteBuffer.wrap(getValueByBuffer(buffer, length)), index);
		}

		@Override
		public byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler) {
			return encodeHandler.encodeExtFieldByFixedLength(message, index);
		}
	},
	
	/**
	 * <p>TLV格式子域</p>
	 */
	tlv {
		@Override
		public String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler) {
			if (!message.getExtBodyAttributes().containsKey(index)) {
				message.getExtBodyAttributes().put(index, new HashMap<String, String>());
			}
			return decodeHandler.decodeExtFieldByTLV(message, ByteBuffer.wrap(getValueByBuffer(buffer, length)), index);
		}

		@Override
		public byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler) {
			return encodeHandler.encodeExtFieldByTlv(message, index);
		}
	};
	
	/**
	 * <p>根据类型解码</p>
	 * @param charset
	 * @param buffer
	 * @param length
	 * @return
	 */
	public abstract String decodeField(YakMessage message, Charset charset, ByteBuffer buffer, int length, int index, DecodeHandler decodeHandler);
	
	/**
	 * <p>根据类型编码</p>
	 * @param charset
	 * @param buffer
	 * @param length
	 * @return
	 */
	public abstract byte[] encodeField(FieldDefinition fieldDefinition, YakMessage message, String tempValue, String encoding, boolean needPadding, int index, EncodeHandler encodeHandler);
	
	/**
	 * <p>根据各种类型的长度从缓冲区中取值</p>
	 * @param buffer
	 * @param length
	 * @return
	 */
	public byte[] getValueByBuffer(ByteBuffer buffer, int length) {
		byte[] contentTemp = new byte[length];
		buffer.get(contentTemp);
		return contentTemp;
	}

}
