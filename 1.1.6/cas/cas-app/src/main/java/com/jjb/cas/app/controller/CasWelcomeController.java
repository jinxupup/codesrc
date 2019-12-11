package com.jjb.cas.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jjb.cas.biz.service.index.CasWelcomeService;
import com.jjb.ecms.facility.dto.IndexWorkCountDto;
import com.jjb.unicorn.web.controller.BaseController;

@Controller
@RequestMapping("/cas_index")
public class CasWelcomeController extends BaseController {
	
	@Autowired
	private CasWelcomeService casWelcomeService;
	
	@RequestMapping("/index")
	public String index(){
		List<IndexWorkCountDto> indexWorkCountDtos = casWelcomeService.getWorkCount();
		List<IndexWorkCountDto> indexWorkUntreatedCountDtos = casWelcomeService.getWorkUntreatedCount();//未处理工作量统计
		setAttr("indexWorkCountDtos", indexWorkCountDtos);
		setAttr("indexWorkUntreatedCountDtos", indexWorkUntreatedCountDtos);
		return "casIndex/index.ftl";
	}
}
