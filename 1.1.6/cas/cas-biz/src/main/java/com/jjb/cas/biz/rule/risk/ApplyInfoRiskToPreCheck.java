package com.jjb.cas.biz.rule.risk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.unicorn.facility.exception.ActivitiException;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * 调用风控决策系统-预审版本
 *
 * @author hn
 */
@Transactional(readOnly = false)
@Service("applyInfoRiskToPreCheck")
public class ApplyInfoRiskToPreCheck implements JavaDelegate {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ApplyInfoRiskToPreCheckUtils applyInfoRiskToPreCheckUtils;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyInputService applyInputService;

	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {

		long start = System.currentTimeMillis();
		String appNo = delegateExecution.getProcessBusinessKey();
		logger.info("系统风控决策" + AppConstant.BEGINING + LogPrintUtils.printAppNoLog(appNo, start, null));
		ApplyNodeCommonData applyNodeCommonData = (ApplyNodeCommonData) delegateExecution.getVariable(AppConstant.APPLY_NODE_COMMON_DATA);


		ApplyInfoDto dto = new ApplyInfoDto();
		TmAppMain main = applyQueryService.getTmAppMainByAppNo(appNo);
		if(main==null) {
			throw new ProcessException("风控决策失败，无效的申请件["+appNo+"]信息");
		}
		dto.setTmAppMain(main);
		dto.setTmAppCustInfoList(applyQueryService.getTmAppCustInfoListByAppNo(appNo));
		dto.setTmAppPrimCardInfo(applyQueryService.getTmAppPrimCardInfoByAppNo(appNo));
		Map<String, Serializable> nodeData = new HashMap<>();
		nodeData.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		dto.setTmAppNodeInfoRecordMap(nodeData);
		dto.setAppNo(appNo);
		RtfState nextState = RtfState.B16;
		try {
			dto = applyInfoRiskToPreCheckUtils.cellRiskPreExecute(start, dto, "");
			if(dto!=null) {
				TmAppMain mainNew = dto.getTmAppMain();
				if(mainNew!=null) {
					main = mainNew;
				}
				try {
					nextState = RtfState.valueOf(main.getRtfState());
				} catch (Exception e) {
					logger.warn(LogPrintUtils.printAppNoLog(appNo, start,null)
							+"调用决策后系统未取到有效的申请状态"+main.getRtfState());
				}
				if(nodeData!=null && nodeData.containsKey(AppConstant.APPLY_NODE_COMMON_DATA)) {
					applyNodeCommonData = (ApplyNodeCommonData) nodeData.get(AppConstant.APPLY_NODE_COMMON_DATA);
				}
			}
		} catch (Exception e) {
			logger.error("调用系统风险系统异常！",e);
			if(e instanceof ProcessException) {
				main.setRemark(StringUtils.valueOf(main.getRemark())+e.getMessage());
			}if(e instanceof ProcessException) {
				main.setRemark(StringUtils.valueOf(main.getRemark())+e.getMessage());
			}else if(e instanceof ActivitiException) {
				main.setRemark(StringUtils.valueOf(main.getRemark())+e.getMessage());
			}else {
				main.setRemark(StringUtils.valueOf(main.getRemark())+"调用[决策系统]处理发生未知异常，请联系管理员!");
			}
//			throw new ActivitiException("调用系统风控决策失败.",e);
		}finally {
			main.setRtfState(nextState.state);
			TmAppHistory history = AppCommonUtil.insertApplyHist(main.getAppNo(),
					AppConstant.SYS_AUTO, nextState, main.getRefuseCode(), main.getRemark());
			applyInputService.updateTmAppMain(main);
			applyInputService.saveTmAppHistory(history);
			applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, main);
			nodeData.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
			logger.info("系统风控决策"+AppConstant.END+LogPrintUtils.printAppNoEndLog(appNo, start,null));
		}
		delegateExecution.setVariable(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
	}

}
