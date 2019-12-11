package com.jjb.cas.service.impl;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.ecms.biz.activiti.ActivitiUtils;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppHistoryDao;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.service.api.AutoHandleTaskQuartzService;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: TODO  定时处理进件任务,异步发起工作流
 * @Author: shiminghong
 * @Data: 2019/7/4 18:52
 * @Version 1.0
 */
@Service("autoHandleTaskQuartzImpl")
public class AutoHandleTaskQuartzImpl extends BaseController implements AutoHandleTaskQuartzService {

    private int corePoolSize = 20;
    private int maxPoolSize = 50;


    private static Logger logger = LoggerFactory.getLogger(AutoHandleTaskQuartzImpl.class);
    @Autowired
    private ApplyInputService applyInputService;
    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private AppCommonUtil appCommonUtil;
    @Autowired
    private ActivitiUtils activitiUtils;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private TmAppMainDao tmAppMainDao;

    @Override
    public void autoHandleTaskQuartz() {
        Page<TmAppMain> page = new Page<>();
        //查询B10状态的申请件并且是非02渠道（微信公众号）的进件
        page.getQuery().put("rtfState", "B10");
        page.getQuery().put("appSouce", "02");

        //获取指定的最大的处理次数
        TmDitDic ditDicTimes = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.MAX_REFUSE_TIMES);
        //获取指定的条数的申请件任务
        TmDitDic ditDicTask = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.THE_NUMBEROF_TASK);
        if (ditDicTask != null && StringUtils.isNotEmpty(ditDicTask.getRemark())) {
            logger.info("定时处理进件任务,每次处理任务量为" + ditDicTask.getRemark());
            page.setPageSize(Integer.parseInt(ditDicTask.getRemark()));
        }
        //以申请件编号升序排序
        page.setSortName("CREATE_DATE");
        page.setSortOrder("DESC");
        if (ditDicTimes != null && StringUtils.isNotEmpty(ditDicTimes.getRemark())) {
            logger.info("定时处理进件任务,每次处理最大异常为" + ditDicTimes.getRemark());
            page.getQuery().put("refuseTimes", Integer.parseInt(ditDicTimes.getRemark()));
        }
        page = applyQueryService.getTheNumberOfTask(page);
        List<TmAppMain> tmAppMainList = new ArrayList<>();
        tmAppMainList = page.getRows();
        //更新状态  使用refuseCode2字段标记
        for (int i = 0; i < tmAppMainList.size(); i++) {
            TmAppMain tmAppMainUpdate = tmAppMainList.get(i);
            tmAppMainUpdate.setRefuseCode2("isHandling");
            applyInputService.updateTmAppMain(tmAppMainUpdate);
        }
        //开始处理
        for (int i = 0; i < tmAppMainList.size(); i++) {
            TmAppMain tmAppMain= tmAppMainList.get(i);
             String org = OrganizationContextHolder.getOrg();
             String operId = OrganizationContextHolder.getUserNo(); // 操作员ID
             String appNo = tmAppMain.getAppNo();
             String productCd = tmAppMain.getProductCd();
             Map<String, Serializable> vars = new HashMap<String, Serializable>();
            ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
            applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, tmAppMain);
            vars.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
            setThreadSize();//设置线程连接数
            taskExecutor.execute(new Runnable() {
                public void run() {
                    long start = System.currentTimeMillis();
                    try {
                        logger.info("发起流程----" + LogPrintUtils.printAppNoLog(appNo, start, T1005Req.servId));
                        appCommonUtil.setOrg(org);
                        if (StringUtils.isNotEmpty(operId)) {
                            OrganizationContextHolder.setUserNo(operId);
                        } else {
                            OrganizationContextHolder.setUserNo(AppConstant.SYS_AUTO.toString());
                        }
                        //获取使用流程
                        String procdefKey = activitiUtils.getTaskProcessKeyByProductAndAppSource(
                                appNo, start, productCd, tmAppMain.getAppSource(), T1005Req.servId);
                        if (StringUtils.isNotEmpty(procdefKey)) {
                            activitiService.startNewProcess(procdefKey, appNo, vars);
                            logger.info("流程结束---" + LogPrintUtils.printAppNoEndLog(appNo, start, T1005Req.servId));
                        } else {
                            logger.info("没有获取到默认的流程定义，请检查流程！" + LogPrintUtils.printAppNoLog(appNo, null, null));
                        }
                        //更新状态(必须重新查询出来，如果直接用tmAppMain对象，会是以前的rtfstate)
                        TmAppMain appMain =tmAppMainDao.getTmAppMainByAppNo(appNo);
                        appMain.setRefuseCode2("");
                        applyInputService.updateTmAppMain(appMain);
                    } catch (ProcessException e) {
                        logger.error("申请审批流程处理失败-appNo[" + appNo + "], tokenId[" + start + "]", e);
//						throw new ProcessException("申请审批流程处理失败-" + e.getMessage());
                        //更新状态
                        tmAppMain.setRefuseCode2("");
                        //如果不存在就赋值配置中的最大异常处理次数，避免重复循环提交该申请件
                        Integer i = 1;
                        if (StringUtils.isNotEmpty(StringUtils.valueOf(tmAppMain.getRefuseCode3()))) {
                            i = StringUtils.stringToInteger(tmAppMain.getRefuseCode3());
                            ++i;
                            tmAppMain.setRefuseCode3(StringUtils.valueOf(i));
                        }else{
                            tmAppMain.setRefuseCode3(ditDicTimes.getRemark());
                        }
                        applyInputService.updateTmAppMain(tmAppMain);
                        //存tmapphistory
                        RtfState rst = AppCommonUtil.stringToEnum(RtfState.class, tmAppMain.getRtfState(), null);
                        TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo,
                                OrganizationContextHolder.getUserNo(), rst, e.getMessage(),
                                "定时任务：处理B10状态申请件任务提交工作流第" + i + "次失败。详情看日志");
                        tmAppHistory.setName(tmAppMain.getName());
                        tmAppHistory.setIdNo(tmAppMain.getIdNo());
                        applyInputService.saveTmAppHistory(tmAppHistory);
                    }
                }
            });
        }
    }

    /**
     * 设置线程池数量
     */
    private void setThreadSize() {
        TmDitDic threadSize = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_threadPoolSize);
        if (threadSize != null) {
            try {
                corePoolSize = StringUtils.stringToInteger(StringUtils.setValue(threadSize.getRemark(), "20"));
            } catch (Exception e) {
                logger.warn("转换数据库配置的[核心线程数]异常,故默认[20],ErMsg:" + e.getMessage());
            }
            try {
                maxPoolSize = StringUtils.stringToInteger(StringUtils.setValue(threadSize.getIfUsed(), "50"));
            } catch (Exception e) {
                logger.warn("转换数据库配置的[最大线程数]异常,故默认[50],ErMsg:" + e.getMessage());
            }
        }
        // 使用线程发起工作流
        taskExecutor.setCorePoolSize(corePoolSize);
        taskExecutor.setMaxPoolSize(maxPoolSize);
    }


}
