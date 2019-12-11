package com.jjb.ecms.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jjb.ecms.biz.service.index.WelcomeService;
import com.jjb.ecms.facility.dto.IndexWorkCountDto;
import com.jjb.unicorn.web.controller.BaseController;

@Controller
@RequestMapping("/index")
public class WelcomeController extends BaseController {
	
	@Autowired
	WelcomeService welcomeService;
	
	@RequestMapping("/index")
	public String index(){
		List<IndexWorkCountDto> indexWorkCountDtos = welcomeService.getWorkCount();
		List<IndexWorkCountDto> indexWorkUntreatedCountDtos = welcomeService.getWorkUntreatedCount();//未处理工作量统计
		setAttr("indexWorkCountDtos", indexWorkCountDtos);
		setAttr("indexWorkUntreatedCountDtos", indexWorkUntreatedCountDtos);
		return "index/index.ftl";
	}
}
