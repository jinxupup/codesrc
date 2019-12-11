package com.jjb.unicorn.socket.codec;

import org.apache.commons.codec.binary.Hex;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.CodeMarkUtils;

/**
 * <p>Title: LVMessageDecoder.java</p>
 * <p>Description: lv格式协议解码器，lv格式为报文长度(length)+报文内容(value)</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @author LI.H
 * @date 2015年10月19日
 * @version 1.0
 */
public class LVMessageDecoder extends CumulativeProtocolDecoder {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private YakDecodeFilterChain filterChain;

	private LengthType lengthType = LengthType.INT;

	private int lengthSize = 4;
	
	private int lengthSuffix = 0;
	
	@Autowired
	private CodeMarkUtils codeMarkUtils;

	/**
	 * @see YakDecodeFilterChain
	 * @return 解码过滤器链
	 */
	public YakDecodeFilterChain getFilterChain() {
		return filterChain;
	}

	/**
	 * @see YakDecodeFilterChain
	 * @param filterChain 解码过滤器链
	 */
	@Required
	public void setFilterChain(YakDecodeFilterChain filterChain) {
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
	protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {
		// 得到报文长度
		in.mark();
		if (in.remaining() < lengthSize + lengthSuffix) {
			return false;
		}
		byte[] lengthBytes = new byte[lengthSize + lengthSuffix];
		in.get(lengthBytes);
		int length;
		// 根据不同长度类型解码长度
		boolean dataAvailable;
		switch (lengthType) {
		case INT:
			//visa长度为有效位，但是表示为前两个
			byte[] visaLengthBytes = new byte[lengthSize];
			for (int i = 0; i < lengthSize; i++) {
				visaLengthBytes[i] = lengthBytes[i];
			}
			length = CodecUtil.bytesToInt(visaLengthBytes);
			break;
		case STRING:
			length = Integer.parseInt(new String(lengthBytes));
			break;
		default:
			throw new IllegalArgumentException("不支持的长度类型[" + lengthType + "]");
		}
		// 判断缓冲区是否有足够的字节数
		dataAvailable = in.remaining() >= length;
		in.reset();
		if (dataAvailable) {
			if (logger.isDebugEnabled()) {
				byte[] log = new byte[length + lengthSize + lengthSuffix];
				in.get(log);
				in.reset();
				logger.debug("接收的报文[{}]", codeMarkUtils.makeMask(new String(Hex.encodeHex(log))));
			}
			// 得到报文长度
			in.get(lengthBytes);
			// 记录报文内容起始位置
			in.mark();
			// 得到报文内容
			byte[] rawMessage = new byte[length];
			in.get(rawMessage);
			// 构造报文对象
			YakMessage message = new YakMessage();
			message.setRawMessage(rawMessage);
			// 缓存指针重置到报文内容起点
//			in.reset();
			// 执行解码过滤器链
			try {
				IoBuffer buf = IoBuffer.allocate(rawMessage.length, false);
				buf.mark();//缓存指针重置到报文内容起点
				buf.put(rawMessage);
				buf.reset();//回到mark标记的起始点
				message = filterChain.doDecodeFilterChain(session, buf, message, out);
				
				if(buf.hasRemaining()) {
					//最后一个字节'|',则通过;否则,报错
					if(buf.remaining() == 1 ){
						byte remainingByte = buf.get();
						if(remainingByte != (byte)0x7C){
							logger.error("接收的报文格式错误，解析后有剩余数据，此报文做丢弃处理");
							throw new Exception("接收的报文格式错误，解析后有剩余数据，此报文做丢弃处理");
						}
					}else{
						logger.error("接收的报文格式错误，解析后有剩余数据，此报文做丢弃处理");
						throw new Exception("接收的报文格式错误，解析后有剩余数据，此报文做丢弃处理");
					}
				}
				
				if (message != null) {
					out.write(message);
				}
			} catch (Exception e) {
				//logger.error("接收的报文格式错误，此报文做丢弃处理.....",e);
				return true;
			}
			
			return true;
		}
		// 缓冲区字节数不足，返回false
		return false;
	}

}
