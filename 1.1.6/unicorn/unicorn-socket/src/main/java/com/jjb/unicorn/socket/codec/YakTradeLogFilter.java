package com.jjb.unicorn.socket.codec;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.TradeLogHandler;
import com.jjb.unicorn.socket.definition.MessageDefinition;

/**
 * <p>Description: ISO-8583报文交易日志监控处理类</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: YakTradeLogFilter
 * @author LI.H
 * @date 2016年3月14日 下午1:39:13
 * @version 1.0
 */
public class YakTradeLogFilter implements YakDecodeFilter, YakEncodeFilter {

	private TradeLogHandler handler;
	
	/**
	 * 判断交易方向
	 * @param mti
	 * @return true为请求，false为响应
	 */
	private static boolean checkTranDirection(String mti) {
		boolean direction = true;
		if(mti != null && mti.length() > 3 && mti.substring(2, 3).matches("\\d")) {
			int value = Integer.parseInt(mti.substring(2, 3));
			if(value % 2 == 1) {
				direction = false;
			}
		}
		
		return direction;
	}
	
	/**
	 * 针对行内前置的特殊处理，仅考虑8583报文交易
	 * @param message
	 * @param txnMode
	 * @return
	 */
	private boolean isBank8583Trade(YakMessage message, int txnMode) {
		String reportType = message.getUnifiedHeadAttributes().get(YakMsgConstants.REPORT_TYPE_INDEX);//报文类型
		if(txnMode == YakMsgConstants.TXN_MODE_8583 || 
				(txnMode == YakMsgConstants.TXN_MODE_UNIFIED && YakMsgConstants.REPORT_TYPE_8583.equals(reportType))){
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer, YakMessage message, MessageDefinition msgdef, ProtocolEncoderOutput out) throws Exception {
		handler.simpleYakMessageTradeLog(message, checkTranDirection(handler.getMTI(message)), false);
		return true;
	}

	@Override
	public boolean doEncode(IoSession session, IoBuffer buffer, YakMessage message, MessageDefinition msgHeaddef, MessageDefinition msgdef, ProtocolEncoderOutput out, int txnMode) throws Exception {
		if(isBank8583Trade(message, txnMode)){
			handler.simpleYakMessageTradeLog(message, checkTranDirection(handler.getMTI(message)), false);
		}
		
		return true;
	}

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer, YakMessage message, MessageDefinition msgDef, ProtocolDecoderOutput out) throws Exception {
		handler.simpleYakMessageTradeLog(message, checkTranDirection(handler.getMTI(message)), true);
		return true;
	}

	@Override
	public boolean doDecode(IoSession session, IoBuffer buffer, YakMessage message, MessageDefinition msgHeaddef, MessageDefinition msgdef, ProtocolDecoderOutput out, int txnMode) throws Exception {
		if(isBank8583Trade(message, txnMode)){
			handler.simpleYakMessageTradeLog(message, checkTranDirection(handler.getMTI(message)), true);
		}
		
		return true;
	}

	public void setHandler(TradeLogHandler handler) {
		this.handler = handler;
	}
	
}
