package com.jjb.ecms.adapter.client.socket.ext;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.adapter.client.socket.MessageToBean;
import com.jjb.ecms.adapter.client.socket.SendSocketSupport;
import com.jjb.ecms.adapter.utils.XmlMessage.MarshallerListener;
import com.jjb.ecms.adapter.utils.XmlMessage.T2004800067Req;
import com.jjb.ecms.adapter.utils.ccif.TransUtis;
import com.jjb.ecms.service.api.CreditSysPushService;
import com.jjb.ecms.service.dto.T9000.T9000Req;
import com.jjb.ecms.service.dto.T9000.T9000Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;


/**
* 申请件审批信息推送
* 
* @author hh
*
*/
@Service("creditSysPushServiceImpl")
public class CreditSysPushServiceImpl implements CreditSysPushService{
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MessageToBean messageToBean;
	@Autowired
	private SendSocketSupport sendSocketSupport;
//	@Autowired
//	private SendSocket sendSocket;
	
	/**
	 * 推送审批结果给渠道端
	 */
	@Override
	public T9000Resp T9000(T9000Req req) throws Exception {
		T9000Resp resp = new T9000Resp();
		String servId = T9000Req.servId +"-ccif:"+T9000Req.servIdCcif+"";
		if(req==null){
			logger.error("交易["+servId+"-推送审批结果]请求参数为空");
			throw new ProcessException("交易["+servId+"-推送审批结果]请求参数为空");
		}
		long tokenId = System.currentTimeMillis();
		if(req.getTokenId()==null){
			req.setTokenId(tokenId+"");
		}
		logger.info("PID["+req.getTokenId()+"]..."+servId+"-推送申请件["+req.getAppNo()+"]审批结果-开始...");
		try {
			//封装请求接口
			String reqXml = getXmlReq(req);
			//调用行内服务
			String result=sendSocketSupport.sendSocketMsg(servId,req.getExtHost(),req.getExtPort(),
					reqXml,req.getCharset(),req.getConnectTimeOut(),req.getLvMsgLength(),
					req.getOrg(),null);
			if (StringUtils.isBlank(result)) {
				logger.error("未收到响应报文：{" + result + "}");
				throw new ProcessException("联机推送审批结果结束，但未收到渠道端返回报文");
			}
			if (logger.isDebugEnabled()) {
				logger.debug("应答报文长度为[{}]", (StringUtils.isBlank(result) ? "0":result.length()));
			}
			resp = messageToBean.parseXML(resp,result,null,servId,req.getTokenId());
		}catch (Exception e) {
			logger.error("交易["+servId+"-推送审批结果]异常{}", e);
			throw new ProcessException("交易["+servId+"-推送审批结果]失败！"+e.getMessage());
		}finally{
			logger.info("PID["+req.getTokenId()+"]..."+servId+"-推送申请件["+req.getAppNo()+"]审批结果-结束,耗时["+(System.currentTimeMillis()-tokenId)+"]...");
		}
		return resp;
	  
		
	}
	
	  /**
     * @Author hn
     * @Description This is T2004800067 message
     * @Date 2018/12/03 18:11
     */
	private String getXmlReq(T9000Req req) {
		JAXBContext context = null;
		Marshaller marshaller = null;
		StringWriter body = null;
		try {
			context = JAXBContext.newInstance(T2004800067Req.class);

			marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setListener(new MarshallerListener());
			String serviceCode = StringUtils.setValue(req.getServiceCode(), T9000Req.servIdCcif);
			T2004800067Req t0067Req = new T2004800067Req();
			t0067Req.sysHead.ServiceCode = serviceCode;
			t0067Req = (T2004800067Req) TransUtis.convertRequest(req, t0067Req, serviceCode, false);
			t0067Req.trans0067Req = req;
			body = new StringWriter();
			marshaller.marshal(t0067Req, body);

		} catch (JAXBException e) {
			logger.error("拼写请求报文异常",e);
			throw new ProcessException("T9000拼写请求报文异常", e);
		}
		return body.toString();
	}
}
