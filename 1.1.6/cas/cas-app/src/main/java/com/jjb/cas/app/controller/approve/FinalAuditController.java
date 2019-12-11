package com.jjb.cas.app.controller.approve;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.unicorn.facility.exception.ProcessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.cas.app.controller.utils.CasAppUtils;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimCardInfoDao;
import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.approve.ApplyFinalAuditService;
import com.jjb.ecms.biz.util.BizAuditHistoryUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppAuditQuota;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmProduct;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.model.Json;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @description 终审操作controller 
 * @author hn
 * @date 2016年8月25日11:53:51
 */

@Controller
@RequestMapping("/cas_finalAuditQuota")
public class FinalAuditController extends BaseController{

	@Autowired
	private ApplyFinalAuditService finalAuditService;
	
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private TmAppMainDao tmAppMainDao;
	
	@Autowired
	private TmAppPrimCardInfoDao tmAppPrimCardInfoDao;
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private TmAppAuditDao tmAppAuditDao;
	@Autowired
	private BizAuditHistoryUtils bizAuditHistoryUtils;
	@Autowired
	private CasAppUtils casAppUtils;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/finalAuditQuotaPage")
	public String finalAuditPage(){
		return "applyManage/applyFinalAuditQuota/applyFinalAuditQuotaPage_V1.ftl";
	}



