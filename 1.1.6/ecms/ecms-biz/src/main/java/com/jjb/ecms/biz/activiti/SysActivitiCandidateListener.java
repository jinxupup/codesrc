package com.jjb.ecms.biz.activiti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjb.acl.access.service.AccessUserService;
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.bus.TaskTransferStatus;
import com.jjb.acl.facility.enums.bus.TaskTransferType;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.activiti.impl.ActivitiCandidateListener;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.apply.TmAppMsgSendService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.commonDialog.ApplyHistoryService;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.dto.SimpleUser;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppUserRelation;
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 *
 * @Description: 案件自动分配
 * @author JYData-R&D-HN
 * @date 2016年9月23日 下午3:56:12
 * @version V1.0
 */
@Service
public class SysActivitiCandidateListener extends ActivitiCandidateListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = 1L;

//	@Autowired
//	private RepositoryService repositoryService;
//
//	@Autowired
//	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;
	@Autowired
	private ApplyQueryService applyQueryService;
	//	@Autowired
//	private ApplyTaskDetailsService applyTaskDetailsService;
//	@Autowired
//	private SysParamService sysParamService;
//	@Autowired
//	private ApplyHistoryService applyHistoryService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private AccessUserService accessUserService;
	//	@Autowired
//	private ApplyInputService applyInputService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private TmAppAuditDao tmAppAuditDao;
	@Autowired
	private TmAppMsgSendService tmAppMsgSendService;
	@Autowired
	private ApplyHistoryService applyHistoryService;

	private static Map<String,String> filterUser = new HashMap<String, String>();

	@Override
	public void notify(DelegateTask delegateTask) {
//		String processKey = repositoryService.createProcessDefinitionQuery()
//				.processDefinitionId(delegateTask.getProcessDefinitionId())
//				.singleResult().getKey();
		String taskKey = delegateTask.getTaskDefinitionKey();
		String taskName = delegateTask.getName();
//		String authority = ActivitiAuthorityUtils.getAuthorityByProcessTaskKey(
//				processKey, taskKey);

		// 由于 activiti 的图形编辑器必须指定 candidate group，所以这里约定使用dummy，然后删了它
		// delegateTask.deleteCandidateGroup("dummy");

//		List<Integer> roleIds = securityService.getRoleIdsByAuthority(authority);
//		taskKey = taskKey.replaceAll("-", "_");
//		taskKey=taskKey.toUpperCase();
//		String[] authString={taskKey};
//		List<TmAclUser> aclUsers=accessUserService.getUserMenus(authString);
//		List<String> list = new ArrayList<String>();
//		for (TmAclUser acluser : aclUsers) {
//			list.add(acluser.getUserNo());
//		}
//		delegateTask.addCandidateGroups(list);
		//取出业务表中的节点信息
//		getVariable(delegateTask);
//		String taskId = delegateTask.getId();
//		String appNo ="";
//		appNo = (String) delegateTask.getVariable("applyAppNo");
//		if(StringUtils.isEmpty(appNo)){
//			appNo=(String) delegateTask.getExecution().getProcessBusinessKey();
//		}
		// 自动分配
//		assignee(taskId,taskKey,appNo);
	}

	/**
	 * 1-1.通过操作员提交任务发起的自动分配动作保留：录入复核退回、初审退回、终审退回、重审等共计4个状态操作。
	 * 1-2.录入复核退回、初审退回、终审退回、重审等分配到原经办人手中（不考虑手头案件数量和是否自动分配，后续若新增退回操作，都是类似处理）
	 * 1-3.如果原经办人权限或者发生岗位变更，则该任务变成待分配状态。
	 * @param taskId
	 * @param taskKey
	 * @param appNo
	 */
	public void assignee(String taskId,String taskKey,String appNo) {

		String org = OrganizationContextHolder.getOrg();
		String opUser = OrganizationContextHolder.getUserNo();
		if(appNo==null || appNo.equals("") || org==null || org.equals("")){
			logger.error("无法正常分配，申请件编号["+appNo+"] 或者机构号["+org+"] 为空");
			return;
		}
		logger.info("开始分配...申请件编号["+appNo+"],机构号["+org+"],opUser["+opUser+"],taskId["+taskId+"],taskKey["+taskKey+"]");
		TmAppMain appMain =  applyQueryService.getTmAppMainByAppNo(appNo);
		if(appMain==null || appMain.getOwningBranch()==null){
			return;
		}
		//先查询出任务
		List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(appNo).list();
		Task task = null;
		if(taskList!=null && taskList.size()>0) {
			task = taskList.get(0);
		}
		//获取系统所有分支行
		List<String> branchIds = setOwningBranchVariable(appMain.getOwningBranch());
		// 获取拥有操作此流程的员工,此流程未分配员工，忽略分配步骤
		Map<String , SimpleUser> users = filterEmployees(taskKey,appMain.getProductCd(),branchIds);
		if (users == null || users.size() == 0) {
			return;
		}
		//如果是录入复核退回、初审退回、终审退回、重审到初审、补件完成到初审
		//如果是非定时任务发起的达到初审的，那么就是人工操作进的，如果是人工操作近的话了，据需要判断是否是补件完成和人工核查提交。
		String rtfState = appMain.getRtfState();
		String source =AppConstant.Claim;
		//如果是初审要求补件，则案件分配给初审自己
		if(StringUtils.isNotBlank(rtfState) && rtfState.equals(RtfState.F08.toString())	){
			// !设置任务转移记录
			TmTaskTransfer taskTran = new TmTaskTransfer();
			taskTran.setAppNo(appNo);
			taskTran.setOrg(appMain.getOrg());
			taskTran.setAssigner(AppConstant.SYS_AUTO);
			taskTran.setOwner(StringUtils.setValue(opUser, appMain.getTaskLastOpUser()));
			taskTran.setTransferType(TaskTransferType.AUTO_ASSIGNE.state);
			taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
			activitiService.assingneeTask(task, appMain, taskTran, source);
		}else if(StringUtils.isNotBlank(rtfState)
				&&(rtfState.equals(RtfState.B15.toString())		//录入复核退回到录入修改
				|| rtfState.equals(RtfState.E15.toString())		//人工核查退回到录入修改
				|| rtfState.equals(RtfState.F02.toString())		//初审退回到人工核查
				|| rtfState.equals(RtfState.F03.toString())		//初审退回到预审
				|| rtfState.equals(RtfState.F07.toString())		//初审退回到录入修改
				|| rtfState.equals(RtfState.F11.toString())		//初审退回到电调
				|| rtfState.equals(RtfState.F18.toString())		//电调退回到初审
				|| rtfState.equals(RtfState.F19.toString())		//电话调查退回录入修改
				|| rtfState.equals(RtfState.K08.toString())		//终审退回到电调
				|| rtfState.equals(RtfState.K18.toString())		//终审退回初审
				|| rtfState.equals(RtfState.G10.toString())		//补件到初审
				|| rtfState.equals(RtfState.A30.toString())		//重审到初审
				|| rtfState.equals(RtfState.A35.toString())		//重审到电调
				|| rtfState.equals(RtfState.A40.toString())		//重审到终审
		)){

			String hisAppNo = null;
			//重审件中只有重审到初审,电调,终审才要查询改件历史操作人
			if(rtfState.equals(RtfState.A30.state)
					||rtfState.equals(RtfState.A35.state)
					||rtfState.equals(RtfState.A40.state)){
				TmAppAudit tmAppAudit =tmAppAuditDao.getTmAppAuditByAppNo(appNo);
				if (tmAppAudit!=null){
					hisAppNo = tmAppAudit.getAppNo();
				}
			}
			String historyOpUser = commonService.getHisOpUser(appNo, hisAppNo, rtfState);
			//正常流程往下流转时------
			logger.info("回原经办人分配...申请件编号["+appNo+"],opUser["+opUser+"],taskId["+taskId+"],taskKey["+taskKey
					+"],rtfState["+appMain.getRtfState()+"],hisAppNo["+hisAppNo+"]");
			if (StringUtils.isNotBlank(historyOpUser) && isInUserList(users, historyOpUser)) {
				// !设置任务转移记录
				TmTaskTransfer taskTran = new TmTaskTransfer();
				taskTran.setAppNo(appNo);
				taskTran.setOrg(appMain.getOrg());
				taskTran.setAssigner(AppConstant.SYS_AUTO);
				taskTran.setOwner(historyOpUser);
				taskTran.setTransferType(TaskTransferType.AUTO_ASSIGNE.state);
				taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
				activitiService.assingneeTask(task, appMain, taskTran, source);
			}
			return;
		} /*else if (StringUtils.isNotBlank(rtfState) && rtfState.equals(RtfState.B20.toString()) //预审完成
			) {
			// 获取审批历史
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("proName","初审调查");
			map.put("idNo",appMain.getIdNo());
			map.put("_SORT_NAME", "createDate");
			map.put("_SORT_ORDER", "DESC");
			List<TmAppHistory> tmAppHistoryList = applyHistoryService.getAppHistroyByParam(map);
			TmAppHistory lastTmAppHistory= new TmAppHistory();
			if(tmAppHistoryList!=null && tmAppHistoryList.size()>0
					&& StringUtils.isNotEmpty(tmAppHistoryList.get(0).getOperatorId())) {
				lastTmAppHistory=tmAppHistoryList.get(0);
				logger.info("同一个人的初审件直接到历史初审人员，申请件编号["+appNo+"],历史任务人["+lastTmAppHistory.getOperatorId()+"]");
				// !设置任务转移记录
				TmTaskTransfer taskTran = new TmTaskTransfer();
				taskTran.setAppNo(appNo);
				taskTran.setOrg(appMain.getOrg());
				taskTran.setAssigner(AppConstant.SYS_AUTO);
				taskTran.setOwner(lastTmAppHistory.getOperatorId());
				taskTran.setTransferType(TaskTransferType.AUTO_ASSIGNE.state);
				taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
				activitiService.assingneeTask(task, appMain, taskTran, source);
			}else {
				logger.info("申请件["+appNo+"],未检索到历史任务人"+"分配到同一个推广员下对应的初审人员");
				TmAppPrimCardInfo tmAppPrimCardInfo = applyQueryService.getTmAppPrimCardInfoByAppNo(appNo);
				Map<String,Object> maps=new HashMap<String,Object>();
				maps.put("proName","初审调查");
				maps.put("idNo",tmAppPrimCardInfo.getSpreaderNo());
				maps.put("_SORT_NAME", "createDate");
				maps.put("_SORT_ORDER", "DESC");
				List<TmAppHistory> tmAppHistoryLists = applyHistoryService.getAppHistroyByParam(map);
				TmAppHistory lastTmAppHistorys= new TmAppHistory();
				//同一个推广员的初审件直接到历史初审人员
				if(tmAppHistoryLists!=null && tmAppHistoryLists.size()>0
						&& StringUtils.isNotEmpty(tmAppHistoryLists.get(0).getOperatorId())) {
					lastTmAppHistorys=tmAppHistoryLists.get(0);
					logger.info("同一个推广员的初审件直接到历史初审人员，申请件编号["+appNo+"],历史任务人["+lastTmAppHistorys.getOperatorId()+"]");
					// !设置任务转移记录
					TmTaskTransfer taskTran = new TmTaskTransfer();
					taskTran.setAppNo(appNo);
					taskTran.setOrg(appMain.getOrg());
					taskTran.setAssigner(AppConstant.SYS_AUTO);
					taskTran.setOwner(lastTmAppHistorys.getOperatorId());
					taskTran.setTransferType(TaskTransferType.AUTO_ASSIGNE.state);
					taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
					activitiService.assingneeTask(task, appMain, taskTran, source);
				}
			}
			return;
		}*/ else if (StringUtils.isNotBlank(rtfState) &&(rtfState.equals(RtfState.F10.toString()) 	//初审完成
				|| rtfState.equals(RtfState.F06.toString()) //初审调查拒绝
				|| rtfState.equals(RtfState.F21.toString())	//免电调
				|| rtfState.equals(RtfState.F20.toString()) //电话调查完成
				|| rtfState.equals(RtfState.F16.toString())	//电话调查拒绝
		)) {
			TmAppUserRelation tmAppUserRelation=cacheContext.getTmAppUserRelationByUserNo(opUser);
			String userNo=null;
			if(StringUtils.isNotEmpty(tmAppUserRelation)) {
				userNo = tmAppUserRelation.getHighterUserNo();
			}
			TmAppUserRelation tmAppUserRelations =new TmAppUserRelation();
			if(StringUtils.isNotBlank(userNo)) {
				tmAppUserRelations = cacheContext.getTmAppUserRelationByUserNo(userNo);
			}
			if( StringUtils.isNotBlank(userNo)
					&& StringUtils.isNotEmpty(tmAppUserRelations)
					&& StringUtils.equals(tmAppUserRelations.getCondition(), "A")) {
				//上判断条件为：是否初审人有对应终审人并且该终审人是否在职状态
				//写入获得的初审对应终审任务人进入工作流
				Boolean assign=false;
				String authString = null;
				/*if(rtfState.equals(RtfState.F10.toString())){ //初审完成
					authString = "CAS_APPLY_TEL_SURVEY"+"-"+userNo;
				}else{
					authString = "CAS_APPLY_FINALAUDIT"+"-"+userNo;
				}*/
				authString = "CAS_APPLY_FINALAUDIT"+"-"+userNo;
				TmAclUser aclUsers = cacheContext.getAuthorityUsers(authString);
				if(aclUsers !=null && aclUsers.getUserNo() !=null){
					assign=true;
				}
				if(assign) {
					logger.info("初审件直接到上级终审人员，申请件编号[" + appNo + "],上级任务人[" + tmAppUserRelations.getHighterUser() + "]");
					// !设置任务转移记录
					TmTaskTransfer taskTran = new TmTaskTransfer();
					taskTran.setAppNo(appNo);
					taskTran.setOrg(appMain.getOrg());
					taskTran.setAssigner(AppConstant.SYS_AUTO);
					taskTran.setOwner(userNo);
					taskTran.setTransferType(TaskTransferType.AUTO_ASSIGNE.state);
					taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
					activitiService.assingneeTask(task, appMain, taskTran, source);
				}
			}
			return;
		}
	}

	/**
	 * 找到拥有当前节点权限的员工
	 *
	 * @param taskKey
	 * @return
	 */
	private Map<String , SimpleUser> filterEmployees(String taskKey,String productCd,List<String> branchIds) {
		Map<String, SimpleUser> map1 = new HashMap<String, SimpleUser>();
		EcmsAuthority[] ss = EcmsAuthority.values();
		String key = "";
		for (int i = 0; i < ss.length; i++) {
			EcmsAuthority ah = ss[i];
			if (ah.lab.equals(taskKey)) {
				key = ah.name();
				break;
			}
		}
		key = StringUtils.setValue(key, taskKey);
		String[] authString = { key };
		List<TmAclUser> aclUsers = accessUserService.getUserMenus(authString);
		for (TmAclUser acluser : aclUsers) {
			// 不在过滤列表中的用户才可以继续使用
			if (filterUser == null || filterUser.size() == 0
					|| (acluser.getUserNo() != null && !filterUser.containsKey(taskKey + "-" + acluser.getUserNo()))) {
				SimpleUser su = new SimpleUser();
				su.setUserNo(acluser.getUserNo());
				su.setUserChName(acluser.getUserName());
				su.setBranchId(acluser.getBranchCode());
				if (StringUtils.isNotEmpty(acluser.getBranchCode()) && branchIds.contains(acluser.getBranchCode())) {
					map1.put(su.getUserNo(), su);
				}
			}
		}
		return map1;
	}

	/**
	 * 判断所属人是否在筛选后的user集合中（此处不考虑操作员手中案件数量）
	 * @param users
	 * @param checkOperator
	 * @return
	 */
	private boolean isInUserList(Map<String,SimpleUser> users,
								 String checkOperator) {
		if(users.containsKey(checkOperator)){
			return true;
		}
		return false;
	}

	/**
	 * 设置本行以及所有上级网点变量
	 * @param owningBranch
	 * @param data
	 * @param productMap
	 */
	private List<String> setOwningBranchVariable(String owningBranch) {
		List<String> result = new ArrayList<String>();
		TmAclBranch curBran =cacheContext.getTmAclBranchByCode(owningBranch);
		if(curBran!=null){
			result.add(curBran.getBranchCode());
			TmAclBranch branch = null;
			String oBranch = curBran.getBranchCode();
			int i = 0;
			while (true) {
				branch = cacheContext.getTmAclBranchByCode(oBranch);
				if (branch == null){
					break;
				}
				oBranch = branch.getParentCode();
				result.add(oBranch);
				i++;
				if(i>4){
					break;
				}
			}
		}
		return result;
	}

}