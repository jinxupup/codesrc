package com.jjb.ecms.biz.util;

import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.ecms.biz.cache.CacheContext;

public class ParameterRefreshUtil {

	@Autowired
	private CacheContext cacheContext;
	/**
	 * 初始化所有的参数缓存
	 */
	public void refreshParameter(Object obj){
		cacheContext.refresh();
	}
}
