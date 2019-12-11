package com.jjb.unicorn.socket.codec;

import java.nio.ByteBuffer;

import org.apache.commons.lang.StringUtils;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * 描述：xml decoder
 * 
 * @author W.G
 *
 */
public class XmlDecoderFilter implements YakDecodeFilter {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private int lengthSize = 4;
	
	@Autowired
	private CodeMarkUtils codeMarkUtils;
	
	private String charset;

	public void setLengthSize(int lengthSize) {
		this.lengthSize = lengthSize;
	}
	
	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer, YakMessage yakMessage,
                            MessageDefinition msgHeadDef, MessageDefinition msgDef, ProtocolDecoderOutput out, int txnMode) throws Exception {
		String reportType = yakMessage.getUnifiedHeadAttributes().get(YakMsgConstants.REPORT_TYPE_INDEX);//报文类型
		if(txnMode == YakMsgConstants.TXN_MODE_XML || 
				(txnMode == YakMsgConstants.TXN_MODE_UNIFIED && YakMsgConstants.REPORT_TYPE_XML.equals(reportType))){
			byte[] recived = yakMessage.getRawMessage();
			//xml的报文体长度
			int xmlLength = recived.length-buffer.position();
			ByteBuffer bodyBuff = ByteBuffer.allocate(xmlLength);
			bodyBuff.put(recived,buffer.position(),xmlLength);
			String bodyStr;
			if(txnMode == YakMsgConstants.TXN_MODE_UNIFIED){
				bodyStr = new String(bodyBuff.array(),msgDef.getEncoding());
			}else{
				bodyStr = new String(bodyBuff.array(),StringUtils.isNotBlank(charset) ? charset : YakMsgConstants.CHARSET_NAME_UTF8);
			}
			yakMessage.getCustomAttributes().put(YakMsgConstants.MESSAGE_KEY, bodyStr);
			
			buffer.position(recived.length);
			
//			logger.debug("接收的xml报文[" +codeMarkUtils.makeMask(bodyStr) + "]");
		}
		return true;
	}

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer,
			YakMessage yakMessage, MessageDefinition msgDef,
			ProtocolDecoderOutput out) throws Exception {
		return true;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}
	
}
