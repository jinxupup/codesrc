package com.jjb.cas.quartz;

import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppMemoDao;
import com.jjb.ecms.biz.dao.apply.TmAppOrderMainDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCheatCheckData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodePreCheckData;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmAppSprePerBank;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO 当多卡同申的时候父类卡预审通过时 其它的卡自动预审通过
 * @Author: shiminghong
 * @Data: 2019/9/16 14:54
 * @Version 1.0
 */
@Component
public class AutoPreCheckJobQuartz implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4731826300265453145L;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ApplyInputService applyInputService;
    @Autowired
    private NodeObjectUtil nodeObjectUtil;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private TmAppOrderMainDao tmAppOrderMainDao;
    @Autowired
    private AppCommonUtil appCommonUtil;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TmAppMemoDao tmAppMemoDao;

    public void autoPreCheckJobQuartz() {
        long start = System.currentTimeMillis();
        logger.info("自动预审通过开始");
        try {
            List<TmAppOrderMain> tmAppOrderMains = new ArrayList<>();
            //获取在同卡同申时主件已经通过预审并且子件已经创建申请件的多卡同申记录   tmordermain中timerState为“S”
            tmAppOrderMains = tmAppOrderMainDao.getAppOrderMainByTimerState("S");
            if (CollectionUtils.isEmpty(tmAppOrderMains)) {
                logger.info("没有需要处理的多卡同申自动预审件");
            }
            Iterator<TmAppOrderMain> iterator = tmAppOrderMains.iterator();
            while (iterator.hasNext()) {
                TmAppOrderMain tmAppOrderMain = iterator.next();
                if (tmAppOrderMain != null) {
                    long subTkId = System.currentTimeMillis();
                    String mainAppNo = tmAppOrderMain.getAppNo();
                    try {
                        //查询主件预审提交结果
                        ApplyNodePreCheckData preNode = null;
                        Map<String, Serializable> A085Map = applyQueryService.getNodeInfoByAppNo(mainAppNo, EnumsActivitiNodeType.A085.name());
                        if (A085Map != null && A085Map.containsKey(AppConstant.APPLY_NODE_PRE_CHECK_DATA)) {
                            preNode = (ApplyNodePreCheckData) A085Map.get(AppConstant.APPLY_NODE_PRE_CHECK_DATA);
                        }
                        //主件做过预审且预审结果是通过
                        if (preNode != null && StringUtils.equals(preNode.getConfirmType(), "P")) {
                            subPreCheckSubmit(tmAppOrderMain, preNode);
                        } else {
                            throw new ProcessException("多卡同申件[" + mainAppNo + "，其主件未通过预审");
                        }
                    } catch (Exception e) {
                        tmAppOrderMain.setTimerState("E");
                        tmAppOrderMain.setExceptionMsg(e.getMessage());
                        tmAppOrderMainDao.updateTmAppOrderMain(tmAppOrderMain);
                        logger.error(LogPrintUtils.printAppNoEndLog(tmAppOrderMain.getAppNo(), subTkId, "多卡同申-子件预审批量提交"), e);
                    } finally {
                        logger.info(LogPrintUtils.printAppNoEndLog(tmAppOrderMain.getAppNo(), subTkId, "多卡同申-子件预审批量提交"));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("多卡重申时，处理预审通过件异常", e);
            throw new ProcessException("多卡重申时，处理预审通过件异常", e);
        } finally {
            logger.info("自动预审通过,耗时[{}]ms", System.currentTimeMillis() - start);
        }
    }

    /**
     * 1.多卡同申根据主件申请件编号查询子申请件清单
     * 2.若主件是否以及完成预审，若已完成预审，则子件自动预审通过
     * 4.若所有件都预审完成，则当前这笔多卡同申记录状态修改为：O-处理结束
     *
     * @param tmAppOrderMain
     */
    private void subPreCheckSubmit(TmAppOrderMain tmAppOrderMain, ApplyNodePreCheckData mainPreCheckData) {
        if (tmAppOrderMain == null) {
            return;
        }
        String mianAppNo = tmAppOrderMain.getAppNo();
        //以当前的父类的申请件编号作为子类申请件中的taskNum字段，并且状态为B16，查询满足条件的申请件
        //如果已经发起流程并且在预审阶段，则直接预审通过
        List<TmAppMain> mainList = applyQueryService.getApplyJobToPreCheck(mianAppNo);
        if (CollectionUtils.sizeGtZero(mainList)) {
            Iterator<TmAppMain> it = mainList.iterator();
            while (it.hasNext()) {
                //当前处理的子件
                TmAppMain currAppMain = it.next();
                if (currAppMain != null && StringUtils.isNotBlank(currAppMain.getAppNo())) {
                    String appNo = currAppMain.getAppNo();
                    boolean notActProcess = false;
                    String taskId = "";
                    //=====验证当前申请件是否可以接受预审结果
                    List<Task> taskList = taskService.createTaskQuery().processInstanceBusinessKey(appNo).list();
                    if (CollectionUtils.sizeGtZero(taskList)) {
                        for (int i = 0; i < taskList.size(); i++) {
                            Task task = taskList.get(i);
                            taskId = task.getId();
                            if (task != null && task.getTaskDefinitionKey() != null
                                    && task.getTaskDefinitionKey().equals("applyinfo-pre-check")) {
                                notActProcess = true;
                                break;
                            }
                        }
                    }
                    if (!notActProcess) {
                        throw new ProcessException("当前申请件不可以接受预审结果[" + appNo + "]状态");
                    }

                    //默认通过
                    RtfState rtfState = RtfState.B20;
                    //查询当前子件决策结果
                    Map<String, Serializable> A020Map = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A020.name());
                    ApplyNodeCheatCheckData cheat = null;
                    if (A020Map != null && A020Map.containsKey(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA)) {
                        cheat = (ApplyNodeCheatCheckData) A020Map.get(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA);
                    }
                    TmProduct product = cacheContext.getProduct(currAppMain.getProductCd());
                    if (product == null || !StringUtils.equals(product.getProductStatus(), "A")) {
                        throw new ProcessException("产品" + currAppMain.getProductCd() + "已经失效!");
                    }

                    if (cheat != null && StringUtils.equals(cheat.getContent(), "通过")) {
                        if (currAppMain.getSugLmt() != null) {
                            currAppMain.setAccLmt(currAppMain.getSugLmt());
                            if (product.getApprovalMaximum() == null || product.getApprovalMaximum().compareTo(currAppMain.getAccLmt()) >= 0) {
                                // 状态为未归档，进入归档管理节点
                                rtfState = RtfState.B25;
                                currAppMain.setFileFlag("N");
                            } else {
                                rtfState = RtfState.B20;
                                currAppMain.setRemark("系统备注-系统建议额度[" + currAppMain.getAccLmt() + "]大于产品上限额度[" + product.getApprovalMaximum() + "]，故转人工审批");
                            }
                        } else {
                            currAppMain.setRemark("系统备注-系统建议额度为空，转人工审批");
                        }
                    }


                    //更新TmAppMain主表信息
                    //获取多卡同申已经预审通过的卡片信息  复制到当前申请件  需要两个字段 TASK_LAST_OP_USER UPDATE_USER、
                    TmAppMain appMain = applyQueryService.getTmAppMainByAppNo(currAppMain.getTaskNum());
                    if (appMain != null) {
                        String taskLastOpUser = appMain.getTaskLastOpUser();
                        String updateUser = appMain.getUpdateUser();
                        currAppMain.setTaskLastOpUser(taskLastOpUser);
                        currAppMain.setUpdateUser(updateUser);
                        currAppMain.setImageNum(StringUtils.setValue(currAppMain.getImageNum(), appMain.getImageNum()));
                        currAppMain.setOwningBranch(StringUtils.setValue(currAppMain.getOwningBranch(), appMain.getOwningBranch()));
                    }
                    currAppMain.setRtfState(rtfState.name());
                    //设置附件证明信息数据
                    //更新TmAppPrimAnnexEvi表
                    //获取多卡同申已经预审通过的卡片信息  复制到当前申请件
                    setEviData(currAppMain);
                    //更新TmAppPrimCardInfo表
                    //获取多卡同申已经预审通过的卡片信息  复制到当前申请件
                    setCardInfoData(currAppMain);

                    //流程节点公共信息
                    ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
                    Map<String, Serializable> tmAppNodeInfoRecordMap = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A070.name());
                    if (tmAppNodeInfoRecordMap != null && tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA) != null) {
                        applyNodeCommonData = (ApplyNodeCommonData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
                    }
                    //节点公共数据 赋值
                    applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, currAppMain);

                    //节点公共数据 赋值
                    Map<String, Serializable> data = new HashMap<String, Serializable>();//节点数据对象
                    String user = AppConstant.SYS_AUTO;//操作员
                    applyNodeCommonData.setRtfStateType(rtfState.name());
                    applyNodeCommonData.setOperatorId(user);
                    applyNodeCommonData.setAppType(currAppMain.getAppType());
                    applyNodeCommonData.setDate(new Date());
                    //保存节点数据
                    data.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
                    data.put(AppConstant.APPLY_NODE_PRE_CHECK_DATA, mainPreCheckData);
                    appCommonUtil.setOrg(OrganizationContextHolder.getOrg());
                    nodeObjectUtil.insertAllNodeRec(data, appNo);
                    //避免更新历史信息时,机构号保存为空
                    //保存预审备注
                    String mainPreRemark = "";
                    TmAppMemo appMemo = new TmAppMemo();
                    appMemo.setAppNo(appMain.getAppNo());
                    appMemo.setTaskDesc("applyinfo-pre-check");
                    appMemo.setRtfState("B20");
                    TmAppMemo dbMemo=new TmAppMemo();
                    List<TmAppMemo> tmAppMemoList = tmAppMemoDao.getTmAppMemoByAppNo(appMemo);
                    if (CollectionUtils.sizeGtZero(tmAppMemoList)){
                         dbMemo = tmAppMemoList.get(0);
                        if (dbMemo != null) {
                            mainPreRemark = dbMemo.getMemoInfo();
                        }
                    }
                    TmAppMemo tmAppRemark = new TmAppMemo();
                    tmAppRemark.setAppNo(appNo);
                    tmAppRemark.setMemoType(AppConstant.APP_REMARK);
                    tmAppRemark.setTaskKey(EcmsAuthority.CAS_APPLY_PRE_CHECK.name());
                    tmAppRemark.setTaskDesc(EcmsAuthority.CAS_APPLY_PRE_CHECK.lab);
                    tmAppRemark.setRtfState(rtfState.toString());
                    tmAppRemark.setCreateUser(StringUtils.setValue(dbMemo.getCreateUser(),AppConstant.SYS_AUTO.toString()));
                    String preRemark = ",[系统备注]：多卡同申,客户件[" + mianAppNo + "]已完成预审,故当前件自动通过预审";
                    tmAppRemark.setMemoInfo("[主件预审备注]：" + mainPreRemark + preRemark);
                    currAppMain.setRemark(currAppMain.getRemark() + ";" + preRemark);
                    applyInputService.saveTmAppMemo(tmAppRemark);
                    //历史记录信息
                    TmAppHistory tmAppHistory = new TmAppHistory();
                    tmAppHistory = AppCommonUtil.insertApplyHist(appNo, user, rtfState, tmAppHistory.getRefuseCode(), currAppMain.getRemark());
                    tmAppHistory.setName(currAppMain.getName());
                    tmAppHistory.setIdNo(currAppMain.getIdNo());
                    tmAppHistory.setIdType(currAppMain.getIdType());
                    applyInputService.saveTmAppHistory(tmAppHistory);
                    applyInputService.updateTmAppMain(currAppMain);
                    //发起流程
                    //=======发起后续流程=======//
                    Long tokenId = System.currentTimeMillis();
                    logger.info(LogPrintUtils.printAppNoLog(appNo, tokenId, null) + "是否发起工作流:" + notActProcess);
                    Map<String, Serializable> activitiMap = new HashMap<String, Serializable>();
                    activitiMap.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
                    if (notActProcess) {
                        activitiService.completeTask(taskId, activitiMap, appNo);
                    }
                    logger.info("定时任务：结束提交预审，==>申请件编号：[" + appNo + "]任务taskId:[" + taskId + "],节点类型[" + "定时任务自动节点" + "]");
                }
            }
        }
        tmAppOrderMain.setTimerState("O");
        tmAppOrderMainDao.updateTmAppOrderMain(tmAppOrderMain);
    }

    private void setEviData(TmAppMain currAppMain) {
        //当前件
        TmAppPrimAnnexEvi currEvi = applyQueryService.getTmAppPrimAnnexEviByAppNo(currAppMain.getAppNo());
        //主件
        TmAppPrimAnnexEvi mainEvi = applyQueryService.getTmAppPrimAnnexEviByAppNo(currAppMain.getTaskNum());

        boolean eviNoDB = false;
        if (currEvi == null) {
            eviNoDB = true;
            currEvi = new TmAppPrimAnnexEvi();
        }
        currEvi.setAppNo(currAppMain.getAppNo());
        currEvi.setOrg(currAppMain.getOrg());
        if (mainEvi != null) {
            currEvi.setIndSignFile(StringUtils.setValue(currEvi.getIndSignFile(), mainEvi.getIndSignFile()));
            currEvi.setIndIdFile(StringUtils.setValue(currEvi.getIndIdFile(), mainEvi.getIndIdFile()));
            currEvi.setIndJobFile(StringUtils.setValue(currEvi.getIndJobFile(), mainEvi.getIndJobFile()));
        }
        if (eviNoDB) {
            currEvi.setCreateDate(new Date());
            currEvi.setCreateUser(StringUtils.setValue(currEvi.getCreateUser(), mainEvi.getCreateUser()));
            applyInputService.saveTmAppPrimAnnexEvi(currEvi);
        } else {
            currEvi.setUpdateDate(new Date());
            currEvi.setUpdateUser(StringUtils.setValue(currEvi.getUpdateUser(), mainEvi.getUpdateUser()));
            applyInputService.updateTmAppPrimAnnexEvi(currEvi);
        }
    }

    private void setCardInfoData(TmAppMain currAppMain) {
        //当前件
        TmAppPrimCardInfo currCard = applyQueryService.getTmAppPrimCardInfoByAppNo(currAppMain.getAppNo());
        //主件
        TmAppPrimCardInfo mainCard = applyQueryService.getTmAppPrimCardInfoByAppNo(currAppMain.getTaskNum());
        boolean cardInfoNoDB = false;
        if (currCard == null) {
            cardInfoNoDB = true;
            currCard = new TmAppPrimCardInfo();
        }
        currCard.setAppNo(currAppMain.getAppNo());
        currCard.setOrg(currAppMain.getOrg());
        currCard.setSpreaderNo(StringUtils.setValue(currCard.getSpreaderNo(), mainCard.getSpreaderNo()));
        currCard.setSpreaderType(StringUtils.setValue(currCard.getSpreaderType(), mainCard.getSpreaderType()));
        //推广人处理
        currCard.setSpreaderName(StringUtils.setValue(currCard.getSpreaderName(), mainCard.getSpreaderName()));
        currCard.setSpreaderBranchThree(StringUtils.setValue(currCard.getSpreaderBranchThree(), mainCard.getSpreaderBranchThree()));
        currCard.setSpreaderBranchTwo(StringUtils.setValue(currCard.getSpreaderBranchTwo(), mainCard.getSpreaderBranchTwo()));
        currCard.setSpreaderTelephone(StringUtils.setValue(currCard.getSpreaderTelephone(), mainCard.getSpreaderTelephone()));
        currCard.setSpreaderIsBankEmployee("Y");
        currCard.setSpreaderBranchThree(StringUtils.setValue(currCard.getSpreaderBranchThree(), mainCard.getSpreaderBranchThree()));
        if (StringUtils.isEmpty(currCard.getSpreaderBranchThree())) {
            currCard.setSpreaderBranchThree(currAppMain.getOwningBranch());
        }
        //三亲核实与推广渠道需要与主件相同
        currCard.setSpreaderType(StringUtils.setValue(mainCard.getSpreaderType(),currCard.getSpreaderType()));
        currCard.setSpreaderMode(StringUtils.setValue(mainCard.getSpreaderMode(),currCard.getSpreaderMode()));

        currCard.setPreNo(StringUtils.setValue(currCard.getPreNo(), mainCard.getPreNo()));
        currCard.setPreName(StringUtils.setValue(currCard.getPreName(), mainCard.getPreName()));
        currCard.setPreTelephone(StringUtils.setValue(currCard.getPreTelephone(), mainCard.getPreTelephone()));
        currCard.setPreBranchThree(StringUtils.setValue(currCard.getPreBranchThree(), mainCard.getPreBranchThree()));
        if (cardInfoNoDB) {
            currCard.setCreateDate(new Date());
            currCard.setCreateUser(StringUtils.setValue(currCard.getCreateUser(), mainCard.getCreateUser()));
            applyInputService.saveTmAppPrimCardInfo(currCard);
        } else {
            applyInputService.updateTmAppPrimCardInfo(currCard);
        }
    }
}
