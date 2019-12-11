package com.jjb.ecms.biz.service.query.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.access.service.AccessUserService;
import com.jjb.acl.biz.dao.TmAclUserDao;
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.TaskTransferStatus;
import com.jjb.acl.facility.enums.bus.TaskTransferType;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimCardInfoDao;
import com.jjb.ecms.biz.dao.apply.TmTaskTransferDao;
import com.jjb.ecms.biz.dao.query.TmTaskTransferListDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.query.ApplyProcessUtils;
import com.jjb.ecms.biz.service.query.ApplyTaskListService;
import com.jjb.ecms.biz.service.query.ApplyTaskTransferListService;
import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description 已分配任务列表service实现类
 * @author hn
 * @date 2016年9月2日10:55:19 
 */
@Service("ApplyTaskTransferListService")
public class ApplyTaskTransferListServiceImpl implements ApplyTaskTransferListService{

	@Autowired
	private TmTaskTransferListDao tmTaskTransferListDao;
	
	@Autowired
	private TmTaskTransferDao tmTaskTransferDao;
	
	@Autowired
	private TmAclUserDao tmAclUserDao;
	
	@Autowired
	private TmAppMainDao tmAppMainDao;
	
	@Autowired
	private AccessUserService accessUserService;
//	@Autowired
//	private ApplyInputService applyInputService;
	@Autowired
	private TmAppPrimCardInfoDao tmAppPrimCardInfoDao;
	@Autowired
	private TaskService taskService;
//	@Autowired
//	private RuntimeService runtimeService;
//	@Autowired
//	private CacheContext cacheContext;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ApplyTaskListService applyTaskListService;
	@Autowired
	private ApplyProcessUtils applyProcessUtils;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private CacheContext cacheContext;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/*
	 * 获取未分配任务 
	 */
	@Override
	@Transactional
	public Page<ApplyTaskListDto> getTransferTaskList(Page<ApplyTaskListDto> page,String systemType) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		page.getQuery().put("_SORT_LIST", null);
		
		//1.加上当前用户的节点操作权限（只过滤出：录入修改、录入复核、人工核查、初审、补件、终审权限）
		List<String> resourceCodes = commonService.getCurrUserResourceCodeList();//先获取到所有的权限
		List<String> tempNodeAuths = new ArrayList<String>();//存放拥有的人工节点权限
		List<String> nodeAuthList = new ArrayList<>();//存放节点操作权限
		//根据系统获取节点操作权限
		nodeAuthList = cacheContext.getAuthBySystemType(systemType);
		for(String auth : resourceCodes){
			if(nodeAuthList.contains(auth)){
				tempNodeAuths.add(EcmsAuthority.valueOf(auth).lab);
			}
		}
		page.getQuery().put("taskDefKeys", tempNodeAuths.toArray(new String[0]));//节点操作权限
		
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
		
