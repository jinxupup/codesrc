package com.jjb.ecms.biz.service.query.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ActivitiException;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.access.service.AccessResourceService;
import com.jjb.acl.biz.dao.TmAclUserDao;
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.TaskTransferStatus;
import com.jjb.acl.facility.enums.bus.TaskTransferType;
import com.jjb.acl.infrastructure.TmAclResource;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.common.TmDitDicDao;
import com.jjb.ecms.biz.dao.manage.AbnormalProcessAppDao;
import com.jjb.ecms.biz.dao.query.TmTaskListDao;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.query.ApplyProcessUtils;
import com.jjb.ecms.biz.service.query.ApplyTaskListService;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.ecms.infrastructure.TmAppAuditQuota;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description 任务列表service实现类
 * @author hn
 * @date 2016年8月30日17:01:27 
 */
@Service("applyTaskListService")
public class ApplyTaskListServiceImpl implements ApplyTaskListService{

	@Autowired
	private TmTaskListDao tmTaskListDao;
	@Autowired
	private TmAclUserDao tmAclUserDao;
	@Autowired
	private TmDitDicDao tmDitDicDao;
//	@Autowired
//	private AccessRoleService accessRoleService;
	@Autowired
	private AccessResourceService accessResourceService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private CommonService commonService;
//	@Autowired
//	private TmAclBranchDao tmBranchDao;
	@Autowired
	private TmAppMainDao tmAppMainDao;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private ApplyProcessUtils applyProcessUtils;
	@Autowired
	private AbnormalProcessAppDao abnormalProcessAppDao;
	@Autowired
	private RuntimeService runtimeService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 根据系统名字来获取未分配任务 排除预审的任务
	 * @param page
	 * @param systemType
	 * @return
	 */
	@Override
	public Page<ApplyTaskListDto> getTodoTaskList(Page<ApplyTaskListDto> page, String systemType) {

		/************************************加上页面查询的条件**************************************/
		page.getQuery().put("userId", OrganizationContextHolder.getUserNo());
		//1.加上当前用户的节点操作权限（只过滤出：录入修改、录入复核、人工核查、预审、初审、补件、终审权限）
		List<String> resourceCodes = commonService.getCurrUserResourceCodeList();//先获取到所有的权限
		List<String> tempNodeAuths = new ArrayList<String>();//存放拥有的人工节点权限
		List<String>  nodeAuthList=new ArrayList<>();
		//存放节点操作权限
		nodeAuthList = cacheContext.getAuthBySystemType(systemType);
		for(String auth : resourceCodes){
			if(nodeAuthList.contains(auth)){
				tempNodeAuths.add(EcmsAuthority.valueOf(auth).lab);
			}
		}
		//如果用户所属组不为空
		if(page.getQuery().get("myRole") != null && StringUtils.isNotBlank(page.getQuery().get("myRole").toString())){
			int roleId = Integer.valueOf(page.getQuery().get("myRole").toString());//获取角色ID
			List<String> list = getTaskDefKeys(roleId);//存放任务名条件
			if (CollectionUtils.sizeGtZero(list)) {
				page.getQuery().put("taskDefKeys", list.toArray(new String[0]));
			}
		}
		splitTaskDefKeys(page, tempNodeAuths);

		//如果选择了受理网点
	/*	if(page.getQuery().get("owningBranch") != null && StringUtils.isNotBlank(page.getQuery().get("owningBranch").toString())){
			String owningBranch = page.getQuery().get("owningBranch").toString();//获取选择的受理网点
			applyProcessUtils.setBranchToPage(page, owningBranch);
		}else{
			page.getQuery().remove("owningBranch");
		}*/
		//如果没选择了受理网点
		if(page.getQuery().get("owningBranch") != null && StringUtils.isNotBlank(page.getQuery().get("owningBranch").toString())){
			String owningBranch = page.getQuery().get("owningBranch").toString();//获取选择的受理网点
			applyProcessUtils.setBranchToPage(page, owningBranch);
		}else{
			String owningBranch =OrganizationContextHolder.getBranchCode();
			if(StringUtils.isNotEmpty(owningBranch)){
				applyProcessUtils.setBranchToPage(page, owningBranch);
			}
		}
		if(page.getQuery()!=null&&page.getQuery().size()>0){
			//给开始日期和截至日期附加时间部分
			if(StringUtils.isNotEmpty((String)page.getQuery().get("startDate"))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse((String) page.getQuery().get("startDate"));
					page.getQuery().put("startDate", DateUtils.getDateStart(date));
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常", e);
				}
			}
			if(StringUtils.isNotEmpty((String)page.getQuery().get("endDate"))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse((String) page.getQuery().get("endDate"));
					page.getQuery().put("endDate",DateUtils.getDateEnd(date));
				} catch (ParseException e) {
					logger.error("案件转分配时间格式转换发生异常", e);
				}
			}
		}
		page = tmTaskListDao.querytoDoPage(page);
		displayLastNodeInList(page); //在列表中显示最后一个退回节点
		return page;
	}

	/*
         * 获取预审待分配任务
         */
	@Override
	@Transactional
	public Page<ApplyTaskListDto> getPreTodoTaskList(Page<ApplyTaskListDto> page,String systemType) {
		// 查询预审待分配任务
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		try {
			//1.加上当前用户的节点操作权限（只过滤出：录入修改、录入复核、人工核查、预审、初审、补件、终审权限）
			List<String> resourceCodes = commonService.getCurrUserResourceCodeList();//先获取到所有的权限
			List<String> tempNodeAuths = new ArrayList<String>();//存放拥有的人工节点权限
			List<String> nodeAuthList = new ArrayList<>();//存放节点操作权限
			nodeAuthList = cacheContext.getAuthBySystemType(systemType);
			for(String auth : resourceCodes){
				if(nodeAuthList.contains(auth)){
					tempNodeAuths.add(EcmsAuthority.valueOf(auth).lab);
				}
			}
//			page.getQuery().put("taskDefKeys", tempNodeAuths.toArray(new String[0]));//节点操作权限
			/************************************加上页面查询的条件**************************************/
			//如果用户所属组不为空
			if(page.getQuery().get("myRole") != null && StringUtils.isNotBlank(page.getQuery().get("myRole").toString())){
				int roleId = Integer.valueOf(page.getQuery().get("myRole").toString());//获取角色ID
				tempNodeAuths = getTaskDefKeys(roleId);//存放任务名条件
			}
			splitTaskDefKeys(page, tempNodeAuths);
			//如果选择了受理网点
			if(page.getQuery().get("owningBranch") != null && StringUtils.isNotBlank(page.getQuery().get("owningBranch").toString())){
				String owningBranch = page.getQuery().get("owningBranch").toString();//获取选择的受理网点
				applyProcessUtils.setBranchToPage(page, owningBranch);

			}else{//说明默认的网点过滤
				//获取当前用户机构
				String branchCode = OrganizationContextHolder.getBranchCode();
				if(StringUtils.isNotEmpty(branchCode)){
					applyProcessUtils.setBranchToPage(page, branchCode);
				}
			}
			if(page.getQuery()!=null&&page.getQuery().size()>0){
				//给开始日期和截至日期附加时间部分
				if(StringUtils.isNotEmpty((String)page.getQuery().get("startDate"))){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = sdf.parse((String) page.getQuery().get("startDate"));
						page.getQuery().put("startDate", DateUtils.getDateStart(date));
					} catch (ParseException e) {
						logger.error("案件转分配查询时间格式转换发生异常", e);
					}
				}
				if(StringUtils.isNotEmpty((String)page.getQuery().get("endDate"))){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					try {
						Date date = sdf.parse((String) page.getQuery().get("endDate"));
						page.getQuery().put("endDate",DateUtils.getDateEnd(date));
					} catch (ParseException e) {
						logger.error("案件转分配时间格式转换发生异常", e);
					}
				}
			}
			page = tmTaskListDao.querypreToDoPage(page);
			displayLastNodeInList(page); //在列表中显示最后一个退回节点
		} catch (Exception e) {
			logger.error("待办任务查询异常", e);
			throw new ProcessException("",e);
		}

		return page;
	}

	/**
	 * 根据系统名字来获取我的任务
	 * @param page
	 * @return
	 */
	@Override
	public Page<ApplyTaskListDto> getMyTaskList(Page<ApplyTaskListDto> page,String systemType) {

		/************************************加上页面查询的条件**************************************/
		page.getQuery().put("userId", OrganizationContextHolder.getUserNo());
		//1.加上当前用户的节点操作权限（只过滤出：录入修改、录入复核、人工核查、预审、初审、补件、终审权限）
		List<String> resourceCodes = commonService.getCurrUserResourceCodeList();//先获取到所有的权限
		List<String> tempNodeAuths = new ArrayList<String>();//存放拥有的人工节点权限\
		List<String>  nodeAuthList=new ArrayList<>();
/*		if (StringUtils.concat(systemType,"AMS")) {
			nodeAuthList = AppCommonUtil.initNodeAuthListAms();//存放节点操作权限
		}else if (StringUtils.concat(systemType,"CAS")){
			nodeAuthList=AppCommonUtil.initNodeAuthListCas();
		}*/
		//根据系统获取节点操作权限
		nodeAuthList = cacheContext.getAuthBySystemType(systemType);
		for(String auth : resourceCodes){
			if(nodeAuthList.contains(auth)){
				tempNodeAuths.add(EcmsAuthority.valueOf(auth).lab);
			}
		}
		//如果用户所属组不为空
		if(page.getQuery().get("myRole") != null && StringUtils.isNotBlank(page.getQuery().get("myRole").toString())){
			int roleId = Integer.valueOf(page.getQuery().get("myRole").toString());//获取角色ID
			List<String> list = getTaskDefKeys(roleId);//存放任务名条件
			if (CollectionUtils.sizeGtZero(list)) {
				page.getQuery().put("taskDefKeys", list.toArray(new String[0]));
			}
		}
		splitTaskDefKeys(page, tempNodeAuths);

		//如果选择了受理网点
	/*	if(page.getQuery().get("owningBranch") != null && StringUtils.isNotBlank(page.getQuery().get("owningBranch").toString())){
			String owningBranch = page.getQuery().get("owningBranch").toString();//获取选择的受理网点
			applyProcessUtils.setBranchToPage(page, owningBranch);
		}else{
			page.getQuery().remove("owningBranch");
		}*/
		//如果没选择了受理网点
		if(page.getQuery().get("owningBranch") != null && StringUtils.isNotBlank(page.getQuery().get("owningBranch").toString())){
			String owningBranch = page.getQuery().get("owningBranch").toString();//获取选择的受理网点
			applyProcessUtils.setBranchToPage(page, owningBranch);
		}else{
			String owningBranch =OrganizationContextHolder.getBranchCode();
			if(StringUtils.isNotEmpty(owningBranch)){
				applyProcessUtils.setBranchToPage(page, owningBranch);
			}
		}
		if(page.getQuery()!=null&&page.getQuery().size()>0){
			//给开始日期和截至日期附加时间部分
			if(StringUtils.isNotEmpty((String)page.getQuery().get("startDate"))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse((String) page.getQuery().get("startDate"));
					page.getQuery().put("startDate", DateUtils.getDateStart(date));
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常", e);
				}
			}
			if(StringUtils.isNotEmpty((String)page.getQuery().get("endDate"))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse((String) page.getQuery().get("endDate"));
					page.getQuery().put("endDate",DateUtils.getDateEnd(date));
				} catch (ParseException e) {
					logger.error("案件转分配时间格式转换发生异常", e);
				}
			}
		}
		page = tmTaskListDao.queryMyTaskPage(page);
		displayLastNodeInList(page); //在列表中显示最后一个退回节点
		return page;
	}

	/**
	 * 根据操作员ID来获取已完成任务
	 * @param page
	 * @return
	 */
	@Override
	public Page<ApplyTaskListDto> getCompletedTaskList(Page<ApplyTaskListDto> page){
		/************************************加上页面查询的条件**************************************/
		if(StringUtils.isEmpty(page.getQuery().get("operatorId"))) {
			page.getQuery().put("operatorId", OrganizationContextHolder.getUserNo());
		}
		//如果没选择了受理网点
		if(page.getQuery().get("owningBranch") != null && StringUtils.isNotBlank(page.getQuery().get("owningBranch").toString())){
			String owningBranch = page.getQuery().get("owningBranch").toString();//获取选择的受理网点
			applyProcessUtils.setBranchToPage(page, owningBranch);
		}else{
			String owningBranch =OrganizationContextHolder.getBranchCode();
			if(StringUtils.isNotEmpty(owningBranch)){
				applyProcessUtils.setBranchToPage(page, owningBranch);
			}
		}
		if(page.getQuery()!=null&&page.getQuery().size()>0){
			//给开始日期和截至日期附加时间部分
			if(StringUtils.isNotEmpty((String)page.getQuery().get("startDate"))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse((String) page.getQuery().get("startDate"));
					page.getQuery().put("startDate", DateUtils.getDateStart(date));
				} catch (ParseException e) {
					logger.error("案件转分配查询时间格式转换发生异常", e);
				}
			}
			if(StringUtils.isNotEmpty((String)page.getQuery().get("endDate"))){
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				try {
					Date date = sdf.parse((String) page.getQuery().get("endDate"));
					page.getQuery().put("endDate",DateUtils.getDateEnd(date));
				} catch (ParseException e) {
					logger.error("案件转分配时间格式转换发生异常", e);
				}
			}
		}
		page = tmTaskListDao.queryCompleteTaskPage(page);
		return page;
	}

	private void splitTaskDefKeys(Page<ApplyTaskListDto> page,
			List<String> tempNodeAuths) {
		StringBuffer sb = new StringBuffer();
		if(CollectionUtils.sizeGtZero(tempNodeAuths)){
			//获取操作人员最大可见额度和最小可见额度并传入查询条件
			String userNo = OrganizationContextHolder.getUserNo();
			boolean isNotNull = false;
			for (int i = 0; i < tempNodeAuths.size(); i++) {
 				String taskKey = tempNodeAuths.get(i);
				if(StringUtils.isNotEmpty(taskKey)){
					TmAppAuditQuota faq = cacheContext.getTmAppAuditQuotaForCache(userNo, taskKey);
					if(isNotNull){
						sb.append(" or ");
					}
					sb.append(" ( ");
					sb.append(" t.TASK_DEF_KEY_='"+taskKey+"'");
					if(faq!=null){
						BigDecimal visibleMinimum = faq.getVisibleMinimum();
						BigDecimal visibleMaximum = faq.getVisibleMaximum();
						if(visibleMinimum!=null || visibleMaximum!=null){

							sb.append(" and ( ( ");

							if(EcmsAuthority.CAS_APPLY_FINALAUDIT.lab.equals(taskKey)){
								if(visibleMinimum!=null || visibleMaximum!=null){
									sb.append(" m.CHK_LMT is not null ");
								}
								if(visibleMinimum!=null && visibleMinimum.compareTo(new BigDecimal(0))>0){
									sb.append(" and m.CHK_LMT >= "+visibleMinimum);
								}
								if(visibleMaximum!=null && visibleMaximum.compareTo(new BigDecimal(0))>0){
									sb.append(" and m.CHK_LMT <= "+visibleMaximum);
								}
								sb.append(" ) ");
							}
							if(visibleMinimum!=null || visibleMaximum!=null){
								if(EcmsAuthority.CAS_APPLY_FINALAUDIT.lab.equals(taskKey)){
									sb.append(" or (");
									sb.append(" m.CHK_LMT is null and m.SUG_LMT is not null ");
								}else{
									sb.append(" m.SUG_LMT is not null ");
								}
							}
							if(visibleMinimum!=null && visibleMinimum.compareTo(new BigDecimal(0))>0){
								sb.append(" and m.SUG_LMT >= "+visibleMinimum);
							}
							if(visibleMaximum!=null && visibleMaximum.compareTo(new BigDecimal(0))>0){
								sb.append(" and m.SUG_LMT <= "+visibleMaximum);
							}
							sb.append(" ) ");
						}

						if(visibleMinimum!=null || visibleMaximum!=null){
							sb.append(" or (");
							if(visibleMinimum!=null || visibleMaximum!=null){
								if(EcmsAuthority.CAS_APPLY_FINALAUDIT.lab.equals(taskKey)){
									sb.append(" m.CHK_LMT is null and m.SUG_LMT is null ");
								}else{
									sb.append(" m.SUG_LMT is null ");
								}
								sb.append(" and m.APP_LMT is not null ");
							}
							if(visibleMinimum!=null && visibleMinimum.compareTo(new BigDecimal(0))>0){
								sb.append(" and m.APP_LMT >= "+visibleMinimum);
							}
							if(visibleMaximum!=null && visibleMaximum.compareTo(new BigDecimal(0))>0){
								sb.append(" and m.APP_LMT <= "+visibleMaximum);
							}
							sb.append(" ) ");
						}

						if(visibleMinimum!=null || visibleMaximum!=null){
							sb.append(" or (");
							if(visibleMinimum!=null || visibleMaximum!=null){
								if(EcmsAuthority.CAS_APPLY_FINALAUDIT.lab.equals(taskKey)){
									sb.append(" m.CHK_LMT is null and m.SUG_LMT is null and m.APP_LMT is null ");
								}else{
									sb.append(" m.APP_LMT is null ");
								}
							}

							sb.append(" )) ");
						}

					}
					sb.append(" ) ");
					isNotNull = true;
				}
			}
			page.getQuery().put("taskDefKeys", sb);
		}
	}

	/**
	 * 根据操作员ID来获取已完成任务
	 */
	public Page<ApplyTaskListDto> getCompletedTaskList(Page<ApplyTaskListDto> page,String systemType){
		return null;
	}
	/* 
	 * 获取可分配人员名单
	 */
	@Override
	@Transactional
	public Page<TmAclUser> getUserPage(Page<TmAclUser> page) {
		tmAclUserDao.queryPage(page);
		return null;
	}

	
	/**
	 * 通过roleId获取到可以查看的任务名(TASK_DEF_KEY_)
	 * @param roleId
	 * @return
	 */
	public List<String> getTaskDefKeys(int roleId){
		List<TmAclResource> resourceList = new ArrayList<TmAclResource>();
		resourceList = accessResourceService.getResourceCodes(roleId);//获取到该角色的权限
		List<String>  nodeAuthList=new ArrayList<>();
		//存放节点操作权限
		nodeAuthList = cacheContext.getAuthBySystemType("initNodeAuthList");
/*
		List<String> nodeAuthList = AppCommonUtil.initNodeAuthList();//存放节点操作权限
*/
		List<String> taskDefKeyList = new ArrayList<String>();//存放TASK_DEF_KEY_
		for(TmAclResource enty : resourceList){
			if(nodeAuthList.contains(enty.getResourceCode())){//说明在这个任务名list里面
				taskDefKeyList.add(EcmsAuthority.valueOf(enty.getResourceCode()).lab);//和task表中的TASK_DEF_KEY_一样
			}
		}
		
		return taskDefKeyList;
	}

	/**
	 * 获取超时天数
	 * @return
	 */
	@Override
	@Transactional
	public int getOverDays() {
		int overDay = 5;//默认5天
		TmDitDic tmDitDic = cacheContext.getApplyOnlineOnOff(AppConstant.TASK_QUEUE);
		if(tmDitDic!=null && StringUtils.isNotEmpty(tmDitDic.getRemark())) {
			try {
				overDay = Integer.valueOf(tmDitDic.getRemark());
			} catch (Exception e) {
				logger.warn("读取系统设置进件超时时间异常",e);
			}
		}
		return overDay;
	}
	/**
	 * 判断是否有案件分配权限
	 * @return
	 */
	@Override
	@Transactional
	public boolean hasAssignTaskAuth() {
		List<String> resourceCodes = commonService.getCurrUserResourceCodeList();//先获取到所有的权限
		if(CollectionUtils.isNotEmpty(resourceCodes) && resourceCodes.contains(EcmsAuthority.ECMS_ASSIGN_TASK.name())){
			return true;
		}
		return false;
	}
	
	/**
	 * 在列表中显示最后一个退回节点 
	 * @return
	 */
	@Override
	@Transactional
	public void displayLastNodeInList(Page<ApplyTaskListDto> page) {
		Map<Object, Object> backMarkMap = cacheContext.getAclDictByType(AppConstant.BACK_MARK);
		for (ApplyTaskListDto applyTaskListDto : page.getRows()) {
			StringBuffer sb = new StringBuffer();
			if ("Y".equals(applyTaskListDto.getApproveQuickFlag())) {
				sb = sb.append("快速 |");
			}
			if ("Y".equals(applyTaskListDto.getIsPriority())) {
				sb = sb.append("优先 |");
			}
			if("Y".equals(applyTaskListDto.getRetrialFlag())){
				sb = sb.append("重审件 |");
			}
			String backMark = applyTaskListDto.getBackMark();
			if (StringUtils.isNotEmpty(backMark)) {
				String[] splitBackMark = backMark.split("\\|");
				sb = sb.append(backMarkMap.get(splitBackMark[splitBackMark.length - 1]) + "|");
			}
			if (sb.length() > 1) {
				applyTaskListDto.setApproveQuickFlag(sb.toString().substring(0, sb.length()-1));
			} else {
				applyTaskListDto.setApproveQuickFlag("");
			}
		}
	}
	
	@Override 
	@Transactional
	public void cancelTask(String taskId, String appNo) {
		try {
			Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
			if(task==null){
				throw new ProcessException("未获取到申请件["+appNo+"]工作流任务["+taskId+"]信息,审批流程或已被更新，请刷新页面重试!");
			}
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
			if (task != null && task.getOwner() != null) {
				taskService.unclaim(taskId);// 任务分配者
				taskService.setOwner(taskId, "");
			}
			//设置任务转移记录
			TmTaskTransfer taskTransferRecord = new TmTaskTransfer();
			taskTransferRecord.setAppNo(appNo);
			taskTransferRecord.setTransferType(TaskTransferType.SELF_CANCEL.state);//本人取消
			taskTransferRecord.setTaskKey(task.getTaskDefinitionKey());
			taskTransferRecord.setTaskName(task.getName());
			//优化，将TaskId保存到Tm_App_Main
			TmAppMain tmAppMain = tmAppMainDao.getTmAppMainByAppNo(appNo);
			if(tmAppMain!=null && StringUtils.isNotEmpty(appNo)){
				if(task!=null&&StringUtils.isNotEmpty(task.getId())){
					tmAppMain.setTaskId(task.getId());
					tmAppMain.setTaskOwner(null);
					tmAppMainDao.updateTmAppMain(tmAppMain);
				}
			}
			taskTransferRecord.setRtfState(tmAppMain == null ? null : tmAppMain.getRtfState());
			taskTransferRecord.setOwner(null);
			taskTransferRecord.setAssigner(null);
			taskTransferRecord.setClaimTime(new Date());
			taskTransferRecord.setStatus(TaskTransferStatus.UNASSIGNED.state);//未分配
			applyInputService.saveOrUpdateTmTaskTransfer(taskTransferRecord, AppConstant.Cancel);
		} catch (ActivitiException ae) {
			logger.error("cancelTask任务异常:" + taskId, ae);
			throw new ProcessException("任务不存在或已取消");
		}
	}

	/**
	 * 根据姓名、证件类型、证件号码判断是否是贷中状态(用于异步查询人行报告)
	 * @param applyTaskListDto
	 * @return
	 */
	@Override
	@Transactional
	public List<ApplyTaskListDto> getApplyTaskListDtoList(ApplyTaskListDto applyTaskListDto) {
		if(applyTaskListDto == null){
			logger.error("查询人行报告没有输入查询条件，请稍后再试!");
			return null;
		}
		
		return tmTaskListDao.queryApplyTaskListDtoList(applyTaskListDto);
	}
}
