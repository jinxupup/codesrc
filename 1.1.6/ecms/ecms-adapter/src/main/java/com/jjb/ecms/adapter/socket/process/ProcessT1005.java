package com.jjb.ecms.adapter.socket.process;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.adapter.socket.ResponseType;
import com.jjb.ecms.adapter.socket.SocketServerProcessUtils;
import com.jjb.ecms.adapter.utils.DOMXMLReader;
import com.jjb.ecms.adapter.utils.TransBeanConvert;
import com.jjb.ecms.service.api.IPadApplyService;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.ecms.service.dto.T1005.T1005Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 
 * @author hp
 *
 */
@Service
public class ProcessT1005 implements IProcess {

	private Logger logger = LoggerFactory.getLogger(getClass());
	// 联机申请
	@Autowired
	private IPadApplyService ipadApplyService;
	@Autowired
	private SocketServerProcessUtils sspUtils;
	@Override
	public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
		T1005Resp t1005Resp = new T1005Resp();
		// 调用服务
		String reqType = "";
		StringBuffer respEnty = new StringBuffer();
		try {
			// //解析请求报文
			reqType = DOMXMLReader.getElementValue(appHead,"ServiceId");
			if(StringUtils.isBlank(reqType)){
				throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES+",服务代码不能为空!");
			}
			logger.info("PID["+start+"]....调用[{}]接口服务开始。。。。。",reqType);
			// 集中进件录入 申请件
			if (reqType.equals(T1005Req.servId)) {
				T1005Req reqPrim = TransBeanConvert.convertT1005Req(reqEle, res);
				// 如果参数转换正常
				if (res.getSTATUS().equals("S")
						&& SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
						t1005Resp = ipadApplyService.T1005(reqPrim);
				}
			}else {
				throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES+"["+reqType+"]");
			}
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
			if (reqType.equals(T1005Req.servId)) {	
				respEnty.append(StringUtils.valueOf(sspUtils.convertToStr(t1005Resp)));
			}
			respEnty.append("</Response>");
			respEnty.append("</ServiceBody>").append("\n");
			respEnty.append("</Service>");
			
		}
		return respEnty.toString();
	}
}
