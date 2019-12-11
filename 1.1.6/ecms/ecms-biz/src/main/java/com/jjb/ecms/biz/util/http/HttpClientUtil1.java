package com.jjb.ecms.biz.util.http;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.util.HttpClientUtil;
import com.jjb.unicorn.facility.exception.ProcessException;

/**
 * http调用工具类
 * @author hp
 *
 */
public class HttpClientUtil1 {
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil1.class);
	

	/**
	 * 发送http请求
	 * @param servId
	 * @param url
	 * @param map
	 * @return
	 * @throws IOException
	 */
	public static String sendHttpPost(String servId,String url,JSONObject jsReq){
		long start = System.currentTimeMillis();
		if(jsReq==null){
			logger.warn(LogPrintUtils.printCommonStartLog(start,servId)+"服务编码["+servId+"]请求失败，请求参数为空");
			throw new ProcessException("联机交易["+servId+"]失败，请求参数为空");
		}
		String resp = "";
		HttpPost post = new HttpPost(url);
		try {
			logger.info(LogPrintUtils.printCommonStartLog(start,servId)+"风控决策请求:{}",jsReq.toJSONString());
			post.setHeader("Content-Type", "application/json");
			post.setHeader("Connection", "close");
			RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(65000).setConnectionRequestTimeout(10000)
					.setSocketTimeout(65000).build();
			post.setConfig(reqConfig);
			StringEntity entity = new StringEntity(jsReq.toJSONString(),Charset.forName("UTF-8"));
			entity.setContentEncoding("UTF-8");
			entity.setContentType("application/json");
			post.setEntity(entity);

			resp = HttpClientUtil.execute(post);
		} catch (Exception e) {
			logger.error("联机交易["+servId+"]发生异常.", e);
			throw new ProcessException("联机交易["+servId+"]发生异常."+e.getMessage());
		}finally{
			try {
				post.releaseConnection();
			} catch (Exception e2) {
				logger.warn("释放连接失败"+e2.getMessage());
			}
			logger.info(LogPrintUtils.printCommonStartLog(start,servId)+"，响应数据["+resp+"]");
		}
		return resp;
	}
}
