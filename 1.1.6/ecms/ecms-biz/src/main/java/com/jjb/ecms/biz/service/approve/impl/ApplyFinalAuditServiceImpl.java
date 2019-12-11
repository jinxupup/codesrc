package com.jjb.ecms.biz.service.approve.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppCustInfoDao;
import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.approve.ApplyFinalAuditService;
import com.jjb.ecms.biz.service.common.NewTransactionalService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeFinalAuditData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description 终审service实现类
 * @author hn
 * @date 2016年8月29日16:25:50
 */

@Service("finalAuditService")
public class ApplyFinalAuditServiceImpl implements ApplyFinalAuditService{

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private TmAppCustInfoDao tmAppCustInfoDao;
	@Autowired
	private TmAppAuditDao tmAppAuditDao;
	@Autowired
	private NewTransactionalService newTransactionalService;
	/**
	 * 保存终审操作信息
	 * @param query
	 */
	@Override
	@Transactional
	public void saveFinalAuditInfo(Query query) {
		if(query==null){
			throw new ProcessException("终审保存信息失败，请刷新页面并重试!");
		}
		// TODO Auto-generated method stub
		String appNo = StringUtils.valueOf(query.get("appNo"));//申请件编号
		String taskId = StringUtils.valueOf(query.get("taskId"));//任务ID
		String remark = StringUtils.valueOf(query.get("remark"));//备注
		String finalResult = StringUtils.valueOf(query.get("finalResult"));//审批结果
		String refuseCodeTypeSmall=StringUtils.valueOf(query.get("refuseCodeSmallType"));//拒绝原因小类代码
		String refuseSmallCodeDesc=StringUtils.valueOf(query.get("refuseSmallCode"));//根据代码匹配的拒绝原因小类(代码加解释)
		String[] refuseCodes = query.get("refuseCodes") == null?null : (String[])query.get("refuseCodes");//终审拒绝原因大类		String user = OrganizationContextHolder.getUserNo();//操作员
		
		logger.info("开始保存终审信息，插入ApplyNodeFinalAuditData==>申请件编号：["+ appNo +"]");
		ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);//查出申请信息
		if(applyInfoDto==null || applyInfoDto.getTmAppMain()==null){
			throw new ProcessException("未查询到该申请件["+appNo+"]信息，请重试!");
		}
		//设置拒绝原因
		StringBuffer refuseDesc = new StringBuffer();
		List<String> rejectReasonList = new ArrayList<String>();
		if(refuseCodes != null && refuseCodes.length > 0){
			Map<String,Map<String, String>> applyRejectReasonMap = cacheContext.getApplyRejectReasonMap();
			if(applyRejectReasonMap == null || applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP) == null){
				throw new ProcessException("申请件["+appNo+"]设置拒绝原因失败!");
			}
			Map<String, String> rejectReasonMap = applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP);
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
		/****************************************************更新主表***********************************************************************************/
		TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
		tmAppMain.setRefuseCode(null);
		
		TmAppAudit tmAppAudit = applyInfoDto.getTmAppAudit();
		tmAppAudit.setFinalResult(finalResult);
		tmAppAudit.setFinalRefuseCode(null);
		tmAppAudit.setIsReturned(null);
		if(StringUtils.isNotBlank(finalResult) && ApplyResultType.P.name().equals(finalResult)){//通过
			tmAppMain.setRtfState(RtfState.K10.name());
			tmAppMain.setRefuseCode(null);
		}
		String rtfStateStr = tmAppMain.getRtfState();
		RtfState rtfState = RtfState.K05;
		if(finalResult.startsWith(ApplyResultType.B.name())){//退回
			//如果所有的退回操作都没有对于的状态，则停留在当前的（终审调查）任务
			rtfState = AppCommonUtil.setReturnRrtStateByPageOp(finalResult, RtfState.K05);
			rtfStateStr = rtfState.name();
			tmAppMain.setRtfState(rtfStateStr);
			
			if(StringUtils.isEmpty(tmAppAudit.getIsReturned())){
				tmAppAudit.setIsReturned(finalResult);
			}else if(!tmAppAudit.getIsReturned().contains(finalResult)){
				tmAppAudit.setIsReturned(tmAppAudit.getIsReturned()+"|"+finalResult);
			}
			if(tmAppAudit != null){
				if(tmAppAudit.getId() == null){
					tmAppAudit.setAppNo(tmAppMain.getAppNo());
					tmAppAuditDao.saveTmAppAudit(tmAppAudit);
				}else {
					tmAppAuditDao.updateTmAppAudit(tmAppAudit);
				}
			}
		}
		
		if(StringUtils.isNotBlank(finalResult) && ApplyResultType.R.name().equals(finalResult)){//拒绝
			tmAppMain.setRtfState(RtfState.K15.name());
			//tmAppMain.setRefuseCode("拒绝大类："+refuseDesc.toString()+"拒绝小类："+refuseSmallCodeDesc);
			//tmAppAudit.setFinalRefuseCode("拒绝大类："+refuseDesc.toString()+"拒绝小类："+refuseSmallCodeDesc);
			tmAppMain.setRefuseCode(refuseDesc.toString());
			tmAppAudit.setFinalRefuseCode(refuseDesc.toString());
			if(AppType.A.name().equals(tmAppMain.getAppType())){//主卡拒绝，附卡都拒绝

				List<TmAppCustInfo> attachs = applyQueryService.getTmAppAttachCustInfoListByAppNo(appNo);
				for (TmAppCustInfo attach : attachs) {
					attach.setRecordStatus(Indicator.N.name());//N表示拒绝
					tmAppCustInfoDao.updateTmAppCustInfo(attach);
				}
			}
			if(tmAppAudit != null){
				if(tmAppAudit.getId() == null){
					tmAppAudit.setAppNo(tmAppMain.getAppNo());
					tmAppAuditDao.saveTmAppAudit(tmAppAudit);
				}else {
					tmAppAuditDao.updateTmAppAudit(tmAppAudit);
				}
			}
		}
		tmAppMain.setRemark(remark);//更新备注信息
		tmAppMain.setUpdateUser(OrganizationContextHolder.getUserNo());
		tmAppMain.setUpdateDate(new Date());
		newTransactionalService.updateTmAppMain(tmAppMain);
