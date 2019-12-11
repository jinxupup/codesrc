package com.jjb.cas.quartz.schedule;

import com.alibaba.fastjson.JSON;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.cas.biz.process.T1005ProcessUtils;
import com.jjb.cas.biz.util.CheckReqT1005;
import com.jjb.ecms.biz.activiti.ActivitiUtils;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppOrderMainDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.TmMirCardService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmDitDic;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.service.dto.T1005.T1005Req;
import com.jjb.ecms.service.dto.T1005.T1005Resp;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CodeMarkUtils;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多卡同申，自动进件
 *
 * @author hp
 */
@JobHandler(value = "autoApplyJobHandler")
@Service
public class AutoApplyJobHandler extends IJobHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TmAppOrderMainDao tmAppOrderMainDao;

    @Autowired
    private T1005ProcessUtils t1005Process;
    @Autowired
    private CodeMarkUtils codeMarkUtils;
    @Autowired
    private CacheContext cacheContext;
    @Autowired
    private TmMirCardService tmMirCardService;
    @Autowired
    private ApplyInputService applyInputService;
    @Autowired
    private AppCommonUtil appCommonUtil;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private ActivitiUtils activitiUtils;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        long start = System.currentTimeMillis();
        logger.info("多卡同申开始," + param);
        try {
            //查找多卡同申审批失败(M05)的主件并且多卡同申表里定时器状态为W的件,并将其多卡同申表里的定时器状态修改为P
            TmDitDic ditOnline = cacheContext.getApplyOnlineOnOff(AppDitDicConstant.onLinOff_updateByRtfAndTimerState);//是否更新多卡同申失败件
            if (ditOnline != null && StringUtils.isNotEmpty(ditOnline.getRemark()) && ditOnline.getRemark().equals("Y")){
                try {
                    tmAppOrderMainDao.updateByRtfAndTimerState("W");
                    logger.info("多卡同申主件审批失败(M05)及定时器W状态的多卡同申记录修改状态成功");
                }catch (Exception e){
                    logger.error("多卡同申主件审批失败(M05)及定时器W状态的多卡同申记录修改状态失败");
                }
            }
            List<TmAppOrderMain> tmAppOrderMainList = tmAppOrderMainDao.getAppOrderMainByTimerState("P");
            for (TmAppOrderMain tmAppOrderMain : tmAppOrderMainList) {
                if (tmAppOrderMain.getValidProductType().contains("0")) {
                    // 待处理类
                    char[] productTypes = tmAppOrderMain.getValidProductType().toCharArray();
                    String[] productCds = tmAppOrderMain.getValidProductCds().split(",");
                    for (int i = 0; i < productTypes.length; i++) {
                        if (productTypes[i] == '0') {
                            try {
                                T1005Req req = JSON.parseObject(tmAppOrderMain.getReqJson(), T1005Req.class);
                                if(req!=null) {
                                    String productCd = productCds[i];
                                    if(req.getCusts()!=null && req.getCusts().get(0)!=null) {
                                        String idType= req.getCusts().get(0).getIdType();
                                        String idNo= req.getCusts().get(0).getIdNo();
                                        String name= req.getCusts().get(0).getName();
                                        String existProduct = t1005Process.isExistProduct(start+"",idType, idNo, name, productCd);
                                        if (StringUtils.equals(existProduct, "0")) {
                                            req.setProductCd(productCds[i]);
                                            req.setTaskNum(tmAppOrderMain.getAppNo());
                                            //多卡同申时发起子申请件流程时，使用同步方法
                                            startProcess(req);
                                            // 等待1秒避免并发
                                            Thread.sleep(1000L);
                                        }
                                    }else {
                                        productTypes[i] = '2';//重复申请
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("多卡同申异常"+codeMarkUtils.makeIDCardMask(tmAppOrderMain.getIdNo()), e);
                                productTypes[i] = '8';//异常
                                tmAppOrderMain.setValidProductType(new String(productTypes));
                                tmAppOrderMain.setExceptionMsg(tmAppOrderMain.getExceptionMsg() + e.getMessage() + ",");
                                tmAppOrderMainDao.updateTmAppOrderMain(tmAppOrderMain);
                                continue;
                            }
                            productTypes[i] = '1';//成功处理
                            tmAppOrderMain.setValidProductType(new String(productTypes));
                            tmAppOrderMainDao.updateTmAppOrderMain(tmAppOrderMain);
                        }
                    }
                } else {
                    // 处理完毕，修改状态成功
                    tmAppOrderMain.setTimerState("S");//这条记录所有卡处理完成
                    tmAppOrderMainDao.updateTmAppOrderMain(tmAppOrderMain);
                }
            }
        } catch (Exception e) {
            logger.error("多卡同申异常", e);
            return FAIL;
        } finally {
            logger.info("多卡同申,耗时[{}]ms", System.currentTimeMillis() - start);
        }
        return SUCCESS;
    }

    @Transactional
    public T1005Resp startProcess(T1005Req req) throws ProcessException {
        logger.debug("T1005Process-处理申请件开始...：");
        T1005Resp resp = new T1005Resp();
        if (req != null && StringUtils.isEmpty(req.getOrg())) {
            req.setOrg(OrganizationContextHolder.getOrg());
        }
        ApplyInfoDto applyInfoDto = t1005Process.buildApplyInfoDto(req);// 构建ApplyNodeLoggingData对象

        String checkMsg = CheckReqT1005.logicCheck(applyInfoDto, req, cacheContext, tmMirCardService, logger);//数据逻辑联动验证
        if (!checkMsg.equals("")) {
            logger.error("验证申请资料信息失败,Msg[" + checkMsg + "]");
            throw new ProcessException(checkMsg);
        }
        try {
            //提交操作
            saveApplyJobInput(applyInfoDto);
        } catch (ProcessException e) {
            logger.error("提交申请件处理失败" + LogPrintUtils.printAppNoEndLog(applyInfoDto.getAppNo(), null, null), e);
            throw new ProcessException(e.getMessage());
        } catch (Exception e) {
            logger.error("提交申请件异常" + LogPrintUtils.printAppNoEndLog(applyInfoDto.getAppNo(), null, null), e);
            throw new ProcessException("系统保存客户申请资料失败，请重试!");
        }
        TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
        if (tmAppMain == null || StringUtils.isEmpty(tmAppMain.getAppNo())) {

            logger.error("系统未生成申请件编号" + LogPrintUtils.printAppNoEndLog(applyInfoDto.getAppNo(), null, null));
            throw new ProcessException("系统未生成申请件编号，请确认申请相关资料并再次提交！");
        } else {
            resp.setAppNo(tmAppMain.getAppNo());
        }
        // TODO　发送到审批小助手
        return resp;
    }

    private void saveApplyJobInput(ApplyInfoDto applyInfoDto) throws ProcessException {

        final String org = OrganizationContextHolder.getOrg();
        String contUser = OrganizationContextHolder.getUserNo();
        TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
        TmAppAudit tmAppAudit = applyInfoDto.getTmAppAudit();

        TmAppPrimCardInfo tmAppPrimCardInfo = applyInfoDto.getTmAppPrimCardInfo();
        if (tmAppPrimCardInfo != null && StringUtils.isEmpty(contUser)) {
            if (StringUtils.isNotEmpty(tmAppPrimCardInfo.getInputNo())) {
                contUser = tmAppPrimCardInfo.getInputNo();
            } else if (StringUtils.isNotEmpty(tmAppPrimCardInfo.getInputName())) {
                contUser = tmAppPrimCardInfo.getInputName();
            }
            OrganizationContextHolder.setUserNo(contUser);
        }
        final String operId = OrganizationContextHolder.getUserNo(); // 操作员ID
        final String appNo = tmAppMain.getAppNo();
        //卡产品代码
        final String productCd = tmAppMain.getProductCd();
//		final String appType = tmAppMain.getAppType();
        TmProduct tmProduct = null;
        if (StringUtils.isNotBlank(productCd)) {
            tmProduct = cacheContext.getProduct(productCd);
        }
//		tmAppMain.setRtfState(RtfState.B10.toString());
        tmAppMain.setUpdateDate(new Date()); // 修改日期
        tmAppMain.setCreateDate(new Date()); // 创建日期
        if (StringUtils.isNotEmpty(operId)) {
            tmAppMain.setUpdateUser(operId);
            tmAppMain.setCreateUser(operId); // 创建人
        } else {
            tmAppMain.setUpdateUser(AppConstant.SYS_AUTO.toString());
            tmAppMain.setCreateUser(AppConstant.SYS_AUTO.toString()); // 创建人
        }
        if (tmAppAudit != null && StringUtils.isBlank(tmAppAudit.getIsRealtimeIssuing()))
            tmAppAudit.setIsRealtimeIssuing(Indicator.Y.name());
        applyInfoDto.setTmAppAudit(tmAppAudit);
        // 主卡联系人卡片信息
        if (tmAppPrimCardInfo == null) {
            tmAppPrimCardInfo = new TmAppPrimCardInfo();
        }
        tmAppPrimCardInfo.setAppNo(appNo);
        tmAppPrimCardInfo.setOrg(org);
        tmAppPrimCardInfo.setCreateUser(tmAppMain.getCreateUser());
        tmAppPrimCardInfo.setCreateDate(new Date());

        // 附件信息
        TmAppPrimAnnexEvi tmAppPrimAnnexEvi = applyInfoDto.getTmAppPrimAnnexEvi();
        if (tmAppPrimAnnexEvi != null) {
            tmAppPrimAnnexEvi.setAppNo(appNo);
            tmAppPrimAnnexEvi.setOrg(org);
            tmAppPrimAnnexEvi.setCreateUser(tmAppMain.getCreateUser());
            tmAppPrimAnnexEvi.setCreateDate(new Date());
            applyInfoDto.setTmAppPrimAnnexEvi(tmAppPrimAnnexEvi);
        }
        List<TmAppCustInfo> custs = applyInfoDto.getTmAppCustInfoList();
        if (CollectionUtils.sizeGtZero(custs)) {
            for (TmAppCustInfo cust : custs) {
                cust.setAppNo(appNo);
                cust.setOrg(org);
                cust.setCreateUser(tmAppMain.getCreateUser());
                cust.setCreateDate(new Date());
            }
        }

        // ************************
        final Map<String, Serializable> vars = new HashMap<String, Serializable>();
        applyInfoDto.setTmAppNodeInfoRecordMap(new HashMap<String, Serializable>());
        // 设置历史信息
        List<TmAppHistory> tmAppHistoryList = new ArrayList<TmAppHistory>();
        TmAppHistory tmAppHistory = new TmAppHistory();
        tmAppHistory = AppCommonUtil.insertApplyHist(appNo, operId == null ? AppConstant.SYS_AUTO : operId,
                RtfState.valueOf(tmAppMain.getRtfState()), null, tmAppMain.getRemark());
        tmAppHistory.setName(tmAppMain.getName());
        tmAppHistory.setIdType(tmAppMain.getIdType());
        tmAppHistory.setIdNo(tmAppMain.getIdNo());
        tmAppHistoryList.add(tmAppHistory);
        applyInfoDto.setTmAppHistoryList(tmAppHistoryList);

        // 设置公共节点信息
        ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
        applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, tmAppMain);
        vars.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
        if (applyInfoDto.getTmAppNodeInfoRecordMap() == null) {
            applyInfoDto.setTmAppNodeInfoRecordMap(new HashMap<String, Serializable>());
        }
        applyInfoDto.getTmAppNodeInfoRecordMap().put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
        try {
            logger.info("保存申请件数据开始----申请编号：" + appNo);
            applyInfoDto.setIpadApplyFalg(Indicator.Y.name());// pad进件标志
            applyInputService.saveApplyInput(applyInfoDto);
            logger.info("保存申请件数据结束----申请编号：" + appNo);
        } catch (ProcessException e) {
            // 如果业务数据提交失败，要把申请状态置为预录入状态（A05），且不发起工作流
            logger.error("申请提交失败-appNo[" + appNo + "]", e);
            t1005Process.exceptionProcess(applyInfoDto, operId, appNo);
            throw new ProcessException("保存客户申请资料失败-AppNo[" + appNo + "]"
                    + e.getMessage());
        } catch (Exception e) {
            // 如果业务数据提交失败，要把申请状态置为预录入状态（A05），且不发起工作流
            logger.error("申请提交失败-appNo[" + appNo + "]", e);
            t1005Process.exceptionProcess(applyInfoDto, operId, appNo);
            throw new ProcessException("保存客户申请资料失败-AppNo" + appNo);
        }
        //多卡同申时子类申请件同步发起工作流
        if (tmAppMain != null && !StringUtils.equals(tmAppMain.getRtfState(), "M05")
                && !StringUtils.equals(tmAppMain.getRtfState(), "A20")) {
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
                    } catch (ProcessException e) {
                        logger.error("申请审批流程处理失败-appNo[" + appNo + "], tokenId[" + start + "]", e);
//						throw new ProcessException("申请审批流程处理失败-" + e.getMessage());
                    }
        }
    }

}
