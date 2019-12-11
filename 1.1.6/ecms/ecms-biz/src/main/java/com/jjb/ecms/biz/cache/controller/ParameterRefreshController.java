package com.jjb.ecms.biz.cache.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.web.controller.BaseController;

@Controller
@RequestMapping("/parameterRefresh")
public class ParameterRefreshController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private CacheContext cacheContext;
//	private ParameterRefreshService parameterRefreshService;
	@ResponseBody
	@RequestMapping("/refresh")
	public Json refresh(){
		Json j = Json.newSuccess();
		
		try{
			logger.info("开始刷新参数");
			cacheContext.refresh();
//			parameterRefreshService.refreshParam();
			
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
}
