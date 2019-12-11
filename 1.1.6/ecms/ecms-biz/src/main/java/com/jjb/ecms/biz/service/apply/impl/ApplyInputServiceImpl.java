package com.jjb.ecms.biz.service.apply.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjb.acl.facility.enums.bus.RtfState;
import com.jjb.acl.facility.enums.bus.TaskTransferStatus;
import com.jjb.acl.facility.enums.sys.Indicator;
import com.jjb.ecms.biz.dao.apply.TmAppContactDao;
import com.jjb.ecms.biz.dao.apply.TmAppCustInfoDao;
import com.jjb.ecms.biz.dao.apply.TmAppFlagDao;
import com.jjb.ecms.biz.dao.apply.TmAppHistoryDao;
import com.jjb.ecms.biz.dao.apply.TmAppMainDao;
import com.jjb.ecms.biz.dao.apply.TmAppMemoDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimAnnexEviDao;
import com.jjb.ecms.biz.dao.apply.TmAppPrimCardInfoDao;
import com.jjb.ecms.biz.dao.apply.TmAppnoSeqDao;
import com.jjb.ecms.biz.dao.apply.TmTaskTransferDao;
import com.jjb.ecms.biz.dao.approve.TmAppAuditDao;
import com.jjb.ecms.biz.dao.approve.TmExtRiskInputDao;
import com.jjb.ecms.biz.dao.approve.TmExtTriggerRulesDao;
import com.jjb.ecms.biz.dao.node.TmAppNodeInfoDao;
import com.jjb.ecms.biz.service.apply.ApplyInputService;
import com.jjb.ecms.biz.service.apply.ApplyQueryService;
import com.jjb.ecms.biz.service.apply.TmLuckyCardService;
import com.jjb.ecms.biz.service.common.CommonService;
import com.jjb.ecms.biz.util.AppCommonUtil;
import com.jjb.ecms.biz.util.LogPrintUtils;
import com.jjb.ecms.biz.util.NodeObjectUtil;
import com.jjb.ecms.constant.AppConstant;
import com.jjb.ecms.constant.AppDitDicConstant;
import com.jjb.ecms.dto.ApplyInfoDto;
import com.jjb.ecms.facility.dto.ApplyInfoPreDto;
import com.jjb.ecms.facility.nodeobject.ApplyNodeCommonData;
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
import com.jjb.ecms.infrastructure.TmTaskTransfer;
import com.jjb.unicorn.facility.context.OrganizationContextHolder;
import com.jjb.unicorn.facility.exception.ProcessException;
import com.jjb.unicorn.facility.util.CollectionUtils;
import com.jjb.unicorn.facility.util.StringUtils;

/**
 * @Description: 申请件信息保存更新业务处理实现类
 * @author JYData-R&D-Big Star
 * @date 2016年9月1日 下午7:06:15
 * @version V1.0
 */
@Service("applyInputService")
public class ApplyInputServiceImpl implements ApplyInputService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	public TmAppMainDao tmAppMainDao;
	@Autowired
	public TmAppAuditDao tmAppAuditDao;
	@Autowired
	public TmAppCustInfoDao tmAppCustInfoDao;
	@Autowired
	private TmAppnoSeqDao tmAppnoSeqDao;
	@Autowired
	private TmAppMemoDao tmAppMemoDao;
	@Autowired
	private ApplyQueryService applyQueryService;
	@Autowired
	private TmAppPrimCardInfoDao tmAppPrimCardInfoDao;
	@Autowired
	private TmAppPrimAnnexEviDao tmAppPrimAnnexEviDao;
	@Autowired
	private TmAppContactDao tmAppContactInfoDao;
	@Autowired
	private TmAppNodeInfoDao tmAppNodeInforecordDao;
	@Autowired
	private TmAppHistoryDao tmAppHistoryDao;
	@Autowired
	private TmTaskTransferDao tmTaskTransferDao;
	@Autowired
	private NodeObjectUtil nodeObjectUtil;
	@Autowired
	private CommonService commonService;
	@Autowired
	private TmLuckyCardService tmLuckyCardService;
	//	@Autowired
