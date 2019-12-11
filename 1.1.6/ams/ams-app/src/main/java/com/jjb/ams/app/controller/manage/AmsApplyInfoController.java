package com.jjb.ams.app.controller.manage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.ecms.biz.service.manage.ApplyInfoQueryService;
import com.jjb.ecms.facility.dto.ApplyInfoQueryDto;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 申请件信息查询
 * @author J.J
 * @date 2018年10月30日上午9:55:19
 */
@Controller
@RequestMapping("/ams_applyInfoQuery")
public class AmsApplyInfoController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplyInfoQueryService applyInfoQueryService;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;

	/**
	 * 加载页面
	 *
	 * @return
	 */
	@RequestMapping("/applyInfoQuery")
	public String applyInfoQuery() {

		Query query = getQuery();
		if(query!=null){
			setAttr("query", query);
		}

		return "/amsManage/applyInfo/applyInfoQuery.ftl";
	}

	/**
	 * 申请进度查询
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/applyInfoList")
	public Page<ApplyInfoQueryDto> applyInfoList() {
		Query query = getQuery();
		String[] rtfStates = getParas("rtfState");
		query.put("rtfState", rtfStates);
		Page<ApplyInfoQueryDto> page = getPage(ApplyInfoQueryDto.class);
		page.setQuery(query);
		page = applyInfoQueryService.applyInfoList(page);
		return page;
	}

	/**
	 * 导出到excel
	 * @throws ParseException
	 */

	@RequestMapping("/exportExcel")
	public void exportToExcel(ApplyInfoQueryDto applyInfoQueryDto){
		Integer rtfStateNum = 1;
		Page<ApplyInfoQueryDto> page = getPage(ApplyInfoQueryDto.class);
		Query query = getQuery();
		String[] rtfStates = getParas("rtfState");
		page.setQuery(query);
		query.put("rtfState", rtfStates);
		page.setPageSize(0);//此处不分页
		applyInfoQueryService.applyInfoList(page);
			List<ApplyInfoQueryDto> list = page.getRows();
		if (rtfStates!=null) {
            page.getQuery().remove("rtfState");
			for (String rtfState : rtfStates) {
				page.getQuery().put("rtfState"+rtfStateNum,rtfState);
				rtfStateNum++;
			}
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("申请件信息表");
		sheet.autoSizeColumn((short)0); //调整第一列宽度
		sheet.autoSizeColumn((short)10);
		HSSFRow row = sheet.createRow(0);
		sheet.setDefaultColumnWidth(20);
		sheet.createFreezePane(0, 1);
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		style.setFont(font);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
		String[] rowHeaders = { "申请件编号","姓名","证件类型","证件号码","客户类型","申请渠道","审批状态","公司名称","公司电话","推广人编号",
								"预审人工号", "录入员","录入时间","复核员","复核时间","补件操作员","补件时间","人工核查员","人工核查时间",
								"初审员","初审时间","电话调查员","电话调查时间","终审员","终审时间"};
		HSSFCell cell;
		for (int i = 0; i < rowHeaders.length; 	i++) {
			cell = row.createCell(i);
			cell.setCellValue(rowHeaders[i]);
			cell.setCellStyle(style);

		}
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow(i + 1);
			cell = row.createCell(0);
			cell.setCellValue(list.get(i).getAppNo());
			cell = row.createCell(1);
			cell.setCellValue(list.get(i).getName());
			cell = row.createCell(2);
			cell.setCellValue(list.get(i).getIdType());
			cell = row.createCell(3);
			cell.setCellValue(list.get(i).getIdNo());
			cell = row.createCell(4);
			cell.setCellValue(list.get(i).getCustType());
			cell = row.createCell(5);
			cell.setCellValue(list.get(i).getAppSource());
			cell = row.createCell(6);
			cell.setCellValue(list.get(i).getRtfState());
			cell = row.createCell(7);
			cell.setCellValue(list.get(i).getCorpName());
			cell = row.createCell(8);
			cell.setCellValue(list.get(i).getEmpPhone());
			cell = row.createCell(9);
			cell.setCellValue(list.get(i).getSpreaderNo());
			cell = row.createCell(10);
			cell.setCellValue(list.get(i).getPreNo());
			cell = row.createCell(11);
			cell.setCellValue(list.get(i).getInputNo());
			cell = row.createCell(12);
			cell.setCellValue(list.get(i).getInputDate());
			cell = row.createCell(13);
			cell.setCellValue(list.get(i).getReviewNo());
			cell = row.createCell(14);
			cell.setCellValue(list.get(i).getReviewDate());
			cell = row.createCell(15);
			cell.setCellValue(list.get(i).getPatchBoltNo());
			cell = row.createCell(16);
			cell.setCellValue(list.get(i).getPatchBoltDate());
			cell = row.createCell(17);
			cell.setCellValue(list.get(i).getPersonCheckNo());
			cell = row.createCell(18);
			cell.setCellValue(list.get(i).getPersonCheckDate());
			cell = row.createCell(19);
			cell.setCellValue(list.get(i).getCheckNo());
			cell = row.createCell(20);
			cell.setCellValue(list.get(i).getCheckDate());
			cell = row.createCell(21);
			cell.setCellValue(list.get(i).getPhoneNo());
			cell = row.createCell(22);
			cell.setCellValue(list.get(i).getPhoneDate());
			cell = row.createCell(23);
			cell.setCellValue(list.get(i).getFinalNo());
			cell = row.createCell(24);
			cell.setCellValue(list.get(i).getFinalDate());
		}
		bizAuditHistoryUtils.saveAuditHistoryByOrdType("进件管理导出数据");
		try {
			HttpServletResponse response = getResponse();
			String fileName = "申请件信息表【"+DateUtils.dateToString(new Date(), DateUtils.FULL_SECOND_LINE_NO)+"】.xls";
			fileName = URLEncoder.encode(fileName,"UTF8");
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (IOException e) {
			logger.error("生成Excel表格失败！"+e.getMessage());
		}
	}
}

