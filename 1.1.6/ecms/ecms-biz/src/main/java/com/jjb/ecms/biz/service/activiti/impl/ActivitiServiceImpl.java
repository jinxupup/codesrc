package com.jjb.ecms.biz.service.activiti.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.jjb.acl.biz.dao.TmAclDictDao;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.bus.TaskTransferStatus;
import com.jjb.acl.facility.enums.bus.TaskTransferType;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.acl.infrastructure.TmAclDict;
import com.jjb.ecms.biz.activiti.RuntimeServiceExt;
import com.jjb.ecms.biz.activiti.SysActivitiCandidateListener;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.biz.dao.manage.AbnormalProcessAppDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.facility.dto.DeploymentDto;
import com.jjb.ecms.facility.dto.ProcessDefinitionDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description 工作流操作实现类
 * @author hn
 * @date 2016年9月2日09:44:26 
 */
@Transactional(readOnly=false)
@Service("activitiService")
public class ActivitiServiceImpl implements ActivitiService {

	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private FormService formService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TmAclDictDao aclDictDao;
	@Autowired
	private TmAppMainDao tmAppMainDao;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private ProcessEngineConfigurationImpl processEngineConfiguration;
	@Autowired
	private AbnormalProcessAppDao abnormalProcessAppDao;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private TmAppAuditDao tmAppAuditDao;
	@Autowired
	private CommonService commonService;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SysActivitiCandidateListener sysActivitiCandidateListener;
	/**
	 * 发起工作流
	 */
	@Override
	@Transactional
	public String startNewProcess(String processKey, String businessKey ,Map<String, Serializable> vars) {
		if (isBusinessKeyExists(processKey, businessKey))
			throw new ProcessException("相同操作已存在");
		String opUser = "";
		try {
			opUser = OrganizationContextHolder.getUserNo();
		} catch (Exception e) {
			logger.error("未获取到登录用户", e);
		}
		try {
			logger.info("开始启动工作流[{}]，业务键为[{}],操作员[{}]", processKey, businessKey, opUser);
			ProcessInstance pi = runtimeService.startProcessInstanceByKey(
					processKey, businessKey,new HashMap<String, Object>(vars));
			
			//优化，将TaskId保存到Tm_App_Main
			if(StringUtils.isNotEmpty(businessKey)){

				Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
				if(task!=null&&StringUtils.isNotEmpty(task.getId())){
					TmAppMain main=tmAppMainDao.getTmAppMainByAppNo(businessKey);
					if(main==null){
						throw new ProcessException("未查询到该申请件["+businessKey+"]信息，请重试!");
					}
					main.setTaskId(task.getId());
					//如果是重审回录入修改的件,任务所属人为空
					if (StringUtils.isNotBlank(main.getRtfState())){
						if(main.getRtfState().equals(RtfState.A10.name())){
							main.setTaskOwner(null);
						}
					}
					main.setTaskLastOpUser(OrganizationContextHolder.getUserNo());//上一任务
					tmAppMainDao.updateTmAppMain(main);
					sysActivitiCandidateListener.assignee(task.getId(),task.getTaskDefinitionKey(),businessKey);
				}

			}
			logger.info("工作流启动结束[{}]，业务键为[{}]，实例ID为[{}],操作员[{}]", processKey, businessKey, pi.getId(), opUser);
		} catch (Exception e) {
			if (e instanceof ProcessException) {
				logger.error("启动工作流异常,工作流ID[" + processKey + "]，业务键为[" + businessKey + "],操作员[" + opUser + "]", e);
				throw new ProcessException("申请件[" + businessKey + "]启动工作流[" + processKey + "]失败,"+e.getLocalizedMessage());
			}else{
				logger.error("启动工作流异常,工作流ID[" + processKey + "]，业务键为[" + businessKey + "],操作员[" + opUser + "]", e);
				throw new ProcessException("申请件[" + businessKey + "]启动工作流[" + processKey + "]失败,请重试！");
			}
		}
		return null;
	}

