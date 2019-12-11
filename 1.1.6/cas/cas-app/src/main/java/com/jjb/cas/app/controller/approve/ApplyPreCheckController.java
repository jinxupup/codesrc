package com.jjb.cas.app.controller.approve;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.cas.app.controller.CasDataController;
import com.jjb.cas.app.controller.utils.CasAppUtils;
import com.jjb.ecms.biz.dao.apply.TmAppOrderMainDao;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.approve.ApplyCheckAndTelService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @author JYData-R&D-Big Star
 * @version V1.0
 * @Description: 人工节点-预审
 * @date 2016年9月12日 下午3:05:47
 */
@Controller
@RequestMapping("/cas_applyPreCheck")
public class ApplyPreCheckController extends CasDataController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplyCheckAndTelService applyCheckAndTelService;
    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private ApplyInputService applyInputService;
    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;
    @Autowired
    private TmAppOrderMainDao tmAppOrderMainDao;
    @Autowired
    private CasAppUtils casAppUtils;

    /**
     * 预审审调查提交
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/preCheckSubmit")
    public Json preCheckSubmit() {
        Json json = Json.newSuccess();
        Query query = null;
        String appNo = null;
        String basicCheckResult = null;
        logger.info("预审开始提交"+appNo);
        try {
            query = getQuery();
            /*
			 * TmAppMain tmAppMain = getBean(TmAppMain.class); appNo =
			 * tmAppMain.getAppNo();// 申请件编号 query.put("appNo", appNo);
			 */
            appNo = StringUtils.valueOf(query.get("appNo"));
        } catch (Exception e) {
            logger.error(
                    "设置各种预审数据"
                            + LogPrintUtils.printAppNoLog(appNo,
                            System.currentTimeMillis(), null) + "异常", e);
            json.setFail(e.getMessage());
            return json;
        }
        try {
            TmAppPrimCardInfo tmAppPrimCardInfo =applyQueryService.getTmAppPrimCardInfoByAppNo(appNo);
            String[] SpreaderModes = getParas("SpreaderMode");//三亲核实
            if(SpreaderModes == null){
                String SpreaderMode = getPara("SpreaderMode");
                if(StringUtils.isNotEmpty(SpreaderMode)){
                    SpreaderModes = new String[]{SpreaderMode};
                }
            }
            //保存申请件标签
            String[] appFlags = getParas("flagApp[]");//申请件标签
            casAppUtils.setTmAppFlagInfo(appNo, appFlags);
            tmAppPrimCardInfo.setSpreaderMode(commonService.arrayToString(SpreaderModes,","));
            String branch = getPara("branch");
            tmAppPrimCardInfo.setSpreaderBranchThree(StringUtils.setValue(branch, tmAppPrimCardInfo.getSpreaderBranchThree()));
            //预审操作人员的相关信息
            tmAppPrimCardInfo.setPreNo(OrganizationContextHolder.getUserNo());
            tmAppPrimCardInfo.setPreName(OrganizationContextHolder.getUserName());
            applyCheckAndTelService.preSubmitDataService("P", query, tmAppPrimCardInfo);

            TmAppMain main=applyQueryService.getTmAppMainByAppNo(appNo);

            // 多卡同申预审通过时：修改定时任务状态
            if (main!=null&&StringUtils.isNotBlank(appNo)){
                if (StringUtils.equals(main.getTaskNum(), "0")) {
                    logger.info("多卡同申 TmAppOrderMain修改状态开始");
                    TmAppOrderMain tmAppOrderMain = new TmAppOrderMain();
                    tmAppOrderMain.setAppNo(appNo);
                    TmAppOrderMain orderMain = tmAppOrderMainDao.queryForOne(tmAppOrderMain);
                    if (orderMain != null) {
                        orderMain.setTimerState("P");
                        orderMain.setUpdateDate(new Date());
                        orderMain.setJpaVersion(orderMain.getJpaVersion() + 1);
                        tmAppOrderMainDao.updateNotNullable(orderMain);
                        logger.info("多卡同申 TmAppOrderMain修改状态结束");
                    }
                }
                /*tmAppOrderMainDao.updateTmAppOrderMainToTimerState(main.getTaskNum(), appNo);*/
            }
        } catch (Exception e) {
            logger.error("提交各种预审数据" + LogPrintUtils.printAppNoLog(appNo,null, null) + "异常", e);
            json.setS(false);
            json.setFail(e.getMessage());
        }
        bizAuditHistoryUtils.saveAuditHistory(appNo,"预审调查提交");//保存审计历史
        return json;
    }

    // 预审删除
    @ResponseBody
     
    @RequestMapping("/delete")
    public Json delete() {
        Long tokenId = System.currentTimeMillis();
        Json json = Json.newSuccess();
        Query query = null;
        String appNo = null;
        String basicCheckResult = null;
        try {
            query = getQuery();
			/*
			 * TmAppMain tmAppMain = getBean(TmAppMain.class); appNo =
			 * tmAppMain.getAppNo();// 申请件编号
			 */
            appNo = StringUtils.valueOf(query.get("appNo"));
            logger.info("删除预审件========>"
                    + LogPrintUtils.printAppNoLog(appNo, tokenId, null));
            TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
            main.setRtfState(RtfState.A20.name());
            applyInputService.updateTmAppMain(main);
            TmAppHistory appHistory = AppCommonUtil.insertApplyHist(appNo,
                    OrganizationContextHolder.getUserNo(), RtfState.A20, "",
                    "[系统备注]预审件页面发起删除操作");
            applyInputService.saveTmAppHistory(appHistory);

        } catch (Exception e) {
            logger.error("删除预审件[" + appNo + "]失败", e);
            json.setFail("删除预审件[" + appNo + "]失败" + e.getMessage());
            json.setS(false);
        }

        logger.info("删除预审件========>"
                + LogPrintUtils.printAppNoEndLog(appNo, tokenId, null));
        bizAuditHistoryUtils.saveAuditHistory(appNo,"删除预审件");//保存审计历史
        return json;
    }

}
