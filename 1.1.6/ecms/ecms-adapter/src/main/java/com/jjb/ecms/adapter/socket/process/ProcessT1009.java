package com.jjb.ecms.adapter.socket.process;

import java.util.List;

import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.ecms.adapter.socket.ResponseType;
import com.jjb.ecms.adapter.socket.SocketServerProcessUtils;
import com.jjb.ecms.adapter.utils.DOMXMLReader;
import com.jjb.ecms.adapter.utils.TransBeanConvert;
import com.jjb.ecms.service.api.QueryService;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.ecms.service.dto.T1009.Rank;
import com.jjb.ecms.service.dto.T1009.T1009Req;
import com.jjb.ecms.service.dto.T1009.T1009Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName ProcessT1009
 * @Description 根据类型查询渠道端进件排行榜
 * @Author lixing
 * Date 2018/11/23 11:52
 * Version 1.0
 */
@Service
public class ProcessT1009 implements IProcess {

    private Logger logger = LoggerFactory.getLogger(getClass());
    // 联机申请
    @Autowired
    private QueryService queryService;
    @Autowired
    private SocketServerProcessUtils sspUtils;

    /**
     * @Author lixing
     * @Description 处理排行榜查询
     * @Date 2018/12/25 11:38
     */
    @Override
    public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
        T1009Resp resp = new T1009Resp();
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
            if (reqType.equals(T1009Req.servId)) {//4.1.8.T1009-受理外部决策结果提交
                T1009Req t1009Req = new T1009Req();
                t1009Req = TransBeanConvert.convertRequest(reqEle, res,t1009Req);
                // 如果参数转换正常
                if (res.getSTATUS().equals("S") && SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
                    resp = queryService.T1009(t1009Req);
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
            respEnty.append("<Response>").append("\n");
            respEnty.append("<Ranks");
            List<Rank> ranks = resp.getRanks();
            if(CollectionUtils.sizeGtZero(ranks)){
                respEnty.append(">").append("\n");
                for (Rank rank : ranks) {
                    respEnty.append("<Rank>").append("\n");
                    respEnty.append(StringUtils.valueOf(sspUtils.convertToStr(rank)));
                    respEnty.append("</Rank>").append("\n");
                }
                respEnty.append("</Ranks>").append("\n");
            }else {
                respEnty.append("/>");
            }

            respEnty.append("<SpreadNum>");
            respEnty.append(StringUtils.valueOf(resp.getSpreadNum()));
            respEnty.append("</SpreadNum>");
            respEnty.append("<ApprovalNum>");
            respEnty.append(StringUtils.valueOf(resp.getApprovalNum()));
            respEnty.append("</ApprovalNum>");
            respEnty.append("<PreCnt>");
            respEnty.append(StringUtils.valueOf(resp.getPreCnt()));
            respEnty.append("</PreCnt>");
            respEnty.append("</Response>");
            respEnty.append("</ServiceBody>").append("\n");
            respEnty.append("</Service>");
            
            logger.info("PID["+start+"]....[{}]响应报文拼接结束,交易耗时["+(System.currentTimeMillis()-start)+"]",reqType);
        }
        return respEnty.toString();
    }

}
