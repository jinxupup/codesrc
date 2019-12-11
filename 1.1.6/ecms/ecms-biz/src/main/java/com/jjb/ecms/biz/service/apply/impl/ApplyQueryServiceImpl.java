package com.jjb.ecms.biz.service.apply.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.StringUtil;
import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.ecms.biz.dao.apply.TmAppContactDao;
import com.jjb.ecms.biz.dao.apply.TmAppCustInfoDao;
import com.jjb.ecms.biz.dao.apply.TmAppFlagDao;
import com.jjb.ecms.biz.dao.apply.TmAppHistoryDao;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmAppMemoDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimAnnexEviDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimCardInfoDao;
import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.biz.dao.approve.TmExtRiskInputDao;
import com.jjb.ecms.biz.dao.approve.TmExtTriggerRulesDao;
import com.jjb.ecms.biz.dao.node.TmAppNodeInfoDao;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.NodeObjectTemplate;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCheatCheckData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeExtRiskData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeFinalAuditData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeGradeProcessData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeInquiryData;
import com.jjb.ecms.facility.nodeobject.ApplyNodePatchBoltData;
import com.jjb.ecms.facility.nodeobject.ApplyNodePreCheckData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeReviewData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppFlag;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppMemo;
import com.jjb.ecms.infrastructure.TmAppNodeInfo;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmExtRiskInput;
import com.jjb.ecms.infrastructure.TmExtTriggerRules;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.thoughtworks.xstream.XStream;

/**
 *
 * @Description: 申请资料的查询，各种查询
 * @author JYData-R&D-HN
 * @date 2016年9月7日 上午11:56:04
 * @version V1.0
 */
@Service("applyQueryService")
public class ApplyQueryServiceImpl implements ApplyQueryService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TmAppNodeInfoDao nodeInforecordDao;
	@Autowired
	private TmAppMainDao mainDao;
	@Autowired
	private TmAppPrimCardInfoDao cardInfoDao;
	@Autowired
	private TmAppPrimAnnexEviDao annexEviDao;
	@Autowired
	private TmAppContactDao contactInfoDao;
	@Autowired
	private TmAppHistoryDao historyDao;
	@Autowired
	private TmAppMemoDao appMemoDao;
	@Autowired
	private TmExtRiskInputDao tmExtRiskInputDao;
	@Autowired
	private TmExtTriggerRulesDao tmExtTriggerRulesDao;
	@Autowired
	private TmAppCustInfoDao tmAppCustInfoDao;
	@Autowired
	private TmAppAuditDao tmAppAuditDao;
	@Autowired
	private TmAppFlagDao tmAppFlagDao;


	/**
	 * 根据appNo获取申请件信息
	 * @see ApplyQueryService#getApplyByAppNo(java.lang.String)
	 */
	@Override
	@Transactional

	public ApplyInfoDto getApplyInfoByAppNo(String appNo) throws ProcessException{
		long start = System.currentTimeMillis();
		String userNo=OrganizationContextHolder.getUserNo();
		logger.info("获取申请件["+appNo+"]信息开始,opUser["+userNo+"],时间戳["+start+"]");
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}

		ApplyInfoDto applyInfoDto = new ApplyInfoDto();
		applyInfoDto.setAppNo(appNo);
		//申请件主表
		TmAppMain tmAppMain = mainDao.getTmAppMainByAppNo(appNo);
		//主卡\附卡申请人信息表
		List<TmAppCustInfo> custList = getTmAppCustInfoListByAppNo(appNo);

		Map<String,TmAppCustInfo> custInfoMap = getTmAppCustInfoMapByAppNo(appNo);

		//申请主卡卡片信息表
		TmAppPrimCardInfo cardInfo = cardInfoDao.getTmAppPrimCardInfoByAppNo(appNo);
		//申请附件证明信息
		TmAppPrimAnnexEvi annexEvi = getTmAppPrimAnnexEviByAppNo(appNo);
		// 申请件历史信息
		List<TmAppHistory> historyList =  historyDao.getTmAppHistoryByAppNo(appNo);
		//封装联系人数据
		Map<String, TmAppContact> contactInfoMap = getTmAppContactByAppNo(appNo);
