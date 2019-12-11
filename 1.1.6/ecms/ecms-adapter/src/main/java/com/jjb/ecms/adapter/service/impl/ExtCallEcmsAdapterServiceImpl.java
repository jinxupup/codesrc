package com.jjb.ecms.adapter.service.impl;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.adapter.socket.ResponseType;
import com.jjb.ecms.adapter.socket.SocketServerProcessUtils;
import com.jjb.ecms.adapter.socket.process.SwitchProcess;
import com.jjb.ecms.adapter.utils.DOMXMLReader;
import com.jjb.ecms.service.api.ExtCallEcmsAdapterService;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;


/**
* 发送账户查询请求
* 
* @author hh
*
*/
@Service("extCallEcmsAdapterServiceImpl")
public class ExtCallEcmsAdapterServiceImpl implements ExtCallEcmsAdapterService{
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SocketServerProcessUtils sspUtils;
	@Autowired
	private SwitchProcess switchProcess;
	/**
	 * 接收外部系统通过mq调用本系统服务
	 */
	@Override
	public String callEcmsAdapter(String xml) {


		Long start = System.currentTimeMillis();
		logger.info("PID["+start+"]....收到.MQ消息==>["+xml+"]");
		Element appHead = null;
		Element reqEle = null;
		Document document;
		ResponseType res = new ResponseType();
		// 调用服务
		String reqType = "";
		String org = null;
		String respStr = "";
		try {
			// 初始化返回报文
			res = sspUtils.initResp(res);
			document = DocumentHelper.parseText(xml);
			Element root = document.getRootElement();//根节点
			appHead = root.element("ServiceHeader");
			Element body = root.element("ServiceBody");
			if(body!=null) {
				reqEle = body.element("Request");
			}
			// //解析请求报文
			reqType = DOMXMLReader.getElementValue(appHead,"ServiceId");
			if(StringUtils.isBlank(reqType)){
				throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES+",服务代码不能为空!");
			}
			org = DOMXMLReader.getElementValue(appHead,"Org").trim();
			if(StringUtils.isBlank(org)){
				org = OrganizationContextHolder.getOrg();
			}
			OrganizationContextHolder.setOrg(org);
			OrganizationContextHolder.setOrg(org);
			logger.info("PID["+start+"]....调用["+org+"]接口["+reqType+"]服务开始。。。。。");
			//根据reqType选择交易渠道
			respStr = switchProcess.process(reqType, start, appHead, reqEle, res);

		} catch (Exception e) {
			//处理异常
			sspUtils.processExceptions(start, res, e);
		} finally {
			if(StringUtils.isEmpty(respStr)) {
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
			}
			logger.info("PID["+start+"]....交易["+reqType+"]耗时["+(System.currentTimeMillis()-start)+"]，响应报文==>["+respStr+"]");
		}
		return respStr;
	}
}
