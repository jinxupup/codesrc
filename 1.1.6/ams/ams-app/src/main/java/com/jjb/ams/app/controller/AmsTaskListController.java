package com.jjb.ams.app.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.unicorn.facility.model.Query;
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
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.query.ApplyTaskListService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;
import javax.servlet.http.HttpServletResponse;

/**
 * @description 任务列表controller
 * @author hn
 * @date 2016年8月29日14:00:05
 */
@Controller
@RequestMapping("/ams_tasklist")
public class AmsTaskListController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ApplyTaskListService taskListService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;
	/**
	 * 独立的待办任务查询
	 * @return page 
	 */
	@RequestMapping("/toDoTaskListPage")
	public String toDoTaskListPage(){
		//判断是否有案件分配权限
		boolean assignTaskAuth = taskListService.hasAssignTaskAuth();
		setAttr("assignTaskAuth", assignTaskAuth? "yes":"no");
		return "amsTask/taskList/applyToDoTaskList_V1.ftl";
	}
	/**
	 * 待办任务查询
	 * @return page 
	 */
	@ResponseBody
	@RequestMapping("/todoTaskList")
	public Page<ApplyTaskListDto> todoTaskList(){
		Long start = System.currentTimeMillis();
		logger.info("====>开始执行未分配任务查询"+LogPrintUtils.printCommonStartLog(start,null));
		Page<ApplyTaskListDto> page = getPage(ApplyTaskListDto.class);
		try {
			page = taskListService.getTodoTaskList(page,"initNodeAuthListAms");
		} catch (Exception e) {
			throw new ProcessException("待办任务查询异常["+e.getMessage()+"]");
		}
		logger.info("====>结束执行未分配任务查询"+LogPrintUtils.printCommonEndLog(start,null));
		return page;
	}
	
	/**
	 * 独立的我的任务查询
	 * @return page 
	 */
	@RequestMapping("/myTaskListPage")
	public String myTaskListPage(){
		//设置超时天数
		int overDays = taskListService.getOverDays();
		setAttr("overDays", overDays);
//		TmDitDic taskListAutoRefresh = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_taskListAutoRefresh);
//		if(taskListAutoRefresh != null && StringUtils.equals(taskListAutoRefresh.getRemark(), AppDitDicConstant.onLinOff_on)) {
//			setAttr("autoRefresh", true);
//		}else {
//			setAttr("autoRefresh", false);
//		}
		return "amsTask/taskList/applyMyTaskList_V1.ftl";
	}
	
	/**
	 * 我的任务查询
	 * @return page 
	 */
	@ResponseBody
	@RequestMapping("/myTaskList")
	public Page<ApplyTaskListDto> myTaskList(){
		Long start = System.currentTimeMillis();
		logger.info("====>开始执行我的任务查询"+LogPrintUtils.printCommonStartLog(start,null));
		Page<ApplyTaskListDto> page = getPage(ApplyTaskListDto.class);
		logger.info("====>结束执行我的任务查询"+LogPrintUtils.printCommonEndLog(start,null));
		page = taskListService.getMyTaskList(page,"initNodeAuthListAms");
		return page;
	}
	
	/*
	 * 取消任务
	 */
	@ResponseBody
	@RequestMapping("/cancelMyTask")
	public Json cancelMyTask(String taskId, String appNo){
		Json j = Json.newSuccess();
		try{
			taskListService.cancelTask(taskId,appNo);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		bizAuditHistoryUtils.saveAuditHistory(appNo, "取消任务");//保存审计历史
		return j;
	}
	
	
	/*
	 * 获取分配用户名单 
	 */
	@ResponseBody
	@RequestMapping("/getUserList")
	public Page<TmAclUser> getUserList(){

		Long start = System.currentTimeMillis();
		logger.info("====>开始执行可分配用户名单查询"+LogPrintUtils.printCommonStartLog(start,null));
		Page<TmAclUser> page = getPage(TmAclUser.class);
		page = taskListService.getUserPage(page);
		logger.info("====>结束始执行可分配用户名单查询"+LogPrintUtils.printCommonEndLog(start,null));
		return page;
		
	}
	
	/**
	 * TODO: 同一个案件录入人和复核人不能一致, 该功能需要做成配置的，即某些客户行需要，某些不需要
	 * @param appNo
	 * @param taskName
	 * @return
	 */
	public Json judgeOperaterUser(String appNo, String taskName) {
		Json json = Json.newSuccess();
		try {
			TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
			String creatUser = "";
			if (main != null && StringUtils.isNotEmpty(main.getCreateUser())) {
				creatUser = main.getCreateUser();
			}
			if (OrganizationContextHolder.getUserNo().equals(creatUser)) {
				// throw new ProcessException("操作员不能对同一个申请件执行录入和复核");
			}
		} catch (Exception e) {
			json.setFail(e.getMessage());
			return json;
		}

		return json;
	}
	
	/**
	 * 是否有获取任务的权限
	 * 
	 * @return
	 */
	public Json judgeClaimTaskAuth() {
		Json json = Json.newSuccess();
		String org = OrganizationContextHolder.getOrg();
		String userNo = OrganizationContextHolder.getUserNo();
		List<String> resourceCodeList = new ArrayList<String>();
		if (StringUtils.isNotEmpty(org) && StringUtils.isNotEmpty(userNo)) {

			resourceCodeList = commonService.getCurrUserResourceCodeList();
		}
		if (resourceCodeList != null && resourceCodeList.size() > 0) {
			// 是否有获取任务的权限
			if (!resourceCodeList.contains(EcmsAuthority.ECMS_CLAIM_TASK.name())) {
				String msg = "无获取任务权限！用户名【" + userNo + "】";
				json.setFail(msg);
			}
		}
		return json;
	}

	/**
	 * 判断是否有操作的权限（所有判断控制都写在这里）
	 *
	 * @author hn
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/judgeOperateAuth")
	public Json judgeOperateAuth() {
		Json json = Json.newSuccess();
		String appNo = getPara("appNo");// 获取申请件编号
		String taskName = getPara("taskName");// 任务名

		// 同一个案件录入人和复核人不能一致
		if (StringUtils.isNotBlank(taskName) && taskName.equals("录入复核")) {
			json = judgeOperaterUser(appNo, taskName);
			if (!json.isS()) {// 如果出错
				return json;
			}
		}
		//保存审计历史
		bizAuditHistoryUtils.saveAuditHistory(appNo,"获取申请件");
		return json;
	}

	/**
	 * @Author:shiminghong
	 * @Description :导出我的任务到Excel
	 */
	@RequestMapping("/exportMytaskList")
	public void exportMytaskList(){
		Query query = getQuery();
		Page<ApplyTaskListDto> page =getPage(ApplyTaskListDto.class);
		page.setQuery(query);
		page.setPageSize(0);  //不分页
		page = taskListService.getMyTaskList(page,"initNodeAuthListAms");
		List<ApplyTaskListDto> list = page.getRows();
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFSheet hssfSheet = hssfWorkbook.createSheet("我的进件待办任务列表");
		//调整第一列列宽
		hssfSheet.autoSizeColumn((short) 0);
		hssfSheet.autoSizeColumn((short) 10);
		//创建第一行
		HSSFRow hssfRow = hssfSheet.createRow(0);
		HSSFCellStyle hssfCellStyle =hssfWorkbook.createCellStyle();
		//创建居中格式的表格
		hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//设置每一列的中文描述
		String [] rowHeaders={"姓名","申请件编号","申请类型","申请卡产品","证件号码","单位名","申请额度","受理网点","任务名","获取时间"};
		//单元格
		HSSFCell hssfCell;
		//赋值给创建的第一行
		for (int i =0 ;i<rowHeaders.length; i++){
			hssfCell=hssfRow.createCell(i);
			hssfCell.setCellValue(rowHeaders[i]);
			hssfCell.setCellStyle(hssfCellStyle);
		}
		//开始循环加入查询出来的数据
		for (int j=0;j<list.size();j++){
			//从第二行开始加入数据,每一行顺序加入单元格并赋值数据
			hssfRow =hssfSheet.createRow(j+1);
			hssfCell=hssfRow.createCell(0);
			hssfCell.setCellValue(list.get(j).getName());
			hssfCell=hssfRow.createCell(1);
			hssfCell.setCellValue(list.get(j).getAppNo());
			hssfCell=hssfRow.createCell(2);
			hssfCell.setCellValue(list.get(j).getAppType());
			hssfCell=hssfRow.createCell(3);
			hssfCell.setCellValue(list.get(j).getProductCd());
			hssfCell=hssfRow.createCell(4);
			hssfCell.setCellValue(list.get(j).getIdNo());
			hssfCell=hssfRow.createCell(5);
			hssfCell.setCellValue(list.get(j).getCorpName());
			hssfCell=hssfRow.createCell(6);
			hssfCell.setCellValue(StringUtils.valueOf(list.get(j).getAppLmt()));
			hssfCell=hssfRow.createCell(7);
			hssfCell.setCellValue(list.get(j).getOwningBranch());
			hssfCell=hssfRow.createCell(8);
			hssfCell.setCellValue(StringUtils.valueOf(list.get(j).getTaskName()));
			hssfCell=hssfRow.createCell(9);
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String formatDate= sdf.format(list.get(j).getClaimTime());
				hssfCell.setCellValue(formatDate);
			} catch (Exception e) {
				throw new ProcessException("格式化时间转换失败");
			}
		}
		bizAuditHistoryUtils.saveAuditHistoryByOrdType("进件待办任务导出数据");
		try{
			HttpServletResponse httpServletResponse=getResponse();
			String formatDate="";
			//格式化时间  yyyyMMdd
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
			Date date = new Date();
			try {
				formatDate= simpleDateFormat.format(date);
			} catch (Exception e) {
				throw new ProcessException("格式化时间转换失败");
			}
			String fileName="我的进件待办任务列表【"+formatDate+"】"+".xls";
			fileName = URLEncoder.encode(fileName, "UTF8");
			httpServletResponse.reset();
			httpServletResponse.setContentType("application/vnd.ms-excel");
			httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			OutputStream ouputStream = httpServletResponse.getOutputStream();
			hssfWorkbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			logger.info("我的进件待办任务列表下载成功");
		}catch (IOException e){
			logger.info("我的进件待办任务列表下载失败"+e.getMessage());

		}
	}
}
