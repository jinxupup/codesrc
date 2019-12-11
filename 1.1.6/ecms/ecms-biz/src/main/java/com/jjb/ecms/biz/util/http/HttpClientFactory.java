package com.jjb.ecms.biz.util.http;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.jjb.unicorn.facility.util.StringUtils;

/**
 * http连接
 * @author H.N
 * @version 1.0
 */

@Component
public class HttpClientFactory {

	private static Logger log = LoggerFactory.getLogger(HttpClientFactory.class);
	//HTTPCLIENT_CONNECTION_MAX
	private final static int maxConnection = Integer.parseInt("100");
	//HTTPCLIENT_CONNECTION_TIMEOUT
	private final static int connectionTimeOut = Integer.parseInt("3600000");

//	private static MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();

	private static HttpClient httpClient = getHttpClient(maxConnection, connectionTimeOut);

	/**
	 * get one HttpClient supports multi-thread
	 * 
	 * @return
	 */
	public static HttpClient getHttpClient(String servId,String ip,String port) {
		/*if(StringUtils.isEmpty(ip)){
			log.info("======Failed to get HTTP connection, the url is null.............");
			return null;
		}
		if(StringUtils.isEmpty(port) || !StringUtils.isNumberString(port)) {
			log.warn("======Failed to get HTTP connection, the port is null or is String.............set def(80)");
			port="80";
		}
		httpClient.getHostConfiguration().setProxy(ip,Integer.parseInt(port));*/
		
		return httpClient;
	}

	private static HttpClient getHttpClient(int maxConnection, int connectionTimeOut) {

		/*HttpConnectionManagerParams params = connectionManager.getParams();

		params.setConnectionTimeout(connectionTimeOut);
		params.setSoTimeout(connectionTimeOut);
		params.setMaxTotalConnections(maxConnection);
		params.setDefaultMaxConnectionsPerHost(maxConnection);

		return new HttpClient(connectionManager);*/
		return httpClient;

	}

}
