package com.jjb.ecms.adapter.socket.process;

import com.jjb.ecms.adapter.socket.ResponseType;
import com.jjb.ecms.adapter.socket.SocketServerProcessUtils;
import com.jjb.ecms.adapter.utils.DOMXMLReader;
import com.jjb.ecms.adapter.utils.TransBeanConvert;
import com.jjb.ecms.service.api.IPadApplyService;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.ecms.service.dto.T1011.T1011Req;
import com.jjb.ecms.service.dto.T1011.T1011Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName ProcessT1011
 * Company jydata-tech
 * @Description
 * Author wuxiaole
 * Date 2019-8-14 下午 2:53
 * Version 1.0
 */
@Service
public class ProcessT1011 implements IProcess {

    private Logger logger = LoggerFactory.getLogger(ProcessT1011.class);
    // 联机申请
    @Autowired
    private IPadApplyService ipadApplyService;
    @Autowired
    private SocketServerProcessUtils sspUtils;

    @Override
    public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
        T1011Resp t1011Resp = new T1011Resp();
        // 调用服务
        String reqType = "";
        StringBuffer respEnty = new StringBuffer();
        try {
            // //解析请求报文
            reqType = DOMXMLReader.getElementValue(appHead, "ServiceId");
            if (StringUtils.isBlank(reqType)) {
                throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES + ",服务代码不能为空!");
            }
            logger.info("PID[" + start + "]....调用[{ProcessT1011}]接口服务开始。。。。。", reqType);
            // 集中进件录入 申请件
            if (reqType.equals(T1011Req.servId)) {
                T1011Req t1011Req = new T1011Req();
                t1011Req = TransBeanConvert.convertRequest(reqEle, res, t1011Req);
                // 如果参数转换正常
                if (res.getSTATUS().equals("S")
                        && SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
                    t1011Resp = ipadApplyService.T1011(t1011Req);
                }
            } else {
                throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES + "[" + reqType + "]");
            }
            logger.info("PID[" + start + "]....调用[{ProcessT1011}]接口服务结束。。。。。", reqType);
        } catch (Exception e) {
            //处理异常
            sspUtils.processExceptions(start, res, e);
        } finally {
            logger.info("PID[" + start + "]....[ProcessT1011]响应报文拼接开始", reqType);
            respEnty.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
            respEnty.append("<Service>").append("\n");
            //设置响应报文头信息
            sspUtils.setServiceHeader(appHead, res, respEnty);
            respEnty.append("<ServiceBody>");
            respEnty.append("<Response>");
            if (reqType.equals(T1011Req.servId)) {
                respEnty.append(StringUtils.valueOf(sspUtils.convertToStr(t1011Resp)));
            }
            respEnty.append("</Response>");
            respEnty.append("</ServiceBody>").append("\n");
            respEnty.append("</Service>");

        }
        return respEnty.toString();
    }


}
