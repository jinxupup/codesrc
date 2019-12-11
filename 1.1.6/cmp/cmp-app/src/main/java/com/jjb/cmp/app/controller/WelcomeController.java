package com.jjb.cmp.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jjb.cmp.app.controller.utils.CmpPageConstant;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * 系统首页
 * 
 * @author hp
 *
 */
@Controller
@RequestMapping("/index")
public class WelcomeController extends BaseController implements CmpPageConstant {

	/**
	 * 现阶段默认展示影像列表内容
	 * 
	 * @return
	 */
	@RequestMapping("/index")
	public String index() {
		return contentListPage;
	}

}
