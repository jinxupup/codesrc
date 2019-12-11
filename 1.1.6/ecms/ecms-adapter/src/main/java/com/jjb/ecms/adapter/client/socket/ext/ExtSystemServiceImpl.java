package com.jjb.ecms.adapter.client.socket.ext;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.adapter.client.socket.SendSocketSupport;
import com.jjb.ecms.adapter.utils.DOMXMLReader;
import com.jjb.ecms.service.api.ExtSystemService;
import com.jjb.ecms.service.dto.Trans90020.Trans90020Req;
import com.jjb.ecms.service.dto.Trans90020.Trans90020Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 发送请求至外部(行内)系统
 * 
 * @Description:
 * @author JYData-R&D-Big H.N
 * @date 2018年7月24日 上午11:42:39
 * @version V1.0
 */
@Service("extSystemServiceImpl")
public class ExtSystemServiceImpl implements ExtSystemService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SendSocketSupport sendSocketSupport;

	/**
	 * 
	 * @param req
	 * @return
	 * @throws ProcessException
	 */
	@Override
	public Trans90020Resp s90020(Trans90020Req req) throws ProcessException {

		Trans90020Resp resp = new Trans90020Resp();
		String servId = Trans90020Req.servId;
		if (req == null || StringUtils.isEmpty(req.getReqXml())) {
			logger.error("交易[" + servId + "-申请行内客户号]请求参数为空");
			throw new ProcessException("交易[" + servId + "-申请行内客户号]请求参数为空");
		}
		long tokenId = System.currentTimeMillis();
		if (req.getTokenId() == null) {
			req.setTokenId(tokenId + "");
		}
		logger.info("PID[" + req.getTokenId() + "]..." + servId + "-申请件[" + req.getAppNo() + "]申请行内客户号-开始...");
		try {
			// 封装请求接口
			String reqXml = req.getReqXml();
			// 调用行内服务
			String result = sendSocketSupport.sendSocketMsg(servId, req.getExtHost(), req.getExtPort(), reqXml,
					req.getCharset(), req.getConnectTimeOut(), req.getLvMsgLength(), req.getOrg(), null);
			if (StringUtils.isBlank(result)) {
				logger.error("未收到响应报文：{" + result + "}");
				throw new ProcessException("联机申请行内客户号结束，但未收到渠道端返回报文");
			}
			if (logger.isDebugEnabled()) {
				logger.debug("应答报文长度为[{}]", (StringUtils.isBlank(result) ? "0" : result.length()));
			}
			logger.info("交易[" + servId + "-申请行内客户号]响应报文："+result);
			resp = parse90020ResponseXML(result,servId, req.getTokenId());
			if(resp!=null) {
				logger.info("PID["+req.getTokenId()+"]..."+servId+"-申请件["+req.getAppNo()+"]返回交易状态["+resp.getStatus()+"]");
				logger.info("PID["+req.getTokenId()+"]..."+servId+"-申请件["+req.getAppNo()+"]返回系统代码["+resp.getReturnCode()+"]");
				logger.info("PID["+req.getTokenId()+"]..."+servId+"-申请件["+req.getAppNo()+"]返回系统消息["+resp.getReturnMsg()+"]");
				logger.info("PID["+req.getTokenId()+"]..."+servId+"-申请件["+req.getAppNo()+"]返回业务代码["+resp.getBusReturnCode()+"]");
				logger.info("PID["+req.getTokenId()+"]..."+servId+"-申请件["+req.getAppNo()+"]返回行内客户号["+resp.getBankCustomerId()+"]");
				if(StringUtils.equals(resp.getStatus(), "F")) {
					String msg = "行内返回客户号为空!";
					if(StringUtils.isNotEmpty(resp.getReturnCode()) || StringUtils.isNotEmpty(resp.getReturnMsg())) {
						msg = resp.getReturnCode()+"-"+resp.getReturnMsg();
					}
					throw new ProcessException(msg);
				}
				if(StringUtils.isEmpty(resp.getBankCustomerId())) {
					throw new ProcessException("交易成功，行内返回客户号为空!");
				}
			}else {
				throw new ProcessException("行内返回客户为空!");
			}
			
		} catch (Exception e) {
			logger.error("交易[" + servId + "-申请行内客户号]异常{}", e);
			throw new ProcessException(e.getMessage());
		} finally {
			logger.info("PID[" + req.getTokenId() + "]..." + servId + "-申请件[" + req.getAppNo() + "]申请行内客户号-结束,耗时["
					+ (System.currentTimeMillis() - tokenId) + "]...");
		}
		return resp;
	}
	/**
	 * 解析信用卡前置返回的报文
	 * @param resp
	 * @param result
	 * @param servId
	 * @param tokenId
	 * @return
	 * @throws Exception
	 */
	public Trans90020Resp parse90020ResponseXML(String result,String servId, String tokenId) throws Exception  {
		Trans90020Resp resp = null;
		if(StringUtils.isNotBlank(result) && result.length() > 0){
			try {
				String rs = result;
				Document document = DocumentHelper.parseText(rs);
				Element root = document.getRootElement();//根节点
				Element headEl = root.element("SERVICE_HEADER");
				resp = new Trans90020Resp();
				if(headEl!=null) {
					Element headRespEl = headEl.element("SERV_RESPONSE");
					String serviceSn = DOMXMLReader.getElementValue(headEl, "SERVICE_SN");
					String requstTime = DOMXMLReader.getElementValue(headEl, "REQUST_TIME");
					String resServiceSn = DOMXMLReader.getElementValue(headEl, "RES_SERVICE_SN");
					String resServiceTime = DOMXMLReader.getElementValue(headEl, "RES_SERVICE_TIME");
					resp.setServiceSn(serviceSn);
					resp.setRequstTime(requstTime);
					resp.setResServiceSn(resServiceSn);
					resp.setResServiceTime(resServiceTime);
					if(headRespEl!=null){
						String status = DOMXMLReader.getElementValue(headRespEl, "STATUS");
						String code = DOMXMLReader.getElementValue(headRespEl, "CODE");
						String desc = DOMXMLReader.getElementValue(headRespEl, "DESC");
						resp.setStatus(status);
						resp.setReturnCode(code);
						resp.setReturnMsg(desc);
					}
				}
				Element bodyEl = root.element("SERVICE_BODY");
				if(bodyEl!=null) {
					Element bodyReslEl = bodyEl.element("RESPONSE");
					String bankCustomerId = DOMXMLReader.getElementValue(bodyReslEl, "BANK_CUSTOMER_ID");
					String returnCode = DOMXMLReader.getElementValue(bodyReslEl, "RETURN_CODE");
					String respDate = DOMXMLReader.getElementValue(bodyReslEl, "RESP_DATE");
					resp.setBankCustomerId(bankCustomerId);
					resp.setBusReturnCode(returnCode);
					resp.setBusRespDate(respDate);
				}
			} catch (DocumentException e) {
				logger.error("PID-["+tokenId+"]解析接收的交易["+servId+"]报文["+result+"]发生异常",e);
				throw new ProcessException("解析接收的报文发生异常");
			}
		}
	   
	   return resp;
	}
}
