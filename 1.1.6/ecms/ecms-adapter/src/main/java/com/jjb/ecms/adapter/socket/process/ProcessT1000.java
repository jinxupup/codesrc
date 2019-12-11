package com.jjb.ecms.adapter.socket.process;

import java.util.ArrayList;

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
import com.jjb.ecms.service.dto.T1000.T1000Apply;
import com.jjb.ecms.service.dto.T1000.T1000Req;
import com.jjb.ecms.service.dto.T1000.T1000Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 申请进度查询
 * @author hp
 *
 */
@Service
public class ProcessT1000 implements IProcess {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private QueryService queryService;
	@Autowired
	private SocketServerProcessUtils sspUtils;

	@Override
	public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
		T1000Resp t1000Resp = new T1000Resp();
		StringBuffer respEnty = new StringBuffer();
		// 调用服务
		String reqType = "";
		try {
			//解析请求报文
			reqType = DOMXMLReader.getElementValue(appHead,"ServiceId");
			if(StringUtils.isBlank(reqType)){
				throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES+",服务代码不能为空!");
			}
			logger.info("PID["+start+"]....调用[{}]接口服务开始。。。。。",reqType);
			T1000Req t1000Req = new T1000Req();
			t1000Req = TransBeanConvert.convertRequest(reqEle,res,t1000Req);
			// 如果参数转换正常
			if (res.getSTATUS().equals("S") && SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
				t1000Resp = queryService.T1000(t1000Req);
			}
			logger.info("PID["+start+"]....调用[{}]接口服务结束。。。。。",reqType);
		} catch (Exception e) {
			//处理异常
			sspUtils.processExceptions(start, res, e);
		} finally {
			logger.info("PID["+start+"]....[{}]响应报文拼接开始。。。。。",reqType);
			respEnty.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
			respEnty.append("<Service>");
			//设置响应报文头信息
			sspUtils.setServiceHeader(appHead, res, respEnty);
			respEnty.append("<ServiceBody>");
			respEnty.append("<Response>");
			if (reqType.equals(T1000Req.servId)) {
				if(t1000Resp==null){
					t1000Resp = new T1000Resp();
				}
				ArrayList<T1000Apply> applys = t1000Resp.getApplys();
				respEnty.append("<CurPage>");
				respEnty.append(StringUtils.valueOf(t1000Resp.getCurPage()));
				respEnty.append("</CurPage>").append("\n");
				respEnty.append("<RowCnt>");
				respEnty.append(StringUtils.valueOf(t1000Resp.getRowCnt()));
				respEnty.append("</RowCnt>").append("\n");
				respEnty.append("<TotalCnt>");
				respEnty.append(StringUtils.valueOf(t1000Resp.getTotalCnt()));
				respEnty.append("</TotalCnt>").append("\n");
				respEnty.append("<SuccCnt>");
				respEnty.append(StringUtils.valueOf(t1000Resp.getSuccCnt()));// 核卡数量
				respEnty.append("</SuccCnt>").append("\n");
				respEnty.append("<EffSuccCnt>");
				respEnty.append(StringUtils.valueOf(t1000Resp.getEffSuccCnt()));// 有效核卡数量
				respEnty.append("</EffSuccCnt>").append("\n");
				respEnty.append("<RefuseCnt>");
				respEnty.append(StringUtils.valueOf(t1000Resp.getRefuseCnt()));// 拒绝数量
				respEnty.append("</RefuseCnt>").append("\n");
				respEnty.append("<ApproveCnt>");
				respEnty.append(StringUtils.valueOf(t1000Resp.getApproveCnt()));// 审核中数量
				respEnty.append("</ApproveCnt>").append("\n");
				respEnty.append("<PreCnt>");
				respEnty.append(StringUtils.valueOf(t1000Resp.getApproveCnt()));// 预审中数量
				respEnty.append("</PreCnt>").append("\n");
				
				respEnty.append("<Applys");
				if(CollectionUtils.sizeGtZero(applys)){
					respEnty.append(">").append("\n");
					for (T1000Apply apply : applys) {
						respEnty.append("<Apply>").append("\n");
						respEnty.append(StringUtils.valueOf(sspUtils.convertToStr(apply)));
						respEnty.append("</Apply>").append("\n");
					}
					respEnty.append("</Applys>").append("\n");
				}else {
					respEnty.append("/>").append("\n");
				}
			}
			respEnty.append("</Response>");
			respEnty.append("</ServiceBody>").append("\n");
			respEnty.append("</Service>").append("\n");
		}
		return respEnty.toString();
	}
}
