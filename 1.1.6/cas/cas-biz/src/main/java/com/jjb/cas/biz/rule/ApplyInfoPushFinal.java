package com.jjb.cas.biz.rule;

import java.util.Date;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.ext.push.CreditSysPushSupport;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ActivitiException;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 自动节点-推送终审结论
 * @author JYData-R&D-hn
 * @version V1.0  
 */
@Transactional(readOnly=false)
@Service("applyInfoPushFinal")
public class ApplyInfoPushFinal implements JavaDelegate {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ApplyQueryService applyQueryService;

	@Autowired
	private ApplyInputService applyInputService;
	
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private CreditSysPushSupport creditSysPushSupport;
	
	/**
	 * 推送审批最终结论
	 */
	@Override
	public void execute(DelegateExecution delegateexecution) throws Exception {
		
		long start = System.currentTimeMillis();
		String appNo = delegateexecution.getProcessBusinessKey();
		logger.info("自动节点-推送终审结果处理"+AppConstant.BEGINING+LogPrintUtils.printAppNoLog(appNo, start,null));
		
		String org = OrganizationContextHolder.getOrg();
		ApplyNodeCommonData applyNodeCommonData = (ApplyNodeCommonData)delegateexecution.getVariable(AppConstant.APPLY_NODE_COMMON_DATA);
		TmAppMain tmAppMain  = applyQueryService.getTmAppMainByAppNo(appNo);
		if(tmAppMain == null){
			throw new ActivitiException("未获取到客户申请件["+appNo+"]信息，请稍后再试!");
		}
		//此处判断是不是补件超时进来的
		if (tmAppMain.getRtfState() != null && (tmAppMain.getRtfState().equals(RtfState.G05.name())
				|| tmAppMain.getRtfState().equals(RtfState.G15.name())
				|| tmAppMain.getRtfState().equals(RtfState.G16.name())
				|| tmAppMain.getRtfState().equals(RtfState.G03.name()))) {
			tmAppMain.setRefuseCode("OVERTIME-补件超时");
		}
		tmAppMain.setUpdateDate(new Date());
		tmAppMain.setUpdateUser(OrganizationContextHolder.getUserNo());
		if(StringUtils.isEmpty(org)){
			org = tmAppMain.getOrg();
			OrganizationContextHolder.setOrg(org);
		}
		String productCd = tmAppMain.getProductCd();//获取卡产品代码
		TmProduct product = cacheContext.getProduct(productCd);
		if (product == null) {
			logger.info("未获取到卡产品代码["+productCd+"]对应的卡产品！");
			throw new ActivitiException("未获取到相关产品["+productCd+"]信息，产品或已失效，请联系管理员!");
		}
		logger.info("申请编号[{}]，申请类型[{}]", tmAppMain.getAppNo(), tmAppMain.getAppType());
		
		RtfState rtf = RtfState.H20;
		if(StringUtils.equals(RtfState.H21, tmAppMain.getRtfState())) {
			rtf = RtfState.L05;
			if(tmAppMain.getAccLmt()==null) {
				throw new ActivitiException("客户最终授信额度不能为空!");
			}
		}else if(StringUtils.equals(RtfState.H22, tmAppMain.getRtfState())) {
			rtf = RtfState.M05;
		} 
		
		tmAppMain.setRtfState(rtf.name());//推送审批系统结论
		applyNodeCommonData.setRtfStateType(rtf.name());
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, tmAppMain);
		delegateexecution.setVariable(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		applyInputService.updateTmAppMain(tmAppMain);
	    TmAppHistory history = AppCommonUtil.insertApplyHist(tmAppMain.getAppNo(), AppConstant.SYS_AUTO, rtf, tmAppMain.getRefuseCode(),null);
		//记录历史
		if(history != null){
			history.setIdType(tmAppMain.getIdType());
			history.setIdNo(tmAppMain.getIdNo());
			history.setName(tmAppMain.getName());
			applyInputService.saveTmAppHistory(history);
		}
		//推送审批结果(非终审)
		creditSysPushSupport.pushApplyProgress(appNo,"2",tmAppMain);
		
		logger.info("自动节点-推送终审结果处理"+AppConstant.END+LogPrintUtils.printAppNoEndLog(appNo, start,null));
	}
}
