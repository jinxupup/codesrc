package com.jjb.unicorn.facility.util;

/**
 * 在线程上下文存放交易入口生成的内部流水号
 * RPC调用时，向被调用方传递
 * @author H.N
 *
 */
public class AmqpContextHolder {
	
	private static ThreadLocal<String> jydSrcTradeId = new ThreadLocal<String>();//交易入口生成的内部流水号

	private static ThreadLocal<String> org = new ThreadLocal<String>();

	private static ThreadLocal<String> username = new ThreadLocal<String>();
	
	public static String getCurrentOrg()
	{
		return org.get();
	}
	
	public static void setCurrentOrg(String org)
	{
		AmqpContextHolder.org.set(org);
	}

	public static String getUsername() {
		return username.get();
	}

	public static void setUsername(String username) {
		AmqpContextHolder.username.set(username);
	}
	public static String getJydSrcTradeId() {
		return jydSrcTradeId.get();
	}

	public static void setJydSrcTradeId(String jydSrcTradeId) {
		AmqpContextHolder.jydSrcTradeId.set(jydSrcTradeId);
	}
	
}
