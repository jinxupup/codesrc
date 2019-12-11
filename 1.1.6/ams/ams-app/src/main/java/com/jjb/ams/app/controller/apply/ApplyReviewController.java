package com.jjb.ams.app.controller.apply;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.ams.app.controller.utils.AmsAppUtils;
import com.jjb.ecms.biz.dao.query.TmTaskListDao;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.approve.ApplyInputReviewService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.ApplyTaskListDto;
import com.jjb.ecms.facility.dto.AppplyCompareInfoForReviewDto;
import com.jjb.ecms.facility.dto.FieldPageDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;


/**
 * 录入复核
 * 
 * @author BIG.K.K
 *
 */
@Controller
@RequestMapping("/ams_review")
public class ApplyReviewController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ApplyInputReviewService applyInputReviewService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private AmsAppUtils applyInfoUtils;//申请件处理工具类
	@Autowired
	private TmTaskListDao taskListDao;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;
	@Autowired
	private AmsAppUtils amsAppUtils;

	/**
	 * 保存复核信息
	 */
	@ResponseBody
	 
	@RequestMapping("/saveApplyReviewInfo")
	public Json saveApplyReviewInfo() {
		
		Map<String,Map<String , String>> reviewMap = new HashMap<String,Map<String , String>>();
		Json json = Json.newSuccess();
		//获取申请件编号和申请类型
		TmAppMain tmAppMain = getBean(TmAppMain.class);
		String appNo = null;
		String appType = null;
		String productCd = null;
		if(tmAppMain != null){
			appNo = tmAppMain.getAppNo();
			appType = tmAppMain.getAppType();
			productCd = tmAppMain.getProductCd();
		}
		if(StringUtils.isEmpty(appNo) || StringUtils.isEmpty(appType) || StringUtils.isEmpty(productCd)){
			logger.error("复核保存操作时appNo|appType|productCd为空！appNo="+appNo+",appType="+appType+",productCd="+productCd);
		}
		//复核人信息
		TmAppPrimCardInfo tmAppPrimCardInfo = getBean(TmAppPrimCardInfo.class);
		tmAppPrimCardInfo.setAppNo(appNo);
		//复核人备注备忘
		TmAppMemo tmAppMemo=getBean(TmAppMemo.class,"tmAppMemoReview");
		tmAppMemo.setAppNo(appNo);
		//主卡附卡的复合字段的值
		Map<String , String> mainMap = new HashMap<String, String>();
		Map<String , String> attachMap_0 = new HashMap<String, String>();
		Map<String , String> attachMap_1 = new HashMap<String, String>();
		Map<String , String> attachMap_2 = new HashMap<String, String>();
		Map<String , String> commonMap = new HashMap<String, String>();
		//获得申请附卡的数量
		Integer attachNum = null;
		if(AppType.S.name().equals(appType)){
			attachNum = 0;
		}else {
			attachNum = getParaToInt("attachNum");//从5开始
			if(attachNum != null){//attachNum只能取值1、2、3
				attachNum = attachNum - 4;
			}
		}
        //申请件标签
        String[] appFlags = getParas("flagApp");
        amsAppUtils.setTmAppFlagInfo(appNo, appFlags);
		LinkedHashMap<String, TreeMap<String, FieldPageDto>> reviewFieldMap = applyInfoUtils.getReviewFields(productCd, appType, attachNum);
		if(reviewFieldMap != null && reviewFieldMap.size() > 0){
			for (Entry<String , TreeMap<String, FieldPageDto>> typeMap: reviewFieldMap.entrySet()) {
				if(StringUtils.isNotEmpty(typeMap.getKey())){
					if(typeMap.getKey().equals(AppConstant.MAIN_TAB)){//获得主卡复核项的值
						for (Entry<String , FieldPageDto> enty: typeMap.getValue().entrySet()) {
							mainMap.put(enty.getValue().getName(), getPara(enty.getValue().getName()));
						}
					}else if(typeMap.getKey().equals(AppConstant.bscSuppInd_S_1)){//获得附卡1复核项的值
						for (Entry<String , FieldPageDto> enty: typeMap.getValue().entrySet()) {
							attachMap_0.put(enty.getValue().getName(), getPara(enty.getValue().getName()));
						}
					}else if(typeMap.getKey().equals(AppConstant.bscSuppInd_S_2)){//获得附卡2复核项的值
						for (Entry<String , FieldPageDto> enty: typeMap.getValue().entrySet()) {
							attachMap_1.put(enty.getValue().getName(), getPara(enty.getValue().getName()));
						}
					}else if(typeMap.getKey().equals(AppConstant.bscSuppInd_S_3)){//获得附卡3复核项的值
						for (Entry<String , FieldPageDto> enty: typeMap.getValue().entrySet()) {
							attachMap_2.put(enty.getValue().getName(), getPara(enty.getValue().getName()));
						}
					}else if(typeMap.getKey().equals(AppConstant.COMMON_TAB)){//获得公共复核项的值
						for (Entry<String , FieldPageDto> enty: typeMap.getValue().entrySet()) {
							commonMap.put(enty.getValue().getName(), getPara(enty.getValue().getName()));
						}
					}
				}
			}
		}
		if(mainMap != null && mainMap.size() > 0){
			reviewMap.put(AppConstant.MAIN_TAB, mainMap);
		}
		if(attachMap_0 != null && attachMap_0.size() > 0){
			reviewMap.put(AppConstant.bscSuppInd_S_1, attachMap_0);
		}
		if(attachMap_1 != null && attachMap_1.size() > 0){
			reviewMap.put(AppConstant.bscSuppInd_S_2, attachMap_1);
		}
		if(attachMap_2 != null && attachMap_2.size() > 0){
			reviewMap.put(AppConstant.bscSuppInd_S_3, attachMap_2);
		}
		if(commonMap != null && commonMap.size() > 0){
			reviewMap.put(AppConstant.COMMON_TAB, commonMap);
		}

		try{
			applyInputReviewService.saveApplyReviewInfo(tmAppMain, tmAppPrimCardInfo, tmAppMemo, reviewMap);
			logger.info("保存复核信息结束==>申请件编号：[" + appNo + "]");
		}catch(Exception e){
			logger.error("保存复核信息"+LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null)+"异常", e);
			json.setFail(e.getMessage());
		}
		bizAuditHistoryUtils.saveAuditHistory(appNo, "保存复核信息");//记录审计历史
		return json;
	}

	/**
	 * 显示复核对比信息页面
	 * 
	 */
	@RequestMapping("/compareInfoPage")
	public String compareInfo(String appNo, String appType, String taskId){
			
		Map<String , List<AppplyCompareInfoForReviewDto>> resultsMap = applyInputReviewService.compareInfo(appNo, appType);
		LinkedHashMap<String , List<AppplyCompareInfoForReviewDto>> compareFieldsMap = new LinkedHashMap<String, List<AppplyCompareInfoForReviewDto>>();
		Integer diffNum = null;
		if(resultsMap != null && resultsMap.size() > 0){
			diffNum = 0;
			if(CollectionUtils.sizeGtZero(resultsMap.get(AppConstant.COMMON_TAB))){
				compareFieldsMap.put(AppConstant.COMMON_TAB, resultsMap.get(AppConstant.COMMON_TAB));
				diffNum = diffNum + resultsMap.get(AppConstant.COMMON_TAB).size();
			}
			if(CollectionUtils.sizeGtZero(resultsMap.get(AppConstant.MAIN_TAB))){
				compareFieldsMap.put(AppConstant.MAIN_TAB, resultsMap.get(AppConstant.MAIN_TAB));
				diffNum = resultsMap.get(AppConstant.MAIN_TAB).size()+diffNum;
			}
			if(CollectionUtils.sizeGtZero(resultsMap.get(AppConstant.bscSuppInd_S_1))){
				compareFieldsMap.put(AppConstant.bscSuppInd_S_1, resultsMap.get(AppConstant.bscSuppInd_S_1));
				diffNum = diffNum + resultsMap.get(AppConstant.bscSuppInd_S_1).size();
			}
			if(CollectionUtils.sizeGtZero(resultsMap.get(AppConstant.bscSuppInd_S_2))){
				compareFieldsMap.put(AppConstant.bscSuppInd_S_2, resultsMap.get(AppConstant.bscSuppInd_S_2));
				diffNum = diffNum + resultsMap.get(AppConstant.bscSuppInd_S_2).size();
			}
			if(CollectionUtils.sizeGtZero(resultsMap.get(AppConstant.bscSuppInd_S_3))){
				compareFieldsMap.put(AppConstant.bscSuppInd_S_3, resultsMap.get(AppConstant.bscSuppInd_S_3));
				diffNum = diffNum + resultsMap.get(AppConstant.bscSuppInd_S_3).size();
			}
			
			setAttr("compareFieldsMap", compareFieldsMap);
		}
		//返回录入与复核的复核项的值不一致的数目，diffNum默认为0
		if(diffNum == null){
			diffNum = 0;
		}
		setAttr("diffNum", diffNum);
//		
//		ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
//		TmAppMemo tmAppMemo = applyInfoDto.getTmAppMemoMapLast().get(AppCommonConstant.APP_REMARK+"-"+EcmsAuthority.REVIEW.toString());//获取复核备注信息
//		if(tmAppMemo != null){
//			setAttr("remark", tmAppMemo.getMemoInfo());
//		}
		setAttr("taskId", taskId);
		setAttr("appNo", appNo);
		setAttr("appType", appType);
		
		return "/amsTask/applyCC/applyReview/compareInfo.ftl";
	}

	/**
	 * 录入复核提交
	 * 
	 * @return
	 */
	@ResponseBody
	 
	@RequestMapping("/applyInputReviewSubmit")
	public Json applyInputReviewSubmit() {
		Json json = Json.newSuccess();
		// 获取参数
		String taskId = getPara("taskId");
		String appNo = getPara("appNo");
		String remark = getPara("remark");
		if(StringUtils.isEmpty(appNo)){
			json.setS(false);
			throw new ProcessException("录入复核提交操作的申请件编号appNo为空！");
		}
		if(StringUtils.isEmpty(taskId)){
			json.setS(false);
			throw new ProcessException("录入复核提交操作的任务编号taskId为空！");
		}
		logger.info("开始保存复核信息==>申请件编号：[" + appNo + "]任务taskId:[" + taskId + "]");
		
		TmAppMain appMain = applyQueryService.getTmAppMainByAppNo(appNo);
		appMain.setRtfState(RtfState.B10.toString());
		appMain.setRemark(remark);
		
		// 工作流信息保存在applyNodeCommonData
		logger.info("提交applyNodeCommonData==>申请件编号：[" + appNo + "]任务taskId:[" + taskId + "]");

		ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
		ApplyInfoDto applyInfoDto = applyQueryService.getNodeInfoByAppNo(appNo);
		if(applyInfoDto != null && applyInfoDto.getTmAppNodeInfoRecordMap() != null && applyInfoDto.getTmAppNodeInfoRecordMap().containsKey(AppConstant.APPLY_NODE_COMMON_DATA)){
			applyNodeCommonData = (ApplyNodeCommonData) applyInfoDto.getTmAppNodeInfoRecordMap().get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		//节点公共数据 赋值
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData,appMain);
		TmAppHistory tmAppHistory = new TmAppHistory();
		String operId = OrganizationContextHolder.getUserNo();
		tmAppHistory = AppCommonUtil.insertApplyHist(appNo, operId, RtfState.B10, null, remark);
		tmAppHistory.setName(appMain.getName());
		tmAppHistory.setIdType(appMain.getIdType());
		tmAppHistory.setIdNo(appMain.getIdNo());
		TmAppAudit tmAppAudit = applyQueryService.getTmAppAuditByAppNo(appNo);
		try{
			
			applyInputReviewService.applyInputReviewSubmit(taskId, appMain, 
					tmAppHistory, applyNodeCommonData,tmAppAudit);
			json.setS(true);
			json.setMsg("提交成功！");
			logger.info("提交复核信息结束==>申请件编号：[" + appNo + "]任务taskId:[" + taskId + "]");
		}catch(Exception e){
			logger.error("提交复核信息"+LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null)+"异常", e);
			json.setFail(e.getMessage());
			json.setMsg("提交出现异常！["+e.getMessage()+"]");
			json.setS(false);
		}
		bizAuditHistoryUtils.saveAuditHistory(appNo,"录入复核提交");//保存审计历史
		return json;
	}

	/**
	 * 退回操作
	 * 退回至原申请经办人
	 */
	@ResponseBody
	@RequestMapping("/returnBackApplyReviewInfo")
	public Json returnBackApplyReviewInfo() {
		// 获取参数appNo,taskId
		TmAppMain tmAppMain = getBean(TmAppMain.class);
		String appNo = tmAppMain.getAppNo();
		String taskId = getPara("taskId");
		//复核人信息
		TmAppPrimCardInfo tmAppPrimCardInfo = getBean(TmAppPrimCardInfo.class);
		tmAppPrimCardInfo.setAppNo(appNo);
		//复核人备注
		TmAppMemo tmAppMemo=getBean(TmAppMemo.class,"tmAppMemoReview");
		Map<String, Object> filter = new HashMap<String, Object>();
		filter.put("tmAppMain", tmAppMain);
		filter.put("appNo", appNo);
		filter.put("taskId", taskId);
		filter.put("tmAppMemo", tmAppMemo);
		logger.info("开始退回复核信息==>申请件编号：[" + appNo + "]任务taskId:[" + taskId + "]");
		Json json = Json.newSuccess();
		try{
		applyInputReviewService.returnBackApplyReviewInfo(appNo, taskId, tmAppMain.getAppType(), tmAppMemo);
		
		}catch(Exception e){
			logger.error("退回复核信息"+LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null)+"异常", e);;
			json.setFail(e.getMessage());
		}
		bizAuditHistoryUtils.saveAuditHistory(appNo,"退回复核信息");//保存审计历史
		return json;
	}

	/**
	 * 获取下一个申请件编号
	 * @param appNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getNextAppNo")
	public Json getNextAppNo(String appNo){
		Json json = Json.newSuccess();
		try {
			logger.info("开始查询下个案件:=====>");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("taskName", "applyinfo-review");//获取当前操作人复核节点
			map.put("userId", OrganizationContextHolder.getUserNo());
			List<ApplyTaskListDto> list = taskListDao.queryMyTask(map);
			for(ApplyTaskListDto dto : list){
				if(!dto.getAppNo().equals(appNo)){
					json.setCode(dto.getAppNo());
					break;
				}
			}
			if(StringUtils.isEmpty(json.getCode())){//没有任务件
				json.setS(false);
				json.setMsg("当前已无需要复核的案件");
			}
		} catch (Exception e) {
			logger.error("获取下一个复合件信息"+LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null)+"发生异常", e);
			json.setFail(e.getMessage());
			json.setMsg("获取下一个复合件信息出现异常！["+e.getMessage()+"]");
			json.setS(false);
		}
		
		return json;
	}
}
