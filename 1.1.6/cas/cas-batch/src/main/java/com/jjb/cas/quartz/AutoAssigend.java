package com.jjb.cas.quartz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.access.service.AccessUserService;
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.bus.TaskTransferStatus;
import com.jjb.acl.facility.enums.bus.TaskTransferType;
import com.jjb.acl.infrastructure.TmAclBranch;
import com.jjb.acl.infrastructure.TmAclUser;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppUserRelationDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.service.commonDialog.ApplyHistoryService;
import com.jjb.ecms.biz.service.manage.ApplyTaskDetailsService;
import com.jjb.ecms.biz.service.param.SysParamService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.dto.ApplyTaskDetailsDto;
import com.jjb.ecms.facility.dto.SimpleUser;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppAuditQuota;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppUserRelation;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;


/**
 * @author JYData-R&D-HN
 * @version V1.0
 * @Description: 案件自动分配
 * @date 2016年9月23日 下午3:56:12
 */
@Component
public class AutoAssigend implements Serializable {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final long serialVersionUID = 1L;
    private static final String DIT_DIC_AUTO_CONFIG_STR = "autoTransfer";
    private static final String DIT_DIC_FILTER_USER_STR = "autoTransferSDuser";

    @Autowired
    private TaskService taskService;
    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private ApplyTaskDetailsService applyTaskDetailsService;
    @Autowired
    private SysParamService sysParamService;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private AccessUserService accessUserService;
    @Autowired
    private AppCommonUtil appCommonUtil;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private TmAppUserRelationDao tmAppUserRelationDao;
    @Autowired
    private ApplyHistoryService applyHistoryService;
    @Autowired
    private ApplyInputService applyInputService;


    private Map<String, DicBean> isOpenAutoAssigneeMap = new HashMap<String, DicBean>();
    //	private static Map<String,Map<String, BMPUser>> activitiUserMap = new HashMap<String, Map<String, BMPUser>>();
    private Map<String, Map<String, List<SimpleUser>>> aclUserMap = new HashMap<String, Map<String, List<SimpleUser>>>();
    //	private Map<String, TmAclBranch> branchMap = new HashMap<String, TmAclBranch>();
    private Map<String, List<String>> filterUser = new HashMap<String, List<String>>();

    /**
     * 待办任务自动分配
     */
    public void autoAssignee() {
        appCommonUtil.setOrg(OrganizationContextHolder.getOrg());//设置系统机构号
        //获取自动分配的开关
        List<TmDitDic> tmDitDicList = getAllAutoAssignConfi();
        if (tmDitDicList != null && tmDitDicList.size() > 0) {
//			branchMap = new HashMap<String, TmAclBranch>();
            //循环遍历每一条配置,需找特定任务的未分配任务,再循环便利每一条未分配任务去分配
            for (int i = 0; i < tmDitDicList.size(); i++) {
                TmDitDic t1 = tmDitDicList.get(i);
                filterUser = new HashMap<String, List<String>>();
                aclUserMap = new HashMap<String, Map<String, List<SimpleUser>>>();
                if (t1.getTabName() != null && !t1.getTabName().equals("")
                        && t1.getRemark() != null && StringUtils.equals(t1.getRemark(), "Y")) {
                    OrganizationContextHolder.setOrg(t1.getOrg());
                    //获取特定任务的所有未分配任务列表(按照设置的优先派件规则来,如果没有则按默认来)
                    //这边的是按进件属性排序查询；要改动
                    ApplyTaskDetailsDto applyTaskDetailsDto = new ApplyTaskDetailsDto();
                    applyTaskDetailsDto.setTaskDefKey(t1.getTabName());
                    List<ApplyTaskDetailsDto> unTaskList = new ArrayList<ApplyTaskDetailsDto>();
                    String pri = t1.getItemName();
                    StringBuffer sb = new StringBuffer();
                    boolean isUseSort = false;
                    if (StringUtils.isNotEmpty(pri)) {
//						( CASE WHEN M.APP_PROPERTY like '%V%' THEN 1 WHEN M.APP_PROPERTY like '%P%' THEN 2 END) asc ,
                        String[] priortys = pri.split("\\|");
                        //如果非初审用下面的排序查询
                        if (!t1.getTabName().equals("applyinfo-check")){
                            sb.append(" (CASE ");
                            for (int j = 0; j < priortys.length; j++) {
                                String str = priortys[j];
                                //str : M.APP_PROPERTY like '%V%'
                                if (StringUtils.isNotEmpty(str)) {
                                    isUseSort = true;
                                    sb.append(" WHEN  M.APP_PROPERTY like ");
                                    sb.append("'" + "%" + str + "%" + "'");
                                    sb.append(" THEN ");
                                    sb.append(j + 1);
                                }
                            }
                            sb.append(" END) asc , ");
                        }
                    }
                    //如果isUseSort为true说明不是初审，用非初审的查询排序
                    if (isUseSort) {
                        applyTaskDetailsDto.setPriorit(sb.toString());
                        unTaskList = applyTaskDetailsService.getTaskUndistributedList(applyTaskDetailsDto);
                    }else{
                        unTaskList = applyTaskDetailsService.getTaskUndistributedLists(applyTaskDetailsDto);
                    }
                    if (unTaskList != null) {
                        logger.info("节点[" + t1.getItemName() + "]已开启自动分配[" + t1.getRemark() + "],平均数量[" + unTaskList.size() + "]");
                        //遍历每一条的未分配任务并进行分配
                        for (int j = 0; j < unTaskList.size(); j++) {
                            ApplyTaskDetailsDto unTask = unTaskList.get(j);
                            if (unTask == null) {
                                continue;
                            }
                            OrganizationContextHolder.setOrg(unTask.getOrg());
                            OrganizationContextHolder.setUserNo("sysauto");
                            try {
                                boolean rs = assignee(unTask.getTaskId(), unTask.getTaskDefKey(), unTask.getAppNo(), true);
                                if(rs) {
                                    break;
                                }
                            } catch (Exception e) {
                                logger.error("自动分配失败,AppNo[" + unTask.toString() + "]", e);
                            }
                        }
                    }
                }
            }
            //置空值，待下次自动处理时在查
            isOpenAutoAssigneeMap = new HashMap<String, DicBean>();
            aclUserMap = new HashMap<String, Map<String, List<SimpleUser>>>();
//			branchMap = new HashMap<String, TmAclBranch>();
            filterUser = new HashMap<String, List<String>>();
        }
    }

