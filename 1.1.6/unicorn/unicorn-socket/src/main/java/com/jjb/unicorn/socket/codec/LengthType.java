package com.jjb.unicorn.socket.codec;

import org.apache.commons.codec.DecoderException;

import com.jjb.unicorn.socket.codec.exception.CodecException;

/**
 * <p>Title: LengthType.java</p>
 * <p>Description: 根据长度枚举，计算子域长度(枚举策略)</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @author LI.H
 * @date 2015年10月12日
 * @version 1.0
 */
public enum LengthType {
	/**
	 * bit数字型 
	 */
	INT() {
		
		@Override
		public int getValueLengthByType(byte[] lengthBytes) throws IllegalArgumentException, CodecException {
			return CodecUtil.bytesToInt(lengthBytes);
		}
		
		@Override
		public byte[] getBytesByLength(int lengthSize, int value) throws CodecException {
			if (lengthSize != 1 && lengthSize != 2 && lengthSize != 4) {
				throw new CodecException("INT类型的长度容量必须为1,2,4中的一个");
			}
			// 此种类型编码默认取最大长度
			byte[] result = new byte[4];  
			result[0] = (byte)((value >>> 24) & 0xff);
			result[1] = (byte)((value >>> 16)& 0xff );  
			result[2] = (byte)((value >>> 8) & 0xff );  
			result[3] = (byte)((value >>> 0) & 0xff );
			byte[] tempBytes = new byte[lengthSize];
			for (int i = tempBytes.length - 1; i >= 0; i--) {
				tempBytes[i] = result[4 - (tempBytes.length - i)];
			}
			return tempBytes;
		}
	},
	
	/**
	 * 字符数字型 
	 */
	STRING() {
		
		@Override
		public int getValueLengthByType(byte[] lengthBytes) throws IllegalArgumentException {
			return Integer.parseInt(new String(lengthBytes));
		}
		
		@Override
		public byte[] getBytesByLength(int lengthSize, int value) throws CodecException {
			return String.format("%0" + lengthSize + "d", value).getBytes();
		}
	},
	
	/**
	 * bcd压缩码 
	 */
	BCD() {
		
		@Override
		public int getValueLengthByType(byte[] lengthBytes) throws IllegalArgumentException {
			throw new IllegalArgumentException("不支持的长度类型[" + this.toString() + "]");
		}
		
		@Override
		public byte[] getBytesByLength(int lengthSize, int value) throws CodecException {
			try {
				return CodecUtil.intToBCD(value, lengthSize);
			} catch (DecoderException e) {
				throw new CodecException("BCD类型的长度容量必须为偶数", e);
			}
		}
	},
	
	/**
	 * 4 bit BCD
	 */
	FOURBCD() {
		
		@Override
		public int getValueLengthByType(byte[] lengthBytes) throws IllegalArgumentException {
			throw new IllegalArgumentException("不支持的长度类型[" + this.toString() + "]");
		}
		
		@Override
		public byte[] getBytesByLength(int lengthSize, int value) throws CodecException {
			return STRING.getBytesByLength(lengthSize, value);
		}
	};
	
	/**
	 * <p>依照LV格式，根据L的type的定义获取长度</p>
	 * @param lengthBytes
	 * @return
	 */
	public abstract int getValueLengthByType(byte[] lengthBytes) throws IllegalArgumentException, CodecException;
	
	/**
	 * <p>根据value计算length</p>
	 * @param lengthSize 字段长度所占字节数，0为定长
	 * @param value 值
	 * @return
	 * @throws CodecException
	 */
	public abstract byte[] getBytesByLength(int lengthSize, int value) throws CodecException;
	
}
