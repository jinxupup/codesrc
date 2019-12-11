/*
 * http交易
 * @author H.N
 * @version V1.1.0
 * @company lhfinance.com
 * @description: $SELECT$
 * @className: $Name$.java
 * @package com.fintechervision.rcs.asset.runtime.utils$
 * @date 12$ 11:09$
 */
package com.jjb.ecms.biz.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpHelper {

    private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

    private static CloseableHttpClient httpClient = HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.DEFAULT)
            .build();

    public static byte[] post(String address, byte[] data){
    	CloseableHttpResponse response = null;
    	HttpPost httpPost = new HttpPost(address);
        try {
            logger.info("请求数据:[{}]", new String(data));
			RequestConfig reqConfig = RequestConfig.custom().setConnectTimeout(65000).setConnectionRequestTimeout(10000)
					.setSocketTimeout(65000).build();
			httpPost.setConfig(reqConfig);
            httpPost.setEntity(new ByteArrayEntity(data));
            httpPost.addHeader("Content-Type", "application/json");
            response = httpClient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                if (httpEntity != null) {
                    try {
                        BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(httpEntity.getContent(), "UTF-8"), 8 * 1024);
                        StringBuilder entityStringBuilder = new StringBuilder();
                        String line = null;
                        while ((line = bufferedReader.readLine()) != null) {
                            entityStringBuilder.append(line);
                        }
                        logger.info("httpResponse:[{}]", entityStringBuilder.toString());
                        return entityStringBuilder.toString().getBytes();
                    } catch (Exception e) {
						logger.error("请求数据失败", e);
						return new byte[0];
                    }
                }
            }
            return new byte[0];
        } catch (Exception e){
            logger.info("fatal error: http-post请求["+address+"]异常:[{}]", e.getMessage());
            return new byte[0];
        }finally {
        	try {
				if (response != null) {
					response.close();
				}
				httpPost.releaseConnection();
			} catch (IOException e) {
				logger.error("POST连接重置["+address+"]异常", e);
			}
        }
    }
    
	public static String post(String url, String data, Map<String, String> head, int timeout, String encoding)
			throws Exception {

		HttpPost httppost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String result = "";
		try {
			// 设置请求参数
			StringEntity body = new StringEntity(data, encoding);
			httppost.setEntity(body);
			// 设置超时
			int tt = timeout * 1000;
			RequestConfig config = RequestConfig.custom().setConnectTimeout(tt).setConnectionRequestTimeout(tt)
					.setSocketTimeout(tt).build();
			httppost.setConfig(config);
			// 设置请求head
			if (null != head && head.size() > 0) {
				for (String k : head.keySet()) {
					httppost.setHeader(k, head.get(k));
				}
			}
			response = HttpClient.getHttpClient().execute(httppost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, encoding);
			EntityUtils.consume(entity);
		} catch (Exception e) {

            logger.error("http-POST["+url+"]请求异常.", e.getMessage());
			throw e;
		}

		finally {
			try {
				if (response != null) {
					response.close();
				}
				httppost.releaseConnection();
			} catch (IOException e) {

			}
		}
		return result;
	}

}
