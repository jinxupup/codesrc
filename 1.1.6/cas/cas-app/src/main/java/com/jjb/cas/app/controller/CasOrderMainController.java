package com.jjb.cas.app.controller;


import com.jjb.ecms.biz.service.query.TmAppOrderMainService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.infrastructure.TmAppOrderMain;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/cas_orderMain")
public class CasOrderMainController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(CasOrderMainController.class);

    @Autowired
    private TmAppOrderMainService tmAppOrderMainService;
    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;

    /**
     * 多卡同申记录-条件查询--打开页面
     *
     * @param appNo
     * @return
     */
    @RequestMapping("/openOrderMainTerms")
    public String openAuditNew() {
        try {
            logger.info("====>打开多卡同申记录条件搜索页面");
            return "/casTask/apply/commonDialog/casOrderMainPageTerms.ftl";
        } catch (Exception e) {
            logger.error("打开多卡同申记录条件搜索页面异常", e);
            return null;
        }
    }

    /**
     * 多卡同申记录-条件查询--条件搜索
     *
     * @param appNo
     * @return
     */
    @ResponseBody
    @RequestMapping("/orderMainQueryForm")
    public Page<TmAppOrderMain> AuditQueryForm() {
        Long start = System.currentTimeMillis();
        logger.info("====>开始执行审计历史-条件查询" + LogPrintUtils.printCommonStartLog(start, null));
        Page<TmAppOrderMain> page = getPage(TmAppOrderMain.class);
//        bizAuditHistoryUtils.saveAuditHistoryByOperatorId("多卡同申记录查询--条件搜索: "+page.getQuery());
        logger.info("====>结束执行审计历史-条件查询" + LogPrintUtils.printCommonEndLog(start, null));
        page = tmAppOrderMainService.queryTmAppOrderMainPage(page);
        return page;
    }

    /**
     * 多卡同申记录-修改定时器状态
     * @param appNo
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateOrderMainTerms")
    public Json updateAuditNew() {
        Json j = Json.newSuccess();
        String appNo = getPara("appNo");
        String timerState = getPara("timerState");
        //保存审计历史
        if (StringUtils.equals(timerState,"P")) {
            bizAuditHistoryUtils.saveAuditHistory(appNo,"多卡同审-修改定时器状态-转待处理");
        } else if (StringUtils.equals(timerState,"W")) {
            bizAuditHistoryUtils.saveAuditHistory(appNo,"多卡同审-修改定时器状态-转待决策");
        }
        try {
            logger.info("====>修改定时器状态开始");
            tmAppOrderMainService.updateOrderMainTimerState(appNo, timerState);
            logger.info("====>修改定时器状态结束");
        } catch (Exception e) {
            logger.error("修改定时器状态异常", e);
            j.setFail(e.getMessage());
        }
        return j;
    }

}
