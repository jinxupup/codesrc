package com.jjb.acl.mvc.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.web.controller.BaseController;

@Controller
@RequestMapping("/acl/session")
public class SessionController extends BaseController {
	
	@RequestMapping("/page")
	public String page(){
		return "acl/session/session.ftl";
	}
	
	@ResponseBody
	@RequestMapping("/control")
	public Json control(){
		// 清空session中的缓存
		Json j = Json.newSuccess();
		try {
//			AppHolder.getUserNameMap().clear();
		} catch(Exception e) {
			j.setCode("999999");
			j.setFail(e.getMessage());
		}
		return j;
	}

}
