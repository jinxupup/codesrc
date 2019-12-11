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
import com.jjb.ecms.biz.service.report.ApplySuccessReportService;
import com.jjb.ecms.facility.dto.ApplySuccessReportQueryDto;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;
/**
 * @类名： ApplySucceedReportController.java
 * @描述：审批通过报表
 * @作者： zx
 * @修改日期： 2017年11月27日
 */

@Controller
@RequestMapping("/applySuccessReportController")
public class ApplySuccessReportController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ApplySuccessReportController.class);
	
	@Autowired
	private ApplySuccessReportService applySuccessReportService;
	
	@RequestMapping("/success")
	public String page(){
		return "report/applySuccessReport.ftl";
	}
	/*
	 * 获取所有的系统参数配置
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Page<ApplySuccessReportQueryDto> list(ApplySuccessReportQueryDto applySuccessReportQueryDto) throws ParseException{
		
		Page<ApplySuccessReportQueryDto> page = getPage(ApplySuccessReportQueryDto.class);
		page.setSortName("id");
		page.setSortOrder(SortOrder.ASCENDING.name());
		ConvertEndDateUtil.convertEndDate(page.getQuery(),"applySuccessEndDate");//将前台页面输入的查询结束日期加一天
		page = applySuccessReportService.getPage(page,applySuccessReportQueryDto);		
		return page;
	}
	/**
     * 功能描述：导出审批通过报表
     * @param request
     * @param response
     */
	@RequestMapping("/exportApplySuccessData")
	public void exportApplySuccessData(HttpServletRequest request,HttpServletResponse response){
		try{
			String startDate = getPara("startDate");
			String endDate = getPara("endDate");
			String branch = getPara("branch");
			String productCd = getPara("productCd");
			String appType = getPara("appType");
			String appSource = getPara("appSource");
			if(StrKit.isBlank(startDate)||StrKit.isBlank(endDate)){
				logger.info("导出审批通过报表失败，没有选择导出时间");
				return;
			}
			if("null".equalsIgnoreCase(branch)){
				branch = null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("applySuccessStartDate",startDate );
			map.put("applySuccessEndDate",endDate );
			map.put("branch",branch );
			map.put("productCd",productCd );
			map.put("appType",appType );
			map.put("appSource",appSource );
			map.put("org", OrganizationContextHolder.getOrg());
			ConvertEndDateUtil.convertEndDate(map,"applySuccessEndDate");//将前台页面输入的查询结束日期加一天
			List<ApplySuccessReportQueryDto> list = applySuccessReportService.getApplySucessReportData(map);
			
			final String fileName = "审批通过报表("+startDate+"-"+endDate+").xlsx";
			String fields = "申请编号,证件类型,证件号码,姓名,申请类型,产品号,信用额度,受理网点,申请渠道,终审人员ID,录入人员ID,复核人员ID,反欺诈人员ID,初审人员ID,电话调查人员ID,审批时间";
			String[] numberCell = {};
			Map<String,String> bookMappings = new LinkedHashMap<String,String>();
		        bookMappings.put("申请编号","appNo");
		        bookMappings.put("证件类型","idType");
				bookMappings.put("证件号码","idNo");
				bookMappings.put("姓名","name");
				bookMappings.put("申请类型","appType");
				bookMappings.put("产品号","productCd");
				bookMappings.put("信用额度","accLmt");
				bookMappings.put("受理网点","owningBranch");
				bookMappings.put("申请渠道","appSource");
				bookMappings.put("终审人员ID","k10");
				bookMappings.put("录入人员ID","a10");
				bookMappings.put("复核人员ID","b10");
				bookMappings.put("反欺诈人员ID","f30");
				bookMappings.put("初审人员ID","f10");
				bookMappings.put("电话调查人员ID","f20");
				bookMappings.put("审批时间","checkDate");
				PoiExcelUtil.exportTempExcelAndDownloadWithNoPath(getRequest(),getResponse(),fileName,"审批通过报表("+startDate+"-"+endDate+")",fields,list,bookMappings,numberCell);
		}catch(Exception e){
			logger.error("导出审批通过报表出错",e);
		}
	}
	
}
