package com.jjb.ecms.biz.ext.risk;

import java.io.IOException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.http.HttpClientUtil1;
import com.jjb.ecms.biz.util.http.HttpClientUtil2;
import com.jjb.ecms.biz.util.http.HttpHelper;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

@Component
public class CellBjjRiskSysSupport {

	private static Logger logger = LoggerFactory.getLogger(CellBjjRiskSysSupport.class);

	/**
	 * 默认编码格式utf-8
	 */
	public static String[] charset = new String[] {"UTF-8","UTF-8","UTF-8"};
	
	
	/**
	 * 获取json响应数据
	 * @param json
	 * @return
	 * @throws IOException
	 */
	public JSONObject sendBjjRiskSys(String servId,JSONObject js
			,Map<String, String> bjjRiskConf) throws IOException {
		long start = System.currentTimeMillis();
		JSONObject resJs = new JSONObject();
		String riskUrl = StringUtils.valueOf(bjjRiskConf.get("riskDecisionUrl"));
		String ip = StringUtils.valueOf(bjjRiskConf.get("riskDecisionHost"));
		String port = StringUtils.valueOf(bjjRiskConf.get("riskDecisionPort"));
		String riskCallMode = StringUtils.valueOf(bjjRiskConf.get("riskCallMode"));
		if(StringUtils.isEmpty(riskUrl)){
			throw new ProcessException("无法调用风控系统.系统配置[风控地址]为空！"); 
		}
		String resp=null;
		try {
			logger.info(LogPrintUtils.printCommonStartLog(start,servId)+"风控决策请求:{}",js.toJSONString());
			if(StringUtils.isEmpty(riskCallMode) || StringUtils.equals(riskCallMode, "1")) {
				byte[] byRes = HttpHelper.post(riskUrl, js.toJSONString().getBytes("UTF-8"));
				resp = new String(byRes);
			}
			if(StringUtils.equals(riskCallMode, "2") && StringUtils.isNotEmpty(ip)) {
				resp = HttpClientUtil2.pbocQuery(servId, riskUrl, ip, port, js);
			}else if(StringUtils.equals(riskCallMode, "2") && StringUtils.isEmpty(ip)) {
				byte[] byRes = HttpHelper.post(riskUrl, js.toJSONString().getBytes("UTF-8"));
				resp = new String(byRes);
			} 
			if(StringUtils.equals(riskCallMode, "3")) {
				resp = HttpClientUtil1.sendHttpPost(servId, riskUrl, js);
			}
			
		} catch (Exception e) {
			logger.error("发送/接收调用风控系统发生异常.", e);
			throw new ProcessException("发送/接收调用风控系统发生异常."+e.getMessage());
		}finally{
			logger.info("风控决策[耗时"+(System.currentTimeMillis()-start)+"毫秒],响应["+resJs+"]");
		}
		if(StringUtils.isEmpty(resp)) {
			logger.info("风控决策结果返回为空.请重试或联系管理员！");
			throw new ProcessException("风控决策结果返回为空.请重试或联系管理员！");
		}
		try {
			resJs = JSON.parseObject(resp);
		} catch (Exception e) {
			logger.error("解析风控响应数据异常", e);
			throw new ProcessException("解析风控决策结果数据异常.请联系管理员");
		}
		if(resJs!=null && !StringUtils.equals(resJs.get("stateCode"), "00000")){
			String stateCode = StringUtils.valueOf(resJs.getString("stateCode"));
			String stateDesc = StringUtils.valueOf(resJs.getString("stateDesc"));
			String tErMsg =  stateCode+"-"+stateDesc;
			logger.error("调用风控决策系统返回错误码:["+tErMsg+"]");
			throw new ProcessException("风控决策失败.错误码:["+tErMsg+"]"); 
		}
		return resJs;
	}
	
}