	@ResponseBody
	@RequestMapping("/refuseValid")
	public  Json  refuseValid(String refuseCodeSmallType){
		Json json = Json.newFail();
		json.setMsg("申请件设置拒绝原因失败");
		if (StringUtils.isNotBlank(refuseCodeSmallType)) {
			try {
				StringBuffer refuseDesc = new StringBuffer();
				refuseCodeSmallType=refuseCodeSmallType.replace("，",",");
				String[] refuseArray = refuseCodeSmallType.split(",");
				List<String> refuseList = Arrays.asList(refuseArray);
				if(refuseList != null && refuseList.size() > 0){
					Map<String,Map<String, String>> applyRejectReasonMap = cacheContext.getApplyRejectReasonMap();
					if(applyRejectReasonMap == null || applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP) == null){
						throw new Exception("系统获取拒绝原因失败!");
					}
					Map<String, String> rejectReasonMap = applyRejectReasonMap.get(AppConstant.REJECT_REASON_MAP);
					for (int i = 0; i < refuseList.size(); i++) {
						refuseDesc.append(refuseList.get(i));
						refuseDesc.append("-");
						if (StringUtils.isEmpty(rejectReasonMap.get(refuseList.get(i)))){
							throw new Exception(refuseList.get(i)+"未找到对应的拒绝原因");
						}
						refuseDesc.append(rejectReasonMap.get(refuseList.get(i)));
						if (i < refuseList.size()-1) {
							refuseDesc.append(",");
						}
					}
				}
				json.setS(true);
				json.setMsg(StringUtils.valueOf(refuseDesc));
			}catch (Exception e){
				json.setS(false);
				json.setMsg("申请件设置拒绝原因失败："+e.getMessage());
			}
		}
		return  json;
	}
	/**
	 * 终审提交
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/finalSubmit")
	public Json FinalSubmit(){
		Json json = Json.newSuccess();
		Query query = getQuery();
		String finalResult = StringUtils.valueOf(query.get("finalResult"));//审批结果
		TmAppMain tmAppMain = getBean(TmAppMain.class);
		TmAppAudit tmAppAudit = getBean(TmAppAudit.class);
		query.put("appNo", tmAppMain.getAppNo());
		String[] refuseCodes = getParas("refuseCode");//拒绝原因
		if(refuseCodes == null){
			String refuseCode = getPara("refuseCode");
			if(StringUtils.isNotEmpty(refuseCode)){
				refuseCodes = new String[]{refuseCode};
			}
		}
		query.put("refuseCodes", refuseCodes);
		//审批额度权限的判断
		try {
			BigDecimal acctLmt = tmAppMain.getAccLmt();
			if(acctLmt != null){
				BigDecimal approvalMaximum = new BigDecimal(0);
				BigDecimal maximum = new BigDecimal(0);
				TmAppAuditQuota TmAppAuditQuota = cacheContext.getTmAppAuditQuotaForCache(OrganizationContextHolder.getUserNo(), "applyinfo-finalaudit");
				if(TmAppAuditQuota != null && TmAppAuditQuota.getApprovalMaximum() != null){
					maximum = TmAppAuditQuota.getApprovalMaximum();
				}
				TmProduct tmProduct = null;
				if(StringUtils.isNotBlank(tmAppMain.getProductCd())){
					tmProduct = cacheContext.getProduct(tmAppMain.getProductCd());
					if(tmProduct != null && tmProduct.getApprovalMaximum() != null){
						approvalMaximum = tmProduct.getApprovalMaximum();
					} else if (tmProduct!=null && tmProduct.getApprovalMaximum() == null) {
						approvalMaximum = maximum;
					}
				}
				if(maximum.compareTo(approvalMaximum)<0){//二者取小
					approvalMaximum = maximum;
				}
				if(approvalMaximum.compareTo(acctLmt) < 0){
					//如果审批额度  < 页面输入额度
					logger.error("该申请件["+tmAppMain.getAppNo()+"]核准额度为["+acctLmt+"],授信额度超限！根据系统设置,产品单笔最高审批额度[" + tmProduct.getApprovalMaximum() + 
							"],您最高可审批额度为" + maximum + ",故您能最大审批授信额度为[" +approvalMaximum+ "]!");
					json.setMsg("授信额度超限！根据系统设置,产品单笔最高审批额度[" + tmProduct.getApprovalMaximum() + 
							"],您最高可审批额度为" + maximum + ",故您能最大审批授信额度为[" +approvalMaximum+ "]!");
					json.setS(false);
					return json;
				}
			}
		} catch (Exception e) {
			json.setFail("审批额度核准有误，请检查操作员额度配置！");
		}
		//申请件标签
		String[] appFlags = getParas("flagApp");
		casAppUtils.setTmAppFlagInfo(tmAppMain.getAppNo(), appFlags);
		
		//提交操作
		try{
			TmAppMain tmAppMainUpdate = applyQueryService.getTmAppMainByAppNo(tmAppMain.getAppNo());
			if(StringUtils.isNotEmpty(tmAppMainUpdate)&&tmAppMainUpdate.getAppLmt()!=null && tmAppMain.getAccLmt()!=null){
				if(tmAppMainUpdate.getAppLmt().compareTo(tmAppMain.getAccLmt()) <0){
					logger.error("审批额度不能大于申请额度！");
					json.setMsg("审批额度不能大于申请额度！");
					json.setS(false);
					return json;
				}
			}
			tmAppMainUpdate.setAppProperty(StringUtils.setValue(tmAppMain.getAppProperty(), tmAppMainUpdate.getAppProperty()));
			tmAppMainUpdate.setProductCd(StringUtils.setValue(tmAppMain.getProductCd(), tmAppMainUpdate.getProductCd()));
			tmAppMainUpdate.setAccLmt(tmAppMain.getAccLmt());
			tmAppMainDao.updateTmAppMain(tmAppMainUpdate);

			TmAppAudit tmAppAuditUpdate = applyQueryService.getTmAppAuditByAppNo(tmAppMain.getAppNo());
			if(tmAppAuditUpdate != null){
				if(tmAppAudit !=null) {
				tmAppAuditUpdate.setIsSendSmsRefused(tmAppAudit.getIsSendSmsRefused());
			}
				if(tmAppAuditUpdate.getId() == null){
					tmAppAuditUpdate.setAppNo(tmAppMain.getAppNo());
					tmAppAuditDao.saveTmAppAudit(tmAppAuditUpdate);
				}else {
					tmAppAuditDao.updateTmAppAudit(tmAppAuditUpdate);
				}
			}

			//卡片信息
			TmAppPrimCardInfo tmAppPrimCardInfoUpdate = tmAppPrimCardInfoDao.getTmAppPrimCardInfoByAppNo(tmAppMain.getAppNo());
			TmAppPrimCardInfo tmAppPrimCardInfo = getBean(TmAppPrimCardInfo.class);
			if (tmAppPrimCardInfo != null) {
				tmAppPrimCardInfoUpdate.setIsPrdChange(tmAppPrimCardInfo.getIsPrdChange());// 是否同意卡片自动降级
				tmAppPrimCardInfoDao.updateTmAppPrimCardInfo(tmAppPrimCardInfoUpdate);
			}
			finalAuditService.saveFinalAuditInfo(query);
			tmAppMainUpdate = applyQueryService.getTmAppMainByAppNo(tmAppMain.getAppNo());
			String msg="操作成功！";
			if(RtfState.Q05.state.equals(tmAppMainUpdate.getRtfState()) && StringUtils.isNotBlank(tmAppMainUpdate.getRefuseCode())){
				msg=tmAppMainUpdate.getRefuseCode().substring(tmAppMainUpdate.getRefuseCode().indexOf("-")+1);
			}
			json.setS(true);
			json.setMsg(msg);
			
		}catch(Exception e){
			logger.error("终审提交操作异常", e);
			json.setS(false);
			json.setFail(e.getMessage());
		}
		bizAuditHistoryUtils.saveAuditHistory(tmAppMain.getAppNo(),"终审提交");//保存审计历史
		return json;
	}

	/**
	 * 终审提交之前校验
	 */
	@ResponseBody
	@RequestMapping("/beforeFinalSubmit")
	public Json beforeFinalSubmit(){
		Query query = getQuery();
		String appNo = "";
		TmAppMain appMain = getBean(TmAppMain.class);
		appNo=appMain.getAppNo();
		Json json=Json.newSuccess();
		try {
			TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
			if (StringUtils.isNotEmpty(tmAppMain) && StringUtils.isNotBlank(tmAppMain.getTaskNum())
					&& !StringUtils.equals(tmAppMain.getTaskNum(), "0")) {
				TmAppMain primaryMain = applyQueryService.getTmAppMainByAppNo(tmAppMain.getTaskNum());
				if (StringUtils.isNotEmpty(primaryMain) && StringUtils.isNotBlank(primaryMain.getRtfState())) {
					if (!StringUtils.equals(primaryMain.getRtfState(), "P05")
							|| !StringUtils.equals(primaryMain.getRtfState(), "Q05")
							|| !StringUtils.equals(primaryMain.getRtfState(), "M05")
							|| !StringUtils.equals(primaryMain.getRtfState(), "A20")) {
						logger.error("多卡同审主件["+primaryMain.getAppNo()+"]尚未处理完成");
						throw new ProcessException("多卡同审主件["+primaryMain.getAppNo()+"]尚未处理完成");
					}
				}
			}
		}catch (Exception e){
			logger.error("提交各种终审数据" + LogPrintUtils.printAppNoLog(appNo,null, null) + "异常", e);
			json.setS(false);
			json.setFail(e.getMessage());
		}
		return json;
	}

	@ResponseBody
	@RequestMapping("/hasApplyInfo")
	public Json hasApplyInfo(String appNo){
		//这个是卡表中的appNo，需要查看申请表中是否有这个申请件编号
		Json j = Json.newSuccess();
		if(StringUtils.isNotEmpty(appNo)){
			try {
				if(CollectionUtils.sizeGtZero(applyQueryService.getTmAppCustInfoListByAppNo(appNo))){
					j.setMsg(appNo);
					j.setS(true);
				}
			} catch (Exception e) {
				j.setS(false);
				j.setMsg("查询该申请件信息出错，请确认该件状态是否正常！");
			}
		}else {
			j.setS(false);
			j.setMsg("未获取到该申请件信息！");
		}
		return j;
	}
}
