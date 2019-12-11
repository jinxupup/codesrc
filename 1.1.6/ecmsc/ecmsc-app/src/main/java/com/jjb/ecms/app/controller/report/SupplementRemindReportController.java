package com.jjb.ecms.app.controller.report;

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

import com.jjb.ecms.app.controller.utils.PoiExcelUtil;
import com.jjb.ecms.biz.service.report.SupplementRemindReportService;
import com.jjb.ecms.facility.dto.SupplementRemindReportQueryDto;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @类名： ApplySucceedReportController.java
 * @描述：补件提醒报表
 * @作者： zx
 * @修改日期： 2017年11月27日
 */

@Controller
@RequestMapping("/supplementRemindReportController")
public class SupplementRemindReportController extends BaseController {
	private static Logger logger = LoggerFactory
			.getLogger(SupplementRemindReportController.class);

	@Autowired
	private SupplementRemindReportService supplementRemindReportService;

	@RequestMapping("/supplement")
	public String page() {
		return "report/supplementRemindReport.ftl";
	}
	
	/*
	 * 获取所有的系统参数配置
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Page<SupplementRemindReportQueryDto> list(SupplementRemindReportQueryDto supplementRemindReportQueryDto){
		
		Page<SupplementRemindReportQueryDto> page = getPage(SupplementRemindReportQueryDto.class);
		page.setSortName("id");
		page.setSortOrder(SortOrder.ASCENDING.name());
		page = supplementRemindReportService.getPage(page,supplementRemindReportQueryDto);		
		return page;
	}
	@RequestMapping("/exportSupplementRemindData")
	public void exportSupplementRemindData(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			String branch = getPara("branch");
			if ("null".equals(branch)) {
				branch = null;
			}
//			String startDate = getPara("startDate");
//			String endDate = getPara("endDate");
			
//			if (StrKit.isBlank(startDate) || StrKit.isBlank(endDate)) {
//				logger.info("导出补件提醒报表失败，没有选择导出时间");
//				return;
//			}
//			List<SupplementRemindReportQueryDto> list = supplementRemindReportService.getSupplementRemindReportData(startDate, endDate);

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("branch", branch);
			map.put("org", OrganizationContextHolder.getOrg());
			List<SupplementRemindReportQueryDto> list = supplementRemindReportService.getSupplementRemindReportData(map);
	
			final String fileName = "补件提醒报表" + DateUtils.getCurrDate()
					+ ".xlsx";
			String[] numberCell = {};
			String fields = "申请编号,证件类型,证件号码,姓名,申请类型,产品号,受理网点,申请渠道,移动电话,补件类型,推广人编号,推广人姓名,推广人所属机构,补件开始时间,补件结束时间";
			Map<String, String> bookMappings = new LinkedHashMap<String, String>();
			bookMappings.put("申请编号", "appNo");
			bookMappings.put("证件类型", "idType");
			bookMappings.put("证件号码", "idNo");
			bookMappings.put("姓名", "name");
			bookMappings.put("申请类型", "appType");
			bookMappings.put("产品号", "productCd");
			bookMappings.put("受理网点", "owningBranch");
			bookMappings.put("申请渠道", "appSource");
			bookMappings.put("移动电话", "cellPhone");
			bookMappings.put("补件类型", "pbType");
			bookMappings.put("推广人编号", "spreaderNum");
			bookMappings.put("推广人姓名", "spreaderName");
			bookMappings.put("推广人所属机构", "spreaderBank");	
			bookMappings.put("补件开始时间", "pbStartDate");
			bookMappings.put("补件结束时间", "pbTimeoutDate");
			PoiExcelUtil.exportTempExcelAndDownloadWithNoPath(getRequest(),
					getResponse(), fileName,
					"补件提醒报表" + DateUtils.getCurrDate(), fields, list,
					bookMappings, numberCell);
		} catch (Exception e) {
			logger.error("导出补件提醒报表出错", e);
		}
	}
}