		page = tmTaskTransferListDao.queryTaskTransferPage(page);
		applyTaskListService.displayLastNodeInList(page);
		return page;
	}
	
	/*
	 * 获取可分配人员名单
	 */
	@Override
	@Transactional
	public Page<TmAclUser> getUserPage(Page<TmAclUser> page, String selectTask) {
		String[] taskAuth = selectTask.split(",");// 将页面传来的taskId：taskKey 一对对分离（52617:applyinfo-creditReport,29608:input-modify,）
		String authString = "";
		List<String> taskIdList = new ArrayList<String>();// 存放终审件的taskId
		authString = setTaskIdToListIfFinalCheck(taskAuth, authString, taskIdList);
		String authStr = swichToAuthorityName(authString);

		logger.info("所需要的权限：" + authString);
		List<TmAclUser> tmAclUsers = accessUserService.getUserMenus(authStr.split(","));

		List<TmAclUser> finalAclUsers = new ArrayList<TmAclUser>();// 定义最终的输出user

		if (page.getQuery() != null) {// 判断是不是有查询条件（userNo、uesrName）
			List<TmAclUser> tempAclUsers = new ArrayList<TmAclUser>();
			String userNo = page.getQuery().get("userNo") == null ? null : page.getQuery().get("userNo").toString();
			String userName = page.getQuery().get("userName") == null ? null : page.getQuery().get("userName").toString();
			TmAclUser tmAclUser = new TmAclUser();
			tmAclUser.setUserNo(userNo);
			tmAclUser.setUserName(userName);
			tempAclUsers = tmAclUserDao.queryForList(tmAclUser);// 先查询出所有的用户
			addToFinalAclUsersIfInTmAclUsers(tmAclUsers, finalAclUsers, tempAclUsers);// 如果已经在tmAclUsers中，则添加到finalAclUsers中
		}
		if (CollectionUtils.isNotEmpty(finalAclUsers)) {
			page.setRows(finalAclUsers);
		} else {
			page.setRows(tmAclUsers);
		}
		return page;
	}

	/*
	 * 如果已经在tmAclUsers中，则添加到finalAclUsers中
	 */
	private void addToFinalAclUsersIfInTmAclUsers(List<TmAclUser> tmAclUsers,
			List<TmAclUser> finalAclUsers, List<TmAclUser> tempAclUsers) {
		if (CollectionUtils.isNotEmpty(tempAclUsers)) {
			for (TmAclUser enty : tempAclUsers) {
				for (TmAclUser user : tmAclUsers) {
					if (StringUtils.isNotBlank(user.getUserNo())&& user.getUserNo().equals(enty.getUserNo())) {// 如果已经在这个过滤列表中
						finalAclUsers.add(enty);
						break;
					}
				}
			}
		}
	}
	
	/*
	 * 如果是终审，获取taskId并添加到taskIdList
	 */
	private String setTaskIdToListIfFinalCheck(String[] taskAuth,
			String authString, List<String> taskIdList) {
		String taskKey = "";
		for(String enty : taskAuth){
			if(StringUtils.isNotEmpty(enty) && enty.contains(":")){
				String[] entys = enty.split(":");
				taskKey = entys[1];// taskId：taskKey
				if(EcmsAuthority.CAS_APPLY_FINALAUDIT.lab.equals(taskKey)){//如果是终审
					if(StringUtils.isNotBlank(entys[0])){//取出taskId
						taskIdList.add(entys[0]);//如果不为空，则全部放在集合中
					}
				}
			}
			authString += taskKey + ",";
		}
		
		return authString;
	}

	/*
	 * 把authString转换成EcmsAuthority name并拼接
	 */
	private String swichToAuthorityName(String authString) {
		String authStr = "";
		for (EcmsAuthority EcmsAuthority : EcmsAuthority.values()) {
			if (authString.contains(EcmsAuthority.lab)) {
				authStr += EcmsAuthority.name() + ",";
			}
		}
		return authStr;
	}

	/**
	 * 任务转分配
	 * @param userNo
	 * @param taskIdAndAppNo taskid：appNo
	 */
	@Override
	@Transactional
	public void transferTask(String userNo, String taskIdAndAppNo) {
		
		String[] taskAppNo = taskIdAndAppNo.split(",");//将taskId:appNo取出来
		for(String enty : taskAppNo){
			String taskId = enty.split(":")[0];
			String appNo = enty.split(":")[1];
			if(StringUtils.isNotBlank(taskId) && StringUtils.isNotBlank(appNo)){
				//!设置任务转移记录
				//先查询出任务
				Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
				TmAppMain tmAppMain = tmAppMainDao.getTmAppMainByAppNo(appNo);

	            //!设置任务转移记录
	        	TmTaskTransfer taskTran = new TmTaskTransfer();
	        	taskTran.setAppNo(appNo);
	        	taskTran.setOrg(tmAppMain.getOrg());
	        	taskTran.setAssigner(OrganizationContextHolder.getUserNo());
	        	taskTran.setOwner(userNo);
	        	taskTran.setTransferType(TaskTransferType.TRANSFER.state);
	        	taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
	            activitiService.assingneeTask(task, tmAppMain, taskTran,null);
				
			}
		}
		
	}
	@Override
	public void spreaderSave(String spreaderNum, String taskIdAndAppNo) {
		String[] appNos = taskIdAndAppNo.split(",");//将taskId:appNo取出来
		TmAppPrimCardInfo tmAppPrimCardInfo=new TmAppPrimCardInfo();
		for(String enty : appNos){
			String appNo = enty;
			String[] spreader=spreaderNum.split(",");
			for (int i =0;i<spreader.length;i++){
				tmAppPrimCardInfo.setSpreaderNo(spreader[0]);
				tmAppPrimCardInfo.setSpreaderName(spreader[1]);
				tmAppPrimCardInfo.setSpreaderTelephone(spreader[2]);
				tmAppPrimCardInfo.setSpreaderBranchThree(spreader[3]);
			}
			if(StringUtils.isNotBlank(appNo)){
				//先查询出任务
				tmAppPrimCardInfo.setAppNo(appNo);
				tmAppPrimCardInfoDao.saveTmAppPrimCardInfo(tmAppPrimCardInfo);
			}
		}

	}
	@Override
	@Transactional
	public List<TmTaskTransfer> getTransferTaskListByAppNo(String appNo) {
		List<TmTaskTransfer> taskTransferRecordList = tmTaskTransferDao.getTaskTransferRecordList(appNo);
		if (CollectionUtils.isNotEmpty(taskTransferRecordList)) {
			for(TmTaskTransfer record : taskTransferRecordList) {
				for(TaskTransferType value : TaskTransferType.values()) {
					if (value.state.equals(record.getTransferType())) {
						record.setTransferType(value.lab);
					}
				}
				for(TaskTransferStatus status : TaskTransferStatus.values()) {
					if (status.state.equals(record.getStatus())) {
						record.setStatus(status.lab);
					}
				}
			}
		}
		return taskTransferRecordList;
	}
}
