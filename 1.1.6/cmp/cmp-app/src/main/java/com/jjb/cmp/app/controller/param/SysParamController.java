package com.jjb.cmp.app.controller.param;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.cmp.biz.cache.controller.CmpCacheContext;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @author hn
 * @description cmp系统参数配置
 * @date 2018年8月29日15:15:03
 */


@Controller
@RequestMapping("/cmp_/sysParam")
public class SysParamController extends BaseController {

	@Autowired
	private CmpCacheContext cacheContext;

	/*
	 * 进入参数页面
	 */
	@RequestMapping("/page")
	public String page() {
		return "param/sysParam.ftl";
	}


}
