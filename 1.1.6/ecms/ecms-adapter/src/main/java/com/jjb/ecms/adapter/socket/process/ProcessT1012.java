package com.jjb.ecms.adapter.socket.process;

import com.jjb.ecms.adapter.socket.ResponseType;
import com.jjb.ecms.adapter.socket.SocketServerProcessUtils;
import com.jjb.ecms.adapter.utils.DOMXMLReader;
import com.jjb.ecms.adapter.utils.TransBeanConvert;
import com.jjb.ecms.service.api.IPadApplyService;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.ecms.service.dto.T1012.T1012Req;
import com.jjb.ecms.service.dto.T1012.T1012Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ProcessT1012
 * Company jydata-tech
 * @Description
 * Author wuxiaole
 * Date 2019-8-14 下午 2:53
 * Version 1.0
 */
@Service
public class ProcessT1012 implements IProcess {

    private Logger logger = LoggerFactory.getLogger(ProcessT1012.class);
    // 联机申请
    @Autowired
    private IPadApplyService ipadApplyService;
    @Autowired
    private SocketServerProcessUtils sspUtils;

    @Override
    public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
        T1012Resp t1012Resp = new T1012Resp();
        // 调用服务
        String reqType = "";
        StringBuffer respEnty = new StringBuffer();
        try {
            // //解析请求报文
            reqType = DOMXMLReader.getElementValue(appHead, "ServiceId");
            if (StringUtils.isBlank(reqType)) {
                throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES + ",服务代码不能为空!");
            }
            logger.info("PID[" + start + "]....调用[ProcessT1012]接口服务开始....", reqType);
            // 集中进件录入 申请件
            if (reqType.equals(T1012Req.servId)) {
                T1012Req t1012Req = new T1012Req();
                t1012Req = TransBeanConvert.convertRequest(reqEle, res, t1012Req);
                // 如果参数转换正常
                if (res.getSTATUS().equals("S")
                        && SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
                    t1012Resp = ipadApplyService.T1012(t1012Req);
                }
            } else {
                throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES + "[" + reqType + "]");
            }
            logger.info("PID[" + start + "]....调用[ProcessT1012]接口服务结束....", reqType);
        } catch (Exception e) {
            //处理异常
            sspUtils.processExceptions(start, res, e);
        } finally {
            logger.info("PID[" + start + "]....[ProcessT1012]响应报文拼接开始", reqType);
            respEnty.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
            respEnty.append("<Service>").append("\n");
            //设置响应报文头信息
            sspUtils.setServiceHeader(appHead, res, respEnty);
            respEnty.append("<ServiceBody>");
            respEnty.append("<Response>");
            if (reqType.equals(T1012Req.servId) && !"F".equals(res.getSTATUS())) {
                respEnty.append("<Name>").append(t1012Resp.getName()).append("</Name>")
                        .append("<IdType>").append(t1012Resp.getIdType()).append("</IdType>")
                        .append("<IdNo>").append(t1012Resp.getIdNo()).append("</IdNo>")
                        .append("<Cellphone>").append(t1012Resp.getCellphone()).append("</Cellphone>")
                        .append("<AppProducts>").append(t1012Resp.getAppProducts()).append("</AppProducts>")
                        .append("<AppAmount>").append(t1012Resp.getAppAmount()).append("</AppAmount>")
                        .append("<CompanyName>").append(t1012Resp.getCompanyName()).append("</CompanyName>")
                        .append("<MaritalStatus>").append(t1012Resp.getMaritalStatus()).append("</MaritalStatus>")
                        .append("<PolicyResult>").append(t1012Resp.getPolicyResult()).append("</PolicyResult>")
                        .append("<RuleList>").append(t1012Resp.getRuleList()).append("</RuleList>")
                        .append("<RefuseCode>").append(t1012Resp.getRefuseCode()).append("</RefuseCode>")
                        .append("<ImageNum>").append(t1012Resp.getImageNum()).append("</ImageNum>")
                        .append("<WeCode>").append(t1012Resp.getWeCode()).append("</WeCode>")
                        .append("<PptyProvince>").append(t1012Resp.getPptyProvince()).append("</PptyProvince>")
                        .append("<PptyCity>").append(t1012Resp.getPptyCity()).append("</PptyCity>")
                        .append("<PptyArea>").append(t1012Resp.getPptyArea()).append("</PptyArea>")
                        .append("<PptyAreaCode>").append(t1012Resp.getPptyAreaCode()).append("</PptyAreaCode>");
            }
            respEnty.append("</Response>");
            respEnty.append("</ServiceBody>").append("\n");
            respEnty.append("</Service>");

        }
        return respEnty.toString();
    }


}
