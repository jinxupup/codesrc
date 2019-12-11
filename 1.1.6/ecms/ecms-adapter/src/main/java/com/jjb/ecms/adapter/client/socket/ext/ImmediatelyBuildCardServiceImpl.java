package com.jjb.ecms.adapter.client.socket.ext;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.facility.enums.bus.ApplyFileItem;
import com.jjb.ecms.adapter.client.socket.MessageToBean;
import com.jjb.ecms.adapter.client.socket.SendSocket;
import com.jjb.ecms.adapter.utils.AdapterConstants;
import com.jjb.ecms.adapter.utils.XmlMessage.MarshallerListener;
import com.jjb.ecms.adapter.utils.XmlMessage.T2004800059Req;
import com.jjb.ecms.adapter.utils.ccif.TransUtis;
import com.jjb.ecms.service.api.ImmediatelyBuildCardService;
import com.jjb.ecms.service.dto.Trans0059.Trans0059Req;
import com.jjb.ecms.service.dto.Trans0059.Trans0059Resp;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
  *@ClassName ImmediatelyBuildCardServiceImpl
  *@Description 实时建账制卡
  *@Author lixing
  *Date 2018/10/17 17:53
  *Version 1.0
  */
@Service("immediatelyBuildCardServiceImpl")
public class ImmediatelyBuildCardServiceImpl implements ImmediatelyBuildCardService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SendSocket sendSocket;
    @Autowired
    private MessageToBean messageToBean;
    @Autowired
    private AdapterConstants constants;

    /**
     * 实时建账制卡
     */
    @Override
    public Trans0059Resp Trans0059(Trans0059Req req,ApplyFileItem applyFileItem) throws Exception {

        Trans0059Resp resp = new Trans0059Resp();
        String servId = Trans0059Req.servId;
        if(req==null){
            logger.error("交易["+servId+"-发送实时建账制卡结果]请求参数为空");
            throw new ProcessException("交易["+servId+"-发送实时建账制卡结果]请求参数为空");
        }
        long tokenId = System.currentTimeMillis();
        if(req.getTokenId()==null){
            req.setTokenId(tokenId+"");
        }
        logger.info("PID["+req.getTokenId()+"]..."+servId+"-推送实时建账制卡结果-开始...");
        try {
            //封装请求接口
            String reqXml = getXmlReq(req,applyFileItem);
            String timeOut = StringUtils.setValue(req.getConnectTimeOut(), constants.defTimeOut);
            Integer connectTimeOut = StringUtils.stringToIntegerNotNull(timeOut);
            String lvMsgLStr = StringUtils.setValue(req.getLvMsgLength(), constants.defLvMsgLength);
            Integer lvMsgLength = StringUtils.stringToIntegerNotNull(lvMsgLStr);
            String org = StringUtils.setValue(req.getOrg(), OrganizationContextHolder.getOrg());
            String user = StringUtils.setValue(OrganizationContextHolder.getUserNo(),null);
            //调用行内服务
            String result=sendSocket.sendSocketMsg(servId, req.getExtHost(),req.getExtPort(),
                    reqXml,req.getCharset(),connectTimeOut,lvMsgLength,org,user);
            if (StringUtils.isBlank(result)) {
                logger.error("未收到响应报文：{" + result + "}");
                throw new ProcessException("联机推送实时建账制卡结束，但未收到渠道端返回报文");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("应答报文长度为[{}]", (StringUtils.isBlank(result) ? "0":result.length()));
            }
            resp =messageToBean.parseXML(resp,result,null,servId,req.getTokenId());
        }catch (Exception e) {
            logger.error("交易["+servId+"-推送实时建账制卡结果]异常{}", e);
            throw new ProcessException("交易["+servId+"-推送实时建账制卡结果]失败！"+e.getMessage());
        }finally{
            logger.info("PID["+req.getTokenId()+"]..."+servId+"-推送实时建账制卡结束,耗时["+(System.currentTimeMillis()-tokenId)+"]...");
        }
        return resp;
    }


    /**
     * @Author lixing
     * @Description This is T2004800059 message
     * @Date 2018/10/14 18:11
     */
	private String getXmlReq(Trans0059Req req, ApplyFileItem applyFileItem) {
		JAXBContext context = null;
		Marshaller marshaller = null;
		StringWriter body = null;
		try {
			context = JAXBContext.newInstance(T2004800059Req.class);

			marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setListener(new MarshallerListener());
			String serviceCode = StringUtils.setValue(req.getServiceCode(), Trans0059Req.servId);
			T2004800059Req t059Req = new T2004800059Req();
			t059Req.sysHead.ServiceCode = serviceCode;
			t059Req = (T2004800059Req) TransUtis.convertRequest(req, t059Req, serviceCode, false);
			t059Req.applyFileItem = applyFileItem;
			body = new StringWriter();
			marshaller.marshal(t059Req, body);

		} catch (JAXBException e) {
			logger.error("拼写请求报文异常",e);
			throw new ProcessException("Trans0059Req拼写请求报文异常", e);
		}
		return body.toString();
	}


}
