package com.jjb.ecms.biz.ext.risk;

import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 调用外部风控系统
 * 
 * @Description:
 * @author JYData-R&D-HN
 * @date 2018年7月6日 上午11:40:22
 * @version V1.0
 */
@Component
public class RiskUtils {

	private static Logger logger = LoggerFactory.getLogger(RiskUtils.class);

	/**
	 * 规则决策结果
	 * @param req
	 * @return
	 */
	public static String ksRpResult(String req){
		 if("N".equals(req)){
			 return "N-未触发";
		 }else if("R".equals(req)){
			 return "R-人工审核";
		 }else if("RD".equals(req)){
			 return "RD-审慎审核";
		 }else if("D".equals(req)){
			 return "D-快速拒绝";
		 }
	    return req;
	}
	/**
	 * 审批准入策略
	 * @param req
	 * @return
	 */
	public static String ksAaStgy(String req){
		 if("A".equals(req)){
			 return "A-快速通过";
		 }else if("RA".equals(req)){
			 return "RA-建议通过";
		 }else if("R".equals(req)){
			 return "R-人工审批";
		 }else if("RD".equals(req)){
			 return "RD-审慎审核";
		 }else if("D".equals(req)){
			 return "D-快速拒绝";
		 }
	    return req;
	}
	
	/**
	 * 根据当前时间获取交易流水
	 * 
	 * @return
	 */
	public static String genarateTxnSn() {
		DateUtils.getCurrentDate();
		String dates = DateUtils.dateToString(new Date(), DateUtils.FULL_SECOND_LINE_NO);
		String seqNo = dates
				+ (int) ((Math.random() * 9 + 1) * 100000);
		return seqNo;
	}
	
	/**
	 * Json转Map
	 * 
	 * @return
	 */
	
	public static Map<String,Map<String,String>> getRiskJsonValueToMap(String mKey,
			Map<String,Map<String,String>> retMap,
			Map<String,Object> srcMap){
		if(srcMap!=null && srcMap.size()>0){
			for (String key : srcMap.keySet()) {
				Object value = srcMap.get(key);
				if(value instanceof Map){
					Map<String,Object> map4 = (Map<String,Object>)value;	
					getRiskJsonValueToMap(key,retMap,map4);
				}else{
					String str = StringUtils.valueOf(value);	
					Map<String,String> m1 = retMap.get(mKey);
					if(m1!=null){
						m1.put(key, str);
					}else{
						m1 = new HashMap<String, String>();
						m1.put(key, str);
					}
					if(StringUtils.isEmpty(mKey)){
						mKey = key;
					}
					retMap.put(mKey, m1);
				}
			}
		}
		return retMap;
	}

}