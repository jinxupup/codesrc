package com.jjb.unicorn.facility.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.jjb.unicorn.facility.service.YakMessage;

/**
 * <p>Description: 交易日志处理器</p>
 * <p>Company: 上海JYDATA服务有限公司</p>
 * @ClassName: TradeLogHandler
 * @author LI.H
 * @date 2016年3月11日 下午4:36:06
 * @version 1.0
 */
public class TradeLogHandler implements InitializingBean {

	/**
	 * <p>正常日志输出</p>
	 */
	private static final Logger logger = LoggerFactory.getLogger(TradeLogHandler.class);
	
	/**
	 * <p>Lol4j日志类型</p>
	 */
	protected static final String LOG_NAME_TRADE = "trade";
	
	/**
	 * <p>交易监控日志输出</p>
	 */
	private static Logger tradeLogger = LoggerFactory.getLogger(LOG_NAME_TRADE);
	
	/**
	 * <p>交易TYPE需要的关键域, 逗号分隔</p>
	 */
	@Value("#{env['tradeTypeKey']?:''}")
	private String tradeTypeKey;
	
	/**
	 * <p>交易ID需要的关键域, 逗号分隔</p>
	 */
	@Value("#{env['tradeIdKey']?:''}")
	private String tradeIdKey;
	
	/**
	 * <p>交易VAL需要的关键域, 逗号分隔</p>
	 */
	@Value("#{env['tradeValKey']?:''}")
	private String tradeValKey;
	
	/**
	 * <p>在本次交易输出过程中需要进行加密的关键域, 逗号分隔</p>
	 * 每个域可以包含属性：域号:0,域号:1,域号,域号，其中0表示卡号域，默认为1
	 */
	private String tradeEncryptionKey;
	
	private int[] tradeTypeKeyArrays;
	
	private int[] tradeIdKeyArrays;
	
	private int[] tradeValKeyArrays;
	
	private Map<Integer, Boolean> tradeEncryptionMap = new HashMap<Integer, Boolean>();
	
	protected final static String MTI_KEY = "com.jjb.osp.tran.mti";
	private final static int MTI_INDEX = 1;
	/**
	 * <p>机构号</p>
	 */
	private String orgName;
	
	/**
	 * <p>渠道</p>
	 */
	private String input;
	
	
	private static final String SPLIT_KEY = ",";
	
	private static final String TRADE_SPLIT = "|";
	
	private static final String VISA_INPUT = "VISA";
	
	private static final String ICL_INPUT = "ICL";
	
	//机构号：[#{ORG}]，交易渠道：[#{INP_SRC}]，请求时间：[#{REQ_TIME}]，TRADE_TYPE：[#{TRADE_TYPE}]，
	//TRADE_ID：[#{TRADE_ID}]，TRADE_VAL：[#{TRADE_VAL}]
	private static final String FORMAT_KEY_ORG = "#{ORG}";
	private static final String FORMAT_KEY_DIRECTION = "#{DIRECTION}";
	private static final String FORMAT_KEY_INP_SRC = "#{INP_SRC}";
	private static final String FORMAT_KEY_REQ_TIME = "#{REQ_TIME}";
	private static final String FORMAT_KEY_RSP_TIME = "#{RSP_TIME}";
	private static final String FORMAT_KEY_TRADE_TYPE = "#{TRADE_TYPE}";
	private static final String FORMAT_KEY_TRADE_ID = "#{TRADE_ID}";
	private static final String FORMAT_KEY_TRADE_VAL = "#{TRADE_VAL}";
	
	//机构号：[#{ORG}]，请求时间：[#{REQ_TIME}]，#{LOG_CONTENT}
	private static final String FORMAT_KEY_LOG_CONTENT = "#{LOG_CONTENT}";
	
	private static final String FORMAT_KEY_LOC_TRADE_ID = "#{LOC_TRADE_ID}";
	
	@Value("#{env['tradeLogTimeFormat']?:''}")
	private String tradeLogTimeFormat;
	
	@Value("#{env['mtiIndex']?:1}")
	private int mtiIndex;
	
	@Autowired
	private CodeMarkUtils codeMarkUtils;
	
	@Value("#{env['financeReqLogFormat']?:''}")
	private String financeReqLogFormat;
	
	@Value("#{env['financeRspLogFormat']?:''}")
	private String financeRspLogFormat;

	@Value("#{env['nonfinanceReqLogFormat']?:''}")
	private String nonfinanceReqLogFormat;
	
