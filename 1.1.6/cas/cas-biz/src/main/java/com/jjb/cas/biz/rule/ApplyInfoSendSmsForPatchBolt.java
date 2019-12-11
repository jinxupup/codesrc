package com.jjb.cas.biz.rule;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.bus.EnumsActivitiNodeType;
import com.jjb.acl.facility.enums.bus.Gender;
import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.bus.SmsMessageCategory;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.cas.biz.rule.utils.DatetimeFormatUtil;
import com.jjb.ecms.biz.cache.CacheContext;
import com.jjb.ecms.biz.dao.apply.TmAppRfeDao;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeInquiryData;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppHistory;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.ecms.infrastructure.TmAppRfe;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.util.MapBuilder;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description:自动节点-发送补件短信功能
 * @author JYData-R&D-Big Star
 * @date 2016年9月7日 下午2:01:51
 * @version V1.0  
 */
@Transactional(readOnly=false)
@Service("applyInfoSendSmsForPatchBolt")
public class ApplyInfoSendSmsForPatchBolt implements JavaDelegate {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private CacheContext cacheContext;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private TmAppRfeDao tmAppRfeDao;
	
	@Override
	public void execute(DelegateExecution delegateExecution) throws Exception {
		
		long start = System.currentTimeMillis();
		String appNo = delegateExecution.getProcessBusinessKey();
		logger.info("发送补件短信功能开始"+ AppConstant.BEGINING+ LogPrintUtils.printAppNoLog(appNo, start,null));
		ApplyNodeCommonData applyNodeCommonData = (ApplyNodeCommonData)delegateExecution.getVariable(AppConstant.APPLY_NODE_COMMON_DATA);
		
		Map<String, Serializable> resultMap  = applyQueryService.getNodeInfoByAppNo(appNo, EnumsActivitiNodeType.A025.name());
		ApplyNodeInquiryData applyNodeInquiryData = null;
		if(resultMap!=null && resultMap.containsKey(AppConstant.APPLY_NODE_INQUIRY_DATA)){
			applyNodeInquiryData = (ApplyNodeInquiryData) resultMap.get(AppConstant.APPLY_NODE_INQUIRY_DATA);
		}
		LinkedHashMap<Object, Object>  patchBoltMap = cacheContext.getAclDictByType("ApplyPatchBoltType");
		TmAppMain tmAppMain = applyQueryService.getTmAppMainByAppNo(appNo);
		TmAppAudit appAudit = applyQueryService.getTmAppAuditByAppNo(appNo);
		logger.info("申请编号[{}]，申请类型[{}]", applyNodeCommonData.getAppNo(), applyNodeCommonData.getAppType());
		String org = OrganizationContextHolder.getOrg();
		if(StringUtils.isEmpty(org)){
			org = tmAppMain.getOrg();
		}
		OrganizationContextHolder.setOrg(org);
		String name = tmAppMain.getName();
		String idType = tmAppMain.getIdType();
		String idNo = tmAppMain.getIdNo();
		String cellPhone = tmAppMain.getCellphone();
		Gender gender = null;
		List<String> patchBoltList = applyNodeInquiryData.getPatchBoltList();
		String pbType = getBoltInfo(patchBoltList, patchBoltMap);
		// 补件信息入库
//		TmAppAdd tmAppAdd = new TmAppAdd();
		TmAppHistory history = AppCommonUtil.insertApplyHist(appNo, AppConstant.SYS_AUTO, RtfState.G03, null, pbType);
		logger.info("发送补件短信业务参数["+appAudit.getIsSendSmsPatch()+"]"+LogPrintUtils.printAppNoLog(appNo, start,null));
		if (AppType.A == AppType.valueOf(tmAppMain.getAppType()) 
				|| AppType.B == AppType.valueOf(tmAppMain.getAppType()) ) {// 给主卡持卡人发短信
			
			//通过节点信息判断是否发送补件信息
			if(appAudit.getIsSendSmsPatch()!=null&&appAudit.getIsSendSmsPatch().equals(Indicator.Y.name())){
				String addType = "";
				String smsType = SmsMessageCategory.ECMS01.name();
				gender = AppCommonUtil.getGender(appNo, null, idNo);
				Map<String, Object> params = new MapBuilder().add("applyPatchBoltType", pbType).build();
				//发送补件短信
//				sendSmsSupport.sendSmsMessage(appNo, org, smsType,null, name, gender, cellPhone, params,true);
				if(patchBoltList != null && patchBoltList.size()>0){
					for (String enty : applyNodeInquiryData.getPatchBoltList()) {
						addType = addType+"|"+enty;
					}
				}
//				tmAppAdd.setOrg(org);// 机构号
//				tmAppAdd.setAppNo(appNo);// 申请件编号
			}
			
			
		} else if(AppType.S == AppType.valueOf(tmAppMain.getAppType())){ // 给附卡卡持卡人发短信

			//通过数据库数据信息判断是否发送补件信息
			if(appAudit.getIsSendSmsPatch()!=null&&appAudit.getIsSendSmsPatch().equals(Indicator.Y.name())){
				
				String smsType = SmsMessageCategory.ECMS01.name();
				gender = AppCommonUtil.getGender(appNo, null, idNo);
				Map<String, Object> params = new MapBuilder().add("applyPatchBoltType", pbType).build();
				
				//发送补件短信
//				sendSmsSupport.sendSmsMessage(appNo, org, smsType,null, name, gender, cellPhone, params,true);
			}
			
			String addType = "";
			if (patchBoltList != null && patchBoltList.size() > 0) {
				for (String enty : patchBoltList) {
					addType = addType + "|" + enty;
				}
			}
			history.setName(name);
			history.setIdType(idType);
			history.setIdNo(idNo);
		}
		tmAppMain.setRtfState(RtfState.G03.name());
		// 修改公共信息
		applyNodeCommonData.setRtfStateType(StringUtils.valueOf(RtfState.G03));
		// 补件信息入库
//		tmAppAdd.setSetupDate(new Date());// 处理日期
//		rTmAppAdd.save(tmAppAdd);
		Integer waitTime = null;//默认0天
		String pbWtStr = cacheContext.getPatchBoltParamByType(AppDitDicConstant.pbWaitTime_key);
		if(StringUtils.isNotEmpty(pbWtStr)){
			try {
				Integer int1 = Integer.valueOf(pbWtStr); 
				waitTime = int1;
			} catch (Exception e) {
				logger.error("补件超时默认等待时间转换异常"+pbWtStr,e);
			}
		}
		if(waitTime==null){
			waitTime=1;//默认一天，系统写死的，否则立马就拒绝了，很尴尬
		}
		TmAppPrimCardInfo tmAppPrimCardInfo = applyQueryService.getTmAppPrimCardInfoByAppNo(appNo);
		saveTmAppRfe(tmAppMain, name, idType, idNo, cellPhone, pbType, waitTime,tmAppPrimCardInfo);
		applyInputService.updateTmAppMain(tmAppMain);
		//记录历史
		if(history != null){
			history.setName(name);
			history.setIdType(idType);
			history.setIdNo(idNo);
			applyInputService.saveTmAppHistory(history);
		}
		applyNodeCommonData = AppCommonUtil.setApplyNodeCommonData(applyNodeCommonData, tmAppMain);
		delegateExecution.setVariable(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
		delegateExecution.setVariable(AppConstant.APPLY_PB_STTIME, DatetimeFormatUtil.isoFormat(new Date())); // 补件开始日期
		delegateExecution.setVariable(AppConstant.APPLY_PB_TIMEWAIT, "P"+waitTime+"D"); // 补件等待天数
		
		logger.info("自动节点---补件发送短信处理"+AppConstant.END);
	}
	/**
	 * 返回补件信息
	 * @param patchBoltList
	 * @param patchBoltMap
	 * @return
	 */
	private String getBoltInfo(List<String> patchBoltList, LinkedHashMap<Object, Object> patchBoltMap) {
		
		StringBuffer patchBolt = new StringBuffer(); //补件类型
		if(patchBoltList != null && patchBoltList.size() >0){
			for(String str : patchBoltList){
				String pbStr = StringUtils.setValue(patchBoltMap.get(str),str);
				patchBolt.append(pbStr);
				patchBolt.append("|");
			}
		}
		logger.info("补件信息为："+patchBolt.toString());
		
		if ("".equals(patchBolt.toString())) return "";
		return patchBolt.toString().substring(0, patchBolt.toString().length()-1);
	}
	
	/**
	 * 插入附件申请信息表
	 */
	private void saveTmAppRfe(TmAppMain tmAppMain, String name
			, String idType, String IdNo
			, String cellPhome, String pbType,Integer waitTime,TmAppPrimCardInfo tmAppPrimCardInfo) {
		TmAppRfe tar = new TmAppRfe();
		tar.setAppNo(tmAppMain.getAppNo());
		tmAppRfeDao.deleteTmAppRfe(tar);
		
		TmAppRfe tmAppRfe = new TmAppRfe();
		tmAppRfe.setOrg(tmAppMain.getOrg());
		tmAppRfe.setAppNo(tmAppMain.getAppNo());
		tmAppRfe.setAppType(tmAppMain.getAppType());
		tmAppRfe.setName(name);
		tmAppRfe.setIdType(idType);
		tmAppRfe.setIdNo(IdNo);
		tmAppRfe.setCellphone(cellPhome);
		tmAppRfe.setSpreaderBank(tmAppPrimCardInfo.getSpreaderBranchThree());
		tmAppRfe.setSpreaderName(tmAppPrimCardInfo.getSpreaderName());
		tmAppRfe.setSpreaderNum(tmAppPrimCardInfo.getSpreaderNo());
		tmAppRfe.setPbType(pbType);
		tmAppRfe.setPbStartDate(new Date());
		tmAppRfe.setPbStBatchDate(new Date());
		tmAppRfe.setPbOutBatchDate(AppCommonUtil.DateAfter(new Date(), waitTime));
		tmAppRfe.setPbTimeoutDate(AppCommonUtil.DateAfter(new Date(), waitTime));
		tmAppRfe.setPbStartTime(DatetimeFormatUtil.isoFormat(new Date()));
		tmAppRfe.setPbTimeWait("P"+waitTime+"D");
		tmAppRfeDao.saveTmAppRfe(tmAppRfe);
	}
}