//		//分期信息
//		TmAppInstalLoan appInstalLoan = appInstalLoanDao.queryByAppNo(appNo);
		TmAppAudit audit = getTmAppAuditByAppNo(appNo);
		if(audit==null){
			audit = new TmAppAudit();
		}
		TmAppMemo appMemo = new TmAppMemo();
		appMemo.setAppNo(appNo);
		List<TmAppMemo> appMemoList = getTmAppMemoByParam(appMemo);
		Map<String,List<TmAppMemo>> tmAppMemoMapAll = setTmAppMemoInfoAll(appMemoList);
		Map<String,TmAppMemo> tmAppMemoMapLast = setTmAppMemoInfoLast(appMemoList);
		//申请件工作流节点信息列表
		List<TmAppNodeInfo> nodeInfoList = nodeInforecordDao.getTmAppNodeInfoList(appNo);
		Map<String, Serializable> nodeInfoMap = setNodeInfo(nodeInfoList, appNo);
		applyInfoDto.setTmAppMain(tmAppMain);
		applyInfoDto.setTmAppAudit(audit);
		applyInfoDto.setTmAppCustInfoMap(custInfoMap);
		applyInfoDto.setTmAppCustInfoList(custList);
		applyInfoDto.setTmAppPrimCardInfo(cardInfo);
		applyInfoDto.setTmAppPrimAnnexEvi(annexEvi);
		applyInfoDto.setTmAppContactMap(contactInfoMap);
		applyInfoDto.setTmAppHistoryList(historyList);
		applyInfoDto.setTmAppMemoMapAll(tmAppMemoMapAll);
		applyInfoDto.setTmAppMemoMapLast(tmAppMemoMapLast);
		applyInfoDto.setTmAppNodeInfoRecordMap(nodeInfoMap);