	/**
	 * 是否已存在流程实例
	 * 
	 * @param processKey
	 * @param businessKey
	 * @return
	 */
	public boolean isBusinessKeyExists(String processKey, String businessKey) {
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()
				.processInstanceBusinessKey(businessKey, processKey)
				.singleResult();
		return pi != null;
	}

	/**
	 * 根据taskId获取formKey
	 */
	@Override
	@Transactional
	public String handleTask(String taskId,String appNo) throws ProcessException {
		long start = System.currentTimeMillis();
		logger.info("执行任务[" + taskId + "]开始..."+ LogPrintUtils.printAppNoLog(appNo, start,null));
		// TODO Auto-generated method stub
		// 录入和复核操作员不能为同一个，允许同一个操作员同时拥有录入和复核的权限，但是不能对同一个申请件执行录入和复核
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null){
			throw new ProcessException("任务[" + taskId + "]不存在或已被人取走,请刷新页面重试!");
		}
		// 如果是在待办任务页面，直接点击执行任务
		if (StringUtils.isBlank(task.getOwner()) && StringUtils.isBlank(task.getAssignee())) {
			claimTask(taskId,appNo);
		} else {
			// 如果是一个已经被比人抢走的任务
			if (!OrganizationContextHolder.getUserNo().equals(task.getOwner())) {
				throw new ProcessException("任务[" + taskId + "]不存在或已被人取走,请刷新页面重试!");
			}
		}