    /**
     * 自动分配员工
     * //2016-07-28,hn
     * //1-1.通过操作员提交任务发起的自动分配动作保留：录入复核退回、初审退回、终审退回、重审等共计4个状态操作。
     * //1-2.录入复核退回、初审退回、终审退回、重审等分配到原经办人手中（不考虑手头案件数量和是否自动分配，后续若新增退回操作，都是类似处理）
     * //1-3.如果原经办人权限或者发生岗位变更，则该任务变成待分配状态。
     * //1-4.如果是补件->初审，则分配至初审经办人队列（不考虑手头案件数量和是否自动分配）
     * <p>
     * //2.通过系统quartz定时任务发起的分配如下：
     * //2-1.查找配置，判断节点是否需要自动分配
     * //2-2找到拥有操作此流程的权限的员工，当员工为空，跳过分配步骤
     * //2-3过滤分行，过滤后员工为空，跳过分配步骤
     * //2-4如果是终审，根据额度配置过滤用户
     * //2-5统计查询当前节点的任务数
     * //2-6查找用户，不在统计查询列表中的用户优先分配
     * //2-7若6中未找到用户，此时找统计查询的第一个用户
     * //2-8如果是补件操作，则只能分配到录入时选择的受理网点中的补件操作员手中
     *
     * @param
     */
    @Transactional
    public boolean assignee(String taskId, String taskKey, String appNo, boolean isQuartz) {
        long start = System.currentTimeMillis();
        String userId = null;
        String opUser = OrganizationContextHolder.getUserNo();
        if (StringUtils.isEmpty(appNo)) {
            logger.error("无法正常分配，申请件编号[" + appNo + "] 为空");
            return true;
        }
        // 获取自动分配配置信息
        DicBean dicBean = getAutoAssignConfi(taskKey);
        //如果是定时任务自动发起分配
        if (isQuartz) {
            // 判断是否自动分案
            if (dicBean == null || !dicBean.isAssign()) {
                return false;
            }
        }
        logger.info("开始分配...taskId[" + taskId + "],taskKey[" + taskKey + "]" + LogPrintUtils.printAppNoLog(appNo, start, null));
        //先查询出任务
        List<Task> taskList = taskService.createTaskQuery().taskId(taskId).list();
        Task task = null;
        if (CollectionUtils.sizeGtZero(taskList)) {
            task = taskList.get(0);
        }
        TmAppMain appMain = applyQueryService.getTmAppMainByAppNo(appNo);
        String operateUser= appMain.getTaskLastOpUser();
        if (appMain == null || appMain.getOwningBranch() == null) {
            logger.info("本次任务自动分配结束...没有找到该申请件, taskId[" + taskId + "],taskKey[" + taskKey
                    + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null) + "开始自动分配下一个任务");
            return true;
        }
        // 获取拥有操作此流程的员工,此流程未分配员工，忽略分配步骤
        Map<String, List<SimpleUser>> usersMaps = filterEmployees(taskKey, appMain.getProductCd());
        if (usersMaps == null || usersMaps.size() == 0) {
            logger.info("本次任务自动分配结束...没有获取到操作此流程的员工, taskId[" + taskId + "],taskKey[" + taskKey
                    + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null) + "开始自动分配下一个任务");
            if(StringUtils.equals(taskKey, "applyinfo-finalaudit")) {
                return false;
            }else {
                return true;
            }
        }
        //获取系统所有分支行
        List<String> branchIds = setOwningBranchVariable(appMain.getOwningBranch());
        // 筛选分行员工
        usersMaps = filterBranchUser(usersMaps, branchIds);
        if (usersMaps == null || usersMaps.size() == 0) {
            logger.info("本次任务自动分配结束...没有找到可分配用户列表, taskId[" + taskId + "],taskKey[" + taskKey
                    + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null) + "开始自动分配下一个任务");
            if(StringUtils.equals(taskKey, "applyinfo-finalaudit")) {
                return false;
            }else {
                return true;
            }
        }
        List<SimpleUser> userList = new ArrayList<SimpleUser>();
        if (usersMaps != null && usersMaps.size() > 0) {
            for (List<SimpleUser> list : usersMaps.values()) {
                if (CollectionUtils.isNotEmpty(list)) {
                    userList.addAll(list);
                }
            }
        }
        if (CollectionUtils.isEmpty(userList)) {
            logger.info("自动分配结束...没有找到可分配用户列表, taskId[" + taskId + "],taskKey[" + taskKey
                    + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null));
            return false;
        }
        // 补件
        if (taskKey.equals(EcmsAuthority.CAS_APPLY_PATCHBOLT.lab)) {
            userList = filterPathBoltUser(usersMaps, appMain);
        }
        // 终审
        if (taskKey.equals(EcmsAuthority.CAS_APPLY_FINALAUDIT.lab)) {
            userList = filterFinalFlowUser(userList, appMain, taskKey);
        }
        if (CollectionUtils.isEmpty(userList)) {
            logger.info("自动分配结束...没有找到可分配用户列表, taskId[" + taskId + "],taskKey[" + taskKey
                    + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null));
            return false;
        }
        String rtfState = appMain.getRtfState();
        String source =AppConstant.Claim;
        //如果是录入复核退回、初审退回、终审退回、重审到初审、补件完成到初审
        //如果是非定时任务发起的达到初审的，那么就是人工操作进的，如果是人工操作近的话了，据需要判断是否是补件完成和人工核查提交。
        if (rtfState.equals(RtfState.B15.toString())		//录入复核退回到录入修改
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
        ) {
/*			String hisAppNo = applyQueryService.getTmAppAuditByAppNo(appNo).getAppNoHis();
            String historyOpUser ="";
			if (StringUtils.isNotEmpty(hisAppNo)){
				 historyOpUser = getHisOpUser(appNo, hisAppNo, appMain);*/
            TmAppAudit histAudit = applyQueryService.getTmAppAuditByAppNo(appNo);
            String hisAppNo = null;
            if(histAudit!=null) {
                hisAppNo = histAudit.getAppNoHis();
            }
//            String historyOpUser = getHisOpUser(appNo, hisAppNo, appMain);
            String historyOpUser = commonService.getHisOpUser(appNo, hisAppNo, appMain.getRtfState());
            //正常流程往下流转时------
            logger.info("回原经办人分配...申请件编号[" + appNo + "],opUser[" + opUser + "],taskId[" + taskId + "],taskKey[" + taskKey
                    + "],rtfState[" + appMain.getRtfState() + "],hisAppNo[" + hisAppNo + "]");
            if (StringUtils.isNotBlank(historyOpUser) && isInUserList(userList, historyOpUser)) {
                //正常流程往下流转时------
                TmTaskTransfer taskTran = new TmTaskTransfer();
                taskTran.setAppNo(appNo);
                taskTran.setOrg(appMain.getOrg());
                taskTran.setAssigner(AppConstant.SYS_AUTO);
                taskTran.setOwner(historyOpUser);
                taskTran.setTransferType(TaskTransferType.QUARTZ_ASSIGNE.state);
                taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
                activitiService.assingneeTask(task, appMain, taskTran,source);
                logger.info("回原经办人[" + historyOpUser + "]分配成功...申请件编号[" + appNo + "],opUser[" + opUser + "],taskId[" + taskId
                        + "],taskKey[" + taskKey + "],rtfState[" + appMain.getRtfState() + "]");
            } else {
                logger.info("回原经办人[" + historyOpUser + "]分配失败..(原经办人不在指定分配用户中).申请件编号[" + appNo + "],opUser[" + opUser + "],taskId[" + taskId
                        + "],taskKey[" + taskKey + "],rtfState[" + appMain.getRtfState() + "]");
            }
            return false;
        } else if (!isQuartz) {
            return false;
            //初审件到对应终审下
        }else if(  rtfState.equals(RtfState.F10.toString()) //初审完成
                || rtfState.equals(RtfState.F20.toString()) //电话调查完成
                || rtfState.equals(RtfState.F21.toString()) //免电话调查
                || rtfState.equals(RtfState.F06.toString()) //初审调查拒绝
                || rtfState.equals(RtfState.F16.toString())){ //电话调查拒绝
            TmAppUserRelation tmAppUserRelation=new TmAppUserRelation();
            if(StringUtils.isNotBlank(operateUser)) {
                //tmAppUserRelation = tmAppMsgSendService.getTmAppUserRelationByUserNo(operateUser);
                tmAppUserRelation=cacheContext.getTmAppUserRelationByUserNo(operateUser);
            }
            String userNo=null;
            if(StringUtils.isNotEmpty(tmAppUserRelation)) {
                userNo = tmAppUserRelation.getHighterUserNo();
            }
            TmAppUserRelation tmAppUserRelations = new TmAppUserRelation();
            if (StringUtils.isNotBlank(userNo)) {
                //tmAppUserRelations = tmAppMsgSendService.getTmAppUserRelationByUserNo(userNo);
                tmAppUserRelations = cacheContext.getTmAppUserRelationByUserNo(userNo);
            }
            //判断是否初审人有对应终审人并且该终审人是否在职状态
            if(StringUtils.isNotBlank(rtfState)&&StringUtils.isNotBlank(userNo) && StringUtils.isNotEmpty(tmAppUserRelations) && StringUtils.equals(tmAppUserRelations.getCondition(),"A")) {
                //写入获得的初审对应终审任务人进入工作流
                Boolean assign=false;
                String authString = null;
                if(rtfState.equals(RtfState.F10.toString())){ //初审完成
                    authString = "CAS_APPLY_TEL_SURVEY"+"-"+userNo;
                }else{
                    authString = "CAS_APPLY_FINALAUDIT"+"-"+userNo;
                }
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
                    logger.info("分给初审上级终审人[" + userNo + "]分配成功...申请件编号[" + appNo + "],opUser[" + opUser + "],taskId[" + taskId
                            + "],taskKey[" + taskKey + "],rtfState[" + appMain.getRtfState() + "]");
                    return false;
                }
            }
            //初审件
        }else if(StringUtils.isNotBlank(rtfState)
                && (rtfState.equals(RtfState.B20.toString()) //预审完成
                || rtfState.equals(RtfState.H15.toString()) //决策结束转人工
                || rtfState.equals(RtfState.H16.toString()))) //决策结束谨慎审批(转人工)
        {
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("proName","初审调查");
            map.put("idNo",appMain.getIdNo());
            map.put("_SORT_NAME", "createDate");
            map.put("_SORT_ORDER", "DESC");
            // 获取审批历史
            List<TmAppHistory> tmAppHistoryList = applyHistoryService.getAppHistroyByParam(map);
            TmAppHistory lastTmAppHistory= new TmAppHistory();
            TmAppHistory tmAppHistoryNow = new TmAppHistory();
            // 获取全部有当前状态任务的用户,按用户以及任务个数分组
            List<CandidateBean> cbLists = getCandidate(taskKey);
            //获取该件受理网点地区
            TmAclBranch curBran = cacheContext.getTmAclBranchByCode(appMain.getOwningBranch());
            //判断该受理网点地区是不是在划分的审批区域中
            if(curBran!=null && curBran.getBranchSequence()!=null) {
                logger.info("申请件[" + appNo + "],检测受理网点在划分的审批区域中" + "将按区域划分到指定审批组");
                TmAppUserRelation tmAppUserRelation = new TmAppUserRelation();
                String checkGroup=String.valueOf(curBran.getBranchSequence());
                tmAppUserRelation.setCheckGroups(checkGroup);
                tmAppUserRelation.setUserType("B");
                List<TmAppUserRelation> list = tmAppUserRelationDao.queryForList(tmAppUserRelation);
                if (list == null || list.size() == 0) {
                    logger.info("申请件[" + appNo + "],受理网点在划分的审批区域中" + "但未检测到指定审批组");
                }else {
                    //如果有历史申请信息，则寻找历史信息审批人是否有在对应审批组中的
                    if(tmAppHistoryList!=null && tmAppHistoryList.size()>0) {
                        boolean isTheUser = false;
                        for(TmAppHistory tmAppHistory:tmAppHistoryList){
                            if(StringUtils.isNotEmpty(tmAppHistory.getOperatorId())){
                                for (int i = 0; i < userList.size(); i++) {
                                    SimpleUser user = userList.get(i);
                                    if(user.getUserNo().equals(tmAppHistory.getOperatorId())) {
                                        for (TmAppUserRelation relationUser : list) {
                                            if (relationUser.getUserNo().equals(tmAppHistory.getOperatorId()) && relationUser.getCondition().equals("A")) {
                                                /*boolean isTheCh= false;
                                                for (CandidateBean candidateBean : cbLists) {
                                                    if (candidateBean != null && candidateBean.userId.equals(tmAppHistory.getOperatorId())) {
                                                        //判断手上的案件数目，如果该员工案件数目超过配置的最大数目，不分配
                                                        if (dicBean.getMax() == 0 || dicBean.getMax() > candidateBean.getCt()) {
                                                            lastTmAppHistory = tmAppHistory;
                                                            isTheUser = true;
                                                        }
                                                        isTheCh = true;
                                                        break;
                                                    }
                                                }
                                                if(isTheCh == false){
                                                    lastTmAppHistory = tmAppHistory;
                                                    isTheUser = true;
                                                }*/
                                                lastTmAppHistory = tmAppHistory;
                                                isTheUser = true;
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            if(isTheUser){
                                break;
                            }
                        }
                        if(StringUtils.isNotEmpty(lastTmAppHistory.getOperatorId())) {
                            logger.info("同一个人的初审件直接到历史初审人员，申请件编号[" + appNo + "],历史任务人[" + lastTmAppHistory.getOperatorId() + "]");
                            // !设置任务转移记录
                            TmTaskTransfer taskTran = new TmTaskTransfer();
                            taskTran.setAppNo(appNo);
                            taskTran.setOrg(appMain.getOrg());
                            taskTran.setAssigner(AppConstant.SYS_AUTO);
                            taskTran.setOwner(lastTmAppHistory.getOperatorId());
                            taskTran.setTransferType(TaskTransferType.AUTO_ASSIGNE.state);
                            taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
                            activitiService.assingneeTask(task, appMain, taskTran, source);
                            logger.info("自动分配任务给操作员[" + userId + "]成功...,taskId[" + taskId + "],taskKey[" + taskKey
                                    + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null));
                            return false;
                        }
                    }
                    //如果没有历史申请信息，则选择审批组中任务量最少的
                    logger.info("没有历史申请信息，申请件编号[" + appNo + "],则选择审批组中任务量最少的");
                    if (cbLists != null && cbLists.size() > 0) {
                        for (int i = 0; i < userList.size(); i++) {
                            SimpleUser user = userList.get(i);
                            boolean isRelationUser = false;
                            boolean isInCandidate = false;
                            //先取无任务的
                            for (CandidateBean candidateBean : cbLists) {
                                if (user != null && candidateBean.userId.equals(user.getUserNo())) {
                                    isInCandidate = true;
                                    break;
                                }
                            }
                            if(isInCandidate==false){
                                //先判断此业务员是否在对应审批组中
                                for (TmAppUserRelation relationUser : list) {
                                    if (relationUser.getUserNo().equals(user.getUserNo()) && relationUser.getCondition().equals("A")) {
                                        isRelationUser = true;
                                        userId = user.getUserNo();
                                        break;
                                    }
                                }
                                if(isRelationUser){
                                    break;
                                }
                            }
                        }
                        // 手上都有任务，就取任务数最少的人。数据库查询已完成排序工作
                        if(userId == null){
                            logger.info("手上都有任务，选择审批组中任务量最少的");
                            // 此处应当取users中案件最少的人(得到cbList时实现),并且案件数量小于配置的最大数目
                            for (CandidateBean candidateBean : cbLists) {
                                for (int i = 0; i < userList.size(); i++) {
                                    SimpleUser user = userList.get(i);
                                    for (TmAppUserRelation relationUser : list) {
                                        if (relationUser.getUserNo().equals(user.getUserNo()) && relationUser.getCondition().equals("A")) {
                                            if (candidateBean != null && candidateBean.userId.equals(user.getUserNo())) {
                                                // 保证取到第一条，及数量最小的;判断手上的案件数目，如果案件最少的员工案件数目也超过配置的最大数目，不分配
                                                if (dicBean.getMax() == 0 || dicBean.getMax() > candidateBean.getCt()) {
                                                    userId = user.getUserNo();
                                                    if (userId != null) {
                                                        //自动分件时将任务人存入TmAppHistory表
                                                        tmAppHistoryNow.setOrg(appMain.getOrg());
                                                        tmAppHistoryNow.setAppNo(appMain.getAppNo());
                                                        tmAppHistoryNow.setRtfState(appMain.getRtfState());
                                                        tmAppHistoryNow.setProName("初审调查");
                                                        tmAppHistoryNow.setName(appMain.getName());
                                                        tmAppHistoryNow.setIdNo(appMain.getIdNo());
                                                        tmAppHistoryNow.setRemark("自动分件存入任务人信息");
                                                        tmAppHistoryNow.setOperatorId(userId);
                                                        applyInputService.saveTmAppHistory(tmAppHistoryNow);
                                                        //!设置任务转移记录
                                                        TmTaskTransfer taskTran = new TmTaskTransfer();
                                                        taskTran.setAppNo(appNo);
                                                        taskTran.setOrg(appMain.getOrg());
                                                        taskTran.setAssigner(AppConstant.SYS_AUTO);
                                                        taskTran.setOwner(userId);
                                                        taskTran.setTransferType(TaskTransferType.QUARTZ_ASSIGNE.state);
                                                        taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
                                                        activitiService.assingneeTask(task, appMain, taskTran, null);
                                                        logger.info("自动分配任务给操作员[" + userId + "]成功...,taskId[" + taskId + "],taskKey[" + taskKey
                                                                + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null));
                                                        return false;
                                                    }
                                                }
                                                logger.info("业务员编号："+user.getUserNo()+"该审批员任务量已达上限");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        logger.info("所有人都没任务，取该审批组业务员集合中的第一个业务员");
                        userId = list.get(0).getUserNo();
                    }
                    if (userId != null) {
                        //自动分件时将任务人存入TmAppHistory表
                        tmAppHistoryNow.setOrg(appMain.getOrg());
                        tmAppHistoryNow.setAppNo(appMain.getAppNo());
                        tmAppHistoryNow.setRtfState(appMain.getRtfState());
                        tmAppHistoryNow.setProName("初审调查");
                        tmAppHistoryNow.setName(appMain.getName());
                        tmAppHistoryNow.setIdNo(appMain.getIdNo());
                        tmAppHistoryNow.setRemark("自动分件存入任务人信息");
                        tmAppHistoryNow.setOperatorId(userId);
                        applyInputService.saveTmAppHistory(tmAppHistoryNow);
                        //!设置任务转移记录
                        TmTaskTransfer taskTran = new TmTaskTransfer();
                        taskTran.setAppNo(appNo);
                        taskTran.setOrg(appMain.getOrg());
                        taskTran.setAssigner(AppConstant.SYS_AUTO);
                        taskTran.setOwner(userId);
                        taskTran.setTransferType(TaskTransferType.QUARTZ_ASSIGNE.state);
                        taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
                        activitiService.assingneeTask(task, appMain, taskTran, null);
                        logger.info("自动分配任务给操作员[" + userId + "]成功...,taskId[" + taskId + "],taskKey[" + taskKey
                                + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null));
                        return false;
                    }
                }
                return false;
            }
            //当该受理网点地区不在划分的审批区域中时
            //如果有历史申请信息
            logger.info("申请件[" + appNo + "],不在在划分的审批区域中");
            if(tmAppHistoryList!=null && tmAppHistoryList.size()>0) {
                for (TmAppHistory tmAppHis : tmAppHistoryList) {
                    for (int i = 0; i < userList.size(); i++) {
                        SimpleUser user = userList.get(i);
                        if(user.getUserNo().equals(tmAppHis.getOperatorId())) {
                            TmAppUserRelation userCheck = new TmAppUserRelation();
                            if (StringUtils.isNotEmpty(tmAppHis.getOperatorId())) {
                                userCheck.setUserNo(tmAppHis.getOperatorId());
                            }
                            TmAppUserRelation isTheUser = tmAppUserRelationDao.queryForOne(userCheck);
                            if (isTheUser != null && isTheUser.getUserNo() != null) {
                                if (isTheUser.getCondition().equals("A")) {
                                    lastTmAppHistory = tmAppHis;
                                }
                            } else {
                                lastTmAppHistory = tmAppHis;
                            }
                            boolean isTheCh= false;
                            for (CandidateBean candidateBean : cbLists) {
                                if (candidateBean != null && candidateBean.userId.equals(lastTmAppHistory.getOperatorId())) {
                                    //判断手上的案件数目，如果该员工案件数目超过配置的最大数目，不分配
                                    if (dicBean.getMax() == 0 || dicBean.getMax() > candidateBean.getCt()) {
                                        userId = lastTmAppHistory.getOperatorId();
                                    }
                                    isTheCh = true;
                                    break;
                                }
                            }
                            if(isTheCh == false){
                                userId = lastTmAppHistory.getOperatorId();
                            }
                            if (StringUtils.isNotEmpty(userId)) {
                                break;
                            }
                            break;
                        }
                    }
                }
                if(StringUtils.isNotEmpty(userId)) {
                    logger.info("同一个人的初审件直接到历史初审人员，申请件编号[" + appNo + "],历史任务人[" + lastTmAppHistory.getOperatorId() + "]");
                    // !设置任务转移记录
                    TmTaskTransfer taskTran = new TmTaskTransfer();
                    taskTran.setAppNo(appNo);
                    taskTran.setOrg(appMain.getOrg());
                    taskTran.setAssigner(AppConstant.SYS_AUTO);
                    taskTran.setOwner(userId);
                    taskTran.setTransferType(TaskTransferType.AUTO_ASSIGNE.state);
                    taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
                    activitiService.assingneeTask(task, appMain, taskTran, source);
                    logger.info("自动分配任务给操作员[" + userId + "]成功...,taskId[" + taskId + "],taskKey[" + taskKey
                            + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null));
                    return false;
                }
            }/*else {//如果没有历史申请信息
                logger.info("申请件["+appNo+"],未检索到历史任务人"+"分配到同一个推广员下对应的初审人员");
                TmAppPrimCardInfo tmAppPrimCardInfo = applyQueryService.getTmAppPrimCardInfoByAppNo(appNo);
                if(StringUtils.isNotBlank(tmAppPrimCardInfo.getSpreaderNo())) {
                    TmAppPrimCardInfo entity = new TmAppPrimCardInfo();
                    entity.setSpreaderNo(tmAppPrimCardInfo.getSpreaderNo());
                    List<TmAppPrimCardInfo> list = tmAppPrimCardInfoDao.getTmAppPrimCardInfoList(entity);
                    Map<String, Object> maps = new HashMap<String, Object>();
                    for (TmAppPrimCardInfo element : list) {
                        maps.put("appNo", element.getAppNo());
                        maps.put("proName", "初审调查");
                        maps.put("_SORT_NAME", "createDate");
                        maps.put("_SORT_ORDER", "DESC");
                        List<TmAppHistory> tmAppHistoryLists = applyHistoryService.getAppHistroyByParam(maps);

                    //代码未完

                    TmAppHistory lastTmAppHistorys = new TmAppHistory();
                    //同一个推广员的初审件直接到历史初审人员
                    if (tmAppHistoryLists != null && tmAppHistoryLists.size() > 0) {
                        for (TmAppHistory tmAppHis : tmAppHistoryLists) {
                            for (int i = 0; i < userList.size(); i++) {
                                SimpleUser user = userList.get(i);
                                if (user.getUserNo().equals(tmAppHis.getOperatorId())) {
                                    TmAppUserRelation userCheck = new TmAppUserRelation();
                                    if (StringUtils.isNotEmpty(tmAppHis.getOperatorId())) {
                                        userCheck.setUserNo(tmAppHis.getOperatorId());
                                    }
                                    TmAppUserRelation isTheUser = tmAppUserRelationDao.queryForOne(userCheck);
                                    if (isTheUser != null && isTheUser.getUserNo() != null) {
                                        if (isTheUser.getCondition().equals("A")) {
                                            lastTmAppHistorys = tmAppHis;
                                        }
                                    } else {
                                        lastTmAppHistorys = tmAppHis;
                                    }
                                    boolean isTheCh = false;
                                    for (CandidateBean candidateBean : cbLists) {
                                        if (candidateBean != null && candidateBean.userId.equals(lastTmAppHistorys.getOperatorId())) {
                                            //判断手上的案件数目，如果该员工案件数目超过配置的最大数目，不分配
                                            if (dicBean.getMax() == 0 || dicBean.getMax() > candidateBean.getCt()) {
                                                userId = lastTmAppHistorys.getOperatorId();
                                            }
                                            isTheCh = true;
                                            break;
                                        }
                                    }
                                    if (isTheCh == false) {
                                        userId = lastTmAppHistorys.getOperatorId();
                                    }
                                    if (StringUtils.isNotEmpty(userId)) {
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        if (StringUtils.isNotEmpty(userId)) {
                            logger.info("同一个推广员的初审件直接到历史初审人员，申请件编号[" + appNo + "],历史任务人[" + lastTmAppHistorys.getOperatorId() + "]");
                            // !设置任务转移记录
                            TmTaskTransfer taskTran = new TmTaskTransfer();
                            taskTran.setAppNo(appNo);
                            taskTran.setOrg(appMain.getOrg());
                            taskTran.setAssigner(AppConstant.SYS_AUTO);
                            taskTran.setOwner(userId);
                            taskTran.setTransferType(TaskTransferType.AUTO_ASSIGNE.state);
                            taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
                            activitiService.assingneeTask(task, appMain, taskTran, source);
                            logger.info("自动分配任务给操作员[" + userId + "]成功...,taskId[" + taskId + "],taskKey[" + taskKey
                                    + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null));
                            return false;
                        }
                    }
                }
                }
            }*/
        }
        //正常流程往下流转时------
        logger.info("各匹配条件都未能分配走此件，进入随机自动分配...申请件编号[" + appNo + "],opUser[" + opUser + "],taskId[" + taskId
                + "],taskKey[" + taskKey + "],rtfState[" + appMain.getRtfState() + "]");
        // 获取全部有当前状态任务的用户,按用户以及任务个数分组
        List<CandidateBean> cbList = getCandidate(taskKey);
        if (cbList != null && cbList.size() > 0) {
            // 先找手上没任务的
            for (int i = 0; i < userList.size(); i++) {
                SimpleUser user = userList.get(i);
                boolean isInCandidates = false;
                for (CandidateBean candidateBean : cbList) {
                    if (user != null && candidateBean.userId.equals(user.getUserNo())) {
                        isInCandidates = true;
                        break;
                    }
                }
                if (isInCandidates == false) {
                    TmAppUserRelation userCheck = new TmAppUserRelation();
                    userCheck.setUserNo(user.getUserNo());
                    TmAppUserRelation isTheUser = tmAppUserRelationDao.queryForOne(userCheck);
                    if(isTheUser !=null && isTheUser.getUserNo()!=null){
                        if (isTheUser.getCondition().equals("A")){
                            userId = user.getUserNo();
                            break;
                        }
                    }else{
                        userId = user.getUserNo();
                        break;
                    }
                }
            }
            // 手上都有任务，就取任务数最少的人。数据库查询已完成排序工作
            if (userId == null) {
                // 此处应当取users中案件最少的人(得到cbList时实现),并且案件数量小于配置的最大数目
                for (CandidateBean candidateBean : cbList) {
                    for (int i = 0; i < userList.size(); i++) {
                        SimpleUser user = userList.get(i);
                        if (candidateBean != null && candidateBean.userId.equals(user.getUserNo())) {
                            // 保证取到第一条，及数量最小的;判断手上的案件数目，如果案件最少的员工案件数目也超过配置的最大数目，不分配
                            if (dicBean.getMax() == 0 || dicBean.getMax() > candidateBean.getCt()) {

                                TmAppUserRelation userCheck = new TmAppUserRelation();
                                userCheck.setUserNo(user.getUserNo());
                                TmAppUserRelation isTheUser = tmAppUserRelationDao.queryForOne(userCheck);
                                if(isTheUser !=null && isTheUser.getUserNo()!=null){
                                    if (isTheUser.getCondition().equals("A")) {
                                        userId = user.getUserNo();
                                    }
                                }else{
                                    userId = user.getUserNo();
                                }
                                if (userId != null) {
                                    //!设置任务转移记录
                                    TmTaskTransfer taskTran = new TmTaskTransfer();
                                    taskTran.setAppNo(appNo);
                                    taskTran.setOrg(appMain.getOrg());
                                    taskTran.setAssigner(AppConstant.SYS_AUTO);
                                    taskTran.setOwner(userId);
                                    taskTran.setTransferType(TaskTransferType.QUARTZ_ASSIGNE.state);
                                    taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
                                    activitiService.assingneeTask(task, appMain, taskTran,null);
                                    logger.info("自动分配任务给操作员[" + userId + "]成功...,taskId[" + taskId + "],taskKey[" + taskKey
                                            + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null));
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // 当前手上都没任务，就去users中的第一个
            userId = userList.get(0).getUserNo();
        }
        if (userId != null) {
            logger.info("自动分配任务给操作员[" + userId + "]成功...,taskId[" + taskId + "],taskKey[" + taskKey
                    + "],rtfState[" + appMain.getRtfState() + "]" + LogPrintUtils.printAppNoEndLog(appNo, start, null));

            //!设置任务转移记录
            TmTaskTransfer taskTran = new TmTaskTransfer();
            taskTran.setAppNo(appNo);
            taskTran.setOrg(appMain.getOrg());
            taskTran.setAssigner(AppConstant.SYS_AUTO);
            taskTran.setOwner(userId);
            taskTran.setTransferType(TaskTransferType.QUARTZ_ASSIGNE.state);
            taskTran.setStatus(TaskTransferStatus.ASSIGNED.state);
            activitiService.assingneeTask(task, appMain, taskTran,null);

        }
        return false;
    }

    /**
     * 筛选分行出用户
     *
     * @param users
     * @param branchIds
     * @return
     */
    private Map<String, List<SimpleUser>> filterBranchUser(Map<String, List<SimpleUser>> users, List<String> branchIds) {
        Map<String, List<SimpleUser>> users2 = new HashMap<String, List<SimpleUser>>();
        for (int i = 0; i < branchIds.size(); i++) {
            users2.put(branchIds.get(i), users.get(branchIds.get(i)));
        }
        return users2;
    }

    /**
     * 筛选终审可见额度用户
     *
     * @param
     * @param
     * @param
     * @return
     */
    private List<SimpleUser> filterFinalFlowUser(List<SimpleUser> userList, TmAppMain tmAppMain, String taskKey) {
        List<SimpleUser> userList2 = new ArrayList<SimpleUser>();
        BigDecimal appLmt = tmAppMain.getAppLmt();
        if (appLmt == null) {
            appLmt = BigDecimal.ZERO;
        }

        BigDecimal chkLmt = tmAppMain.getChkLmt();
        if (chkLmt == null) {
            chkLmt = BigDecimal.ZERO;
        }
        if (CollectionUtils.isNotEmpty(userList)) {
            for (int i = 0; i < userList.size(); i++) {
                SimpleUser su = userList.get(i);
                TmAppAuditQuota quota = cacheContext.getTmAppAuditQuotaForCache(su.getUserNo(), taskKey);
                //该操作员设置了可见额度最低与最高
                if (quota != null && (quota.getVisibleMinimum() != null || quota.getVisibleMaximum() != null)) {
                    BigDecimal compareSrcAmt = tmAppMain.getSugLmt();
                    if (compareSrcAmt == null && tmAppMain.getChkLmt() != null) {
                        compareSrcAmt = tmAppMain.getChkLmt();
                    } else if (compareSrcAmt == null && tmAppMain.getAppLmt() != null) {
                        compareSrcAmt = tmAppMain.getAppLmt();
                    } else if (compareSrcAmt == null && tmAppMain.getAccLmt() != null) {
                        compareSrcAmt = tmAppMain.getAccLmt();
                    } else {
                        userList2.add(su);
                        continue;
                    }
                    boolean isOk = false;
                    if (quota.getVisibleMinimum() != null && compareSrcAmt.compareTo(quota.getVisibleMinimum()) >= 0) {
                        isOk = true;
                    }
                    if (quota.getVisibleMaximum() != null && compareSrcAmt.compareTo(quota.getVisibleMaximum()) <= 0) {
                        isOk = true;
                    }
                    if (isOk) {
                        userList2.add(su);
                    }
                }/*else { 如果未设置有效的终审人员可见额度，则不分配给此人
					userList2.add(su);
				}*/
            }
        }

        return userList2;
    }

    /**
     * 找到拥有当前节点权限的员工
     *
     * @param taskKey
     * @return
     */
    private Map<String, List<SimpleUser>> filterEmployees(String taskKey, String productCd) {
        Map<String, List<SimpleUser>> map1 = new HashMap<String, List<SimpleUser>>();

        //循环的取tm_dit_dic表中DIC_TYPE为autoTransferSDuser中的REMARK的用户名存入filterUser(屏蔽的用户)
        //如果filterUser里面有值则不再进入此方法
        if (filterUser.size() == 0) {
            TmDitDic ditDic = new TmDitDic();
            ditDic.setDicType(DIT_DIC_FILTER_USER_STR);
            ditDic.setTabName(taskKey);
            List<TmDitDic> ditDics = sysParamService.getTmDitDic(ditDic);
            List<String> sdUserList = new ArrayList<String>();
            for (int i = 0; i < ditDics.size(); i++) {
                TmDitDic tmDitDic = ditDics.get(i);
                if (tmDitDic != null) {
                    String sdUser = tmDitDic.getRemark();
                    if (StringUtils.isNotEmpty(sdUser)) {
                        sdUserList.add(sdUser);
                    }
                }
                filterUser.put("sdUserNo", sdUserList);
            }
        }

/*		//如果等于空或者里面有个值是noData，表示不再进入该方法获取配置参数
		if(filterUser.size()==0 || !filterUser.containsKey(AUTO_FILTER_USER_NO_DATA)){
			TmDitDic ditDic = new TmDitDic();
			ditDic.setDicType(DIT_DIC_FILTER_USER_STR);
			ditDic.setTabName(taskKey);
			List<TmDitDic> ditDics = sysParamService.getTmDitDic(ditDic);
			if(ditDics!=null && ditDics.size()>0){
				for (int i = 0; i < ditDics.size(); i++) {
					filterUser.put(taskKey+"-"+ditDics.get(i).getRemark(), ditDics.get(i).getRemark());
				}
			}
			//设置无值
			if(filterUser.size()==0){
				filterUser = new HashMap<String, String>();
				filterUser.put(AUTO_FILTER_USER_NO_DATA,AUTO_FILTER_USER_NO_DATA);
			}
		}*/

        if (aclUserMap != null && aclUserMap.containsKey(taskKey)) {
            map1 = aclUserMap.get(taskKey);
        } else {
            String[] authString = new String[]{};
            if (StringUtils.equals(taskKey, "applyinfo-finalaudit")) {
                authString = new String[]{"CAS_APPLY_FINALAUDIT"};
            }
            if (StringUtils.equals(taskKey, "applyinfo-check")) {
                authString = new String[]{"CAS_APPLY_BASIC_CHECK"};
            }
            if (StringUtils.equals(taskKey, "applyinfo-pre-check")) {
                authString = new String[]{"CAS_APPLY_PRE_CHECK"};
            }
            if (StringUtils.equals(taskKey, "applyinfo-patchbolt")) {
                authString = new String[]{"CAS_APPLY_PATCHBOLT"};
            }
            if (StringUtils.equals(taskKey, "applyinfo-telephone-survey")) {
                authString = new String[]{"CAS_APPLY_TEL_SURVEY"};
            }
            if (StringUtils.equals(taskKey, "applyinfo-input-modify")) {
                authString = new String[]{"CAS_APPLY_UPDATE"};
            }
            if (StringUtils.equals(taskKey, "applyinfo-review")) {
                authString = new String[]{"CAS_APPLY_REAUDIT"};
            }

            //获取所有有对应操作权限的用户
            List<TmAclUser> aclUsers = accessUserService.getUserMenus(authString);
            for (TmAclUser acluser : aclUsers) {
                //所有有对应操作权限的用户不在过滤列表中的用户才可以继续使用
                if (filterUser == null || filterUser.size() == 0 || (acluser.getUserNo() != null
                        && filterUser.get("sdUserNo") != null && !filterUser.get("sdUserNo").contains(acluser.getUserNo()))) {
                    SimpleUser su = new SimpleUser();
                    su.setUserNo(acluser.getUserNo());
                    su.setUserChName(acluser.getUserName());
                    su.setBranchId(acluser.getBranchCode());
                    if (StringUtils.isNotEmpty(acluser.getBranchCode())) {
                        if (map1 != null && map1.containsKey(acluser.getBranchCode())) {
                            List<SimpleUser> aclu2 = map1.get(acluser.getBranchCode());
                            aclu2.add(su);
                            map1.put(acluser.getBranchCode(), aclu2);
                        } else {
                            List<SimpleUser> aclu2 = new ArrayList<SimpleUser>();
                            aclu2.add(su);
                            map1.put(acluser.getBranchCode(), aclu2);
                        }
                    }
                }
            }
            if (aclUserMap == null) {
                aclUserMap = new LinkedHashMap<String, Map<String, List<SimpleUser>>>();
            }
            aclUserMap.put(taskKey, map1);
        }
        return map1;
    }

    /**
     * 获取自动分案配置信息
     *
     * @param taskKey
     * @return
     */
    private DicBean getAutoAssignConfi(String taskKey) {
        String org = OrganizationContextHolder.getOrg();
        if (isOpenAutoAssigneeMap != null && isOpenAutoAssigneeMap.containsKey(org + "-" + taskKey)) {
            return isOpenAutoAssigneeMap.get(org + "-" + taskKey);
        } else {
            List<TmDitDic> tmDitDicList = getAllAutoAssignConfi();
            if (tmDitDicList == null || tmDitDicList.size() < 1) {
                return null;
            }
            for (int i = 0; i < tmDitDicList.size(); i++) {
                TmDitDic t = tmDitDicList.get(i);
                if (t != null) {
                    //是否开启
                    String assign = t.getRemark();
                    //最大平均派件数
                    String maxNum = t.getFormName();
                    try {
                        DicBean dicBean = new DicBean();
                        dicBean.setAssign(false);
                        dicBean.setMax(0);
                        // 配置以竖线（|）分隔
                        dicBean.setAssign("Y".equals(assign));
                        dicBean.setMax(Integer.parseInt(maxNum));
                        isOpenAutoAssigneeMap.put(t.getOrg() + "-" + t.getTabName(), dicBean);
                    } catch (Exception e) {
                        logger.error(OrganizationContextHolder.getOrg() + "自动分案配置错误", e);
                    }
                }
            }
        }
        return isOpenAutoAssigneeMap.get(org + "-" + taskKey);
    }

    /**
     * 获取所有自动分配配置列表
     *
     * @return
     */
    private List<TmDitDic> getAllAutoAssignConfi() {
        TmDitDic ditDic = new TmDitDic();
        ditDic.setDicType(DIT_DIC_AUTO_CONFIG_STR);
        List<TmDitDic> tmDitDicList = sysParamService.getTmDitDic(ditDic);
        if (tmDitDicList == null || tmDitDicList.size() < 1) {
            return null;
        }

        Map<String, DicBean> mapDicBean = new HashMap<String, DicBean>();
        isOpenAutoAssigneeMap = new HashMap<String, DicBean>();
        for (int i = 0; i < tmDitDicList.size(); i++) {
            TmDitDic t = tmDitDicList.get(i);
            if (t != null) {
                //是否开启
                String assign = t.getRemark();
                //最大平均派件数
                String maxNum = t.getFormName();
                try {
                    DicBean dicBean = new DicBean();
                    dicBean.setAssign(false);
                    dicBean.setMax(0);
                    // 配置以竖线（|）分隔
                    dicBean.setAssign("Y".equals(assign));
                    dicBean.setMax(Integer.parseInt(maxNum));
                    isOpenAutoAssigneeMap.put(t.getOrg() + "-" + t.getTabName(), dicBean);
                    mapDicBean.put(t.getOrg() + "-" + t.getTabName(), dicBean);
                } catch (Exception e) {
                    logger.error(OrganizationContextHolder.getOrg() + "自动分案配置错误", e);
                }
            }
        }
        return tmDitDicList;
    }

    /**
     * 获取已分配清单
     *
     * @param taskKey
     * @return
     */
    public List<CandidateBean> getCandidate(String taskKey) {
        List<CandidateBean> list = new ArrayList<CandidateBean>();
        ApplyTaskDetailsDto applyTaskDetailsDto = new ApplyTaskDetailsDto();
        applyTaskDetailsDto.setTaskDefKey(taskKey);
        List<ApplyTaskDetailsDto> taskList = applyTaskDetailsService.getTaskCntBytaskKey(applyTaskDetailsDto);
        if (CollectionUtils.isEmpty(taskList)) {
            return list;
        }
        for (ApplyTaskDetailsDto applyTaskDetailsDto2 : taskList) {
            CandidateBean candidateBean = new CandidateBean();
            candidateBean.ct = StringUtils.stringToIntegerNotNull(applyTaskDetailsDto2.getTaskCnt()+"");
            candidateBean.taskKey = (String) applyTaskDetailsDto2.getTaskDefKey();
            candidateBean.userId = (String) applyTaskDetailsDto2.getOwner();
            list.add(candidateBean);
        }
        return list;
    }



    /**
     * 判断所属人是否在筛选后的user集合中（此处不考虑操作员手中案件数量）
     *
     * @param
     * @param checkOperator
     * @return
     */
    private boolean isInUserList(List<SimpleUser> userList, String checkOperator) {
        if (CollectionUtils.sizeGtZero(userList)) {
            for (SimpleUser simpleUser : userList) {
                if (simpleUser.getUserNo().equals(checkOperator)) {
                    return true;
                }
            }
        }

        return false;
    }

    // 补件筛选出与录入时受理网点一致的补件员
    private List<SimpleUser> filterPathBoltUser(Map<String, List<SimpleUser>> users, TmAppMain tmAppMain) {
        String owningBranch = null;
        if (tmAppMain != null) {
            owningBranch = tmAppMain.getOwningBranch();// 得到申请件的受理网点
        }

        return users.get(owningBranch);
    }

    /**
     * 设置本行以及所有上级网点变量
     *
     * @param owningBranch
     * @param
     * @param
     */
    private List<String> setOwningBranchVariable(String owningBranch) {
        List<String> result = new ArrayList<String>();
        TmAclBranch curBran = cacheContext.getTmAclBranchByCode(owningBranch);
        if (curBran != null) {
            result.add(curBran.getBranchCode());
            TmAclBranch branch = null;
            String oBranch = curBran.getBranchCode();
            int i = 0;
            while (true) {
                branch = cacheContext.getTmAclBranchByCode(oBranch);
                if (branch == null) {
                    break;
                }
                oBranch = branch.getParentCode();
                if(StringUtils.isNotEmpty(oBranch) && !result.contains(oBranch)) {
                    result.add(oBranch);
                }
                i++;
                if (i > 4) {
                    break;
                }
            }
        }
        return result;
    }


}