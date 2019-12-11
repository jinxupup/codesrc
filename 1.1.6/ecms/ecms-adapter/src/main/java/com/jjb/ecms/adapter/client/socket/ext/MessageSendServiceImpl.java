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
import com.jjb.ecms.adapter.client.socket.SendSocket;
import com.jjb.ecms.adapter.utils.AdapterConstants;
import com.jjb.ecms.adapter.utils.XmlMessage.MarshallerListener;
import com.jjb.ecms.adapter.utils.XmlMessage.T2004800005Req;
import com.jjb.ecms.adapter.utils.ccif.TransUtis;
import com.jjb.ecms.service.api.MessageSendService;
import com.jjb.ecms.service.dto.Trans0005.Trans0005Req;
import com.jjb.ecms.service.dto.Trans0005.Trans0005Resp;
import com.jjb.ecms.service.dto.Trans0059.Trans0059Req;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 *@ClassName MessageSendServiceImpl
 *@Description 发送短信到综合前置
 *@Author lixing
 *Date 2018/10/14 16:43
 *Version 1.0
 */
@Service("messageSendServiceImpl")
public class MessageSendServiceImpl implements MessageSendService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MessageToBean messageToBean;
    @Autowired
    private SendSocket sendSocket;
    @Autowired
    private AdapterConstants constants;

    /**
     * 实时短信发送
     */
    @Override
    public Trans0005Resp Trans0005(Trans0005Req req) throws Exception {
        Trans0005Resp resp = new Trans0005Resp();
        String servId = Trans0005Req.servId;
        if(req==null){
            logger.error("交易["+servId+"-发送短信结果]请求参数为空");
            throw new ProcessException("交易["+servId+"-发送短信结果]请求参数为空");
        }
        long tokenId = System.currentTimeMillis();
        if(req.getTokenId()==null){
            req.setTokenId(tokenId+"");
        }
        String appNo = req.getAppNo();
        logger.info("PID["+req.getTokenId()+"]..."+servId+"-推送["+appNo+"]短信结果-开始...");
        try {
            //封装请求接口
            String reqXml = getXmlReq(req);
            String timeOut = StringUtils.setValue(req.getConnectTimeOut(), constants.defTimeOut);
            Integer connectTimeOut = StringUtils.stringToIntegerNotNull(timeOut);
            String lvMsgLStr = StringUtils.setValue(req.getLvMsgLength(), constants.defLvMsgLength);
            Integer lvMsgLength = StringUtils.stringToIntegerNotNull(lvMsgLStr);
            String org = StringUtils.setValue(req.getOrg(), OrganizationContextHolder.getOrg());
            String user = StringUtils.setValue(OrganizationContextHolder.getUserNo(),null);
            //调用行内服务，首先加密报文
            reqXml = sendSocket.encrypt(reqXml);
            //调用行内服务
            String result=sendSocket.sendSocketMsg(servId, req.getExtHost(),req.getExtPort(),
                    reqXml,req.getCharset(),connectTimeOut,lvMsgLength,org,user);
            //现在要解密返回报文
            result = sendSocket.decrypt(result);
            if (StringUtils.isBlank(result)) {
                logger.error("PID-["+tokenId+"]...申请件["+appNo+"]未收到响应报文：{" + result + "}");
                throw new ProcessException("联机推送短信结束，但未收到渠道端返回报文");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("PID-["+tokenId+"]...申请件["+appNo+"]应答报文长度为[{}]", (StringUtils.isBlank(result) ? "0":result.length()));
            }
            resp = messageToBean.parseXML(resp,result,8,servId,req.getTokenId());
        }catch (Exception e) {
            logger.error("PID-["+tokenId+"]...申请件["+appNo+"]交易["+servId+"-推送短信结果]异常{}", e);
            throw new ProcessException("交易["+servId+"-推送短信结果]失败！"+e.getMessage());
        }finally{
            logger.info("PID-["+tokenId+"]...申请件["+appNo+"]"+servId+"-推送短信结束,耗时["+(System.currentTimeMillis()-tokenId)+"]...");
        }
        return resp;
    }

    /**
     * @Author lixing
     * @Description 短信发送报文
     * @Date 2018/10/14 18:11
     */
    private String getXmlReq(Trans0005Req req) {
        JAXBContext context = null;
        Marshaller marshaller = null;
        StringWriter body = null;
        try {
            context = JAXBContext.newInstance(T2004800005Req.class);

            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setListener(new MarshallerListener());
            T2004800005Req t005Req = new T2004800005Req();
            String serviceCode = StringUtils.setValue(req.getServiceCode(), Trans0059Req.servId);
            t005Req.sysHead.ServiceCode = serviceCode;
            t005Req = (T2004800005Req) TransUtis.convertRequest(req, t005Req, serviceCode, false);
            t005Req.trans0005Req = req;
            body = new StringWriter();
            marshaller.marshal(t005Req, body);

        } catch (JAXBException e) {
            logger.error("拼写请求报文异常",e);
            throw new ProcessException("Trans0059Req拼写请求报文异常", e);
        }
        return body.toString();
    }

}