//		applyInfoDto.setTmAppInstalLoan(appInstalLoan);
		logger.info("获取申请件["+appNo+"]信息结束,opUser["+userNo+"],时间戳["+start+"],耗时["+(System.currentTimeMillis()-start)+"]");
		return applyInfoDto;

	}

	/**
	 * 封装备注数据
	 * @param appMemoList
	 * @return
	 */
	private Map<String, List<TmAppMemo>> setTmAppMemoInfoAll(List<TmAppMemo> appMemoList) {
		Map<String,List<TmAppMemo>> tmAppMemoMap = new HashMap<String, List<TmAppMemo>>();
		if(CollectionUtils.isNotEmpty(appMemoList)){
			List<TmAppMemo> list2 = new ArrayList<TmAppMemo>();
			for (int i = 0; i < appMemoList.size(); i++) {
				TmAppMemo appMemo2 = appMemoList.get(i);
				String key = appMemo2.getMemoType()+"-"+appMemo2.getTaskKey();
				if(tmAppMemoMap.containsKey(key)){
					list2 = tmAppMemoMap.get(key);
					list2.add(appMemo2);
					tmAppMemoMap.put(key, list2);
				}else{
					list2 = new ArrayList<TmAppMemo>();
					list2.add(appMemo2);
					tmAppMemoMap.put(key, list2);
				}
			}
		}
		return tmAppMemoMap;
	}

	/**
	 * 封装备注数据
	 * @param appMemoList
	 * @return
	 */
	private Map<String, TmAppMemo> setTmAppMemoInfoLast(List<TmAppMemo> appMemoList) {
		Map<String,TmAppMemo> tmAppMemoMap = new HashMap<String, TmAppMemo>();
		if(CollectionUtils.isNotEmpty(appMemoList)){
			for (int i = 0; i < appMemoList.size(); i++) {
				TmAppMemo appMemo2 = appMemoList.get(i);
				if(appMemo2.getMemoVersion()==null){
					appMemo2.setMemoVersion(0);
				}
				String key = appMemo2.getMemoType()+"_"+appMemo2.getTaskKey();
				if(tmAppMemoMap.containsKey(key)){
					TmAppMemo appMemo3 = tmAppMemoMap.get(key);
					if(appMemo3.getMemoVersion()==null){
						appMemo3.setMemoVersion(0);
					}
					if(appMemo3.getMemoVersion()<appMemo2.getMemoVersion()){
						tmAppMemoMap.put(key, appMemo2);
					}
				}else{
					tmAppMemoMap.put(key, appMemo2);
				}
			}
		}
		return tmAppMemoMap;
	}

	/**
	 * 根据appNo获取节点信息
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	@Override
	@Transactional

	public ApplyInfoDto getNodeInfoByAppNo(String appNo)
			throws ProcessException {
		ApplyInfoDto applyInfoDto = new ApplyInfoDto();
		//申请件工作流节点信息列表
		List<TmAppNodeInfo> nodeInfoList = nodeInforecordDao.getTmAppNodeInfoList(appNo);
		Map<String, Serializable> nodeInfoMap = setNodeInfo(nodeInfoList, appNo);
		applyInfoDto.setTmAppNodeInfoRecordMap(nodeInfoMap);
		applyInfoDto.setAppNo(appNo);
		return applyInfoDto;
	}
	/**
	 * 根据appNo 与 节点类型 获取节点信息
	 * @param appNo
	 * @param nodeType
	 * @return
	 * @throws ProcessException
	 */
	@Override
	@Transactional

	public Map<String, Serializable> getNodeInfoByAppNo(String appNo,String nodeType) throws ProcessException {
		//申请件工作流节点信息列表
		List<TmAppNodeInfo> nodeInfoList = nodeInforecordDao.getTmAppNodeInfoList(appNo,nodeType);
		Map<String, Serializable> nodeInfoMap = setNodeInfo(nodeInfoList, appNo);
		return nodeInfoMap;
	}
	/**
	 * 根据appNo 与 节点类型 获取节点信息
	 * @param appNo
	 * @param nodeType
	 * @return List<TmAppNodeInfo>
	 * @throws ProcessException
	 */
	@Override
	@Transactional
	public List<Map<String, Serializable>> getNodeInfosByAppNo(String appNo,String nodeType) {
		//申请件工作流节点信息列表
		List<TmAppNodeInfo> nodeInfoList = nodeInforecordDao.getTmAppNodeInfoList(appNo,nodeType);
		List<Map<String, Serializable>> nodeList = new ArrayList<>();
		for (int i = 0; i <nodeInfoList.size(); i++) {
			List<TmAppNodeInfo> nodeInfoList2 = new ArrayList<>();
			nodeInfoList2.add(nodeInfoList.get(i));
			Map<String, Serializable> nodeInfoMap = setNodeInfo(nodeInfoList2, appNo);
			nodeList.add(nodeInfoMap);
		}
		return nodeList;
	}
	/**
	 * @param tmAppNodeInforecordList
	 * @return
	 */
	private Map<String, Serializable> setNodeInfo(
			List<TmAppNodeInfo> tmAppNodeInforecordList, String appNo) {
		Map<String, Serializable> resultMap = new HashMap<String, Serializable>();
		if(CollectionUtils.isNotEmpty(tmAppNodeInforecordList)){
			// 申请节点信息
			for (TmAppNodeInfo tmAppNodeInforecord : tmAppNodeInforecordList) {
				if(tmAppNodeInforecord==null || StringUtils.isEmpty(tmAppNodeInforecord.getContent())){
					continue;
				}
				// 复核节点信息
				if (EnumsActivitiNodeType.A005.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodeReviewData> templete = new NodeObjectTemplate<ApplyNodeReviewData>();
					try {
						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodeReviewData> objList = (NodeObjectTemplate<ApplyNodeReviewData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_REVIEW_DATA, (ApplyNodeReviewData)column.get(0));
					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodeReviewData-Node-XML数据:["+content+"]");
					}
					continue;
				}

				// 申请资料验证信息
//				if (EnumsActivitiNodeType.A010.toString().equals(tmAppNodeInforecord.getInfoType())) {
//					NodeObjectTemplate<ApplyNodeInfoValidityData> templete = new NodeObjectTemplate<ApplyNodeInfoValidityData>();
//					try {
//						XStream xStream = NodeObjectUtil.getXstream(templete);
//						NodeObjectTemplate<ApplyNodeInfoValidityData> objList = (NodeObjectTemplate<ApplyNodeInfoValidityData>)xStream.fromXML(tmAppNodeInforecord.getContent());
//						List<?> column = objList.getNodeObject();
//						resultMap.put(AppConstant.APPLY_NODE_INFO_VALIDITY_DATA, (ApplyNodeInfoValidityData)column.get(0));
//					} catch (Exception e) {
//						String content = "";
//						if(tmAppNodeInforecord.getContent()!=null){
//							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
//						}
//						logger.error("无法解析申请件["+appNo+"]的ApplyNodeInfoValidityData-Node-XML数据:["+content+"]");
//					}
//					continue;
//				}

				// 申请欺诈调查结果信息
				if (EnumsActivitiNodeType.A020.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodeCheatCheckData> templete = new NodeObjectTemplate<ApplyNodeCheatCheckData>();
					try {

						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodeCheatCheckData> objList = (NodeObjectTemplate<ApplyNodeCheatCheckData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA, (ApplyNodeCheatCheckData)column.get(0));
					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodeCheatCheckData-Node-XML数据:["+content+"]");
					}
					continue;
				}

				// 初审调查节点信息
				if (EnumsActivitiNodeType.A025.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodeInquiryData> templete = new NodeObjectTemplate<ApplyNodeInquiryData>();
					try {

						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodeInquiryData> objList = (NodeObjectTemplate<ApplyNodeInquiryData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_INQUIRY_DATA, (ApplyNodeInquiryData)column.get(0));
					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodeInquiryData-Node-XML数据:["+content+"]");
					}
					continue;
				}

				// 初审电话调查信息
				if (EnumsActivitiNodeType.A030.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodeTelCheckBisicData> templete = new NodeObjectTemplate<ApplyNodeTelCheckBisicData>();
					try {

						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodeTelCheckBisicData> objList = (NodeObjectTemplate<ApplyNodeTelCheckBisicData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA, (ApplyNodeTelCheckBisicData)column.get(0));
					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodeTelCheckBisicData-Node-XML数据:["+content+"]");
					}
					continue;
				}

				// 初审欺诈调查(电话调查)信息
				if (EnumsActivitiNodeType.A035.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodeTelCheckBisicData> templete = new NodeObjectTemplate<ApplyNodeTelCheckBisicData>();
					try {
						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodeTelCheckBisicData> objList = (NodeObjectTemplate<ApplyNodeTelCheckBisicData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA, (ApplyNodeTelCheckBisicData)column.get(0));

					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodeTelCheckBisicData-Node-XML数据:["+content+"]");
					}
					continue;
				}

				// 补件信息
				if (EnumsActivitiNodeType.A040.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodePatchBoltData> templete = new NodeObjectTemplate<ApplyNodePatchBoltData>();
					try {

						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodePatchBoltData> objList = (NodeObjectTemplate<ApplyNodePatchBoltData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_PATCHBOLT_DATA, (ApplyNodePatchBoltData)column.get(0));
					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodePatchBoltData-Node-XML数据:["+content+"]");
					}
					continue;
				}

				// 终审调查信息
				if (EnumsActivitiNodeType.A045.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodeFinalAuditData> templete = new NodeObjectTemplate<ApplyNodeFinalAuditData>();
					try {

						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodeFinalAuditData> objList = (NodeObjectTemplate<ApplyNodeFinalAuditData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_FINAL_AUDIT_DATA, (ApplyNodeFinalAuditData)column.get(0));
					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodeFinalAuditData-Node-XML数据:["+content+"]");
					}
					continue;
				}

				// 评分过程信息
				if (EnumsActivitiNodeType.A065.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodeGradeProcessData> templete = new NodeObjectTemplate<ApplyNodeGradeProcessData>();
					try {

						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodeGradeProcessData> objList = (NodeObjectTemplate<ApplyNodeGradeProcessData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_GRADE_PROCESS_DATA, (ApplyNodeGradeProcessData)column.get(0));
					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodeGradeProcessData-Node-XML数据:["+content+"]");
					}
					continue;
				}
				// 公共流程信息
				if (EnumsActivitiNodeType.A070.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodeCommonData> templete = new NodeObjectTemplate<ApplyNodeCommonData>();
					try {
						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodeCommonData> objList = (NodeObjectTemplate<ApplyNodeCommonData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_COMMON_DATA, (ApplyNodeCommonData)column.get(0));
					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodeCommonData-Node-XML数据:["+content+"]");
					}
					continue;
				}
				//外部风控决策
				if (EnumsActivitiNodeType.A080.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodeExtRiskData> templete = new NodeObjectTemplate<ApplyNodeExtRiskData>();
					try {
						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodeExtRiskData> objList = (NodeObjectTemplate<ApplyNodeExtRiskData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_EXT_RISK_DATA, (ApplyNodeExtRiskData)column.get(0));
					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodeExtRiskData-Node-XML数据:["+content+"]");
					}
					continue;
				}
				//预审
				if (EnumsActivitiNodeType.A085.toString().equals(tmAppNodeInforecord.getInfoType())) {
					NodeObjectTemplate<ApplyNodePreCheckData> templete = new NodeObjectTemplate<ApplyNodePreCheckData>();
					try {
						XStream xStream = NodeObjectUtil.getXstream(templete);
						NodeObjectTemplate<ApplyNodePreCheckData> objList = (NodeObjectTemplate<ApplyNodePreCheckData>)xStream.fromXML(tmAppNodeInforecord.getContent());
						List<?> column = objList.getNodeObject();
						resultMap.put(AppConstant.APPLY_NODE_PRE_CHECK_DATA, (ApplyNodePreCheckData)column.get(0));
					} catch (Exception e) {
						String content = "";
						if(tmAppNodeInforecord.getContent()!=null){
							content = StringUtils.stripNonValidCharacters(tmAppNodeInforecord.getContent());
						}
						logger.error("无法解析申请件["+appNo+"]的ApplyNodePreCheckData-Node-XML数据:["+content+"]");
					}
					continue;
				}

			}
		}
		return resultMap;
	}
	/**
	 * 根据appNo获取申请件联系人信息表
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	public Map<String, TmAppContact> getTmAppContactByAppNo(String appNo) throws ProcessException{
		if(StringUtils.isEmpty(appNo)){
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");
		}
		List<TmAppContact> contactInfoList = contactInfoDao.getTmAppContactListByAppNo(appNo);
		Map<String, TmAppContact> contactInfoMap = new HashMap<String, TmAppContact>();
		if(contactInfoList!=null && contactInfoList.size()>0){
			boolean rs1 = false;
			for (int i = 0; i < contactInfoList.size(); i++) {
				TmAppContact ci = contactInfoList.get(i);
				if(StringUtils.isNotEmpty(ci.getContactType())){
					rs1=true;
					contactInfoMap.put(AppConstant.M_CON_ITEM_INFO_PREFIX+ci.getContactType(), ci);
				}
			}
			if(!rs1){
				contactInfoMap.put(AppConstant.M_CON_ITEM_INFO_PREFIX+"1", contactInfoList.get(0));
				//如果只有两条条记录，则只写入其他联系人（第二联系人）
				if ( contactInfoList.size()>1) {
					contactInfoMap.put(AppConstant.M_CON_ITEM_INFO_PREFIX+"2", contactInfoList.get(1));
				}
			}
		}
		return contactInfoMap;
	}

	/**
	 * 根据appNo获取申请件主表信息
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	@Override
	@Transactional

	public TmAppMain getTmAppMainByAppNo(String appNo) throws ProcessException{
		logger.info("获取TmAppMain信息"+ LogPrintUtils.printAppNoLog(appNo, null,null));
		if(appNo==null || appNo.trim().equals("")){
			return null;
		}
		return mainDao.getTmAppMainByAppNo(appNo);
	}

	/**
	 * 根据参数获取备注备忘信息
	 * @param appMemo
	 * @return
	 */
	@Override
	@Transactional

	public List<TmAppMemo> getTmAppMemoByParam(TmAppMemo appMemo) {
		return appMemoDao.getTmAppMemoByAppNo(appMemo);
	}
	/**
	 * 根据申请件编号获取申请件主卡卡片信息及申请件其他信息表数据
	 * @param appNo
	 * @return
	 */
	public TmAppPrimCardInfo getTmAppPrimCardInfoByAppNo(String appNo){

		return cardInfoDao.getTmAppPrimCardInfoByAppNo(appNo);
	}

