package com.jjb.ecms.biz.test;


import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.util.HttpClientUtil;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;



public class CellRiskSupportTest {

	private static Logger logger = LoggerFactory.getLogger(CellRiskSupportTest.class);
	private static Boolean loginRs;
	public static void main(String[] args) {


		long start = System.currentTimeMillis();
		Map<String, String> bjjRiskConf = new HashMap<String, String>();
		bjjRiskConf.put("riskDecisionUrl", "http://10.109.3.220:8081/decision/sync/get");
		bjjRiskConf.put("riskAuthUrl", "http://10.109.3.220:8081/oauth/token");
		bjjRiskConf.put("grant_type", "authorization_code");
		bjjRiskConf.put("client_id", "invoke");
		bjjRiskConf.put("client_secret", "123");
		String riskAuthUrl = StringUtils.valueOf(bjjRiskConf.get("riskAuthUrl"));
		if(bjjRiskConf==null || !bjjRiskConf.containsKey("riskAuthUrl")){
			throw new ProcessException("未查询到风控系统网络配置，调用失败！");
		}
		if(StringUtils.isEmpty(riskAuthUrl)){
			throw new ProcessException("无法调用风控系统，系统配置[风控地址]为空！"); 
		}
		String tonken = "";
		if(getLoginRs()==null || !getLoginRs()){
			tonken = loginRisk(bjjRiskConf);
			if(StringUtils.isNotEmpty(tonken)){
				setLoginRs(true);
			}else{
				throw new ProcessException("未获取到风控决策系统有效的token令牌参数");
			}
		}
		String riskDecisionUrl = StringUtils.valueOf(bjjRiskConf.get("riskDecisionUrl"));
		if(bjjRiskConf==null || !bjjRiskConf.containsKey("riskDecisionUrl")){
			throw new ProcessException("未查询到风控系统网络配置，调用失败！");
		}
		String resp = "";
		try {
			JSONObject js = new JSONObject();
			js.put("productCode", "000020180903193510");
			js.put("token", tonken);
			JSONObject custJs = new JSONObject();
			custJs.put("name", "何嘉能");
			custJs.put("id_card", "430523199108230717");
			custJs.put("phone_no", "18521064139");
			js.put("requestParam", custJs);
			logger.info("风控决策请求:{}",js.toJSONString());
			resp = sendHttp2("decision", riskDecisionUrl, js);

		} catch (Exception e) {
			logger.error("发送/接收调用风控系统发生异常.", e);
			// TODO: handle exception
			throw new ProcessException("发送/接收调用风控系统发生异常."+e.getMessage());
		}finally{
			logger.info("风控决策[耗时"+(System.currentTimeMillis()-start)+"毫秒],响应["+resp+"]");
		}
	
	
	}
	
	/**
	 * 登录风控系统
	 * @param bjjRiskConf
	 * @return
	 */
	private static String loginRisk(Map<String, String> bjjRiskConf) {
		if(bjjRiskConf==null || !bjjRiskConf.containsKey("riskAuthUrl")){
			throw new ProcessException("未查询到风控系统网络配置，调用失败！");
		}
		String riskAuthUrl = StringUtils.valueOf(bjjRiskConf.get("riskAuthUrl"));
		String grant_type = StringUtils.valueOf(bjjRiskConf.get("grant_type"));
		String client_id = StringUtils.valueOf(bjjRiskConf.get("client_id"));
		String client_secret = StringUtils.valueOf(bjjRiskConf.get("client_secret"));
		if(StringUtils.isEmpty(riskAuthUrl)){
			throw new ProcessException("无法调用风控系统，系统配置[风控地址]为空！"); 
		}
		if(StringUtils.isEmpty(grant_type)){
			throw new ProcessException("无法调用风控系统，系统配置[权限类型]为空！"); 
		}
		if(StringUtils.isEmpty(client_id)){
			throw new ProcessException("无法调用风控系统，系统配置[用户名]为空！"); 
		}
		if(StringUtils.isEmpty(client_secret)){
			throw new ProcessException("无法调用风控系统，系统配置[密码]为空！"); 
		}
		JSONObject js = new JSONObject();
		js.put("grant_type", grant_type);
		js.put("client_id", client_id);
		js.put("client_secret", client_secret);
//		
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("grant_type", grant_type);
//		map.put("client_id", client_id);
//		map.put("client_secret", client_secret);
		
		String resp = sendHttp("oauth", riskAuthUrl, js);
		if(resp==null){
			logger.error("登录风控系统失败，响应数据为空");
			return null;
		}
		String token;
		try {
			JSONObject jo = JSON.parseObject(resp);
			if(jo==null || !jo.containsKey("token") 
					|| StringUtils.equals(jo.get("token"), "")){
				logger.error("登录风控系统失败，交易未返回有效的token令牌");
				return null;
			}
			token = StringUtils.valueOf(jo.get("token"));
		} catch (Exception e) {
			logger.error("解析风控系统响应结果异常",e);
			return null;
		}
		return token;
	}

	/**
	 * 发送http请求
	 * @param servId
	 * @param url
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public static String sendHttp(String servId,String url,JSONObject js){
		long start = System.currentTimeMillis();
		if(js==null){
			logger.warn(LogPrintUtils.printCommonStartLog(start,servId)+"服务编码["+servId+"]请求失败，请求参数为空");
			throw new ProcessException("联机交易["+servId+"]失败，请求参数为空");
		}
		String resp = "";
		try {
			logger.info(LogPrintUtils.printCommonStartLog(start,servId)+"风控决策请求:{}",js.toJSONString());
			resp = HttpClientUtil.httpPostRequest(url, js);
			
		} catch (Exception e) {
			logger.error("联机交易["+servId+"]发生异常.", e);
			throw new ProcessException("联机交易["+servId+"]发生异常."+e.getMessage());
		}finally{
			logger.info(LogPrintUtils.printCommonStartLog(start,servId)+"，响应数据["+resp+"]");
		}
		return resp;
	}
	
	/**
	 * 发送http请求
	 * @param servId
	 * @param url
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public static String sendHttp2(String servId,String url,JSONObject jsReq){
		long start = System.currentTimeMillis();
		if(jsReq==null){
			logger.warn(LogPrintUtils.printCommonStartLog(start,servId)+"服务编码["+servId+"]请求失败，请求参数为空");
			throw new ProcessException("联机交易["+servId+"]失败，请求参数为空");
		}
		String resp = "";
		try {
			logger.info(LogPrintUtils.printCommonStartLog(start,servId)+"风控决策请求:{}",jsReq.toJSONString());
//			resp = HttpClientUtil.httpPostRequest(url, jsReq);
			
			HttpPost post = new HttpPost(url);
			post.setHeader("Content-Type", "application/json");
			StringEntity entity = new StringEntity(jsReq.toJSONString(),Charset.forName("UTF-8"));
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			post.setEntity(entity);

			resp = HttpClientUtil.execute(post);
			
		} catch (Exception e) {
			logger.error("联机交易["+servId+"]发生异常.", e);
			throw new ProcessException("联机交易["+servId+"]发生异常."+e.getMessage());
		}finally{
			logger.info(LogPrintUtils.printCommonStartLog(start,servId)+"，响应数据["+resp+"]");
		}
		return resp;
	}
	private static synchronized Boolean getLoginRs() {
		return loginRs;
	}
	private static synchronized void setLoginRs(Boolean loginRs) {
		CellRiskSupportTest.loginRs = loginRs;
	}
	
}
