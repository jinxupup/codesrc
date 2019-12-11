package com.jjb.unicorn.socket.session;

public class SessionKey {
	
	/**
	 * session中存放响应报文的key
	 */
	public static final String RESPONSE_KEY_NAME = "com.jjb.unicorn.socket.client.response";

	/**
	 * session中存放运行时异常的key
	 */
	public static final String EXCEPTION_KEY_NAME = "com.jjb.unicorn.socket.client.exception";
	
	/**
	 * session中负责同步用的FLAG
	 */
	public static final String NOTIFY_FLAG = "com.jjb.unicorn.socket.notify.flag";
	
	/**
	 * session中负责保存当前等待线程的key
	 */
	public static final String WAIT_THREAD = "com.jjb.unicorn.socket.wait.thread";
	
}
