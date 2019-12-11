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
import com.jjb.ecms.biz.service.report.ApplyProportionReportService;
import com.jjb.ecms.facility.dto.ApplyProportionReportDto;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.kits.StrKit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @类名： ApplyDailyStatisticReportController.java
 * @描述：审批占比报表
 * @作者： J.J
 * @修改日期： 2018年4月9日
 */

@Controller
@RequestMapping("/applyProportionReportController")
public class ApplyProportionReportController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ApplyProportionReportController.class);
	
	@Autowired
	private ApplyProportionReportService applyProportionReportService;
	
	@RequestMapping("/page")
	public String page(){
		return "report/applyProportionReport.ftl";
	}
	
	/*
	 * 获取所有的系统参数配置
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Page<ApplyProportionReportDto> list(ApplyProportionReportDto applyProportionReportDto) throws ParseException{
		Page<ApplyProportionReportDto> page = getPage(ApplyProportionReportDto.class);
		page.setSortName("id");
		page.setSortOrder(SortOrder.ASCENDING.name());
		ConvertEndDateUtil.convertEndDate(page.getQuery(),"applySuccessEndDate");//将前台页面输入的查询结束日期加一天
		page = applyProportionReportService.getPage(page,applyProportionReportDto);		
		return page;
	}
	
	/**
     * 功能描述：导出审批通过报表
     * @param request
     * @param response
     */
	@RequestMapping("/exportApplyProportionData")
	public void exportApplyProportionData(HttpServletRequest request,HttpServletResponse response){
		try{
			String startDate = getPara("startDate");
			String endDate = getPara("endDate");
			if(StrKit.isBlank(startDate)||StrKit.isBlank(endDate)){
				logger.info("导出审批通过报表失败，没有选择导出时间");
				return;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("applySuccessStartDate",startDate );
			map.put("applySuccessEndDate",endDate );
			map.put("org", OrganizationContextHolder.getOrg());
			ConvertEndDateUtil.convertEndDate(map,"applySuccessEndDate");//将前台页面输入的查询结束日期加一天
			List<ApplyProportionReportDto> list = applyProportionReportService.getApplyProportionReportData(map);
			
			final String fileName = "审批占比报表("+startDate+"-"+endDate+").xlsx";
			String fields = "日期,建议通过次数,实际通过次数,通过占比,建议拒绝次数,实际拒绝次数,拒绝占比";
			String[] numberCell = {};
			Map<String,String> bookMappings = new LinkedHashMap<String,String>();
		        bookMappings.put("日期","date");
				bookMappings.put("建议通过次数","sugSuccessTime");
				bookMappings.put("实际通过次数","realSuccessTime");
				bookMappings.put("通过占比","successTimePercent");
				bookMappings.put("建议拒绝次数","sugRejectTime");
				bookMappings.put("实际拒绝次数","realRejectTime");
				bookMappings.put("拒绝占比","rejectTimePercent");
				PoiExcelUtil.exportTempExcelAndDownloadWithNoPath(getRequest(),getResponse(),fileName,"审批占比报表("+startDate+"-"+endDate+")",fields,list,bookMappings,numberCell);
		}catch(Exception e){
			logger.error("导出审批占比报表出错",e);
		}
	}

}
