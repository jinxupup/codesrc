package com.jjb.cas.service.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.cas.service.util.CheckUtil;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppRfeDao;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.dto.PatchResultDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeInquiryData;
import com.jjb.ecms.facility.nodeobject.ApplyNodePatchBoltData;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppRfe;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.ecms.service.api.PatchResultService;
import com.jjb.ecms.service.constants.SysConstants;
import com.jjb.ecms.service.dto.T1001.T1001Patch;
import com.jjb.ecms.service.dto.T1001.T1001Req;
import com.jjb.ecms.service.dto.T1001.T1001Resp;
import com.jjb.ecms.util.IdentificationCodeUtil;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.DateUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 补件结果反馈接口
 * @author JYData-R&D-Big T.T
 * @date 2017年8月7日 上午10:15:07
 * @version V1.0
 */

@Service("patchRespServiceImpl")
public class PatchResultServiceImpl implements PatchResultService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TaskService taskService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private TmAppRfeDao tmAppRfeDao;
	@Autowired
	private ApplyQueryService applyQueryService;
    @Autowired
    private ApplyInputService applyInputService;	
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	
	@Autowired
	private AppCommonUtil appCommonUtil;
	
	@Override
	@Transactional
	public T1001Resp T1001(T1001Req req) throws ProcessException {
		
		appCommonUtil.setOrg(OrganizationContextHolder.getOrg());
		String org = OrganizationContextHolder.getOrg();
		
//		if(StringUtils.isBlank(req.getOrg()) || !req.getOrg().equals(org)){
//			throw new ProcessException(Constants.ERRQ003CODE,Constants.ERRQ003_MES);
//		}
		
		// 构建响应报文对象
		T1001Resp resp = new T1001Resp();
		
		if(StringUtils.isNotBlank(req.getOptType())){
			if(req.getOptType().equals("01")){//01-待补件列表查询(默认)
				logger.debug("补件信息查询开始...");
				
				boolean ifAppCodeAndOperatorIdIsBlank = StringUtils.isBlank(req.getAppCode()) && StringUtils.isBlank(req.getOperatorId());
				boolean ifIdTypeOrIdNoIsBlank = StringUtils.isBlank(req.getIdType()==null?"":req.getIdType().toString()) || StringUtils.isBlank(req.getIdNo());
				if(ifAppCodeAndOperatorIdIsBlank && ifIdTypeOrIdNoIsBlank){
					throw new ProcessException(SysConstants.ERRQ002_CODE,SysConstants.ERRQ002_MES);
				}
				checkReqIdTypeAndIdNo(req);
				Page<PatchResultDto> patchResultDtoList = selectPatchResultDtoPage(req, org);
				ArrayList<T1001Patch> T1001Patchs = setPatchBoltResultDtoListToT1001PatchList(org, patchResultDtoList, resp);
				setValueToResp(req, resp, T1001Patchs);   
				
				logger.debug("补件信息查询结束...");
			}else if(req.getOptType().equals("02")) {//02-补件提交
				logger.debug("补件结果反馈开始...");
				
				TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(req.getAppNo());
				boolean ifPatchBoltCompleted = StringUtils.isNotBlank(req.getIsOk()) && Indicator.Y.name().equals(req.getIsOk());
				if(ifPatchBoltCompleted){
					TmAppRfe tmAppRfe = getPbTypeByAppNo(req.getAppNo(), org);
					tmAppRfe.setPbSource(req.getPbSource());
					tmAppRfe.setIsOk(Indicator.Y.name());
					tmAppRfeDao.update(tmAppRfe);
					boltBujianComplete(req, tmAppMain);//发起流程
					resp.setPatchs(null);
				}else {					
					TmAppRfe tmAppRfe = getPbType(org, req, tmAppMain);
					if(StringUtils.isBlank(tmAppRfe.getPbType())){
						resp.setPatchs(null);
					}else {
						T1001Patch T1001Patch = passValueFromTmAppRfeToT1001Patch(tmAppMain, tmAppRfe);
						ArrayList<T1001Patch> T1001Patchs = new ArrayList<T1001Patch>();
						T1001Patchs.add(T1001Patch);
						resp.setIdNo(tmAppRfe.getIdNo());
						resp.setIdType(tmAppRfe.getIdType());
						setValueToResp(req, resp, T1001Patchs);
					}
				}
				
				logger.debug("补件结果反馈结束");
			}
		}
	
		return resp;
	}

	private void setValueToResp(T1001Req req, T1001Resp resp,ArrayList<T1001Patch> T1001Patchs) {
		try {
			resp.setCurPage(req.getCurPage());
		} catch (Exception e) {
			resp.setCurPage(1);
		}
		try {
			resp.setRowCnt(req.getRowCnt());
		} catch (Exception e) {
			resp.setRowCnt(10);
		}
		if (CollectionUtils.isNotEmpty(T1001Patchs)) {
			resp.setTotalCnt(T1001Patchs.size());
		} else {
			resp.setTotalCnt(0);
		}
		resp.setPatchs(T1001Patchs);
	}

	private ArrayList<T1001Patch> setPatchBoltResultDtoListToT1001PatchList(String org, Page<PatchResultDto> patchResultDtoPage, T1001Resp resp) {
		ArrayList<T1001Patch> T1001Patchs = null;
		if(CollectionUtils.sizeGtZero(patchResultDtoPage.getRows())){
			T1001Patchs = new ArrayList<T1001Patch>();
			for (PatchResultDto patch : patchResultDtoPage.getRows()) {
				TmAppRfe tmAppRfe = getPbTypeByAppNo(patch.getAppNo(), org);
				String pbType = tmAppRfe.getPbType();
				boolean ifPatchBoltHasCompleted = StringUtils.isNotBlank(tmAppRfe.getIsOk()) && Indicator.Y.name().equals(tmAppRfe.getIsOk());
				if(StringUtils.isBlank(pbType) || ifPatchBoltHasCompleted){
					continue;
				}else {
					T1001Patch T1001Patch = passValueFromPatchResultDtoToT1001Patch(patch, pbType);
					T1001Patchs.add(T1001Patch);
					resp.setIdNo(patch.getIdNo());
					resp.setIdType(patch.getIdType());
				}
			}					
		}
		return T1001Patchs;
	}

	private void checkReqIdTypeAndIdNo(T1001Req req) {
		boolean ifIdTypeNotBlank = StringUtils.isNotBlank(req.getIdType()==null?"":req.getIdType().toString());
		boolean ifIdNoNotBlank = StringUtils.isNotBlank(req.getIdNo());
		if(ifIdTypeNotBlank && ifIdNoNotBlank){
			if (req.getIdType() == "I") {
				if(!IdentificationCodeUtil.isIdentityCode(req.getIdNo())){
					throw new ProcessException(SysConstants.ERRQ001_CODE,SysConstants.ERRQ001_MES);
				}
			}
		}else {
			req.setIdType(null);
			req.setIdNo(null);
		}
	}

	private T1001Patch passValueFromTmAppRfeToT1001Patch(TmAppMain tmAppMain, TmAppRfe tmAppRfe) {
		
		T1001Patch T1001Patch = new T1001Patch();
		
		T1001Patch.setAppNo(tmAppRfe.getAppNo());				
		T1001Patch.setPbOutBatchDate(tmAppRfe.getPbOutBatchDate());
		T1001Patch.setPbStBatchDate(tmAppRfe.getPbStBatchDate());
		T1001Patch.setPbStartDate(tmAppRfe.getPbStartDate());
		T1001Patch.setPbTimeoutDate(tmAppRfe.getPbTimeoutDate());
		T1001Patch.setPbType(tmAppRfe.getPbType());
		String applyDate = tmAppRfe.getPbStartTime();
		
		if(StringUtils.isNotBlank(applyDate)){
			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(applyDate.substring(0,10));
			stringBuffer.append(" ");
			stringBuffer.append(applyDate.substring(11));
			try {
				T1001Patch.setApplyDate(DateUtils.stringToDate(stringBuffer.toString(), DateUtils.FULL_YMD_LINE));
			} catch (ParseException e) {
				throw new ProcessException("时间转换异常"+e.getMessage());
			}
		}
		
		if(tmAppMain != null){
			T1001Patch.setAppCode(tmAppMain.getImageNum());
			T1001Patch.setRtfState(tmAppMain.getRtfState());
			T1001Patch.setApplyDate(tmAppMain.getUpdateDate());
			T1001Patch.setOperatorId(tmAppMain.getTaskOwner());
			
			TmProduct product = cacheContext.getProduct(tmAppMain.getProductCd());
			if(product == null){
				throw new ProcessException(SysConstants.ERRS006_CODE,SysConstants.ERRS006_MES);			
			}else {
				T1001Patch.setProductName(product.getProductCd() + "|" + CheckUtil.objToString(product.getProductDesc()));
			}
		}
		
		return T1001Patch;
	}

	private T1001Patch passValueFromPatchResultDtoToT1001Patch(PatchResultDto patch, String pbType) {
		T1001Patch t1001Patch = new T1001Patch();
		t1001Patch.setAppCode(patch.getAppCode());
		t1001Patch.setAppNo(patch.getAppNo());
		String applyDate = patch.getApplyDate();
		if(StringUtils.isNotBlank(applyDate)){
			applyDate = applyDate.replaceAll("T", " ");// 去掉日期中的“T”
			try {
				t1001Patch.setApplyDate(DateUtils.stringToDate(applyDate, DateUtils.FULL_YMD_LINE));
			} catch (ParseException e) {
				throw new ProcessException("时间转换异常"+e.getMessage());
			}
		}
		
		t1001Patch.setName(patch.getName());
		t1001Patch.setOperatorId(patch.getOperatorId());
		t1001Patch.setPbOutBatchDate(patch.getPbOutBatchDate());
		t1001Patch.setPbStBatchDate(patch.getPbStBatchDate());
		t1001Patch.setPbStartDate(patch.getPbStartDate());
		t1001Patch.setPbTimeoutDate(patch.getPbTimeoutDate());
		String pbCodeValue = appCommonUtil.getPatchBoltTypeEnumKeyByValue(pbType);
		t1001Patch.setPbType(pbCodeValue);
		t1001Patch.setRtfState(patch.getRtfState());
		TmProduct product = cacheContext.getProduct(patch.getProductCd());
		if(product == null){
			throw new ProcessException(SysConstants.ERRS006_CODE,SysConstants.ERRS006_MES);
		}else {
			t1001Patch.setProductName(patch.getProductCd() + "|" + CheckUtil.objToString(product.getProductDesc()));
		}
		return t1001Patch;
	}

	private Page<PatchResultDto> selectPatchResultDtoPage(T1001Req req, String org) {
		PatchResultDto patchResultDto = new PatchResultDto();
		setCurPageAndRowCntToPatchResult(req, patchResultDto);
		patchResultDto.setAppCode(StringUtils.isBlank(req.getAppCode())?null:req.getAppCode());
		patchResultDto.setOrg(org);
		patchResultDto.setIdType(req.getIdType()==null?null:req.getIdType().toString());
		patchResultDto.setIdNo(StringUtils.isBlank(req.getIdNo())?null:req.getIdNo());
		if(StringUtils.isNotBlank(req.getAppCode()) || StringUtils.isNotBlank(req.getAppCode())){
			patchResultDto.setOperatorId(StringUtils.isBlank(req.getOperatorId())?null:req.getOperatorId());
		}
//		Page<PatchResultDto> patchResultDtoPage = patchResultDtoDao.getPatchResultDtoList(patchResultDto);
		Page<PatchResultDto> patchResultDtoPage = null;
		return patchResultDtoPage;
	}

	private void setCurPageAndRowCntToPatchResult(T1001Req req,
			PatchResultDto patchResultDto) {
		try {
			patchResultDto.setCurPage(req.getCurPage());
		} catch (Exception e) {
			patchResultDto.setCurPage(1);
		}
		try {
			patchResultDto.setRowCnt(req.getRowCnt());
		} catch (Exception e) {
			patchResultDto.setRowCnt(10);
		}
	}

	/**
	 * 根据appNo获取补件信息
	 */
	public TmAppRfe getPbTypeByAppNo(String appNo, String org) {
		TmAppRfe tmAppRfe = new TmAppRfe();
		tmAppRfe.setOrg(org);
		tmAppRfe.setAppNo(appNo);
		List<TmAppRfe> tmAppRfeList = tmAppRfeDao.getTmAppRfeByParam(tmAppRfe);
		if(CollectionUtils.sizeGtZero(tmAppRfeList)){
			tmAppRfe = tmAppRfeList.get(0);
		}else {
			logger.info("没有找到[{}]待补件相关信息",appNo);
			throw new ProcessException("没有找到["+appNo+"]待补件相关信息");
		}
		
		return tmAppRfe; 
	}
	/**
	 * 返回当前申请件待补件信息
	 */
	public TmAppRfe getPbType(String org, T1001Req req, TmAppMain tmAppMain) {
		
		StringBuffer pbType = new StringBuffer(); // 拼接尚未补件的补件信息
		String appNo = req.getAppNo();
		TmAppRfe tmAppRfe = getPbTypeByAppNo(appNo, org);//获取补件信息
		String pbTypeArrayStr = tmAppRfe.getPbType();
		if (pbTypeArrayStr != null) {
			String[] patchs = pbTypeArrayStr.split("\\|");
			boolean flag = true;
			
			if (patchs.length > 0) {//统计待补件的类型
				String resPatch = req.getPbType(); // 反馈结果补件信息
				for (String patchBolt : patchs) {
					flag = true;
					if (resPatch != null) {
						if(resPatch.contains("附卡-") && patchBolt.endsWith(resPatch)){
							flag = false;
						}else if (patchBolt.equals(resPatch)) {
							flag = false;
						}
					}
					if (flag == true) {
						pbType.append("|");
						pbType.append(patchBolt);
					}
				}
			}
			//更新补件信息
			if (pbType != null && pbType.length() != 0 && !pbType.toString().equals("")) {
				tmAppRfe.setPbType(pbType.substring(1));
				tmAppRfe.setPbSource(req.getPbSource());//补件渠道
				tmAppRfeDao.update(tmAppRfe);
					
				TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo, OrganizationContextHolder.getUserNo(), RtfState.G05, null, req.getPbType());
				if(tmAppMain != null){
					tmAppHistory.setName(tmAppMain.getName());
					tmAppHistory.setIdNo(tmAppMain.getIdNo());
					tmAppHistory.setIdType(tmAppMain.getIdType());
				}
				applyInputService.saveTmAppHistory(tmAppHistory);//插入历史数据---所有业务流程操作都需要插入历史信息记录
				
				ApplyNodePatchBoltData applyNodePatchBoltData = new ApplyNodePatchBoltData();			
				List<String> boltList = applyNodePatchBoltData.getPatchBoltList();
				if(StringUtils.isNotBlank(req.getPbType())){
					boltList.add(appCommonUtil.getPatchBoltTypeEnumValueByCH(pbTypeArrayStr));
				}
				applyNodePatchBoltData.setPatchBoltList(boltList);

				Map<String, Serializable> patchData = new HashMap<String, Serializable>();
				patchData.put(AppConstant.APPLY_NODE_PATCHBOLT_DATA, applyNodePatchBoltData);
				nodeObjectUtil.insertAllNodeRec(patchData,appNo);//保存补件节点信息
				
				return tmAppRfe;
			} else {//补件完成
				tmAppRfe.setPbType("");
				tmAppRfe.setPbSource(req.getPbSource());//补件渠道
				tmAppRfe.setIsOk(Indicator.Y.name());//添加补件完成标志
				tmAppRfeDao.update(tmAppRfe);
				
				boltBujianComplete(req, tmAppMain);//发起流程
				
				return tmAppRfe;
			}
		}
		
		return tmAppRfe;
	}
	
	/**
	 * 补件完成发起流程
	 */
	private void boltBujianComplete(T1001Req req, TmAppMain tmAppMain){
		String appNo = req.getAppNo();
		if(StringUtils.isBlank(appNo)){
			throw new ProcessException("补件完成[APPNO]为空");
		}
		List<Task> query =  taskService.createTaskQuery().processInstanceBusinessKey(appNo).list();
		
		if(CollectionUtils.sizeGtZero(query)){
			Task task = query.get(0);
			String taskId = task.getId();
			if(StringUtils.isBlank(taskId)){
				throw new ProcessException("联机补件["+appNo+"]没找到对应的任务的ID");
			}
			
			Map<String, Serializable> data = applyQueryService.getNodeInfoByAppNo(appNo).getTmAppNodeInfoRecordMap();
			if(data != null && data.get(AppConstant.APPLY_NODE_COMMON_DATA) != null){
				// 流程节点公共信息
				ApplyNodeCommonData APPLY_NODECommonData = (ApplyNodeCommonData) data.get(AppConstant.APPLY_NODE_COMMON_DATA);
				APPLY_NODECommonData.setRtfStateType(RtfState.G10.name());
				data.put(AppConstant.APPLY_NODE_COMMON_DATA, APPLY_NODECommonData);
				
				TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo, req.getOperatorId(), RtfState.G10, null, req.getPbType());
				if(tmAppMain != null){
					tmAppMain.setRtfState(RtfState.G10.name());
					tmAppMain.setUpdateDate(new Date());
					tmAppMain.setUpdateUser(OrganizationContextHolder.getUserNo());
					applyInputService.updateTmAppMain(tmAppMain);
					
					tmAppHistory.setName(tmAppMain.getName());
					tmAppHistory.setIdType(tmAppMain.getIdType());
					tmAppHistory.setIdNo(tmAppMain.getIdNo());
				}else {
					throw new ProcessException("申请件[{"+appNo+"}]main表信息为空");
				}
				
				applyInputService.saveTmAppHistory(tmAppHistory);//插入历史数据---所有业务流程操作都需要插入历史信息记录		
				
				data = boltBujan(data,req.getPbType(),Indicator.Y.name(),tmAppMain);
				
				TmAppHistory his = new TmAppHistory();
				his.setAppNo(appNo);
				his.setRtfState(RtfState.G05.name());
				List<TmAppHistory> hisList = applyQueryService.getTmAppHistoryList(tmAppHistory);
				
				ApplyNodePatchBoltData applyNodePatchBoltData = new ApplyNodePatchBoltData();			
				List<String> boltList = applyNodePatchBoltData.getPatchBoltList();
				if(CollectionUtils.sizeGtZero(hisList)){
					for (TmAppHistory tm : hisList) {
						String pbType = tm.getRemark();
						if(StringUtils.isNotBlank(pbType)){
							boltList.add(appCommonUtil.getPatchBoltTypeEnumValueByCH(pbType));
						}
					}
					applyNodePatchBoltData.setPatchBoltList(boltList);
				}
				Map<String, Serializable> nodeData = new HashMap<String, Serializable>();
				nodeData.put(AppConstant.APPLY_NODE_PATCHBOLT_DATA, applyNodePatchBoltData);
				nodeData.put(AppConstant.APPLY_NODE_COMMON_DATA, APPLY_NODECommonData);
				nodeData.put(AppConstant.APPLY_NODE_INQUIRY_DATA, data.get(AppConstant.APPLY_NODE_INQUIRY_DATA));
				nodeObjectUtil.insertAllNodeRec(nodeData,appNo);//保存节点信息
				
				try {
					Map<String, Serializable> dataMap = new HashMap<String, Serializable>();
					dataMap.put(AppConstant.APPLY_NODE_COMMON_DATA, APPLY_NODECommonData);
					taskService.complete(taskId, (Map<String, Object>)(Map)dataMap);
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("[{}]启动工作流失败",appNo);
				}
			}
		}	
	}
	
	/**
	 * 
	 * @param data 更新初审节点补件信息
	 */
	private Map<String, Serializable> boltBujan(Map<String, Serializable> data,String boltString,String ok, TmAppMain tmAppMain){
		ApplyNodeInquiryData applyNodeInquiryData = null;
		if(data != null && data.get(AppConstant.APPLY_NODE_INQUIRY_DATA) != null){
			applyNodeInquiryData = (ApplyNodeInquiryData) data.get(AppConstant.APPLY_NODE_INQUIRY_DATA);
		}
		if(applyNodeInquiryData == null){
			throw new ProcessException("["+tmAppMain.getAppNo()+"]补件操作没找到初审补件节点信息");
		}

		if(StringUtils.isNotBlank(ok) && Indicator.Y.name().equals(ok)){//补件完成发起流程清空初审补件信息
			applyNodeInquiryData.setPatchBoltList(new ArrayList<String>());
		}else {
			if(StringUtils.isNotBlank(boltString)){
				String[] bolts = boltString.split("\\|");
				List<String> boltList = null;
				if(StringUtils.isNotBlank(tmAppMain.getAppType())){
					if(AppType.S.name().equals(tmAppMain.getAppType())){
						boltList = applyNodeInquiryData.getAttachPatchBoltList().get(AppConstant.bscSuppInd_S);
					}else {
						boltList = applyNodeInquiryData.getPatchBoltList();
					}
				}else {
					throw new ProcessException("["+tmAppMain.getAppNo()+"]补件操作申请件类型为空");
				}					
				if(CollectionUtils.sizeGtZero(boltList)){
					for(String bolt:bolts){
						String types = appCommonUtil.getPatchBoltTypeEnumValueByCH(bolt);
						if(StringUtils.isNotBlank(types)){
							for (int i = 0; i < boltList.size(); i++) {
								String dbPicType = boltList.get(i);
								if(types.contains("附卡-") && dbPicType.endsWith(types)){
									boltList.remove(i);
								}else if (dbPicType.equals(types)) {
									boltList.remove(i);
								}
							}
						}
					}
				}
				applyNodeInquiryData.setPatchBoltList(boltList);
				
			}else {
				return null;
			}
		}
		data.put(AppConstant.APPLY_NODE_INQUIRY_DATA, applyNodeInquiryData);
		
		return data;
	}
	
	/**
	 * 根据中文描述获取枚举信息
	 * @param picType
	 * @return
	 *//*
	public static String getPatchBoltTypeEnumValueByCH(String picType) {
		if (StringUtils.isNotBlank(picType)) {
			//如果是附属卡的补件，则直接返回中文
			if(picType.contains("附卡-")){
				return picType;
			}
			ApplyPatchBoltType [] idTs = ApplyPatchBoltType.values();
			for (int i = 0; i < idTs.length; i++) {
				ApplyPatchBoltType id = idTs[i];
				if (id.name().trim().equals(picType.trim())) {
					return id.name();
				}
				if (id.getApplyPatchBoltType().trim().contains(picType.trim())) {
					return id.name();
				}
			}
		}
		return null;
	}*/
}
