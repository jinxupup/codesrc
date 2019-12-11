package com.jjb.ams.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jjb.ams.biz.service.index.AmsWelcomeService;
import com.jjb.ecms.facility.dto.IndexWorkCountDto;
import com.jjb.unicorn.web.controller.BaseController;

@Controller
@RequestMapping("/ams_index")
public class AmsWelcomeController extends BaseController {
	
	@Autowired
	private AmsWelcomeService amsWelcomeService;
	
	@RequestMapping("/index")
	public String index(){
		List<IndexWorkCountDto> indexWorkCountDtos = amsWelcomeService.getWorkCount();
		List<IndexWorkCountDto> indexWorkUntreatedCountDtos = amsWelcomeService.getWorkUntreatedCount();//未处理工作量统计
		setAttr("indexWorkCountDtos", indexWorkCountDtos);
		setAttr("indexWorkUntreatedCountDtos", indexWorkUntreatedCountDtos);
		return "amsIndex/index.ftl";
	}
}