//	/**
//	 * 分期系信息查询
//	 */
//	@Override
//	@Transactional
//
//	public TmAppInstalLoan getTmAppInstalLoanByAppNo(String appNo) {
//		// TODO Auto-generated method stub
//		return appInstalLoanDao.queryByAppNo(appNo);
//	}

	/**
	 * 第三方风控信息查询
	 */
	@Override
	@Transactional

	public TmExtRiskInput getTmExtRiskInputByAppNo(String appNo) {
		return tmExtRiskInputDao.getTmExtRiskInputByAppNo(appNo);
	}

	/**
	 * 第三方风控处罚规则（标签）明细查询
	 */
	@Override
	@Transactional

	public Map<String, TmExtTriggerRules> getTmExtTriggerRulesByAppNo(
			String appNo) {
		List<TmExtTriggerRules> list = tmExtTriggerRulesDao.getTmExtTriggerRulesByAppNo(appNo);
		Map<String,TmExtTriggerRules> map = new HashMap<String, TmExtTriggerRules>();
		if(CollectionUtils.isNotEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				TmExtTriggerRules rules = list.get(i);
				if(rules!=null){
					if(StringUtils.isEmpty(rules.getRuleType())){//如果规则类型为空，则赋值：unknown
						rules.setRuleType("unknown"+i);
					}
					map.put(rules.getRuleType(), rules);
				}
			}
			return map;
		}
		return null;
	}

	/**
	 * 	获取在同卡同申的时候已经通过预审的父类申请件
	 * @return
	 */
	@Override
	public List<TmAppMain> getApplyJobPreChecked() {

		return  mainDao.getApplyJobPreChecked();
	}

    /**
     * 多卡同申时查询满足taskNum字段条件的申请件
     * @param taskNum
     * @return
     */
    @Override
    public List<TmAppMain> getApplyJobToPreCheck(String taskNum) {
        return mainDao.getApplyJobToPreCheck(taskNum);
    }

    /**
	 * 根据appNo获取申请件附件信息表
	 * @param appNo
	 * @return
	 * @throws ProcessException
	 */
	@Override
	@Transactional

	public TmAppPrimAnnexEvi getTmAppPrimAnnexEviByAppNo(String appNo)
			throws ProcessException {
		if(StringUtils.isEmpty(appNo)){
			throw new ProcessException("申请件["+appNo+"]为空无法查询附件信息，请重新再试!");
		}
		return annexEviDao.getTmAppPrimAnnexEviByAppNo(appNo);
	}

	/**
	 * 获取所有主附卡客户信息申请LIST清单
	 */
	@Override
	@Transactional

	public List<TmAppCustInfo> getTmAppCustInfoListByAppNo(String appNo)
			throws ProcessException {
		return tmAppCustInfoDao.getTmAppCustInfoList(appNo,null,null);
	}

	/**
	 * 获取所有主附卡客户信息申请MAP集合
	 */
	@Override
	@Transactional

	public Map<String, TmAppCustInfo> getTmAppCustInfoMapByAppNo(String appNo)
			throws ProcessException {
		List<TmAppCustInfo> list = getTmAppCustInfoListByAppNo(appNo);
		Map<String,TmAppCustInfo> custInfoMap = new HashMap<String,TmAppCustInfo>();
		if(CollectionUtils.isNotEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				TmAppCustInfo cust = list.get(i);
				custInfoMap.put(cust.getBscSuppInd()+StringUtils.setValue(cust.getAttachNo(), "1"), cust);
			}
		}
		return custInfoMap;
	}

	/**
	 * 获取主卡申请人信息
	 */
	@Override
	@Transactional

	public TmAppCustInfo getTmAppPrimCustByAppNo(String appNo)
			throws ProcessException {
		return tmAppCustInfoDao.getTmAppCustInfo(appNo, null, AppConstant.bscSuppInd_B);
	}
	/**
	 * 获取附卡申请集合
	 */
	@Override
	@Transactional

	public Map<String, TmAppCustInfo> getTmAppAttachCustInfoMapByAppNo(
			String appNo) throws ProcessException {
		List<TmAppCustInfo> list = tmAppCustInfoDao.getTmAppCustInfoList(appNo, null, AppConstant.bscSuppInd_S);
		Map<String, TmAppCustInfo> map = new HashMap<String, TmAppCustInfo>();
		if(CollectionUtils.isNotEmpty(list)){
			for (int i = 0; i < list.size(); i++) {
				TmAppCustInfo cust = list.get(i);
				map.put(cust.getBscSuppInd()+cust.getAttachNo(), cust);
			}
		}
		return map;
	}
	/**
	 * 获取附卡申请list
	 */
	@Override
	@Transactional

	public List<TmAppCustInfo> getTmAppAttachCustInfoListByAppNo(
			String appNo) throws ProcessException {
		return tmAppCustInfoDao.getTmAppCustInfoList(appNo, null, AppConstant.bscSuppInd_S);
	}
	/**
	 * 根据申请将编号获取进件审计信息
	 */
	@Override
	@Transactional

	public TmAppAudit getTmAppAuditByAppNo(String appNo)
			throws ProcessException {
		return tmAppAuditDao.getTmAppAuditByAppNo(appNo);
	}
	/**
	 * 根据参数查询主表数据
	 */
	@Override
	@Transactional
	public List<TmAppMain> getTmAppMainByParm(Map<String, Object> parameter) {
		return mainDao.getTmAppMainByParam(parameter);
	}
	/**
	 * 根据特定条件查询审批任务
	 * @param page
	 * @return
	 */
	@Override
	public Page<TmAppMain> getTheNumberOfTask(Page<TmAppMain> page) {
		if(null == page.getQuery()){
			page.setQuery(new Query());
		}
		return mainDao.getTheNumberOfTask( page);
	}
	/**
     * 查找申请件标签
     *
     * @param appNo 申请件编号
     * @return
     */
    @Override
    public List<TmAppFlag> getTmAppFlagListByAppNo(String appNo) {
        if (StringUtil.isNotEmpty(appNo)) {
            List<TmAppFlag> tmAppFlagListByAppNo = null;
            try {
                tmAppFlagListByAppNo = tmAppFlagDao.getTmAppFlagListByAppNo(appNo);
            } catch (Exception e) {
                logger.error(LogPrintUtils.printAppNoLog(appNo, null, null)+"申请件标签查询异常.",e);
                throw new ProcessException("申请件标签查询异常"+e.getMessage());
            }
            return tmAppFlagListByAppNo;
        } else {
            logger.error("查询申请件标签失败, appNo为空！");
            throw new ProcessException("申请件编号不能为空!");
        }
    }
    /**
     * 根据参数查询申请件标签
     * @param tmAppFlag
     * @return
     */
    @Override
    public Map<String,TmAppFlag> getTmAppFlagByParm(TmAppFlag tmAppFlag) {
    	Map<String,TmAppFlag> retMap = new HashMap<>();
    	if(tmAppFlag!=null && (tmAppFlag.getAppNo()!=null || tmAppFlag.getFlagType()!=null 
    			|| tmAppFlag.getFlagCode()!=null)) {
    		Map<String,Object> map = new HashMap<String,Object>();
    		map.put("_SORT_NAME", "flagType,flagCode");
    		map.put("_SORT_ORDER", "asc");
    		List<TmAppFlag> list = tmAppFlagDao.queryForList(tmAppFlag, map);
    		if(CollectionUtils.sizeGtZero(list)) {
    			for (int i = 0; i < list.size(); i++) {
    				TmAppFlag tf = list.get(i);
    				retMap.put(tf.getFlagType()+tf.getFlagCode(), tf);
				}
    		}
    	}
    	return retMap;
    }
    /**
     * 查询审批历史
     * @param history
     * @return
     */
    @Override
    public List<TmAppHistory> getTmAppHistoryList(TmAppHistory history) {
    	return historyDao.getTmAppHistoryList(history);
    }
    /**
     * 根据申请件编号查询审批历史
     * @param history
     * @return
     */
    @Override
    public List<TmAppHistory> getTmAppHistoryByAppNo(String appNo) {
    	return historyDao.getTmAppHistoryByAppNo(appNo);
    }
}
