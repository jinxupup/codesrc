package com.jjb.acl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jjb.unicorn.web.controller.BaseController;


@Controller
public class IndexController extends BaseController{
	
	@RequestMapping("/devdoc")
	public String devdoc() {
		return "/devdoc/devdoc.ftl";
	}
	
	@RequestMapping("/devdoc/{forder}/{ftl:.+}")
	public String devdoc(@PathVariable("forder")String forder,@PathVariable("ftl") String ftl) {
		return "/devdoc/"+forder+"/"+ftl;
	}
	
	@RequestMapping("/unicorn/{ftl:.+}")
	public String unicorn(@PathVariable("ftl") String ftl) {
		return "/unicorn/" + ftl;
	}
	
}