//	private TmAppInstalMerchantDao appInstalMerchantDao;
	@Autowired
	private TmExtRiskInputDao tmExtRiskInputDao;
	@Autowired
	private TmExtTriggerRulesDao tmExtTriggerRulesDao;
    @Autowired
    public TmAppFlagDao tmAppFlagDao;


	@Override
	public List<TmAppMain> select100(Map<String, Object> parameter) {
		return tmAppMainDao.select100(parameter);
	}


	@Override
	@Transactional
	public void saveApplyInput(ApplyInfoDto applyInfoDto) throws SecurityException, IllegalArgumentException,
			NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		String org = OrganizationContextHolder.getOrg();
		String createUser = OrganizationContextHolder.getUserNo();
		if(StringUtils.isEmpty(createUser)){//PAD进件
			createUser= AppConstant.SYS_AUTO.toString();
		}
		Date date = new Date();
		String appNo ="";
		String oldAppNo ="";
		if((StringUtils.isNotEmpty(applyInfoDto.getRetrialFlag())&&applyInfoDto.getRetrialFlag().equals(Indicator.Y.name()))){//重审
			appNo=applyInfoDto.getAppNo();
			oldAppNo=applyInfoDto.getOldAppNo();
		}else if(StringUtils.isNotEmpty(applyInfoDto.getIpadApplyFalg())&&applyInfoDto.getIpadApplyFalg().equals(Indicator.Y.name())
				|| StringUtils.isNotBlank(applyInfoDto.getBatchInputFalg())&&applyInfoDto.getBatchInputFalg().equals(Indicator.Y.name())){//pad进件|批量导入

			appNo=applyInfoDto.getTmAppMain().getAppNo();
			applyInfoDto.setAppNo(appNo);
		}else if (StringUtils.isEmpty(appNo)){
			appNo = tmAppnoSeqDao.getAppNo(org);
			logger.debug("申请件编号"+appNo+"获取成功！");
			applyInfoDto.setAppNo(appNo);
		}
		TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
		TmAppAudit audit = applyInfoDto.getTmAppAudit();
		if(StringUtils.isNotEmpty(applyInfoDto.getRetrialFlag())&&applyInfoDto.getRetrialFlag().equals(Indicator.Y.name())){
			TmAppAudit auditOld =  applyQueryService.getTmAppAuditByAppNo(oldAppNo);
			if(auditOld!=null){
				auditOld.setIsHaveRetrial(Indicator.Y.name());//设置老件已经重审的标志
				auditOld.setUpdateDate(new Date());
				auditOld.setUpdateUser(OrganizationContextHolder.getUserNo());
				tmAppAuditDao.updateTmAppAudit(auditOld);
			}
			if(audit!=null && StringUtils.isNotEmpty(audit.getAppNo())){
				audit.setAppNo(appNo);
				audit.setAppNoHis(oldAppNo);//重申件原件的APPNO
				audit.setUpdateDate(new Date());
				audit.setUpdateUser(createUser);
				tmAppAuditDao.updateTmAppAudit(audit);
			}else{
				auditOld.setId(null);
				auditOld.setAppNo(appNo);
				auditOld.setAppNoHis(oldAppNo);//重申件原件的APPNO
				auditOld.setUpdateDate(new Date());
				auditOld.setUpdateUser(createUser);
				tmAppAuditDao.saveTmAppAudit(auditOld);
			}

			TmAppMain appMain=tmAppMainDao.getTmAppMainByAppNo(oldAppNo);
			if (tmAppMain != null&&appMain!=null) {

				appMain=(TmAppMain) commonService.compareAndUpdateObject(tmAppMain,appMain);
				appMain.setRefuseCode(null);
				appMain.setOrg(org);
				appMain.setAppNo(appNo);
				appMain.setCreateUser(createUser);
				appMain.setCreateDate(date);
				appMain.setUpdateDate(new Date());
				appMain.setUpdateUser(OrganizationContextHolder.getUserNo());
				tmAppMainDao.saveTmAppMain(appMain);
				logger.debug("申请件"+appNo+"的tmAppMain保存成功！");
			}

		}else{
			if (tmAppMain != null) {
				tmAppMain.setOrg(org);
				tmAppMain.setAppNo(appNo);
				tmAppMain.setCreateUser(createUser);
				tmAppMain.setCreateDate(date);
				tmAppMainDao.saveTmAppMain(tmAppMain);
				logger.debug("申请件"+appNo+"的tmAppMain保存成功！");
			}
			if(audit!=null){
				audit.setAppNo(appNo);
				audit.setUpdateDate(new Date());
				audit.setUpdateUser(createUser);
				tmAppAuditDao.saveTmAppAudit(audit);
			}
		}

		TmAppPrimCardInfo tmAppPrimCardInfo = applyInfoDto.getTmAppPrimCardInfo();
		if (tmAppPrimCardInfo != null) {
			tmAppPrimCardInfo.setOrg(org);
			tmAppPrimCardInfo.setAppNo(appNo);
			if(StringUtils.isEmpty(tmAppPrimCardInfo.getInputNo())){
				tmAppPrimCardInfo.setInputNo(createUser);
			}
			if(StringUtils.isEmpty(tmAppPrimCardInfo.getInputName())){
				tmAppPrimCardInfo.setInputName(OrganizationContextHolder.getUserName());//录入员
			}
			if(StringUtils.isEmpty(tmAppPrimCardInfo.getInputTelephone())){
				tmAppPrimCardInfo.setInputTelephone(OrganizationContextHolder.getCellPhone());
			}
			if(tmAppPrimCardInfo.getInputDate()==null){
				tmAppPrimCardInfo.setInputDate(date);//录入日期
			}
			if(StringUtils.isEmpty(tmAppPrimCardInfo.getCreateUser())){
				tmAppPrimCardInfo.setCreateUser(createUser);
			}
			tmAppPrimCardInfo.setCreateDate(date);
			tmAppPrimCardInfoDao.saveTmAppPrimCardInfo(tmAppPrimCardInfo);
			logger.debug("申请件"+appNo+"的主卡卡片tmAppPrimCardInfo信息保存成功！");
		}

		TmAppPrimAnnexEvi tmAppPrimAnnexEvi = applyInfoDto.getTmAppPrimAnnexEvi();
		if (tmAppPrimAnnexEvi != null) {
			tmAppPrimAnnexEvi.setOrg(org);
			tmAppPrimAnnexEvi.setAppNo(appNo);
			tmAppPrimAnnexEvi.setCreateUser(createUser);
			tmAppPrimAnnexEvi.setCreateDate(date);
			tmAppPrimAnnexEviDao.saveTmAppPrimAnnexEvi(tmAppPrimAnnexEvi);
			logger.debug("申请件"+appNo+"的主卡申请人其他证明tmAppPrimAnnexEvi信息保存成功！");
		}
		Map<String, TmAppCustInfo>  custInfoMap = applyInfoDto.getTmAppCustInfoMap();
		if(custInfoMap != null){
			for (Entry<String, TmAppCustInfo> enty : custInfoMap.entrySet()) {

				TmAppCustInfo custInfo = enty.getValue();
				custInfo.setOrg(org);
				custInfo.setAppNo(appNo);
				custInfo.setCreateUser(createUser);
				custInfo.setCreateDate(date);
				tmAppCustInfoDao.save(custInfo);
				logger.debug("申请件"+appNo+"的附卡申请人TmAppCustInfo信息保存成功！");
			}
		}

		Map<String , TmAppContact> tmAppContactInfoMap = applyInfoDto.getTmAppContactMap();
		if(tmAppContactInfoMap != null){
			for (Entry<String , TmAppContact> enty : tmAppContactInfoMap.entrySet()) {

				TmAppContact tmAppContact = enty.getValue();
				tmAppContact.setOrg(org);
				tmAppContact.setAppNo(appNo);
				tmAppContact.setCreateUser(createUser);
				tmAppContact.setCreateDate(date);
				tmAppContactInfoDao.saveTmAppContact(tmAppContact);
				logger.debug("申请件"+appNo+"的联系人tmAppContact信息保存成功！");
			}
		}
		List<TmAppHistory> tmAppHistoryList = applyInfoDto.getTmAppHistoryList();
		if(tmAppHistoryList != null) {
			for (TmAppHistory enty : tmAppHistoryList) {
				saveTmAppHistory(enty);
				logger.debug("申请件"+appNo+"的历史tmAppHistory信息保存成功！");
			}
		}
		if(applyInfoDto.getTmAppMemoMapLast()!=null && applyInfoDto.getTmAppMemoMapLast().size()>0){
			for (TmAppMemo tmAppMemo : applyInfoDto.getTmAppMemoMapLast().values()) {
				tmAppMemo.setAppNo(appNo);
				tmAppMemo.setOrg(org);
				tmAppMemo.setCreateUser(createUser);
				tmAppMemo.setCreateDate(date);
				saveTmAppMemo(tmAppMemo);
			}
		}
