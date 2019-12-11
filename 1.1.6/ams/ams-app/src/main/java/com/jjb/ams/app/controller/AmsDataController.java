/**
 * 
 */
package com.jjb.ams.app.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.jjb.acl.facility.enums.bus.AppType;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.dao.apply.TmAppContactDao;
import com.jjb.ecms.biz.dao.apply.TmAppCustInfoDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimAnnexEviDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimCardInfoDao;
import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.nodeobject.AllpyTelCheckRecordItem;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
import com.jjb.ecms.facility.nodeobject.ApplyNodeTelCheckBisicData;
import com.jjb.ecms.facility.nodeobject.ApplyTelInquiryRecordItem;
import com.jjb.ecms.infrastructure.TmAppAudit;
import com.jjb.ecms.infrastructure.TmAppContact;
import com.jjb.ecms.infrastructure.TmAppCustInfo;
import com.jjb.ecms.infrastructure.TmAppMain;
import com.jjb.ecms.infrastructure.TmAppPrimAnnexEvi;
import com.jjb.ecms.infrastructure.TmAppPrimCardInfo;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.model.Query;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;
import com.jjb.unicorn.web.controller.BaseController;

/**
 * @Description: 初审、电调、申请件进度查询修改页面申请人信息保存
 * @author JYData-R&D-Big T.T
 * @date 2018年2月5日 上午10:52:17
 * @version V1.0  
 */

public class AmsDataController extends BaseController {
	
	@Autowired
	private ApplyInputService applyInputService;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private TmAppPrimCardInfoDao tmAppPrimCardInfoDao;
	@Autowired
	private TmAppPrimAnnexEviDao tmAppPrimAnnexEviDao;
	@Autowired
	private TmAppContactDao tmAppContactDao;
	@Autowired
	private TmAppAuditDao tmAppAuditDao;
	@Autowired
	private TmAppCustInfoDao tmAppCustInfoDao;
	
