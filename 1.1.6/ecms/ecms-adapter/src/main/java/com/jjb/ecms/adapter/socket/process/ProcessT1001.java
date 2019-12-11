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
import com.jjb.ecms.service.api.PatchResultService;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.ecms.service.dto.T1001.T1001Patch;
import com.jjb.ecms.service.dto.T1001.T1001Req;
import com.jjb.ecms.service.dto.T1001.T1001Resp;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 补件查询及提交
 * @author hp
 *
 */
@Service
public class ProcessT1001 implements IProcess {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PatchResultService patchResultService;
	@Autowired
	private SocketServerProcessUtils sspUtils;

	@Override
	public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
		T1001Resp t1001Resp = new T1001Resp();
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
			
			if (reqType.equals(T1001Req.servId)) {// 补件操作

				T1001Req t1001Req = new T1001Req();
				t1001Req = TransBeanConvert.convertRequest(reqEle, res,t1001Req);
//				// 如果参数转换正常
				if (res.getSTATUS().equals("S") && SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
					t1001Resp = patchResultService.T1001(t1001Req);
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
			
			if(reqType.equals(T1001Req.servId)) { 
				if(t1001Resp==null){
					t1001Resp = new T1001Resp();
				}
				respEnty.append("<CurPage>");
				respEnty.append(StringUtils.valueOf(t1001Resp.getCurPage()));
				respEnty.append("</CurPage>");
				respEnty.append("<RowCnt>");
				respEnty.append(StringUtils.valueOf(t1001Resp.getRowCnt()));
				respEnty.append("</RowCnt>");
				respEnty.append("<TotalCnt>");
				respEnty.append(StringUtils.valueOf(t1001Resp.getTotalCnt()));
				respEnty.append("</TotalCnt>");
				respEnty.append("<Applys");
				ArrayList<T1001Patch> patchResultDtos = t1001Resp.getPatchs();
				if(CollectionUtils.sizeGtZero(patchResultDtos)){
					respEnty.append(">").append("\n");
					for (T1001Patch patchResultDto : patchResultDtos) {
						respEnty.append("<Apply>");
						respEnty.append(StringUtils.valueOf(sspUtils.convertToStr(patchResultDto)));
						respEnty.append("</Apply>");
					}
					respEnty.append("</Applys>").append("\n");
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
