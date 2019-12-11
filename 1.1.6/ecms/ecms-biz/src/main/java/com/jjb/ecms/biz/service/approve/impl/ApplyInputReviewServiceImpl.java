package com.jjb.ecms.biz.service.approve.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppModifyHisDao;
import com.jjb.ecms.biz.dao.node.TmAppNodeInfoDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.approve.ApplyInputReviewService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.NodeObjectTemplate;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.AppplyCompareInfoForReviewDto;
import com.jjb.ecms.facility.dto.FieldProductDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeReviewData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppModifyHis;
import com.jjb.ecms.infrastructure.TmAppNodeInfo;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.thoughtworks.xstream.XStream;

/**
 * 录入复核
 * 
 * @author BIG.K.K
 *
 */
@Service("applyInputReviewService")
public class ApplyInputReviewServiceImpl implements ApplyInputReviewService {
	@Autowired
	private TmAppNodeInfoDao tmAppNodeInforecordDao;
	@Autowired
	private TmAppModifyHisDao tmAppModifyHisDao;
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private ApplyQueryService applyQueryService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ApplyInputService applyInputService;
	/**
	 *保存复核信息
	 * @param reviewMap 主附卡需要复核的字段
	 * @param tmAppMemo 复核人备注
	 */
	@Override
	@Transactional
	public void saveApplyReviewInfo(TmAppMain tmAppMain,
			TmAppPrimCardInfo tmAppPrimCardInfo, TmAppMemo tmAppMemo,
			Map<String, Map<String, String>> reviewMap) {
		String appNo = null;
		String appType = null;
		if(tmAppMain != null){
			appNo = tmAppMain.getAppNo();
			appType = tmAppMain.getAppType();
		}
		if(StringUtils.isEmpty(appNo)){
			throw new ProcessException("保存申请件的appNo为null");
		}
		if(StringUtils.isEmpty(appType)){
			throw new ProcessException("保存申请件[" + appNo + "]的appType为null");
		}
		if(reviewMap == null || reviewMap.size() == 0){
			throw new ProcessException("保存申请件[" + appNo + "]复核信息的复合字段为空！");
		}
		logger.info("开始保存复核信息，插入applyNodeReviewData==>申请件编号：[" + appNo + "]");
		
		ApplyNodeReviewData applyNodeReviewData = new ApplyNodeReviewData();
		applyNodeReviewData.setReviewsMap(reviewMap);
		
		List<TmAppNodeInfo> tmAppNodeInforecordList = tmAppNodeInforecordDao.getTmAppNodeInfoList(appNo);
		Map<String, Serializable> tmAppNodeInfoRecordMap = new HashMap<String, Serializable>();
		if(CollectionUtils.isNotEmpty(tmAppNodeInforecordList)){
			// 申请节点信息
			for (TmAppNodeInfo tmAppNodeInforecord : tmAppNodeInforecordList) {
				// 复核节点信息
				if (EnumsActivitiNodeType.A005.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodeReviewData> templete = new NodeObjectTemplate<ApplyNodeReviewData>();
					XStream xStream = NodeObjectUtil.getXstream(templete);
					NodeObjectTemplate<ApplyNodeReviewData> objList = (NodeObjectTemplate<ApplyNodeReviewData>)xStream.fromXML(tmAppNodeInforecord.getContent());
					List<?> column = objList.getNodeObject();
					tmAppNodeInfoRecordMap.put(AppConstant.APPLY_NODE_REVIEW_DATA, (ApplyNodeReviewData)column.get(0));
				}
			}
		}
		ApplyNodeCommonData applyNodeCommonData = null;
		if(tmAppNodeInfoRecordMap != null && tmAppNodeInfoRecordMap.size() > 0 && tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA) != null){
			applyNodeCommonData = (ApplyNodeCommonData)tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		//节点公共数据 赋值
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData,tmAppMain);
		applyNodeCommonData.setRtfStateType(RtfState.B05.name());
		
		Map<String, Serializable> result = new HashMap<String, Serializable>();
		result.put(AppConstant.APPLY_NODE_REVIEW_DATA, applyNodeReviewData);
		result.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		result.put("appNo", appNo);
		nodeObjectUtil.insertAllNodeRec(result,appNo);
		
