package com.jjb.ecms.adapter.socket.process;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.adapter.socket.ResponseType;
import com.jjb.ecms.adapter.socket.SocketServerProcessUtils;
import com.jjb.ecms.adapter.utils.DOMXMLReader;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 
 * @author hp
 *
 */
@Service
public class ProcessT1004 implements IProcess {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SocketServerProcessUtils sspUtils;

	@Override
	public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
		StringBuffer respEnty = new StringBuffer();
		// 调用服务
		String reqType = "";
		try {
			//解析请求报文
			reqType = DOMXMLReader.getElementValue(appHead,"ServiceId");
			if(StringUtils.isBlank(reqType)){
				throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES+",服务代码不能为空!");
			}
			logger.info("PID["+start+"]....调用[{}]接口服务开始。。。。。",reqType);
			
//			if (reqType.equals(AdapterConstants.T1002)) {
//
//				T1005Req reqPrim = TransBeanConvert.convertT1005Req(reqEle, res);
//				// 如果参数转换正常
//				if (res.getSTATUS().equals("S")
//						&& SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
//
//					OrganizationContextHolder.setCurrentOrg(org);
//					OrganizationContextHolder.setOrg(org);
//					t1005Resp = ipadApplyService.T1005(reqPrim);
//				}
//			} else {
//				throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES+"["+reqType+"]");
//			}
			
			logger.info("PID["+start+"]....调用[{}]接口服务结束。。。。。",reqType);
			
		} catch (Exception e) {
			//处理异常
			sspUtils.processExceptions(start, res, e);
		} finally {
			logger.info("PID["+start+"]....[{}]响应报文拼接开始。。。。。",reqType);
			respEnty.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
			respEnty.append("<Service>").append("\n");
			//设置响应报文头信息
			sspUtils.setServiceHeader(appHead, res, respEnty);
			respEnty.append("<ServiceBody>");
			respEnty.append("<Response>");
			
//			if (reqType.equals(AdapterConstants.T1002)) {	
//				respEnty.append(StringUtils.valueOf(convertToStr(t1002Resp)));
//			}
			respEnty.append("</Response>");
			respEnty.append("</ServiceBody>").append("\n");
			respEnty.append("</Service>");
			
			logger.info("PID["+start+"]....[{}]响应报文拼接结束,交易耗时["+(System.currentTimeMillis()-start)+"]",reqType);
		}
		return respEnty.toString();
	}
}
