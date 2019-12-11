package com.jjb.cas.app.controller.approve;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.nodeobject.ApplyTelInquiryRecordItem;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.bus.ApplyResultType;
import com.jjb.cas.app.controller.CasDataController;
import com.jjb.cas.app.controller.utils.CasAppUtils;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.approve.ApplyCheckAndTelService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 人工节点-初审调查
 * @author JYData-R&D-Big Star
 * @date 2016年9月12日 下午3:05:47
 * @version V1.0
 */
@Controller
@RequestMapping("/cas_applyBasicCheck")
public class ApplyBasicCheckController extends CasDataController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplyCheckAndTelService applyCheckAndTelService;
	@Autowired
	private TmAppMainDao tmAppMainDao;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;
	@Autowired
	private CasAppUtils casAppUtils;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	/**
	 * 初审调查提交
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/basicCheckSubmit")
	public Json basicCheckSubmit() {
		Json json = Json.newSuccess();
		Query query = null;
		String appNo = null;
		String basicCheckResult = null;
		query = getBasicQuery();
		TmAppMain tmAppMain = getBean(TmAppMain.class);
		appNo = tmAppMain.getAppNo();// 申请件编号
		query.put("appNo", appNo);
		try {
			if(query!=null){
				basicCheckResult = StringUtils.valueOf(query.get("basicCheckResult"));//审批结果
			}
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

			if(StringUtils.isNotBlank(basicCheckResult) && ApplyResultType.O.name().equals(basicCheckResult)){//补件操作
				if(applyPatchBoltTypes == null || applyPatchBoltTypes.length == 0){
					throw new ProcessException("请选择补件信息！");
				}
			}
			query = setBasicData(query,"初审调查","C",logger);
			//保存申请件标签
			String[] appFlags = getParas("flagApp[]");//申请件标签
			casAppUtils.setTmAppFlagInfo(appNo, appFlags);
		} catch (Exception e) {
			logger.error("初审提交或保存时设置各种初审数据"+ LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null) + "异常", e);
			json.setFail(e.getMessage());
			/**
			 * 初审、提交保存或发起流程异常时的处理   保存tmapphistory
			 */
			TmAppMain appMain=tmAppMainDao.getTmAppMainByAppNo(appNo);
			RtfState rst = AppCommonUtil.stringToEnum(RtfState.class,appMain.getRtfState(),null);
			TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo,
					OrganizationContextHolder.getUserNo(), rst, "初审提交或保存:设置各种初审数据异常", "详情看日志");
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
			applyCheckAndTelService.saveOrSubmitDataService(false, "C", query, applyNodeTelCheckBisicData);
			json.setMsg("操作成功！");
		} catch (Exception e) {
			/**
			 * 初审、提交保存或发起流程异常时的处理   保存tmapphistory
			 */
			TmAppMain appMain=tmAppMainDao.getTmAppMainByAppNo(appNo);
			RtfState rst = AppCommonUtil.stringToEnum(RtfState.class,appMain.getRtfState(),null);
			TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo,
					OrganizationContextHolder.getUserNo(), rst, "初审:提交保存或发起流程异常", "详情看日志");
			if(tmAppHistory != null){
				if(appMain != null){
					tmAppHistory.setName(appMain.getName());
					tmAppHistory.setIdNo(appMain.getIdNo());
					tmAppHistory.setIdType(appMain.getIdType());
				}
				applyInputService.saveTmAppHistory(tmAppHistory);
			}
			json.setS(false);
			logger.error("初审提交保存或发起流程"+LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null)+"异常", e);
			json.setFail(e.getMessage());
		}
		bizAuditHistoryUtils.saveAuditHistory(appNo, "初审调查提交");//审计历史保存

		return json;
	}

	/**
	 * 电调信息保存
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/basicCheckTelSave")
	public Json basicCheckTelSave(ApplyTelInquiryRecordItem applyTelInquiryRecordItem,String appNo) {
		Json json = Json.newSuccess();
		List<ApplyTelInquiryRecordItem> applyTelInquiryRecordDtoList = new ArrayList<ApplyTelInquiryRecordItem>();
		applyTelInquiryRecordDtoList.add(applyTelInquiryRecordItem);
		ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = null;
		if(CollectionUtils.sizeGtZero(applyTelInquiryRecordDtoList)){
			applyNodeTelCheckBisicData = new ApplyNodeTelCheckBisicData();
			applyNodeTelCheckBisicData.setTelInquiryRecordItemList(applyTelInquiryRecordDtoList);
		}
		Map<String, Serializable> data = new HashMap<String, Serializable>();
		if(applyNodeTelCheckBisicData != null){//若电调合并到初审
			data.put(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA, applyNodeTelCheckBisicData);
		}
		nodeObjectUtil.insertAllNodeRec(data,appNo);

		return json;
	}

	/**
	 * 初审调查保存
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/basicCheckSave")
	public Json basicCheckSave() {
		Json json = Json.newSuccess();
		Query query = null;
		String appNo = "";
		try {
			query = getBasicQuery();
			String[] applyPatchBoltTypes = getParas("applyPatchBoltType");//补件
			if(applyPatchBoltTypes == null){
				String applyPatchBoltType = getPara("applyPatchBoltType");
				if(StringUtils.isNotEmpty(applyPatchBoltType)){
					applyPatchBoltTypes = new String[]{applyPatchBoltType};
				}
			}
			query.put("applyPatchBoltTypes", applyPatchBoltTypes);

			String[] refuseCodes = getParas("refuseCode");//拒绝原因
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
			//保存申请件标签
			String[] appFlags = getParas("flagApp");//申请件标签
			casAppUtils.setTmAppFlagInfo(appNo, appFlags);
		} catch (Exception e) {
			logger.error("初审提交或保存时设置各种初审数据"+ LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null) + "异常", e);
			json.setFail(e.getMessage());
			/**
			 * 初审、提交保存或发起流程异常时的处理   保存tmapphistory
			 */
			TmAppMain appMain=tmAppMainDao.getTmAppMainByAppNo(appNo);
			RtfState rst = AppCommonUtil.stringToEnum(RtfState.class,appMain.getRtfState(),null);
			TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo,
					OrganizationContextHolder.getUserNo(), rst, "初审提交或保存:设置各种初审数据异常", "详情看日志");
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
			applyCheckAndTelService.saveOrSubmitDataService(true, "C", query, applyNodeTelCheckBisicData);
			json.setMsg("操作成功！");
		} catch (Exception e) {
			/**
			 * 初审、提交保存或发起流程异常时的处理   保存tmapphistory
			 */
			TmAppMain appMain=tmAppMainDao.getTmAppMainByAppNo(appNo);
			RtfState rst = AppCommonUtil.stringToEnum(RtfState.class,appMain.getRtfState(),null);
			TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo,
					OrganizationContextHolder.getUserNo(), rst, "初审:提交保存或发起流程异常", "详情看日志");
			if(tmAppHistory != null){
				if(appMain != null){
					tmAppHistory.setName(appMain.getName());
					tmAppHistory.setIdNo(appMain.getIdNo());
					tmAppHistory.setIdType(appMain.getIdType());
				}
				applyInputService.saveTmAppHistory(tmAppHistory);
			}
			json.setS(false);
			logger.error("初审提交保存或发起流程"+LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null)+"异常", e);
			json.setFail(e.getMessage());
		}
		bizAuditHistoryUtils.saveAuditHistory(appNo,"初审调查保存");//保存审计历史
		return json;
	}

}
