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
import com.jjb.ecms.service.dto.T1008.T1008Req;
import com.jjb.ecms.service.dto.T1008.T1008Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName ProcessT1008
 * @Description TODO   T1008接收渠道端提交的“决策结果”
 * @Author smh
 * Date 2018/11/23 11:52
 * Version 1.0
 */
@Service
public class ProcessT1008 implements IProcess {

    private Logger logger = LoggerFactory.getLogger(getClass());
    // 联机申请
    @Autowired
    private IPadApplyService ipadApplyService;
    @Autowired
    private SocketServerProcessUtils sspUtils;

    /**
     * @Author smh
     * @Description TODO
     * @Date 2018/11/23 14:21
     */
    @Override
    public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
        T1008Resp t1008Resp = new T1008Resp();
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
            if (reqType.equals(T1008Req.servId)) {//4.1.8.T1008-受理外部决策结果提交
                T1008Req t1008Req = new T1008Req();
                t1008Req = TransBeanConvert.convertRequest(reqEle, res,t1008Req);
                // 如果参数转换正常
                if (res.getSTATUS().equals("S") && SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
                    t1008Resp = ipadApplyService.T1008(t1008Req);
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
            if (reqType.equals(T1008Req.servId)) {

            }
            respEnty.append("</Response>");
            respEnty.append("</ServiceBody>").append("\n");
            respEnty.append("</Service>");
            
            logger.info("PID["+start+"]....[{}]响应报文拼接结束,交易耗时["+(System.currentTimeMillis()-start)+"]",reqType);
        }
        return respEnty.toString();
    }

}
