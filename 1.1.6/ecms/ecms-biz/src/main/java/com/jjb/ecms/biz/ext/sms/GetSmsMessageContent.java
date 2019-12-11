package com.jjb.ecms.biz.ext.sms;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jjb.acl.facility.enums.bus.Gender;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.unicorn.facility.util.StringUtils;

@Component
public class GetSmsMessageContent {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM月dd日");
	
	private static DateFormat TIME_FORMAT2 = new SimpleDateFormat("HH:mm");
	
	private static Map<Class<?>, Map<String, String>> ENUM_DESC_MAP = new ConcurrentHashMap<Class<?>, Map<String,String>>();
	
	//检测短信内容中是否包含无效变量
	private static final String MSG = "${";
//	@Autowired
//	private CodeMarkUtils codeMarkUtils;
	
	public  String getMessageContent(String appNo,String org,String smsType,String msgCd,String cardNo, String name,
			Gender gender,String cellPhone,Map<String, Object> params,long start1){
		String msgContent =null;
		Date txnTime = new Date();
		if(StringUtils.isEmpty(msgCd)){
			logger.error("SMS-短信发送失败,未获取到短信模版["+smsType+"]"+ LogPrintUtils.printAppNoEndLog(appNo, start1,null));
			return null;
		}else{
			logger.debug("短信模板号为:{}",msgCd);
//			Organization organization = parameterFacility.loadParameter(null,
//					Organization.class);
//			// 将必填内容补齐到信息参数中
//			if (params == null)
//				params = new HashMap<String, Object>();
//			params.put("NAME", name);
//			params.put("GENDER", gender == Gender.F ? "女士" : "先生");
//			if(cardNo != null){
//				params.put("CARD_NO", CodeMarkUtils.subCreditCard(cardNo));
//				params.put("CARD_NO_E4", CodeMarkUtils.subCreditCard(cardNo));
//			}
//			params.put("TXN_DATE", DATE_FORMAT.format(txnTime));
//			params.put("TXN_TIME", TIME_FORMAT2.format(txnTime));
//			params.put("ORG_NAME", organization.name);
//			params.put("ORG_CSS_PHONE", organization.cssPhone);
//
//			Set<String> keys = params.keySet();
//			Iterator<String> it = keys.iterator();  	
//			while(it.hasNext()){
//				String key = it.next();
//				if((key.equals("newPhone")||key.equals("oldPhone"))&&(!(key.contains("-"))))
//					logger.info(key + ":" + codeMarkUtils.makeMask((String) params.get(key)));
//				else
//					logger.info(key + ":" + params.get(key));
//			}
//			BMPMessageTemplate template = parameterFacility.loadParameter(msgCd, BMPMessageTemplate.class);
//			 msgContent  = formatMsgContent(template, params);
//			if(msgContent.contains(MSG)){
//				logger.error("短信内容为{},其中包含：{}字符,模板编号：{},机构号：{}",msgContent, MSG, msgCd, OrganizationContextHolder.getCurrentOrg());
//			}
			return msgContent;
		}
	}
	
	/*
public  String formatMsgContent(BMPMessageTemplate template, Map<String, Object> params) {
		
		String content = template.contentTemplate;
		
		for (Entry<String, Object> entry : params.entrySet()) {
			if (entry.getValue() == null)
				logger.warn("模版[{}]对应的数据[{}]值为null", template.code, entry.getKey());

			// 需要对日期等数据进行格式化
			String value;
			if (entry.getValue() == null) {
				value = "";
			} else {
				Class<?> clazz = entry.getValue().getClass();
				if (clazz.isEnum()) { // 枚举
					if (!ENUM_DESC_MAP.containsKey(clazz)) {
						EnumInfo enumInfo = clazz.getAnnotation(EnumInfo.class);
						
						Map<String, String> enumDescMap = new HashMap<String, String>();
						for (String info : enumInfo.value()) {
							String[] infos = org.apache.commons.lang.StringUtils.split(info, "|");
							if (infos == null || infos.length != 2) {
								logger.warn("枚举类{}的EnumInfo描述无法解析", clazz, entry.getKey());
							} else {
								enumDescMap.put(infos[0], infos[1]);
							}
						}
						
						ENUM_DESC_MAP.put(clazz, enumDescMap);
					}
					
					value = ENUM_DESC_MAP.get(clazz).get(entry.getValue().toString());
				} else if (clazz.equals(Date.class)) { // 日期
					value =  DATE_FORMAT.format((Date) entry.getValue());
				} else {
					value = StringUtils.valueOf(entry.getValue());
				}
			}
			
			content = StringUtils.replace(content, "${" + entry.getKey() + "}", value);
		}
		
		return content;
	}*/
	
}
