package com.jjb.ecms.biz.util.http;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;

public class HttpClient {
	
    private static CloseableHttpClient httpclient = null; 
    
    static final int maxTotal=1000;//总最大连接数
    static final int defaultMaxPerRoute=800;
	
	 public static CloseableHttpClient getHttpClient() {
	    	
			if (null == httpclient) {
				synchronized (HttpClient.class) {
					if (null == httpclient) {
						httpclient = getNewHttpClient();
					}
				}
			}
	        
	        return httpclient;
	    }
	 
	   private static CloseableHttpClient getNewHttpClient() {

	      
	        // 设置连接池
	        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
            LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create().register("http", plainsf).register("https", sslsf).build();
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
            // 配置最大连接数
            cm.setMaxTotal(maxTotal);
            // 配置每条线路的最大连接数
            cm.setDefaultMaxPerRoute(defaultMaxPerRoute);
            
            // 请求重试处理
            HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            	@Override
                public boolean retryRequest(IOException exception,
                        int executionCount, HttpContext context) {
                    if (executionCount >= 3) {// 如果已经重试了3次，就放弃
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                        return false;
                    }
                    if (exception instanceof InterruptedIOException) {// 超时
                        return false;
                    }
                    if (exception instanceof UnknownHostException) {// 目标服务器不可达
                        return false;
                    }
                    if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                        return false;
                    }
                    if (exception instanceof SSLException) {// SSL握手异常
                        return false;
                    }

                    HttpClientContext clientContext = HttpClientContext.adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }

            };
	        
	    	CloseableHttpClient newHttpclient=null;
	    
	        	newHttpclient = HttpClients.custom()
	                                       .setConnectionManager(cm)
	                                       .setRetryHandler(httpRequestRetryHandler)
	                                       .build();
	        	
	    	 return newHttpclient;
	   }
}
