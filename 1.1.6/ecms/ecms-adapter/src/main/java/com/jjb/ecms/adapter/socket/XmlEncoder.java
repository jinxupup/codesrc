package com.jjb.ecms.adapter.socket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.unicorn.socket.codec.CodecUtil;
import com.jjb.unicorn.socket.codec.LengthType;

/**
 * @Description: xml encoder
 * @author JYData-R&D-Big H.N
 * @date 2016年3月2日 下午4:20:40
 * @version V1.0
 */
public class XmlEncoder implements ProtocolEncoder {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private LengthType lengthType = LengthType.INT;

	private int lengthSize = 8;

	/**
	 * @return 报文编入长度的类型{@link LengthType}，默认为{@link LengthType #INT}
	 */
	public LengthType getLengthType() {
		return lengthType;
	}

	/**
	 * @param lengthType
	 *            报文编入长度的类型{@link LengthType}，默认为{@link LengthType #INT}
	 */
	public void setLengthType(LengthType lengthType) {
		this.lengthType = lengthType;
	}

	/**
	 * @return 报文编入长度的容量，默认为4
	 */
	public int getLengthSize() {
		return lengthSize;
	}

	/**
	 * @param lengthSize
	 *            报文编入长度的容量，默认为4
	 */
	public void setLengthSize(int lengthSize) {
		this.lengthSize = lengthSize;
	}

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		String mes = (String) message;
		IoBuffer buffer = IoBuffer.allocate(1024 * 1024 * 10, false);
		buffer.setAutoExpand(true);
		buffer.flip();

		buffer.put(CodecUtil.lvBytes(mes.getBytes("UTF-8"), lengthType,
				lengthSize));
		// 缓冲区写入完毕，翻回，为读取做准备
		buffer.flip();
//		if (logger.isDebugEnabled()) {
//			byte[] log = new byte[buffer.limit()];
//			buffer.get(log);
//			buffer.rewind();
//			logger.debug("发送的报文[" + new String(Hex.encodeHex(log)) + "]");
//		}
		out.write(buffer);

	}

	@Override
	public void dispose(IoSession session) throws Exception {
		
	}

}
