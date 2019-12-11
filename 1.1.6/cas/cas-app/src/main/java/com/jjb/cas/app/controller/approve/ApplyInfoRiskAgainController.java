package com.jjb.cas.app.controller.approve;

import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.cas.app.controller.CasDataController;
import com.jjb.cas.biz.rule.risk.ApplyInfoRiskTo51ccUtils;
import com.jjb.cas.biz.rule.risk.ApplyInfoRiskToPartnerUtils;
import com.jjb.cas.biz.rule.risk.ApplyInfoRiskToPreCheckUtils;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.approve.ApplyCheckAndTelService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @ClassName ApplyInfoRiskAgainController
 * @Description TODO 风控重调:保存-调风控-刷新页面
 * @Author smh
 * Date 2018/12/12 15:33
 * Version 1.0
 */
@Controller
@RequestMapping("/cas_applyInfoRiskAgain")
public class ApplyInfoRiskAgainController extends CasDataController{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private    ApplyCheckAndTelService  applyCheckAndTelService;
    @Autowired
    private ApplyQueryService applyQueryService;
    @Autowired
    private ActivitiService activitiService;
    @Autowired
    private ApplyInfoRiskTo51ccUtils applyInfoRiskTo51ccUtils;
    @Autowired
    private ApplyInfoRiskToPreCheckUtils applyInfoRiskToPreCheckUtils;
    @Autowired
    private ApplyInfoRiskToPartnerUtils applyInfoRiskToPartnerUtils;
    @Autowired
    private ApplyBasicCheckController basicCheckController;
    @Autowired
    private TelephoneController telephoneController;
    @Autowired
    private BizAuditHistoryUtils bizAuditHistoryUtils;

    /**
     * 获取参数数据
     */
    protected Query getBasicQuery() {
        return getQuery();
    }

    /**
     * @Author smh
     * @Description TODO  初审,电调,终审 风控重调
     * @Date 2018/12/13 12:40
     */
    @ResponseBody
    @RequestMapping("/applyInfoRiskAgain")
    public Json basOrTelRiskAgain(){
        Json json = Json.newSuccess();
        TmAppMain mainPage = getBean(TmAppMain.class);
        String appNo=null;
        try {
        if(mainPage==null) {
            json.setFail("没有获取到有效的参数");
            json.setS(false);
            throw new ProcessException("没有获取到有效的参数");
        }
        appNo=mainPage.getAppNo();
        if (StringUtils.isEmpty(appNo)) {
            json.setFail("风控重调时的appNo为空");
            json.setS(false);
            throw new ProcessException("风控重调时的appNo为空！");
        }
        TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
        if(main==null) {
            json.setFail("发起决策调查失败，无效的申请件["+appNo+"]信息");
            json.setS(false);
            throw new ProcessException("发起决策调查失败，无效的申请件["+appNo+"]信息");
        }
        Task task = null;
        String taskId = "";
        task = activitiService.getTaskId(appNo);
        if (task==null) {
            json.setFail("风控重调时流程异常");
            json.setS(false);
            throw new ProcessException("风控重调时流程异常！");
        }
        else{
            taskId = task.getId();
            if (StringUtils.isEmpty(taskId)) {
                json.setFail("风控重调时流程异常");
                json.setS(false);
                throw new ProcessException("风控重调时流程异常！");
            }
        }
        String formKey = "";
        if(StringUtils.isNotBlank(taskId)){
            formKey = activitiService.handleTask(taskId,appNo);
            if (StringUtils.isEmpty(formKey)) {
                json.setFail("风控重调时流程异常");
                json.setS(false);
                throw new ProcessException("风控重调时流程异常！");
            }
        }
        if (StringUtils.equals(formKey,"application-check")){
            json=basicCheckController.basicCheckSave();
        }
        if (StringUtils.equals(formKey,"application-telephonesurvey")){
            json=telephoneController.telCheckSave();
        }

    		if(StringUtils.equals(main.getAppSource(), "51")) {//如果是51
    			applyInfoRiskTo51ccUtils.reCellRisk51ccExecute(appNo);
    		}else if(StringUtils.equals(main.getAppSource(), "02")) {//如果是02-微信公众号
    			applyInfoRiskToPreCheckUtils.reCellRiskPreExecute(appNo);
    		}else { //其他都走合伙人流程
    			applyInfoRiskToPartnerUtils.reCellRiskPartnerExecute(appNo);
    		}
		} catch (Exception e) {
			logger.error("重调风控异常", e);
			json.setS(false);
			json.setFail(e.getMessage());
			json.setMsg(e.getMessage());
		}
        bizAuditHistoryUtils.saveAuditHistory(appNo,"重调风控");//保存审计历史
        return json;
    }

    /**
     * @Author smh
     * @Description TODO  电调时风控重调 保存
     * @Date 2018/12/13 12:37
     */
    public Json telCheckSave(String appNo) {
        Json json = Json.newSuccess();
        Query query = null;
        try {
            query = getBasicQuery();
            String[] refuseCodes = getParas("refuseCode[]");//拒绝原因
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
        } catch (Exception e) {
            logger.error("设置各种电调数据" + LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(), null) + "异常", e);
            json.setFail(e.getMessage());
            json.setS(false);
            return json;
        }
        try {
            ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = setTelCheckBisicData(false);
            applyCheckAndTelService.saveOrSubmitDataService(true, "T", query, applyNodeTelCheckBisicData);
            json.setMsg("操作成功！");
        } catch (Exception e) {
            logger.error("保存各种电调数据" + LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(), null) + "异常", e);
            json.setFail(e.getMessage());
            json.setS(false);
        }
        return json;
    }

    /**
     * @Author smh
     * @Description TODO  初审时风控重调 保存
     * @Date 2018/12/13 11:38
     */
    public Json basicCheckSave(String appNo) {
        Json json = Json.newSuccess();
        Query query = null;
        try {
            query = getBasicQuery();
            String[] applyPatchBoltTypes = getParas("applyPatchBoltType[]");//补件
            if(applyPatchBoltTypes == null){
                String applyPatchBoltType = getPara("applyPatchBoltType");
                if(StringUtils.isNotEmpty(applyPatchBoltType)){
                    applyPatchBoltTypes = new String[]{applyPatchBoltType};
                }
            }
            query.put("applyPatchBoltTypes", applyPatchBoltTypes);

            String[] refuseCodes = getParas("refuseCode[]");//拒绝原因
            if(refuseCodes == null){
                String refuseCode = getPara("refuseCode");
                if(StringUtils.isNotEmpty(refuseCode)){
                    refuseCodes = new String[]{refuseCode};
                }
            }
            query.put("refuseCodes", refuseCodes);

            query = setBasicData(query,"初审调查","C",logger);
            if(query!=null){
                appNo = StringUtils.valueOf(query.get("appNo"));
            }
        } catch (Exception e) {
            logger.error("设置各种初审数据"+ LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null) + "异常", e);
            json.setFail(e.getMessage());
            json.setS(false);
            return json;
        }
        try {
            ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = setTelCheckBisicData(false);
            applyCheckAndTelService.saveOrSubmitDataService(true, "C", query, applyNodeTelCheckBisicData);
            json.setMsg("操作成功！");
        } catch (Exception e) {
            logger.error("保存各种初审数据"+LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null)+"异常", e);
            json.setFail(e.getMessage());
            json.setS(false);
            return json;
        }
        return json;
    }
    
}
