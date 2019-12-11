package com.jjb.cas.app.controller;


import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;

import com.jjb.ecms.infrastructure.TmBizAudit;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/cas_bizAudit")
public class CasBizAuditController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;


    /**
     * 审计历史页面-条件查询--打开页面
     *
     * @param appNo
     * @return
     */
    @RequestMapping("/openAuditTerms")
    public String openAuditNew() {
        try {
            logger.info("====>打开审计历史条件搜索页面");
            return "/casTask/apply/commonDialog/casAuditHistoryPageTerms.ftl";
        } catch (Exception e) {
            logger.error("打开审计历史条件搜索页面异常", e);
            return null;
        }
    }

    /**
     * 审计历史-条件查询--条件搜索
     *
     * @param appNo
     * @return
     */
    @ResponseBody
    @RequestMapping("/AuditQueryForm")
    public Page<TmBizAudit> AuditQueryForm() {
        Long start = System.currentTimeMillis();
        logger.info("====>开始执行审计历史-条件查询" + LogPrintUtils.printCommonStartLog(start, null));
        Page<TmBizAudit> page = getPage(TmBizAudit.class);
        logger.info("====>结束执行审计历史-条件查询" + LogPrintUtils.printCommonEndLog(start, null));
        page = bizAuditHistoryUtils.findAuditHistoryNew(page);
        return page;
    }


}
