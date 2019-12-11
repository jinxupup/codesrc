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
import com.jjb.ecms.service.dto.TCustInfo;
import com.jjb.ecms.service.dto.T1002.T1002Req;
import com.jjb.ecms.service.dto.T1002.T1002Resp;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 *
 * @author hp
 *
 */
@Service
public class ProcessT1002 implements IProcess {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SocketServerProcessUtils sspUtils;
	@Autowired
	private QueryService queryService;

	@Override
	public String doProcess(Long start, Element appHead, Element reqEle, ResponseType res) throws Exception {
		StringBuffer respEnty = new StringBuffer();
		// 调用服务
		String reqType = "";
		T1002Resp resp = new T1002Resp();
		try {
			//解析请求报文
			reqType = DOMXMLReader.getElementValue(appHead,"ServiceId");
			if(StringUtils.isBlank(reqType)){
				throw new ProcessException(SysConstants.ERRS002_CODE, SysConstants.ERRS002_MES+",服务代码不能为空!");
			}
			logger.info("PID["+start+"]....调用[{}]接口服务开始。。。。。",reqType);
			T1002Req t1002Req = new T1002Req();
			t1002Req = TransBeanConvert.convertRequest(reqEle,res,t1002Req);
			if (res.getSTATUS().equals("S") && SysConstants.SUCCESS_CODE.equals(res.getCODE())) {
				resp = queryService.T1002(t1002Req);
			}
			if(resp==null) {
				res.setCODE(SysConstants.ERRS001_CODE);
				res.setDESC("准入及老客户验证失败，系统未返回有效验证结果");
				res.setSTATUS("F");
			}
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
			if (reqType.equals(T1002Req.servId) && resp!=null) {
				respEnty.append("<Admittance>").append(resp.getAdmittance()).append("</Admittance>");
				respEnty.append("<CustomerType>").append(resp.getCustomerType()).append("</CustomerType>");
				respEnty.append("<ExitsProducts>").append(resp.getExitsProducts()).append("</ExitsProducts>");
				T1005Req applyDto = resp.getApply();
				if(applyDto!=null) {
					respEnty.append("\n").append("<Apply>").append("\n");
					respEnty.append(StringUtils.valueOf(sspUtils.convertToStr(applyDto)));

					List<TCustInfo> custs = applyDto.getCusts();
					respEnty.append("\n").append("<Custs");
					if(CollectionUtils.isNotEmpty(custs)){
						respEnty.append(">").append("\n");
						for (int i = 0; i < custs.size(); i++) {
							TCustInfo cust = custs.get(i);
							if(cust!=null) {
								respEnty.append("<Cust>");
								respEnty.append(StringUtils.valueOf(sspUtils.convertToStr(cust)));
								respEnty.append("</Cust>").append("\n");
							}
						}
						respEnty.append("</Custs>").append("\n");
					}else {
						respEnty.append("/>").append("\n");
					}
					respEnty.append("</Apply>").append("\n");
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
