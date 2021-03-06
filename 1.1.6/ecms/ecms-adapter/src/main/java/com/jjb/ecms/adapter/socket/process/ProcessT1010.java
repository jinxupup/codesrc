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
import com.jjb.ecms.service.dto.T1010.T1010Req;
import com.jjb.ecms.service.dto.T1010.T1010Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName ProcessT1010
 * @Description 用于支持客户经理自主分配到其指定的其他客户经理名下
 * @Author lixing
 * Date 2018/11/23 11:52
 * Version 1.0
 */
@Service
public class ProcessT1010 implements IProcess {

    private Logger logger = LoggerFactory.getLogger(getClass());
    // 联机申请
    @Autowired
    private IPadApplyService iPadApplyService;
    @Autowired
    private SocketServerProcessUtils sspUtils;

    /**
     * @Author lixing
     * @Description 处理排行榜查询
     * @Date 2018/12/25 11:38
     */
    @Override
    public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
        T1010Resp resp = new T1010Resp();
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
            if (reqType.equals(T1010Req.servId)) {//4.1.8.T1009-受理外部决策结果提交
                T1010Req t1010Req = new T1010Req();
                t1010Req = TransBeanConvert.convertRequest(reqEle, res,t1010Req);
                // 如果参数转换正常
                if (res.getSTATUS().equals("S") && SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
                    resp = iPadApplyService.T1010(t1010Req);
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
            respEnty.append("</Response>");
            respEnty.append("</ServiceBody>").append("\n");
            respEnty.append("</Service>");
            
            logger.info("PID["+start+"]....[{}]响应报文拼接结束,交易耗时["+(System.currentTimeMillis()-start)+"]",reqType);
        }
        return respEnty.toString();
    }

}
