package com.jjb.ecms.app.controller.manage;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.service.activiti.ActivitiService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.manage.AbnormalProcessAppService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.facility.dto.ApplyAbnormalProcessDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmProductProcess;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Page;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @Description: 异常流程申请件管理
 * @author JYDATA-R&D-L.L
 * @date 2016年9月22日 上午11:17:09
 * @version V1.0
 */
@Controller
@RequestMapping("/abnormalProcess")
public class AbnAppController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private ApplyInputService applyInfoService;

	@Autowired
	private ApplyQueryService applyQueryService;

	@Autowired
	private AbnormalProcessAppService abnormalProcessAppService;
	@Autowired
	private ActivitiService activitiService;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;

	@RequestMapping("/abnormalProcessApply")
	public String getPage(){
		return "applyManage/applyAbnProcess/applyAbnProcessList_V1.ftl";
	}
	
	/**
	 * 查询异常流程申请件页面
	 * 这个sql看起来可能费劲.......
	 * @param appNo
	 * @param rtfState
	 * @return
	 */
	@RequestMapping("/abnormalProcessApplyQuery")
	@ResponseBody
	public Page<ApplyAbnormalProcessDto> abnormalProcessApplyPage(ApplyAbnormalProcessDto abnormalProcessAppDto) {
		Page<ApplyAbnormalProcessDto> page = getPage(ApplyAbnormalProcessDto.class);
//		String appNo =  (String) page.getQuery().get("appNo");
//		String rtfState =(String) page.getQuery().get("rtfState");
//		String cardNo =(String) page.getQuery().get("cardNo");
//		String idNo =(String) page.getQuery().get("idNo");
//		String idType = (String) page.getQuery().get("idType");
//		List<ApplyAbnormalProcessDto> abnormalProcessAppDtos = 
//				abnormalProcessAppService.getAbnormalProcessApp(appNo, rtfState,cardNo,idNo,idType);
//		setAttr("abnormalProcessAppDtos", abnormalProcessAppDtos);
//		page.setRows(abnormalProcessAppDtos);
//		page.setPageNumber(1);
//		page.setPageSize(abnormalProcessAppDtos.size());
//		page.setTotal(abnormalProcessAppDtos.size());
//		return "abnormalProcess/abnormalProcessApply.ftl";
//		return cacheContext.getPageConfig(ApsPagePathConstant.applyAbnProcessList);
		return abnormalProcessAppService.getAbnormalProcessApp(page);
	}

	/**
	 * 发起流程
	 * 
	 * @param appNo
	 */
	@ResponseBody
	@RequestMapping("/execution")
	public Json execution(String appNo) {
		Json json = Json.newSuccess();
		long tokenId = System.currentTimeMillis();
		try {
			if (StringUtils.isEmpty(appNo)) {
				json.setFail("申请件编号为空，系统无法处理，请刷新页面重试");
				return json;
			}
			TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
			//保存审计历史
			bizAuditHistoryUtils.saveAuditHistory(appNo, "异常件管理-发起流程");
//			String appRtfState = tmAppMain.getRtfState();
//			TmAppPrimApplicantInfo primApp = applyQueryService.getTmAppPrimApplicantInfoByAppNo(appNo);
//			TmAppAttachApplierInfo ati1 = null;
//			List<TmAppAttachApplierInfo> attatchInfos = applyQueryService.getTmAppAttachApplierInfoByAppNo(appNo);
//			if (CollectionUtils.sizeGtZero(attatchInfos)) {
//				ati1 = attatchInfos.get(0);
//			}
			Map<String, Serializable> vars = new HashMap<String, Serializable>();
			// applyInfoService.saveApplyInput(applyInfoDto);
			logger.info("删除异常件========>"+ LogPrintUtils.printAppNoLog(appNo, tokenId,null));
			abnormalProcessAppService.delete(appNo);
			// 保存工作流节点数据
			ApplyNodeCommonData applyNodeCommonData = new ApplyNodeCommonData();
			applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData,tmAppMain);
			vars.put(AppConstant.APPLY_NODE_COMMON_DATA,applyNodeCommonData);
			logger.info("启动工作流========>"+ LogPrintUtils.printAppNoLog(appNo, tokenId,null));
			String procdefKey = "";
			if(StringUtils.isNotBlank(tmAppMain.getProductCd())){
				String key = tmAppMain.getProductCd()+"-"+tmAppMain.getAppSource();//每个产品+申请渠道
				String defKey = tmAppMain.getProductCd()+"-def";//每个产品默认的流程图
				if(StringUtils.isEmpty(tmAppMain.getAppSource())) {
					key = tmAppMain.getProductCd()+"-def";
				}
				TmProductProcess proProcess = cacheContext.getProductProcessByProduct(key);
				if(proProcess==null || StringUtils.isEmpty(proProcess.getProcdefKey())) {
					proProcess = cacheContext.getProductProcessByProduct(defKey);
					if(proProcess!=null && StringUtils.isNotEmpty(proProcess.getProcdefKey())) {
						procdefKey = proProcess.getProcdefKey();
					}
				}else {
					procdefKey = proProcess.getProcdefKey();
				}
				if(proProcess==null || StringUtils.isEmpty(proProcess.getProcdefKey())) {
					procdefKey = activitiService.getDefProcess();//系统设置总的默认流程图
				}
			}else {
				//设置默认流程
				procdefKey = activitiService.getDefProcess();
			}
			if(StringUtils.isNotEmpty(procdefKey)){
				activitiService.restartNewProcess(procdefKey, appNo, vars);
//				if(RtfState.Q05.name().equals(appRtfState) && Indicator.Y.name().equals(tmAppMain.getIsInstalment())){
//					logger.info("Q05-制卡失败状态重新发起工作流，分期申请件，需累计商户额度。申请件：{}。",tmAppMain.getAppNo());
//					//实时建卡失败，重新发起流程，分期申请，需累计商户额度
//					TmAppInstalmentCredit credit = applyQueryService.getTmAppInstalmentCreditByAppNo(tmAppMain.getAppNo());
//					if(credit != null && StringUtils.isNotBlank(credit.getMccNo())){
//						TmAppInstalMerchant merchant = tmAppInstalMerchantService.getTmAppInstalMerchantByMccNo(credit.getMccNo());
//						if(merchant != null){
//							logger.info("分期申请件：{}，Q05-制卡失败重新发起流程，累计商户额度，申请金额：{}，商户号：{}，累计前商户审批中金额累计值：{}",
//									tmAppMain.getAppNo(),credit.getCashAmt(),merchant.getMerId(),merchant.getInAuditLmt());
//							merchant.setInAuditLmt((merchant.getInAuditLmt()==null?BigDecimal.ZERO:merchant.getInAuditLmt())
//									.add(credit.getCashAmt()));
//							logger.info("merchant累计审批中金额后的值：{}",merchant.getInAuditLmt());
//							tmAppInstalMerchantService.updateTmAppInstalMerchant(merchant);
//						}
//						credit.setLoanRegStatus(ApsLoanRegStatus.U.name());
//						credit.setUpdateDate(new Date());
//						credit.setUpdateUser(OrganizationContextHolder.getUserNo());
//						tmAppInstalmentCreditService.updateTmAppInstalmentCreditByAppNo(credit);
//					}
//				}
			}else {
				logger.info("没有获取到默认的流程定义，请检查流程！");
				json.setS(false);
				json.setFail("没有获取到默认的流程定义，请检查流程！");
			}
//			activitiService.startNewProcess("application",tmAppMain.getAppNo(), vars);
			logger.info("完成异常件处理========>"+ LogPrintUtils.printAppNoEndLog(appNo, tokenId,null));
		} catch (Exception e) {
			logger.error("重新发起流程失败...." + LogPrintUtils.printAppNoLog(appNo, tokenId,null), e);
			json.setFail("重新发起流程失败...." + e.getMessage());
		}
		return json;
	}
}
