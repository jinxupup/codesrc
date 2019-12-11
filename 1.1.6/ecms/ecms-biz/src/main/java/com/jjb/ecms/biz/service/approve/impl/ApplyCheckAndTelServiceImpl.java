/**
 *
 */
package com.jjb.ecms.biz.service.approve.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.ApplyResultType;
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.approve.ApplyCheckAndTelService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCheatCheckData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeInquiryData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 初审、电调提交保存或发起流程服务
 * @author JYData-R&D-Big T.T
 * @date 2018年2月23日 下午3:18:59
 * @version V1.0
 */
@Service("applyCheckAndTelService")
public class ApplyCheckAndTelServiceImpl implements ApplyCheckAndTelService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	@Autowired
	private TmAppAuditDao tmAppAuditDao;


	/**
	 * 初审、电调提交保存或发起流程
	 * @param ifSave false：提交；true：保存
	 * @param type 节点类型  C：初审  T：电调
	 * @param query 操作数据
	 * @param applyNodeTelCheckBisicData 电调数据
	 */
	@Transactional
	@Override
	public void saveOrSubmitDataService(Boolean ifSave, String type,
										Query query, ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData) {

		if(StringUtils.isBlank(type)){
			logger.error("初审|电调提交保存或发起流程操作节点类型为空");
			throw new ProcessException("初审|电调提交保存或发起流程操作节点类型为空");
		}
		String appNo = StringUtils.valueOf(query.get("appNo"));//申请件编号
		String taskId = StringUtils.valueOf(query.get("taskId"));//任务ID
		String isFreeTelCheck = StringUtils.valueOf(query.get("isFreeTelCheck"));//免电话调查标志
		String[] refuseCodes = query.get("refuseCodes") == null?null : (String[])query.get("refuseCodes");//拒绝原因
		String isSendSms = StringUtils.valueOf(query.get("isSendSms"));//发送(拒绝|补件)短信标志

		if(ifSave){
			logger.info("开始保存初审|电调信息，==>申请件编号：["+ appNo +"],节点类型["+type+"]");
		}else {
			logger.info("开始提交初审|电调信息，==>申请件编号：["+ appNo +"],节点类型["+type+"]");
		}

		ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
		TmAppMain tmAppMain = applyInfoDto.getTmAppMain();//主表信息
		TmAppAudit tmAppAudit = applyInfoDto.getTmAppAudit();//进件审计信息
		if(applyInfoDto == null || tmAppMain == null){
			logger.error("未查询到该申请件["+appNo+"]信息，请重试!");
			throw new ProcessException("未查询到该申请件["+appNo+"]信息，请重试!");
		}

		RtfState rtfState = null;//流程状态
		String result = null;//审批结果
		String remark = null;//备注信息
		String riskClassic = null;//风险等级
		Map<String, Serializable> data = new HashMap<String, Serializable>();//节点数据对象

		//流程节点公共信息
		ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
		Map<String, Serializable> tmAppNodeInfoRecordMap = applyInfoDto.getTmAppNodeInfoRecordMap();
		if(tmAppNodeInfoRecordMap != null && tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA) !=null){
			applyNodeCommonData = (ApplyNodeCommonData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		//历史记录信息
		TmAppHistory tmAppHistory = new TmAppHistory();
		String user = OrganizationContextHolder.getUserNo();//操作员
		//节点类型判断
		if("C".equals(type)){
			riskClassic = StringUtils.valueOf(query.get("riskClassic"));//风险等级
			result = StringUtils.valueOf(query.get("basicCheckResult"));//初审审批结果
			remark = StringUtils.valueOf(query.get("basicRemark"));//初审备注
			String chkLmtStr = StringUtils.valueOf(query.get("chkLmt"));//初审额度
			BigDecimal chkLmt = StringUtils.isEmpty(chkLmtStr)?null:new BigDecimal(chkLmtStr);

			ApplyNodeInquiryData applyNodeInquiryData = new ApplyNodeInquiryData();
			applyNodeInquiryData.setCheckResult(result);

			tmAppMain.setRefuseCode(null);
			tmAppAudit.setBasicResult(result);//初审结果
			tmAppAudit.setIsFreeTelCheck(isFreeTelCheck);//免电话调查
			tmAppAudit.setIsSendSmsRefused(null);
			tmAppAudit.setIsSendSmsPatch(null);
			tmAppAudit.setBasicRefuseCode(null);
			//审批结果判断
			if(StringUtils.isNotBlank(result)){
				tmAppMain.setChkLmt(chkLmt);
				if(ifSave){//保存操作
					rtfState = RtfState.F05;
					if(ApplyResultType.R.name().equals(result)){//拒绝
						//设置拒绝原因
						StringBuffer refuseDesc = new StringBuffer();
						List<String> rejectReasonList = new ArrayList<String>();
						if(refuseCodes != null && refuseCodes.length > 0){
							Map<String,Map<String, String>> applyRejectReasonMap = cacheContext.getApplyRejectReasonMap();
							if(applyRejectReasonMap == null || applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP) == null){
								throw new ProcessException("申请件["+appNo+"]设置拒绝原因失败!");
							}
							Map<String, String> rejectReasonMap = applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP);//所有的拒绝原因
							for (int i = 0; i < refuseCodes.length; i++) {
								refuseDesc.append(refuseCodes[i]);
								refuseDesc.append("-");
								refuseDesc.append(rejectReasonMap.get(refuseCodes[i]));
								rejectReasonList.add(refuseCodes[i]);
								if(i != refuseCodes.length){
									refuseDesc.append(",");
								}
							}
						}
						tmAppMain.setRefuseCode(refuseDesc.toString());
						tmAppAudit.setBasicRefuseCode(refuseDesc.toString());
						tmAppAudit.setIsSendSmsRefused(isSendSms);
						applyNodeInquiryData.setRejectReasonList(rejectReasonList);
					}else if(ApplyResultType.O.name().equals(result)){//补件操作
						if(applyNodeInquiryData.getPatchBoltList()!=null){
							applyNodeInquiryData.getPatchBoltList().clear();
						}
						String[] applyPatchBoltTypes = query.get("applyPatchBoltTypes") == null?null:(String[])query.get("applyPatchBoltTypes");//补件信息
						List<String> patchBoltList = new ArrayList<String>();
						if(applyPatchBoltTypes != null && applyPatchBoltTypes.length > 0){
							for (int i = 0; i < applyPatchBoltTypes.length; i++) {
								patchBoltList.add(applyPatchBoltTypes[i]);
							}
						}
						if(AppType.S.name().equals(tmAppMain.getAppType())){//独立附卡
							Map<String, List<String>> patchBoltListMap = new HashMap<String, List<String>>();
							patchBoltListMap.put(AppConstant.bscSuppInd_S, patchBoltList);
							applyNodeInquiryData.setAttachPatchBoltList(patchBoltListMap);
						}else {
							applyNodeInquiryData.setPatchBoltList(patchBoltList);
						}
						tmAppAudit.setIsSendSmsPatch(isSendSms);

						//补件备注
						String patchBoltRemark = StringUtils.valueOf(query.get("patchBoltRemark"));
						if(StringUtils.isNotBlank(patchBoltRemark)){
							TmAppMemo tmAppMemo = new TmAppMemo();
							tmAppMemo.setAppNo(appNo);
							tmAppMemo.setMemoType(AppConstant.APP_REMARK_PATCHBOLT);
							tmAppMemo.setTaskKey(EcmsAuthority.CAS_APPLY_PATCHBOLT.name());
							tmAppMemo.setTaskDesc(EcmsAuthority.CAS_APPLY_PATCHBOLT.lab);
							tmAppMemo.setRtfState(rtfState.toString());
							tmAppMemo.setMemoInfo(patchBoltRemark);
							applyInputService.saveTmAppMemo(tmAppMemo);
						}
					}else if (ApplyResultType.M.name().equals(result)) {//再次风险决策
						tmAppAudit.setCfRiskClassic(riskClassic);
					}
				}else {//提交操作
					if(ApplyResultType.P.name().equals(result)){//通过
						if(StringUtils.isNotEmpty(isFreeTelCheck) && isFreeTelCheck.equals(Indicator.Y.name())
								&& StringUtils.isNotBlank(tmAppMain.getAppType()) && !AppType.S.name().equals(tmAppMain.getAppType())){//非独立附卡才能免电话调查
							rtfState = RtfState.F21;
							tmAppAudit.setIsFreeTelCheck(Indicator.Y.name());//设置免电话调查标志位
//							applyNodeCommonData.setIfSkipNext(isFreeTelCheck);//跳过下电调
						}else {
							rtfState = RtfState.F10;
							tmAppAudit.setIsFreeTelCheck(null);
						}
					}else if(result.startsWith(ApplyResultType.B.name())){//退回

						//如果所有的退回操作都没有对于的状态，则停留在当前的（初审调查）任务
						rtfState = AppCommonUtil.setReturnRrtStateByPageOp(result, RtfState.F05);
						if(StringUtils.isEmpty(tmAppAudit.getIsReturned())){
							tmAppAudit.setIsReturned(result);
						}else if(!tmAppAudit.getIsReturned().contains(result)){
							tmAppAudit.setIsReturned(tmAppAudit.getIsReturned()+"|"+result);
						}

						//退回上一任务人//TODO 使用SysActivitiCandidateListener工作流监听处理
//						String hisOpUser = commonService.getHisOpUser(appNo, null, rtfState.toString());
//						if(hisOpUser!=null){
//							taskService.setAssignee(taskId, user == null ? "" : user);
//							taskService.setOwner(taskId,  hisOpUser == null ? "" : hisOpUser);
//						}
					}else if(ApplyResultType.R.name().equals(result)){//拒绝
						//设置拒绝原因
						StringBuffer refuseDesc = new StringBuffer();
						List<String> rejectReasonList = new ArrayList<String>();
						Boolean rejectReasonFlag = false;
						if(refuseCodes != null && refuseCodes.length > 0){
							Map<String,Map<String, String>> applyRejectReasonMap = cacheContext.getApplyRejectReasonMap();
							if(applyRejectReasonMap == null || applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP) == null){
								throw new ProcessException("申请件["+appNo+"]设置拒绝原因失败!");
							}
							Map<String, String> rejectReasonMap = applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP);//所有的拒绝原因
							Map<String, String> rejectReasonFlagMap = applyRejectReasonMap.get(AppConstant.REJECT_REASON_FLAG_MAP);//具有直接拒绝标志
							for (int i = 0; i < refuseCodes.length; i++) {
								refuseDesc.append(refuseCodes[i]);
								refuseDesc.append("-");
								refuseDesc.append(rejectReasonMap.get(refuseCodes[i]));
								rejectReasonList.add(refuseCodes[i]);
								if(rejectReasonFlagMap != null && rejectReasonFlagMap.containsKey(refuseCodes[i])){
									rejectReasonFlag = true;
								}
								if(i != refuseCodes.length){
									refuseDesc.append(",");
								}
							}
						}
						if(rejectReasonFlag){
							rtfState = RtfState.F09;
							applyNodeCommonData.setRejectReason(AppConstant.REJECT_FLAG);
//							applyNodeCommonData.setIsRefuse(Indicator.N.name());//根据拒绝原因直接拒绝
						}else {
							rtfState = RtfState.F06;
//							applyNodeCommonData.setIsRefuse(Indicator.Y.name());//到终审
						}
						tmAppAudit.setIsSendSmsRefused(isSendSms);

						//设置拒绝原因码
						tmAppMain.setRefuseCode(refuseDesc.toString());
						tmAppAudit.setBasicRefuseCode(refuseDesc.toString());
						tmAppHistory.setRefuseCode(refuseDesc.toString());
						applyNodeInquiryData.setRejectReasonList(rejectReasonList);
					}else if(ApplyResultType.O.name().equals(result)){//补件操作
						if(applyNodeInquiryData.getPatchBoltList()!=null){
							applyNodeInquiryData.getPatchBoltList().clear();
						}
						String[] applyPatchBoltTypes = query.get("applyPatchBoltTypes") == null?null:(String[])query.get("applyPatchBoltTypes");//补件信息
						List<String> patchBoltList = new ArrayList<String>();
						if(applyPatchBoltTypes != null && applyPatchBoltTypes.length > 0){
							for (int i = 0; i < applyPatchBoltTypes.length; i++) {
								patchBoltList.add(applyPatchBoltTypes[i]);
							}
						}
						if(AppType.S.name().equals(tmAppMain.getAppType())){//独立附卡
							Map<String, List<String>> patchBoltListMap = new HashMap<String, List<String>>();
							patchBoltListMap.put(AppConstant.bscSuppInd_S, patchBoltList);
							applyNodeInquiryData.setAttachPatchBoltList(patchBoltListMap);
						}else {
							applyNodeInquiryData.setPatchBoltList(patchBoltList);
						}
						rtfState = RtfState.F08;
						tmAppAudit.setIsSendSmsPatch(isSendSms);

						//补件备注
						String patchBoltRemark = StringUtils.valueOf(query.get("patchBoltRemark"));
						if(StringUtils.isNotBlank(patchBoltRemark)){
							TmAppMemo tmAppMemo = new TmAppMemo();
							tmAppMemo.setAppNo(appNo);
							tmAppMemo.setMemoType(AppConstant.APP_REMARK_PATCHBOLT);
							tmAppMemo.setTaskKey(EcmsAuthority.CAS_APPLY_PATCHBOLT.name());
							tmAppMemo.setTaskDesc(EcmsAuthority.CAS_APPLY_PATCHBOLT.lab);
							tmAppMemo.setRtfState(rtfState.toString());
							tmAppMemo.setMemoInfo(patchBoltRemark);
							applyInputService.saveTmAppMemo(tmAppMemo);
						}
					}else if (ApplyResultType.M.name().equals(result)) {//再次风险决策
						rtfState = RtfState.F01;
						tmAppAudit.setCfRiskClassic(riskClassic);
					}
				}

			}else {
				logger.error("申请件[{}]初审审批结果为空",appNo);
				throw new ProcessException("申请件["+appNo+"]初审审批结果为空");
			}

			//保存初审备注
			if(StringUtils.isNotBlank(remark)){
				TmAppMemo tmAppRemark = new TmAppMemo();
				tmAppRemark.setAppNo(appNo);
				tmAppRemark.setMemoType(AppConstant.APP_REMARK);
				tmAppRemark.setTaskKey(EcmsAuthority.CAS_APPLY_BASIC_CHECK.name());
				tmAppRemark.setTaskDesc(EcmsAuthority.CAS_APPLY_BASIC_CHECK.lab);
				tmAppRemark.setRtfState(rtfState.toString());
				tmAppRemark.setMemoInfo(remark);
				applyInputService.saveTmAppMemo(tmAppRemark);
			}

			//客户经理注记
			String spreaderMemo = StringUtils.valueOf(query.get("memoInfo"));
			if(StringUtils.isNotBlank(spreaderMemo)){
				TmAppMemo tmAppMemo = new TmAppMemo();
				tmAppMemo.setAppNo(appNo);
				tmAppMemo.setMemoType(AppConstant.APP_MEMO);
				tmAppMemo.setTaskKey(EcmsAuthority.INPUT.name());
				tmAppMemo.setTaskDesc(EcmsAuthority.INPUT.lab);
				tmAppMemo.setRtfState(rtfState.toString());
				tmAppMemo.setMemoInfo(spreaderMemo);
				applyInputService.saveTmAppMemo(tmAppMemo);
			}

			//设置节点信息
			data.put(AppConstant.APPLY_NODE_INQUIRY_DATA, applyNodeInquiryData);
			if(applyNodeTelCheckBisicData != null){//若电调合并到初审
				data.put(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA, applyNodeTelCheckBisicData);
			}
		}else if ("T".equals(type)) {//电调
			result = StringUtils.valueOf(query.get("telCheckResult"));//电调审批结果
			remark = StringUtils.valueOf(query.get("telRemark"));//电调备注

			if(applyNodeTelCheckBisicData == null){
				applyNodeTelCheckBisicData = new ApplyNodeTelCheckBisicData();
			}
			applyNodeTelCheckBisicData.setResult(result);

			//审批结果判断
			if(StringUtils.isNotBlank(result)){
				if(ifSave){//保存操作
					rtfState = RtfState.F15;
					if(ApplyResultType.P.name().equals(result)){//通过
						tmAppAudit.setIsFreeTelCheck(isFreeTelCheck);//免电话调查
					}/*else if(result.startsWith(ApplyResultType.B.name())){//退回

					}*/else if(ApplyResultType.R.name().equals(result)){//拒绝
						//设置拒绝原因
						StringBuffer refuseDesc = new StringBuffer();
						List<String> rejectReasonList = new ArrayList<String>();
						if(refuseCodes != null && refuseCodes.length > 0){
							Map<String,Map<String, String>> applyRejectReasonMap = cacheContext.getApplyRejectReasonMap();
							if(applyRejectReasonMap == null || applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP) == null){
								throw new ProcessException("申请件["+appNo+"]设置拒绝原因失败!");
							}
							Map<String, String> rejectReasonMap = applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP);//所有的拒绝原因
							for (int i = 0; i < refuseCodes.length; i++) {
								refuseDesc.append(refuseCodes[i]);
								refuseDesc.append("-");
								refuseDesc.append(rejectReasonMap.get(refuseCodes[i]));
								rejectReasonList.add(refuseCodes[i]);
								if(i != refuseCodes.length){
									refuseDesc.append(",");
								}
							}
						}
						tmAppAudit.setTelRefuseCode(refuseDesc.toString());
						tmAppAudit.setIsSendSmsRefused(isSendSms);
						applyNodeTelCheckBisicData.setRefuseCodeList(refuseCodes);
					}
				}else {//提交操作
					if(ApplyResultType.P.name().equals(result)){//通过
						rtfState = RtfState.F20;
						tmAppAudit.setIsFreeTelCheck(isFreeTelCheck);//免电话调查
					}else if(result.startsWith(ApplyResultType.B.name())){//退回

						//如果所有的退回操作都没有对于的状态，则停留在当前的（电话调查）任务
						rtfState = AppCommonUtil.setReturnRrtStateByPageOp(result, RtfState.F15);
						if(StringUtils.isEmpty(tmAppAudit.getIsReturned())){
							tmAppAudit.setIsReturned(result);
						}else if(!tmAppAudit.getIsReturned().contains(result)){
							tmAppAudit.setIsReturned(tmAppAudit.getIsReturned()+"|"+result);
						}
						//退回上一任务人
//						String hisOpUser = commonService.getHisOpUser(appNo, null, rtfState.toString());
//						if(hisOpUser!=null){
//							taskService.setAssignee(taskId, user == null ? "" : user);
//							taskService.setOwner(taskId,  hisOpUser == null ? "" : hisOpUser);
//						}
					}else if(ApplyResultType.R.name().equals(result)){//拒绝
						//设置拒绝原因
						StringBuffer refuseDesc = new StringBuffer();
						List<String> rejectReasonList = new ArrayList<String>();
						rtfState=RtfState.F16;
						Boolean rejectReasonFlag = false;
						if(refuseCodes != null && refuseCodes.length > 0){
							Map<String,Map<String, String>> applyRejectReasonMap = cacheContext.getApplyRejectReasonMap();
							if(applyRejectReasonMap == null || applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP) == null){
								throw new ProcessException("申请件["+appNo+"]设置拒绝原因失败!");
							}
							Map<String, String> rejectReasonMap = applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP);//所有的拒绝原因
							Map<String, String> rejectReasonFlagMap = applyRejectReasonMap.get(AppConstant.REJECT_REASON_FLAG_MAP);//具有直接拒绝标志
							for (int i = 0; i < refuseCodes.length; i++) {
								refuseDesc.append(refuseCodes[i]);
								refuseDesc.append("-");
								refuseDesc.append(rejectReasonMap.get(refuseCodes[i]));
								rejectReasonList.add(refuseCodes[i]);
								if(rejectReasonFlagMap != null && rejectReasonFlagMap.containsKey(refuseCodes[i])){
									rejectReasonFlag = true;
								}
								if(i != refuseCodes.length){
									refuseDesc.append(",");
								}
							}
						}
						tmAppAudit.setIsSendSmsRefused(isSendSms);
						//设置拒绝原因码
						tmAppMain.setRefuseCode(refuseDesc.toString());
						tmAppAudit.setTelRefuseCode(refuseDesc.toString());
						tmAppHistory.setRefuseCode(refuseDesc.toString());
						applyNodeTelCheckBisicData.setRefuseCodeList(refuseCodes);
					}
				}
			}else {
				logger.error("申请件[{}]电调审批结果为空",appNo);
				throw new ProcessException("申请件["+appNo+"]电调审批结果为空");
			}

			//保存电调备注
			if(StringUtils.isNotBlank(remark)){
				TmAppMemo tmAppRemark = new TmAppMemo();
				tmAppRemark.setAppNo(appNo);
				tmAppRemark.setMemoType(AppConstant.APP_REMARK);
				tmAppRemark.setTaskKey(EcmsAuthority.CAS_APPLY_TEL_SURVEY.name());
				tmAppRemark.setTaskDesc(EcmsAuthority.CAS_APPLY_TEL_SURVEY.lab);
				tmAppRemark.setRtfState(rtfState.toString());
				tmAppRemark.setMemoInfo(remark);
				applyInputService.saveTmAppMemo(tmAppRemark);
			}

			//客户经理注记
			String spreaderMemo = StringUtils.valueOf(query.get("memoInfo"));
			if(StringUtils.isNotBlank(spreaderMemo)){
				TmAppMemo tmAppMemo = new TmAppMemo();
				tmAppMemo.setAppNo(appNo);
				tmAppMemo.setMemoType(AppConstant.APP_MEMO);
				tmAppMemo.setTaskKey(EcmsAuthority.INPUT.name());
				tmAppMemo.setTaskDesc(EcmsAuthority.INPUT.lab);
				tmAppMemo.setRtfState(rtfState.toString());
				tmAppMemo.setMemoInfo(spreaderMemo);
				applyInputService.saveTmAppMemo(tmAppMemo);
			}

			//设置节点信息
			data.put(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA, applyNodeTelCheckBisicData);
		}else if ("R".equals(type)){
			result = StringUtils.valueOf(query.get("result"));
			if("P".equals(result)){
				rtfState = RtfState.E26;
			}else if ("R".equals(result)){
				rtfState = RtfState.E27;
			}
		}

		//更新主表信息
		tmAppMain.setRtfState(rtfState.name());
		tmAppMain.setUpdateUser(user);
		tmAppMain.setUpdateDate(new Date());
		tmAppMain.setRemark(remark);//更新备注信息
		applyInputService.updateTmAppMain(tmAppMain);
		tmAppAudit.setUpdateDate(new Date());//更新TM_APP_AUDIT表信息
		tmAppAudit.setUpdateUser(user);
		tmAppAuditDao.updateTmAppAudit(tmAppAudit);


		//节点公共数据 赋值
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, tmAppMain);
		applyNodeCommonData.setRtfStateType(rtfState.name());
		applyNodeCommonData.setAppType(tmAppMain.getAppType());
		applyNodeCommonData.setOperatorId(user);
		applyNodeCommonData.setDate(new Date());
		//保存节点数据
		data.put("appNo", appNo);
		data.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		nodeObjectUtil.insertAllNodeRec(data,appNo);

