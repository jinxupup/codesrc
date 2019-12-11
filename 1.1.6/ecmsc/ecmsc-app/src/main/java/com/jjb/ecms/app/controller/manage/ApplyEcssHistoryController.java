package com.jjb.ecms.app.controller.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.ecms.biz.service.manage.ApplyEcssHistoryService;
import com.jjb.ecms.infrastructure.TmAppImageHistory;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @Description: 影像调阅记录查询
 * @author JYData-R&D-L.L
 * @date 2016年8月31日 下午6:44:04
 * @version V1.0  
 */
@Controller
@RequestMapping("/ecssHis")
public class ApplyEcssHistoryController extends BaseController{

	@Autowired
	private ApplyEcssHistoryService applyEcssHistoryService;
	/**
	 * 影像调阅历史记录页面
	 * @return 影像调阅记录查询页面
	 */
	@RequestMapping("/applyEcssHis")
	public String  applyEcssHis() {
		return "/applyManage/applyEcssHis/applyEcssHis_V1.ftl";
	}
	
	/**
	 * 影像调阅记录 分页查询
	 * @param 
	 * @return 影像调阅查询记录
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Page<TmAppImageHistory> list(){
		Page<TmAppImageHistory> page = getPage(TmAppImageHistory.class);
		page = applyEcssHistoryService.getImagePage(page);
		
		return page;
	}
	
}