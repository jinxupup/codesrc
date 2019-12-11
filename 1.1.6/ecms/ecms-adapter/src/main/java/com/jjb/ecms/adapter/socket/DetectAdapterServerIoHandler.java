package com.jjb.ecms.adapter.socket;

import java.io.IOException;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.ecms.adapter.socket.process.SwitchProcess;
import com.jjb.ecms.adapter.utils.DOMXMLReader;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.service.YakMessage;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 接受top-card送来的xml格式报文,调用cas征审系统服务
 * @author JYData-R&D-Big H.N
 * @date 2015年12月8日 下午1:37:30
 * @version V1.0
 */
public class DetectAdapterServerIoHandler extends IoHandlerAdapter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Logger logTrade = LoggerFactory.getLogger("trade.logger");

	@Autowired
	private SocketServerProcessUtils sspUtils;
	@Autowired
	private SwitchProcess switchProcess;

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		if(cause instanceof IOException){
			logger.info("处理请求出现IO异常："+session,cause);
			session.close();
		}else if(cause instanceof ProtocolDecoderException){
			logger.info("出现编码与解码异常："+session,cause);
			session.close();
		}else{
			logger.info("出现其他异常："+session,cause);
			session.close();
		}
	}
	@Override
	public void messageReceived(IoSession session, Object msg) throws Exception {

		Long start = System.currentTimeMillis();
		logger.debug("PID["+start+"]....收到消息，开始执行messageReceived方法");
		YakMessage yakMessage = (YakMessage) msg;
		String xmlContent = new String(yakMessage.getRawMessage(), "UTF-8");
		logTrade.info("PID["+start+"]....==服务器收到消息==>" + xmlContent);
		//交易的规范类型
		String transSpecType = "";
		//审批自定义请求规范与响应
		Element appHead = null;
		ResponseType res = new ResponseType();
		//ESB-请求与响应
		Element esbSysHead = null;
		
		//请求交易代码
		String serviceId = "";
		//响应报文
		String respStr = "";
		Element root = null;
		try {
			// 初始化返回报文
			res = sspUtils.initResp(res);
			Document document = DocumentHelper.parseText(xmlContent);
			root = document.getRootElement();//根节点
			appHead = root.element("ServiceHeader");
			esbSysHead = root.element("SYS_HEAD");
			//审批系统自定义规范报文
			if(appHead!=null && esbSysHead==null) {
				transSpecType = "ecms";
				Element body = root.element("ServiceBody");
				Element reqEle = null;
				if(body!=null) {
					reqEle = body.element("Request");
				}
				// //解析请求报文
				serviceId = DOMXMLReader.getElementValue(appHead,"ServiceId");
				if(StringUtils.isBlank(serviceId)){
					throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES);
				}
				String org = DOMXMLReader.getElementValue(appHead,"Org").trim();
				if(StringUtils.isBlank(org)){
					org = OrganizationContextHolder.getOrg();
				}
				OrganizationContextHolder.setOrg(org);
				logger.info("PID["+start+"]....调用["+org+"]接口["+serviceId+"]服务开始。。。。。");
				//根据serviceId选择交易渠道
				respStr = switchProcess.process(serviceId, start, appHead, reqEle, res);
			}else if (appHead==null && esbSysHead!=null) {//ESB规范的请求通道
				transSpecType = "esb";
				// 初始化返回报文
				res = sspUtils.initEsbResp(res);
				// //解析请求报文
				serviceId = DOMXMLReader.getElementValue(esbSysHead,"ServiceCode");
				if(StringUtils.isBlank(serviceId)){
					throw new ProcessException(SysConstants.ERR_ESB_S002_CODE, SysConstants.ERR_ESB_S002_MES);
				}
				String serviceScene = DOMXMLReader.getElementValue(esbSysHead,"ServiceScene");
				serviceId = serviceId+serviceScene;
				OrganizationContextHolder.setOrg(SysConstants.ORG);
				logger.info("PID["+start+"]....调用接口["+serviceId+"]服务开始。。。。。");
				//根据serviceId选择交易渠道
				respStr = switchProcess.process(serviceId, start, root, null, res);
			}
		} catch (Exception e) {
			//处理异常
			if(StringUtils.equals(transSpecType, "esb")) {
				sspUtils.processEsbSpecExceptions(start, res, e);
			}else {
				sspUtils.processExceptions(start, res, e);
			}
		} finally {
			if(StringUtils.isEmpty(respStr) && !StringUtils.equals(transSpecType, "esb")) {
				StringBuffer respEnty = new StringBuffer();
				respEnty.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
				respEnty.append("<Service>").append("\n");
				//设置响应报文头信息
				sspUtils.setServiceHeader(appHead, res, respEnty);
				respEnty.append("<ServiceBody>");
				respEnty.append("<Response>");
				
				respEnty.append("</Response>");
				respEnty.append("</ServiceBody>").append("\n");
				respEnty.append("</Service>");
				respStr = respEnty.toString();
			}else if(StringUtils.isEmpty(respStr) && StringUtils.equals(transSpecType, "esb")){
				StringBuffer respEnty = new StringBuffer();
				respEnty.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
				respEnty.append("<service>").append("\n");
				//设置响应报文头信息
				sspUtils.setEsbRespHeader(root, res, respEnty);
				respEnty.append("<BODY>");
				
				respEnty.append("</BODY>").append("\n");
				respEnty.append("</service>");
				respStr = respEnty.toString();
			}
			session.write(respStr+"\n");
			logger.info("PID["+start+"]....[{}]响应报文拼接结束,交易耗时["+(System.currentTimeMillis()-start)+"]",serviceId);
		}
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		logger.info("会话超时，关闭当前网络连接");
		session.closeNow();
	}
}
