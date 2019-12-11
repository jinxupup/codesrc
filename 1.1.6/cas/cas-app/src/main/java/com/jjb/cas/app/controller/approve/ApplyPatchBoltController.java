package com.jjb.cas.app.controller.approve;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.cas.app.controller.utils.CasAppUtils;
import com.jjb.ecms.biz.service.approve.ApplyFinalAuditService;
import com.jjb.ecms.biz.service.approve.ApplyPatchBoltService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 补件操作controller 
 * @author hn
 * @date 2016-9-23 14:40:26
 */

@Controller
@RequestMapping("/cas_applyPatchBolt")
public class ApplyPatchBoltController extends BaseController{

	@Autowired
	private ApplyFinalAuditService finalAuditService;
	
	@Autowired
	private ApplyPatchBoltService applyPatchBoltService;

	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;
	@Autowired
	private CasAppUtils casAppUtils;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	/**
	 * 补件提交
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/patchBoltSubmit")
	public Json patchBoltSubmit(){
		Json j = Json.newSuccess();
		String status = AppConstant.SUBMIT;
		Query patchBoltInfo = getQuery();
		String appNo = StringUtils.valueOf(patchBoltInfo.get("appNo"));//申请件编号
		bizAuditHistoryUtils.saveAuditHistory(appNo,"补件提交");//保存审计历史
		//申请件标签
		String[] appFlags = getParas("flagApp");
		casAppUtils.setTmAppFlagInfo(appNo, appFlags);
		try{
			applyPatchBoltService.submitPatchBoltInfo(patchBoltInfo, status);
		}catch(Exception e){
			logger.error("补件提交异常", e);
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	/**
	 * 补件保存
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/patchBoltSave")
	public Json patchBoltSave(){

		Json j = Json.newSuccess();
		String status = AppConstant.SAVE;
		Query patchBoltInfo = getQuery();
		String appNo = StringUtils.valueOf(patchBoltInfo.get("appNo"));//申请件编号
		bizAuditHistoryUtils.saveAuditHistory(appNo,"补件保存");//保存审计历史
		//申请件标签
		String[] appFlags = getParas("flagApp");
		casAppUtils.setTmAppFlagInfo(appNo, appFlags);
		try{
			applyPatchBoltService.submitPatchBoltInfo(patchBoltInfo, status);
		}catch(Exception e){
			logger.error("补件保存操作异常", e);
			j.setFail(e.getMessage());
		}
		
		return j;
	}
	/**
	 * 确认补件超时
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/patchBoltOverTime")
	public Json patchBoltOverTime(){ 
		Json j = Json.newSuccess();
		String status = AppConstant.OVER_TIME;
		Query patchBoltInfo = getQuery();
		String appNo = StringUtils.valueOf(patchBoltInfo.get("appNo"));//申请件编号
		bizAuditHistoryUtils.saveAuditHistory(appNo,"确认补件超时确认");//保存审计历史
		//申请件标签
		String[] appFlags = getParas("flagApp");
		casAppUtils.setTmAppFlagInfo(appNo, appFlags);
		try{
			applyPatchBoltService.submitPatchBoltInfo(patchBoltInfo, status);
		}catch(Exception e){
			logger.error("补件超时操作异常", e);
			j.setFail(e.getMessage());
		}
		
		return j;
	}
		
}