	@Value("#{env['nonfinanceRspLogFormat']?:''}")
	private String nonfinanceRspLogFormat;
	
	/**
	 * 获取报文的MTI
	 * @param message
	 * @return
	 */
	public String getMTI(YakMessage message) {
		String MTI = message.getHeadAttributes().get(mtiIndex);
		if(input.equals(VISA_INPUT)){
			MTI = (String)message.getCustomAttributes().get(MTI_KEY);
		}
		if(input.equals(ICL_INPUT)){
			MTI = message.getBodyAttributes().get(1);
			if(StringUtils.isBlank(MTI)){
				MTI = (String)message.getCustomAttributes().get(MTI_KEY);
			}
		}
		
		return MTI;
	}
	
	/**
	 * <p>目的：简单记录YakMessage对象交易日志（ISO-8583报文）</p>
	 * <p>承诺：无任何业务逻辑，单纯记录入职</p>
	 * @param message 内部交换消息
	 * @param reqFlag 是否请求交易：true为请求，false为响应
	 * @param toLocal(本系统) 交易方向：true为Local接收报文，false为Local发送报文
	 */
	public void simpleYakMessageTradeLog(YakMessage message, boolean reqFlag, boolean toLocal) {
		String MTI = getMTI(message);
		StringBuffer tradeType = new StringBuffer("");
		for (int i = 0; i < tradeTypeKeyArrays.length; i++) {
			if(i > 0) {
				tradeType.append(TRADE_SPLIT);
			}
			tradeType.append(getValForIndex(message, tradeTypeKeyArrays[i], MTI));
		}
//		tradeType.append(TRADE_SPLIT).append(getValForIndex(message, 3, MTI));
//		tradeType.append(TRADE_SPLIT).append(getValForIndex(message, 25, MTI));
//		if(tradeTypeKeyArrays.length > 0) {
//			tradeType.append(TRADE_SPLIT);
//		}
//		tradeType.append(input);
		
		StringBuffer tradeId = new StringBuffer("");
		for (int i = 0; i < tradeIdKeyArrays.length; i++) {
			if(i > 0) {
				tradeId.append(TRADE_SPLIT);
			}
			tradeId.append(getValForIndex(message, tradeIdKeyArrays[i], MTI));
		}
		
		StringBuffer tradeVal = new StringBuffer("");
		for (int i = 0; i < tradeValKeyArrays.length; i++) {
			if(i > 0) {
				tradeVal.append(TRADE_SPLIT);
			}
			tradeVal.append(getValForIndex(message, tradeValKeyArrays[i], MTI));
		}
		
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put(FORMAT_KEY_ORG, orgName);
		logMap.put(FORMAT_KEY_DIRECTION, toLocal ? "I" : "O");
		logMap.put(FORMAT_KEY_INP_SRC, input);
		logMap.put(FORMAT_KEY_TRADE_TYPE, tradeType.toString());
		logMap.put(FORMAT_KEY_TRADE_ID, tradeId.toString());
		logMap.put(FORMAT_KEY_TRADE_VAL, tradeVal.toString());
		
		String locTradeId=AmqpContextHolder.getJydSrcTradeId();
		if(reqFlag){
			if(locTradeId == null){
				locTradeId=TradeIdWorker.getInstance().getLocTraderId();
				AmqpContextHolder.setJydSrcTradeId(locTradeId);
			}
			logMap.put(FORMAT_KEY_LOC_TRADE_ID, locTradeId);
		}else{
			logMap.put(FORMAT_KEY_LOC_TRADE_ID, locTradeId != null ? locTradeId:"");
			AmqpContextHolder.setJydSrcTradeId(null);
		}
		if (reqFlag) {
//			tradeLogger.info("机构号：[{}]，交易渠道：[{}]，请求时间：[{}]，TRADE_TYPE：[{}]，TRADE_ID：[{}]，TRADE_VAL：[{}]",
//							orgName, input, new SimpleDateFormat(tradeInfoTimeFormat).format(new Date()), tradeType.toString(), tradeId.toString(), tradeVal.toString());
			logMap.put(FORMAT_KEY_REQ_TIME, new SimpleDateFormat(tradeLogTimeFormat).format(new Date()));
			tradeLogger.info(buildTradeLog(financeReqLogFormat, logMap));
		} else {
//			tradeLogger.info("机构号：[{}]，交易渠道：[{}]，响应时间：[{}]，TRADE_TYPE：[{}]，TRADE_ID：[{}]，TRADE_VAL：[{}]",
//							orgName, input, new SimpleDateFormat(tradeInfoTimeFormat).format(new Date()), tradeType.toString(), tradeId.toString(), tradeVal.toString());
			logMap.put(FORMAT_KEY_RSP_TIME, new SimpleDateFormat(tradeLogTimeFormat).format(new Date()));
			tradeLogger.info(buildTradeLog(financeRspLogFormat, logMap));
		}
		
	}
	
