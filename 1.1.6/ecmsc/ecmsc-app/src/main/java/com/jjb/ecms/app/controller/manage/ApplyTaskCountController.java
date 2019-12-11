package com.jjb.ecms.app.controller.manage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.manage.ApplyTaskCountService;
import com.jjb.ecms.biz.service.manage.ApplyTaskDetailsService;
import com.jjb.ecms.facility.dto.ApplyTaskCountDto;
import com.jjb.ecms.facility.dto.ApplyTaskDetailsDto;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @Description: 申请工作量查询
 * @author JYData-R&D-L.L
 * @date 2016年9月1日 下午2:00:08
 * @version V1.0
 */
@Controller
@RequestMapping("/taskCount")
public class ApplyTaskCountController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	ApplyTaskCountService applyTaskCountService;
	@Autowired
	ApplyTaskDetailsService applyTaskDetailsService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private CacheContext cacheContext;
	/**
	 * 申请工作量查询
	 * 
	 * @return 申请工作量查询页面
	 */
	@RequestMapping("/taskCountQuery")
	public String applyWorkQuery() {
		ApplyTaskCountDto applyTaskCountDto = new ApplyTaskCountDto();
		applyTaskCountDto.setStartDate(DateUtils.getCurrDate());
		applyTaskCountDto.setEndDate(DateUtils.getCurrDate());
		applyTaskCountDto.setOperatorId(OrganizationContextHolder.getUserNo());
		Json json = hasTaskCountAuth();
		if(json == null || json.isS()){//没有权限，只能查自己的
			setAttr("hasTaskAuth", "true");
		}else {
			setAttr("hasTaskAuth", "false");
		}
//		return "taskCount/taskCountQuery.ftl";
//		return cacheContext.getPageConfig(PagePathConstant.applyCountQuery);
		setAttr("applyTaskCountDto", applyTaskCountDto);
		return "applyManage/applyWorkStatistics/applyCountQuery_V1.ftl";
	}

	/**
	 * 申请工作量 分页查询
	 * @param
	 * @return 申请工作量查询列表
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Page<ApplyTaskCountDto> list() {
		Page<ApplyTaskCountDto> page = getPage(ApplyTaskCountDto.class);
		//判断是否有查看他人工作权限
		Json json = hasTaskCountAuth();
		if(json == null || !json.isS()){//没有权限，只能查自己的
			String userNo = OrganizationContextHolder.getUserNo();
			setAttr("hasTaskAuth", true);
			page.getQuery().put("operatorId", userNo);
		}else {
			setAttr("hasTaskAuth", false);
		}
		if(page.getQuery().get("startDate")==null) {
			page.getQuery().put("startDate", "2019-01-16");
		}
		
		
		page = applyTaskCountService.getTaskCountList(page);
		
		Date startDate =null;
		if(StringUtils.isNotEmpty(page.getQuery().get("startDate"))) {
			startDate = (Date) page.getQuery().get("startDate");	
		}
		Date endDate = null;
		if(StringUtils.isNotEmpty(page.getQuery().get("endDate"))) {
			endDate = (Date) page.getQuery().get("endDate");	
		}
		// 返回前台传来的日期
		for (ApplyTaskCountDto applyTaskCountDto : page.getRows()) {
			applyTaskCountDto.setStartDate(DateUtils.dateToString(startDate, DateUtils.DAY_YMD_LINE));
			applyTaskCountDto.setEndDate(DateUtils.dateToString(endDate, DateUtils.DAY_YMD_LINE));
		}
		return page;
	}

	/**
	 * 判断操作员是否有工作统计权限（查看他人的权限）
	 * @return
	 */
	@ResponseBody
	@RequestMapping("hasTaskCountAuth")
	public Json hasTaskCountAuth(){
		Json json = Json.newSuccess();
		try {
			List<String> resourceCodeList=new ArrayList<String>();
			resourceCodeList=commonService.getCurrUserResourceCodeList();
			if(resourceCodeList!=null&&resourceCodeList.size()>0){
				if(!resourceCodeList.contains(EcmsAuthority.ECMS_APPLY_TASK_COUNT.name())){
					json.setS(false);//说明没有查看他人的权限
				}
			}
		} catch (Exception e) {
			json.setS(false);//说明没有查看他人的权限
		}
		
		return json;
	}
	
	/**
	 * 统计结果明细页面
	 * 
	 * @return
	 */
	@RequestMapping("/taskDetails")
	public String taskDetails() {

		ApplyTaskCountDto applyTaskCountDto = getBean(ApplyTaskCountDto.class,"applyTaskCountDto");
		String proName = getPara("proName");
		applyTaskCountDto.setProName(proName);

		setAttr("applyTaskCountDto", applyTaskCountDto);
		return "applyManage/applyWorkStatistics/applyCountDetails_V1.ftl";
	}

	/**
	 * 查询结果统计详细信息
	 * 
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/details")
	public Page<ApplyTaskDetailsDto> details() {
		Page<ApplyTaskDetailsDto> page = getPage(ApplyTaskDetailsDto.class);
		String startDateStr = StringUtils.valueOf(page.getQuery().get("startDate"));
		if(!StringUtils.isEmpty(startDateStr)) {
			try {
				Date startDate = DateUtils.getDateStart(DateUtils.stringToDate(startDateStr, DateUtils.DAY_YMD_LINE));
				page.getQuery().put("startDate", startDate);
			} catch (ParseException e) {
				logger.warn("工作量统计查询[开始时间:"+startDateStr+"]转换异常"+e.getMessage());
			}	
		}
		String endDateStr = StringUtils.valueOf(page.getQuery().get("endDate"));
		if(!StringUtils.isEmpty(endDateStr)) {
			try {
				Date endDate = DateUtils.getDateEnd(DateUtils.stringToDate(endDateStr, DateUtils.DAY_YMD_LINE));
				page.getQuery().put("endDate", endDate);
			} catch (ParseException e) {
				logger.warn("工作量统计查询[结束时间:"+startDateStr+"]转换异常"+e.getMessage());
			}			
		}
//		page = applyTaskDetailsService.getTaskDetails(page);
		page = applyTaskDetailsService.getTaskWorkDetails(page);
		return page;
	}

	/**
	 * 将工作统计数据导出到excel
	 */
	@RequestMapping("/exportExcel")
	public void exportToExcel(ApplyTaskCountDto applyTaskCountDto) {
		Page<ApplyTaskCountDto> page = getPage(ApplyTaskCountDto.class);
		page.getQuery().put("startDate", applyTaskCountDto.getStartDate());
		page.getQuery().put("endDate", applyTaskCountDto.getEndDate());
		page.getQuery().put("operatorId", applyTaskCountDto.getOperatorId());
		String proName = applyTaskCountDto.getProName();
		try{
			proName = URLDecoder.decode(proName,"utf-8");
		}catch(Exception e){
			e.getMessage();
		}
		page.getQuery().put("proName", proName);
		
		page.setPageSize(0);//此处不分页
		applyTaskCountService.getTaskCountList(page);
		Date startDate =(Date) page.getQuery().get("startDate");
		Date endDate = (Date) page.getQuery().get("endDate");;
		for (ApplyTaskCountDto app : page.getRows()) {
			app.setStartDate(DateUtils.dateToString(startDate, DateUtils.DAY_YMD_LINE));
			app.setEndDate(DateUtils.dateToString(endDate, DateUtils.DAY_YMD_LINE));
		}
		List<ApplyTaskCountDto> list = page.getRows();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("工作统计信息表");
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		String[] rowHeaders = { "起始日期", "截止日期", "操作人员ID", "任务名称", "数量" };
		HSSFCell cell;
		for (int i = 0; i < rowHeaders.length; 	i++) {
			cell = row.createCell(i);
			cell.setCellValue(rowHeaders[i]);
			cell.setCellStyle(style);
		}
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			cell = row.createCell(0);
			cell.setCellValue(list.get(i).getStartDate());
			cell = row.createCell(1);
			cell.setCellValue(list.get(i).getEndDate());
			cell = row.createCell(2);
			cell.setCellValue(list.get(i).getOperatorId());
			cell = row.createCell(3);
			cell.setCellValue(list.get(i).getProName());
			cell = row.createCell(4);
			cell.setCellValue(list.get(i).getNums());
		}

		try {
			HttpServletResponse response = getResponse();
			String fileName = "工作统计信息表.xls";
			fileName = URLEncoder.encode(fileName,"UTF8"); 
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			logger.error("导出工作统计信息表失败", e);
		}
	}
	/**
	 * 将结果统计详细信息导出到excel
	 */
	@RequestMapping("/exportDetailExcel")
	public void exportDetailToExcel() {
		Page<ApplyTaskDetailsDto> page = getPage(ApplyTaskDetailsDto.class);
		if(page==null || page.getQuery()==null) {
			throw new ProcessException("查询条件不能为空!");
		}
		
		String proName = StringUtils.valueOf(page.getQuery().get("proName"));
		String operatorId = StringUtils.valueOf(page.getQuery().get("operatorId"));
		page=details();
		List<ApplyTaskDetailsDto> list = page.getRows();
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("明细详情");
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		String[] rowHeaders = { 
				"申请件编号", "操作员工号", "操作员姓名","姓名", "证件号码","产品代码","产品描述","单位名称", "任务处理日期", 
				"操作类型", "最新审批状态", "申请额度", "初审额度", "终审额度", 
				"录入备注", "复核备注", "预审备注", "初审备注", "补件备注", "电调备注", "终审备注" };
		HSSFCell cell;
		for (int i = 0; i < rowHeaders.length; i++) {
			cell = row.createCell(i);
			cell.setCellValue(rowHeaders[i]);
			cell.setCellStyle(style);
		}
		for (int i = 0; i < list.size(); i++) {
			ApplyTaskDetailsDto dto = list.get(i);
			if(dto==null) {
				continue;
			}
			row = sheet.createRow(i + 1);
			cell = row.createCell(0);
			cell.setCellValue(dto.getAppNo());
			
			String opId = dto.getOperatorId();
			TmAclUser user = cacheContext.getTmAclUserByUserName(opId);
			String opName = (user==null? "":user.getUserName());
			cell = row.createCell(1);
			cell.setCellValue(dto.getOperatorId());
			
			cell = row.createCell(2);
			cell.setCellValue(opName);
			
			cell = row.createCell(3);
			cell.setCellValue(dto.getName());
			
			cell = row.createCell(4);
			cell.setCellValue(dto.getIdNo());

			String productCd = dto.getProductCd();
			String productDesc = "";
			cell = row.createCell(5);
			cell.setCellValue(productCd);
			TmProduct product = cacheContext.getProduct(productCd);
			if(product!=null) {
				productDesc = product.getProductDesc();
			}
			cell = row.createCell(6);
			cell.setCellValue(productDesc);
			
			cell = row.createCell(7);
			cell.setCellValue(dto.getCorpName());
			
			cell = row.createCell(8);
			cell.setCellValue(DateUtils.dateToString(dto.getTaskProcDate(), DateUtils.FULL_YMDM_LINE));
	
			String rtfState = dto.getRtfState();
			TmAclDict rtf1 = cacheContext.getAclDictByTypeAndCode("RtfState",rtfState);
			String rtfStateDesc = (rtf1==null ? rtfState:rtfState+"-"+rtf1.getCodeName());
			cell = row.createCell(9);
			cell.setCellValue(rtfStateDesc);
			
			String curRtfState = dto.getCurrRtfState();
			TmAclDict rtf2 = cacheContext.getAclDictByTypeAndCode("RtfState",curRtfState);
			String curRtfStateDesc = (rtf2==null ? curRtfState:curRtfState+"-"+rtf2.getCodeName());
			cell = row.createCell(10);
			cell.setCellValue(curRtfStateDesc);
			
			cell = row.createCell(11);
			cell.setCellValue(StringUtils.valueOf(dto.getAppLmt()));
			
			cell = row.createCell(12);
			cell.setCellValue(StringUtils.valueOf(dto.getBasicLmt()));
			
			cell = row.createCell(13);
			cell.setCellValue(StringUtils.valueOf(dto.getFinalLmt()));
			
			cell = row.createCell(14);
			cell.setCellValue(dto.getInputRemark());
			
			cell = row.createCell(15);
			cell.setCellValue(dto.getReviewRemark());
			
			cell = row.createCell(16);
			cell.setCellValue(dto.getPreRemark());
			
			cell = row.createCell(17);
			cell.setCellValue(dto.getBasicRemark());

			cell = row.createCell(18);
			cell.setCellValue(dto.getPatchRemark());

			cell = row.createCell(19);
			cell.setCellValue(dto.getTelRemark());
			
			cell = row.createCell(20);
			cell.setCellValue(dto.getFinalRemark());
		}

		try {
			HttpServletResponse response = getResponse();
			String curDate = DateUtils.dateToString(new Date(), DateUtils.DAY_YMD);
			String fileName = "";
			if(StringUtils.isNotEmpty(operatorId)) {
				fileName =operatorId+"_";
			}
			if(StringUtils.isNotEmpty(proName)) {
				fileName =fileName + proName+"_";
			}
			fileName = fileName + "工作量明细表_"+curDate+".xls";
			fileName = URLEncoder.encode(fileName, "UTF8");
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			logger.error("导出工作量明细表失败", e);
		}
	}
}