		logger.info("更新TmAppMain状态信息RtfState，==>申请件编号：[" + appNo + "]");
		TmAppMain appMain = applyQueryService.getTmAppMainByAppNo(appNo);
		appMain.setRtfState(RtfState.B05.name());
		appMain.setUpdateDate(new Date());
		applyInputService.updateTmAppMain(appMain);

		logger.info("更新tmAppPrimCardInfo复核人信息，==>申请件编号：[" + appNo + "]");
		TmAppPrimCardInfo appPrimCardInfo = applyQueryService.getTmAppPrimCardInfoByAppNo(appNo);
		if(tmAppPrimCardInfo != null && appPrimCardInfo != null){
			appPrimCardInfo.setReviewName(tmAppPrimCardInfo.getReviewName());
			appPrimCardInfo.setReviewNo(tmAppPrimCardInfo.getReviewNo());
			applyInputService.updateTmAppPrimCardInfo(appPrimCardInfo);
		}
		
		logger.info("保存TmAppMemo复核备注信息，==>申请件编号：[" + appNo + "]");
		if(tmAppMemo!=null){
			tmAppMemo.setAppNo(tmAppMain.getAppNo());
			tmAppMemo.setMemoType(AppConstant.APP_REMARK);
			tmAppMemo.setMemoVersion(0);
			tmAppMemo.setRtfState(RtfState.B05.name());
			tmAppMemo.setTaskKey(EcmsAuthority.AMS_APPLY_REVIEW.name());
			tmAppMemo.setTaskDesc(EcmsAuthority.AMS_APPLY_REVIEW.lab);
			applyInputService.saveTmAppMemo(tmAppMemo);
		}
		
//		logger.info("更新分期贷款表的进度信息:==>申请件编号:{}",appNo);
//		TmAppInstalLoan appInstalLoan = appInstalLoanDao.queryByAppNo(appNo);
//		if(appInstalLoan!=null){
//			appInstalLoan.setStatus(tmAppMain.getRtfState());
//			appInstalLoanDao.updateByAppNo(appInstalLoan);
//		}
	}
	
	
	/* *
	 * 提交复核信息 保存applyNodeReviewData，applyNodeCommonData 更改TM_APP_MAIN中状态信息
	 */
	@Override
	@Transactional
	public void applyInputReviewSubmit(String taskId, TmAppMain tmAppMain,
			TmAppHistory tmAppHistory, ApplyNodeCommonData applyNodeCommonData,
			TmAppAudit tmAppAudit)
			throws ProcessException {
		String appNo = null;
		if(tmAppMain != null){
			appNo = tmAppMain.getAppNo();
			if(tmAppAudit !=null) {
//				if ("Y".equals(tmAppAudit.getIsInstalment())) {
//					logger.info("更新分期贷款表的进度信息:==>申请件编号:{}", appNo);
//					TmAppInstalLoan appInstalLoan = appInstalLoanDao.queryByAppNo(appNo);
//					if (appInstalLoan != null) {
//						appInstalLoan.setStatus(tmAppMain.getRtfState());
//						appInstalLoanDao.updateByAppNo(appInstalLoan);
//					}
//				}
				logger.info("更改TmAppMain状态信息RtfState，==>申请件编号：[" + appNo + "]");
				tmAppMain.setRtfState(RtfState.B10.name());
				tmAppMain.setUpdateDate(new Date());
				applyInputService.updateTmAppMain(tmAppMain);//更新备注
			}
		}
		
		Map<String , Serializable> data = new HashMap<String, Serializable>();
		data.put("appNo", appNo);
		data.put("taskId", taskId);
		data.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		
		if(tmAppHistory != null){
			applyInputService.saveTmAppHistory(tmAppHistory);
		}
		
		logger.info("提交复核节点信息applyNodeReviewData，applyNodeCommonData，==>"+ LogPrintUtils.printAppNoEndLog(appNo, null,null));
		nodeObjectUtil.insertAllNodeRec(data,appNo);

		Map<String, Serializable> map = new HashMap<String, Serializable>();
		map.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		try {
			activitiService.completeTask(taskId, map, appNo);
		}catch (Exception e) {
			throw new ProcessException(e.getMessage());
		}
	}

	/**
	 * 退回至原录入经办人列表
	 */

	@Override
	@Transactional
	public void returnBackApplyReviewInfo(String appNo, String taskId, String appType, TmAppMemo tmAppMemo) {
		if(StringUtils.isEmpty(appNo)){
			throw new ProcessException("录入复核退回操作的appNo为空!");
		}
		if(StringUtils.isEmpty(taskId)){
			throw new ProcessException("申请件["+appNo+"]录入复核退回操作的taskId为空!");
		}
		if(tmAppMemo==null){
			tmAppMemo = new TmAppMemo();
		}
		tmAppMemo.setAppNo(appNo);
		tmAppMemo.setMemoType(AppConstant.APP_REMARK);
		tmAppMemo.setMemoVersion(0);
		tmAppMemo.setRtfState(RtfState.B15.name());
		tmAppMemo.setTaskKey(EcmsAuthority.AMS_APPLY_REVIEW.name());
		tmAppMemo.setTaskDesc(EcmsAuthority.AMS_APPLY_REVIEW.lab);
		applyInputService.saveTmAppMemo(tmAppMemo);
	
		logger.info("退回复核信息，设置applyNodeCommonData，==>申请件编号：[" + appNo
				+ "]，taskId:[" + taskId + "]");
		// 获取applyNodeCommonData
		ApplyNodeCommonData applyNodeCommonDatanew = null;
//		ApplyInfoDto applyInfoDto = applyQueryService.getNodeInfoByAppNo(appNo);
		
		
		
//		ApplyNodeCommonData applyNodeCommonDatold= (ApplyNodeCommonData) applyInfoDto.getTmAppNodeInfoRecordMap().get(AppCommonConstant.APPLY_NODE_COMMON_DATA);
		
		Map<String, Serializable> resultMap  = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A070.name());
		ApplyNodeCommonData applyNodeCommonDatold = null;
		if(resultMap!=null && resultMap.containsKey(AppConstant.APPLY_NODE_COMMON_DATA)){
			applyNodeCommonDatold = (ApplyNodeCommonData) resultMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		
		if (applyNodeCommonDatold!= null) {
			applyNodeCommonDatold.setRtfStateType(RtfState.B15.name());
			applyNodeCommonDatold.setOperatorId(OrganizationContextHolder.getUserNo());
			applyNodeCommonDatold.setDate(new Date());
		}else{
			applyNodeCommonDatanew = new ApplyNodeCommonData();
			applyNodeCommonDatanew.setRtfStateType(RtfState.B15.name());
			applyNodeCommonDatanew.setOperatorId(OrganizationContextHolder.getUserNo());
			applyNodeCommonDatanew.setDate(new Date());
		}
		Map<String, Serializable> data = new HashMap<String, Serializable>();			
		if (applyNodeCommonDatold!= null) {		
			data.put("applyNodeCommonData", applyNodeCommonDatold);				
		}else if(applyNodeCommonDatanew!=null){
			data.put("applyNodeCommonData", applyNodeCommonDatanew);
		}
		data.put("appNo", appNo);
		data.put("taskId", taskId);	
		nodeObjectUtil.insertAllNodeRec(data,appNo);
		
		// 设置TM_APP_MAIN状态信息
		TmAppMain appMain = applyQueryService.getTmAppMainByAppNo(appNo);
		TmAppAudit tmAppAudit=applyQueryService.getTmAppAuditByAppNo(appNo);
		String result = "";
		if(tmAppAudit!=null){
			if(StringUtils.isEmpty(tmAppAudit.getIsReturned())){
				tmAppAudit.setIsReturned(result);
			}else if(!tmAppAudit.getIsReturned().contains(result)){
				tmAppAudit.setIsReturned(tmAppAudit.getIsReturned()+"|"+result);
			}
//			if("Y".equals(tmAppAudit.getIsInstalment())){
//				TmAppInstalLoan appInstalLoan = appInstalLoanDao.queryByAppNo(appNo);
//				if(appInstalLoan!=null){
//					appInstalLoan.setStatus(appMain.getRtfState());
//					appInstalLoanDao.updateByAppNo(appInstalLoan);
//				}
//			}
		}
		String user = OrganizationContextHolder.getUserNo();
		if(appMain!=null){
			appMain.setRtfState(RtfState.B15.name());
			appMain.setUpdateDate(new Date());
			appMain.setUpdateUser(user);
			applyInputService.updateTmAppMain(appMain);
			logger.info("更新分期贷款表的进度信息:==>申请件编号:{}",appNo);
			
		}
		
		//更新历史表数据
		TmAppHistory tmAppHistory = new TmAppHistory();
		tmAppHistory = AppCommonUtil.insertApplyHist(appNo, 
				OrganizationContextHolder.getUserNo(), RtfState.B15, null, tmAppMemo == null ? null:tmAppMemo.getMemoInfo());

		if (StringUtils.isEmpty(appType)) {
			throw new ProcessException("申请件[" + appNo + "]录入复核退回操作的appType为空！");
		}
		tmAppHistory.setName(appMain.getName());
		tmAppHistory.setIdNo(appMain.getIdNo());				
		tmAppHistory.setIdType(appMain.getIdType());
		applyInputService.saveTmAppHistory(tmAppHistory);
		
		// 操作工作流
		// 任务分配人
		taskService.setAssignee(taskId, OrganizationContextHolder.getUserNo());
		// 退回至原任务所属人（申请录入经办人）
		TmAppPrimCardInfo tmAppPrimCardInfo = applyQueryService.getTmAppPrimCardInfoByAppNo(appNo);		
		if(tmAppPrimCardInfo != null && StringUtils.isNotEmpty(tmAppPrimCardInfo.getInputName())){
			taskService.setOwner(taskId, tmAppPrimCardInfo.getInputName());
		}else {
//			throw new ProcessException("申请件["+appNo+"]录入复核退回至原任务所属人失败！");
		}
		
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		if (applyNodeCommonDatold!= null) {			
			map.put("applyNodeCommonData", applyNodeCommonDatold);		
		}else if(applyNodeCommonDatanew!=null){
			map.put("applyNodeCommonData", applyNodeCommonDatanew);
		}
		
		activitiService.completeTask(taskId, map, appNo);
	
	}

	/* 查看修改历史信息
	 */
	@Override
	@Transactional
	public Page<TmAppModifyHis> getModifyHistoryInfoPage(
			Page<TmAppModifyHis> page, String appNo) {
		page.getQuery().put("_SORT_LIST", null);
	
		return tmAppModifyHisDao.getModifyHistoryInfoPage(page,appNo);
	}

	/**
	 * 获取比对信息
	 */
	@Override
	@Transactional
	public Map<String , List<AppplyCompareInfoForReviewDto>> compareInfo(String appNo, String appType){
		// 获取原始录入信息
		ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
		if(applyInfoDto==null){
			throw new ProcessException("未查询到申请件["+appNo+"]相关数据,请页面后重试!");
		}
		//获取复核录入的信息
		ApplyNodeReviewData applyNodeReviewData = null;
		if(applyInfoDto.getTmAppNodeInfoRecordMap()!=null 
				&& applyInfoDto.getTmAppNodeInfoRecordMap().containsKey(AppConstant.APPLY_NODE_REVIEW_DATA)){
			applyNodeReviewData = (ApplyNodeReviewData)applyInfoDto.getTmAppNodeInfoRecordMap().get(AppConstant.APPLY_NODE_REVIEW_DATA);
		}
		String productCd = null;
		if(applyInfoDto.getTmAppMain() == null || StringUtils.isBlank(applyInfoDto.getTmAppMain().getProductCd())){
			logger.info("复核信息对比操作未查到卡产品信息");
			throw new ProcessException("复核信息对比操作未查到卡产品信息");
		}else {
			productCd = applyInfoDto.getTmAppMain().getProductCd();
		}
		//复核项
		List<FieldProductDto> fieldProductDtoList = cacheContext.getFieldProductDtosByProductCd(productCd);
		Map<String , List<AppplyCompareInfoForReviewDto>> compareFieldsMap = null;
		AppplyCompareInfoForReviewDto appplyCompareInfoForReviewDto = null;
		if(CollectionUtils.sizeGtZero(fieldProductDtoList)){
			List<AppplyCompareInfoForReviewDto> mainTabList = new ArrayList<AppplyCompareInfoForReviewDto>();
			List<AppplyCompareInfoForReviewDto> commonTabList = new ArrayList<AppplyCompareInfoForReviewDto>();
			TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
			Map<String, TmAppCustInfo> custMap = applyInfoDto.getTmAppCustInfoMap();
			TmAppPrimCardInfo tmAppPrimCardInfo = applyInfoDto.getTmAppPrimCardInfo();
			Map<String, TmAppContact> tmAppContactMap = applyInfoDto.getTmAppContactMap();
			TmAppPrimAnnexEvi tmAppPrimAnnexEvi = applyInfoDto.getTmAppPrimAnnexEvi();
//			TmAppInstalLoan tmAppInstalLoan = applyInfoDto.getTmAppInstalLoan();
			Map<String, Serializable> tmAppMainMap = null;
			Map<String, Serializable> tmAppPrimApplicantInfoMap = null;
			Map<String, Serializable> attachMap_0 = null;
			Map<String, Serializable> attachMap_1 = null;
			Map<String, Serializable> attachMap_2 = null;
			List<AppplyCompareInfoForReviewDto> attachInfo_0 = null;//存放附卡复核项信息
			List<AppplyCompareInfoForReviewDto> attachInfo_1 = null;
			List<AppplyCompareInfoForReviewDto> attachInfo_2 = null;
			Map<String, Serializable> tmAppPrimCardInfoMap = null;
			Map<String, Serializable> contactInfoMap_1 = null;
			Map<String, Serializable> contactInfoMap_2 = null;
			Map<String, Serializable> tmEtcCarMap = null;
			Map<String, Serializable> tmAppPrimAnnexEviMap = null;
			Map<String, Serializable> tmAppInstalLoanMap = null;
			//存放表各字段类型
			Map<String, String> tmAppMainFieldTypeMap = null;
//			Map<String, String> tmAppPrimApplicantInfoFieldTypeMap = null;
//			Map<String, String> attachFieldTypeMap = null;
			Map<String, String> custFieldTypeMap = null;
			Map<String, String> tmAppPrimCardInfoFieldTypeMap = null;
			Map<String, String> contactInfoFieldTypeMap = null;
			Map<String, String> tmEtcCarFieldTypeMap = null;
			Map<String, String> tmAppPrimAnnexEviFieldTypeMap = null;
			Map<String, String> tmAppInstalLoanFieldTypeMap = null;
			
			/**
			 * 把所有的复核项都转化为字符串类型进行比较，避免了对复核字段类型的判断
			 * 遍历所有的复核项，分为主卡和附卡两部分
			 * map<表名，map<复核字段名,复核字段值>>
			 */
			Map<String , Map<String , String >> reviewsMap = applyNodeReviewData.getReviewsMap();
			if(reviewsMap == null || reviewsMap.size() == 0){
				logger.error("复核提交操作的reviewsMap复核值为空！");
			}
			for (FieldProductDto fieldProductDto : fieldProductDtoList) {
				String reviewFlag = fieldProductDto.getIfReviewItem();//复核项标志
				if(StringUtils.isNotBlank(reviewFlag) && !Indicator.N.name().equals(reviewFlag)){
					String tabName = fieldProductDto.getTabName();//表名
					String field = fieldProductDto.getFieldEn();//字段名
					String fieldName = fieldProductDto.getFieldName();
					String remark = fieldProductDto.getRemark();//用于区分第一、二联系人,也是为了兼容录入环节保存的联系人信息数据
					String reviewValue = null;
					if(tabName.equals(AppConstant.TmAppContact) && StringUtils.isNotEmpty(remark)){//1：亲属；2其他
						if(remark.contains("1"))
							remark = "1";
						else if(remark.contains("2"))
							remark = "2";
						reviewValue = tabName + "[" + remark + "]." + field;
					}else {
							reviewValue = tabName + "." + field;
					}
					appplyCompareInfoForReviewDto = new AppplyCompareInfoForReviewDto();
					if(tabName.equals(AppConstant.TmAppMain)){
						if(tmAppMainMap == null){
							tmAppMainMap = tmAppMain.convertToMap();
						}
						if(tmAppMainFieldTypeMap == null){
							tmAppMainFieldTypeMap = getFieldTypeMap(TmAppMain.class);
						}
						appplyCompareInfoForReviewDto.setOption(fieldName);//对比项
						String inputVal = getInputValueStr(field, tmAppMainFieldTypeMap,tmAppMainMap);
						appplyCompareInfoForReviewDto.setInputValue(inputVal);
						appplyCompareInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.COMMON_TAB).get(reviewValue));
						if(!appplyCompareInfoForReviewDto.getInputValue().equals(appplyCompareInfoForReviewDto.getReviewValue())){
							commonTabList.add(appplyCompareInfoForReviewDto);
						}
						appplyCompareInfoForReviewDto.setInputValue(setHideValue(inputVal));
						
					}else if(tabName.equals(AppConstant.TmAppCustInfo)){
						
						TmAppCustInfo custInfo = null;
						
						for (Entry<String, TmAppCustInfo> enty: custMap.entrySet()) {
							custInfo = enty.getValue();
							if(custInfo != null){
								if(custFieldTypeMap == null){
									custFieldTypeMap = getFieldTypeMap(TmAppCustInfo.class);
								}
								AppplyCompareInfoForReviewDto attachInfoForReviewDto = new AppplyCompareInfoForReviewDto();
								
								//如果是附卡
								if(StringUtils.equals(custInfo.getBscSuppInd(), AppConstant.bscSuppInd_B)){
									//注释:附卡复核提交时会报null异常,原因:会检查主卡的信息,处理:加一个条件判断主卡表是否为null(tmAppPrimApplicantInfo!=null)
									if(tmAppPrimApplicantInfoMap == null){
										tmAppPrimApplicantInfoMap = custInfo.convertToMap();
									}
									appplyCompareInfoForReviewDto.setOption(fieldName);
									String inputVal = getInputValueStr(field, custFieldTypeMap,tmAppPrimApplicantInfoMap);
									appplyCompareInfoForReviewDto.setInputValue(inputVal);
									appplyCompareInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.MAIN_TAB).get(reviewValue));
									if(appplyCompareInfoForReviewDto.getInputValue()!=null && 
											!appplyCompareInfoForReviewDto.getInputValue().equals(appplyCompareInfoForReviewDto.getReviewValue())){
										mainTabList.add(appplyCompareInfoForReviewDto);
									}
									appplyCompareInfoForReviewDto.setInputValue(setHideValue(inputVal));
								}else if(StringUtils.equals(custInfo.getBscSuppInd(), AppConstant.bscSuppInd_S) && custInfo.getAttachNo() == 5){
									if(attachMap_0 == null){
										attachMap_0 = custInfo.convertToMap();
									}
									if(attachInfo_0 == null){
										attachInfo_0 = new ArrayList<AppplyCompareInfoForReviewDto>();
									}
									attachInfoForReviewDto.setOption(fieldName);
									String inputVal = getInputValueStr(field, custFieldTypeMap,attachMap_0);
									attachInfoForReviewDto.setInputValue(inputVal);
									if(AppType.S.name().equals(appType)){
										attachInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.bscSuppInd_S_1).get(tabName+"."+field));
									}else {
										attachInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.bscSuppInd_S_1).get(tabName+"["+0+"]."+field));
									}
									if(!attachInfoForReviewDto.getInputValue().equals(attachInfoForReviewDto.getReviewValue())){
										attachInfo_0.add(attachInfoForReviewDto);
									}
									attachInfoForReviewDto.setInputValue(setHideValue(inputVal));
								}else if(StringUtils.equals(custInfo.getBscSuppInd(), AppConstant.bscSuppInd_S) && custInfo.getAttachNo() == 6){
									if(attachMap_1 == null){
										attachMap_1 = custInfo.convertToMap();
									}
									if(attachInfo_1 == null){
										attachInfo_1 = new ArrayList<AppplyCompareInfoForReviewDto>();
									}
									attachInfoForReviewDto.setOption(fieldName);
									String inputVal = getInputValueStr(field, custFieldTypeMap,attachMap_1);
									attachInfoForReviewDto.setInputValue(inputVal);
									attachInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.bscSuppInd_S_2).get(tabName+"["+1+"]."+field));
									if(!attachInfoForReviewDto.getInputValue().equals(attachInfoForReviewDto.getReviewValue())){
										attachInfo_1.add(attachInfoForReviewDto);
									}
									attachInfoForReviewDto.setInputValue(setHideValue(inputVal));
								}else if(StringUtils.equals(custInfo.getBscSuppInd(), AppConstant.bscSuppInd_S) && custInfo.getAttachNo() == 7){
									if(attachMap_2 == null){
										attachMap_2 = custInfo.convertToMap();
									}
									if(attachInfo_2 == null){
										attachInfo_2 = new ArrayList<AppplyCompareInfoForReviewDto>();
									}
									attachInfoForReviewDto.setOption(fieldName);
									String inputVal = getInputValueStr(field, custFieldTypeMap,attachMap_2);
									attachInfoForReviewDto.setInputValue(inputVal);
									attachInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.bscSuppInd_S_3).get(tabName+"["+2+"]."+field));
									if(!attachInfoForReviewDto.getInputValue().equals(attachInfoForReviewDto.getReviewValue())){
										attachInfo_2.add(attachInfoForReviewDto);
									}
									attachInfoForReviewDto.setInputValue(setHideValue(inputVal));
									
								}
							}
						}
					}else if(tabName.equals(AppConstant.TmAppPrimCardInfo)){
						if(tmAppPrimCardInfoMap == null){
							tmAppPrimCardInfoMap = tmAppPrimCardInfo.convertToMap();
						}
						if(tmAppPrimCardInfoFieldTypeMap == null){
							tmAppPrimCardInfoFieldTypeMap = getFieldTypeMap(TmAppPrimCardInfo.class);
						}
						appplyCompareInfoForReviewDto.setOption(fieldName);
						String inputVal = getInputValueStr(field, tmAppPrimCardInfoFieldTypeMap,tmAppPrimCardInfoMap);
						appplyCompareInfoForReviewDto.setInputValue(inputVal);
						appplyCompareInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.COMMON_TAB).get(reviewValue));
						if(!appplyCompareInfoForReviewDto.getInputValue().equals(appplyCompareInfoForReviewDto.getReviewValue())){
							commonTabList.add(appplyCompareInfoForReviewDto);
						}
						appplyCompareInfoForReviewDto.setInputValue(setHideValue(inputVal));
					}else if(tabName.equals(AppConstant.TmAppContact)){
						TmAppContact tmAppContact = null;
						
						for (Entry<String ,TmAppContact> enty: tmAppContactMap.entrySet()) {
							tmAppContact = enty.getValue();
							if(tmAppContact != null && StringUtils.isNotEmpty(remark)){
								if(contactInfoFieldTypeMap == null){
									contactInfoFieldTypeMap = getFieldTypeMap(TmAppContact.class);
								}
								if(remark.equals("1") && remark.equals(tmAppContact.getContactType())){
									if(contactInfoMap_1 == null){
										contactInfoMap_1 = tmAppContact.convertToMap();
									}
									appplyCompareInfoForReviewDto.setOption(fieldName);
									String inputVal = getInputValueStr(field, contactInfoFieldTypeMap,contactInfoMap_1);
									appplyCompareInfoForReviewDto.setInputValue(inputVal);
									appplyCompareInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.MAIN_TAB).get(reviewValue));
									if(!appplyCompareInfoForReviewDto.getInputValue().equals(appplyCompareInfoForReviewDto.getReviewValue())){
										commonTabList.add(appplyCompareInfoForReviewDto);
									}
									appplyCompareInfoForReviewDto.setInputValue(setHideValue(inputVal));
									
								}
								if(remark.equals("2") && remark.equals(tmAppContact.getContactType())){
									if(contactInfoMap_2 == null){
										contactInfoMap_2 = tmAppContact.convertToMap();
									}
									appplyCompareInfoForReviewDto.setOption(fieldName);
									String inputVal = getInputValueStr(field, contactInfoFieldTypeMap,contactInfoMap_2);
									appplyCompareInfoForReviewDto.setInputValue(inputVal);
									appplyCompareInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.COMMON_TAB).get(reviewValue));
									if(!appplyCompareInfoForReviewDto.getInputValue().equals(appplyCompareInfoForReviewDto.getReviewValue())){
										commonTabList.add(appplyCompareInfoForReviewDto);
									}
									appplyCompareInfoForReviewDto.setInputValue(setHideValue(inputVal));
								}
								
							}
						}
					}else if(tabName.equals(AppConstant.TmAppPrimAnnexEvi)){
						if(tmAppPrimAnnexEviMap == null){
							tmAppPrimAnnexEviMap = tmAppPrimAnnexEvi.convertToMap();
						}
						if(tmAppPrimAnnexEviFieldTypeMap == null){
							tmAppPrimAnnexEviFieldTypeMap = getFieldTypeMap(TmAppPrimAnnexEvi.class);
						}
						appplyCompareInfoForReviewDto.setOption(fieldName);
						String inputVal = getInputValueStr(field, tmAppPrimAnnexEviFieldTypeMap,tmAppPrimAnnexEviMap);
						appplyCompareInfoForReviewDto.setInputValue(inputVal);
						appplyCompareInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.COMMON_TAB).get(reviewValue));
						if(!appplyCompareInfoForReviewDto.getInputValue().equals(appplyCompareInfoForReviewDto.getReviewValue())){
							commonTabList.add(appplyCompareInfoForReviewDto);
						}
						appplyCompareInfoForReviewDto.setInputValue(setHideValue(inputVal));
					}/*else if(tabName.equals(AppConstant.TmAppInstalLoan)){
						if(tmAppInstalLoanMap == null && tmAppInstalLoan != null){
							tmAppInstalLoanMap = tmAppInstalLoan.convertToMap();
						}
						if(tmAppInstalLoanFieldTypeMap == null){
							tmAppInstalLoanFieldTypeMap = getFieldTypeMap(TmAppInstalLoan.class);
						}
						appplyCompareInfoForReviewDto.setOption(fieldName);
						String inputVal = getInputValueStr(field, tmAppInstalLoanFieldTypeMap,tmAppInstalLoanMap);
						appplyCompareInfoForReviewDto.setInputValue(inputVal);
						appplyCompareInfoForReviewDto.setReviewValue(reviewsMap.get(AppConstant.COMMON_TAB).get(reviewValue));
						if(!appplyCompareInfoForReviewDto.getInputValue().equals(appplyCompareInfoForReviewDto.getReviewValue())){
							commonTabList.add(appplyCompareInfoForReviewDto);
						}
						appplyCompareInfoForReviewDto.setInputValue(setHideValue(inputVal));
					}*/
				}
			}
			
			compareFieldsMap = new HashMap<String, List<AppplyCompareInfoForReviewDto>>();
			if(mainTabList != null){
				compareFieldsMap.put(AppConstant.MAIN_TAB, mainTabList);
			}
			if(attachInfo_0 != null){
				compareFieldsMap.put(AppConstant.bscSuppInd_S_1, attachInfo_0);
			}
			if(attachInfo_1 != null){
				compareFieldsMap.put(AppConstant.bscSuppInd_S_2, attachInfo_1);
			}
			if(attachInfo_2 != null){
				compareFieldsMap.put(AppConstant.bscSuppInd_S_3, attachInfo_2);
			}
			if(commonTabList != null){
				compareFieldsMap.put(AppConstant.COMMON_TAB, commonTabList);
			}
		}
		
		return compareFieldsMap;
		
	}
	
	private Map<String, String> getFieldTypeMap(Class<?> clazz){
		Map<String, String> fieldTypeMap = new HashMap<String, String>();
		if(clazz != null){
		//	Class<?> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fieldTypeMap.put(fields[i].getName(), fields[i].getType().getName());
			}
		}
		return fieldTypeMap;
	}
	
	/**
	 * 
	 * @Description (TODO 获取obj 对象中字段 fieldname的值)
	 * @param fieldname
	 * @param obj
	 * @return
	 */
	private String getInputValueStr(String fieldname,Map<String, String> fieldTypeMap,Map<String, Serializable> objMap){
		String oldstr = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timedf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(StringUtils.isNotEmpty(fieldname) && fieldTypeMap != null && objMap != null){
			try {
				
				String fieldType = fieldTypeMap.get(fieldname);
				
				if(fieldType == null){
					logger.info("该对象内无该名称的变量："+fieldname);
					return oldstr;
				}
				if(fieldType.endsWith(AppConstant.DATE)){//
					oldstr="";
					Object date= objMap.get(fieldname);				
					if(date!=null){
						oldstr = sdf.format(date);
					}
									
				}else if(fieldType.endsWith(AppConstant.TIMESTAMP)){//
					oldstr="";
					Object date= objMap.get(fieldname);
					if(date!=null){
						oldstr = timedf.format(date);
					}
				}else{
					oldstr="";
					oldstr= String.valueOf(objMap.get(fieldname));
				}
			}catch (Exception e) {
				logger.error("获取{}-录入值异常",fieldname,e);
			}
			
		}
		if(oldstr == null || oldstr.equals("null"))
			oldstr = "";
		return oldstr;
	}
	
	/**
	 * 录入值用*替换(*代替后半部分)
	 * @param inputValue
	 * @return
	 */
	private String setHideValue(String inputValue){
		
		if(StringUtils.isNotEmpty(inputValue)){
			if(inputValue.trim().length() > 1){
				
				int repLength = 0;
				if(inputValue.trim().length()%2 == 0)
					repLength=inputValue.trim().length()/2;
				else
					repLength =inputValue.trim().length()/2+1;
				int oldLength = inputValue.trim().length()-repLength;
				
				String repVal ="";
				for(int i=0;i<repLength;i++){
					repVal += "*";
				}
				return inputValue =inputValue.trim().substring(0,oldLength)+repVal;
			}
			return "*";
		}
		return "";
	}

}
