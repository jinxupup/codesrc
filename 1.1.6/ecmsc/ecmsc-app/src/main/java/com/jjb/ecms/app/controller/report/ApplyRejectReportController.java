package com.jjb.ecms.app.controller.report;

import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.SortOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.ecms.app.controller.utils.ConvertEndDateUtil;
import com.jjb.ecms.app.controller.utils.PoiExcelUtil;
import com.jjb.ecms.biz.service.report.ApplyRejectReportService;
import com.jjb.ecms.facility.dto.ApplyRejectReportQueryDto;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @类名： ApplyRejectReportController.java
 * @描述：审批拒绝报表
 * @作者： zx
 * @修改日期： 2017年11月27日
 */
@Controller
@RequestMapping("/applyRejectReportController")
public class ApplyRejectReportController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ApplyRejectReportController.class);

	@Autowired
	private ApplyRejectReportService applyRejectReportService;

	@RequestMapping("/reject")
	public String page() {
		return "report/applyRejectReport.ftl";
	}

	/*
	 * 获取所有的系统参数配置
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Page<ApplyRejectReportQueryDto> list(ApplyRejectReportQueryDto applyRejectReportQueryDto) throws ParseException{
		
		Page<ApplyRejectReportQueryDto> page = getPage(ApplyRejectReportQueryDto.class);
		page.setSortName("id");
		page.setSortOrder(SortOrder.ASCENDING.name());
		ConvertEndDateUtil.convertEndDate(page.getQuery(), "applyRejectEndDate");
		page = applyRejectReportService.getPage(page,applyRejectReportQueryDto);		
		return page;
	}
	/**
	 * 功能描述：导出审批拒绝报表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportApplyRejectData")
    public void exportApplyRejectData(HttpServletRequest request,HttpServletResponse response){    	
    	try{
    		String startDate = getPara("startDate");
    		String endDate = getPara("endDate");
    		if(StrKit.isBlank(startDate)||StrKit.isBlank(endDate)){
    			logger.info("导出审批拒绝报表失败，没有选择导出时间");
    			return ;
    		}
    		String branch = getPara("branch");
    		if("null".equalsIgnoreCase(branch)){
    			branch= null;
    		}
    		String productCd = getPara("productCd");
    		String appType = getPara("appType");
    		String appSource = getPara("appSource");
    		
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("branch", branch);
    		map.put("productCd", productCd);
    		map.put("appType", appType);
    		map.put("appSource", appSource);
    		map.put("applyRejectStartDate", startDate);
    		map.put("applyRejectEndDate", endDate);
    		map.put("org", OrganizationContextHolder.getOrg());
    		ConvertEndDateUtil.convertEndDate(map, "applyRejectEndDate");
    		List<ApplyRejectReportQueryDto> list = applyRejectReportService.getApplyRejectReportData(map);
    		
    		final String fileName = "每日审批拒绝报表(" +startDate+"-"+endDate+").xlsx";
    		String fields = "申请编号,证件类型,证件号码,申请类型,姓名,产品号,受理网点,申请渠道,拒绝原因码,拒绝原因,审批员ID,审批时间";
    		String[] numberCell = {};
    		Map<String,String> bookMappings = new LinkedHashMap<String,String>();
    		bookMappings.put("申请编号","appNo");
    		bookMappings.put("证件类型","idType");
    		bookMappings.put("证件号码","idNo");
    		bookMappings.put("申请类型","appType");
    		bookMappings.put("姓名","name");    		
    		bookMappings.put("产品号","productCd");
    		bookMappings.put("受理网点","owningBranch");
    		bookMappings.put("申请渠道","appSource");
    		bookMappings.put("拒绝原因码","refuseCode");
    		bookMappings.put("拒绝原因","refuseDesc");
    		bookMappings.put("审批员ID","oper");
    		bookMappings.put("审批时间","checkDate");
    		PoiExcelUtil.exportTempExcelAndDownloadWithNoPath(getRequest(),getResponse(), fileName, "每日审批拒绝报表("+startDate+"-"+endDate+")", fields, list, bookMappings, numberCell);
    	}catch(Exception e){
    		logger.error("导出每日审批拒绝报表出错",e);
    	}
    	
    }

}
