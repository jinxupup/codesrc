package com.jjb.acl.gmp.api;

import java.io.Serializable;

public class ParameterRefreshRequest implements Serializable{

	private static final long serialVersionUID = 9012429433946121045L;
	
	/**
	 * 参数类全名，如果为null则表示刷新所有缓存
	 */
	private String paramClazzName;
	
	/**
	 * 需要刷新的类主键，如果为null则表示所有参数
	 */
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getParamClazzName() {
		return paramClazzName;
	}

	public void setParamClazzName(String paramClazzName) {
		this.paramClazzName = paramClazzName;
	}
}
