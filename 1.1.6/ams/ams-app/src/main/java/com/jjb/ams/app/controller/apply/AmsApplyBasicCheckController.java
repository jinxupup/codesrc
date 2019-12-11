package com.jjb.ams.app.controller.apply;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.bus.ApplyResultType;
import com.jjb.ams.app.controller.AmsDataController;
import com.jjb.ecms.biz.service.approve.ApplyCheckAndTelService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 人工节点-初审调查
 * @author JYData-R&D-Big Star
 * @date 2016年9月12日 下午3:05:47
 * @version V1.0
 */
@Controller
@RequestMapping("/ams_applyBasicCheck")
public class AmsApplyBasicCheckController extends AmsDataController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ApplyCheckAndTelService applyCheckAndTelService;
	
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
		try {
			query = getBasicQuery();
			TmAppMain tmAppMain = getBean(TmAppMain.class);
			appNo = tmAppMain.getAppNo();// 申请件编号
			query.put("appNo", appNo);
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
		} catch (Exception e) {
			logger.error("设置各种初审数据"+ LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null) + "异常", e);
			json.setFail(e.getMessage());
			return json;
		}
		
		try {
			ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = setTelCheckBisicData(true);
			applyCheckAndTelService.saveOrSubmitDataService(false, "C", query, applyNodeTelCheckBisicData);
			
			json.setMsg("操作成功！");
		} catch (Exception e) {
			logger.error("提交各种初审数据"+LogPrintUtils.printAppNoLog(appNo, System.currentTimeMillis(),null)+"异常", e);
			json.setFail(e.getMessage());
		}
		
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
	
	/** 
	 * 获取电话调查记录信息
	 */
	/**
	@ResponseBody
	@RequestMapping("/telcheckInfoList")
	public Page<ApplyTelInquiryRecordItem> telcheckInfoList(String appNo){
		if(StringUtils.isBlank(appNo)){
			throw new ProcessException("初审页面电话记录appNo为空");
		}
		Page<ApplyTelInquiryRecordItem> page = getPage(ApplyTelInquiryRecordItem.class);
		List<ApplyTelInquiryRecordItem> applyTelInquiryRecordItem = new ArrayList<ApplyTelInquiryRecordItem>();
		ApplyInfoDto applyInfoDto = applyQueryService.getNodeInfoByAppNo(appNo);
		Map<String,Serializable> tmAppNodeInfoRecordMap = applyInfoDto.getTmAppNodeInfoRecordMap();
		if(tmAppNodeInfoRecordMap.containsKey(AppCommonConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA)){
			ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = (ApplyNodeTelCheckBisicData) tmAppNodeInfoRecordMap.get(AppCommonConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA);
			if (applyNodeTelCheckBisicData != null) {
				applyTelInquiryRecordItem = applyNodeTelCheckBisicData.getTelInquiryRecordItemList();
			}
		}
		page.setRows(applyTelInquiryRecordItem);
		
		return page;
	}
	*/
	
	/**
	 * 保存初审电话调查记录信息
	 */
	/**
	@ResponseBody
	@RequestMapping("/saveTelcheckInfo")
	public Json saveTelcheckInfo(String appNo){
		if(StringUtils.isBlank(appNo)){
			throw new ProcessException("保存初审电话记录appNo为空");
		}
		Json json = Json.newSuccess();
		try {
			//申请电话调查记录
			List<ApplyTelInquiryRecordItem> applyTelInquiryRecordDtoList = getList(ApplyTelInquiryRecordItem.class, "applyTelInquiryRecordDto");
			if(CollectionUtils.isNotEmpty(applyTelInquiryRecordDtoList)){
				for (ApplyTelInquiryRecordItem item : applyTelInquiryRecordDtoList) {
					if(item==null){
						continue;
					}
					if(StringUtils.isBlank(item.getTelType()) || StringUtils.isBlank(item.getTelResult())){
						applyTelInquiryRecordDtoList.remove(item);
						if(applyTelInquiryRecordDtoList==null || applyTelInquiryRecordDtoList.size()==0){
							break;
						}
					}
				}
			}
			ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = new ApplyNodeTelCheckBisicData();
			applyNodeTelCheckBisicData.setTelInquiryRecordItemList(applyTelInquiryRecordDtoList);
			Map<String, Serializable> data = new HashMap<String, Serializable>();
			data.put("appNo", appNo);
			//保存初审调查信息节点对象
			data.put(AppCommonConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA, applyNodeTelCheckBisicData);
			nodeObjectUtil.insertAllNodeRec(data,appNo);
			json.setS(true);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存申请件["+appNo+"]初审电话调查记录信息失败");
			json.setFail(e.getMessage());
			json.setS(false);
		}		
		
		return json;
	}
	
	*/
}
