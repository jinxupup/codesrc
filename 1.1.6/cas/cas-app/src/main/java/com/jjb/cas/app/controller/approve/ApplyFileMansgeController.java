package com.jjb.cas.app.controller.approve;

import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.exception.ProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.cas.app.controller.CasDataController;
import com.jjb.cas.app.controller.utils.CasAppUtils;
import com.jjb.ecms.biz.service.approve.ApplyCheckAndTelService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @author JYData-R&D-Big Star
 * @version V1.0
 * @Description: 人工节点-归档
 * @date 2016年9月12日 下午3:05:47
 */
@Controller
@RequestMapping("/cas_applyFileManage")
public class ApplyFileMansgeController extends CasDataController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplyCheckAndTelService applyCheckAndTelService;
    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;
    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private CasAppUtils casAppUtils;
    /**
     * 归档操作提交
     *
     * @return
     */
    @ResponseBody
     
    @RequestMapping("/fileManageSubmit")
    public Json fileManageSubmit() {
        Json json = Json.newSuccess();
        Query query = null;
        String appNo = null;
        try {
            query = getQuery();
            appNo = StringUtils.valueOf(query.get("appNo"));
            //申请件标签
            String[] appFlags = getParas("flagApp");//申请件标签
            casAppUtils.setTmAppFlagInfo(appNo, appFlags);

        } catch (Exception e) {
            logger.error(
                    "设置各种归档数据"
                            + LogPrintUtils.printAppNoLog(appNo,
                            null, null) + "异常", e);
            json.setFail(e.getMessage());
            return json;
        }
        try {
            applyCheckAndTelService.fileSubmitDataService("P", query);
        } catch (Exception e) {
            logger.error("提交各种归档数据" + LogPrintUtils.printAppNoLog(appNo,null, null) + "异常", e);
            json.setS(false);
            json.setFail(e.getMessage());
        }
        bizAuditHistoryUtils.saveAuditHistory(appNo,"归档操作提交");//保存审计历史
        return json;
    }
/**
 * 归档提交之前校验
 */
    @ResponseBody
    @RequestMapping("/beforeFileManageSubmit")
    public Json beforeFileManageSubmit(){
        Query query = null;
        String appNo = null;
        Json json=Json.newSuccess();
        try {
            query = getQuery();
            appNo = StringUtils.valueOf(query.get("appNo"));
            TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
            if (StringUtils.isNotEmpty(tmAppMain) && StringUtils.isNotBlank(tmAppMain.getTaskNum())
                    && !StringUtils.equals(tmAppMain.getTaskNum(), "0")) {
                TmAppMain primaryMain = applyQueryService.getTmAppMainByAppNo(tmAppMain.getTaskNum());
                if (StringUtils.isNotEmpty(primaryMain) && StringUtils.isNotBlank(primaryMain.getRtfState())) {
                    if (!StringUtils.equals(primaryMain.getRtfState(), "P05")
                            || !StringUtils.equals(primaryMain.getRtfState(), "Q05")
                            || !StringUtils.equals(primaryMain.getRtfState(), "M05")
                            || !StringUtils.equals(primaryMain.getRtfState(), "A20")) {
                        logger.error("多卡同审主件["+primaryMain.getAppNo()+"]尚未处理完成");
                        throw new ProcessException("多卡同审主件["+primaryMain.getAppNo()+"]尚未处理完成");
                    }
                }
            }
        }catch (Exception e){
            logger.error("提交各种归档数据" + LogPrintUtils.printAppNoLog(appNo,null, null) + "异常", e);
            json.setS(false);
            json.setFail(e.getMessage());
        }
        return json;
    }


}