		// 与界面约定使用_formKey来传formKey
		String formKey = formService.getTaskFormKey( task.getProcessDefinitionId(), task.getTaskDefinitionKey());
		if (StringUtils.isEmpty(formKey)) {
			throw new ProcessException("执行任务[" + taskId + "]时流程定义错误：没有定义formKey。" + task.getTaskDefinitionKey());
		}
		logger.info("执行任务[" + taskId + "]结束..."+LogPrintUtils.printAppNoEndLog(appNo, start,null));
		return formKey;
	}

	/**
	 * 获取任务
	 */
	@Override
	@Transactional
	public void claimTask(String taskId,String appNo) throws ProcessException {
		try {
			// 录入和复核操作员不能为同一个，允许同一个操作员同时拥有录入和复核的权限，但是不能对同一个申请件执行录入和复核
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if(task==null){
				throw new ProcessException("未获取到申请件["+appNo+"]工作流任务["+taskId+"]信息,审批流程或已被更新，请刷新页面重试!");
			}

			TmAppMain appMain = tmAppMainDao.getTmAppMainByAppNo(appNo);
			// !设置任务转移记录
			TmTaskTransfer taskTran = new TmTaskTransfer();
			taskTran.setAppNo(appNo);
			taskTran.setOrg(appMain.getOrg());
			taskTran.setAssigner(OrganizationContextHolder.getUserNo());
			taskTran.setOwner(OrganizationContextHolder.getUserNo());
			taskTran.setTransferType(TaskTransferType.SELF_CLAIM.state);
			taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
			assingneeTask(task, appMain, taskTran, null);
		} catch (ActivitiException ae) {
			logger.error("claimTask任务异常:" + taskId, ae);
			throw new ProcessException("任务不存在或已被人取走");
		}
	}
	

	/**
	 * 认领与解锁任务
	 */
	@Override
	@Transactional
	public void claimOrCancelTask(Task task,String appNo,String opUser,String opType) throws ProcessException {
		String taskId = null;
		try {
			if(task!=null) {
				taskId=task.getId();
			}
			//设置任务转移记录
			String ttfType = TaskTransferType.SELF_CLAIM.state;
			String ttfStatus = TaskTransferStatus.ASSIGNED.state;
			String source = AppConstant.Claim;
			if(StringUtils.equals(opType, "C")){
				ttfStatus = TaskTransferStatus.UNASSIGNED.state;
				ttfType = TaskTransferType.SELF_CANCEL.state;
				source = AppConstant.Cancel;
			}
			TmAppMain tmAppMain = tmAppMainDao.getTmAppMainByAppNo(appNo);

            //!设置任务转移记录
        	TmTaskTransfer taskTran = new TmTaskTransfer();
        	taskTran.setAppNo(appNo);
        	taskTran.setOrg(tmAppMain.getOrg());
        	taskTran.setAssigner(opUser);
        	taskTran.setOwner(opUser);
        	taskTran.setTransferType(ttfType);
        	taskTran.setStatus(ttfStatus);
            assingneeTask(task, tmAppMain, taskTran,source);
				
		} catch (ActivitiException ae) {
			logger.error("claimTask任务异常:" + taskId, ae);
			throw new ProcessException("任务不存在或已被人取走");
		}
	}
	
	/**
	 *  完成任务
	 */
	@Override
	@Transactional
	public void completeTask(String taskId, Map<String, Serializable> vars, String appNo) throws ProcessException {
		try {
			//设置任务转移记录
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			TmTaskTransfer taskTransferRecord = new TmTaskTransfer();
			taskTransferRecord.setAppNo(appNo);
			taskTransferRecord.setTransferType(TaskTransferType.SELF_CLAIM.state);//本人获取
			taskTransferRecord.setTaskKey(task.getTaskDefinitionKey());
			taskTransferRecord.setTaskName(task.getName());
			TmAppMain tmAppMain = tmAppMainDao.getTmAppMainByAppNo(appNo);
			taskTransferRecord.setOrg(tmAppMain.getOrg());
			taskTransferRecord.setRtfState(tmAppMain == null ? null : tmAppMain.getRtfState());
			taskTransferRecord.setOwner(OrganizationContextHolder.getUserNo());
			taskTransferRecord.setAssigner(OrganizationContextHolder.getUserNo());
			taskTransferRecord.setClaimTime(new Date());
			taskTransferRecord.setEndTime(new Date());
			taskTransferRecord.setStatus(TaskTransferStatus.COMPLETED.state);//已分配
			applyInputService.saveOrUpdateTmTaskTransfer(taskTransferRecord, AppConstant.Complete);
			
			//完成任务
			taskService.complete(taskId, (Map<String, Object>)(Map)vars);
			
			//优化，将TaskId保存到Tm_App_Main
			if(StringUtils.isNotEmpty(appNo)){
				Task tempTask = taskService.createTaskQuery().processInstanceBusinessKey(appNo).singleResult();
				if(tempTask!=null&&StringUtils.isNotEmpty(tempTask.getId())){
					TmAppMain main=tmAppMainDao.getTmAppMainByAppNo(appNo);
					main.setTaskId(tempTask.getId());
					main.setTaskOwner(null);
					if (StringUtils.isNotBlank(OrganizationContextHolder.getUserNo())) {
						main.setTaskLastOpUser(OrganizationContextHolder.getUserNo());//上一任务人
					}
					//退回件自动分配到历史任务人名下
					sysActivitiCandidateListener.assignee(tempTask.getId(), tempTask.getTaskDefinitionKey(), appNo);
					//退回，重审
//					if(main.getRtfState().equals(RtfState.B15.toString())
//							||main.getRtfState().equals(RtfState.K08.toString())||main.getRtfState().equals(RtfState.F07.toString())){
//TODO 使用SysActivitiCandidateListener工作流监听处理 
//						String hisOpUser = commonService.getHisOpUser(appNo, null, main.getRtfState());
//						if(hisOpUser!=null){				
//							main.setTaskOwner(hisOpUser == null ? "" : hisOpUser);	
//						}
//					}
//					else{//通过
//						main.setOwner(null);
//					}
					tmAppMainDao.updateTmAppMain(main);
				}
			}
		} catch (Exception e) {
			logger.error("提交工作流异常"+e.getMessage());
			throw new ProcessException("提交工作流失败,系统提示["+e.getMessage()+"]");
		}
	}

	/*
	 * 获取流程节点图片路径 
	 */
	@Override
	@Transactional
	public InputStream getProImgPath(String taskId) {
		
		final Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		if (task == null)
		{
			logger.error("task id {} 没找到", taskId);
			return null;
		}
		//这里必须用command模式调用，是一个静态方法+ThreadLocal全局变量的非常奇葩的设计
		InputStream is = processEngineConfiguration.getCommandExecutor().execute(new Command<InputStream>() {
			@Override
			public InputStream execute(CommandContext commandContext) {
				BpmnModel bpmn = repositoryService.getBpmnModel(task.getProcessDefinitionId());
				return ProcessDiagramGenerator.generateDiagram(bpmn, "png", runtimeService.getActiveActivityIds(task.getExecutionId()));
			}
		});
		
		return is;
	}

	 /**
	  * 获取流程定义列表
	  * @return
	  */
	@Override
	@Transactional
	public List<ProcessDefinitionDto> getProcessDefinitionDtos() {
		// TODO Auto-generated method stub
		List<ProcessDefinitionDto> processDefinitionDtos = new ArrayList<ProcessDefinitionDto>();
		//创建流程定义查询
		ProcessDefinitionDto processDefinitionDto = new ProcessDefinitionDto();
		List<ProcessDefinition> proList = repositoryService.createProcessDefinitionQuery()//创建流程定义查询
				.orderByProcessDefinitionVersion().asc().list();
		for(ProcessDefinition enty : proList){
			processDefinitionDto = new ProcessDefinitionDto();
			processDefinitionDto.setId(enty.getId());
			processDefinitionDto.setProName(enty.getName());
			processDefinitionDto.setProKey(enty.getKey());
			processDefinitionDto.setProVersion(enty.getVersion());
			processDefinitionDto.setResourceName(enty.getResourceName());
			processDefinitionDto.setDiagramResourceName(enty.getDiagramResourceName());
			processDefinitionDto.setDeploymentId(enty.getDeploymentId());
			processDefinitionDtos.add(processDefinitionDto);
		}
		
		return processDefinitionDtos;
	}

	 /**
	  * 保存流程图
	  * @param zipInputStream
	  */
	@Override
	@Transactional
	public void saveDeployment(MultipartFile diagramFile, String proName) {
		if(diagramFile != null){
			try {
				logger.info("开始部署流程图======================>");
				ZipInputStream zipInputStream = new ZipInputStream(diagramFile.getInputStream());
				Deployment deployment = repositoryService.createDeployment()//创建部署对象
					.name(diagramFile.getOriginalFilename())//添加部署名称
					.addZipInputStream(zipInputStream)//
					.deploy();//完成部署
				
				// 将流程图同步到业务字典TM_ACL_DICT
				String deploymentId = deployment.getId();// 获取流程图部署ID
				ProcessDefinition pro = repositoryService.createProcessDefinitionQuery().
						deploymentId(deploymentId).singleResult();// 根据部署ID获取流程
				
				TmAclDict dict = aclDictDao.getDictByType("ProcdefKey", pro.getKey());
				
				TmAclDict tmAclDic = new TmAclDict();
				tmAclDic.setOrg(OrganizationContextHolder.getOrg());
				tmAclDic.setType("ProcdefKey");
				tmAclDic.setTypeName("流程定义");
				tmAclDic.setCode(pro.getKey()); // 
				tmAclDic.setCodeName(pro.getName());// 流程名称
				tmAclDic.setValue(pro.getKey()); // 流程定义的KEY
				tmAclDic.setValue3(pro.getDeploymentId());// 部署ID
				if(dict==null){
					tmAclDic.setValue4(Indicator.N.toString());// 是否为默认流程图
				}else{
					tmAclDic.setValue4(Indicator.Y.toString());// 是否为默认流程图
				}
				
				aclDictDao.save(tmAclDic);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error("部署流程图发生异常", e);
			}
		}
	}

	/**
	  * 获取流程图
	  * @param diagramResourceName
	  * @param deploymentId
	  * @return
	  */
	@Override
	@Transactional
	public InputStream getProImgPathByDeploymentId(String diagramResourceName,
			String deploymentId) {
		
		return repositoryService.getResourceAsStream(deploymentId, diagramResourceName);
	}

	/**
	  * 获取部署信息
	  * @return
	  */
	@Override
	@Transactional
	public List<DeploymentDto> getDeploymentDtos() {
		// TODO Auto-generated method stub
		 List<DeploymentDto> deploymentDtos = new ArrayList<DeploymentDto>();
		 DeploymentDto deploymentDto = new DeploymentDto();
		//创建部署对象查询
		 List<Deployment> deploymentList = repositoryService.createDeploymentQuery()
					.orderByDeploymenTime().desc().list();
		 for(Deployment enty : deploymentList){
			 deploymentDto = new DeploymentDto();
			 deploymentDto.setId(enty.getId());
			 deploymentDto.setDeploymentName(enty.getName());
			 deploymentDto.setDeploymentTime(enty.getDeploymentTime());
			 deploymentDtos.add(deploymentDto);
		 }
		return deploymentDtos;
	}

	/**
	  * 删除该流程部署
	  * @param deploymentId
	  */
	@Override
	@Transactional
	public void deleteDeployment(String deploymentId) {
		logger.info("开始删除流程定义============================>");
		// 删除流程定义
		repositoryService.deleteDeployment(deploymentId, true);
		
		// 删除流程图部署记录
		TmAclDict tmAclDict = new TmAclDict();
		tmAclDict.setOrg(OrganizationContextHolder.getOrg());
		tmAclDict.setType("ProcdefKey");
		tmAclDict.setTypeName("流程定义");
		tmAclDict.setValue3(deploymentId);// 部署ID
		List<TmAclDict> tmAclDictList = aclDictDao.queryForList(tmAclDict);
		if(CollectionUtils.sizeGtZero(tmAclDictList)){
			tmAclDict = tmAclDictList.get(0);
			aclDictDao.deleteByKey(tmAclDict);
		}
		logger.info("删除流程定义成功============================>");
	}

	 /**
	  * 设置默认流程图
	  * @param procdefKey
	  */
	@Override
	@Transactional
	public void initDeployment(String procdefKey, String deploymentId) {
		logger.info("设置默认流程图开始============================>");
		TmAclDict tmAclDict = new TmAclDict();
		tmAclDict.setOrg(OrganizationContextHolder.getOrg());
		tmAclDict.setType("ProcdefKey");
		tmAclDict.setValue4(Indicator.Y.toString());// 原默流程图
		// 取消原来的默认流程图
		List<TmAclDict> tmAclDicts = aclDictDao.queryForList(tmAclDict);
		for (TmAclDict aclDict : tmAclDicts) {
			aclDict.setValue4(Indicator.N.toString());
			aclDictDao.update(aclDict);
		}
		// 设置新的默认
		TmAclDict defaultAclDict = new TmAclDict();
		defaultAclDict.setOrg(OrganizationContextHolder.getOrg());// 组织
		defaultAclDict.setType("ProcdefKey");// 类型
		defaultAclDict.setCode(procdefKey);
		defaultAclDict.setValue(procdefKey);// key
		defaultAclDict.setValue3(deploymentId);// 部署ID
		tmAclDicts = aclDictDao.queryForList(defaultAclDict);
		if (CollectionUtils.isNotEmpty(tmAclDicts)) {
			defaultAclDict = tmAclDicts.get(0);
			defaultAclDict.setValue4(Indicator.Y.toString());// 设为默认
			aclDictDao.update(defaultAclDict);
		} else {
			logger.error("设置默认流程图发生异常，找不到部署ID[{}]对应的流程图", deploymentId);
			throw new ProcessException("设置默认流程图发生异常，找不到部署ID[" + deploymentId
					+ "]对应的流程图");
		}
		logger.info("设置默认流程图结束============================>");
	}
	
	

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public String restartNewProcess(String processKey, String businessKey, Map<String, Serializable> vars) {
		String opUser = "";
		try {
			opUser = OrganizationContextHolder.getUserNo();
		} catch (Exception e) {
			logger.error("未获取到登录用户", e);
		}
		if(StringUtils.isEmpty(processKey) || StringUtils.isEmpty(businessKey) || vars==null ){
			logger.error("申请件[" + businessKey + "]启动工作流[" + processKey + "],vars[" + vars + "]失败,请重试！系统未获取到需要发起工作流的必要条件");
			throw new ProcessException("申请件[" + businessKey + "]启动工作流[" + processKey + "],任务名[" + null + "]失败,请重试！系统未获取到需要发起工作流的必要条件");
		}
		TmAppMain main=tmAppMainDao.getTmAppMainByAppNo(businessKey);
		if(main == null){
			throw new ProcessException("未查询到该申请件["+businessKey+"]信息，请重试!");
		}
		String activitiId = null;
		
		try {
			List<HistoricTaskInstance> list1 = historyService.createHistoricTaskInstanceQuery().processInstanceBusinessKey(businessKey).orderByHistoricTaskInstanceStartTime().desc().list();
			List<HistoricProcessInstance> historicProcessInstances = historyService.createHistoricProcessInstanceQuery().processInstanceBusinessKey(businessKey).orderByProcessInstanceId().desc().list();
			
			if(CollectionUtils.isEmpty(list1) && CollectionUtils.isEmpty(historicProcessInstances)){
				logger.error("申请件[" + businessKey + "]启动工作流[" + processKey + "]找不到历史流程记录！");
//				throw new ProcessException("申请件[" + businessKey + "]启动工作流[" + processKey + "]找不到历史流程记录！");
				String rtfState = "";
				if(vars!=null && vars.containsKey(AppConstant.APPLY_NODE_COMMON_DATA)){
					
					ApplyNodeCommonData applyNodeCommonData = (ApplyNodeCommonData) vars.get(AppConstant.APPLY_NODE_COMMON_DATA);
					applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, main);
					
					
					if(StringUtils.isNotEmpty(applyNodeCommonData.getRtfStateType()) 
							&& (!applyNodeCommonData.getRtfStateType().equals(RtfState.A10.name()) 
									&& !applyNodeCommonData.getRtfStateType().equals(RtfState.B10.name()))){
						applyNodeCommonData.setRtfStateType(RtfState.B10.name());
						vars.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
					}
					rtfState= applyNodeCommonData.getRtfStateType();
				}
				//先保存历史
				TmAppHistory tmAppHistory = new TmAppHistory();
				tmAppHistory = AppCommonUtil.insertApplyHist(businessKey, 
						OrganizationContextHolder.getUserNo(), RtfState.valueOf(rtfState), null, "因流程异常，重新发起审批流程");
				tmAppHistory.setName(main.getName());
				tmAppHistory.setIdType(main.getIdType());
				tmAppHistory.setIdNo(main.getIdNo());
				applyInputService.saveTmAppHistory(tmAppHistory);
				
				startNewProcess(processKey, businessKey, vars);
				
			}else{
				activitiId = historicProcessInstances.get(0).getEndActivityId();
				for (int i = 0; i < list1.size(); i++) {
					HistoricTaskInstance hti = list1.get(i);
					if(hti!=null && StringUtils.isNotEmpty(hti.getTaskDefinitionKey()) 
							&& !hti.getTaskDefinitionKey().equals("endevent1")
							&& !StringUtils.equals(hti.getTaskDefinitionKey(), "applyinfo-patchbolt")){
						//先保存历史记录
						TmAppHistory tmAppHistory = new TmAppHistory();
						tmAppHistory = AppCommonUtil.insertApplyHist(businessKey, 
								OrganizationContextHolder.getUserNo(), RtfState.valueOf(main.getRtfState()), null, "因流程异常，重新发起审批流程");
						tmAppHistory.setName(main.getName());
						tmAppHistory.setIdType(main.getIdType());
						tmAppHistory.setIdNo(main.getIdNo());
						applyInputService.saveTmAppHistory(tmAppHistory);
						
						
						activitiId = hti.getTaskDefinitionKey();
						logger.info("开始重新启动工作流[{}]，业务键为[{}],操作员[{}],任务名[{}]", processKey, businessKey, opUser,activitiId);
						abnormalProcessAppDao.deleteFromHiProcinst(businessKey);
						
						if(StringUtils.equals(activitiId, "applyinfo-patchbolt")) {
							Integer waitTime = null;//默认0天
							String pbWtStr = cacheContext.getPatchBoltParamByType(AppDitDicConstant.pbWaitTime_key);
							if(StringUtils.isNotEmpty(pbWtStr)){
								try {
									Integer int1 = Integer.valueOf(pbWtStr); 
									waitTime = int1;
								} catch (Exception e) {
									logger.error("补件超时默认等待时间转换异常"+pbWtStr,e);
								}
							}
							if(waitTime==null){
								waitTime=1;//默认一天，系统写死的，否则立马就拒绝了，很尴尬
							}
							vars.put(AppConstant.APPLY_PB_STTIME, DateUtils.dateToString(DateUtils.getCurrentDate(), DateUtils.MGT_LINE));
							vars.put(AppConstant.APPLY_PB_TIMEWAIT, "P"+waitTime+"D");
						}
						ProcessInstance pi = ((RuntimeServiceExt)runtimeService).restartProcessInstanceByKey(
								processKey, businessKey,activitiId,new HashMap<String, Object>(vars));
						
						Task task = taskService.createTaskQuery().processInstanceBusinessKey(businessKey).singleResult();
						if(task!=null&&StringUtils.isNotEmpty(task.getId())){
							main.setTaskId(task.getId());
							if(main!=null&&StringUtils.isNotEmpty((main.getRtfState()))){
								if(main.getRtfState().equals(RtfState.A10.name())){
									main.setTaskOwner(null);
								}
							}
							main.setTaskLastOpUser(OrganizationContextHolder.getUserNo());//上一任务
							applyInputService.updateTmAppMain(main);
							
						}
						logger.info("工作流启动结束[{}]，业务键为[{}]，实例ID为[{}],操作员[{}],任务名[{}]", processKey, businessKey, pi.getId(), opUser,activitiId);
						break;
					}
				}
			}
		} catch (Exception e) {
			if (e instanceof ProcessException) {
				logger.error("启动工作流异常,工作流ID[" + processKey + "]，业务键为[" + businessKey + "],操作员[" + opUser + "]", e);
				throw new ActivitiException(e.getLocalizedMessage());
			}if (e instanceof ProcessException) {
				logger.error("启动工作流异常,工作流ID[" + processKey + "]，业务键为[" + businessKey + "],操作员[" + opUser + "]", e);
				throw new ProcessException(e.getLocalizedMessage());
			}else {
				logger.error("启动工作流异常,工作流ID[" + processKey + "]，业务键为[" + businessKey + "],操作员[" + opUser + "],任务名[" + activitiId + "]", e.getMessage());
				throw new ProcessException("申请件[" + businessKey + "]启动工作流[" + processKey + "],任务名[" + activitiId + "]失败,请重试！");
			}
		}
		return null;
	}
	 /**
	  * 获取默认流程图
	  * @return
	  */
	 public String getDefProcess(){
		String process = "";
		TmAclDict tmAclDict = new TmAclDict();
		tmAclDict.setOrg(OrganizationContextHolder.getOrg());
		tmAclDict.setType("ProcdefKey");
		tmAclDict.setTypeName("流程定义");
		tmAclDict.setValue4(Indicator.Y.toString());
		List<TmAclDict> tmAclDictList = aclDictDao.queryForList(tmAclDict);
		if (CollectionUtils.sizeGtZero(tmAclDictList)) {
			process = tmAclDictList.get(0).getValue();
		}else {
			throw new ProcessException("未查询到系统配置的默认流程图，请联系管理员尽快配置!");
		}
		return process;
	 }
	 
	 /**
	  * 获取默认流程图部署ID
	  * @return
	  * 	默认流程图的部署ID，没有则返回空字符串
	  */
	 public String getDefProcessDepId() {
		String deploymentId = "";
		TmAclDict tmAclDict = new TmAclDict();
		tmAclDict.setOrg(OrganizationContextHolder.getOrg());
		tmAclDict.setType("ProcdefKey");
		tmAclDict.setTypeName("流程定义");
		tmAclDict.setValue4(Indicator.Y.toString());
		List<TmAclDict> tmAclDictList = aclDictDao.queryForList(tmAclDict);
		if (CollectionUtils.sizeGtZero(tmAclDictList)) {
			deploymentId = tmAclDictList.get(0).getValue3();// 部署ID
		} 
		return deploymentId;
	}

	/**
	 * 根据申请件编号获取taskId
	 * 
	 * @param appNo
	 * @return
	 */
	@Override
	@Transactional
	public Task getTaskId(String appNo) throws ProcessException {
		try {
			if (StringUtils.isEmpty(appNo)) {
				throw new ProcessException("未获取到申请件信息，请稍后再试！");
			}
			List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(appNo).list();
			if (CollectionUtils.sizeGtZero(taskList)) {
				Task task = taskList.get(0);
				return task;
			}
		} catch (Exception e) {
			throw new ProcessException("未能获取到申请件[" + appNo + "]任务编号");
		}
		return null;
	}

	/**
	 *  分配任务赋值到工作流里面
	 * @param task
	 * @param appNo
	 * @param assignee
	 * @param owner
	 * @throws ProcessException
	 */
	@Override
	public void assingneeTask(Task task,TmAppMain appMain,TmTaskTransfer taskTran,String source) throws ProcessException {
		String taskId = "";
		if(task==null && appMain!=null && StringUtils.isNotEmpty(appMain.getAppNo())) {
			List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(appMain.getAppNo()).list();
			if(taskList!=null && taskList.size()>0) {
				task = taskList.get(0);
			}
		}
		if(task==null){
			throw new ProcessException("未获取到申请件["+appMain.getAppNo()+"]工作流任务["+taskId+"]信息,审批流程或已被更新，请刷新页面重试!");
		}
		taskId=task.getId();
		List<IdentityLink> actIdList= runtimeService.getIdentityLinksForProcessInstance(task.getExecutionId());
		boolean userIsNull = false;
		if(CollectionUtils.isNotEmpty(actIdList)) {
			for (int i = 0; i < actIdList.size(); i++) {
				IdentityLink idl = actIdList.get(i);
				if(idl!=null && StringUtils.isEmpty(idl.getUserId())) {
					userIsNull=true;
					break;
				}
			}
		}
		if(userIsNull) {
			abnormalProcessAppDao.deleteFromIdentitylink(taskId, task.getExecutionId());
		}
		if(taskTran==null) {
			taskTran = new TmTaskTransfer();
		}
		String assignee = StringUtils.setValue(taskTran.getAssigner(), OrganizationContextHolder.getUserNo());
		String owner = taskTran.getOwner();
		// 任务分配者
		taskService.setAssignee(taskId, assignee);// FIXME 需要加入机构
		// 任务所属者
		taskService.setOwner(taskId,  owner);
		//后期优化，给tm_app_main表设置owner_ assigner task_id，
		appMain.setTaskOwner(owner);
		appMain.setUpdateDate(new Date());
		applyInputService.updateTmAppMain(appMain);		
		
		taskTran.setAppNo(appMain.getAppNo());
		taskTran.setTransferType(StringUtils.setValue(taskTran.getTransferType(), TaskTransferType.TRANSFER));//默认转分配
		taskTran.setTaskKey(task.getTaskDefinitionKey());
		taskTran.setTaskName(task.getName());
		taskTran.setRtfState(appMain.getRtfState());
		taskTran.setOwner(owner);
		taskTran.setAssigner(assignee);
		taskTran.setClaimTime(new Date());
		taskTran.setStatus(StringUtils.setValue(taskTran.getStatus(), TaskTransferStatus.ASSIGNED));//已分配
		source=StringUtils.setValue(source, AppConstant.Claim);
		applyInputService.saveOrUpdateTmTaskTransfer(taskTran, source);
	
	}
}