package com.jjb.ams.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.query.ApplyTaskListService;
import com.jjb.ecms.biz.service.query.ApplyTaskTransferListService;
import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 案件转分配controller
 * @author hn
 * @date 2016年8月29日14:00:05
 */
@Controller
@RequestMapping("/ams_taskTransfer")
public class AmsTaskTransferController extends BaseController{

	@Autowired
	private ApplyTaskTransferListService applyTaskTransferListService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyTaskListService taskListService;
	@Autowired
	private ActivitiService activitiService;
	
	@RequestMapping("/page")
	public String page(){
		//设置超时天数
		int overDays = taskListService.getOverDays();
		setAttr("overDays", overDays);
		String userNo = OrganizationContextHolder.getUserNo();
		setAttr("operator", userNo);//将操作人传到页面上（隐藏）
		return "amsTask/taskTransfer/applyTaskTransferPage_V1.ftl";
	}
	
	/*
	 * 获取已分配的任务 
	 */
	@ResponseBody
	@RequestMapping("/list")
	public Page<ApplyTaskListDto> list(){
		
		Page<ApplyTaskListDto> page = getPage(ApplyTaskListDto.class);
		page = applyTaskTransferListService.getTransferTaskList(page,"initNodeAuthListAms");
		return page;
	}
	
	/*
	 * 弹出可分配用户名单
	 */
	@RequestMapping("/userSelect")
	public String userSelect(){
		String select = getPara("select");
		String appNoTaskId = getPara("appNoTaskId");
		setAttr("selectTask", select);//将选择的task传到用户列表页面
		setAttr("appNoTaskId", appNoTaskId);//将选择的task传到用户列表页面
		
		return "/amsTask/taskTransfer/applyAssignedUserList_V1.ftl";
	}
	
	/*
	 * 获取分配用户名单 
	 */
	@ResponseBody
	@RequestMapping("/getUserList")
	public Page<TmAclUser> getUserList(){
		String selectTask = getPara("selectTask");//选择的任务
		Page<TmAclUser> page = getPage(TmAclUser.class);
		page.setQuery(getQuery());
		page = applyTaskTransferListService.getUserPage(page,selectTask);
		
		return page;
		
	}
	
	
	/**
	 * 转分配任务
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/transferTask")
	public Json transferTask(){
		
		Json j = Json.newSuccess();
		String selectTask = getPara("selectTask");
		String userNo = getPara("userNo");
		String appNoTaskId = getPara("appNoTaskId");//页面上传来的taskid：appNo
		
		String[] taskAuth = selectTask.split(",");//将页面传来的taskId：taskKey 一对对 分离（52617:applyinfo-creditReport,29608:input-modify,）
		List<String> taskIdList = new ArrayList<String>();//将所有的taskId放一起
		for(String enty : taskAuth){
			taskIdList.add(enty.split(":")[0]);// taskId：taskKey
		}
		//将任务列表传到service
		try{
			applyTaskTransferListService.transferTask(userNo, appNoTaskId);
		}catch(Exception e){
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	
	/**
	 * 同一个案件录入人和复核人不能一致
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/judgeTransferOperaterUser")
	public Json judgeTransferOperaterUser(){
			Json json = Json.newSuccess();

			String userNo = getPara("userNo");
			String appNoTaskId=getPara("appNoTaskId");		
			String[] appNoAuth = appNoTaskId.split(",");//taskId:appNo
			
			for(String enty : appNoAuth){
				String taskId =enty.split(":")[0];// taskId：taskKey	
				String appNo =enty.split(":")[1];// taskId：taskKey
				try{
					Task task = activitiService.getTaskId(appNo);
					TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
					String creatUser = "";
					if(main!=null && StringUtils.isNotEmpty(main.getCreateUser())){
						creatUser = main.getCreateUser();
					}
					if(task==null){
						throw new ProcessException("未获取到申请件["+appNo+"]对应任务，可能已被更新，请刷新页面后重试");
					}
					//系统绝对不在从工作流里面取数据了
					if (task.getName().equals("录入复核") && userNo.equals(creatUser)) {
						throw new ProcessException("同一个操作员不能对同一个申请件执行录入和复核");
					}
				}catch(Exception e){
					json.setFail(e.getMessage());
					return json;
				}
			}
			return json;
	}
}