	/**
	 *  初审、电调、申请件进度查询修改页面申请人信息
	 * @param query 参数数据
	 * @param taskState 申请件状态
	 * @param pageType 页面类型 I:申请件修改,C:初审,T:电调
	 * @param logger
	 * @return
	 */
	protected Query setBasicData(Query query,String taskState,String pageType,Logger logger) {
		if(query == null){
			query = getQuery();
		}
		TmAppMain appMainPage = getBean(TmAppMain.class);
		TmAppAudit appAuditPage = getBean(TmAppAudit.class);
		String appNo = appMainPage.getAppNo();// 申请件编号
		long tokenId = System.currentTimeMillis();
		try {
			if(StringUtils.isEmpty(appNo)){
				throw new ProcessException("保存失败，申请进编号为空，请稍候再试！");
			}		
			query.put("appNo", appNo);
			
			ApplyInfoDto applyInfoDto = applyQueryService.getApplyInfoByAppNo(appNo);
			TmAppMain tmAppMainUpdate = applyInfoDto.getTmAppMain();
			tmAppMainUpdate.setAppProperty(appMainPage.getAppProperty());//特批
			TmAppCustInfo primCustdb = applyInfoDto.getTmAppCustInfoMap().get(AppConstant.bscSuppInd_B_1);
			String appType = appMainPage.getAppType();
			if(StringUtils.isEmpty(appType)){
				throw new ProcessException("保存失败，申请类型为空，请稍候再试！");
			}
			if(StringUtils.isEmpty(pageType)){
				throw new ProcessException("保存失败，操作修改历史页面类型参数为空");
			}
			logger.info("保存节点["+taskState+"]操作修改历史页面类型参数为[{}]",pageType);
			//根据申请类型获取历史修改字段对比项
			Map<String,Map<String,String>> fieldsMap = commonService.getHisModifyFieldsMap(appType, appMainPage.getProductCd(), pageType);
			Map<String, Object> modifiedFieldsMap = null;//发生修改的字段
			if (AppType.S.name().equals(appType)) {
				TmAppCustInfo attachCustPage = getBean(TmAppCustInfo.class);
				attachCustPage.setAppNo(appNo);
				if(StringUtils.isBlank(getPara("validBit"))){
					attachCustPage.setCardNo(null);//自选卡号
				}else {
					attachCustPage.setCardNo(getPara("cardBin")+attachCustPage.getCardNo()+getPara("validBit"));//自选卡号
				}
				List<TmAppCustInfo> custs = new ArrayList<TmAppCustInfo>();
				
				TmAppCustInfo attachCustUpdate = applyInfoDto.getTmAppCustInfoMap().get(AppConstant.bscSuppInd_S_1);
				
				modifiedFieldsMap = commonService.compareValue(attachCustPage.convertToMap(),attachCustUpdate.convertToMap(),
						appNo,AppConstant.TM_APP_CUST_INFO_NAME, taskState, fieldsMap.get(AppConstant.TM_APP_CUST_INFO));
				
				attachCustUpdate = commonService.getModifiedClazz(attachCustUpdate, modifiedFieldsMap);
				custs.add(attachCustUpdate);
				tmAppCustInfoDao.updateTmAppCustInfo(attachCustUpdate);
				//后期优化，待办任务和申请进度查询只查TM_APP_MAIN一张表
				if(attachCustUpdate!=null){
					//姓名，证件号码，移动电话,上一任务人......
					if(StringUtils.isNotEmpty(attachCustUpdate.getName())){
						tmAppMainUpdate.setName(attachCustUpdate.getName());
					}
					if(StringUtils.isNotEmpty(attachCustUpdate.getIdNo())){
						tmAppMainUpdate.setIdNo(attachCustUpdate.getIdNo());
					}
					if(StringUtils.isNotEmpty(attachCustUpdate.getIdType())){
						tmAppMainUpdate.setIdType(attachCustUpdate.getIdType());
					}
					if(StringUtils.isNotEmpty(attachCustUpdate.getCellphone())){
						tmAppMainUpdate.setCellphone(attachCustUpdate.getCellphone());
					}
					if(StringUtils.isNotEmpty(attachCustUpdate.getCorpName())){
						tmAppMainUpdate.setCorpName(attachCustUpdate.getCorpName());
					}
					if(StringUtils.isNotEmpty(attachCustUpdate.getEmpPhone())){
						tmAppMainUpdate.setEmpPhone(attachCustUpdate.getEmpPhone());
					}
				}
			} else {
				if(AppType.A.name().equals(appType)){
					//是否拒绝附卡
					String[] ifAttachRefuses = getParas("ifAttachRefuse[]");
					if(ifAttachRefuses == null || ifAttachRefuses.length == 0){
						String ifAttachRefuse = getPara("ifAttachRefuse");
						if(StringUtils.isNotEmpty(ifAttachRefuse)){
							ifAttachRefuses = new String[]{ifAttachRefuse};
						}
					}
					Map<Integer,String> statuMap = new HashMap<Integer,String>();
					if(ifAttachRefuses != null && ifAttachRefuses.length > 0){
						for (String str : ifAttachRefuses) {
							statuMap.put(Integer.valueOf(str),str);
						}
					}
					List<TmAppCustInfo> attachCustList = getList(TmAppCustInfo.class, "attachCust");
					for (int i = 0; i<attachCustList.size(); i++) {
						TmAppCustInfo attachCust = attachCustList.get(i);
						attachCust.setAppNo(appNo);
						if(StringUtils.isBlank(getPara("validBit"+i))){
							attachCust.setCardNo(null);//自选卡号
						}else {
							attachCust.setCardNo(getPara("cardBin"+i)+attachCust.getCardNo()+getPara("validBit"+i));//自选卡号
						}
						TmAppCustInfo attachCustUpdate = applyInfoDto.getTmAppCustInfoMap().get(AppConstant.bscSuppInd_S+(i+1));
						
						modifiedFieldsMap = commonService.compareValue(attachCust.convertToMap(),attachCustUpdate.convertToMap(),
								appNo,AppConstant.TM_APP_CUST_INFO_NAME+(i==0?"":i), taskState, fieldsMap.get(AppConstant.TM_APP_CUST_INFO));
						
						attachCustUpdate = commonService.getModifiedClazz(attachCustUpdate, modifiedFieldsMap);
						attachCustUpdate.setRecordStatus(StringUtils.isBlank(statuMap.get(attachCust.getAttachNo()-4))?null:Indicator.N.name());//N表示拒绝
						tmAppCustInfoDao.updateTmAppCustInfo(attachCustUpdate);
					}
				}
				//第一联系人和其他联系人的处理
				Map<String , TmAppContact> tmAppContactInfoMap = new HashMap<String, TmAppContact>();
				List<TmAppContact> tmAppContactList = getList(TmAppContact.class, "tmAppContact");
				//验证并赋值联系信息
				tmAppContactInfoMap = AppCommonUtil.validAppContactEntityIsNull(tmAppContactInfoMap, tmAppContactList);
				TmAppContact tmAppContact1 = tmAppContactInfoMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"1");
				TmAppContact tmAppContact2 = tmAppContactInfoMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"2");

				Map<String , TmAppContact> oldMap = applyInfoDto.getTmAppContactMap();
				TmAppContact tmAppContactUpdate1 = oldMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"1");
				TmAppContact tmAppContactUpdate2 = oldMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+"2");
				if(tmAppContact1!=null&&(tmAppContact1.getContactName()!=null||
					tmAppContact1.getContactRelation()!=null||tmAppContact1.getContactMobile()!=null)){
					tmAppContact1.setAppNo(appNo);
					tmAppContact1.setContactType("1");
					modifiedFieldsMap = commonService.compareValue(tmAppContact1.convertToMap(),tmAppContactUpdate1.convertToMap(),
							appNo,"亲属"+AppConstant.TM_APP_CONTACT_NAME, taskState, fieldsMap.get(AppConstant.TM_APP_CONTACT+"1"));
					
					tmAppContactUpdate1 = commonService.getModifiedClazz(tmAppContactUpdate1, modifiedFieldsMap);
					
					tmAppContactInfoMap.put(AppConstant.M_CON_ITEM_INFO_PREFIX+"1", tmAppContactUpdate1);
				}
				
		        if(tmAppContact2!=null&&(tmAppContact2.getContactName()!=null||
		        	tmAppContact2.getContactRelation()!=null||tmAppContact2.getContactMobile()!=null)){
		        	if(tmAppContactUpdate2 != null){
		        		tmAppContact2.setAppNo(appNo);
		        		tmAppContact2.setContactType("2");
		        		modifiedFieldsMap = commonService.compareValue(tmAppContact2.convertToMap(),tmAppContactUpdate2.convertToMap(),
		        				appNo, "其他"+AppConstant.TM_APP_CONTACT_NAME, taskState, fieldsMap.get(AppConstant.TM_APP_CONTACT+"2"));
		        		
		        		tmAppContactUpdate2 = commonService.getModifiedClazz(tmAppContactUpdate2, modifiedFieldsMap);
		        	}else{
		        		Map<String, Serializable> map=new HashMap<String, Serializable>();
		        		modifiedFieldsMap = commonService.compareValue(tmAppContact2.convertToMap(),map,
		        				appNo, "其他"+AppConstant.TM_APP_CONTACT_NAME, taskState, fieldsMap.get(AppConstant.TM_APP_CONTACT+"2"));
		        		tmAppContactUpdate2 = new TmAppContact();
		        		tmAppContactUpdate2.setAppNo(appNo);
		        		tmAppContactUpdate2.setContactType("2");
		        		tmAppContactUpdate2 = commonService.getModifiedClazz(tmAppContactUpdate2, modifiedFieldsMap);
		        	}
		        	tmAppContactInfoMap.put(AppConstant.M_CON_ITEM_INFO_PREFIX+"2", tmAppContactUpdate2);
		        }
		        applyInputService.updateTmAppContactInfoMap(tmAppContactInfoMap, appNo);
		        
		        //附件证明信息
		        TmAppPrimAnnexEvi annexEviPage = getBean(TmAppPrimAnnexEvi.class);
		        annexEviPage.setAppNo(appNo);
				if(applyInfoDto.getTmAppPrimAnnexEvi() != null && applyInfoDto.getTmAppPrimAnnexEvi().getId() != null){
					annexEviPage.setId(applyInfoDto.getTmAppPrimAnnexEvi().getId());
				}
				TmAppPrimAnnexEvi tmAppPrimAnnexEviUpdate = applyInfoDto.getTmAppPrimAnnexEvi();
				if(tmAppPrimAnnexEviUpdate == null){
					tmAppPrimAnnexEviUpdate = new TmAppPrimAnnexEvi();
					annexEviPage.setAppNo(appNo);
				}		
				modifiedFieldsMap = commonService.compareValue(annexEviPage.convertToMap(), tmAppPrimAnnexEviUpdate.convertToMap(),
						appNo,AppConstant.TM_APP_PRIM_ANNEX_EVI_NAME, taskState, fieldsMap.get(AppConstant.TM_APP_PRIM_ANNEX_EVI));
				
				tmAppPrimAnnexEviUpdate = commonService.getModifiedClazz(tmAppPrimAnnexEviUpdate, modifiedFieldsMap);		
				if(tmAppPrimAnnexEviUpdate != null){
					if(tmAppPrimAnnexEviUpdate.getId() == null){
						tmAppPrimAnnexEviUpdate.setAppNo(appNo);
						tmAppPrimAnnexEviDao.save(tmAppPrimAnnexEviUpdate);
					}else {
						tmAppPrimAnnexEviDao.update(tmAppPrimAnnexEviUpdate);
					}
				}
				
				//主卡申请人信息
				TmAppCustInfo primCustPage = getBean(TmAppCustInfo.class);
				primCustPage.setAppNo(appNo);
				if(StringUtils.isBlank(getPara("validBit"))){
					primCustPage.setCardNo(null);//自选卡号
				}else {
					primCustPage.setCardNo(getPara("cardBin")+primCustPage.getCardNo()+getPara("validBit"));//自选卡号
				}
				modifiedFieldsMap = commonService.compareValue(primCustPage.convertToMap(),primCustdb.convertToMap(),
						appNo, AppConstant.TM_APP_CUST_INFO_NAME, taskState, fieldsMap.get(AppConstant.TM_APP_CUST_INFO));
				
				primCustdb = commonService.getModifiedClazz(primCustdb, modifiedFieldsMap);
				tmAppCustInfoDao.updateTmAppCustInfo(primCustdb);
				//后期优化，待办任务和申请进度查询只查TM_APP_MAIN一张表
				if(primCustdb!=null){
					//姓名，证件号码，移动电话,上一任务人......
					if(StringUtils.isNotEmpty(primCustdb.getName())){
						tmAppMainUpdate.setName(primCustdb.getName());
					}
					if(StringUtils.isNotEmpty(primCustdb.getIdNo())){
						tmAppMainUpdate.setIdNo(primCustdb.getIdNo());
					}
					if(StringUtils.isNotEmpty(primCustdb.getIdType())){
						tmAppMainUpdate.setIdType(primCustdb.getIdType());
					}
					if(StringUtils.isNotEmpty(primCustdb.getCellphone())){
						tmAppMainUpdate.setCellphone(primCustdb.getCellphone());
					}
					if(StringUtils.isNotEmpty(primCustdb.getCorpName())){
						tmAppMainUpdate.setCorpName(primCustdb.getCorpName());
					}
					if(StringUtils.isNotEmpty(primCustdb.getEmpPhone())){
						tmAppMainUpdate.setEmpPhone(primCustdb.getEmpPhone());
					}
				}
			}

			query.put("isFreeTelCheck", appAuditPage.getIsFreeTelCheck());//免电话调查标志
			query.put("appProperty",tmAppMainUpdate.getAppProperty());//进件属性
			
			modifiedFieldsMap = commonService.compareValue(appAuditPage.convertToMap(), tmAppMainUpdate.convertToMap(),
					appNo, AppConstant.TM_APP_MAIN_NAME, taskState, fieldsMap.get(AppConstant.TM_APP_MAIN));
			
			tmAppMainUpdate = commonService.getModifiedClazz(tmAppMainUpdate, modifiedFieldsMap);
			applyInputService.updateTmAppMain(tmAppMainUpdate);
		
			//卡片信息
			TmAppPrimCardInfo cardInfoPage = getBean(TmAppPrimCardInfo.class);
			//推广方式
			String[] spreaderMode = getParas("tmAppPrimCardInfo.spreaderMode[]");
			if(spreaderMode == null || spreaderMode.length == 0){
				spreaderMode = getParas("tmAppPrimCardInfo.spreaderMode");
			}
			if(spreaderMode != null && spreaderMode.length > 0){
				cardInfoPage.setSpreaderMode(commonService.arrayToString(spreaderMode,","));
			}
			TmAppPrimCardInfo tmAppPrimCardInfoUpdate = null;
			if (applyInfoDto.getTmAppPrimCardInfo() != null && cardInfoPage != null) {
				cardInfoPage.setAppNo(appNo);
				cardInfoPage.setId(applyInfoDto.getTmAppPrimCardInfo().getId());
				tmAppPrimCardInfoUpdate = applyInfoDto.getTmAppPrimCardInfo();
				modifiedFieldsMap = commonService.compareValue(cardInfoPage.convertToMap(), tmAppPrimCardInfoUpdate.convertToMap(),
						appNo,AppConstant.TM_APP_PRIM_CARD_INFO_NAME,taskState, fieldsMap.get(AppConstant.TM_APP_PRIM_CARD_INFO));
				
				tmAppPrimCardInfoUpdate = commonService.getModifiedClazz(tmAppPrimCardInfoUpdate, modifiedFieldsMap);
				tmAppPrimCardInfoDao.updateTmAppPrimCardInfo(tmAppPrimCardInfoUpdate);
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error("申请件["+taskState+"]保存数据异常,"+LogPrintUtils.printAppNoEndLog(appNo, tokenId,null),e);
			throw new ProcessException("申请件["+taskState+"]保存数据异常",e);
		}
			
		return query;
	}
	
	/**
	 * 用于初审修改数据后重新计算评分
	 * @return
	 */
	protected ApplyNodeCommonData getBasicSuggestLimit(Query query, String appNo, Logger logger) {
		if(query == null){
			query = getBasicQuery();
		}
		//获取节点信息
		ApplyInfoDto applyInfo = applyQueryService.getNodeInfoByAppNo(appNo);
		if(applyInfo!=null && applyInfo.getTmAppNodeInfoRecordMap()!=null){
			Map<String, Serializable> map = applyInfo.getTmAppNodeInfoRecordMap();
			ApplyNodeCommonData applyNodeCommonData = (ApplyNodeCommonData) map.get(AppConstant.APPLY_NODE_COMMON_DATA);
			return applyNodeCommonData;
		}
		return null;
	}
	
	/**
	 * 获取参数数据
	 */
	protected Query getBasicQuery() {
		return getQuery();
	}
	
	/**
	 * 电话调查数据保存
	 * @param isSubmit true：提交 ，false：保存
	 * @return
	 */
	protected ApplyNodeTelCheckBisicData setTelCheckBisicData(Boolean isSubmit) {
		//申请电话调查记录
		List<ApplyTelInquiryRecordItem> applyTelInquiryRecordDtoList = getList(ApplyTelInquiryRecordItem.class, "applyTelInquiryRecordDto");		
		//核身问题调查记录
		List<AllpyTelCheckRecordItem> idCheckList = getList(AllpyTelCheckRecordItem.class, "idCheckList");
		//必问问题调查记录
		List<AllpyTelCheckRecordItem> mustCheckList =  getList(AllpyTelCheckRecordItem.class, "mustCheckList");
		//选核问题调查记录
		List<AllpyTelCheckRecordItem> choiceCheckList =  getList(AllpyTelCheckRecordItem.class, "choiceCheckList");
		
		//提交操作需要处理信息不全项
		if(isSubmit){
			//申请电话调查记录信息处理
			if (CollectionUtils.sizeGtZero(applyTelInquiryRecordDtoList)) {
				for (int i = 0; i < applyTelInquiryRecordDtoList.size(); i++) {
					ApplyTelInquiryRecordItem item = applyTelInquiryRecordDtoList.get(i);
					if (item == null) {
						continue;
					}
					if (StringUtils.isBlank(item.getTelType()) || StringUtils.isBlank(item.getTelResult())) {
						applyTelInquiryRecordDtoList.remove(item);
						i--;
						if (applyTelInquiryRecordDtoList == null || applyTelInquiryRecordDtoList.size() == 0) {
							break;
						}
					}
				}
			}
			//选核问题调查记录信息处理
			if(CollectionUtils.sizeGtZero(choiceCheckList)){
				for (AllpyTelCheckRecordItem item : choiceCheckList) {
					if(item==null){
						continue;
					}
					if(StringUtils.isBlank(item.getResult()) || StringUtils.isBlank(item.getAskContent())){
						choiceCheckList.remove(item);
						if(choiceCheckList==null || choiceCheckList.size()==0){
							break;
						}
					}
				}
			}
		}
		ApplyNodeTelCheckBisicData applyNodeTelCheckBisicData = null;
		if(CollectionUtils.sizeGtZero(applyTelInquiryRecordDtoList) || CollectionUtils.sizeGtZero(idCheckList)
				|| CollectionUtils.sizeGtZero(mustCheckList) || CollectionUtils.sizeGtZero(choiceCheckList)){
			applyNodeTelCheckBisicData = new ApplyNodeTelCheckBisicData();
			applyNodeTelCheckBisicData.setTelInquiryRecordItemList(applyTelInquiryRecordDtoList);
			applyNodeTelCheckBisicData.setIdCheckRecordItem(idCheckList);
			applyNodeTelCheckBisicData.setMustCheckRecordItem(mustCheckList);
			applyNodeTelCheckBisicData.setChoiceCheckRecordItem(choiceCheckList);
		}
		
		return applyNodeTelCheckBisicData;
	}
}
