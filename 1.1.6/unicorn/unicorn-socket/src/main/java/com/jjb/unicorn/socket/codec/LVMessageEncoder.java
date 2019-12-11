package com.jjb.unicorn.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.CodeMarkUtils;

/**
 * lv格式协议编码器，lv格式为报文长度+报文内容
 * 
 * @author BIG.CPU
 * 
 */
public class LVMessageEncoder extends ProtocolEncoderAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private YakEncodeFilterChain filterChain;

	private LengthType lengthType = LengthType.INT;

	private int lengthSize = 4;

	@Autowired
	private CodeMarkUtils codeMarkUtils;
	
	private int lengthSuffix = 0;

	/**
	 * @see YakEncodeFilterChain
	 * @return 编码过滤器链
	 */
	public YakEncodeFilterChain getFilterChain() {
		return filterChain;
	}

	/**
	 * @see YakEncodeFilterChain
	 * @param filterChain 编码过滤器链
	 */
	@Required
	public void setFilterChain(YakEncodeFilterChain filterChain) {
		this.filterChain = filterChain;
	}

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

	/**
	 * @return 报文编入长度的后缀，默认为0
	 */
	public int getLengthSuffix() {
		return lengthSuffix;
	}

	/**
	 * @param lengthSize
	 *            报文编入长度的后缀，默认为0
	 */
	public void setLengthSuffix(int lengthSuffix) {
		this.lengthSuffix = lengthSuffix;
	}
	
	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		YakMessage msg = (YakMessage) message;
		// 通过编码过滤器链得到编码结果
		// FIXME 缓冲容量暂时写死
		IoBuffer buffer = IoBuffer.allocate(2048, false);
		buffer.setAutoExpand(true);
		buffer.flip();
		// 执行编码过滤器链
		buffer = filterChain.doEncodeFilterChain(session, buffer, msg, out);
		if (buffer != null) {
			// 读取解码结果，存入消息对象
			byte[] resultBytes = new byte[buffer.limit()];
			buffer.get(resultBytes);
			msg.setRawMessage(resultBytes);

			// 重置缓冲区，用LV格式包装报文内容放入缓冲
			buffer.clear();
			//组长度
			buffer.put(lengthType.getBytesByLength(lengthSize, resultBytes.length));
			//如果长度有后缀组后缀
			if (0 != lengthSuffix) {
				buffer.put(new byte[lengthSuffix]);
			}
			//组报文
			buffer.put(resultBytes);

			// 缓冲区写入完毕，翻回，为读取做准备
			buffer.flip();
			if (logger.isDebugEnabled()) {
				byte[] log = new byte[buffer.limit()];
				buffer.get(log);
				buffer.rewind();
				//logger.debug("发送的报文[" + codeMarkUtils.makeMask(new String(Hex.encodeHex(log))) + "]");
			}
			out.write(buffer);
		}
	}

}
