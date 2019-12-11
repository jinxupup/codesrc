package com.jjb.ecms.adapter.utils.ccif;


import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jjb.ecms.adapter.utils.XmlMessage.OnlineBasicReq;
import com.jjb.ecms.service.dto.BasicRequest;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;
//import com.jjccb.uniqueid.GUIDHelper;

/**
 * 处理信用卡综合前置报文
 * @author hp
 *
 */
public class TransUtis {
	private static Logger logger = LoggerFactory.getLogger(TransUtis.class);
	/**
	 * 综合前置请求公共报文头拼写
	 * @param req
	 * @param onlineReq
	 * @param servCode
	 * @return
	 * @throws ProcessException
	 */
	public static OnlineBasicReq convertRequest(BasicRequest req, 
			OnlineBasicReq onlineReq, String servCode,boolean isUseMAC) throws ProcessException {
        Date now = new Date();
        try {
            String sysId = StringUtils.setValue(req.getSourceSysId(), "70238");;
            String serviceCode = StringUtils.setValue(req.getServiceCode(), servCode);
            if(isUseMAC) {
            	onlineReq.sysHead.Mac =StringUtils.setValue("", "70238");
            }else {
            	onlineReq.sysHead.Mac ="";
            }
            onlineReq.sysHead.MsgId =sysId+DateUtils.dateToString(now, DateUtils.FULL_SECOND_LINE_NO2);
            onlineReq.sysHead.SourceSysId =sysId;
            onlineReq.sysHead.ConsumerId =StringUtils.setValue(req.getConsumerId(), "200630");
            onlineReq.sysHead.ServiceCode =serviceCode;
            onlineReq.sysHead.ServiceScene =StringUtils.setValue(req.getServiceScene(), "");;

            onlineReq.apphead.TranDate =DateUtils.dateToString(now, DateUtils.DAY_YMD);
            onlineReq.apphead.TranTime =DateUtils.dateToString(now, DateUtils.SECOND_LINE_NO);
            onlineReq.apphead.TranTellerNo ="888888";
            //参数使用 consumerId
            onlineReq.apphead.TranSeqNo =GUIDHelper.getGUID(StringUtils.setValue(req.getConsumerId(),"200630"));
            onlineReq.apphead.GlobalSeqNo =onlineReq.apphead.TranSeqNo;//GlobalSeqNo：与TranSeqNo送相同的值
            onlineReq.apphead.BranchId =StringUtils.setValue(req.getBranchId(), "06101");
        } catch (Exception e) {
        	logger.error("公共请求报文拼写异常", e);
        	throw new ProcessException("联机综合前置公共请求报文拼写异常");
        }
		return onlineReq;
	}
}