	/**
	 * <p>目的：简单交易日志打印，直接打印传入的消息</p>
	 * <p>承诺：直接打印传入的消息</p>
	 * @param org 机构号, 如果org为null则取orgName
	 * @param message 需要打印的消息
	 * @param reqFlag 交易方向：true为请求，false为响应
	 * @param toLocal 交易方向：true为Local接收报文，false为Local发送报文
	 */
	public void simpleOtherTradeLog(String org, String message, boolean reqFlag, boolean toLocal) {
		Map<String, String> logMap = new HashMap<String, String>();
		logMap.put(FORMAT_KEY_ORG, org != null ? org : orgName);
		logMap.put(FORMAT_KEY_DIRECTION, toLocal ? "I" : "O");
		logMap.put(FORMAT_KEY_LOG_CONTENT, message);
		
		if (reqFlag) {
//			tradeLogger.info("机构号：[{}]，请求时间：[{}]，{}",
//									org, new SimpleDateFormat(tradeInfoTimeFormat).format(new Date()), message);
			logMap.put(FORMAT_KEY_REQ_TIME, new SimpleDateFormat(tradeLogTimeFormat).format(new Date()));
			tradeLogger.info(buildTradeLog(nonfinanceReqLogFormat, logMap));
		} else {
//			tradeLogger.info("机构号：[{}]，响应时间：[{}]，{}",
//									org, new SimpleDateFormat(tradeInfoTimeFormat).format(new Date()), message);
			logMap.put(FORMAT_KEY_RSP_TIME, new SimpleDateFormat(tradeLogTimeFormat).format(new Date()));
			tradeLogger.info(buildTradeLog(nonfinanceRspLogFormat, logMap));
			
			AmqpContextHolder.setJydSrcTradeId(null);
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
	
	/**
	 * <p>目的：根据传入的index获取对应的值</p>
	 * <p>承诺：此方法会判断是否需要对该值进行加密处理</p>
	 * @param message 消息对象
	 * @param index 对应索引
	 * @return
	 */
	private String getValForIndex(YakMessage message, Integer index, String mti) {
		if(index == MTI_INDEX) {
			return mti;
		}
		
		if (tradeEncryptionMap.containsKey(index)) {
			if (tradeEncryptionMap.get(index)) {
				return codeMarkUtils.makeTradeMask(message.getBody(index));
			} else {
				return codeMarkUtils.makeTradeCardMask(message.getBody(index));
			}
		} else {
			String val = message.getBody(index);
			return StringUtils.isBlank(val) ? "" : val;
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		if (StringUtils.isBlank(tradeTypeKey)) {
			tradeTypeKeyArrays = new int[]{1, 3, 25};
			logger.info("环境变量对应的tradeTypeKey为空，将使用默认值:"+Arrays.toString(tradeTypeKeyArrays));
		} else {
			String [] tradeTypeKeyTemp = tradeTypeKey.split(SPLIT_KEY);
			tradeTypeKeyArrays = new int[tradeTypeKeyTemp.length];
			for (int i = 0; i < tradeTypeKeyTemp.length; i++) {
				tradeTypeKeyArrays[i] = Integer.parseInt(tradeTypeKeyTemp[i].trim());
				
			}
			logger.info("环境变量对应的tradeTypeKey不为空，将使用[{}]，解析后:{}", tradeTypeKey, Arrays.toString(tradeTypeKeyArrays));
		}
		
		if (StringUtils.isBlank(tradeIdKey)) {
			tradeIdKeyArrays = new int[]{1, 2, 4, 6, 7, 11, 32, 37};
			logger.info("环境变量对应的tradeIdKey为空，将使用默认值:"+Arrays.toString(tradeIdKeyArrays));
		} else {
			String [] tradeIdKeyTemp = tradeIdKey.split(SPLIT_KEY);
			tradeIdKeyArrays = new int[tradeIdKeyTemp.length];
			for (int i = 0; i < tradeIdKeyTemp.length; i++) {
				tradeIdKeyArrays[i] = Integer.parseInt(tradeIdKeyTemp[i].trim());
				
			}
			logger.info("环境变量对应的tradeIdKey不为空，将使用[{}]， 解析后:{}", tradeIdKey, Arrays.toString(tradeIdKeyArrays));
		}
		
		if (StringUtils.isBlank(tradeValKey)) {
			tradeValKeyArrays = new int[]{5, 49, 50, 51, 18, 19, 22, 33, 38, 39, 41, 42, 43};
			logger.info("环境变量对应的tradeValKey为空，将使用默认值:"+Arrays.toString(tradeValKeyArrays));
		} else {
			String [] tradeValKeyTemp = tradeValKey.split(SPLIT_KEY);
			tradeValKeyArrays = new int[tradeValKeyTemp.length];
			for (int i = 0; i < tradeValKeyTemp.length; i++) {
				tradeValKeyArrays[i] = Integer.parseInt(tradeValKeyTemp[i].trim());
				
			}
			logger.info("环境变量对应的tradeValKey不为空，将使用[{}]， 解析后:{}", tradeValKey, Arrays.toString(tradeValKeyArrays));
		}
		
		if (StringUtils.isBlank(tradeEncryptionKey)) {
			tradeEncryptionMap.put(2, false);
			tradeEncryptionMap.put(35, true);
			tradeEncryptionMap.put(36, true);
			tradeEncryptionMap.put(45, true);
			tradeEncryptionMap.put(48, true);
			tradeEncryptionMap.put(52, true);
			tradeEncryptionMap.put(55, true);
			tradeEncryptionMap.put(57, true);
			tradeEncryptionMap.put(61, true);
			tradeEncryptionMap.put(102, true);
			tradeEncryptionMap.put(103, true);
			logger.info("环境变量对应的tradeEncryptionKey为空，将使用默认值:"+tradeEncryptionMap.entrySet());
		} else {
			retrieveTradeEncryptFields(tradeEncryptionKey, tradeEncryptionMap);
			logger.info("环境变量对应的tradeEncryptionKey不为空，将使用[{}]，解析后:{}", tradeEncryptionKey, tradeEncryptionMap.entrySet());
		}
		
		
		if(StringUtils.isBlank(tradeLogTimeFormat)) {
			tradeLogTimeFormat = "yyyyMMdd HHmmssSSS";
			logger.info("环境变量对应的tradeLogTimeFormat为空，将使用默认值:"+tradeLogTimeFormat);
		} else {
			logger.info("环境变量对应的tradeLogTimeFormat不为空，将使用:"+tradeLogTimeFormat);
		}
		
		if(StringUtils.isBlank(financeReqLogFormat)) {
			financeReqLogFormat = "机构号：["+FORMAT_KEY_ORG+"]，交易渠道：["+FORMAT_KEY_INP_SRC+"]，请求时间：["+FORMAT_KEY_REQ_TIME+"]，TRADE_TYPE：["+FORMAT_KEY_TRADE_TYPE+"|"+FORMAT_KEY_INP_SRC+"]，TRADE_ID：["+FORMAT_KEY_TRADE_ID+"]，TRADE_VAL：["+FORMAT_KEY_TRADE_VAL+"]，交易方向：[#{DIRECTION}]，LOC_TRADE_ID：["+FORMAT_KEY_LOC_TRADE_ID+"]";
			logger.info("环境变量对应的financeReqLogFormat为空，将使用默认值:"+financeReqLogFormat);
		} else {
			logger.info("环境变量对应的financeReqLogFormat不为空，将使用:"+financeReqLogFormat);
		}
		
		if(StringUtils.isBlank(financeRspLogFormat)) {
			financeRspLogFormat = "机构号：["+FORMAT_KEY_ORG+"]，交易渠道：["+FORMAT_KEY_INP_SRC+"]，响应时间：["+FORMAT_KEY_RSP_TIME+"]，TRADE_TYPE：["+FORMAT_KEY_TRADE_TYPE+"|"+FORMAT_KEY_INP_SRC+"]，TRADE_ID：["+FORMAT_KEY_TRADE_ID+"]，TRADE_VAL：["+FORMAT_KEY_TRADE_VAL+"]，交易方向：[#{DIRECTION}]，LOC_TRADE_ID：["+FORMAT_KEY_LOC_TRADE_ID+"]";
			logger.info("环境变量对应的financeRspLogFormat为空，将使用默认值:"+financeRspLogFormat);
		} else {
			logger.info("环境变量对应的financeRspLogFormat不为空，将使用:"+financeRspLogFormat);
		}
		
		if(StringUtils.isBlank(nonfinanceReqLogFormat)) {
			nonfinanceReqLogFormat = "机构号：["+FORMAT_KEY_ORG+"]，请求时间：["+FORMAT_KEY_REQ_TIME+"]，交易方向：[#{DIRECTION}]，"+FORMAT_KEY_LOG_CONTENT;
			logger.info("环境变量对应的nonfinanceReqLogFormat为空，将使用默认值:"+nonfinanceReqLogFormat);
		} else {
			logger.info("环境变量对应的nonfinanceReqLogFormat不为空，将使用:"+nonfinanceReqLogFormat);
		}
		
		if(StringUtils.isBlank(nonfinanceRspLogFormat)) {
			nonfinanceRspLogFormat = "机构号：["+FORMAT_KEY_ORG+"]，响应时间：[#{RSP_TIME}]，交易方向：[#{DIRECTION}]，"+FORMAT_KEY_LOG_CONTENT;
			logger.info("环境变量对应的nonfinanceRspLogFormat为空，将使用默认值:"+nonfinanceRspLogFormat);
		} else {
			logger.info("环境变量对应的nonfinanceRspLogFormat不为空，将使用:"+nonfinanceRspLogFormat);
		}
		
	}
	
	/**
	 * 解析敏感域
	 * @param tradeEncryptionKey
	 * @param tradeEncryptionMap
	 */
	private static void retrieveTradeEncryptFields(String tradeEncryptionKey, Map<Integer, Boolean> tradeEncryptionMap) {
		String [] tradeEncryptionKeyTemp = tradeEncryptionKey.split(SPLIT_KEY);
		for (int i = 0; i < tradeEncryptionKeyTemp.length; i++) {
			tradeEncryptionKeyTemp[i] = tradeEncryptionKeyTemp[i].trim();
			if(tradeEncryptionKeyTemp[i].length() == 0) {
				continue;
			}
			
			//解析每个域的属性：域号:0,域号:1,域号:1,域号:1，其中0表示false表示卡号域，默认为1
			String keyVal[] = tradeEncryptionKeyTemp[i].split(":");
			keyVal[0] = keyVal[0].trim();
			if(keyVal.length > 1) {
				keyVal[1] = keyVal[1].trim();
			}
			
			Integer key = Integer.parseInt(keyVal[0]);
			
			if(keyVal.length == 1 || keyVal[1].length() == 0) {
				tradeEncryptionMap.put(key, key != 2);//第二域默认为卡号域
			} else {
				tradeEncryptionMap.put(key, !keyVal[1].equals("0"));
			}
			
		}
	}

	public void setTradeTypeKey(String tradeTypeKey) {
		this.tradeTypeKey = tradeTypeKey;
	}

	public void setTradeIdKey(String tradeIdKey) {
		this.tradeIdKey = tradeIdKey;
	}

	public void setTradeValKey(String tradeValKey) {
		this.tradeValKey = tradeValKey;
	}
	
	public void setTradeEncryptionKey(String tradeEncryptionKey) {
		this.tradeEncryptionKey = tradeEncryptionKey;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
	
	public static void main(String[] args) throws Exception {
		YakMessage yakMsg = new YakMessage();
		yakMsg.getHeadAttributes().put(1, "0200");
		yakMsg.getBodyAttributes().put(2, "62222");
		yakMsg.getBodyAttributes().put(6, "100200");
		yakMsg.getBodyAttributes().put(43, "haoyouduo");
		
		CodeMarkUtils utils = new CodeMarkUtils();
		
		TradeLogHandler tr = new TradeLogHandler();
		tr.mtiIndex = 1;
		tr.codeMarkUtils = utils;
		tr.afterPropertiesSet();
		tr.setInput("CUP");
		tr.setOrgName("000000006559");
		tr.simpleYakMessageTradeLog(yakMsg, true, true);
		tr.simpleYakMessageTradeLog(yakMsg, false, false);
		
		tr.simpleOtherTradeLog("000000006559", "请求消息", true, false);
		tr.simpleOtherTradeLog(null, "响应消息", false, true);
	}

}
