package com.jjb.cas.biz.rule;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.bus.SmsMessageCategory;
import com.jjb.acl.facility.enums.sys.MsgType;
import com.jjb.ecms.biz.ext.push.CreditSysPushSupport;
import com.jjb.ecms.biz.ext.sms.SendSmsSupport;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @author JYData-R&D-Big Star
 * @version V1.0
 * @Description: 自动节点-失败申请
 * @date 2016年9月7日 下午2:05:49
 */
@Transactional(readOnly = false)
@Service("applyInfofailure")
public class ApplyInfofailure implements JavaDelegate {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private ApplyInputService applyInputService;
    @Autowired
    private SendSmsSupport sendSmsSupport;
    @Autowired
    CreditSysPushSupport creditSysPushSupport;

    @Override
    public void execute(DelegateExecution delegateexecution) throws Exception {

        long start = System.currentTimeMillis();
        String appNo = delegateexecution.getProcessBusinessKey();
        logger.info("自动节点-失败申请处理" + AppConstant.BEGINING + LogPrintUtils.printAppNoLog(appNo, start, null));

        String org = OrganizationContextHolder.getOrg();
        ApplyNodeCommonData applyNodeCommonData = (ApplyNodeCommonData) delegateexecution.getVariable(AppConstant.APPLY_NODE_COMMON_DATA);
        TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
        if (tmAppMain == null) {
            tmAppMain = new TmAppMain();//预防空指针
        }
        //此处判断是不是补件超时进来的
        if (tmAppMain.getRtfState() != null && (tmAppMain.getRtfState().equals(RtfState.G05.name())
                || tmAppMain.getRtfState().equals(RtfState.G15.name())
                || tmAppMain.getRtfState().equals(RtfState.G16.name())
                || tmAppMain.getRtfState().equals(RtfState.G03.name()))) {
            tmAppMain.setRefuseCode("OVERTIME-补件超时");
        }

        tmAppMain.setUpdateDate(new Date());
        tmAppMain.setUpdateUser(OrganizationContextHolder.getUserNo());
        if (StringUtils.isEmpty(org)) {
            org = tmAppMain.getOrg();
            OrganizationContextHolder.setOrg(org);
        }
        //插入拒绝短信给TmAppMsgSend
        sendSmsSupport.setSmsToDB(tmAppMain, SmsMessageCategory.ECMS02.name());
        tmAppMain.setRtfState(RtfState.M05.state);

        applyNodeCommonData.setRtfStateType(RtfState.M05.name());
        if(tmAppMain.getFileFlag() !=null && tmAppMain.getFileFlag().equals("N")) {
            tmAppMain.setFileFlag("R");
        }else {
            tmAppMain.setFileFlag("F");
        }
        applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, tmAppMain);
        delegateexecution.setVariable(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
        applyInputService.updateTmAppMain(tmAppMain);
        TmAppHistory history = AppCommonUtil.insertApplyHist(tmAppMain.getAppNo(), AppConstant.SYS_AUTO, RtfState.M05, tmAppMain.getRefuseCode(), null);
        //记录历史
        if (history != null) {
            history.setIdType(tmAppMain.getIdType());
            history.setIdNo(tmAppMain.getIdNo());
            history.setName(tmAppMain.getName());
            applyInputService.saveTmAppHistory(history);
        }
        //推送异步审批消息
        creditSysPushSupport.asynPushApplyInfo(appNo, MsgType.CreditLife.name());
        logger.info("自动节点-失败申请处理" + AppConstant.END + LogPrintUtils.printAppNoEndLog(appNo, start, null));
    }
}
