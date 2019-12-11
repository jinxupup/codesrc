/**
 * 
 */
package com.jjb.ecms.biz.service.approve.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.ecms.biz.dao.apply.TmAppHistoryDao;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimAnnexEviDao;
import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.approve.PersonCheckInfoService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 人工核查
 * @author JYData-R&D-BigK.K
 * @date 2016年9月7日 下午3:43:53
 * @version V1.0
 */
@Transactional(readOnly=false)
@Service("personCheckInfoService")
public class PersonCheckInfoServiceImpl implements PersonCheckInfoService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	@Autowired
	private TmAppAuditDao tmAppAuditDao;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyInputService applyInputService;

	/**
	 * 提交人工核查信息
	 */
	@Override
	@Transactional
	public void submitPersonCheckResult(TmAppMain main,TmAppAudit audit) {
		String taskId = main.getTaskId();
		String appNo = main.getAppNo();
		String remark = main.getRemark();//人工核查备注
		String result = main.getCurrOpResult();//人工核查操作结果
		
		applyInputService.updateTmAppMain(main);

		tmAppAuditDao.updateTmAppAudit(audit);
		
		//更新历史表数据
		TmAppHistory tmAppHistory = new TmAppHistory();
		RtfState rtf = AppCommonUtil.stringToEnum(RtfState.class, main.getRtfState(), RtfState.E12);
		tmAppHistory = AppCommonUtil.insertApplyHist(appNo, OrganizationContextHolder.getUserNo(),rtf, null, remark);
		tmAppHistory.setName(main.getName());
		tmAppHistory.setIdNo(main.getIdNo());
		tmAppHistory.setIdType(main.getIdType());
		applyInputService.saveTmAppHistory(tmAppHistory);
		ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
		Map<String, Serializable> comMap  = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A070.name());
		if(comMap!=null && comMap.containsKey(AppConstant.APPLY_NODE_COMMON_DATA)){
			applyNodeCommonData = (ApplyNodeCommonData) comMap.get(AppConstant.APPLY_NODE_COMMON_DATA);
		}
		//节点公共数据 赋值
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData,main);
		
		Map<String, Serializable> map = new HashMap<String, Serializable>();
		applyNodeCommonData.setRtfStateType(main.getRtfState());
		applyNodeCommonData.setApplyStatus(result);
		map.put("applyNodeCommonData", applyNodeCommonData);
		logger.info("提交人工核查节点信息applyNodeCommonData，==>申请件编号：[" + appNo + "]");
		nodeObjectUtil.insertAllNodeRec(map,appNo);
		if(StringUtils.equals(result, "P") || StringUtils.concat(result, "B")){
			activitiService.completeTask(taskId, map, appNo);
		}
		
	}
}
