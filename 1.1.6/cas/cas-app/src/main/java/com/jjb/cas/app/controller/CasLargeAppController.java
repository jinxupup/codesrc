package com.jjb.cas.app.controller;


import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.infrastructure.TmLargeScaleStaging;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.web.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 大额审批-条件查询
 */
@Controller
@RequestMapping("/cas_LargeApp")
public class CasLargeAppController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private com.jjb.ecms.biz.service.scale.TmLargeScaleStagingService TmLargeScaleStagingService;

    /**
     * 大额审批-条件查询--打开页面
     *
     * @param
     * @return
     */
    @RequestMapping("/openLargeTerms")
    public String openAuditNew() {
        try {
            logger.info("====>打开大额审批条件搜索页面");
            return "/casTask/apply/commonDialog/casLargeAppPageTerms.ftl";
        } catch (Exception e) {
            logger.error("====>打开大额审批条件搜索页面异常", e);
            return null;
        }
    }

    /**
     * 大额审批-条件查询--条件搜索
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/largeQueryForm")
    public Page<TmLargeScaleStaging> AuditQueryForm() {
        Long start = System.currentTimeMillis();
        logger.info("====>开始执行大额审批-条件查询" + LogPrintUtils.printCommonStartLog(start, null));
        Page<TmLargeScaleStaging> page = getPage(TmLargeScaleStaging.class);
        logger.info("====>结束执行大额审批-条件查询" + LogPrintUtils.printCommonEndLog(start, null));
        page = TmLargeScaleStagingService.findLargeAppPageService(page);
        return page;
    }


}