//		// add:2017/12/01/ 9:58  by sf
//		TmAppInstalLoan appInstalLoan = applyInfoDto.getTmAppInstalLoan();
//		if(appInstalLoan!=null){
//			TmAppInstalLoan entity = new TmAppInstalLoan();
//			entity.setAppNo(appNo);
//			entity = appInstalLoanDao.queryForOne(entity);
//			if(entity==null){
//				appInstalLoan.setOrg(org);
//				appInstalLoan.setAppNo(tmAppMain.getAppNo());
//				appInstalLoan.setStatus(tmAppMain.getRtfState());
//				appInstalLoan.setCreateUser(createUser);
//				appInstalLoan.setCreateDate(date);
//				appInstalLoan.setIfExpired(Indicator.N.name());
//				appInstalLoanDao.save(appInstalLoan);
//			}
//			logger.debug("申请件"+appNo+"贷款分期表appInstalLoan信息保存成功!");
//		}
		//保存节点数据
		if(StringUtils.isNotEmpty(applyInfoDto.getRetrialFlag())&&applyInfoDto.getRetrialFlag().equals("Y")){
			if(applyInfoDto.getTmAppNodeInfoRecordMap().get(AppConstant.APPLY_NODE_COMMON_DATA)!=null){
				ApplyNodeCommonData applyNodeCommonData=(ApplyNodeCommonData) applyInfoDto.getTmAppNodeInfoRecordMap().get(AppConstant.APPLY_NODE_COMMON_DATA);
				Map<String, Serializable> data = new HashMap<String, Serializable>();
				data.put("appNo", appNo);
				data.put(AppConstant.APPLY_NODE_COMMON_DATA, applyNodeCommonData);
				nodeObjectUtil.insertAllNodeRec(data,appNo);//保存节点信息
			}
		}
		//保存或者更新第三方风控信息与处罚规则详情
		saveOrUpdateTmExtRiskInfo(applyInfoDto);
	}

	@Override
	@Transactional
	public void updateApplyInput(ApplyInfoDto applyInfoDto) {
		try {
			// TODO Auto-generated method stub
			String org = OrganizationContextHolder.getOrg();
			String appNo = applyInfoDto.getAppNo();
			if(StringUtils.isEmpty(appNo)){
				throw new ProcessException("申请件编号为空，无法更新进件信息，请稍后再试!");
			}
			String oldAppNo=applyInfoDto.getOldAppNo();
			String updateUser = OrganizationContextHolder.getUserNo();
			Date date = new Date();

			TmAppMain tmAppMain = applyInfoDto.getTmAppMain();
			TmAppAudit tmAppAudit = applyInfoDto.getTmAppAudit();
			if(StringUtils.isNotEmpty(applyInfoDto.getRetrialFlag())&&applyInfoDto.getRetrialFlag().equals("Y")){
				if (tmAppMain != null) {
					tmAppMain.setOrg(org);
					tmAppMain.setAppNo(appNo);
					tmAppMain.setUpdateUser(updateUser);
					tmAppMain.setUpdateDate(date);
					tmAppMain.setRefuseCode(null);
					tmAppMain.setRefuseCode2(null);
					tmAppMain.setRefuseCode3(null);
					TmAppMain appMain = tmAppMainDao.getTmAppMainByAppNo(appNo);
					if(appMain!=null){
						tmAppMain.setOrg(org);
						tmAppMain.setAppNo(appNo);
						tmAppMain.setUpdateUser(updateUser);
						tmAppMain.setUpdateDate(date);
						tmAppMain.setJpaVersion(appMain.getJpaVersion());
						tmAppMainDao.updateTmAppMain(tmAppMain);
						logger.debug("重审件"+appNo+"的主信息表tmAppMain信息更新成功！");
					}else{
						tmAppMain.setAppNo(appNo);
						tmAppMainDao.saveTmAppMain(tmAppMain);
						logger.debug("申请件"+appNo+"的主信息表tmAppMain信息保存成功！");
					}
				}
				//重申件时需要修改历史件的重审标志
				if(StringUtils.isNotEmpty(oldAppNo)){
					TmAppAudit auditOld =  applyQueryService.getTmAppAuditByAppNo(oldAppNo);
					if(auditOld!=null){
						auditOld.setIsHaveRetrial(Indicator.Y.name());//设置老件已经重审的标志
						auditOld.setUpdateDate(new Date());
						auditOld.setUpdateUser(updateUser);
						tmAppAuditDao.updateTmAppAudit(auditOld);
					}
				}
				if(tmAppAudit!=null){
					TmAppAudit audit =  applyQueryService.getTmAppAuditByAppNo(appNo);
					if(audit!=null){
						tmAppAudit.setUpdateDate(new Date());
						tmAppAudit.setUpdateUser(updateUser);
						tmAppAudit.setAppNoHis(oldAppNo);
						tmAppAudit.setJpaVersion(audit.getJpaVersion());
						tmAppAuditDao.updateTmAppAudit(tmAppAudit);
					}else{
						tmAppAudit.setId(null);
						tmAppAudit.setAppNo(appNo);
						tmAppAudit.setAppNoHis(oldAppNo);
						tmAppAuditDao.saveTmAppAudit(tmAppAudit);
					}
				}
				if(applyInfoDto.getTmAppMemoMapAll()!=null && applyInfoDto.getTmAppMemoMapAll().size()>0){
					for (List<TmAppMemo> tmAppMemos : applyInfoDto.getTmAppMemoMapAll().values()) {
						if(CollectionUtils.sizeGtZero(tmAppMemos)) {
							for (int i = 0; i < tmAppMemos.size(); i++) {
								TmAppMemo tmAppMemo = tmAppMemos.get(i);
								tmAppMemo.setAppNo(appNo);
								tmAppMemo.setOrg(org);
								tmAppMemo.setCreateUser(OrganizationContextHolder.getUserNo());
								tmAppMemo.setCreateDate(date);
								saveTmAppMemo(tmAppMemo);
							}
						}
					}
				}
			}else{
				TmAppMain appMain = tmAppMainDao.getTmAppMainByAppNo(appNo); //老件中的数据，用于保存修改丢失的三个数据
				if(appMain!=null) {
					tmAppMain.setCreateUser(appMain.getCreateUser());
					tmAppMain.setCreateDate(appMain.getCreateDate());
					tmAppMain.setTaskNum(appMain.getTaskNum());
					tmAppMain.setImageNum(appMain.getImageNum());
					tmAppMain.setTaskId(appMain.getTaskId());
					tmAppMain.setJpaVersion(appMain.getJpaVersion());
					tmAppMainDao.updateTmAppMain(tmAppMain);
				}else {
					tmAppMainDao.saveTmAppMain(tmAppMain);
				}
				logger.debug("申请件"+appNo+"的主信息表tmAppMain信息更新成功！");

				TmAppAudit audit =  applyQueryService.getTmAppAuditByAppNo(appNo);

				if(audit!=null){
					tmAppAudit.setAppNo(appNo);
					if(StringUtils.isNotEmpty(audit.getIsOldCust())){//防止联机进件时被覆盖
						tmAppAudit.setIsOldCust(audit.getIsOldCust());
					}
					tmAppAudit.setUpdateDate(new Date());
					tmAppAudit.setUpdateUser(updateUser);
					tmAppAudit.setJpaVersion(audit.getJpaVersion());
					tmAppAuditDao.updateTmAppAudit(tmAppAudit);
				}else {
					tmAppAuditDao.saveTmAppAudit(tmAppAudit);
				}
			}

			//主卡卡片信息
			TmAppPrimCardInfo tmAppPrimCardInfo = applyInfoDto.getTmAppPrimCardInfo();
			if (tmAppPrimCardInfo != null) {
				tmAppPrimCardInfo.setOrg(org);
				tmAppPrimCardInfo.setAppNo(appNo);
				tmAppPrimCardInfo.setUpdateUser(updateUser);
				tmAppPrimCardInfo.setUpdateDate(date);
//				tmAppPrimCardInfo.setJpaVersion(1);

				TmAppPrimCardInfo cardInfo = tmAppPrimCardInfoDao.getTmAppPrimCardInfoByAppNo(appNo);
				if(cardInfo != null){
					tmAppPrimCardInfo.setId(cardInfo.getId());
					tmAppPrimCardInfo.setJpaVersion(cardInfo.getJpaVersion());
					tmAppPrimCardInfoDao.updateTmAppPrimCardInfo(tmAppPrimCardInfo);
					logger.debug("申请件"+appNo+"的主卡卡片tmAppPrimCardInfo信息更新成功！");
				}
				else{

					tmAppPrimCardInfoDao.saveTmAppPrimCardInfo(tmAppPrimCardInfo);
					logger.debug("申请件"+appNo+"的主卡卡片tmAppPrimCardInfo信息保存成功！");
				}
			}
			//附件
			TmAppPrimAnnexEvi tmAppPrimAnnexEvi = applyInfoDto.getTmAppPrimAnnexEvi();
			if (tmAppPrimAnnexEvi != null) {
				tmAppPrimAnnexEvi.setOrg(org);
				tmAppPrimAnnexEvi.setAppNo(appNo);
				tmAppPrimAnnexEvi.setUpdateUser(updateUser);
				tmAppPrimAnnexEvi.setUpdateDate(date);

				TmAppPrimAnnexEvi annexEvi = tmAppPrimAnnexEviDao.getTmAppPrimAnnexEviByAppNo(appNo);
				if(annexEvi != null){
					tmAppPrimAnnexEvi.setId(annexEvi.getId());
					tmAppPrimAnnexEvi.setJpaVersion(annexEvi.getJpaVersion());
					tmAppPrimAnnexEviDao.updateTmAppPrimAnnexEvi(tmAppPrimAnnexEvi);
					logger.debug("申请件"+appNo+"的其他证明tmAppPrimAnnexEvi信息更新成功！");
				}
				else{

					tmAppPrimAnnexEviDao.saveTmAppPrimAnnexEvi(tmAppPrimAnnexEvi);
					logger.debug("申请件"+appNo+"的其他证明tmAppPrimAnnexEvi信息保存成功！");
				}
			}
			//主附卡客户信息入库
			List<TmAppCustInfo> custList = applyInfoDto.getTmAppCustInfoList();
			if(CollectionUtils.isNotEmpty(custList)){
				Map<String,TmAppCustInfo> dbMap = applyQueryService.getTmAppCustInfoMapByAppNo(appNo);
				for (int i = 0; i < custList.size(); i++) {
					TmAppCustInfo t1 = custList.get(i);
					String key = t1.getBscSuppInd()+t1.getAttachNo();
					if(dbMap!=null && dbMap.containsKey(t1.getBscSuppInd()+t1.getAttachNo())){
						TmAppCustInfo d1 = dbMap.get(key);
						t1.setUpdateUser(updateUser);
						t1.setUpdateDate(date);
						t1.setId(d1.getId());
						t1.setJpaVersion(d1.getJpaVersion());
						tmAppCustInfoDao.update(t1);
						logger.debug("申请件"+appNo+"的["+key+"]申请人TmAppCustInfo信息更新成功！");
						dbMap.remove(key);
					}else{
						t1.setCreateDate(date);
						t1.setCreateUser(OrganizationContextHolder.getUserNo());
						tmAppCustInfoDao.save(t1);
						logger.debug("申请件"+appNo+"的["+key+"]申请人TmAppCustInfo信息保存成功！");
					}
				}
				for (TmAppCustInfo db2: dbMap.values()) {
					tmAppCustInfoDao.deleteByKey(db2);
					if (StringUtils.isNotBlank(db2.getCardNo()) && db2.getCardNo().length()>15) {
						tmLuckyCardService.unlock(db2.getCardNo().substring(0, 15),appNo,db2.getAttachNo(),db2.getBscSuppInd());
					}
				}
			}
			//联系人
			Map<String , TmAppContact> tmAppContactInfoMap = applyInfoDto.getTmAppContactMap();
			if(tmAppContactInfoMap != null && tmAppContactInfoMap.size()>0){
				List<TmAppContact> dblist = tmAppContactInfoDao.getTmAppContactListByAppNo(appNo);
				if(CollectionUtils.sizeGtZero(dblist)) {
					for (int i = 0; i < dblist.size(); i++) {
						TmAppContact dbContact = dblist.get(i);
						if ((StringUtils.isNotEmpty(dbContact.getContactType())
								&& tmAppContactInfoMap.containsKey(AppConstant.M_CON_ITEM_INFO_PREFIX + dbContact.getContactType()))
								|| i < 2) {
							if (StringUtils.isEmpty(dbContact.getContactType())) {
								dbContact.setContactType(StringUtils.valueOf(i + 1));
							}
							TmAppContact cont1 = tmAppContactInfoMap
									.get(AppConstant.M_CON_ITEM_INFO_PREFIX + dbContact.getContactType());
							if (cont1 != null) {
								cont1.setOrg(org);
								cont1.setAppNo(appNo);
								cont1.setUpdateUser(updateUser);
								cont1.setUpdateDate(date);
//								cont1.setJpaVersion(1);
								cont1.setJpaVersion(dbContact.getJpaVersion());
								if (cont1.getId() == null) {
									cont1.setId(dbContact.getId());
								}
								tmAppContactInfoDao.updateTmAppContact(cont1);
								logger.debug("申请件" + appNo + "联系人tmAppContact[" + cont1.getContactName() + "|" + cont1.getContactMobile() + "]信息更新成功！");
								tmAppContactInfoMap.remove(AppConstant.M_CON_ITEM_INFO_PREFIX + dbContact.getContactType());
							} else {
								tmAppContactInfoDao.deleteTmAppContact(dbContact);
								logger.debug("申请件" + appNo + "联系人tmAppContact[" + dbContact.getContactName() + "|" + dbContact.getContactMobile() + "]信息删除成功！");
							}
						}
						if (i > 1) {//因为暂时最多只有两个联系人，故而删除异常是联系人数据
							tmAppContactInfoDao.deleteTmAppContact(dbContact);
							logger.debug("申请件" + appNo + "联系人tmAppContact[" + dbContact.getContactName() + "|" + dbContact.getContactMobile() + "]信息删除成功！");
						}
					}
				}
				for (Entry<String , TmAppContact> enty : tmAppContactInfoMap.entrySet()) {
					TmAppContact cont2 = enty.getValue();
					if(cont2 != null){
						cont2.setOrg(org);
						cont2.setAppNo(appNo);
						cont2.setUpdateUser(updateUser);
						cont2.setUpdateDate(date);
//							cont2.setJpaVersion(1);
						if(cont2.getId()==null){

							cont2.setCreateUser(updateUser);
							cont2.setCreateDate(date);
							tmAppContactInfoDao.saveTmAppContact(cont2);
							logger.debug("申请件"+appNo+"联系人tmAppContact+["+cont2.getContactName()+"|"+cont2.getContactMobile()+"]+信息新增成功！");
						}else{
							TmAppContact tmAppContactDb =  tmAppContactInfoDao.queryByKey(cont2);
							cont2.setJpaVersion(tmAppContactDb.getJpaVersion());
							tmAppContactInfoDao.updateTmAppContact(cont2);
							logger.debug("申请件"+appNo+"联系人tmAppContact["+cont2.getContactName()+"|"+cont2.getContactMobile()+"]信息更新成功！");
						}
					}
				}
			}

			if(applyInfoDto.getTmAppMemoMapLast()!=null && applyInfoDto.getTmAppMemoMapLast().size()>0){
				for (TmAppMemo tmAppMemo : applyInfoDto.getTmAppMemoMapLast().values()) {
					tmAppMemo.setAppNo(appNo);
					tmAppMemo.setOrg(org);
					tmAppMemo.setCreateUser(OrganizationContextHolder.getUserNo());
					tmAppMemo.setCreateDate(date);
					saveTmAppMemo(tmAppMemo);
//					tmAppMemoDao.saveTmAppMemo(tmAppMemo);
				}
			}
			List<TmAppHistory> tmAppHistoryList = applyInfoDto.getTmAppHistoryList();  //历史信息只保存，不更新(针对于重审件)
			if(tmAppHistoryList != null) {
				for (TmAppHistory enty : tmAppHistoryList) {
					if(!enty.getAppNo().equals(appNo)) {
						enty.setOrg(org);
						enty.setAppNo(appNo);
//						enty.setCreateDate(enty.getCreateDate());
//						enty.setJpaVersion(1);
						saveTmAppHistory(enty);
						logger.debug("申请件" + appNo + "历史tmAppHistory信息保存成功！");
					}
				}
			}

			if(applyInfoDto.getTmAppNodeInfoRecordMap()!=null){
				nodeObjectUtil.insertAllNodeRec(applyInfoDto.getTmAppNodeInfoRecordMap(),appNo);
				logger.debug("申请件"+appNo+"节点数据TmAppNodeInfoRecord信息保存成功！");
			}
//			//更新分期信息
//			if(applyInfoDto.getTmAppInstalLoan()!=null && "Y".equals(tmAppAudit.getIsInstalment())){
//				TmAppInstalLoan appInstalLoan = applyInfoDto.getTmAppInstalLoan();
//				TmAppInstalLoan entity = new TmAppInstalLoan();
//				entity.setAppNo(appInstalLoan.getAppNo());
//				entity = appInstalLoanDao.queryForOne(entity);
//
//				appInstalLoan.setCreateUser(updateUser);
//				appInstalLoan.setCreateDate(date);
//				appInstalLoan.setStatus(tmAppMain.getRtfState());
//				appInstalLoan.setIfExpired(Indicator.N.name());
//				if(entity==null){
//					appInstalLoanDao.save(appInstalLoan);
//				}else{
//					appInstalLoan.setJpaVersion(entity.getJpaVersion());
//					appInstalLoanDao.updateByAppNo(appInstalLoan);
//				}
//			}else if("N".equals(tmAppAudit.getIsInstalment())){
//				TmAppInstalLoan enCredit = new TmAppInstalLoan();
//				enCredit.setAppNo(appNo);
//				appInstalLoanDao.deleteByAppNo(enCredit);
//			}
			//保存或者更新第三方风控信息与处罚规则详情
			saveOrUpdateTmExtRiskInfo(applyInfoDto);
		} catch (Exception e) {
			logger.error("更新申请件数据失败", e);
		}
	}

	/**
	 * 删除预录入申请信息
	 * @param applyInfoPreDto
	 * 根据applyInfoPreDto获取申请件编号appNo
	 * 申请类型appType
	 * 姓名name
	 * 证件类型idType
	 * 证件号码idNo
	 */
	@Override
	@Transactional
	public void deleteApplyInput(ApplyInfoPreDto applyInfoPreDto) {
		// TODO Auto-generated method stub
		String appNo = applyInfoPreDto.getAppNo();
		String appType = applyInfoPreDto.getAppType();
		String idType = applyInfoPreDto.getIdType();
		String idNo = applyInfoPreDto.getIdNo();
		String name = applyInfoPreDto.getName();

		TmAppHistory tmAppHistory = new TmAppHistory();
		tmAppHistory = AppCommonUtil.insertApplyHist(appNo, OrganizationContextHolder.getUserNo(), RtfState.A20, null, null);
		tmAppHistory.setName(name);
		tmAppHistory.setIdType(idType);
		tmAppHistory.setIdNo(idNo);
		saveTmAppHistory(tmAppHistory);

		TmAppCustInfo tmAppCustInfo = new TmAppCustInfo();
		tmAppCustInfo.setAppNo(appNo);
		tmAppCustInfoDao.deleteTmAppCustInfo(tmAppCustInfo);

		TmAppPrimAnnexEvi tmAppPrimAnnexEvi = new TmAppPrimAnnexEvi();
		tmAppPrimAnnexEvi.setAppNo(appNo);
		tmAppPrimAnnexEviDao.deleteTmAppPrimAnnexEvi(tmAppPrimAnnexEvi);

		TmAppPrimCardInfo tmAppPrimCardInfo = new TmAppPrimCardInfo();
		tmAppPrimCardInfo.setAppNo(appNo);
		tmAppPrimCardInfoDao.deleteTmAppPrimCardInfo(tmAppPrimCardInfo);

		TmAppContact tmAppContact = new TmAppContact();
		tmAppContact.setAppNo(appNo);
		tmAppContactInfoDao.deleteTmAppContact(tmAppContact);

		TmAppNodeInfo tmAppNodeInforecord = new TmAppNodeInfo();
		tmAppNodeInforecord.setAppNo(appNo);
		tmAppNodeInforecordDao.deleteTmAppNodeInfo(tmAppNodeInforecord);

		TmAppMemo tmAppMemo = new TmAppMemo();
		tmAppMemo.setAppNo(appNo);
		tmAppMemoDao.deleteTmAppMemo(tmAppMemo);

		TmAppMain tmAppMain = new TmAppMain();
		tmAppMain.setAppNo(appNo);
		tmAppMainDao.deleteTmAppMain(tmAppMain);//TmAppMain表中的appNo同时也是一个外键，所以要最后执行删除操作
		
		/*TmAppInstalLoan appInstalLoan = new TmAppInstalLoan();
		appInstalLoan.setAppNo(appNo);
		appInstalLoanDao.deleteByAppNo(appInstalLoan);*/
	}

	/**
	 * 更新业务主表
	 */
	@Override
	@Transactional
	public void updateTmAppMain(TmAppMain tmAppMain) {
		if(tmAppMain!=null){
			tmAppMain.setUpdateUser(OrganizationContextHolder.getUserNo());
			if(StringUtils.isEmpty(tmAppMain.getUpdateUser())){
				tmAppMain.setUpdateUser("sysauto");
			}
			tmAppMain.setUpdateDate(new Date());
			tmAppMainDao.update(tmAppMain);

		}
	}
	/**
	 * 获取appNo
	 */
	public String getAppNo(){
		String org = OrganizationContextHolder.getOrg();
		String appNo = tmAppnoSeqDao.getAppNo(org);
		return appNo;
	}

	/**
	 * 保存备注备忘数据
	 * @param appMemo
	 * @return
	 */
	@Override
	@Transactional
	public void saveTmAppMemo(TmAppMemo appMemo) {
		if(appMemo==null){
			return;
		}
		//MemoInfo 备注、备忘文本信息内容
		//MemoType 备注备忘类型,(AppCommonConstant 备注：APP_REMARK ,备忘：APP_MEMO)
		//TaskKey 任务名称,取值详见：EcmsAuthority
		if(StringUtils.isNotEmpty(appMemo.getAppNo()) //进件编号
				/*&& StringUtils.isNotEmpty(appMemo.getMemoInfo())*/
				&& StringUtils.isNotEmpty(appMemo.getMemoType())
				&& StringUtils.isNotEmpty(appMemo.getTaskKey())){
			TmAppMemo paramMode = new TmAppMemo();
			paramMode.setAppNo(appMemo.getAppNo());
			paramMode.setMemoType(appMemo.getMemoType());
			paramMode.setTaskKey(appMemo.getTaskKey());
			List<TmAppMemo> list1 = applyQueryService.getTmAppMemoByParam(paramMode);
			if(CollectionUtils.isNotEmpty(list1)){
				Map<String,TmAppMemo> tmAppMemoMapLast = AppCommonUtil.setTmAppMemoInfoLast(list1);

				TmAppMemo lastMemo = new TmAppMemo();
				for (int i = 0; i < list1.size(); i++) {
					TmAppMemo dbMemo = list1.get(i);
					if(lastMemo.getMemoVersion()==null){
						lastMemo.setMemoVersion(0);
					}
					if(dbMemo.getMemoVersion()==null){
						dbMemo.setMemoVersion(0);
					}
					if(lastMemo.getMemoVersion()<=dbMemo.getMemoVersion()){
						lastMemo = dbMemo;
					}
					if(lastMemo.getMemoInfo()==null){
						lastMemo.setMemoInfo("");
					}
				}
				if(appMemo.getMemoInfo()==null){
					appMemo.setMemoInfo("");
				}
				if(lastMemo.getMemoInfo().equals(appMemo.getMemoInfo())){
					logger.debug("无备注信息更新"+ LogPrintUtils.printAppNoLog(appMemo.getAppNo(), null,null));
					return;
				}
				appMemo.setMemoVersion(lastMemo.getMemoVersion()+1);

			}else{
				appMemo.setMemoVersion(1);
			}
			if(appMemo.getCreateDate()==null){
				appMemo.setCreateDate(new Date());
			}
			if(StringUtils.isEmpty(appMemo.getCreateUser())){
				appMemo.setCreateUser(OrganizationContextHolder.getUserNo());
			}
			tmAppMemoDao.save(appMemo);
		}else{
			logger.error("保存备注失败!关键字段值为空"+LogPrintUtils.printAppNoLog(appMemo.getAppNo(), null,null)
					+"Memo-Type["+appMemo.getMemoType()
					+"],Task-Key["+appMemo.getTaskKey() +"]");
		}
	}

	/**
	 * 修改或保存申请件信息
	 * @param applyInfoDto
	 */
	@Override
	@Transactional
	public void saveOrUpdateApplyInput(ApplyInfoDto applyInfoDto) {}

	/**
	 * 插入或更新 任务转移记录表
	 * 注：所有的字段都在调此方法之前设置，此处不操作
	 * @param tmTaskTransfer
	 * @param source判断是获取还是完成
	 */
	@Override
	@Transactional
	public void saveOrUpdateTmTaskTransfer(TmTaskTransfer tmTaskTransfer, String source) {
		if(tmTaskTransfer != null){
			logger.debug("开始插入任务转移信息TmTaskTransfer："
					+ LogPrintUtils.printAppNoLog(tmTaskTransfer.getAppNo(), null,null));

			String appNo = tmTaskTransfer.getAppNo();//申请件编号
			String rtfState = tmTaskTransfer.getRtfState();//审批状态
			String taskKey = tmTaskTransfer.getTaskKey();
			//如果appNo和rtfState不为空，
			if(StringUtils.isNotBlank(appNo) && StringUtils.isNotBlank(rtfState)){
				TmTaskTransfer transferRecord = new TmTaskTransfer();
				transferRecord.setAppNo(appNo);
				transferRecord.setTaskKey(taskKey);
				transferRecord.setStatus(TaskTransferStatus.COMPLETED.state);//设置已完成的状态
				//先查出非完成状态的申请件
				List<TmTaskTransfer> tmTaskTransfers = tmTaskTransferDao.getUncompletedTask(transferRecord);
				if(CollectionUtils.isNotEmpty(tmTaskTransfers)){
					TmTaskTransfer tmRecordUpdate = tmTaskTransfers.get(0);//取出第一个(查询时已按id倒序)
					tmTaskTransfer.setId(tmRecordUpdate.getId());
					if(AppConstant.Complete.equals(source) && tmRecordUpdate.getClaimTime() != null){//如果是执行完成任务
						tmTaskTransfer.setClaimTime(tmRecordUpdate.getClaimTime());
					}
					tmTaskTransfer.setJpaVersion(tmRecordUpdate.getJpaVersion());
					tmTaskTransferDao.update(tmTaskTransfer);//更新记录
				}else{
					//没有非完成状态的，插入新记录
					tmTaskTransferDao.save(tmTaskTransfer);
				}
			}else{
				logger.error("保存记录失败!关键字段值为空"+LogPrintUtils.printAppNoLog(tmTaskTransfer.getAppNo(), null,null));
			}

		}else{
			logger.error("保存记录失败!tmTaskTransfer对象为空");
		}

	}





	//联系人
	public void updateTmAppContactInfoMap(Map<String , TmAppContact> tmAppContactInfoMap,String appNo){
		if(tmAppContactInfoMap != null && tmAppContactInfoMap.size()>0){
			List<TmAppContact> dblist = tmAppContactInfoDao.getTmAppContactListByAppNo(appNo);
			if(CollectionUtils.sizeGtZero(dblist)){
				for (int i = 0; i < dblist.size(); i++) {
					TmAppContact dbContact = dblist.get(i);
					if((StringUtils.isNotEmpty(dbContact.getContactType())
							&& tmAppContactInfoMap.containsKey(AppConstant.M_CON_ITEM_INFO_PREFIX+dbContact.getContactType()))
							|| i<2){
						if(StringUtils.isEmpty(dbContact.getContactType())){
							dbContact.setContactType(StringUtils.valueOf(i+1));
						}
						TmAppContact cont1 = tmAppContactInfoMap.get(AppConstant.M_CON_ITEM_INFO_PREFIX+dbContact.getContactType());
						if(cont1 != null){
							cont1.setOrg(OrganizationContextHolder.getOrg());
							cont1.setAppNo(appNo);
							cont1.setUpdateUser(OrganizationContextHolder.getUserNo());
							cont1.setUpdateDate(new Date());
//								cont1.setJpaVersion(1);
							if(cont1.getId()==null){
								cont1.setId(dbContact.getId());
							}
							tmAppContactInfoDao.updateTmAppContact(cont1);
							logger.debug("申请件"+appNo+"联系人tmAppContact["+cont1.getContactName()+"|"+cont1.getContactMobile()+"]信息更新成功！");
							tmAppContactInfoMap.remove(AppConstant.M_CON_ITEM_INFO_PREFIX+dbContact.getContactType());
						}else{
							tmAppContactInfoDao.deleteTmAppContact(dbContact);
							logger.debug("申请件"+appNo+"联系人tmAppContact["+dbContact.getContactName()+"|"+dbContact.getContactMobile()+"]信息删除成功！");
						}
					}
					if(i>1){//因为暂时最多只有两个联系人，故而删除异常是联系人数据
						tmAppContactInfoDao.deleteTmAppContact(dbContact);
						logger.debug("申请件"+appNo+"联系人tmAppContact["+dbContact.getContactName()+"|"+dbContact.getContactMobile()+"]信息删除成功！");
					}
				}
			}
			for (Entry<String , TmAppContact> enty : tmAppContactInfoMap.entrySet()) {
				TmAppContact cont2 = enty.getValue();
				if(cont2 != null){
					cont2.setOrg(OrganizationContextHolder.getOrg());
					cont2.setAppNo(appNo);
					cont2.setUpdateUser(OrganizationContextHolder.getUserNo());
					cont2.setUpdateDate(new Date());
//						cont2.setJpaVersion(1);
					if(cont2.getId()==null){

						cont2.setCreateUser(OrganizationContextHolder.getUserNo());
						cont2.setCreateDate(new Date());
						tmAppContactInfoDao.saveTmAppContact(cont2);
						logger.debug("申请件"+appNo+"联系人tmAppContact+["+cont2.getContactName()+"|"+cont2.getContactMobile()+"]+信息新增成功！");
					}else{
						tmAppContactInfoDao.updateTmAppContact(cont2);
						logger.debug("申请件"+appNo+"联系人tmAppContact["+cont2.getContactName()+"|"+cont2.getContactMobile()+"]信息更新成功！");
					}
				}
			}
		}
	}

	/**
	 * 从老件更新欺诈及114信息到重申件(新件)
	 * @param retrialAppNo 重申件(新件)appNo
	 * @param oldAppNo 重申件(老件)appNo
	 */
	/*@Override
	@Transactional
	public void updateCheatInfoFromOld(String oldAppNo, String retrialAppNo) {
		// TODO Auto-generated method stub
		List<TmAppNodeInfo> tmAppNodeInforecordList = tmAppNodeInforecordDao.getTmAppNodeInfoList(oldAppNo);//获取欺诈节点信息
		if(CollectionUtils.sizeGtZero(tmAppNodeInforecordList)){
			for (TmAppNodeInfo tmAppNodeInforecord : tmAppNodeInforecordList) {
				String infoType =  tmAppNodeInforecord.getInfoType();
				if(StringUtils.isNotEmpty(infoType) && infoType.equals(EnumsActivitiNodeType.A020.name())){
					tmAppNodeInforecord.setAppNo(retrialAppNo);//更新appNo
					tmAppNodeInforecordDao.saveTmAppNodeInfo(tmAppNodeInforecord);
				}
			}
		}
	}*/

	/**
	 * 保存或者更新第三方风控信息与处罚规则详情
	 */
	@Override
	@Transactional
	public void saveOrUpdateTmExtRiskInfo(ApplyInfoDto applyInfoDto) {
		try {

			String appNo = applyInfoDto.getAppNo();
			if(StringUtils.isEmpty(appNo)){
				throw new ProcessException("无法保存第三方风控信息，[申请件编号]为空，请稍后重试!");
			}
			Date date = new Date();
			//第三方风控结论信息
			if(applyInfoDto.getTmExtRiskInput()!=null){
				TmExtRiskInput riskInput = applyInfoDto.getTmExtRiskInput();
				TmExtRiskInput riskInputDb = applyQueryService.getTmExtRiskInputByAppNo(appNo);
				if(StringUtils.isEmpty(riskInput.getAppNo())){
					riskInput.setAppNo(appNo);
				}
				if(riskInput.getCreateTime()==null){
					riskInput.setCreateTime(date);
				}
				riskInput.setUpdateTime(date);
				if(riskInputDb==null){
					tmExtRiskInputDao.saveTmExtRiskInput(riskInput);
				}else{
					tmExtRiskInputDao.updateTmExtRiskInput(riskInput);;
				}
			}
			//第三方风控结论标签(其他信息)
			if(applyInfoDto.getTmExtTriggerRulesMap()!=null && applyInfoDto.getTmExtTriggerRulesMap().size()>0){
				Map<String,TmExtTriggerRules> dbRuleMap = applyQueryService.getTmExtTriggerRulesByAppNo(appNo);
				for (TmExtTriggerRules rules : applyInfoDto.getTmExtTriggerRulesMap().values()) {
					if(StringUtils.isEmpty(rules.getAppNo())){
						rules.setAppNo(appNo);
					}
					if(rules.getCreateTime()==null){
						rules.setCreateTime(date);
					}
					if(dbRuleMap!=null && StringUtils.isNotEmpty(rules.getRuleType())
							&& dbRuleMap.containsKey(rules.getRuleType())){
						tmExtTriggerRulesDao.updateTmExtTriggerRules(rules);
					}else{
						tmExtTriggerRulesDao.saveTmExtTriggerRules(rules);
					}

				}
			}

		} catch (Exception e) {
			logger.error("新增/更新第三方风控结论信息异常", e);
			throw new ProcessException("新增/更新第三方风控结论信息失败");
		}
	}

	/*
	 * 更新TmAppPrimAnnexEvi
	 */
	@Override
	@Transactional
	public void updateTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi_new) {
		logger.debug("开始更新人工核查信息（TmAppPrimAnnexEvi）==>");
		tmAppPrimAnnexEviDao.updateTmAppPrimAnnexEvi(tmAppPrimAnnexEvi_new);

	}

	/*
	 * 保存TmAppPrimAnnexEvi
	 */
	@Override
	@Transactional
	public void saveTmAppPrimAnnexEvi(TmAppPrimAnnexEvi tmAppPrimAnnexEvi) {
		logger.debug("开始保存人工核查信息（TmAppPrimAnnexEvi）==>");
		tmAppPrimAnnexEviDao.saveTmAppPrimAnnexEvi(tmAppPrimAnnexEvi);
	}
	/*
	 * 保存TmAppPrimAnnexEvi
	 */
	@Override
	@Transactional
	public void saveTmAppHistory(TmAppHistory tmAppHistory) {
		logger.debug("开始保存进件操作历史记录信息（tmAppHistory）==>");
		tmAppHistoryDao.saveTmAppHistory(tmAppHistory);
	}
	@Override
	@Transactional
	public void saveTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo) {
		tmAppPrimCardInfoDao.saveTmAppPrimCardInfo(tmAppPrimCardInfo);
	}
	@Override
	@Transactional
	public void updateTmAppPrimCardInfo(TmAppPrimCardInfo tmAppPrimCardInfo) {
		tmAppPrimCardInfoDao.updateTmAppPrimCardInfo(tmAppPrimCardInfo);
	}


	@Override
	public void updateTmAppCustInfo(TmAppCustInfo custInfo) {
		tmAppCustInfoDao.updateTmAppCustInfo(custInfo);
		
	}

	/**
     * 删除申请件标签
     *
     * @param tmAppFlagList
     */
    @Override
    public void deleteTmAppFlag(List<TmAppFlag> tmAppFlagList) {
        if (CollectionUtils.sizeGtZero(tmAppFlagList)) {
            for (TmAppFlag tmAppFlag : tmAppFlagList) {
                if (tmAppFlag != null && tmAppFlag.getId() != null) {
                    try {
                        tmAppFlagDao.deleteTmAppFlag(tmAppFlag);
                    } catch (Exception e) {
                        logger.error("删除申请件标签失败 id: [" + tmAppFlag.getId() + "] ");
                    }
                }
            }
        }
    }
    /**
     * 保存申请件标签
     *
     * @param tmAppFlag
     */
    @Override
    public void saveTmAppFlag(TmAppFlag tmAppFlag) {
        if (tmAppFlag != null && StringUtils.isNotEmpty(tmAppFlag.getAppNo()) 
        		&& (tmAppFlag.getId()!=null || tmAppFlag.getFlagCode()!=null)) {
			if (tmAppFlag.getFlagCode().equals("00")){
				return;
			}
            //判断是否重复
            TmAppFlag dbFlag = new TmAppFlag();
            dbFlag.setAppNo(tmAppFlag.getAppNo());
            dbFlag.setId(tmAppFlag.getId());
			dbFlag.setFlagStatus("A");//有效
			dbFlag.setFlagCode(tmAppFlag.getFlagCode());
            try {
            	List<TmAppFlag> list = tmAppFlagDao.queryForList(dbFlag);
            	if(CollectionUtils.sizeGtZero(list)) {
            		logger.debug("该申请件["+tmAppFlag.getAppNo()+"]系统已存在相同的标签，故本次不再新增与更新"+dbFlag.toString());
            		return ;
            	}
            } catch (Exception e) {
                logger.error("查询["+tmAppFlag.getAppNo()+"]存量标签失败!",e);
            }
            logger.info("开始保存申请件标签...");
            try {
                tmAppFlagDao.saveTmAppFlag(tmAppFlag);
                logger.info("保存申请件标签完成...");
            } catch (Exception e) {
                logger.error("保存申请件标签失败 tmAppFlag: [" + tmAppFlag + "]" + e.getMessage());
            }
        } else {
            logger.error("保存申请件标签失败 tmAppFlag 为空！");
            throw new ProcessException("保存申请件标签失败 tmAppFlag 为空！");
        }
    }
    /**
     * 批量保存和删除申请件标签
     * 如果本次新增的标签列表在系统中已经存在，则无更新，如果本次操作列表中有
     * @param tmAppFlag
     */
    @Override
    public void saveOrDelTmAppFlagList(String appNo,List<TmAppFlag> tmAppFlagList) {
		boolean removeKey = false;
        if (CollectionUtils.isNotEmpty(tmAppFlagList)) {
        	TmAppFlag parm = new TmAppFlag();
    		parm.setAppNo(appNo);
    		parm.setFlagType(AppDitDicConstant.FLAG_TYEP_APP);
    		parm.setFlagStatus("A");//只查询有效的
    		Map<String,TmAppFlag> retMap = applyQueryService.getTmAppFlagByParm(parm);
			for (int i = 0; i < tmAppFlagList.size(); i++) {
				TmAppFlag taf = tmAppFlagList.get(i);
				if (taf != null) {
					String key = taf.getFlagType() + taf.getFlagCode();
					//判断tmAppFlagList 里的值 ,是否存在自定义标签,若是允许删除
					if (taf.getFlagType().equals(AppDitDicConstant.FLAG_TYEP_APP)) {
						removeKey = true;
					}
					if (retMap != null && retMap.containsKey(key)
							&& taf.getFlagType().equals(AppDitDicConstant.FLAG_TYEP_APP)) {
						retMap.remove(key);
					} else {
						saveTmAppFlag(taf);
					}
				}
			}
        	if(retMap!=null && retMap.size()>0 && removeKey) {
    			for (TmAppFlag rmFlag : retMap.values()) {
    				//只删除自定义标签
    				if (rmFlag.getFlagType().equals(AppDitDicConstant.FLAG_TYEP_APP)) {
						rmFlag.setFlagStatus("B");//无效
						tmAppFlagDao.updateTmAppFlag(rmFlag);
					}
				}
    		}
//            tmAppFlagList.forEach(this::saveTmAppFlag);
        }
    }
    
}
