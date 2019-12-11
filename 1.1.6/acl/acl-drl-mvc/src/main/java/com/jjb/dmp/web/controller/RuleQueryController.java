package com.jjb.dmp.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.dmp.biz.service.RuleQueryService;
import com.jjb.dmp.engine.bean.RuleQueryBean;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 策略方案
 * @author BIG.D.W.K
 *
 */
@Controller
@RequestMapping("dmp/ruleQuery")
public class RuleQueryController extends BaseController{

	@Autowired
	private RuleQueryService ruleQueryService;
	
	@RequestMapping("/page")
	public String page(){
		return "dmp/ruleQuery/ruleQuery.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public Page<RuleQueryBean> list(){
		
		Page<RuleQueryBean> page = getPage(RuleQueryBean.class);
		page = ruleQueryService.query(page);
		
		return page;
	}
	
}	