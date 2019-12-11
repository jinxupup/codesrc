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
import com.jjb.ecms.biz.service.report.ApplyUncompletedReportServcie;
import com.jjb.ecms.facility.dto.ApplyUncompletedDto;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @类名： ApplyUncompletedReportController.java
 * @描述：审批未完成报表
 * @作者： zx
 * @修改日期： 2017年11月27日
 */
@Controller
@RequestMapping("/applyUncompletedReportController")
public class ApplyUncompletedReportController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(ApplyUncompletedReportController.class);


	@Autowired
	private ApplyUncompletedReportServcie applyUncompletedReportServcie;

	@RequestMapping("/uncompleted")
	public String page() {
		return "report/applyUncompletedReport.ftl";
	}

	@ResponseBody
	@RequestMapping("/list")
	public Page<ApplyUncompletedDto> list(ApplyUncompletedDto applyUncompletedDto) throws ParseException{
		
		Page<ApplyUncompletedDto> page = getPage(ApplyUncompletedDto.class);
		page.setSortName("id");
		page.setSortOrder(SortOrder.ASCENDING.name());
		ConvertEndDateUtil.convertEndDate(page.getQuery(),"applyUncompletedEndDate");//将前台页面输入的查询结束日期加一天
		page = applyUncompletedReportServcie.getPage(page,applyUncompletedDto);		
		return page;
	}
	/**
	 * 功能描述：导出审批未完成报表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportApplyUncompletedData")
    public void exportApplyUncompletedData(HttpServletRequest request,HttpServletResponse response){    	
    	try{
    		String startDate = getPara("startDate");
    		String endDate = getPara("endDate");
    		if(StrKit.isBlank(startDate)||StrKit.isBlank(endDate)){
    			logger.info("导出审批未完成报表失败，没有选择导出时间");
    			return ;
    		}
    		String branch = getPara("branch");
    		String productCd = getPara("productCd");
    		String appType = getPara("appType");
    		String appSource = getPara("appSource");
    		if("null".equalsIgnoreCase(branch)){
    			branch = null;
    		}
    		
    		Map<String, Object> map = new HashMap<String, Object>();
    		map.put("applyUncompletedStartDate", startDate);
    		map.put("applyUncompletedEndDate", endDate);
    		map.put("branch",branch );
    		map.put("productCd",productCd );
    		map.put("appType",appType );
    		map.put("appSource",appSource );
    		map.put("org", OrganizationContextHolder.getOrg());
    		ConvertEndDateUtil.convertEndDate(map,"applyUncompletedEndDate");//将前台页面输入的查询结束日期加一天
    		List<ApplyUncompletedDto> list = applyUncompletedReportServcie.getApplyUncompletedReportData(map);
    		
    		final String fileName = "审批未完成报表(" +startDate+"---"+endDate+").xlsx";
    		String fields = "申请编号,证件类型,证件号码,姓名,申请类型,产品号,受理网点,申请渠道,上一任务人,任务所属人,任务分配人,任务节点,任务名称";
    		String[] numberCell = {};
    		Map<String,String> bookMappings = new LinkedHashMap<String,String>();
    		bookMappings.put("申请编号","appNo");
    		bookMappings.put("证件类型","idType");
    		bookMappings.put("证件号码","idNo");
    		bookMappings.put("姓名","name");
    		bookMappings.put("申请类型","appType");
    		bookMappings.put("产品号","productCd");
    		bookMappings.put("受理网点","owningBranch");
    		bookMappings.put("申请渠道","appSource");
    		bookMappings.put("上一任务人","lastOpUser");
    		bookMappings.put("任务所属人","owner");
    		bookMappings.put("任务分配人","assignee");
    		bookMappings.put("任务节点","taskDefKey");
    		bookMappings.put("任务名称","taskName");
    		PoiExcelUtil.exportTempExcelAndDownloadWithNoPath(getRequest(),getResponse(), fileName, "审批未完成报表(" +startDate+"---"+endDate+")", fields, list, bookMappings, numberCell);
    	}catch(Exception e){
    		logger.error("导出审批未完成报表报表出错",e);
    	}
    	
    }

}