//		//更新分期贷款信息
//		if("Y".equals(tmAppAudit.getIsInstalment())){
//			logger.info("同步分期贷款表状态:=====>appNo:{}",appNo);
//			TmAppInstalLoan appInstalLoan = appInstalLoanDao.queryByAppNo(appNo);
//			if(appInstalLoan!=null){
//				appInstalLoan.setStatus(rtfState.name());
//				appInstalLoanDao.update(appInstalLoan);
//			}
//		}

		//是否需要发起工作流
		if (ifSave) {
			//历史记录信息
			 tmAppHistory = AppCommonUtil.insertApplyHist(appNo, user,
					 rtfState, tmAppHistory.getRefuseCode(), remark);
			tmAppHistory.setName(tmAppMain.getName());
			tmAppHistory.setIdNo(tmAppMain.getIdNo());
			tmAppHistory.setIdType(tmAppMain.getIdType());
			applyInputService.saveTmAppHistory(tmAppHistory);
			logger.info("结束保存初审/电调信息，==>申请件编号：[" + appNo + "]任务taskId:[" + taskId + "],节点类型["+type+"]");
		}else {
			//更新历史信息
			tmAppHistory = AppCommonUtil.insertApplyHist(appNo, user, rtfState, tmAppHistory.getRefuseCode(), remark);
			tmAppHistory.setName(tmAppMain.getName());
			tmAppHistory.setIdNo(tmAppMain.getIdNo());
			tmAppHistory.setIdType(tmAppMain.getIdType());
			applyInputService.saveTmAppHistory(tmAppHistory);

			//发起流程
			Map<String, Serializable> activitiMap = new HashMap<String, Serializable>();
			activitiMap.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
			activitiService.completeTask(taskId, activitiMap, appNo);
			logger.info("结束提交初审/电调信息，==>申请件编号：[" + appNo + "]任务taskId:[" + taskId + "],节点类型["+type+"]");
		}
	}


	/**
	 * @Description: 预审提交发起流程服务
	 *
	 */

	@Override
	@Transactional
	public void preSubmitDataService(String type,Query query,TmAppPrimCardInfo tmAppPrimCardInfo) {
		// TODO Auto-generated method stub
		if (StringUtils.isBlank(type)) {
			logger.error("预审操作节点类型为空");
			throw new ProcessException("预审操作节点类型为空");
		}
		String appNo = StringUtils.valueOf(query.get("appNo"));//申请件编号
		String taskId = StringUtils.valueOf(query.get("taskId"));//任务ID
		logger.info("开始提交初审|电调信息，==>申请件编号：[" + appNo + "],节点类型[" + type + "]");
		//ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
		TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);//主表信息
		TmAppAudit tmAppAudit = applyQueryService.getTmAppAuditByAppNo(appNo);//进件审计信息
		if (tmAppMain == null) {
			throw new ProcessException("未查询到该申请件[" + appNo + "]信息，请重试!");
		}
		RtfState rtfState = null;//流程状态
		String remark = StringUtils.valueOf(query.get("remark"));//预审备注信息
		Map<String, Serializable> data = new HashMap<String, Serializable>();//节点数据对象
		//流程节点公共信息
		ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
		Map<String, Serializable> tmAppNodeInfoRecordMap = applyQueryService.getNodeInfoByAppNo(appNo).getTmAppNodeInfoRecordMap();
		if (tmAppNodeInfoRecordMap != null && tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA) != null) {
			applyNodeCommonData = (ApplyNodeCommonData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		//历史记录信息
		TmAppHistory tmAppHistory = new TmAppHistory();
		String user = OrganizationContextHolder.getUserNo();//操作员
		Map<String, Serializable> A020Map = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A020.name());
		ApplyNodeCheatCheckData cheat = null;
		if (A020Map != null && A020Map.containsKey(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA)) {
			cheat = (ApplyNodeCheatCheckData) A020Map.get(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA);
		}
		TmProduct product = cacheContext.getProduct(tmAppMain.getProductCd());
		if (product == null || !StringUtils.equals(product.getProductStatus(), "A")) {
			throw new ProcessException("产品" + tmAppMain.getProductCd() + "已经失效!");
		}
		/*if (tmAppMain.getRtfState() !=null && tmAppMain.getRtfState().equals("B11")) {
			rtfState = RtfState.H18;
		}*/
		rtfState = RtfState.B20;
		/*rtfState = RtfState.B17;
		tmAppMain.setFileFlag("N");*/
		if (cheat != null && StringUtils.equals(cheat.getContent(), "通过")) {
			if (tmAppMain.getSugLmt() != null) {
				tmAppMain.setAccLmt(tmAppMain.getSugLmt());
				//rtfState = RtfState.H18;
				if (product.getApprovalMaximum() == null || product.getApprovalMaximum().compareTo(tmAppMain.getAccLmt()) >= 0) {
					// rtfState = RtfState.H18;
					//状态为未归档，进入归档管理节点
					rtfState = RtfState.B25;
					//tmAppAudit.setUpdateDate(new Date());
					tmAppMain.setFileFlag("N");

				} else {
					rtfState = RtfState.B20;
					//rtfState=RtfState.B17;
					tmAppMain.setRemark("系统备注-系统建议额度[" + tmAppMain.getAccLmt() + "]大于产品上限额度[" + product.getApprovalMaximum() + "]，故转人工审批");
				}
			} else {
				tmAppMain.setRemark("系统备注-系统建议额度为空，转人工审批");
			}
		}


		//保存预审备注
		if(StringUtils.isNotBlank(remark)){
			tmAppMain.setRemark(tmAppMain.getRemark()+"; "+remark);
			TmAppMemo tmAppRemark = new TmAppMemo();
			tmAppRemark.setAppNo(appNo);
			tmAppRemark.setMemoType(AppConstant.APP_REMARK);
			tmAppRemark.setTaskKey(EcmsAuthority.CAS_APPLY_PRE_CHECK.name());
			tmAppRemark.setTaskDesc(EcmsAuthority.CAS_APPLY_PRE_CHECK.lab);
			tmAppRemark.setRtfState(rtfState.toString());
			tmAppRemark.setMemoInfo(remark);
			applyInputService.saveTmAppMemo(tmAppRemark);
		}


		//更新主表信息
		tmAppMain.setRemark(remark);//更新备注信息
		tmAppMain.setRtfState(rtfState.toString());
		applyInputService.updateTmAppMain(tmAppMain);

		//更新TmAppPrimAnnexEvi表
		String indIdFile = StringUtils.valueOf(query.get("indIdFile"));//信息
		String indJobFile = StringUtils.valueOf(query.get("indJobFile"));//信息
		String indSignFile = StringUtils.valueOf(query.get("indSignFile"));//信息
		TmAppPrimAnnexEvi appPrimAnnexEvi =	applyQueryService.getTmAppPrimAnnexEviByAppNo(appNo);
		appPrimAnnexEvi.setIndIdFile(indIdFile);
		appPrimAnnexEvi.setIndJobFile(indJobFile);
		appPrimAnnexEvi.setIndSignFile(indSignFile);
		applyInputService.updateTmAppPrimAnnexEvi(appPrimAnnexEvi);

		//更新   Tm_App_Prim_Card_Info  表  TmAppPrimCardInfo
/*		String[] applyPatchBoltTypes = query.get("applyPatchBoltTypes") == null?null:(String[])query.get("applyPatchBoltTypes");
		String[] spreaderMode = (String[]) query.get("spreaderModes"); //三亲核实
		String[] spreaderBranchThree = (String[]) query.get("branchs");//推广人网点
		TmAppPrimCardInfo tmAppPrimCardInfo =applyQueryService.getTmAppPrimCardInfoByAppNo(appNo);
		tmAppPrimCardInfo.setSpreaderMode(spreaderMode);
		tmAppPrimCardInfo.setSpreaderBranchThree(spreaderBranchThree);*/

		applyInputService.updateTmAppPrimCardInfo(tmAppPrimCardInfo);

		//节点公共数据 赋值
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, tmAppMain);
		applyNodeCommonData.setRtfStateType(rtfState.name());
		applyNodeCommonData.setOperatorId(user);
		applyNodeCommonData.setAppType(tmAppMain.getAppType());
		applyNodeCommonData.setDate(new Date());
		//保存节点数据
		data.put("appNo", appNo);
		data.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		nodeObjectUtil.insertAllNodeRec(data,appNo);



		//更新历史信息
		tmAppHistory = AppCommonUtil.insertApplyHist(appNo, user, rtfState, tmAppHistory.getRefuseCode(), remark);
		tmAppHistory.setName(tmAppMain.getName());
		tmAppHistory.setIdNo(tmAppMain.getIdNo());
		tmAppHistory.setIdType(tmAppMain.getIdType());
		applyInputService.saveTmAppHistory(tmAppHistory);

		//发起流程
		Map<String, Serializable> activitiMap = new HashMap<String, Serializable>();
		activitiMap.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		activitiService.completeTask(taskId, activitiMap, appNo);
		logger.info("结束提交初审/电调信息，==>申请件编号：[" + appNo + "]任务taskId:[" + taskId + "],节点类型["+type+"]");
	}


	/**
	 * @Description: 归档提交发起流程服务
	 *
	 */
	@Override
	@Transactional
	public void fileSubmitDataService(String type,Query query){
		if(StringUtils.isBlank(type)){
			logger.error("归档操作节点类型为空");
			throw new ProcessException("归档操作节点类型为空");
		}
		String appNo = StringUtils.valueOf(query.get("appNo"));//申请件编号
		String taskId = StringUtils.valueOf(query.get("taskId"));//任务ID
        String fileResult =  StringUtils.valueOf(query.get("fileManageResult"));//归档结果
		logger.info("开始提交归档信息，==>申请件编号：["+ appNo +"],节点类型["+type+"]");
		TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);//主表信息
		if(tmAppMain == null){
			throw new ProcessException("未查询到该申请件["+appNo+"]信息，请重试!");
		}
		RtfState rtfState = null;//流程状态
		String remark = StringUtils.valueOf(query.get("remark"));//预审备注信息
		Map<String, Serializable> data = new HashMap<String, Serializable>();//节点数据对象
		//流程节点公共信息
		ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
		Map<String, Serializable> tmAppNodeInfoRecordMap = 	applyQueryService.getNodeInfoByAppNo(appNo).getTmAppNodeInfoRecordMap();
		if(tmAppNodeInfoRecordMap != null && tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA) !=null){
			applyNodeCommonData = (ApplyNodeCommonData) tmAppNodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		//历史记录信息
		TmAppHistory tmAppHistory = new TmAppHistory();
		String user = OrganizationContextHolder.getUserNo();//操作员
		rtfState = RtfState.B26;
		TmProduct product = cacheContext.getProduct(tmAppMain.getProductCd());
		if(product==null || !StringUtils.equals(product.getProductStatus(), "A")) {
			throw new ProcessException("产品"+tmAppMain.getProductCd()+"已经失效!");
		}
		//根据归档结果设置流程状态
        if(fileResult ==null || fileResult.equals("R")){
            rtfState=RtfState.B27;
        }else if(fileResult.equals("P")){
            rtfState=RtfState.B26;
        }else if(fileResult.equals("B11")){
            rtfState=RtfState.B28;
			tmAppMain.setFileFlag(null);
        }


		//保存预审备注
		if(StringUtils.isNotBlank(remark)){
			tmAppMain.setRemark(tmAppMain.getRemark()+"; "+remark);
			TmAppMemo tmAppRemark = new TmAppMemo();
			tmAppRemark.setAppNo(appNo);
			tmAppRemark.setMemoType(AppConstant.APP_REMARK);
			tmAppRemark.setTaskKey(EcmsAuthority.CAS_APPLY_FILE_MANAGE.name());
			tmAppRemark.setTaskDesc(EcmsAuthority.CAS_APPLY_FILE_MANAGE.lab);
			tmAppRemark.setRtfState(rtfState.toString());
			tmAppRemark.setMemoInfo(remark);
			applyInputService.saveTmAppMemo(tmAppRemark);
		}


		//更新主表信息
		tmAppMain.setRemark(remark);//更新备注信息
		tmAppMain.setRtfState(rtfState.toString());
		applyInputService.updateTmAppMain(tmAppMain);

		//节点公共数据 赋值
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, tmAppMain);
		applyNodeCommonData.setRtfStateType(rtfState.name());
		applyNodeCommonData.setOperatorId(user);
		applyNodeCommonData.setAppType(tmAppMain.getAppType());
		applyNodeCommonData.setDate(new Date());
		//保存节点数据
		data.put("appNo", appNo);
		data.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		nodeObjectUtil.insertAllNodeRec(data,appNo);



		//更新历史信息
		tmAppHistory = AppCommonUtil.insertApplyHist(appNo, user, rtfState, tmAppHistory.getRefuseCode(), remark);
		tmAppHistory.setName(tmAppMain.getName());
		tmAppHistory.setIdNo(tmAppMain.getIdNo());
		tmAppHistory.setIdType(tmAppMain.getIdType());
		applyInputService.saveTmAppHistory(tmAppHistory);

		//发起流程
		Map<String, Serializable> activitiMap = new HashMap<String, Serializable>();
		activitiMap.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		activitiService.completeTask(taskId, activitiMap, appNo);
		logger.info("结束提交归档信息，==>申请件编号：[" + appNo + "]任务taskId:[" + taskId + "],节点类型["+type+"]");
	}
}