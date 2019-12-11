package com.jjb.ecms.biz.service.approve.impl;

import java.io.Serializable;
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
import com.jjb.acl.facility.enums.bus.EcmsAuthority;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmAppRfeDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.approve.ApplyPatchBoltService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCheatCheckData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeInquiryData;
import com.jjb.ecms.facility.nodeobject.ApplyNodePatchBoltData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppRfe;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @description: 补件操作service实现类
 * @author -BigZ.Y
 * @date 2016年9月25日 下午12:18:55 
 */
@Service("applyPatchBoltService")
public class ApplyPatchBoltServiceImpl implements ApplyPatchBoltService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private TmAppMainDao tmAppMainDao;
	@Autowired
	private TmAppRfeDao tmAppRfeDao;
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	
	@Autowired
	private ActivitiService activitiService;
	
	/**
	 * 补件提交
	 * @param query
	 * @param status
	 */

	@Override
	@Transactional
	public void submitPatchBoltInfo(Query query, String status) {
		
		String appNo = StringUtils.valueOf(query.get("appNo"));//申请件编号
		String taskId = StringUtils.valueOf(query.get("taskId"));//任务ID
		String remark = StringUtils.valueOf(query.get("remark"));//备注
		String memo = StringUtils.valueOf(query.get("memo"));//备忘
		
		ApplyNodePatchBoltData applyNodePatchBoltData = new ApplyNodePatchBoltData();
		//反欺诈节点信息
		ApplyNodeCheatCheckData applyNodeCheatCheckData = new ApplyNodeCheatCheckData();
		//初审节点
		ApplyNodeInquiryData applyNodeInquiryData = null;
		
		if(StringUtils.isBlank(appNo)){
			throw new ProcessException("申请件编号为空!");
		}
		// 先查出所有的申请信息
//		ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
		TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
		if(tmAppMain == null){
			throw new ProcessException("找不到主表["+appNo+"]相关信息，请重新再试!");
		}
		TmAppAudit tmAppAudit = applyQueryService.getTmAppAuditByAppNo(appNo);
		Map<String, Serializable> nodeInfoRecordMap = applyQueryService.getNodeInfoByAppNo(appNo).getTmAppNodeInfoRecordMap();
		if(nodeInfoRecordMap != null){
			if(nodeInfoRecordMap.get(AppConstant.APPLY_NODE_PATCHBOLT_DATA) != null){
				applyNodePatchBoltData = (ApplyNodePatchBoltData) nodeInfoRecordMap.get(AppConstant.APPLY_NODE_PATCHBOLT_DATA);
			}
			if(nodeInfoRecordMap.get(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA) != null){
				applyNodeCheatCheckData = (ApplyNodeCheatCheckData)nodeInfoRecordMap.get(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA);
			}
			//清除初审节点补件数据
			
			if(nodeInfoRecordMap.get(AppConstant.APPLY_NODE_INQUIRY_DATA) != null){
				applyNodeInquiryData = (ApplyNodeInquiryData) nodeInfoRecordMap.get(AppConstant.APPLY_NODE_INQUIRY_DATA);
			}
			if(applyNodeInquiryData == null){
				throw new ProcessException("["+appNo+"]补件操作没找到初审补件节点信息");
			}
			if(applyNodePatchBoltData==null){
				applyNodePatchBoltData = new ApplyNodePatchBoltData();
			}
			
			applyNodePatchBoltData.setMemo(memo);
			applyNodePatchBoltData.setRemark(remark);
			
			List<String> patchList = new ArrayList<String>();//定义一个已补件集合
			String appType = tmAppMain.getAppType();
			if(StringUtils.isNotBlank(appType)){
				if(AppType.S.name().equals(appType)){
					//如果是独立附卡
					for(String enty : query.keySet()){
						//遍历出页面数据的名字
						if(enty.startsWith("patch") && query.get(enty) != null && StringUtils.valueOf(query.get(enty)).equals("on")){
							//说明是补件确认信息
							patchList.add(enty.substring(5, 6));
						}
					}
					applyNodePatchBoltData.getAttachPatchBoltList().clear();//先把之前的数据清掉
					applyNodePatchBoltData.getAttachPatchBoltList().put(AppConstant.bscSuppInd_S,patchList);
					
					if(AppConstant.SUBMIT.equals(status)){//在提交时清除初审补件信息
						applyNodeInquiryData.getAttachPatchBoltList().clear();
						
					}
				}else{
					//非独立附卡
					for(String enty : query.keySet()){
						//遍历出页面数据的名字
						if(enty.startsWith("patch") && query.get(enty) != null && StringUtils.valueOf(query.get(enty)).equals("on")){
							//说明是补件确认信息
							patchList.add(enty.substring(5, 6));
						}
					}
					applyNodePatchBoltData.getPatchBoltList().clear();//先把之前的数据清掉
					applyNodePatchBoltData.getPatchBoltList().addAll(patchList);
					
					if(AppConstant.SUBMIT.equals(status)){//在提交时清除初审补件信息
						applyNodeInquiryData.getPatchBoltList().clear();
					}
				}
			}
		}
		/*****************************************************设置历史信息****************************************************************************/		
		RtfState rtfState = null;
		if(AppConstant.SUBMIT.equals(status)){
			rtfState = RtfState.G10;
		}else if(AppConstant.SAVE.equals(status)){
			rtfState = RtfState.G05;
		}else if(AppConstant.OVER_TIME.equals(status)){
			rtfState = RtfState.G15;
		}
		TmAppHistory tmAppHistory = AppCommonUtil.insertApplyHist(appNo, OrganizationContextHolder.getUserNo(), rtfState, null, remark);
		tmAppHistory.setName(tmAppMain.getName());
		tmAppHistory.setIdNo(tmAppMain.getIdNo());
		tmAppHistory.setIdType(tmAppMain.getIdType());

		applyInputService.saveTmAppHistory(tmAppHistory);
		
		/****************************************************更新主表***********************************************************************************/
		if(tmAppMain != null ){
			//通过
			if(AppConstant.SUBMIT.equals(status)){
				tmAppMain.setRtfState(RtfState.G10.name());
			}else if(AppConstant.SAVE.equals(status)){
				tmAppMain.setRtfState(RtfState.G05.name());
			}else if(AppConstant.OVER_TIME.equals(status)){
				tmAppMain.setRtfState(RtfState.G15.name());
			}
			tmAppMain.setRemark(remark);//更新备注信息
			tmAppMain.setUpdateUser(OrganizationContextHolder.getUserNo());
			tmAppMain.setUpdateDate(new Date());
			tmAppMainDao.update(tmAppMain);
		}
//		//同步分期贷款表信息
//		if("Y".equals(tmAppAudit.getIsInstalment())){
//			TmAppInstalLoan appInstalLoan = appInstalLoanDao.queryByAppNo(tmAppMain.getAppNo());
//			if(appInstalLoan!=null){
//				appInstalLoan.setStatus(tmAppMain.getRtfState());
//				appInstalLoanDao.updateByAppNo(appInstalLoan);
//			}
//		}
		/*************************************************更新补件表状态*******************************************************************/
		if(AppConstant.SUBMIT.equals(status)){
			tmAppMain.setRtfState(RtfState.G10.name());
			TmAppRfe appRfe = new TmAppRfe();
			appRfe.setAppNo(appNo);
			appRfe = tmAppRfeDao.queryForOne(appRfe);
			if(null!=appRfe){
				appRfe.setIsOk(Indicator.Y.name());
				tmAppRfeDao.update(appRfe);
			}	
		}
		/*************************************************保存备注、备忘*******************************************************************/
		//备注
		TmAppMemo tmAppRemark = new TmAppMemo();
		tmAppRemark.setAppNo(tmAppMain.getAppNo());
		tmAppRemark.setMemoType(AppConstant.APP_REMARK);
		tmAppRemark.setRtfState(tmAppMain.getRtfState());
		tmAppRemark.setTaskKey(EcmsAuthority.CAS_APPLY_PATCHBOLT.name());
		tmAppRemark.setTaskDesc(EcmsAuthority.CAS_APPLY_PATCHBOLT.lab);
		tmAppRemark.setMemoInfo(remark);
		applyInputService.saveTmAppMemo(tmAppRemark);
		
		//备忘
		TmAppMemo tmAppMemo = new TmAppMemo();
		tmAppMemo.setAppNo(tmAppMain.getAppNo());
		tmAppMemo.setMemoType(AppConstant.APP_MEMO);
		tmAppMemo.setRtfState(tmAppMain.getRtfState());
		tmAppMemo.setTaskKey(EcmsAuthority.CAS_APPLY_PATCHBOLT.name());
		tmAppMemo.setTaskDesc(EcmsAuthority.CAS_APPLY_PATCHBOLT.lab);
		tmAppMemo.setMemoInfo(memo);
		applyInputService.saveTmAppMemo(tmAppMemo);
		
		
		
		logger.info("开始保存补件信息，插入applyNodePatchBoltData==>申请件编号：["+ appNo +"]");
		
		ApplyNodeCommonData applyNodeCommonData = null;
		if(nodeInfoRecordMap!=null && nodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA) != null){
			applyNodeCommonData = (ApplyNodeCommonData) nodeInfoRecordMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		if(applyNodeCommonData == null){
			//重新设置对象
			applyNodeCommonData = new ApplyNodeCommonData();
			applyNodeCommonData.setOrg(OrganizationContextHolder.getOrg());
			applyNodeCommonData.setAppNo(appNo);
			applyNodeCommonData.setAppType(tmAppMain.getAppType());
			applyNodeCommonData.setProductCd(tmAppMain.getProductCd());
			applyNodeCommonData.setName(tmAppMain.getName());
			applyNodeCommonData.setIdType(tmAppMain.getIdType());
			applyNodeCommonData.setIdNo(tmAppMain.getIdNo());
			applyNodeCommonData.setAppProperty(tmAppMain.getAppProperty());
			applyNodeCommonData.setApplyStatus(tmAppMain.getCurrOpResult());
			applyNodeCommonData.setRejectReason(tmAppMain.getRefuseCode());
		}
		Map<String, Serializable> data = new HashMap<String, Serializable>();
		applyNodeCommonData.setOperatorId(OrganizationContextHolder.getUserNo());
		applyNodeCommonData.setDate(new Date());
		if(AppConstant.SUBMIT.equals(status)){
			applyNodeCommonData.setRtfStateType(RtfState.G10.name());
		}else if(AppConstant.SAVE.equals(status)){
			applyNodeCommonData.setRtfStateType(RtfState.G05.name());
		}else if(AppConstant.OVER_TIME.equals(status)){
			applyNodeCommonData.setRtfStateType(RtfState.G15.name());
		}
		data.put("appNo", appNo);
		data.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		data.put(AppConstant.APPLY_NODE_PATCHBOLT_DATA, applyNodePatchBoltData);
		data.put(AppConstant.APPLY_NODE_INQUIRY_DATA, applyNodeInquiryData);//更新初审补件信息
		if(applyNodeCheatCheckData!=null){
			data.put(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA,applyNodeCheatCheckData);
		}
		nodeObjectUtil.insertAllNodeRec(data,appNo);//保存节点信息
		
		//提交工作流
		if(AppConstant.SUBMIT.equals(status) || AppConstant.OVER_TIME.equals(status)){
			Map<String, Serializable> activitiMap = new HashMap<String, Serializable>();
			activitiMap.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
			activitiService.completeTask(taskId, activitiMap, appNo);
		}
	}

	/**
	 * 获取补件发起时间及补件时长
	 * @param appNo
	 * @return
	 */
	@Override
	@Transactional
	public Map<String, String> getPatchTimeMap(String appNo) {
		// TODO Auto-generated method stub
		Map<String, String> patchTime = new HashMap<String, String>();
		TmAppRfe tmAppRfe = new TmAppRfe();
		if(StringUtils.isNotBlank(appNo)){
			tmAppRfe.setAppNo(appNo);
		}
		List<TmAppRfe> tmAppRfes = tmAppRfeDao.getTmAppRfeByParam(tmAppRfe);
		if(CollectionUtils.isNotEmpty(tmAppRfes) && tmAppRfes.get(0) != null){
			patchTime.put(AppConstant.APPLY_PB_STTIME, tmAppRfes.get(0).getPbStartTime());//补件开始时间
			patchTime.put(AppConstant.APPLY_PB_TIMEWAIT, tmAppRfes.get(0).getPbTimeWait());//补件时间间隔
		}
		return patchTime;
	}

}
