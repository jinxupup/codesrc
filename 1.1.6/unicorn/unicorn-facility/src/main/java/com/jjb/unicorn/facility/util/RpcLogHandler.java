package com.jjb.unicorn.facility.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import com.jjb.unicorn.facility.service.YakMessage;

/**
 * <p>Description: 交易日志处理器</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: RpcLogHandler
 * @author H.N
 * @date 2017年7月24日 下午6:44:00
 * @version 1.0
 */
public class RpcLogHandler implements InitializingBean {

	/**
	 * <p>正常日志输出</p>
	 */
	private static final Logger logger = LoggerFactory.getLogger(RpcLogHandler.class);
	
	/**
	 * <p>Lol4j日志类型</p>
	 */
	protected static final String LOG_NAME_TRADE = "rpc";
	
	/**
	 * <p>交易监控日志输出</p>
	 */
	private static Logger tradeLogger = LoggerFactory.getLogger(LOG_NAME_TRADE);
	/**
	 * <p>机构号</p>
	 */
	private String orgName;
	
	
	//机构号：[#{ORG}]，交易渠道：[#{INP_SRC}]，请求时间：[#{REQ_TIME}]，TRADE_TYPE：[#{TRADE_TYPE}]，
	//TRADE_ID：[#{TRADE_ID}]，TRADE_VAL：[#{TRADE_VAL}]
	private static final String FORMAT_KEY_ORG = "#{ORG}";
	private static final String FORMAT_KEY_DIRECTION = "#{DIRECTION}";
	private static final String FORMAT_KEY_REQ_TIME = "#{REQ_TIME}";
	private static final String FORMAT_KEY_RSP_TIME = "#{RSP_TIME}";
	
	//机构号：[#{ORG}]，请求时间：[#{REQ_TIME}]，#{LOG_CONTENT}
	private static final String FORMAT_KEY_LOG_CONTENT = "#{LOG_CONTENT}";
	
	@Value("#{env['tradeLogTimeFormat']?:''}")
	private String tradeLogTimeFormat;
	

	@Value("#{env['rpcReqLogFormat']?:''}")
	private String rpcReqLogFormat;
	
	@Value("#{env['rpcRspLogFormat']?:''}")
	private String rpcRspLogFormat;
	
	/**
	 * <p>目的：简单交易日志打印，直接打印传入的消息</p>
	 * <p>承诺：直接打印传入的消息</p>
	 * @param org 机构号, 如果org为null则取orgName
	 * @param message 需要打印的消息
	 * @param reqFlag 交易方向：true为请求，false为响应
	 * @param toMe 交易方向：true为当前模块接收消息，false为当前模块发送消息
	 */
	public void simpleOtherTradeLog(String org, String message, boolean reqFlag, boolean toMe) {
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put(FORMAT_KEY_ORG, org != null ? org : orgName);
		logMap.put(FORMAT_KEY_DIRECTION, toMe ? "I" : "O");
		logMap.put(FORMAT_KEY_LOG_CONTENT, message);
		
		if (reqFlag) {
//			tradeLogger.info("机构号：[{}]，请求时间：[{}]，{}",
//									org, new SimpleDateFormat(tradeInfoTimeFormat).format(new Date()), message);
			logMap.put(FORMAT_KEY_REQ_TIME, new SimpleDateFormat(tradeLogTimeFormat).format(new Date()));
			tradeLogger.info(buildTradeLog(rpcReqLogFormat, logMap));
		} else {
//			tradeLogger.info("机构号：[{}]，响应时间：[{}]，{}",
//									org, new SimpleDateFormat(tradeInfoTimeFormat).format(new Date()), message);
			logMap.put(FORMAT_KEY_RSP_TIME, new SimpleDateFormat(tradeLogTimeFormat).format(new Date()));
			tradeLogger.info(buildTradeLog(rpcRspLogFormat, logMap));
		}
		
	}
	
	/**
	 * 占位符替换
	 * @param logFormat
	 * @param keyList
	 * @param valueList
	 * @return
	 */
	private String buildTradeLog(String logFormat, Map<String, String> logMap) {
		String logContent = logFormat;
		
		for(String key : logMap.keySet()) {
			logContent = StringUtils.replace(logContent, key, logMap.get(key));
		}
		
		return logContent;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		if(StringUtils.isBlank(tradeLogTimeFormat)) {
			tradeLogTimeFormat = "yyyyMMdd HHmmssSSS";
			logger.info("环境变量对应的tradeLogTimeFormat为空，将使用默认值:"+tradeLogTimeFormat);
		} else {
			logger.info("环境变量对应的tradeLogTimeFormat不为空，将使用:"+tradeLogTimeFormat);
		}
		
		if(StringUtils.isBlank(rpcReqLogFormat)) {
			rpcReqLogFormat = "机构号：["+FORMAT_KEY_ORG+"]，请求时间：["+FORMAT_KEY_REQ_TIME+"]，交易方向：[#{DIRECTION}]，"+FORMAT_KEY_LOG_CONTENT;
			logger.info("环境变量对应的rpcReqLogFormat为空，将使用默认值:"+rpcReqLogFormat);
		} else {
			logger.info("环境变量对应的rpcReqLogFormat不为空，将使用:"+rpcReqLogFormat);
		}
		
		if(StringUtils.isBlank(rpcRspLogFormat)) {
			rpcRspLogFormat = "机构号：["+FORMAT_KEY_ORG+"]，响应时间：[#{RSP_TIME}]，交易方向：[#{DIRECTION}]，"+FORMAT_KEY_LOG_CONTENT;
			logger.info("环境变量对应的rpcRspLogFormat为空，将使用默认值:"+rpcRspLogFormat);
		} else {
			logger.info("环境变量对应的rpcRspLogFormat不为空，将使用:"+rpcRspLogFormat);
		}
		
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	
	public static void main(String[] args) throws Exception {
		YakMessage yakMsg = new YakMessage();
		yakMsg.getHeadAttributes().put(1, "0200");
		yakMsg.getBodyAttributes().put(2, "62222");
		yakMsg.getBodyAttributes().put(6, "100200");
		yakMsg.getBodyAttributes().put(43, "haoyouduo");
		
		
		RpcLogHandler tr = new RpcLogHandler();
		tr.afterPropertiesSet();
		tr.setOrgName("000000006559");
		
		tr.simpleOtherTradeLog("000000006559", "请求消息", true, false);
		tr.simpleOtherTradeLog(null, "响应消息", false, true);
	}

}
