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
import com.jjb.ecms.adapter.utils.XmlMessage.T2004800004Req;
import com.jjb.ecms.adapter.utils.ccif.TransUtis;
import com.jjb.ecms.service.api.AccountVerficationService;
import com.jjb.ecms.service.dto.Trans0004.Trans0004Req;
import com.jjb.ecms.service.dto.Trans0004.Trans0004Resp;
import com.jjb.ecms.service.dto.Trans0059.Trans0059Req;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 *@ClassName AccountVerficationServiceImpl
 *@Description 发送约定还款账户到综合前置
 *@Author lixing
 *Date 2018/10/14 16:43
 *Version 1.0
 */
@Service("accountVerficationServiceImpl")
public class AccountVerficationServiceImpl implements AccountVerficationService {


    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MessageToBean messageToBean;
    @Autowired
    private SendSocket sendSocket;
    @Autowired
    private AdapterConstants constants;

    @Override
    public Trans0004Resp Trans0004(Trans0004Req req) throws Exception {
        Trans0004Resp resp = new Trans0004Resp();
        String servId = Trans0004Req.servId;
        if(req==null){
            logger.error("交易["+servId+"-验证账户结果]请求参数为空");
            throw new ProcessException("交易["+servId+"-验证账户结果]请求参数为空");
        }
        long tokenId = System.currentTimeMillis();
        if(req.getTokenId()==null){
            req.setTokenId(tokenId+"");
        }
        logger.info("PID["+req.getTokenId()+"]..."+servId+"-验证账户结果-开始...");
        try {
            //封装请求接口
            String reqXml = getXmlReq(req);
            String timeOut = StringUtils.setValue(req.getConnectTimeOut(), constants.defTimeOut);
            Integer connectTimeOut = StringUtils.stringToIntegerNotNull(timeOut);
            String lvMsgLStr = StringUtils.setValue(req.getLvMsgLength(), constants.defLvMsgLength);
            Integer lvMsgLength = StringUtils.stringToIntegerNotNull(lvMsgLStr);
            String org = StringUtils.setValue(req.getOrg(), OrganizationContextHolder.getOrg());
            String user = OrganizationContextHolder.getUserNo();
            //调用行内服务
            String result=sendSocket.sendSocketMsg(servId, req.getExtHost(),req.getExtPort(),
                    reqXml,req.getCharset(),connectTimeOut,lvMsgLength,org,user);
            if (StringUtils.isBlank(result)) {
                logger.error("未收到响应报文：{" + result + "}");
                throw new ProcessException("联机验证账户结束，但未收到渠道端返回报文");
            }
            if (logger.isDebugEnabled()) {
                logger.debug("应答报文长度为[{}]", (StringUtils.isBlank(result) ? "0":result.length()));
            }
            resp = messageToBean.parseXML(resp,result,8,servId,req.getTokenId());
        }catch (Exception e) {
            logger.error("交易["+servId+"-验证账户结果]异常{}", e);
            throw new ProcessException("交易["+servId+"-验证账户结果]失败！"+e.getMessage());
        }finally{
            logger.info("PID["+req.getTokenId()+"]..."+servId+"-验证账户结束,耗时["+(System.currentTimeMillis()-tokenId)+"]...");
        }
        return resp;
    }

    /**
     * @Author lixing
     * @Description 还款账号验证报文
     * @Date 2018/10/14 18:11
     */
    private String getXmlReq(Trans0004Req req) {
        JAXBContext context = null;
        Marshaller marshaller = null;
        StringWriter body = null;
        try {
            context = JAXBContext.newInstance(T2004800004Req.class);

            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.setListener(new MarshallerListener());
            T2004800004Req t004Req = new T2004800004Req();
            String serviceCode = StringUtils.setValue(req.getServiceCode(), Trans0059Req.servId);
            t004Req.sysHead.ServiceCode = serviceCode;
            t004Req = (T2004800004Req) TransUtis.convertRequest(req, t004Req, serviceCode, false);
            t004Req.trans0004Req = req;
            body = new StringWriter();
            marshaller.marshal(t004Req, body);

        } catch (JAXBException e) {
            logger.error("拼写请求报文异常",e);
            throw new ProcessException("Trans0059Req拼写请求报文异常", e);
        }
        return body.toString();
    }


}