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
import com.jjb.ecms.service.dto.T1007.T1007Req;
import com.jjb.ecms.service.dto.T1007.T1007Resp;
import com.jjb.ecms.service.dto.T1007.T1007UserInfo;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 
 * @author hp
 *
 */
@Service
public class ProcessT1007 implements IProcess {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private QueryService queryService;
	@Autowired
	private SocketServerProcessUtils sspUtils;

	@Override
	public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
		T1007Resp t1007Resp = new T1007Resp();
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
			if (reqType.equals(T1007Req.servId)) {//系统用户（推广人）查询
				T1007Req t1007Req = new T1007Req();
				t1007Req = TransBeanConvert.convertRequest(reqEle, res,t1007Req);
				// 如果参数转换正常
				if (res.getSTATUS().equals("S") && SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
					t1007Resp = queryService.T1007(t1007Req);
				}
			} else {
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
			
			if(reqType.equals(T1007Req.servId)) { 
				if(t1007Resp==null){
					t1007Resp = new T1007Resp();
				}
				respEnty.append("<CurPage>");
				respEnty.append(StringUtils.valueOf(t1007Resp.getCurPage()));
				respEnty.append("</CurPage>");
				respEnty.append("<RowCnt>");
				respEnty.append(StringUtils.valueOf(t1007Resp.getRowCnt()));
				respEnty.append("</RowCnt>");
				respEnty.append("<TotalCnt>");
				respEnty.append(StringUtils.valueOf(t1007Resp.getTotalCnt()));
				respEnty.append("</TotalCnt>").append("\n");
				respEnty.append("<Users");
				List<T1007UserInfo> users = t1007Resp.getUsers();
				if(CollectionUtils.sizeGtZero(users)){
					respEnty.append(">");
					for (T1007UserInfo user : users) {
						respEnty.append("<User>");
						respEnty.append(StringUtils.valueOf(sspUtils.convertToStr(user)));
						respEnty.append("</User>").append("\n");
					}
					respEnty.append("</Users>").append("\n");
				}else {
					respEnty.append("/>").append("\n");
				}
			}
			respEnty.append("</Response>");
			respEnty.append("</ServiceBody>").append("\n");
			respEnty.append("</Service>");
			
			logger.info("PID["+start+"]....[{}]响应报文拼接结束,交易耗时["+(System.currentTimeMillis()-start)+"]",reqType);
		}
		return respEnty.toString();
	}
}
