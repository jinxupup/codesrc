package com.jjb.cas.app.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
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
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.cas.app.controller.utils.CasPagePathConstant;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.query.ApplyTaskListService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.ecms.infrastructure.TmAppAuditQuota;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmBizAudit;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 任务列表controller
 * @author hn
 * @date 2016年8月29日14:00:05
 */
@Controller
@RequestMapping("/cas_tasklist")
public class CasTaskListController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ApplyTaskListService taskListService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;

	/**
	 * 审计历史页面-条件查询--打开页面
	 *
	 * @param appNo
	 * @return
	 */
	@RequestMapping("/openAuditTerms")
	public String openAuditNew() {
		try {
			return "/casTask/apply/commonDialog/casAuditHistoryPageTerms.ftl";
		} catch (Exception e) {
			logger.error("打开审计历史条件搜索页面异常", e);
			return null;
		}
	}

	/**
	 * 审计历史-条件查询--条件搜索
	 *
	 * @param appNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/AuditQueryForm")
	public Page<TmBizAudit> AuditQueryForm() {
		Long start = System.currentTimeMillis();
		logger.info("====>开始执行我的任务查询"+LogPrintUtils.printCommonStartLog(start,null));
		Page<TmBizAudit> page = getPage(TmBizAudit.class);
		logger.info("====>结束执行我的任务查询"+LogPrintUtils.printCommonEndLog(start,null));
		page = bizAuditHistoryUtils.findAuditHistoryNew(page);
		return page;
	}



	/**
	 * 进入任务列表页面
	 * @return
	 */
	@RequestMapping("/page")
	public String page(){
		//设置超时天数
		int overDays = taskListService.getOverDays();
		setAttr("overDays", overDays);
		//判断是否有案件分配权限
		boolean assignTaskAuth = taskListService.hasAssignTaskAuth();
		setAttr("assignTaskAuth", assignTaskAuth? "yes":"no");
//		TmDitDic taskListAutoRefresh = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_taskListAutoRefresh);
//		if(taskListAutoRefresh != null && StringUtils.equals(taskListAutoRefresh.getRemark(), AppDitDicConstant.onLinOff_on)) {
//			setAttr("autoRefresh", true);
//		}else {
//			setAttr("autoRefresh", false);
//		}
		return cacheContext.getPageConfig(CasPagePathConstant.applyMyTaskList);
	}


	/**
	 * 独立的待办任务查询
	 * @return page
	 */
	@RequestMapping("/toDoTaskListPage")
	public String toDoTaskListPage(){
		//判断是否有案件分配权限
		boolean assignTaskAuth = taskListService.hasAssignTaskAuth();
		setAttr("assignTaskAuth", assignTaskAuth? "yes":"no");
		return "casTask/taskList/applyToDoTaskList_V1.ftl";
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
			Query query = getQuery();
			String[] appFlags = getParas("appFlag");
			query.put("appFlag", appFlags);
			page.setQuery(query);
			page = taskListService.getTodoTaskList(page,"initNodeAuthListTodoCas");
		} catch (Exception e) {
			logger.error("未分配任务查询异常",e);
			throw new ProcessException("待办任务查询异常["+e.getMessage()+"]");
		}
		logger.info("====>结束执行未分配任务查询"+LogPrintUtils.printCommonEndLog(start,null));
		return page;
	}
	/**
	 * 待预审任务查询
	 * @return page
	 */
	@RequestMapping("/preToDoTaskListPage")
	public String preToDoTaskListPage(){
		//判断是否有案件分配权限
		boolean assignTaskAuth = taskListService.hasAssignTaskAuth();
		setAttr("assignTaskAuth", assignTaskAuth? "yes":"no");
		return "casTask/taskList/applyPreToDoTaskList_V1.ftl";
	}
	/**
	 * 待预审任务查询
	 * @return page
	 */
	@ResponseBody
	@RequestMapping("/preTodoTaskList")
	public Page<ApplyTaskListDto> preTodoTaskList(){
		Long start = System.currentTimeMillis();
		logger.info("====>开始执行预审未分配任务查询"+LogPrintUtils.printCommonStartLog(start,null));
		Page<ApplyTaskListDto> page = getPage(ApplyTaskListDto.class);
		try {
			page = taskListService.getPreTodoTaskList(page,"initNodeAuthListPre");
		} catch (Exception e) {
			throw new ProcessException("待办任务查询异常["+e.getMessage()+"]");
		}
		logger.info("====>结束执行预审未分配任务查询"+LogPrintUtils.printCommonEndLog(start,null));
		return page;
	}
	/**
	 * 待归档任务查询
	 * @return page
	 */
	@RequestMapping("/filTodoTaskListPage")
	public String filTodoTaskListPage(){
		//判断是否有案件分配权限
		boolean assignTaskAuth = taskListService.hasAssignTaskAuth();
		setAttr("assignTaskAuth", assignTaskAuth? "yes":"no");
		return "casTask/taskList/applyFilToDoTaskList_V1.ftl";
	}
	/**
	 * 待归档任务查询
	 * @return page
	 */
	@ResponseBody
	@RequestMapping("/filTodoTaskList")
	public Page<ApplyTaskListDto> filTodoTaskList(){
		Long start = System.currentTimeMillis();
		logger.info("====>开始执行归档未分配任务查询"+LogPrintUtils.printCommonStartLog(start,null));
		Page<ApplyTaskListDto> page = getPage(ApplyTaskListDto.class);
		//保存审计历史
//		bizAuditHistoryUtils.saveAuditHistoryByOperatorId("待归档任务查询--条件搜索: "+page.getQuery());
		try {
			page = taskListService.getPreTodoTaskList(page,"initNodeAuthListFil");
		} catch (Exception e) {
			throw new ProcessException("待办任务查询异常["+e.getMessage()+"]");
		}
		logger.info("====>结束执行归档未分配任务查询"+LogPrintUtils.printCommonEndLog(start,null));
		return page;
	}
	/**
	 * 已完成任务查询页面跳转路径
	 * @return page
	 */
	@RequestMapping("/completedTaskListPage")
	public String completedTaskListPage(){
		//判断是否有案件分配权限
		/*boolean assignTaskAuth = taskListService.hasAssignTaskAuth();
		setAttr("assignTaskAuth", assignTaskAuth? "yes":"no");*/
		return "casTask/taskList/applyCompletedTaskList_V1.ftl";
	}
	/**
	 * 已完成任务查询方法
	 * @return page
	 */
	@ResponseBody
	@RequestMapping("/completedTaskList")
	public Page<ApplyTaskListDto> completedTaskList(){
		Query query = getQuery();
		Long start = System.currentTimeMillis();
		logger.info("====>开始执行已完成任务查询"+LogPrintUtils.printCommonStartLog(start,null));
		String[] appFlags = getParas("appFlag");
		query.put("appFlag", appFlags);
		Page<ApplyTaskListDto> page = getPage(ApplyTaskListDto.class);
		page.setQuery(query);
		page = taskListService.getCompletedTaskList(page);
		logger.info("====>结束执行已完成任务查询"+LogPrintUtils.printCommonEndLog(start,null));
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
		return "casTask/taskList/applyMyTaskList_V1.ftl";
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
		Query query = getQuery();
		String[] appFlags = getParas("appFlag");
		query.put("appFlag", appFlags);
		page.setQuery(query);
		
		page = taskListService.getMyTaskList(page,"initNodeAuthListCas");
		logger.info("====>结束执行我的任务查询"+LogPrintUtils.printCommonEndLog(start,null));

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
		bizAuditHistoryUtils.saveAuditHistory(appNo, "审批-取消任务");//保存审计历史
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
	 * 判断初审额度是否够
	 * @param chkLmt
	 * @param userNo
	 * @return
	 */
	public Json judgeBasicCheckLmt(String chkLmt, String userNo, String taskName) {
		Json json = Json.newSuccess();
		//1.如果页面没有初审额度，下面可以不用判断，即所有的终审操作员都可以执行
		//2.如果用户userNo为空，则为当前用户
		if (StringUtils.isBlank(userNo)) {
			userNo = OrganizationContextHolder.getUserNo();
		}
		TmAppAuditQuota tafaq = cacheContext.getTmAppAuditQuotaForCache(userNo, "applyinfo-check");
//		if (tafaq == null) {
//			//如果审批额度 > 初审额度
//			json.setFail("当前用户[" + userNo + "]未设置初审的审批额度范围,请联系管理员设置!");
//			return json;
//		}
		if (tafaq!=null && (tafaq.getApprovalMaximum() != null && tafaq.getApprovalMaximum().compareTo(new BigDecimal(0)) > 0
				&& StringUtils.isNotEmpty(chkLmt)
				&& tafaq.getApprovalMaximum().compareTo(new BigDecimal(chkLmt)) < 0)
				|| (tafaq!=null && tafaq.getApprovalMaximum() == null)) {
			//如果审批额度 > 初审额度
			json.setFail("没审批权限或审批额度不够！");
		}

		return json;
	}

	/**
	 * 判断终审额度是否够
	 * @param chkLmt
	 * @param userNo
	 * @return
	 */
	public Json judgeFinalLmt(String chkLmt, String userNo,String taskName){
		Json json = Json.newSuccess();
		//1.如果页面没有初审额度，下面可以不用判断，即所有的终审操作员都可以执行
		//2.如果用户userNo为空，则为当前用户
		if(StringUtils.isBlank(userNo)){
			userNo = OrganizationContextHolder.getUserNo();
		}
		TmAppAuditQuota tafaq = cacheContext.getTmAppAuditQuotaForCache(userNo, "applyinfo-finalaudit");
		if(tafaq==null){
			//如果审批额度 > 初审额度
			json.setFail("当前用户["+userNo+"]未设置终审的审批额度范围,请联系管理员设置!");
			return json;
		}
		if((tafaq!=null && tafaq.getApprovalMaximum() != null && tafaq.getApprovalMaximum().compareTo(new BigDecimal(0))>0
				&& StringUtils.isNotEmpty(chkLmt)
				&& tafaq.getApprovalMaximum().compareTo(new BigDecimal(chkLmt))<0) || tafaq.getApprovalMaximum()==null){
			//如果审批额度 > 初审额度
			json.setFail("没审批权限或审批额度不够！");
		}
		return json;
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
				String msg = "无获取任务权限！机构号[" + org + "],用户名[" + userNo + "]";
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
//		String taskId = getPara("taskId");// 获取任务ID
		String chkLmt = getPara("chkLmt");// 获取初审额度
		String taskName = getPara("taskName");// 任务名
		String userNo = getPara("userNo");// 获取用户

		if (StringUtils.isNotBlank(taskName) && taskName.equals("预审")) {
			bizAuditHistoryUtils.saveAuditHistory(appNo, "审批待预审任务获取件");//保存审计历史
		}else {
			bizAuditHistoryUtils.saveAuditHistory(appNo, "审批待分配任务获取件");//保存审计历史
		}

		// 同一个案件录入人和复核人不能一致
		if (StringUtils.isNotBlank(taskName) && taskName.equals("录入复核")) {
			json = judgeOperaterUser(appNo, taskName);
			if (!json.isS()) {// 如果出错
				return json;
			}
		}

		// 判断初审额度是否够用 
		if (StringUtils.isNotBlank(taskName) && taskName.equals("初审调查")) {
			json = judgeBasicCheckLmt(chkLmt, userNo, taskName);
			if (!json.isS()) {
				return json;
			}
		}

		// 判断终审额度是否够
		if (StringUtils.isNotBlank(taskName) && taskName.equals("终审")) {
			json = judgeFinalLmt(chkLmt, userNo, taskName);
			if (!json.isS()) {// 如果出错
				return json;
			}
		}
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
		page=taskListService.getMyTaskList(page,"initNodeAuthListCas");
		List<ApplyTaskListDto> list = page.getRows();
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		HSSFSheet hssfSheet = hssfWorkbook.createSheet("我的审批待办任务列表");
		//调整第一列列宽
		hssfSheet.autoSizeColumn((short) 0);
		hssfSheet.autoSizeColumn((short) 10);
		//创建第一行
		HSSFRow hssfRow = hssfSheet.createRow(0);
		HSSFCellStyle hssfCellStyle =hssfWorkbook.createCellStyle();
		//创建居中格式的表格
		hssfCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//设置每一列的中文描述
		String [] rowHeaders={"姓名","申请件编号","申请类型","申请卡产品","上一任务操作人","证件号码","移动电话","推广人","单位名","初审额度","任务名","获取时间"};
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
			hssfCell.setCellValue(list.get(j).getTaskLastOpUser());
			hssfCell=hssfRow.createCell(5);
			hssfCell.setCellValue(list.get(j).getIdNo());
			hssfCell=hssfRow.createCell(6);
			hssfCell.setCellValue(list.get(j).getCellPhone());
			hssfCell=hssfRow.createCell(7);
			hssfCell.setCellValue(list.get(j).getSpreaderNo()+list.get(j).getSpreaderName());
			hssfCell=hssfRow.createCell(8);
			hssfCell.setCellValue(list.get(j).getCorpName());
			hssfCell=hssfRow.createCell(9);
			hssfCell.setCellValue(StringUtils.valueOf(list.get(j).getChkLmt()));
			hssfCell=hssfRow.createCell(10);
			hssfCell.setCellValue(list.get(j).getTaskName());
			hssfCell=hssfRow.createCell(11);
			try {
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				String formatDate= sdf.format(list.get(j).getClaimTime());
				hssfCell.setCellValue(formatDate);
			} catch (Exception e) {
				throw new ProcessException("格式化时间转换失败");
			}
		}
		bizAuditHistoryUtils.saveAuditHistoryByOrdType("审批待办任务数据导出");
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
			String fileName="我的审批待办任务列表【"+formatDate+"】"+".xls";
			fileName = URLEncoder.encode(fileName, "UTF8");
			httpServletResponse.reset();
			httpServletResponse.setContentType("application/vnd.ms-excel");
			httpServletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			OutputStream ouputStream = httpServletResponse.getOutputStream();
			hssfWorkbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			logger.info("我的审批待办任务列表下载成功");
		}catch (IOException e){
			logger.info("我的审批待办任务列表下载失败"+e.getMessage());

		}
	}

}
