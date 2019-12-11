package com.jjb.cas.biz.rule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.sys.MsgType;
import com.jjb.cas.biz.rule.utils.OnlineMakeCardSupport;
import com.jjb.ecms.biz.dao.apply.TmMirCardDao;
import com.jjb.ecms.biz.dao.query.ApplyQueryRankingDao;
import com.jjb.ecms.biz.ext.push.CreditSysPushSupport;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.ApplyQueryRankingDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmMirCard;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 自动节点-成功申请
 * @author JYData-R&D-Big Star
 * @date 2016年9月7日 下午2:00:03
 * @version V1.0  
 */
@Transactional(readOnly=false)
@Service("applyInfoSucceed")
public class ApplyInfoSucceed implements JavaDelegate {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired 
	private ApplyInputService applyInputService;
	@Autowired
	private OnlineMakeCardSupport onlineMakeCardSupport;
	@Autowired
	private ApplyQueryRankingDao applyQueryRankingDao;
	@Autowired
	private TmMirCardDao tmMirCardDao;
	@Autowired
	CreditSysPushSupport creditSysPushSupport;
	/**
	 * 成功申请处理
	 */
	@Override
	public void execute(DelegateExecution delegateexecution) throws Exception {
		long start = System.currentTimeMillis();
		String appNo = delegateexecution.getProcessBusinessKey();
		logger.info("自动节点-成功申请处理"+ AppConstant.BEGINING+ LogPrintUtils.printAppNoLog(appNo, start,null));
		
		ApplyNodeCommonData applyNodeCommonData = (ApplyNodeCommonData)delegateexecution.getVariable(AppConstant.APPLY_NODE_COMMON_DATA);
		ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
		TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
		TmAppAudit tmAppAudit = applyInfoDto.getTmAppAudit();
		tmAppMain.setRtfState(RtfState.L05.state);
		tmAppMain.setUpdateDate(new Date());
		tmAppMain.setUpdateUser(OrganizationContextHolder.getUserNo());
		tmAppMain.setFileFlag("Y");
		if(StringUtils.isEmpty(OrganizationContextHolder.getOrg())){
			OrganizationContextHolder.setOrg(tmAppMain.getOrg());
		}
		
		logger.info("申请编号[{}]，申请类型[{}]", tmAppMain.getAppNo(), tmAppMain.getAppType());
		
		applyNodeCommonData.setRtfStateType(RtfState.L05.state);
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, tmAppMain);
		delegateexecution.setVariable(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		if(StringUtils.equals(tmAppMain.getAppSource(), "05")) {
			TmAppPrimCardInfo cardInfo = applyInfoDto.getTmAppPrimCardInfo();
			Map<String, Object> map = new HashMap<>();
			map.put("idType", tmAppMain.getIdType());
			map.put("idNo", tmAppMain.getIdNo());
//			map.put("name", tmAppMain.getName());
			
			ApplyQueryRankingDto rankDto = applyQueryRankingDao.querySpreadSuccEff(map);
			List<TmMirCard> tmMirCardList1 = tmMirCardDao.getTmMirCardList(map);
			
			if((rankDto==null || StringUtils.isEmpty(rankDto.getSuccEffCnt())
						|| StringUtils.equals(rankDto.getSuccEffCnt(),"0")) 
					&& (!CollectionUtils.sizeGtZero(tmMirCardList1))) {
				cardInfo.setSpreaderIsEff("Y");//设置是否是有效的核卡-主要是给推荐办卡、合伙人使用
				applyInputService.updateTmAppPrimCardInfo(cardInfo);
			}
		}
		applyInputService.updateTmAppMain(tmAppMain);
	    TmAppHistory history = AppCommonUtil.insertApplyHist(tmAppMain.getAppNo(), AppConstant.SYS_AUTO, RtfState.L05, null,null);
		//记录历史
		if(history != null){
			history.setIdType(tmAppMain.getIdType());
			history.setIdNo(tmAppMain.getIdNo());
			history.setName(tmAppMain.getName());
			applyInputService.saveTmAppHistory(history);
		}
//		TmAppInstalLoan appInstalLoan=null;
		applyInfoDto.setTmAppMain(tmAppMain);
		//实时制卡
		onlineMakeCardSupport.onlineMakeCard(applyInfoDto);
		//判断是否实时制卡标识
//		if (StringUtils.equals(tmAppAudit.getIsRealtimeIssuing(),Indicator.Y.name())) {
//			onlineMakeCardSupport.onlineMakeCard(applyInfoDto);
//		}else {
//			//非实时建卡
//			//商户用信额度增加 + 置分期表申请状态为成功
//			if(StringUtils.equals(tmAppAudit.getIsInstalment(),Indicator.Y.name())){
//				TmAppInstalMerchant appInstalMerchant = new TmAppInstalMerchant();
//				TmAppInstalLoan entity = new TmAppInstalLoan();
//				entity.setAppNo(appNo);
//				appInstalLoan = appInstalLoanDao.queryForOne(entity);
//				if(appInstalLoan!=null){
//
//					appInstalMerchant.setMerId(appInstalLoan.getMccNo());
//					appInstalMerchant = appInstalMerchantDao.queryForOne(appInstalMerchant);
//					//本次申请额度
//					BigDecimal inAuditLmt = new BigDecimal(0);
//					if(appInstalLoan.getCashAmt()!=null){
//						inAuditLmt = appInstalLoan.getCashAmt();
//					}
//					appInstalMerchant.setInAuditLmt((appInstalMerchant.getInAuditLmt()==null?BigDecimal.ZERO:appInstalMerchant.getInAuditLmt()).subtract(inAuditLmt));
//					appInstalMerchant.setFinishAuditLmt((appInstalMerchant.getFinishAuditLmt()==null?BigDecimal.ZERO:appInstalMerchant.getFinishAuditLmt()).add(inAuditLmt));
//					appInstalMerchantDao.update(appInstalMerchant);
//				}
//			}
//		}
//		if(appInstalLoan != null){
//			TmProduct product = cacheContext.getProduct(tmAppMain.getProductCd());
//			if(LendingType.B.name().equals(product.getLendingMethod())){
//				appInstalLoan.setLoanRegStatus(LoanRegStatus.B.name());
//			}else {
//				appInstalLoan.setLoanRegStatus(LoanRegStatus.A.name());
//			}
//
//			appInstalLoan.setStatus(RtfState.L05.name());
//			appInstalLoan.setStatusDesc("applySuccessed");
//			appInstalLoan.setUpdateDate(new Date());
//			appInstalLoan.setUpdateUser(OrganizationContextHolder.getUserNo());
//			appInstalLoan.setActiveDate(globalManagementService.getSystemStatus().getBusinessDate());
//			appInstalLoanDao.updateByAppNo(appInstalLoan);
//		}
		//推送审批结果
//		CreditSysPushSupport.asynPushApplyProgress(appNo,tmAppMain);
		creditSysPushSupport.asynPushApplyInfo(appNo, MsgType.CreditLife.name());
		logger.info("自动节点-成功申请处理"+AppConstant.END+LogPrintUtils.printAppNoEndLog(appNo, start,null));
	}
}
