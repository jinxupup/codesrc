package com.jjb.ecms.adapter.socket;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.stereotype.Component;

import com.jjb.ecms.adapter.utils.DOMXMLReader;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

@Component
public class SocketServerProcessUtils {

	private Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * 生成响应报文 responseProcess(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public ResponseType initResp(ResponseType res) {
		res.setSTATUS("S");
		res.setCODE(SysConstants.SUCCESS_CODE);
		res.setDESC("交易成功");
		return res;
	}
	/**
	 * 设置审批自定义规范响应报文头信息
	 * @param appHead
	 * @param res
	 * @param respEnty
	 */
	public void setServiceHeader(Element appHead, ResponseType res, StringBuffer respEnty) {
		respEnty.append("<ServiceHeader>");
		respEnty.append("<ServiceSn>");
		respEnty.append(DOMXMLReader.getElementValue(appHead, "ServiceSn"));
		respEnty.append("</ServiceSn>");
//		respEnty.append("<ServiceId>");
//		respEnty.append(DOMXMLReader.getElementValue(appHead, "ServiceId"));
//		respEnty.append("</ServiceId>");
//		respEnty.append("<ChannelId>");
//		respEnty.append(DOMXMLReader.getElementValue(appHead, "ChannelId"));
//		respEnty.append("</ChannelId>");
//		respEnty.append("<OpId>");
//		respEnty.append(DOMXMLReader.getElementValue(appHead, "OpId"));
//		respEnty.append("</OpId>");
		respEnty.append("<RequstTime>");
		respEnty.append(DOMXMLReader.getElementValue(appHead, "RequstTime"));
		respEnty.append("</RequstTime>");
//		respEnty.append("<VersionId>");
//		respEnty.append(DOMXMLReader.getElementValue(appHead, "VersionId"));
//		respEnty.append("</VersionId>");
//		respEnty.append("<Mac>");
//		respEnty.append(DOMXMLReader.getElementValue(appHead, "Mac"));
//		respEnty.append("</Mac>");
		respEnty.append("<ResServiceSn>");
		respEnty.append(DOMXMLReader.getElementValue(appHead, "ServiceSn"));
		respEnty.append("</ResServiceSn>");
		respEnty.append("<ResServiceTime>");
		Long reqTime = 0L;
		String reqTimeStr = null;
		try {
			reqTimeStr =DOMXMLReader.getElementValue(appHead, "RequstTime"); 
			reqTime = Long.parseLong(reqTimeStr);
		} catch (Exception e2) {
			logger.warn("无法转换请求参数中[RequstTime]标签与值["+reqTimeStr+"]");
		}
		respEnty.append(StringUtils.valueOf(Long.parseLong(DateUtils.dateToString(new Date(), DateUtils.FULL_THRID_LINE))-reqTime));
		respEnty.append("</ResServiceTime>");
		respEnty.append("<ServResponse>");
		respEnty.append("<Status>");
		respEnty.append(StringUtils.valueOf(res.getSTATUS()));
		respEnty.append("</Status>");
		respEnty.append("<Code>");
		respEnty.append(StringUtils.valueOf(res.getCODE()));
		respEnty.append("</Code>");
		respEnty.append("<Desc>");
		respEnty.append(StringUtils.valueOf(res.getDESC()));
		respEnty.append("</Desc>");
		respEnty.append("</ServResponse>");
		respEnty.append("</ServiceHeader>");
	}

	/**
	 * 审批系统自定义规范异常处理
	 * @param start
	 * @param res
	 * @param e
	 */
	public void processExceptions(Long start, ResponseType res, Exception e) {
		res.setSTATUS("F");
		if (e instanceof ProcessException) {
			logger.error("PID["+start+"]....服务处理错误,错误码{}，错误消息{}",
					((ProcessException) e).getErrorCode(), e.getMessage());
			// 报文格式验证错误，返回响应报文
			ProcessException processexecption = (ProcessException) e;
			res.setSTATUS("F");
			if (StringUtils.isEmpty(processexecption.getErrorCode())) {
				res.setCODE(SysConstants.ERRS001_CODE);
				if (StringUtils.isEmpty(e.getMessage())) {
					res.setDESC(SysConstants.ERRS001_CODE);
				} else {
					res.setDESC(e.getMessage());
				}
			} else {
				res.setCODE(processexecption.getErrorCode());
				if (processexecption.getMessage() != null)
					res.setDESC(processexecption.getErrorCode());
				else {
					if (StringUtils.isEmpty(e.getMessage())) {
						res.setDESC("未定义的错误");
					} else {
						res.setDESC(e.getMessage());
					}
				}
			}
		} else if (e instanceof DocumentException) {
			logger.error("请求报文解析异常", e);
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERRS013_CODE);
			res.setDESC(SysConstants.ERRS013_MES);
		} else if (e instanceof AmqpConnectException) {
			logger.error("PID["+start+"]....RPC服务响应超时错误消息{}", e);
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERRB202_CODE);
			res.setDESC(SysConstants.ERRB202_MES);
		} else if (e instanceof IllegalArgumentException) {
			logger.error("PID["+start+"]....参数异常{}", e);
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERRB202_CODE);
			res.setDESC(SysConstants.ERRB202_MES);

		} else if (e instanceof TimeoutException) {
			logger.error("PID["+start+"]....RPC服务响应超时错误消息{}", e);
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERRB202_CODE);
			res.setDESC(SysConstants.ERRB202_MES);

		} else {
			String[] errInfo = e.toString().split(":");
			if(errInfo.length > 1){
				logger.error("PID["+start+"]....系统处理失败:" + errInfo[1], e);
				res.setSTATUS("F");
				res.setCODE(SysConstants.ERRS001_CODE);
				res.setDESC("系统处理失败:" + errInfo[1]);
			}else{
				logger.error("PID["+start+"]....系统处理失败", e);
				res.setSTATUS("F");
				res.setCODE(SysConstants.ERRS001_CODE);
				res.setDESC("系统处理失败");
			}
		}
	}

	/**
	 * 设置ESB响应报文头信息
	 * @param appHead
	 * @param res
	 * @param respEnty
	 */
	public void setEsbRespHeader(Element root, ResponseType res, StringBuffer respEnty) {
		if(root==null) {
			return;
		}
		Element sysHead = root.element("SYS_HEAD");
		respEnty.append("<SYS_HEAD>");
			respEnty.append("<Mac>");
			respEnty.append(DOMXMLReader.getElementValue(sysHead, "Mac"));
			respEnty.append("</Mac>");
			
			respEnty.append("<MsgId>");
			respEnty.append(DOMXMLReader.getElementValue(sysHead, "MsgId"));
			respEnty.append("</MsgId>");
			
			respEnty.append("<ConsumerId>");
			respEnty.append(DOMXMLReader.getElementValue(sysHead, "ConsumerId"));
			respEnty.append("</ConsumerId>");
			
			respEnty.append("<SourceSysId>");
			respEnty.append(DOMXMLReader.getElementValue(sysHead, "SourceSysId"));
			respEnty.append("</SourceSysId>");
			
			respEnty.append("<ServiceCode>");
			respEnty.append(DOMXMLReader.getElementValue(sysHead, "ServiceCode"));
			respEnty.append("</ServiceCode>");
			
			respEnty.append("<ServiceScene>");
			respEnty.append(DOMXMLReader.getElementValue(sysHead, "ServiceScene"));
			respEnty.append("</ServiceScene>");
			
			respEnty.append("<ExtendContent>");
			respEnty.append(DOMXMLReader.getElementValue(sysHead, "ExtendContent"));
			respEnty.append("</ExtendContent>");
			
			respEnty.append("<ReplyAdr>");
			respEnty.append(DOMXMLReader.getElementValue(sysHead, "ReplyAdr"));
			respEnty.append("</ReplyAdr>");
		respEnty.append("</SYS_HEAD>");
		
		Element appHead = root.element("APP_HEAD");
		respEnty.append("<APP_HEAD>");
			respEnty.append("<AuthrCardFlag>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "AuthrCardFlag"));
			respEnty.append("</AuthrCardFlag>");
			
			respEnty.append("<AuthrCardNo>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "AuthrCardNo"));
			respEnty.append("</AuthrCardNo>");
			
			respEnty.append("<AuthrPwd>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "AuthrPwd"));
			respEnty.append("</AuthrPwd>");
			
			respEnty.append("<AuthrTellerNo>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "AuthrTellerNo"));
			respEnty.append("</AuthrTellerNo>");
			
			respEnty.append("<BranchId>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "BranchId"));
			respEnty.append("</BranchId>");
			
			respEnty.append("<CityCode>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "CityCode"));
			respEnty.append("</CityCode>");
			
			respEnty.append("<LangCode>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "LangCode"));
			respEnty.append("</LangCode>");
			
			respEnty.append("<TerminalCode>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "TerminalCode"));
			respEnty.append("</TerminalCode>");
			
			respEnty.append("<TranDate>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "TranDate"));
			respEnty.append("</TranDate>");
			
			respEnty.append("<TranSeqNo>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "TranSeqNo"));
			respEnty.append("</TranSeqNo>");
			
			respEnty.append("<TranTellerNo>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "TranTellerNo"));
			respEnty.append("</TranTellerNo>");
			
			respEnty.append("<TranTime>");
			respEnty.append(DOMXMLReader.getElementValue(appHead, "TranTime"));
			respEnty.append("</TranTime>");

			respEnty.append("<ReturnCode>");
			respEnty.append(StringUtils.valueOf(res.getCODE()));
			respEnty.append("</ReturnCode>");
			
			respEnty.append("<ReturnMsg>");
			respEnty.append(StringUtils.valueOf(res.getDESC()));
			respEnty.append("</ReturnMsg>");
			
		respEnty.append("</APP_HEAD>");
	}

	/**
	 * 审批系统自定义规范异常处理
	 * @param start
	 * @param res
	 * @param e
	 */
	public void processEsbSpecExceptions(Long start, ResponseType res, Exception e) {
		res.setSTATUS("F");
		if (e instanceof ProcessException) {
			logger.error("PID["+start+"]....服务处理错误,错误码{}，错误消息{}",
					((ProcessException) e).getErrorCode(), e.getMessage());
			// 报文格式验证错误，返回响应报文
			ProcessException processexecption = (ProcessException) e;
			res.setSTATUS("F");
			if (StringUtils.isEmpty(processexecption.getErrorCode())) {
				res.setCODE(SysConstants.ERR_ESB_S001_CODE);
				if (StringUtils.isEmpty(e.getMessage())) {
					res.setDESC(SysConstants.ERR_ESB_S001_CODE);
				} else {
					res.setDESC(e.getMessage());
				}
			} else {
				res.setCODE(processexecption.getErrorCode());
				if (processexecption.getMessage() != null)
					res.setDESC(processexecption.getMessage());
				else {
					if (StringUtils.isEmpty(e.getMessage())) {
						res.setDESC("未定义的错误");
					} else {
						res.setDESC(e.getMessage());
					}
				}
			}
		} else if (e instanceof DocumentException) {
			logger.error("请求报文解析异常", e);
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERR_ESB_S013_CODE);
			res.setDESC(SysConstants.ERR_ESB_S013_MES);
		} else if (e instanceof AmqpConnectException) {
			logger.error("PID["+start+"]....RPC服务响应超时错误消息{}", e);
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERR_ESB_B202_CODE);
			res.setDESC(SysConstants.ERR_ESB_B202_MES);
		} else if (e instanceof IllegalArgumentException) {
			logger.error("PID["+start+"]....参数异常{}", e);
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERR_ESB_S001_CODE);
			res.setDESC(SysConstants.ERR_ESB_S001_MES);
		} else if (e instanceof TimeoutException) {
			logger.error("PID["+start+"]....RPC服务响应超时错误消息{}", e);
			res.setSTATUS("F");
			res.setCODE(SysConstants.ERR_ESB_B202_CODE);
			res.setDESC(SysConstants.ERR_ESB_B202_MES);
		} else {
			String[] errInfo = e.toString().split(":");
			if(errInfo.length > 1){
				logger.error("PID["+start+"]....系统处理失败:" + errInfo[1], e);
				res.setSTATUS("F");
				res.setCODE(SysConstants.ERR_ESB_S001_CODE);
				res.setDESC("系统处理失败:" + errInfo[1]);
			}else{
				logger.error("PID["+start+"]....系统处理失败", e);
				res.setCODE(SysConstants.ERR_ESB_S001_CODE);
				res.setDESC("系统处理失败");
			}
		}
	}
	
	/**
	 * 生成响应报文 responseProcess(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选) void
	 * 
	 * @exception
	 * @since 1.0.0
	 */
	public ResponseType initEsbResp(ResponseType res) {
		res.setSTATUS("S");
		res.setCODE(SysConstants.ESB_SUCCESS_CODE);
		res.setDESC("交易成功");
		return res;
	}


	/**
	 * 过滤String 字符串中不可见的字符
	 * 
	 * @param in
	 * @return
	 */
	public static String stripNonValidCharacters(String in) {
		String str = "";
		if (in != null && !in.equals("")) {
			StringBuffer out = new StringBuffer(); // Used to hold the output.
			Pattern p = Pattern.compile("\\s+");
			Matcher m = p.matcher(in);
			in = m.replaceAll(" ");
			in = in.replace("  ", " ");

			char current; // Used to reference the current character.

			if (in == null || ("".equals(in)))
				return ""; // vacancy test.
			for (int i = 0; i < in.length(); i++) {
				current = in.charAt(i); // NOTE: No IndexOutOfBoundsException
										// caught
										// here; it should not happen.
				if ((current == 0x9) || (current == 0xA) || (current == 0xD)
						|| ((current >= 0x20) && (current <= 0xD7FF))
						|| ((current >= 0xE000) && (current <= 0xFFFD))
						|| ((current >= 0x10000) && (current <= 0x10FFFF)))
					out.append(current);
			}
			str = out.toString();
		}
		return str;
	}
	
	public <T> String convertToStr(T clazz) {
		Field[] fields = clazz.getClass().getDeclaredFields();
		StringBuffer strbBuf = new StringBuffer();
		for (int i = 0; i < fields.length; i++) {
			String nodeName = fields[i].getName();
			if("serialVersionUID".equals(nodeName)){
				continue;
			}
			nodeName = (new StringBuffer()).append(Character.toUpperCase(nodeName.charAt(0)))
					.append(nodeName.substring(1)).toString();
			if("Custs".equals(nodeName)){
				continue;
			}
			if("Cust".equals(nodeName)){
				continue;
			}
			strbBuf.append("<");
			strbBuf.append(StringUtils.valueOf(nodeName));
			try {
				Object nodeValue = fields[i].get(clazz);
				if(nodeValue != null && StringUtils.isNotBlank(nodeValue.toString())){
					strbBuf.append(">");
					if(fields[i].getType().equals(Date.class)){
						strbBuf.append(StringUtils.valueOf(DateUtils.dateToString((Date)nodeValue, DateUtils.FULL_THRID_LINE)));
					}else {
						strbBuf.append(StringUtils.valueOf(nodeValue.toString().trim()));
					}
					strbBuf.append("</");
					strbBuf.append(StringUtils.valueOf(nodeName));
					strbBuf.append(">");
				}else {
					strbBuf.append("/>");
				}
			} catch (IllegalArgumentException e) {
				logger.error("模型[{}]数据[{}]转换发生异常",clazz.toString(),nodeName);
				throw new ProcessException("模型[clazz.toString()]数据转换发生异常"+e.getMessage());
			} catch (IllegalAccessException e) {
				logger.error("模型[{}]数据[{}]转换发生异常",clazz.toString(),nodeName);
				throw new ProcessException("模型[clazz.toString()]数据转换发生异常"+e.getMessage());
			}
		}
		
		return strbBuf.toString();
	}

}
