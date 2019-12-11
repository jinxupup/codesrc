package com.jjb.cas.app.controller.approve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.cas.app.controller.CasDataController;
import com.jjb.cas.app.controller.utils.CasAppUtils;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.approve.ApplyCheckAndTelService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @author BIG.LT.M
 * @version 1.0
 * @date 创建时间：2017年3月15日 下午2:47:47
 */
@Controller
@RequestMapping("/cas_telephone")
public class TelephoneController extends CasDataController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplyCheckAndTelService applyCheckAndTelService;
    @Autowired
    private ApplyInputService applyInputService;
    @Autowired
    private TmAppMainDao tmAppMainDao;
    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;
    @Autowired
    private CasAppUtils casAppUtils;

    /**
     * 电话调查提交
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/telCheckSubmit")
    public Json telCheckSubmit() {
        Json json = Json.newSuccess();
        Query query = null;
        String appNo = null;
        try {
            query = getBasicQuery();
            String[] refuseCodes = getParas("refuseCode");//拒绝原因
            if (refuseCodes == null) {
                String refuseCode = getPara("refuseCode");
                if (StringUtils.isNotEmpty(refuseCode)) {
                    refuseCodes = new String[]{refuseCode};
                }
            }
            query.put("refuseCodes", refuseCodes);
            query = setBasicData(query, "电话调查", "T", logger);
            appNo = StringUtils.valueOf(query.get("appNo"));
            bizAuditHistoryUtils.saveAuditHistory(appNo,"电调提交");//保存审计历史
            //申请件标签
            String[] appFlags = getParas("flagApp");
            casAppUtils.setTmAppFlagInfo(appNo, appFlags);
        } catch (Exception e) {
/*            logger.error("设置各种电调数据" + LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(), null) + "异常", e);
            json.setFail(e.getMessage());
            return json;*/
            logger.error("电调提交或保存时设置各种电调数据"+ LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null) + "异常", e);
            json.setFail(e.getMessage());
            /**
             * 电调提交保存或发起流程异常时的处理   保存tmapphistory
             */
            TmAppMain appMain=tmAppMainDao.getTmAppMainByAppNo(appNo);
            RtfState rst = AppCommonUtil.stringToEnum(RtfState.class,appMain.getRtfState(),null);
            TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo,
                    OrganizationContextHolder.getUserNo(), rst, "电调提交或保存:设置各种电调数据异常", "详情看日志");
            if(tmAppHistory != null){
                if(appMain != null){
                    tmAppHistory.setName(appMain.getName());
                    tmAppHistory.setIdNo(appMain.getIdNo());
                    tmAppHistory.setIdType(appMain.getIdType());
                }
                applyInputService.saveTmAppHistory(tmAppHistory);
            }
            return json;

        }
        try {
            ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = setTelCheckBisicData(true);
            applyCheckAndTelService.saveOrSubmitDataService(false, "T", query, applyNodeTelCheckBisicData);
            json.setMsg("操作成功！");
        } catch (Exception e) {
/*            logger.error("保存各种电调数据" + LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(), null) + "异常", e);
            json.setFail(e.getMessage());
            json.setS(false);*/
            logger.error("电调提交保存或发起流程"+ LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null) + "异常", e);
            json.setFail(e.getMessage());
            /**
             * 电调提交保存或发起流程异常时的处理   保存tmapphistory
             */
            TmAppMain appMain=tmAppMainDao.getTmAppMainByAppNo(appNo);
            RtfState rst = AppCommonUtil.stringToEnum(RtfState.class,appMain.getRtfState(),null);
            TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo,
                    OrganizationContextHolder.getUserNo(), rst, "电调:提交保存或发起流程异常", "详情看日志");
            if(tmAppHistory != null) {
                if (appMain != null) {
                    tmAppHistory.setName(appMain.getName());
                    tmAppHistory.setIdNo(appMain.getIdNo());
                    tmAppHistory.setIdType(appMain.getIdType());
                }
                applyInputService.saveTmAppHistory(tmAppHistory);
            }
        }

        return json;
    }

    /**
     * 电话调查保存
     *
     * @return
     */
    @ResponseBody
     
    @RequestMapping("/telCheckSave")
    public Json telCheckSave() {
        Json json = Json.newSuccess();
        Query query = null;
        String appNo = "";
        try {
            query = getBasicQuery();
            String[] refuseCodes = getParas("refuseCode");//拒绝原因
            if (refuseCodes == null) {
                String refuseCode = getPara("refuseCode");
                if (StringUtils.isNotEmpty(refuseCode)) {
                    refuseCodes = new String[]{refuseCode};
                }
            }
            query = setBasicData(query, "电话调查", "T", logger);
            if (query != null) {
                appNo = StringUtils.valueOf(query.get("appNo"));
            }
            bizAuditHistoryUtils.saveAuditHistory(appNo,"电调保存");//保存审计历史
            //申请件标签
            String[] appFlags = getParas("flagApp");
            casAppUtils.setTmAppFlagInfo(appNo, appFlags);
        } catch (Exception e) {
/*            logger.error("设置各种电调数据" + LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(), null) + "异常", e);
            json.setFail(e.getMessage());
            return json;*/
            logger.error("电调提交或保存时设置各种电调数据"+ LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null) + "异常", e);
            json.setFail(e.getMessage());
            /**
             * 电调提交保存或发起流程异常时的处理   保存tmapphistory
             */
            TmAppMain appMain=tmAppMainDao.getTmAppMainByAppNo(appNo);
            RtfState rst = AppCommonUtil.stringToEnum(RtfState.class,appMain.getRtfState(),null);
            TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo,
                    OrganizationContextHolder.getUserNo(), rst, "电调提交或保存:设置各种电调数据异常", "详情看日志");
            if(tmAppHistory != null){
                if(appMain != null){
                    tmAppHistory.setName(appMain.getName());
                    tmAppHistory.setIdNo(appMain.getIdNo());
                    tmAppHistory.setIdType(appMain.getIdType());
                }
                applyInputService.saveTmAppHistory(tmAppHistory);
            }
            return json;

        }
        try {
            ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = setTelCheckBisicData(false);
            applyCheckAndTelService.saveOrSubmitDataService(true, "T", query, applyNodeTelCheckBisicData);
            json.setMsg("操作成功！");
        } catch (Exception e) {
/*            logger.error("保存各种电调数据" + LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(), null) + "异常", e);
            json.setFail(e.getMessage());
            json.setS(false);*/
            logger.error("电调提交保存或发起流程"+ LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null) + "异常", e);
            json.setFail(e.getMessage());
            /**
             * 电调提交保存或发起流程异常时的处理   保存tmapphistory
             */
            TmAppMain appMain=tmAppMainDao.getTmAppMainByAppNo(appNo);
            RtfState rst = AppCommonUtil.stringToEnum(RtfState.class,appMain.getRtfState(),null);
            TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo,
                    OrganizationContextHolder.getUserNo(), rst, "电调:提交保存或发起流程异常", "详情看日志");
            if(tmAppHistory != null) {
                if (appMain != null) {
                    tmAppHistory.setName(appMain.getName());
                    tmAppHistory.setIdNo(appMain.getIdNo());
                    tmAppHistory.setIdType(appMain.getIdType());
                }
                applyInputService.saveTmAppHistory(tmAppHistory);
            }
        }
        return json;
    }
}
