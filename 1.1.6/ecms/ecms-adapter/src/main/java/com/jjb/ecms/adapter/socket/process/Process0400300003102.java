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
import com.jjb.ecms.service.api.CreditInfoAuxTreatService;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.ecms.service.dto.Trans0140.Trans0140Req;
import com.jjb.ecms.service.dto.Trans0140.Trans0140Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName Process0400300003102
 * @Description 04003000031账户资产信息查询</br>
 * @Author hejn
 * Date
 * Version 1.0
 */
@Service
public class Process0400300003102 implements IProcess {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private CreditInfoAuxTreatService creditInfoAuxTreatService;
    @Autowired
    private SocketServerProcessUtils sspUtils;

    /**
     * @Author 何嘉能
     * @Description 04003000031账户资产信息查询</br>
     * 1.法院执行人查询
     */
    @Override
    public String doProcess(Long start, Element root, Element reqEle, ResponseType res) throws Exception {
    	Trans0140Resp resp = new Trans0140Resp();
        // 调用服务
        String serviceId = "";
        StringBuffer respEnty = new StringBuffer();
        try {
        	Element esbSysHead = root.element("SYS_HEAD");
        	reqEle = root.element("BODY");
            serviceId = DOMXMLReader.getElementValue(esbSysHead,"ServiceCode");
            String serScene = DOMXMLReader.getElementValue(esbSysHead,"ServiceScene");
            serviceId = serviceId+"-"+serScene;
            if(StringUtils.isBlank(serviceId)){
                throw new ProcessException(SysConstants.ERR_ESB_S002_CODE, SysConstants.ERR_ESB_S002_MES+",服务代码不能为空!");
            }
            logger.info("PID["+start+"]....调用[{}]接口服务开始。。。。。",serviceId);
            if (serviceId.equals(Trans0140Req.servId+"-"+"02")) {
            	Trans0140Req req = new Trans0140Req();
                req = TransBeanConvert.convertRequest(reqEle, res,req);
                // 如果参数转换正常
                if (res.getSTATUS().equals("S") && SysConstants.ESB_SUCCESS_CODE.equals(res.getCODE())) {
                    resp = creditInfoAuxTreatService.Trans0140(req);
                }
            }else {
                throw new ProcessException(SysConstants.ERR_ESB_S002_CODE, SysConstants.ERR_ESB_S002_MES+"["+serviceId+"]");
            }
            logger.info("PID["+start+"]....调用[{}]接口服务结束。。。。。",serviceId);
        } catch (Exception e) {
            //处理异常
            sspUtils.processEsbSpecExceptions(start, res, e);
        } finally {
            logger.info("PID["+start+"]....[{}]响应报文拼接开始。。。。。",serviceId);
            respEnty.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
            respEnty.append("<service>").append("\n");
            //设置响应报文头信息
            sspUtils.setEsbRespHeader(root, res, respEnty);
            respEnty.append("\n").append("<BODY>");
            if(resp!=null) {
            	respEnty.append(StringUtils.valueOf(sspUtils.convertToStr(resp)));
            }
            respEnty.append("</BODY>").append("\n");
            respEnty.append("</service>");
            logger.info("PID["+start+"]....[{}]响应报文拼接结束,交易耗时["+(System.currentTimeMillis()-start)+"]",serviceId);
        }
        return respEnty.toString();
    }

}
