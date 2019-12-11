package com.jjb.unicorn.socket.codec;

import java.nio.ByteBuffer;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * 描述：xml encoder
 * @author W.G
 *
 */
public class XmlEncoderFilter implements YakEncodeFilter {
	
	@Autowired
	private CodeMarkUtils codeMarkUtils;
	
	private String charset;

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer,
			YakMessage message, MessageDefinition msgHeaddef, MessageDefinition msgdef,
			ProtocolEncoderOutput out, int txnMode) throws Exception {
		// 得到对象的编码结果
		ByteBuffer temp = null;
		String reportType = message.getUnifiedHeadAttributes().get(YakMsgConstants.REPORT_TYPE_INDEX);//报文类型
		if(txnMode == YakMsgConstants.TXN_MODE_XML || 
				(txnMode == YakMsgConstants.TXN_MODE_UNIFIED && YakMsgConstants.REPORT_TYPE_XML.equals(reportType))){
			String mes = (String)message.getCustomAttributes().get(YakMsgConstants.MESSAGE_KEY);
			
			if(txnMode == YakMsgConstants.TXN_MODE_UNIFIED){
				temp = CodecUtil.lvBytes(mes.getBytes(msgdef.getEncoding()), LengthType.STRING, 0);
			}else{
				temp = CodecUtil.lvBytes(mes.getBytes(StringUtils.isNotBlank(charset) ? charset : YakMsgConstants.CHARSET_NAME_UTF8), LengthType.STRING, 0);
			}
			
			// 指针移动到缓冲区末尾
			buffer.position(buffer.limit());
			// 填入解码结果
			buffer.put(temp);
			// 写入完成、翻回
			buffer.flip();
					
		}
		return true;
	}

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer,
			YakMessage message, MessageDefinition msgdef,
			ProtocolEncoderOutput out) throws Exception {
		return true;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
}
