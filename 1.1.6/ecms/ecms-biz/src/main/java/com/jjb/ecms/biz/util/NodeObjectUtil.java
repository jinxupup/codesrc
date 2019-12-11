package com.jjb.ecms.biz.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.ecms.biz.dao.node.TmAppNodeInfoDao;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCheatCheckData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeExtDecisionData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeFinalAuditData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeGradeProcessData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeInquiryData;
import com.jjb.ecms.facility.nodeobject.ApplyNodePatchBoltData;
import com.jjb.ecms.facility.nodeobject.ApplyNodePreCheckData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeReviewData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.infrastructure.TmAppNodeInfo;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * @Description: 操作XML公共方法类
 * @author JYData-R&D-BigK.K
 * @date 2016年9月1日 上午11:01:42
 * @version V1.0
 */

@Component
public class NodeObjectUtil {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private XStream xStream = new XStream(new DomDriver());

//	@Autowired
//	private ApplyQueryService applyQueryService;
	@Autowired
	private TmAppNodeInfoDao tmAppNodeInforecordDao;
	public static XStream getXstream(NodeObjectTemplate<?> template) {

		XStream xStream = new XStream(new DomDriver());
		xStream.alias("xml", template.getClass());
		return xStream;
	}

	/**
	 * 保存每个节点结束时的节点对象到TM_APP_NODE_INFORECORD
	 */
	@Transactional
	public void insertNewTmAppNodeInfo(NodeObjectTemplate<?> template,List<?> columnlist, Object param,
			String appNo,EnumsActivitiNodeType nodeType,List<TmAppNodeInfo> appNodeInforecords) {
		logger.info("新增节点信息，详细记录参看xml数据");
		template.setNodeObject(columnlist);
		xStream.alias("xml", template.getClass());
		if (logger.isDebugEnabled())
			logger.debug("详细xml数据如下：" + encode(template));
		boolean flag=true;//标志
		TmAppNodeInfo tmAppNodeInforecord = new TmAppNodeInfo();
		if (appNodeInforecords != null && appNodeInforecords.size() > 0) {
			for (TmAppNodeInfo appNodeInfo : appNodeInforecords) {
				if (appNodeInfo!=null && appNodeInfo.getInfoType().equals(nodeType.toString())
						&& !StringUtils.equals(nodeType.toString(), EnumsActivitiNodeType.A085.toString())
						&& !StringUtils.equals(nodeType.toString(), EnumsActivitiNodeType.A030.toString())) {
					flag=false;
					appNodeInfo.setOrg(OrganizationContextHolder.getOrg());
					appNodeInfo.setAppNo(appNo);
					appNodeInfo.setInfoType(StringUtils.valueOf(nodeType));
					appNodeInfo.setContent(encode(template));
					appNodeInfo.setOperatorId(OrganizationContextHolder.getUserNo());// 操作员ID
					appNodeInfo.setSetupDate(new Date());// 处理日期
					tmAppNodeInforecordDao.updateTmAppNodeInfo(appNodeInfo);
				}
			}
			if(flag){
				tmAppNodeInforecord.setAppNo(appNo);
				tmAppNodeInforecord.setOrg(OrganizationContextHolder.getOrg());
				tmAppNodeInforecord.setInfoType(StringUtils.valueOf(nodeType));
				tmAppNodeInforecord.setContent(encode(template));
				tmAppNodeInforecord.setOperatorId(OrganizationContextHolder.getUserNo());// 操作员ID
				tmAppNodeInforecord.setSetupDate(new Date());// 处理日期
				tmAppNodeInforecordDao.saveTmAppNodeInfo(tmAppNodeInforecord);
			}

		} else {
			tmAppNodeInforecord.setAppNo(appNo);
			tmAppNodeInforecord.setOrg(OrganizationContextHolder.getOrg());
			tmAppNodeInforecord.setInfoType(StringUtils.valueOf(nodeType));
			tmAppNodeInforecord.setContent(encode(template));
			tmAppNodeInforecord.setOperatorId(OrganizationContextHolder.getUserNo());// 操作员ID
			tmAppNodeInforecord.setSetupDate(new Date());// 处理日期
			tmAppNodeInforecordDao.saveTmAppNodeInfo(tmAppNodeInforecord);
		}

	}

	/**
	 * 插入XML
	 *
	 * @param obj
	 * @return
	 */
	public String encode(Object obj) {
		return xStream.toXML(obj);
	}

	/**
	 * 获取XML
	 *
	 * @param
	 * @return
	 */
	public Object decode(String data) {
		return xStream.fromXML(data);
	}