//		//同步分期贷款表状态
//		if("Y".equals(tmAppAudit.getIsInstalment())){
//		TmAppInstalLoan appInstalLoan = appInstalLoanDao.queryByAppNo(tmAppMain.getAppNo());
//		if(appInstalLoan!=null){
//			appInstalLoan.setStatus(tmAppMain.getRtfState());
//			appInstalLoanDao.updateByAppNo(appInstalLoan);
//		}
//		}
	
		/*****************************************************设置历史信息****************************************************************************/
		TmAppHistory tmAppHistory = new TmAppHistory();
		RtfState rtf = AppCommonUtil.stringToEnum(RtfState.class,tmAppMain.getRtfState(),RtfState.K05);
		tmAppHistory = AppCommonUtil.insertApplyHist(appNo,
				OrganizationContextHolder.getUserNo(), rtf, tmAppHistory.getRefuseCode(), remark);
		if(tmAppHistory != null){
			
			if(tmAppMain != null){
				tmAppHistory.setName(tmAppMain.getName());
				tmAppHistory.setIdNo(tmAppMain.getIdNo());
				tmAppHistory.setIdType(tmAppMain.getIdType());
			}
			newTransactionalService.saveTmAppHistory(tmAppHistory);
		}
		/****************************************************更新卡面表*******************************************************/
		//卡片信息
		TmAppPrimCardInfo tmAppPrimCardInfo = applyInfoDto.getTmAppPrimCardInfo();
		if(tmAppPrimCardInfo != null){
			String cardFace = "";
			LinkedHashMap<Object, Object> map = cacheContext.getSimpleProductCardFaceLinkedMap(tmAppMain.getProductCd());
			for (Object key : map.keySet()) {
				cardFace = StringUtils.valueOf(key);
				break;
			}
			if(StringUtils.isNotEmpty(cardFace)){
				tmAppPrimCardInfo.setCardfaceCd(cardFace);
			}
		}	
		/*************************************************保存备注*******************************************************************/
		//终审备注
		TmAppMemo tmAppMemo = new TmAppMemo();
		tmAppMemo.setAppNo(tmAppMain.getAppNo());
		tmAppMemo.setMemoType(AppConstant.APP_REMARK);
		tmAppMemo.setRtfState(tmAppMain.getRtfState());
		tmAppMemo.setTaskKey(EcmsAuthority.CAS_APPLY_FINALAUDIT.name());
		tmAppMemo.setTaskDesc(EcmsAuthority.CAS_APPLY_FINALAUDIT.lab);
		tmAppMemo.setMemoInfo(remark);
		newTransactionalService.saveTmAppMemo(tmAppMemo);
		
		/*************************************************终审节点信息***********************************************************/
		ApplyNodeFinalAuditData  applyNodeFinalAuditData = new ApplyNodeFinalAuditData();
		applyNodeFinalAuditData.setFinalResult(finalResult);
		if(tmAppPrimCardInfo != null && StringUtils.isNotBlank(tmAppPrimCardInfo.getIsPrdChange()) && tmAppPrimCardInfo.getIsPrdChange().equals(Indicator.Y.name())){
			applyNodeFinalAuditData.setIfdemotionProduct(true);
		}else{
			applyNodeFinalAuditData.setIfdemotionProduct(false);
		}
		applyNodeFinalAuditData.setInnerCreditCheck(false);
		applyNodeFinalAuditData.setSendSmsForRefuse(Indicator.Y.name().equals(tmAppAudit.getIsSendSmsRefused())?true:false);
		applyNodeFinalAuditData.setProductCd(tmAppMain.getProductCd());
		//applyNodeFinalAuditData.setRemark("拒绝大类："+refuseDesc.toString()+"拒绝小类："+refuseSmallCodeDesc);
		applyNodeFinalAuditData.setRemark(refuseDesc.toString());
		//拒绝原因
		applyNodeFinalAuditData.setResultReasonList(rejectReasonList);
		//拒绝原因小类代码
		applyNodeFinalAuditData.setRefuseCodeTypeSmall(refuseCodeTypeSmall);
		/*************************************************流程公共信息***********************************************************/
		ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
		if(applyInfoDto != null && applyInfoDto.getTmAppNodeInfoRecordMap() != null){
			applyNodeCommonData = (ApplyNodeCommonData) applyInfoDto.getTmAppNodeInfoRecordMap().get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		//重新设置对象，附卡无终审
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData,tmAppMain);
		Map<String, Serializable> data = new HashMap<String, Serializable>();
		data.put("appNo", appNo);
		data.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		data.put(AppConstant.APPLY_NODE_FINAL_AUDIT_DATA, applyNodeFinalAuditData);
		nodeObjectUtil.insertAllNodeRec(data,appNo);//保存节点信息
		
		
		//提交工作流
		Map<String, Serializable> activitiMap = new HashMap<String, Serializable>();
		activitiMap.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		activitiService.completeTask(taskId, activitiMap, appNo);
	}

}
