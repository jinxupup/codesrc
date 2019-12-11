package com.jjb.ecms.adapter.utils;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.socket.codec.YakMsgConstants;

@Service
public class UnifiedMessageUtil {
	@Value("#{env['msg_version'] ?: 'v2.0.0'}")
	private String version;
	
	@Value("#{env['txnMode'] ?: 0}")
	private int txnMode;
	
	public void setYakReqUnifiedHeaderMessage(YakMessage yakMessage,String service_id,
			String org,String opUser){
		Date date = new Date();
		if(StringUtils.isEmpty(org)){
			org = OrganizationContextHolder.getOrg();
		}
		if(StringUtils.isEmpty(opUser)){
			opUser = OrganizationContextHolder.getUserNo();
		}
		if(txnMode == YakMsgConstants.TXN_MODE_UNIFIED){
			Map<Integer, String> unifiedHead = yakMessage.getUnifiedHeadAttributes();
			unifiedHead.put(1, org); //机构号
			unifiedHead.put(2, version); //版本号
			unifiedHead.put(3, "1");//交易类型
			unifiedHead.put(4, "1");//报文类型
			unifiedHead.put(5, "0");//报文方向
			unifiedHead.put(6, service_id);//交易代码
			unifiedHead.put(7, "98");//请求渠道编号-贷记卡核心
			unifiedHead.put(8, DateUtils.dateToString(date, DateUtils.DAY_YMD));//请求方业务日期
			unifiedHead.put(9, DateUtils.dateToString(date, DateUtils.FULL_SECOND_LINE_NO));//请求方系统时间
			unifiedHead.put(10, getResServiceSn(date));//请求系统流水号
			unifiedHead.put(11, "");//应答系统时间
			unifiedHead.put(12, "");//应答业务日期
			unifiedHead.put(13, "");//应答系统流水号
			unifiedHead.put(14, "");//银行分支机构号
			unifiedHead.put(15, opUser);//银行柜员号
			unifiedHead.put(YakMsgConstants.RESPONSE_CODE_INDEX, "");//应答码
			unifiedHead.put(YakMsgConstants.RESPONSE_DESC_INDEX, "");//应答信息
			unifiedHead.put(18, "");//预留
		}
	}
	private String getResServiceSn(Date date) {
		String nowdate = DateUtils.dateToString(date, DateUtils.FULL_SECOND_LINE_NO2);
		int randonstr = (int) (Math.random() * 90) + 10;
		return nowdate + String.valueOf(randonstr);
	}

}