	/**
	 * 节点保存
	 *
	 */
	public void insertAllNodeRec(Map<String, Serializable> data,String appNo) throws ProcessException{
		logger.info("插入节点信息，==>");
		ApplyInfoDto applyInfoDto = (ApplyInfoDto) data.get(AppConstant.APPLY_INFO_DTO);
		ApplyNodeReviewData applyNodeReviewData = (ApplyNodeReviewData) data.get(AppConstant.APPLY_NODE_REVIEW_DATA);
		ApplyNodeInquiryData applyNodeInquiryData = (ApplyNodeInquiryData)data.get(AppConstant.APPLY_NODE_INQUIRY_DATA);
		ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData =(ApplyNodeTelCheckBisicData)data.get(AppConstant.APPLY_NODE_TEL_CHECK_BISIC_DATA);
		ApplyNodeCheatCheckData applyNodeCheatCheckData =(ApplyNodeCheatCheckData)data.get(AppConstant.APPLY_NODE_CHEAT_CHECK_DATA);
		ApplyNodeFinalAuditData applyNodeFinalAuditData =(ApplyNodeFinalAuditData)data.get(AppConstant.APPLY_NODE_FINAL_AUDIT_DATA);
		ApplyNodeGradeProcessData applyNodeGradeProcessData =(ApplyNodeGradeProcessData)data.get(AppConstant.APPLY_NODE_GRADE_PROCESS_DATA);
//		ApplyNodeInfoValidityData applyNodeInfoValidityData =(ApplyNodeInfoValidityData)data.get(AppConstant.APPLY_NODE_INFO_VALIDITY_DATA);
		ApplyNodePatchBoltData applyNodePatchBoltData =(ApplyNodePatchBoltData)data.get(AppConstant.APPLY_NODE_PATCHBOLT_DATA);
//		ApplyNodeRuleEngineObj applyNodeRuleEngineObj =(ApplyNodeRuleEngineObj)data.get(AppCommonConstant.APPLY_NODE_RULE_ENGINE_OBJ);
		ApplyNodeCommonData applyNodeCommonData = (ApplyNodeCommonData) data.get(AppConstant.APPLY_NODE_COMMON_DATA);
		ApplyNodePreCheckData applyNodePreCheckData = (ApplyNodePreCheckData) data.get(AppConstant.APPLY_NODE_PRE_CHECK_DATA);
		ApplyNodeExtDecisionData extDecision = (ApplyNodeExtDecisionData) data.get(AppConstant.APPLY_NODE_EXT_RISK);
		//如果appNo为空就不保存
		if(StringUtils.isNotEmpty(appNo)){
			List<TmAppNodeInfo> nodeInfos = tmAppNodeInforecordDao.getTmAppNodeInfoList(appNo);

			// 复核节点信息
			if (applyNodeReviewData != null) {
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodeReviewData>();
				List<ApplyNodeReviewData> columnlist = new ArrayList<ApplyNodeReviewData>();
				columnlist.add(applyNodeReviewData);
				insertNewTmAppNodeInfo(template, columnlist,applyNodeReviewData, appNo, EnumsActivitiNodeType.A005,nodeInfos);
			}
			 // 终审调查信息
			if (applyNodeFinalAuditData != null) {
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodeFinalAuditData>();
				List<ApplyNodeFinalAuditData> columnlist = new ArrayList<ApplyNodeFinalAuditData>();
				columnlist.add(applyNodeFinalAuditData);
				insertNewTmAppNodeInfo(template, columnlist, applyNodeFinalAuditData, appNo, EnumsActivitiNodeType.A045, nodeInfos);
			}

			 // 补件信息
			if (applyNodePatchBoltData != null) {
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodePatchBoltData>();
				List<ApplyNodePatchBoltData> columnlist = new ArrayList<ApplyNodePatchBoltData>();
				columnlist.add(applyNodePatchBoltData);
				insertNewTmAppNodeInfo(template, columnlist,applyNodePatchBoltData, appNo, EnumsActivitiNodeType.A040,nodeInfos);
			}

			// 申请资料校验信息
//			if (applyNodeInfoValidityData != null) {
//				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodeInfoValidityData>();
//				List<ApplyNodeInfoValidityData> columnlist = new ArrayList<ApplyNodeInfoValidityData>();
//				columnlist.add(applyNodeInfoValidityData);
//				insertNewTmAppNodeInfo(template, columnlist,applyNodeInfoValidityData, appNo,EnumsActivitiNodeType.A010,nodeInfos);
//			}

			//预审确认结果信息
			if (applyNodePreCheckData != null) {
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodePreCheckData>();
				List<ApplyNodePreCheckData> columnlist = new ArrayList<ApplyNodePreCheckData>();
				columnlist.add(applyNodePreCheckData);
				insertNewTmAppNodeInfo(template, columnlist,applyNodePreCheckData, appNo,EnumsActivitiNodeType.A085,nodeInfos);
			}
			//受理外部决策结果信息
			if (extDecision != null) {
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodeExtDecisionData>();
				List<ApplyNodeExtDecisionData> columnlist = new ArrayList<ApplyNodeExtDecisionData>();
				columnlist.add(extDecision);
				insertNewTmAppNodeInfo(template, columnlist,extDecision, appNo,EnumsActivitiNodeType.A090,nodeInfos);
			}
			// 申请欺诈调查结果信息
			if (applyNodeCheatCheckData != null) {
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodeCheatCheckData>();
				List<ApplyNodeCheatCheckData> columnlist = new ArrayList<ApplyNodeCheatCheckData>();
				columnlist.add(applyNodeCheatCheckData);
				insertNewTmAppNodeInfo(template, columnlist,applyNodeCheatCheckData, appNo,EnumsActivitiNodeType.A020,nodeInfos);
			}
			// 初审调查节点信息
			if (applyNodeInquiryData != null) {
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodeInquiryData>();
				List<ApplyNodeInquiryData> columnlist = new ArrayList<ApplyNodeInquiryData>();
				columnlist.add(applyNodeInquiryData);
				insertNewTmAppNodeInfo(template, columnlist,applyNodeInquiryData, appNo, EnumsActivitiNodeType.A025,nodeInfos);
			}

			// 初审电话调查信息
			if (applyNodeTelCheckBisicData != null) {

			//现在电调信息不需叠加，直接页面覆盖（H.N）
	//			ApplyNodeTelCheckBisicData telCheckBisicData  =  (ApplyNodeTelCheckBisicData) applyQueryService.getNodeInfoByAppNo(appNo).getTmAppNodeInfoRecordMap().get("applyNodeTelcheckbisicData");
	//
	//			if(telCheckBisicData!=null){
	//			List<ApplyTelInquiryRecordItem> list =  telCheckBisicData.getTelInquiryRecordItemList();
	//			applyNodeTelCheckBisicData.getTelInquiryRecordItemList().addAll(list);
	//			}
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodeTelCheckBisicData>();
				List<ApplyNodeTelCheckBisicData> columnlist = new ArrayList<ApplyNodeTelCheckBisicData>();
				columnlist.add(applyNodeTelCheckBisicData);

				insertNewTmAppNodeInfo(template, columnlist,applyNodeTelCheckBisicData, appNo,EnumsActivitiNodeType.A030,nodeInfos);
			}

			// 评分过程信息
			if (applyNodeGradeProcessData != null) {
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodeGradeProcessData>();
				List<ApplyNodeGradeProcessData> columnlist = new ArrayList<ApplyNodeGradeProcessData>();
				columnlist.add(applyNodeGradeProcessData);
				insertNewTmAppNodeInfo(template, columnlist,applyNodeGradeProcessData, appNo,EnumsActivitiNodeType.A065, nodeInfos);
			}
			 // 申请流程公共信息
			if (applyNodeCommonData != null) {
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodeCommonData>();
				List<ApplyNodeCommonData> columnlist = new ArrayList<ApplyNodeCommonData>();
				columnlist.add(applyNodeCommonData);
				insertNewTmAppNodeInfo(template, columnlist,applyNodeCommonData, appNo, EnumsActivitiNodeType.A070,nodeInfos);
			}

			// 申请人基本信息
			if (applyInfoDto != null) {
				NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyInfoDto>();
				List<ApplyInfoDto> columnlist = new ArrayList<ApplyInfoDto>();
				columnlist.add(applyInfoDto);
				insertNewTmAppNodeInfo(template, columnlist, applyInfoDto,appNo, EnumsActivitiNodeType.A075,nodeInfos);
			}

			// 定义规则变量
	//		if (applyNodeRuleEngineObj != null) {
	//			NodeObjectTemplate<?> template = new NodeObjectTemplate<ApplyNodeRuleEngineObj>();
	//			List<ApplyNodeRuleEngineObj> columnlist = new ArrayList<ApplyNodeRuleEngineObj>();
	//			columnlist.add(applyNodeRuleEngineObj);
	//			insertNewTmAppNodeInfo(template, columnlist,
	//					applyNodeRuleEngineObj, appNo, EnumsActivitiNodeType.A080);
	//		}
		}else{
			logger.info("申请件编号为空,请刷新！");
			throw new ProcessException("申请件编号为空，请刷新！");

		}
	}
}
