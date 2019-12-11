package com.jjb.ecms.biz.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.DateUtils;

/**
 * http调用工具类
 * @author hp
 *
 */
public class HttpClientUtil2 {
	private static PoolingHttpClientConnectionManager poolingHttpClientConnectionManager;
	private static Logger log = LoggerFactory.getLogger(HttpClientUtil2.class);
	private static Logger logger = LoggerFactory.getLogger(HttpHelper.class);

	public static String pbocQuery(String servId,String url,String ip,String port,JSONObject js){

        String result = null;

		/*HttpClient client = HttpClientFactory.getHttpClient(servId, ip, port);
        HttpPost post = new HttpPost(url);//		NameValuePair p1 = new NameValuePair(PBOC_WEBSITE_QUERY_TOKEN_KEY, PBOC_WEBSITE_QUERY_TOKEN_VALUE);
		post.addHeader("Content-type", "application/json; charset=UTF-8");
//		post.setRequestBody(new NameValuePair[] { p1, p2, p3, p4, p5, p6, p7, p8, p9 });
		post.setEntity(js.toJSONString());
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		try {
			log.info("start ["+servId+"]...., the param[begin_time="+ DateUtils.dateToString(new Date(), DateUtils.FULL_YMDM_LINE));
			int status = client.execute(post);
			log.info("end ["+servId+"]...., the param[begin_time="+ DateUtils.dateToString(new Date(), DateUtils.FULL_YMDM_LINE));
			if ((status == HttpStatus.SC_MOVED_TEMPORARILY) || (status == HttpStatus.SC_MOVED_PERMANENTLY)
					|| (status == HttpStatus.SC_SEE_OTHER) || (status == HttpStatus.SC_TEMPORARY_REDIRECT)) {
				Header locationHeader = post.getResponseHeader("location");
				if (locationHeader != null) {
					String redirectUri = locationHeader.getValue();
					if (redirectUri == null || "".equals(redirectUri)) {
						redirectUri = "/";
					}
					GetMethod get = new GetMethod(redirectUri);
					client.executeMethod(get);
					get.releaseConnection();
				}
			}

			if(status != HttpStatus.SC_OK){
				log.error("(HTTP)状态-httpStatus:{},StatusTEXT:{}",post.getStatusCode(),post.getStatusText());
				throw new ProcessException("联机["+servId+"]失败", "服务器端返回:"+post.getStatusText());
			}
			InputStream txtis = post.getResponseBodyAsStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(txtis));
			String tempbf;
			StringBuffer html = new StringBuffer();
			while ((tempbf = br.readLine()) != null) {
				html.append(tempbf).append("\n");
			}
			result = html.toString();

			br.close();
			txtis.close();
		} catch (HttpException e) {
			log.error("["+servId+"]...HttpException,msg:{"+js.toJSONString()+"}",e);
			throw new ProcessException(servId+"-HTTP异常"+ e.getMessage());

		} catch (IOException e) {
			log.error("["+servId+"]...IOException,msg:{"+js.toJSONString()+"}",e);
			throw new ProcessException(servId+"-IO异常"+ e.getMessage());
		}
		post.releaseConnection();
*/
		return result;

	}

	public static String  getBatchNo(String url,Map<String, Object> params){
		String result=null;
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new UrlEncodedFormEntity(covertParams2NVPS(params),"UTF-8"));
			init();
			CloseableHttpClient httpClient= HttpClients.custom().setConnectionManager(poolingHttpClientConnectionManager).build();
			CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity,"UTF-8");
			httpResponse.close();
		} catch (IOException e) {
			logger.error("url = ["+url+"]", e);
		}
		return result;
	}

	private static List<BasicNameValuePair> covertParams2NVPS(Map<String, Object> params) {
		List<BasicNameValuePair> pairs = new ArrayList<>();
		params.entrySet().forEach(param -> pairs.add(new BasicNameValuePair(param.getKey(),String.valueOf(param.getValue()))));
		return pairs;
	}
	private static void init(){
		if (poolingHttpClientConnectionManager == null){
			poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
			poolingHttpClientConnectionManager.setMaxTotal(200);//设置连接池最大连接数
		}
	}
}
